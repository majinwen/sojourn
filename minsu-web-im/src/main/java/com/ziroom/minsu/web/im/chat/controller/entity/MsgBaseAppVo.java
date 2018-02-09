/**
 * @FileName: MsgBaseAppVo.java
 * @Package com.ziroom.minsu.web.im.chat.controller.entity
 * 
 * @author yd
 * @created 2016年9月22日 下午2:24:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller.entity;

import java.io.Serializable;

import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;

/**
 * <p>返回app端的vo类</p>
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
public class MsgBaseAppVo implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 23077700884715495L;

	/**
	 * 消息体（即：消息内容）
	 */
	private String msgContent;

	/**
	 * 消息发送人类型（1=房东 2=房客）
	 */
	private Integer msgSenderType;
	/**
	 * 创建时间 字符串形式
	 */
	private String createTime;
	
	/**
	 * 发送人uid
	 */
	private String froms;
	
	/**
	 * 接收人uid
	 */
	private String tos;
	
	/**
	 * 房源或房间fid
	 */
	private String fid;

	/**
	 * 出租方式 0=整租  1 = 分租
	 */
	private Integer rentWay;
	

	private AppChatRecordsExt appChatRecordsExt;
	
	/**
	 * 环信 消息发送时间
	 */
	private String msgSendTime;
	

	/**
	 * @return the msgSendTime
	 */
	public String getMsgSendTime() {
		return msgSendTime;
	}

	/**
	 * @param msgSendTime the msgSendTime to set
	 */
	public void setMsgSendTime(String msgSendTime) {
		this.msgSendTime = msgSendTime;
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
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the appChatRecordsExt
	 */
	public AppChatRecordsExt getAppChatRecordsExt() {
		return appChatRecordsExt;
	}

	/**
	 * @param appChatRecordsExt the appChatRecordsExt to set
	 */
	public void setAppChatRecordsExt(AppChatRecordsExt appChatRecordsExt) {
		this.appChatRecordsExt = appChatRecordsExt;
	}



}
