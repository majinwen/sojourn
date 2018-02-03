package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.SubwayStationEntity;
import com.ziroom.minsu.services.basedata.dao.SubwayStationDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class SubwayStationDaoTest extends BaseTest {
	
	@Resource(name = "basedata.subwayStationDao")
	private SubwayStationDao subwayStationDao;
	
	
	@Test
	public void TestSaveSubwayStation() {
		SubwayStationEntity subwayStation = new SubwayStationEntity();
		subwayStation.setFid(UUIDGenerator.hexUUID());
		subwayStation.setLineFid("8a9e9ab9540fa26e01540fa26ed30000");
		subwayStation.setStationName("将台");
		subwayStation.setLatitude(12313.21);
		subwayStation.setLongitude(2121.21);
		subwayStation.setCreateFid(UUIDGenerator.hexUUID());
		
		int resultNum = subwayStationDao.saveSubwayStation(subwayStation);
		System.out.println("resultNum:" + resultNum);
		
		
		
	}

}
