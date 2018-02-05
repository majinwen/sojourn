package com.ziroom.minsu.api.test.order.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.order.dto.OrderApiRequest;
import com.ziroom.minsu.api.order.dto.ToPayApiDto;
import com.ziroom.minsu.api.order.service.CallPayService;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.services.order.entity.PayVo;
import com.ziroom.minsu.valenum.pay.ToPayTypeEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class PayOrderControllerTest {

	@Resource(name = "pay.callPayService")
	private CallPayService callPayService;
	
	
	public static void toPayTest(){
		ToPayApiDto toPayApiDto = new ToPayApiDto();
		toPayApiDto.setToPayType(ToPayTypeEnum.NORMAL_PAY.getCode());
		toPayApiDto.setUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
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
    	
		String json = JsonEntityTransform.Object2Json(toPayApiDto);
		
		System.out.println("json" + json);
		
		IEncrypt encrypt = EncryptFactory.createEncryption("DES");
		String ddd = encrypt.encrypt(json);
		//参数加密
		System.out.println("2y5QfvAy:"+ ddd);
		
		String sign = MD5Util.MD5Encode(json,"UTF-8");
		//md5 签名
		System.out.println("hPtJ39Xs:"+sign);
		
		
		
		String result ="b29f8942196125a57aaf20c96d255d6dd229a9b7855e13d4dd5cb805c7235d43b2dc1ada027a60d2b70bf55bb95ca7e2283ff73b6fd18120e8190815bdeb73b34ce480f04fb68729";
		System.err.println(encrypt.decrypt(result));
	}
	
	public static void main(String[] args) throws ParseException {
		OrderApiRequest a = new OrderApiRequest();
		a.setLoginToken("360a6c93-42aa-4f5d-ba3e-0e1e24433286");
		a.setUid("1038e77a-b7da-8634-7278-68cc34970690");
		a.setOrderSn("1609289TMEPO20175045");
		//String json = "{\"loginToken\": \"360a6c93-42aa-4f5d-ba3e-0e1e24433286\",\"uid\": \"1038e77a-b7da-8634-7278-68cc34970690\",\"orderSn\": \"1609289TMEPO20175045\"}";
		String json = JsonEntityTransform.Object2Json(a);
		
		IEncrypt encrypt = EncryptFactory.createEncryption("DES");
		String ddd = encrypt.encrypt(json);
		//参数加密
		System.out.println("2y5QfvAy:"+ ddd);
		
		String sign = MD5Util.MD5Encode(json,"UTF-8");
		//md5 签名
		System.out.println("hPtJ39Xs:"+sign);
		
		
		String result ="b29f8942196125a5513afebbf051b09b8636262133d443304e616d188fb75b24d8a645bac1fbaf8294defbc7250b48777f1af18d492c79ab";
		System.err.println(encrypt.decrypt(result));
	}
	
	
	@Test
	public void TestGetPayCode() throws Exception {
		//微信ios 微信安卓ad  微信扫码ios 微信扫码安卓ad 银联信用卡安卓 ad 银联信用卡 ios  银联借记安卓 ad 银联借记 ios 银联贷记安卓 ad 银联贷记 ios  
		ToPayRequest toPayRequest = new ToPayRequest();
		List<PayVo> payList = new ArrayList<PayVo>();
		PayVo payVo = new PayVo();
		payVo.setOrderType(1);
		payVo.setPayMoney(906);
		payList.add(payVo);

		toPayRequest.setBizeCode("160511854886TP130218");
		toPayRequest.setCityCode("111111");
		toPayRequest.setOrderSn("160511854886TP130218");
		toPayRequest.setSourceType("ad");
		toPayRequest.setPayType("yljj");
		toPayRequest.setUserUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		toPayRequest.setPayList(payList);
		toPayRequest.setTotalFee(906);
		toPayRequest.setExpiredDate(1200l);
		toPayRequest.setNotifyUrl("tttt");
		toPayRequest.setPassAccount(0);
		Map<String,String> retsult = callPayService.createPayOrder(toPayRequest);
		System.out.println("000000000000000000000000000retsult:" + JsonEntityTransform.Object2Json(retsult));
		String payCode = retsult.get("data");
		Map<String,Object> retsult2 =  callPayService.paySubmit(payCode,toPayRequest);
		System.out.println("000000000000000000000000000retsult:" + JsonEntityTransform.Object2Json(retsult2));
	}
}
