package com.ziroom.minsu.services.house.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>需要拍照片的房源信息</p>
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
public class NeedPhotogHouseVo  extends BaseEntity{ 

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -479079745221073614L;
	
	/**
	 * 房源fid
	 */
	private String houseFid;

	/**
	 * 房源sn
	 */
	private String houseSn;
	
	/**
	 * 房源名称
	 */
	private String houseName;
	
	/**
	 * 房源地址
	 */
	private String houseAddr;
	
	/**
	 * 出租方式
	 */
	private String rentWay;
	
	/**
	 * 房间编号
	 */
	private String roomSn;
	
	/**
	 * 房间名称
	 */
	private String roomName;
	
	/**
	 * 是否需要预约拍照
	 */
	private Integer isPhotography;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 房东手机号
	 */
	private String landlordMobile;
	

	

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
	 * @return the landlordName
	 */
	public String getLandlordName() {
		return landlordName;
	}

	/**
	 * @param landlordName the landlordName to set
	 */
	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	/**
	 * @return the landlordMobile
	 */
	public String getLandlordMobile() {
		return landlordMobile;
	}

	/**
	 * @param landlordMobile the landlordMobile to set
	 */
	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}

	/**
	 * @return the houseFid
	 */
	public String getHouseFid() {
		return houseFid;
	}

	/**
	 * @param houseFid the houseFid to set
	 */
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	/**
	 * @return the houseSn
	 */
	public String getHouseSn() {
		return houseSn;
	}

	/**
	 * @param houseSn the houseSn to set
	 */
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the houseAddr
	 */
	public String getHouseAddr() {
		return houseAddr;
	}

	/**
	 * @param houseAddr the houseAddr to set
	 */
	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	/**
	 * @return the rentWay
	 */
	public String getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(String rentWay) {
		this.rentWay = rentWay;
	}

	/**
	 * @return the roomSn
	 */
	public String getRoomSn() {
		return roomSn;
	}

	/**
	 * @param roomSn the roomSn to set
	 */
	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @param roomName the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * @return the isPhotography
	 */
	public Integer getIsPhotography() {
		return isPhotography;
	}

	/**
	 * @param isPhotography the isPhotography to set
	 */
	public void setIsPhotography(Integer isPhotography) {
		this.isPhotography = isPhotography;
	}
	
	
}
