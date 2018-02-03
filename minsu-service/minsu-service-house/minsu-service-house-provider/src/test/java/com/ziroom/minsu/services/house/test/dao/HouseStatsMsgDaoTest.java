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

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseStatsDayMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseStatsDayMsgDao;
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
public class HouseStatsMsgDaoTest extends BaseTest{
	
	@Resource(name="house.houseStatsMsgDao")
	private HouseStatsDayMsgDao houseStatsMsgDao;
	
	
	@Test
	public void insertHouseStatisticsMsgTest(){
		HouseStatsDayMsgEntity houseStatsMsgEntity = new HouseStatsDayMsgEntity();
		houseStatsMsgEntity.setFid(UUIDGenerator.hexUUID());
		houseStatsMsgEntity.setHouseFid("8a9e9a9a54afbc950154afbc95290001");
		houseStatsMsgEntity.setRentWay(0);
		houseStatsMsgEntity.setStatsDate(new Date());
		houseStatsMsgEntity.setHouseSn("110100761075Z");
		houseStatsMsgEntity.setProvinceCode("110000");
		houseStatsMsgEntity.setCityCode("110100");
		int upNum = houseStatsMsgDao.insertHouseStatisticsMsg(houseStatsMsgEntity);
		System.err.println(upNum);
	}
	
	@Test
	public void deleteHouseStatsMsgByStatsDateTest() throws ParseException{
		int upNum = houseStatsMsgDao.deleteHouseStatsMsgByStatsDate(DateUtil.parseDate("2016-12-05", "yyyy-MM-dd"));
		System.err.println(upNum);
	}
}
