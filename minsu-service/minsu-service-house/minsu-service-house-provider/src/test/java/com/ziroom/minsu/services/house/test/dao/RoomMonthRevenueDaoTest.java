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

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.RoomMonthRevenueEntity;
import com.ziroom.minsu.services.house.dao.RoomMonthRevenueDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>房间月收益dao测试类</p>
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
public class RoomMonthRevenueDaoTest extends BaseTest{
	
	@Resource(name="house.roomMonthRevenueDao")
	private RoomMonthRevenueDao roomMonthRevenueDao;
	
	@Test
	public void insertHouseDayRevenueTest() {
		RoomMonthRevenueEntity roomMonthRevenue = new RoomMonthRevenueEntity();
		roomMonthRevenue.setFid(UUIDGenerator.hexUUID());
		roomMonthRevenue.setHouseMonthRevenueFid("8a9e9ab0545833bf01545833bf230000");
		roomMonthRevenue.setRoomFid("8a9e9aae5419d34e015419d3510a0001");
		roomMonthRevenue.setRoomMonthRevenue(320);
		roomMonthRevenue.setHouseShareRevenue(250);
		roomMonthRevenueDao.insertRoomMonthRevenue(roomMonthRevenue);
	}
	
	@Test
	public void updateRoomMonthRevenueTest() {
		RoomMonthRevenueEntity roomMonthRevenue = new RoomMonthRevenueEntity();
		roomMonthRevenue.setFid("8a9e9ab0545837ec01545837ec7c0000");
		roomMonthRevenue.setHouseMonthRevenueFid("8a9e9ab0545833bf01545833bf230001");
		roomMonthRevenue.setRoomFid("8a9e9aae5419d34e015419d3510a0001");
		roomMonthRevenue.setRoomMonthRevenue(320);
		roomMonthRevenue.setHouseShareRevenue(250);
		roomMonthRevenue.setIsDel(0);
		roomMonthRevenue.setLastModifyDate(new Date());
		roomMonthRevenueDao.updateRoomMonthRevenue(roomMonthRevenue);
	}
	
	@Test
	public void findRoomMonthRevenueListByHouseMonthRevenueFidTest() {
		String houseMonthRevenueFid = "8a9e9ab0545833bf01545833bf230001";
		List<RoomMonthRevenueEntity> list = roomMonthRevenueDao
				.findRoomMonthRevenueListByHouseMonthRevenueFid(houseMonthRevenueFid);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

}
