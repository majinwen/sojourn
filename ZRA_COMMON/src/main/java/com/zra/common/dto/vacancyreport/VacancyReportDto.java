package com.zra.common.dto.vacancyreport;

/**
 * 空置报表对象DTO
 *
 * @author dongl50@ziroom.com
 * @date 2016/11/30 14:49
 */
public class VacancyReportDto implements Comparable<VacancyReportDto> {

    // 项目ID
    private String projectId;
    // 项目名称
    private String projectName;
    // 房间号
    private String roomNo;
    // 房型
    private String houseType;
    // 租赁类型
    private Integer rentType;
    // 租赁类型字符串
    private String rentTypeString;
    // 房间状态
    private String roomStatus;
    //房间状态字符串
    private String roomStatusString;
    // 空置天数
    private Integer vacantDays;
    // 7天内带看天数
    private Integer seeDays;

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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
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

    public String getRentTypeString() {
        return rentTypeString;
    }

    public void setRentTypeString(String rentTypeString) {
        this.rentTypeString = rentTypeString;
    }

    public String getRoomStatusString() {
        return roomStatusString;
    }

    public void setRoomStatusString(String roomStatusString) {
        this.roomStatusString = roomStatusString;
    }

    public int compareTo(VacancyReportDto o) {
        if (Integer.valueOf(this.vacantDays) < Integer.valueOf(o.getVacantDays())) {
            return 1;
        } else if (Integer.valueOf(this.vacantDays) > Integer.valueOf(o.getVacantDays())){
            return -1;
        } else {
            return 0;
        }
    }
}
