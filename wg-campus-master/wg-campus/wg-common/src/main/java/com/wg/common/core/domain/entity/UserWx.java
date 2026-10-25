package com.wg.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 小程序普通用户表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_wx")
public class UserWx implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("uid")
    private Long uid;

    /**
     * 小程序唯一id
     */
    @TableField("openid")
    private String openid;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 手机
     */
    @TableField("phone")
    private String phone;

    /**
     * 积分
     */
    @TableField("points")
    private Integer points;

    /**
     * 是否互助 0 否 1 是
     */
    @TableField("is_runner")
    private Integer isRunner;

    /**
     * 是否可以下单 0 否 1 是
     */
    @TableField("can_order")
    @NotNull(message = "下单权限不可为空")
    private Integer canOrder;

    /**
     * 是否可以接单 0 否 1 是
     */
    @TableField("can_take")
    @NotNull(message = "接单权限不可为空")
    private Integer canTake;

    /**
     * 互助绑定学校id
     */
    @TableField("school_id")
    private Long schoolId;

    /**
     * 互助绑定学校名字
     */
    @TableField(exist = false)
    private String schoolName;

    /**
     * 互助真实姓名
     */
    @TableField("realname")
    private String realname;

    /**
     * 互助性别
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 信用分
     */
    @TableField("credit_score")
    private Integer creditScore;
}
