package com.ziroom.minsu.spider.airbnb.processor.tasks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.time.DateUtils;
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
import com.ziroom.minsu.spider.airbnb.entity.AirbnbAdditionalHostsEntity;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHouseInfoEntityWithBLOBs;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHousePriceEntity;
import com.ziroom.minsu.spider.airbnb.entity.enums.RoomPropertyTypeEnum;
import com.ziroom.minsu.spider.airbnb.entity.enums.RoomTypeEnum;
import com.ziroom.minsu.spider.airbnb.service.AirbnbHouseInfoEntityService;
import com.ziroom.minsu.spider.commons.BaseTasker;
import com.ziroom.minsu.spider.commons.HttpUtil;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.entity.enums.FailUrlRecordTypeEnum;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;
/**
 * 
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
public class AirbnbHouseInfoTasker extends BaseTasker implements Runnable  {

	private String url=null;
	
	private String street=null;
	
	private AirbnbHouseInfoEntityService houseInfoService;
	
	private FailUrlRecordsService failUrlService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AirbnbHouseInfoTasker.class);
	
	private static final String baseUrl="https://zh.airbnb.com";
	
	public AirbnbHouseInfoTasker(String url,String street) {
		super();
		houseInfoService = super.getBeanByClass(AirbnbHouseInfoEntityService.class);
		failUrlService = super.getBeanByClass(FailUrlRecordsService.class);
		this.url=url;
		this.street=street;
	}

	@Override
	public void run() {
		try {

			LogUtil.info(LOGGER, "开始爬取房源，url={}",url);
			if (url==null) {
				return;
			}
			if (url.indexOf("?")>-1) {
				url=url.substring(0, url.indexOf("?"));
			}
			AirbnbHouseInfoEntityWithBLOBs houseInfo= new AirbnbHouseInfoEntityWithBLOBs();
			 
			Document doc = null;
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
					failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHouse.getCode());
					failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
					LogUtil.info(LOGGER, "爬取房源获取代理ip失败，url={}",url);
					return;
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
					LogUtil.info(LOGGER, "获取房源成功，url={}",url);
	            	HttpUtil.updateCookies(res.cookies());
	            	doc = res.parse();
	            }else {
	            	LogUtil.info(LOGGER, "获取房源失败，url={},res={}",url,res.toString());
	            	FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
					failUrlRecordsEntity.setCreateDate(new Date());
					failUrlRecordsEntity.setFailReason("请求失败："+res.toString() );
					failUrlRecordsEntity.setUrl(url);
					failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHouse.getCode());
					failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
					return;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
				failUrlRecordsEntity.setCreateDate(new Date());
				failUrlRecordsEntity.setFailReason(e.toString());
				failUrlRecordsEntity.setUrl(url);
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHouse.getCode());
				failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
				LogUtil.info(LOGGER, "获取房源失败，url={},e={}",url,e);
				return;
			}
			if (doc!=null) {
				
				String houseSn=url.substring(url.lastIndexOf("/")+1);
//				System.out.println("houseSn---"+houseSn);
				houseInfo.setHouseSn(houseSn);	
				houseInfo.setDetailUrl(url);
				houseInfo.setCreateDate(new Date());
				String hostSn =null; 
				
				Element houseDetailElement = doc.getElementById("_bootstrap-listing");
				if(houseDetailElement!=null){
					String houseDetailJson=houseDetailElement.attr("content").trim();
//					System.out.println("houseDetailJson---"+houseDetailJson);
					houseInfo.setHouseJson(houseDetailJson);
					if (houseDetailJson!=null && houseDetailJson.length()>0) {
						JSONObject houseDetailObject = JSONObject.parseObject(houseDetailJson);
						if (houseDetailObject!=null) { 
							
							if (houseDetailObject.containsKey("full_address")) {							
								houseInfo.setFullAddress(houseDetailObject.getString("full_address").trim());
								
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("calendar_last_updated_at")) {
								String calendarLastUpdatedAtStr=houseDetailObject.getJSONObject("listing").getString("calendar_last_updated_at");
								if (calendarLastUpdatedAtStr!=null && calendarLastUpdatedAtStr.length()>0) {
									Date calendarLastUpdatedAt = null;
									try {
										calendarLastUpdatedAt = DateUtils.parseDate(calendarLastUpdatedAtStr, "yyyy-MM-dd");
									} catch (ParseException e) {
										e.printStackTrace();
									}
									houseInfo.setCalendarLastUpdatedAt( calendarLastUpdatedAt);
								}
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("cancellation_policy")) {
								houseInfo.setCancellationPolicy(houseDetailObject.getJSONObject("listing").getString("cancellation_policy").trim());
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("city")) {
								houseInfo.setCity(houseDetailObject.getJSONObject("listing").getString("city").trim());
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("description")) {
								houseInfo.setDescription(houseDetailObject.getJSONObject("listing").getString("description").trim());
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("description_locale")) {
								houseInfo.setDescriptionLocale(houseDetailObject.getJSONObject("listing").getString("description_locale").trim());
							}
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("house_rules")) {
								houseInfo.setHouseRules(houseDetailObject.getJSONObject("listing").getString("house_rules").trim());
							}
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("instant_bookable")) {
								Boolean bl=houseDetailObject.getJSONObject("listing").getBoolean("instant_bookable");
								if (bl) {
									houseInfo.setInstantBookable(1);
								}else {
									houseInfo.setInstantBookable(0);
								}
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("min_nights")) {
								houseInfo.setMinNights(houseDetailObject.getJSONObject("listing").getInteger("min_nights")); 
							}
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("name")) {
								houseInfo.setHouseName(houseDetailObject.getJSONObject("listing").getString("name").trim());
							}
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("person_capacity")) {
								houseInfo.setPersonCapacity(houseDetailObject.getJSONObject("listing").getInteger("person_capacity")); 
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("star_rating")) {
								houseInfo.setStarRating(houseDetailObject.getJSONObject("listing").getFloat("star_rating")); 
							}
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("summary")) {
								houseInfo.setSummary(houseDetailObject.getJSONObject("listing").getString("summary").trim());
							}
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("xl_picture_url")) {
								houseInfo.setHouseImg(houseDetailObject.getJSONObject("listing").getString("xl_picture_url").trim());
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("guest_controls")) {
								JSONObject guest_controls = null;
								try {
									 guest_controls = houseDetailObject.getJSONObject("listing").getJSONObject("guest_controls");
								} catch (Exception e) { 
									e.printStackTrace();
								}
								if (guest_controls!=null && guest_controls.containsKey("allows_children")) {
									Boolean bl=guest_controls.getBoolean("allows_children");
									if (bl) {
										houseInfo.setAllowsChildren(1);
									}else {
										houseInfo.setAllowsChildren(0);
									}
								}							
								if (guest_controls!=null && guest_controls.containsKey("allows_infants")) {
									Boolean bl=guest_controls.getBoolean("allows_infants");
									if (bl) {
										houseInfo.setAllowsInfants(1);
									}else {
										houseInfo.setAllowsInfants(0);
									}
								}
								if (guest_controls!=null && guest_controls.containsKey("allows_pets")) {
									Boolean bl=guest_controls.getBoolean("allows_pets");
									if (bl) {
										houseInfo.setAllowsPets(1);
									}else {
										houseInfo.setAllowsPets(0);
									}
								}
								if (guest_controls!=null && guest_controls.containsKey("allows_smoking")) {
									Boolean bl=guest_controls.getBoolean("allows_smoking");
									if (bl) {
										houseInfo.setAllowsSmoking(1);
									}else {
										houseInfo.setAllowsSmoking(0);
									}
								}
								if (guest_controls!=null && guest_controls.containsKey("allows_events")) {
									Boolean bl=guest_controls.getBoolean("allows_events");
									if (bl) {
										houseInfo.setAllowsEvents(1);
									}else {
										houseInfo.setAllowsEvents(0);
									}
								}
								
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("listing_amenities")) {
								JSONArray listing_amenities =null; 
								try {
									 listing_amenities = houseDetailObject.getJSONObject("listing").getJSONArray("listing_amenities");
								} catch (Exception e) {
									e.printStackTrace();
								}
								 if (listing_amenities!=null) {
									 StringBuilder idsString=new StringBuilder();
									 for (int i = 0; i < listing_amenities.size(); i++) {
										 JSONObject am = listing_amenities.getJSONObject(i);
										 if (am!=null && am.containsKey("id") && am.containsKey("name")&& am.containsKey("is_present")) {
											Boolean bl=am.getBoolean("is_present");
											if (bl) {
												if (i==0) {
													idsString.append(am.getString("id")).append(":").append(am.getString("name"));
												}else {
													idsString.append(",").append(am.getString("id")).append(":").append(am.getString("name"));
												}
											}
										 }
									 }
									 
									houseInfo.setListingAmenities(idsString.toString());	
								 }
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("price_interface")) {
								JSONObject price_interface =null;
								try {
									price_interface = houseDetailObject.getJSONObject("listing").getJSONObject("price_interface");
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (price_interface!=null && price_interface.toJSONString().indexOf("cleaning_fee")>-1) {
									String valString =price_interface.getJSONObject("cleaning_fee").getString("value");
									if (valString!=null && valString.length()>0 && valString.indexOf(";")>0) {									
										houseInfo.setCleaningFee(Integer.valueOf(valString.substring(valString.indexOf(";")+1)));
									}
								}
								if (price_interface!=null && price_interface.toJSONString().indexOf("security_deposit")>-1) {
									String valString =price_interface.getJSONObject("security_deposit").getString("value");
									if (valString!=null && valString.length()>0 && valString.indexOf(";")>0) {									
										houseInfo.setSecurityDeposit(Integer.valueOf(valString.substring(valString.indexOf(";")+1)));
									}
								}
								
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("review_details_interface")) {
								JSONObject review_details_interface = null;
								try {
									 review_details_interface = houseDetailObject.getJSONObject("listing").getJSONObject("review_details_interface");
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (review_details_interface!=null && review_details_interface.containsKey("review_count")) {
									houseInfo.setReviewCount(review_details_interface.getInteger("review_count"));
								}
								if (review_details_interface!=null && review_details_interface.containsKey("review_score")) {
									houseInfo.setReviewScore(review_details_interface.getInteger("review_score"));
								}
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("space_interface")) {
								JSONArray space_interface=null;
								try {
									 space_interface=houseDetailObject.getJSONObject("listing").getJSONArray("space_interface");
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (space_interface!=null && space_interface.size()>0) {
									for (int i = 0; i < space_interface.size(); i++) {
										JSONObject object=space_interface.getJSONObject(i);
										if (object!=null && object.containsKey("label")&& object.containsKey("value")) {
											String label = object.getString("label");
											if (label!=null && label.length()>0) {
												if (label.contains("卫生间")) {
													try {
														String valString = object.getString("value");
														if (valString!=null && valString.length()>0) {
															if (valString.indexOf("+")>=0) {
																valString=valString.replace("+", "");
															}
														}
														Float val=Float.valueOf(valString);
														val+=1;
														houseInfo.setToiletCount(val);
														
													} catch (Exception e) {
														e.printStackTrace();
													}
												}else if (label.contains("床型")) {
													houseInfo.setBedType(object.getString("value"));
												}else if (label.contains("床位")) {
													try {
														String valString = object.getString("value");
														if (valString!=null && valString.length()>0) {
															if (valString.indexOf("+")>=0) {
																valString=valString.replace("+", "");
															}
														}
														Integer val=Integer.valueOf(valString);
														val+=1;
														houseInfo.setBedCount(val);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}else if (label.contains("卧室")) {
													try {
														String valString = object.getString("value");
														if (valString!=null && valString.length()>0) {
															if (valString.indexOf("+")>=0) {
																valString=valString.replace("+", "");
															}
														}
														Integer val=Integer.valueOf(valString);
														val+=1;
														houseInfo.setBedroomCount(val);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}else if (label.contains("入住时间")) {
													houseInfo.setCheckInTime(object.getString("value"));
												}else if (label.contains("退房时间")) {
													houseInfo.setCheckOutTime(object.getString("value"));
												}else if (label.contains("房源类型")) {
													houseInfo.setHouseType(object.getString("value"));
													houseInfo.setHouseTypeValue(RoomPropertyTypeEnum.getCodeByName(object.getString("value")));
												}else if (label.contains("房间类型")) {
													houseInfo.setRoomType(object.getString("value"));
													houseInfo.setRoomTypeValue(RoomTypeEnum.getCodeByName(object.getString("value")));
												}
											}
											
										}
									}
								}
							}
							
							if (houseDetailObject.containsKey("listing") && houseDetailObject.getJSONObject("listing").containsKey("user")) {
								JSONObject user =null;
								try {
									 user = houseDetailObject.getJSONObject("listing").getJSONObject("user");
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (user!=null) {
									if (user.containsKey("host_name")) {
										houseInfo.setHostName(user.getString("host_name"));
									}
									if (user.containsKey("id")) {
										hostSn =user.getString("id");
										houseInfo.setHostSn(user.getString("id"));
									}
									if (user.containsKey("profile_pic_path")) {
										houseInfo.setHostImg(user.getString("profile_pic_path"));
									}
									if (user.containsKey("is_superhost")) {
										Boolean bl=user.getBoolean("is_superhost");
										if (bl) {
											houseInfo.setIsSuperhost(1);
										}else {
											houseInfo.setIsSuperhost(0);
										}
									}
									
									if (user.containsKey("profile_path")) {
										String hostDetailUrl =user.getString("profile_path");
										if (hostDetailUrl.indexOf("?")>-1) {
											hostDetailUrl=hostDetailUrl.substring(0, hostDetailUrl.indexOf("?"));
										}
										
										try {
											Thread.sleep(HttpUtil.getRandTime());
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										LogUtil.info(LOGGER, "开始爬取房东，url={}",baseUrl+hostDetailUrl);
										try {
											AirbnbHostTasker hostTasker= new AirbnbHostTasker(baseUrl+hostDetailUrl);
											hostTasker.run();
										} catch (Exception e) {
											FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
											failUrlRecordsEntity.setCreateDate(new Date());
											failUrlRecordsEntity.setFailReason(e.toString());
											failUrlRecordsEntity.setUrl(baseUrl+hostDetailUrl);
											failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHost.getCode());
											try {
												failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
											} catch (Exception e2) {
												e2.printStackTrace();
											}
											LogUtil.error(LOGGER, "爬取房东失败，url={},e={}",baseUrl+hostDetailUrl,e);
										}
										
										
									}
									
								}
							}
							
						}
						
					}
					
				} 
				
				if (doc.getElementsByAttributeValue("property", "airbedandbreakfast:region")!=null) {
					
					Elements regionElements = Selector.select("meta", doc.getElementsByAttributeValue("property", "airbedandbreakfast:region"));
					if(regionElements!=null && regionElements.size()==1){
						String houseRegion=regionElements.get(0).attr("content").trim();
//					System.out.println("houseRegion---"+houseRegion);
						houseInfo.setHouseregion(houseRegion);
					}
				}
				
				if (doc.getElementsByAttributeValue("property", "airbedandbreakfast:country")!=null) {
					
					Elements countryElements = Selector.select("meta", doc.getElementsByAttributeValue("property", "airbedandbreakfast:country"));
					if(countryElements!=null && countryElements.size()==1){
						String country=countryElements.get(0).attr("content").trim();
//					System.out.println("country---"+country);
						houseInfo.setCountry(country);
					}
				}
				
				if (doc.getElementsByAttributeValue("property", "airbedandbreakfast:location:latitude")!=null) {
					
					Elements latitudeElements = Selector.select("meta", doc.getElementsByAttributeValue("property", "airbedandbreakfast:location:latitude"));
					if(latitudeElements!=null && latitudeElements.size()==1){
						String latitude=latitudeElements.get(0).attr("content").trim();
//					System.out.println("latitude---"+latitude);
						if(latitude!=null && latitude.length()>0){
							houseInfo.setLatitude(Double.valueOf(latitude));
						}
					}
				}
				
				if (doc.getElementsByAttributeValue("property", "airbedandbreakfast:location:longitude")!=null) {
					
					Elements longitudeElements = Selector.select("meta", doc.getElementsByAttributeValue("property", "airbedandbreakfast:location:longitude"));
					if(longitudeElements!=null && longitudeElements.size()==1){
						String longitude=longitudeElements.get(0).attr("content").trim();
//					System.out.println("longitude---"+longitude);
						if (longitude!=null && longitude.length()>0) {
							houseInfo.setLongitude(Double.valueOf(longitude));
						}
					}
				}
				if (doc.getElementsByAttributeValue("itemprop", "price")!=null) {
					
					Elements priceElements = Selector.select("meta", doc.getElementsByAttributeValue("itemprop", "price"));
					if(priceElements!=null && priceElements.size()==1){
						String price=priceElements.get(0).attr("content").trim();
//					System.out.println("price---"+price);
						if (price!=null && price.length()>0) {
							houseInfo.setHousePrice(Integer.valueOf(price));
						}
					}
				}
				
				if (doc.getElementsByAttributeValue("itemprop", "priceCurrency")!=null) {
					
					Elements priceCurrencyElements = Selector.select("meta", doc.getElementsByAttributeValue("itemprop", "priceCurrency"));
					if(priceCurrencyElements!=null && priceCurrencyElements.size()==1){
						String priceCurrency=priceCurrencyElements.get(0).attr("content").trim();
//					System.out.println("priceCurrency---"+priceCurrency);
						houseInfo.setPriceCurrency(priceCurrency);
					}
				}
				
				if (doc.getElementsByClass("wish_list_button")!=null) {
					
					Elements houseElements = Selector.select("div[data-hosting_id]", doc.getElementsByClass("wish_list_button"));
					if(houseElements!=null && houseElements.size()==1){
						String houseName=houseElements.get(0).attr("data-name").trim();
						if (houseInfo.getHouseName()==null) {
							houseInfo.setHouseName(houseName);
						}
//					System.out.println("houseName---"+houseName);
						String address=houseElements.get(0).attr("data-address").trim();
						if (houseInfo.getFullAddress()==null) {
							houseInfo.setFullAddress(address);
						}
//					System.out.println("address---"+address);
						String reviewCount=houseElements.get(0).attr("data-review_count").trim();
						if (houseInfo.getReviewCount()==null) {
							try {
								if(reviewCount!=null && reviewCount.length()>0){
									houseInfo.setReviewCount(Integer.valueOf(reviewCount));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
//					System.out.println("reviewCount---"+reviewCount);
						String houseImg=houseElements.get(0).attr("data-img").trim();
						if (houseInfo.getHouseImg()==null) {
							houseInfo.setHouseImg(houseImg);
						}
						
//					System.out.println("houseImg---"+houseImg);
						String hostImg=houseElements.get(0).attr("data-host_img").trim();
//					System.out.println("hostImg---"+hostImg);
						if (houseInfo.getHostImg()==null) {
							houseInfo.setHostImg(hostImg);
						}
						String starRating=houseElements.get(0).attr("data-star_rating").trim();
						if (houseInfo.getStarRating()==null) {
							try {
								if (starRating!=null && starRating.length()>0) {
									houseInfo.setStarRating(Float.valueOf(starRating));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
//					System.out.println("starRating---"+starRating);
						String collectCount=houseElements.get(0).attr("data-count").trim(); 
						if (collectCount!=null && collectCount.length()>0) {
							houseInfo.setCollectCount(Integer.valueOf(collectCount));
						}
						
						
					}
				}
				
				if (doc.getElementsByAttributeValue("property", "airbedandbreakfast:city")!=null && houseInfo.getCity()==null) {
					
					Elements cityElements = Selector.select("meta", doc.getElementsByAttributeValue("property", "airbedandbreakfast:city"));
					if(cityElements!=null && cityElements.size()==1){
						String city=cityElements.get(0).attr("content").trim();
//					System.out.println("city---"+city);
						houseInfo.setCity(city);
					}
				}
				
				if (doc.getElementsByAttributeValue("property", "airbedandbreakfast:rating")!=null && houseInfo.getStarRating()==null) {
					
					Elements ratingElements = Selector.select("meta", doc.getElementsByAttributeValue("property", "airbedandbreakfast:rating"));
					if(ratingElements!=null && ratingElements.size()==1){
						String rating=ratingElements.get(0).attr("content").trim();
//					System.out.println("rating---"+rating);
						if(rating!=null && rating.length()>0){
							houseInfo.setStarRating(Float.valueOf(rating));
						} 
					}
				}
				
				if (doc.getElementsByClass("js-details-column")!=null) {
					
					Elements aboutHouseElements = Selector.select(".space-top-8>div>p>span", doc.getElementsByClass("js-details-column"));
					if(aboutHouseElements!=null && aboutHouseElements.size()==1){
						String aboutHouse=aboutHouseElements.get(0).text().trim();
						if (houseInfo.getDescription()==null) {
							houseInfo.setDescription(aboutHouse);
						}
//					System.out.println("aboutHouse---"+aboutHouse);
					} 
					
					Elements peopleCountElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(可住)>div:contains(可住)>strong", doc.getElementsByClass("js-details-column"));
					
					if(peopleCountElements!=null && peopleCountElements.size()==1){
						String peopleCount=peopleCountElements.get(0).text().trim();
//					System.out.println("peopleCount---"+peopleCount);
						if (houseInfo.getPersonCapacity()==null) {
							if (peopleCount!=null && peopleCount.length()>0) {
								try {
									houseInfo.setPersonCapacity(Integer.valueOf(peopleCount));								
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					
					Elements toiletCountElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(卫生间)>div:contains(卫生间)>strong", doc.getElementsByClass("js-details-column"));
					if(toiletCountElements!=null && toiletCountElements.size()==1){
						String toiletCount=toiletCountElements.get(0).text().trim();
//					System.out.println("toiletCount---"+toiletCount);
						if (houseInfo.getToiletCount()==null) {
							if (toiletCount!=null && toiletCount.length()>0) {
								try {
									houseInfo.setToiletCount(Float.valueOf(toiletCount));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				 
					
					Elements bedTypeElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(床型)>div:contains(床型)>strong", doc.getElementsByClass("js-details-column"));
					if(bedTypeElements!=null && bedTypeElements.size()==1){
						String bedType=bedTypeElements.get(0).text().trim();
						if (houseInfo.getBedType()==null) {
							houseInfo.setBedType(bedType);
						}
//					System.out.println("bedType---"+bedType);
					}
				
				Elements bedroomCountElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(卧室)>div:contains(卧室)>strong", doc.getElementsByClass("js-details-column"));
				if(bedroomCountElements!=null && bedroomCountElements.size()==1){
					String bedroomCount=bedroomCountElements.get(0).text().trim();
//					System.out.println("bedroomCount---"+bedroomCount);
					if (houseInfo.getBedroomCount()==null) {
						if (bedroomCount!=null && bedroomCount.length()>0) {
							try {
								houseInfo.setBedroomCount(Integer.valueOf(bedroomCount));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				Elements bedCountElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(床位)>div:contains(床位)>strong", doc.getElementsByClass("js-details-column"));
				if(bedCountElements!=null && bedCountElements.size()==1){
					String bedCount=bedCountElements.get(0).text().trim();
//					System.out.println("bedCount---"+bedCount);
					if (houseInfo.getBedCount()==null) {
						if (bedCount!=null && bedCount.length()>0) {
							try {
								houseInfo.setBedCount(Integer.valueOf(bedCount));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				Elements checkInTypeElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(入住时间)>div:contains(入住时间)>strong", doc.getElementsByClass("js-details-column"));
				if(checkInTypeElements!=null && checkInTypeElements.size()==1){
					String checkInType=checkInTypeElements.get(0).text().trim();
//					System.out.println("checkInType---"+checkInType);
					if (houseInfo.getCheckInTime()==null) {
						houseInfo.setCheckInTime(checkInType);
					}
				}
				
				Elements checkOutTimeElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(退房时间)>div:contains(退房时间)>strong", doc.getElementsByClass("js-details-column"));
				if(checkOutTimeElements!=null && checkOutTimeElements.size()==1){
					String checkOutTime=checkOutTimeElements.get(0).text().trim();
//					System.out.println("checkOutTime---"+checkOutTime);
					if (houseInfo.getCheckOutTime()==null) {
						houseInfo.setCheckOutTime(checkOutTime);
					}
				}
				
				Elements houseTypeElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(房源类型)>div:contains(房源类型)>a>strong", doc.getElementsByClass("js-details-column"));
				if(houseTypeElements!=null && houseTypeElements.size()==1){
					String houseType=houseTypeElements.get(0).text().trim();
//					System.out.println("houseType---"+houseType);
					if (houseInfo.getHouseType()==null) {
						
						houseInfo.setHouseType(houseType);
						houseInfo.setHouseTypeValue(RoomPropertyTypeEnum.getCodeByName(houseType));
					}
				}
				
				Elements roomTypeElements = Selector.select(".space-top-8>div:contains(房源)>div.col-md-9>div.row>div:contains(房间类型)>div:contains(房间类型)>strong", doc.getElementsByClass("js-details-column"));
				if(roomTypeElements!=null && roomTypeElements.size()==1){
					String roomType=roomTypeElements.get(0).text().trim();
//					System.out.println("roomType---"+roomType);
					if (houseInfo.getRoomType()==null) {
						houseInfo.setRoomType(roomType);
						houseInfo.setRoomTypeValue(RoomTypeEnum.getCodeByName(roomType));
					}
				}
			}
				
				if (doc.getElementById("host-profile")!=null) {
					
					Elements hostElements = Selector.select("div a.media-photo", doc.getElementById("host-profile"));
					if(hostElements!=null && hostElements.size()==1){
						if (houseInfo.getHostSn()==null) {
							hostSn=hostElements.attr("href").trim();
							if (hostSn!=null) {
								if (hostSn.indexOf("?")>-1) {
									hostSn=hostSn.substring(0, hostSn.indexOf("?"));
								}
								hostSn=hostSn.substring(hostSn.lastIndexOf("/")+1);
								houseInfo.setHostSn(hostSn);
							}
						}
//					System.out.println("hostSn---"+hostSn);
						Elements hostNameElements = Selector.select("img", hostElements);
						if(hostNameElements!=null && hostNameElements.size()==1){
							String hostName=hostNameElements.attr("title").trim();
//						System.out.println("hostName---"+hostName);
							if (houseInfo.getHostName()==null) {
								houseInfo.setHostName(hostName);
							}
							if (houseInfo.getHostImg()==null) {
								houseInfo.setHostImg(hostNameElements.attr("src").trim());
							}
						}
						
					}
				}
				
				
				try {
					if (doc.getElementsByAttributeValue("type", "application/json")!=null) {
						
						Elements additionHostElements = Selector.select("[data-hypernova-key=p3book_itbundlejs]", doc.getElementsByAttributeValue("type", "application/json"));
						if(additionHostElements!=null && additionHostElements.size()==1){
							String jsonString = additionHostElements.html();
							if (jsonString!=null && jsonString.length()>0) {
								if (jsonString.startsWith("<!--")) {
									jsonString=jsonString.replaceFirst("<!--", "");
								}
								if (jsonString.endsWith("-->")) {
									jsonString=jsonString.substring(0, jsonString.lastIndexOf("-->"));
								}
								if (jsonString!=null && jsonString.length()>0) {
									JSONObject additionHostObject = JSONObject.parseObject(jsonString);
									if (additionHostObject!=null && additionHostObject.containsKey("additional_hosts")) { 
										JSONArray additional_hosts= additionHostObject.getJSONArray("additional_hosts");
										if (additional_hosts!=null) {
											
											List<AirbnbAdditionalHostsEntity> additionalHostsEntities = new ArrayList<AirbnbAdditionalHostsEntity>();
											
											for (int i = 0; i < additional_hosts.size(); i++) {
												JSONObject additional_host= additional_hosts.getJSONObject(i).getJSONObject("user");
												if (additional_host!=null) {
													AirbnbAdditionalHostsEntity additionalHostsEntity = new AirbnbAdditionalHostsEntity();
													if (additional_host.containsKey("birthdate")) {
														try {
															String dateString = additional_host.getString("birthdate");
															if (dateString!=null && dateString.length()>0) {
																if (dateString.indexOf(" ")>0) {
																	dateString=dateString.substring(0, dateString.indexOf(" "));
																}
																Date date = DateUtils.parseDate(dateString, "yyyy-MM-dd");
																additionalHostsEntity.setBirthdate(date);
															}
														} catch (Exception e) {
															e.printStackTrace();
														}
														
													}
													if (additional_host.containsKey("country")) {
														additionalHostsEntity.setCountry(additional_host.getString("country"));
													}
													if (additional_host.containsKey("email")) {
														additionalHostsEntity.setEmail(additional_host.getString("email"));
													}
													if (additional_host.containsKey("facebook_id")) {
														additionalHostsEntity.setFacebookId(additional_host.getString("facebook_id"));
													}
													if (additional_host.containsKey("first_name")) {
														additionalHostsEntity.setFirstName(additional_host.getString("first_name"));
													}
													if (additional_host.containsKey("last_name")) {
														additionalHostsEntity.setLastName(additional_host.getString("last_name"));
													}
													if (additional_host.containsKey("id")) {
														additionalHostsEntity.setAdditionalHostSn(additional_host.getString("id"));
													}
													if (additional_host.containsKey("initial_ip")) {
														additionalHostsEntity.setInitialIp(additional_host.getString("initial_ip"));
													}
													if (additional_host.containsKey("languages")) {
														additionalHostsEntity.setLanguages(additional_host.getInteger("languages"));
													}
													if (additional_host.containsKey("native_currency")) {
														additionalHostsEntity.setNativeCurrency(additional_host.getString("native_currency"));
													}
													if (additional_host.containsKey("reviewee_count")) {
														additionalHostsEntity.setRevieweeCount(additional_host.getInteger("reviewee_count"));
													}
													if (additional_host.containsKey("reviewee_rating")) {
														additionalHostsEntity.setRevieweeRating(additional_host.getFloat("reviewee_rating"));
													}
													if (additional_host.containsKey("sex")) {
														additionalHostsEntity.setSex(additional_host.getString("sex"));
													}
													if (additional_host.containsKey("affiliate_referral_at")) {
														additionalHostsEntity.setAffiliateAt(additional_host.getDate("affiliate_referral_at"));
													}
													
													additionalHostsEntity.setHostSn(hostSn);
													additionalHostsEntity.setHouseSn(houseSn);
													additionalHostsEntity.setCreateDate(new Date());
													
													if (additionalHostsEntity.getAdditionalHostSn()!=null && additionalHostsEntity.getHouseSn()!=null && additionalHostsEntity.getHostSn()!=null) {
														additionalHostsEntities.add(additionalHostsEntity);
													}
													
												}
												
											}
											if (additionalHostsEntities.size()>0) {
												houseInfo.setAdditionalHostsEntities(additionalHostsEntities);
											}
											
										}
									}
								}
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String priceUrl="";
				try {
					
					Element initElement=doc.getElementById("_bootstrap-layout-init");
					if (initElement!=null) {
						JSONObject initElementObject=JSON.parseObject(initElement.attr("content"));
						if (initElementObject!=null && initElementObject.containsKey("api_config")) {
							JSONObject api_config=initElementObject.getJSONObject("api_config");
							if (api_config!=null && api_config.containsKey("baseUrl") && api_config.containsKey("key")) {
								
								Calendar calendar =Calendar.getInstance();
								priceUrl=api_config.getString("baseUrl")+"/v2/calendar_months?key="+api_config.getString("key")+"&locale="+initElementObject.getString("locale")
										+"&listing_id="+houseSn+"&month="+calendar.get(Calendar.MONTH)+"&year="+calendar.get(Calendar.YEAR)+"&count=12&_format=with_conditions";
								
//								System.err.println(priceUrl);
								
								houseInfo.setPriceEntities(parseHousePrice(priceUrl));
								
							}
						}
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
					failUrlRecordsEntity.setCreateDate(new Date());
					failUrlRecordsEntity.setFailReason(e.toString());
					failUrlRecordsEntity.setUrl(url);
					failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbPrice.getCode());
					LogUtil.error(LOGGER, "获取房源价格失败，priceUrl={},e={}",priceUrl,e);
					try {
						failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				
			}
			if(houseInfo!=null && houseInfo.getHouseSn()!=null&& houseInfo.getHouseName()!=null){
				houseInfo.setStreet(street);	
				houseInfoService.saveAirbnbHouseInfo(houseInfo);
				failUrlService.deleteByUrl(url);
				LogUtil.info(LOGGER, "保存房源成功，url={}",url);
			}else {
				LogUtil.info(LOGGER, "房源数据解析不完整，url={}",url);
				FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
				failUrlRecordsEntity.setCreateDate(new Date());
				failUrlRecordsEntity.setFailReason("数据结构不完整");
				failUrlRecordsEntity.setUrl(url);
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHouse.getCode());
				try {
					failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} 
		} catch (Exception e) {
			FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
			failUrlRecordsEntity.setCreateDate(new Date());
			failUrlRecordsEntity.setFailReason(e.toString());
			failUrlRecordsEntity.setUrl(url);
			failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHouse.getCode());
			LogUtil.error(LOGGER, "获取房源失败，url={},e={}",url,e);
			try {
				failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月12日 下午1:23:11
	 *
	 * @param priceUrl
	 */
	public static List<AirbnbHousePriceEntity> parseHousePrice(String priceUrl){
		if (priceUrl==null) {
			return null;
		}
		
		try {
			
			LogUtil.info(LOGGER, "获取房源日历，priceUrl={}",priceUrl);
			
			String houseSn=null;
			Pattern pattern=Pattern.compile("(^|\\?|&)listing_id=\\d*(&|$)");  
	        Matcher matcher = pattern.matcher(priceUrl);  
	        if (matcher.find()){
	        	houseSn = matcher.group(0);
	        	houseSn=houseSn.replaceAll("&", "");
	        	houseSn=houseSn.replaceAll("listing_id=", "");
	        	houseSn=houseSn.trim();
	        }
	        if (houseSn==null || houseSn.length()==0) {
				return null;
			}
			
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(priceUrl);
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 200000);
			client.executeMethod(method);
			String priceString = method.getResponseBodyAsString();
//			System.out.println(priceString);
			if (priceString!=null) {
				JSONObject priceObject = JSON.parseObject(priceString);
				if (priceObject!=null && priceObject.containsKey("calendar_months")) {
					JSONArray calendar_months = priceObject.getJSONArray("calendar_months");
					
					List<AirbnbHousePriceEntity> priceEntities=new ArrayList<AirbnbHousePriceEntity>();
					
					for (int i = 0; i < calendar_months.size(); i++) {
						JSONObject calendarMonthsObj=calendar_months.getJSONObject(i);
						if (calendarMonthsObj!=null &&calendarMonthsObj.containsKey("days") ) {
							JSONArray days = calendarMonthsObj.getJSONArray("days");
							for (int j = 0; j < days.size(); j++) {
								
								AirbnbHousePriceEntity priceEntity = new AirbnbHousePriceEntity();
								
								Boolean bl=days.getJSONObject(j).getBoolean("available");
								if (bl) {
									priceEntity.setAvailable(1);
								}else {
									priceEntity.setAvailable(0);
								} 
								
								JSONObject dayPriceObject=days.getJSONObject(j).getJSONObject("price");
								if (dayPriceObject.containsKey("date")) {
									priceEntity.setDate(dayPriceObject.getDate("date"));
								}
								if (dayPriceObject.containsKey("local_currency")) {
									priceEntity.setLocalCurrency(dayPriceObject.getString("local_currency"));
								}
								if (dayPriceObject.containsKey("native_currency")) {
									priceEntity.setNativeCurrency(dayPriceObject.getString("native_currency"));
								}
								if (dayPriceObject.containsKey("local_price")) {
									priceEntity.setLocalPrice(dayPriceObject.getInteger("local_price"));
								}
								if (dayPriceObject.containsKey("native_price")) {
									priceEntity.setNativePrice(dayPriceObject.getInteger("native_price"));
								}
								priceEntity.setCreateDate(new Date());
								priceEntity.setHouseSn(houseSn);
								if (priceEntity.getHouseSn()!=null&& priceEntity.getDate()!=null) {
									priceEntities.add(priceEntity);
								}
							}
							
						}
					} 
					
					return priceEntities;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
