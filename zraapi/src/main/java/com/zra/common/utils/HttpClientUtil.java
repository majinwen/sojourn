package com.zra.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.asura.framework.base.entity.DataTransferObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;


/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/11 11:15
 * @since 1.0
 */
public class HttpClientUtil {
    private final Logger logger = LoggerFactoryProxy.getLogger(HttpClientUtil.class);

    private static HttpClientUtil instance = new HttpClientUtil();

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        return instance;
    }

    /**
     *
     * get请求
     *
     * @author zhangshaobin
     * @created 2014年12月15日 下午4:53:06
     *
     * @param url
     * @return
     */
    public DataTransferObject get(String url) {
        DataTransferObject dto = new DataTransferObject();
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 200000);
        int statusCode = 0;
        try {
            client.executeMethod(method);
            statusCode = method.getStatusCode();
            logger.trace("statusCode:" + statusCode);
            String data = method.getResponseBodyAsString();
            dto.putValue("data", data);
            logger.trace("data:" + data);
        } catch (Exception e) {
            dto.setErrCode(DataTransferObject.ERROR);
            logger.error("AsuraHttpClient post 请求异常 . ", e);
        } finally {
            if (statusCode == HttpStatus.SC_OK) {
                dto.setErrCode(statusCode);
                dto.setMsg("请求成功，返回" + statusCode);
            } else {
                dto.setErrCode(statusCode);
                dto.setMsg("请求失败，返回" + statusCode);
            }
            //释放链接
            method.releaseConnection();
        }
        return dto;
    }

    /**
     *
     * post请求
     *
     * @author zhangshaobin
     * @created 2014年12月15日 下午4:53:19
     *
     * @param url
     * @param param
     * @return
     */
    public DataTransferObject post(String url, Map<String, String> param) {
        DataTransferObject dto = new DataTransferObject();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        if (param != null) {
            for (Map.Entry<String, String> p : param.entrySet()) {
                method.addParameter(new NameValuePair(p.getKey(), p.getValue()));
            }
        }
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 200000);
        int statusCode = 0;
        try {
            client.executeMethod(method);
            statusCode = method.getStatusCode();
            logger.trace("statusCode:" + statusCode);
            String data = method.getResponseBodyAsString();
            dto.putValue("data", data);
            logger.trace("data:" + data);
        } catch (Exception e) {
            statusCode = DataTransferObject.ERROR;
            logger.error("AsuraHttpClient post 请求异常 . ", e);
        } finally {
            if (statusCode == HttpStatus.SC_OK) {
                dto.setErrCode(statusCode);
                dto.setMsg("请求成功，返回" + statusCode);
            } else {
                dto.setErrCode(statusCode);
                dto.setMsg("请求失败，返回" + statusCode);
            }
            //释放链接
            method.releaseConnection();
        }
        return dto;

    }

    /**
     * post发送JSON请求
     * @param url
     * @param json
     * @return
     */
    public DataTransferObject postJSON(String url, String json) {
        DataTransferObject dto = new DataTransferObject();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);

        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 200000);

        int statusCode = 0;
        InputStreamReader input = null;
        BufferedReader reader = null;
        try {

            RequestEntity jsonEntity = new StringRequestEntity(json, "application/json", "utf-8");
            method.setRequestEntity(jsonEntity);

            client.executeMethod(method);
            statusCode = method.getStatusCode();
            logger.info("statusCode:" + statusCode);
            input = new InputStreamReader(method.getResponseBodyAsStream());
            reader = new BufferedReader(input);  
            StringBuilder stringBuffer = new StringBuilder();  
            String str = "";  
            while((str = reader.readLine())!=null){  
                stringBuffer.append(str);  
            }  
            String data = stringBuffer.toString();
            dto.putValue("data", data);
            logger.info("data:" + data);
        } catch (Exception e) {
            statusCode = DataTransferObject.ERROR;
            logger.error("AsuraHttpClient post 请求异常 . ", e);
        } finally {
            if (statusCode == HttpStatus.SC_OK) {
                dto.setErrCode(statusCode);
                dto.setMsg("请求成功，返回" + statusCode);
            } else {
                dto.setErrCode(statusCode);
                dto.setMsg("请求失败，返回" + statusCode);
            }
            
        	if(reader!=null) {
        		try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
        	}
        	if(input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
        	}
            //释放链接
            method.releaseConnection();
        }
        return dto;

    }
}
