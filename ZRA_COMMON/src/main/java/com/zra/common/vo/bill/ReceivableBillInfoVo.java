package com.zra.common.vo.bill;

/**
 * <p>应收账单信息VO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月26日 15:45
 * @version 1.0
 * @since 1.0
 */
public class ReceivableBillInfoVo{

	private Integer period; // 期数
	private String billCycle;// 账单周期
	private String paymentDate; // 付款日期
	private String receivableAmount;// 应收金额
	private String receivedAmount; // 实收金额
	private String billStatusTxt;// 账单状态文本
	private Integer billStatus;// 账单状态
	private Integer operationCode; // 账单操作码 1-去支付
	private String operation;// 账单操作文本

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getBillCycle() {
		return billCycle;
	}

	public void setBillCycle(String billCycle) {
		this.billCycle = billCycle;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(String receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public String getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(String receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getBillStatusTxt() {
		return billStatusTxt;
	}

	public void setBillStatusTxt(String billStatusTxt) {
		this.billStatusTxt = billStatusTxt;
	}

	public Integer getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(Integer operationCode) {
		this.operationCode = operationCode;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}
