package com.zra.cardCoupon.entity;

import java.math.BigDecimal;

import com.zra.common.enums.BillTypeEnum;
import com.zra.common.enums.CardCouponTypeEnum;
import com.zra.common.utils.KeyGenUtils;

public class CardCouponUsageEntity {
	private String couponCode; //优惠券编码
	private String uid; //用户标识
	private String contractCode; //合同号
	private String mobile; //手机号
	private Integer usageState; //使用状态1：消费成功；2：已回退；3：回退失败 4：保存态；5：消费失败
	private Integer type; //类型：1：租金卡；2：优惠券；
	private BigDecimal payAmount; //实际支付金额
	private Integer isDel;
	private String rentCardCode; // 租金卡编码
	private String usageId;
	private String receiBillFid; // 应收账单标识(comeSource=1)或者是收款单id(comeSource=2)
	
	//add by tianxf9 兼容首次收款
	private Integer comeSource;//1:app;2:合同首次收款

	
	public CardCouponUsageEntity() {
		
	}
	
	public CardCouponUsageEntity(String uid, String billFid, String code, Integer billType, Integer usageState,Integer comeSource) {

		this.usageId = KeyGenUtils.genKey();
		this.receiBillFid = billFid;
		if (BillTypeEnum.CONTRACT_PLAN_COST.getIndex() == billType.intValue()) {
			this.rentCardCode = code;
			this.type = CardCouponTypeEnum.CARD.getIndex();
		} else {
			this.couponCode = code;
			this.type = CardCouponTypeEnum.COUPON.getIndex();
		}
		this.uid = uid;
		this.usageState = usageState;
		this.comeSource = comeSource;
		this.isDel = 0;

	}

	public String getUsageId() {
		return usageId;
	}

	public void setUsageId(String usageId) {
		this.usageId = usageId;
	}

	public String getRentCardCode() {
		return rentCardCode;
	}

	public void setRentCardCode(String rentCardCode) {
		this.rentCardCode = rentCardCode;
	}

	public Integer getUsageState() {
		return usageState;
	}

	public void setUsageState(Integer usageState) {
		this.usageState = usageState;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setReceiBillFid(String receiBillFid) {

		this.receiBillFid = receiBillFid;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public String getUid() {
		return uid;
	}

	public String getContractCode() {
		return contractCode;
	}

	public String getMobile() {
		return mobile;
	}

	public String getReceiBillFid() {
		return receiBillFid;
	}

	public Integer getComeSource() {
		return comeSource;
	}

	public void setComeSource(Integer comeSource) {
		this.comeSource = comeSource;
	}
	
}