/**
 * @FileName: TenantOrderVo.java
 * @Package com.ziroom.minsu.report.customer.vo
 * 
 * @author zl
 * @created 2017年5月8日 上午10:26:47
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.report.customer.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>房客订单信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class TenantOrderVo extends BaseEntity {

	/**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 6423568549469705399L;
	
	/**
	 * 预订人UID
	 */
	@FieldMeta(name="预定人UID",order=1)
	private String bookUid;	
	/**
	 * 咨询量
	 */
	@FieldMeta(name="咨询量",order=2)
	private Integer adMsgNum;
	/**
	 * 申请订单量
	 */
	@FieldMeta(name="申请订单量",order=3)
	private Integer bookOrderNum;
	/**
	 * 被拒绝订单量
	 */
	@FieldMeta(name="被拒绝订单量",order=4)
	private Integer refBookOrderNum;
	/**
	 * 支付订单量
	 */
	@FieldMeta(name="支付订单量",order=5)
	private Integer paidOrderNum;
	/**
	 * 入住订单量
	 */
	@FieldMeta(name="入住订单量",order=6)
	private Integer checkInOrderNum;
	/**
	 * 入住间夜量
	 */
	@FieldMeta(name="入住间夜量",order=7)
	private Integer checkInDaysNum;
	/**
	 * 累计房租费用
	 */
	@FieldMeta(name="累计房租费用(元)",order=8)
	private Integer paidMoney;
	/**
	 * 给出评价量
	 */
	@FieldMeta(name="给出评价量",order=9)
	private Integer evaluateNum;
	/**
	 * 获得评价量
	 */
	@FieldMeta(name="获得评价量",order=10)
	private Integer evaluatedNum;
	/**
	 * 累计给出评分平均分
	 */
	@FieldMeta(name="累计给出评分平均分",order=11)
	private Double evaluateAvgScore;
	/**
	 * 累计获得评分平均分
	 */
	@FieldMeta(name="累计获得评分平均分",order=12)
	private Double isEvaluatedAvgScore;
	public String getBookUid() {
		return bookUid;
	}
	public Integer getAdMsgNum() {
		return adMsgNum;
	}
	public Integer getBookOrderNum() {
		return bookOrderNum;
	}
	public Integer getRefBookOrderNum() {
		return refBookOrderNum;
	}
	public Integer getPaidOrderNum() {
		return paidOrderNum;
	}
	public Integer getCheckInOrderNum() {
		return checkInOrderNum;
	}
	public Integer getCheckInDaysNum() {
		return checkInDaysNum;
	}
	public Integer getPaidMoney() {
		return paidMoney;
	}
	public Integer getEvaluateNum() {
		return evaluateNum;
	}
	public Integer getEvaluatedNum() {
		return evaluatedNum;
	}
	public Double getEvaluateAvgScore() {
		return evaluateAvgScore;
	}
	public Double getIsEvaluatedAvgScore() {
		return isEvaluatedAvgScore;
	}
	public void setBookUid(String bookUid) {
		this.bookUid = bookUid;
	}
	public void setAdMsgNum(Integer adMsgNum) {
		this.adMsgNum = adMsgNum;
	}
	public void setBookOrderNum(Integer bookOrderNum) {
		this.bookOrderNum = bookOrderNum;
	}
	public void setRefBookOrderNum(Integer refBookOrderNum) {
		this.refBookOrderNum = refBookOrderNum;
	}
	public void setPaidOrderNum(Integer paidOrderNum) {
		this.paidOrderNum = paidOrderNum;
	}
	public void setCheckInOrderNum(Integer checkInOrderNum) {
		this.checkInOrderNum = checkInOrderNum;
	}
	public void setCheckInDaysNum(Integer checkInDaysNum) {
		this.checkInDaysNum = checkInDaysNum;
	}
	public void setPaidMoney(Integer paidMoney) {
		this.paidMoney = paidMoney;
	}
	public void setEvaluateNum(Integer evaluateNum) {
		this.evaluateNum = evaluateNum;
	}
	public void setEvaluatedNum(Integer evaluatedNum) {
		this.evaluatedNum = evaluatedNum;
	}
	public void setEvaluateAvgScore(Double evaluateAvgScore) {
		this.evaluateAvgScore = evaluateAvgScore;
	}
	public void setIsEvaluatedAvgScore(Double isEvaluatedAvgScore) {
		this.isEvaluatedAvgScore = isEvaluatedAvgScore;
	}
	

}
