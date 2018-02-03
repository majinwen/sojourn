

package com.ziroom.minsu.services.order.test.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.services.order.entity.PayVo;
import com.ziroom.minsu.services.order.proxy.OrderPayServiceProxy;
import com.ziroom.minsu.services.order.service.OrderPayServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.pay.ToPayTypeEnum;

/**
 * <p>去支付的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public class OrderPayServiceProxyTest extends BaseTest {


    @Resource(name = "order.orderPayServiceProxy")
	private OrderPayServiceProxy orderPayServiceProxy;

    
    @Resource(name = "order.orderPayServiceImpl")
	private OrderPayServiceImpl orderPayServiceImpl;


    @Test
    public void Testvalue() {
        //System.out.println(orderPayServiceImpl.getTest());

        String aa = CloseableHttpUtil.sendPost("https://www.baidu.com/");
        System.out.println(aa);
    }


    @Test
    public void TestPayCallBackByProxy() {
    	String encryption = "CBEhE7cdMaqDcYL4Q8NDII0scmqAbYLDC%2FfsxH2PGfFOqlJaFMehTR0o8bj2iHtmk0C7agVJI0wq4zrQIWyzQ3OSl6mN49cq64UiNcxFEm8NYLAib12PJLwuPcDeiMsyz1IaCQZyttymyf2YlQjHlmH2yJ9a57HfZtqOufZ6THpoUDi8s5bM7dRKoWGsGU3BvtoHZjaAheLGXMiIR5cmu1ZgQM23iSIZR2T%2B16nInOl%2BGyslP4%2BuBZQMhf6geBftSHx3kN5ZQempr%2FEziZ9dATzZR4ibbXNK2JhIfuZ2apL7X9xYrp7MrdoTgLxsqdWLiBm53yOy%2B6RakqcN5NYKhrcK3YnGQoQ3%2BfCMWSFJ7m5v81VTTLtlUIh90xXeM%2FR2CkSpqBlwWvJF9cyaJ1AZaQ%3D%3D";
    	orderPayServiceProxy.payCallBack(encryption, 1);
    }

    
    
//    @Test
//    public void TestGetPayCode() {
//    	ToPayRequest toPayRequest = new ToPayRequest();
//    	toPayRequest.setOrderSn("8a9e9cc253e9938a0153e993952b0081");
//    	toPayRequest.setBizeCode("8a9e9cc223e9428a0153e993952b0081");
//    	toPayRequest.setCityCode("110000");
//    	toPayRequest.setUserUid("8a9e9cc253e9938a0153e9938c100007");
//    	toPayRequest.setTotalFee(3);
//    	toPayRequest.setNotifyUrl("http://www.baidu.com");
//    	toPayRequest.setPayType("zfb");
//    	toPayRequest.setSourceType("ad");
//
//    	PayVo pay = new PayVo();
//    	pay.setPayMoney(1);
//    	pay.setOrderType(1);//外部支付
//
//    	PayVo pay2 = new PayVo();
//    	pay2.setPayMoney(2);
//    	pay2.setOrderType(91);//内部支付
//
//    	List<PayVo> payList = new ArrayList<PayVo>();
//    	payList.add(pay);
//    	payList.add(pay2);
//    	toPayRequest.setPayList(payList);
//
//    	try {
//
////    		String str = orderPayServiceProxy.toPay(JsonEntityTransform.Object2Json(toPayRequest));
////    		System.out.println("==============================="+str);
//
//			Map<String,String> resMap = callPayServiceProxy.createPayOrder(toPayRequest);
//			System.out.println("=========================="+resMap);
//			//String resJson = orderPayServiceImpl.paySubmit(resCode, toPayRequest);
//			//System.out.println("resJson========"+resJson);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//    }
    
    
    @Test
    public void TestCheckToPay(){
    	ToPayRequest toPayApiDto = new ToPayRequest();
    	toPayApiDto.setToPayType(ToPayTypeEnum.NORMAL_PAY.getCode());
		toPayApiDto.setUserUid("uid");

    	// toPayApiDto.setBizeCode("16042847E2OYWL212522");
    	toPayApiDto.setOrderSn("1605042NL8KYP8111215");
     	toPayApiDto.setCityCode("110000");
    	toPayApiDto.setPayType("yljj");
    	toPayApiDto.setSourceType("ad");

    	PayVo pay = new PayVo();
    	pay.setPayMoney(2050);
    	pay.setOrderType(1);//外部支付

    	List<PayVo> payList = new ArrayList<PayVo>();
    	payList.add(pay);
    	toPayApiDto.setPayList(payList);
    	
    	String result = orderPayServiceProxy.checkToPay(JsonEntityTransform.Object2Json(toPayApiDto));
    	System.out.println("result:"+result);
    	DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
    	ToPayRequest toPayRequest  = dto.parseData("toPayRequest", new TypeReference<ToPayRequest>() {});
    	System.out.println("toPayRequest:"+toPayRequest);
    }
    
    
    
    @Test
	public void TestGetAccountBalance() {
		String json = orderPayServiceProxy.getAccountBalance("937d573a-4f25-638b-db9b-f97339e3e5ming-2", 0);
		System.out.println("json:" + json);
	}

	@Test
	public void queryTradeNumByHouseFidTest(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", "2016-11-01");
		paramMap.put("endTime", "2016-11-30");
		String resultJson = this.orderPayServiceProxy.queryTradeNumByHouseFid(JsonEntityTransform.Object2Json(paramMap));
		System.err.println(resultJson);
	}
 
}