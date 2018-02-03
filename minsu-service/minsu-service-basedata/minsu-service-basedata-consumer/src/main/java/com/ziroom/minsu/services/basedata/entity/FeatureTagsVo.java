/**
 * @FileName: FeatureTagsVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author zl
 * @created 2017年1月9日 下午6:10:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

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
public class FeatureTagsVo extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8085152475151028131L;

	/**
	 * 特色标识fid
	 */
	private String fid;
	
	/**
	 * 是否有效 0：否，1：是
	 */
	private Integer isValid;

	/**
	 * 标识名称
	 */
	private String tagName;
	
	/**
	 * 标识类型名称
	 */
	private String tagTypeName;
	
	/**
	 * 外键fid（标签fid或者类型fid）
	 */
	private String foreignFid;
	
	public String getForeignFid() {
		return foreignFid;
	}

	public void setForeignFid(String foreignFid) {
		this.foreignFid = foreignFid;
	}

	public String getFid() {
		return fid;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public String getTagName() {
		return tagName;
	}

	public String getTagTypeName() {
		return tagTypeName;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setTagTypeName(String tagTypeName) {
		this.tagTypeName = tagTypeName;
	}
	
}
