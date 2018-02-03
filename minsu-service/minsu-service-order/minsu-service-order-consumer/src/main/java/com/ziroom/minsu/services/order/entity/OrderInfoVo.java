package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.order.FinancePenaltyEntity;

import java.util.Date;
import java.util.List;

/**
 * <p>订单的主表 + 房源+ 金额 </p>
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
public class OrderInfoVo  extends OrderHouseVo {


    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 7960984319207898542L;

	/** 总金额 所有费用之和 */
    private Integer sumMoney;

    /** 优惠券金额 分 */
    private Integer couponMoney;

    /** 押金 */
    private Integer depositMoney;

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

    /** 实际消费金额 订单结算之后实际发生的消费金额 （违约金 + 额外消费 + 房租） */
    private Integer realMoney;

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

    /**房东的佣金*/
    private Integer lanCommMoney;

    /**用户的佣金*/
    private Integer userCommMoney;

    /** 房东的实际佣金金额 */
    private Integer realLanMoney;

    /** 用户的实际佣金金额 */
    private Integer realUserMoney;

    /** 地推管家员工号*/
    private String empPushCode;

    /** 地推管家姓名*/
    private String empPushName;

    /** 维护管家员工号*/
    private String empGuardCode;

    /** 维护管家姓名*/
    private String empGuardName;
    
    /** 房租 分 此值下单固定不在改变 */
    private Integer rentalMoneyAll;
    
    /**押金 分 此值下单固定不在改变 */
    private Integer depositMoneyAll;
    
    /**优惠券金额 分 此值下单固定不在改变 */
    private Integer couponMoneyAll;
    /**活动总金额 分*/
    private Integer actMoney;
    /**活动总金额 分 此值下单后固定不变*/
    private Integer actMoneyAll;
    
    /**房东评价上线时间*/
    private Date landEvalTime;
    
    /**房客评价上线时间 */
    private Date tenantEvalTime;
    
    /**房东评价审核状态 */
    private Integer landEvaStatu;
    
    /**房客评价审核状态 */
    private Integer tenantEvaStatu;
    /**房东回复审核状态*/
    private Integer lanReplStatu;
    
    /**房东回复时间*/
    private Date landReplyTime;
    
    /**房东评价是否可展示 */
    private Boolean landEvaIsShow = false;
    
    /**房客评价是否可展示 */
    private Boolean tenantEvaIsShow = false;
    
    /**房东回复是否可展示 */
    private Boolean landReplyIsShow = false;
    
    /**国家码*/
    private String countryCode;
    
    /**房东fid*/
    private String landlordUid;
    
    /**
     * 房东罚款单
     */
    private FinancePenaltyEntity financePenalty;
    
    /**
     * 订单被罚详情
     */
    private List<FinancePenaltyPayRelVo> listFinancePenaltyPayRelVo;
    


	/**
	 * @return the listFinancePenaltyPayRelVo
	 */
	public List<FinancePenaltyPayRelVo> getListFinancePenaltyPayRelVo() {
		return listFinancePenaltyPayRelVo;
	}

	/**
	 * @param listFinancePenaltyPayRelVo the listFinancePenaltyPayRelVo to set
	 */
	public void setListFinancePenaltyPayRelVo(
			List<FinancePenaltyPayRelVo> listFinancePenaltyPayRelVo) {
		this.listFinancePenaltyPayRelVo = listFinancePenaltyPayRelVo;
	}

	/**
	 * @return the financePenalty
	 */
	public FinancePenaltyEntity getFinancePenalty() {
		return financePenalty;
	}

	/**
	 * @param financePenalty the financePenalty to set
	 */
	public void setFinancePenalty(FinancePenaltyEntity financePenalty) {
		this.financePenalty = financePenalty;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Boolean getLandEvaIsShow() {
		return landEvaIsShow;
	}

	public Boolean getTenantEvaIsShow() {
		return tenantEvaIsShow;
	}

	public Boolean getLandReplyIsShow() {
		return landReplyIsShow;
	}

	public void setLandEvaIsShow(Boolean landEvaIsShow) {
		this.landEvaIsShow = landEvaIsShow;
	}

	public void setTenantEvaIsShow(Boolean tenantEvaIsShow) {
		this.tenantEvaIsShow = tenantEvaIsShow;
	}

	public void setLandReplyIsShow(Boolean landReplyIsShow) {
		this.landReplyIsShow = landReplyIsShow;
	}

	public Date getLandReplyTime() {
		return landReplyTime;
	}

	public void setLandReplyTime(Date landReplyTime) {
		this.landReplyTime = landReplyTime;
	}

	public Date getLandEvalTime() {
		return landEvalTime;
	}

	public Date getTenantEvalTime() {
		return tenantEvalTime;
	}

	public void setLandEvalTime(Date landEvalTime) {
		this.landEvalTime = landEvalTime;
	}

	public void setTenantEvalTime(Date tenantEvalTime) {
		this.tenantEvalTime = tenantEvalTime;
	}
	
	public Integer getLandEvaStatu() {
		return landEvaStatu;
	}

	public Integer getTenantEvaStatu() {
		return tenantEvaStatu;
	}

	public void setLandEvaStatu(Integer landEvaStatu) {
		this.landEvaStatu = landEvaStatu;
	}

	public void setTenantEvaStatu(Integer tenantEvaStatu) {
		this.tenantEvaStatu = tenantEvaStatu;
	}

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

	public String getEmpPushCode() {
        return empPushCode;
    }

    public void setEmpPushCode(String empPushCode) {
        this.empPushCode = empPushCode;
    }

    public String getEmpPushName() {
        return empPushName;
    }

    public void setEmpPushName(String empPushName) {
        this.empPushName = empPushName;
    }

    public String getEmpGuardCode() {
        return empGuardCode;
    }

    public void setEmpGuardCode(String empGuardCode) {
        this.empGuardCode = empGuardCode;
    }

    public String getEmpGuardName() {
        return empGuardName;
    }

    public void setEmpGuardName(String empGuardName) {
        this.empGuardName = empGuardName;
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

    public Integer getLanReplStatu() {
        return lanReplStatu;
    }

    public void setLanReplStatu(Integer lanReplStatu) {
        this.lanReplStatu = lanReplStatu;
    }

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
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

    /**
     * 优惠金额
     * 优惠券+活动优惠
     */
    private Integer reduceMoney;

    public Integer getReduceMoney() {
        if (Check.NuNObj(couponMoneyAll)) {
            couponMoneyAll = 0;
        }
        if (Check.NuNObj(actMoneyAll)) {
            actMoneyAll = 0;
        }
        reduceMoney = couponMoneyAll + actMoneyAll;
        return reduceMoney;
    }

}
