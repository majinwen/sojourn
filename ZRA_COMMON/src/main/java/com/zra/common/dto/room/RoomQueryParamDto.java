package com.zra.common.dto.room;

import com.zra.common.dto.base.BasePageParamDto;

/**
 * @author wangws21 2016年9月9日
 *	房间信息管理v1  查询dto
 */
public class RoomQueryParamDto extends BasePageParamDto{
	private String projectId; // 项目Id
	private String houseTypeId; // 户型Id
	private String roomNumber; // 房间号
	private String direction; // 朝向
	private Double roomAreaMin; // 房间面积xiao
	private Double roomAreaMax; // 房间面积da 
	private String bildingId; // 楼栋
	private String floorNumber; // 房间所在层数
	private String decorationStyle; // 装修风格
	private Integer isUsed;
	private String currentState; // 当前状态
	private String rentType;  //默认出租方式为按房间
	private String bedStandardBid; // 床位配置方案
    /**
     * 城市Id 
     */
    private String cityId;
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getHouseTypeId() {
        return houseTypeId;
    }
    public void setHouseTypeId(String houseTypeId) {
        this.houseTypeId = houseTypeId;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public Double getRoomAreaMin() {
        return roomAreaMin;
    }
    public void setRoomAreaMin(Double roomAreaMin) {
        this.roomAreaMin = roomAreaMin;
    }
    public Double getRoomAreaMax() {
        return roomAreaMax;
    }
    public void setRoomAreaMax(Double roomAreaMax) {
        this.roomAreaMax = roomAreaMax;
    }
    public String getBildingId() {
        return bildingId;
    }
    public void setBildingId(String bildingId) {
        this.bildingId = bildingId;
    }
    public String getFloorNumber() {
        return floorNumber;
    }
    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }
    public String getDecorationStyle() {
        return decorationStyle;
    }
    public void setDecorationStyle(String decorationStyle) {
        this.decorationStyle = decorationStyle;
    }
    public Integer getIsUsed() {
        return isUsed;
    }
    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
    public String getCurrentState() {
        return currentState;
    }
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
    public String getRentType() {
        return rentType;
    }
    public void setRentType(String rentType) {
        this.rentType = rentType;
    }
    public String getBedStandardBid() {
        return bedStandardBid;
    }
    public void setBedStandardBid(String bedStandardBid) {
        this.bedStandardBid = bedStandardBid;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
	
}
