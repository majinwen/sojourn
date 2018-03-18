package com.ziroom.minsu.report.proxyip.processor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.utils.ProxyUtils;

import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NetProxyIpPortEntity;
import com.ziroom.minsu.report.proxyip.dao.NetProxyIpPortDao;

/**
 * 
 * <p>
 * 代理ip抽取结束后处理
 * </p>
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
@Service("report.proxyipPipeline")
public class ProxyipPipeline implements Pipeline {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProxyipPipeline.class);

	@Resource(name = "report.netProxyIpPortDao")
	private NetProxyIpPortDao netProxyIpPortDao;

	/**
	 * 处理页面抽取结果
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {

		List<NetProxyIpPortEntity> netIps = resultItems.get("netIps");

		LogUtil.info(LOGGER, "Pipeline开始处理，网站={}，当前页面={}，ip数量={}", resultItems.get("siteName"), resultItems.get("url"), netIps.size());

		try {
			for (NetProxyIpPortEntity netIp : netIps) {
				
				if (ProxyUtils.validateProxy(new Proxy(netIp.getProxyIp(), netIp.getProxyPort()))) {
					
					netIp.setFid(UUIDGenerator.hexUUID());
					
					netProxyIpPortDao.saveNetProxyIp(netIp);
					
				} else {
					LogUtil.info(LOGGER, "Pipeline IP无效！ 网站={}，ip={}", resultItems.get("siteName"), netIp);
				}
			}
			LogUtil.info(LOGGER, "Pipeline处理完成，网站={}，当前页面={}，ip数量={}", resultItems.get("siteName"), resultItems.get("url"), netIps.size());
		} catch (Exception e) {
			LogUtil.info(LOGGER, "Pipeline process异常！ 网站={}，当前页面={}，ip数量={}，e={}", resultItems.get("siteName"), resultItems.get("url"), netIps.size(), e);
		}

	}


	public static void main(String[] args) {
		List<Boolean> list = new ArrayList<Boolean>();
		
		Proxy proxy = new Proxy("113.140.25.4", 81);
		
		list.add(ProxyUtils.validateProxy(proxy));
		
		proxy = new Proxy("43.240.138.31", 8080);

		list.add(ProxyUtils.validateProxy(proxy));
		
		proxy = new Proxy("114.239.149.230", 808);

		list.add(ProxyUtils.validateProxy(proxy));
		
		proxy = new Proxy("115.203.64.9", 808);

		list.add(ProxyUtils.validateProxy(proxy));

		System.out.println(list);
	}

}
