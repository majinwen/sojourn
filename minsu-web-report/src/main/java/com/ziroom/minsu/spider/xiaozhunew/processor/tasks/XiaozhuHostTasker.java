/**
 * @FileName: XiaozhuHostTasker.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.processor.tasks
 * 
 * @author zl
 * @created 2016年10月21日 下午2:59:17
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.processor.tasks;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import com.ziroom.minsu.spider.commons.BaseTasker;
import com.ziroom.minsu.spider.commons.HttpUtil;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.entity.enums.FailUrlRecordTypeEnum;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;
import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHostInfoEntity;
import com.ziroom.minsu.spider.xiaozhunew.service.XiaozhuHostEntityService;

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
public class XiaozhuHostTasker extends BaseTasker implements Runnable {
	 
		private String url=null;
		
		private String xz_srf_token=null;
		
		private FailUrlRecordsService failUrlService;
		
		private XiaozhuHostEntityService hostService;
		
		
		public XiaozhuHostTasker(String url, String xz_srf_token) {
			super();
			this.url = url;
			this.xz_srf_token = xz_srf_token;
			failUrlService = super.getBeanByClass(FailUrlRecordsService.class);
			hostService = super.getBeanByClass(XiaozhuHostEntityService.class);
		}

		
		@Override
		public void run() {
			
			try {
				
				System.err.println(url);
				if (url==null) {
					return;
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
						failUrlRecordsEntity.setUrl(url);
						failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuHost.getCode());
							failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
						return;
					}
					
					
					Connection connection = Jsoup.connect(url).header("Accept", "application/json, text/javascript, */*;")
							.header("Accept-Encoding", "gzip, deflate")
							.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
							.header("Connection", "keep-alive")
							.header("Cache-Control", "max-age=0")
							.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
							.header("Host", HttpUtil.getHost(url))
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
						System.err.println("got");
		            	HttpUtil.updateXiaozhuCookies(res.cookies());
		            	doc = res.parse();
		            }
					
				} catch (IOException e) {
					e.printStackTrace();
					FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
					failUrlRecordsEntity.setCreateDate(new Date());
					failUrlRecordsEntity.setFailReason(e.toString());
					failUrlRecordsEntity.setUrl(url);
					failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuHost.getCode());
						failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
					return;
				}
				
				if (doc!=null) {
					
					if (doc.getElementsByClass("fd_infor")==null && doc.getElementsByClass("person_infor")==null) {
						return;
					}
					
					XiaozhuNewHostInfoEntity hostInfoEntity = new XiaozhuNewHostInfoEntity();
					hostInfoEntity.setDetailUrl(url);					
					
					String hostSn = url.substring(0,url.lastIndexOf("/"));
					hostSn = hostSn.substring(hostSn.lastIndexOf("/")+1);
//					System.out.println("hostSn="+hostSn); 
					hostInfoEntity.setHostSn(hostSn);
					
					
					String string = doc.text();
					if (string.contains("该房东暂未开通房东专页")) {
						
						Elements nameElements = Selector.select("div>h1", doc.getElementsByClass("fd_infor"));
						if(nameElements!=null && nameElements.size()==1){
							String name=nameElements.get(0).text().trim();
//							System.out.println("name="+name);
							hostInfoEntity.setHostName(name);	
						}
						
						Elements eElements = Selector.select("div>ul>li", doc.getElementsByClass("fd_infor"));
						if(eElements!=null && eElements.size()>0){
							for (Element element : eElements) {
								String lable=element.text().trim();
								lable=lable.substring(0, lable.indexOf("："));
								Elements valElement= Selector.select("span>strong", element.getElementsByTag("span")); 
								if(valElement!=null && valElement.size()==1){
									if (lable.contains("房源")) {
										try {
											String valString = valElement.text().trim();
											if (valString!=null && valString.length()>0) {
												Integer valInteger = Integer.valueOf(valString);
												hostInfoEntity.setHouseCount(valInteger);
//												System.out.println("房源="+valInteger);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else if (lable.contains("在线的回复率")) {
										try {
											String valString = valElement.text().trim();
											if (valString!=null && valString.length()>0) {
												
												valString=valString.replace("%", "");
												Double valDouble = Double.valueOf(valString);
												valDouble =valDouble/100;
												
//												System.out.println("在线的回复率="+valDouble);
												hostInfoEntity.setOnlineReplyRate(valDouble);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else if (lable.contains("点评")) {
										try {
											String valString = valElement.text().trim();
											if (valString!=null && valString.length()>0) {
												valString=valString.substring(0, valString.indexOf("/"));
												
												Float val = Float.valueOf(valString);
												
//												System.out.println("点评="+val);
												hostInfoEntity.setReviewsScore(val);
											}
										
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else if (lable.contains("平均确认时间")) {
										String valString = valElement.text().trim();
										
//										System.out.println("平均确认时间="+valString);
										hostInfoEntity.setAveSureTime(valString);
									}else if (lable.contains("预订")) {
										try {
											String valString = valElement.text().trim();
											if (valString!=null && valString.length()>0) {
												Integer valInteger = Integer.valueOf(valString);
												
//												System.out.println("预订="+valInteger);
												hostInfoEntity.setOrderCount(valInteger);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else if (lable.contains("订单的接受率")) {
										try {
											String valString = valElement.text().trim();
											if (valString!=null && valString.length()>0) {
												
												valString=valString.replace("%", "");
												Double valDouble = Double.valueOf(valString);
												valDouble =valDouble/100;
												
//												System.out.println("订单的接受率="+valDouble);
												hostInfoEntity.setOrderAcceptRate(valDouble);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								
							}
						}
						
						
						
					}else {//有专题页
						
						Elements nameElements = Selector.select("div>div.fd_name", doc.getElementsByClass("person_infor"));
						if(nameElements!=null && nameElements.size()==1){
							String name=nameElements.text().trim();
							
//							System.out.println("name="+name);
							hostInfoEntity.setHostName(name);	
						}
						
						Elements infoElements = Selector.select("div>ul.fd_person", doc.getElementsByClass("person_infor"));
						if(infoElements!=null && infoElements.size()==1){
							String info=infoElements.text().trim();
							
//							System.out.println("info="+info);
							hostInfoEntity.setHostInfo(info);
						}
						
						Elements liElements = Selector.select("div>ul.infor_ul>li", doc.getElementsByClass("person_infor"));
						if(liElements!=null && liElements.size()>0){
							for (Element element : liElements) {
								String lable=element.text().trim();
								Elements valElement= Selector.select("li>strong", element.getElementsByTag("li")); 
								if(valElement!=null && valElement.size()==1){
									
									if (lable.contains("在线回复率")) {
										try {
											String valString = valElement.text().trim();
											if (valString!=null && valString.length()>0) {
												
												valString=valString.replace("%", "");
												Double valDouble = Double.valueOf(valString);
												valDouble =valDouble/100;
												
//												System.out.println("在线回复率="+valDouble);
												hostInfoEntity.setOnlineReplyRate(valDouble);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else if (lable.contains("平均确认")) {
										String valString = valElement.text().trim();
										
//										System.out.println("平均确认="+valString);
										hostInfoEntity.setAveSureTime(valString);
									}else if (lable.contains("订单接受率")) {
										try {
											String valString = valElement.text().trim();
											if (valString!=null && valString.length()>0) {
												
												valString=valString.replace("%", "");
												Double valDouble = Double.valueOf(valString);
												valDouble =valDouble/100;
												
//												System.out.println("订单接受率="+valDouble);
												hostInfoEntity.setOrderAcceptRate(valDouble);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									
								}
							}
						}
						
						
						Elements eElements = Selector.select("ul>a>li", doc.getElementsByClass("fd_navUl"));
						if(eElements!=null && eElements.size()>0){
							for (Element element : eElements) {
								String lable=element.text().trim();
								Elements valElement= element.getElementsByTag("span"); 
								if(valElement!=null && valElement.size()==1){
									if (lable.contains("短租日记")) {
										String valString = valElement.text().trim();
										if (valString!=null) {
											valString =valString.replaceAll("[\\u4e00-\\u9fa5]", "");
											valString =valString.replaceAll("\\[", "");
											valString =valString.replaceAll("\\]", "");
											
											System.out.println("短租日记="+valString);
										}
									}else if (lable.contains("房源信息")) {
										String valString = valElement.text().trim();
										if (valString!=null) {
											valString =valString.replaceAll("[\\u4e00-\\u9fa5]", "");
											valString =valString.replaceAll("\\[", "");
											valString =valString.replaceAll("\\]", "");
											try {
												hostInfoEntity.setHouseCount(Integer.valueOf(valString));
											} catch (Exception e) {
												e.printStackTrace();
											}
//											System.out.println("房源信息="+valString);
										}
									}else if (lable.contains("房客点评")) {
										String valString = valElement.text().trim();
										if (valString!=null) {
											valString =valString.replaceAll("[\\u4e00-\\u9fa5]", "");
											valString =valString.replaceAll("\\[", "");
											valString =valString.replaceAll("\\]", "");
											try {
												hostInfoEntity.setReviewsCount(Integer.valueOf(valString));
											} catch (Exception e) {
												e.printStackTrace();
											}
//											System.out.println("房客点评="+valString);
										}
									}else if (lable.contains("预订历史")) {
										String valString = valElement.text().trim();
										if (valString!=null) {
											valString =valString.replaceAll("[\\u4e00-\\u9fa5]", "");
											valString =valString.replaceAll("\\[", "");
											valString =valString.replaceAll("\\]", "");
											try {
												hostInfoEntity.setOrderCount(Integer.valueOf(valString));
											} catch (Exception e) {
												e.printStackTrace();
											}
//											System.out.println("预订历史="+valString);
										}
									}
								}
							}
						}
						
					}
					
					hostService.saveAirbnbHostInfo(hostInfoEntity);
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
				failUrlRecordsEntity.setCreateDate(new Date());
				failUrlRecordsEntity.setFailReason(e.toString());
				failUrlRecordsEntity.setUrl(url);
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuHost.getCode());
				try {
					failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
					
		}

}
