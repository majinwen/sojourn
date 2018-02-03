package com.ziroom.minsu.services.order.entity;

import java.io.Serializable;

public class PayVo implements Serializable{

	private static final long serialVersionUID = -7287329071716995996L;

	/** 外部支付：1 包括支付宝、微信等
	 * 内部支付：90账户余额支付(余额转冻结) 91积分卡支付 92员工卡支付 93代金券  94账户余额支付(余额消费) */
	private Integer orderType;

	/** 支付金额 */
	private Integer payMoney;

	/** 客户积分卡号，多个卡号，请使用半角逗号（,）分割 ，非必填*/
	private String cardNo;

	
    public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}
