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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.evaluate.entity.*;
import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.services.evaluate.dao.EvaluateOrderDao;
import com.ziroom.minsu.services.evaluate.dto.EvaluatePCRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
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
 * 
 */
public class EvaluateOrderDaoTest extends BaseTest{


	@Resource(name="evaluate.evaluateOrderDao")
	private EvaluateOrderDao evaluateOrderDao;
	@Test
	public void testSaveEvaluateOrder() {

		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setBedFid("bedfid1范德萨发的说法45645645dddfasf6456456");
		evaluateOrderEntity.setCreateTime(new Date());
		evaluateOrderEntity.setEvaStatu(3);
		evaluateOrderEntity.setEvaUserType(2);
		evaluateOrderEntity.setHouseFid("housefidfdsfsdf4f56ds4");
		evaluateOrderEntity.setLastModifyDate(new Date());
		evaluateOrderEntity.setOrderSn("8a9e9fdscd253df7d89789d0b29d460001");
		evaluateOrderEntity.setRatedUserUid("fds65df4d6s54f");
		evaluateOrderEntity.setRoomFid("roomfid4ffdsdfds56dsa4f56s4f5");
		evaluateOrderEntity.setEvaUserUid("f4d5s6f4d5s6f4");
		evaluateOrderEntity.setRentWay(RentWayEnum.ROOM.getCode());

		int index = this.evaluateOrderDao.saveEvaluateOrder(evaluateOrderEntity);

		System.out.println(index);
	}

	@Test
	public void testQueryLandlordEvaluateByPage(){

		EvaluateRequest evaluateRequest = new EvaluateRequest();

		//evaluateRequest.setEvaStatu(3);
		evaluateRequest.setRatedUserUid("fds65f4d6s54f");
		evaluateRequest.setIsDel(0);
		evaluateRequest.setEvaUserType(1);

		PagingResult<LandlordEvaluateVo> pagingResult = this.evaluateOrderDao.queryLandlordEvaluateByPage(evaluateRequest);

		if(!Check.NuNObjs(pagingResult)){
			System.out.println(pagingResult.toString());
		}
	}
	
	@Test
	public void testQueryOtherLanEvaByPage(){

		EvaluateRequest evaluateRequest = new EvaluateRequest();

		//evaluateRequest.setEvaStatu(3);
		evaluateRequest.setEvaUserUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");

		PagingResult<LandlordEvaluateVo> pagingResult = this.evaluateOrderDao.queryOtherLanEvaByPage(evaluateRequest);

		if(!Check.NuNObjs(pagingResult)){
			System.out.println(pagingResult.toString());
		}
	}
	
	

	@Test
	public void testQueryTenantEvaluateByPage(){

		EvaluateRequest evaluateRequest = new EvaluateRequest();

		//evaluateRequest.setEvaStatu(3);
		evaluateRequest.setEvaUserUid("fds45f6ds56f4");
		evaluateRequest.setIsDel(0);
		evaluateRequest.setEvaUserType(2);

		PagingResult<TenantEvaluateVo> pagingResult = this.evaluateOrderDao.queryTenantEvaluateByPage(evaluateRequest);

		if(!Check.NuNObjs(pagingResult)){
			System.out.println(pagingResult.toString());
		}
	}

	@Test
	public void testUpdateByFid(){
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setEvaStatu(3);
		evaluateOrderEntity.setFid("8a9e9cb353f199490153f19949ba0000");
		evaluateOrderEntity.setLastModifyDate(new Date());

		int index = this.evaluateOrderDao.updateByFid(evaluateOrderEntity);

		System.out.println(index);
	}

	@Test
	public void testQueryByFid(){

		EvaluateOrderEntity evaluateOrderEntity = this.evaluateOrderDao.queryByFid("8a9e9cb353f199490153f19949ba0000");
		System.out.println(evaluateOrderEntity);

	}

	@Test
	public void testQueryByOrderSn(){
		EvaluateRequest evaluateRequest = new EvaluateRequest();
		evaluateRequest.setOrderSn("17020659Z5JU59144022");
		List<EvaluateOrderEntity> listOrderEntities = this.evaluateOrderDao.queryByOrderSn(evaluateRequest);
		System.err.println(JsonEntityTransform.Object2Json(listOrderEntities));

	}

