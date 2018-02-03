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

import com.asura.framework.base.entity.BaseEntity;


/**
 * <p>客户自定义消息回复VO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class MsgCustomizationVo extends BaseEntity{

	private static final long serialVersionUID = -4702328086357945982L;
	/**
	 * 逻辑id
	 */
	private String fid;

	/**
	 * 消息类型 1:系统 2:自定义
	 */
	private Integer msgType;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 用户id
	 */
	private String uid;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
