/**
 * @FileName: UpsConfig.java
 * @Package org.eclipse.jdt.ui
 * 
 * @author bushujie
 * @created 2017年10月24日 下午2:39:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.entity;

/**
 * <p>权限相关配置</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class UpsConfig {
	
	/**
	 * 系统标识
	 */
	private String systemCode;
	
	/**
	 * 登录用户信息接口url
	 */
	private String userMsgApi;
	
	 /**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @return the userMsgApi
	 */
	public String getUserMsgApi() {
		return userMsgApi;
	}

	/**
	 * @param userMsgApi the userMsgApi to set
	 */
	public void setUserMsgApi(String userMsgApi) {
		this.userMsgApi = userMsgApi;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
}
