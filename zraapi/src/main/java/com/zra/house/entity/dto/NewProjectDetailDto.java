package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/2/16.
 */
public class NewProjectDetailDto {
    @ApiModelProperty(value = "项目id")
    private String proId;

    @ApiModelProperty(value = "轮播图")
    private List<ProDetailTopPicDto> topPicList = new ArrayList<>();
    //-----------------------------------------
    @ApiModelProperty(value = "项目名称")
    private String proName;
    @ApiModelProperty(value = "项目slogan")
    private String slogan;
    @ApiModelProperty(value = "项目联系电话")
    private String telePhone;
    //-----------------------------------------
    @ApiModelProperty(value = "全景看房URL")
    private String panoramicUrl;
    //-----------------------------------------
    @ApiModelProperty(value = "项目简介")
    private String briefInfo;
    @ApiModelProperty(value = "项目经度")
    private Double lng;
    @ApiModelProperty(value = "项目纬度")
    private Double lat;
    @ApiModelProperty(value = "项目地址")
    private String proAddr;
    @ApiModelProperty(value = "周边链接")
    private String peripheralUrl;
    //-----------------------------------------
    @ApiModelProperty(value = "Z-Space区域")
    private ProDetailZSpaceDto ZSpaceDto;
    //-----------------------------------------
    @ApiModelProperty(value = "户型简介区域")
    private List<HouseTypeDto> houseTypeList = new ArrayList<>();
    //-----------------------------------------
    @ApiModelProperty(value = "专属ZO介绍")
    private String ZODesc;
    @ApiModelProperty(value = "专属ZO图片URL")
    private String ZOUrl;
    //----------------------------------------
    @ApiModelProperty(value = "交通")
    private List<String> trafficList = new ArrayList<>();
    @ApiModelProperty(value = "专属服务")
    private String zoServiceDesc;
    @ApiModelProperty(value = "分享链接")
    private String shareUrl;

    //--------------------预约看房的额外信息---------------------
    @ApiModelProperty(value = "项目头图-for预约看房")
    private String proHeadPic;
    @ApiModelProperty(value = "项目最低价格-for预约看房")
    private String minPrice = "0.0";
    @ApiModelProperty(value = "项目最高价格-for预约看房")
    private String maxPrice = "0.0";
    @ApiModelProperty(value = "币种标签")
    private String priceTag = "￥";

    public String getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(String priceTag) {
        this.priceTag = priceTag;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getZoServiceDesc() {
        return zoServiceDesc;
    }

    public void setZoServiceDesc(String zoServiceDesc) {
        this.zoServiceDesc = zoServiceDesc;
    }

    public List<String> getTrafficList() {
        return trafficList;
    }

    public void setTrafficList(List<String> trafficList) {
        this.trafficList = trafficList;
    }

    public List<ProDetailTopPicDto> getTopPicList() {
        return topPicList;
    }

    public void setTopPicList(List<ProDetailTopPicDto> topPicList) {
        this.topPicList = topPicList;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getPanoramicUrl() {
        return panoramicUrl;
    }

    public void setPanoramicUrl(String panoramicUrl) {
        this.panoramicUrl = panoramicUrl;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getProAddr() {
        return proAddr;
    }

    public void setProAddr(String proAddr) {
        this.proAddr = proAddr;
    }

    public String getPeripheralUrl() {
        return peripheralUrl;
    }

    public void setPeripheralUrl(String peripheralUrl) {
        this.peripheralUrl = peripheralUrl;
    }

    public ProDetailZSpaceDto getZSpaceDto() {
        return ZSpaceDto;
    }

    public void setZSpaceDto(ProDetailZSpaceDto ZSpaceDto) {
        this.ZSpaceDto = ZSpaceDto;
    }

    public List<HouseTypeDto> getHouseTypeList() {
        return houseTypeList;
    }

    public void setHouseTypeList(List<HouseTypeDto> houseTypeList) {
        this.houseTypeList = houseTypeList;
    }

    public String getZODesc() {
        return ZODesc;
    }

    public void setZODesc(String ZODesc) {
        this.ZODesc = ZODesc;
    }

    public String getZOUrl() {
        return ZOUrl;
    }

    public void setZOUrl(String ZOUrl) {
        this.ZOUrl = ZOUrl;
    }

    public String getProHeadPic() {
        return proHeadPic;
    }

    public void setProHeadPic(String proHeadPic) {
        this.proHeadPic = proHeadPic;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
}
