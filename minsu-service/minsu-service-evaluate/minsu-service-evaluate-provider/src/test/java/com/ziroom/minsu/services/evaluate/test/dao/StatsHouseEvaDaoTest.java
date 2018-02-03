/**
 * @FileName: StatsHouseEvaDaoTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.dao
 * 
 * @author yd
 * @created 2016年4月8日 下午5:28:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.dao;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateOrderDao;
import com.ziroom.minsu.services.evaluate.dao.StatsHouseEvaDao;
import com.ziroom.minsu.services.evaluate.dao.TenantEvaluateDao;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>测试dao层</p>
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
public class StatsHouseEvaDaoTest extends BaseTest{



	@Resource(name = "evaluate.statsHouseEvaDao")
	private StatsHouseEvaDao statsHouseEvaDao;
	
	@Resource(name = "evaluate.evaluateOrderDao")
	private EvaluateOrderDao evaluateOrderDao;
	
	@Resource(name = "evaluate.tenantEvaluateDao")
	private TenantEvaluateDao tenantEvaluateDao;
	

	@Test
	public void testSave() {
		StatsHouseEvaEntity statsHouseEvaEntity = new StatsHouseEvaEntity();
		//statsHouseEvaEntity.setBedFid(null);
		statsHouseEvaEntity.setFid(UUIDGenerator.hexUUID());
		statsHouseEvaEntity.setEvaTotal(100);
		statsHouseEvaEntity.setHouseFid("45fs6ddfdsfdsf4f5");
		statsHouseEvaEntity.setRoomFid("4564564fdsfd56");
		statsHouseEvaEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		statsHouseEvaEntity.setLandlordUid("fdsfiopdsifdfdsfdpoi");
		statsHouseEvaEntity.setLastModifyDate(new Date());
		statsHouseEvaEntity.setCreateTime(new Date());
		statsHouseEvaEntity.setCostPerforTal(50);
		statsHouseEvaEntity.setDesMathTal(40);
		statsHouseEvaEntity.setEvaFiveTal(40);
		statsHouseEvaEntity.setEvaFourTal(40);
		statsHouseEvaEntity.setHouseCleanTal(40);
		statsHouseEvaEntity.setSafeDegreeTal(40);
		statsHouseEvaEntity.setTrafPosTal(40);
		int total = statsHouseEvaEntity.getEvaTotal();
		DecimalFormat df = new DecimalFormat("0.0");
		statsHouseEvaEntity.setCostPerforAva(getFloatValue(total, statsHouseEvaEntity.getCostPerforTal(),df));
		statsHouseEvaEntity.setDesMatchAva(getFloatValue(total, statsHouseEvaEntity.getDesMathTal(),df));
		statsHouseEvaEntity.setEvaFiveAvage(getFloatValue(total, statsHouseEvaEntity.getEvaFiveTal(),df));
		statsHouseEvaEntity.setEvaFourAvage(getFloatValue(total, statsHouseEvaEntity.getEvaFourTal(),df));
		statsHouseEvaEntity.setHouseCleanAva(getFloatValue(total, statsHouseEvaEntity.getHouseCleanTal(),df));
		statsHouseEvaEntity.setSafeDegreeAva(getFloatValue(total, statsHouseEvaEntity.getSafeDegreeTal(),df));
		statsHouseEvaEntity.setTrafPosAva(getFloatValue(total, statsHouseEvaEntity.getTrafPosTal(),df));
		statsHouseEvaEntity.setDesMatchAva(4.2F);
		
		int index = this.statsHouseEvaDao.save(statsHouseEvaEntity);
		
		System.out.println(index);
	}

	/**
	 * 
	 * 获取float值
	 *
	 * @author yd
	 * @created 2016年4月8日 下午5:43:58
	 *
	 * @param total
	 * @param oneTal
	 * @return
	 */
	private Float getFloatValue(int total,int oneTal,DecimalFormat df){
		if(df == null) df=new DecimalFormat("0.0");
		if(total>0){
			return Float.parseFloat(df.format(oneTal/(float)total));
		}
		return 0f;
	}
	
	@Test
	public void testQueryByCondition() {
		
		StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
		statsHouseEvaRequest.setHouseFid("45fs6dd4fdfdf5");
		statsHouseEvaRequest.setRentWay(RentWayEnum.HOUSE.getCode());
		List<StatsHouseEvaEntity> listStatsHouseEvaEntities = 	 this.statsHouseEvaDao.queryByCondition(statsHouseEvaRequest);
		if(!Check.NuNCollection(listStatsHouseEvaEntities)){
			System.out.println(listStatsHouseEvaEntities.toString());
		}
	}

	@Test
	public void testUpdateBySelective() {
		
		StatsHouseEvaEntity statsHouseEvaEntity = new StatsHouseEvaEntity();
		
		statsHouseEvaEntity.setEvaTotal(10);
		statsHouseEvaEntity.setHouseFid("45fs6ddfdsf4f5");
		statsHouseEvaEntity.setRoomFid("4564fdsf56456");
		statsHouseEvaEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		statsHouseEvaEntity.setLandlordUid("fdsfiopdfdsfsifdpoi");
		statsHouseEvaEntity.setLastModifyDate(new Date());
		statsHouseEvaEntity.setCostPerforTal(50);
		statsHouseEvaEntity.setDesMathTal(40);
		statsHouseEvaEntity.setEvaFiveTal(40);
		statsHouseEvaEntity.setEvaFourTal(40);
		statsHouseEvaEntity.setHouseCleanTal(40);
		statsHouseEvaEntity.setSafeDegreeTal(40);
		statsHouseEvaEntity.setTrafPosTal(40);
		int total = statsHouseEvaEntity.getEvaTotal();
		DecimalFormat df = new DecimalFormat("0.0");
		statsHouseEvaEntity.setCostPerforAva(getFloatValue(total, statsHouseEvaEntity.getCostPerforTal(),df));
		statsHouseEvaEntity.setDesMatchAva(getFloatValue(total, statsHouseEvaEntity.getDesMathTal(),df));
		statsHouseEvaEntity.setEvaFiveAvage(getFloatValue(total, statsHouseEvaEntity.getEvaFiveTal(),df));
		statsHouseEvaEntity.setEvaFourAvage(getFloatValue(total, statsHouseEvaEntity.getEvaFourTal(),df));
		statsHouseEvaEntity.setHouseCleanAva(getFloatValue(total, statsHouseEvaEntity.getHouseCleanTal(),df));
		statsHouseEvaEntity.setSafeDegreeAva(getFloatValue(total, statsHouseEvaEntity.getSafeDegreeTal(),df));
		statsHouseEvaEntity.setTrafPosAva(getFloatValue(total, statsHouseEvaEntity.getTrafPosTal(),df));
		int index = this.statsHouseEvaDao.updateBySelective(statsHouseEvaEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void testSaveOrUpdateStatsHouseEva(){
		
		TenantEvaluateEntity tenantEvaluateEntity = new TenantEvaluateEntity();
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		
		evaluateOrderEntity.setFid(UUIDGenerator.hexUUID());
		evaluateOrderEntity.setBedFid("bedfid1456456fdsfd45dfasf64fdf56456");
		evaluateOrderEntity.setCreateTime(new Date());
		evaluateOrderEntity.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaluateOrderEntity.setEvaUserType(UserTypeEnum.TENANT.getUserType());
		evaluateOrderEntity.setHouseFid("housefidffddsfsdfdsfdf4f56ds4");
		evaluateOrderEntity.setLastModifyDate(new Date());
		evaluateOrderEntity.setOrderSn("8a9e9cd2dfd53dfdsfdsfdsfd0b29d0153d0b29d460001");
		evaluateOrderEntity.setRatedUserUid("fds65dfdf4fdsfd6s54f");
		evaluateOrderEntity.setRoomFid("roomfid4fdfffdfdsfdsfds56dsa4f56s4f5");
		evaluateOrderEntity.setEvaUserUid("f4d5sfdsfds6f4d5s6f4");
		evaluateOrderEntity.setRentWay(RentWayEnum.ROOM.getCode());
		
		int index = this.evaluateOrderDao.saveEvaluateOrder(evaluateOrderEntity);
		
		if(index>0){
			tenantEvaluateEntity.setContent("非常好ffdsfdsdsafd");
			tenantEvaluateEntity.setCostPerformance(4);
			tenantEvaluateEntity.setCreateTime(new Date());
			tenantEvaluateEntity.setDescriptionMatch(4);
			tenantEvaluateEntity.setEvaFive(4);
			tenantEvaluateEntity.setEvaFour(4);
			tenantEvaluateEntity.setEvaOrderFid(evaluateOrderEntity.getFid());
			tenantEvaluateEntity.setHouseClean(4);
			tenantEvaluateEntity.setIsDel(0);
			tenantEvaluateEntity.setLastModifyDate(new Date());
			tenantEvaluateEntity.setSafetyDegree(4);
			tenantEvaluateEntity.setTrafficPosition(4);
			index = this.tenantEvaluateDao.saveTentantEvaluate(tenantEvaluateEntity);
			if(index>0){
				this.statsHouseEvaDao.saveOrUpdateStatsHouseEva(tenantEvaluateEntity, evaluateOrderEntity);
			}
			
		}
		
	}
	
	
	@Test
	public void selectByAVEScoreByUid(){
		
		String uidString="aa3b72ed-3b93-4018-93f4-880ec4d7096b";		
		float score = statsHouseEvaDao.selectByAVEScoreByUid(uidString);		
		System.err.println(score);
		
		
		
	}
	
	

}
