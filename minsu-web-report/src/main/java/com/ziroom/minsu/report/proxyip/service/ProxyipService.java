package com.ziroom.minsu.report.proxyip.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.utils.ProxyUtils;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;
import com.ziroom.minsu.report.proxyip.dao.NetProxyIpPortDao;
import com.ziroom.minsu.report.proxyip.entity.enums.ProxyipSiteEnum;
import com.ziroom.minsu.report.proxyip.processor.PageProcessorFactory;
import com.ziroom.minsu.report.proxyip.processor.SimpleHttpClientDownloader;

/**
 * 
 * <p>代理ipService</p>
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
@Service("report.proxyipService")
public class ProxyipService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProxyipService.class);
	
	@Resource(name="report.netProxyIpPortDao")
	private NetProxyIpPortDao netProxyIpPortDao;
	
	@Resource(name="report.proxyipPipeline")
    private Pipeline pipeline;
	
	private static final String PROXYIP_SPIDER_THREAD_NAME = "PROXYIP_SPIDER_THREAD_NAME-";
	
	private List<Proxy> proxysList = new ArrayList<Proxy>();
	
	private SimpleHttpClientDownloader simpleHttpClientDownloader = new SimpleHttpClientDownloader();
	
	/**
	 * 
	 * 爬虫线程防御性容错
	 *
	 * @author zhangyl
	 * @created 2017年7月10日 下午7:21:29
	 *
	 */
	public void runAsyncSpider() {

		boolean started = false;
		Thread[] threads = new Thread[Thread.activeCount()];
		Thread.enumerate(threads);
		if (!Check.NuNObject(threads)) {
			for (Thread th : threads) {
				if (th.getName().startsWith(PROXYIP_SPIDER_THREAD_NAME) && th.isAlive()) {
					started = true;
					break;
				}
			}
		}

		if (started) {
			LogUtil.info(LOGGER, "ProxyipService.runAsyncSpider代理ip爬虫已经启动或尚未结束!请勿重复调用！");
		} else {
			try {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						LogUtil.info(LOGGER, "ProxyipService.runAsyncSpider代理ip爬虫线程启动中！");
						
						// 设置库里可用的ip做代理池
						// 获取有效代理ip地址列表
						List<NetProxyIpPortEntity> ipList = netProxyIpPortDao.listNetProxyIp();
						proxysList = new ArrayList<Proxy>();
						
						if (!Check.NuNCollection(ipList)) {
							for (NetProxyIpPortEntity ip : ipList) {
								Proxy proxy = new Proxy(ip.getProxyIp(), ip.getProxyPort(), "", "");
								if (ProxyUtils.validateProxy(proxy)) {
									proxysList.add(proxy);
								}
							}
						}
						
						// 配置WebMagic代理
						simpleHttpClientDownloader = new SimpleHttpClientDownloader();
						if (!Check.NuNCollection(proxysList)) {
							simpleHttpClientDownloader.setProxyProvider(new SimpleProxyProvider(Collections.unmodifiableList(proxysList)));
						}
						
						// 开始串行爬取任务
						startSpiderTasker(ProxyipSiteEnum.KUAI_DAILI);
						startSpiderTasker(ProxyipSiteEnum.IP_181_DAILI);
						startSpiderTasker(ProxyipSiteEnum.XICI_DAILI);
					}
				});
				
				LogUtil.info(LOGGER, "ProxyipService.runAsyncSpider代理ip爬虫线程启动前！");
				thread.setName(PROXYIP_SPIDER_THREAD_NAME);
				thread.start();
			} catch (Exception e) {
				LogUtil.error(LOGGER, "ProxyipService.runAsyncSpider代理ip爬虫线程启动异常！ e={}", e);
			}
		}
	}
	
	/**
	 * 
	 * 开始爬取任务
	 *
	 * @author zhangyl
	 * @created 2017年7月5日 下午5:13:30
	 *
	 * @param proxyipSiteEnum
	 */
	public void startSpiderTasker(ProxyipSiteEnum proxyipSiteEnum) {
		
		String siteName = null;
		String url = null;
		PageProcessor pageProcessor = null;
		
		try {
			siteName = proxyipSiteEnum.getSiteName();
			url = proxyipSiteEnum.getUrl();
			pageProcessor = PageProcessorFactory.createPageProcessor(proxyipSiteEnum);
			
			LogUtil.info(LOGGER, "ProxyipService.startSpiderTasker开始爬取：网站={} url={} 代理数量={} 代理={}", siteName, url, proxysList.size(), proxysList);
			
		    // Spider开始爬取
			Spider.create(pageProcessor)
				.addUrl(url)
				.addPipeline(pipeline)
				.setDownloader(simpleHttpClientDownloader)
				.thread(1)
				.run();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "ProxyipService.startSpiderTasker发生异常：网站={} url={} pageProcessor={} exception,e={}", siteName, url, pageProcessor, e);
			return;
		}
	}
	
}
