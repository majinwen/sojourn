/**
 * @FileName: HouseFollowVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2017年2月23日 下午4:58:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseFollowEntity;
import com.ziroom.minsu.entity.house.HouseFollowLogEntity;

/**
 * <p>房源跟进展示Vo</p>
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
public class HouseFollowVo extends HouseFollowEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6520832594954996939L;
	
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
	private String landordMobile;
	
	/**
	 * 房源编号
	 */
	private String houseSn;
	
	/**
	 * 房源姓名
	 */
	private String houseName;
	
	/**
	 * 审核未通过备注
	 */
	private String refuseMark;
	
	/**
	 * 房源发布时间
	 */
	private Date pushDate;
	
	/**
	 * 房源发布时间
	 */
	private String cityCode;
	
	/**
	 * 房源发布时间
	 */
	private String cityName;
	
	/**
	 * 跟进状态描述
	 */
	private String followStatusStr;
	
	/**
	 * 房源状态
	 */
	private Integer houseStatus;
	
	/**
	 * 房源状态描述
	 */
	private String houseStatusStr;
	
	/**
	 * 房源跟进明细集合
	 */
	private List<HouseFollowLogEntity> followLogList=new ArrayList<>();
	
	/**
	 * 跟进房源预约拍照订单状态
	 */
	private String photoOrderStatus;

	/**
	 * @return the photoOrderStatus
	 */
	public String getPhotoOrderStatus() {
		return photoOrderStatus;
	}

	/**
	 * @param photoOrderStatus the photoOrderStatus to set
	 */
	public void setPhotoOrderStatus(String photoOrderStatus) {
		this.photoOrderStatus = photoOrderStatus;
	}

	/**
	 * @return the followLogList
	 */
	public List<HouseFollowLogEntity> getFollowLogList() {
		return followLogList;
	}

	/**
	 * @param followLogList the followLogList to set
	 */
	public void setFollowLogList(List<HouseFollowLogEntity> followLogList) {
		this.followLogList = followLogList;
	}

	/**
	 * @return the houseStatus
	 */
	public Integer getHouseStatus() {
		return houseStatus;
	}

	/**
	 * @param houseStatus the houseStatus to set
	 */
	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	/**
	 * @return the houseStatusStr
	 */
	public String getHouseStatusStr() {
		return houseStatusStr;
	}

	/**
	 * @param houseStatusStr the houseStatusStr to set
	 */
	public void setHouseStatusStr(String houseStatusStr) {
		this.houseStatusStr = houseStatusStr;
	}

	/**
	 * @return the followStatusStr
	 */
	public String getFollowStatusStr() {
		return followStatusStr;
	}

	/**
	 * @param followStatusStr the followStatusStr to set
	 */
	public void setFollowStatusStr(String followStatusStr) {
		this.followStatusStr = followStatusStr;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	 * @return the landordMobile
	 */
	public String getLandordMobile() {
		return landordMobile;
	}

	/**
	 * @param landordMobile the landordMobile to set
	 */
	public void setLandordMobile(String landordMobile) {
		this.landordMobile = landordMobile;
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
	 * @return the refuseMark
	 */
	public String getRefuseMark() {
		return refuseMark;
	}

	/**
	 * @param refuseMark the refuseMark to set
	 */
	public void setRefuseMark(String refuseMark) {
		this.refuseMark = refuseMark;
	}

	/**
	 * @return the pushDate
	 */
	public Date getPushDate() {
		return pushDate;
	}

	/**
	 * @param pushDate the pushDate to set
	 */
	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}
}
