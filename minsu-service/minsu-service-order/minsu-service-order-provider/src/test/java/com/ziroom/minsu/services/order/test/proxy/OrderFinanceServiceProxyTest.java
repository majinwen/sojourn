package com.ziroom.minsu.services.order.test.proxy;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.finance.dto.PayVouchersCreateRequest;
import com.ziroom.minsu.services.finance.dto.PunishListRequest;
import com.ziroom.minsu.services.order.proxy.OrderFinanceServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * 
 * <p>
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月25日
 * @since 1.0
 * @version 1.0
 */
public class OrderFinanceServiceProxyTest extends BaseTest {

	@Resource(name = "order.orderFinanceServiceProxy")
	private OrderFinanceServiceProxy orderFinanceServiceProxy;

	@Test
	public void TestGetPunishList() {
		PunishListRequest punishRequest = new PunishListRequest();
		punishRequest.setLimit(10);
		punishRequest.setPage(1);
		punishRequest.setPunishUid("1111");

		String result = orderFinanceServiceProxy.getPunishList(JsonEntityTransform.Object2Json(punishRequest));
		System.out.println("result:" + result);
	}
	
	@Test
	public void landlordDayRevenueListTest(){
		String resultJson =orderFinanceServiceProxy.landlordDayRevenueList(DateUtil.dateFormat(new Date()));
		System.err.println(resultJson);
	}
	
	/*@Test
	public void createPayVouchersBySelfTest(){
		
		PayVouchersCreateRequest pvRequest = new PayVouchersCreateRequest();
		pvRequest.setUserId("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		pvRequest.setTotalFee(11);
		
		String requestJson = JsonEntityTransform.Object2Json(pvRequest);
		String resultJson = orderFinanceServiceProxy.createPayVouchersBySelf(requestJson);
		System.err.println(resultJson);
		
	}*/
}
