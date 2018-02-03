
package com.ziroom.minsu.services.order.test.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.finance.dto.FinanceIncomeRequest;
import com.ziroom.minsu.services.finance.dto.FinancePayVosRequest;
import com.ziroom.minsu.services.finance.dto.PaymentVouchersRequest;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.finance.entity.FinancePayVouchersVo;
import com.ziroom.minsu.services.finance.entity.FinancePaymentVo;
import com.ziroom.minsu.services.order.proxy.BillManageServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * <p>账单代理层测试</p>
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
public class BillManageServiceProxyTest extends BaseTest{


	@Resource(name = "order.billManageServiceProxy")
	private BillManageServiceProxy billManageServiceProxy;








    @Test
    public void TestgetFinanceIncomeDetail() {

        String aa = this.billManageServiceProxy.getFinanceIncomeDetail("SR160512G2C514OL150410");

        System.out.println(aa);

    }



	@Test
	public void queryPaymentVoByPageTest() {

		PaymentVouchersRequest  paymentRequest  = new PaymentVouchersRequest();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.billManageServiceProxy.queryPaymentVoByPage(JsonEntityTransform.Object2Json(paymentRequest)));

		List<FinancePaymentVo> lsitFinancePaymentVos = dto.parseData("listFinancePaymentVo", new TypeReference<List<FinancePaymentVo>>() {
		});
		System.out.println(lsitFinancePaymentVos);

	}

	@Test
	public void queryFinanceIncomeByPageTest(){

		FinanceIncomeRequest incomeRequest = new FinanceIncomeRequest();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.billManageServiceProxy.queryFinanceIncomeByPage(JsonEntityTransform.Object2Json(incomeRequest)));

		List<FinanceIncomeVo> lsitFinanceIncomeVo= dto.parseData("listFinanceIncomeVo", new TypeReference<List<FinanceIncomeVo>>() {
		});
		System.out.println(lsitFinanceIncomeVo);
	}

	
	@Test
	public void queryFinancePayVosByPageTest(){
		/*FinancePayVosRequest payVosRequest = new FinancePayVosRequest();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.billManageServiceProxy.queryFinancePayVosByPage(JsonEntityTransform.Object2Json(payVosRequest)));

		List<FinancePayVouchersVo> listFinancePayVouchersVos= dto.parseData("listFinancePayVouchersVo", new TypeReference<List<FinancePayVouchersVo>>() {
		});
		System.out.println(listFinancePayVouchersVos);*/
		
		String params = "{\"page\":1,\"limit\":10,\"userName\":\"\",\"userTel\":\"\",\"userUid\":\"\",\"orderSn\":\"1608234N5CZFE5174853\",\"incomeSn\":null,\"receiveName\":\"\",\"receiveTel\":\"\",\"receiveUidList\":null,\"paymentStatus\":null,\"actualStartTime\":null,\"actualEndTime\":null,\"runTimeStart\":\"\",\"runTimeEnd\":\"\",\"pvSn\":\"\"}";
		
		String queryFinancePayVosByPage = billManageServiceProxy.queryFinancePayVosByPage(params);
		System.err.println(queryFinancePayVosByPage);
	}
	
	
	@Test
	public void getPayVouchersDetailTest(){
		String result = billManageServiceProxy.getPayVouchersDetail("FK160824SDQ5S7Y6163509");
		System.err.println("result:"+result);
	}
	
	
	@Test
	public void reCreatePvsTest(){
		String reCreatePvs = billManageServiceProxy.reCreatePvs("FK160824SDQ5S7Y6163509_1", "lishaochuan");
		System.err.println(reCreatePvs);
	}


	@Test
	public void checkCanYlfh(){
		String str = billManageServiceProxy.checkCanYlfh("FK160825UF1D8C9E153642");
		System.err.println(str);
	}
}
