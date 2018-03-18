package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/1.
 */
public class ProjectDetailDto {
    @ApiModelProperty(value = "是否有搜索结果,0-没有;1-有")
    private String ifSearchSuc;
    @ApiModelProperty(value = "项目筛选和房型筛选，根据筛选条件没有查到内容时显示的文案")
    private String searchFailMsg;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "项目轮播图")
    private List<String> projectImgUrl;
    @ApiModelProperty(value = "项目地址")
    private String projectAddr;
    @ApiModelProperty(value = "项目最低价格")
    private Double projectMinPrice;
    @ApiModelProperty(value = "项目的联系电话")
    private String projectPhone;
    @ApiModelProperty(value = "项目的经度")
    private Double projectLong;
    @ApiModelProperty(value = "项目的纬度")
    private Double projectLat;
    @ApiModelProperty(value = "全景看房链接")
    private String fPanoramicUrl;
    @ApiModelProperty(value = "项目分享链接")
    private String projectShareUrl;
    @ApiModelProperty(value = "项目周边链接")
    private String projectAroundUrl;
    @ApiModelProperty(value = "房型信息")
    private List<HouseTypeDto> houseTypeList;
    @ApiModelProperty(value = "项目周边模块名称")
    private String projectAroundName;

    public String getProjectAroundName() {
        return projectAroundName;
    }

    public void setProjectAroundName(String projectAroundName) {
        this.projectAroundName = projectAroundName;
    }

    public String getSearchFailMsg() {
        return searchFailMsg;
    }

    public void setSearchFailMsg(String searchFailMsg) {
        this.searchFailMsg = searchFailMsg;
    }

    public String getIfSearchSuc() {
        return ifSearchSuc;
    }

    public void setIfSearchSuc(String ifSearchSuc) {
        this.ifSearchSuc = ifSearchSuc;
    }

    public String getfPanoramicUrl() {
        return fPanoramicUrl;
    }

    public void setfPanoramicUrl(String fPanoramicUrl) {
        this.fPanoramicUrl = fPanoramicUrl;
    }

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

    public List<String> getProjectImgUrl() {
        return projectImgUrl;
    }

    public void setProjectImgUrl(List<String> projectImgUrl) {
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

    public String getProjectPhone() {
        return projectPhone;
    }

    public void setProjectPhone(String projectPhone) {
        this.projectPhone = projectPhone;
    }

    public Double getProjectLong() {
        return projectLong;
    }

    public void setProjectLong(Double projectLong) {
        this.projectLong = projectLong;
    }

    public Double getProjectLat() {
        return projectLat;
    }

    public void setProjectLat(Double projectLat) {
        this.projectLat = projectLat;
    }

    public String getProjectShareUrl() {
        return projectShareUrl;
    }

    public void setProjectShareUrl(String projectShareUrl) {
        this.projectShareUrl = projectShareUrl;
    }

    public String getProjectAroundUrl() {
        return projectAroundUrl;
    }

    public void setProjectAroundUrl(String projectAroundUrl) {
        this.projectAroundUrl = projectAroundUrl;
    }

    public List<HouseTypeDto> getHouseTypeList() {
        return houseTypeList;
    }

    public void setHouseTypeList(List<HouseTypeDto> houseTypeList) {
        this.houseTypeList = houseTypeList;
    }
}
