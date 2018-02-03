package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * <p>抽象的请求信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/9.
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractCloseable {


    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCloseable.class);



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
	public final static int WAIT_TIMEOUT = 3000;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 3000;
	/**
	 * 读取超时时间
	 */
	public final static int SOCKET_TIME_OUT = 5000;


	/*   *//**
	 * httpClient
	 *//*
    private static CloseableHttpClient httpClient;

	  *//**
	  * httpsClient
	  *//*
    private static CloseableHttpClient httpsClient;*/

	//private static  RequestConfig requestConfig;

	public final static Charset UTF8 = Charset.forName("UTF-8");

	/**
	 * 获取http的客户端
	 * @return
	 */
	public static RequestConfig getRequestConfig(){
		RequestConfig	requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT).setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(WAIT_TIMEOUT).build();
		return requestConfig;
	}

	/**
	 * 获取http的客户端
	 * @author afi
	 * @return
	 */
	public static CloseableHttpClient getHttpClient() {
		/* if (httpClient == null) {
            httpClient = HttpClients.custom().build();
        }*/
		CloseableHttpClient httpClient = HttpClients.custom().build();
		return httpClient;
	}


	/**
	 * 初始化消息头
	 * @author afi
	 * @param request GET/POST...
	 */
	public static void initHeader(HttpUriRequest request, Map<String, String> headerParams) {
		if (!Check.NuNMap(headerParams)) {
			for (String key: headerParams.keySet()) {
				request.addHeader(key, headerParams.get(key));
			}
		}
	}

	/**
	 * 初始化忽略证书的CloseableHttpClient
	 * @author yd
	 * @created 2016年5月8日 下午6:48:36
	 * @return
	 */
	public static CloseableHttpClient getHttpsClient() {

		CloseableHttpClient httpsClient = HttpClients.custom().build();
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
		httpsClient = HttpClientBuilder.create()
				.setMaxConnTotal(MAX_TOTAL_CONNECTIONS)
				.setMaxConnPerRoute(MAX_ROUTE_CONNECTIONS)
				.setSSLSocketFactory(ssf)
				.setDefaultRequestConfig(getRequestConfig()).build();
		return httpsClient;
	}

}
