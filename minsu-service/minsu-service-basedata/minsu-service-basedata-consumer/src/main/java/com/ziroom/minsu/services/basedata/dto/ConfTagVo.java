/**
 * @FileName: ConfTagVo.java
 * @Package com.ziroom.minsu.services.basedata.dto
 * 
 * @author zl
 * @created 2017年1月10日 下午4:47:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;

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
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class ConfTagVo extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8611424220888640574L;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签类型
     */
    private Integer tagType;

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
     * 是否有效 0：未生效 1：已生效
     */
    private Integer isValid;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;
	
	/**
	 * 标签类型名称
	 */
	private String tagTypeName;

	public String getFid() {
		return fid;
	}

	public String getTagName() {
		return tagName;
	}

	public Integer getTagType() {
		return tagType;
	}

	public String getCreateFid() {
		return createFid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public String getTagTypeName() {
		return tagTypeName;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public void setTagTypeName(String tagTypeName) {
		this.tagTypeName = tagTypeName;
	}
	
}
