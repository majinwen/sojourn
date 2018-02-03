package com.ziroom.minsu.services.finance.dto;

/**
 * <p>
 * 用户提现生成付款单入参
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月23日
 * @since 1.0
 * @version 1.0
 */
public class PayVouchersCreateRequest {

	/** 提现人uid */
	String userId;

	/** 提现金额 */
	Integer totalFee;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

}
