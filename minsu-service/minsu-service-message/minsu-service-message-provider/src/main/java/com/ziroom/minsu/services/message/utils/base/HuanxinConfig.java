/**
 * @FileName: HuanxinConfig.java
 * @Package com.ziroom.minsu.services.message.utils.base
 * 
 * @author yd
 * @created 2016年9月20日 上午9:59:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.utils.base;

/**
 * <p>环信配置信息</p>
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
public class HuanxinConfig {

	
	/**
	 * 环信应用标识(AppKey)
	 */
	private String huanxinAppKey;
	
	/**
	 * 环信Client Id
	 */
	private String huanxinClientId;
	
	/**
	 * 环信 Client Secret
	 */
	private String huanxinClientSecret;
	
	/**
	 * 环信域名
	 */
	private String huanxinDomain;
	
	/**
	 * 环信获取token的url
	 */
	private String huanxinTokenUrl;
	
	/**
	 * 获取聊天记录请求
	 */
	private String huanxinChatMessageUrl;
	
	/**
	 * 自如网IM聊天 民宿标示
	 */
	private String imMinsuFlag;
	
	/**
	 * 同步环信聊天记录环境标识 （minsu_d  minsu_t  minsu_q minsu_online）
	 */
	private String domainFlag;  
	
	/**
	 * 向环信发送消息URL
	 */
	private String huanxinSendImMsgUrl;
	

	/**
	 * @return the huanxinSendImMsgUrl
	 */
	public String getHuanxinSendImMsgUrl() {
		return huanxinSendImMsgUrl;
	}

	/**
	 * @param huanxinSendImMsgUrl the huanxinSendImMsgUrl to set
	 */
	public void setHuanxinSendImMsgUrl(String huanxinSendImMsgUrl) {
		this.huanxinSendImMsgUrl = huanxinSendImMsgUrl;
	}

	/**
	 * @return the domainFlag
	 */
	public String getDomainFlag() {
		return domainFlag;
	}

	/**
	 * @param domainFlag the domainFlag to set
	 */
	public void setDomainFlag(String domainFlag) {
		this.domainFlag = domainFlag;
	}

	/**
	 * @return the huanxinAppKey
	 */
	public String getHuanxinAppKey() {
		return huanxinAppKey;
	}

	/**
	 * @param huanxinAppKey the huanxinAppKey to set
	 */
	public void setHuanxinAppKey(String huanxinAppKey) {
		this.huanxinAppKey = huanxinAppKey;
	}

	/**
	 * @return the huanxinClientId
	 */
	public String getHuanxinClientId() {
		return huanxinClientId;
	}

	/**
	 * @param huanxinClientId the huanxinClientId to set
	 */
	public void setHuanxinClientId(String huanxinClientId) {
		this.huanxinClientId = huanxinClientId;
	}

	/**
	 * @return the huanxinClientSecret
	 */
	public String getHuanxinClientSecret() {
		return huanxinClientSecret;
	}

	/**
	 * @param huanxinClientSecret the huanxinClientSecret to set
	 */
	public void setHuanxinClientSecret(String huanxinClientSecret) {
		this.huanxinClientSecret = huanxinClientSecret;
	}

	/**
	 * @return the huanxinDomain
	 */
	public String getHuanxinDomain() {
		return huanxinDomain;
	}

	/**
	 * @param huanxinDomain the huanxinDomain to set
	 */
	public void setHuanxinDomain(String huanxinDomain) {
		this.huanxinDomain = huanxinDomain;
	}

	/**
	 * @return the huanxinTokenUrl
	 */
	public String getHuanxinTokenUrl() {
		return huanxinTokenUrl;
	}

	/**
	 * @param huanxinTokenUrl the huanxinTokenUrl to set
	 */
	public void setHuanxinTokenUrl(String huanxinTokenUrl) {
		this.huanxinTokenUrl = huanxinTokenUrl;
	}

	/**
	 * @return the huanxinChatMessageUrl
	 */
	public String getHuanxinChatMessageUrl() {
		return huanxinChatMessageUrl;
	}

	/**
	 * @param huanxinChatMessageUrl the huanxinChatMessageUrl to set
	 */
	public void setHuanxinChatMessageUrl(String huanxinChatMessageUrl) {
		this.huanxinChatMessageUrl = huanxinChatMessageUrl;
	}

	/**
	 * @return the imMinsuFlag
	 */
	public String getImMinsuFlag() {
		return imMinsuFlag;
	}

	/**
	 * @param imMinsuFlag the imMinsuFlag to set
	 */
	public void setImMinsuFlag(String imMinsuFlag) {
		this.imMinsuFlag = imMinsuFlag;
	}
	
	
}
