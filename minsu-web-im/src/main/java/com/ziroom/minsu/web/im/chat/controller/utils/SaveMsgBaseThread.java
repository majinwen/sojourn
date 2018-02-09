/**
 * @FileName: saveMsgBaseThrea.java
 * @Package com.ziroom.minsu.web.im.chat.controller
 * 
 * @author yd
 * @created 2016年9月21日 上午9:31:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.dto.AppChatRecordsDto;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.msg.MsgExtTypeEnum;

/**
 * <p>异步保存消息接口</p>
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
public class SaveMsgBaseThread implements Runnable{
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SaveMsgBaseThread.class);
	
	/**
	 * 环信 im 消息参数
	 */
	private AppChatRecordsDto appChatRecordsDto;
	
	private String basePath;
	
	private String uid;
	
	private MsgHouseService msgHouseService;
	
	private MsgBaseService msgBaseService;

	private SmsTemplateService smsTemplateService;
	
	private CustomerMsgManagerService customerMsgManagerService;
	
	private ShortChainMapService shortChainMapService;

	public SaveMsgBaseThread(){};

	/**
	 * @param appChatRecordsDto
	 * @param basePath
	 * @param uid
	 * @param msgHouseService
	 * @param msgBaseService
	 * @param smsTemplateService
	 * @param customerMsgManagerService
	 * @param shortChainMapService
	 */
	public SaveMsgBaseThread(AppChatRecordsDto appChatRecordsDto, String basePath, String uid,
			MsgHouseService msgHouseService, MsgBaseService msgBaseService, SmsTemplateService smsTemplateService,
			CustomerMsgManagerService customerMsgManagerService, ShortChainMapService shortChainMapService) {
		this.appChatRecordsDto = appChatRecordsDto;
		this.basePath = basePath;
		this.uid = uid;
		this.msgHouseService = msgHouseService;
		this.msgBaseService = msgBaseService;
		this.smsTemplateService = smsTemplateService;
		this.customerMsgManagerService = customerMsgManagerService;
		this.shortChainMapService = shortChainMapService;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		AppChatRecordsDto appChatRecordsDto = this.getAppChatRecordsDto();
		MsgHouseService msgHouseService = this.getMsgHouseService();
		if(!Check.NuNObj(msgHouseService)){
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(msgHouseService.saveImMsg(JsonEntityTransform.Object2Json(appChatRecordsDto)));
			
			LogUtil.info(logger, "【SaveMsgBaseThread】保存实体参数appChatRecordsDto={}，结果dto={}", appChatRecordsDto.toJsonStr(),dto.toJsonString());
			
			//String sendNum = appChatRecordsDto.getSendNum() == null?"1":String.valueOf( appChatRecordsDto.getSendNum());
			if(dto.getCode() == DataTransferObject.SUCCESS){
				String reslut = dto.getData().get("retult").toString();
				MsgHouseEntity msgHouseEntity = dto.parseData("msgHouse", new TypeReference<MsgHouseEntity>() {});
				if(Integer.valueOf(reslut).intValue()>0){
					if(!Check.NuNObj(msgHouseEntity)){
						AppChatRecordsExt appChatRecordsExt = appChatRecordsDto.getAppChatRecordsExt();

                        if (!Check.NuNObj(appChatRecordsExt) && !Check.NuNStr(this.getBasePath())) {
                            //短信中 待调转的url
							int  userType = appChatRecordsDto.getMsgSentType();
							String url = this.getBasePath();
							if(!Check.NuNObj(UserTypeEnum.getUserTypeByCode(userType)) && userType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()){
								 url = url+"imApp/ee5f86/goToApp?toChatUsername="+appChatRecordsDto.getLandlordUid()+"&msgSenderType="+UserTypeEnum.getUserTypeByCode(userType).getUserCode()+"&uid="+appChatRecordsDto.getTenantUid();
							}
							if(!Check.NuNObj(UserTypeEnum.getUserTypeByCode(userType)) && userType == UserTypeEnum.TENANT_HUANXIN.getUserType()){
								 url = url+"imApp/ee5f86/goToApp?toChatUsername="+appChatRecordsDto.getTenantUid()+"&msgSenderType="+UserTypeEnum.getUserTypeByCode(userType).getUserCode()+"&uid="+appChatRecordsDto.getLandlordUid();
							}
							
							// updated by liujun 2016-10-28
							ShortChainMapService shortChainMapService = this.getShortChainMapService();
							String resultJson = shortChainMapService.generateShortLink(url, this.getUid());
							LogUtil.info(logger, "短链生成返回结果:{}", resultJson);
							dto = JsonEntityTransform.json2DataTransferObject(resultJson);
							if (dto.getCode() == DataTransferObject.SUCCESS) {
								String shortLink = (String) dto.getData().get("shortLink");
								//发送短信
								todayFirstChatSendMsg(msgHouseEntity,appChatRecordsDto.getMsgSentType(),shortLink,appChatRecordsExt.getHouseName(),appChatRecordsExt.getMsgType());
							}
						}
						
					}
				}
			}
		}
		
	}

	/**
	 * 说明：
	 * 半小时内无聊天记录就发短信提醒
	 * @author jixd on 2016年7月6日
	 */
	private void todayFirstChatSendMsg(MsgHouseEntity msgHouseEntity,int msgSendTypeInt,String url,String houseName,String msgType){

		try {
			
			if (!Check.NuNStr(msgType) && msgType.equals(String.valueOf(MsgExtTypeEnum.AUTO_REPLY.getCode()))){
				return;
			}
			
			String fid = msgHouseEntity.getFid();
			Date currTime = new Date();
			MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
			String currentDate = DateUtil.dateFormat(currTime,"yyyy-MM-dd HH:mm:ss");
			String hafHourStr = DateUtil.dateFormat(new Date(currTime.getTime()-30*60*1000),"yyyy-MM-dd HH:mm:ss");
			msgBaseRequest.setStartTime(hafHourStr);
			msgBaseRequest.setEndTime(currentDate);
			msgBaseRequest.setMsgSenderType(msgSendTypeInt);
			msgBaseRequest.setMsgHouseFid(fid);
			DataTransferObject countDto = JsonEntityTransform.json2DataTransferObject(this.getMsgBaseService().queryMsgCountByItem(JsonEntityTransform.Object2Json(msgBaseRequest)));
			
			LogUtil.info(logger, "查询IM是否发短信的信息，参数msgBaseRequest={}，返回结果countDto={}", msgBaseRequest.toJsonStr(),countDto.toJsonString());
			if(countDto.getCode() == DataTransferObject.SUCCESS){
				int count = (int)countDto.getData().get("count");
				LogUtil.info(logger, "IM短信发送开始.....,查询返回消息数量count={}", count);
				if(count <= 1){
					String uid = msgHouseEntity.getLandlordUid();
					if(msgSendTypeInt == UserTypeEnum.LANDLORD_HUAXIN.getUserType()){
						uid = msgHouseEntity.getTenantUid();
					}
					//发送短信
					sendMsgIm(uid,msgSendTypeInt,url,houseName);
				}
			}else{
				LogUtil.info(logger, "查询失败countDto={}", countDto.toJsonString());
			}
		} catch (Exception e) {
			LogUtil.error(logger, "IM聊天短信发送失败，聊天记录msgHouseEntity={}，发送类型msgSendTypeInt={}", msgHouseEntity.toString(),msgSendTypeInt);
		}

	}
	

	/**
	 * im 当天第一次聊天发送短信
	 * @author jixd on 2016年7月6日
	 * @param uid 发送人uid
	 * @param userType 用户类型
	 * @url 短信中 待调转url
	 */
	private void sendMsgIm(String uid,int userType,String url,String houseName){
		try {
            houseName = Check.NuNStr(houseName) ? "" : houseName;
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(getCustomerMsgManagerService().getCutomerVo(uid));
			if(customerDto.getCode() == DataTransferObject.SUCCESS){
				CustomerVo customerVo = customerDto.parseData("customerVo", new TypeReference<CustomerVo>() {});

				if(!Check.NuNObj(customerVo)){
					String phone = customerVo.getShowMobile();
					if(Check.NuNStr(phone)){
						return;
					}
					SmsRequest smsRequest = new SmsRequest();
					smsRequest.setMobile(phone);
					Map<String,String> paramsMap = new HashMap<String,String>();
					
					if(!Check.NuNStr(url)){
						paramsMap.put("{1}", houseName);
						paramsMap.put("{2}", url);
					}
					
					smsRequest.setParamsMap(paramsMap);
					
					String msgCode = "";
					if(userType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()){
						msgCode = String.valueOf(MessageTemplateCodeEnum.IM_TENENT_UNREAD_MSG.getCode());
					}
					if(userType == UserTypeEnum.TENANT_HUANXIN.getUserType()){
						msgCode = String.valueOf(MessageTemplateCodeEnum.IM_LANDLORD_UNREAD_MSG.getCode());
					}
					if(!Check.NuNStr(msgCode)){
						smsRequest.setSmsCode(msgCode);
						LogUtil.info(logger, "IM短信发送接口调用开始，参数smsRequest={}", JsonEntityTransform.Object2Json(smsRequest));
						getSmsTemplateService().sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));

					}
				}

			}
		} catch (Exception e) {
			LogUtil.error(logger, "I短信发送异常e={}", e);
		}
	}
	
	public AppChatRecordsDto getAppChatRecordsDto() {
		return appChatRecordsDto;
	}
	
	public void setAppChatRecordsDto(AppChatRecordsDto appChatRecordsDto) {
		this.appChatRecordsDto = appChatRecordsDto;
	}
	
	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public MsgHouseService getMsgHouseService() {
		return msgHouseService;
	}
	
	public void setMsgHouseService(MsgHouseService msgHouseService) {
		this.msgHouseService = msgHouseService;
	}
	
	public MsgBaseService getMsgBaseService() {
		return msgBaseService;
	}
	
	public void setMsgBaseService(MsgBaseService msgBaseService) {
		this.msgBaseService = msgBaseService;
	}
	
	public SmsTemplateService getSmsTemplateService() {
		return smsTemplateService;
	}
	
	public void setSmsTemplateService(SmsTemplateService smsTemplateService) {
		this.smsTemplateService = smsTemplateService;
	}

	public CustomerMsgManagerService getCustomerMsgManagerService() {
		return customerMsgManagerService;
	}

	public void setCustomerMsgManagerService(
			CustomerMsgManagerService customerMsgManagerService) {
		this.customerMsgManagerService = customerMsgManagerService;
	}

	public ShortChainMapService getShortChainMapService() {
		return shortChainMapService;
	}

	public void setShortChainMapService(ShortChainMapService shortChainMapService) {
		this.shortChainMapService = shortChainMapService;
	}
	
}
