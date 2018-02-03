/**
 * @FileName: HouseTopServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2017年3月17日 下午4:40:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.house.dto.HouseTopDto;
import com.ziroom.minsu.services.house.proxy.HouseTopServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>TODO</p>
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
public class HouseTopServiceProxyTest extends BaseTest{
	
	@Resource(name="house.houseTopServiceProxy")
	private HouseTopServiceProxy houseTopServiceProxy;
	
	@Test
	public void topHouseListDataTest(){
		HouseTopDto houseTopDto=new HouseTopDto();
		String resultJson=houseTopServiceProxy.topHouseListData(JsonEntityTransform.Object2Json(houseTopDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void houseTopDetailTest(){
		String resultJson=houseTopServiceProxy.houseTopDetail("55ffbf8ef5526bf5fb66ea74bbb40a2d");
		System.err.println(resultJson);
	}
	@Test
	public void testHouseTopOnlineDownLine(){
		String s = houseTopServiceProxy.houseTopOnlineOrDownline("562908bc7c616032931e856cea15cd79");
		System.err.println(s);
	}
}
