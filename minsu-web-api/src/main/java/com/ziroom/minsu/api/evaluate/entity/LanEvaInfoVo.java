/**
 * @FileName: LanEvaInfoVo.java
 * @Package com.ziroom.minsu.api.evaluate.entity
 * 
 * @author yd
 * @created 2017年1月20日 上午10:44:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.evaluate.entity;

import java.io.Serializable;

import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;

/**
 * <p>房东端 评价详情信息</p>
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
public class LanEvaInfoVo implements Serializable{


	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 5083644051739839880L;

	/** 房源名称 */
	private String houseName;
	/**
	 * 房东名字
	 */
	private String landlordName;
	
	/**
	 * 房东url
	 */
	private String landlordPicUrl;
	
	/**
	 * 房客头像
	 */
	private String userPicUrl;

	/** 房间名称  */
	private String roomName;

	/** 床编号  */
	private String bedSn;
	/**预订人姓名*/
	private String userName;
	
	/**住房实际时长（单位：晚）*/
	private Integer housingDay;
	
	/**
	 * 房源照片地址
	 */
	private String picUrl;
	
	/**
	 * 入住起始时间
	 */
	private String startTimeStr;
	
	/**
	 * 入住结束时间
	 */
	private String endTimeStr;
	
	/**入住人数量*/
	private Integer contactsNum;
	
	/**
	 * 评价状态200：不可评价 100 待评价 101 用户已评价 110 房东已评价 111 都已经评价
	 */
	private Integer evaStatus;
	
	/**
	 * 订单评价关系实体
	 */
	private EvaluateOrderVo evaluateOrder;
	
	/**
	 * 房东评价实体
	 */
	private LandlordEvaluateVo landlordEvaluate;
	
	/**
	 * 房客评价实体
	 */
	private TenantEvaluateVo tenantEvaluate;

	
	/**
	 *房东时间字符串
	 */
	private String lanTimeStr;
	
	/**
	 * 房客字符串
	 */
	private String tenTimeStr;
	
	/**
	 * 评价回复内容
	 */
	private String evaReplayCon;
	
	/**
	 * 房东 评价状态 1=可以评价 2=不可以评价 
	 */
	private Integer lanEvaStatu;
	
	/**
	 * 房东回复状态 1=可以回复  2=不可以回复
	 */
	private Integer lanReplayStatu;
	
	/**
	 * @return the lanEvaStatu
	 */
	public Integer getLanEvaStatu() {
		return lanEvaStatu;
	}

	/**
	 * @param lanEvaStatu the lanEvaStatu to set
	 */
	public void setLanEvaStatu(Integer lanEvaStatu) {
		this.lanEvaStatu = lanEvaStatu;
	}

	/**
	 * @return the lanReplayStatu
	 */
	public Integer getLanReplayStatu() {
		return lanReplayStatu;
	}

	/**
	 * @param lanReplayStatu the lanReplayStatu to set
	 */
	public void setLanReplayStatu(Integer lanReplayStatu) {
		this.lanReplayStatu = lanReplayStatu;
	}

	/**
	 * @return the evaReplayCon
	 */
	public String getEvaReplayCon() {
		return evaReplayCon;
	}

	/**
	 * @param evaReplayCon the evaReplayCon to set
	 */
	public void setEvaReplayCon(String evaReplayCon) {
		this.evaReplayCon = evaReplayCon;
	}

	public String getLanTimeStr() {
		return lanTimeStr;
	}

	public void setLanTimeStr(String lanTimeStr) {
		this.lanTimeStr = lanTimeStr;
	}

	public String getTenTimeStr() {
		return tenTimeStr;
	}

	public void setTenTimeStr(String tenTimeStr) {
		this.tenTimeStr = tenTimeStr;
	}

	public Integer getEvaStatus() {
		return evaStatus;
	}

	public void setEvaStatus(Integer evaStatus) {
		this.evaStatus = evaStatus;
	}

	public Integer getContactsNum() {
		return contactsNum;
	}

	public void setContactsNum(Integer contactsNum) {
		this.contactsNum = contactsNum;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordPicUrl() {
		return landlordPicUrl;
	}

	public void setLandlordPicUrl(String landlordPicUrl) {
		this.landlordPicUrl = landlordPicUrl;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBedSn() {
		return bedSn;
	}

	public void setBedSn(String bedSn) {
		this.bedSn = bedSn;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getHousingDay() {
		return housingDay;
	}

	public void setHousingDay(Integer housingDay) {
		this.housingDay = housingDay;
	}


	/**
	 * @return the evaluateOrder
	 */
	public EvaluateOrderVo getEvaluateOrder() {
		return evaluateOrder;
	}

	/**
	 * @param evaluateOrder the evaluateOrder to set
	 */
	public void setEvaluateOrder(EvaluateOrderVo evaluateOrder) {
		this.evaluateOrder = evaluateOrder;
	}

	

	/**
	 * @return the landlordEvaluate
	 */
	public LandlordEvaluateVo getLandlordEvaluate() {
		return landlordEvaluate;
	}

	/**
	 * @param landlordEvaluate the landlordEvaluate to set
	 */
	public void setLandlordEvaluate(LandlordEvaluateVo landlordEvaluate) {
		this.landlordEvaluate = landlordEvaluate;
	}

	/**
	 * @return the tenantEvaluate
	 */
	public TenantEvaluateVo getTenantEvaluate() {
		return tenantEvaluate;
	}

	/**
	 * @param tenantEvaluate the tenantEvaluate to set
	 */
	public void setTenantEvaluate(TenantEvaluateVo tenantEvaluate) {
		this.tenantEvaluate = tenantEvaluate;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}
