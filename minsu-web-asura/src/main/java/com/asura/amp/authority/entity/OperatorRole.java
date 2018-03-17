/**
 * @FileName: OperatorRole.java
 * @Package com.asura.amp.authority.entity
 * 
 * @author zhangshaobin
 * @created 2013-1-28 下午1:46:21
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 操作员角色对应关系对象
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class OperatorRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2162286646515995762L;

	/*
	 * 自增id
	 */
	private Integer orId;

	/*
	 * 操作员id
	 */
	private Integer operatorId;

	/*
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * @return the orId
	 */
	public Integer getOrId() {
		return orId;
	}

	/**
	 * @param orId
	 *            the orId to set
	 */
	public void setOrId(Integer orId) {
		this.orId = orId;
	}

	/**
	 * @return the operatorId
	 */
	public Integer getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId
	 *            the operatorId to set
	 */
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
