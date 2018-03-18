package com.zra.m.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/13.
 */
public class MProjectInfoDto {
    @ApiModelProperty(value = "项目id")
    private String proId;
    @ApiModelProperty(value = "项目名称")
    private String proName;
    @ApiModelProperty(value = "户型列表")
    private List<MHouseTypeDto> houseTypeList;

    public List<MHouseTypeDto> getHouseTypeList() {
        return houseTypeList;
    }

    public void setHouseTypeList(List<MHouseTypeDto> houseTypeList) {
        this.houseTypeList = houseTypeList;
    }

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
}
