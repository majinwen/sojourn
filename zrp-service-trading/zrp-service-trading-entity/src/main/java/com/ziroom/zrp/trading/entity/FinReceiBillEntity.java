package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
  * @description: 应收账单实体
  * @author: lusp
  * @date: 2017/10/13 下午 17:00
  * @params:
  * @return:
  */
public class FinReceiBillEntity extends BaseEntity{
    private static final long serialVersionUID = -3049057506626467826L;
    /**
     * 业务id
     */
    private String fid;

    /**
     * 付款计划id
     */
    private Integer paymentId;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 应收账单编号
     */
    private String billNumber;

    /**
     * 单据状态(0.未收款；1.部分收款；2.已收款；3.已作废)
     */
    private Integer billState;

    /**
     * 单据类型(0.合同计划收款；1.其它收款)
     */
    private Integer billType;

    /**
     * 生成方式(0.自动生成；1.手工录入)
     */
    private Integer genWay;

    /**
     * 第几次收款
     */
    private Integer payNum;

    /**
     * 计划收款日期
     */
    private Date planGatherDate;

    /**
     * 应收金额总计
     */
    private Double oughtTotalAmount;

    /**
     * 已收金额总计
     */
    private Double actualTotalAmount;

    /**
     * 开始收款周期
     */
    private Date startCycle;

    /**
     * 截止收款周期
     */
    private Date endCycle;

    /**
     * 制单人
     */
    private String createId;

    /**
     * 制单日期
     */
    private Date createTime;

    /**
     * 备注
     */
    private String commonts;

    /**
     * 城市id
     */
    private String cityId;

    /**
     * 更新人
     */
    private String updateId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0:未删除；1:已删除)
     */
    private Integer isDel;

    /**
     * 是否有效(0:失效；1:有效)
     */
    private Integer isValid;

    /**
     * 如果数据来源于解约，解约协议的id
     */
    private String surrenderId;

    /**
     * 支付订单号
     */
    private String paymentnum;

    /**
     * 计算违约金时间
     */
    private Date calcWyjTime;

    /**
     * 是否计算逾期违约金（0计算  1不计算），与应收账单状态、类型、日期等属性同时验证是否需要计算逾期违约金 
     */
    private Integer isCalcWyj;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber == null ? null : billNumber.trim();
    }

    public Integer getBillState() {
        return billState;
    }

    public void setBillState(Integer billState) {
        this.billState = billState;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public Integer getGenWay() {
        return genWay;
    }

    public void setGenWay(Integer genWay) {
        this.genWay = genWay;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public Date getPlanGatherDate() {
        return planGatherDate;
    }

    public void setPlanGatherDate(Date planGatherDate) {
        this.planGatherDate = planGatherDate;
    }

    public Double getOughtTotalAmount() {
        return oughtTotalAmount;
    }

    public void setOughtTotalAmount(Double oughtTotalAmount) {
        this.oughtTotalAmount = oughtTotalAmount;
    }

    public Double getActualTotalAmount() {
        return actualTotalAmount;
    }

    public void setActualTotalAmount(Double actualTotalAmount) {
        this.actualTotalAmount = actualTotalAmount;
    }

    public Date getStartCycle() {
        return startCycle;
    }

    public void setStartCycle(Date startCycle) {
        this.startCycle = startCycle;
    }

    public Date getEndCycle() {
        return endCycle;
    }

    public void setEndCycle(Date endCycle) {
        this.endCycle = endCycle;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCommonts() {
        return commonts;
    }

    public void setCommonts(String commonts) {
        this.commonts = commonts == null ? null : commonts.trim();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
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

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId == null ? null : surrenderId.trim();
    }

    public String getPaymentnum() {
        return paymentnum;
    }

    public void setPaymentnum(String paymentnum) {
        this.paymentnum = paymentnum == null ? null : paymentnum.trim();
    }

    public Date getCalcWyjTime() {
        return calcWyjTime;
    }

    public void setCalcWyjTime(Date calcWyjTime) {
        this.calcWyjTime = calcWyjTime;
    }

    public Integer getIsCalcWyj() {
        return isCalcWyj;
    }

    public void setIsCalcWyj(Integer isCalcWyj) {
        this.isCalcWyj = isCalcWyj;
    }
}