/**
 * @FileName: StatsTenantEvaServiceImplTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.service
 * 
 * @author yd
 * @created 2016年4月9日 下午9:57:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.evaluate.StatsTenantEvaEntity;
import com.ziroom.minsu.services.evaluate.dto.StatsTenantEvaRequest;
import com.ziroom.minsu.services.evaluate.service.StatsTenantEvaServiceImpl;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.services.evaluate.utils.EvaluateUtils;

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
public class StatsTenantEvaServiceImplTest  extends BaseTest{

	@Resource(name = "evaluate.statsTenantEvaServiceImpl")
	private StatsTenantEvaServiceImpl statsTenantEvaServiceImpl;
	@Test
	public void testSave() {
		
		StatsTenantEvaEntity statsTenantEvaEntity = new StatsTenantEvaEntity();

		statsTenantEvaEntity.setCreateTime(new Date());
		statsTenantEvaEntity.setFid(UUIDGenerator.hexUUID());
		statsTenantEvaEntity.setEvaTotal(30);
		statsTenantEvaEntity.setLandSatisfTal(20);
		statsTenantEvaEntity.setLastModifyDate(new Date());
		statsTenantEvaEntity.setTenantUid("fds465ffdfdf4d56");
		int total = statsTenantEvaEntity.getEvaTotal();
		statsTenantEvaEntity.setLandSatisfAva(EvaluateUtils.getFloatValue(total, statsTenantEvaEntity.getLandSatisfTal(),null));
		this.statsTenantEvaServiceImpl.save(statsTenantEvaEntity);
	}
	
	@Test
	public void testUpdateBySelective(){
		
		StatsTenantEvaEntity statsTenantEvaEntity = new StatsTenantEvaEntity();

		statsTenantEvaEntity.setFid("8a9e9c8b53fb54020153fb5402450000");
		statsTenantEvaEntity.setEvaTotal(100);
		statsTenantEvaEntity.setLandSatisfTal(10);
		statsTenantEvaEntity.setLastModifyDate(new Date());
		int total = statsTenantEvaEntity.getEvaTotal();
		statsTenantEvaEntity.setTenantUid("fds465ffdfdf4d56");
		statsTenantEvaEntity.setLandSatisfAva(EvaluateUtils.getFloatValue(total, statsTenantEvaEntity.getLandSatisfTal(),null));
		this.statsTenantEvaServiceImpl.updateBySelective(statsTenantEvaEntity);
	}
	
	@Test
	public void testqueryByCondition(){
		
		
		StatsTenantEvaRequest statsTenantEvaRequest = new StatsTenantEvaRequest();
		statsTenantEvaRequest.setTenantUid("fds465ffdfdf4d56");
		List<StatsTenantEvaEntity> listTenantEvaEntities = this.statsTenantEvaServiceImpl.queryByCondition(statsTenantEvaRequest);
		
		System.out.println(listTenantEvaEntities);
	}

}
