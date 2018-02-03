/**
 * @FileName: HouseTopColumnDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2017年3月17日 下午5:28:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseTopColumnEntity;
import com.ziroom.minsu.services.house.dao.HouseTopColumnDao;
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
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseTopColumnDaoTest extends BaseTest{

	@Resource(name = "house.houseTopColumnDao")
	private  HouseTopColumnDao houseTopColumnDao;
	
	@Test
	public void findHouseTopColumnByHouseTopFidTest() {
		
		 List<HouseTopColumnEntity> list = this.houseTopColumnDao.findHouseTopColumnByHouseTopFid("562908bc7c616032931e856cea15cd79");
		 
		 System.out.println(JsonEntityTransform.Object2Json(list));
	}

}
