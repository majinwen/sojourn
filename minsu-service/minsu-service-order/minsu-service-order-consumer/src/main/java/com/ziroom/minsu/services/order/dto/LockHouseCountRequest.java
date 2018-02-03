package com.ziroom.minsu.services.order.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 查询指定时间段的订单数量
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class LockHouseCountRequest extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5559721331280546805L;

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

    /**
     * 日期开始时间
     */
    private Date startTime;
    /**
     * 日期结束时间
     */
    private Date endTime;


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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
