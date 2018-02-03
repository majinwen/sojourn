/**
 * @FileName: AuthMenuEntity.java
 * @Package com.ziroom.minsu.entity.base
 * 
 * @author yd
 * @created 2016年10月31日 上午11:48:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.entity.base;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;

/**
 * <p>权限菜单实体</p>
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
public class AuthMenuEntity extends BaseEntity{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -2386225631928659693L;

	/**
	 * 角色类型
	 */
	private  Integer roleType ;
	
	/**
	 * 用户区域集合
	 */
	private  List<CurrentuserCityEntity> userCityList;
	
	/**
	 * 当前管家编号
	 */
	private  String empCode;

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

	/**
	 * @return the userCityList
	 */
	public List<CurrentuserCityEntity> getUserCityList() {
		return userCityList;
	}

	/**
	 * @param userCityList the userCityList to set
	 */
	public void setUserCityList(List<CurrentuserCityEntity> userCityList) {
		this.userCityList = userCityList;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	
}
