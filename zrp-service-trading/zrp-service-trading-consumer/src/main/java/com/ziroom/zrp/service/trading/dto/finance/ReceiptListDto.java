package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;
import java.util.List;
/**
 * <p>查询实收信息返回的每次支付信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月17日
 * @since 1.0
 */
public class ReceiptListDto {
	
	private String receiptNum;//String类型 收款单编号
	private String paymentTypeCode;//String类型 支付方式（微信IOS：wx_ios_pay 微信安卓：wx_ad_pay）
	private String receiptMothed;//String类型 付款渠道
	private String paySerialNum;//String类型 支付流水号
	private String payTime;//Date类型 支付时间
	private Integer receiptStatus;//Integer类型 收款状态（0已收款,1未收款 2打回）
	private Integer amount;//Integer类型 费用项实收金额（单位：分）
	private String payer;//String类型  实际付款人姓名
	private String remark;//String类型 备注
	private List<String> annexList;//List类型 附件地址列表
	
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	public String getPaymentTypeCode() {
		return paymentTypeCode;
	}
	public void setPaymentTypeCode(String paymentTypeCode) {
		this.paymentTypeCode = paymentTypeCode;
	}
	public String getReceiptMothed() {
		return receiptMothed;
	}
	public void setReceiptMothed(String receiptMothed) {
		this.receiptMothed = receiptMothed;
	}
	public String getPaySerialNum() {
		return paySerialNum;
	}
	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public Integer getReceiptStatus() {
		return receiptStatus;
	}
	public void setReceiptStatus(Integer receiptStatus) {
		this.receiptStatus = receiptStatus;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<String> getAnnexList() {
		return annexList;
	}
	public void setAnnexList(List<String> annexList) {
		this.annexList = annexList;
	}
}
