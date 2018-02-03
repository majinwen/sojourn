/**
 * @FileName: HouseUpdateHistoryExtLogDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2017年7月3日 下午4:15:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.house.HouseUpdateHistoryExtLogEntity;
import com.ziroom.minsu.services.house.dao.HouseUpdateHistoryExtLogDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;
 
/**
 * <p>测试</p>
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
public class HouseUpdateHistoryExtLogDaoTest extends BaseTest{
	
	
	@Resource(name = "house.houseUpdateHistoryExtLogDao")
	HouseUpdateHistoryExtLogDao houseUpdateHistoryExtLogDao ;

	@Test
	public void saveHouseUpdateHistoryExtLog() {
		
		HouseUpdateHistoryExtLogEntity houseUpdateHistoryExtLog = new HouseUpdateHistoryExtLogEntity();
		houseUpdateHistoryExtLog.setFid("8a9e989c5d077ff8015d077ff8320000");
		houseUpdateHistoryExtLog.setNewValue("杨东的测试房源55555fdsa1f64sd56f4ds56f4ds56f4sd5f4sd5f4sd5f4sd5f4sdf4sd5f4dsf4sd5f4dsf4sdf4dsf45sd6f4");
		houseUpdateHistoryExtLog.setOldValue("大连民族的测试房源666666af16sd4f56sd4f5sd4f5sdf4sdf4dsfffffffffffffffffffffffffff555555555555555555555");
		houseUpdateHistoryExtLogDao.saveHouseUpdateHistoryExtLog(houseUpdateHistoryExtLog);
	}

}
