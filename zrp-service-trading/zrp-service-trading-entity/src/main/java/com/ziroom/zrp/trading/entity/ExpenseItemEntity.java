package com.ziroom.zrp.trading.entity;

import java.util.Date;

public class ExpenseItemEntity {
    /**
     * KEY
     */
    private Integer fid;

    /**
     * 费用项目编码
     */
    private String itemCode;

    /**
     * 费用项目名称
     */
    private String itemName;

    /**
     * 账单来源\n1 合同付款计划  \n2 消费单产生\n3 直接录入收款\n
     */
    private Integer typeBill;

    /**
     * 付款性质\n1 预付消费\n2 预付冻结\n3 直接消费\n
     */
    private Integer typePayment;

    /**
     * 消费账期\n1 日\n2 月\n3  一次性\n0 不消费\n
     */
    private Integer typeConsumption;

    /**
     * 收入科目\n1 预付款\n2 代收代付\n3 主营业务收入\n4 营业外收入\n0 其他业务收入\n
     */
    private Integer typeIncome;

    /**
     * 是否允许分次\n0 不允许\n1 允许\n
     */
    private Integer batch;

    /**
     * 是否内置\n0 否\n1 是
     */
    private Integer system;

    /**
     * AFA编码
     */
    private String afa;

    /**
     * 备注
     */
    private String remark;

    /**
     * [预留] 城市
     */
    private String cityid;

    /**
     * 有效期起
     */
    private Date effectiveDate;

    /**
     * 有效期止
     */
    private Date expireDate;

    private Integer fvalid;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    private Integer fisdel;

    /**
     * 解约结算确认时 结算抵消顺序
     */
    private Integer surrOrder;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public Integer getTypeBill() {
        return typeBill;
    }

    public void setTypeBill(Integer typeBill) {
        this.typeBill = typeBill;
    }

    public Integer getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(Integer typePayment) {
        this.typePayment = typePayment;
    }

    public Integer getTypeConsumption() {
        return typeConsumption;
    }

    public void setTypeConsumption(Integer typeConsumption) {
        this.typeConsumption = typeConsumption;
    }

    public Integer getTypeIncome() {
        return typeIncome;
    }

    public void setTypeIncome(Integer typeIncome) {
        this.typeIncome = typeIncome;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getSystem() {
        return system;
    }

    public void setSystem(Integer system) {
        this.system = system;
    }

    public String getAfa() {
        return afa;
    }

    public void setAfa(String afa) {
        this.afa = afa == null ? null : afa.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid == null ? null : createrid.trim();
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid == null ? null : updaterid.trim();
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public Integer getSurrOrder() {
        return surrOrder;
    }

    public void setSurrOrder(Integer surrOrder) {
        this.surrOrder = surrOrder;
    }
}