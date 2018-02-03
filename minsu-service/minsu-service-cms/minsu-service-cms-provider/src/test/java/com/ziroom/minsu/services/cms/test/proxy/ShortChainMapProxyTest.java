package com.ziroom.minsu.services.cms.test.proxy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.cms.proxy.ShortChainMapProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

public class ShortChainMapProxyTest extends BaseTest{

	@Resource(name = "cms.shortChainMapProxy")
	private ShortChainMapProxy shortChainMapProxy;
	
	@Test
	public void generateShortLinkTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(shortChainMapProxy.generateShortLink("http://minsu.m.t.ziroom.com/common/ee5f86/goToApp?param=%7B%22jumpType%22%3A1%2C%22type%22%3A3%7D", "001"));
		System.err.println(dto);
	}
	
	@Test
	public void findShortChainMapByUniqueCodeTest(){
		String uniqueCode = "00000001";
		String resultJson = shortChainMapProxy.findShortChainMapByUniqueCode(uniqueCode);
		System.err.println(resultJson);
	}
	

	@Test
	public void getMinsuHomeJumpTest(){
		String resultJson = shortChainMapProxy.getMinsuHomeJump();
		System.err.println(resultJson);
	}
}