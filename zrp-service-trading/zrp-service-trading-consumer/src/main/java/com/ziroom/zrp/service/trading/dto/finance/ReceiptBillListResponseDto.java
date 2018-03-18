package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>收款单列表 返回实体</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月10日 11:05
 * @since 1.0
 */
public class ReceiptBillListResponseDto implements Serializable {

	private String outContractCode; // 出房合同号
	private String companyCode; //公司
	private String uid; //用户uid
	private String paySerialNum; //流水号
	private String receiptNum; //收款单编号
	private String receiptMothed; //付款渠道
	private String payTime;
	private String paymentTypeCode; //支付方式
	private String remark; //备注
	private String payer; //付款人
	private int confirmStatus; // 审核状态：3 未提交 0 未审核，1 审核通过，2 审核未通过 null为全部
	private int totalAmount; // 总金额 单位分
	private String beatBackReason; //打回原因
	private int receiptStatus; //收款状态 0已收款,1未收款 2打回
	private List<String> annexList;
	private List<Map<String, Object>> receiptList;

	public String getOutContractCode() {
		return outContractCode;
	}

	public void setOutContractCode(String outContractCode) {
		this.outContractCode = outContractCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPaySerialNum() {
		return paySerialNum;
	}

	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public String getReceiptMothed() {
		return receiptMothed;
	}

	public void setReceiptMothed(String receiptMothed) {
		this.receiptMothed = receiptMothed;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPaymentTypeCode() {
		return paymentTypeCode;
	}

	public void setPaymentTypeCode(String paymentTypeCode) {
		this.paymentTypeCode = paymentTypeCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public int getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(int confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBeatBackReason() {
		return beatBackReason;
	}

	public void setBeatBackReason(String beatBackReason) {
		this.beatBackReason = beatBackReason;
	}

	public int getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(int receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	public List<String> getAnnexList() {
		return annexList;
	}

	public void setAnnexList(List<String> annexList) {
		this.annexList = annexList;
	}

	public List<Map<String, Object>> getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List<Map<String, Object>> receiptList) {
		this.receiptList = receiptList;
	}
}
