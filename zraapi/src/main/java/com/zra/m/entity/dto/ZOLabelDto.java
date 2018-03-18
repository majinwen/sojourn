package com.zra.m.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/14.
 */
public class ZOLabelDto {
    @ApiModelProperty(value = "标签id")
    private String labelId;
    @ApiModelProperty(value = "标签内容")
    private String labelName;
    @ApiModelProperty(value = "标签类型(0:正面 1:反面)")
    private String labelType;

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }
}
