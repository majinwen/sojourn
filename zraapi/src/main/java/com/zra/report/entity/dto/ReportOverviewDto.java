package com.zra.report.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 自如寓业务总览dto.
 * @author wangws21 
 * @date 2017年5月22日
 */
public class ReportOverviewDto {

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 类型 1 长租;2 短租
     */
    private String conType;

    /**
     * 签约数
     */
    private Integer signCount;

    /**
     * 解约数量
     */
    private Integer surrenderCount;

    /**
     * 支出金额
     */
    private BigDecimal amount;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
}