package com.ziroom.minsu.services.order.entity;

import java.util.Date;
import java.util.List;

import com.ziroom.minsu.valenum.order.OrderPaymentTypeEnum;

import org.springframework.beans.BeanUtils;

import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;

public class FinancePayAndDetailVo {
	
	/**付款单号*/
	private String pvSn;

	/** 订单编号 */
	private String orderSn;
	
	/** 城市code */
	private String cityCode;

	/** 付款单来源 */
	private Integer paySourceType;

	/**
	 * @see OrderPaymentTypeEnum
	 * 付款类型 银行付款
	 */
	private String paymentType;


	/** 收款人uid */
	private String receiveUid;

	/** 收款人类型：1：房东、2：租客 */
	private Integer receiveType;

	/** 付款人uid 从哪个账户划款 */
	private String payUid;
	
	/** 付款人类型 1：房东、2：租客 */
    private Integer payType;

	/** 付款金额 退款金额 分 退给客户需为负的 */
	private Integer totalFee;

	/** 产生费用日期 */
	private Date generateFeeTime;

	/** 执行时间 定时任务执行时间 */
	private Date runTime;

	/** 审核状态 ： 1：待提交 2：提交审核 3：审核驳回 4：审核通过 */
	private Integer AuditStatus;
	
	/** 付款状态*/
	private Integer paymentStatus;

	/** 付款单 详细信息 */
	private List<FinancePayVouchersDetailVo> financePayVouchersDetailList;
	
	/**
	 * 创建人
	 */
	private String createdFid;


	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * 获取对应实体
	 * @author lishaochuan
	 * @create 2016年5月7日上午10:51:56
	 * @return
	 */
	public FinancePayVouchersEntity getFinancePayVouchersEntity() {
		FinancePayVouchersEntity financePayVouchersEntity = new FinancePayVouchersEntity();
		BeanUtils.copyProperties(this, financePayVouchersEntity);
		return financePayVouchersEntity;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getPaySourceType() {
		return paySourceType;
	}

	public void setPaySourceType(Integer paySourceType) {
		this.paySourceType = paySourceType;
	}

	public String getReceiveUid() {
		return receiveUid;
	}

	public void setReceiveUid(String receiveUid) {
		this.receiveUid = receiveUid;
	}

	public Integer getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}

	public String getPayUid() {
		return payUid;
	}

	public void setPayUid(String payUid) {
		this.payUid = payUid;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Date getGenerateFeeTime() {
		return generateFeeTime;
	}

	public void setGenerateFeeTime(Date generateFeeTime) {
		this.generateFeeTime = generateFeeTime;
	}

	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	public Integer getAuditStatus() {
		return AuditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		AuditStatus = auditStatus;
	}

	public List<FinancePayVouchersDetailVo> getFinancePayVouchersDetailList() {
		return financePayVouchersDetailList;
	}

	public void setFinancePayVouchersDetailList(List<FinancePayVouchersDetailVo> financePayVouchersDetailList) {
		this.financePayVouchersDetailList = financePayVouchersDetailList;
	}

	public String getCreatedFid() {
		return createdFid;
	}

	public void setCreatedFid(String createdFid) {
		this.createdFid = createdFid;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPvSn() {
		return pvSn;
	}

	public void setPvSn(String pvSn) {
		this.pvSn = pvSn;
	}
	
	

}
