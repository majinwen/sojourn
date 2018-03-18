package com.zra.m.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/15.
 */
public class MBusinessEntryDto {
    @ApiModelProperty(value = "项目信息list")
    private List<MProjectInfoDto> projectInfoList;
    @ApiModelProperty(value = "国家信息list")
    private List<String> countryList;
    @ApiModelProperty(value = "来源")
    private String comeSource;

    public String getComeSource() {
        return comeSource;
    }

    public void setComeSource(String comeSource) {
        this.comeSource = comeSource;
    }

    public List<MProjectInfoDto> getProjectInfoList() {
        return projectInfoList;
    }

    public void setProjectInfoList(List<MProjectInfoDto> projectInfoList) {
        this.projectInfoList = projectInfoList;
    }

    public List<String> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<String> countryList) {
        this.countryList = countryList;
    }
}
