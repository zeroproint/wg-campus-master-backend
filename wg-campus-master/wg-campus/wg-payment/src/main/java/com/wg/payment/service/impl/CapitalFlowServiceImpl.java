package com.wg.payment.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wg.common.core.domain.PageQuery;
import com.wg.common.core.domain.model.LoginUser;
import com.wg.common.core.page.TableDataInfo;
import com.wg.common.enums.DeviceType;
import com.wg.common.enums.UserType;
import com.wg.common.exception.ServiceException;
import com.wg.common.helper.LoginHelper;
import com.wg.payment.domain.po.CapitalFlow;
import com.wg.payment.mapper.CapitalFlowMapper;
import com.wg.payment.service.ICapitalFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资金流动表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CapitalFlowServiceImpl extends ServiceImpl<CapitalFlowMapper, CapitalFlow> implements ICapitalFlowService {

    private final CapitalFlowMapper capitalFlowMapper;

    /**
     * 当前用户资金明细查询
     * @param capitalFlow
     * @param pageQuery
     * @return
     */
    @Override
    public TableDataInfo<CapitalFlow> listCurr(CapitalFlow capitalFlow, PageQuery pageQuery) {
        LambdaQueryWrapper<CapitalFlow> lqm = new LambdaQueryWrapper<CapitalFlow>();
        lqm.orderByDesc(CapitalFlow::getCreateTime);
        lqm.between(ObjectUtil.isNotNull(capitalFlow.getBeginTime()) && ObjectUtil.isNotNull(capitalFlow.getEndTime()),
                CapitalFlow::getCreateTime, capitalFlow.getBeginTime(), capitalFlow.getEndTime());
        lqm.eq(ObjectUtil.isNotNull(capitalFlow.getType()), CapitalFlow::getType, capitalFlow.getType());

        LoginUser loginUser = LoginHelper.getLoginUser();
        if (loginUser.getDeviceType().equals(DeviceType.PC.getDevice())) {
            if (loginUser.getUserType().equals(UserType.SCHOOL_AGENT.getUserType())) {
                lqm.eq(CapitalFlow::getAgentId, loginUser.getUid());
            } else if (loginUser.getUserType().equals(UserType.SUPER_ADMIN.getUserType())) {
                lqm.eq(ObjectUtil.isNotNull(capitalFlow.getAgentId()), CapitalFlow::getAgentId, capitalFlow.getAgentId());
                lqm.eq(ObjectUtil.isNotNull(capitalFlow.getRunnerId()), CapitalFlow::getRunnerId, capitalFlow.getRunnerId());
                lqm.eq(ObjectUtil.isNotNull(capitalFlow.getUserId()), CapitalFlow::getUserId, capitalFlow.getUserId());
            }
        } else if (loginUser.getDeviceType().equals(DeviceType.WX.getDevice())) {
            lqm.eq(CapitalFlow::getRunnerId, loginUser.getUid());
        } else {
            throw new ServiceException("没有该权限");
        }
        Page<CapitalFlow> selectPage = capitalFlowMapper.selectPage(pageQuery.build(), lqm);
        return TableDataInfo.build(selectPage);
    }
}
