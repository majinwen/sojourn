package com.ziroom.zrp.service.trading.dto.finance;

/**
 * <p>应收账单信息返回</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月25日 11:25
 * @since 1.0
 */
public class ReceiptBillResponse {
    /**
     * 账单编号
     */
    private String billNum;
    /**
     * 出房合同号
     */
    private String outContractCode;
    /**
     * 账单类型
     */
    private String documentType;
    /**
     * 期数
     */
    private Integer periods;
    /**
     * 费用项编码
     */
    private String costCode;
    /**
     * 用户uid
     */
    private String uid;
    /**
     * 预计收款日（yyyy-MM-dd）
     */
    private String preCollectionDate;
    /**
     * 账单开始时间（yyyy-MM-dd）
     */
    private String billsycleStarttime;
    /**
     * 账单截止时间（yyyy-MM-dd）
     */
    private String billsycleEndtime;
    /**
     * 核销日期（yyyy-MM-dd）没有返回""
     */
    private String verificateDate;
    /**
     * 应收金额（单位分）
     */
    private Integer receiptBillAmount;
    /**
     * 实收金额（单位分）
     */
    private Integer receivedBillAmount;
    /**
     * （0未核销,1已核销,2部分核销）
     */
    private Integer verificateStatus;

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getOutContractCode() {
        return outContractCode;
    }

    public void setOutContractCode(String outContractCode) {
        this.outContractCode = outContractCode;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getVerificateDate() {
        return verificateDate;
    }

    public void setVerificateDate(String verificateDate) {
        this.verificateDate = verificateDate;
    }

    public Integer getReceiptBillAmount() {
        return receiptBillAmount;
    }

    public void setReceiptBillAmount(Integer receiptBillAmount) {
        this.receiptBillAmount = receiptBillAmount;
    }

    public Integer getReceivedBillAmount() {
        return receivedBillAmount;
    }

    public void setReceivedBillAmount(Integer receivedBillAmount) {
        this.receivedBillAmount = receivedBillAmount;
    }

    public Integer getVerificateStatus() {
        return verificateStatus;
    }

    public void setVerificateStatus(Integer verificateStatus) {
        this.verificateStatus = verificateStatus;
    }
}
