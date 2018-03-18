package com.ziroom.zrp.service.trading.dto.finance;

import java.util.List;

/**
 * <p>付款单参数对象</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月16日 15:47
 * @since 1.0
 */
public class PayVoucherReqDto {
	private String paySerialNum;// 支付流水号
	private Integer isRelatedReceipt = 1;// 是否关联收款单	0：不关联 1：关联（默认）
	private String outContract; // 出房合同号/预定单号
	private String sourceCode;// 支付方式来源代码
	private String payTime; // 支付时间
	private String customerBankName;// 客户银行名称
	private String customerAccountName;// 客户开户名
	private String customerBankAccount; // 客户银行账号
	private String recievedAccount;// 对方自如账号
	private String callUrl; // 业务方回调地址
	private Integer isCheckContract;// 是否校验合同有效性	 0：不校验；1：校验
	private Integer accountFlag;// 账户操作标识	0：不调账户；1：账户充值 2：冻结金转余额
	private String panymentTypeCode = "zhdjzye"; //付款类型	账户冻结转余额  zhdjzye
	private String markCollectCode = "yzrk";//付款对象
	private String busId;// 业务系统关联号	保证唯一，财务回调业务方的唯一标识
	private String dataSources = "ZRA";// 数据来源
	private String billType; // 单据类型 H：收房合同，C:出房合同， D:定金号
	private String uid; // 客户uid
	private String auditFlag = "4"; // 审核状态	1:待提交，2：提交审核，3：审核驳回，4：审核通过
	private String payFlag = "2";// 付款状态	1：未付款，2：已付款，3：付款异常，4：付款中
	private List<PayVoucherDetailReqDto> payVouchersDetail; // 付款单详情列表

	public String getPaySerialNum() {
		return paySerialNum;
	}

	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}

	public Integer getIsRelatedReceipt() {
		return isRelatedReceipt;
	}

	public void setIsRelatedReceipt(Integer isRelatedReceipt) {
		this.isRelatedReceipt = isRelatedReceipt;
	}

	public String getOutContract() {
		return outContract;
	}

	public void setOutContract(String outContract) {
		this.outContract = outContract;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
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

	public String getCustomerBankAccount() {
		return customerBankAccount;
	}

	public void setCustomerBankAccount(String customerBankAccount) {
		this.customerBankAccount = customerBankAccount;
	}

	public String getRecievedAccount() {
		return recievedAccount;
	}

	public void setRecievedAccount(String recievedAccount) {
		this.recievedAccount = recievedAccount;
	}

	public String getCallUrl() {
		return callUrl;
	}

	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}

	public Integer getIsCheckContract() {
		return isCheckContract;
	}

	public void setIsCheckContract(Integer isCheckContract) {
		this.isCheckContract = isCheckContract;
	}

	public Integer getAccountFlag() {
		return accountFlag;
	}

	public void setAccountFlag(Integer accountFlag) {
		this.accountFlag = accountFlag;
	}

	public String getPanymentTypeCode() {
		return panymentTypeCode;
	}

	public void setPanymentTypeCode(String panymentTypeCode) {
		this.panymentTypeCode = panymentTypeCode;
	}

	public String getMarkCollectCode() {
		return markCollectCode;
	}

	public void setMarkCollectCode(String markCollectCode) {
		this.markCollectCode = markCollectCode;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getDataSources() {
		return dataSources;
	}

	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public List<PayVoucherDetailReqDto> getPayVouchersDetail() {
		return payVouchersDetail;
	}

	public void setPayVouchersDetail(List<PayVoucherDetailReqDto> payVouchersDetail) {
		this.payVouchersDetail = payVouchersDetail;
	}
}
