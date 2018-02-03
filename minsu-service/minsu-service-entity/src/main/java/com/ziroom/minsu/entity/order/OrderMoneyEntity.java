package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>订单的金额</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/8.
 * @version 1.0
 * @since 1.0
 */
public class OrderMoneyEntity extends BaseEntity {


    /** 序列化id  */
    private static final long serialVersionUID = 496423458112315698L;

    private String fid;

    /** 订单编号 */
    private String orderSn;

    /** 总金额 所有费用之和 */
    private Integer sumMoney;

    /** 优惠券金额 分 */
    private Integer couponMoney ;

    /** 押金 */
    private Integer depositMoney ;

    /** 保险金额  */
    private Integer insuranceMoney;

    /** 保险类型 */
    private Integer insuranceType;

    /** 折扣金额 */
    private Integer discountMoney;

    /** 清洁费 */
    private Integer cleanMoney;

    /** 应付金额 */
    private Integer needPay;

    /** 实际消费金额 订单结算之后实际发生的消费金额
     *  特殊说明，只有在用户确认额外消费之后才能生效
     * （违约金 + 额外消费 + 房租） */
    private Integer realMoney ;

    /** 实际支付金额 */
    private Integer payMoney;

    /** 房东发起的用户的额外消费 */
    private Integer otherMoney;

    /** 违约金 */
    private Integer penaltyMoney;

    /** 罚款金 */
    private Integer punishMoney;

    /** 房租 */
    private Integer rentalMoney;

    /** 退款金额 */
    private Integer refundMoney;

    /** 房东的佣金金额 */
    private Integer lanCommMoney;

    /** 用户的佣金金额 */
    private Integer userCommMoney;


    /** 房东的实际佣金金额 */
    private Integer realLanMoney;


    /** 用户的实际佣金金额 */
    private Integer realUserMoney;
    
    /** 房租 分 此值下单固定不在改变 */
    private Integer rentalMoneyAll;
    
    /**押金 分 此值下单固定不在改变 */
    private Integer depositMoneyAll;
    
    /**优惠券金额 分 此值下单固定不在改变 */
    private Integer couponMoneyAll;
    /** 活动金额 */
    private Integer actMoney;
    /** 活动金额 分 此值下单固定不在改变*/
    private Integer actMoneyAll;
    
    /**
	 * @return the rentalMoneyAll
	 */
	public Integer getRentalMoneyAll() {
		return rentalMoneyAll;
	}

	/**
	 * @param rentalMoneyAll the rentalMoneyAll to set
	 */
	public void setRentalMoneyAll(Integer rentalMoneyAll) {
		this.rentalMoneyAll = rentalMoneyAll;
	}

	/**
	 * @return the depositMoneyAll
	 */
	public Integer getDepositMoneyAll() {
		return depositMoneyAll;
	}

	/**
	 * @param depositMoneyAll the depositMoneyAll to set
	 */
	public void setDepositMoneyAll(Integer depositMoneyAll) {
		this.depositMoneyAll = depositMoneyAll;
	}

	/**
	 * @return the couponMoneyAll
	 */
	public Integer getCouponMoneyAll() {
		return couponMoneyAll;
	}

	/**
	 * @param couponMoneyAll the couponMoneyAll to set
	 */
	public void setCouponMoneyAll(Integer couponMoneyAll) {
		this.couponMoneyAll = couponMoneyAll;
	}

	public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
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

    public Integer getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(Integer depositMoney) {
        this.depositMoney = depositMoney;
    }

    public Integer getInsuranceMoney() {
        return insuranceMoney;
    }

    public void setInsuranceMoney(Integer insuranceMoney) {
        this.insuranceMoney = insuranceMoney;
    }

    public Integer getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(Integer insuranceType) {
        this.insuranceType = insuranceType;
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

	public Integer getLanCommMoney() {
		return lanCommMoney;
	}

	public void setLanCommMoney(Integer lanCommMoney) {
		this.lanCommMoney = lanCommMoney;
	}

	public Integer getUserCommMoney() {
		return userCommMoney;
	}

	public void setUserCommMoney(Integer userCommMoney) {
		this.userCommMoney = userCommMoney;
	}

    public Integer getRealLanMoney() {
        return realLanMoney;
    }

    public void setRealLanMoney(Integer realLanMoney) {
        this.realLanMoney = realLanMoney;
    }

    public Integer getRealUserMoney() {
        return realUserMoney;
    }

    public void setRealUserMoney(Integer realUserMoney) {
        this.realUserMoney = realUserMoney;
    }

    public Integer getPunishMoney() {
        return punishMoney;
    }

    public void setPunishMoney(Integer punishMoney) {
        this.punishMoney = punishMoney;
    }


    public Integer getCleanMoney() {
        return cleanMoney;
    }

    public void setCleanMoney(Integer cleanMoney) {
        this.cleanMoney = cleanMoney;
    }

    public Integer getActMoney() {
        return actMoney;
    }

    public void setActMoney(Integer actMoney) {
        this.actMoney = actMoney;
    }

    public Integer getActMoneyAll() {
        return actMoneyAll;
    }

    public void setActMoneyAll(Integer actMoneyAll) {
        this.actMoneyAll = actMoneyAll;
    }
}
