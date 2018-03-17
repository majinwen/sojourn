package com.ziroom.minsu.portal.fd.center.house.vo;

import java.io.Serializable;

/**
 * <p>
 * PC房东个人信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年7月22日
 * @since 1.0
 * @version 1.0
 */
public class LandlordIntroducePcVo implements Serializable {

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

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
