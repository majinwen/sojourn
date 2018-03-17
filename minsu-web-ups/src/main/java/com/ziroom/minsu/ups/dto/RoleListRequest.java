/**
 * @FileName: RoleListRequest.java
 * @Package com.ziroom.minsu.ups.dto
 * 
 * @author bushujie
 * @created 2016年12月29日 上午11:31:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>角色列表查询条件</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class RoleListRequest extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7147951068186369287L;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 系统fid
	 */
	private String systemFid;
	/**
	 * 角色类型
	 */
	private Integer roleType;
	
	/**
	 * 是否有效
	 */
	private Integer isVail;
	/**
	 * @return the isVail
	 */
	public Integer getIsVail() {
		return isVail;
	}
	/**
	 * @param isVail the isVail to set
	 */
	public void setIsVail(Integer isVail) {
		this.isVail = isVail;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the systemFid
	 */
	public String getSystemFid() {
		return systemFid;
	}
	/**
	 * @param systemFid the systemFid to set
	 */
	public void setSystemFid(String systemFid) {
		this.systemFid = systemFid;
	}
	/**
	 * @return the roleType
	 */
	public Integer getRoleType() {
		return roleType;
	}
	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
}
