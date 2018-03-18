package com.zra.app;

import com.alibaba.fastjson.JSON;
import com.zra.common.utils.PropUtils;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;

@Provider
public class AppFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger("FILTERLOG");

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    /**
     * Filter created by cuigh6 .
     * request
     *
     * @param requestContext the request context
     * @throws IOException the io exception
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

    }

    /**
     * Filter created by cuigh6 .
     * response
     *
     * @param containerRequestContext  the container request context
     * @param containerResponseContext the container response context
     * @throws IOException the io exception
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        if (Boolean.valueOf(PropUtils.getString("json.result.debug"))) {
            Object entity = containerResponseContext.getEntity();
            LOGGER.info("***[返回结果]***:" + JSON.toJSONString(entity));
        }
    }

    /**
     * Input stream to string created by cuigh6.
     *
     * @param in the in
     * @return the string
     * @throws IOException the io exception
     */
    public String inputStreamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}
