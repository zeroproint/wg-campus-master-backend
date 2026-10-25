package com.wg.system.service.impl;

import com.wg.common.core.service.SensitiveService;
import com.wg.common.helper.LoginHelper;
import org.springframework.stereotype.Service;

/**
 * 脱敏服务
 */
@Service
public class SysSensitiveServiceImpl implements SensitiveService {

    /**
     * 是否脱敏
     */
    @Override
    public boolean isSensitive() {
        return !LoginHelper.isAdmin();
    }

}
