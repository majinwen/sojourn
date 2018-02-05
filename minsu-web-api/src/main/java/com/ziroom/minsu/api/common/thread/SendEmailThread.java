/**
 * @FileName: SendEmailThread.java
 * @Package com.ziroom.minsu.api.common.thread
 * 
 * @author bushujie
 * @created 2017年4月22日 下午7:13:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.EmailRequest;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>发送邮件线程</p>
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
public class SendEmailThread implements Runnable{
	
	private HouseManageService houseManageService;
	
	private CustomerMsgManagerService customerMsgManagerService;
	
	private EmailRequest emailRequest;
	
	private String houseFid;
	
	private Integer rentWay;
		
	private String uid;
	
	private SmsTemplateService smsTemplateService; 
	
	private static Logger logger = LoggerFactory.getLogger(SendEmailThread.class);
	
	public SendEmailThread(HouseManageService houseManageService,CustomerMsgManagerService customerMsgManagerService,EmailRequest emailRequest,
			SmsTemplateService smsTemplateService,String houseFid,Integer rentWay,String uid){
		this.houseManageService=houseManageService;
		this.customerMsgManagerService=customerMsgManagerService;
		this.emailRequest=emailRequest;
		this.smsTemplateService=smsTemplateService;
		this.houseFid=houseFid;
		this.rentWay=rentWay;
		this.uid=uid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		try {
			String resultJson=houseManageService.findOrderNeedHouseVo(houseFid, rentWay);
			OrderNeedHouseVo houseVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseBase", OrderNeedHouseVo.class);
			String customerJson=customerMsgManagerService.getCustomerBaseMsgEntitybyUid(houseVo.getLandlordUid());
			CustomerBaseMsgEntity customer=SOAResParseUtil.getValueFromDataByKey(customerJson, "customer", CustomerBaseMsgEntity.class);
			//预订人姓名
			String bookJson=customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
			CustomerBaseMsgEntity book=SOAResParseUtil.getValueFromDataByKey(bookJson, "customer", CustomerBaseMsgEntity.class);
			emailRequest.getParamsMap().put("{7}",book.getRealName());
	    	//LogUtil.info(logger, "发邮件参数线程：{}",JsonEntityTransform.Object2Json(emailRequest));
			if(!Check.NuNStr(customer.getCustomerEmail())&&houseVo.getHouseBaseExtEntity().getOrderType()==OrderTypeEnum.ORDINARY.getCode()){
				emailRequest.setEmailAddr(customer.getCustomerEmail());
				if(rentWay==RentWayEnum.HOUSE.getCode()){
					emailRequest.getParamsMap().put("{1}", houseVo.getHouseName());
				} else if(rentWay==RentWayEnum.ROOM.getCode()) {
					emailRequest.getParamsMap().put("{1}", houseVo.getRoomName());
				}
				emailRequest.setEmailTitle(emailRequest.getParamsMap().get("{1}")+"的"
    			+emailRequest.getParamsMap().get("{2}") +"至"+emailRequest.getParamsMap().get("{3}")+"的订单待您处理");
				String smsJson=smsTemplateService.sendEmailByCode(JsonEntityTransform.Object2Json(emailRequest));
				LogUtil.info(logger, "发邮件结果线程：{}",smsJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
