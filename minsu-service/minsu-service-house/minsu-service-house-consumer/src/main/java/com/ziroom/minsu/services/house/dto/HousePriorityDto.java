/**
 * @FileName: HousePriorityDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2016年12月6日 下午5:51:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>房源  夹心dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HousePriorityDto extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -591857820324040494L;

	/**
	 * 房源fid
	 */
	private String houseBaseFid;
	
	/**
	 * 房间fid
	 */
	private String houseRoomFid;
	
	/**
	 * 床位fid
	 */
	private String bedFid;
	
	/**
	 * 出租方式
	 */
	private Integer rentWay;
	
	/**
	 * 日历开始时间
	 */
	private Date startDate;
	
	/**
	 * 日历结束时间
	 */
	private Date endDate;
	
	/**
	 * 当天时间
	 */
	private Date nowDate;
	
	/**
	 * 开始日期字符串
	 */
	private String startDateStr;
	
	/**
	 * 结束日期字符串
	 */
	private String endDateStr;

	/**
	 * 夹心日期界限（默认是2天）
	 */
	private Integer priorityDateLimit=2;
	
	/**
	 * 房源 出租结束日期
	 */
	private Date tillDate;

	/**
	 * @return the tillDate
	 */
	public Date getTillDate() {
		return tillDate;
	}

	/**
	 * @param tillDate the tillDate to set
	 */
	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	/**
	 * @return the houseBaseFid
	 */
	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	/**
	 * @param houseBaseFid the houseBaseFid to set
	 */
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	/**
	 * @return the houseRoomFid
	 */
	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	/**
	 * @param houseRoomFid the houseRoomFid to set
	 */
	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	/**
	 * @return the bedFid
	 */
	public String getBedFid() {
		return bedFid;
	}

	/**
	 * @param bedFid the bedFid to set
	 */
	public void setBedFid(String bedFid) {
		this.bedFid = bedFid;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the nowDate
	 */
	public Date getNowDate() {
		return nowDate;
	}

	/**
	 * @param nowDate the nowDate to set
	 */
	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

	/**
	 * @return the startDateStr
	 */
	public String getStartDateStr() {
		return startDateStr;
	}

	/**
	 * @param startDateStr the startDateStr to set
	 */
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	/**
	 * @return the endDateStr
	 */
	public String getEndDateStr() {
		return endDateStr;
	}

	/**
	 * @param endDateStr the endDateStr to set
	 */
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	/**
	 * @return the priorityDateLimit
	 */
	public Integer getPriorityDateLimit() {
		return priorityDateLimit;
	}

	/**
	 * @param priorityDateLimit the priorityDateLimit to set
	 */
	public void setPriorityDateLimit(Integer priorityDateLimit) {
		this.priorityDateLimit = priorityDateLimit;
	}
	
	
}
