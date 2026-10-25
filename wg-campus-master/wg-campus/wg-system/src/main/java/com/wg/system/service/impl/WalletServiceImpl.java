package com.wg.system.service.impl;

import com.wg.system.domain.po.Wallet;
import com.wg.system.mapper.WalletMapper;
import com.wg.system.service.IWalletService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户表 服务实现类
 * </p>
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements IWalletService {

}
