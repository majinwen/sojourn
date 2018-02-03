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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseMonthRevenueEntity;
import com.ziroom.minsu.services.house.dao.HouseMonthRevenueDao;
import com.ziroom.minsu.services.house.dto.LandlordRevenueDto;
import com.ziroom.minsu.services.house.entity.HouseMonthRevenueVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>房源约收益dao测试类</p>
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
public class HouseMonthRevenueDaoTest extends BaseTest{
	
	@Resource(name="house.houseMonthRevenueDao")
	private HouseMonthRevenueDao houseMonthRevenueDao;
	
	@Test
	public void insertHouseDayRevenueTest() {
		HouseMonthRevenueEntity houseMonthRevenueEntity = new HouseMonthRevenueEntity();
		houseMonthRevenueEntity.setFid(UUIDGenerator.hexUUID());
		houseMonthRevenueEntity.setHouseBaseFid("8a9e9a9453f95bf40153f95bf70c0001");
		houseMonthRevenueEntity.setMonthRevenue(1200);
		houseMonthRevenueEntity.setStatisticsDateMonth(1);
		houseMonthRevenueEntity.setStatisticsDateYear(2016);
		houseMonthRevenueEntity.setRevenueType(0);
		houseMonthRevenueDao.insertHouseMonthRevenue(houseMonthRevenueEntity);
	}
	
	@Test
	public void updateHouseMonthRevenueTest() {
		HouseMonthRevenueEntity houseMonthRevenueEntity = new HouseMonthRevenueEntity();
		houseMonthRevenueEntity.setFid("8a9e9ab0545833bf01545833bf230000");
		houseMonthRevenueEntity.setHouseBaseFid("8a9e9a9453f95bf40153f95bf70c0001");
		houseMonthRevenueEntity.setMonthRevenue(1200);
		houseMonthRevenueEntity.setStatisticsDateMonth(1);
		houseMonthRevenueEntity.setStatisticsDateYear(2016);
		houseMonthRevenueEntity.setRevenueType(0);
		houseMonthRevenueEntity.setIsDel(0);
		houseMonthRevenueEntity.setLastModifyDate(new Date());
		houseMonthRevenueDao.updateHouseMonthRevenue(houseMonthRevenueEntity);
	}
	
	@Test
	public void findMonthRevenueListByHouseBaseFidTest() {
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		landlordRevenueDto.setStatisticsDateYear(2016);
		List<HouseMonthRevenueVo> list = houseMonthRevenueDao.findMonthRevenueListByHouseBaseFid(landlordRevenueDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseBaseFidListFromHouseMonthRevenueTest() {
		Integer statisticsDateYear = Integer.valueOf(DateUtil.dateFormat(new Date(), "yyyy"));
		Integer statisticsDateMonth = Integer.valueOf(DateUtil.dateFormat(new Date(), "MM"));
		List<String> list = houseMonthRevenueDao.findHouseBaseFidListFromHouseMonthRevenue(statisticsDateYear,
				statisticsDateMonth);
		System.err.println(JsonEntityTransform.Object2Json(list));
		System.err.println(list.size());
	}
	
	@Test
	public void findOneHouseMonthRevenueTest() {
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		landlordRevenueDto.setStatisticsDateYear(2016);
		landlordRevenueDto.setStatisticsDateMonth(1);
		HouseMonthRevenueEntity houseMonthRevenue = houseMonthRevenueDao.findOneHouseMonthRevenue(landlordRevenueDto);
		System.err.println(JsonEntityTransform.Object2Json(houseMonthRevenue));
	}

}
