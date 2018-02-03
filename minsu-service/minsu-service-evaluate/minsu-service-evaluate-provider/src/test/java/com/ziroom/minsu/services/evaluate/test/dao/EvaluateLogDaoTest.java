/**
 * @FileName: EvaluateLogDaoTest.java
 * @Package com.ziroom.minsu.services.evaluate.test
 * 
 * @author yd
 * @created 2016年4月7日 下午8:22:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.evaluate.EvaluateLogEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateLogDao;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;

/**
 * <p>日志测试</p>
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
public class EvaluateLogDaoTest extends BaseTest{

	
	@Resource(name = "evaluate.evaluateLogDao")
	private EvaluateLogDao evaluateLogDao;
	@Test
	public void testSaveEvaluateOrder() {
		
		EvaluateLogEntity evaluateLogEntity = new EvaluateLogEntity();
		evaluateLogEntity.setCreateTime(new Date());
		evaluateLogEntity.setCreateUid("dafdfafddfds4564fd");
		evaluateLogEntity.setEvaOrderFid("48f5ds64f5d");
		evaluateLogEntity.setFromStatus(2);
		evaluateLogEntity.setIsDel(0);
		evaluateLogEntity.setRemark("fdasf456sdf4d65sf4");
		evaluateLogEntity.setToStatus(3);
		int index = this.evaluateLogDao.saveEvaluateOrder(evaluateLogEntity);
		
		System.out.println(index);
	}

}
