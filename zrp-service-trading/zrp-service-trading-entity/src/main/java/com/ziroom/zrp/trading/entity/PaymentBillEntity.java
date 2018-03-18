package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class PaymentBillEntity extends BaseEntity {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 付款单标识
     */
    private String fid;

    /**
     * 合同号
     */
    private String outContract;

    /**
     * 支付流水号
     */
    private String paySerialNum;

    /**
     * 支付来源代码
     */
    private String sourceCode;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 客户银行名称
     */
    private String customerBankName;

    /**
     * 客户开户名
     */
    private String customerAccountName;

    /**
     * 客户银行账号
     */
    private String customerBankAccount;

    /**
     * 对方自如账号
     */
    private String recievedAccount;

    /**
     * 0：不调账户；1：账户充值 2：冻结金转余额
     */
    private Integer accountFlag;

    /**
     * 付款类型	账户冻结转余额  zhdjzye
     */
    private String panymentTypeCode;

    /**
     * 付款对象
     */
    private String markCollectCode;

    /**
     * 业务系统关联号
     */
    private String busId;

    /**
     * 数据来源
     */
    private String dataSources;

    /**
     * 单据类型 H：收房合同，C:出房合同， D:定金号
     */
    private String billType;

    /**
     * 客户uid
     */
    private String uid;

    /**
     * 审核状态	1:待提交，2：提交审核，3：审核驳回，4：审核通过
     */
    private Integer auditFlag;

    /**
     * 付款状态	1：未付款，2：已付款，3：付款异常，4：付款中
     */
    private Integer payFlag;

    /**
     * 生成方式(0.自动生成；1.手工录入)
     */
    private Integer genWay;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 是否删除(0:未删除；1:已删除)
     */
    private Integer isDel;

    /**
     * 是否有效(0:失效；1:有效)
     */
    private Integer isValid;

    /**
     * 解约协议id
     */
    private String surrenderId;

    /**
     * 是否同步财务（0：未同步，1：已同步）
     */
    private Integer synFinance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getOutContract() {
        return outContract;
    }

    public void setOutContract(String outContract) {
        this.outContract = outContract == null ? null : outContract.trim();
    }

    public String getPaySerialNum() {
        return paySerialNum;
    }

    public void setPaySerialNum(String paySerialNum) {
        this.paySerialNum = paySerialNum == null ? null : paySerialNum.trim();
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode == null ? null : sourceCode.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getCustomerBankName() {
        return customerBankName;
    }

    public void setCustomerBankName(String customerBankName) {
        this.customerBankName = customerBankName == null ? null : customerBankName.trim();
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
        this.customerBankAccount = customerBankAccount == null ? null : customerBankAccount.trim();
    }

    public String getRecievedAccount() {
        return recievedAccount;
    }

    public void setRecievedAccount(String recievedAccount) {
        this.recievedAccount = recievedAccount == null ? null : recievedAccount.trim();
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
        this.panymentTypeCode = panymentTypeCode == null ? null : panymentTypeCode.trim();
    }

    public String getMarkCollectCode() {
        return markCollectCode;
    }

    public void setMarkCollectCode(String markCollectCode) {
        this.markCollectCode = markCollectCode == null ? null : markCollectCode.trim();
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources == null ? null : dataSources.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public Integer getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

    public Integer getGenWay() {
        return genWay;
    }

    public void setGenWay(Integer genWay) {
        this.genWay = genWay;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId == null ? null : surrenderId.trim();
    }

    public Integer getSynFinance() {
        return synFinance;
    }

    public void setSynFinance(Integer synFinance) {
        this.synFinance = synFinance;
    }
}