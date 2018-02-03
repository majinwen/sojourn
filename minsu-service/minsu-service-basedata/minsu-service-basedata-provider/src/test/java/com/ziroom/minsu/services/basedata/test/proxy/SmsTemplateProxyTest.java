
package com.ziroom.minsu.services.basedata.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.proxy.SmsTemplateProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>消息模板测试</p>
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



	@Resource(name="basedata.smsTemplateProxy")
	private SmsTemplateProxy smsTemplateProxy;



    @Test
    public void findEntityBySmsCodeTest(){
    String smsTemplateEntity = this.smsTemplateProxy.findEntityBySmsCode("{aa:123456789789798789}");
    if(smsTemplateEntity != null) {
        System.out.println(smsTemplateEntity);
    }}


    @Test
	public void testJpushByCode() throws InterruptedException {
		
	/*	JpushRequest jpushRequest =  new JpushRequest();
		jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
		jpushRequest.setUid("HAHAHHAHH");
		jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.TENANT_EVALUATE.getCode()));
		Map<String, String> map = new HashMap<String, String>();
		map.put("{1}", "yagnd");
		map.put("{2}", "杨东");
		jpushRequest.setParamsMap(map);*/
		
		

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("{1}",  "我的房源名称9944");
		
		JpushRequest jpushRequest = new JpushRequest();
		jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
		jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
		jpushRequest.setParamsMap(paramsMap);
		jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_ONLINE.getCode()));
		jpushRequest.setTitle(MessageTemplateCodeEnum.HOUSE_ONLINE.getName());
		jpushRequest.setUid("0f163457-ad6a-09ce-d5de-de452a251cf6");

		Map<String, String> extrasMap = new HashMap<String, String>();
		extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
		extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
		extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"0");
		extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
		extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));

		jpushRequest.setExtrasMap(extrasMap);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smsTemplateProxy.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest)));
		System.out.println(dto.toJsonString());
		
		Thread.sleep(10000);
	}
    
    @Test
	public void sendSmsByCode() throws InterruptedException {
		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setMobile("182016939966");
		Map<String, String> paramsMap=new HashMap<String, String>();
		paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
		smsRequest.setParamsMap(paramsMap);
		String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_NOT_CONTACT_LANDLORD_SMS.getCode());
		smsRequest.setSmsCode(String.valueOf(msgCode));
		smsTemplateProxy.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
	}
}
