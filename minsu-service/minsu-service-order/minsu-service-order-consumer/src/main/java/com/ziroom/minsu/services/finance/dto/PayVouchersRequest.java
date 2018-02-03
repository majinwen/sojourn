/**
 * @FileName: PayVouchersRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author liyingjie
 * @created 2016年4月2日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.finance.dto;

import java.util.Date;
import java.util.List;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
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
public class PayVouchersRequest {
	
    /** 收款人类型：1：房东、2：租客 */
    private int receiveType;
    
    /** 收款明细  */
    private List<FinancePayVouchersDetailEntity> detailList;
	
    /** 付款单号（业务关联id） 付款单号  订单_标识_序号 */
    private String pvSn;

    /** 审核状态 ： 1：待提交  2：提交审核  3：审核驳回  4：审核通过 */
    private int auditStatus;
    
    /** 付款状态：1：未付款 2：已付款 3：提前退房取消 4：银行账户错误取消 */
    private int paymentStatus;
    
    /** 创建人  */
    private String creator;
    
    /** 订单编号 */
    private String orderSn;
    
    /** 收款人  银行名称  */
    private String customerBankName;
    
    /** 收款人  银行开户名  */
    private String customerAccountName;
    
    /** 收款人  银行卡号  */
    private String customerAccountNo;
    
    /** 实际付款时间  传提现当天  */
    private Date payTime;
    
    /** 收款人uid 如房东id，客户的id */
    private String receiveUid;
    
    /** 客户名称 */
    private String customerName;
    
    /** 客户手机号 */
    private String customerPhone;
    
    /**
     * 付款类型
     * 提现：yhfk
     * 退款：ylfh
     */
    private String paymentTypeCode;
    
    
    //以下是原路返回参数
    
    /**
     * 付款时候的支付方式（微信：42），（原路返回时，必填）
     */
    private String channal;

    /**
     * 付款时候的业务系统订单号，（原路返回时，必填）
     */
    private String bizCode;

    /**
     * 付款时候的商户单号，（原路返回时，必填）
     */
    private String outTradeNo;

    /**
     * 付款时候的微信订单号，（非必填）
     */
    private String transactionId;

    /**
     * 付款时候的支付订单号，（原路返回时，必填）
     */
    private String payorderCodeOrigin;

    /**
     * 用户付款的总金额，（原路返回时，必填）
     */
    private String payTotalFee;
    

    public int getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(int receiveType) {
		this.receiveType = receiveType;
	}

	public List<FinancePayVouchersDetailEntity> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<FinancePayVouchersDetailEntity> detailList) {
		this.detailList = detailList;
	}

	public String getPvSn() {
		return pvSn;
	}

	public void setPvSn(String pvSn) {
		this.pvSn = pvSn;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getCustomerBankName() {
		return customerBankName;
	}

	public void setCustomerBankName(String customerBankName) {
		this.customerBankName = customerBankName;
	}

	public String getCustomerAccountName() {
		return customerAccountName;
	}

	public void setCustomerAccountName(String customerAccountName) {
		this.customerAccountName = customerAccountName;
	}

	public String getCustomerAccountNo() {
		return customerAccountNo;
	}

	public void setCustomerAccountNo(String customerAccountNo) {
		this.customerAccountNo = customerAccountNo;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getReceiveUid() {
		return receiveUid;
	}

	public void setReceiveUid(String receiveUid) {
		this.receiveUid = receiveUid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getPaymentTypeCode() {
		return paymentTypeCode;
	}

	public void setPaymentTypeCode(String paymentTypeCode) {
		this.paymentTypeCode = paymentTypeCode;
	}

	public String getChannal() {
		return channal;
	}

	public void setChannal(String channal) {
		this.channal = channal;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPayorderCodeOrigin() {
		return payorderCodeOrigin;
	}

	public void setPayorderCodeOrigin(String payorderCodeOrigin) {
		this.payorderCodeOrigin = payorderCodeOrigin;
	}

	public String getPayTotalFee() {
		return payTotalFee;
	}

	public void setPayTotalFee(String payTotalFee) {
		this.payTotalFee = payTotalFee;
	}

	
}
