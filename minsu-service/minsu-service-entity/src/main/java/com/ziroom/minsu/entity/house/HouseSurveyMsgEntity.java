package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HouseSurveyMsgEntity extends BaseEntity {
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6046898731108561248L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 实勘编号
     */
    private String surveySn;

    /**
     * 房源逻辑id
     */
    private String houseBaseFid;

    /**
     * 实勘结果 1：符合品质，2;不符品质
     */
    private Integer surveyResult;

    /**
     * 实勘人fid
     */
    private String surveyEmpFid;

    /**
     * 实勘人姓名
     */
    private String surveyEmpName;

    /**
     * 实勘日期
     */
    private Date surveyDate;

    /**
     * 审阅人fid
     */
    private String auditEmpFid;

    /**
     * 审阅人姓名
     */
    private String auditEmpName;

    /**
     * 审阅日期
     */
    private Date auditDate;

    /**
     * 是否审阅 0:否 1:是
     */
    private Integer isAudit;

    /**
     * 创建人fid
     */
    private String createFid;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 记录状态 0:草稿 1:正常 2:删除
     */
    private Integer recordStatus;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 操作人fid
     */
    transient private String operateFid;
    
    /**
     * 操作类型 1：修改 2:审阅
     */
    transient private Integer operateType;

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

    public String getSurveySn() {
        return surveySn;
    }

    public void setSurveySn(String surveySn) {
        this.surveySn = surveySn == null ? null : surveySn.trim();
    }

    public String getHouseBaseFid() {
        return houseBaseFid;
    }

    public void setHouseBaseFid(String houseBaseFid) {
        this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
    }

    public Integer getSurveyResult() {
        return surveyResult;
    }

    public void setSurveyResult(Integer surveyResult) {
        this.surveyResult = surveyResult;
    }

    public String getSurveyEmpFid() {
        return surveyEmpFid;
    }

    public void setSurveyEmpFid(String surveyEmpFid) {
        this.surveyEmpFid = surveyEmpFid == null ? null : surveyEmpFid.trim();
    }

    public String getSurveyEmpName() {
        return surveyEmpName;
    }

    public void setSurveyEmpName(String surveyEmpName) {
        this.surveyEmpName = surveyEmpName == null ? null : surveyEmpName.trim();
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getAuditEmpFid() {
        return auditEmpFid;
    }

    public void setAuditEmpFid(String auditEmpFid) {
        this.auditEmpFid = auditEmpFid == null ? null : auditEmpFid.trim();
    }

    public String getAuditEmpName() {
        return auditEmpName;
    }

    public void setAuditEmpName(String auditEmpName) {
        this.auditEmpName = auditEmpName == null ? null : auditEmpName.trim();
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }
    
    public String getRemark() {
    	return remark;
    }
    
    public void setRemark(String remark) {
    	this.remark = remark == null ? null : remark.trim();
    }
    
    public String getOperateFid() {
    	return operateFid;
    }
    
    public void setOperateFid(String operateFid) {
    	this.operateFid = operateFid;
    }

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
}