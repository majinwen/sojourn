package com.zra.m.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/14.
 */
public class MZOProDto {
    @ApiModelProperty(value = "项目id")
    private String proId;
    @ApiModelProperty(value = "项目名称")
    private String proName;
    @ApiModelProperty(value = "项目风采展示图片URL")
    private String proShowImg;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProShowImg() {
        return proShowImg;
    }

    public void setProShowImg(String proShowImg) {
        this.proShowImg = proShowImg;
    }
}
