package com.wg.system.domain.bo;

import lombok.Data;

/**
 * 更新个人信息请求体
 */
@Data
public class ProfileUpdateDTO {
    private String avatar;

    private String nickname;

    private Integer emailEnable;
}
