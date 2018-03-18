package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: wangxm113
 * CreateDate: 2017/2/16.
 */
public class ZSpacePicDto {
    @ApiModelProperty(value = "图片地址")
    private String picUrl;
    @ApiModelProperty(value = "图片描述")
    private String picDes;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicDes() {
        return picDes;
    }

    public void setPicDes(String picDes) {
        this.picDes = picDes;
    }
}
