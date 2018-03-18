/**
 * @FileName: XiaozhuProcessor.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.processor
 * 
 * @author zl
 * @created 2016年10月14日 下午4:23:07
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.processor;

import java.io.IOException;
import java.util.Date;
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

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.spider.commons.HttpUtil;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.entity.enums.FailUrlRecordTypeEnum;
import com.ziroom.minsu.spider.xiaozhunew.entity.enums.HouseTypeEnum;
import com.ziroom.minsu.spider.xiaozhunew.entity.enums.RentTypeEnum;
import com.ziroom.minsu.spider.xiaozhunew.entity.enums.SortTypeEnum;
import com.ziroom.minsu.spider.xiaozhunew.processor.tasks.XiaozhuHostTasker;
import com.ziroom.minsu.spider.xiaozhunew.processor.tasks.XiaozhuHouseTasker;


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
public class XiaozhuProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(XiaozhuProcessor.class);
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30, 100, 300000L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
	 
	private static String xz_srf_token =null;
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 下午4:26:18
	 *
	 * @param cityAbbreviation
	 */
	public static void pearseList(String cityAbbreviation,Set<FailUrlRecordsEntity> failUrlRecords){
		if (cityAbbreviation==null || cityAbbreviation.length()==0) {
			return;
		}
		
		if (failUrlRecords==null) {
			failUrlRecords = new HashSet<FailUrlRecordsEntity>();
		}
		String[] areas=new String[]{""};
		RentTypeEnum[] rentTypes=RentTypeEnum.values();
		HouseTypeEnum[] houseTypes=HouseTypeEnum.values();
		SortTypeEnum[] sortTypes= SortTypeEnum.values();
		
		for (int l = 0; l < sortTypes.length; l++) {
			SortTypeEnum sortTypeEnum = sortTypes[l];
			
			int areaIndex=0;
			while(areaIndex<areas.length){//按照区域循环
				String area=areas[areaIndex];
				if (area==null || area.length()==0) {
					area="";
					areas=new String[]{};
				}else {
					areaIndex++;
				}
				
				for (int i = 0; i < rentTypes.length; i++) {//按照出租方式
					RentTypeEnum rentType=rentTypes[i];
					
					for (int j = 0; j < houseTypes.length; j++) {//按照户型
						HouseTypeEnum houseType=houseTypes[j];
						
						for (int guestnum = 1; guestnum < 11; guestnum++) {//按照宜居人数
							int page=1;
							while(page>0){
								String url="http://"+cityAbbreviation+".xiaozhu.com/";
								if (area!=null && area.length()>0) {
									url=url+area+"-";
								}
								
								url=url+rentType+"-"+sortTypeEnum+"-duanzufang-p"+page;
								if (area!=null && area.length()>0) {
									url=url+"-1a";
								}else {
									url=url+"-12";
								}
								url=url+"/?guestnum="+guestnum+"&housetyperoomcnt="+houseType+"%7C";
								LogUtil.info(LOGGER, "爬取小猪列表，url={}",url);
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
										failUrlRecordsEntity.setUrl(url);
										failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuList.getCode());
										failUrlRecords.add(failUrlRecordsEntity);
										continue;
									}
									try {
										Thread.sleep(HttpUtil.getRandTime(10000, 20000));
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									
									Connection connection = Jsoup.connect(url).header("Accept", "application/json, text/javascript, */*;")
											.header("Accept-Encoding", "gzip, deflate")
											.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
											.header("Connection", "keep-alive")
											.header("Cache-Control", "max-age=0")
											.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
											.header("Host", cityAbbreviation+".xiaozhu.com")
											.header("Referer", url)
											.header("X-Requested-With", "XMLHttpRequest");
									
									Map<String, String> cookies = HttpUtil.getXiaozhuCookies();
									if (cookies!=null && cookies.size()>0) {
										connection.cookies(cookies);
									}
									
									if (xz_srf_token!=null) {
										connection.header("xSRF-Token", xz_srf_token);
									}
									
									String userAgent=HttpUtil.getUserAgent();
									if (userAgent!=null) {
										connection.userAgent(userAgent);
									}
									
									Response res = connection.timeout(10000).execute();
									if(res.statusCode()==200){
										LogUtil.info(LOGGER, "爬取小猪列表成功，url={}",url);
						            	HttpUtil.updateXiaozhuCookies(res.cookies());
						            	doc = res.parse();
						            }else {
						            	FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
										failUrlRecordsEntity.setCreateDate(new Date());
										failUrlRecordsEntity.setFailReason("请求失败："+res.toString() );
										failUrlRecordsEntity.setUrl(url);
										failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuList.getCode());
										failUrlRecords.add(failUrlRecordsEntity);
										continue;
									}
									
								} catch (IOException e) {
									FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
									failUrlRecordsEntity.setCreateDate(new Date());
									failUrlRecordsEntity.setFailReason(e.toString());
									failUrlRecordsEntity.setUrl(url);
									failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuList.getCode());
									failUrlRecords.add(failUrlRecordsEntity);
									LogUtil.error(LOGGER, "爬取小猪列表失败，url={},e={}",url,e);
									continue;
								}
								
								if (doc!=null) {
									
									
									//analysisListDoc(doc);
									
									
									//区域参数
									try {
										if (doc.getElementById("xingzhengquS_line_box")!=null) {
											
											Elements areasElements = Selector.select("div>span>a", doc.getElementById("xingzhengquS_line_box"));
											if (areasElements!=null) {
												areas=new String[areasElements.size()];
												for (int k = 0; k < areasElements.size(); k++) {
													Element areaElement = areasElements.get(k);
													if (areaElement!=null) {
														String areastr=areaElement.attr("href");
														if (areastr!=null && areastr.length()>0) {
															areastr=areastr.substring(areastr.indexOf(".com/")+5, areastr.indexOf("-"));
														}
														areas[k]=areastr; 
//													System.out.println(areastr);
													}
												}
											}
										}
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									//下一页
									try {
										Elements pageElements = Selector.select("div>a.font_st", doc.getElementsByClass("pagination_v2"));
										if (pageElements!=null) {
											
											String pagestr=pageElements.attr("href");
											if (pagestr!=null && pagestr.length()>0) {
												pagestr=pagestr.substring(pagestr.indexOf("duanzufang"), pagestr.indexOf("?"));
												pagestr=pagestr.substring(pagestr.indexOf("-p")+2, pagestr.lastIndexOf("-"));
												
											}
//											System.out.println(pagestr);
											if (pagestr!=null && pagestr.length()>0) {
												page=Integer.valueOf(pagestr);									
											}
										}else {
											page=-1;
										}
										
									} catch (Exception e) {
										page=-1;
										e.printStackTrace();
									}
									
								}
								
							}
							
						}
						
					}
					
				}
			}
			
		}
		
		
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月21日 上午10:42:20
	 *
	 * @param listUrl
	 */
	public static void pearseListByListUrl(String listUrl,Set<FailUrlRecordsEntity> failUrlRecords){
		if (listUrl==null || listUrl.length()==0) {
			return;
		}
		
		if (failUrlRecords==null) {
			failUrlRecords= new HashSet<FailUrlRecordsEntity>();
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
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuList.getCode());
				failUrlRecords.add(failUrlRecordsEntity);
				return;
			}
			try {
				Thread.sleep(HttpUtil.getRandTime(10000,20000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			LogUtil.info(LOGGER, "爬取小猪列表，url={}",listUrl);
			Connection connection = Jsoup.connect(listUrl).header("Accept", "application/json, text/javascript, */*;")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0")
					.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
					.header("X-Requested-With", "XMLHttpRequest");
			
			Map<String, String> cookies = HttpUtil.getXiaozhuCookies();
			if (cookies!=null && cookies.size()>0) {
				connection.cookies(cookies);
			}
			
			if (xz_srf_token!=null) {
				connection.header("xSRF-Token", xz_srf_token);
			}
			
			String userAgent=HttpUtil.getUserAgent();
			if (userAgent!=null) {
				connection.userAgent(userAgent);
			}
			Response res = connection.timeout(10000).execute();
			if(res.statusCode()==200){
				LogUtil.info(LOGGER, "爬取小猪列表成功，url={}",listUrl);
            	HttpUtil.updateXiaozhuCookies(res.cookies());
            	doc = res.parse();
            }else {
            	FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
				failUrlRecordsEntity.setCreateDate(new Date());
				failUrlRecordsEntity.setFailReason("请求失败："+res.toString() );
				failUrlRecordsEntity.setUrl(listUrl);
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuList.getCode());
				failUrlRecords.add(failUrlRecordsEntity);
				return;
			}
		} catch (IOException e) {
			FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
			failUrlRecordsEntity.setCreateDate(new Date());
			failUrlRecordsEntity.setFailReason(e.toString());
			failUrlRecordsEntity.setUrl(listUrl);
			failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuList.getCode());
			failUrlRecords.add(failUrlRecordsEntity);
			LogUtil.error(LOGGER, "爬取小猪列表失败，url={},e={}",listUrl,e);
			return;
		}
		if (doc!=null) {
			analysisListDoc(doc);
		}
		
		
	}
	
	/**
	 * 小猪房源列表
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月20日 下午3:00:23
	 *
	 * @param listDoc
	 */
	public static void analysisListDoc(Document listDoc){
		if (listDoc==null) {
			return;
		}
		
		
		Element xz_srf_tokenElement= listDoc.getElementById("xz_srf_token");
		if (xz_srf_tokenElement!=null) {
			xz_srf_token = xz_srf_tokenElement.attr("value").trim(); 
			 
		}
		
		
		Elements housElements = Selector.select("div>ul>li", listDoc.getElementById("page_list"));
	    if (housElements!=null && housElements.size()>0) {
	    	LogUtil.info(LOGGER, "列表房源{}个",housElements.size());
	    	for (Element housElement : housElements) {
	    		Elements housDeatilElements = Selector.select("a.resule_img_a", housElement);
	    		if (housDeatilElements!=null && housDeatilElements.size()==1) {
					String houseUrl=housDeatilElements.attr("href");
//					System.out.println("houseUrl="+houseUrl);
					Float reviewScore =null;
					Integer reviewCount =null;
					try {
						Elements evaluateElements = Selector.select("div.result_btm_con>div.result_intro>em.hiddenTxt>span", housElement);
						if (evaluateElements!=null && evaluateElements.size()==1) {
							String evaluateText = evaluateElements.get(0).text().trim();
							if (evaluateText!=null) {
								String[] valStrs= evaluateText.split("/");
								if (valStrs!=null && valStrs.length>0) {
									for (String valString : valStrs) {
										if (valString.contains("分")) {
											valString=valString.replace("分", "");
											 reviewScore = Float.valueOf(valString);
											
										}else if (valString.contains("条")) {
											valString=valString.replace("条点评", "");
											 reviewCount = Integer.valueOf(valString);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					XiaozhuHouseTasker houseTasker = new XiaozhuHouseTasker(houseUrl,xz_srf_token,reviewCount);
//					threadPoolExecutor.execute(houseTasker);
					houseTasker.run();
					
				}
	    		Elements hostElements = Selector.select("div.result_btm_con>span.result_img>a", housElement);
	    		if (hostElements!=null && hostElements.size()==1) {
					String hostUrl=hostElements.attr("href");
//					System.out.println("hostUrl="+hostUrl);
					XiaozhuHostTasker tasker = new XiaozhuHostTasker(hostUrl,xz_srf_token);
//					threadPoolExecutor.execute(tasker);
					tasker.run();
				}
	    		
	    		
	    	}
	    }
		
		
		
		
	}
	
	
}
