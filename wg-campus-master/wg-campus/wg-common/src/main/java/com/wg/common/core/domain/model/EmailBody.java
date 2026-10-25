package com.wg.common.core.domain.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * email登录对象
 */
@Data
public class EmailBody {
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不可为空")
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不可为空")
    private String code;
}
