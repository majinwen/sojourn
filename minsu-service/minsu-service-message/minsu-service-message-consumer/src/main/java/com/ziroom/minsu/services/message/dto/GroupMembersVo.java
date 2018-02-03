/**
 * @FileName: GroupMembersVo.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年8月8日 下午3:38:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.entity.message.HuanxinImGroupMemberEntity;

/**
 * <p>群成员信息</p>
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
public class GroupMembersVo extends HuanxinImGroupMemberEntity{

	
	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -4557158623278958452L;

	/**
	 * 角色展示
	 */
	private String memberRoleShow;
	
	/**
	 * 成员昵称
	 */
	private String memberNickName;
	
	/**
	 * 成员昵称
	 */
	private String memberMobile;

	/**
	 * @return the memberRoleShow
	 */
	public String getMemberRoleShow() {
		return memberRoleShow;
	}

	/**
	 * @param memberRoleShow the memberRoleShow to set
	 */
	public void setMemberRoleShow(String memberRoleShow) {
		this.memberRoleShow = memberRoleShow;
	}

	/**
	 * @return the memberNickName
	 */
	public String getMemberNickName() {
		return memberNickName;
	}

	/**
	 * @param memberNickName the memberNickName to set
	 */
	public void setMemberNickName(String memberNickName) {
		this.memberNickName = memberNickName;
	}

	/**
	 * @return the memberMobile
	 */
	public String getMemberMobile() {
		return memberMobile;
	}

	/**
	 * @param memberMobile the memberMobile to set
	 */
	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	
}
