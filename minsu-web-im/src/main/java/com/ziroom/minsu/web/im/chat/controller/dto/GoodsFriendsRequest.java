/**
 * @FileName: GoodsFriendsRequest.java
 * @Package com.ziroom.minsu.api.im.controller.dto
 * 
 * @author yd
 * @created 2016年9月18日 下午12:06:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller.dto;


/**
 * <p>好友列表请求参数</p>
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
public class GoodsFriendsRequest{

	
	/**
	 * 查询列表用户uid  要和当前登录用户uid一致，不然非法
	 */
	private String uid;
	
	/**
	 * 当前用户类型（1=房东 2=房客）
	 */
	private Integer senderType;

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the senderType
	 */
	public Integer getSenderType() {
		return senderType;
	}

	/**
	 * @param senderType the senderType to set
	 */
	public void setSenderType(Integer senderType) {
		this.senderType = senderType;
	}
	
	
}
