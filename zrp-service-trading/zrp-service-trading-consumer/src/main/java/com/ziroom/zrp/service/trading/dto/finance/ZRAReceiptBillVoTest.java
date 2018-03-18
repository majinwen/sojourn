package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;

/**
 * <p>收款单列表单个收款单实体</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月21日 11:12
 * @since 1.0
 */
public class ZRAReceiptBillVoTest implements Serializable {

	private static final long serialVersionUID = 5730757047729280499L;

	/**
	 * 收款单号
	 */
	private String receiptNo;

	/**
	 * 创建时间（制单时间）
	 */
	private String createDate;

	/**
	 * 支付时间
	 */
	private String payDate;

	/**
	 * 审核状态
	 */
	private Integer auditStatus;

	/**
	 * 收款状态
	 */
	private Integer receiptStatus;

	/**
	 * 支付方式
	 */
	private String paymentTypeCode;

	/**
	 * 出房合同号
	 */
	private String outContractCode;

	/**
	 * 房源编号
	 */
	private String houseCode;

	/**
	 * 收款金额，单位分
	 */
	private Integer totalAmount;

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(Integer receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	public String getPaymentTypeCode() {
		return paymentTypeCode;
	}

	public void setPaymentTypeCode(String paymentTypeCode) {
		this.paymentTypeCode = paymentTypeCode;
	}

	public String getOutContractCode() {
		return outContractCode;
	}

	public void setOutContractCode(String outContractCode) {
		this.outContractCode = outContractCode;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
}
