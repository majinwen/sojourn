package com.ziroom.minsu.services.cms.entity;

import java.util.Date;

import com.ziroom.minsu.entity.cms.ActCouponEntity;

/**
 * <p>
 * 优惠券信息、活动信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月15日
 * @since 1.0
 * @version 1.0
 */
public class ActCouponInfoUserVo extends ActCouponEntity {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 4895012354426116553L;

	/** 活动名称 */
	private String actName;
	/** 活动来源 */
	private String actSource;
	/** 活动城市 */
	private String cityCode;
	/** 活动状态 */
	private Integer actStatus;
	/** 活动对象 */
	private String roleCode;
	/** 活动类型 */
	private Integer actType;
	/** 最少使用金额 分 */
	private Integer actLimit;
	/** 最大使用金额 分 */
	private Integer actMax;
	/** 优惠券金额/折扣百分比 分 */
	private Integer actCut;
	/** 活动开始时间 */
	private Date actStartTime;
	/** 活动结束时间 */
	private Date actEndTime;
	/** 是否限制入住时间 */
	private Integer isCheckTime;

	/** 绑定用户uid */
	private String uid;
	/** 绑定用户手机号 */
	private String customerMobile;
	/** 使用时间 */
	private Date usedTime;

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getActSource() {
		return actSource;
	}

	public void setActSource(String actSource) {
		this.actSource = actSource;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getActStatus() {
		return actStatus;
	}

	public void setActStatus(Integer actStatus) {
		this.actStatus = actStatus;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public Integer getActLimit() {
		return actLimit;
	}

	public void setActLimit(Integer actLimit) {
		this.actLimit = actLimit;
	}

	public Integer getActMax() {
		return actMax;
	}

	public void setActMax(Integer actMax) {
		this.actMax = actMax;
	}

	public Integer getActCut() {
		return actCut;
	}

	public void setActCut(Integer actCut) {
		this.actCut = actCut;
	}

	public Date getActStartTime() {
		return actStartTime;
	}

	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}

	public Date getActEndTime() {
		return actEndTime;
	}

	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}

	public Integer getIsCheckTime() {
		return isCheckTime;
	}

	public void setIsCheckTime(Integer isCheckTime) {
		this.isCheckTime = isCheckTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

}
