/**
 * @FileName: AirbnbController.java
 * @Package com.ziroom.minsu.spider.airbnb.controller
 * 
 * @author zl
 * @created 2016年9月29日 下午1:32:52
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.spider.airbnb.dto.AirbnbListRequest;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHouseInfoEntityWithBLOBs;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHousePriceEntity;
import com.ziroom.minsu.spider.airbnb.processor.AirbnbProcessor;
import com.ziroom.minsu.spider.airbnb.processor.tasks.AirbnbHostTasker;
import com.ziroom.minsu.spider.airbnb.processor.tasks.AirbnbHouseInfoTasker;
import com.ziroom.minsu.spider.airbnb.service.AirbnbHouseInfoEntityService;
import com.ziroom.minsu.spider.commons.PropertiesUtils;
import com.ziroom.minsu.spider.commons.SpiderEntityParser;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;

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
@Controller 
@RequestMapping("/airbnb/spider")
public class AirbnbController { 
	
	@Autowired
	private FailUrlRecordsService failUrlService;
	
	@Autowired
	private AirbnbHouseInfoEntityService houseInfoService;
	
	private static Properties property = PropertiesUtils.getProperties("classpath:application.properties"); 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AirbnbController.class);
	
	private static final String SPIDER_THREAD_NAME_AIRBNB_LIST_DEFAULT="SPIDER_THREAD_NAME_AIRBNB_LIST_DEFAULT--";
	
	private static final String SPIDER_THREAD_NAME_AIRBNB_LIST_URL="SPIDER_THREAD_NAME_AIRBNB_LIST_URL--";
	
	private static final String SPIDER_THREAD_NAME_AIRBNB_LIST_CITY="SPIDER_THREAD_NAME_AIRBNB_LIST_CITY--";
	
	/**
	 * 
	 *	airbnb 爬虫首页
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午9:57:43
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request,HttpServletResponse response){
		
		
		 return "spider/airbnb/airbnbSpider";
	}
	
	/**
	 * 按照默认配置城市爬取
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午9:58:46
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/spiderAllByDefaultStart")
	@ResponseBody
	public DataTransferObject spiderAllByDefaultStart(HttpServletRequest request,HttpServletResponse response){
		
		DataTransferObject obj = new DataTransferObject();
		
		boolean started=false;
		LogUtil.info(LOGGER, "开始启动默认配置城市爬虫");
		//判断此线程是否已经启动
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		if (!Check.NuNObject(threads)) {
			for (Thread th:threads) {
				if (SPIDER_THREAD_NAME_AIRBNB_LIST_DEFAULT.equalsIgnoreCase(th.getName()) && th.isAlive()) {
					started=true;
				}
			}
		}
		
		if (started) {
			LogUtil.info(LOGGER, "默认配置城市爬虫已经启动");
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("默认爬虫已经启动，请耐心等待！");
			return obj;
		}else {
			
			try {				
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						
						try {
							LogUtil.info(LOGGER, "spiderAllByDefault started");
							Date beginDate=new Date();
							String airbnbSpiderDefaultCity = property.getProperty("airbnbSpiderDefaultCity");
							if (!Check.NuNStr(airbnbSpiderDefaultCity)) {
								String[] cityStrings= airbnbSpiderDefaultCity.split("\\|");
								if (!Check.NuNObject(cityStrings)) {
									Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
									for (String city:cityStrings) {
										if(Thread.interrupted()) break;
										AirbnbListRequest airbnbListRequest = new AirbnbListRequest(); 
										AirbnbProcessor.parseHouseList(city,airbnbListRequest,failUrlRecords);
									}
									for (FailUrlRecordsEntity failUrl: failUrlRecords) {
										failUrlService.saveFailUrlRecords(failUrl);
									} 
								}
								
							} 
							LogUtil.info(LOGGER, "spiderAllByDefault finished,cost="+(new Date().getTime()-beginDate.getTime()));
							
						} catch (Exception e) {
							LogUtil.warn(LOGGER, "spiderAllByDefault exception,e={}",e);
						}
					}
				});
				thread.setName(SPIDER_THREAD_NAME_AIRBNB_LIST_DEFAULT);
				thread.start();
			} catch (Exception e) {
				LogUtil.error(LOGGER, "spiderAllByDefault exception,e={}",e);
				obj.setErrCode(DataTransferObject.ERROR);
				obj.setMsg("默认爬虫启动失败，请稍后再试！");
				return obj;
			}
		}
		LogUtil.info(LOGGER, "默认配置城市爬虫启动成功");
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("默认爬虫启动成功！");
		return obj;
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午10:27:12
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/spiderAllByDefaultStop")
	@ResponseBody
	public DataTransferObject spiderAllByDefaultStop(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("默认爬虫已经停止！");
		//判断此线程是否已经启动
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		if (!Check.NuNObject(threads)) {
			for (Thread th:threads) {
				if (SPIDER_THREAD_NAME_AIRBNB_LIST_DEFAULT.equalsIgnoreCase(th.getName()) && th.isAlive()&& th.isInterrupted()) {
					try {
						if (th.getState().equals(Thread.State.BLOCKED)) {
							th.interrupt(); 
						}
						th.interrupt(); 
					} catch (Exception e) {
						LogUtil.error(LOGGER, "spiderAllByDefaultStop exception,e={}",e);
						obj.setErrCode(DataTransferObject.ERROR);
						obj.setMsg("默认爬虫停止失败，请稍后再试！");
					}
				}
			}
		}
		return obj;
	}
	
	
	/**
	 * 按照城市爬取
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午10:07:25
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/spiderAllByCityStart")
	@ResponseBody
	public DataTransferObject spiderAllByCityStart(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		
		final String city = request.getParameter("city");
		
		LogUtil.info(LOGGER, "spiderAllByCityStart city={}",city);
		
		Pattern p = Pattern.compile("[A-Za-z]{1,50}(--)[A-Za-z]{1,50}$",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(city);
		if (!matcher.find()) {
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("参数错误！");
			return obj;
		}
		
		try {
			
			final String sessionId= request.getSession().getId();
			boolean started = false;
			//判断此线程是否已经启动
			Thread[] threads = new Thread[Thread.activeCount()];
			Thread.enumerate(threads);
			if (!Check.NuNObject(threads)) {
				for (Thread th:threads) {
					if ((SPIDER_THREAD_NAME_AIRBNB_LIST_CITY+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()) {
						started=true;
					}
				}
			}
			
			if (started) {//已经启动
				obj.setErrCode(DataTransferObject.ERROR);
				obj.setMsg("您启动的城市爬虫还在努力爬取中，请耐心等待！");
				return obj;
			}
			
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					LogUtil.info(LOGGER, "spiderAllByCity start,thread name ={}",(SPIDER_THREAD_NAME_AIRBNB_LIST_CITY+sessionId));
					Date beginDate=new Date();
					
					 Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
					 AirbnbListRequest airbnbListRequest = new AirbnbListRequest(); 
					 AirbnbProcessor.parseHouseList(city, airbnbListRequest, failUrlRecords);
					 for (FailUrlRecordsEntity failUrl: failUrlRecords) {
							failUrlService.saveFailUrlRecords(failUrl);
						}
					 
					 LogUtil.info(LOGGER, "spiderAllByCity finished,thread name ={},cost={}ms",(SPIDER_THREAD_NAME_AIRBNB_LIST_CITY+sessionId),(new Date().getTime()-beginDate.getTime()));
				}
			}); 
			
			thread.setName(SPIDER_THREAD_NAME_AIRBNB_LIST_CITY+sessionId);			
			thread.start();
			
			
		 } catch (Exception e) {
			LogUtil.error(LOGGER, "spiderAllByCity Exception e={}",e);
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("您启动的城市爬虫启动失败，请稍后再试！");
			return obj;
		 }
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("您启动的城市爬虫已经开始爬取数据，请耐心等待！");
		return obj;
	}
	
	/**
	 * 
	 * TODO
	 * 按照城市爬取停止
	 * @author zl
	 * @created 2016年10月14日 上午10:18:21
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/spiderAllByCityStop")
	@ResponseBody
	public DataTransferObject spiderAllByCityStop(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("您启动的城市爬虫已经停止！");
		//判断此线程是否已经启动
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		String sessionId= request.getSession().getId();
		if (!Check.NuNObject(threads)) {
			for (Thread th:threads) {
				if ((SPIDER_THREAD_NAME_AIRBNB_LIST_CITY+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()&& th.isInterrupted()) {
					try {
						if (th.getState().equals(Thread.State.BLOCKED)) {
							th.interrupt(); 
						}
						th.interrupt(); 
					} catch (Exception e) {
						LogUtil.error(LOGGER, "spiderAllByCityStop exception,e={}",e);
						obj.setErrCode(DataTransferObject.ERROR);
						obj.setMsg("默认爬虫停止失败，请稍后再试！");
					}
				}
			}
		}
		
		return obj;
	}
	 
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午10:30:27
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/spiderListByUrlStart")
	@ResponseBody
	public DataTransferObject spiderListByUrlStart(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		
		final String urlString = request.getParameter("airbnbListUrl");
		
		LogUtil.info(LOGGER, "spiderListByUrl url={}",urlString);
		
		Pattern p = Pattern.compile("^(https://)[a-z]{1,10}(.airbnb.com/s/)[A-Za-z]{1,50}(--)[A-Za-z]{1,50}[?#]",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(urlString);
		if (!matcher.find()) {
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("参数错误！");
			return obj;
		}
		
		try {
			
			final String sessionId= request.getSession().getId();
			boolean started = false;
			//判断此线程是否已经启动
			Thread[] threads = new Thread[Thread.activeCount()];
			Thread.enumerate(threads);
			if (!Check.NuNObject(threads)) {
				for (Thread th:threads) {
					if ((SPIDER_THREAD_NAME_AIRBNB_LIST_URL+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()) {
						started=true;
					}
				}
			}
			
			if (started) {//已经启动
				obj.setErrCode(DataTransferObject.ERROR);
				obj.setMsg("您启动的房源列表爬虫还在努力爬取中，请耐心等待！");
				return obj;
			}
			
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					LogUtil.info(LOGGER, "spiderListByUrl start,thread name ={}",(SPIDER_THREAD_NAME_AIRBNB_LIST_URL+sessionId));
					Date beginDate=new Date();
					
					 Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
					 AirbnbProcessor.parseHouseListByListUrl(urlString, failUrlRecords);
					 for (FailUrlRecordsEntity failUrl: failUrlRecords) {
							failUrlService.saveFailUrlRecords(failUrl);
						}
					 
					 LogUtil.info(LOGGER, "spiderListByUrl finished,thread name ={},cost={}ms",(SPIDER_THREAD_NAME_AIRBNB_LIST_URL+sessionId),(new Date().getTime()-beginDate.getTime()));
				}
			}); 
			
			thread.setName(SPIDER_THREAD_NAME_AIRBNB_LIST_URL+sessionId);			
			thread.start();
			
			
		 } catch (Exception e) {
			LogUtil.error(LOGGER, "spiderListByUrl Exception e={}",e);
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("您启动的房源列表爬虫启动失败，请稍后再试！");
			return obj;
		 }
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("您启动的房源列表爬虫已经开始爬取数据，请耐心等待！");
		return obj;
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午10:37:08
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/spiderListByUrlStop")
	@ResponseBody
	public DataTransferObject spiderListByUrlStop(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("您启动的房源列表爬虫已经停止！");
		//判断此线程是否已经启动
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		String sessionId= request.getSession().getId();
		if (!Check.NuNObject(threads)) {
			for (Thread th:threads) {
				if ((SPIDER_THREAD_NAME_AIRBNB_LIST_URL+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()&& th.isInterrupted()) {
					try {
						if (th.getState().equals(Thread.State.BLOCKED)) {
							th.interrupt(); 
						}
						th.interrupt(); 
					} catch (Exception e) {
						LogUtil.error(LOGGER, "spiderListByUrlStop exception,e={}",e);
						obj.setErrCode(DataTransferObject.ERROR);
						obj.setMsg("默认爬虫停止失败，请稍后再试！");
					}
				}
			}
		}
		
		return obj;
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午10:41:54
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/spiderHouseByUrl")
	@ResponseBody
	public DataTransferObject spiderHouseByUrl(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		
		String urlString = request.getParameter("url");
		
		LogUtil.info(LOGGER, "spiderHouseByUrl url={}",urlString);
		
		Pattern p = Pattern.compile("^(https://)[a-z]{1,10}(.airbnb.com/rooms/)[0-9]{1,20}$",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(urlString);
		if (!matcher.find()) {
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("参数错误！");
			return obj;
		}
		try {
			
			AirbnbHouseInfoTasker houseInfoTasker = new AirbnbHouseInfoTasker(urlString, null);
			houseInfoTasker.run();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "spiderHouseByUrl Exception,e={}",e);
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("房源数据爬取异常，请稍后再试！");
			return obj;
		}
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("房源数据爬取成功");
		
		return obj;
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午10:44:39
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/spiderHostByUrl")
	@ResponseBody
	public DataTransferObject spiderHostByUrl(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		
		String urlString = request.getParameter("url");
		
		LogUtil.info(LOGGER, "spiderHostByUrl url={}",urlString);
		
		Pattern p = Pattern.compile("^(https://)[a-z]{1,10}(.airbnb.com/users/show/)[0-9]{1,20}$",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(urlString);
		if (!matcher.find()) {
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("参数错误！");
			return obj;
		}
		try {
			
			AirbnbHostTasker hostTasker= new AirbnbHostTasker(urlString);
			hostTasker.run();
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "spiderHouseByUrl Exception,e={}",e);
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("房东数据爬取异常，请稍后再试！");
			return obj;
		}
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("房东数据爬取成功");
		
		return obj;
	}
	
	
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月25日 下午9:16:05
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/spiderHouseByUrlApi")
	public void spiderHouseByUrlApi(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		
		String urlString = request.getParameter("url");
		
		LogUtil.info(LOGGER, "spiderHouseByUrl url={}",urlString);
		
		Pattern p = Pattern.compile("^(https://)[a-z]{1,10}(.airbnb.com/rooms/)[0-9]{1,20}$",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(urlString);
		if (!matcher.find()) {
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("参数错误！");
		}
		try {
			
			AirbnbHouseInfoTasker houseInfoTasker = new AirbnbHouseInfoTasker(urlString, null);
			houseInfoTasker.run();
			
			if (urlString.indexOf("?")>-1) {
				urlString=urlString.substring(0, urlString.indexOf("?"));
			}
			String houseSn=urlString.substring(urlString.lastIndexOf("/")+1);
			
			AirbnbHouseInfoEntityWithBLOBs houseInfo = houseInfoService.selectAirbnbHouseByHouseSn(houseSn);
			List<AirbnbHousePriceEntity> priceList=  houseInfoService.selectAirbnbPriceByHouseSn( houseSn);
			obj.putValue("houseInfo", SpiderEntityParser.airbnbHouseToMinsuHouse(houseInfo));
			obj.putValue("priceList", SpiderEntityParser.airbnbPriceToMinsuPrice(priceList));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "spiderHouseByUrl Exception,e={}",e);
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("房源数据爬取异常，请稍后再试！");
		}
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("房源数据爬取成功");
		
		response.setContentType("text/plain");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        
        try {
			PrintWriter out = response.getWriter();
			String jsonpCallback = request.getParameter("callbackparam");
			
			String resultJSON = JSON.toJSONString(obj);
			if (jsonpCallback!=null && jsonpCallback.length()>0) {
				out.write(jsonpCallback + "("+resultJSON+")");
			}
			
		     out.flush();  
		     out.close();  
			
		} catch (IOException e) {
			e.printStackTrace();
		}     
	}
	
	

}
