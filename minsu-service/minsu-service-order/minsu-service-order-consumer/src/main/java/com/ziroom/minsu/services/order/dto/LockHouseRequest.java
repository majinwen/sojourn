package com.ziroom.minsu.services.order.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 锁定房源参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class LockHouseRequest extends BaseEntity{


    /** 序列化id */
    private static final long serialVersionUID = -214532456472154563L;

    /** houseFid */
    private String houseFid;

    /**  roomFid */
    private String roomFid;

    /**  bedFid */
    private String bedFid;

    /**
     * 房源类型
     * @see RentWayEnum
     */
    private Integer rentWay;

    /** 锁定类型 1：订单锁定 2：房东 */
    private Integer lockType;


    private List<Date> lockDayList;
    
    /**
     * 当前待处理日历
     */
    private  List<CalendarDataVo> calendarDataVos;
    


    /**
	 * @return the calendarDataVos
	 */
	public List<CalendarDataVo> getCalendarDataVos() {
		return calendarDataVos;
	}

	/**
	 * @param calendarDataVos the calendarDataVos to set
	 */
	public void setCalendarDataVos(List<CalendarDataVo> calendarDataVos) {
		this.calendarDataVos = calendarDataVos;
	}

	public Integer getLockType() {
        return lockType;
    }

    public void setLockType(Integer lockType) {
        this.lockType = lockType;
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


    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public String getBedFid() {
        return bedFid;
    }

    public void setBedFid(String bedFid) {
        this.bedFid = bedFid;
    }

    public List<Date> getLockDayList() {
        return lockDayList;
    }

    public void setLockDayList(List<Date> lockDayList) {
        this.lockDayList = lockDayList;
    }
}
