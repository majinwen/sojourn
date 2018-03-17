/**
 * @FileName: CalendarHouseDto.java
 * @Package com.ziroom.minsu.portal.fd.center.house.dto
 * 
 * @author jixd
 * @created 2016年8月3日 下午7:27:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.house.dto;

/**
 * <p>出租日历列表返回</p>
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
public class CalendarHouseDto {

	/**
	 * 房源
	 */
	private String houseFid;
	
	private String roomFid;
	
	private String name;
	
	private Integer rentWay;
	
	private Integer isSelect;
	
	
	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
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

	public Integer getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Integer isSelect) {
		this.isSelect = isSelect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
