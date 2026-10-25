package com.wg.admin.controller.system;

import com.wg.common.core.controller.BaseController;
import com.wg.system.service.IUserPcService;
import com.wg.system.service.IUserService;
import com.wg.system.service.IUserWxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/system/user")
public class UserController extends BaseController {

    private final IUserService userService;
    private final IUserPcService userPcService;
    private final IUserWxService userWxService;




}
