package com.ziroom.minsu.services.house.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseGuardLogEntity;
import com.ziroom.minsu.services.house.dao.HouseGuardLogDao;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * 
 * <p>房源维护管家日志dao测试类</p>
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
public class HouseGuardLogDaoTest extends BaseTest{
	
	@Resource(name = "house.houseGuardLogDao")
    private HouseGuardLogDao houseGuardLogDao;
	
	@Test
	public void insertHouseGuardLogTest(){
		HouseGuardLogEntity entity = new HouseGuardLogEntity();
		entity.setFid(UUIDGenerator.hexUUID());
		entity.setGuardRelFid(UUIDGenerator.hexUUID());
		entity.setOldGuardCode("20080808");
		entity.setOldGuardName("ZO");
		entity.setCreaterFid(UUIDGenerator.hexUUID());
		entity.setCreateDate(new Date());
		houseGuardLogDao.insertHouseGuardLog(entity);
	}
	
	@Test
	public void findHouseGuardLogByFidTest(){
		String fid = "8a9e9aa855c070e60155c070e6c30000";
		HouseGuardLogEntity entity = houseGuardLogDao.findHouseGuardLogByFid(fid);
		System.err.println(JsonEntityTransform.Object2Json(entity));
	}
	
	@Test
	public void findHouseGuardLogForPageTest(){
		HouseGuardDto houseGuardDto = new HouseGuardDto();
		houseGuardDto.setLimit(50);
		houseGuardDto.setPage(1);
		houseGuardDto.setHouseGuardFid("8a9e9aa855c070e60155c070e6c30001");
		PagingResult<HouseGuardLogEntity> list = houseGuardLogDao.findHouseGuardLogForPage(houseGuardDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

}
