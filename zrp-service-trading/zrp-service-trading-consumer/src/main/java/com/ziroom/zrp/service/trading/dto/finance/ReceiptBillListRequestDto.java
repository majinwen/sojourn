package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;

/**
 * <p>收款单列表 请求实体</p>
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
public class ReceiptBillListRequestDto implements Serializable {

	private String periods; // 期数 null为全部
	private String confirmStatus; // 审核状态：3 未提交 0 未审核，1 审核通过，2 审核未通过 null为全部
	private String receiptStatus; // 收款状态:0已收款,1未收款 2打回 null为全部
	private String verificateStatus; // 应收账单核销状态 0未核销,1已核销,2部分核销 null为全部
	private String contractCode; // 出房合同号 必传

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	public String getVerificateStatus() {
		return verificateStatus;
	}

	public void setVerificateStatus(String verificateStatus) {
		this.verificateStatus = verificateStatus;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
}
