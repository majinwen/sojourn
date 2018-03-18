package com.ziroom.minsu.report.order.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>订单优惠券统计</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/14.
 * @version 1.0
 * @since 1.0
 */
public class OrderCouponEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 城市code
     */
    private String cityCode;

    /**
     * 订单数量数量
     */
    private Integer orderNum =  0;


    /**
     * 使用优惠券的数量
     */
    private Integer couponNum =  0;


    /**
     * 房租
     */
    private Integer rentalMoney =  0;

    /**
     * 优惠券
     */
    private Integer couponMoney =  0;


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    public Integer getRentalMoney() {
        return rentalMoney;
    }

    public void setRentalMoney(Integer rentalMoney) {
        this.rentalMoney = rentalMoney;
    }

    public Integer getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Integer couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
