package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HouseSurveyLogEntity extends BaseEntity {
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6432052491211912642L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 实勘表fid
     */
    private String surveyFid;

    /**
     * 操作人fid
     */
    private String operateFid;

    /**
     * 修改前实勘结果
     */
    private Integer preSurveyResult;

    /**
     * 修改后实勘结果
     */
    private Integer curSurveyResult;

    /**
     * 修改前实勘日期
     */
    private Date preSurveyDate;

    /**
     * 修改后实勘日期
     */
    private Date curSurveyDate;

    /**
     * 操作类型 1：修改 2:审阅
     */
    private Integer operateType;

    /**
     * 操作时间
     */
    private Date createDate;

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

    public String getSurveyFid() {
        return surveyFid;
    }

    public void setSurveyFid(String surveyFid) {
        this.surveyFid = surveyFid == null ? null : surveyFid.trim();
    }

    public String getOperateFid() {
        return operateFid;
    }

    public void setOperateFid(String operateFid) {
        this.operateFid = operateFid == null ? null : operateFid.trim();
    }

    public Integer getPreSurveyResult() {
        return preSurveyResult;
    }

    public void setPreSurveyResult(Integer preSurveyResult) {
        this.preSurveyResult = preSurveyResult;
    }

    public Integer getCurSurveyResult() {
        return curSurveyResult;
    }

    public void setCurSurveyResult(Integer curSurveyResult) {
        this.curSurveyResult = curSurveyResult;
    }

    public Date getPreSurveyDate() {
        return preSurveyDate;
    }

    public void setPreSurveyDate(Date preSurveyDate) {
        this.preSurveyDate = preSurveyDate;
    }

    public Date getCurSurveyDate() {
        return curSurveyDate;
    }

    public void setCurSurveyDate(Date curSurveyDate) {
        this.curSurveyDate = curSurveyDate;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}