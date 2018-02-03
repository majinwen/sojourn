/**
 * @FileName: GroupBaseDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年8月4日 下午4:55:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;

/**
 * <p>群操作基本参数</p>
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
public class GroupBaseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2179230889279461801L;
	
	/**
	 * 群组id
	 */
	private  String groupId;
	
	/**
	 * 操作人 业务id
	 */
	private  String opBid;
	
	/**
	 * 操作人类型
	 */
	private  Integer opType;
	
	/**
	 * 群主id
	 */
	private String owner;
	
	

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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	 * @return the opType
	 */
	public Integer getOpType() {
		return opType;
	}

	/**
	 * @param opType the opType to set
	 */
	public void setOpType(Integer opType) {
		this.opType = opType;
	}
	
	

}
