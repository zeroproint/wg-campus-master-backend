package com.wg.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.wg.framework.websocket.server.NioWebSocketServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 积分改造第 3 块（注册赠送积分）端到端验证。
 * <p>
 * 需要本机 MySQL(campus_mutual_aid) + Redis 已启动。用例会 mock 微信接口拿到固定 openid，
 * 走真实的 /xcxLogin 注册流程，断言钱包和流水，结束后清理测试账号。
 * <p>
 * 只跑这一个：
 * mvn -pl wg-admin -am test -Dtest=RegisterGiftIT -Dsurefire.failIfNoSpecifiedTests=false
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterGiftIT {

    private static final String TEST_OPENID = "it-register-gift-openid";
    private static final int EXPECTED_GIFT = 100;

    @MockBean
    private WxMaService wxMaService;

    @MockBean
    private NioWebSocketServer nioWebSocketServer;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void cleanup() {
        List<Long> uids = jdbcTemplate.queryForList(
                "SELECT uid FROM user_wx WHERE openid = ?", Long.class, TEST_OPENID);
        for (Long uid : uids) {
            jdbcTemplate.update("DELETE FROM capital_flow WHERE user_id = ?", uid);
            jdbcTemplate.update("DELETE FROM wallet WHERE uid = ?", uid);
            jdbcTemplate.update("DELETE FROM user_wx WHERE uid = ?", uid);
            jdbcTemplate.update("DELETE FROM `user` WHERE uid = ?", uid);
        }
    }

    @Test
    void registerGift() throws Exception {
        // mock 微信登录：同一个 code 永远返回同一个 openid
        WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
        session.setOpenid(TEST_OPENID);
        WxMaUserService wxMaUserService = mock(WxMaUserService.class);
        when(wxMaService.getUserService()).thenReturn(wxMaUserService);
        when(wxMaUserService.getSessionInfo(anyString())).thenReturn(session);

        // 第一次登录 = 自动注册
        ResponseEntity<String> resp = restTemplate.postForEntity(
                "/xcxLogin?xcxCode=it-code", null, String.class);
        assertEquals(200, resp.getStatusCodeValue(), "登录接口应返回 200，实际：" + resp.getBody());
        assertTrue(resp.getBody() != null && resp.getBody().contains("token"),
                "登录响应里应有 token，实际：" + resp.getBody());

        Long uid = jdbcTemplate.queryForObject(
                "SELECT uid FROM user_wx WHERE openid = ?", Long.class, TEST_OPENID);
        Integer points = jdbcTemplate.queryForObject(
                "SELECT points FROM wallet WHERE uid = ?", Integer.class, uid);
        Integer frozenPoints = jdbcTemplate.queryForObject(
                "SELECT frozen_points FROM wallet WHERE uid = ?", Integer.class, uid);
        assertEquals(EXPECTED_GIFT, points, "注册赠送后可用积分应为 " + EXPECTED_GIFT);
        assertEquals(0, frozenPoints, "注册赠送不应产生冻结积分");

        Integer giftFlows = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM capital_flow WHERE user_id = ? AND type = 4 AND points = ? AND order_id IS NULL",
                Integer.class, uid, EXPECTED_GIFT);
        assertEquals(1, giftFlows, "应有且只有一条注册赠送流水(type=4, points=+100, order_id=NULL)");

        // 第二次登录（同一 openid）不应重复赠送
        restTemplate.postForEntity("/xcxLogin?xcxCode=it-code", null, String.class);
        Integer pointsAfter = jdbcTemplate.queryForObject(
                "SELECT points FROM wallet WHERE uid = ?", Integer.class, uid);
        Integer giftFlowsAfter = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM capital_flow WHERE user_id = ? AND type = 4",
                Integer.class, uid);
        assertEquals(EXPECTED_GIFT, pointsAfter, "重复登录不应再次赠送积分");
        assertEquals(1, giftFlowsAfter, "重复登录不应多写赠送流水");
    }
}
