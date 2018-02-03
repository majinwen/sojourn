/**
 * @FileName: SysMsgVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2017年4月17日 下午2:26:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;

/**
 * <p>TODO</p>
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
public class SysMsgVo extends MsgFirstAdvisoryEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5236288972042097160L;
	
	private Integer roleType;

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
