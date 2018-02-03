/**
 * @FileName: MsgUserInfoVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author loushuai
 * @created 2017年9月18日 下午5:58:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

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
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class MsgUserInfoVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7605562072590281677L;
	
	/**
	 * uid
	 */
	private String uid;
	
	/**
	 * 客户头像URL地址
	 */
	private String userPicUrl;
	
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 用户活跃度
	 */
	private String liveness;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLiveness() {
		return liveness;
	}

	public void setLiveness(String liveness) {
		this.liveness = liveness;
	}

}
