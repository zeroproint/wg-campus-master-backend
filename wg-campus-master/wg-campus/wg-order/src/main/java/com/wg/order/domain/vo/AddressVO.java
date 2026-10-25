package com.wg.order.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户地址返回体
 */
@Data
public class AddressVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 地点
     */
    @NotBlank(message = "先填写地址")
    private String title;

    /**
     * 地址详情
     */
    private String detail;

    /**
     * 经度
     */
    private String lon;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;





}
