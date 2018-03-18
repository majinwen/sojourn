package com.zra.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.AppResult;
import com.zra.common.security.DESUtils;
import com.zra.common.security.Md5Utils;
import org.slf4j.Logger;

import com.apollo.logproxy.param.AppParam;
import com.apollo.logproxy.slf4j.LogThreadHolder;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by cuigh6 on 2016/4/26.
 * 过滤请求
 */
public class ClientParamFilter implements Filter {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger("FILTERLOG");
    //网关请求标示
    private static final String X_Forwarded_Ziroom = "toread";

    private static final String X_Forwarded_Ziroom_name = "X-Forwarded-Ziroom";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            //==========获取请求路径=================================
            StringBuffer url = request.getRequestURL();
            String uri = request.getRequestURI();
            //==========获取请求参数=================================
            Map<String, String[]> paramMap = request.getParameterMap();

            //来自网关请求 不走加密 add by jixd 2017.09.26
            String forwarded = request.getHeader(X_Forwarded_Ziroom_name);
            if (X_Forwarded_Ziroom.equals(forwarded)){
                filterChain.doFilter(request, response);
                return;
            }

            //注意:这里日志屏蔽掉了路径中带有upload的方法
            if (url.toString().contains("upload")) {
                filterChain.doFilter(request, response);
            } else {
                String p = paramMap.get("p")[0];//获取DES加密参数p
                String[] signs = paramMap.get("sign");
                if (signs == null) {
                    //TODO - 此处是否需要考虑加日志
                    response.getWriter().print(JSON.toJSONString(AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL)));
                    response.getWriter().flush();
                    response.getWriter().close();
                    return;
                }
                String sign = signs[0];
                //解密
                String _p = DESUtils.decrypt(p);
                //MD5加密
                String _sign = Md5Utils.md5s(_p);
                //比较sign和_sign
                if (sign.equals(_sign)) {
                    parseAndputParamInfo(_p);
                    logReqInfo(uri, p, _p, sign, _sign);
                    filterChain.doFilter(request, response);
                } else {
                    response.getWriter().print(JSON.toJSONString(AppResult.toFail(null, ErrorEnum.MSG_SIGN_FAIL)));
                    response.getWriter().flush();
                    response.getWriter().close();
                }
            }

        } catch (Exception e) {
            LOGGER.error("参数过滤", e);
            response.getWriter().print(JSON.toJSONString(AppResult.toFail(null, ErrorEnum.MSG_FAIL)));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
    
    /**
     * 解析用户传过来的数据并放到ThreadHold中.
     * @param _p
     */
    private void parseAndputParamInfo(String _p) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(_p);
            String uid = null;
            String imei = null;
            
            if (jsonObject.containsKey("imei")) {
                imei = jsonObject.getString("imei");
            }
            
            // 客户端参数名有使用uid而不是uuid
            if (jsonObject.containsKey("uuid")) {
                uid = jsonObject.getString("uuid");
            } else if (jsonObject.containsKey("uid")) {
                uid = jsonObject.getString("uid");
            }
            AppParam appParam = new AppParam(uid, imei);
            LogThreadHolder.putParam(appParam);
        } catch (Exception e) {
            LOGGER.error(_p, e);
        }
       
    }
    
    /**
     * 打印第一次请求日志
     * @param url
     * @param p
     * @param _p
     * @param sign
     * @param _sign
     */
    private void logReqInfo(String url, String p, String _p, String sign, String _sign) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append(" ");
            sb.append(p);
            sb.append(" ");
            sb.append(JSON.parse(_p));
            sb.append(" ");
            sb.append(sign);
            LOGGER.info(sb.toString());
        } catch (Exception e) {
            LOGGER.error(url + " " + p + " " + sign + " " + _p + " " + _sign, e);
        }
    } 
    @Override
    public void destroy() {

    }
}
