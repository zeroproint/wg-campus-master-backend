package com.wg.order.domain.vo;

import com.wg.order.domain.po.OrderAppeal;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 订单申诉返回体
 */
@Data
public class OrderAppealVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private OrderAppeal orderAppeal;

    private List<String> imageUrls;

}
