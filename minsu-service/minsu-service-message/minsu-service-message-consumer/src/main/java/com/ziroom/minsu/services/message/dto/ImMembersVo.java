/**
 * @FileName: ImMembersVo.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年7月31日 下午12:07:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>查询群成员返回信息</p>
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
public class ImMembersVo extends BaseEntity {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 1843562729827662280L;

	/**
	 * 组员uid
	 */
	private String member;
	
	/**
	 * 群主
	 */
	private String owner;
	
	/**
	 * 成员角色  0=普通成员 1=管理员 2=群主
	 */
	private int memberRole;
	
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
	 * 成员状态 0=正常 1=禁言 2=黑名单
	 */
	private Integer memberStatu;
	
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

	/**
	 * @return the memberRole
	 */
	public int getMemberRole() {
		return memberRole;
	}

	/**
	 * @param memberRole the memberRole to set
	 */
	public void setMemberRole(int memberRole) {
		this.memberRole = memberRole;
	}

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
	 * @return the member
	 */
	public String getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(String member) {
		this.member = member;
	}
	
	
}
