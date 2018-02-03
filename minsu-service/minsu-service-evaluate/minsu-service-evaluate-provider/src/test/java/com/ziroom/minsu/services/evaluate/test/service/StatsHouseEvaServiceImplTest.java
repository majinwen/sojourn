/**
 * @FileName: StatsHouseEvaServiceImplTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.service
 * 
 * @author yd
 * @created 2016年4月9日 下午9:44:52
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.service.StatsHouseEvaServiceImpl;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.services.evaluate.utils.EvaluateUtils;
import com.ziroom.minsu.valenum.house.RentWayEnum;

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
public class StatsHouseEvaServiceImplTest extends BaseTest{

	
	@Resource(name = "evaluate.statsHouseEvaServiceImpl")
	private StatsHouseEvaServiceImpl statsHouseEvaServiceImpl;
	@Test
	public void testSave() {
		
		StatsHouseEvaEntity statsHouseEvaEntity = new StatsHouseEvaEntity();
		//statsHouseEvaEntity.setBedFid(null);
		statsHouseEvaEntity.setFid(UUIDGenerator.hexUUID());
		statsHouseEvaEntity.setEvaTotal(100);
		statsHouseEvaEntity.setHouseFid("45fsfdfd6dd4f5");
		statsHouseEvaEntity.setRoomFid("45645fdfd6456");
		statsHouseEvaEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		statsHouseEvaEntity.setLandlordUid("fdsfifdfopdsifdpoi");
		statsHouseEvaEntity.setLastModifyDate(new Date());
		statsHouseEvaEntity.setCreateTime(new Date());
		statsHouseEvaEntity.setCostPerforTal(5);
		statsHouseEvaEntity.setDesMathTal(40);
		statsHouseEvaEntity.setEvaFiveTal(4);
		statsHouseEvaEntity.setEvaFourTal(40);
		statsHouseEvaEntity.setHouseCleanTal(4);
		statsHouseEvaEntity.setSafeDegreeTal(40);
		statsHouseEvaEntity.setTrafPosTal(40);
		int total = statsHouseEvaEntity.getEvaTotal();
		DecimalFormat df = new DecimalFormat("0.0");
		statsHouseEvaEntity.setCostPerforAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getCostPerforTal(),df));
		statsHouseEvaEntity.setDesMatchAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getDesMathTal(),df));
		statsHouseEvaEntity.setEvaFiveAvage(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getEvaFiveTal(),df));
		statsHouseEvaEntity.setEvaFourAvage(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getEvaFourTal(),df));
		statsHouseEvaEntity.setHouseCleanAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getHouseCleanTal(),df));
		statsHouseEvaEntity.setSafeDegreeAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getSafeDegreeTal(),df));
		statsHouseEvaEntity.setTrafPosAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getTrafPosTal(),df));
		statsHouseEvaEntity.setDesMatchAva(4.2F);
		
		int index = this.statsHouseEvaServiceImpl.save(statsHouseEvaEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void testUpdateBySelective(){
		
		StatsHouseEvaEntity statsHouseEvaEntity = new StatsHouseEvaEntity();
		statsHouseEvaEntity.setEvaTotal(1000);
		statsHouseEvaEntity.setHouseFid("45fsfdfd6dd4f5");
		statsHouseEvaEntity.setRoomFid("45645fdfd6456");
		statsHouseEvaEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		statsHouseEvaEntity.setLandlordUid("fdsfifdfopdsifdpoi");
		statsHouseEvaEntity.setLastModifyDate(new Date());
		statsHouseEvaEntity.setCreateTime(new Date());
		statsHouseEvaEntity.setCostPerforTal(5);
		statsHouseEvaEntity.setDesMathTal(40);
		statsHouseEvaEntity.setEvaFiveTal(4);
		statsHouseEvaEntity.setEvaFourTal(40);
		statsHouseEvaEntity.setHouseCleanTal(4);
		statsHouseEvaEntity.setSafeDegreeTal(40);
		statsHouseEvaEntity.setTrafPosTal(40);
		int total = statsHouseEvaEntity.getEvaTotal();
		DecimalFormat df = new DecimalFormat("0.0");
		statsHouseEvaEntity.setCostPerforAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getCostPerforTal(),df));
		statsHouseEvaEntity.setDesMatchAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getDesMathTal(),df));
		statsHouseEvaEntity.setEvaFiveAvage(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getEvaFiveTal(),df));
		statsHouseEvaEntity.setEvaFourAvage(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getEvaFourTal(),df));
		statsHouseEvaEntity.setHouseCleanAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getHouseCleanTal(),df));
		statsHouseEvaEntity.setSafeDegreeAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getSafeDegreeTal(),df));
		statsHouseEvaEntity.setTrafPosAva(EvaluateUtils.getFloatValue(total, statsHouseEvaEntity.getTrafPosTal(),df));
		
		statsHouseEvaEntity.setFid("8a9e9c8b53fb47140153fb47145e0000");
		
		this.statsHouseEvaServiceImpl.updateBySelective(statsHouseEvaEntity);
	}

	
	@Test
	public void testQueryByCondition(){
		
		StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
		statsHouseEvaRequest.setHouseFid("45fsfdfd6dd4f5");
		statsHouseEvaRequest.setRentWay(RentWayEnum.HOUSE.getCode());
		List<StatsHouseEvaEntity> listStatsHouseEvaEntities = this.statsHouseEvaServiceImpl.queryByCondition(statsHouseEvaRequest);
		
		System.out.println(listStatsHouseEvaEntities);
	}
	
	@Test
	public void selectByAVEScoreByUid(){
		 String uid="5895899898495fghjfg988f89r99";
		System.out.println(statsHouseEvaServiceImpl.selectByAVEScoreByUid(uid));
	}
	
}
