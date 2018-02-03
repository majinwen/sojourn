/**
 * @FileName: ImGroupsInfoVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2017年7月31日 下午3:05:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import java.io.Serializable;


/**
 * <p>群组vo</p>
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
public class ImGroupsInfoVo implements Serializable{

	/**
	 *序列id
	 */
	private static final long serialVersionUID = 6183994874933221256L;
	
	/**
	 * 群组uid
	 */
	private  String owner;
	
	/**
	 * 群id
	 */
	public   String groupid;
	
	/**
	 * 现有成员总数
	 */
	public  String  affiliations;
	
	/**
	 * 类型
	 */
	private  String  type;
	
	/**
	 * 修改时间 
	 */
	private  String last_modified;
	
	/**
	 * 组名称
	 */
	private  String groupname;

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the groupid
	 */
	public String getGroupid() {
		return groupid;
	}

	/**
	 * @param groupid the groupid to set
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	/**
	 * @return the affiliations
	 */
	public String getAffiliations() {
		return affiliations;
	}

	/**
	 * @param affiliations the affiliations to set
	 */
	public void setAffiliations(String affiliations) {
		this.affiliations = affiliations;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the last_modified
	 */
	public String getLast_modified() {
		return last_modified;
	}

	/**
	 * @param last_modified the last_modified to set
	 */
	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
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
	
	

}
