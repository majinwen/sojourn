/**
 * @FileName: ConfigController.java
 * @Package com.ziroom.minsu.portal.front.common.controller
 * 
 * @author jixd
 * @created 2016年8月11日 下午2:33:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;

/**
 * <p>JS读取配置文件信息</p>
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
@RequestMapping("/config")
@Controller
public class ConfigController {
	
	//登陆接口
	@Value("#{'${SSO_USER_LOGIN_URL}'.trim()}")
	private String SSO_USER_LOGIN_URL;
	//注册接口
	@Value("#{'${SSO_USER_REGISTER_URL}'.trim()}")
	private String SSO_USER_REGISTER_URL;
	//首页地址
	@Value("#{'${PORTAL_FRONT_URL}'.trim()}")
	private String PORTAL_FRONT_URL;

	/**
	 * 
	 * 读取配置文件信息类
	 *
	 * @author jixd
	 * @created 2016年8月11日 下午2:34:22
	 *
	 * @return
	 */
	@RequestMapping("/msg")
	@ResponseBody
	public DataTransferObject getConfigMsg(){
		DataTransferObject dto = new DataTransferObject();
		JSONObject obj = new JSONObject();
		obj.put("PORTAL_FRONT_URL", PORTAL_FRONT_URL);
		obj.put("SSO_USER_LOGIN_URL", SSO_USER_LOGIN_URL);
		obj.put("SSO_USER_REGISTER_URL", SSO_USER_REGISTER_URL);
		dto.putValue("conf", obj);
		return dto;
	}
}
