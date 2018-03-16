package com.ziroom.minsu.api.search.controller.abs;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.api.search.common.interceptor.HeadersInterceptor;
import com.ziroom.minsu.api.search.common.interceptor.ParamCollector;
import com.ziroom.minsu.services.common.utils.IpUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
     * 将接收的参数转换为指定对象.
     * @param request
     * @param tClass
     * @return
     */
    protected final <T extends BaseEntity> T getEntity(final HttpServletRequest request, final Class<T> tClass) {
        final String params = (String) request.getAttribute(ParamCollector.PARAMS);
        if (Check.NuNStrStrict(params)) {
            return null;
        }
        try {
            return JsonEntityTransform.json2Entity(params, tClass);
        }catch (Exception e){
            return null;
        }
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
     * 获取用户唯一标识
     * @param request
     * @return
     */
    protected final String getUserKey(final HttpServletRequest request) {

        String uid = this.getUserId(request);
        if (!Check.NuNStr(uid)) return uid;

        Header header = this.getHeader(request);
        if (!Check.NuNObj(header)){
            if (!Check.NuNStr(header.getTel())) return header.getTel();
            if (!Check.NuNStr(header.getDeviceId())) return header.getDeviceId();
            if (!Check.NuNStr(header.getImei())) return header.getImei();
            if (!Check.NuNStr(header.getImsi())) return header.getImsi();
        }

        HttpSession session = request.getSession();
        if(!Check.NuNObj(session)){
            return session.getId();
        }
        return null;
    }



}
