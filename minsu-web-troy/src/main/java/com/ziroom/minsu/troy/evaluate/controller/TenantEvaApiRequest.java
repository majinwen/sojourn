package com.ziroom.minsu.troy.evaluate.controller;

import java.io.Serializable;


/**
 * <p>房客评价请求参数</p>
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
public class TenantEvaApiRequest implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 7632482060043421049L;

	/**
	 * fid
	 */
	private String evaOrderFid;

	/**
	 * 房东评价对应的evaOrderFid;
	 */
	private String evaOrderIdLandlord;

	/**
	 * 订单编号
	 */
	private String orderSn;

	private String houseFid;

	private String roomFid;

	private Integer rentWay;

	/**
	 * 房东uid
	 */
	private String landlordUid;

	/**
	 * 房客uid
	 */
	private String tenantUid;

	/**
	 * 评价人类型（房东=1 房客=2）
	 */
	private Integer evaUserType;

	/**
	 * 评价状态
	 */
	private Integer evaStatu;
	/**
	 * 评价内容
	 */
	private String content;

	/**
	 * 整洁卫生星级（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
	 */
	private Integer houseClean;

	/**
	 * 描述相符星级（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意
	 */
	private Integer descriptionMatch;

	/**
	 * 安全程度（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
	 */
	private Integer safetyDegree;

	/**
	 * 交通位置星级(1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
	 */
	private Integer trafficPosition;

	/**
	 * 性价比（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
	 */
	private Integer costPerformance;

	private Integer landlordSatisfied;

	private String lanContent;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getEvaUserType() {
		return evaUserType;
	}

	public void setEvaUserType(Integer evaUserType) {
		this.evaUserType = evaUserType;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getHouseClean() {
		return houseClean;
	}

	public void setHouseClean(Integer houseClean) {
		this.houseClean = houseClean;
	}

	public Integer getDescriptionMatch() {
		return descriptionMatch;
	}

	public void setDescriptionMatch(Integer descriptionMatch) {
		this.descriptionMatch = descriptionMatch;
	}

	public Integer getSafetyDegree() {
		return safetyDegree;
	}

	public void setSafetyDegree(Integer safetyDegree) {
		this.safetyDegree = safetyDegree;
	}

	public Integer getTrafficPosition() {
		return trafficPosition;
	}

	public void setTrafficPosition(Integer trafficPosition) {
		this.trafficPosition = trafficPosition;
	}

	public Integer getCostPerformance() {
		return costPerformance;
	}

	public void setCostPerformance(Integer costPerformance) {
		this.costPerformance = costPerformance;
	}

	public String getEvaOrderFid() {
		return evaOrderFid;
	}

	public void setEvaOrderFid(String evaOrderFid) {

		this.evaOrderFid = evaOrderFid;
	}

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

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Integer getEvaStatu() {
		return evaStatu;
	}

	public void setEvaStatu(Integer evaStatu) {
		this.evaStatu = evaStatu;
	}

	public Integer getLandlordSatisfied() {
		return landlordSatisfied;
	}

	public void setLandlordSatisfied(Integer landlordSatisfied) {
		this.landlordSatisfied = landlordSatisfied;
	}

	public String getLanContent() {
		return lanContent;
	}

	public void setLanContent(String lanContent) {
		this.lanContent = lanContent;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getTenantUid() {
		return tenantUid;
	}

	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	public String getEvaOrderIdLandlord() {
		return evaOrderIdLandlord;
	}

	public void setEvaOrderIdLandlord(String evaOrderIdLandlord) {
		this.evaOrderIdLandlord = evaOrderIdLandlord;
	}
}
