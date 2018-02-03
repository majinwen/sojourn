package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>优惠券活动实体</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月15日
 * @since 1.0
 * @version 1.0
 */
public class ActCouponInfoEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -4131853100072412362L;
    /** id */
    private Integer id;
    /** 优惠券号 */
    private String actSn;
    /** 活动名称 */
    private String actName;
    /** 活动城市 */
    private String cityCode;
    /** 优惠券名称 */
    private String couponName;
    /** 优惠券来源 */
    private Integer couponSource;
    /** 活动状态 */
    private Integer actStatus;
    /** 活动对象 */
    private Integer actUser;
    /** 优惠券数量 */
    private Integer actNum;
    /** 活动类型 */
    private Integer actType;
    /** 最少使用金额 分 */
    private Integer actLimit;
    /** 优惠券金额/折扣百分比 分*/
    private Integer actCut;
    /** 随机最大金额 分 */
    private Integer randomMax;
    /** 随机最大金额 分*/
    private Integer randomMin;
    /** 活动开始时间*/
    private Date actStartTime;
    /** 活动结束时间*/
    private Date actEndTime;
    /** 优惠券开始时间*/
    private Date couponStartTime;
    /** 优惠券结束时间*/
    private Date couponEndTime;
    /** 是否限制入住时间*/
    private Integer isCheckTime;
    /** 限制入住时间*/
    private Date checkInTime;
    /** 限制离开时间*/
    private Date checkOutTime;
    /** 是否已生成优惠券*/
    private Integer isCoupon;
    /** 创建人id*/
    private String createId;
    /** 创建时间*/
    private Date createTime;
    /** 最后修改时间*/
    private Date lastModifyDate;
    /** 是否删除 0：否，1：是*/
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

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName == null ? null : actName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public Integer getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(Integer couponSource) {
        this.couponSource = couponSource;
    }

    public Integer getActStatus() {
        return actStatus;
    }

    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }

    public Integer getActUser() {
        return actUser;
    }

    public void setActUser(Integer actUser) {
        this.actUser = actUser;
    }

    public Integer getActNum() {
        return actNum;
    }

    public void setActNum(Integer actNum) {
        this.actNum = actNum;
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

    public Integer getActCut() {
        return actCut;
    }

    public void setActCut(Integer actCut) {
        this.actCut = actCut;
    }

    public Integer getRandomMax() {
        return randomMax;
    }

    public void setRandomMax(Integer randomMax) {
        this.randomMax = randomMax;
    }

    public Integer getRandomMin() {
        return randomMin;
    }

    public void setRandomMin(Integer randomMin) {
        this.randomMin = randomMin;
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

    public Integer getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(Integer isCoupon) {
        this.isCoupon = isCoupon;
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

    public Integer getIsCheckTime() {
        return isCheckTime;
    }

    public void setIsCheckTime(Integer isCheckTime) {
        this.isCheckTime = isCheckTime;
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


}