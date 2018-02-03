/**
 * @FileName: StatsTenantEvaDaoTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.dao
 * 
 * @author yd
 * @created 2016年4月8日 下午6:28:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.StatsTenantEvaEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateOrderDao;
import com.ziroom.minsu.services.evaluate.dao.LandlordEvaluateDao;
import com.ziroom.minsu.services.evaluate.dao.StatsTenantEvaDao;
import com.ziroom.minsu.services.evaluate.dto.StatsTenantEvaRequest;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

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
public class StatsTenantEvaDaoTest extends BaseTest{

	@Resource(name = "evaluate.statsTenantEvaDao")
	private StatsTenantEvaDao statsTenantEvaDao;
	
	@Resource(name = "evaluate.evaluateOrderDao")
	private EvaluateOrderDao evaluateOrderDao;
	
	@Resource(name = "evaluate.landlordEvaluateDao")
	private LandlordEvaluateDao landlordEvaluateDao;
	@Test
	public void testSave() {

		StatsTenantEvaEntity statsTenantEvaEntity = new StatsTenantEvaEntity();

		statsTenantEvaEntity.setCreateTime(new Date());
		statsTenantEvaEntity.setFid(UUIDGenerator.hexUUID());
		statsTenantEvaEntity.setEvaTotal(30);
		statsTenantEvaEntity.setLandSatisfTal(20);
		statsTenantEvaEntity.setLastModifyDate(new Date());
		statsTenantEvaEntity.setTenantUid("fds465f4d56");
		int total = statsTenantEvaEntity.getEvaTotal();
		statsTenantEvaEntity.setLandSatisfAva(getFloatValue(total, statsTenantEvaEntity.getLandSatisfTal(),null));

		int index = this.statsTenantEvaDao.save(statsTenantEvaEntity);

		System.out.println(index);
	}

	@Test
	public void  testUpdateBySelective(){

		StatsTenantEvaEntity statsTenantEvaEntity = new StatsTenantEvaEntity();

		statsTenantEvaEntity.setEvaTotal(1);
		statsTenantEvaEntity.setLandSatisfTal(1);
		statsTenantEvaEntity.setLastModifyDate(new Date());
		int total = statsTenantEvaEntity.getEvaTotal();
		statsTenantEvaEntity.setTenantUid("fds465f4d5fadf6");
		statsTenantEvaEntity.setLandSatisfAva(getFloatValue(total, statsTenantEvaEntity.getLandSatisfTal(),null));

		int index = this.statsTenantEvaDao.updateBySelective(statsTenantEvaEntity);

		System.out.println(index);
	}

	@Test
	public void  testQueryByCondition(){

		StatsTenantEvaRequest statsTenantEvaRequest = new StatsTenantEvaRequest();
		statsTenantEvaRequest.setTenantUid("fds465f4d5fadf6");
		List<StatsTenantEvaEntity> listTenantEvaEntities = this.statsTenantEvaDao.queryByCondition(statsTenantEvaRequest);
		if(!Check.NuNCollection(listTenantEvaEntities)){
			System.out.println(listTenantEvaEntities.toString());
		}

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
	public void testSaveOrUpdateStatsTenantEva(){
		
		
		LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();
		
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		
		evaluateOrderEntity.setFid(UUIDGenerator.hexUUID());
		evaluateOrderEntity.setBedFid("bedfid145645645dfasf64fddfff56456");
		evaluateOrderEntity.setCreateTime(new Date());
		evaluateOrderEntity.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaluateOrderEntity.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
		evaluateOrderEntity.setHouseFid("housefidffddsfsdf4f5fdf6ds4");
		evaluateOrderEntity.setLastModifyDate(new Date());
		evaluateOrderEntity.setOrderSn("8a9e9cd2dfd53dfdsfdfdfd0b29d0153d0b29d460001");
		evaluateOrderEntity.setRatedUserUid("fds65dfdfdfdfdf4d6s54f");
		evaluateOrderEntity.setRoomFid("roomfid4ffdfdfdfffdsfds56dsa4f56s4f5");
		evaluateOrderEntity.setEvaUserUid("f4d5s6f4fdfdfd5s6f4");
		evaluateOrderEntity.setRentWay(RentWayEnum.ROOM.getCode());
		
		int index = this.evaluateOrderDao.saveEvaluateOrder(evaluateOrderEntity);
		
		if(index>0){
			landlordEvaluateEntity.setContent("非常好,又感觉差一点点dsafsff");
			landlordEvaluateEntity.setCreateTime(new Date());
			landlordEvaluateEntity.setEvaOrderFid(evaluateOrderEntity.getFid());
			landlordEvaluateEntity.setIsDel(0);
			landlordEvaluateEntity.setLandlordSatisfied(4);
			landlordEvaluateEntity.setLastModifyDate(new Date());
		    index = this.landlordEvaluateDao.saveLandlordEvaluate(landlordEvaluateEntity);
		    if(index>0){
		    	this.statsTenantEvaDao.saveOrUpdateStatsTenantEva(landlordEvaluateEntity, evaluateOrderEntity);
		    }
		}
		
		
	}
}
