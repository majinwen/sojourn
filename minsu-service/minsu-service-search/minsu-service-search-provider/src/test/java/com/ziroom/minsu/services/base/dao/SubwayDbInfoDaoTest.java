package com.ziroom.minsu.services.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import base.BaseTest;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.dao.SubwayDbInfoDao;
import com.ziroom.minsu.services.search.vo.SubwayStationVo;

public class SubwayDbInfoDaoTest extends BaseTest {

	@Resource(name = "search.subwayDbInfoDao")
	private SubwayDbInfoDao subwayDbInfoDao;
	
	@Test
	public void getSubwayStationTest(){
		
		
		List<SubwayStationVo> subwayStation = subwayDbInfoDao.getSubwayStation("110100");
		System.err.println(JsonEntityTransform.Object2Json(subwayStation));
	}

}
