package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 首页搜索结果-房型信息
 * <p>
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
public class HouseTypeDto {
    @ApiModelProperty(value = "房型id")
    private String htId;
    @ApiModelProperty(value = "房型名称")
    private String htName;
    @ApiModelProperty(value = "房型图片")
    private String htImgUrl;
    @ApiModelProperty(value = "房型最低价格")
    private Double htMinPrice;
    @ApiModelProperty(value = "房型最高价格")
    private Double htMaxPrice;
    @ApiModelProperty(value = "可用房间数")
    private Integer htAvaRoomAcc;
    @ApiModelProperty(value = "面积")
    private Double htArea;
    @ApiModelProperty(value = "是否满房(0:满房; 1:未满)")
    private Integer isRoomful = 0;
    @ApiModelProperty(value = "当已满房时，展示的文本")
    private String roomFulTag;
    @ApiModelProperty(value = "户型下房间的标签")
    private List<String> roomTags;

    public String getHtId() {
        return htId;
    }

    public void setHtId(String htId) {
        this.htId = htId;
    }

    public String getHtName() {
        return htName;
    }

    public void setHtName(String htName) {
        this.htName = htName;
    }

    public String getHtImgUrl() {
        return htImgUrl;
    }

    public void setHtImgUrl(String htImgUrl) {
        this.htImgUrl = htImgUrl;
    }

    public Double getHtMinPrice() {
        return htMinPrice;
    }

    public void setHtMinPrice(Double htMinPrice) {
        this.htMinPrice = htMinPrice;
    }

    public Double getHtMaxPrice() {
        return htMaxPrice;
    }

    public void setHtMaxPrice(Double htMaxPrice) {
        this.htMaxPrice = htMaxPrice;
    }

    public Integer getHtAvaRoomAcc() {
        return htAvaRoomAcc;
    }

    public void setHtAvaRoomAcc(Integer htAvaRoomAcc) {
        this.htAvaRoomAcc = htAvaRoomAcc;
    }

    public Double getHtArea() {
        return htArea;
    }

    public void setHtArea(Double htArea) {
        this.htArea = htArea;
    }

    public Integer getIsRoomful() {
        return isRoomful;
    }

    public void setIsRoomful(Integer isRoomful) {
        this.isRoomful = isRoomful;
    }

    public String getRoomFulTag() {
        return roomFulTag;
    }

    public void setRoomFulTag(String roomFulTag) {
        this.roomFulTag = roomFulTag;
    }

    public List<String> getRoomTags() {
        return roomTags;
    }

    public void setRoomTags(List<String> roomTags) {
        this.roomTags = roomTags;
    }
}
