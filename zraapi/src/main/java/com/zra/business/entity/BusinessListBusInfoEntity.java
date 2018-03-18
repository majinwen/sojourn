package com.zra.business.entity;

import java.util.Date;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/24.
 */
public class BusinessListBusInfoEntity {
    private Date createTime;
    private Integer busiId;
    private String expectTime;
    private String proAddress;
    private String busiStep;
    private String projectId;
    private String employeeId;
    private String zoSmallImg;
    private String zoName;
    private String zoMobile;
    //added by wangxm113
    private String busiBid;

    public String getBusiBid() {
        return busiBid;
    }

    public void setBusiBid(String busiBid) {
        this.busiBid = busiBid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBusiId() {
        return busiId;
    }

    public void setBusiId(Integer busiId) {
        this.busiId = busiId;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public String getBusiStep() {
        return busiStep;
    }

    public void setBusiStep(String busiStep) {
        this.busiStep = busiStep;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getZoSmallImg() {
        return zoSmallImg;
    }

    public void setZoSmallImg(String zoSmallImg) {
        this.zoSmallImg = zoSmallImg;
    }

    public String getZoName() {
        return zoName;
    }

    public void setZoName(String zoName) {
        this.zoName = zoName;
    }

    public String getZoMobile() {
        return zoMobile;
    }

    public void setZoMobile(String zoMobile) {
        this.zoMobile = zoMobile;
    }
}
