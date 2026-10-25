package com.wg.system.domain.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户账户表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uid
     */
    @TableId(value = "uid", type = IdType.INPUT)
    private Long uid;

    /**
     * 已提现（累计提现金额）
     */
    @TableField("withdrawn")
    private BigDecimal withdrawn;

    /**
     * 当前余额（真实货币，互助员收益可提现）
     */
    @TableField("balance")
    private BigDecimal balance;

    /**
     * 可用积分
     */
    @TableField("points")
    private Integer points;

    /**
     * 冻结积分（发单托管中）
     */
    @TableField("frozen_points")
    private Integer frozenPoints;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
