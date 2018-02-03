
/**
 * @FileName: OrderCommonServiceProxyTest.java
 * @Package com.ziroom.minsu.services.order.proxy
 * 
 * @author yd
 * @created 2016年4月5日 下午5:20:05
 * 
 * Copyright 2011-2015 asura
 */

/**
 * @FileName: OrderCommonServiceProxyTest.java
 * @Package com.ziroom.minsu.services.order.proxy
 * 
 * @author yd
 * @created 2016年4月5日 下午5:20:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.HouseSnapshotVo;
import com.ziroom.minsu.services.order.proxy.OrderCommonServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>房东管理订单接口测试</p>
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
public class OrderCommonServiceProxyTest extends BaseTest{

	@Resource(name = "order.orderCommonServiceProxy")
	private OrderCommonServiceProxy orderCommonServiceProxy;


	@Test
	public void countWaitEvaNumAll(){
		String pagingResult = orderCommonServiceProxy.countWaitEvaNumAll("a06f82a2-423a-e4e3-4ea8-e98317540190",UserTypeEnum.LANDLORD.getUserType());
		System.err.println(JsonEntityTransform.Object2Json(pagingResult));

	}


    @Test
    public void countLockTest(){


        String pagingResult = orderCommonServiceProxy.countLock("123");
        System.err.println(JsonEntityTransform.Object2Json(pagingResult));

    }


	@Test
	public void getOrderListLandTest(){

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setLandlordUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
//		orderRequest.setUserUid("1122222");
		orderRequest.setRequestType(2);
		orderRequest.setLanOrderType(11);
		orderRequest.setPage(1);
		orderRequest.setLimit(10);

		String pagingResult = orderCommonServiceProxy.getOrderList(JsonEntityTransform.Object2Json(orderRequest));
		System.err.println(JsonEntityTransform.Object2Json(pagingResult));



	}



	@Test
	public void getOrderListByCondictionTest(){

		OrderRequest orderRequest = new OrderRequest();
		//orderRequest.setLandlordUid("8a9e9a8b53d6da8b0153d6da8bae0000");
		//orderRequest.setUserUid("1122222");
		orderRequest.setRequestType(3);
		orderRequest.setOrderOprationType(1);
		orderRequest.setRoleType(0);
		/*List<Integer> list = new ArrayList<Integer>();
		list.add(100);
		list.add(110);
		list.add(200);
		orderRequest.setListEvaStatus(list);*/
		orderRequest.setPage(1);
		orderRequest.setLimit(10);



		//查询待处理订单
		/*List<Integer> listOrderStatus = new ArrayList<Integer>();
		listOrderStatus.add(20);
		listOrderStatus.add(50);
		orderRequest.setListOrderStatus(listOrderStatus);*/
		String pagingResult = orderCommonServiceProxy.getOrderListByCondiction(JsonEntityTransform.Object2Json(orderRequest));
		System.err.println(JsonEntityTransform.Object2Json(pagingResult));

		/*if(pagingResult!=null){

			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(pagingResult);

			try {
				List<OrderInfoVo> listInfoVos = SOAResParseUtil.getListValueFromDataByKey(pagingResult, "orderHouseList", OrderInfoVo.class);

				System.err.println(listInfoVos);
			} catch (SOAParseException e) {
				e.printStackTrace();
			}
			Object object = resultDto.getData().get("orderHouseList");
			if(object!=null){
				List<OrderInfoVo> listInfoVos = ((List<OrderInfoVo>) object);
				System.err.println(listInfoVos);
			}
			List<OrderInfoVo> listInfoVos = resultDto.parseData("orderHouseList", new TypeReference<List<OrderInfoVo>>() {
			});
			System.out.println(listInfoVos);
		}*/

	}


	@Test
	public void getOrderAllBySn(){
		String dto =  orderCommonServiceProxy.getOrderAllBySn("170512123WWLUF191513");
		if(dto!=null){
			System.out.println(dto);
		}
	}

	@Test
	public void queryOrderInfoBySnTest(){
		OrderDetailRequest request = new OrderDetailRequest();
        request.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190");;
		request.setRequestType(3);
		request.setOrderSn("1606305EMS41D8163011");
		String dto =  orderCommonServiceProxy.queryOrderInfoBySn(JsonEntityTransform.Object2Json(request));
		if(dto!=null){
			System.out.println(dto);
		}
	}

	/**
	 * 
	 * 测试查询房源快照
	 *
	 * @author yd
	 * @created 2016年5月3日 下午3:52:02
	 *
	 */
	@Test
	public void findHouseSnapshotByOrderTest(){
		//查询参数
		OrderRequest orderRe = new OrderRequest();
		List<Integer> list = new ArrayList<Integer>();
		//待评价(100 待评价  110 房东已评价)
		list.add(OrderEvaStatusEnum.WAITINT_EVA.getCode());
		list.add(OrderEvaStatusEnum.LANLORD_EVA.getCode());
		
		List<Integer> listOrderSta = new ArrayList<Integer>();
		listOrderSta.add(OrderStatusEnum.FINISH_COMMON.getOrderStatus());
		listOrderSta.add(OrderStatusEnum.FINISH_PRE.getOrderStatus());
		orderRe.setUserUid("8a9e9a9e543d23f901543d23f9e90000");
		/*orderRe.setListEvaStatus(list);
		orderRe.setListOrderStatus(listOrderSta);*/



		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonServiceProxy.findHouseSnapshotByOrder(JsonEntityTransform.Object2Json(orderRe)));
		
		List<HouseSnapshotVo> lisHouseSnapshot = dto.parseData("listSnapshot", new TypeReference<List<HouseSnapshotVo>>() {
		});
		
		System.out.println(lisHouseSnapshot);
	}
	
	/**
	 * @author yd
	 * @created 2016年5月3日 下午5:40:06
	 *
	 */
	@Test
	public void getOrderByOrderSnTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonServiceProxy.getOrderByOrderSn("16050267DY79C7174903"));
		OrderEntity orderEntity  = dto.parseData("order", new TypeReference<OrderEntity>() {
		});
		//校验订单房客是否已评价
		int evaStatus = orderEntity.getEvaStatus().intValue();
		
		System.out.println(evaStatus);
	}
	@Test
	public void updateOrderBaseByOrderSnTest(){
		
		OrderEntity orderEntity = new OrderEntity();
		
		orderEntity.setEvaStatus(OrderEvaStatusEnum.UESR_HVA_EVA.getCode());
		orderEntity.setOrderSn("16050267DY79C7174903");
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonServiceProxy.updateOrderBaseByOrderSn(JsonEntityTransform.Object2Json(orderEntity)));
		
		System.out.println(dto);
	}
	
	@Test
	public void testGetCheckOutStrategyByOrderSn(){
		String checkOutStrategyByOrderSn = orderCommonServiceProxy.getCheckOutStrategyByOrderSn("1605065D4OSG91114032");
		System.out.println(checkOutStrategyByOrderSn);
	}
	@Test
	public void testGetOrderList(){
		OrderRequest request = new OrderRequest();
		request.setPage(1);
		request.setLimit(10);
		request.setRequestType(RequestTypeEnum.LANDLORD.getRequestType());
		request.setLanOrderType(3);
		request.setLandlordUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		String  list = orderCommonServiceProxy.getOrderList(JsonEntityTransform.Object2Json(request));
		System.out.println(list);
	}


	@Test
	public void testGetOrderListForTenant() {
		OrderRequest request = new OrderRequest();
		request.setPage(1);
		request.setLimit(10);
		request.setRequestType(RequestTypeEnum.TENANT.getRequestType());
		request.setTenantOrderType(8);
		request.setUserUid("fb65b265-f2ee-b9fa-75b0-fbeafa6f9de9");
		String list = orderCommonServiceProxy.getOrderList(JsonEntityTransform.Object2Json(request));
		System.err.println(list);
	}

	@Test
	public void updateByFid(){
		String request = "{\"id\":null,\"fid\":\"8a9e98b558b9d2280158b9d228030000\",\"userUid\":\"e0a0f779-9117-6283-84e1-43e0be20ecf4\",\"conName\":\"李少川2\",\"conTel\":\"18633033235\",\"conSex\":null,\"cardType\":2,\"cardValue\":\"12345678902\",\"frontPic\":null,\"obversePic\":null,\"isDefault\":null,\"createTime\":null,\"lastModifyDate\":1480578032880,\"isDel\":null,\"isAuth\":null}";
		String result = orderCommonServiceProxy.updateByFid(request);
		System.err.println(result);
	}

	@Test
	public void deleteContact(){
		String result = orderCommonServiceProxy.deleteContact("8a9084df58b9d8040158b9d951e7000a", "e0a0f779-9117-6283-84e1-43e0be20ecf4");
		System.err.println(result);
	}


	@Test
	public void findUsualContactsByContion(){
		String result = orderCommonServiceProxy.findUsualContactsByContion("{\"page\":2,\"limit\":2,\"listFid\":null,\"userUid\":\"e0a0f779-9117-6283-84e1-43e0be20ecf4\"}");
		System.err.println(result);
	}
	
	@Test
	public void cancelUnPayOrderTest(){
		CancelOrderServiceRequest cancelOrder = new CancelOrderServiceRequest();
		cancelOrder.setOrderSn("160511252A9834163856");
		cancelOrder.setCancelReason("测试");
		String result = orderCommonServiceProxy.cancelUnPayOrder(JsonEntityTransform.Object2Json(cancelOrder));
		System.err.println(result);
	}
	
    @Test
    public void testQueryOrderForCustomerBehaviorJob(){
        OrderRequest orderRequest = new OrderRequest();
        Date now = new Date();
        orderRequest.setOrderStatus(OrderStatusEnum.REFUSED.getOrderStatus());
        orderRequest.setStartTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 00:00:00"));
        orderRequest.setEndTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 23:59:59"));
        List<String> proveFidList  = new ArrayList<>();
        orderRequest.setListOrderSn(proveFidList);
        System.out.println(orderCommonServiceProxy.queryOrderForCustomerBehaviorJob(orderRequest.toJsonStr()));

    }
	
	 @Test
	    public void testgetLandAcceptOrderRateIn60Days(){
		 Set<String> uidSet = new HashSet<String>();
		 uidSet.add("23fba67c-1f0d-4009-8ab5-d94130243ad0");
		 uidSet.add("7f56afc5-3318-4dde-9a8b-29d84b370b0a");
		 uidSet.add("82215fee-800b-4987-ae99-7585c2558b99");
		 uidSet.add("30c83fd1-97ce-46b1-a632-e8076ab0fcae");
		 uidSet.add("912189cf-59df-48bc-a282-9649f6df489f");
		 uidSet.add("bba260fe-369c-4560-b03d-e22bb4e1e163");
		 uidSet.add("d235c7de-172d-42b4-b3b1-c760e45c8fae");
		 uidSet.add("6005c98b-9e2c-4442-9667-d68b79c7f6e6");
		 uidSet.add("d3e2eb42-b06b-4945-a9d2-0ddf404c580b");
		 uidSet.add("c73657fc-3f37-4a50-9df9-cfb90fbd11e3");
		 uidSet.add("061f210c-9fe0-40ec-a171-53af457e5bfb");
		 uidSet.add("a117ddd4-fc9f-4e40-b5eb-102664927ee2");
		 uidSet.add("d2b43b5e-e9c3-456b-9930-e2f5ce508216");
		 uidSet.add("22cc78ca-c14c-4174-8221-02f2de8235f7");
		 uidSet.add("8b5f4499-2e9f-41f3-b373-fe6ce0d0453d");
		 uidSet.add("9e874aeb-88b0-400b-9817-83e0f3905c7b");
		 uidSet.add("6964ed69-5726-4561-9698-7e412f54279e");
		 uidSet.add("ddc8cf02-2263-deed-7c13-1841d0726a73");
		 uidSet.add("91e0bea5-ec42-505e-7977-9fd31a5a232b");
		 uidSet.add("ed66bd11-5b99-48e1-8ec9-b9b3ea5b204d");
		 uidSet.add("5b73e83f-1944-4616-9a0e-a426b808b77d");
		 uidSet.add("5c47e29c-a503-6104-4a98-ad952627da72");
		 uidSet.add("1a9a0dfa-5fcb-4b04-a045-f51ee00d02a5");
		 uidSet.add("5303d9db-60a8-4692-b94d-450b67b165e1");
		 uidSet.add("17f29c6a-faa9-417b-8894-fecc52d81030");
		 uidSet.add("6e8b6fcb-bb98-4a71-9f08-f225b73f776b");
		 uidSet.add("8e8924ce-08d0-4f5c-9acd-acdef9252839");
		 uidSet.add("6e1eb192-ceea-4392-b7ed-b8314501e998");
		 uidSet.add("986e497a-f85a-470f-b498-bb9f39107e93");
		 uidSet.add("551f12ea-895e-70e1-9a16-0c5e86eca7e7");
		 uidSet.add("66b8f109-8649-48f9-bd60-97ccafb58cc7");
		 uidSet.add("0c6e4a5d-5ce8-4bba-b44b-cdd523fc3385");
		 uidSet.add("738c345d-f178-4e25-ae38-7c435be408ce");
		 uidSet.add("66f1bdd8-7f99-4c9c-868b-3e68f06eda5a");
		 uidSet.add("b654cd60-bc83-4d1f-a23b-12e6f9bdc4a6");
		 uidSet.add("111d7d21-beac-4e96-805a-90c069b1836a");
		 uidSet.add("accbf7e2-eac5-4b01-9d85-5ea54a934a5f");
		 uidSet.add("c6b55399-c53b-4561-b808-db211d2a7166");
	     System.out.println(orderCommonServiceProxy.getLandAcceptOrderRateIn60Days(JsonEntityTransform.Object2Json(uidSet)));

	    }
	 
	 @Test
	    public void g(){
		 OrderEvalRequest request = new OrderEvalRequest();
			//request.setLimit(150);
			request.setOrderEvalType(1);//0：所有，1：待评价，2：已评价（房东不区分，默认全部）
			//request.setPage(1);
			request.setUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
			request.setRequestType(2);//2：房客，1：房东
			String orderEavlList = orderCommonServiceProxy.getOrderEavlList(JsonEntityTransform.Object2Json(request));
	        System.out.println(orderEavlList);

	    }
	 
	     @Test
	    public void testgetBeInviterStatusInfo(){
		 BeInviterStatusInfoRequest request = new BeInviterStatusInfoRequest();
		 List<String> beInviterInfoList = new ArrayList<String>();
		 beInviterInfoList.add("6fe31fe4-3dff-4daa-b861-752897d609e2");
		 beInviterInfoList.add("49a29062-d653-4f81-a339-bb4ebc20992c");
		 beInviterInfoList.add("fdfa");
		 beInviterInfoList.add("fd");
		 request.setBeInviterInfoList(beInviterInfoList);
		 request.setOrderStatus(20);
		 String orderEavlList = orderCommonServiceProxy.getBeInviterStatusInfo(JsonEntityTransform.Object2Json(request));
	     System.out.println(orderEavlList);

	    }

	@Test
	public void queryOrder4HourTest() {
		String s = orderCommonServiceProxy.queryOrder4Hour();
		System.out.println(s);

	}
}
