/**
 * @FileName: HouseManageServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2016年4月3日 下午1:10:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.service;

import com.ziroom.minsu.services.house.service.HouseIssueServicePcImpl;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>房东端房源管理实现测试类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseIssuePcServiceImplTest extends BaseTest{
	
	@Resource(name="house.HouseIssueServicePcImpl")
	private HouseIssueServicePcImpl houseIssueServicePcImpl;
	
	@Test
	public void testfindHouseRoomWithBedsList(){
		//HouseRoomsWithBedsPcDto findHouseRoomWithBedsList = houseIssueServicePcImpl.findHouseRoomWithBedsList("8a9e9aad568ca29d01568ca29dbb0001");
		System.out.println();
	}
	
	@Test
	public void testdelFRoomByFid(){
		int delFRoomByFid = houseIssueServicePcImpl.delFRoomByFid("8a9e989e5ce994b5015cf3c204a6005a");
		System.out.println(delFRoomByFid);
	}
}
