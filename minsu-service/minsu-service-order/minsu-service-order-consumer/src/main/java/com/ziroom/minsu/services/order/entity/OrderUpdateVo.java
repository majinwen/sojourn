package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;
import java.util.Date;


/**
 * <p>保存订单需要的信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class OrderUpdateVo extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -86542226309673L;

	/** 订单编号 */
	private String orderSn;

    /** 总金额 所有费用之和 */
    private Integer sumMoney;

    /** 优惠券金额 分 */
    private Integer couponMoney;

    /** 折扣金额 */
    private Integer discountMoney;

    /** 应付金额 */
    private Integer needPay;

    /** 实际消费金额 订单结算之后实际发生的消费金额 （违约金 + 额外消费 + 房租） */
    private Integer realMoney;

    /** 实际支付金额 */
    private Integer payMoney;

    /** 房东发起的用户的额外消费 */
    private Integer otherMoney;

    /** 违约金 */
    private Integer penaltyMoney;

    /** 房租 */
    private Integer rentalMoney;

    /** 退款金额 */
    private Integer refundMoney;

    /** 订单状态 */
    private Integer orderStatus;

    /** 支付状态 */
    private Integer payStatus;

    /** 付款单状态 */
    private Integer accountsStatus;


    /** 订单的实际结束时间 */
    private Date realEndTime;

    /** 更新热 */
    private  String userFid;


    /** 是否删除 0：否，1：是 */
    private Integer isDel;


    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getAccountsStatus() {
        return accountsStatus;
    }

    public void setAccountsStatus(Integer accountsStatus) {
        this.accountsStatus = accountsStatus;
    }

    public String getUserFid() {
        return userFid;
    }

    public void setUserFid(String userFid) {
        this.userFid = userFid;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Integer sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Integer getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Integer couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getNeedPay() {
        return needPay;
    }

    public void setNeedPay(Integer needPay) {
        this.needPay = needPay;
    }

    public Integer getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Integer realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getOtherMoney() {
        return otherMoney;
    }

    public void setOtherMoney(Integer otherMoney) {
        this.otherMoney = otherMoney;
    }

    public Integer getPenaltyMoney() {
        return penaltyMoney;
    }

    public void setPenaltyMoney(Integer penaltyMoney) {
        this.penaltyMoney = penaltyMoney;
    }

    public Integer getRentalMoney() {
        return rentalMoney;
    }

    public void setRentalMoney(Integer rentalMoney) {
        this.rentalMoney = rentalMoney;
    }

    public Integer getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Integer refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
