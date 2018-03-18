package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>订单数量统计</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/19.
 * @version 1.0
 * @since 1.0
 */
public class OrderCouponVo extends BaseEntity{

    @FieldMeta(skip = true)
    private static final long serialVersionUID = 1L;

    /**
     * 区域
     */
    @FieldMeta(name="区域",order=10)
    private String regionName;

    /**
     * 城市code
     */
    @FieldMeta(name="城市code",order=10)
    private String  cityCode;

    /**
     * 城市名称
     */
    @FieldMeta(name="城市名称",order=10)
    private String  cityName;


    /**
     * 订单实际支付房租
     */
    @FieldMeta(name="订单实际支付房租",order=10)
    private Double realMoney = 0.0;

    /**
     * 订单应付租金
     */
    @FieldMeta(name="订单应付租金",order=10)
    private Double rentalMoney = 0.0;

    /**
     * 订单优惠金额
     */
    @FieldMeta(name="订单优惠金额",order=10)
    private Double couponMoney = 0.0;

    /**
     * 平均优惠
     */
    @FieldMeta(name="平均优惠",order=10)
    private Double couponMoneyAvg = 0.0;


    /**
     * 优惠金额占比
     */
    @FieldMeta(name="优惠金额占比%",order=10)
    private Double couponMoneyRate = 0.0;


    /**
     * 优惠订单数量
     */
    @FieldMeta(name="优惠订单数量",order=10)
    private Integer orderCouponNum = 0;


    /**
     * 全部订单数量
     */
    @FieldMeta(name="全部订单数量",order=10)
    private Integer orderNum = 0;

    /**
     * 优惠订单占比
     */
    @FieldMeta(name="优惠订单占比%",order=10)
    private Double couponNumRate = 0.0;


    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Double realMoney) {
        this.realMoney = realMoney;
    }

    public Double getRentalMoney() {
        return rentalMoney;
    }

    public void setRentalMoney(Double rentalMoney) {
        this.rentalMoney = rentalMoney;
    }

    public Double getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Double couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Double getCouponMoneyAvg() {
        return couponMoneyAvg;
    }

    public void setCouponMoneyAvg(Double couponMoneyAvg) {
        this.couponMoneyAvg = couponMoneyAvg;
    }

    public Double getCouponMoneyRate() {
        return couponMoneyRate;
    }

    public void setCouponMoneyRate(Double couponMoneyRate) {
        this.couponMoneyRate = couponMoneyRate;
    }

    public Integer getOrderCouponNum() {
        return orderCouponNum;
    }

    public void setOrderCouponNum(Integer orderCouponNum) {
        this.orderCouponNum = orderCouponNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Double getCouponNumRate() {
        return couponNumRate;
    }

    public void setCouponNumRate(Double couponNumRate) {
        this.couponNumRate = couponNumRate;
    }
}
