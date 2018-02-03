/**
 * @FileName: Chat.java
 * @Package com.ziroom.minsu.web.im.chat.controller.dto
 * 
 * @author yd
 * @created 2016年9月20日 下午10:04:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;
import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>app 保存消息 请求参数</p>
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
public class AppChatRecordsDto extends BaseEntity {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -733832134742552081L;

	/**
	 * 房客uid
	 */
	private String tenantUid;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 消息内容   {环信嘻嘻分2部分  A. 消息内容部分  B.消息扩展信息部分}
	 */
	private String msgContent;
	
	/**
	 * 消息发送人类型（1=房东 2=房客 100=房东的环信IM消息  200=房客的环信IM消息）
	 */
	private Integer msgSentType;
	
	/**
	 * 发送次数
	 */
	private String sendNum;
	
	/**
	 * 极光标识
	 */
	private String jpushFlag;

	/**
	 * 环信消息发送时间
	 */
	private Date msgSendTime;
	/**
	 * IM扩展信息
	 */
	private AppChatRecordsExt appChatRecordsExt;
	
	/**
	 * 上传图片消息地址，在上传图片成功后会返回UUID 或 上传语音远程地址，在上传语音后会返回UUID
	 */
	private String url;

	
	/**
	 * secret在上传图片后会返回，只有含有secret才能够下载此图片 或 secret在上传文件后会返回
	 */
	private String secret;
	
	/**
	 * 语音名称 或 图片名称
	 */
	private String filename;
	
	/**
	 * 图片附件大小 或 语音附件大小（单位：字节）
	 */
	private Integer fileLength;
	
	/**
	 * 环信消息类型txt: 文本消息；img: 图片；loc: 位置；audio: 语音
	 */
	private String type;
	
	/**
	 * 图片尺寸
	 */
	private String size;
	
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

	/**
	 * @return the tenantUid
	 */
	public String getTenantUid() {
		return tenantUid;
	}

	/**
	 * @param tenantUid the tenantUid to set
	 */
	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
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
	 * @return the msgSentType
	 */
	public Integer getMsgSentType() {
		return msgSentType;
	}

	/**
	 * @param msgSentType the msgSentType to set
	 */
	public void setMsgSentType(Integer msgSentType) {
		this.msgSentType = msgSentType;
	}

	/**
	 * @return the sendNum
	 */
	public String getSendNum() {
		return sendNum;
	}

	/**
	 * @param sendNum the sendNum to set
	 */
	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	/**
	 * @return the jpushFlag
	 */
	public String getJpushFlag() {
		return jpushFlag;
	}

	/**
	 * @param jpushFlag the jpushFlag to set
	 */
	public void setJpushFlag(String jpushFlag) {
		this.jpushFlag = jpushFlag;
	}


	public Date getMsgSendTime() {
		return msgSendTime;
	}

	public void setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getFileLength() {
		return fileLength;
	}

	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}

