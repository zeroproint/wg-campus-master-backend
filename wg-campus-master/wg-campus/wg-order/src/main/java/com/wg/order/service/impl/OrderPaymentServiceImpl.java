package com.wg.order.service.impl;

import com.wg.order.domain.po.OrderPayment;
import com.wg.order.mapper.OrderPaymentMapper;
import com.wg.order.service.IOrderPaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class OrderPaymentServiceImpl extends ServiceImpl<OrderPaymentMapper, OrderPayment> implements IOrderPaymentService {

}
