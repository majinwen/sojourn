package com.ziroom.minsu.web.im.common.controller.abs;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.utils.IpUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.web.im.common.header.Header;
import com.ziroom.minsu.web.im.common.interceptor.HeadersInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>抽象的controller 基类. 提供各控制器公共方法调用.</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/15.
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractController {


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
     * 获取用户信息
     * @param request
     * @return
     */
    protected final String getUserId(final HttpServletRequest request) {
        return (String) request.getAttribute(HeadersInterceptor.USERID);
    }



    /**
     * 获取用户ID.
     * @param request
     * @return
     */
    protected Long getUserIdByRequest(final HttpServletRequest request) {
        final Object userId = request.getAttribute("userid");
        if (Check.NuNObj(userId)) {
            return 0L;
        }
        return (Long) userId;
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
