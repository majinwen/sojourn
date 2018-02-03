/**
 * @FileName: MsgFirstAdvisoryEntity.java
 * @Package com.ziroom.minsu.entity.message
 * 
 * @author yd
 * @created 2017年4月8日 上午11:07:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.entity.message;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>首次咨询执行 实体</p>
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
public class MsgFirstAdvisoryEntity extends BaseEntity{


	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -5712339498616939036L;

	/**
	 * 编号
	 */
	private Integer id;

	/**
	 * 业务编号
	 */
	private String fid;
	
	/**
	 * 消息主表fid
	 */
	private String msgHouseFid;
	
	/**
	 * 消息记录fid
	 */
	private String msgBaseFid;
	
	/**
	 * 发送人uid
	 */
	private String fromUid;
	/**
	 * 接收人uid
	 */
	private String toUid;
	
	/**
	 * 执行时间
	 */
	private Date runtime;

	/**
	 * 环信消息的发送时间
	 */
	private Date msgSendTime;

	/**
	 * 执行状态 （0=未执行 1=执行成功 2=执行失败）
	 */
	private Integer status;
	
	/**
	 * 10：未跟进；20：跟进中；30：跟进结束；默认值为10
	 */
	private Integer followStatus;
	
	/**
	 * 老状态 用于更新操作
	 */
	transient private Integer oldStatus;

	/**
	 * 首次咨询消息发送内容
	 */
	private String msgContent;
	
	/**
	 * 首次咨询消息扩展内容
	 */
	private String msgContentExt;

	/**
	 * 房源fid或房间fid或床位fid
	 */
	private String houseFid;

	/**
	 * 出租类型 0：整租，1：合租，2：床位
	 */
	private Integer rentWay;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 执行备注（如果失败 是失败的原因）
	 */
	private String remark;
	
	/**
	 * 最后修改时间
	 */
	private Date lastModifyDate;

	private Integer roleType;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the msgHouseFid
	 */
	public String getMsgHouseFid() {
		return msgHouseFid;
	}

	/**
	 * @param msgHouseFid the msgHouseFid to set
	 */
	public void setMsgHouseFid(String msgHouseFid) {
		this.msgHouseFid = msgHouseFid;
	}

	/**
	 * @return the msgBaseFid
	 */
	public String getMsgBaseFid() {
		return msgBaseFid;
	}

	/**
	 * @param msgBaseFid the msgBaseFid to set
	 */
	public void setMsgBaseFid(String msgBaseFid) {
		this.msgBaseFid = msgBaseFid;
	}

	/**
	 * @return the fromUid
	 */
	public String getFromUid() {
		return fromUid;
	}

	/**
	 * @param fromUid the fromUid to set
	 */
	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}

	/**
	 * @return the toUid
	 */
	public String getToUid() {
		return toUid;
	}

	/**
	 * @param toUid the toUid to set
	 */
	public void setToUid(String toUid) {
		this.toUid = toUid;
	}

	/**
	 * @return the runtime
	 */
	public Date getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(Date runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the msgSendTime
	 */
	public Date getMsgSendTime() {
		return msgSendTime;
	}

	/**
	 * @param msgSendTime the msgSendTime to set
	 */
	public void setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the msgContent
	 */
	public String getMsgContent() {
		return msgContent;
	}

	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	/**
	 * @return the msgContentExt
	 */
	public String getMsgContentExt() {
		return msgContentExt;
	}

	/**
	 * @param msgContentExt the msgContentExt to set
	 */
	public void setMsgContentExt(String msgContentExt) {
		this.msgContentExt = msgContentExt;
	}

	/**
	 * @return the houseFid
	 */
	public String getHouseFid() {
		return houseFid;
	}

	/**
	 * @param houseFid the houseFid to set
	 */
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the lastModifyDate
	 */
	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	/**
	 * @param lastModifyDate the lastModifyDate to set
	 */
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Integer getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(Integer followStatus) {
		this.followStatus = followStatus;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
}
