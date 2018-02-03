/**
 * @FileName: SmsTemplateProxyTest.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author yd
 * @created 2016年4月2日 下午2:23:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.api.inner;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.dto.EmailRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.dto.SmsTemplateRequest;
import com.ziroom.minsu.services.basedata.proxy.SmsTemplateProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.MailMessage;
import com.ziroom.minsu.services.common.utils.randomUtil;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>代理层测试</p>
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
public class SmsTemplateProxyTest extends BaseTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsTemplateProxyTest.class);

	@Resource(name = "basedata.smsTemplateProxy")
	private SmsTemplateProxy smsTemplateProxy;
	@Test
	public void findEntityByConditionTest() {

		SmsTemplateRequest smsTemplateRequest = new SmsTemplateRequest();

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smsTemplateProxy.findEntityByCondition(JsonEntityTransform.Object2Json(smsTemplateRequest)));

		if(dto != null){
			System.out.println(dto.getData().get("listSmsTemplate"));
		}
	}

	@Test
	public void findEntityByIdTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smsTemplateProxy.findEntityById(JsonEntityTransform.Object2Json(2)));

		if(dto!=null){
			System.out.println(dto.getData().get("smsTemplateEntity"));
		}
	}

	@Test
	public void deleteEntityByIdTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smsTemplateProxy.deleteEntityById(JsonEntityTransform.Object2Json(2)));

		if(dto!=null){
			System.out.println(dto.getData().get("result"));
		}

	}

	@Test
	public void saveEntityTest(){
		SmsTemplateEntity smsTemplateEntity = new SmsTemplateEntity();

		smsTemplateEntity.setCreateFid("123487"+(new Date().getTime()));
		smsTemplateEntity.setCreateTime(new Date());
		smsTemplateEntity.setSmsCode("123456");
		smsTemplateEntity.setSmsEnabled(0);
		smsTemplateEntity.setCreateFid("456465");
		smsTemplateEntity.setSmsComment("你好，感谢您！");
		smsTemplateEntity.setSmsName("yangdong");
		smsTemplateEntity.setFid("4897987"+(new Date().getTime()));

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smsTemplateProxy.saveEntity(JsonEntityTransform.Object2Json(smsTemplateEntity)));

		if(dto!=null){
			System.out.println(dto.getData().get("result"));
		}

	}

	@Test
	public void updateEntityByIdTest(){

		SmsTemplateEntity smsTemplateEntity =  new SmsTemplateEntity();
		smsTemplateEntity.setId(2);
		smsTemplateEntity.setCreateFid("789778978"+(new Date().getTime()));
		smsTemplateEntity.setSmsCode("4564789798");
		smsTemplateEntity.setSmsEnabled(1);
		smsTemplateEntity.setCreateFid("456465");
		smsTemplateEntity.setSmsComment("你好，感谢您！");
		smsTemplateEntity.setSmsName("yangdong");
		smsTemplateEntity.setFid("fdsafdf"+(new Date().getTime()));
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smsTemplateProxy.updateEntityById(JsonEntityTransform.Object2Json(smsTemplateEntity)));

		if(dto!=null){
			System.out.println(dto.getData().get("result"));
		}

	}

	@Test
	public void updateEntityByFidTest(){
		SmsTemplateEntity smsTemplateEntity =  new SmsTemplateEntity();
		smsTemplateEntity.setCreateFid("789778978"+(new Date().getTime()));
		smsTemplateEntity.setSmsCode("456478fdsfsdf9798");
		smsTemplateEntity.setSmsEnabled(1);
		smsTemplateEntity.setCreateFid("4564fdsfdsf65");
		smsTemplateEntity.setSmsComment("你好，感谢您！");
		smsTemplateEntity.setSmsName("yangdong");
		smsTemplateEntity.setFid("48979871459578747009");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smsTemplateProxy.updateEntityByFid(JsonEntityTransform.Object2Json(smsTemplateEntity)));

		if(dto!=null){
			System.out.println(dto.getData().get("result"));
		}

	}
	@Test
	public void findEntityBySmsCodeTest() throws SOAParseException{
		String smsTemplateEntity = this.smsTemplateProxy.findEntityBySmsCode("mail_101");
		//测试发送邮件
		SmsTemplateEntity smsTemplate=SOAResParseUtil.getValueFromDataByKey(smsTemplateEntity, "smsTemplateEntity", SmsTemplateEntity.class);
		MailMessage mailMessage = new MailMessage("420976724@qq.com", smsTemplate.getSmsComment(),"自如民宿");
		MessageUtils.sendMail(mailMessage, null);
		try {
			Thread.sleep(999999999);
		} catch (InterruptedException e) {
			
		}
	}


	@Test
	public  void sendSmsByCodeTest(){


		//去获取短信获取验证码动态码
		String randomNum = randomUtil.getNumrOrChar(6, "num");
		SmsRequest smsRequest  = new SmsRequest();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("{1}", randomNum);
		smsRequest.setMobile("18701482472");
		smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.MOBILE_CODE.getCode()));
		smsRequest.setParamsMap(paramsMap);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.smsTemplateProxy.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
		System.out.println(dto);
		
		try {
			Thread.sleep(999999999);
		} catch (InterruptedException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}
	
	@Test
	public void sendEmailByCodeTest(){
		EmailRequest emailRequest=new EmailRequest();
		emailRequest.setEmailAddr("420976724@qq.com");
		emailRequest.setEmailCode("mail_101");
		emailRequest.setEmailTitle("自如民宿订单提醒");
    	Map<String, String> emailPar = new HashMap<>();
    	emailPar.put("{1}", "我是一个大房子啊啊啊啊啊啊");
    	emailPar.put("{2}", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
    	emailPar.put("{3}", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
    	emailPar.put("{4}", OrderStatusEnum.getOrderStatusByCode(10).getShowName());
    	emailPar.put("{5}", DateUtil.dateFormat(new Date(), "yyyy年MM月dd日"));
    	emailPar.put("{6}", DateUtil.dateFormat(new Date(), "yyyy年MM月dd日"));
    	emailPar.put("{7}", "卜书杰");
    	emailPar.put("{8}", 8+"");
    	emailRequest.setParamsMap(emailPar);
    	smsTemplateProxy.sendEmailByCode(JsonEntityTransform.Object2Json(emailRequest));
	}
}
