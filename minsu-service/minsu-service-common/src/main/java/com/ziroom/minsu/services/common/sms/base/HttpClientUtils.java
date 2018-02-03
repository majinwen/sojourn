package com.ziroom.minsu.services.common.sms.base;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.utils.LogUtil;

/**
 * 发起请求的工具类
 * @author yd
 * @date 2016-03-31 15:53
 * @version 1.0
 *
 */
@Deprecated
public class HttpClientUtils {
	/**
	 * 日志对象
	 */
	private static Logger logger =  LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 * @param url
	 * @param paramsJson
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public static String  httpClientPostXml(String url,String paramsJson,String charSet)  
			throws ClientProtocolException, IOException {  

		String resData = null;
		HttpPost method = null;  
		
		try {
			if(url!=null){
				HttpClient httpClient = new DefaultHttpClient(); 
				method = new HttpPost(url);
				StringEntity entity = new StringEntity(paramsJson.toString(),charSet);//解决中文乱码问题    
				entity.setContentEncoding(charSet);    
				entity.setContentType("application/json");    
				method.setEntity(entity);    

				HttpResponse result = httpClient.execute(method);  
				// 请求结束，返回结果  
				resData = EntityUtils.toString(result.getEntity());

                LogUtil.error(logger, "当前短信发送请求返回result={}", result);
			}else{
                LogUtil.debug(logger,"当前求情URL为null");
			}
			
		} catch (Exception e) {
            LogUtil.error(logger,"当前请求失败, error:{}",e);
		}finally{
			method.releaseConnection();
		}
		return resData;

	}  

	/**
	 * 测试短息平台
	 * @param args
	 */
	public static void main(String[] args) {
		/*String paramsJson =  null;

		JSONObject jsonObject = new JSONObject();
		SmsConfig smsConfig = new SmsConfig("18701482471", "杨东你好"+(new Date().getTime()),"eoPbfod4Qpq5w-WP7JcVRg");
		jsonObject.put("token", smsConfig.getToken());
		jsonObject.put("to", smsConfig.getReceiver());
		jsonObject.put("content", smsConfig.getContent());

		if(smsConfig.getSignature()!=null&&smsConfig.getSignature().trim().length()>0){
			jsonObject.put("signature", smsConfig.getSignature());
		}
		paramsJson = jsonObject.toString();

		try {
			httpClientPostXml(smsConfig.getRequestUrl(), paramsJson,smsConfig.getCharSet());
		} catch (Exception e) {
			LogUtil.error(logger,"error:{}",e);
		}*/
	}


}
