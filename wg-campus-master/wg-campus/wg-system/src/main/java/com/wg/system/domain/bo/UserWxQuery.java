package com.wg.system.domain.bo;


import com.wg.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序用户分页查询
 */
@Data
public class UserWxQuery extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 全局uid
     */
    private Long uid;

    private String openid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机
     */
    private String phone;

    /**
     * 是否互助 0 否 1 是
     */
    private Integer isRunner;

    /**
     * 是否可以下单 0 否 1 是
     */
    private Integer canOrder;

    /**
     * 是否可以接单 0 否 1 是
     */
    private Integer canTake;

    /**
     * 互助绑定学校id
     */
    private Long schoolId;

    /**
     * 互助真实姓名
     */
    private String realname;

    /**
     * 互助性别
     */
    private Integer gender;

    /**
     * 登录地址
     */
    private String loginRegion;
    /**
     * 用户类型 0 超级管理员 1 校区管理员 2 普通管理员 3 普通用户 4 互助用户
     */
    private Integer userType;
    /**
     * 创建人
     */
    private Long createId;

}
