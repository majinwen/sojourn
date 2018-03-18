package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>房间详情</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/2 17:27
 * @since 1.0
 */
public class RoomDetailDto {

    /**
     * 房间id
     */
    @ApiModelProperty(value = "房间id")
    private String id;
    /**
     * 层数
     */
    @ApiModelProperty(value = "层数")
    private Integer floorNumber;
    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号")
    private String roomNumber;
    /**
     * 房间面积
     */
    @ApiModelProperty(value = "房间面积")
    private Double roomArea;
    /**
     * 当前长租房价
     */
    @ApiModelProperty(value = "房价")
    private Double longPrice;
    /**
     *   朝向
     */
    @ApiModelProperty(value = "朝向")
    private String direction;
    /**
     * 当前状态
     */
    @ApiModelProperty(value = "当前状态")
    private String currentState;
    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称")
    private String stateName;
    /**
     * 可签约时间
     */
    @ApiModelProperty(value = "可签约时间")
    private String avaSignDate;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Double roomArea) {
        this.roomArea = roomArea;
    }

    public Double getLongPrice() {
        return longPrice;
    }

    public void setLongPrice(Double longPrice) {
        this.longPrice = longPrice;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getAvaSignDate() {
        return avaSignDate;
    }

    public void setAvaSignDate(String avaSignDate) {
        this.avaSignDate = avaSignDate;
    }
}
