package com.zra.business.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/24.
 */
public class BusinessListDto {
    @ApiModelProperty(value = "约看的商机id")
    private Long orderId;
    @ApiModelProperty(value = "管家姓名")
    private String keeperName;
    @ApiModelProperty(value = "系统号")
    private String keeperId;
    @ApiModelProperty(value = "管家400电话")
    private String keeperPhone;
    @ApiModelProperty(value = "管家头像")
    private String keeperHeadCorn;
    @ApiModelProperty(value = "自如寓项目bid")
    private String houseId;
    @ApiModelProperty(value = "房间编号")
    private String houseCode;
    @ApiModelProperty(value = "房源类型")
    private String houseType;
    @ApiModelProperty(value = "约看时间")
    private String appointTime;
    @ApiModelProperty(value = "约看地址(自如寓项目地址)")
    private String appointAddr;
    @ApiModelProperty(value = "约看订单状态")
    private String appointStatus;
    @ApiModelProperty(value = "自如寓项目头图")
    private String housePhoto;
    @ApiModelProperty(value = "商圈名")
    private String bizCircleName;
    @ApiModelProperty(value = "小区名字")
    private String villageName;
    @ApiModelProperty(value = "第几卧或者几室")
    private String number;
    @ApiModelProperty(value = "卧室数量")
    private String bedroom;
    @ApiModelProperty(value = "楼层")
    private String floor;
    @ApiModelProperty(value = "面积")
    private String area;
    @ApiModelProperty(value = "租金")
    private String rent;
    @ApiModelProperty(value = "租金单位")
    private String rentUnit;
    @ApiModelProperty(value = "房源状态")
    private String houseStatus;
    @ApiModelProperty(value = "是否可评价0:可评价；1:不可评价")
    private String isEvaluate;
    @ApiModelProperty(value = "约看商机创建时间")
    private String createTime;
    @ApiModelProperty(value = "商机阶段")
    private String appointStatusZra;
    @ApiModelProperty(value = "来源-zra代表自如寓")
    private String source;
    @ApiModelProperty(value = "tokenId-查看评价需要")
    private String tokenId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAppointStatusZra() {
        return appointStatusZra;
    }

    public void setAppointStatusZra(String appointStatusZra) {
        this.appointStatusZra = appointStatusZra;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getKeeperName() {
        return keeperName;
    }

    public void setKeeperName(String keeperName) {
        this.keeperName = keeperName;
    }

    public String getKeeperId() {
        return keeperId;
    }

    public void setKeeperId(String keeperId) {
        this.keeperId = keeperId;
    }

    public String getKeeperPhone() {
        return keeperPhone;
    }

    public void setKeeperPhone(String keeperPhone) {
        this.keeperPhone = keeperPhone;
    }

    public String getKeeperHeadCorn() {
        return keeperHeadCorn;
    }

    public void setKeeperHeadCorn(String keeperHeadCorn) {
        this.keeperHeadCorn = keeperHeadCorn;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getAppointAddr() {
        return appointAddr;
    }

    public void setAppointAddr(String appointAddr) {
        this.appointAddr = appointAddr;
    }

    public String getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(String appointStatus) {
        this.appointStatus = appointStatus;
    }

    public String getHousePhoto() {
        return housePhoto;
    }

    public void setHousePhoto(String housePhoto) {
        this.housePhoto = housePhoto;
    }

    public String getBizCircleName() {
        return bizCircleName;
    }

    public void setBizCircleName(String bizCircleName) {
        this.bizCircleName = bizCircleName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getRentUnit() {
        return rentUnit;
    }

    public void setRentUnit(String rentUnit) {
        this.rentUnit = rentUnit;
    }

    public String getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(String houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(String isEvaluate) {
        this.isEvaluate = isEvaluate;
    }
}
