/**
 * @FileName: RoleResource.java
 * @Package com.asura.amp.authority.entity
 * 
 * @author zhangshaobin
 * @created 2013-1-27 下午4:13:16
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 角色资源关联类
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
public class RoleResource extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7537935931395531623L;
	/*
	 * 角色资源id 自增id
	 */
	private Integer rrId;
	/*
	 * 角色id
	 */
	private Integer roleId;
	/*
	 * 资源id
	 */
	private Integer resId;

	/**
	 * @return the rrId
	 */
	public Integer getRrId() {
		return rrId;
	}

	/**
	 * @param rrId
	 *            the rrId to set
	 */
	public void setRrId(Integer rrId) {
		this.rrId = rrId;
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

	/**
	 * @return the resId
	 */
	public Integer getResId() {
		return resId;
	}

	/**
	 * @param resId
	 *            the resId to set
	 */
	public void setResId(Integer resId) {
		this.resId = resId;
	}

}
