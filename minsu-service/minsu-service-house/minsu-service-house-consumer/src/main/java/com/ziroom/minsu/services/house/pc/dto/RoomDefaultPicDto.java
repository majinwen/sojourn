/**
 * @FileName: RoomDefaultPicDto.java
 * @Package com.ziroom.minsu.services.house.pc.dto
 * 
 * @author jixd
 * @created 2016年8月24日 下午8:06:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.pc.dto;

import java.io.Serializable;

/**
 * <p>房间默认图片设置</p>
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
public class RoomDefaultPicDto implements Serializable{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -5234596770054058312L;
	
	/**
	 * 房源FID
	 */
	private String houseFid;
	/**
	 * 房间FID
	 */
	private String roomFid;
	/**
	 * 图片FID
	 */
	private String picFid;
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
	public String getPicFid() {
		return picFid;
	}
	public void setPicFid(String picFid) {
		this.picFid = picFid;
	}
	
	
}
