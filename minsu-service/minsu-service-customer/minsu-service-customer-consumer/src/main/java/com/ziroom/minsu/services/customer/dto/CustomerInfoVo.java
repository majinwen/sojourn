/**
 * @FileName: CustomerInfoVo.java
 * @Package com.ziroom.minsu.services.customer.dto
 * 
 * @author yd
 * @created 2017年8月4日 下午6:48:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

import java.io.Serializable;

/**
 * <p>用户信息</p>
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
public class CustomerInfoVo implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 1440246969768910511L;
	
	/**
	 * uid
	 */
	private  String  uid;
	
	/**
	 * 昵称
	 */
	private  String nickName;
	
	/**
	 * 头像
	 */
	private String headImg;

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
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the headImg
	 */
	public String getHeadImg() {
		return headImg;
	}

	/**
	 * @param headImg the headImg to set
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	

}
