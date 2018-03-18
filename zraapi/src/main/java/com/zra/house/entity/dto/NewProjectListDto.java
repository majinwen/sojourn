package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: wangxm113
 * CreateDate: 2017/2/16.
 */
public class NewProjectListDto {
    @ApiModelProperty(value = "项目fid")
    private String proFid;
    @ApiModelProperty(value = "项目编号")
    private String proCode;
    @ApiModelProperty(value = "项目名称")
    private String proName;
    @ApiModelProperty(value = "项目地址")
    private String proAddr;
    @ApiModelProperty(value = "项目最小价格")
    private String minPrice;
    @ApiModelProperty(value = "项目最高价格")
    private String maxPrice;
    @ApiModelProperty(value = "满房标签0:未满；1:已满房")
    private String tagFlag = "0";
    @ApiModelProperty(value = "项目标签列表")
    private Set<String> tagList;
    @ApiModelProperty(value = "项目经度")
    private Double lng;
    @ApiModelProperty(value = "项目纬度")
    private Double lat;
    @ApiModelProperty(value = "项目头图")
    private String headPic;
    @ApiModelProperty(value = "项目风采展示图")
    private String showPic;
    @ApiModelProperty(value = "币种(RMB:￥)")
    private String priceTag = "￥";

    public String getTagFlag() {
        return tagFlag;
    }

    public void setTagFlag(String tagFlag) {
        this.tagFlag = tagFlag;
    }

    public String getProFid() {
        return proFid;
    }

    public void setProFid(String proFid) {
        this.proFid = proFid;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProAddr() {
        return proAddr;
    }

    public void setProAddr(String proAddr) {
        this.proAddr = proAddr;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Set<String> getTagList() {
        return tagList;
    }

    public void setTagList(Set<String> tagList) {
        this.tagList = tagList;
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

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getShowPic() {
        return showPic;
    }

    public void setShowPic(String showPic) {
        this.showPic = showPic;
    }

    public String getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(String priceTag) {
        this.priceTag = priceTag;
    }
}
