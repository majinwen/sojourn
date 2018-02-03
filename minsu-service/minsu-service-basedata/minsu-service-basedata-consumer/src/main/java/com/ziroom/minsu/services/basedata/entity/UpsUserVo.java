/**
 * @FileName: UpsUserVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author bushujie
 * @created 2017年10月28日 下午3:44:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;

/**
 * <p>ups登录user信息</p>
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
public class UpsUserVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5711665740722398608L;
	/**
	 * 用户信息
	 */
	private CurrentuserEntity currentuserEntity;
	/**
	 * 员工信息
	 */
	private EmployeeEntity employeeEntity;
	/**
	 * 权限set拦截器用
	 */
	private Set<String> resourceVoSet;
	/**
	 * 权限树 菜单用
	 */
	private List<ResourceVo> resourceVoList;
	
	public CurrentuserEntity getCurrentuserEntity() {
		return currentuserEntity;
	}

	public void setCurrentuserEntity(CurrentuserEntity currentuserEntity) {
		this.currentuserEntity = currentuserEntity;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public Set<String> getResourceVoSet() {
		return resourceVoSet;
	}

	public void setResourceVoSet(Set<String> resourceVoSet) {
		this.resourceVoSet = resourceVoSet;
	}

	public List<ResourceVo> getResourceVoList() {
		return resourceVoList;
	}

	public void setResourceVoList(List<ResourceVo> resourceVoList) {
		this.resourceVoList = resourceVoList;
	}
}
