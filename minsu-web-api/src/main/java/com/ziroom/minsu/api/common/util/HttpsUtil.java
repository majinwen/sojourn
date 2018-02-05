package com.ziroom.minsu.api.common.util;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import com.asura.framework.utils.LogUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.asura.framework.base.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>https请求 可以忽略证书</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HttpsUtil {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(HttpsUtil.class);

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 400;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 6000;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 10000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 60000;
    private static final byte[] lock = new byte[0];

    private static volatile CloseableHttpClient defaultHttpClient;

    /**
     * 
     * 初始化忽略证书的CloseableHttpClient
     *
     * @author yd
     * @created 2016年5月8日 下午6:48:36
     *
     * @return
     */
	public static CloseableHttpClient getHttpClient() {
        if (defaultHttpClient == null) {
            synchronized (lock) {
                if (defaultHttpClient == null) {
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                            .setConnectionRequestTimeout(WAIT_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT)
                            .setSocketTimeout(READ_TIMEOUT).build();
                    SSLConnectionSocketFactory ssf = null;
                    try {
                        SSLContext sslContext = SSLContext.getInstance("TLS");
                        sslContext.init(null, new X509TrustManager[] {new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                            }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                        }}, null);
                        ssf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
                    } catch (NoSuchAlgorithmException | KeyManagementException e) {
                        LogUtil.error(LOGGER,"e:{}",e);
                    }
                    defaultHttpClient = HttpClientBuilder.create()
                            .setMaxConnTotal(MAX_TOTAL_CONNECTIONS)
                            .setMaxConnPerRoute(MAX_ROUTE_CONNECTIONS)
                            .setSSLSocketFactory(ssf)
                            .setDefaultRequestConfig(requestConfig).build();
                }
            }
        }
		return defaultHttpClient;
	}

	/**
	 * 
	 * post请求
	 *
	 * @author yd
	 * @created 2016年5月8日 下午6:46:17
	 *
	 * @param uri
	 * @param rawParams
	 * @param headerParams
	 * @return
	 */
	public static String postRequest(String uri, Map<String, String> rawParams, Map<String, String> headerParams) {
		HttpPost post = new HttpPost(uri);
		initHeader(post, headerParams);
		List<NameValuePair> params = new ArrayList<>();
		if (!Check.NuNMap(rawParams)) {
			for (String key : rawParams.keySet()) {
				params.add(new BasicNameValuePair(key, rawParams.get(key)));
			}
		}
        post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        return getResult(post);
	}

	/**
	 * 
	 * 参数为null
	 *
	 * @author yd
	 * @created 2016年5月8日 下午7:09:48
	 *
	 * @param uri
	 * @param headerParams
	 */
	public  static String postHeaderRequest(String uri, Map<String, String> headerParams) {
		HttpPost post = new HttpPost(uri);
		initHeader(post, headerParams);
        return getResult(post);
	}
	/**
	 * 
	 * 上传请求
	 *
	 * @author yd
	 * @created 2016年5月8日 下午6:50:25
	 *
	 * @param uri
	 * @param rawParams
	 * @param fileParams
	 * @param headerParams
	 * @return
	 */
	public static String uploadRequest(String uri, Map<String, String> rawParams,
									   Map<String, File> fileParams, Map<String, String> headerParams) {
		HttpPost post = new HttpPost(uri);
		initHeader(post, headerParams);
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (!Check.NuNMap(fileParams)) {
			for (String key : fileParams.keySet()) {
                entityBuilder.addPart(key, new FileBody(fileParams.get(key)));
			}
		}
        if (!Check.NuNMap(rawParams)) {
			for (String key : rawParams.keySet()) {
                entityBuilder.addPart(key, new StringBody(rawParams.get(key), ContentType.TEXT_PLAIN));
			}
		}
		post.setEntity(entityBuilder.build());
		return getResult(post);
	}

	public static String postRequest(String uri, Map<String, String> rawParams) {
		return postRequest(uri, rawParams, null);
	}

	public static String postRequest(String uri) {
		HttpPost post = new HttpPost(uri);
		return getResult(post);
	}

    public static String postRequest(String uri, String json, Map<String, String> headerParams) {
        HttpPost post = new HttpPost(uri);
        initHeader(post, headerParams);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        return getResult(post);
    }

    public static String postRequest(String uri, String json) {
        return postRequest(uri, json, null);
    }

	public static String getRequest(String uri, Map<String, String> headerParams) {
		HttpGet get = new HttpGet(uri);
		initHeader(get, headerParams);
		return getResult(get);
	}

	public static String getRequest(String uri) {
		HttpGet get = new HttpGet(uri);
		return getResult(get);
	}

	/**
	 * 
	 * 请求返回结果
	 *
	 * @author yd
	 * @created 2016年5月8日 下午6:47:31
	 *
	 * @param request
	 * @return
	 */
	private static String getResult(HttpUriRequest request) {
		CloseableHttpClient httpClient = getHttpClient();
		try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
			HttpEntity entity = httpResponse.getEntity();
			return EntityUtils.toString(entity);
		} catch (IOException e) {
            LogUtil.error(LOGGER,"e:{}",e);
		}
		return null;
	}

	/**
	 * 初始化消息头
	 *
	 * @param request GET/POST...
	 */
	public static void initHeader(HttpUriRequest request, Map<String, String> headerParams) {
		if (!Check.NuNMap(headerParams)) {
			for (String key: headerParams.keySet()) {
				request.addHeader(key, headerParams.get(key));
			}
		}
	}
}
