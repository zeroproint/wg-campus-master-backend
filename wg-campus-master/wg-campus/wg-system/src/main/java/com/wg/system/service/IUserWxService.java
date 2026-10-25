package com.wg.system.service;

import com.wg.common.core.domain.PageQuery;
import com.wg.common.core.domain.entity.User;
import com.wg.common.core.domain.entity.UserWx;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wg.common.core.page.TableDataInfo;
import com.wg.system.domain.bo.UserWxQuery;

/**
 * <p>
 * 小程序普通用户表 服务类
 * </p>
 */
public interface IUserWxService extends IService<UserWx> {

    /**
     * 创建新用户
     * @return
     */
    UserWx createNewUser();

    UserWx getByUid(Long uid);

    /**
     * 获取WX用户列表
     * @param userWxQuery
     * @param pageQuery
     * @return
     */
    TableDataInfo<User> selectPageUserWxList(UserWxQuery userWxQuery, PageQuery pageQuery);
}
