/**
 * @FileName: LandlordIntroduceVo.java
 * @Package com.ziroom.minsu.api.customer.entity
 * 
 * @author jixd
 * @created 2016年5月28日 下午4:31:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.customer.entity;

import java.io.Serializable;

/**
 * <p>房东的个人主页信息</p>
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
public class LandlordIntroduceVo implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -9135298676793728191L;
	/**
	 * 头像url
	 */
	private String headPicUrl;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 评价分数
	 */
	private float  eva;
	/**
	 * 自我介绍
	 */
	private String introduce;
	
	public String getHeadPicUrl() {
		return headPicUrl;
	}
	public void setHeadPicUrl(String headPicUrl) {
		this.headPicUrl = headPicUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public float getEva() {
		return eva;
	}
	public void setEva(float eva) {
		this.eva = eva;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
}
