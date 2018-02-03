package com.ziroom.minsu.services.common.utils;


import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>http的封装</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/11.
 * @version 1.0
 * @since 1.0
 */
public class CloseableHttpUtil extends AbstractCloseable{

	private final static Logger logger = LoggerFactory.getLogger(CloseableHttpUtil.class);

	final static  String baiDuAapUrl = "api.map.baidu.com/location/ip";

	/**
	 * 发送post请求 包含头信息
	 * @author afi
	 * @param api_url
	 * @param headerMap
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String sendPost(final String api_url, Map<String, String> param,Map<String, String> headerMap)
			throws  IOException {
		return sendPost(api_url,JsonEntityTransform.Object2Json(param),headerMap);
	}
	/**
	 * 调用远程地址.
	 * @author afi
	 * @param api_url
	 * @param object
	 * @return
	 */
	public static String sendPost(String api_url, Object object){
		return sendPost(api_url, JsonEntityTransform.Object2Json(object));
	}


	/**
	 * 发送post请求
	 * @author afi
	 * @param api_url
	 * @param jsonPostArgs
	 * @return
	 */
	public static String sendPost(final String api_url, final String jsonPostArgs) {
		return sendPost(api_url,jsonPostArgs,null);
	}



	/**
	 * 发送post请求 带有head信息
	 * @author afi
	 * @param api_url
	 * @param jsonPostArgs
	 * @param headerMap  头参数map
	 * @return
	 */
	public static String sendPost(final String api_url, final String jsonPostArgs,Map<String, String> headerMap) {
		return sendCustomPost(api_url, jsonPostArgs,headerMap,null,null,null,null);
	}

	/**
	 * 发送post请求 带有head信息和自定义网络参数
	 *
	 * @author zl
	 * @created 2017年6月22日 下午4:19:44
	 *
	 * @param api_url
	 * @param jsonPostArgs
	 * @param headerMap
	 * @param socketTimeOut
	 * @param connectTimeout
	 * @param waitTimeout
	 * @param retryCount
	 * @return
	 */
	public static String sendCustomPost(final String api_url, final String jsonPostArgs,Map<String, String> headerMap,
			Integer socketTimeOut,Integer connectTimeout,Integer waitTimeout,Integer retryCount) {
		final HttpPost httpPost = new HttpPost(api_url);

		//初始化head
		initHeader(httpPost,headerMap);
		httpPost.addHeader("Content-Type", "application/json");
		//校验当前参数是否存在
		if(!Check.NuNStr(jsonPostArgs)){
			final StringEntity postEntity = new StringEntity(jsonPostArgs, UTF8);
			httpPost.setEntity(postEntity);
		}
		String result = null;
		//开始埋点
		String baseUrl = ValueUtil.getBaseUrl(api_url);
		Transaction tran = Cat.newTransaction("catHttpUrl",baseUrl);
		Long  satrt = System.currentTimeMillis();
		try {

			//			CloseableHttpClient httpClient = getHttpClient();			
			//			if(!Check.NuNObjs(socketTimeOut,connectTimeout,waitTimeout,retryCount)){
			//				httpClient = getRetryHttpClient(socketTimeOut,connectTimeout,waitTimeout,retryCount);
			//			}

			final HttpResponse response = getHttpClient().execute(httpPost);					
			final HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, UTF8);
			/**
			 * 设置消息的状态,必须设置，0:标识成功,其他标识发生了异常
			 */
			tran.setStatus(Transaction.SUCCESS);
		} catch (final Exception e) {
			//调用支付的参数
			if(!Check.NuNStr(api_url)&&api_url.contains(CloseableHttpUtil.baiDuAapUrl)){
				LogUtil.info(logger, "执行远程调用失败,调用参数 url:{}，param：{}，headerMap：{} rst:{}", api_url,jsonPostArgs,JsonEntityTransform.Object2Json(headerMap),result);
			}else{
				LogUtil.error(logger, "执行远程调用失败,调用参数 url:{}，param：{}，headerMap：{} rst:{},e:{}", api_url,jsonPostArgs,JsonEntityTransform.Object2Json(headerMap),result,e);
			}

			/**
			 * cat使用log4j记录异常信息
			 */
			Cat.logError(e);
			/**
			 * 发生异常了，要设置消息的状态为e
			 */
			tran.setStatus(e);
		} finally {
			httpPost.abort();
			/**
			 * complete()方法必须要写、
			 * 因为前面记录的所有消息都是在这里异步发送出去的
			 * cat源码中使用的是netty,NIO来发送cat-client
			 * 收集到的消息包
			 */
			tran.complete();
		}
		Long  end = System.currentTimeMillis();

		LogUtil.debug(logger,"time:{}ms,API:{},parMap:{},headerMap:{}",end-satrt,api_url,jsonPostArgs,JsonEntityTransform.Object2Json(headerMap));

