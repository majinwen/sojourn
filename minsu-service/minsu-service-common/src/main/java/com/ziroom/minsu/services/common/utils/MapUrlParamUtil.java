/**
 * @FileName: MapUrlParamUtil.java
 * @Package com.ziroom.minsu.services.common.utils
 * 
 * @author bushujie
 * @created 2017年2月8日 上午10:05:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>map和url参数转换</p>
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
public class MapUrlParamUtil {
	    /** 
	* 将url参数转换成map 
	* @param param aa=11&bb=22&cc=33 
	* @return 
	*/  
	public static Map<String, Object> getUrlParams(String param) {  
		Map<String, Object> map = new HashMap<String, Object>(0);  
		if (StringUtils.isBlank(param)) {  
		    return map;  
		}  
		String[] params = param.split("&");  
		for (int i = 0; i < params.length; i++) {  
		    String[] p = params[i].split("=");  
		    if (p.length == 2) {  
		        map.put(p[0], p[1]);  
		    }  
		}  
		return map;  
	}  
	
	/** 
	* 将map转换成url 
	* @param map 
	* @return 
	*/  
	public static String getUrlParamsByMap(Map<String, Object> map) {  
		if (map == null) {  
		    return "";  
		}  
		StringBuffer sb = new StringBuffer();  
		for (Map.Entry<String, Object> entry : map.entrySet()) {  
		    sb.append(entry.getKey() + "=" + entry.getValue());  
		    sb.append("&");  
		}  
		String s = sb.toString();  
		if (s.endsWith("&")) {  
		    s = StringUtils.substringBeforeLast(s, "&");  
		}  
		return s;  
	} 
}
