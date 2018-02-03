/**
 * @FileName: CustomerFieldAuditVo.java
 * @Package com.ziroom.minsu.services.customer.dto
 * 
 * @author loushuai
 * @created 2017年8月8日 下午3:39:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class CustomerFieldAuditVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2447708549871607530L;

	/**
     * id
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
     * 审核状态  默认0=未审核；1=审核通过；2=审核拒绝  只有需要审核字段才有此状态 
     */
    private Integer auditStatus;

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
    private String createFid;

    /**
     * 创建人类型 0=其他 1=房东 2=业务人员
     */
    private Integer createType;


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
		this.fid = fid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFieldPathKey() {
		return fieldPathKey;
	}

	public void setFieldPathKey(String fieldPathKey) {
		this.fieldPathKey = fieldPathKey;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
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
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
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
		this.remark = remark;
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

}
