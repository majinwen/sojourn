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
import com.ziroom.minsu.services.house.dto.HouseBaseDto;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.proxy.HousePCServiceProxy;
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
public class HousePCServiceProxyTest extends BaseTest{
	
	@Resource(name="house.housePCServiceProxy")
	private HousePCServiceProxy housePCServiceProxy;
	
	@Test
	public void testgetOnlineHouseRoomList(){
		HouseBaseListDto dto = new HouseBaseListDto();
		dto.setLandlordUid("8a9e989e5d15d0fc015d15d0fcf10001");
		String result = housePCServiceProxy.getOnlineHouseRoomList(JsonEntityTransform.Object2Json(dto));
		System.out.println(result);
	}
	
	@Test
	public void testgetCalendarDateList(){
		HouseBaseDto dto = new HouseBaseDto();
		dto.setHouseFid("8a9e989e5d15d0fc015d15d0fcf10001");
		dto.setRentWay(0);
		String calendarDateList = housePCServiceProxy.getCalendarDate(JsonEntityTransform.Object2Json(dto));
		System.out.println(calendarDateList);
	}
	
	@Test
	public void testgetLandlordHouseOrRoomList(){
		HouseBaseListDto dto = new HouseBaseListDto();
		dto.setLandlordUid("0f163457-ad6a-09ce-d5de-de452a251cf6");
		dto.setRentWay(0);
		String calendarDateList = housePCServiceProxy.getLandlordHouseOrRoomList(JsonEntityTransform.Object2Json(dto));
		System.out.println(calendarDateList);
	}
	
}
