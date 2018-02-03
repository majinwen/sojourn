/**
 * @FileName: SmsAuthLogServiceImplProxy.java
 * @Package com.ziroom.minsu.services.customer.proxy
 * 
 * @author bushujie
 * @created 2016年4月22日 上午11:15:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.customer.SmsAuthLogEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerAuthService;
import com.ziroom.minsu.services.customer.constant.CustomerMessageConst;
import com.ziroom.minsu.services.customer.dto.ContactAuthDto;
import com.ziroom.minsu.services.customer.dto.IdentityAuthDto;
import com.ziroom.minsu.services.customer.dto.SmsAuthLogDto;
import com.ziroom.minsu.services.customer.logic.ParamCheckLogic;
import com.ziroom.minsu.services.customer.logic.ValidateResult;
import com.ziroom.minsu.services.customer.service.CustomerBaseMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerPicMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.SmsAuthLogServiceImpl;

/**
 * <p>客户认证服务代理层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Component("customer.customerAuthServiceProxy")
public class CustomerAuthServiceProxy implements CustomerAuthService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAuthServiceProxy.class);

	@Resource(name="customer.smsAuthLogServiceImpl")
	private SmsAuthLogServiceImpl smsAuthLogServiceImpl;

	@Resource(name="customer.customerBaseMsgServiceImpl")
	private CustomerBaseMsgServiceImpl customerBaseMsgServiceImpl;

	@Resource(name="customer.customerPicMsgServiceImpl")
	private CustomerPicMsgServiceImpl customerPicMsgServiceImpl;

	@Resource(name="customer.messageSource")
	private MessageSource messageSource;

	@Resource(name="customer.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerAuthService#saveCustomerAuth()
	 */
	@Override
	public String saveSmsAuthLog(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			SmsAuthLogEntity smsAuthLogEntity=JsonEntityTransform.json2Entity(paramJson, SmsAuthLogEntity.class);
			smsAuthLogEntity.setFid(UUIDGenerator.hexUUID());
			smsAuthLogEntity.setTimeoutDate(DateUtil.intervalDate(20, IntervalUnit.MINUTE));
			smsAuthLogServiceImpl.insertSmsAuthLog(smsAuthLogEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerAuthService#customerMobileAuth(java.lang.String)
	 */
	@Override
	public String customerContactAuth(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		//参数通用验证
		ValidateResult<ContactAuthDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, ContactAuthDto.class);
		if (!validateResult.isSuccess()) {
			return validateResult.getDto().toJsonString();
		}
		try{
			ContactAuthDto contactAuthDto=validateResult.getResultObj();
			//验证手机号和验证码
			if(!smsAuthLogServiceImpl.findMobileVerifyResult(contactAuthDto.getMobileNo(), contactAuthDto.getAuthCode())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.MOBILENO_AUTH_ERROR));
				return dto.toJsonString();
			} else {
				CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
				customerBaseMsgEntity.setCustomerMobile(contactAuthDto.getMobileNo());
				customerBaseMsgEntity.setUid(contactAuthDto.getUid());
				customerBaseMsgServiceImpl.updateCustomerInfo(customerBaseMsgEntity);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 判断当前手机验证码是否可用
	 *
	 * @author yd
	 * @created 2016年5月10日 下午10:46:22
	 *
	 * @param contactAuthDtoStr
	 * @return
	 */
	@Override
	public String findMobileVerifyResult(String smsAuthLogDtoStr){
		DataTransferObject dto = new DataTransferObject();
		SmsAuthLogDto SmsAuthLogDto= JsonEntityTransform.json2Object( smsAuthLogDtoStr, SmsAuthLogDto.class);

		if(Check.NuNObj(SmsAuthLogDto)||Check.NuNStr(SmsAuthLogDto.getAuthCode())
				||Check.NuNStr(SmsAuthLogDto.getMobileNo())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
			dto.setMsg("手机号或者验证码不存在");
			return dto.toJsonString();
		}
		if(!smsAuthLogServiceImpl.findMobileVerifyResult(SmsAuthLogDto.getMobileNo(), SmsAuthLogDto.getAuthCode())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.MOBILENO_AUTH_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerAuthService#customerIdentityAuth(java.lang.String)
	 */
	@Override
	public String customerIdentityAuth(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		//参数通用验证
		ValidateResult<IdentityAuthDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, IdentityAuthDto.class);
		if (!validateResult.isSuccess()) {
			return validateResult.getDto().toJsonString();
		}
		try{
			IdentityAuthDto identityAuthDto=validateResult.getResultObj();
			CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
			customerBaseMsgEntity.setRealName(identityAuthDto.getRealName());
			customerBaseMsgEntity.setIdType(identityAuthDto.getIdType());
			customerBaseMsgEntity.setIdNo(identityAuthDto.getIdNo());
			customerBaseMsgEntity.setUid(identityAuthDto.getUid());
			customerBaseMsgServiceImpl.updateCustomerInfo(customerBaseMsgEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerAuthService#customerIconAuth(java.lang.String)
	 */
	@Override
	public String customerIconAuth(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			List<CustomerPicMsgEntity> picList=JsonEntityTransform.json2ObjectList(paramJson, CustomerPicMsgEntity.class);
			for(CustomerPicMsgEntity pic:picList){
				//判断此类型图片是否存在
				CustomerPicMsgEntity customerPicMsgEntity=customerPicMsgServiceImpl.getCustomerPicByType(pic.getUid(), pic.getPicType());
				if(customerPicMsgEntity!=null){
					pic.setFid(customerPicMsgEntity.getFid());
					customerPicMsgServiceImpl.updateCustomerPicMsg(pic);
					dto.putValue("picServerUuid", customerPicMsgEntity.getPicServerUuid());
				} else {
					customerPicMsgServiceImpl.insertCustomerPicMsg(pic);
				}
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
