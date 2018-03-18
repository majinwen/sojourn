package com.ziroom.zrp.service.houses.dao;


import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.RentContractActivityDao;
import com.ziroom.zrp.trading.entity.RentContractActivityEntity;
import org.junit.Test;

import javax.annotation.Resource;


public class RentContractActivityDaoTest extends BaseTest{



	@Resource(name = "trading.rentContractActivityDao")
	private RentContractActivityDao rentContractActivityDao;

	@Test
	public void testSave(){
		RentContractActivityEntity rentContractActivityEntity = new RentContractActivityEntity();
		rentContractActivityEntity.setContractId("fhdskjhfksahlf");
		rentContractActivityEntity.setActivityId(111);
		rentContractActivityEntity.setCategory(90);
		rentContractActivityEntity.setActivityName("测试活动");
		rentContractActivityEntity.setActivityNumber("fjdlksjf");
		rentContractActivityEntity.setExpenseItemCode("fjkdsahjf");
		int num = rentContractActivityDao.insertSelective(rentContractActivityEntity);
		System.err.print(num);
	}


}
