/**
 * @FileName: HouseManageServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2016年4月3日 下午1:10:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.proxy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HouseCheckDto;
import com.ziroom.minsu.services.house.dto.HousePriceConfDto;
import com.ziroom.minsu.services.house.dto.HousePriceWeekConfDto;
import com.ziroom.minsu.services.house.dto.LandlordRevenueDto;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.dto.OrderHouseDto;
import com.ziroom.minsu.services.house.proxy.HouseManageServiceProxy;
import com.ziroom.minsu.services.house.smartlock.dto.MSmartLockDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;

/**
 * <p>房源管理测试</p>
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
public class HouseManageServiceProxyTest extends BaseTest{
	
	@Resource(name="house.houseManageServiceProxy")
	private HouseManageServiceProxy houseManageServiceProxy;

	@Test
	public void getHouseListByHouseSnsTest(){
		List<String> houseSns = new ArrayList<String>();
		houseSns.add("110100352003Z");
		houseSns.add("110100952787Z");
		String resultJson = houseManageServiceProxy.getHouseListByHouseSns(JsonEntityTransform.Object2Json(houseSns));
		System.err.println(resultJson);
	}

	@Test
	public void getRoomListByRoomSnsTest(){
		List<String> roomSns = new ArrayList<String>();
		roomSns.add("110100446361F");
		roomSns.add("110100107386F");
		String resultJson = houseManageServiceProxy.getRoomListByRoomSns(JsonEntityTransform.Object2Json(roomSns));
		System.err.println(resultJson);
	}

	@Test
	public void findOrderNeedHouseVoPlus()throws Exception{
		OrderHouseDto orderHouseDto = new OrderHouseDto();
		orderHouseDto.setFid("8a9099775c3aa61e015c3f064848095f");
		orderHouseDto.setRentWay(0);
		orderHouseDto.setStartDate(DateUtil.parseDate("2017-08-17 ", "yyyy-MM-dd"));
		orderHouseDto.setEndDate(DateUtil.parseDate("2017-10-31", "yyyy-MM-dd"));
		String resultJson=houseManageServiceProxy.findOrderNeedHouseVoPlus(JsonEntityTransform.Object2Json(orderHouseDto));
		System.err.println(resultJson);
	}


    @Test
    public void TestfindOrderNeedHouseVo(){
        String resultJson=houseManageServiceProxy.findOrderNeedHouseVo("8a9084df5847373d0158485f8b7d032d",0);
        System.err.println(resultJson);
    }

	@Test
	public void refreshHouse(){
		String resultJson=houseManageServiceProxy.refreshHouse("8a9e9aae5419cc22015419cc250a0002");
		System.err.println(resultJson);
	}
	
	@Test
	public void houseListTest(){
		HouseBaseListDto houseBaseListDto=new HouseBaseListDto();
		houseBaseListDto.setLandlordUid("8a9e9a8b53d6089f0153d6089f710000");
		houseBaseListDto.setHouseStatus(HouseStatusEnum.DFB.getCode());
		houseBaseListDto.setPage(1);
		houseBaseListDto.setLimit(10);
		String resultJson=houseManageServiceProxy.houseList(JsonEntityTransform.Object2Json(houseBaseListDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void houseRoomListTest(){
		HouseBaseListDto houseBaseListDto=new HouseBaseListDto();
		houseBaseListDto.setLandlordUid("8a9e9a8b53d6089f0153d608a1f80002");
		houseBaseListDto.setHouseStatus(HouseStatusEnum.DFB.getCode());
		houseBaseListDto.setPage(1);
		houseBaseListDto.setLimit(10);
		String resultJson=houseManageServiceProxy.houseRoomList(JsonEntityTransform.Object2Json(houseBaseListDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void upDownHouseTest(){
		String resutJson=houseManageServiceProxy.upDownHouse("8a9e9a8b53d6089f0153d608a1f80002", "8a9e9a8b53d6089f0153d608a1f80002");
		System.err.println(resutJson);
	}
	
	@Test
	public void upDownHouseRoomTest(){
		String resultJson=houseManageServiceProxy.upDownHouseRoom("8a9e9a8b53d62d740153d62d76730002", "8a9e9a8b53d6089f0153d608a1f80002");
		System.err.println(resultJson);
	}
	
	@Test
	public void leaseCalendarTest() throws ParseException{
		LeaseCalendarDto leaseCalendarDto=new LeaseCalendarDto();
		leaseCalendarDto.setRentWay(0);
		leaseCalendarDto.setStartDate(DateUtil.parseDate("2016-09-11", "yyyy-MM-dd"));
		leaseCalendarDto.setEndDate(DateUtil.parseDate("2016-09-30", "yyyy-MM-dd"));
		leaseCalendarDto.setHouseBaseFid("8a9084df56fad7b00156fd3e8a2006a1");
//		leaseCalendarDto.setHouseRoomFid("8a9e9a8b53d62d740153d62d76730002");
		String resultJson=houseManageServiceProxy.leaseCalendar(JsonEntityTransform.Object2Json(leaseCalendarDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void setSpecialPriceTest() throws ParseException{
		HousePriceConfDto housePriceConfEntity=new HousePriceConfDto();
		housePriceConfEntity.setFid(UUIDGenerator.hexUUID());
		housePriceConfEntity.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		housePriceConfEntity.setSetTime(DateUtil.parseDate("2016-4-28", "yyyy-MM-dd"));
		housePriceConfEntity.setRentWay(0);
		housePriceConfEntity.setPriceVal(100);
		housePriceConfEntity.setCreateUid("8a9e9a8b53d6089f0153d608a1f80002");
		List<HousePriceConfDto> list=new ArrayList<HousePriceConfDto>();
		HousePriceConfDto housePriceConfEntity1=new HousePriceConfDto();
		list.add(housePriceConfEntity);

		System.err.println(houseManageServiceProxy.setSpecialPrice(JsonEntityTransform.Object2Json(list)));
	}
	
	@Test
	public void houseDetailTest(){
		String resultJson=houseManageServiceProxy.houseDetail("8a9e9a8b53d6089f0153d608a1f80002", "8a9e9a8b53d6089f0153d6089f710000");
		System.err.println(resultJson);
	}
	
	@Test
	public void communityNameListTest(){
		String resultJson=houseManageServiceProxy.communityNameList("8a9e9a8b53d6da8b0153d6da8bae0000");
		System.err.println(resultJson);
	}
	
	@Test
	public void searchHouseRoomListTest(){
		HouseBaseListDto houseBaseListDto = new HouseBaseListDto();
		houseBaseListDto.setLandlordUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		houseBaseListDto.setPage(1);
		houseBaseListDto.setLimit(10);
		//houseBaseListDto.setHouseStatus(10);
		//houseBaseListDto.setHousePhyFid("8a9e9aae5419cc22015419cc24e60001");
		String resultJson=houseManageServiceProxy.searchHouseRoomList(JsonEntityTransform.Object2Json(houseBaseListDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchLandlordRevenueTest(){
		String landlordUid = "8a9e9a9453f95bf40153f95bf4770000";
		String resultJson = houseManageServiceProxy.searchLandlordRevenue(landlordUid);
		System.err.println(resultJson);
	}
	
	@Test
	public void searchHouseRevenueListByLandlordUidTest(){
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setLandlordUid("8a9e9a9453f95bf40153f95bf4770000");
		landlordRevenueDto.setStatisticsDateYear(2016);
		landlordRevenueDto.setStatisticsDateMonth(4);
		String resultJson = houseManageServiceProxy.searchHouseRevenueListByLandlordUid(JsonEntityTransform
				.Object2Json(landlordRevenueDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchMonthRevenueListByHouseBaseFidTest(){
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		landlordRevenueDto.setStatisticsDateYear(2016);
		landlordRevenueDto.setStatisticsDateMonth(4);
		String resultJson = houseManageServiceProxy.searchMonthRevenueListByHouseBaseFid(JsonEntityTransform
				.Object2Json(landlordRevenueDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchMonthRevenueListTest(){
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setLandlordUid("8a9e9a9453f95bf40153f95bf4770000");
		landlordRevenueDto.setStatisticsDateYear(2016);
		String resultJson = houseManageServiceProxy.searchMonthRevenueList(JsonEntityTransform
				.Object2Json(landlordRevenueDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchLandlordHouseListTest(){
		HouseBaseListDto houseBaseListDto = new HouseBaseListDto();
		houseBaseListDto.setLandlordUid("5dc1d8c8-54ef-41c2-8a28-83dbdb6f8c01");
		houseBaseListDto.setPage(1);
		houseBaseListDto.setLimit(5);
		houseBaseListDto.setRentWay(0);
		houseBaseListDto.setHouseBaseFid("8a9099775fc2cde6015fdd14cc73340c");
		String resultJson=houseManageServiceProxy.searchLandlordHouseList(JsonEntityTransform.Object2Json(houseBaseListDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void checkHouseOrRoomTest(){
		HouseCheckDto houseCheckDto = new HouseCheckDto();

		houseCheckDto.setFid("8a90a2d4549341c60154940db7c800ee");
		houseCheckDto.setRentWay(1);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseManageServiceProxy.checkHouseOrRoom(JsonEntityTransform.Object2Json(houseCheckDto)));
		
		System.out.println(dto);
	}
	@Test
	public void testfindHouseSmartlock(){
		MSmartLockDto dto = new MSmartLockDto();
		dto.setLandlordUid("0f163457-ad6a-09ce-d5de-de452a251cf6");
		dto.setHouseBaseFid("8a9e9aa8555711fd01555711fda20001");
		dto.setRoomFid("8a9e9aa8555711fd01555711fda20001");
		String resJson = houseManageServiceProxy.findHouseSmartlock(JsonEntityTransform.Object2Json(dto));
		System.out.println(resJson);
		
	}
	
	@Test
    public void saveHousePriceWeekConf(){
		
		HousePriceWeekConfDto weekPriceDto = new HousePriceWeekConfDto();
		weekPriceDto.setCreateUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		weekPriceDto.setRentWay(0);
		weekPriceDto.setHouseBaseFid("8a9084df56fad7b00156fd3e8a2006a1");
		  
		weekPriceDto.setPriceVal(6700);
		Set<Integer> weekSet = new HashSet<>();
		weekSet.add(5);
		weekSet.add(6); 
		
		weekPriceDto.setSetWeeks(weekSet);  
        String resultJson=houseManageServiceProxy.saveHousePriceWeekConf(JsonEntityTransform.Object2Json(weekPriceDto));
        System.err.println(resultJson);
    }
	
	@Test
	public void searchHouseBaseMsgByLandlorduid(){
		String houseBaseMsg = houseManageServiceProxy.searchHouseBaseMsgByLandlorduid("0f163457-ad6a-09ce-d5de-de452a251cf6");
		System.err.println(houseBaseMsg);
		
	}
	
	@Test
	public void updateHousePriceWeekConfByFid(){
		HousePriceWeekConfEntity housePriceWeekConf = new HousePriceWeekConfEntity();
    	housePriceWeekConf.setFid("8a9084df5746344f0157467e498504b1");
    	List<HousePriceWeekConfEntity> list = new ArrayList<HousePriceWeekConfEntity>();
    	list.add(housePriceWeekConf);
		String resultJson = houseManageServiceProxy.updateHousePriceWeekListByFid(JsonEntityTransform.Object2Json(list));
		System.err.println(resultJson);
	}
	
}
