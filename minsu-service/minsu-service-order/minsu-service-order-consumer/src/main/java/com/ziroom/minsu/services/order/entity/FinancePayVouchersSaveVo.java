package com.ziroom.minsu.services.order.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;

public class FinancePayVouchersSaveVo extends BaseEntity {

	// 序列化id
	private static final long serialVersionUID = 6406653832765055765L;

	// 付款单号
	String pvSn;

	// 房东佣金 = 折扣后租金*房东佣金费率
	Integer landlordCommission;

	// 租客佣金 = 折扣后租金*租客佣金费率
	Integer userCommission;

	// 总金额
	Integer totalFee;

	// 产生费用日期
	Date generateFeeTime;

	// 执行时间：住房时间+2天
	Date runTime;
	
	// 付款单来源
	Integer paySourceType;

	// 付款单表
	FinancePayVouchersEntity payVouchersEntity = new FinancePayVouchersEntity();

	// 付款单明细
	List<FinancePayVouchersDetailEntity> financePayVouchersDetailEntityList = new ArrayList<FinancePayVouchersDetailEntity>();

	// 收入表
	List<FinanceIncomeEntity> financeIncomeEntityList = new ArrayList<FinanceIncomeEntity>();

	public String getPvSn() {
		return pvSn;
	}

	public void setPvSn(String pvSn) {
		this.pvSn = pvSn;
	}

	public Integer getLandlordCommission() {
		return landlordCommission;
	}

	public void setLandlordCommission(Integer landlordCommission) {
		this.landlordCommission = landlordCommission;
	}

	public Integer getUserCommission() {
		return userCommission;
	}

	public void setUserCommission(Integer userCommission) {
		this.userCommission = userCommission;
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

	public FinancePayVouchersEntity getPayVouchersEntity() {
		return payVouchersEntity;
	}

	public void setPayVouchersEntity(FinancePayVouchersEntity payVouchersEntity) {
		this.payVouchersEntity = payVouchersEntity;
	}

	public List<FinancePayVouchersDetailEntity> getFinancePayVouchersDetailEntityList() {
		return financePayVouchersDetailEntityList;
	}

	public void setFinancePayVouchersDetailEntityList(List<FinancePayVouchersDetailEntity> financePayVouchersDetailEntityList) {
		this.financePayVouchersDetailEntityList = financePayVouchersDetailEntityList;
	}

	public List<FinanceIncomeEntity> getFinanceIncomeEntityList() {
		return financeIncomeEntityList;
	}

	public void setFinanceIncomeEntityList(List<FinanceIncomeEntity> financeIncomeEntityList) {
		this.financeIncomeEntityList = financeIncomeEntityList;
	}

	public Integer getPaySourceType() {
		return paySourceType;
	}

	public void setPaySourceType(Integer paySourceType) {
		this.paySourceType = paySourceType;
	}

	
}
