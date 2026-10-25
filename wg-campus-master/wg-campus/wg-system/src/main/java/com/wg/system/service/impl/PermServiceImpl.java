package com.wg.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wg.common.core.domain.entity.Perm;
import com.wg.common.core.domain.entity.RolePerm;
import com.wg.system.mapper.PermMapper;
import com.wg.system.mapper.RolePermMapper;
import com.wg.system.service.IPermService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements IPermService {


    private final PermMapper permMapper;
    private final RolePermMapper rolePermMapper;

    @Override
    public Collection<String> selectMenuPermsByUserType(Integer userType) {
        List<RolePerm> rolePerms = new ArrayList<>();
        if (userType != null) {
            rolePerms = rolePermMapper.selectList(new LambdaQueryWrapper<RolePerm>()
                    .eq(RolePerm::getRoleId, userType));
        }
        if (!rolePerms.isEmpty()) {
            List<Long> collect = rolePerms.stream().map(RolePerm::getPermId).collect(Collectors.toList());
            List<Perm> perms = permMapper.selectBatchIds(collect);
            return perms.stream().map(Perm::getPerms).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
