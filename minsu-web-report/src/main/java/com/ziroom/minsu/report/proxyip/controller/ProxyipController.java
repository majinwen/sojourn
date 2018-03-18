package com.ziroom.minsu.report.proxyip.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.proxyip.service.ProxyipService;

/**
 * 
 * <p>爬取代理ip接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/proxyip")
public class ProxyipController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProxyipController.class);
	
	@Resource(name="report.proxyipService")
    private ProxyipService proxyipService;
	
	/**
	 * 
	 * 爬取代理ip
	 *
	 * @author zhangyl
	 * @created 2017年7月5日 下午5:23:13
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/spider")
	public void kuaidaili(HttpServletRequest request, HttpServletResponse response) {
		// 开始爬取任务
		LogUtil.info(LOGGER, "ProxyipService爬取代理ip接口响应成功！");
		proxyipService.runAsyncSpider();
	}
	
}
