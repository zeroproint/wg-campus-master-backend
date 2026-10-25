package com.wg.order.service;

import com.wg.common.core.domain.PageQuery;
import com.wg.common.core.page.TableDataInfo;
import com.wg.order.domain.po.School;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface ISchoolService extends IService<School> {

    /**
     * 校区分页查询
     * @param school
     * @param pageQuery
     * @return
     */
    TableDataInfo<School> selectPageSchoolList(School school, PageQuery pageQuery);
}
