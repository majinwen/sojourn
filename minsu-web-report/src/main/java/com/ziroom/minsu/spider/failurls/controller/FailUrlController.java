/**
 * @FileName: FailUrlController.java
 * @Package com.ziroom.minsu.spider.failurls.controller
 * 
 * @author zl
 * @created 2016年10月24日 下午5:34:53
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.failurls.controller;

import java.util.Date;
import java.util.Map;

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
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.spider.failurls.dto.FailUrlRequestDto;
import com.ziroom.minsu.spider.failurls.entity.enums.FailUrlRecordTypeEnum;
import com.ziroom.minsu.spider.failurls.processor.tasks.FailedUrlTasker;
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
@RequestMapping("/failurl")
public class FailUrlController {

	@Autowired
	private FailUrlRecordsService failUrlService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FailUrlController.class);
	
	private static final String SPIDER_THREAD_NAME_FAILURL="SPIDER_THREAD_NAME_FAILURL--";
	
	
	/**
	 * 
	 *
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
		
		Map<Integer,String> failUrlType = FailUrlRecordTypeEnum.getSelectList();
		request.setAttribute("failUrlType", failUrlType);
		 return "spider/failurls/failurl";
	}
	
	
	/**
	 * 
	 *
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月14日 上午9:57:43
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(FailUrlRequestDto requestDto){
		PageResult pageResult = new PageResult();
		try {
			if (requestDto.getTypes()!=null&&requestDto.getTypes().size()>0) {
				requestDto.setUrlTypes(requestDto.getTypes().toArray(new Integer[]{}));
			}
			pageResult = failUrlService.selectRecordsByPage(requestDto);
			
		} catch (Exception e) {
			return pageResult;
		}
		
		return pageResult;
	}
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月28日 下午10:04:44
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/trySelectIDs")
	@ResponseBody
	public DataTransferObject trySelectIDs(HttpServletRequest request,final FailUrlRequestDto requestDto){
		
		DataTransferObject obj = new DataTransferObject();
		
		boolean started=false;
		LogUtil.info(LOGGER, "开始按照id尝试失败url");
		
		final String sessionId= request.getSession().getId();		
		//判断此线程是否已经启动
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		if (!Check.NuNObject(threads)) {
			for (Thread th:threads) {
				if ((SPIDER_THREAD_NAME_FAILURL+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()) {
					started=true;
				}
			}
		}
		
		if (started) {
			LogUtil.info(LOGGER, "您尝试的id还在努力爬取中");
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("您尝试的id还在努力爬取中，请耐心等待！");
			return obj;
		}else {
				
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					LogUtil.info(LOGGER, "按照id尝试 start,thread name ={}",(SPIDER_THREAD_NAME_FAILURL+sessionId));
					Date beginDate=new Date();
					FailedUrlTasker failedUrlTasker = new FailedUrlTasker(requestDto.getIds());
					failedUrlTasker.run();
					  
					 LogUtil.info(LOGGER, "按照id尝试 finished,thread name ={},cost={}ms",(SPIDER_THREAD_NAME_FAILURL+sessionId),(new Date().getTime()-beginDate.getTime()));
				}
			}); 
			
			thread.setName(SPIDER_THREAD_NAME_FAILURL+sessionId);			
			thread.start();
		}
		
		
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("按照id尝试爬取开始，请耐心等待！");
		return obj;
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月28日 下午10:04:48
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/tryTypes")
	@ResponseBody
	public DataTransferObject tryTypes(HttpServletRequest request,final FailUrlRequestDto requestDto){
		DataTransferObject obj = new DataTransferObject();
		
		boolean started=false;
		LogUtil.info(LOGGER, "开始按照url类型尝试url");
		
		final String sessionId= request.getSession().getId();		
		//判断此线程是否已经启动
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		if (!Check.NuNObject(threads)) {
			for (Thread th:threads) {
				if ((SPIDER_THREAD_NAME_FAILURL+"URL-TYPE-"+sessionId).equalsIgnoreCase(th.getName()) && th.isAlive()) {
					started=true;
				}
			}
		}
		
		if (started) {
			LogUtil.info(LOGGER, "您按照url类型尝试的url还在努力爬取中");
			obj.setErrCode(DataTransferObject.ERROR);
			obj.setMsg("您按照url类型尝试的url还在努力爬取中，请耐心等待！");
			return obj;
		}else {
				
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					LogUtil.info(LOGGER, "按照url类型尝试的url start,thread name ={}",(SPIDER_THREAD_NAME_FAILURL+"URL-TYPE-"+sessionId));
					Date beginDate=new Date();
					FailedUrlTasker failedUrlTasker = new FailedUrlTasker(requestDto.getTypes().toArray(new Integer[]{}));
					failedUrlTasker.run();
					  
					 LogUtil.info(LOGGER, "按照url类型尝试的url finished,thread name ={},cost={}ms",(SPIDER_THREAD_NAME_FAILURL+"URL-TYPE-"+sessionId),(new Date().getTime()-beginDate.getTime()));
				}
			}); 
			
			thread.setName(SPIDER_THREAD_NAME_FAILURL+"URL-TYPE-"+sessionId);			
			thread.start();
		}
		
		obj.setErrCode(DataTransferObject.SUCCESS);
		obj.setMsg("按照url类型尝试的url开始，请耐心等待！");
		return obj;
	}
	
	/**
	 * 
	 * TODO
	 *
	 * @author zl
	 * @created 2016年10月28日 下午10:04:54
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/tryAll")
	@ResponseBody
	public DataTransferObject tryAll(HttpServletRequest request,HttpServletResponse response){
		
		DataTransferObject obj = new DataTransferObject();
		
		return obj;
	}
	
	
}
