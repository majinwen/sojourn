/**
 * @FileName: GroupVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2017年8月1日 下午4:05:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import com.ziroom.minsu.entity.message.HuanxinImGroupEntity;

/**
 * <p>群组返回 vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class GroupVo  extends HuanxinImGroupEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -5672282096826416440L;
	
	/**
	 * 项目id
	 */
	private  String projectBid;
	
	/**
	 * 操作人uid
	 */
	private String opBid;
	
	/**
	 * 操作人类型
	 */
	private  Integer opType;
	
	/**
	 * 是否是默认群组 0=是 1=不是 默认1
	 */
	private Integer isDefault;
	
	
	/**
	 * @return the isDefault
	 */
	public Integer getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the opBid
	 */
	public String getOpBid() {
		return opBid;
	}

	/**
	 * @param opBid the opBid to set
	 */
	public void setOpBid(String opBid) {
		this.opBid = opBid;
	}

	/**
	 * @return the opType
	 */
	public Integer getOpType() {
		return opType;
	}

	/**
	 * @param opType the opType to set
	 */
	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	/**
	 * @return the projectBid
	 */
	public String getProjectBid() {
		return projectBid;
	}

	/**
	 * @param projectBid the projectBid to set
	 */
	public void setProjectBid(String projectBid) {
		this.projectBid = projectBid;
	}



	
	
	

}
