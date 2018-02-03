/**
 * @FileName: HouseDefaultPicInfoVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author lusp
 * @created 2017年6月22日 下午22:23:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>房源默认图片相关信息Vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */

public class HouseDefaultPicInfoVo extends BaseEntity {


	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6373814294902973389L;
	
	/**
	 * 房源fid
	 */
	private String houseFid;

	/**
	 * 房间fid
	 */
	private String roomFid;

	/**
	 * 房源或者房间名字
	 */
	private String houseOrRoomName;

	/**
	 * 默认图片fid
	 */
	private String defaultPicFid;

	/**
	 * 默认图片baseUrl
	 */
	private String picBaseUrl;

	/**
	 * 默认图片picSuffix
	 */
	private String picSuffix;

	//大图url
	private String maxPicUrl;

	//缩略图url
	private String minPicUrl;

	private Integer picType;

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

	public String getHouseOrRoomName() {
		return houseOrRoomName;
	}

	public void setHouseOrRoomName(String houseOrRoomName) {
		this.houseOrRoomName = houseOrRoomName;
	}

	public String getDefaultPicFid() {
		return defaultPicFid;
	}

	public void setDefaultPicFid(String defaultPicFid) {
		this.defaultPicFid = defaultPicFid;
	}

	public String getPicBaseUrl() {
		return picBaseUrl;
	}

	public void setPicBaseUrl(String picBaseUrl) {
		this.picBaseUrl = picBaseUrl;
	}

	public String getPicSuffix() {
		return picSuffix;
	}

	public void setPicSuffix(String picSuffix) {
		this.picSuffix = picSuffix;
	}

	public String getMaxPicUrl() {
		return maxPicUrl;
	}

	public void setMaxPicUrl(String maxPicUrl) {
		this.maxPicUrl = maxPicUrl;
	}

	public String getMinPicUrl() {
		return minPicUrl;
	}

	public void setMinPicUrl(String minPicUrl) {
		this.minPicUrl = minPicUrl;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}
}
