package com.ziroom.minsu.entity.customer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class CustomerUpdateHistoryLogEntity  extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4530153200865084300L;

	/**
     * 主键id
     */
	private Integer id;

	/**
     * 业务fid
     */
    private String fid;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 字段路径key
     */
    private String fieldPathKey;

    /**
     * 修改字段的名称描述
     */
    private String fieldDesc;

    /**
     * 修改字段的路径
     */
    private String fieldPath;

    /**
     * 修改字段来源 0=其他 1=app 2=M站 3=pc 4=troy
     */
    private Integer sourceType;

    /**
     * 修改字段的旧值
     */
    private String oldValue;

    /**
     * 修改字段的新值
     */
    private String newValue;

    /**
     * 是否是大字段 0=否 1=是 (超过1024 即是大字段)
     */
    private Integer isText;

    /**
     * 修改备注
     */
    private String remark;

    /**
     * 创建人fid （房东是uid，业务人员是系统号）
     */
    private String createrFid;

    /**
     * 创建人类型 0=其他 1=房东 2=业务人员
     */
    private Integer createrType;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getFieldPathKey() {
        return fieldPathKey;
    }

    public void setFieldPathKey(String fieldPathKey) {
        this.fieldPathKey = fieldPathKey == null ? null : fieldPathKey.trim();
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc == null ? null : fieldDesc.trim();
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath == null ? null : fieldPath.trim();
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }

    public Integer getIsText() {
        return isText;
    }

    public void setIsText(Integer isText) {
        this.isText = isText;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreaterFid() {
        return createrFid;
    }

    public void setCreaterFid(String createrFid) {
        this.createrFid = createrFid == null ? null : createrFid.trim();
    }

    public Integer getCreaterType() {
		return createrType;
	}

	public void setCreaterType(Integer createrType) {
		this.createrType = createrType;
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
}