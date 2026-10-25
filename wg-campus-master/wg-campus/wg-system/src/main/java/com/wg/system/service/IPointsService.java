package com.wg.system.service;

/**
 * <p>
 * 积分服务类
 * </p>
 */
public interface IPointsService {

    /**
     * 新用户注册赠送积分（加可用积分并记录流水）
     * @param uid 用户uid
     */
    void grantRegisterGift(Long uid);
}
