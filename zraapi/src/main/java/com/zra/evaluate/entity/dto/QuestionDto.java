package com.zra.evaluate.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class QuestionDto {
    @ApiModelProperty(value = "问题编码")
    private String code;
    @ApiModelProperty(value = "问题选择项")
    private String optionCode;
    @ApiModelProperty(value = "评价内容")
    private String content;
    @ApiModelProperty(value = "图片的路径")
    private String picUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOptionCode() {
        return optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "code:" + code + "; optionCode:" + "; content:" + content + "; picUrl:" + picUrl;
    }
}
