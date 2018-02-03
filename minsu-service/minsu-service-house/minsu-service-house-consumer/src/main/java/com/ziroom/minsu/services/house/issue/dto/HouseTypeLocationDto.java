/**
 * @FileName: HouseTypeLocationDto.java
 * @Package com.ziroom.minsu.services.house.issue.vo.dto
 * 
 * @author bushujie
 * @created 2017年6月20日 下午2:18:37
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.dto;

import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * <p>保存更新房源类型位置信息dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseTypeLocationDto {
	
	/*
	 * 房源fid
	 */
	private String houseBaseFid;
	
	/*
	 * 房东uid
	 */
	@NotEmpty(message = "{landlordUid.null}")
	private String landlordUid;

	/*
	 * 出租方式 0：合租，1：整租
	 */
	@NotNull(message = "{house.rentway.null}")
	private Integer rentWay;
	/*
	 * 房源类型
	 */
	@NotNull(message = "{house.type.null}")
	private Integer houseType;
	/*
	 * 地区code
	 */
	@NotEmpty(message = "{house.area.code.null}")
	private String regionCode;
	
	/*
	 * 地区名称
	 */
	private String regionName;

	/*
	 * 街道信息
	 */
	@NotEmpty(message = "{house.houseStreet.null}")
	private String houseStreet;
	/*
	 * 小区名称
	 */
	private String communityName;
	/*
	 * 楼层门牌号
	 */
	private String houseNumber;
	/*
	 * 经度（百度）
	 */
	private Double longitude;
	/*
	 * 纬度（百度）
	 */
	private Double latitude;
	
	/*
	 * 经度（谷歌）
	 */
	private Double googleLongitude;

	/*
	 * 纬度（谷歌）
	 */
	private Double googleLatitude;
	
	/*
	 * 房源来源 0:tory, 1:pc, 2:ios, 3:android, 5:app 
	 */
	private Integer houseSource;
	
	/*
	 * 房源管家信息
	 */
	private HouseGuardRelEntity houseGuardRel;
	/*
	 * 房间类型 0：房间   1：客厅
	 */
	private Integer roomType;

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return the houseGuardRel
	 */
	public HouseGuardRelEntity getHouseGuardRel() {
		return houseGuardRel;
	}

	/**
	 * @param houseGuardRel the houseGuardRel to set
	 */
	public void setHouseGuardRel(HouseGuardRelEntity houseGuardRel) {
		this.houseGuardRel = houseGuardRel;
	}

	/**
	 * @return the houseSource
	 */
	public Integer getHouseSource() {
		return houseSource;
	}

	/**
	 * @param houseSource the houseSource to set
	 */
	public void setHouseSource(Integer houseSource) {
		this.houseSource = houseSource;
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
	 * @return the houseType
	 */
	public Integer getHouseType() {
		return houseType;
	}

	/**
	 * @param houseType the houseType to set
	 */
	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the houseStreet
	 */
	public String getHouseStreet() {
		return houseStreet;
	}

	/**
	 * @param houseStreet the houseStreet to set
	 */
	public void setHouseStreet(String houseStreet) {
		this.houseStreet = houseStreet;
	}

	/**
	 * @return the communityName
	 */
	public String getCommunityName() {
		return communityName;
	}

	/**
	 * @param communityName the communityName to set
	 */
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the googleLongitude
	 */
	public Double getGoogleLongitude() {
		return googleLongitude;
	}

	/**
	 * @param googleLongitude the googleLongitude to set
	 */
	public void setGoogleLongitude(Double googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	/**
	 * @return the googleLatitude
	 */
	public Double getGoogleLatitude() {
		return googleLatitude;
	}

	/**
	 * @param googleLatitude the googleLatitude to set
	 */
	public void setGoogleLatitude(Double googleLatitude) {
		this.googleLatitude = googleLatitude;
	}
	
	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
	
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}
