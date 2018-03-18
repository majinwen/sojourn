package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>收款单实体</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月08日 14:52
 * @since 1.0
 */
public class ReceiptEntity extends BaseEntity {

	private static final long serialVersionUID = 5684515547794577522L;

	private Integer id;
	private String fid;
	private String parentFid;
	private String billNum;// 收款单编号
	private Integer amount; //支付总金额
	/**
	 * 不序列化该字段  浮点型金额  2位小数
	 */
	private transient Double amountAccept;

	private String paySerialNum;// 流水号	线上支付该字段为支付平台的流水号；线下支付该字段为对方账户
	private String payType; //支付方式
	private String payTime; //支付时间
	private String receiptMothed; //付款渠道
	private String payer; //付款人姓名
	private String posId; //POS终端号
	private String referenceNum; //参考号
	private String checkNumber; //支票号
	private String makerCode; //制单人系统号
	private String makerName; //制单人姓名
	private String makerDept; //制单人部门
	private Integer confirmStatus; //审核状态
	private Integer receiptStatus; //收款状态
	private Integer accountFlag; //账户操作标识
	private String attach; //附件地址
	private Date createTime;
	private Date updateTime;
	private Integer isDel;
	private Integer isValid;
	private Integer isSyncFinance;
	private String contractId; // 合同标识
	private String remittanceAccount; // 汇入帐号

	public String getRemittanceAccount() {
		return remittanceAccount;
	}

	public void setRemittanceAccount(String remittanceAccount) {
		this.remittanceAccount = remittanceAccount;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsSyncFinance() {
		return isSyncFinance;
	}

	public void setIsSyncFinance(Integer isSyncFinance) {
		this.isSyncFinance = isSyncFinance;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getPaySerialNum() {
		return paySerialNum;
	}

	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getReceiptMothed() {
		return receiptMothed;
	}

	public void setReceiptMothed(String receiptMothed) {
		this.receiptMothed = receiptMothed;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getReferenceNum() {
		return referenceNum;
	}

	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getMakerCode() {
		return makerCode;
	}

	public void setMakerCode(String makerCode) {
		this.makerCode = makerCode;
	}

	public String getMakerName() {
		return makerName;
	}

	public void setMakerName(String makerName) {
		this.makerName = makerName;
	}

	public String getMakerDept() {
		return makerDept;
	}

	public void setMakerDept(String makerDept) {
		this.makerDept = makerDept;
	}

	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Integer getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(Integer receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	public Integer getAccountFlag() {
		return accountFlag;
	}

	public void setAccountFlag(Integer accountFlag) {
		this.accountFlag = accountFlag;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Double getAmountAccept() {
		return amountAccept;
	}

	public void setAmountAccept(Double amountAccept) {
		this.amountAccept = amountAccept;
	}

	public String getParentFid() {
		return parentFid;
	}

	public void setParentFid(String parentFid) {
		this.parentFid = parentFid;
	}
}
