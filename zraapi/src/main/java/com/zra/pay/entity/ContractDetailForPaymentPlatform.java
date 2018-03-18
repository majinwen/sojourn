package com.zra.pay.entity;

import java.math.BigDecimal;

/**
 * @Author: wangxm113
 * @CreateDate: 2016年5月5日
 */
public class ContractDetailForPaymentPlatform {
	private Integer orderType;// 支付种类
	private String cardNo;// 客户卡号
	private BigDecimal amount;// 支付金额

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
