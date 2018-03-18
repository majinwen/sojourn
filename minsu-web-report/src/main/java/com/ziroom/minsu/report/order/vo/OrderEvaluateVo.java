package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;


public class OrderEvaluateVo extends BaseEntity {

	@FieldMeta(skip = true)
	private static final long serialVersionUID = -2241893974424220937L;

	@FieldMeta(name = "国家", order = 1)
	private String nation = "";

	@FieldMeta(name = "大区", order = 2)
	private String regionName = "";

	@FieldMeta(name = "城市", order = 3)
	private String cityName = "";

	@FieldMeta(skip = true)
	private String cityCode;

	@FieldMeta(name="订单号",order=4)
	private String orderSn;
	
	@FieldMeta(name="房源编号",order=5)
	private String houseSn;
	
	@FieldMeta(name="订单结束时间",order=6)
	private String realEndTime;

	@FieldMeta(name="客户评价时间",order=7)
	private String tenantEvaTime;
	
	@FieldMeta(name="房客评分",order=8)
	private String avgTenantScore = "0.00";

	@FieldMeta(name="清洁度",order=9)
	private String scoreHouseClean = "0";

	@FieldMeta(name="描述相符",order=10)
	private String scoreDescriptionMatch = "0";

	@FieldMeta(name="房东印象",order=11)
	private String scoreSafetyDegree = "0";

	@FieldMeta(name="周边环境",order=12)
	private String scoreTrafficPosition = "0";

	@FieldMeta(name="性价比",order=13)
	private String scoreCostPerformance = "0";

	@FieldMeta(name="评价内容",order=14)
	private String tenantContent = "";

	@FieldMeta(name="房东评价时间",order=15)
	private String landlordEvaTime = "";

	@FieldMeta(name="房东评价内容",order=16)
	private String landlordContent = "";

	@FieldMeta(name="房东评分",order=17)
	private String landlordSatisfied = "0";


	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(String realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getTenantEvaTime() {
		return tenantEvaTime;
	}

	public void setTenantEvaTime(String tenantEvaTime) {
		this.tenantEvaTime = tenantEvaTime;
	}

	public String getAvgTenantScore() {
		return avgTenantScore;
	}

	public void setAvgTenantScore(String avgTenantScore) {
		this.avgTenantScore = avgTenantScore;
	}

	public String getScoreHouseClean() {
		return scoreHouseClean;
	}

	public void setScoreHouseClean(String scoreHouseClean) {
		this.scoreHouseClean = scoreHouseClean;
	}

	public String getScoreDescriptionMatch() {
		return scoreDescriptionMatch;
	}

	public void setScoreDescriptionMatch(String scoreDescriptionMatch) {
		this.scoreDescriptionMatch = scoreDescriptionMatch;
	}

	public String getScoreSafetyDegree() {
		return scoreSafetyDegree;
	}

	public void setScoreSafetyDegree(String scoreSafetyDegree) {
		this.scoreSafetyDegree = scoreSafetyDegree;
	}

	public String getScoreTrafficPosition() {
		return scoreTrafficPosition;
	}

	public void setScoreTrafficPosition(String scoreTrafficPosition) {
		this.scoreTrafficPosition = scoreTrafficPosition;
	}

	public String getScoreCostPerformance() {
		return scoreCostPerformance;
	}

	public void setScoreCostPerformance(String scoreCostPerformance) {
		this.scoreCostPerformance = scoreCostPerformance;
	}

	public String getTenantContent() {
		return tenantContent;
	}

	public void setTenantContent(String tenantContent) {
		this.tenantContent = tenantContent;
	}

	public String getLandlordEvaTime() {
		return landlordEvaTime;
	}

	public void setLandlordEvaTime(String landlordEvaTime) {
		this.landlordEvaTime = landlordEvaTime;
	}

	public String getLandlordContent() {
		return landlordContent;
	}

	public void setLandlordContent(String landlordContent) {
		this.landlordContent = landlordContent;
	}

	public String getLandlordSatisfied() {
		return landlordSatisfied;
	}

	public void setLandlordSatisfied(String landlordSatisfied) {
		this.landlordSatisfied = landlordSatisfied;
	}
}
