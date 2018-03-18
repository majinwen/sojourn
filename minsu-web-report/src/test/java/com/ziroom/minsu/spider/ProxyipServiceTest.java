package com.ziroom.minsu.spider;

import javax.annotation.Resource;

import org.junit.Test;

import base.BaseTest;

import com.ziroom.minsu.report.proxyip.entity.enums.ProxyipSiteEnum;
import com.ziroom.minsu.report.proxyip.service.ProxyipService;

public class ProxyipServiceTest extends BaseTest {
	
	@Resource(name="report.proxyipService")
    private ProxyipService proxyipService;
	
	@Test
	public void startSpiderTasker() {
		proxyipService.startSpiderTasker(ProxyipSiteEnum.KUAI_DAILI);
	}
	
}
