/**
 * @FileName: GroupInfoDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年8月4日 下午2:19:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;

/**
 * <p>组修改参数</p>
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
public class GroupInfoDto  extends GroupBaseDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -7209722537442875253L;

	
	/**
	 * 群组名称
	 */
	private  String groupname;
	
	/**
	 * 描述信息
	 */
	private String description;
	
	/**
	 * 
	 */
	private Integer maxusers;

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
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}

	/**
	 * @param groupname the groupname to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the maxusers
	 */
	public Integer getMaxusers() {
		return maxusers;
	}

	/**
	 * @param maxusers the maxusers to set
	 */
	public void setMaxusers(Integer maxusers) {
		this.maxusers = maxusers;
	}
	
	

}
