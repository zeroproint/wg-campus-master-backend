package com.wg.payment.domain.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资金流动表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("capital_flow")
public class CapitalFlow {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 代理id
     */
    @TableField("agent_id")
    private Long agentId;

    /**
     * 代理收益
     */
    @TableField("profit_agent")
    private BigDecimal profitAgent;

    /**
     * 互助id
     */
    @TableField("runner_id")
    private Long runnerId;

    /**
     * 互助收益
     */
    @TableField("profit_runner")
    private BigDecimal profitRunner;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户收益
     */
    @TableField("profit_user")
    private BigDecimal profitUser;

    /**
     * 平台收益
     */
    @TableField("profit_plat")
    private BigDecimal profitPlat;



    /**
     * 类型 0订单收益 1互助提现 2代理提现 3积分充值 4注册赠送 5积分冻结 6积分解冻划转 7积分退回
     */
    @TableField("type")
    private Integer type;

    /**
     * 积分变动（正为收入，负为支出）
     */
    @TableField("points")
    private Integer points;

    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 搜索值
     */
    @JsonIgnore
    @TableField(exist = false)
    private LocalDateTime beginTime;

    /**
     * 搜索值
     */
    @JsonIgnore
    @TableField(exist = false)
    private LocalDateTime endTime;
}
