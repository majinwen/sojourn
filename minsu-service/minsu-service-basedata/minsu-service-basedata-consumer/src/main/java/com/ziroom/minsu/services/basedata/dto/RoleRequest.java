/**
 * @FileName: CurrentuserRequest.java
 * @Package com.ziroom.minsu.services.basedata.logic
 * 
 * @author bushujie
 * @created 2016年3月9日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;


import com.ziroom.minsu.services.common.dto.PageRequest;


/**
 * <p>后台角色查询参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class RoleRequest extends PageRequest{
	
	
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 创建人姓名
	 */
	private String createrName;
	
	/**
	 * 是否启用
	 */
	private Integer isVail;

	public String getRoleName() {
		return roleName;
	}

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

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
}
