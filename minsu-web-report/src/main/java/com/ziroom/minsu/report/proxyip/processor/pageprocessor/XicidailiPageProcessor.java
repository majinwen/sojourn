package com.ziroom.minsu.report.proxyip.processor.pageprocessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;
import com.ziroom.minsu.report.proxyip.entity.enums.ProxyTypeEnum;
import com.ziroom.minsu.report.proxyip.entity.enums.ProxyipSiteEnum;

/**
 * 
 * <p>西刺代理爬虫逻辑</p>
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
public class XicidailiPageProcessor implements PageProcessor {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setCycleRetryTimes(3).setSleepTime(1000).setUseGzip(true).setTimeOut(10000).setRetrySleepTime(2000);
	
	private static final ProxyipSiteEnum proxyipSiteEnum = ProxyipSiteEnum.XICI_DAILI;
	
    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
    	
    	LogUtil.info(LOGGER, "开始抽取页面，网站={}，当前页面={}", proxyipSiteEnum.getSiteName(), page.getUrl());
    	
        List<Selectable> trs = page.getHtml().xpath("//table[@id='ip_list']/tbody/tr").nodes();
        
        List<NetProxyIpPortEntity> netProxyIpPortEntitys = new ArrayList<NetProxyIpPortEntity>();
        NetProxyIpPortEntity netProxyIpPortEntity = null;
        
        // 是否继续抓取其他列表页
        boolean continueSpiderOther = true;
        
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
				if(!Check.NuNCollection(tds) && tds.size() == 10){
					// ip
					netProxyIpPortEntity.setProxyIp(tds.get(1).$("td", "text").get());
					// 端口
					netProxyIpPortEntity.setProxyPort(Integer.parseInt(tds.get(2).$("td", "text").get()));
					// 代理类型
					String proxyType = tds.get(5).$("td", "text").get();
					if (ProxyTypeEnum.HTTP.getType().equalsIgnoreCase(proxyType)) {
						netProxyIpPortEntity.setProxyType(ProxyTypeEnum.HTTP.getCode());
					} else if (ProxyTypeEnum.HTTPS.getType().equalsIgnoreCase(proxyType)) {
						netProxyIpPortEntity.setProxyType(ProxyTypeEnum.HTTPS.getCode());
					} else {
						// 其他类型则忽略当前行
						continue;
					}
					// 最后验证时间
					Date lastValidate = DateUtil.parseDate(tds.get(9).$("td", "text").get(), "yy-MM-dd HH:mm");
					if (DateUtil.getTime(-2).after(lastValidate)) {
						// 抓到2天以前的，直接舍弃当前页后续的行数据，并且不追加后续的页面了
						continueSpiderOther = false;
						break;
					}
				}

				if (!Check.NuNStr(netProxyIpPortEntity.getProxyIp())
						&& !Check.NuNObj(netProxyIpPortEntity.getProxyPort())
						&& !Check.NuNObj(netProxyIpPortEntity.getProxyType())) {
					netProxyIpPortEntitys.add(netProxyIpPortEntity);
				} else {
					LogUtil.error(LOGGER, "页面结构变化无法抓取，请及时修改抓取规则，网站={}，当前页面={}", proxyipSiteEnum.getSiteName(), page.getUrl());
					continueSpiderOther = false;
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
        
		try {
			int sleepTime = new Random().nextInt(2000) + 1000;
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (continueSpiderOther) {
			page.addTargetRequests(page.getHtml().xpath("//div[@class=\"pagination\"]").links().regex("http://www\\.xicidaili\\.com/nn/\\d+").all());
		}
    }

    @Override
    public Site getSite() {
        return site;
    }
    
//    public static void main(String[] args) {
//    	
//    	// 获取有效代理ip地址列表, 排除从该网站抓的ip
//    	HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//    	httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("122.112.230.18", 8080)));			
//    				
//    	// Spider开始爬取
//		Spider.create(new XicidailiPageProcessor())
//			.addUrl(proxyipSiteEnum.getUrl())
//			.setDownloader(httpClientDownloader)
//			.thread(1)
//			.run();
//	}
    
}
