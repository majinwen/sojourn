/**
 * @FileName: ImController.java
 * @Package test
 * 
 * @author yd
 * @created 2016年9月6日 下午8:30:49
 * 
 * Copyright 2011-2015 asura
 */
package test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;

/**
 * <p>IM完善</p>
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
public class ImController {

	/**
	 * api 请求url
	 */
	private static String apiURL = "https://a1.easemob.com/ziroom/ziroom/chatmessages";

	/**
	 * 请求token的URL
	 */
	private static String tokenURL = "https://a1.easemob.com/ziroom/ziroom/token";



	public static void main(String[] args) {



		Map<String, String> paramsMap = new HashMap<String, String>();

		paramsMap.put("grant_type", "client_credentials");
		paramsMap.put("client_id", "YXA65e9zYK0OEeSJrcsiLP3ulQ");
		paramsMap.put("client_secret", "YXA6bQjOz7HdP9U-mzeCZNqZ-gVfvxU");


		/*String accessToken = CloseableHttpsUtil.sendPost(tokenURL,JsonEntityTransform.Object2Json(paramsMap));

		System.out.println(accessToken);*/

		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
	   //	apiURL = apiURL+StringEscapeUtils.escapeJava("select * where timestamp>"+((new Date()).getTime()-2*24*60*1000));
		headerMap.put("Authorization", "Bearer YWMtmSF6CHQvEeag9iejz8t3BQAAAVg0hHcFl0wNUb9Jdo-hBw102Erpc_hQYRU");
		String content = CloseableHttpsUtil.sendGet(apiURL, headerMap);

		System.out.println(content);

	}

	/*	public static String getToken(String url, String appid, String secret)
            throws Exception {
        String resultStr = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);
        JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
        // 接收参数json列表
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("grant_type", "client_credentials");
        jsonParam.put("client_id", client_id);
        jsonParam.put("client_secret", client_secret);
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);

        // 请求结束，返回结果
        try {
            HttpResponse res = httpClient.execute(post);
            // 如果服务器成功地返回响应
            String responseContent = null; // 响应内容
            HttpEntity httpEntity = res.getEntity();
            responseContent = EntityUtils.toString(httpEntity, "UTF-8");
            JsonObject json = jsonparer.parse(responseContent)
                    .getAsJsonObject();
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (json.get("errcode") != null) 
                    //resultStr = json.get("errcode").getAsString();
                } else {// 正常情况下
                    resultStr = json.get("access_token").getAsString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接 ,释放资源
            httpClient.getConnectionManager().shutdown();
            return resultStr;
        }
    }*/

}