	@Test
	public void testQueryAllEvaluateByPage(){

		EvaluateRequest evaluateRequest = new EvaluateRequest();

		//evaluateRequest.setEvaStatu(3);
		//evaluateRequest.setEvaUserUid("fds45f6ds56f4");
		/*evaluateRequest.setIsDel(0);
		evaluateRequest.setEvaUserType(2);*/
		/*evaluateRequest.setStartTime("2016-04-09 19:00:39");
		evaluateRequest.setEndTime("2016-04-14 15:18:17");*/
		
		evaluateRequest.setContent("非常好,又感觉差");

		PagingResult<EvaluateVo> pagingResult = this.evaluateOrderDao.queryAllEvaluateByPage(evaluateRequest);
		
		System.out.println(pagingResult);

	}
	
	@Test
	public void testUpdateBycondition(){
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setEvaStatu(1);
		evaluateOrderEntity.setOrderSn("8a9e9cd2dfd53dfdsfdfdfd0b29d0153d0b29d460001");;
		evaluateOrderEntity.setOrderEvaFlag(1);
		evaluateOrderEntity.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());

		int index = this.evaluateOrderDao.updateBycondition(evaluateOrderEntity);

		System.out.println(index);
	}
	
	@Test
	public void testQueryByCondition(){
		
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
		List<EvaluateOrderEntity> listOrderEntities = this.evaluateOrderDao.queryByCondition(evaluateOrderEntity);
		
		System.out.println(listOrderEntities);
	}
	
	@Test
	public void testUpdateOrderEvaFlag(){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderEvaFlag", 1);
		List<String> listOrderSn = new ArrayList<String>();
		
		listOrderSn.add("1605022FDZ6352190804");
		params.put("evaUserType", UserTypeEnum.TENANT.getUserType());
		
		params.put("listOrderSn", listOrderSn);
		
		int index = this.evaluateOrderDao.updateOrderEvaFlag(params);
		
		System.out.println(index);
	}
	
	@Test
	public void testUpdateByEvaluateRe(){
		
		EvaluateRequest evaluateRequest = new EvaluateRequest();
		
		List<String> listFid = new ArrayList<String>();
		listFid.add("00000000549ee89101549ee891430000");
		
		int index = this.evaluateOrderDao.updateByEvaluateRe(evaluateRequest);
		
		System.out.println(index);
	}
	
	@Test
	public void testcountLanEva(){
		EvaluatePCRequest request = new EvaluatePCRequest();
		request.setRentWay(0);
		request.setHouseFid("8a9084df54d3646f0154d66a1ec90157");
		long count = evaluateOrderDao.countHouseEva(request);
		System.out.println(count);
		
	}
	
	
	@Test
	public void testqueryLanEvaPage(){
		EvaluatePCRequest request = new EvaluatePCRequest();
		request.setLandlordUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		PagingResult<EvaluateBothItemVo> queryLanEvaPage = evaluateOrderDao.queryLanEvaPage(request);
		System.out.println(JsonEntityTransform.Object2Json(queryLanEvaPage));
	}

	@Test
	public void testcountTenToLanEvaNum(){
		EvaluateRequest evaluateRequest = new EvaluateRequest();
		evaluateRequest.setRatedUserUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		long l = evaluateOrderDao.countTenToHouseEvaNum(evaluateRequest);
	}

	@Test
	public void testcountTenToHouseEvaNum(){
		EvaluateRequest evaluateRequest = new EvaluateRequest();
		evaluateRequest.setHouseFid("8a9084df54b39bd30154b3c6b76e0019");
		evaluateRequest.setRoomFid("");
		evaluateRequest.setRentWay(0);
		long l = evaluateOrderDao.countTenToHouseEvaNum(evaluateRequest);
		System.out.println(l);
	}

	@Test
	public void testqueryEvaluateOrderShowByOrderSn(){
		EvaluateRequest evaluateRequest =  new EvaluateRequest();
		evaluateRequest.setOrderSn("160515EQWV613Q214749");
		List<EvaluateOrderShowVo> evaluateOrderShowVos = evaluateOrderDao.queryEvaluateOrderShowByOrderSn(evaluateRequest);


	}

}
