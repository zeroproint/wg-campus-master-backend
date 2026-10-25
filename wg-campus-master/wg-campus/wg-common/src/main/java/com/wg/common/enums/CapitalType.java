package com.wg.common.enums;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

/**
 * 明细类型
 */
@Getter
@RequiredArgsConstructor
public enum CapitalType {
    /**
     * 订单收益
     */
    ORDER_COMPLETE(0,"订单收益"),

    /**
     * 互助提现
     */
    RECODE_RUNNER(1,"互助提现"),

    /**
     * 代理提现
     */
    RECODE_AGENT(2,"代理提现"),

    /**
     * 积分充值
     */
    POINTS_RECHARGE(3,"积分充值"),

    /**
     * 注册赠送
     */
    POINTS_GIFT(4,"注册赠送"),

    /**
     * 积分冻结
     */
    POINTS_FREEZE(5,"积分冻结"),

    /**
     * 积分解冻划转
     */
    POINTS_RELEASE(6,"积分解冻划转"),

    /**
     * 积分退回
     */
    POINTS_REFUND(7,"积分退回");

    private final Integer code;

    private final String type;

}
