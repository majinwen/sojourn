/**
 * @FileName: LandlordEvaluateServiceImplTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.service
 * 
 * @author yd
 * @created 2016年4月9日 下午6:53:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.LandlordEvaluateVo;
import com.ziroom.minsu.services.evaluate.service.EvaluateOrderServiceImpl;
import com.ziroom.minsu.services.evaluate.service.LandlordEvaluateServiceImpl;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>房东对房客的评价 服务层测试</p>
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
public class LandlordEvaluateServiceImplTest extends BaseTest{


	@Resource(name = "evaluate.landlordEvaluateServiceImpl")
	private LandlordEvaluateServiceImpl landlordEvaluateServiceImpl;

	@Resource(name = "evaluate.evaluateOrderServiceImpl")
	private EvaluateOrderServiceImpl evaluateOrderServiceImpl;

	@Test
	public void tesQueryLandlordEvaluateByPage() {

		EvaluateRequest evaluateRequest = new EvaluateRequest();
		evaluateRequest.setEvaStatu(4);
		evaluateRequest.setRatedUserUid("fds65dfdfdfdf4d6s54f");
		evaluateRequest.setIsDel(0);
		evaluateRequest.setEvaUserType(1);
		evaluateRequest.setEvaUserUid("f4d5s6dfd5s6f4");
		PagingResult<LandlordEvaluateVo> pagingResult = this.landlordEvaluateServiceImpl.queryLandlordEvaluateByPage(evaluateRequest);
		System.err.println(JsonEntityTransform.Object2Json(pagingResult));
	}

	@Test
	public void testSaveLandlordEvaluate(){
		LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();

		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();

		evaluateOrderEntity.setFid(UUIDGenerator.hexUUID());
		evaluateOrderEntity.setBedFid("bedfid14dfasf64fddfff56456");
		evaluateOrderEntity.setCreateTime(new Date());
		evaluateOrderEntity.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaluateOrderEntity.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
		evaluateOrderEntity.setHouseFid("housefidffddfdf6ds4");
		evaluateOrderEntity.setLastModifyDate(new Date());
		evaluateOrderEntity.setOrderSn("8a9e9cddfdsfdfdfd0b29d0153d0b29d460001");
		evaluateOrderEntity.setRatedUserUid("fds65dfdfdfdf4d6s54f");
		evaluateOrderEntity.setRoomFid("roomfiddfffdsfds56dsa4f56s4f5");
		evaluateOrderEntity.setEvaUserUid("f4d5s6dfd5s6f4");
		evaluateOrderEntity.setRentWay(RentWayEnum.ROOM.getCode());

		landlordEvaluateEntity.setContent("非常好,又感觉差一点点dsafsff");
		landlordEvaluateEntity.setCreateTime(new Date());
		landlordEvaluateEntity.setEvaOrderFid(evaluateOrderEntity.getFid());
		landlordEvaluateEntity.setIsDel(0);
		landlordEvaluateEntity.setLandlordSatisfied(4);
		landlordEvaluateEntity.setLastModifyDate(new Date());
		
		
		this.landlordEvaluateServiceImpl.saveLandlordEvaluate(landlordEvaluateEntity, evaluateOrderEntity);
		
	}
	
	@Test
	public void testUpdateByEvaOrderFid(){
		
		LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();

		landlordEvaluateEntity.setContent("非常好");
		landlordEvaluateEntity.setEvaOrderFid("8a9e9c8b53fabf780153fabf78770000");
		
		int index = this.landlordEvaluateServiceImpl.updateByEvaOrderFid(landlordEvaluateEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void testUpdateByFid(){
		LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();

		landlordEvaluateEntity.setContent("非常好,又感觉差一点点");
		landlordEvaluateEntity.setFid("8a9e9c8b53fac1e00153fac2311f0001");
		
		int index = this.landlordEvaluateServiceImpl.updateByFid(landlordEvaluateEntity);
		
		System.out.println(index);
	}
}

