package com.zra.common.dto.room;

/**
 * @author wangws21 2016年9月14日
 *  房间信息管理v1  房间复制dto
 */
public class RoomCopyParamDto {
    
    /**
     * 楼栋ID
     */
    private String buildingId;
    
    /**
     * 层数
     */
    private Integer floorNumber;

    /**
     * 房间号
     */
    private String roomNumber;

    /**
     * 用户id.
     */
    private String userId;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
