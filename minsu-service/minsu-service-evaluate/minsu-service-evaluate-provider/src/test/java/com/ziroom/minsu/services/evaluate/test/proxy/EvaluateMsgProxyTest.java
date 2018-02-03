/**
 * @FileName: EvaluateOrderProxyTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.proxy
 * 
 * @author yd
 * @created 2016年4月11日 下午4:36:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.evaluate.dto.EvaluatePCRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.proxy.EvaluateMsgProxy;
import com.ziroom.minsu.services.evaluate.proxy.EvaluateOrderProxy;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

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
public class EvaluateMsgProxyTest extends BaseTest{



	@Resource(name = "evaluate.evaluateMsgProxy")
	private EvaluateMsgProxy evaluateMsgProxy;
	@Test
	public void testsendMsgToLanAndTenFinish() {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn("170215QM3MAA9N142929");
		orderEntity.setLandlordTel("18611540773");
		orderEntity.setLandlordUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		orderEntity.setUserName("哦广告1");

		evaluateMsgProxy.sendMsgToLanAndTenFinish(orderEntity);

	}



}
