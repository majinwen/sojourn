package com.ziroom.minsu.services.order.entity;


import com.ziroom.minsu.valenum.traderules.CheckOutStrategy;

/**
 * <p>取消订单的策略</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public class CancleStrategyVo extends CheckOutStrategy {

    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2145256446342555707L;

    /**
     * 是否支付
     */
    private Boolean hasPay;

    /** 已经支付金额 */
    private Integer hasPayMoney;


    /** 使用的优惠券 */
    private Integer couponMoney;

    /** 违约金 */
    private Integer penaltyMoney;


    /** 佣金 */
    private Integer userComm;

    public Integer getUserComm() {
        return userComm;
    }

    public void setUserComm(Integer userComm) {
        this.userComm = userComm;
    }

    public Integer getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Integer couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Boolean getHasPay() {
        return hasPay;
    }

    public void setHasPay(Boolean hasPay) {
        this.hasPay = hasPay;
    }

    public Integer getHasPayMoney() {
        return hasPayMoney;
    }

    public void setHasPayMoney(Integer hasPayMoney) {
        this.hasPayMoney = hasPayMoney;
    }

    public Integer getPenaltyMoney() {
        return penaltyMoney;
    }

    public void setPenaltyMoney(Integer penaltyMoney) {
        this.penaltyMoney = penaltyMoney;
    }
}
