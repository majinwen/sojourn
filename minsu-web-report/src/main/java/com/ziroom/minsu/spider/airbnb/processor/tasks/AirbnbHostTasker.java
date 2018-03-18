package com.ziroom.minsu.spider.airbnb.processor.tasks;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHostInfoEntity;
import com.ziroom.minsu.spider.airbnb.service.AirbnbHostInfoEntityService;
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
public class AirbnbHostTasker extends BaseTasker implements Runnable {

	private String url=null;
	
	private AirbnbHostInfoEntityService airbnbHostInfoService;
	
	private FailUrlRecordsService failUrlService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AirbnbHostTasker.class);
	
	public AirbnbHostTasker(String url) {
		super();
		airbnbHostInfoService = super.getBeanByClass(AirbnbHostInfoEntityService.class);
		failUrlService = super.getBeanByClass(FailUrlRecordsService.class);
		this.url = url;
	}




	@Override
	public void run() {
		try {

			LogUtil.info(LOGGER, "开始爬取房东，url={}",url);
			if (url==null) {
				return;
			}
			if (url.indexOf("?")>-1) {
				url=url.substring(0, url.indexOf("?"));
			}
			AirbnbHostInfoEntity host = new AirbnbHostInfoEntity();
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
					failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHost.getCode());
					failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
					return;
				}
				
				Connection connection = Jsoup.connect(url)
						.header("Accept-Encoding", "gzip, deflate")
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
					LogUtil.info(LOGGER, "获取房东成功，url={}",url);
	            	HttpUtil.updateCookies(res.cookies());
	            	doc = res.parse();
	            }else {
	            	LogUtil.info(LOGGER, "获取房东失败，url={},res={}",url,res.toString());
	            	FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
					failUrlRecordsEntity.setCreateDate(new Date());
					failUrlRecordsEntity.setFailReason("请求失败："+res.toString() );
					failUrlRecordsEntity.setUrl(url);
					failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHost.getCode());
					failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
					return;
				}
				 
			} catch (IOException e) {
				FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
				failUrlRecordsEntity.setCreateDate(new Date());
				failUrlRecordsEntity.setFailReason(e.toString());
				failUrlRecordsEntity.setUrl(url);
				failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHost.getCode());
				failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
				LogUtil.error(LOGGER, "获取房东失败，url={},e={}",url,e);
				return;
			}
			if (doc!=null) {
				String hostSn=url.substring(url.lastIndexOf("/")+1);
				host.setHostSn(hostSn);
				host.setDetailUrl(url);
				Elements firstNameElements = Selector.select("div.row>*:eq(1)>div.space-4>div.col-sm-8>h1", doc.getElementsByClass("page-container"));
				if(firstNameElements!=null && firstNameElements.size()==1){
					String firstName=firstNameElements.get(0).text().trim();
					if (firstName!=null && firstName.length()>0) {
						firstName=firstName.replace("您好, 我是", "");
						firstName=firstName.replace("Hey, I’m", "");
						firstName=firstName.replace("！", "");
						firstName=firstName.replace("!", "");
					}
//					System.out.println("firstName---"+firstName);
					host.setFirstName(firstName);
				}
				
				Elements hostimgElements = Selector.select("div>ul>li:eq(0)>img", doc.getElementById("slideshow"));
				
				if(hostimgElements!=null && hostimgElements.size()==1){
					String imgsrc=hostimgElements.attr("src");
//					System.out.println("imgsrc---"+imgsrc);
					host.setHostImg(imgsrc);
				}
				
				Elements cityElements = Selector.select("div.row>*:eq(1)>div.space-4>div.col-sm-8>div>a", doc.getElementsByClass("page-container"));
				if(cityElements!=null && cityElements.size()==1){
					String city=cityElements.get(0).text().trim();
//					System.out.println("city---"+city);
					host.setCity(city);
				}
				
				Elements superHostElements = Selector.select("div.row>*:eq(1)>div[data-hypernova-key]>div *:matchesOwn(超赞房东|Superhost)", doc.getElementsByClass("page-container"));
				if(superHostElements!=null && superHostElements.size()>0){
//					System.out.println("superHost---"+true);
					host.setSuperHost(1);
				}
				
				Elements reviewsCountElements = Selector.select("div.row>*:eq(1)>div[data-hypernova-key]>div span.badge-pill-count", doc.getElementsByClass("page-container"));
				if(reviewsCountElements!=null && reviewsCountElements.size()==1){
					String reviewsCount=reviewsCountElements.get(0).text().trim();
//					System.out.println("reviewsCount---"+reviewsCount);
					if (reviewsCount!=null && reviewsCount.length()>0) {
						host.setReviewsCount(Integer.valueOf(reviewsCount));
					}
				}
				
				Elements authElements = Selector.select("div.row>*:eq(1)>div[data-hypernova-key]>div *:matchesOwn(已验证|Verified)", doc.getElementsByClass("page-container"));
				if(authElements!=null && authElements.size()>0){
//					System.out.println("auth---"+true);
					host.setAuth(1);
				}
				
			}
			
			if (host!=null && host.getHostSn()!=null) {
				host.setCreateDate(new Date());
				airbnbHostInfoService.saveAirbnbHostInfo(host);
				failUrlService.deleteByUrl(url);
			} 
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "爬取房东失败，url={},e={}",url,e);
			FailUrlRecordsEntity failUrlRecordsEntity = new FailUrlRecordsEntity();
			failUrlRecordsEntity.setCreateDate(new Date());
			failUrlRecordsEntity.setFailReason(e.toString());
			failUrlRecordsEntity.setUrl(url);
			failUrlRecordsEntity.setUrlType(FailUrlRecordTypeEnum.AirbnbHost.getCode());
			try {
				failUrlService.saveFailUrlRecords(failUrlRecordsEntity);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

}
