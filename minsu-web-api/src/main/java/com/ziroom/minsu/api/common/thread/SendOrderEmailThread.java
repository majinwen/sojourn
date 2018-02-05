/**
 * @FileName: SendOrderEmailThread.java
 * @Package com.ziroom.minsu.services.job.order
 * 
 * @author bushujie
 * @created 2017年4月25日 下午2:05:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.thread;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.EmailRequest;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.order.dto.SendOrderEmailRequest;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>定时发邮件线程</p>
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
public class SendOrderEmailThread implements Runnable{
	
	/**
	 * 发邮件参数
	 */
	private SendOrderEmailRequest sendOrderEmailRequest;
	
	private CustomerMsgManagerService customerMsgManagerService;
	
	private SmsTemplateService smsTemplateService;
	
	
	public SendOrderEmailThread(SendOrderEmailRequest sendOrderEmailRequest,CustomerMsgManagerService customerMsgManagerService,SmsTemplateService smsTemplateService){
		this.sendOrderEmailRequest=sendOrderEmailRequest;
		this.customerMsgManagerService=customerMsgManagerService;
		this.smsTemplateService=smsTemplateService;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		EmailRequest emailRequest=new EmailRequest();
    	emailRequest.setEmailTitle(sendOrderEmailRequest.getEmailTitle());
    	emailRequest.setEmailCode(MessageTemplateCodeEnum.EMAIL_CODE_ORDER.getCode()+"");
    	emailRequest.getParamsMap().put("{1}", sendOrderEmailRequest.getHouseName());
    	emailRequest.getParamsMap().put("{2}", DateUtil.dateFormat(sendOrderEmailRequest.getOrderStartDate(), "yyyy-MM-dd"));
    	emailRequest.getParamsMap().put("{3}", DateUtil.dateFormat(sendOrderEmailRequest.getOrderEndDate(), "yyyy-MM-dd"));
    	emailRequest.getParamsMap().put("{4}", sendOrderEmailRequest.getOrderStatus());
    	if(!Check.NuNObj(sendOrderEmailRequest.getCheckInDate())){
    		emailRequest.getParamsMap().put("{5}", DateUtil.dateFormat(sendOrderEmailRequest.getCheckInDate(), "yyyy年MM月dd日"));
    	} else {
    		emailRequest.getParamsMap().put("{5}", DateUtil.dateFormat(sendOrderEmailRequest.getOrderStartDate(), "yyyy-MM-dd"));
		}
    	if(!Check.NuNObj(sendOrderEmailRequest.getCheckOutDate())){
    		emailRequest.getParamsMap().put("{6}", DateUtil.dateFormat(sendOrderEmailRequest.getCheckOutDate(), "yyyy年MM月dd日"));
    	} else {
    		emailRequest.getParamsMap().put("{6}", DateUtil.dateFormat(sendOrderEmailRequest.getOrderEndDate(), "yyyy-MM-dd"));
		}
    	emailRequest.getParamsMap().put("{7}", sendOrderEmailRequest.getBookName());
    	emailRequest.getParamsMap().put("{8}", sendOrderEmailRequest.getCheckInNum()+"");
    	String customerJson=customerMsgManagerService.getCustomerBaseMsgEntitybyUid(sendOrderEmailRequest.getLandlordUid());
		try {
			CustomerBaseMsgEntity customer=SOAResParseUtil.getValueFromDataByKey(customerJson, "customer", CustomerBaseMsgEntity.class);
			emailRequest.setEmailAddr(customer.getCustomerEmail());
		} catch (SOAParseException e) {
			e.printStackTrace();
		}
		if(!Check.NuNStr(emailRequest.getEmailAddr())){
			smsTemplateService.sendEmailByCode(JsonEntityTransform.Object2Json(emailRequest));
		}
	}

}
