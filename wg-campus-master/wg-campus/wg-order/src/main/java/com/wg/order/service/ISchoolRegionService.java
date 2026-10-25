package com.wg.order.service;

import com.wg.order.domain.po.SchoolRegion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wg.order.domain.vo.RegionVO;

import java.util.List;

/**
 * <p>
 * 学校楼栋管理表 服务类
 * </p>
 */
public interface ISchoolRegionService extends IService<SchoolRegion> {

    /**
     * 校区区域分页查询
     * @return
     */
    List<RegionVO> selectSchoolRegionListAll(Long schoolId);

    /**
     * 校区区域列表查询
     * @param schoolRegion
     * @return
     */
    List<SchoolRegion> selectSchoolRegionList(SchoolRegion schoolRegion);
}
