
package com.ziroom.minsu.services.basedata.dto;

import java.util.List;
import java.util.Map;

/**
 * <p>极光推送的服务类</p>
 * 推送平台 默认所有平台(0=所有默认 1=Ios 2=Android 3=Winphone)
 * 现在推送是所有平台
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
public class JpushRequest {

	/**
	 * 消息类型 100=通知  101=自定义消息
	 * 默认通知
	 * 具体类型  由客户端定
	 */
	private Integer messageType;
	/**
	 * 消息内容  当内容不为null时，就不去查模板了
	 */
	private String content;

	/**
	 * 消息标题 (最好给出)
	 */
	private String title;
	
	/**
	 * 消息编码
	 */
	private String smsCode;
	/**
	 * 用户uid  单个用户推送 必传
	 */
	private String uid;
	
	/**
	 * 多个用户推送必传
	 */
	private List<String> listUid;
	/**
	 * 推送替换参数
	 */
	private Map<String, String> paramsMap;
	
	/**
	 * 推送人群  0=所有  1=单个 2=多个
	 */
	private Integer jpushPersonType;
	/**
	 * 额外参数 键值对方式传送 (只有在自定义消息时 才有额外参数)
	 */
	private Map<String, String> extrasMap;

	public Map<String, String> getExtrasMap() {
		return extrasMap;
	}

	public void setExtrasMap(Map<String, String> extrasMap) {
		this.extrasMap = extrasMap;
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public Integer getJpushPersonType() {
		return jpushPersonType;
	}

	public void setJpushPersonType(Integer jpushPersonType) {
		this.jpushPersonType = jpushPersonType;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
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

	

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<String> getListUid() {
		return listUid;
	}

	public void setListUid(List<String> listUid) {
		this.listUid = listUid;
	}
	
	
}
