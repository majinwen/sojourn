package com.ziroom.minsu.services.order.send;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.jpush.JpushUtils;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.SendService;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Map;

/**
 * <p>通知和短信同步进行</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/12.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "order.smsAndJpushSendService")
public class SmsAndJpushSendService implements SendService<Integer,Map<String,String>,Map<String,String>,Map<String,String>> {

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsAndJpushSendService.class);

	/**
	 * 发送消息
	 * @author afi
	 * @param template
	 * @param pushPar  uid
	 * @param smsPar phone   此参数放国际码：key=mobileNationCodeKey
	 * @return
	 */
	@Override
	public boolean send(Integer template,Map<String,String> pushPar, Map<String, String> smsPar,Map<String,String> bizPar) {
		boolean flag = false;

		try {
			if(Check.NuNObjs(pushPar,template)){
				LogUtil.error(LOGGER,"par is empty pushPar:{},template:{},par:{}",JsonEntityTransform.Object2Json(pushPar),template,JsonEntityTransform.Object2Json(smsPar));
				return false;
			}
			//调用短信模板
			String templateJson = smsTemplateService.getTemplateByCode(ValueUtil.getStrValue(template));
			DataTransferObject templateJsonDto = JsonEntityTransform.json2DataTransferObject(templateJson);
			SmsTemplateEntity smsTemplateEntity = templateJsonDto.parseData("template", new TypeReference<SmsTemplateEntity>() {
			});
			if(Check.NuNObj(smsTemplateEntity)){
				LogUtil.info(LOGGER, "smsTemplateEntity is empty the template is",template);
				return false;
			}
			//获取短信内容
			if(Check.NuNStr(smsTemplateEntity.getSmsComment())){
				LogUtil.info(LOGGER, "smsComment is empty the template is",template);
				return false;
			}
			String  mobileNationCode =Check.NuNStr(smsPar.get(SysConst.MOBILE_NATION_CODE_KEY))?SysConst.MOBILE_NATION_CODE:smsPar.get(SysConst.MOBILE_NATION_CODE_KEY);
			//直接发送短息
			MessageUtils.sendSms(new SmsMessage(smsPar.get("phone"), smsTemplateEntity.getSmsComment(),mobileNationCode), ValueUtil.cleanMap(smsPar));
			//直接发送推送
			JpushConfig jpushConfig = new JpushConfig();

			//带参数的为 自定义消息类型，其他的为通知类型
			jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
			jpushConfig.setExtrasMap(bizPar);
			if(!bizPar.containsKey(JpushConst.MSG_BODY_TYPE_KEY)){
				bizPar.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
				bizPar.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
			}
			jpushConfig.setTitle(bizPar.get(JpushConst.MSG_TITLE));
		jpushConfig.setContent(smsTemplateEntity.getSmsComment());
		LogUtil.info(LOGGER, "开始发送推送 参数：uid:{},jpushConfig:{}，bizpar:{}",pushPar.get("uid"),JsonEntityTransform.Object2Json(jpushConfig),JsonEntityTransform.Object2Json(pushPar) );
		JpushUtils.sendPushOne(pushPar.get("uid"), jpushConfig, ValueUtil.cleanMap(pushPar));
		flag = true;
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
		}
		return flag;
	}

}
