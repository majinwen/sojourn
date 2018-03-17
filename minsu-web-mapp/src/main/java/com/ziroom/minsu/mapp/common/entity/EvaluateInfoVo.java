
package com.ziroom.minsu.mapp.common.entity;

import java.io.Serializable;

import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;

/**
 * <p>评价详情实体</p>
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
public class EvaluateInfoVo implements Serializable{

	

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
	   /** 入住时间 值必须是标准的时间格式 12:00:00 */
    private String checkInTime;

    /** 退订时间 值必须是标准的时间格式 12:00:00 */
    private String checkOutTime;
	/** 出租价格 */
	private Integer price;
	/**住房实际时长（单位：晚）*/
	private Integer housingDay;
	
	/**
	 * 总金额 所有费用之和
	 */
	private int sumMoney;
	
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
	private EvaluateOrderEntity evaluateOrder;
	
	/**
	 * 房东评价实体
	 */
	private LandlordEvaluateEntity landlordEvaluate;
	
	/**
	 * 房客评价实体
	 */
	private TenantEvaluateEntity tenantEvaluate;

	
	/**
	 *房东时间字符串
	 */
	private String lanTimeStr;
	
	/**
	 * 房客字符串
	 */
	private String tenTimeStr;
	
	
	
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

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getHousingDay() {
		return housingDay;
	}

	public void setHousingDay(Integer housingDay) {
		this.housingDay = housingDay;
	}

	public int getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(int sumMoney) {
		this.sumMoney = sumMoney;
	}

	public EvaluateOrderEntity getEvaluateOrder() {
		return evaluateOrder;
	}

	public void setEvaluateOrder(EvaluateOrderEntity evaluateOrder) {
		this.evaluateOrder = evaluateOrder;
	}

	public LandlordEvaluateEntity getLandlordEvaluate() {
		return landlordEvaluate;
	}

	public void setLandlordEvaluate(LandlordEvaluateEntity landlordEvaluate) {
		this.landlordEvaluate = landlordEvaluate;
	}

	public TenantEvaluateEntity getTenantEvaluate() {
		return tenantEvaluate;
	}

	public void setTenantEvaluate(TenantEvaluateEntity tenantEvaluate) {
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
