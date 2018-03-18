/**
 * @FileName: IntellectWatermeterClearDaoTest.java
 * @Package com.ziroom.zrp.service.houses.dao
 * 
 * @author bushujie
 * @created 2018年1月12日 下午5:13:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.IntellectWatermeterClearDao;
import com.ziroom.zrp.service.trading.valenum.ZKConfigEnum;
import com.ziroom.zrp.trading.entity.IntellectWatermeterClearEntity;

import java.util.*;

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
public class IntellectWatermeterClearDaoTest extends BaseTest{
	
	@Resource(name="trading.IntellectWatermeterClearDao")
	private IntellectWatermeterClearDao intellectWatermeterClearDao;
	
	
	@Test
	public void findIntellectWatermeterClearByFid(){
		IntellectWatermeterClearEntity intellectWatermeterClearEntity=intellectWatermeterClearDao.findIntellectWatermeterClearByFid("8a9e9ad260e9adbe0160e9adbeee0000");
		System.err.println(JsonEntityTransform.Object2Json(intellectWatermeterClearEntity));
	}
	
	@Test
	public void insertIntellectWatermeterClear(){
		IntellectWatermeterClearEntity intellectWatermeterClearEntity=new IntellectWatermeterClearEntity();
		intellectWatermeterClearEntity.setFid(UUIDGenerator.hexUUID());
		intellectWatermeterClearEntity.setContractId(UUIDGenerator.hexUUID());
		intellectWatermeterClearEntity.setClearingType(0);
		intellectWatermeterClearEntity.setClearingReading(256d);
		intellectWatermeterClearEntity.setSettlementStatus(0);
		intellectWatermeterClearEntity.setShareFactor(3);
		intellectWatermeterClearEntity.setStartReading(200d);
		intellectWatermeterClearEntity.setSumMoney(600);
		intellectWatermeterClearEntity.setUnitPrice(3d);
		intellectWatermeterClearDao.insertIntellectWatermeterClear(intellectWatermeterClearEntity);
	}
	
	@Test
	public void updateIntellectWatermeterClear(){
		IntellectWatermeterClearEntity intellectWatermeterClearEntity=intellectWatermeterClearDao.findIntellectWatermeterClearByFid("8a9e9ad260e9adbe0160e9adbeee0000");
		intellectWatermeterClearEntity.setContractId("123");
		int upNum=intellectWatermeterClearDao.updateIntellectWatermeterClear(intellectWatermeterClearEntity);
		System.err.println(upNum);
	}

	@Test
	public void findIntellectWatermeterClearByContractId(){
		List<IntellectWatermeterClearEntity> intellectWatermeterClearEntities = intellectWatermeterClearDao.findIntellectWatermeterClearByContractId("123");
		System.err.println(intellectWatermeterClearEntities);
		Date date = new Date();
		long l = date.getTime();

		Double startReading = intellectWatermeterClearEntities.stream().mapToDouble(IntellectWatermeterClearEntity::getStartReading).summaryStatistics().getMin();
		Double endReading = intellectWatermeterClearEntities.stream().mapToDouble(IntellectWatermeterClearEntity::getClearingReading).summaryStatistics().getMax();
		intellectWatermeterClearEntities.get(0).getCreateTime();
		intellectWatermeterClearEntities.get(intellectWatermeterClearEntities.size()-1).getCreateTime();


		System.err.println(startReading);
		System.err.println(endReading);
		System.err.println(intellectWatermeterClearEntities.get(0).getCreateTime());
		System.err.println(intellectWatermeterClearEntities.get(intellectWatermeterClearEntities.size()-1).getCreateTime());

	}
	
	@Test
    public void testD(){
    	String result = ZkUtil.getZkSysValue(ZKConfigEnum.ZK_CONFIG_ENUM_001.getType(), ZKConfigEnum.ZK_CONFIG_ENUM_001.getCode());
    	System.err.println(result);
    }

    @Test
    public void testupdateClearStatusToYJS(){
		System.out.println(intellectWatermeterClearDao.updateClearStatusToYJS("123"));
	}
}
