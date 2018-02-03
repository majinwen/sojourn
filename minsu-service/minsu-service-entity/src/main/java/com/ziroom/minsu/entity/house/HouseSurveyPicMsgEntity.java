package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HouseSurveyPicMsgEntity extends BaseEntity {
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8360008113319935577L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 实勘表逻辑fid
     */
    private String surveyFid;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 图片访问基础地址
     */
    private String picBaseUrl;

    /**
     * 图片后缀
     */
    private String picSuffix;

    /**
     * 图片服务器唯一标示
     */
    private String picServerUuid;

    /**
     * 图片类型 0:实勘
     */
    private Integer picType;

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
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

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

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getPicBaseUrl() {
        return picBaseUrl;
    }

    public void setPicBaseUrl(String picBaseUrl) {
        this.picBaseUrl = picBaseUrl == null ? null : picBaseUrl.trim();
    }

    public String getPicSuffix() {
        return picSuffix;
    }

    public void setPicSuffix(String picSuffix) {
        this.picSuffix = picSuffix == null ? null : picSuffix.trim();
    }

    public String getPicServerUuid() {
        return picServerUuid;
    }

    public void setPicServerUuid(String picServerUuid) {
        this.picServerUuid = picServerUuid == null ? null : picServerUuid.trim();
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}