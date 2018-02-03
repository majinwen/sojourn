/**
 * @FileName: HouseBusniessServiceProxy.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2016年7月7日 上午10:30:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.house.dto.HouseBusinessDto;
import com.ziroom.minsu.services.house.proxy.HouseBusinessServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseBusniessServiceProxyTest extends BaseTest{
	
	@Resource(name="house.houseBusinessServiceProxy")
	private HouseBusinessServiceProxy houseBusinessServiceProxy;
	
	@Test
	public void houseBusinessListTest(){
		HouseBusinessDto houseBusinessDto=new HouseBusinessDto();
		String resultJson=houseBusinessServiceProxy.houseBusinessList(JsonEntityTransform.Object2Json(houseBusinessDto));
		System.err.println(resultJson);
	}
	
	
	@Test
	public void findHouseBusinessDetailByFidTest(){
		String resultJson=houseBusinessServiceProxy.findHouseBusinessDetailByFid("8a9e9a9a55c9f74b0155ca026e20000c");
		System.err.println(resultJson);
	}
	
	@Test
	public void findHouseCountByUidTest(){
		String resultJson=houseBusinessServiceProxy.findHouseCountByUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
//		System.err.println(resultJson);
	}
}
