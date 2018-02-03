/**
 * @FileName: EvaluateOrderServiceImplTest.java
 * @Package com.ziroom.minsu.services.evaluate.test.service
 * 
 * @author yd
 * @created 2016年4月9日 下午5:39:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.dto.EvaluateOrderRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.EvaluateVo;
import com.ziroom.minsu.services.evaluate.entity.LandlordEvaluateVo;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.services.evaluate.service.EvaluateOrderServiceImpl;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvaFlagEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

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
public class EvaluateOrderServiceImplTest extends BaseTest{

	@Resource(name = "evaluate.evaluateOrderServiceImpl")
	private EvaluateOrderServiceImpl evaluateOrderServiceImpl;
	@Test
	public void testSaveEvaluateOrder() {

		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setBedFid("bedfid145645645dfasfdsf6456456");
		evaluateOrderEntity.setCreateTime(new Date());
		evaluateOrderEntity.setEvaStatu(1);
		evaluateOrderEntity.setEvaUserType(2);
		evaluateOrderEntity.setHouseFid("housefidfdsfsdf4f56ds4");
		evaluateOrderEntity.setLastModifyDate(new Date());
		evaluateOrderEntity.setOrderSn("8a9e9cd253dfdsfd0b29d0153d0b29d460001");
		evaluateOrderEntity.setRatedUserUid("fds65f4d6s54f");
		evaluateOrderEntity.setRoomFid("roomfid4ffdsfds56dsa4f56s4f5");
		evaluateOrderEntity.setEvaUserUid("f4d5s6f4d5s6f4");
		evaluateOrderEntity.setRentWay(RentWayEnum.ROOM.getCode());

		int index = 	this.evaluateOrderServiceImpl.saveEvaluateOrder(evaluateOrderEntity);

		System.out.println(index);
	}

	@Test
	public void testUpdateEvaluateOrderByFid(){

		EvaluateOrderRequest evaluateOrde = new EvaluateOrderRequest();
		evaluateOrde.setFid("0000000054c58a560154c59a89af0002");
		evaluateOrde.setCreateUid("fds46f5sd4f56");
		evaluateOrde.setRemark("状态修改为上线");
		evaluateOrde.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		EvaluateOrderEntity evaluateOrderEntity = this.evaluateOrderServiceImpl.queryByFid(evaluateOrde.getFid());
		int index = this.evaluateOrderServiceImpl.updateEvaluateOrderByFid(evaluateOrde,evaluateOrderEntity);

		System.out.println(index);

	}

	@Test
	public void testQueryLandlordEvaluateByPage(){

		EvaluateRequest evaluateRequest = new EvaluateRequest();
		//evaluateRequest.setEvaStatu(3);
		evaluateRequest.setRatedUserUid("fds65f4d6s54f");
		evaluateRequest.setIsDel(0);
		evaluateRequest.setEvaUserType(1);
		PagingResult<LandlordEvaluateVo> pagingResult =this.evaluateOrderServiceImpl.queryLandlordEvaluateByPage(evaluateRequest);
		
		System.out.println(pagingResult);
	}


	@Test
	public void testQueryTenantEvaluateByPage(){
		EvaluateRequest evaluateRequest = new EvaluateRequest();

		//evaluateRequest.setEvaStatu(3);
		evaluateRequest.setEvaUserUid("f4d5s6f4d5s6f4");
		evaluateRequest.setIsDel(0);
		evaluateRequest.setEvaUserType(2);

		PagingResult<TenantEvaluateVo> pagingResult = this.evaluateOrderServiceImpl.queryTenantEvaluateByPage(evaluateRequest);

		System.out.println(pagingResult);
	}
	
	@Test
	public void testQueryByOrderSn(){
		EvaluateRequest evaluateRequest = new EvaluateRequest();
		evaluateRequest.setOrderSn("17020659Z5JU59144022");
		evaluateRequest.setEvaUserType(UserTypeEnum.All.getUserType());
		evaluateRequest.setEvaUserUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		Map<String, Object> evaluateMap = this.evaluateOrderServiceImpl.queryEvaluateByOrderSn(evaluateRequest);
 
		
		System.err.println(JsonEntityTransform.Object2Json(evaluateMap));
		
	}
	
	@Test
	public void testQueryAllEvaluateByPage(){

		EvaluateRequest evaluateRequest = new EvaluateRequest();

		//evaluateRequest.setEvaStatu(3);
		evaluateRequest.setEvaUserUid("fds45f6ds56f4");
		evaluateRequest.setEvaUserType(2);

		PagingResult<EvaluateVo> pagingResult = this.evaluateOrderServiceImpl.queryAllEvaluateByPage(evaluateRequest);
		
		System.out.println(pagingResult);
	
	}
	@Test
	public void testUpdateBycondition(){
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setEvaStatu(4);
		evaluateOrderEntity.setOrderSn("8a9e9cd2dfd53dfdsfdfdfd0b29d0153d0b29d460001");;
		evaluateOrderEntity.setOrderEvaFlag(1);
		evaluateOrderEntity.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());

		int index = this.evaluateOrderServiceImpl.updateBycondition(evaluateOrderEntity);

		System.out.println(index);
	}
	
	@Test
	public void testQueryByCondition(){
		
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
		List<EvaluateOrderEntity> listOrderEntities = this.evaluateOrderServiceImpl.queryByCondition(evaluateOrderEntity);
		
		System.out.println(listOrderEntities);
	}
	
	@Test
	public void testUpdateOrderEvaFlag(){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderEvaFlag", 1);
		List<String> listOrderSn = new ArrayList<String>();
		
		listOrderSn.add("8a9e9cd2dfd53dfdsfdfdfd0b29d0153d0b29d460001");
		params.put("evaUserType", UserTypeEnum.LANDLORD.getUserType());
		
		params.put("listOrderSn", listOrderSn);
		
		int index = this.evaluateOrderServiceImpl.updateOrderEvaFlag(params);
		
		System.out.println(index);
	}
}
