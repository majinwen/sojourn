/**
 * @FileName: AppMsgBaseVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2016年9月22日 下午4:16:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
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
public class AppMsgBaseVo extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -7312736595580464992L;

	/**
	 * 编号
	 */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 消息体（即：消息内容）
     */
    private String msgContent;

    /**
     * 消息发送人类型（1=房东 2=房客）
     */
    private Integer msgSenderType;

    /**
     * 单条记录是否删除 默认0(0：不删除 1：删除)
     */
    private Integer isDel;

    /**
     * 房源留言关联表fid
     */
    private String msgHouseFid;

    /**
     * 是否已读 默认0（0：已读 1：未读）
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 发送人uid
     */
    private String froms;
    
    /**
     * 接收人uid
     */
    private String tos;
    
    /**
     * 房源或房间或床位 fid
     */
    private String houseFid;
    
    /**
     * 出租类型 0：整租，1：合租，2：床位
     */
    private Integer rentWay;
    
    /**
     * IM消息发送时间
     */
    private Date msgSendTime;
    

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
	 * @return the msgSenderType
	 */
	public Integer getMsgSenderType() {
		return msgSenderType;
	}

	/**
	 * @param msgSenderType the msgSenderType to set
	 */
	public void setMsgSenderType(Integer msgSenderType) {
		this.msgSenderType = msgSenderType;
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
	 * @return the isRead
	 */
	public Integer getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
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
	 * @return the froms
	 */
	public String getFroms() {
		return froms;
	}

	/**
	 * @param froms the froms to set
	 */
	public void setFroms(String froms) {
		this.froms = froms;
	}

	/**
	 * @return the tos
	 */
	public String getTos() {
		return tos;
	}

	/**
	 * @param tos the tos to set
	 */
	public void setTos(String tos) {
		this.tos = tos;
	}
    
    
    
}
