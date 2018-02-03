package com.ziroom.minsu.services.house.issue.dto;

import javax.validation.constraints.NotNull;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年06月27日 20:34
 * @since 1.0
 */
public class HouseCheckInMsgDto {

    /**
     * 房源fid
     */
    @NotNull(message="{house.base.fid.null}")
    private String houseBaseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 0整租 1合租
     */
    @NotNull(message="{house.rentway.null}")
    private Integer rentWay;

    /**
     * 最小入住天数
     */
    @NotNull(message="{house.minday.null}")
    private Integer minDay;
    /**
     * 入住时间
     */
    @NotNull(message="{house.checkInTime.null}")
    private String checkInTime;
    /**
     * 离开时间
     */
    @NotNull(message="{house.checkOutTime.null}")
    private String checkOutTime;
    
    /**
     * 步骤
     */
    private Integer step;

    
    public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getHouseBaseFid() {
        return houseBaseFid;
    }

    public void setHouseBaseFid(String houseBaseFid) {
        this.houseBaseFid = houseBaseFid;
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Integer getMinDay() {
        return minDay;
    }

    public void setMinDay(Integer minDay) {
        this.minDay = minDay;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
