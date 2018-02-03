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

import java.util.Date;

/**
 * <p>聊天消息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class MsgChatVo extends BaseEntity{


	private static final long serialVersionUID = 5599899314842676095L;

	/**
	 * 房客uid
	 */
	private String tenantUid;
	/**
	 * 房东uid
	 */
	private String landlordUid;
	/**
	 * 发送人类型
	 */
	private Integer msgSenderType;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String headPic;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 发送时间
	 */
	private Date msgSendTime;

	public String getTenantUid() {
		return tenantUid;
	}

	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public Integer getMsgSenderType() {
		return msgSenderType;
	}

	public void setMsgSenderType(Integer msgSenderType) {
		this.msgSenderType = msgSenderType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getMsgSendTime() {
		return msgSendTime;
	}

	public void setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
	}
}
