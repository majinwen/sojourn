package com.ziroom.minsu.api.common.interceptor;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.HeaderParamName;
import com.ziroom.minsu.api.common.header.Header;
import org.apache.http.HeaderIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

/**
 * <p>请求头的拦截器信息</p>
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
public class HeadersInterceptor extends HandlerInterceptorAdapter {


    private final static Logger logger = LoggerFactory.getLogger(HeadersInterceptor.class);

    /** 请求头属性名 */
    public final static String HEADER = HeaderIterator.class.getName() + ".Header";

    /** 请求头属性名 */
    public final static String USERID = HeaderIterator.class.getName() + ".UserId";

    /** Token值属性名. */
    public final static String TOKEN = HeadersInterceptor.class.getName() + ".Token";

    /** 是否输出请求头信息. */
    private boolean isPrint;

    @Value("#{'${X-Forwarded-Ziroom}'.trim()}")
    private String X_Forwarded_Ziroom;

    public void setPrint(final boolean isPrint) {
        this.isPrint = isPrint;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        final String traceinfo = request.getHeader("traceInfo");

        final String uid = request.getHeader("uid");
        final Header header = matchTraceInfoToHeader(traceinfo);
        final String token = request.getParameter("token");
        //如果是网关请求 把网关标志放到返回header中，后续流程处理网关返回信息
        String forwarded = request.getHeader(HeaderParamName.X_Forwarded_Ziroom);
        if (X_Forwarded_Ziroom.equals(forwarded)) {
            response.addHeader(HeaderParamName.X_Forwarded_Ziroom, forwarded);
        }
        request.setAttribute(HEADER, header);
        request.setAttribute(TOKEN, token);
        request.setAttribute(USERID, uid);
        if (isPrint) {
            logger.info("header: {}, token: {}.", header, token);
        }

        return true;
    }


    /**
     * 解析请求头, 构建Header信息.
     * @param traceinfo
     * @return
     */
    private Header matchTraceInfoToHeader(final String traceinfo) {
        Header header = new Header();
        if (Check.NuNStrStrict(traceinfo)) {
            return header;
        }
        try {
            header = JsonEntityTransform.json2Entity(traceinfo, Header.class);
        }catch (Exception e){
          LogUtil.info(logger, "解析请求头, 构建Header信息-异常：", traceinfo);
        }
        return header;
    }


    public static void main(String[] args) {
        String aa = "{\"appName\":\"ziroom\",\"channelName\":\"Leshi\",\"deviceId\":\"867979021561615\",\"imei\":null,\"imsi\":null,\"macAddress\":\"DC:EE:06:79:D8:6C\",\"netWork\":\"4\",\"osType\":\"1\",\"osVersion\":\"7.0\",\"phoneBrand\":\"google\",\"phoneModel\":\"Nexus 6P\",\"tel\":null,\"versionCode\":100000,\"versionName\":\"4.4.3\"}";

        Header header = new Header();

        try {
            header = JsonEntityTransform.json2Entity(aa, Header.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(JsonEntityTransform.Object2Json(header));
    }

//    /**
//     * 解析请求头, 构建Header信息.
//     * @param traceinfo
//     * @return
//     */
//    private Header matchTraceInfoToHeader(final String traceinfo) {
//        final Header header = new Header();
//        if (Check.NuNStrStrict(traceinfo)) {
//            return header;
//        }
//
//        final String[] traceInfos = traceinfo.split(";");
//        for (final String info : traceInfos) {
//            if (Check.NuNStr(info)) {
//                continue;
//            }
//            final int indexOf = info.indexOf('=');
//            if (indexOf == -1) {
//                continue;
//            }
//
//            setParameters(header, info, indexOf);
//        }
//
//        return header;
//    }

    /**
     * 为 Header 赋值, 通过 Field 反射实现.
     * @param header
     * @param info
     * @param indexOf
     */
    private void setParameters(final Header header, final String info, final int indexOf) {
        final String paramName = info.substring(0, indexOf);
        final String paramValue = info.substring(indexOf + 1);
        if (Check.NuNObjs(paramName, paramValue)) {
            return;
        }
        try {
            final Field field = header.getClass().getDeclaredField(paramName);
            field.setAccessible(true);
            field.set(header, paramValue);
        } catch (final Exception ignore) {
            //Ignore all exception.
        }
    }



}
