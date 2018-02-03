package com.ziroom.minsu.services.account.dto;

import java.util.Date;

/**
 * <p>
 * 充值冻结金额请求接口
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */

public class FillMoneyRequest extends AccountCommonRequest{
	
	/** 订单编号 */
	private String orderSn;
	
	/** 客户类型 */
	private String uidType;
	
	/** 支付流水号  保证唯一 */
	private String tradeNo;
	
	/** 支付总金额单位分  */
	private Integer totalFee;
	
	/** 收款充值传1  退租充传2  */
	private Integer business_type;
	
	/** 支付方式  */
	private String payType;
	
	/** 充值类型  优惠券 还是 现金  */
	private Integer fillType;
	
	/** 充值金额类型  充值冻结 还是 充值余额  */
	private Integer fillMoneyType;
	
	/** 充值结果  */
	private Integer fillStatus;
	
	/** 支付时间  */
	private Date payTime;
	
	/** 描述  */
	private String description;
	
	/** 充值类型 区别是否是虚拟充值  */
	private String biz_common_type;
	
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getUidType() {
		return uidType;
	}

	public void setUidType(String uidType) {
		this.uidType = uidType;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFillType() {
		return fillType;
	}

	public void setFillType(Integer fillType) {
		this.fillType = fillType;
	}

	public Integer getFillMoneyType() {
		return fillMoneyType;
	}

	public void setFillMoneyType(Integer fillMoneyType) {
		this.fillMoneyType = fillMoneyType;
	}

	public Integer getFillStatus() {
		return fillStatus;
	}

	public void setFillStatus(Integer fillStatus) {
		this.fillStatus = fillStatus;
	}

	public Integer getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Integer business_type) {
		this.business_type = business_type;
	}

	public String getBiz_common_type() {
		return biz_common_type;
	}

	public void setBiz_common_type(String biz_common_type) {
		this.biz_common_type = biz_common_type;
	}
	
	
}
