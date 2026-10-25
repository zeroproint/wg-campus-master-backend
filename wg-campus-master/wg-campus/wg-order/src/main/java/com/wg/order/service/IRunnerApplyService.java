package com.wg.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wg.common.core.domain.PageQuery;
import com.wg.common.core.page.TableDataInfo;
import com.wg.order.domain.po.RunnerApply;

/**
 * <p>
 * 互助申请表
 服务类
 * </p>
 */
public interface IRunnerApplyService extends IService<RunnerApply> {

    /**
     * 互助申请分页查询
     * @param runnerApply
     * @param pageQuery
     * @return
     */
    TableDataInfo<RunnerApply> selectPageRunnerApplyList(RunnerApply runnerApply, PageQuery pageQuery);

    /**
     * 互助申请
     * @param runnerApply
     */
    void apply(RunnerApply runnerApply);

    /**
     * 处理申请
     * @param runnerApply
     */
    void handle(RunnerApply runnerApply);
}
