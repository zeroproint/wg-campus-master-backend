package com.wg.system.service.impl;

import com.wg.common.core.domain.entity.RolePerm;
import com.wg.system.mapper.RolePermMapper;
import com.wg.system.service.IRolePermService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和权限 关联表 服务实现类
 * </p>
 */
@Service
public class RolePermServiceImpl extends ServiceImpl<RolePermMapper, RolePerm> implements IRolePermService {

}
