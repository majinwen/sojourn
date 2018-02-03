/**
 * @FileName: GroupMemberPageInfoDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年7月31日 上午11:26:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>分页获取 群成员 参数</p>
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
public class GroupMemberPageInfoDto extends PageRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 3710832858140170779L;
	
	/**
	 * 群组id
	 */
	private String groupId;
	
	/**
	 * 群成员uid
	 */
	private List<String> members;
	
	/**
	 * 操作人opFid
	 */
	private String opFid;
	
	/**
	 * 群成员角色 0=普通成员 1=管理员 2=群主
	 */
	private Integer memberRole;
	
	/**
	 * 成员状态 0=正常 1=禁言 2=黑名单
	 */
	private Integer memberStatu;
	

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
	 * @return the memberStatu
	 */
	public Integer getMemberStatu() {
		return memberStatu;
	}

	/**
	 * @param memberStatu the memberStatu to set
	 */
	public void setMemberStatu(Integer memberStatu) {
		this.memberStatu = memberStatu;
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
	 * @return the members
	 */
	public List<String> getMembers() {
		return members;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(List<String> members) {
		this.members = members;
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
	
	

}
