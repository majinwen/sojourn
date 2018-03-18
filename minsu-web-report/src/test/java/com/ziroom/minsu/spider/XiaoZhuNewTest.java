/**
 * @FileName: XiaoZhuNewTest.java
 * @Package com.ziroom.minsu.spider
 * 
 * @author zl
 * @created 2016年10月20日 下午7:24:27
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;
import com.ziroom.minsu.spider.xiaozhunew.processor.XiaozhuProcessor;
import com.ziroom.minsu.spider.xiaozhunew.processor.tasks.XiaozhuHostTasker;
import com.ziroom.minsu.spider.xiaozhunew.processor.tasks.XiaozhuHouseTasker;

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
public class XiaoZhuNewTest extends BaseTest {
	
	@Autowired
	private FailUrlRecordsService failUrlService;

	@Test
	public void spiderAll() {
		 
		Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
		Set<String> cityAbbreviations = new HashSet<String>();
		cityAbbreviations.add("bj");
//		cityAbbreviations.add("sh");
//		cityAbbreviations.add("gz");
//		cityAbbreviations.add("cd");
		
//		cityAbbreviations.add("xa");
//		cityAbbreviations.add("sz");
//		cityAbbreviations.add("su");
//		cityAbbreviations.add("qd");
//		cityAbbreviations.add("lijiang");
//		cityAbbreviations.add("dali");
//		cityAbbreviations.add("hz");
//		cityAbbreviations.add("sanya");
//		cityAbbreviations.add("xm");
		for (String string : cityAbbreviations) {
			XiaozhuProcessor.pearseList(string,failUrlRecords);
			
		}
		for (FailUrlRecordsEntity failUrl: failUrlRecords) {
			failUrlService.saveFailUrlRecords(failUrl);
		}
	}
	
	@Test
	public void spiderHouse() {
		String urlString="http://cd.xiaozhu.com/fangzi/2836591963.html";
		XiaozhuHouseTasker houseTasker = new XiaozhuHouseTasker(urlString,"042e9c0636e84f6f9683c139a599b734",null);
		houseTasker.run();
		
	}
	
	@Test
	public void spiderHost() {
		String urlString="http://www.xiaozhu.com/fangdong/1694082135/";
		XiaozhuHostTasker tasker = new XiaozhuHostTasker(urlString,"042e9c0636e84f6f9683c139a599b734");
		tasker.run();
		
	}
	
	
}
