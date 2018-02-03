package com.ziroom.minsu.services.house.airbnb.vo;

import java.io.Serializable;
import java.util.Date;

import com.ziroom.minsu.entity.house.AbHouseRelateEntity;

/**
 * 房源关系vo
 * @author jixd
 * @created 2017年04月17日 14:23:35
 * @param
 * @return
 */
public class AbHouseRelateVo extends AbHouseRelateEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6676297441889551206L; 
	/**
	 * 房源或者房间标号
	 */
	private String houseSn;
	/**
	 * 房源或者房间名称
	 */
	private String houseName;
	/**
	 * 房源或者房间状态
	 */
	private Integer houseStatus;
	/**
	 * 房源渠道
	 */
	private String houseChannel;
	/**
	 * 截止时间
	 */
	private Date tillDate;	
	
	/**
	 * 房东uid
	 */
	private String landlordUid;

	/**
	 * 房东姓名
	 */
	private String landlordName;

	/**
	 * 房东手机
	 */
	private String landlordMobile;

	public String getHouseSn() {
		return houseSn;
	}

	public String getHouseName() {
		return houseName;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public String getHouseChannel() {
		return houseChannel;
	}

	public Date getTillDate() {
		return tillDate;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public void setHouseChannel(String houseChannel) {
		this.houseChannel = houseChannel;
	}

	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}
}
