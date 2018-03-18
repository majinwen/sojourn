package com.ziroom.minsu.report.proxyip.processor.pageprocessor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;
import com.ziroom.minsu.report.proxyip.entity.enums.ProxyTypeEnum;
import com.ziroom.minsu.report.proxyip.entity.enums.ProxyipSiteEnum;
import com.ziroom.minsu.report.proxyip.processor.SimpleHttpClientDownloader;

/**
 * 
 * <p>Ip181代理爬虫逻辑</p>
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
public class Ip181dailiPageProcessor implements PageProcessor {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setCycleRetryTimes(3).setSleepTime(1000).setUseGzip(true).setTimeOut(10000).setRetrySleepTime(2000);
	
	private static final ProxyipSiteEnum proxyipSiteEnum = ProxyipSiteEnum.IP_181_DAILI;
	
    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
    	
    	LogUtil.info(LOGGER, "开始抽取页面，网站={}，当前页面={}", proxyipSiteEnum.getSiteName(), page.getUrl());
    	
        List<Selectable> trs = page.getHtml().xpath("//table[@class='table table-hover panel-default panel ctable']/tbody/tr").nodes();
        
        List<NetProxyIpPortEntity> netProxyIpPortEntitys = new ArrayList<NetProxyIpPortEntity>();
        NetProxyIpPortEntity netProxyIpPortEntity = null;
        
        // 去掉表头
        if(!Check.NuNCollection(trs)){
        	trs.remove(0);
        }
        // 遍历ip行数据
		for (Selectable tr : trs) {
			try {
				netProxyIpPortEntity = new NetProxyIpPortEntity();
				netProxyIpPortEntity.setIpSource(proxyipSiteEnum.getUrl());
				netProxyIpPortEntity.setIsValid(1);
				netProxyIpPortEntity.setValidUsedCount(0);
				netProxyIpPortEntity.setIsDel(0);

				List<Selectable> tds = tr.xpath("//td").nodes();

				// 获取ip属性
				if(!Check.NuNCollection(tds) && tds.size() == 7){
					// ip
					netProxyIpPortEntity.setProxyIp(tds.get(0).$("td", "text").get());
					// 端口
					netProxyIpPortEntity.setProxyPort(Integer.parseInt(tds.get(1).$("td", "text").get()));
					// 代理类型
					String proxyType = tds.get(3).$("td", "text").get();
					if (!Check.NuNStr(proxyType)) {
						if (proxyType.toUpperCase().indexOf(ProxyTypeEnum.HTTP.getType().toUpperCase()) > -1) {
							netProxyIpPortEntity.setProxyType(ProxyTypeEnum.HTTP.getCode());
						} else if (proxyType.toUpperCase().indexOf(ProxyTypeEnum.HTTPS.getType().toUpperCase()) > -1) {
							netProxyIpPortEntity.setProxyType(ProxyTypeEnum.HTTPS.getCode());
						} else {
							// 其他类型则忽略当前行
							continue;
						}
					}
				}

				if (!Check.NuNStr(netProxyIpPortEntity.getProxyIp())
						&& !Check.NuNObj(netProxyIpPortEntity.getProxyPort())
						&& !Check.NuNObj(netProxyIpPortEntity.getProxyType())) {
					netProxyIpPortEntitys.add(netProxyIpPortEntity);
				} else {
					LogUtil.error(LOGGER, "页面结构变化无法抓取，请及时修改抓取规则，网站={}，当前页面={}", proxyipSiteEnum.getSiteName(), page.getUrl());
					break;
				}
			} catch (Exception e) {
				LogUtil.info(LOGGER, "抽取页面失败，网站={}，当前页面={}，e={}", proxyipSiteEnum.getSiteName(), page.getUrl(), e);
				continue;
			}
		}
        
        if(Check.NuNCollection(netProxyIpPortEntitys)){
        	
        	LogUtil.info(LOGGER, "无数据跳过页面，网站={}，当前页面={}", proxyipSiteEnum.getSiteName(), page.getUrl());
        	
        	page.setSkip(true);
        }else{
        	
        	LogUtil.info(LOGGER, "抽取页面完成，网站={}，当前页面={}，ip数量={}", proxyipSiteEnum.getSiteName(), page.getUrl(), netProxyIpPortEntitys.size());
        	
        	page.putField("siteName", proxyipSiteEnum.getSiteName());
        	page.putField("url", page.getUrl());
        	page.putField("netIps", netProxyIpPortEntitys);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
    
    public static void main(String[] args) {
    	
    	// 获取有效代理ip地址列表, 排除从该网站抓的ip
    	
    	SimpleHttpClientDownloader simpleDownloader = new SimpleHttpClientDownloader();
    				
    	// Spider开始爬取
		Spider.create(new Ip181dailiPageProcessor())
			.addUrl(proxyipSiteEnum.getUrl())
			.setDownloader(simpleDownloader)
			.thread(1)
			.run();
	}
    
}
