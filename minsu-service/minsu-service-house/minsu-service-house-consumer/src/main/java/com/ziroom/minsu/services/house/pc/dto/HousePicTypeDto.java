/**
 * @FileName: HousePicTypeDto.java
 * @Package com.ziroom.minsu.services.house.pc.dto
 * 
 * @author jixd
 * @created 2016年8月17日 上午11:58:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.pc.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <p>查询房源图片</p>
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
public class HousePicTypeDto implements Serializable{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -3835690021109814334L;
	/**
	 * 房源Fid
	 */
	private String houseFid;
	/**
	 * 房间fid
	 */
	private String roomFid;
	/**
	 * 图片类型集合
	 */
	private List<Integer> picTypes;

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

	public List<Integer> getPicTypes() {
		return picTypes;
	}

	public void setPicTypes(List<Integer> picTypes) {
		this.picTypes = picTypes;
	}

}
