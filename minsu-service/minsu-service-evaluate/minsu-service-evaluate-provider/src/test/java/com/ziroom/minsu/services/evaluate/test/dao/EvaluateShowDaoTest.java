/**
 * @FileName: EvaluateOrderDaoTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.dao
 * 
 * @author yd
 * @created 2016年4月7日 下午1:24:52
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateShowEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateOrderDao;
import com.ziroom.minsu.services.evaluate.dao.EvaluateShowDao;
import com.ziroom.minsu.services.evaluate.dto.EvaluatePCRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.*;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvaFlagEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.*;

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
 * 
 */
public class EvaluateShowDaoTest extends BaseTest{


	@Resource(name="evaluate.evaluateShowDao")
	private EvaluateShowDao evaluateShowDao;
	@Test
	public void testSave() {
		EvaluateShowEntity evaluateShowEntity = new EvaluateShowEntity();
		evaluateShowEntity.setEvaOrderFid("dsfsdfsdfsd");
		evaluateShowDao.saveEntity(evaluateShowEntity);

	}

}
