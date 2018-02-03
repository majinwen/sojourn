package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>优惠券实体类</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/24.
 * @version 1.0
 * @since 1.0
 */
public class CouponEntity extends BaseEntity {

    /**  序列化id  */
    private static final long serialVersionUID = 12456465666424L;

    /** id */
    private Long id;

    /** fid */
    private String fid;

    /** 用户id */
    private String userId;

    /** 订单编号 */
    private String orderSn;

    /** 优惠券编号 */
    private String couponSn;

    /** 优惠券来源 */
    private Integer orgType;

    /** 优惠券类型 */
    private Integer couponType;

    /** 优惠券标题 */
    private String couponName;

    /** 优惠券金额 */
    private Integer couponPrice;

    /** 优惠券状态 */
    private Byte couponStatus;

    /** 优惠券描述 */
    private String couponDes;

    /** 优惠券最小使用限制 */
    private Integer couponMin;

    /** 使用城市 */
    private String cityCode;

    /** 有效开始时间 */
    private Date startTime;

    /** 有效结束时间 */
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Byte getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Byte couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponDes() {
        return couponDes;
    }

    public void setCouponDes(String couponDes) {
        this.couponDes = couponDes;
    }

    public Integer getCouponMin() {
        return couponMin;
    }

    public void setCouponMin(Integer couponMin) {
        this.couponMin = couponMin;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
}
