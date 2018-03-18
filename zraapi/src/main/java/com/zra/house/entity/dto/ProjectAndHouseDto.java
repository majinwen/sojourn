package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 首页搜索结果-项目信息+房型信息
 * <p>
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
public class ProjectAndHouseDto {
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "项目头图")
    private String projectImgUrl;
    @ApiModelProperty(value = "项目地址")
    private String projectAddr;
    @ApiModelProperty(value = "项目最低价格")
    private Double projectMinPrice;
    @ApiModelProperty(value = "房型信息")
    private List<HouseTypeDto> houseTypeList;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectImgUrl() {
        return projectImgUrl;
    }

    public void setProjectImgUrl(String projectImgUrl) {
        this.projectImgUrl = projectImgUrl;
    }

    public String getProjectAddr() {
        return projectAddr;
    }

    public void setProjectAddr(String projectAddr) {
        this.projectAddr = projectAddr;
    }

    public Double getProjectMinPrice() {
        return projectMinPrice;
    }

    public void setProjectMinPrice(Double projectMinPrice) {
        this.projectMinPrice = projectMinPrice;
    }

    public List<HouseTypeDto> getHouseTypeList() {
        return houseTypeList;
    }

    public void setHouseTypeList(List<HouseTypeDto> houseTypeList) {
        this.houseTypeList = houseTypeList;
    }
}
