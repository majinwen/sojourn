/**
 * @FileName: AirbnbProcessor.java
 * @Package com.ziroom.minsu.spider.airbnb.processor
 * 
 * @author zl
 * @created 2016年9月29日 下午1:36:03
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.processor;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.spider.airbnb.dto.AirbnbListRequest;
import com.ziroom.minsu.spider.airbnb.entity.enums.RoomTypeEnum;
import com.ziroom.minsu.spider.airbnb.processor.tasks.AirbnbHouseInfoTasker;
import com.ziroom.minsu.spider.commons.HttpUtil;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.entity.enums.FailUrlRecordTypeEnum;


/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */ 
public class AirbnbProcessor {

	private static final String baseUrl="https://zh.airbnb.com";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AirbnbProcessor.class);
	
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30, 100, 300000L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
	 
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年9月29日 下午1:41:53
	 *
	 * @param cityQueryString  Shanghai--China
	 * @param listRequest
	 * @param failUrlRecords 
	 */
	public static void parseHouseList(String cityQueryString,AirbnbListRequest listRequest,Set<FailUrlRecordsEntity> failUrlRecords){
		
		if (listRequest==null || cityQueryString==null ) {
			return;
		}
		if (listRequest.getPage()==null) {
			listRequest.setPage(1);
		} 
		if (failUrlRecords==null) {
			failUrlRecords = new HashSet<FailUrlRecordsEntity>();
		}
		Document doc = null;
		
		String[] roomTypes=null;
		
		//按照房源类型爬取
		if (listRequest.getRoom_types()==null || listRequest.getRoom_types().length==0) {
			roomTypes = RoomTypeEnum.getCodes();
		}else {
			roomTypes =listRequest.getRoom_types();
		}
		
		//按照街区爬取
		if (listRequest.getNeighborhoods()==null || listRequest.getNeighborhoods().length==0) {
			listRequest.setNeighborhoods(new String[]{""});//街区循环一次
		}
		
		int streetIndex=0;
		while (streetIndex<listRequest.getNeighborhoods().length) {
			String street =listRequest.getNeighborhoods()[streetIndex];
			
			if (street!=null && street.length()>0) {
				streetIndex++;
			}
			for (int g = 1; g < 17; g++) {//可容纳人数
				listRequest.setGuests(g);
				
				for (int k =0;k<roomTypes.length;k++) {
					
					String roomType=roomTypes[k];
					listRequest.setRoom_types(new String[]{roomType});//按照房间类型逐个爬
					
					while (listRequest.getPage()>0 && listRequest.getPage()<18) {//按照页数爬取
						
						if (street==null || street.length()==0) {
							listRequest.setNeighborhoods(null);//还原参数
						}else {
							listRequest.setNeighborhoods(new String[]{street});//按照街区逐个爬
						}
						
						String url=baseUrl+"/s/"+cityQueryString+"?"+HttpUtil.toAirbnbUrlParamString(listRequest);
						
						LogUtil.info(LOGGER, "爬取airbnb列表，url={}",url);
						try {
							String ipPort =HttpUtil.getIpAndPort();
							if (ipPort!=null) {
								String ip = ipPort.substring(0, ipPort.indexOf(":"));
								String port= ipPort.substring(ipPort.indexOf(":")+1);
								System.setProperty("http.maxRedirects", "50");
								System.getProperties().setProperty("proxySet", "true");
								System.getProperties().setProperty("http.proxyHost", ip);
								System.getProperties().setProperty("http.proxyPort", port);
							}else {
								FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
								failUrlRecordsEntity.setCreateDate(new Date());
								failUrlRecordsEntity.setFailReason("获取代理ip失败");
								failUrlRecordsEntity.setUrl(url);
								failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbList.getCode());
								failUrlRecords.add(failUrlRecordsEntity);
								continue;
							}
							
							Connection connection = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate")
									.header("Connection", "keep-alive")
									.header("Accept-Language", "zh-cn")
									.header("Cache-Control", "no-cache")
									.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
							
							Map<String, String> cookies = HttpUtil.getCookies();
							if (cookies!=null && cookies.size()>0) {
								connection.cookies(cookies);
							}
							
							String userAgent=HttpUtil.getUserAgent();
							if (userAgent!=null) {
								connection.userAgent(userAgent);
							}
							Response res = connection.timeout(10000).execute();
							if(res.statusCode()==200){
								LogUtil.info(LOGGER, "爬取airbnb列表成功，url={}",url);
				            	HttpUtil.updateCookies(res.cookies());
				            	doc = res.parse();
				            }else {
				            	FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
								failUrlRecordsEntity.setCreateDate(new Date());
								failUrlRecordsEntity.setFailReason("请求失败："+res.toString() );
								failUrlRecordsEntity.setUrl(url);
								failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbList.getCode());
								failUrlRecords.add(failUrlRecordsEntity);
								continue;
							}
						} catch (IOException e) {
							FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
							failUrlRecordsEntity.setCreateDate(new Date());
							failUrlRecordsEntity.setFailReason(e.toString());
							failUrlRecordsEntity.setUrl(url);
							failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbList.getCode());
							failUrlRecords.add(failUrlRecordsEntity);
							LogUtil.error(LOGGER, "爬取airbnb列表失败，url={},e={}",url,e);
							continue;
						}
						if (doc!=null) {
							
							analysisListDoc(doc, street,failUrlRecords);
							
							//更新下一页
							String nextPageStr="";
							Elements nextPageElements = Selector.select("div.pagination-responsive>ul.list-unstyled>li.next_page>a", doc.getElementsByClass("pagination"));
							if(nextPageElements!=null && nextPageElements.size()==1){
								nextPageStr=nextPageElements.attr("href");
								if (nextPageStr!=null && nextPageStr.length()>0) {
									nextPageStr=nextPageStr.substring(nextPageStr.lastIndexOf("page=")+1);
									nextPageStr=nextPageStr.substring(nextPageStr.lastIndexOf("=")+1);
									nextPageStr=nextPageStr.trim();
								}
							} 
							if (nextPageStr.length()>0) {
								listRequest.setPage(Integer.valueOf(nextPageStr));
							}else {
								listRequest.setPage(-1);
							}
							
							//街区
							Elements listJsonElements = Selector.select("div[data-bootstrap-data]", doc.getElementsByClass("map-search"));
							if(listJsonElements!=null && listJsonElements.size()==1){
								String listJson=listJsonElements.attr("data-bootstrap-data").trim();
								JSONObject listJsonObject = JSONObject.parseObject(listJson);
								if (listJsonObject.containsKey("results_json") && listJsonObject.getJSONObject("results_json").containsKey("metadata")
										&& listJsonObject.getJSONObject("results_json").getJSONObject("metadata").containsKey("facets")
										&& listJsonObject.getJSONObject("results_json").getJSONObject("metadata").getJSONObject("facets").containsKey("neighborhood_facet")) {
									JSONArray streetJsonObject =listJsonObject.getJSONObject("results_json").getJSONObject("metadata").getJSONObject("facets").getJSONArray("neighborhood_facet");
									if (streetJsonObject!=null && streetJsonObject.size()>0) {
										Map<String,String> map = new HashMap<String, String>();
										for (int i = 0; i < streetJsonObject.size(); i++) {
											JSONObject streetObj=streetJsonObject.getJSONObject(i);
											if (streetObj!=null) {
												map.put(streetObj.getString("key"), streetObj.getString("value"));
											}
										}
										
										if (map.size()>0) {
											listRequest.setNeighborhoods(map.keySet().toArray(new String[]{}));
										}
									} 
									
									
								}
							}
							
							
						} 				
					}
					listRequest.setPage(1);//从第一页开始
					
				}
				
			}
		}
		
	
	}
 
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月21日 上午10:25:09
	 *
	 * @param listUrl
	 * @param failUrlRecords
	 */
	public static void parseHouseListByListUrl(String listUrl,Set<FailUrlRecordsEntity> failUrlRecords){
		
		if (listUrl==null ) {
			return;
		}
		 
		if (failUrlRecords==null) {
			failUrlRecords = new HashSet<FailUrlRecordsEntity>();
		}
		
		Document doc = null;
		try {
			String ipPort =HttpUtil.getXiaozhuIpAndPort();
			if (ipPort!=null) {
				String ip = ipPort.substring(0, ipPort.indexOf(":"));
				String port= ipPort.substring(ipPort.indexOf(":")+1);
				System.setProperty("http.maxRedirects", "50");
				System.getProperties().setProperty("proxySet", "true");
				System.getProperties().setProperty("http.proxyHost", ip);
				System.getProperties().setProperty("http.proxyPort", port);
			}else {
				FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
				failUrlRecordsEntity.setCreateDate(new Date());
				failUrlRecordsEntity.setFailReason("获取代理ip失败");
				failUrlRecordsEntity.setUrl(listUrl);
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbList.getCode());
				failUrlRecords.add(failUrlRecordsEntity);
				return;
			}
			
			LogUtil.info(LOGGER, "爬取airbnb列表，url={}",listUrl);
			Connection connection = Jsoup.connect(listUrl).header("Accept-Encoding", "gzip, deflate")
					.header("Connection", "keep-alive")
					.header("Accept-Language", "zh-cn")
					.header("Cache-Control", "no-cache")
					.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			
			Map<String, String> cookies = HttpUtil.getCookies();
			if (cookies!=null && cookies.size()>0) {
				connection.cookies(cookies);
			}
			
			String userAgent=HttpUtil.getUserAgent();
			if (userAgent!=null) {
				connection.userAgent(userAgent);
			}
			Response res = connection.timeout(10000).execute();
			if(res.statusCode()==200){
				LogUtil.info(LOGGER, "爬取airbnb列表成功，url={}",listUrl);
            	HttpUtil.updateCookies(res.cookies());
            	doc = res.parse();
            }else {
            	FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
				failUrlRecordsEntity.setCreateDate(new Date());
				failUrlRecordsEntity.setFailReason("请求失败："+res.toString() );
				failUrlRecordsEntity.setUrl(listUrl);
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbList.getCode());
				failUrlRecords.add(failUrlRecordsEntity);
				return;
			}
		} catch (IOException e) {
			FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
			failUrlRecordsEntity.setCreateDate(new Date());
			failUrlRecordsEntity.setFailReason(e.toString());
			failUrlRecordsEntity.setUrl(listUrl);
			failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbList.getCode());
			failUrlRecords.add(failUrlRecordsEntity);
			LogUtil.error(LOGGER, "爬取airbnb列表失败，url={},e={}",listUrl,e);
			return;
		}
		
		if (doc!=null) {
			analysisListDoc(doc,null,failUrlRecords);
		}
		
	
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月19日 下午1:30:28
	 *
	 * @param listDoc
	 * @param street
	 * @param failUrlRecords
	 */
	private static void analysisListDoc(Document listDoc,String street,Set<FailUrlRecordsEntity> failUrlRecords){
		if (listDoc==null) {
			return;
		}
		if (failUrlRecords==null) {
			failUrlRecords= new HashSet<FailUrlRecordsEntity>();
		}
		
		
		Elements housElements = Selector.select("a.media-photo", listDoc);
		if (housElements!=null) {
			LogUtil.info(LOGGER, "列表房源{}个",housElements.size());
			for (int i=0;i<housElements.size();i++) {
				Element houseDetailUrlElements =housElements.get(i);
				if(houseDetailUrlElements!=null){
					String houseDetailUrl=houseDetailUrlElements.attr("href").trim();
					if (houseDetailUrl.indexOf("?")>-1) {
						houseDetailUrl=houseDetailUrl.substring(0, houseDetailUrl.indexOf("?"));
					}
					try {
						Thread.sleep(HttpUtil.getRandTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					LogUtil.info(LOGGER, "开始爬取房源，url={}",baseUrl+houseDetailUrl);
					try {
						AirbnbHouseInfoTasker houseInfoTasker = new AirbnbHouseInfoTasker(baseUrl+houseDetailUrl, street);
						threadPoolExecutor.execute(houseInfoTasker);
					} catch (Exception e) {
						FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
						failUrlRecordsEntity.setCreateDate(new Date());
						failUrlRecordsEntity.setFailReason(e.toString());
						failUrlRecordsEntity.setUrl(baseUrl+houseDetailUrl);
						failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHouse.getCode());
						failUrlRecords.add(failUrlRecordsEntity);
						LogUtil.error(LOGGER, "爬取房源失败，url={},e={}",baseUrl+houseDetailUrl,e);
					} 
					
				}
				
			}
		}
		
//		Elements hostElements = Selector.select("a[rel*=/users/]", listDoc);
//		if (hostElements!=null) { 
//			
//			for (int i=0;i<hostElements.size();i++) {
//				Element hostUrlElement =hostElements.get(i);
//				if(hostUrlElement!=null){
//					String hostDetailUrl=hostUrlElement.attr("href").trim();
//					if (hostDetailUrl.indexOf("?")>-1) {
//						hostDetailUrl=hostDetailUrl.substring(0, hostDetailUrl.indexOf("?"));
//					}
//					try {
//						Thread.sleep(HttpUtil.getRandTime());
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					LogUtil.info(LOGGER, "开始爬取房东，url={}",baseUrl+hostDetailUrl);
//					try {
//						AirbnbHostTasker hostTasker= new AirbnbHostTasker(baseUrl+hostDetailUrl);
//						threadPoolExecutor.execute(hostTasker);
//					} catch (Exception e) {
//						FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
//						failUrlRecordsEntity.setCreateDate(new Date());
//						failUrlRecordsEntity.setFailReason(e.toString());
//						failUrlRecordsEntity.setUrl(baseUrl+hostDetailUrl);
//						failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHost.getCode());
//						failUrlRecords.add(failUrlRecordsEntity);
//						LogUtil.error(LOGGER, "爬取房东失败，url={},e={}",baseUrl+hostDetailUrl,e);
//					}
//				}
//				
//				
//			}
//			
//		}
		
		
		
		
	}
	
	 
	public static void main(String[] args) {
//		parseHouseList();
	}
	
}
