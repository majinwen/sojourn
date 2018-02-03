package com.ziroom.minsu.entity.cms;

import java.util.Date;

/**
 * <p>全部的活动信息 优惠券</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/14.
 * @version 1.0
 * @since 1.0
 */
public class ActivityFullEntity extends ActivityEntity {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -5818325655058326029L;


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
    
    //新加属性，优惠定额规则
    private String discountQuota;

    /**
     * 优惠券时间类型
     */
    private Integer  couponTimeType;

    /**
     * 优惠券活动时长
     */
    private Integer  couponTimeLast;
    
    /**
     * 是否报警
     */
    private Integer isWarn;
    /**
     * 报警次数
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
    
	@Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getActSn() {
        return actSn;
    }

    @Override
    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
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

    @Override
    public String getCreateId() {
        return createId;
    }

    @Override
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    @Override
    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    @Override
    public Integer getIsDel() {
        return isDel;
    }

    @Override
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    
    public String getDiscountQuota() {
		return discountQuota;
	}

	public void setDiscountQuota(String discountQuota) {
		this.discountQuota = discountQuota;
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
