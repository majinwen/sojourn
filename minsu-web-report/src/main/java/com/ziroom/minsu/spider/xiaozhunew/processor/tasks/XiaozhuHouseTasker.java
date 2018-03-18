/**
 * @FileName: XiaozhuHouseTasker.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.processor.tasks
 * 
 * @author zl
 * @created 2016年10月20日 下午7:10:43
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.processor.tasks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.spider.commons.BaseTasker;
import com.ziroom.minsu.spider.commons.HttpUtil;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.entity.enums.FailUrlRecordTypeEnum;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;
import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHouseInfoEntityWithBLOBs;
import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHousePriceEntity;
import com.ziroom.minsu.spider.xiaozhunew.entity.enums.RentTypeEnum;
import com.ziroom.minsu.spider.xiaozhunew.service.XiaozhuHouseInfoEntityService;

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
public class XiaozhuHouseTasker extends BaseTasker implements Runnable {
 
	private static String url=null;
	
	private static String xz_srf_token=null;
	
	private FailUrlRecordsService failUrlService;
	
	private XiaozhuHouseInfoEntityService houseService;
	
	private static Integer reviewCount=null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XiaozhuHouseTasker.class);
	
	public XiaozhuHouseTasker(String url,String xz_srf_token,Integer reviewCount) {
		super();
		this.url = url;
		this.xz_srf_token = xz_srf_token;
		this.reviewCount = reviewCount;
		failUrlService = super.getBeanByClass(FailUrlRecordsService.class);
		houseService = super.getBeanByClass(XiaozhuHouseInfoEntityService.class);
	}

	@Override
	public void run() {
		try {
			LogUtil.info(LOGGER, "开始爬取房源，url={}",url);
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
					failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuHouse.getCode());
					failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
					LogUtil.info(LOGGER, "爬取房源获取代理ip失败，url={}",url);
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
					LogUtil.info(LOGGER, "获取房源成功，url={}",url);
	            	HttpUtil.updateXiaozhuCookies(res.cookies());
	            	doc = res.parse();
	            }else {
	            	FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
					failUrlRecordsEntity.setCreateDate(new Date());
					failUrlRecordsEntity.setFailReason("请求失败："+res.toString() );
					failUrlRecordsEntity.setUrl(url);
					failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuHouse.getCode());
					failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
					LogUtil.info(LOGGER, "获取房源失败，url={},res={}",url,res.toString());
					return;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
				failUrlRecordsEntity.setCreateDate(new Date());
				failUrlRecordsEntity.setFailReason(e.toString());
				failUrlRecordsEntity.setUrl(url);
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuHouse.getCode());
				failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
				LogUtil.info(LOGGER, "获取房源失败，url={},e={}",url,e);
				return;
			}
			if (doc!=null) {
				
				if (doc.getElementById("introduce")==null) {
					return;
				}
				
				
				XiaozhuNewHouseInfoEntityWithBLOBs houseInfoEntity =new XiaozhuNewHouseInfoEntityWithBLOBs();
				
				houseInfoEntity.setDetailUrl(url);
				
				
				String houseSn = url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("."));
//				System.out.println("houseSn="+houseSn);
				houseInfoEntity.setHouseSn(houseSn);
				
				Elements houseNameElements = Selector.select("div>h4", doc.getElementsByClass("pho_info"));
				if(houseNameElements!=null && houseNameElements.size()==1){
					String houseName=houseNameElements.get(0).text().trim();
//					System.out.println("houseName="+houseName);
					houseInfoEntity.setHouseName(houseName);
				}
				
				Elements addressElements = Selector.select("div>p", doc.getElementsByClass("pho_info"));
				if(addressElements!=null && addressElements.size()==1){
					String address=addressElements.get(0).attr("title").trim();
//					System.out.println("address="+address);
					houseInfoEntity.setAddress(address);
				}
				
				Elements cityNameElements = Selector.select("div>p>em>a:eq(1)", doc.getElementsByClass("pho_info"));
				if(cityNameElements!=null && cityNameElements.size()==1){
					String cityName=cityNameElements.get(0).text().trim();
					
//					System.out.println("cityName="+cityName);
					houseInfoEntity.setCity(cityName);
				}
				
				Elements scoreElements = Selector.select("div.top_bar li:matchesOwn(评分)", doc.getElementsByClass("con_r"));
				if(scoreElements!=null && scoreElements.size()==1){
					String address=scoreElements.get(0).text().trim();
					if (address!=null && address.length()>0) {
						address =address.replaceAll("评分：", "");
						address =address.replaceAll("分", "");
						address =address.replaceAll("[\\u4e00-\\u9fa5]", "");//有可能是“暂无”
					}
					if (address!=null && address.length()>0) {
						
						try {
							Float score=Float.valueOf(address);
							
//							System.out.println("score="+score);
							houseInfoEntity.setReviewScore(score);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				Elements priceElements = Selector.select("div>div.day_l>span", doc.getElementById("pricePart"));
				if(priceElements!=null && priceElements.size()==1){
					String priceStr=priceElements.get(0).text().trim();
					if (priceStr!=null && priceStr.length()>0) {
						Integer price = Integer.valueOf(priceStr);
						
//						System.out.println("price="+price);
						houseInfoEntity.setHousePrice(price);
					}
				}
				
				
				
				Elements introduceElements = Selector.select("ul>li", doc.getElementById("introduce"));
				if(introduceElements!=null && introduceElements.size()>0){
					for (int i = 0; i < introduceElements.size(); i++) {
						Element el = introduceElements.get(i);
						if (el!=null) {
							String string = el.text().trim();
							
							if (string.contains("面积")) {
								Elements eElements = Selector.select("li>h6", el);
								if(eElements!=null && eElements.size()==1){  
									String rentTypeName=eElements.get(0).text().trim();
									if (rentTypeName!=null && rentTypeName.length()>0) {
										Integer rentType = RentTypeEnum.getCodeByName(rentTypeName);
										
										
//										System.out.println("rentType="+rentType);
										houseInfoEntity.setRentType(rentType);
										houseInfoEntity.setRentTypeName(rentTypeName);
									}
//									System.out.println("rentTypeName="+rentTypeName);
								}
								
								Elements pElements = Selector.select("li>p", el);
								if(pElements!=null && pElements.size()==1){  
									String pText= pElements.text().trim();
									if (pText!=null && pText.length()>0) {
										try {
											String areaStr = pText.substring(pText.indexOf("面积：")+3, pText.indexOf("平米"));
//											System.out.println("areaStr="+areaStr);
											houseInfoEntity.setHouseArea(Double.valueOf(areaStr));
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										try {
											String houseTypeStr = pText.substring(pText.indexOf("户型：")+3);
//											System.out.println("houseTypeStr="+houseTypeStr);
											
											if (houseTypeStr!=null && houseTypeStr.length()>0) {
												
												Pattern pat = Pattern.compile("(\\d+)[\\u4e00-\\u9fa5]+"); 
												Matcher mat = pat.matcher(houseTypeStr); 
												while (mat.find()) {
													String str=mat.group();
													
													if (str.contains("室")) {
														str=str.replace("室", "");
														if (str!=null && str.length()>0) {
															try {
																Integer roomNum = Integer.valueOf(str);
																
//																System.out.println("roomNum="+roomNum);
																houseInfoEntity.setRoomNum(roomNum);
															} catch (Exception e) {
																e.printStackTrace();
															}
														}
													}else if (str.contains("厅")) {
														str=str.replace("厅", "");
														try {
															Integer hallNum = Integer.valueOf(str);
															
//															System.out.println("hallNum="+hallNum);
															houseInfoEntity.setHallNum(hallNum);
														} catch (Exception e) {
															e.printStackTrace();
														}
														
													}else if (str.contains("卫")) {
														str=str.replace("卫", "");
														try {
															Integer toiletNum = Integer.valueOf(str);
															
//															System.out.println("toiletNum="+toiletNum);
															houseInfoEntity.setToiletNum(toiletNum);
														} catch (Exception e) {
															e.printStackTrace();
														}
													}else if (str.contains("厨")) {
														str=str.replace("厨", "");
														try {
															Integer kitchenNum = Integer.valueOf(str);
															
//															System.out.println("kitchenNum="+kitchenNum);
															houseInfoEntity.setKitchenNum(kitchenNum);
														} catch (Exception e) {
															e.printStackTrace();
														}
													}else if (str.contains("阳台")) {
														str=str.replace("阳台", "");
														try {
															Integer balconyNum = Integer.valueOf(str);
															
//															System.out.println("balconyNum="+balconyNum);
															houseInfoEntity.setBalconyNum(balconyNum);
														} catch (Exception e) {
															e.printStackTrace();
														}
													}
													
												}
											}
											
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								
							}else if (string.contains("宜住")) {
								Elements eElements = Selector.select("li>h6", el);
								if(eElements!=null && eElements.size()==1){  
									String capacity=eElements.get(0).text().trim();
									if (capacity!=null && capacity.length()>0) {
										capacity=capacity.replace("宜住", "");
										capacity=capacity.replace("人", "");
										capacity=capacity.replace("大于", "");
									}
									if (capacity!=null && capacity.length()>0) {
										try {
											Integer capacityVal = Integer.valueOf(capacity);
											
//											System.out.println("capacityVal="+capacityVal);
											houseInfoEntity.setPersonCapacity(capacityVal);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}else if (string.contains("米×")) {
								Elements eElements = Selector.select("li>h6", el);
								if(eElements!=null && eElements.size()==1){  
									String bedNumStr=eElements.get(0).text().trim();
									if (bedNumStr!=null && bedNumStr.length()>0) {
										bedNumStr=bedNumStr.replace("共", "");
										bedNumStr=bedNumStr.replace("张", "");
									}
									if (bedNumStr!=null && bedNumStr.length()>0) {
										try {
											Integer bedNum = Integer.valueOf(bedNumStr);
											
//											System.out.println("bedNum="+bedNum);
											houseInfoEntity.setBedCount(bedNum);
											
										} catch (Exception e) {
											e.printStackTrace();
										}
									} 
								}
								
								
								Elements pElements = Selector.select("li>p", el);
								if(pElements!=null && pElements.size()==1){  
									String pText= pElements.text().trim();
									if (pText!=null && pText.length()>0) {
										pText=pText.replaceAll("<br>", ";");
									}
									
//									System.out.println("bedInfo="+pText);
									houseInfoEntity.setBedInfo(pText);
								}
							}							
							
						}
					}
				}
				
				
				Elements contentElements = Selector.select("div>div.clearfix", doc.getElementById("introducePart"));
				if(contentElements!=null && contentElements.size()>0){
					for (Element element : contentElements) {
						String string = element.text();
						if (string.contains("个性描述")) {
							Elements eElements = Selector.select("div>div.info_r>div.intro_item_content", element);
							if(eElements!=null && eElements.size()==1){
								String desc = eElements.text().trim();
								
//								System.out.println("个性描述="+desc);
								houseInfoEntity.setPersonalDesc(desc);
							}
							
						}else if (string.contains("内部情况")) {
							Elements eElements = Selector.select("div>div.info_r>div:eq(0)", element);
							if(eElements!=null && eElements.size()==1){
								String desc = eElements.text().trim();
								
//								System.out.println("内部情况="+desc);
								houseInfoEntity.setIndoorDesc(desc);
							}
						}else if (string.contains("交通情况")) {
							Elements eElements = Selector.select("div>div.info_r>div:eq(0)", element);
							if(eElements!=null && eElements.size()==1){
								String desc = eElements.text().trim();
								
//								System.out.println("交通情况="+desc);
								houseInfoEntity.setTrafficDesc(desc);
							}
						}else if (string.contains("周边情况")) {
							Elements eElements = Selector.select("div>div.info_r>div:eq(0)", element);
							if(eElements!=null && eElements.size()==1){
								String desc = eElements.text().trim();
								
//								System.out.println("周边情况="+desc);
								houseInfoEntity.setSurroundDesc(desc);
							}
						}else if (string.contains("配套设施")) {
							Elements eElements = Selector.select("div>div.info_r>div:eq(0)>ul>li", element);
							if(eElements!=null && eElements.size()>0){
								StringBuilder stringBuilder = new StringBuilder();
								for (Element li : eElements) {
									String clas=li.attr("class");
									if (clas!=null && clas.length()>0 && !clas.contains("no")) {
										stringBuilder.append(li.text().trim()).append("|");
									}
									
								}
								if (stringBuilder.length()>0) {
									stringBuilder.deleteCharAt(stringBuilder.length()-1);
								}
								
//								System.out.println("配套设施="+stringBuilder.toString());
								houseInfoEntity.setListingAmenities(stringBuilder.toString());
							}							
						}else if (string.contains("入住须知")) {
							
							Elements eElements = Selector.select("div>div.info_r>div.info_text_mid>ul>li", element);
							if(eElements!=null && eElements.size()>0){
								
								for (Element element2 : eElements) {
									String text = element2.text().trim();
									
									if (text.contains("卫生间：")) {
										text=text.replace("卫生间：", "");
										
//										System.out.println("卫生间类型="+text);
										houseInfoEntity.setToiletType(text);
									}else if (text.contains("最少入住天数：")) {
										text=text.replace("最少入住天数：", "");
										text=text.replace("天", "");
										
//										System.out.println("最少入住天数="+text);
										houseInfoEntity.setMinNights(text);
									}else if (text.contains("最多入住天数：")) {
										text=text.replace("最多入住天数：", "");
										text=text.replace("天", "");
										
//										System.out.println("最多入住天数="+text);
										houseInfoEntity.setMaxNights(text);
									}else if (text.contains("是否接待境外人士：")) {
										text=text.replace("是否接待境外人士：", "");
										
//										System.out.println("是否接待境外人士="+text);
										houseInfoEntity.setAllowsForeigner(text);
									}else if (text.contains("被单更换：")) {
										text=text.replace("被单更换：", "");
										
//										System.out.println("被单更换="+text);
										houseInfoEntity.setSheetReplace(text);
									} 
									
								}
								
							}
							
						}
					}
				}
				
				
				Elements rulesElements = Selector.select("div>div.clause_box", doc.getElementById("rulesPart"));
				if(rulesElements!=null && rulesElements.size()>0){
					for (Element rulesElement : rulesElements) {
						String text = rulesElement.text().trim();
						if (text.contains("押金及其他费用")) {
							
//							System.out.println("text="+text);
						houseInfoEntity.setChargeRule(text);
						}else if (text.contains("房东对房客的要求")) {
							
//							System.out.println("text="+text);
							houseInfoEntity.setRequireRule(text);
						}else if (text.contains("预订条款")) {
							
//							System.out.println("text="+text);
							houseInfoEntity.setBookRule(text);
						}else if (text.contains("退订说明")) {
							
						}
					}
				}
				

				Element latitudeElement= doc.getElementById("latitude");
				if (latitudeElement!=null) {
					String text = latitudeElement.attr("value").trim(); 
					if (text!=null && text.length()>0) {
						try {
							Double latitude = Double.valueOf(text);
							
//							System.out.println("latitude="+latitude);
							houseInfoEntity.setLatitude(latitude);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				Element longitudeElement= doc.getElementById("longitude");
				if (longitudeElement!=null) {
					String text = longitudeElement.attr("value").trim(); 
					if (text!=null && text.length()>0) {
						try {
							Double longitude = Double.valueOf(text);
							
//							System.out.println("longitude="+longitude);
							houseInfoEntity.setLongitude(longitude);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				
				if (reviewCount==null) {
					
					Element evaluateNumElement= doc.getElementById("thisroom");
					if (evaluateNumElement!=null) {
						String text = evaluateNumElement.text().trim();
						if (text!=null) {
							text=text.replace("本房源点评(", "");
							text=text.replace(")", "");
						}
						if (text!=null && text.length()>0) {
							try {
								Integer evaluateNum = Integer.valueOf(text);
								
								
//							System.out.println("evaluateNum="+evaluateNum);
								houseInfoEntity.setReviewCount(evaluateNum);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}else {
					houseInfoEntity.setReviewCount(reviewCount);
				}
				
				
				Element otherEvaluateNumElement= doc.getElementById("otherroom");
				if (otherEvaluateNumElement!=null) {
					String text = otherEvaluateNumElement.text().trim();
					if (text!=null) {
						text=text.replace("房东其他房源点评(", "");
						text=text.replace(")", "");
					}
					if (text!=null && text.length()>0) {
						try {
							Integer evaluateNum = Integer.valueOf(text);
							
//							System.out.println("otherroomEvaluateNum="+evaluateNum);
							houseInfoEntity.setHostOtherReviewCount(evaluateNum);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				
				
				
				
				Elements hostElements = Selector.select("div>div.js_box>div.member_pic>a", doc.getElementById("floatRightBox"));
				if(hostElements!=null && hostElements.size()==1){
					String hostUrl = hostElements.get(0).attr("href");
					try {
						if (hostUrl!=null) {
							String hostSn = hostUrl.substring(0,hostUrl.lastIndexOf("/"));
							hostSn = hostSn.substring(hostSn.lastIndexOf("/")+1);
							houseInfoEntity.setHostSn(hostSn);	
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				Elements hostNameElements = Selector.select("div>div.js_box>div.w_240>h6>a", doc.getElementById("floatRightBox"));
				if(hostNameElements!=null && hostNameElements.size()==1){
					houseInfoEntity.setHostName(hostNameElements.get(0).text().trim());
				}
				
				
				
				List<XiaozhuNewHousePriceEntity> priceEntities = new ArrayList<XiaozhuNewHousePriceEntity>();
				
				Calendar calendar =Calendar.getInstance();
				String priceUrl="http://"+HttpUtil.getHost(url)+"/ajax.php?op=AJAX_GetLodgeUnitCalendar&lodgeunitid="+houseSn
						+"&startdate="+new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				calendar.add(Calendar.MONTH, 2);
				priceUrl+="&enddate="+new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
//				priceUrl+="&editable=true";
				priceUrl+="&calendarCode=true";
//				priceUrl+="&rand="+new Random().nextDouble()+"&_="+new Random().nextDouble();
				priceUrl+="&_="+new Date().getTime();
				
				try {
					priceEntities.addAll(parseHousePrice( priceUrl));
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				houseInfoEntity.setPriceEntities(priceEntities);
//				
//				Elements calendarElements = Selector.select("div div.pl5>table>thead:eq(1)>tr>td", doc.getElementById("calendar-box"));
//				if (calendarElements!=null && calendarElements.size()>0) {
//					for (Element element : calendarElements) {
//						String dayString = element.attr("ymd");
//						if (dayString!=null && dayString.length()>0) {
//							try {
//								Date date = DateUtils.parseDate(dayString, "yyyy-MM-dd");
//								
//								System.out.println("dayString="+dayString);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//						String priceString =element.attr("price");
//						if (priceString!=null && priceString.length()>0) {
//							try {
//								Integer priceInteger = Integer.valueOf(priceString);
//								
//								System.out.println("priceInteger="+priceInteger);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//						
//						String available = element.attr("available");
//						
//						if (available!=null && available.length()>0) {
//							try {
//								Integer availableInteger = Integer.valueOf(available);
//								
//								System.out.println("availableInteger="+availableInteger);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
				
				houseService.saveAirbnbHouseInfo(houseInfoEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
			failUrlRecordsEntity.setCreateDate(new Date());
			failUrlRecordsEntity.setFailReason(e.toString());
			failUrlRecordsEntity.setUrl(url);
			failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.XiaozhuHouse.getCode());
			try {
				failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
	private static List<XiaozhuNewHousePriceEntity> parseHousePrice(String priceUrl){
		List<XiaozhuNewHousePriceEntity> priceEntities = new ArrayList<XiaozhuNewHousePriceEntity>();
		if (priceUrl==null) {
			return priceEntities;
		}
		LogUtil.info(LOGGER, "获取房源日历，priceUrl={}",priceUrl);
		String houseSn=null;
		Pattern pattern=Pattern.compile("(^|\\?|&)lodgeunitid=\\d*(&|$)");  
        Matcher matcher = pattern.matcher(priceUrl);  
        if (matcher.find()){
        	houseSn = matcher.group(0);
        	houseSn=houseSn.replaceAll("&", "");
        	houseSn=houseSn.replaceAll("lodgeunitid=", "");
        	houseSn=houseSn.trim();
        }
        if (houseSn==null||houseSn.length()==0) {
        	return priceEntities;
		}
        
        try {
			Thread.sleep(HttpUtil.getRandTime(10000,20000));
		} catch (InterruptedException e) {
			e.printStackTrace();
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
			} 		
			
			Connection connection = Jsoup.connect(priceUrl).header("Accept", "application/json, text/javascript, */*;")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Connection", "keep-alive")
					.header("Cache-Control", "max-age=0")
					.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
					.header("Host", HttpUtil.getHost(url))
					.header("Referer", url)
					.header("X-Requested-With", "XMLHttpRequest")
					.header("xSRF-Token", xz_srf_token);
			
			Map<String, String> cookies = HttpUtil.getXiaozhuCookies();
			if (cookies!=null && cookies.size()>0) {
				connection.cookies(cookies);
			}
			
			String userAgent=HttpUtil.getUserAgent();
			if (userAgent!=null) {
				connection.userAgent(userAgent);
			}
			Response res = connection.timeout(10000).execute();
			if(res.statusCode()==200){
				LogUtil.info(LOGGER, "获取房源日历成功，priceUrl={}",priceUrl);
            	HttpUtil.updateXiaozhuCookies(res.cookies());
            	doc = res.parse();
            }
			
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		if (doc!=null) {
			Elements elements= doc.getElementsByTag("body");
			if (elements!=null && elements.size()==1) {
				String jsonString =elements.text().trim();
				if (jsonString!=null&&jsonString.length()>0) {
					JSONArray jsonArray = JSON.parseArray(jsonString);
					if (jsonArray!=null && jsonArray.size()>0) {
						
						 for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject object = jsonArray.getJSONObject(i);
							if (object!=null) {
								try {
									XiaozhuNewHousePriceEntity priceEntity = new XiaozhuNewHousePriceEntity();
									priceEntity.setHouseSn(houseSn);
									if (object.containsKey("houseprice")) {
										Integer houseprice=object.getInteger("houseprice");
										
//										System.out.println("houseprice="+houseprice);
										priceEntity.setHousePrice(houseprice);
									}
									if (object.containsKey("state")) {
										String state=object.getString("state");
										
//										System.out.println("state="+state);
										priceEntity.setAvailable(state);
									}
									if (object.containsKey("pricetype")) {
										String pricetype=object.getString("pricetype");
										
//										System.out.println("pricetype="+pricetype);
										priceEntity.setPriceType(pricetype);
									}
									if (object.containsKey("start")) {
										Date date = object.getDate("start");
										
//										System.out.println("date="+date);
										priceEntity.setDate(date);
									}
									priceEntities.add(priceEntity);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}
							
						}
					}
				}
			}
			
		}
		return priceEntities;
	}
	
	
	

}
