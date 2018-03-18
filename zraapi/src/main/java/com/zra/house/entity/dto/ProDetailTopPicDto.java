package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/2/16.
 */
public class ProDetailTopPicDto {
    @ApiModelProperty(value = "图片标签")
    private String label;
    @ApiModelProperty(value = "图片URL")
    private List<String> picURL;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getPicURL() {
        return picURL;
    }

    public void setPicURL(List<String> picURL) {
        this.picURL = picURL;
    }
}
