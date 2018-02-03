/**
 * @FileName: HouseCommonServiceProxyTest.java
 * @Package com.ziroom.minsu.services.order.test.proxy
 * 
 * @author yd
 * @created 2016年12月8日 上午9:22:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.proxy.HouseCommonServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>order房源接口测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseCommonServiceProxyTest extends BaseTest{

	
	@Resource(name = "order.houseCommonServiceProxy")
	private HouseCommonServiceProxy houseCommonServiceProxy;
	
	
	@Test
	public void findPriorityDateTest() {
		
		try {
			HousePriorityDto housePriorityDto = new HousePriorityDto();
			
			housePriorityDto.setHouseBaseFid("8a90a2d458a4d4890158a8b9cf7b03aa");
			housePriorityDto.setStartDate(DateUtil.parseDate("2016-05-18", "yyyy-MM-dd"));
			housePriorityDto.setEndDate(DateUtil.parseDate("2017-12-11", "yyyy-MM-dd"));
			housePriorityDto.setNowDate(new Date());
			housePriorityDto.setRentWay(0);
			housePriorityDto.setTillDate(DateUtil.parseDate("2017-12-10", "yyyy-MM-dd"));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseCommonServiceProxy.findPriorityDate(JsonEntityTransform.Object2Json(housePriorityDto)));
			System.out.println(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getLockFidByLockTimeTest(){
        String lockTime=DateUtil.dateFormat(new Date(), "yyyy-MM-dd")+" 00:00:00";
        String rst=houseCommonServiceProxy.getLockFidByLockTime(lockTime);
        System.err.println(rst);
	}
	
	@Test
	public void isNewUserByOrder(){
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseCommonServiceProxy.isNewUserByOrder("664524c5-6e75-ee98-4e0d-667d38b9eee1"));
		if(dto.getCode() == DataTransferObject.SUCCESS){
			Integer isNewUser = dto.parseData("isNerUser",new TypeReference<Integer>() {
			});
			System.out.println(isNewUser);
		}
	}
	
	@Test
	public void getHouseLockDayList() {
		HouseLockRequest houseLockRequest = new HouseLockRequest();
		houseLockRequest.setFid("8a9084df5b9da32e015b9da3a31d0008");
		houseLockRequest.setRoomFid(null);
		houseLockRequest.setRentWay(0);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseCommonServiceProxy.getHouseLockDayList(JsonEntityTransform.Object2Json(houseLockRequest)));
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			System.out.println(dto.toJsonString());
			List<CalendarDataVo> list = dto.parseData("data", new TypeReference<List<CalendarDataVo>>() {});
			System.out.println(list);
		}
	}

	@Test
	public void getEffectiveOfJYTJInfoTest() {
		String param = "\"id\":null,\"fid\":null,\"houseFid\":\"8a90997858f76b5b0158f88124c50370\",\"roomFid\":null,\"rentWay\":0,\"discount\":null,\"discountSource\":null,\"startTime\":null,\"endTime\":null,\"createDate\":null,\"createUid\":null,\"discountDate\":null,\"lastModifyDate\":null,\"lastModifyUid\":null,\"isDel\":null";

		String result = houseCommonServiceProxy.getEffectiveOfJYTJInfo(param);
		System.out.println(result);

	}
}
