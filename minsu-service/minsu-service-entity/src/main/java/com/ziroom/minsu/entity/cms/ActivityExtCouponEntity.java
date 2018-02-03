package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>优惠券活动扩展表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年7月12日
 * @since 1.0
 * @version 1.0
 */
public class ActivityExtCouponEntity extends BaseEntity {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 6347808842508850437L;

	private Integer id;

	private String actSn;

	private String couponName;

	private Integer couponNum;

	private Integer randomMin;

	private Integer randomMax;

	private Date couponStartTime;

	private Date couponEndTime;

	private String createId;

	private Date createTime;

	private Date lastModifyDate;

	private Integer isDel;

	/**
	 * 优惠券时间类型
	 */
	private Integer  couponTimeType;

	/**
	 * 优惠券活动时长
	 */
	private Integer  couponTimeLast;
	/**
	 * 是否提醒
	 */
	private Integer isWarn;
	
	/**
	 * 提醒次数
	 */
	private Integer warnTimes;

	public Integer getCouponTimeType() {
		return couponTimeType;
	}

	public void setCouponTimeType(Integer couponTimeType) {
		this.couponTimeType = couponTimeType;
	}

	public Integer getCouponTimeLast() {
		return couponTimeLast;
	}

	public void setCouponTimeLast(Integer couponTimeLast) {
		this.couponTimeLast = couponTimeLast;
	}

	//新加属性，优惠定额规则
    private String discountQuota;

	public String getDiscountQuota() {
		return discountQuota;
	}

	public void setDiscountQuota(String discountQuota) {
		this.discountQuota = discountQuota;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActSn() {
		return actSn;
	}

	public void setActSn(String actSn) {
		this.actSn = actSn == null ? null : actSn.trim();
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName == null ? null : couponName.trim();
	}

	public Integer getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(Integer couponNum) {
		this.couponNum = couponNum;
	}

	public Integer getRandomMin() {
		return randomMin;
	}

	public void setRandomMin(Integer randomMin) {
		this.randomMin = randomMin;
	}

	public Integer getRandomMax() {
		return randomMax;
	}

	public void setRandomMax(Integer randomMax) {
		this.randomMax = randomMax;
	}

	public Date getCouponStartTime() {
		return couponStartTime;
	}

	public void setCouponStartTime(Date couponStartTime) {
		this.couponStartTime = couponStartTime;
	}

	public Date getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(Date couponEndTime) {
		this.couponEndTime = couponEndTime;
	}


	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId == null ? null : createId.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsWarn() {
		return isWarn;
	}

	public void setIsWarn(Integer isWarn) {
		this.isWarn = isWarn;
	}

	public Integer getWarnTimes() {
		return warnTimes;
	}

	public void setWarnTimes(Integer warnTimes) {
		this.warnTimes = warnTimes;
	}
	
}