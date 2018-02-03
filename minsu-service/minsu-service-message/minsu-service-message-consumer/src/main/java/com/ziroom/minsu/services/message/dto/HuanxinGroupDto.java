/**
 * @FileName: HuanxinGroupDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年7月28日 下午2:26:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <p>环信 添加群组 请求参数</p>
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
public class HuanxinGroupDto implements Serializable{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 6193588713636277164L;
	
	/**
	 * 群主id
	 */
	private String groupId;

	/**
	 * 群组名称，此属性为必须的
	 */
	private  String groupname;
	
	/**
	 * 群组描述，此属性为必须的
	 */
	private  String desc;
	
	/**
	 * 是否是公开群，此属性为必须的
	 */
	private boolean isPublic=true;
	
	/**
	 * 群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
	 */
	private Integer maxusers=500;
	
	/**
	 * 加入群是否需要群主或者群管理员审批，默认是false
	 */
	private boolean membersOnly = false;
	
	/**
	 * 是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主或者管理员才可以往群里加人
	 */
	private boolean allowinvites = true;
	
	/**
	 * 群组的管理员，此属性为必须的
	 */
	private String owner;
	
	/**
	 * 群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）
	 */
	private List<String> members;
	
	/**
	 * 邀请加群，被邀请人是否需要确认。如果是true，表示邀请加群需要被邀请人确认；如果是false，表示不需要被邀请人确认，直接将被邀请人加入群。 该字段的默认值为true。
	 */
	private boolean inviteNeedConfirm = false;
	
	/**
	 * 操作人 fid
	 */
	private String opFid;
	
	/**
	 * 操作人类型
	 */
	private Integer opType;
	
	/**
	 * 项目编号
	 */
	private String projectBid;
	
	/**
	 * 是否是默认群组  0=是  1=不是
	 */
	private Integer isDefault;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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
	 * @return the inviteNeedConfirm
	 */
	public boolean isInviteNeedConfirm() {
		return inviteNeedConfirm;
	}

	/**
	 * @param inviteNeedConfirm the inviteNeedConfirm to set
	 */
	public void setInviteNeedConfirm(boolean inviteNeedConfirm) {
		this.inviteNeedConfirm = inviteNeedConfirm;
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
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the isPublic
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 * @param isPublic the isPublic to set
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
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

	/**
	 * @return the membersOnly
	 */
	public boolean isMembersOnly() {
		return membersOnly;
	}

	/**
	 * @param membersOnly the membersOnly to set
	 */
	public void setMembersOnly(boolean membersOnly) {
		this.membersOnly = membersOnly;
	}

	/**
	 * @return the allowinvites
	 */
	public boolean isAllowinvites() {
		return allowinvites;
	}

	/**
	 * @param allowinvites the allowinvites to set
	 */
	public void setAllowinvites(boolean allowinvites) {
		this.allowinvites = allowinvites;
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

	
	
}
