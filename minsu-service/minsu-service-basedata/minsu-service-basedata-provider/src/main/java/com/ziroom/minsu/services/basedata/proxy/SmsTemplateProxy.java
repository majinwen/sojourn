/**
 * @FileName: SmsTemplateProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author yd
 * @created 2016年4月1日 下午3:09:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;


import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.common.utils.JsonTransform;

import net.sf.cglib.proxy.CallbackHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.EmailRequest;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.dto.SmsTemplateRequest;
import com.ziroom.minsu.services.basedata.service.SmsTemplateServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.jpush.JpushUtils;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.MailMessage;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.valenum.customer.JpushPersonType;

/**
 * <p>消息模板</p>
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
@Component("basedata.smsTemplateProxy")
public class SmsTemplateProxy implements SmsTemplateService{

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsTemplateProxy.class);

	@Resource(name = "basedata.smsTemplateServiceImpl")
	private SmsTemplateServiceImpl smsTemplateServiceImpl;

	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;

	/**
	 * 
	 * 条件分页查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午3:08:31
	 *
	 * @param smsTemplateRequest
	 * @return
	 */
	@Override
	public String findEntityByCondition(
			String smsTemplateRequest) {

		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(smsTemplateRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		SmsTemplateRequest smsTemplateRe = JsonEntityTransform.json2Object(smsTemplateRequest, SmsTemplateRequest.class);

		PagingResult<SmsTemplateEntity> pagingResult = smsTemplateServiceImpl.findEntityByCondition(smsTemplateRe);
		dto.putValue("listSmsTemplate", pagingResult.getRows());
		dto.putValue("total", pagingResult.getTotal());
		return dto.toJsonString();
	}
	/**
	 * 
	 * 按fid查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param fid
	 * @return
	 */
	@Override
	public String findEntityByFid(String fid) {
		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		dto.putValue("smsTemplateEntity", smsTemplateServiceImpl.findEntityByFid(JsonEntityTransform.json2String(fid)));
		return dto.toJsonString();
	}

	/**
	 * 
	 * 按fid查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param id
	 * @return
	 */
	@Override
	public String findEntityById(String id) {
		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(id)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		dto.putValue("smsTemplateEntity", smsTemplateServiceImpl.findEntityById(Integer.parseInt(JsonEntityTransform.json2String(id))));
		return dto.toJsonString();
	}
	/**
	 * 
	 * 按id删除
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:34:18
	 *
	 * @param fid
	 */
	@Override
	public String deleteEntityByFid(String fid) {
		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}

		dto.putValue("smsTemplateEntity", smsTemplateServiceImpl.deleteEntityByFid(JsonEntityTransform.json2String(fid)));
		return dto.toJsonString();
	}

	/**
	 * 
	 * 按id更新
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:45:17
	 *
	 * @param smsTemplateEntity
	 */
	@Override
	public String updateEntityById(String smsTemplateEntity) {
		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(smsTemplateEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}

		dto.putValue("result", smsTemplateServiceImpl.updateEntityById(JsonEntityTransform.json2Entity(smsTemplateEntity, SmsTemplateEntity.class)));
		return dto.toJsonString();
	}
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:47:47
	 *
	 * @param smsTemplateEntity
	 */
	@Override
	public String saveEntity(String smsTemplateEntity) {

		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(smsTemplateEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		dto.putValue("result", smsTemplateServiceImpl.saveEntity(JsonEntityTransform.json2Entity(smsTemplateEntity, SmsTemplateEntity.class)));

		return dto.toJsonString();
	}


	/**
	 * 通过模板id获取模板
	 * @author afi
	 * @param templateCode
	 * @return
	 */
	public String getTemplateByCode(String templateCode){
		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(templateCode)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		dto.putValue("template", smsTemplateServiceImpl.findEntityBySmsCode(templateCode));
		return dto.toJsonString();
	}


	/**
	 * 
	 * 按fid查询
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:29:55
	 *
	 * @param smsCode
	 * @smsCode
	 */
	@Override
	public String findEntityBySmsCode(String smsCode){
		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(smsCode)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		dto.putValue("smsTemplateEntity", smsTemplateServiceImpl.findEntityBySmsCode(smsCode));
		return dto.toJsonString();
	}



	/**
	 * 
	 * 按id删除
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:45:17
	 *
	 * @param id
	 */
	@Override
	public String deleteEntityById(String id) {
		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(id)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		dto.putValue("result", smsTemplateServiceImpl.deleteEntityById(Integer.parseInt(JsonEntityTransform.json2String(id))));
		return dto.toJsonString();
	}
	/**
	 * 
	 * 按id更新
	 *
	 * @author yd
	 * @created 2016年4月1日 下午2:45:17
	 *
	 * @param smsTemplateEntity
	 */
	@Override
	public String updateEntityByFid(String smsTemplateEntity) {
		DataTransferObject dto = new DataTransferObject();
		//非空校验
		if(Check.NuNObj(smsTemplateEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}

		SmsTemplateEntity smsTemplate = JsonEntityTransform.json2Entity(smsTemplateEntity, SmsTemplateEntity.class);
		dto.putValue("result", smsTemplateServiceImpl.updateEntityByFid(smsTemplate));
		return dto.toJsonString();
	}


	/**
	 * 
	 * 按模板code发送(说明：要到消息模板中添加模板，并在枚举中添加code值，code值不能重复)
	 * 1.校验
	 * 2.按code查询模板，并获取模板内容
	 * 3.发送
	 *
	 * @author yd
	 * @created 2016年5月10日 上午12:29:03
	 *
	 * @param smsRequestStr
	 * @return
	 */
	@Override
	public String sendSmsByCode(String smsRequestStr){

		DataTransferObject dto = new DataTransferObject();

		SmsRequest smsRequest = JsonEntityTransform.json2Object(smsRequestStr, SmsRequest.class);

		if(Check.NuNObj(smsRequest)||Check.NuNStr(smsRequest.getSmsCode())
				||Check.NuNStr(smsRequest.getMobile())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数不存在或模板code不存在或者手机号错误");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "短信发送参数smsRequest={}", smsRequest.toString());
		SmsTemplateEntity smsTemplateEntity = smsTemplateServiceImpl.findEntityBySmsCode(smsRequest.getSmsCode());
		if(Check.NuNObj(smsTemplateEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("按照模板code={"+smsRequest.getSmsCode()+"}查询，结果不存在");
			return dto.toJsonString();
		}
		Map<String, String> paramsMap = smsRequest.getParamsMap();
		LogUtil.info(LOGGER, "短信发送手机号mobile={},发送内容smsContent={},发送参数paramsMap={}", smsRequest.getMobile(),smsTemplateEntity.getSmsComment(),paramsMap);
		String  mobileNationCode = null;
		if(Check.NuNMap(paramsMap) || Check.NuNStr(paramsMap.get(SysConst.MOBILE_NATION_CODE_KEY))){
			mobileNationCode = SysConst.MOBILE_NATION_CODE;
		}else{
			mobileNationCode = paramsMap.get(SysConst.MOBILE_NATION_CODE_KEY);
		}
		//String  mobileNationCode =Check.NuNStr(paramsMap.get(SysConst.MOBILE_NATION_CODE_KEY))?SysConst.MOBILE_NATION_CODE:paramsMap.get(SysConst.MOBILE_NATION_CODE_KEY);
		SmsMessage smsMessage = new SmsMessage(smsRequest.getMobile(),smsTemplateEntity.getSmsComment(),mobileNationCode);
		MessageUtils.sendSms(smsMessage, paramsMap); 
		return dto.toJsonString();
	}


	/**
	 * 
	 * 按照模板code推送
	 *
	 * @author yd
	 * @created 2016年5月11日 下午1:55:47
	 *
	 * @param jpushRequestStr
	 * @return
	 */
	@Override
	public String jpushByCode(String jpushRequestStr){

		DataTransferObject dto = new DataTransferObject();

		JpushRequest jpushRequest = JsonEntityTransform.json2Object(jpushRequestStr, JpushRequest.class);

		if(Check.NuNObj(jpushRequest)||Check.NuNObj(jpushRequest.getJpushPersonType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("推送参数不存在，或者推送人不明确");
			return dto.toJsonString();
		}
		//校验推送人  当为 多个时候 list不能为null  为单个时  uid不能为null

		int jpushPersonType = jpushRequest.getJpushPersonType().intValue();
		if(jpushPersonType ==JpushPersonType.ONE.getCode()&&Check.NuNStr(jpushRequest.getUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("单个推送，uid不存在");
			return dto.toJsonString();
		}
		if(jpushPersonType ==JpushPersonType.MANY.getCode()&&Check.NuNCollection(jpushRequest.getListUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("给多人推送，ListUid不存在");
			return dto.toJsonString();
		}
		JpushConfig jpushConfig = new JpushConfig();
		jpushConfig.setMessageType(jpushRequest.getMessageType());
		jpushConfig.setTitle(jpushRequest.getTitle());
		//内容不为null直接推送
		if(!Check.NuNStr(jpushRequest.getContent())){
			jpushConfig.setContent(jpushRequest.getContent());
		}else{

			if(Check.NuNStr(jpushRequest.getSmsCode())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("模板smsCode不存在");
				return dto.toJsonString();
			}
			LogUtil.info(LOGGER, "推送参数jpushRequest={}", JsonTransform.Object2Json(jpushRequest));
			SmsTemplateEntity smsTemplateEntity = smsTemplateServiceImpl.findEntityBySmsCode(jpushRequest.getSmsCode());
			if(Check.NuNObj(smsTemplateEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("按照模板code={"+jpushRequest.getSmsCode()+"}查询，结果不存在");
				return dto.toJsonString();
			}
			jpushConfig.setContent(smsTemplateEntity.getSmsComment());
			if(Check.NuNStr(jpushConfig.getTitle())){
				jpushConfig.setTitle(smsTemplateEntity.getSmsName());
			}
		}
		//添加额外参数
		if(!Check.NuNObj(jpushRequest.getMessageType())
				&&jpushRequest.getMessageType().intValue() == MessageTypeEnum.MESSAGE.getCode()
				&&!Check.NuNMap(jpushRequest.getExtrasMap())){
			jpushConfig.setExtrasMap(jpushRequest.getExtrasMap());

		}

		LogUtil.info(LOGGER, "极光推送请求参数jpushConfig={}", JsonEntityTransform.Object2Json(jpushConfig));
		jpushSms(jpushConfig, jpushRequest.getParamsMap(), jpushRequest);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 按人群类型推送
	 *
	 * @author yd
	 * @created 2016年5月11日 下午2:13:13
	 *
	 * @param jpushConfig
	 * @param paramsMap
	 * @param jpushRequest
	 */
	private void jpushSms(JpushConfig jpushConfig,Map<String, String> paramsMap,JpushRequest jpushRequest ){

		if(!Check.NuNObj(jpushConfig)&&!Check.NuNObjs(jpushRequest)&&!Check.NuNStr(jpushConfig.getContent())){
			int jpushPersonType  = jpushRequest.getJpushPersonType().intValue();
			if(jpushPersonType == JpushPersonType.ALL.getCode()){
				JpushUtils.sendPushAll(jpushConfig, paramsMap);
			}
			if(jpushPersonType == JpushPersonType.ONE.getCode()){
				JpushUtils.sendPushOne(jpushRequest.getUid(), jpushConfig, paramsMap);
			}
			if(jpushPersonType == JpushPersonType.MANY.getCode()){
				JpushUtils.sendPushMany(jpushRequest.getListUid(), jpushConfig, paramsMap);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService#sendEmailByCode(java.lang.String)
	 */
	@Override
	public String sendEmailByCode(String emailRequestStr) {
		//LogUtil.info(LOGGER, "邮件发送参数smsRequest={}", emailRequestStr);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(emailRequestStr)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数不存在");
			return dto.toJsonString();
		}

		EmailRequest emailRequest=JsonEntityTransform.json2Object(emailRequestStr, EmailRequest.class);

		if(Check.NuNStr(emailRequest.getEmailCode())
				||Check.NuNStr(emailRequest.getEmailAddr())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("模板code不存在或者邮箱地址为空");
			return dto.toJsonString();
		}
		SmsTemplateEntity smsTemplateEntity = smsTemplateServiceImpl.findEntityBySmsCode(emailRequest.getEmailCode());
		if(Check.NuNObj(smsTemplateEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("按照模板code={"+emailRequest.getEmailCode()+"}查询，结果不存在");
			return dto.toJsonString();
		}
		MailMessage mailMessage = new MailMessage(emailRequest.getEmailAddr(),smsTemplateEntity.getSmsComment() ,emailRequest.getEmailTitle());
		//LogUtil.info(LOGGER, "邮件主题{}", JsonEntityTransform.Object2Json(mailMessage));
		try {
			MessageUtils.sendMail(mailMessage, emailRequest.getParamsMap());
		} catch (Exception e) {
			LogUtil.info(LOGGER, "邮件发送异常{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("邮件发送异常");
		}
		return dto.toJsonString();
	}
}
