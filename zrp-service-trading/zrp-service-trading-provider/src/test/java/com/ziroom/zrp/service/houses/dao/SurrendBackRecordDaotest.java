package com.ziroom.zrp.service.houses.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.SurrendBackRecordDao;
import com.ziroom.zrp.trading.entity.SurrendBackRecordEntity;

public class SurrendBackRecordDaotest extends BaseTest{

	@Resource(name="trading.surrendBackRecordDao")
	private SurrendBackRecordDao surrendBackRecordDao;
	
	@Test
	public void testfindSurrendBackRecordEntityByParam(){
		String surrenderId = "2c908d174e4d9a9c014e4e086c8e0050";
		String type = "1";
		List<SurrendBackRecordEntity> ss = surrendBackRecordDao.findSurrendBackRecordEntityByParam(surrenderId, type);
		System.out.println(JsonEntityTransform.Object2Json(ss));
	}
	@Test
	public void testsaveSurrendBackRecord(){
		SurrendBackRecordEntity surrendBackRecordEntity = new SurrendBackRecordEntity();
		surrendBackRecordEntity.setFid("1");
		surrendBackRecordEntity.setSurrenderid("9028076f-690b-4b72-b743-9b2381c1f7be");
		surrendBackRecordEntity.setFisdel(1);
		surrendBackRecordDao.saveSurrendBackRecord(surrendBackRecordEntity);
	}
}
