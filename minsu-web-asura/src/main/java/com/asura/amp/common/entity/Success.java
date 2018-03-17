/**
 * @FileName: Success.java
 * @Package com.asura.amp.common.entity
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.entity;

/**
 * <p>Ajax返回成功Json数据格式</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class Success extends BaseAjaxValue {
	
	/**
	 * 成功CODE
	 */
	private String statusCode = CODE_SUCCESS;
	
	/**
	 * 默认消息
	 */
	private String message = "操作成功";
	
	/**
	 * 返回需操作的tabId
	 */
	private String navTabId;
	
	/**
	 * 返回需操作的divId
	 */
	private String rel;
	
	/**
	 * 返回后回调函数，只有callbackType="forward"时需要forwardUrl值
	 */
	private String callbackType;
	
	/**
	 * 返回后执行跳转，需设置callbackType="forward"
	 */
	private String forwardUrl;
	
	/**
	 * 默认构造函数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:27:44
	 */
	public Success() {
		//this.statusCode = CODE_SUCCESS;
		//this.message = "操作成功";
		this.navTabId = "";
		this.rel = "";
		this.callbackType = "";
		this.forwardUrl = "";
	}
	
	/**
	 * 默认构造函数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:27:44
	 * 
	 * @param message 消息内容
	 */
	public Success(String message) {
		//this.statusCode = CODE_SUCCESS;
		this.message = message;
		this.navTabId = "";
		this.rel = "";
		this.callbackType = "";
		this.forwardUrl = "";
	}
	
	/**
	 * 默认构造函数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:27:44
	 * 
	 * @param message		消息内容
	 * @param navTabId		导航tabId
	 * @param rel			divId
	 * @param callbackType	回调函数
	 * @param forwardUrl	跳转URL
	 */
	public Success(String message, String navTabId, String rel, String callbackType, String forwardUrl) {
		//this.statusCode = CODE_SUCCESS;
		this.message = message;
		this.navTabId = navTabId;
		this.rel = rel;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
	}

	/**
	 * 获取状态码
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:29:44
	 *
	 * @return 状态码
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * 设置状态码
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:27:44
	 *
	 * @param statusCode 状态码
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 获取消息内容
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:30:48
	 *
	 * @return 消息内容
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置消息内容
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:31:10
	 *
	 * @param message 消息内容
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取导航tabId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:31:39
	 *
	 * @return 导航tabId
	 */
	public String getNavTabId() {
		return navTabId;
	}

	/**
	 * 设置导航tabId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:32:03
	 *
	 * @param navTabId 导航tabId
	 */
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	/**
	 * 获取divId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:32:21
	 *
	 * @return divId
	 */
	public String getRel() {
		return rel;
	}

	/**
	 * 设置divId
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:32:38
	 *
	 * @param rel divId
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}

	/**
	 * 获取回调函数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:32:50
	 *
	 * @return 回调函数
	 */
	public String getCallbackType() {
		return callbackType;
	}

	/**
	 * 设置回调函数
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:33:06
	 *
	 * @param callbackType 回调函数
	 */
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	/**
	 * 获取跳转URL
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:33:28
	 *
	 * @return 跳转URL
	 */
	public String getForwardUrl() {
		return forwardUrl;
	}

	/**
	 * 设置跳转URL
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午5:33:43
	 *
	 * @param forwardUrl 跳转URL
	 */
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
}
