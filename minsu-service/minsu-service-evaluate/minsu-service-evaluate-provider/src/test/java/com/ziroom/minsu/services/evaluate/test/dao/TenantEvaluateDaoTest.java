/**
 * @FileName: TenantEvaluateDaoTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.dao
 * 
 * @author yd
 * @created 2016年4月7日 下午4:11:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dao.TenantEvaluateDao;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;

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
public class TenantEvaluateDaoTest extends BaseTest{

	@Resource(name="evaluate.tenantEvaluateDao")
	private TenantEvaluateDao tenantEvaluateDao;
	@Test
	public void testSaveTentantEvaluate() {
		
		TenantEvaluateEntity tenantEvaluateEntity = new TenantEvaluateEntity();
		tenantEvaluateEntity.setContent("非常好");
		tenantEvaluateEntity.setCostPerformance(4);
		tenantEvaluateEntity.setCreateTime(new Date());
		tenantEvaluateEntity.setDescriptionMatch(4);
		tenantEvaluateEntity.setEvaFive(4);
		tenantEvaluateEntity.setEvaFour(4);
		tenantEvaluateEntity.setEvaOrderFid("8a9e9c8b53fa671b0153fa671b240000");
		tenantEvaluateEntity.setHouseClean(4);
		tenantEvaluateEntity.setIsDel(0);
		tenantEvaluateEntity.setLastModifyDate(new Date());
		tenantEvaluateEntity.setSafetyDegree(4);
		tenantEvaluateEntity.setTrafficPosition(4);
		
		int index = this.tenantEvaluateDao.saveTentantEvaluate(tenantEvaluateEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void testUpdateByEvaOrderFid(){

		TenantEvaluateEntity tenantEvaluateEntity = new TenantEvaluateEntity();
		tenantEvaluateEntity.setContent("非常好");
		tenantEvaluateEntity.setCostPerformance(5);
		tenantEvaluateEntity.setDescriptionMatch(5);
		tenantEvaluateEntity.setEvaFive(4);
		tenantEvaluateEntity.setEvaFour(4);
		tenantEvaluateEntity.setEvaOrderFid("8a9e9c8b53fa671b0153fa671b240000");
		tenantEvaluateEntity.setHouseClean(4);
		tenantEvaluateEntity.setIsDel(1);
		tenantEvaluateEntity.setLastModifyDate(new Date());
		tenantEvaluateEntity.setSafetyDegree(4);
		tenantEvaluateEntity.setTrafficPosition(4);
		
		int index = this.tenantEvaluateDao.updateByEvaOrderFid(tenantEvaluateEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void testUpdateByFid(){
		TenantEvaluateEntity tenantEvaluateEntity = new TenantEvaluateEntity();
		tenantEvaluateEntity.setContent("非常好");
		tenantEvaluateEntity.setCostPerformance(4);
		tenantEvaluateEntity.setDescriptionMatch(4);
		tenantEvaluateEntity.setEvaFive(4);
		tenantEvaluateEntity.setEvaFour(4);
		tenantEvaluateEntity.setFid("4f56ds4f56ds4f56");
		tenantEvaluateEntity.setHouseClean(4);
		tenantEvaluateEntity.setIsDel(0);
		tenantEvaluateEntity.setLastModifyDate(new Date());
		tenantEvaluateEntity.setSafetyDegree(4);
		tenantEvaluateEntity.setTrafficPosition(4);
		
		int index = this.tenantEvaluateDao.updateByFid(tenantEvaluateEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void testQueryByEvaOrderFid(){
		
		String evaOrderFid = "b65fd8c158d39b176hfujd39b1e74098";
		TenantEvaluateEntity tenantEvaluateEntity = this.tenantEvaluateDao.queryByEvaOrderFid(evaOrderFid);
		
		System.out.println(tenantEvaluateEntity);
		
	}

	@Test
	public void testlistTenEvaByEvaOrderFids(){
		List<String> list = new ArrayList<>();
		list.add("8a90a2d45a60ddda015a652a04f210d9");
		tenantEvaluateDao.listTenEvaByEvaOrderFids(list);
	}

}
