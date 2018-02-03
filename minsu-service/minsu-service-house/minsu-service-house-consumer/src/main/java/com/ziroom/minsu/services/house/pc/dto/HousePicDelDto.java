/**
 * @FileName: HousePicDelDto.java
 * @Package com.ziroom.minsu.services.house.pc.dto
 * 
 * @author jixd
 * @created 2016年9月8日 下午5:29:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.pc.dto;

import java.io.Serializable;

/**
 * <p>PC图片删除参数</p>
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
public class HousePicDelDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7434418908627302916L;
	
	/**
	 * 房源fid
	 */
	private String houseBaseFid;
	/**
	 * 房间fid
	 */
	private String houseRoomFid;
	/**图片类型
	 * 
	 */
	private Integer picType;
	/**
	 * 图片fid
	 */
	private String housePicFid;
	/**
	 * 当前所在房间的fid
	 */
	private String currentRoomFid;
	
	public String getHouseBaseFid() {
		return houseBaseFid;
	}
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}
	public String getHouseRoomFid() {
		return houseRoomFid;
	}
	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}
	public Integer getPicType() {
		return picType;
	}
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	public String getHousePicFid() {
		return housePicFid;
	}
	public void setHousePicFid(String housePicFid) {
		this.housePicFid = housePicFid;
	}
	public String getCurrentRoomFid() {
		return currentRoomFid;
	}
	public void setCurrentRoomFid(String currentRoomFid) {
		this.currentRoomFid = currentRoomFid;
	}
	
	
}
