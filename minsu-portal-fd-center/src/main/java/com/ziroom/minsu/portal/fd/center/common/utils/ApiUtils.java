/**
 * @FileName: ApiUtils.java
 * @Package com.ziroom.minsu.portal.front.common.utils
 * 
 * @author jixd
 * @created 2016年7月28日 下午1:57:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.common.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.portal.fd.center.common.constant.HeaderParamName;
import com.ziroom.minsu.services.common.dto.ssoapi.LoginHeaderDto;

/**
 * <p>API请求相关接口</p>
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
public class ApiUtils {
	
	/**
	 * 
	 * 请求SSO接口需要的头部参数
	 *
	 * @author jixd
	 * @created 2016年7月28日 下午1:59:06
	 *
	 * @param request
	 * @param minsuWebAccept
	 * @param minsuWebSys
	 * @return
	 */
	public static Map<String, String> getHeaderMap(HttpServletRequest request,String minsuWebAccept,String minsuWebSys){
		Map<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);

		}
		LoginHeaderDto loginHeaderDto = new LoginHeaderDto();
		if(!Check.NuNStr(map.get("client-version")))
			loginHeaderDto.setClientVersion(map.get("client-version"));
		if(!Check.NuNStr(map.get("client-type")))
			loginHeaderDto.setClientType(Integer.valueOf(map.get("client-type")));
		if(!Check.NuNStr(map.get("user-agent")))
			loginHeaderDto.setUserAgent(map.get("user-agent"));

		if(Check.NuNStr(loginHeaderDto.getClientVersion())){
			loginHeaderDto.setClientVersion("1.0");
		}
		//pc
		if(Check.NuNObj(loginHeaderDto.getClientType())){
			loginHeaderDto.setClientType(4);
		}
		loginHeaderDto.setAccept(minsuWebAccept);
		loginHeaderDto.setSys(minsuWebSys);
		Map<String, String> headerMap = new HashMap<String, String>();

		headerMap.put(HeaderParamName.ACCEPT,loginHeaderDto.getAccept());
		headerMap.put(HeaderParamName.CLIENT_TYPE,loginHeaderDto.getClientType()+"");
		headerMap.put(HeaderParamName.CLIENT_VERSION, loginHeaderDto.getClientVersion());
		headerMap.put(HeaderParamName.REQUEST_ID, loginHeaderDto.getRequestId());
		headerMap.put(HeaderParamName.SYS, loginHeaderDto.getSys());
		headerMap.put(HeaderParamName.USER_AGENT, loginHeaderDto.getUserAgent());
		return headerMap;
	}
}
