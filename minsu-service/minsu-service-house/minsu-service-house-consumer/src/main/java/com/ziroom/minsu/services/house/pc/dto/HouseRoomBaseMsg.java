/**
 * @FileName: HouseRoomSuccessMsg.java
 * @Package com.ziroom.minsu.services.house.pc.dto
 * 
 * @author jixd
 * @created 2016年8月18日 下午9:51:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.pc.dto;

import java.io.Serializable;

import com.ziroom.minsu.entity.house.HousePicMsgEntity;

/**
 * <p>房源房间基本信息</p>
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
public class HouseRoomBaseMsg implements Serializable{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 3159631957015697470L;
	
	/**
	 * 房源FID
	 */
	private String houseFid;
	/**
	 * 房间FID
	 */
	private String roomFid;
	/**
	 * 房源或这房间名称
	 */
	private String name;
	/**
	 * 房源图片
	 */
	private String url;
	/**
	 * 默认图片
	 */
	private HousePicMsgEntity picMsgEntity;
	/**
	 * 出租方式
	 */
	private Integer rentWay;
	/**
	 * 显示名称
	 */
	private String rentWayName;
	/**
	 * 可住人数
	 */
	private Integer checkInLimit;
	/**
	 * 房间或者房源状态
	 */
	private Integer status;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HousePicMsgEntity getPicMsgEntity() {
		return picMsgEntity;
	}
	public void setPicMsgEntity(HousePicMsgEntity picMsgEntity) {
		this.picMsgEntity = picMsgEntity;
	}
	public Integer getRentWay() {
		return rentWay;
	}
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	public Integer getCheckInLimit() {
		return checkInLimit;
	}
	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRentWayName() {
		return rentWayName;
	}
	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
