package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>OrderFinanceVo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan
 * @version 1.0
 * @since 1.0
 */

public class OrderFinanceVo extends BaseEntity {
    /**
     *
     */
    @FieldMeta(skip = true)
    private static final long serialVersionUID = 1449723418011236882L;

    @FieldMeta(name = "国家", order = 1)
    private String nation = "";

    @FieldMeta(name = "大区", order = 2)
    private String regionName = "";

    @FieldMeta(name = "城市", order = 3)
    private String cityName = "";

    @FieldMeta(skip = true)
    private String cityCode;

    @FieldMeta(name = "订单号", order = 4)
    private String orderSn;

    @FieldMeta(skip = true)
    private String orderStatus;

    @FieldMeta(name = "订单状态", order = 5)
    private String orderStatusName;

    @FieldMeta(skip = true)
    private String payStatus;

    @FieldMeta(name = "是否支付", order = 6)
    private String payStatusName;

    @FieldMeta(name = "日租金金额", order = 7)
    private String dayMoney = "0.00";

    @FieldMeta(name = "总租金", order = 8)
    private String rentalMoney;

    @FieldMeta(name = "房客服务费", order = 9)
    private String userCommMoney;

    @FieldMeta(name = "清洁费", order = 10)
    private String cleanMoney;

    @FieldMeta(name = "押金", order = 11)
    private String depositMoney;

    @FieldMeta(name = "优惠金额", order = 12)
    private String couponMoney;

    @FieldMeta(name = "优惠券码", order = 13)
    private String couponSn = "";

    @FieldMeta(name = "实际支付总金额", order = 14)
    private String needPay;

    @FieldMeta(name = "房东服务费", order = 15)
    private String lanCommMoney;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(String dayMoney) {
        this.dayMoney = dayMoney;
    }

    public String getRentalMoney() {
        return rentalMoney;
    }

    public void setRentalMoney(String rentalMoney) {
        this.rentalMoney = rentalMoney;
    }

    public String getUserCommMoney() {
        return userCommMoney;
    }

    public void setUserCommMoney(String userCommMoney) {
        this.userCommMoney = userCommMoney;
    }

    public String getCleanMoney() {
        return cleanMoney;
    }

    public void setCleanMoney(String cleanMoney) {
        this.cleanMoney = cleanMoney;
    }

    public String getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(String depositMoney) {
        this.depositMoney = depositMoney;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public String getNeedPay() {
        return needPay;
    }

    public void setNeedPay(String needPay) {
        this.needPay = needPay;
    }

    public String getLanCommMoney() {
        return lanCommMoney;
    }

    public void setLanCommMoney(String lanCommMoney) {
        this.lanCommMoney = lanCommMoney;
    }
}

