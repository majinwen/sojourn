/**
 * @FileName: GroupMemberDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年7月31日 上午9:45:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>添加群组 多成员 dto</p>
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
public class GroupMemberDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 8222917930415052657L;
	
	/**
	 * 群组id
	 */
	private String groupId;
	
	/**
	 * 群成员uid
	 */
	private  List<String> members;
	
	/**
	 * 操作人 fid
	 */
	private String opFid;
	
	/**
	 * 操作人类型
	 */
	private Integer opType; 
	
	/**
	 * 备注
	 */
	private  String remark;
	
	/**
	 * 项目bid
	 */
	private String projectBid;
	
	/**
	 * 1=自如驿后台添加群组 2=自如驿下单 3=自如驿退房 4=自如驿申请退款成功 这几个来源存入 操作日志  0=不需要重新添加
	 */
	private Integer sourceType = 0;
	
	/**
	 * 群成员角色 0=普通成员 1=管理员 2=群主
	 */
	private Integer memberRole;

	/**
	 * 群成员状态  0=正常 1=禁言 2=黑名单
	 */
	private Integer memberStatu;
	
	/**
	 * 手机号集合
	 */
	private List<String> mobiles;
	
	/**
	 * 群名称
	 */
	private String groupName;
	
	
	/**
	 * uid 和 手机号 对应的map
	 */
	private Map<String, String> uidMobileMap = new HashMap<String, String>();
	
	/**
	 * @return the uidMobileMap
	 */
	public Map<String, String> getUidMobileMap() {
		return uidMobileMap;
	}

	/**
	 * @param uidMobileMap the uidMobileMap to set
	 */
	public void setUidMobileMap(Map<String, String> uidMobileMap) {
		this.uidMobileMap = uidMobileMap;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the mobiles
	 */
	public List<String> getMobiles() {
		return mobiles;
	}

	/**
	 * @param mobiles the mobiles to set
	 */
	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
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
	 * @return the sourceType
	 */
	public Integer getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
