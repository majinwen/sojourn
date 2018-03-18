package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.SurMeterDetailDao;
import com.ziroom.zrp.trading.entity.SurMeterDetailEntity;

public class SurMeterDetailDaoTest extends BaseTest{

	@Resource(name="trading.surMeterDetailDao")
	private SurMeterDetailDao surMeterDetailDao;
	
	@Test
	public void testgetSDPriceBySurrenderId(){
		String surrenderId = "2c909dd04e4d5076014e4d548bda0002";
		SurMeterDetailEntity surMeterDetailEntity = this.surMeterDetailDao.getSDPriceBySurrenderId(surrenderId);
		System.out.println(JsonEntityTransform.Object2Json(surMeterDetailEntity));
	}
	
}
