package com.wg.payment.service;

import com.wg.payment.domain.po.MoneyRecode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 提现审核表 服务类
 * </p>
 */
public interface IMoneyRecodeService extends IService<MoneyRecode> {

    /**
     * 提交提现
     * @param moneyRecode
     */
    void submit(MoneyRecode moneyRecode);

    /**
     * 处理提现
     * @param moneyRecode
     */
    void handle(MoneyRecode moneyRecode);

    /**
     * 最后一次提现
     * @param userId
     * @return
     */
    MoneyRecode lastRecodeByUid(Long userId);
}
