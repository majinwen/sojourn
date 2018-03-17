
package com.ziroom.minsu.mapp.common.entity;

import java.io.Serializable;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>app 端请求参数</p>
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
public class AppRequest extends BaseEntity implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 1718972973551977439L;

	/**
	 * 加密的uid
	 */
	private String uid;
	
	/**
	 * token
	 */
	private String token;
	
	/**
	 * 请求来源
	 */
	private Integer sourceType;
	
	/**
	 * 请求时间戳
	 */
	private String timestamp;
	
	/**
	 * 随机串
	 */
	private String echostr;
	
	/**
	 * 请求签名
	 */
	private String signature;



	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
}
