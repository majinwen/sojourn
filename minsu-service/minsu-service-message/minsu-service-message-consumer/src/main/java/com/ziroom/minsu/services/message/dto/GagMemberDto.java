/**
 * @FileName: GagMemberDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年8月3日 下午7:20:44
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.valenum.msg.MemberStatuEnum;

/**
 * <p>禁言参数</p>
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
public class GagMemberDto extends BaseEntity{
	
	private static Logger logger = LoggerFactory.getLogger(GagMemberDto.class);

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -4293485968384031839L;

	/**
	 * 禁言人员列表
	 */
	private List<String> members ;
	
	/**
	 * 禁言的时长，单位是毫秒,必须是Integer或者Long类型  默认90天
	 */
	private Long muteDuration = 90*24*60*60*1000L;
	
	/**
	 * 群id
	 */
	private String groupId;

	/**
	 * 操作人 fid
	 */
	private String opFid;
	
	/**
	 * 操作人类型
	 */
	private Integer opType; 
	
	/**
	 * 禁言恢复时间
	 */
	private Date recoveryGagTime;
	
	/**
	 * 成员状态 0=正常 1=禁言 2=黑名单
	 */
	private Integer memberStatu = MemberStatuEnum.GAG.getCode();
	
	/**
	 * 是否删除
	 */
	private Integer isDel;
	
	/**
	 * 备注
	 */
	private String remark;
	
	public GagMemberDto(){
	    //计算禁言恢复时间
		Long  time = System.currentTimeMillis()+this.muteDuration;
		try {
			this.setRecoveryGagTime(DateUtil.parseDate(DateUtil.dateFormat(new Date(time), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			LogUtil.error( logger,"【禁言时间转化异常】muteDuration={},e={}",muteDuration, e);
		}
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
	 * @return the isDel
	 */
	public Integer getIsDel() {
		return isDel;
	}

	/**
	 * @param isDel the isDel to set
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
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
	 * @return the recoveryGagTime
	 */
	public Date getRecoveryGagTime() {
		return recoveryGagTime;
	}

	/**
	 * @param recoveryGagTime the recoveryGagTime to set
	 */
	public void setRecoveryGagTime(Date recoveryGagTime) {
		this.recoveryGagTime = recoveryGagTime;
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

	/**
	 * @return the muteDuration
	 */
	public Long getMuteDuration() {
		return muteDuration;
	}

	/**
	 * @param muteDuration the muteDuration to set
	 */
	public void setMuteDuration(Long muteDuration) {
		this.muteDuration = muteDuration;
	}
	
	
	
}
