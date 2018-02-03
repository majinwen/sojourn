
package com.ziroom.minsu.services.common.utils;

import com.asura.framework.utils.LogUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;

import java.nio.charset.Charset;

/**
 * Created by lyy on 16/4/14.
 */
@Deprecated
public class HttpUtil {


    public static final int CONNECT_TIMEOUT = 10 * 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public static <T> T post(final String url, final String jsonData, final ResponseCallback<T> callback) {
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(CONNECT_TIMEOUT).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost request = new HttpPost(url);
        if (!Check.NuNStr(jsonData)) {
            StringEntity jsonEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            request.setEntity(jsonEntity);
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
            int resultCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String resultJson = EntityUtils.toString(entity, UTF_8);
            if (callback != null) {
                return callback.onResponse(resultCode, resultJson);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
            if (callback != null) {
                return callback.onResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toString());
            }
        } finally {
            if (null != request && !request.isAborted()) {
                request.abort();
            }
            HttpClientUtils.closeQuietly(client);
            HttpClientUtils.closeQuietly(response);
        }
        return null;
    }
}
