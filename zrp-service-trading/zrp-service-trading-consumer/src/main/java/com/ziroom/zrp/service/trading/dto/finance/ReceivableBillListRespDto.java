package com.ziroom.zrp.service.trading.dto.finance;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月14日 15:36
 * @since 1.0
 */
public class ReceivableBillListRespDto {
	private String documentType; // 账单类型
	private String billNum; // 账单号
	private String uid; // 用户标识
	private String userName; // 操作人姓名
	private String preCollectionDate; //预计收款日
	private String billsycleStarttime;//账单开始日
	private String billsycleEndtime;//账单结束日
	private String houseCode;//房源编号
	private String costCode;// 费用项编码
	private Long receiptBillAmount;//应收金额（分
	private Long receivedBillAmount;//实收金额（分）
	private Integer verificateStatus;//核销状态
	private String createTime;// 制单日期
	private String verificateDate;//核销日期
	private String outContractCode;
	private String houseId;//项目id
	private Integer periods;//期数
	private String parentContractCode;// 父合同号
	private String receiptBillAmountStr;
	private String receivedBillAmountStr;

	public String getReceiptBillAmountStr() {
		return receiptBillAmountStr;
	}

	public void setReceiptBillAmountStr(String receiptBillAmountStr) {
		this.receiptBillAmountStr = receiptBillAmountStr;
	}

	public String getReceivedBillAmountStr() {
		return receivedBillAmountStr;
	}

	public void setReceivedBillAmountStr(String receivedBillAmountStr) {
		this.receivedBillAmountStr = receivedBillAmountStr;
	}

	public String getParentContractCode() {
		return parentContractCode;
	}

	public void setParentContractCode(String parentContractCode) {
		this.parentContractCode = parentContractCode;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPreCollectionDate() {
		return preCollectionDate;
	}

	public void setPreCollectionDate(String preCollectionDate) {
		this.preCollectionDate = preCollectionDate;
	}

	public String getBillsycleStarttime() {
		return billsycleStarttime;
	}

	public void setBillsycleStarttime(String billsycleStarttime) {
		this.billsycleStarttime = billsycleStarttime;
	}

	public String getBillsycleEndtime() {
		return billsycleEndtime;
	}

	public void setBillsycleEndtime(String billsycleEndtime) {
		this.billsycleEndtime = billsycleEndtime;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getCostCode() {
		return costCode;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}

	public Long getReceiptBillAmount() {
		return receiptBillAmount;
	}

	public void setReceiptBillAmount(Long receiptBillAmount) {
		this.receiptBillAmount = receiptBillAmount;
	}

	public Long getReceivedBillAmount() {
		return receivedBillAmount;
	}

	public void setReceivedBillAmount(Long receivedBillAmount) {
		this.receivedBillAmount = receivedBillAmount;
	}

	public Integer getVerificateStatus() {
		return verificateStatus;
	}

	public void setVerificateStatus(Integer verificateStatus) {
		this.verificateStatus = verificateStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVerificateDate() {
		return verificateDate;
	}

	public void setVerificateDate(String verificateDate) {
		this.verificateDate = verificateDate;
	}

	public String getOutContractCode() {
		return outContractCode;
	}

	public void setOutContractCode(String outContractCode) {
		this.outContractCode = outContractCode;
	}

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
}
