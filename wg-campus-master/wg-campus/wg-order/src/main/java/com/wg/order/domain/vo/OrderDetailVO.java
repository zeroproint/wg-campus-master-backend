package com.wg.order.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wg.order.domain.po.OrderAttachment;
import com.wg.order.domain.po.OrderMain;
import com.wg.order.domain.po.OrderPayment;
import com.wg.order.domain.po.OrderProgress;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情返回体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "order_main",autoResultMap = true)
public class OrderDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String avatarRunner;
    private String nicknameRunner;

    private String avatarUser;
    private String nicknameUser;

    /**
     * 订单基本表
     */
    private OrderMain orderMain;

    /**
     * 订单支付表
     */
    private OrderPayment orderPayment;

    /**
     * 订单流程表
     */
    private OrderProgress progress;

    /**
     * 附件图片
     */
    private List<OrderAttachment> attachImages;

    /**
     * 附件文件
     */
    private List<OrderAttachment> attachFiles;

    /**
     * 完成凭证
     */
    private List<OrderAttachment> completionImages;
}
