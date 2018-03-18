package com.zra.report.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 自如寓业务总览实体.
 * @author wangws21 
 * @date 2017年5月22日
 */
public class ReportOverviewEntity {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 记录时间
     */
    private Date recordDate;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 新增短租签约数
     */
    private Integer shortSignCount;

    /**
     * 新增长租签约数
     */
    private Integer longSignCount;

    /**
     * 总签约数
     */
    private Integer signCount;

    /**
     * 解约数量
     */
    private Integer surrenderCount;

    /**
     * 总收款额
     */
    private BigDecimal voucherTotalAmount;

    /**
     * 总支出金额
     */
    private BigDecimal payTotalAmount;

    /**
     * 制单人
     */
    private String createId;

    /**
     * 制单日期
     */
    private Date createTime;

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

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Integer getShortSignCount() {
        return shortSignCount;
    }

    public void setShortSignCount(Integer shortSignCount) {
        this.shortSignCount = shortSignCount;
    }

    public Integer getLongSignCount() {
        return longSignCount;
    }

    public void setLongSignCount(Integer longSignCount) {
        this.longSignCount = longSignCount;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getSurrenderCount() {
        return surrenderCount;
    }

    public void setSurrenderCount(Integer surrenderCount) {
        this.surrenderCount = surrenderCount;
    }

    public BigDecimal getVoucherTotalAmount() {
        return voucherTotalAmount;
    }

    public void setVoucherTotalAmount(BigDecimal voucherTotalAmount) {
        this.voucherTotalAmount = voucherTotalAmount;
    }

    public BigDecimal getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(BigDecimal payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
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

}