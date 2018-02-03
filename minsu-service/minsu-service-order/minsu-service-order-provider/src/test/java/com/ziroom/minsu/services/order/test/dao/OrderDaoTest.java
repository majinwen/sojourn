package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.dao.OrderDao;
import com.ziroom.minsu.services.order.dto.EvalSynRequest;
import com.ziroom.minsu.services.order.dto.OrderCashbackRequest;
import com.ziroom.minsu.services.order.dto.OrderEvalRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.CashbackOrderVo;
import com.ziroom.minsu.services.order.entity.OrderCashbackVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.OrderInviteVo;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>订单类测测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class OrderDaoTest  extends BaseTest {


    @Resource(name = "order.orderDao")
    private OrderDao orderDao;





	/*@Test
	public void getLandOrderEavlWaitingList() {
		OrderEvalRequest orderRequest = new OrderEvalRequest();
		orderRequest.setUid("123");
		orderRequest.setLimitDay(1);
		PagingResult<OrderInfoVo>  infoVo = orderDao.getLandOrderEavlWaitingList(orderRequest);
		System.out.println(JsonEntityTransform.Object2Json(infoVo));
	}*/


	@Test
	public void getTenantOrderEavlWaitingList() {
		OrderEvalRequest orderRequest = new OrderEvalRequest();
		orderRequest.setUid("123");
		orderRequest.setLimitDay(1);
		PagingResult<OrderInfoVo>  infoVo = orderDao.getTenantOrderEavlWaitingList(orderRequest);
		System.out.println(JsonEntityTransform.Object2Json(infoVo));
	}




	@Test
	public void updateCjStatuByOrderSn() {


		EvalSynRequest evalSynRequest  = new EvalSynRequest();

//		int  infoVo = orderDao.updateCjStatuByOrderSn(evalSynRequest);
//		System.out.println(JsonEntityTransform.Object2Json(infoVo));
	}





	@Test
	public void countUserApplyNum() {

		Long  infoVo = orderDao.countUserApplyNum("123");
		System.out.println(JsonEntityTransform.Object2Json(infoVo));
	}

	@Test
	public void countUserWaitPayNum() {

		Long  infoVo = orderDao.countUserWaitPayNum("123");
		System.out.println(JsonEntityTransform.Object2Json(infoVo));
	}

	@Test
	public void countUserWaitCheckInNum() {

		Long  infoVo = orderDao.countUserWaitCheckInNum("123");
		System.out.println(JsonEntityTransform.Object2Json(infoVo));
	}

	@Test
	public void countUserWaitEvaNum() {

		Long  infoVo = orderDao.countUserWaitEvaNum("123");
		System.out.println(JsonEntityTransform.Object2Json(infoVo));
	}

    @Test
    public void countOrderLock() {

        Long  infoVo = orderDao.countLock("123");
        System.out.println(JsonEntityTransform.Object2Json(infoVo));
    }

    @Test
    public void getTenantOrderLockList() {
        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setUserUid("123");

        PagingResult<OrderInfoVo>  infoVo = orderDao.getTenantOrderLockList(orderRequest);
        System.out.println(JsonEntityTransform.Object2Json(infoVo));
    }

    @Test
    public void TestgetOrderInfoByOrderSn() {
        OrderInfoVo infoVo = orderDao.getOrderInfoByOrderSn("1605132G6T2421154821");
        System.out.println(JsonEntityTransform.Object2Json(infoVo));
        System.out.println(JsonEntityTransform.Object2Json(infoVo));
        System.out.println(JsonEntityTransform.Object2Json(infoVo));
    }




    @Test
    public void TestGetOrderForPageByCondiction() {
    	
    	OrderRequest orderRequest = new OrderRequest();
//		orderRequest.setUserUid("1122222");
//		orderRequest.setRequestType(1);
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(100);
		list.add(110);
		list.add(200);
		orderRequest.setListEvaStatus(list);
		orderRequest.setPage(1);
		orderRequest.setLimit(10);
    	/*OrderRequest or = new OrderRequest();
    	or.setLimit(3);
    	or.setPage(1);*/
    	/*or.setHouseFid("testtest");
    	or.setLandlordUid("");
    	or.setRoomFid("");
    	or.setUserUid("");*/
    	PagingResult<OrderInfoVo> pr = orderDao.getOrderInfoListByCondiction(orderRequest);
        System.out.println("--------------"+JsonEntityTransform.Object2Json(pr));
    }
    

    
    
    @Test
    public void taskGetOrderList(){
    	
    	Map<String,Object> paramMap = new HashMap<String,Object>(2);
    	paramMap.put("payStatus", 0);
    	paramMap.put("orderStatus", 20);
    	paramMap.put("limitTime", "2016-04-01");
//    	List<String> resList = 	orderDao.taskGetOrderList(paramMap);
    	
//    	System.out.println("------------------"+JsonEntityTransform.Object2Json(resList));
    }

    
    @Test
    public void testQueryOrderByCondition(){
    	List<String> listOrderSn = new ArrayList<String>();
    	listOrderSn.add("16051176ZD432U160931");
    	listOrderSn.add("16051108554595161902");
    	OrderRequest orderRequest = new OrderRequest();
    	orderRequest.setListOrderSn(listOrderSn);
    	List<OrderEntity> lsitEntities = this.orderDao.queryOrderByCondition(orderRequest);
    	
    	System.out.println(lsitEntities);
    }

    
    @Test
    public void testUpdateEvaStatuByOrderSn(){
    	
    	List<String> listOrderSn = new ArrayList<String>();
    	listOrderSn.add("16051176ZD432U160931");
    	/*listOrderSn.add("8a9e9cc253e9938a0153e9938c370009");
    	listOrderSn.add("8a9e9cc253e9938a0153e9938d380015");
    	listOrderSn.add("8a9e9cc253e9938a0153e9938ed90021")*/;
    	OrderRequest orderRequest = new OrderRequest();
    	orderRequest.setListOrderSn(listOrderSn);
    	orderRequest.setEvaStatus(111);
    	int index = this.orderDao.updateEvaStatuByOrderSn(orderRequest);
    	
    	System.out.println(index);
    }
    
    @Test
    public void testGetOrderInfoListByCondiction(){
    	List<Integer> listEvaStatus = new ArrayList<Integer>();
    	listEvaStatus.add(100);
    	listEvaStatus.add(101);
    	OrderRequest orderRequest = new OrderRequest();
    	orderRequest.setListEvaStatus(listEvaStatus);
    	//orderRequest.setEvaStatus(200);
    	orderRequest.setRequestType(2);
    	orderRequest.setLandlordUid("8a9e9a8b53d6da8b0153d6da8bae0000");
    	
    	List<Integer> listOrderStatus = new ArrayList<Integer>();
    	listOrderStatus.add(20);
    	listOrderStatus.add(30);
    	listOrderStatus.add(50);
    	orderRequest.setListOrderStatus(listOrderStatus);
    	PagingResult<OrderInfoVo> list = orderDao.getOrderInfoListByCondiction(orderRequest);
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void testGetOrderDoingList(){
    	OrderRequest orderRequest = new OrderRequest();
		orderRequest.setUserUid("1122222");
		orderRequest.setLimit(3);
		orderRequest.setPage(1);
		PagingResult<OrderInfoVo> tenantOrderDoneList = orderDao.getTenantOrderDoingList(orderRequest);
		System.out.println(tenantOrderDoneList.getTotal());
    }
    
    
    
    @Test
    public void testGetOrderCashbackList(){
    	OrderCashbackRequest request = new OrderCashbackRequest();
    	PagingResult<OrderCashbackVo> orderCashbackList = orderDao.getOrderCashbackList(request);
    	System.err.println(JsonEntityTransform.Object2Json(orderCashbackList));
    }


	@Test
	public void getOrderLastByUid(){
		OrderInfoVo orderInfoVo = orderDao.getOrderLastByUid("123");
		System.err.println(JsonEntityTransform.Object2Json(orderInfoVo));
	}
	
	@Test
	public void countWaitLandlordEvaNum(){
		Long num = orderDao.countWaitLandlordEvaNum("53df78d5-b599-47e5-8acf-d1e19eb53394");
		System.err.println(num);
	}
	
	@Test
	public void countWaitTenantEvaNum(){
		Long num = orderDao.countWaitTenantEvaNum("53df78d5-b599-47e5-8acf-d1e19eb53394");
		System.err.println(num);
	}
	
	@Test
	public void countCanEvaNum(){
		Long num = orderDao.countCanEvaNum("53df78d5-b599-47e5-8acf-d1e19eb53394");
		System.err.println(num);
	}
	
	@Test
	public void countTenantEvaedNum(){
		Long num = orderDao.countTenantEvaedNum("53df78d5-b599-47e5-8acf-d1e19eb53394");
		System.err.println(num);
	}
	
	@Test
	public void countLandlordEvaedNum(){
		Long num = orderDao.countLandlordEvaedNum("53df78d5-b599-47e5-8acf-d1e19eb53394");
		System.err.println(num);
	}
	
	@Test
	public void queryNewUserByOrder(){
		
		boolean isNew = orderDao.isNewUserByOrderForFirstPage("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		System.out.println(isNew);
		
		
	}
	
	@Test
	public void queryCashbackOrderVo(){
		
		OrderCashbackRequest request = new OrderCashbackRequest();
		PagingResult<CashbackOrderVo> page = orderDao.queryCashbackOrderVo(request);	
		
		System.out.println(page);
	}

	@Test
	public void queryOrderByPage(){
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setCheckInTimeStart("2017-09-09 00:00:00");
		orderRequest.setCheckInTimeEnd("2017-09-09 23:59:59");
		orderRequest.setOrderStatus(20);
		orderRequest.setPayStatus("1");
		PagingResult<OrderEntity> page = orderDao.queryOrderByPage(orderRequest);	
		
		System.out.println(page);
	}

	@Test
	public void queryOrder4Hour() {
		List<OrderInviteVo> list;
		list=orderDao.queryOrder4Hour();
		System.err.print(list);
	}
}
