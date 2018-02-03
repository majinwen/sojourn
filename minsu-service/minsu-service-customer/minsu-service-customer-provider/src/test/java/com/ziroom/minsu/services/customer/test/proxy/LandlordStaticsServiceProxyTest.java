package com.ziroom.minsu.services.customer.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.LandlordStatisticsEntity;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.dto.StaticsCusBaseReqDto;
import com.ziroom.minsu.services.customer.proxy.LandlordStaticsServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;

public class LandlordStaticsServiceProxyTest extends BaseTest {
	
	@Resource(name = "customer.landlordStaticsServiceProxy")
	private LandlordStaticsServiceProxy landlordStaticsServiceProxy;
	
	@Test
	public void staticsInsertLanActAssociationImp(){
		LandlordStatisticsEntity entity = new LandlordStatisticsEntity();
		entity.setImReplySumTime(500);
		entity.setLandlordUid("8a9e9a9f544b372101544b3721de0000");
		entity.setLanRefuseOrderNum(45);
		entity.setOrderReplySumTime(45);
		entity.setReplyOrderNum(45);
		entity.setReplyPeopleNum(45);
		entity.setSysRefuseOrderNum(45);
		entity.setOrderSumNum(90);
		String result = landlordStaticsServiceProxy.staticsInsertLanActAssociationImp(JsonEntityTransform.Object2Json(entity));
		System.err.println("result:"+result);
		System.err.println("result:"+result);
	}

	
	@Test
	public void staticsUpdateLanActAssociationImp(){
		LandlordStatisticsEntity entity = new LandlordStatisticsEntity();
		entity.setImReplySumTime(500);
		entity.setLandlordUid("8a9e9a9f544b372101544b3721de0000");
		entity.setLanRefuseOrderNum(450);
		entity.setOrderReplySumTime(450);
		entity.setReplyOrderNum(450);
		entity.setReplyPeopleNum(450);
		entity.setSysRefuseOrderNum(450);
		entity.setOrderSumNum(89);
		String result = landlordStaticsServiceProxy.staticsUpdateLanActAssociationImp(JsonEntityTransform.Object2Json(entity));
		System.err.println("result:"+result);
		System.err.println("result:"+result);
	}

	@Test
	public void findLandlordStatisticsByUid(){
		
	    String result = landlordStaticsServiceProxy.findLandlordStatisticsByUid("8a9e9a9f544b372101544b3721de0000");
		System.err.println("result:"+result);
		System.err.println("result:"+result);
	}
	
	
}
