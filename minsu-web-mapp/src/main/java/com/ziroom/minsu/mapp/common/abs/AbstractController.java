package com.ziroom.minsu.mapp.common.abs;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.mapp.common.header.Header;
import com.ziroom.minsu.mapp.common.interceptor.HeadersInterceptor;
import com.ziroom.minsu.mapp.common.interceptor.LoginInterceptor;
import com.ziroom.minsu.services.common.utils.IpUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>抽象的controller 基类. 提供各控制器公共方法调用</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/21.
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractController {

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    protected final String getUserId(final HttpServletRequest request) {
    	
    	String userID = (String) request.getAttribute(LoginInterceptor.USERID);
    	if (Check.NuNStr(userID)) {
    		userID = (String) request.getAttribute(HeadersInterceptor.USERID);
		}
        return userID;
    }
    

    /**
     * 获取token信息
     * @param request
     * @return
     */
    protected final String getToken(final HttpServletRequest request) {
        return (String) request.getAttribute(HeadersInterceptor.TOKEN);
    }
    
    /**
     * 获取请求头信息.
     * @param request
     * @return
     */
    protected final Header getHeader(final HttpServletRequest request) {
        return (Header) request.getAttribute(HeadersInterceptor.HEADER);
    }
    
    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (IpUtil.checkIp(ValueUtil.getStrValue(ip))){
            return ip;
        }else {
            return "";
        }
    }
    
}
