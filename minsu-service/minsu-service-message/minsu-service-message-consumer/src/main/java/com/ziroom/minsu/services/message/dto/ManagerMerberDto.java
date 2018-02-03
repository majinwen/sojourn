/**
 * @FileName: ManagerMerberDt.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年8月4日 上午10:41:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;

/**
 * <p>添加管理员参数</p>
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
public class ManagerMerberDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 277663401288705056L;
	
	/**
	 * 组id
	 */
	private  String groupId;
	
	/**
	 * 管理员uid
	 */
	private String adminUid;
	
	/**
	 * 操作人 fid
	 */
	private String opFid;
	
	/**
	 * 操作人类型
	 */
	private Integer opType; 
	
	
	/**
	 * 角色
	 */
	private Integer memberRole;
	

	/**
	 * @return the memberRole
	 */
	public Integer getMemberRole() {
		return memberRole;
	}

	/**
	 * @param memberRole the memberRole to set
	 */
	public void setMemberRole(Integer memberRole) {
		this.memberRole = memberRole;
	}

	/**
	 * @return the opFid
	 */
	public String getOpFid() {
		return opFid;
	}

	/**
	 * @param opFid the opFid to set
	 */
	public void setOpFid(String opFid) {
		this.opFid = opFid;
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
	 * @return the adminUid
	 */
	public String getAdminUid() {
		return adminUid;
	}

	/**
	 * @param adminUid the adminUid to set
	 */
	public void setAdminUid(String adminUid) {
		this.adminUid = adminUid;
	}
	
	

}
