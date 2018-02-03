/**
 * @FileName: HouseStatisticsMsgDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年5月15日 上午1:47:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseStatisticsMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseStatisticsMsgDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>TODO</p>
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
public class HouseStatisticsMsgDaoTest extends BaseTest{
	
	@Resource(name="house.houseStatisticsMsgDao")
	private HouseStatisticsMsgDao houseStatisticsMsgDao;
	
	
	@Test
	public void insertHouseStatisticsMsgTest(){
		HouseStatisticsMsgEntity houseStatisticsMsgEntity=new HouseStatisticsMsgEntity();
		houseStatisticsMsgEntity.setFid(UUIDGenerator.hexUUID());
		houseStatisticsMsgEntity.setHouseBaseFid("8a9e9a9a54afbc950154afbc95290001");
		houseStatisticsMsgEntity.setRentWay(0);
		houseStatisticsMsgEntity.setHousePv(10);
		houseStatisticsMsgDao.insertHouseStatisticsMsg(houseStatisticsMsgEntity);
	}
	
	@Test
	public void getHouseStatisticsMsgByParamTest(){
		HouseStatisticsMsgEntity paramMap=new HouseStatisticsMsgEntity();
		paramMap.setRentWay(0);
		paramMap.setHouseBaseFid("8a9e9a9a54afbc950154afbc95290001");
		HouseStatisticsMsgEntity houseStatisticsMsgEntity=houseStatisticsMsgDao.getHouseStatisticsMsgByParam(paramMap);
		System.err.println(JsonEntityTransform.Object2Json(houseStatisticsMsgEntity));
	}
	
	@Test
	public void updateHouseStatisticsMsgPvByParamTest(){
		HouseStatisticsMsgEntity paramMap=new HouseStatisticsMsgEntity();
		paramMap.setRentWay(0);
		paramMap.setHouseBaseFid("8a9e9a9a54afbc950154afbc95290001");
		paramMap.setHousePv(50);
		int num=houseStatisticsMsgDao.updateHouseStatisticsMsgPvByParam(paramMap);
		System.err.println(num);
	}
}
