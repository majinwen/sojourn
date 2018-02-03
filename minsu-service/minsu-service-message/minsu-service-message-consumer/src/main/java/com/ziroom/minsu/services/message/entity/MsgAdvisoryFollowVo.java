/**
 * @FileName: MsgAdvisoryFollowVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author lunan
 * @created 2017年5月25日 下午1:09:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>IM跟进展示Vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class MsgAdvisoryFollowVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3302727700330673674L;
	

	/**
	 * 预订人uid
	 */
	private String tenantUid;
	
	/**
	 * 预订人姓名
	 */
	private String tenantName;
	
	/**
	 * 预订人电话
	 */
	private String tenantTel;
	
	/**
	 * 房源名称
	 */
	private String houseName;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 房东电话
	 */
	private String landlordTel;
	
	/**
	 * 跟进状态
	 */
	private String followStatus;
	
	/**
	 * 跟进状态
	 */
	private String followStatusName;
	
	/**
	 * 城市code
	 */
	private String cityCode;
	
	/**
	 * 城市name
	 */
	private String cityName;

	/**
	 * 房源或房间fid(包括整租分租的)
	 */
	private String houseFid;
	
	/**
	 * 出租方式(包括整租分租的)
	 */
	private Integer rentWay;
	
	/**
	 *创建时间
	 */
	private Date createTime;
	
	/**
	 *创建时间
	 */
	private String msgBaseFid;
	
	/**
	 *创建时间
	 */
	private String msgHouseFid;
	

	/**
	 *  房东是否已回复,0:未回复  1:已回复   @Author:lusp  @Date:2017/8/11
	 */
	private Integer isReplay;

	/**
	 *  房客是否已下单,0:未下单  1:已下单未支付  2:已下单并支付  @Author:lusp  @Date:2017/8/11
	 */
	private Integer isOrder;

	/**
	 * 订单编号 ,供跳转订单跟进页面使用
	 *
	 */
	private String orderSn;

	public String getTenantUid() {
		return tenantUid;
	}

	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantTel() {
		return tenantTel;
	}

	public void setTenantTel(String tenantTel) {
		this.tenantTel = tenantTel;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordTel() {
		return landlordTel;
	}

	public void setLandlordTel(String landlordTel) {
		this.landlordTel = landlordTel;
	}

	public String getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(String followStatus) {
		this.followStatus = followStatus;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgBaseFid() {
		return msgBaseFid;
	}

	public void setMsgBaseFid(String msgBaseFid) {
		this.msgBaseFid = msgBaseFid;
	}

	public String getFollowStatusName() {
		return followStatusName;
	}

	public void setFollowStatusName(String followStatusName) {
		this.followStatusName = followStatusName;
	}

	public Integer getIsReplay() {
		return isReplay;
	}

	public void setIsReplay(Integer isReplay) {
		this.isReplay = isReplay;
	}

	public Integer getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getMsgHouseFid() {
		return msgHouseFid;
	}

	public void setMsgHouseFid(String msgHouseFid) {
		this.msgHouseFid = msgHouseFid;
	}
	
}
