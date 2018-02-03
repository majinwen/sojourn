/**
 * @FileName: HouseDayRevenueDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月11日 下午5:15:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseDayRevenueEntity;
import com.ziroom.minsu.entity.house.HouseMonthRevenueEntity;
import com.ziroom.minsu.entity.house.RoomMonthRevenueEntity;
import com.ziroom.minsu.services.house.dao.HouseDayRevenueDao;
import com.ziroom.minsu.services.house.dto.LandlordRevenueDto;
import com.ziroom.minsu.services.house.entity.LandlordRevenueVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>测试</p>
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
public class HouseDayRevenueDaoTest extends BaseTest{
	
	@Resource(name="house.houseDayRevenueDao")
	private HouseDayRevenueDao houseDayRevenueDao;
	
	@Test
	public void insertHouseDayRevenueTest() throws ParseException{
		HouseDayRevenueEntity houseDayRevenueEntity=new HouseDayRevenueEntity();
		houseDayRevenueEntity.setFid(UUIDGenerator.hexUUID());
		houseDayRevenueEntity.setHouseBaseFid("8a9e9a9453f95bf40153f95bf70c0001");
		houseDayRevenueEntity.setDayRevenue(40000);
		houseDayRevenueEntity.setLandlordUid("8a9e9a9453f95bf40153f95bf4770000");
		houseDayRevenueEntity.setStatisticsDateDay(DateUtil.parseDate(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"));
		houseDayRevenueEntity.setStatisticsDateMonth(Integer.valueOf(DateUtil.dateFormat(new Date(), "MM")));
		houseDayRevenueEntity.setStatisticsDateYear(Integer.valueOf(DateUtil.dateFormat(new Date(), "yyyy")));
		houseDayRevenueEntity.setRentWay(0);
		houseDayRevenueDao.insertHouseDayRevenue(houseDayRevenueEntity);
	}
	
	@Test
	public void getAddUpRevenueByLandlordUidTest() {
		String landlordUid = "8a9e9a9453f95bf40153f95bf4770000";
		Integer totalIncome = houseDayRevenueDao.getAddUpRevenueByLandlordUid(landlordUid);
		System.err.println(totalIncome);
	}
	
	@Test
	public void getYesterdayRevenueByLandlordUidTest() {
		String landlordUid = "8a9e9a9453f95bf40153f95bf4770000";
		String yesterDate = DateUtil.getDayBeforeCurrentDate();
		Integer totalIncome = houseDayRevenueDao.getYesterdayRevenueByLandlordUid(landlordUid, yesterDate);
		System.err.println(totalIncome);
	}
	
	@Test
	public void getMonthRevenueByLandlordUidTest() {
		String landlordUid = "8a9e9a9453f95bf40153f95bf4770000";
		Integer statisticsDateMonth = Integer.valueOf(DateUtil.dateFormat(new Date(), "MM"));
		Integer totalIncome = houseDayRevenueDao.getMonthRevenueByLandlordUid(landlordUid, statisticsDateMonth);
		System.err.println(totalIncome);
	}
	
	@Test
	public void getWeekRevenueByLandlordUidTest() {
		String landlordUid = "8a9e9a9453f95bf40153f95bf4770000";
		String firstDayOfWeek = DateUtil.getFirstDayOfWeek(new Date());
		String lastDayOfWeek = DateUtil.getLastDayOfWeek(new Date());
		Integer totalIncome = houseDayRevenueDao.getWeekRevenueByLandlordUid(landlordUid, firstDayOfWeek, lastDayOfWeek);
		System.err.println(totalIncome);
	}
	
	@Test
	public void getMonthRevenueListByLandlordUidTest() {
		String landlordUid = "8a9e9a9453f95bf40153f95bf4770000";
		Integer statisticsDateYear = Integer.valueOf(DateUtil.dateFormat(new Date(), "yyyy"));
		List<LandlordRevenueVo> list = houseDayRevenueDao.getMonthRevenueListByLandlordUid(landlordUid, statisticsDateYear);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseRevenueListByLandlordUidTest() {
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setLandlordUid("8a9e9a9453f95bf40153f95bf4770000");
		landlordRevenueDto.setStatisticsDateYear(2016);
		landlordRevenueDto.setStatisticsDateMonth(3);
		List<LandlordRevenueVo> list = houseDayRevenueDao.findHouseRevenueListByLandlordUid(landlordRevenueDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseMonthRevenueByHouseBaseFidTest() {
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		landlordRevenueDto.setStatisticsDateYear(Integer.valueOf(DateUtil.dateFormat(new Date(), "yyyy")));
		landlordRevenueDto.setStatisticsDateMonth(Integer.valueOf(DateUtil.dateFormat(new Date(), "MM")));
		HouseMonthRevenueEntity entity = houseDayRevenueDao.findHouseMonthRevenueByHouseBaseFid(landlordRevenueDto);
		System.err.println(JsonEntityTransform.Object2Json(entity));
	}
	
	@Test
	public void findRoomDayRevenueListByHouseBaseFidTest() {
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		landlordRevenueDto.setStatisticsDateYear(Integer.valueOf(DateUtil.dateFormat(new Date(), "yyyy")));
		landlordRevenueDto.setStatisticsDateMonth(Integer.valueOf(DateUtil.dateFormat(new Date(), "MM")));
		List<RoomMonthRevenueEntity> list = houseDayRevenueDao.findRoomMonthRevenueListByHouseBaseFid(landlordRevenueDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
		System.err.println(list.size());
	}
	
	@Test
	public void findHouseBaseFidListFromHouseDayRevenueTest() {
		Integer statisticsDateYear = Integer.valueOf(DateUtil.dateFormat(new Date(), "yyyy"));
		Integer statisticsDateMonth = Integer.valueOf(DateUtil.dateFormat(new Date(), "MM"));
		List<String> list = houseDayRevenueDao.findHouseBaseFidListFromHouseDayRevenue(statisticsDateYear, statisticsDateMonth);
		System.err.println(JsonEntityTransform.Object2Json(list));
		System.err.println(list.size());
	}

}
