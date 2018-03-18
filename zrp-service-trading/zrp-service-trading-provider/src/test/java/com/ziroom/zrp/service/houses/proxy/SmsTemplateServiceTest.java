package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class SmsTemplateServiceTest extends BaseTest {

    @Resource(name="basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;


    @Test
    public void testSendSmsByCode() {
        String smsCode = "200001";
        String phone = "13260487433";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("{1}", "http://a.ziroom.com/ssdddss");
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setSmsCode(smsCode);
        smsRequest.setMobile(phone);
        smsRequest.setParamsMap(paramMap);

        //result:{"code":0,"msg":"","data":{}}
        String result = smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        System.out.println("result:" + result);
    }

    @Test
    public void testJpushByCode() {
        String smsCode = "210001";
        String uid = "8fdc471a-536c-6ab6-a334-1f8f16fc690f";
        String title = "测试";
        Map<String, String> paramMap = new HashMap<>();
        //发送极光推送
        JpushRequest jpushRequest = new JpushRequest();
        jpushRequest.setParamsMap(paramMap);
        jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
        jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
        jpushRequest.setSmsCode(smsCode);
        jpushRequest.setTitle(title);
        jpushRequest.setUid(uid);

        //自定义消息
        Map<String, String> extrasMap = new HashMap<>();
        extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, "minsu_notify");
        extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
        extrasMap.put(JpushConst.MSG_HAS_RESPONSE, "1");
        extrasMap.put(JpushConst.MSG_PUSH_TIME, String.valueOf(System.currentTimeMillis()));
        extrasMap.put("msg_biz_params", null);
        jpushRequest.setExtrasMap(extrasMap);

        String result = smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
        System.out.println("result:" +  result);
    }


}
