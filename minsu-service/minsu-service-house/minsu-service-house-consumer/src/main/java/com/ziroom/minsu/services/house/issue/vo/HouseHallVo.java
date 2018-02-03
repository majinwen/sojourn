/**
 * @FileName: HouseRoomVo.java
 * @Package com.ziroom.minsu.services.house.issue.vo
 * 
 * @author bushujie
 * @created 2017年6月15日 下午7:32:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.vo;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yanb
 * @since 1.0
 * @version 1.0
 */
public class HouseHallVo {
	
	/**
	 * 房间roomFid
	 */
	private String roomFid;
	
	/**
	 * 房间类型 0：房间   1：客厅
	 */
	private Integer roomType;

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	@Override
	public String toString() {
		return "HouseHallVo{" +
				"roomFid='" + roomFid + '\'' +
				", roomType=" + roomType +
				'}';
	}
}
