/**
 * @FileName: GroupDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年8月1日 下午4:09:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>群组查询请求参数</p>
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
public class GroupDto extends PageRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6122501920531969963L;
	/**
	 * 项目id
	 */
	private  String projectBid;
	
	/**
	 * 群名称
	 */
	private String name;
	
	/**
	 * 操作人 uid
	 */
	private String opBid;
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
