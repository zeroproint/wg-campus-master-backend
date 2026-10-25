package com.wg.payment.service;

import com.wg.common.core.domain.PageQuery;
import com.wg.common.core.page.TableDataInfo;
import com.wg.payment.domain.po.CapitalFlow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 资金流动表 服务类
 * </p>
 */
public interface ICapitalFlowService extends IService<CapitalFlow> {

    TableDataInfo<CapitalFlow> listCurr(CapitalFlow capitalFlow, PageQuery pageQuery);
}
