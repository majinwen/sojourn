/**
 * @FileName: GoogleApiUtils.java
 * @Package com.ziroom.minsu.services.common.utils
 * 
 * @author yd
 * @created 2017年2月23日 上午11:41:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.SysConst;

/**
 * <p>google api工具类</p>
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
public class GoogleApiUtils implements Serializable {


	private static Logger logger = LoggerFactory.getLogger(GoogleApiUtils.class);

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -2986473187521512739L;

	/**
	 * 缺省google ak
	 */
	private static final String defaultAk = "AIzaSyC7c1Z9PJxM5D8WVXd_LehFeJZCHpjbX9A";

	/**
	 * google ak的map
	 */
	private static ConcurrentHashMap<Integer, String> googleAkMap = new ConcurrentHashMap<Integer, String>();

	/**
	 * google 地址编码 api接口
	 */
	public static String googleGeocodingApi ="https://maps.google.cn/maps/api/geocode/json?address=CITYNAME&ak=GOOGLEAK";
	
	

	/**
	 * 
	 * 地址编码
	 *
	 * @author yd
	 * @created 2017年2月23日 上午11:43:44
	 *
	 * @param adress
	 * @param key   默认给1 就可以
	 * @return
	 */
	public static Gps geocode(String adress,Integer key){

		Gps gps = null;
		
		try {
			String  url =  GoogleApiUtils.googleGeocodingApi;
			if(!Check.NuNStr(adress)&&!Check.NuNStr(url)){
				url = url.replace("CITYNAME", adress);
				if(Check.NuNObj(key)||key>6) key = 1;
				if(googleAkMap.containsKey(key)){
					String ak = googleAkMap.get(key);
					if(Check.NuNStr(ak)){
						throw new BusinessException("谷歌地图，根据地址查询经纬度ak异常");
					}
					url = url.replace("GOOGLEAK", ak);
					LogUtil.info(logger, "谷歌地图,地址编码信息geocode：adress={},key={},ak={},url={}", adress,key,ak,url);
					String res = CloseableHttpsUtil.sendGet(url, null);

					Map<String, Object> resMap = (Map<String, Object>) JsonTransform.json2Map(res);
					if(!Check.NuNMap(resMap)){
						Object statusObj = resMap.get("status");
						if(!Check.NuNObj(statusObj)){
							if(SysConst.google_statu_ok.equals(statusObj.toString())){
								Object results = resMap.get("results");
								JSONArray jsonArray = new JSONArray(JsonEntityTransform.Object2Json(results));
								if(!Check.NuNObj(jsonArray)&&!Check.NuNObj(jsonArray.getJSONObject(0))){
									JSONObject obj = jsonArray.getJSONObject(0);
									if(obj.has("geometry")&&!Check.NuNObj(obj.getJSONObject("geometry"))){
										JSONObject geometryObj = obj.getJSONObject("geometry");
										if(geometryObj.has("location")&&!Check.NuNObj(geometryObj.getJSONObject("location"))){
											JSONObject locationObj = geometryObj.getJSONObject("location");
										    Double lan =  locationObj.getDouble("lat");
										    Double lng =  locationObj.getDouble("lng");
										    gps = new Gps();
										    gps.setWgLat(lan);
										    gps.setWgLon(lng);
										}
									}
									
								}
							}else{
								LogUtil.error(logger, "谷歌地图,地址编码返回失败,继续访问geocode：adress={},key={},statusObj={}",adress,key,res);
								gps = geocode(adress, ++key);
							}
							
						}

					}
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "谷歌地图,地址编码信息异常,继续访问e={},geocode：adress={},key={}", e,adress,key);
			gps = geocode(adress, ++key);
		}
		
		return gps;
	}


	static{
		String googleAk = SystemGlobalsUtils.getValue("GOOGLE_AK",defaultAk);
		if(!Check.NuNStr(googleAk)){
			String[] googleAkArr = googleAk.split(",");
			if(!Check.NuNObj(googleAkArr)){
				for (int i = 0; i < googleAkArr.length; i++) {
					googleAkMap.put(i+1, googleAkArr[i]);
				}
			}
		}
		String apiUrl = SystemGlobalsUtils.getValue("Google_Geocoding_api");
		if(!Check.NuNStr(apiUrl))
		googleGeocodingApi = SystemGlobalsUtils.getValue("Google_Geocoding_api");
	}
	
	
	
	// 测试变量
	private  static volatile int number = 0;


	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	public static void addAbc(){
		int i = 0;
		Runnable ru = new Runnable() {
			@Override
			public void run() {
				number++;
				Gps gps = GoogleApiUtils.geocode("北京", 1);
				if(number==10000||number==20000||number==30000){
					System.out.println(gps);	
				}	

			}
		};

		for (i = 0 ;i<30000 ;i++) {
			threadPoolExecutor.execute(ru);
		}

	}
	public static void main(String[] args) {
		addAbc();
	}
}
