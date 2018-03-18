package com.ziroom.minsu.report.cms.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/3/15 10:42
 * @version 1.0
 * @since 1.0
 */
public class CouponInfoVo extends BaseEntity {

    @FieldMeta(skip = true)
    private static final long serialVersionUID = -1935363325182416667L;

    @FieldMeta(name = "活动名称", order = 1)
    private String actName;

    @FieldMeta(name = "编号", order = 2)
    private String actSn;

    @FieldMeta(name = "优惠券名称", order = 3)
    private String couponSn;

    @FieldMeta(name = "优惠券状态", order = 4)
    private String couponStatus;

    @FieldMeta(name = "优惠券类型", order = 5)
    private String actType;

    @FieldMeta(name = "优惠券金额", order = 6)
    private String actCut;

    @FieldMeta(name = "活动创建时间", order = 7)
    private String actCreateTime;

    @FieldMeta(name = "优惠券创建时间", order = 8)
    private String couponCreateTime;

    @FieldMeta(name = "优惠券开始时间", order = 9)
    private String couponStartTime;

    @FieldMeta(name = "优惠券结束时间", order = 10)
    private String couponEndTime;

    @FieldMeta(name = "领取人uid", order = 11)
    private String userUid = "";

    @FieldMeta(name = "领取电话", order = 12)
    private String customerMobile = "";

    @FieldMeta(name = "领取时间", order = 13)
    private String gotTime = "";

    @FieldMeta(name = "订单号", order = 14)
    private String orderSn = "";

    @FieldMeta(name = "使用时间", order = 15)
    private String usedTime = "";

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public String getActCut() {
        return actCut;
    }

    public void setActCut(String actCut) {
        this.actCut = actCut;
    }

    public String getActCreateTime() {
        return actCreateTime;
    }

    public void setActCreateTime(String actCreateTime) {
        this.actCreateTime = actCreateTime;
    }

    public String getCouponCreateTime() {
        return couponCreateTime;
    }

    public void setCouponCreateTime(String couponCreateTime) {
        this.couponCreateTime = couponCreateTime;
    }

    public String getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(String couponStartTime) {
        this.couponStartTime = couponStartTime;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getGotTime() {
        return gotTime;
    }

    public void setGotTime(String gotTime) {
        this.gotTime = gotTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }
}
