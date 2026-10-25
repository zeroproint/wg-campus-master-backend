package com.wg.order.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单大厅查询返回体
 */
@Data
public class OrderHallVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 学校id
     */
    private Long schoolId;

    /**
     * 服务类型 0 帮取送 1 代买 2 万能服务
     */
    private Integer serviceType;
    /**
     * 标签
     */
    private String tag;
    /**
     * 起点地址
     */
    private AddressVO startAddress;
    /**
     * 终点地址
     */
    private AddressVO endAddress;
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
