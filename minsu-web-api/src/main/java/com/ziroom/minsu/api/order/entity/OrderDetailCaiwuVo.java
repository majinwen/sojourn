package com.ziroom.minsu.api.order.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>给财务的返回结果</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/22.
 * @version 1.0
 * @since 1.0
 */
public class OrderDetailCaiwuVo extends BaseEntity{

    private static final long serialVersionUID = 30968412389846703L;

    /** 城市code */
    private String cityCode;
    /** 城市名称 */
    private String cityName;
    /** 订单号 */
    private String orderSn;
    /** 0待确认20:待入住30:强制取消31:房东已拒绝32:房客取消33:未支付超时取消34:房东强制取消申请中35:房东未确认超时取消40:已入住未生成单据41:已入住已生成单据50:正常退房中51:提前退房中60:待用户确认额外消费61:提前退房待用户确认额外消费70:正常退房完成71:提前退房完成 */
    private Integer orderStatus;
    /** 订单状态 */
    private String orderStatusName;
    /** 下单手机号 */
    private String userTel;
    /** 客户uid */
    private String userUid;
    /** 支付状态 0：未支付 1：已支付 */
    private Integer payStatus;
    /**   支付类型 1.银联支付 2.支付宝 41.微信IOS支付 42.微信Android支付 */
    private Integer payType;
    /** 订单类型 */
    private Integer orderType;
    /** 订单类型 */
    private String orderTypeName;
    /** 应付金额 */
    private Double allMoney;
    /** 实付金额 */
    private Double payMoney;
    /** 优惠券金额 */
    private Double couponMoney;
    /** 折扣金额 */
    private Double discountMoney=0.0;


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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public Double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(Double allMoney) {
        this.allMoney = allMoney;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Double couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }
}
