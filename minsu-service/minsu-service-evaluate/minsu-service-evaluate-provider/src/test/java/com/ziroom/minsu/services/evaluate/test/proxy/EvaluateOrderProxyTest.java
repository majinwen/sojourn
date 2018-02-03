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
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.evaluate.dto.EvaluatePCRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.services.evaluate.proxy.EvaluateOrderProxy;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class EvaluateOrderProxyTest extends BaseTest{



	@Resource(name = "evaluate.evaluateOrderProxy")
	private EvaluateOrderProxy evaluateOrderService;
	@Test
	public void testQueryAllEvaluateByPage() {
		/*EvaluateRequest evaluateRequest = new EvaluateRequest();
		//evaluateRequest.setEvaUserUid("f4d5s6f4fdfdfd5s6f4");
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryAllEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest)));
		List<EvaluateVo> lsitEvaluateVo = resultDto.parseData("lsitEvaluateVo", new TypeReference<List<EvaluateVo>>() {
		});
		System.out.println(lsitEvaluateVo);*/


		String json = "{\"page\":1,\"limit\":10,\"orderSn\":\"\",\"evaStatu\":null,\"evaUserUid\":\"\",\"ratedUserUid\":\"\",\"evaUserType\":null,\"houseFid\":null,\"roomFid\":null,\"bedFid\":null,\"isDel\":null,\"tenContent\":null,\"rentWay\":null,\"laeContent\":null,\"orderEvaFlag\":null,\"startTime\":\"\",\"endTime\":\"\",\"content\":\"\",\"empPushName\":\"\",\"empGuardName\":\"高冠阳\",\"listFid\":[\"8a90a2d455daafe90155dcb507e3003d\",\"8a90a2d455c421700155c43c14160019\",\"8a90a2d455ce38260155ce5402000016\",\"8a90a2d4566e3a64015672a2267f00ca\",\"8a90a2d4568cdd4701568ce08fe70006\",\"8a90a2d456da64080156db2728a60291\",\"8a90a2d456db5c8e0156db70cb73007b\",\"8a90a2d456db5c8e0156db72d9b20089\",\"8a90a2d456db5c8e0156db747e2100b7\",\"8a90a2d456db5c8e0156db76461400f6\"]}";
		String result = this.evaluateOrderService.queryAllEvaluateByPage(json);
		System.err.println(result);
	}


	//	@Test
	//	public void testInspectOrderEvaStatuTiming(){
	//		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.inspectOrderEvaStatuTiming());
	//
	//		System.out.println(resultDto.getData().get("result").toString());
	//	}

	/**
	 * 
	 * TODO
	 *
	 * @author yd
	 * @created 2016年5月3日 下午6:03:53
	 *
	 */
	@Test
	public void saveTenantEvaluateTest(){
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();

		evaluateOrderEntity.setFid(UUIDGenerator.hexUUID());
		evaluateOrderEntity.setCreateTime(new Date());
		evaluateOrderEntity.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaluateOrderEntity.setEvaUserType(UserTypeEnum.TENANT.getUserType());
		evaluateOrderEntity.setHouseFid("888888888888888888");
		evaluateOrderEntity.setLastModifyDate(new Date());
		evaluateOrderEntity.setOrderSn("160519O0AYSBN5161550");
		evaluateOrderEntity.setRatedUserUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		evaluateOrderEntity.setEvaUserUid("f4d5s6f4fdfdfd5s6f4");
		evaluateOrderEntity.setRentWay(RentWayEnum.HOUSE.getCode());

		TenantEvaluateEntity tenantEvaluateEntity = new TenantEvaluateEntity();
		tenantEvaluateEntity.setContent("非常好");
		tenantEvaluateEntity.setCostPerformance(4);
		tenantEvaluateEntity.setCreateTime(new Date());
		tenantEvaluateEntity.setDescriptionMatch(4);
		tenantEvaluateEntity.setEvaOrderFid(evaluateOrderEntity.getFid());
		tenantEvaluateEntity.setHouseClean(4);
		tenantEvaluateEntity.setIsDel(0);
		tenantEvaluateEntity.setLastModifyDate(new Date());
		tenantEvaluateEntity.setSafetyDegree(4);
		tenantEvaluateEntity.setTrafficPosition(4);
		
		OrderEntity order =  new OrderEntity();
		order.setOrderSn(evaluateOrderEntity.getOrderSn());
		order.setLandlordName("fdsafd");
		order.setUserName("hyuifhidus");

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.saveTenantEvaluate(JsonEntityTransform.Object2Json(tenantEvaluateEntity), JsonEntityTransform.Object2Json(evaluateOrderEntity),JsonEntityTransform.Object2Json(order)));

		System.out.println(dto);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void evaluateOnlineTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.evaluateOnline("30"));
		System.out.println(dto);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	@Test
	public void queryEvaluateByOrderSnTest() {
		//String reqJson = "{\"page\":1,\"limit\":50,\"orderSn\":\"1605082VY4L432215140\",\"evaStatu\":null,\"evaUserUid\":null,\"ratedUserUid\":null,\"evaUserType\":2,\"houseFid\":null,\"roomFid\":null,\"bedFid\":null,\"isDel\":null,\"tenContent\":null,\"rentWay\":null,\"laeContent\":null,\"orderEvaFlag\":null,\"startTime\":null,\"endTime\":null,\"content\":null,\"listFid\":null}";
		
		EvaluateRequest evaluateRequest  = new EvaluateRequest();
		evaluateRequest.setOrderSn("17020659Z5JU59144022");
		evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
//		evaluateRequest.setEvaUserUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		String resJson = evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest));
		System.err.println(resJson);
	}

	@Test
	public void testQueryOtherLanEvaByPage(){

		EvaluateRequest evaluateRequest = new EvaluateRequest();

		//evaluateRequest.setEvaStatu(3);
		evaluateRequest.setEvaUserUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryOtherLanEvaByPage(JsonEntityTransform.Object2Json(evaluateRequest)));

		System.out.println(JsonEntityTransform.Object2Json(dto));
		if(!Check.NuNObjs(dto)){
			System.out.println(dto);
		}
	}
	
	@Test
	public void testGetEvaluteList(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.getEvaluteList());
		
		System.out.println(dto);
	}
	
	
	@Test
	public void testQueryStatsHouseEvaByCondition(){
		
		StatsHouseEvaRequest staRequest = new StatsHouseEvaRequest();
		staRequest.setHouseFid("8a9084df54b96fb70154b9c2e7fd00c0");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(staRequest)));
		
		System.out.println(dto);
	}
	
	@Test
	public void testhouseDetailEvaInfo(){
		EvaluatePCRequest request = new EvaluatePCRequest();
		request.setHouseFid("8a9084df54d3646f0154d66a1ec90157");
		request.setRentWay(0);
		String str = evaluateOrderService.houseDetailEvaInfo(JsonEntityTransform.Object2Json(request));
		System.out.println(str);
	}
	
	@Test
	public void testqueryHouseDetailEvaPage(){
		EvaluatePCRequest request = new EvaluatePCRequest();
		request.setRentWay(1);
		request.setRoomFid("8a9084df550d9bdd01550db2ec340107");
		String page = evaluateOrderService.queryHouseDetailEvaPage(JsonEntityTransform.Object2Json(request));
		System.out.println(page);
	}
	
	@Test
	public void saveSystemEval(){
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn("1705156I1E7Y09143447");
		OrderHouseSnapshotEntity snapshotEntity = new OrderHouseSnapshotEntity();
		snapshotEntity.setOrderSn("1705156I1E7Y09143447");
		String orderEntityJson = JsonEntityTransform.Object2Json(orderEntity);
		String snapshotEntityJson = JsonEntityTransform.Object2Json(snapshotEntity);
		//String saveSystemEvalJson= evaluateOrderService.saveSystemEval(orderEntityJson,snapshotEntityJson );
	}
	
	@Test
	public void queryTenantEvaluateByPage(){
		EvaluateRequest evaluateRequest = new EvaluateRequest();
		evaluateRequest.setRoomFid("8a9084df550d9bdd01550db2ec340107");
		evaluateRequest.setRentWay(1);
		String evaluatePageJson = evaluateOrderService.queryTenantEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest));
        try {
			List<TenantEvaluateVo> evaList = SOAResParseUtil.getListValueFromDataByKey(evaluatePageJson, "listTenantEvaluateVo", TenantEvaluateVo.class);
			System.out.println(JsonEntityTransform.Object2Json(evaList));
        } catch (SOAParseException e) {
			e.printStackTrace();
		}

	}


    @Test
    public void queryTenantEvaluateByPageForCustomerBehaviorJob(){
        //查询昨天的评价
        EvaluateRequest evaluateRequest = new EvaluateRequest();
        Date now = new Date();
        List<String> proveFidList  = new ArrayList<>();
        proveFidList.add("123456789134567987");
        evaluateRequest.setListFid(proveFidList);
        evaluateRequest.setStartTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 00:00:00"));
        evaluateRequest.setEndTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 23:59:59"));
        String s = evaluateOrderService.queryTenantEvaluateForCustomerBehaviorJob(JsonEntityTransform.Object2Json(evaluateRequest));
        System.out.println(s);

    }
	
	

}
