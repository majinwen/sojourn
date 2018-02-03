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

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseDescDto;
import com.ziroom.minsu.services.house.dto.HousePriceWeekConfDto;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.house.entity.SpecialPriceVo;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class HouseManageServiceImplTest extends BaseTest{
	
	@Resource(name="house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;
	
	@Resource(name = "house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;
	
	
	@Test
	public void getHousePicList(){
		HouseDescDto dto = new HouseDescDto();
		dto.setHouseBaseFid("8a9e9aae5419cc22015419cc251d1113");
		dto.setHouseRules("sdfsdfsfsdfsdfsdf");
		houseIssueServiceImpl.updateHouseDescAndBaseExt(dto);
	}
	
	@Test
	public void countByConditionTest(){
		
		HouseRoomMsgEntity houseRoomMsgDto = new HouseRoomMsgEntity();
		houseRoomMsgDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		houseRoomMsgDto.setDefaultPicFid("8a9e9aae5419d73b015419d73b0");
		houseRoomMsgDto.setRoomStatus(HouseStatusEnum.SJ.getCode());
		Long coun = this.houseManageServiceImpl.countByCondition(houseRoomMsgDto);
		
		System.out.println(coun);
	}
	
	
	@Test
	public void findSpecialPriceListTest() throws ParseException{
		LeaseCalendarDto leaseCalendarDto =new LeaseCalendarDto();
		leaseCalendarDto.setHouseBaseFid("8a9084df56fad7b00156fd3e8a2006a1");
		leaseCalendarDto.setStartDate(DateUtil.parseDate("2016-09-11", "yyyy-MM-dd"));
		leaseCalendarDto.setEndDate(DateUtil.parseDate("2016-09-30", "yyyy-MM-dd"));
		leaseCalendarDto.setRentWay(0);
		List<SpecialPriceVo> list =houseManageServiceImpl.findSpecialPriceList(leaseCalendarDto);
		System.out.println("result="+JsonEntityTransform.Object2Json(list));
		
	}
	
	
	@Test
    public void saveHousePriceWeekConf() throws ParseException{
		
		HousePriceWeekConfDto weekPriceDto = new HousePriceWeekConfDto();
		weekPriceDto.setCreateUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		weekPriceDto.setRentWay(1);
		weekPriceDto.setHouseRoomFid("8a9e988e5719006801571bf77a110027");
		weekPriceDto.setStartDate(DateUtil.parseDate("2016-09-01", "yyyy-MM-dd"));
		weekPriceDto.setEndDate(DateUtil.parseDate("2017-4-0", "yyyy-MM-dd"));
		  
		weekPriceDto.setPriceVal(86666);
		Set<Integer> weekSet = new HashSet<>();
		weekSet.add(5);
		weekSet.add(6); 
		
		weekPriceDto.setSetWeeks(weekSet);  
        int num =houseManageServiceImpl.saveHousePriceWeekConf(weekPriceDto);
        System.err.println(num);
    }
	
	@Test
	public void findOrderNeedHouseVo() {
		String fidString="8a9e98925745a676015746b966d00032";
		int rentWay=1;
		OrderNeedHouseVo vo = houseManageServiceImpl.findOrderNeedHouseVo(fidString,rentWay);
		System.err.println("result="+JsonEntityTransform.Object2Json(vo));
	}

	@Test
	public void getRoomFidListByDefaultPicFid(){
		String defaultPicFid = "8a90a2d255b9ab980155ba286e910044";
		List<String> list = houseManageServiceImpl.getRoomFidListByDefaultPicFid(defaultPicFid);
		System.out.print(list);
	}

	@Test
	public void getRoomFidByPicFid(){
		String picFid = "8a9e9a9e541a404c01541a5138df0003";
		String result = houseManageServiceImpl.getRoomFidByPicFid(picFid);
		System.out.print(Check.NuNStr(result));
	}
	
}
