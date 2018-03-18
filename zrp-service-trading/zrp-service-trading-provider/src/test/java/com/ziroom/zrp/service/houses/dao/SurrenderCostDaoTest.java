package com.ziroom.zrp.service.houses.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.dubbo.common.json.JSON;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.SurrenderCostDao;
import com.ziroom.zrp.trading.entity.SurrenderCostEntity;

public class SurrenderCostDaoTest extends BaseTest{

	@Resource(name="trading.surrenderCostDao")
	private SurrenderCostDao surrenderCostDao;
	
	@Test
	public void testselectSurrenderCostByParam(){
		String contractId = "2c908d174e00aba0014e01188fd4000d";
		String surrenderId = "2c909dd04e4d5076014e4d548bda0002";
		List<SurrenderCostEntity> ss = surrenderCostDao.selectSurrenderCostByParam(contractId, surrenderId);
		System.out.println(JsonEntityTransform.Object2Json(ss));
	}
	@Test
	public void testfindSurrenderCostByFid(){
		String surrendercostId = "2c909dd04e4d5076014e4d65b9af0008";
		SurrenderCostEntity surrenderCostEntity = surrenderCostDao.findSurrenderCostByFid(surrendercostId);
		System.out.println(JsonEntityTransform.Object2Json(surrenderCostEntity));
	}
}
