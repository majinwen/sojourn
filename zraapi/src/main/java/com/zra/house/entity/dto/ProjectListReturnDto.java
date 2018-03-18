package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/8.
 */
public class ProjectListReturnDto {
    @ApiModelProperty(value = "项目id")
    private String projId;
    @ApiModelProperty(value = "项目名称")
    private String projName;
    @ApiModelProperty(value = "项目头图")
    private String projImgUrl;
    @ApiModelProperty(value = "项目地址")
    private String projAddr;
    @ApiModelProperty(value = "项目最低价格")
    private Double projMinPrice;

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjImgUrl() {
        return projImgUrl;
    }

    public void setProjImgUrl(String projImgUrl) {
        this.projImgUrl = projImgUrl;
    }

    public String getProjAddr() {
        return projAddr;
    }

    public void setProjAddr(String projAddr) {
        this.projAddr = projAddr;
    }

    public Double getProjMinPrice() {
        return projMinPrice;
    }

    public void setProjMinPrice(Double projMinPrice) {
        this.projMinPrice = projMinPrice;
    }
}
