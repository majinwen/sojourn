/**
 * @FileName: RoleListVo.java
 * @Package com.ziroom.minsu.ups.vo
 * 
 * @author bushujie
 * @created 2016年12月29日 上午10:35:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.vo;

import com.ziroom.minsu.entity.sys.RoleEntity;

/**
 * <p>角色列表查询vo</p>
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
public class RoleListVo extends RoleEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6291931222035565303L;
	
	/**
	 * 系统名称
	 */
	private String systemName;
	
	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}
