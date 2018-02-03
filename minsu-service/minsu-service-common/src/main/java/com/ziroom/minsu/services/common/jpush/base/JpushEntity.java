/**
 * @FileName: JpushEntity.java
 * @Package com.ziroom.minsu.services.common.jpush.base
 * 
 * @author yd
 * @created 2016年11月24日 下午4:32:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.jpush.base;

import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>极光推送实体</p>
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
public class JpushEntity extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 7047571269740479758L;
	
	/**
	 * 业务token
	 */
	private String token;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 消息内容
	 */
	private String content;
	
	/**
	 * t:透传（不显示，应用处理），all：所有用户，sigin：单个用户
	 */
	private String target;
	
	/**
	 * 消息目标URL taget为t时不传
	 */
	private String sendUrl;
	
	/**
	 * 终端类型 ，只支持极光通道(只能选小写android,ios)
	 */
	private String[] platform;

	/**
	 * 额外参数
	 */
	private Map<String, String> extras;
	
	/**
	 * 终端标识(目前用户UID)
	 */
	private String[] alias;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the sendUrl
	 */
	public String getSendUrl() {
		return sendUrl;
	}

	/**
	 * @param sendUrl the sendUrl to set
	 */
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	/**
	 * @return the platform
	 */
	public String[] getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String[] platform) {
		this.platform = platform;
	}

	/**
	 * @return the extras
	 */
	public Map<String, String> getExtras() {
		return extras;
	}

	/**
	 * @param extras the extras to set
	 */
	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}

	/**
	 * @return the alias
	 */
	public String[] getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String[] alias) {
		this.alias = alias;
	}
	
	
}
