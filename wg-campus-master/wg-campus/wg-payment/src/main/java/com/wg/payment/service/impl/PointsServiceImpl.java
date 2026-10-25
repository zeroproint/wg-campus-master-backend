package com.wg.payment.service.impl;

import com.wg.common.config.WgConfig;
import com.wg.common.enums.CapitalType;
import com.wg.payment.domain.po.CapitalFlow;
import com.wg.payment.mapper.CapitalFlowMapper;
import com.wg.system.domain.po.Wallet;
import com.wg.system.mapper.WalletMapper;
import com.wg.system.service.IPointsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 积分服务实现类
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PointsServiceImpl implements IPointsService {

    private final WgConfig wgConfig;
    private final WalletMapper walletMapper;
    private final CapitalFlowMapper capitalFlowMapper;

    /**
     * 新用户注册赠送积分
     * @param uid 用户uid
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRegisterGift(Long uid) {
        Integer giftPoints = wgConfig.getRegisterGiftPoints();
        if (giftPoints == null || giftPoints <= 0) {
            return;
        }
        Wallet wallet = walletMapper.selectById(uid);
        if (wallet == null) {
            log.warn("注册赠送积分失败：钱包不存在，uid={}", uid);
            return;
        }
        //加可用积分
        wallet.setPoints(wallet.getPoints() == null ? giftPoints : wallet.getPoints() + giftPoints);
        wallet.setUpdateTime(LocalDateTime.now());
        walletMapper.updateById(wallet);

        //记录注册赠送流水
        CapitalFlow capitalFlow = new CapitalFlow();
        capitalFlow.setUserId(uid);
        capitalFlow.setPoints(giftPoints);
        capitalFlow.setType(CapitalType.POINTS_GIFT.getCode());
        capitalFlow.setCreateTime(LocalDateTime.now());
        capitalFlowMapper.insert(capitalFlow);
    }
}
