/**
 * @FileName: TenantEvaluateServiceImplTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.service
 * 
 * @author yd
 * @created 2016年4月9日 下午9:30:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.service.TenantEvaluateServiceImpl;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>房客评价服务层测试</p>
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
public class TenantEvaluateServiceImplTest extends BaseTest{

	
	@Resource(name = "evaluate.tenantEvaluateServiceImpl")
	private TenantEvaluateServiceImpl tenantEvaluateServiceImpl;
	@Test
	public void testSaveTenantEvaluate() {
		
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();

		evaluateOrderEntity.setFid(UUIDGenerator.hexUUID());
		evaluateOrderEntity.setBedFid("bedfid16fvdfd4fddfff56456");
		evaluateOrderEntity.setCreateTime(new Date());
		evaluateOrderEntity.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaluateOrderEntity.setEvaUserType(UserTypeEnum.TENANT.getUserType());
		evaluateOrderEntity.setHouseFid("housefidffddsfsfdsfdf4f5fdf6ds4");
		evaluateOrderEntity.setLastModifyDate(new Date());
		evaluateOrderEntity.setOrderSn("8a9efdfdfdfd0b29d0153d0b29d460001");
		evaluateOrderEntity.setRatedUserUid("fds65dfdfdfdffdfdf4d6s54f");
		evaluateOrderEntity.setRoomFid("roomfid4fffdfddfdfdfffdsfds56dsa4f56s4f5");
		evaluateOrderEntity.setEvaUserUid("f4d5s6f4fdfdfd5s6f4");
		evaluateOrderEntity.setRentWay(RentWayEnum.ROOM.getCode());
		
		TenantEvaluateEntity tenantEvaluateEntity = new TenantEvaluateEntity();
		tenantEvaluateEntity.setContent("非常好");
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
		
		this.tenantEvaluateServiceImpl.saveTenantEvaluate(tenantEvaluateEntity, evaluateOrderEntity);
		
	
	}
	
	
	@Test
	public void testUpdateByEvaOrderFid(){
		TenantEvaluateEntity tenantEvaluateEntity = new TenantEvaluateEntity();
		tenantEvaluateEntity.setContent("非常好fdsafsdfdsfds");
		tenantEvaluateEntity.setCostPerformance(5);
		tenantEvaluateEntity.setDescriptionMatch(5);
		tenantEvaluateEntity.setEvaFive(4);
		tenantEvaluateEntity.setEvaFour(4);
		tenantEvaluateEntity.setEvaOrderFid("8a9e9c8b53fb3f920153fb3f92190000");
		tenantEvaluateEntity.setHouseClean(4);
		tenantEvaluateEntity.setIsDel(0);
		tenantEvaluateEntity.setLastModifyDate(new Date());
		tenantEvaluateEntity.setSafetyDegree(4);
		tenantEvaluateEntity.setTrafficPosition(4);
		int index = this.tenantEvaluateServiceImpl.updateByEvaOrderFid(tenantEvaluateEntity);
		
		System.out.println(index);
	}

}
