package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class NpsEntiy extends BaseEntity {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 调查编号
     */
    private String npsCode;

    /**
     * 调查名称
     */
    private String npsName;

    /**
     * 调查内容
     */
    private String npsContent;

    /**
     * nps状态
     */
    private Integer npsStatus;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

    public Integer getNpsStatus() {
        return npsStatus;
    }

    public void setNpsStatus(Integer npsStatus) {
        this.npsStatus = npsStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNpsCode() {
        return npsCode;
    }

    public void setNpsCode(String npsCode) {
        this.npsCode = npsCode == null ? null : npsCode.trim();
    }

    public String getNpsName() {
        return npsName;
    }

    public void setNpsName(String npsName) {
        this.npsName = npsName == null ? null : npsName.trim();
    }

    public String getNpsContent() {
        return npsContent;
    }

    public void setNpsContent(String npsContent) {
        this.npsContent = npsContent == null ? null : npsContent.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}