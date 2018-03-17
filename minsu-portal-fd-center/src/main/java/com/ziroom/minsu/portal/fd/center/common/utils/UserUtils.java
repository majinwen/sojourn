/**
 * @FileName: UserUtils.java
 * @Package com.ziroom.minsu.portal.front.common.utils
 * 
 * @author jixd
 * @created 2016年7月24日 下午3:44:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.portal.fd.center.common.constant.Constant;

/**
 * <p>用户工具类</p>
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
public class UserUtils {

	/**
	 * 
	 * 获取当前环境rquest
	 *
	 * @author jixd
	 * @created 2016年7月24日 下午4:00:21
	 *
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 
	 * 获取当前环境session
	 *
	 * @author jixd
	 * @created 2016年7月24日 下午4:00:15
	 *
	 * @return
	 */
	public static HttpSession getSession(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
	/**
	 * 
	 * 获取当前会话 uid
	 *
	 * @author jixd
	 * @created 2016年7月24日 下午4:06:17
	 *
	 * @return
	 */
	public static String getCurrentUid(){
		HttpSession session =  getSession();
		String currentToken= (String) session.getAttribute(Constant.CURRENT_TOKEN);
		if(Check.NuNStr(currentToken)){
			return "";
		}
		String uid = (String) session.getAttribute(currentToken);
		if(Check.NuNStr(uid)){
			return "";
		}
		return uid;
	}
	
}
