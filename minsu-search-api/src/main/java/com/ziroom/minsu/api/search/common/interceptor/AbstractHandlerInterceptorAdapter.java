package com.ziroom.minsu.api.search.common.interceptor;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>抽象拦截器</p>
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
public class AbstractHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHandlerInterceptorAdapter.class);

    /**
     * 检测是否以流的方式传输.
     * @param request
     * @return
     */
    protected boolean checkStream(final HttpServletRequest request) {
        return MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType());
    }


    /**
     * 向客户端写入信息.
     * @param request
     * @param response
     * @param msg
     */
    protected void responseMsg(final HttpServletRequest request, final ServletResponse response, final Object msg) {
        if (Check.NuNObj(msg)) {
            return;
        }
        String message = msg.toString();
        try {
            //TODO 这里可以对返回结果的封装 暂时应该没用到
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(message);
            response.flushBuffer();
        } catch (final IOException e) {
            LogUtil.error(LOGGER, " e:{}", e);
        }
    }
}