		return result;
	}


	/**
	 * 直接调用post、
	 * @author afi
	 * @param api_url
	 * @return
	 */
	public static String sendPost(String api_url) {
		return sendPost(api_url,null);
	}


	/**
	 * get请求
	 * @author afi
	 * @param api_url
	 * @param headerMap
	 * @return
	 */
	public static String sendGet(String api_url,Map<String, String> headerMap) {
		final HttpGet httpGet = new HttpGet(api_url);
		//初始化head
		initHeader(httpGet, headerMap);
		httpGet.addHeader("Content-Type", "application/json");
		String result = null;

		//开始埋点
		String baseUrl = ValueUtil.getBaseUrl(api_url);
		Transaction tran = Cat.newTransaction("catHttpUrl",baseUrl);
		Long  satrt = System.currentTimeMillis();
		try {
			final HttpResponse response = getHttpClient().execute(httpGet);
			final HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, UTF8);
			/**
			 * 设置消息的状态,必须设置，0:标识成功,其他标识发生了异常
			 */
			tran.setStatus(Transaction.SUCCESS);
		} catch (final Exception e) {
			//调用支付的参数
			if(!Check.NuNStr(api_url)&&api_url.contains(CloseableHttpUtil.baiDuAapUrl)){
				LogUtil.info(logger, "执行远程调用失败,调用参数 url:{}，headerMap：{},rst:{}", api_url, JsonEntityTransform.Object2Json(headerMap),result);
			}else{
				LogUtil.error(logger, "执行远程调用失败,调用参数 url:{}，headerMap：{},rst:{},e={}", api_url, JsonEntityTransform.Object2Json(headerMap),result,e);
			}

			/**
			 * cat使用log4j记录异常信息
			 */
			Cat.logError(e);
			/**
			 * 发生异常了，要设置消息的状态为e
			 */
			tran.setStatus(e);
		} finally {
			httpGet.abort();
			/**
			 * complete()方法必须要写、
			 * 因为前面记录的所有消息都是在这里异步发送出去的
			 * cat源码中使用的是netty,NIO来发送cat-client
			 * 收集到的消息包
			 */
			tran.complete();
		}
		Long  end = System.currentTimeMillis();
		LogUtil.debug(logger,"time:{}ms,API:{},parMap:{},headerMap:{}",end-satrt,api_url,JsonEntityTransform.Object2Json(headerMap));

		return result;
	}

	/**
	 * 
	 * delete 方法
	 *
	 * @author yd
	 * @created 2017年8月3日 下午9:08:36
	 *
	 * @param url
	 * @param param
	 * @param headMap
	 * @return
	 */

	public static String delete(String url, Map<String, String> param, Map<String, String> headMap){  
		HttpClient httpClient = new HttpClient();  
		DeleteMethod deleteMethod = new DeleteMethod(url);  
		Transaction tran = Cat.newTransaction("catHttpUrl",url);
		try {  
			// 创建参数列表
			if (param != null) {

				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				deleteMethod.setQueryString((org.apache.commons.httpclient.NameValuePair[]) paramList.toArray());
			}

			//初始化head
			initDeleteHeader(deleteMethod, headMap);
			int statusCode = httpClient.executeMethod(deleteMethod); 


			if (statusCode != HttpStatus.SC_OK) {  
				return "Method failed: " + deleteMethod.getStatusLine();  
			}  
			byte[] responseBody = deleteMethod.getResponseBody();  
			/**
			 * 设置消息的状态,必须设置，0:标识成功,其他标识发生了异常
			 */
			tran.setStatus(Transaction.SUCCESS);
			return new String(responseBody,"utf-8");
		} catch (IOException e) {  
			LogUtil.error(logger, "执行远程调用失败,调用参数 url:{}，headerMap：{},e:{}", url, JsonEntityTransform.Object2Json(headMap),e);
		}finally {  
			deleteMethod.releaseConnection();  
		}  
		return null;  
	}  

	/**
	 * 
	 * 添加header
	 *
	 * @author yd
	 * @created 2017年8月3日 下午9:00:59
	 *
	 * @param deleteMethod
	 * @param headerParams
	 */
	public static void initDeleteHeader(DeleteMethod deleteMethod ,Map<String, String> headerParams){
		if (!Check.NuNMap(headerParams)) {
			for (String key: headerParams.keySet()) {
				deleteMethod.addRequestHeader(key, headerParams.get(key));
			}
		}
	}

	/**
	 * 将参数放在url 模拟表单提交
	 * @param api_url
	 * @param param
	 * @param headerMap
	 * @return
	 */
	public static String sendFormPost(String api_url, Map<String, String> param,Map<String, String> headerMap){
		// 创建Http Post请求
		HttpPost httpPost = new HttpPost(api_url);
		// 创建Httpclient对象
		CloseableHttpResponse response = null;
		String resultString = "";
		//开始埋点
		String baseUrl = ValueUtil.getBaseUrl(api_url);
		Transaction tran = Cat.newTransaction("catHttpUrl",baseUrl);
		Long  satrt = System.currentTimeMillis();
		try {

			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
				httpPost.setEntity(entity);
			}
			//初始化head
			initHeader(httpPost, headerMap);
			// 执行http请求
			response = getHttpClient().execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			/**
			 * 设置消息的状态,必须设置，0:标识成功,其他标识发生了异常
			 */
			tran.setStatus(Transaction.SUCCESS);
		} catch (Exception e) {
			//调用支付的参数
			if(!Check.NuNStr(api_url)&&api_url.contains(CloseableHttpUtil.baiDuAapUrl)){
				LogUtil.info(logger, "执行远程调用失败 ,调用参数 url:{}，param：{}，headerMap：{}", api_url,JsonEntityTransform.Object2Json(param),JsonEntityTransform.Object2Json(param),JsonEntityTransform.Object2Json(headerMap));
			}else{
				LogUtil.error(logger, "执行远程调用失败 ,调用参数 url:{}，param：{}，headerMap：{},e={}", api_url,JsonEntityTransform.Object2Json(param),JsonEntityTransform.Object2Json(param),JsonEntityTransform.Object2Json(headerMap),e);
			}

			/**
			 * cat使用log4j记录异常信息
			 */
			Cat.logError(e);
			/**
			 * 发生异常了，要设置消息的状态为e
			 */
			tran.setStatus(e);
		} finally {
			httpPost.abort();
			/**
			 * complete()方法必须要写、
			 * 因为前面记录的所有消息都是在这里异步发送出去的
			 * cat源码中使用的是netty,NIO来发送cat-client
			 * 收集到的消息包
			 */
			tran.complete();
		}
		Long  end = System.currentTimeMillis();
		LogUtil.debug(logger,"time:{}ms,API:{},parMap:{},headerMap:{}",end-satrt,api_url,JsonEntityTransform.Object2Json(param),JsonEntityTransform.Object2Json(headerMap));
		return resultString;
	}
	
	/**
	 * 
	 * delete 请求
	 *
	 * @author yd
	 * @created 2017年8月4日 下午3:05:31
	 *
	 * @param url
	 * @param param
	 * @param headerMap
	 * @return
	 */
	public static String sendDelete(String url, Map<String, String> headerMap){


		HttpDelete  httpDelete = new HttpDelete(url);
		// 创建Httpclient对象
		CloseableHttpResponse response = null;
		String resultString = "";
		//开始埋点
		String baseUrl = ValueUtil.getBaseUrl(url);
		Transaction tran = Cat.newTransaction("catHttpUrl",baseUrl);
		Long  satrt = System.currentTimeMillis();
		try {
			//初始化head
			initHeader(httpDelete, headerMap);
			// 执行http请求
			response = getHttpClient().execute(httpDelete);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			/**
			 * 设置消息的状态,必须设置，0:标识成功,其他标识发生了异常
			 */
			tran.setStatus(Transaction.SUCCESS);
		} catch (Exception e) {
			//调用支付的参数
			if(!Check.NuNStr(url)&&url.contains(CloseableHttpUtil.baiDuAapUrl)){
				LogUtil.info(logger, "执行远程调用失败 ,调用参数 url:{}，headerMap：{}", url,JsonEntityTransform.Object2Json(headerMap));
			}else{
				LogUtil.error(logger, "执行远程调用失败 ,调用参数 url:{}，headerMap：{},e={}", url,JsonEntityTransform.Object2Json(headerMap),e);
			}

			/**
			 * cat使用log4j记录异常信息
			 */
			Cat.logError(e);
			/**
			 * 发生异常了，要设置消息的状态为e
			 */
			tran.setStatus(e);
		} finally {
			httpDelete.abort();
			/**
			 * complete()方法必须要写、
			 * 因为前面记录的所有消息都是在这里异步发送出去的
			 * cat源码中使用的是netty,NIO来发送cat-client
			 * 收集到的消息包
			 */
			tran.complete();
		}
		Long  end = System.currentTimeMillis();
		LogUtil.debug(logger,"time:{}ms,API:{},headerMap:{}",end-satrt,url,JsonEntityTransform.Object2Json(headerMap));
		return resultString;
	}

	/**
	 * 
	 * put 请求
	 *
	 * @author yd
	 * @created 2017年8月4日 下午3:05:31
	 *
	 * @param url
	 * @param param
	 * @param headerMap
	 * @return
	 */
	public static String sendPut(String url,String  jsonPostArgs,Map<String, String> headerMap){


		HttpPut  httpPut = new HttpPut(url);
		// 创建Httpclient对象
		CloseableHttpResponse response = null;
		String resultString = "";
		//开始埋点
		String baseUrl = ValueUtil.getBaseUrl(url);
		Transaction tran = Cat.newTransaction("catHttpUrl",baseUrl);
		Long  satrt = System.currentTimeMillis();
		try {

			
			//校验当前参数是否存在
			if(!Check.NuNStr(jsonPostArgs)){
				final StringEntity postEntity = new StringEntity(jsonPostArgs, UTF8);
				httpPut.setEntity(postEntity);
			}
			
			//初始化head
			initHeader(httpPut, headerMap);
			// 执行http请求
			response = getHttpClient().execute(httpPut);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			/**
			 * 设置消息的状态,必须设置，0:标识成功,其他标识发生了异常
			 */
			tran.setStatus(Transaction.SUCCESS);
		} catch (Exception e) {
			//调用支付的参数
			if(!Check.NuNStr(url)&&url.contains(CloseableHttpUtil.baiDuAapUrl)){
				LogUtil.info(logger, "执行远程调用失败 ,调用参数 url:{}，jsonPostArgs：{}，headerMap：{}", url,jsonPostArgs,JsonEntityTransform.Object2Json(headerMap));
			}else{
				LogUtil.error(logger, "执行远程调用失败 ,调用参数 url:{}，jsonPostArgs：{}，headerMap：{},e={}", url,jsonPostArgs,JsonEntityTransform.Object2Json(headerMap),e);
			}

			/**
			 * cat使用log4j记录异常信息
			 */
			Cat.logError(e);
			/**
			 * 发生异常了，要设置消息的状态为e
			 */
			tran.setStatus(e);
		} finally {
			httpPut.abort();
			/**
			 * complete()方法必须要写、
			 * 因为前面记录的所有消息都是在这里异步发送出去的
			 * cat源码中使用的是netty,NIO来发送cat-client
			 * 收集到的消息包
			 */
			tran.complete();
		}
		Long  end = System.currentTimeMillis();
		LogUtil.debug(logger,"time:{}ms,API:{},jsonPostArgs:{},headerMap:{}",end-satrt,url,jsonPostArgs,JsonEntityTransform.Object2Json(headerMap));
		return resultString;
	}
	/**
	 * form 表单提交post
	 * @author afi
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendFormPost(String url, Map<String, String> param){
		return sendFormPost(url, param, null);
	}


	public static void main(String[] args) throws IOException {
	/*	Map<String,String> map = new HashMap<>();
		map.put("Accept","application/json");*/
		//        String s = CloseableHttpUtil.sendGet("http://ups.d.ziroom.com/upsApi/userMsg?userAccount=busj", map);
		//        System.out.println(s);
		//        String ss = CloseableHttpUtil.sendGet("http://ups.d.ziroom.com/upsApi/userAuthList?sysCode=report&userAccountFid=8a9e9aaf537e3f7501537e3f75af0000", map);
		//        System.out.println(ss);
		//        String sss = CloseableHttpUtil.sendGet("http://ups.d.ziroom.com/upsApi/userAuthSet?sysCode=report&userAccountFid=8a9e9aaf537e3f7501537e3f75af0000", map);
		//        System.out.println(sss);
		//        String roleType = CloseableHttpUtil.sendGet("http://localhost:8080/minsu-web-ups/upsApi/isPrivilegeRes?sysCode=troy&resUrl=house/houseMgt/housePicAuditDetail", map);
		//        System.err.println(roleType);
		//String resultString=sendGet("http://www.baidu.com", map);
		
		
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("uid", "0f163457-ad6a-09ce-d5de-de452a251cf6,5f4f193b-07fd-a708-85f8-22907004fd6d,a06f82a2-423a-e4e3-4ea8-e98317540190,49c0ca56-6803-da38-e11f-22e640499010,7a8c4184-8e2e-37b4-08e8-f4c20225e350");
//		param.put("hide", "0");
//		param.put("auth_code","600007");
//		param.put("auth_secret_key", "9216e17086420ad259229e430d54610c");
//		String url = "http://passport.inside.fp003.qa.ziroom.com/account/profile/batch-select.json";
//		String resultString = sendFormPost(url, param);
//		System.err.println(resultString);
		
		//服务查询登录用户信息
		Map<String, String> param=new HashMap<String, String>();
		param.put("userAccount", "busj");
		param.put("sysCode", "troy");
        Map<String,String> map = new HashMap<>();
        map.put("Accept","application/json");
		String resultJson=CloseableHttpUtil.sendFormPost("http://127.0.0.1:8080/upsApi/upsUserMsg", param,map);
	}
}
