package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>优惠券</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/06/08.
 * @version 1.0
 * @since 1.0
 */
public class ActCouponEntity extends BaseEntity {

    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6420878391201098965L;
	/** id */
    private Integer id;
    /** 活动组号 */
    private String groupSn;
    /** 活动编号 */
    private String actSn;
	/** 随机号 */
    private String randSn;
    /** 优惠券号 */
    private String couponSn;
    /** 优惠券名称 */
    private String couponName;
    /** 优惠券状态 */
    private Integer couponStatus;
    
    transient private Integer oldStatus;
    
    /** 优惠券来源 */
    private Integer couponSource;

	/** 活动对象 */
    private String actUser;
    /** 活动类型 */
    private Integer actType;
    /** 最低消费金额 */
    private Integer actLimit;

    /** 使用次数 */
    private Integer times;

	/** 最高消费金额 */
    private Integer actMax;
	/** 折扣金额 */
    private Integer actCut;
	/** 优惠券开始时间 */
    private Date couponStartTime;
    /** 优惠券结束时间 */
    private Date couponEndTime;
	/** 限制入住时间*/
    private Date checkInTime;
	/** 限制离开时间*/
    private Date checkOutTime;
    /**
     * 是否限制房源 0=不限制 1=限制
     */
    private Integer isLimitHouse;
	/** 创建人id*/
    private String createId;
    /** 创建时间 */
    private Date createTime;
    /** 最后修改时间 */
    private Date lastModifyDate;
    /** 是否删除0：未删除 1： 已删除 */
    private Integer isDel;

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

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn == null ? null : couponSn.trim();
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Integer getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(Integer couponSource) {
        this.couponSource = couponSource;
    }

    public String getActUser() {
        return actUser;
    }

    public void setActUser(String actUser) {
        this.actUser = actUser;
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

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
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

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    public String getRandSn() {
        return randSn;
    }

    public void setRandSn(String randSn) {
        this.randSn = randSn;
    }

    public Integer getIsLimitHouse() {
        return isLimitHouse;
    }

    public void setIsLimitHouse(Integer isLimitHouse) {
        this.isLimitHouse = isLimitHouse;
    }
}