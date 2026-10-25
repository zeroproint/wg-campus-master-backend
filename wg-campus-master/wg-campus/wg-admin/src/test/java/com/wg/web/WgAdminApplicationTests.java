package com.wg.web;

import com.wg.common.constant.QueueNames;
import com.wg.common.utils.redis.QueueUtils;
import com.wg.framework.websocket.server.NioWebSocketServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.TimeUnit;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBean(NioWebSocketServer.class)
class WgAdminApplicationTests {


    @Test
    void test1() throws Exception {
        QueueUtils.addDelayedQueueObject(
                QueueNames.ORDER_PENDING_ACCEPT_CANCEL,
                "1904730740481687553",10,
                TimeUnit.SECONDS);
    }

}
