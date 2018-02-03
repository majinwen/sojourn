package com.ziroom.minsu.services.order.test.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.services.order.dto.AuditCashbackQueryRequest;
import com.ziroom.minsu.services.order.dto.OrderCashbackRequest;
import com.ziroom.minsu.services.order.proxy.FinanceCashbackServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

public class FinanceCashbackServiceProxyTest extends BaseTest {
	
	@Resource(name = "order.financeCashbackServiceProxy")
	private FinanceCashbackServiceProxy financeCashbackServiceProxy;
	
	@Test
	public void testGetOrderCashbackList(){
		OrderCashbackRequest request = new OrderCashbackRequest();
		String orderCashbackList = financeCashbackServiceProxy.getOrderCashbackList(JsonEntityTransform.Object2Json(request));
		System.err.println(orderCashbackList);
	} 

	
	@Test
	public void getAuditCashbackList(){
		/*AuditCashbackQueryRequest request = new AuditCashbackQueryRequest();
		
		List<String> receiveUidList = new ArrayList<>();
		receiveUidList.add("a06f82a2-423a-e4e3-4ea8-e98317540190");
		receiveUidList.add("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		
		
		request.setReceiveUidList(receiveUidList);
		String auditCashbackList = financeCashbackServiceProxy.getAuditCashbackList(JsonEntityTransform.Object2Json(request));
		System.err.println(auditCashbackList);*/
		
		String param = "{\"page\":1,\"limit\":10,\"cashbackSn\":\"\",\"orderSn\":\"\",\"cashbackStatus\":null,\"actSn\":\"\",\"receiveType\":null,\"receiveUid\":\"\",\"receiveUidList\":[],\"receiveName\":\"\",\"receiveTel\":\"\",\"operTimeStart\":\"2016-08-29 00:00:00\",\"operTimeEnd\":\"2016-09-30 00:00:00\",\"createTimeStart\":\"\",\"createTimeEnd\":\"\"}";
		String auditCashbackList = financeCashbackServiceProxy.getAuditCashbackList(param);
		System.err.println(auditCashbackList);
	}
	
	@Test
	public void getAuditCashbackSumFee(){
		String param = "{\"page\":1,\"limit\":10,\"cashbackSn\":\"\",\"orderSn\":\"\",\"cashbackStatus\":null,\"actSn\":\"\",\"receiveType\":null,\"receiveUid\":\"\",\"receiveUidList\":[],\"receiveName\":\"\",\"receiveTel\":\"\",\"operTimeStart\":\"\",\"operTimeEnd\":\"\",\"createTimeStart\":\"\",\"createTimeEnd\":\"\"}";
		String auditCashbackList = financeCashbackServiceProxy.getAuditCashbackSumFee(param);
		System.err.println(auditCashbackList);
	}
	
	
	@Test
	public void getLogByCashbackSn(){
		
		String logByCashbackSn = financeCashbackServiceProxy.getLogByCashbackSn("1606305EMS41D8163011_6");
		System.err.println(logByCashbackSn);
	}
	
	
	
	@Test
	public void rejectCashback(){
		String params = "{\"userId\":\"lisc30\",\"cashbackSns\":[\"FX1606305EMS41D8163011_14\"]}";
		String rejectCashback = financeCashbackServiceProxy.rejectCashback(params);
		System.err.println(rejectCashback);
	}
	
	
	
	@Test
	public void auditCashback(){
		String param = "{\"userId\":\"yangd74\",\"cashbackSns\":[\"FX1608307JZ48Z2K094740_8\"]}";
		String auditCashback = financeCashbackServiceProxy.auditCashback(param);
		System.err.println(auditCashback);
	}
	
	public static void main(String[] args) {
		System.out.println(UUIDGenerator.hexUUID());
	}
	
	@Test
	public void getHasCashBackNum(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", "4b37c562-6ce9-7065-e9e6-aab2182cdbc4");
		paramMap.put("actSn", "8a90a36558de8ece0158e1af36ac00b1");
		String hasCashBackNumJson = financeCashbackServiceProxy.getHasCashBackNum(JsonEntityTransform.Object2Json(paramMap));
		System.err.println(hasCashBackNumJson);
	}
	
	@Test
	public void saveCashbackFromBatchTest(){
		List<FinanceCashbackEntity> cashbackList = new ArrayList<FinanceCashbackEntity>();
		for(int i=0;i<4;i++){
			 String orderSn="160511N2UZFA0B174512";
			 FinanceCashbackEntity cashback = new FinanceCashbackEntity();
	    	 cashback.setOrderSn(orderSn);
	    	 cashback.setActSn("111");
	    	 cashback.setTotalFee(Integer.valueOf(10));
	    	 cashback.setReceiveType(1);
	    	 cashback.setApplyRemark("sfdfdfdsf");
	    	 cashback.setCreateId("001"+i);
	    	 cashbackList.add(cashback);
		}
		String hasCashBackNumJson = financeCashbackServiceProxy.saveCashbackBatch(JsonEntityTransform.Object2Json(cashbackList));
		System.err.println(hasCashBackNumJson);
	}
}
