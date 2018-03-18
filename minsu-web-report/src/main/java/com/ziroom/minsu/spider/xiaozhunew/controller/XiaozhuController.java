/**
 * @FileName: XiaozhuController.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.controller
 * 
 * @author zl
 * @created 2016年10月20日 下午1:55:18
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.controller;

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

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.spider.commons.PropertiesUtils;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;
import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHouseInfoEntityWithBLOBs;
import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHousePriceEntity;
import com.ziroom.minsu.spider.xiaozhunew.processor.XiaozhuProcessor;
import com.ziroom.minsu.spider.xiaozhunew.processor.tasks.XiaozhuHostTasker;
import com.ziroom.minsu.spider.xiaozhunew.processor.tasks.XiaozhuHouseTasker;
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
@Controller 
@RequestMapping("/xiaozhu/spider")
public class XiaozhuController {

	@Autowired
	private FailUrlRecordsService failUrlService;
	@Autowired
	private XiaozhuHouseInfoEntityService houseService;
	
	private static Properties property = PropertiesUtils.getProperties("classpath:application.properties"); 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XiaozhuController.class);
	
	private static final String SPIDER_THREAD_NAME_XIAOZHU_LIST_DEFAULT="SPIDER_THREAD_NAME_XIAOZHU_LIST_DEFAULT--";
	
	private static final String SPIDER_THREAD_NAME_XIAOZHU_LIST_URL="SPIDER_THREAD_NAME_XIAOZHU_LIST_URL--";
	
	private static final String SPIDER_THREAD_NAME_XIAOZHU_LIST_CITY="SPIDER_THREAD_NAME_XIAOZHU_LIST_CITY--";
	
	/**
	 * 
	 *	小猪 爬虫首页
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
		
		
		 return "spider/xiaozhu/xiaozhuSpider";
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
				if (SPIDER_THREAD_NAME_XIAOZHU_LIST_DEFAULT.equalsIgnoreCase(th.getName()) && th.isAlive()) {
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
							LogUtil.info(LOGGER, "XIAOZHU_spiderAllByDefault started");
							Date beginDate=new Date();
							String defaultCity = property.getProperty("xiaozhuSpiderDefaultCity");
							if (!Check.NuNStr(defaultCity)) {
								String[] cityStrings= defaultCity.split("\\|");
								if (!Check.NuNObject(cityStrings)) {
									Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
									for (String city:cityStrings) {
										if(Thread.interrupted()) break;
										XiaozhuProcessor.pearseList(city,failUrlRecords);
									}
									for (FailUrlRecordsEntity failUrl: failUrlRecords) {
										failUrlService.saveFailUrlRecords(failUrl);
									} 
								}
								
							} 
							LogUtil.info(LOGGER, "XIAOZHU_spiderAllByDefault finished,cost="+(new Date().getTime()-beginDate.getTime()));
							
						} catch (Exception e) {
							LogUtil.warn(LOGGER, "XIAOZHU_spiderAllByDefault exception,e={}",e);
						}
					}
				});
				thread.setName(SPIDER_THREAD_NAME_XIAOZHU_LIST_DEFAULT);
				thread.start();
			} catch (Exception e) {
				LogUtil.error(LOGGER, "XIAOZHU_spiderAllByDefault exception,e={}",e);
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
				if (SPIDER_THREAD_NAME_XIAOZHU_LIST_DEFAULT.equalsIgnoreCase(th.getName()) && th.isAlive()&& th.isInterrupted()) {
					try {
						if (th.getState().equals(Thread.State.BLOCKED)) {
							th.interrupt(); 
						}
						th.interrupt(); 
					} catch (Exception e) {
						LogUtil.error(LOGGER, "XIAOZHU_spiderAllByDefaultStop exception,e={}",e);
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
		
		LogUtil.info(LOGGER, "XIAOZHU_spiderAllByCityStart city={}",city);
		
		Pattern p = Pattern.compile("^[a-z]{1,10}$",Pattern.CASE_INSENSITIVE);
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
					if ((SPIDER_THREAD_NAME_XIAOZHU_LIST_CITY+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()) {
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
					
					LogUtil.info(LOGGER, "XIAOZHU_spiderAllByCity start,thread name ={}",(SPIDER_THREAD_NAME_XIAOZHU_LIST_CITY+sessionId));
					Date beginDate=new Date();
					
					 Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
					 XiaozhuProcessor.pearseList(city,failUrlRecords);
						
					 for (FailUrlRecordsEntity failUrl: failUrlRecords) {
							failUrlService.saveFailUrlRecords(failUrl);
						}
					 
					 LogUtil.info(LOGGER, "XIAOZHU_spiderAllByCity finished,thread name ={},cost={}ms",(SPIDER_THREAD_NAME_XIAOZHU_LIST_CITY+sessionId),(new Date().getTime()-beginDate.getTime()));
				}
			}); 
			
			thread.setName(SPIDER_THREAD_NAME_XIAOZHU_LIST_CITY+sessionId);			
			thread.start();
			
			
		 } catch (Exception e) {
			LogUtil.error(LOGGER, "XIAOZHU_spiderAllByCity Exception e={}",e);
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
				if ((SPIDER_THREAD_NAME_XIAOZHU_LIST_CITY+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()&& th.isInterrupted()) {
					try {
						if (th.getState().equals(Thread.State.BLOCKED)) {
							th.interrupt(); 
						}
						th.interrupt(); 
					} catch (Exception e) {
						LogUtil.error(LOGGER, "XIAOZHU_spiderAllByCityStop exception,e={}",e);
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
		
		final String urlString = request.getParameter("listUrl");
		
		LogUtil.info(LOGGER, "XIAOZHU_spiderListByUrl url={}",urlString);
		
		Pattern p = Pattern.compile("^(http://)[a-z]{1,10}(.xiaozhu.com/)",Pattern.CASE_INSENSITIVE);
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
					if ((SPIDER_THREAD_NAME_XIAOZHU_LIST_URL+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()) {
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
					
					LogUtil.info(LOGGER, "XIAOZHU_spiderListByUrl start,thread name ={}",(SPIDER_THREAD_NAME_XIAOZHU_LIST_URL+sessionId));
					Date beginDate=new Date();
					
					 Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
					 XiaozhuProcessor.pearseListByListUrl(urlString,failUrlRecords);
						
					 for (FailUrlRecordsEntity failUrl: failUrlRecords) {
							failUrlService.saveFailUrlRecords(failUrl);
						}
					 
					 LogUtil.info(LOGGER, "spiderListByUrl finished,thread name ={},cost={}ms",(SPIDER_THREAD_NAME_XIAOZHU_LIST_URL+sessionId),(new Date().getTime()-beginDate.getTime()));
				}
			}); 
			
			thread.setName(SPIDER_THREAD_NAME_XIAOZHU_LIST_URL+sessionId);			
			thread.start();
			
			
		 } catch (Exception e) {
			LogUtil.error(LOGGER, "XIAOZHU_spiderListByUrl Exception e={}",e);
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
				if ((SPIDER_THREAD_NAME_XIAOZHU_LIST_URL+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()&& th.isInterrupted()) {
					try {
						if (th.getState().equals(Thread.State.BLOCKED)) {
							th.interrupt(); 
						}
						th.interrupt(); 
					} catch (Exception e) {
						LogUtil.error(LOGGER, "XIAOZHU_spiderListByUrlStop exception,e={}",e);
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
		
		LogUtil.info(LOGGER, "XIAOZHU_spiderHouseByUrl url={}",urlString);
		
		Pattern p = Pattern.compile("^(http://)[a-z]{1,10}(.xiaozhu.com/fangzi/)[0-9]{1,20}(.html)$",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(urlString);
		if (!matcher.find()) {
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("参数错误！");
			return obj;
		}
		try {
			
			XiaozhuHouseTasker houseTasker = new XiaozhuHouseTasker(urlString,null,null);
			houseTasker.run();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "XIAOZHU_spiderHouseByUrl Exception,e={}",e);
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
		
		LogUtil.info(LOGGER, "XIAOZHU_spiderHostByUrl url={}",urlString);
		
		Pattern p = Pattern.compile("^(http://)(www.xiaozhu.com/fangdong/)[0-9]{1,20}",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(urlString);
		if (!matcher.find()) {
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("参数错误！");
			return obj;
		}
		try {
			
			XiaozhuHostTasker tasker = new XiaozhuHostTasker(urlString,null);
			tasker.run();
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "XIAOZHU_spiderHouseByUrl Exception,e={}",e);
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
	@ResponseBody
	public DataTransferObject spiderHouseByUrlApi(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject obj = new DataTransferObject();
		
		String urlString = request.getParameter("url");
		
		LogUtil.info(LOGGER, "XIAOZHU_spiderHouseByUrlApi url={}",urlString);
		
		Pattern p = Pattern.compile("^(http://)[a-z]{1,10}(.xiaozhu.com/fangzi/)[0-9]{1,20}(.html)$",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(urlString);
		if (!matcher.find()) {
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("参数错误！");
			return obj;
		}
		try {
			
			XiaozhuHouseTasker houseTasker = new XiaozhuHouseTasker(urlString,null,null);
			houseTasker.run();
			
			String houseSn = urlString.substring(urlString.lastIndexOf("/")+1, urlString.lastIndexOf("."));
			
			XiaozhuNewHouseInfoEntityWithBLOBs houseInfo= houseService.selectByHouseSn(houseSn) ;
			List<XiaozhuNewHousePriceEntity> priceList= houseService.findByHouseSn(houseSn);
			obj.putValue("houseInfo", houseInfo);
			obj.putValue("priceList", priceList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "XIAOZHU_spiderHouseByUrl Exception,e={}",e);
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("房源数据爬取异常，请稍后再试！");
			return obj;
		}
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("房源数据爬取成功");
		
		return obj;
	}
	
	
	
	
}
