package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.BigDecimalUtil;

/**
 * <p>
 * 订单配置快照信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月19日
 * @since 1.0
 * @version 1.0
 */
public class OrderConfigVo extends BaseEntity {

	/** 序列化id */
	private static final long serialVersionUID = -436613190803185597L;

	/** 折扣比率 */
	private Double discountRate = 0.00;
	
	/** 结算方式 */
	private Integer checkType;

	/** 租客佣金比率 */
	private Double commissionRateUser;
	
	/** 房东佣金比率 */
	private Double commissionRateLandlord;

	/** 房东佣金折扣比率 */
	private Double commissionDiscountRateLandlord = 0.00;

    /** 房东享受的免佣金的活动 */
    private String landCom ;

	/** 是否是长租 */
	private boolean changzuFlag = false ;

	/**
	 * 房东佣金总优惠率
	 * @author lishaochuan
	 * @create 2016年4月26日
	 * @return 房东佣金比率*房东佣金折扣比率
	 */
	public Double returnRateLandlord(){
		return BigDecimalUtil.mul(commissionRateLandlord, BigDecimalUtil.sub(1,commissionDiscountRateLandlord));
	}

	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	public Double getCommissionRateLandlord() {
		return commissionRateLandlord;
	}

	public void setCommissionRateLandlord(Double commissionRateLandlord) {
		this.commissionRateLandlord = commissionRateLandlord;
	}

	public Double getCommissionDiscountRateLandlord() {
		return commissionDiscountRateLandlord;
	}

	public void setCommissionDiscountRateLandlord(Double commissionDiscountRateLandlord) {
		this.commissionDiscountRateLandlord = commissionDiscountRateLandlord;
	}

	public Double getCommissionRateUser() {
		return commissionRateUser;
	}

	public void setCommissionRateUser(Double commissionRateUser) {
		this.commissionRateUser = commissionRateUser;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

    public String getLandCom() {
        return landCom;
    }

    public void setLandCom(String landCom) {
        this.landCom = landCom;
    }

	public boolean isChangzuFlag() {
		return changzuFlag;
	}

	public void setChangzuFlag(boolean changzuFlag) {
		this.changzuFlag = changzuFlag;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			System.out.println(i);
		}
	}
}
