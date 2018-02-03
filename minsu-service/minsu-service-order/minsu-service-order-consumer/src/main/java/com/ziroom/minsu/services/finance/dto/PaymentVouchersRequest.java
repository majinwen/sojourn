/**
 * @FileName: PaymentVouchersRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author liyingjie
 * @created 2016年4月28日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.finance.dto;

import java.util.List;


/**
 * <p>
 * 财务系统 付款单接口
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
public class PaymentVouchersRequest  extends BillOrderRequest{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4284161567079666032L;

	/** 关联号  */
    private String paymentSn;
    
    /** 收款单来源  */
    private String sourceType;
    
    /** 收款单类型  */
    private String paymentType;
    /**支付类型*/
    private String payType;
    
    /**支付流水号*/
    private String tradeNo;
	/**实际同步开始时间 */
	private String actualStartTime;
	
	/**实际同步结束时间 */
	private String actualEndTime;

	/**同步 状态 1：未同步 2：同步成功 3：同步失败*/
    private Integer syncStatus;
    
	/**
	 * 角色类型
	 */
	private Integer roleType;
	
	/**
	 * 房源fid集合
	 */
	private List<String> houseFids;
    
	/**
	 * @return the roleType
	 */
	public Integer getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the houseFids
	 */
	public List<String> getHouseFids() {
		return houseFids;
	}

	/**
	 * @param houseFids the houseFids to set
	 */
	public void setHouseFids(List<String> houseFids) {
		this.houseFids = houseFids;
	}

	public String getPaymentSn() {
		return paymentSn;
	}

	public void setPaymentSn(String paymentSn) {
		this.paymentSn = paymentSn;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public String getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}
    
  
}
