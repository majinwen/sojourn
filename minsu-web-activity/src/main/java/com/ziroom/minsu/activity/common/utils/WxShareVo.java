/**
 * @FileName: WxShareVo.java
 * @Package com.ziroom.minsu.activity.common.utils
 * 
 * @author lunan
 * @created 2016年9月20日 下午5:48:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.common.utils;

import java.io.Serializable;

/**
 * <p>TODO</p>
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
public class WxShareVo implements Serializable{

	/** 序列化 id */
	private static final long serialVersionUID = 3388621878658640914L;
	
	/** 微信签名 */
	private String signature;
	
	/** 微信appId */
	private String appId;
	
	/** 生成签名时的随机字符串 */
	private String nonceStr;
	
	/** sha1加密 */
	private Object timestamp;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public Object getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Object timestamp) {
		this.timestamp = timestamp;
	}
	
	
	

}
