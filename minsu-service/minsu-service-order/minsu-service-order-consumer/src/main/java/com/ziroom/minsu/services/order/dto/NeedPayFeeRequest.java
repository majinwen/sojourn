package com.ziroom.minsu.services.order.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.services.order.entity.CommissionFreeVo;


public class NeedPayFeeRequest extends ConfigBaseRequest {

	/** 序列化id */
	private static final long serialVersionUID = 6395788730601905743L;

	/** 用户uid */
	private String userUid;

	/**
	 * 是否需要自动优惠券
	 * 1：需要，0：不需要
	 * 默认为0（只有couponSn参数为空时此字段才有效）
	 */
	private Integer isAutoCoupon = 0;

	/** 优惠券号 */
	private String couponSn;

	/** 房源 houseFid、房间 roomFid、床位bedFid */
	private String fid;

	/** 租住方式 */
	private Integer rentWay;

	/** 开始时间 */
	private Date startTime;

	/** 结束时间 */
	private Date endTime;

	/** 设备来源 */
	private Integer sourceType;

	/** 出行目的 */
	private String tripPurpose;

	/** 优惠券信息 */
	private ActCouponUserEntity actCouponUserEntity;

	/** 免佣金的逻辑 */
	private CommissionFreeVo lanFree = new CommissionFreeVo();

	/** 当前的有效的活动信息 */
	private List<ActivityVo> activitys = new ArrayList<ActivityVo>();

	public List<ActivityVo> getActivitys() {
		return activitys;
	}

	/** 版本号 */
	private Integer versionCode;

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public void setActivitys(List<ActivityVo> activitys) {
		this.activitys = activitys;
	}

	public ActCouponUserEntity getActCouponUserEntity() {
		return actCouponUserEntity;
	}

	public void setActCouponUserEntity(ActCouponUserEntity actCouponUserEntity) {
		this.actCouponUserEntity = actCouponUserEntity;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCouponSn() {
		return couponSn;
	}

	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getTripPurpose() {
		return tripPurpose;
	}

	public void setTripPurpose(String tripPurpose) {
		this.tripPurpose = tripPurpose;
	}


	public CommissionFreeVo getLanFree() {
		return lanFree;
	}

	public void setLanFree(CommissionFreeVo lanFree) {
		this.lanFree = lanFree;
	}

	public Integer getIsAutoCoupon() {
		return isAutoCoupon;
	}

	public void setIsAutoCoupon(Integer isAutoCoupon) {
		this.isAutoCoupon = isAutoCoupon;
	}
}
