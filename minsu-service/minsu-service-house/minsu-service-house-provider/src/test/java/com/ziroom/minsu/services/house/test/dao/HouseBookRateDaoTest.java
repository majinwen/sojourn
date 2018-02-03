/**
 * @FileName: HouseBookRateDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月11日 下午4:30:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBookRateEntity;
import com.ziroom.minsu.services.house.dao.HouseBookRateDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>未来30天预订率dao测试</p>
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
public class HouseBookRateDaoTest extends BaseTest{
	
	@Resource(name="house.houseBookRateDao")
	private HouseBookRateDao houseBookRateDao;
	
	@Test
	public void insertHouseBookRate(){
		HouseBookRateEntity houseBookRateEntity=new HouseBookRateEntity();
		houseBookRateEntity.setFid(UUIDGenerator.hexUUID());
		houseBookRateEntity.setHouseBaseFid("8a9e9a9453f95bf40153f95bf70c0001");
		houseBookRateEntity.setHouseBookRate(23.8);
		houseBookRateDao.insertHouseBookRate(houseBookRateEntity);
	}
	
	@Test
	public void updateHouseBookRate(){
		HouseBookRateEntity houseBookRateEntity=new HouseBookRateEntity();
		houseBookRateEntity.setFid("822f10cf-fb7d-11e5-9cf9-0050568f07f8");
		houseBookRateEntity.setHouseBookRate(34d);
		houseBookRateDao.updateHouseBookRate(houseBookRateEntity);
	}
}
