package com.zra.m.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/14.
 */
public class MZOListDto {
    @ApiModelProperty(value = "项目id")
    private String proId;
    @ApiModelProperty(value = "项目名称")
    private String proName;
    @ApiModelProperty(value = "ZOid")
    private String zoId;
    @ApiModelProperty(value = "ZO名字")
    private String zoName;
    @ApiModelProperty(value = "ZO大图")
    private String zoBigImg;
    @ApiModelProperty(value = "ZO小图")
    private String zoSmallImg;
    @ApiModelProperty(value = "ZO描述")
    private String zoDesc;
    @ApiModelProperty(value = "ZO正面标签")
    private List<ZOLabelDto> positiveLabelList;
    @ApiModelProperty(value = "ZO负面标签")
    private List<ZOLabelDto> negativeLabelList;
    private String employeeId;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public List<ZOLabelDto> getNegativeLabelList() {
        return negativeLabelList;
    }

    public void setNegativeLabelList(List<ZOLabelDto> negativeLabelList) {
        this.negativeLabelList = negativeLabelList;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getZoSmallImg() {
        return zoSmallImg;
    }

    public void setZoSmallImg(String zoSmallImg) {
        this.zoSmallImg = zoSmallImg;
    }

    public String getZoBigImg() {
        return zoBigImg;
    }

    public void setZoBigImg(String zoBigImg) {
        this.zoBigImg = zoBigImg;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getZoId() {
        return zoId;
    }

    public void setZoId(String zoId) {
        this.zoId = zoId;
    }

    public String getZoName() {
        return zoName;
    }

    public void setZoName(String zoName) {
        this.zoName = zoName;
    }

    public String getZoDesc() {
        return zoDesc;
    }

    public void setZoDesc(String zoDesc) {
        this.zoDesc = zoDesc;
    }

    public List<ZOLabelDto> getPositiveLabelList() {
        return positiveLabelList;
    }

    public void setPositiveLabelList(List<ZOLabelDto> positiveLabelList) {
        this.positiveLabelList = positiveLabelList;
    }
}
