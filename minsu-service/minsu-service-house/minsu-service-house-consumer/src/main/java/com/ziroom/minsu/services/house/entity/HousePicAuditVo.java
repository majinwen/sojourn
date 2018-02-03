/**
 * @FileName: HousePicAuditVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月13日 下午10:07:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;

/**
 * <p>房源图片审核Vo</p>
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
public class HousePicAuditVo extends HouseBaseMsgEntity{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8758974086167070766L;
	//房间fid
	private String roomFid;
	//房东名称
	private String landlordName;
	// 房东电话
	private String landlordMobile;
	// 楼盘名称
	private String communityName;
	//房源户型
	private String houseModel;
	//出租类型
	private String rentWayStr;
	//房间相关图片集合
	private List<HousePicVo> roomPicList=new ArrayList<HousePicVo>();
	//房源相关图片集合
	private List<HousePicVo> housePicList=new ArrayList<HousePicVo>();
	//房源或者房间默认图片fid
	private String defaultPicFid;
	
	/**
	 * @return the defaultPicFid
	 */
	public String getDefaultPicFid() {
		return defaultPicFid;
	}
	/**
	 * @param defaultPicFid the defaultPicFid to set
	 */
	public void setDefaultPicFid(String defaultPicFid) {
		this.defaultPicFid = defaultPicFid;
	}
	/**
	 * @return the roomPicList
	 */
	public List<HousePicVo> getRoomPicList() {
		return roomPicList;
	}
	/**
	 * @param roomPicList the roomPicList to set
	 */
	public void setRoomPicList(List<HousePicVo> roomPicList) {
		this.roomPicList = roomPicList;
	}
	/**
	 * @return the housePicList
	 */
	public List<HousePicVo> getHousePicList() {
		return housePicList;
	}
	/**
	 * @param housePicList the housePicList to set
	 */
	public void setHousePicList(List<HousePicVo> housePicList) {
		this.housePicList = housePicList;
	}
	/**
	 * @return the roomFid
	 */
	public String getRoomFid() {
		return roomFid;
	}
	/**
	 * @param roomFid the roomFid to set
	 */
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
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
	 * @return the houseModel
	 */
	public String getHouseModel() {
		return houseModel;
	}
	/**
	 * @param houseModel the houseModel to set
	 */
	public void setHouseModel(String houseModel) {
		this.houseModel = houseModel;
	}
	
	/**
	 * @return the rentWayStr
	 */
	public String getRentWayStr() {
		return rentWayStr;
	}
	/**
	 * @param rentWayStr the rentWayStr to set
	 */
	public void setRentWayStr(String rentWayStr) {
		this.rentWayStr = rentWayStr;
	}
}
