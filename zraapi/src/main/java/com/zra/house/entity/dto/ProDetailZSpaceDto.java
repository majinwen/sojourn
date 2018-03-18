package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/2/16.
 */
public class ProDetailZSpaceDto {
    @ApiModelProperty(value = "ZSpace介绍")
    private String description;
    @ApiModelProperty(value = "ZSpace图片")
    private List<ZSpacePicDto> picList = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ZSpacePicDto> getPicList() {
        return picList;
    }

    public void setPicList(List<ZSpacePicDto> picList) {
        this.picList = picList;
    }
}
