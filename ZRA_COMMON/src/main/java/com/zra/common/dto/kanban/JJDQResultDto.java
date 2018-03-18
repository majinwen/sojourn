package com.zra.common.dto.kanban;

/**
 * Author: wangxm113
 * CreateDate: 2016/12/26.
 */
public class JJDQResultDto {
    private String roomId;//房间id
    private String roomNumber;//房间号
    private String roomStatus;//房间状态
    private String conRentCode;//承租合同号
    private String conType;//租赁类型
    private String isRenew;//续约意向
    private String difDate;//剩余天数

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getConRentCode() {
        return conRentCode;
    }

    public void setConRentCode(String conRentCode) {
        this.conRentCode = conRentCode;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getIsRenew() {
        return isRenew;
    }

    public void setIsRenew(String isRenew) {
        this.isRenew = isRenew;
    }

    public String getDifDate() {
        return difDate;
    }

    public void setDifDate(String difDate) {
        this.difDate = difDate;
    }
}
