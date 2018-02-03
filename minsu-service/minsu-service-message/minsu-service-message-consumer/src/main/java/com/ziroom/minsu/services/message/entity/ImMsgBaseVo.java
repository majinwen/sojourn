/**
 * @FileName: ImMsgBaseVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2017年4月1日 下午3:51:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>im消息记录vo</p>
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
public class ImMsgBaseVo extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 5741568514211984329L;
	
	/**
	 * 消息体（即：消息内容）
	 */
	private String msgContent;


	/**
	 * 消息体（即：消息内容 不包含扩展消息)
	 */
	private String msgRealContent;

	/**
	 * 环信消息的发送时间
	 */
	private Date msgSendTime;
	
	/**
	 * 消息发送人类型（10=房东 20=房客）
	 */
	private Integer msgSenderType;
	

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
	 * @return the msgRealContent
	 */
	public String getMsgRealContent() {
		return msgRealContent;
	}

	/**
	 * @param msgRealContent the msgRealContent to set
	 */
	public void setMsgRealContent(String msgRealContent) {
		this.msgRealContent = msgRealContent;
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
	
	

}
