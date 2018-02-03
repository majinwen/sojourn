/**
 * @FileName: JpushConfig.java
 * @Package com.ziroom.minsu.services.common.jpush
 * 
 * @author yd
 * @created 2016年4月14日 下午3:22:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.jpush.base;

import java.util.Map;

import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.audience.Audience;

/**
 * <p>极光推送配置</p>
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
public class JpushConfig {
	
	/**
	 * 自如平台 极光token
	 */
	public static final String JPUSH_TOKEN = SystemGlobalsUtils.getValue("JPUSH_TOKEN");

	/**
	 * 极光的APP_KEY
	 */
	//public static final String APP_KEY ="c016951ed2e400cfe286bd2b";// 测试
	public static final String APP_KEY =SystemGlobalsUtils.getValue("JPUSH_APP_KEY");
	/**
	 * 极光的MASTER_SECRET
	 */
	//public static final String MASTER_SECRET = "25adf4a89a2864cef0dcabc3";//测试
	public static final String MASTER_SECRET = SystemGlobalsUtils.getValue("JPUSH_MASTER_SECRET");

	/**
	 * 推送平台 默认所有平台(Ios Android Winphone)
	 */
	private Platform platform;

	/**
	 * 推送用户 
	 */
	private Audience audience;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 消息标题 (最好给出)
	 */
	private String title;

	/**
	 * 消息类型 100=通知  101=自定义消息
	 */
	private Integer messageType;
	
	/**
	 * 额外参数 键值对方式传送
	 */
	private Map<String, String> extrasMap;
	
	/**
	 * 终端标识(目前用户UID)
	 */
	private String[] alias;

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
	/**
	 * 默认构造器  默认通知类型
	 */
	public JpushConfig(){
		this.platform = Platform.all();
		this.audience = Audience.all();
		this.messageType = MessageTypeEnum.NOTIFICATION.getCode();
	}
	/**
	 * 构造内容  默认通知类型
	 * @param content
	 */
	public JpushConfig(String content){

		this.platform = Platform.all();
		this.audience = Audience.all();
		this.messageType = MessageTypeEnum.NOTIFICATION.getCode();
		this.content = content;

	}

	/**
	 * 构造标题 和 内容  默认通知类型
	 * @param title
	 * @param content
	 */
	public JpushConfig(String title,String content){

		this.platform = Platform.all();
		this.audience = Audience.all();
		this.messageType = MessageTypeEnum.NOTIFICATION.getCode();
		this.title = title;
		this.content = content;

	}
	/**
	 * 构造平台  用户  内容  默认通知类型
	 * @param platform
	 * @param audience
	 * @param content
	 */
	public JpushConfig(Platform platform, Audience audience,String content){
		this.platform = platform;
		this.audience = audience;
		this.content = content;
		this.title = content;
		this.messageType = MessageTypeEnum.NOTIFICATION.getCode();
	}

	/**
	 * 构造标题  平台  用户  内容  默认通知类型
	 * @param title
	 * @param platform
	 * @param audience
	 * @param content
	 */
	public JpushConfig(String title,Platform platform, Audience audience,String content){
		this.platform = platform;
		this.audience = audience;
		this.content = content;
		this.title = title;
		this.messageType = MessageTypeEnum.NOTIFICATION.getCode();
	}

	/**
	 * 构造标题  平台  用户  内容  通知类型
	 * @param title
	 * @param platform
	 * @param audience
	 * @param content
	 * @param messageType
	 */
	public JpushConfig(String title,Platform platform, Audience audience,String content,Integer messageType){
		this.platform = platform;
		this.audience = audience;
		this.content = content;
		this.title = title;
		this.messageType =messageType;
	}
	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Audience getAudience() {
		return audience;
	}

	public void setAudience(Audience audience) {
		this.audience = audience;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public Map<String, String> getExtrasMap() {
		return extrasMap;
	}
	public void setExtrasMap(Map<String, String> extrasMap) {
		this.extrasMap = extrasMap;
	}


	

}
