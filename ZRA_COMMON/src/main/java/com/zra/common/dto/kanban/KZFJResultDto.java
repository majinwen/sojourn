package com.zra.common.dto.kanban;

/**
 * Author: wangxm113
 * CreateDate: 2016/12/26.
 */
public class KZFJResultDto {
    // 房间id
    private String roomId;
    // 房间号
    private String roomNo;
    // 房间状态
    private String roomStatus;
    // 空置天数
    private Integer vacantDays;
    // 7天内带看天数
    private Integer seeDays;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Integer getVacantDays() {
        return vacantDays;
    }

    public void setVacantDays(Integer vacantDays) {
        this.vacantDays = vacantDays;
    }

    public Integer getSeeDays() {
        return seeDays;
    }

    public void setSeeDays(Integer seeDays) {
        this.seeDays = seeDays;
    }
}
