/**
 * @FileName: EvaluateLogServiceImplTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.service
 * 
 * @author yd
 * @created 2016年4月8日 上午10:28:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.evaluate.EvaluateLogEntity;
import com.ziroom.minsu.services.evaluate.service.EvaluateLogServiceImpl;
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
public class EvaluateLogServiceImplTest extends BaseTest{

	
	@Resource(name = "evaluate.evaluateLogServiceImpl")
	private EvaluateLogServiceImpl evaluateLogServiceImpl;
	@Test
	public void testSaveEvaluateOrder() {
		
		EvaluateLogEntity evaluateLogEntity = new EvaluateLogEntity();
		evaluateLogEntity.setCreateTime(new Date());
		evaluateLogEntity.setCreateUid("dafdfafdfdsff4564fd");
		evaluateLogEntity.setEvaOrderFid("48f5ds64f5d");
		evaluateLogEntity.setFromStatus(3);
		evaluateLogEntity.setIsDel(0);
		evaluateLogEntity.setRemark("hahahhhfdhahfdshfdf");
		evaluateLogEntity.setToStatus(4);
		int index = this.evaluateLogServiceImpl.saveEvaluateOrder(evaluateLogEntity);
		
		System.out.println(index);
		
	}

}
