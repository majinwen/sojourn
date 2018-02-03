/**
 * @FileName: HouseIssueDescDto.java
 * @Package com.ziroom.minsu.services.house.pc.dto
 * 
 * @author jixd
 * @created 2016年8月15日 下午9:11:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.pc.dto;

import java.io.Serializable;

/**
 * <p>房源发布周边情况描述</p>
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
public class HouseIssueDescDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7686613800005357275L;

	/**
	 * 房源FId
	 */
	private String houseFid;
	/**
	 * 房源名称
	 */
	private String houseName;
	/**
	 * 房源描述
	 */
	private String houseDesc;
	/**
	 * 房源周末情况
	 */
	private String houseAround;
	
	/**
	 * 分租fid
	 */
	private String roomFid;
	
	public String getHouseFid() {
		return houseFid;
	}
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getHouseDesc() {
		return houseDesc;
	}
	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}
	public String getHouseAround() {
		return houseAround;
	}
	public void setHouseAround(String houseAround) {
		this.houseAround = houseAround;
	}
	public String getRoomFid() {
		return roomFid;
	}
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}
	
}
