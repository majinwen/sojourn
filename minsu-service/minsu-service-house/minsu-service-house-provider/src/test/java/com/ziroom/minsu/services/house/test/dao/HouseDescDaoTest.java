/**
 * @FileName: HouseBaseMsgDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月1日 下午1:53:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.services.house.dao.HouseDescDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * 
 * <p>房源描述信息dao测试类</p>
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
public class HouseDescDaoTest extends BaseTest{
	
	@Resource(name = "house.houseDescDao")
    private HouseDescDao houseDescDao;

	
	@Test
	public void insertHouseDescTest(){
		HouseDescEntity houseDesc = new HouseDescEntity();
		houseDesc.setFid(UUIDGenerator.hexUUID());
		houseDesc.setHouseBaseFid(UUIDGenerator.hexUUID());
		houseDesc.setCreateUid(UUIDGenerator.hexUUID());
		houseDesc.setHouseDesc("测试房源描述");
		houseDesc.setHouseAroundDesc("测试房源交通状况");
		houseDescDao.insertHouseDesc(houseDesc);
	}
	
	@Test
	public void updateHouseDescTest(){
		HouseDescEntity houseDesc = new HouseDescEntity();
		houseDesc.setFid("8a9e9a8b53d507bd0153d507bd7d0000");
		houseDesc.setHouseBaseFid(UUIDGenerator.hexUUID());
		houseDesc.setCreateUid(UUIDGenerator.hexUUID());
		houseDesc.setHouseDesc("测试房源描述");
		houseDesc.setHouseAroundDesc("测试房源交通状况");
		int line = houseDescDao.updateHouseDesc(houseDesc);
		System.err.println(line);
	}

}
