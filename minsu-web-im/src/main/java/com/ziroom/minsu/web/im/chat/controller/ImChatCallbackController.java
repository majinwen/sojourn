/**
 * @FileName: ImChatCallbackController.java
 * @Package com.ziroom.minsu.web.im.chat.controller
 * 
 * @author yd
 * @created 2017年4月5日 上午9:37:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.AuthIdentifyEntity;
import com.ziroom.minsu.entity.message.HuanxinImOfflineEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.entity.message.MsgBaseOfflineEntity;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.services.basedata.api.inner.AuthIdentifyService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.message.api.inner.HuanxinImManagerService;
import com.ziroom.minsu.services.message.api.inner.HuanxinImRecordService;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.api.inner.MsgUserLivenessService;
import com.ziroom.minsu.services.message.dto.AppChatRecordsDto;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;
import com.ziroom.minsu.services.message.dto.HuanxinImRecordDto;
import com.ziroom.minsu.services.message.dto.PeriodHuanxinRecordRequest;
import com.ziroom.minsu.services.message.dto.SendImMsgRequest;
import com.ziroom.minsu.services.message.dto.ShareHouseMsg;
import com.ziroom.minsu.services.message.entity.ImExtForChangzuVo;
import com.ziroom.minsu.valenum.base.AuthIdentifyEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.ImForTxtMsgTypeEnum;
import com.ziroom.minsu.valenum.msg.ImTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;
import com.ziroom.minsu.valenum.msg.MsgUserRelSCreateTypeEnum;
import com.ziroom.minsu.valenum.msg.MsgUserRelSourceTypeEnum;
import com.ziroom.minsu.valenum.msg.TargetTypeEnum;
import com.ziroom.minsu.web.im.chat.controller.dto.HuanXinResponse;
import com.ziroom.minsu.web.im.chat.controller.utils.SaveHuanxinOfflineThread;
import com.ziroom.minsu.web.im.chat.controller.utils.SaveMsgBaseThread;
import com.ziroom.minsu.web.im.chat.controller.utils.SaveMsgOfflineThread;
import com.ziroom.minsu.web.im.common.utils.DtoToResponseDto;

/**
 * <p>IM聊天回调</p>
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
@Controller
@RequestMapping("/im")
public class ImChatCallbackController {


	/**
	 * 日志对象
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ImChatCallbackController.class);

	@Resource(name = "message.msgHouseService")
	private MsgHouseService msgHouseService;

	@Resource(name = "message.msgBaseService")
	private MsgBaseService msgBaseService;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name="cms.shortChainMapService")
	private ShortChainMapService shortChainMapService;

	@Value("#{'${IM_MINSU_FLAG}'.trim()}")
	private String IM_MINSU_FLAG;

	@Value("#{'${IM_DOMAIN_FLAG}'.trim()}")
	private String IM_DOMAIN_FLAG;

	@Value("#{'${HUANXIN_CHAT_REQUEST_SECRET}'.trim()}")
	private String HUANXIN_CHAT_REQUEST_SECRET;

	@Value("#{'${HUANXIN_CHAT_RESPONSE_SECRET}'.trim()}")
	private String HUANXIN_CHAT_RESPONSE_SECRET;

	@Value("#{'${HUANXIN_CHAT_OFFLINE_REQUEST_SECRET}'.trim()}")
	private String HUANXIN_CHAT_OFFLINE_REQUEST_SECRET;

	@Value("#{'${HUANXIN_CHAT_OFFLINE_RESPONSE_SECRET}'.trim()}")
	private String HUANXIN_CHAT_OFFLINE_RESPONSE_SECRET;

	@Value("#{'${IM_ZRY_FLAG}'.trim()}")
	private String IM_ZRY_FLAG;
	
	@Value("#{'${IM_CHANGZU_FLAG}'.trim()}")
	private String IM_CHANGZU_FLAG;

	@Resource(name = "message.huanxinImRecordService")
	private HuanxinImRecordService huanxinImRecordService;
	
	@Resource(name = "basedata.authIdentifyService")
	private AuthIdentifyService authIdentifyService;
	
	@Resource(name = "message.msgUserLivenessService")
	private MsgUserLivenessService msgUserLivenessService;
	
	@Resource(name = "message.huanxinImManagerService")
	private HuanxinImManagerService  huanxinImManagerService;

	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;

	/**
	 * 每页条数
	 */
	private final static int LIMIT = 500;
	
	/**
	 * 
	 * IM聊天回调接口
	 * 
	 * 说明： 如果非自如宿的聊天信息  保存至t_huanxin_im_record
	 *
	 * @author yd
	 * @created 2017年4月5日 上午9:41:38
	 *
	 * @param request
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/saveImChat")
	public void imChatCallback(HttpServletRequest request,HttpServletResponse response){
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String appMsg=sb.toString();
			LogUtil.info(LOGGER, "【IM聊天消息回调 saveImChat】:msg={}", appMsg);
			if (Check.NuNStr(appMsg)){
				return;
			}
			String basePath = (String) request.getAttribute("basePath");
			JSONObject msgObj = JSONObject.parseObject(appMsg);
			JSONObject payload = msgObj.getJSONObject("payload");
			JSONObject ext = payload.getJSONObject("ext");
			String extStr = payload.getString("ext");
			String callId = msgObj.getString("callId");
			String responseStr = huanxinResponse(callId,HUANXIN_CHAT_RESPONSE_SECRET);
			if (Check.NuNObj(ext)){
				response.getWriter().write(responseStr);
				return;
			}
			String ziroomFlag = ext.getString("ziroomFlag");
			String domainFlag = ext.getString("domainFlag");
			

			if (Check.NuNObj(domainFlag)){
				response.getWriter().write(responseStr);
				return;
			}

			//自如驿 和 自如宿 聊天入库
			if (Check.NuNStr(ziroomFlag) ||( !IM_MINSU_FLAG.equals(ziroomFlag) && !IM_ZRY_FLAG.equals(ziroomFlag)  && !IM_CHANGZU_FLAG.equals(ziroomFlag))){
				response.getWriter().write(responseStr);
				return;
			}
			
			//说明:1. 线上数据
			if(IM_DOMAIN_FLAG.equals("minsu_online")){
				if(!IM_DOMAIN_FLAG.equals(domainFlag)&&!domainFlag.equals("minsu_t")){
					
					LogUtil.info(LOGGER, "【环境标识】IM_DOMAIN_FLAG={},domainFlag={}", IM_DOMAIN_FLAG,domainFlag);
					response.getWriter().write(responseStr);
					return;
				}
			
			}
			//非线上数据
			if(!IM_DOMAIN_FLAG.equals("minsu_online")){
				if(!IM_DOMAIN_FLAG.equals(domainFlag)||domainFlag.equals("minsu_t")){
					response.getWriter().write(responseStr);
					return;
				}
			}
			
			


			String from = msgObj.getString("from");
			String to = msgObj.getString("to");
			Long timestamp= msgObj.getLong("timestamp");
			String msgId = msgObj.getString("msg_id");
			String security = msgObj.getString("security");

			String desStr = callId + HUANXIN_CHAT_REQUEST_SECRET + timestamp;
			String sign = DigestUtils.md5Hex(desStr);
			if (!sign.equals(security)){
				LogUtil.info(LOGGER,"【saveImChat】签名错误校验未通过,str={},sign={}",desStr,sign);
				//签名错误 校验不通过
				return;
			}
			if(extStr.contains("\"[]\"")){
				extStr = extStr.replace("\"[]\"", "[]");
			}

			if(IM_MINSU_FLAG.equals(ziroomFlag)){
				LogUtil.info(LOGGER, "saveImChat  extStr={}", extStr);
				AppChatRecordsExt appChatRecordsExt = JSONObject.parseObject(extStr, AppChatRecordsExt.class);
                LogUtil.info(LOGGER, "saveImChat  appChatRecordsExt={}", JsonEntityTransform.Object2Json(appChatRecordsExt));
				int msgSenderType = Integer.parseInt(ext.getString("msgSenderType"));

				JSONArray bodies = payload.getJSONArray("bodies");
				JSONObject txtObj = bodies.getJSONObject(0);
				String msg = txtObj.getString("msg");
				
				//截取聊天消息结束
				AppChatRecordsDto appChatRecordsDto = new AppChatRecordsDto();
				appChatRecordsDto.setAppChatRecordsExt(appChatRecordsExt);
				String uid = "";
				if (msgSenderType == UserTypeEnum.LANDLORD.getUserType()){
					appChatRecordsDto.setMsgSentType(UserTypeEnum.LANDLORD_HUAXIN.getUserType());
					appChatRecordsDto.setLandlordUid(getTrueUid(from));
					appChatRecordsDto.setTenantUid(getTrueUid(to));
					uid = appChatRecordsDto.getLandlordUid();
				}
				if (msgSenderType == UserTypeEnum.TENANT.getUserType()){
					appChatRecordsDto.setMsgSentType(UserTypeEnum.TENANT_HUANXIN.getUserType());
					appChatRecordsDto.setTenantUid(getTrueUid(from));
					appChatRecordsDto.setLandlordUid(getTrueUid(to));
					uid = appChatRecordsDto.getTenantUid();
				}
				appChatRecordsDto.setMsgContent(msg);
				appChatRecordsExt.setDomainFlag(domainFlag);
				appChatRecordsExt.setZiroomFlag(ziroomFlag);
				appChatRecordsExt.setHuanxinMsgId(msgId);


				appChatRecordsDto.setMsgSendTime(new Date(timestamp));

				//填充图片相关字段值
				if(!Check.NuNObj(bodies)){
					JSONObject bodiesObj=bodies.getJSONObject(0);
					appChatRecordsDto.setType(bodiesObj.getString("type"));
					appChatRecordsDto.setUrl(bodiesObj.getString("url"));
					appChatRecordsDto.setFilename(bodiesObj.getString("filename"));
					appChatRecordsDto.setFileLength(bodiesObj.getInteger("file_length"));
					appChatRecordsDto.setSecret(bodiesObj.getString("secret"));
					appChatRecordsDto.setSize(bodiesObj.getString("size"));
				}
				LogUtil.info(LOGGER,"开始保存消息");
				//保存消息
				SendThreadPool.execute(new SaveMsgBaseThread(appChatRecordsDto, basePath, uid,
						msgHouseService, msgBaseService, smsTemplateService, customerMsgManagerService, shortChainMapService));

			}
			//自如驿
			if(IM_ZRY_FLAG.equals(ziroomFlag) || IM_CHANGZU_FLAG.equals(ziroomFlag)){
				saveHuanxinImRecord(msgObj);
			}
			//如果是长租聊天的信息，当聊天双方发送的消息达到15条的时候，需要给发送人和被发送人都将发送一条活动（卡片消息）推送
			if(IM_CHANGZU_FLAG.equals(ziroomFlag)){
				String zkSysValue = zkSysService.getZkSysValue(EnumMinsuConfig.changzu_chat_cardActivity_switch.getType(), EnumMinsuConfig.changzu_chat_cardActivity_switch.getCode());
				LogUtil.info(LOGGER, "卡片开关zkSysValue={}", zkSysValue);
				if(YesOrNoEnum.YES.getStr().endsWith(zkSysValue)){
					pushCardMsg(getTrueUid(from), getTrueUid(to), ziroomFlag);
				}
			}
			response.getWriter().write(ziroomFlag);
		}catch (Exception e){
			LogUtil.error(LOGGER, "【IM聊天消息回调异常】e={}", e);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					LogUtil.error(LOGGER, "saveImChat 关闭流异常", e);
				}
			}
		}

	}

	/**
	 * 
	 * 保存 非自如宿的聊天信息
	 *
	 * @author yd
	 * @created 2017年8月1日 下午5:51:51
	 *
	 * @param obj
	 */
	private  void saveHuanxinImRecord(JSONObject obj){
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					LogUtil.info(LOGGER, "【保存消息开始】");
					if(Check.NuNObj(obj)||Check.NuNObj(obj.getJSONObject("payload"))){
						return ;
					}
					//ext 和  bodies 解析  只保存有民宿标识的聊天记录
					JSONObject payload  = null;
					try {
						payload = obj.getJSONObject("payload");
					} catch (Exception e) {
						LogUtil.error(LOGGER, "当前环信异常数据obj={},原因不是json格式的数据", e);
						return ;
					}
					HuanxinImRecordEntity huanxinIm = new HuanxinImRecordEntity();
					JSONObject ext = payload.getJSONObject("ext");
					if(!Check.NuNObj(ext)){
						String ziroomFlag = ext.getString("ziroomFlag");
						if(!Check.NuNObj(ziroomFlag)){
							huanxinIm.setZiroomFlag(ziroomFlag);
							String extStr = ext.toString();
							huanxinIm.setExt(extStr);
							if(extStr.length()>500){
								huanxinIm.setExt(extStr.substring(0, 500));
							}
							huanxinIm.setFid(UUIDGenerator.hexUUID());
							huanxinIm.setIsDel(IsDelEnum.NOT_DEL.getCode());
							huanxinIm.setIsRead(IsReadEnum.UNREAD.getCode());
							huanxinIm.setZiroomFlag(ziroomFlag);

							huanxinIm.setUuid(obj.getString("uuid"));
							huanxinIm.setInterfaceType(obj.getString("type"));
							if(!Check.NuNObj(obj.getLong("created"))){
								huanxinIm.setCreated(new Date(obj.getLong("created")));
							}
							if(!Check.NuNObj(obj.getLong("modified"))){
								huanxinIm.setModified(new Date(obj.getLong("modified")));
							}
							if(!Check.NuNObj(obj.getLong("timestamp"))){
								huanxinIm.setTimestampSend(new Date(obj.getLong("timestamp")));
							}
							huanxinIm.setFromUid(getTrueUid(obj.getString("from")) );
							huanxinIm.setMsgId(obj.getString("msg_id"));
							huanxinIm.setToUid(getTrueUid(obj.getString("to")));
							huanxinIm.setChatType(obj.getString("chat_type"));

							JSONArray bodiesMap = payload.getJSONArray("bodies");
							if(!Check.NuNObj(bodiesMap)){
								JSONObject bodiesObj=bodiesMap.getJSONObject(0);
								huanxinIm.setMsg(bodiesObj.getString("msg"));
								huanxinIm.setType(bodiesObj.getString("type"));
								huanxinIm.setUrl(bodiesObj.getString("url"));
								huanxinIm.setFilename(bodiesObj.getString("filename"));
								huanxinIm.setFileLength(bodiesObj.getInteger("file_length"));
								JSONObject size = bodiesObj.getJSONObject("size");
								if(!Check.NuNObj(size)){
									huanxinIm.setSize(size.toString());
								}
								
								huanxinIm.setSecret(bodiesObj.getString("secret"));
								huanxinIm.setAddr(bodiesObj.getString("addr"));
								huanxinIm.setLength(bodiesObj.getInteger("length"));
								if(!Check.NuNObj(bodiesObj.getDouble("lat"))){
									huanxinIm.setLat(Float.valueOf(bodiesObj.getDouble("lat")+""));
								}
								if(!Check.NuNObj(bodiesObj.getDouble("lng"))){
									huanxinIm.setLng(Float.valueOf(bodiesObj.getDouble("lng")+""));
								}	
							}
							//填充ziroomType
							if(ImTypeEnum.TXT_MSG.getType().equals(huanxinIm.getType()) || ImTypeEnum.CMD_MSG.getType().equals(huanxinIm.getType())){
								huanxinIm.setZiroomType(ImForTxtMsgTypeEnum.NORMAL_MSG.getCode());
								if(!Check.NuNObj(ext)){
									Integer ziroomType = ext.getInteger("ziroomType");
									LogUtil.info(LOGGER, "saveHuanxinImRecord 填充ziroomType={}", ziroomType);
									if(!Check.NuNObj(ziroomType)){
										huanxinIm.setZiroomType(ziroomType);
									}
								}
							}
							
						}
						HuanxinImRecordDto huanxinImRecordDto = new HuanxinImRecordDto();
						huanxinImRecordDto.setHuanxinImRecord(huanxinIm);
					    huanxinImRecordService.saveHuanxinImRecord(JsonEntityTransform.Object2Json(huanxinImRecordDto));
						LogUtil.info(LOGGER, "【保存消息结束】huanxinIm={}",JsonEntityTransform.Object2Json(huanxinIm));
						if(ziroomFlag.equals(AuthIdentifyEnum.ZIROOM_CHANGZU_IM.getType())){
							saveOrUpdateMsgUserRel(obj, ext);								
						}
					}

				} catch (Exception e) {
					LogUtil.error(LOGGER, "【保存环信记录异常】e={}", e);
				}
			}

		});
		th.start();

	}
	
	/**
	 * 
	 * 推送卡片消息
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午2:51:51
	 *
	 * @param obj
	 */
	private void saveOrUpdateMsgUserRel(JSONObject obj, JSONObject ext){
		//如果ext中的ziroomType 200：屏蔽消息 201：取消屏蔽消息 202：投诉消息 时，需要处理t_msg_user_rel
		Integer ziroomType = ext.getInteger("ziroomType");
		if(!Check.NuNObj(ziroomType) && (MsgUserRelSourceTypeEnum.SHIELD.getCode()==ziroomType.intValue() 
				|| MsgUserRelSourceTypeEnum.CANCELSHIELD.getCode()==ziroomType.intValue()
				|| MsgUserRelSourceTypeEnum.COMPLAINT.getCode()==ziroomType.intValue())){
			MsgUserRelEntity msgUserRel = new MsgUserRelEntity();
			msgUserRel.setFromUid(getTrueUid(obj.getString("from")));
			msgUserRel.setToUid(getTrueUid(obj.getString("to")));
			msgUserRel.setZiroomFlag(ext.getString("ziroomFlag"));
			msgUserRel.setSourceType(ziroomType);
			msgUserRel.setCreateFid(getTrueUid(obj.getString("from")));
			msgUserRel.setCreaterType(MsgUserRelSCreateTypeEnum.ZIROOMKE.getCode());
			msgUserRel.setRemark(ext.getString("remark"));
			huanxinImManagerService.saveOrUpdateMsgUserRel(JsonEntityTransform.Object2Json(msgUserRel));
		}
	}
	
	/**
	 * 
	 * 推送卡片消息
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午2:51:51
	 *
	 * @param obj
	 */
	private void pushCardMsg(String fromUid, String toUid, String ziroomFlg){
				try {
					LogUtil.info(LOGGER, "推送卡片消息开始");
					if(Check.NuNStr(fromUid) || Check.NuNStr(toUid) || Check.NuNStr(ziroomFlg)){
						return;
					}
					
					HuanxinImRecordEntity huanxinIm = new HuanxinImRecordEntity();
					huanxinIm.setFromUid(fromUid);
					huanxinIm.setToUid(toUid);
					huanxinIm.setZiroomFlag(ziroomFlg);
					String resultJson = huanxinImRecordService.getCountMsgEach(JsonEntityTransform.Object2Json(huanxinIm));
					LogUtil.info(LOGGER, "推送卡片消息开始  查询消息总数结果getCountMsgEac={}",resultJson);
					DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
					if(resultDto.getCode()==DataTransferObject.SUCCESS){
						Long countEach = resultDto.parseData("countEach", new TypeReference<Long>() {});
						if(countEach==15){
							//给用户发送卡片消息
							 List<SendImMsgRequest> listSendImMsgRequest = new ArrayList<SendImMsgRequest>();
		                     SendImMsgRequest sendImMsgRequest = new SendImMsgRequest();
		                     
		                     sendImMsgRequest.setTarget_type(TargetTypeEnum.SINGLE_USER.getHuanxinTargetType());
		                     String[] totarget = new String[]{MessageConst.IM_UID_PRE + toUid};
		                     sendImMsgRequest.setTarget(totarget);
		                     sendImMsgRequest.setFrom(MessageConst.IM_UID_PRE + fromUid);
		                     Map<String, Object> appChatRecordsExt = new HashMap<String, Object>();
		                     appChatRecordsExt.put("ziroomFlag", ziroomFlg);
		                     appChatRecordsExt.put("ziroomType", String.valueOf(ImForTxtMsgTypeEnum.CARD_MSG.getCode()));
		                     appChatRecordsExt.put("content", MessageConst.IM_FOR_CHANGZU_CONTENT);
		                     //appChatRecordsExt.put("activityPicUrl", MessageConst.IM_FOR_CHANGZU_PIC_URL);//上线前一定要改过来
		                     appChatRecordsExt.put("activityPicUrl", "http://10.16.34.48:8080/group3/M00/06/35/ChAiKlnMyAmAN_XGAAAGWyQpR0Q287.jpg");
		                     sendImMsgRequest.setExtMap(appChatRecordsExt);
		                     listSendImMsgRequest.add(sendImMsgRequest);
		                     huanxinImRecordService.sendHuanxinForChangzu(JsonEntityTransform.Object2Json(listSendImMsgRequest));
		                     String[] fromtarget = new String[]{MessageConst.IM_UID_PRE + fromUid};
		                     sendImMsgRequest.setFrom(MessageConst.IM_UID_PRE + toUid);
		                     sendImMsgRequest.setTarget(fromtarget);
		                     huanxinImRecordService.sendHuanxinForChangzu(JsonEntityTransform.Object2Json(listSendImMsgRequest));
						}
					}
				} catch (Exception e) {
					LogUtil.info(LOGGER, "推送卡片消息失败");
				}
	} 
	
	/**
	 * 环信返回结果
	 * @param callId
	 * @return
	 */
	private String huanxinResponse(String callId, String huanxinResponseSecret){
		HuanXinResponse huanXinResponse = new HuanXinResponse();
		huanXinResponse.setCallId(callId);
		huanXinResponse.setSecurity(DigestUtils.md5Hex(callId+huanxinResponseSecret+"true"));
		return JsonEntityTransform.Object2Json(huanXinResponse);
	}

	/**
	 * 获取实际地址
	 * @param huanxinId
	 * @return
	 */
	private String getTrueUid(String huanxinId){
		if(Check.NuNStr(huanxinId)||!huanxinId.contains("app_")){
			return huanxinId;
		}
		return huanxinId.split("app_")[1];
	}



	/**
	 *
	 * IM聊天回调接口
	 *
	 * @author yd
	 * @created 2017年4月5日 上午9:41:38
	 *
	 * @param request
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/saveImChatOffline")
	public void imChatOfflineCallback(HttpServletRequest request,HttpServletResponse response){
		BufferedReader br = null;
		try {
		    br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String appMsg=sb.toString();
			LogUtil.info(LOGGER, "【IM聊天消息回调 saveImChatOffline】:msg={}", appMsg);
			if (Check.NuNStr(appMsg)){
				return;
			}
			if(appMsg.contains("\"[]\"")){
				appMsg = appMsg.replace("\"[]\"", "[]");
			}
			JSONObject msgObj = JSONObject.parseObject(appMsg);
			JSONObject payload = msgObj.getJSONObject("payload");
			JSONObject ext = payload.getJSONObject("ext");
			String extStr = payload.getString("ext");
			String callId = msgObj.getString("callId");
			String responseStr = huanxinResponse(callId,HUANXIN_CHAT_OFFLINE_RESPONSE_SECRET);
			if (Check.NuNObj(ext)){
				response.getWriter().write(responseStr);
				return;
			}
			String ziroomFlag = ext.getString("ziroomFlag");
			String domainFlag = ext.getString("domainFlag");
			if (Check.NuNStr(ziroomFlag) ||( !IM_MINSU_FLAG.equals(ziroomFlag) && !IM_ZRY_FLAG.equals(ziroomFlag)  && !IM_CHANGZU_FLAG.equals(ziroomFlag))){
				response.getWriter().write(responseStr);
				return;
			}

			if (Check.NuNObj(domainFlag)){
				response.getWriter().write(responseStr);
				return;
			}
			
			//说明:1. 线上数据
			if(IM_DOMAIN_FLAG.equals("minsu_online")){
				if(!IM_DOMAIN_FLAG.equals(domainFlag)&&!domainFlag.equals("minsu_t")){
					LogUtil.info(LOGGER, "【环境标识】IM_DOMAIN_FLAG={},domainFlag={}", IM_DOMAIN_FLAG,domainFlag);
					response.getWriter().write(responseStr);
					return;
				}
			
			}
			//非线上数据
			if(!IM_DOMAIN_FLAG.equals("minsu_online")){
				
				
				if(!IM_DOMAIN_FLAG.equals(domainFlag)||domainFlag.equals("minsu_t")){
					LogUtil.info(LOGGER, "【环境标识】IM_DOMAIN_FLAG={},domainFlag={}", IM_DOMAIN_FLAG,domainFlag);
					response.getWriter().write(responseStr);
					return;
				}
			}
			
			
			String from = msgObj.getString("from");
			String to = msgObj.getString("to");
			Long timestamp= msgObj.getLong("timestamp");
			String msgId = msgObj.getString("msg_id");
			String security = msgObj.getString("security");

			String desStr = callId + HUANXIN_CHAT_OFFLINE_REQUEST_SECRET + timestamp;
			String sign = DigestUtils.md5Hex(desStr);
			if (!sign.equals(security)){
				LogUtil.info(LOGGER,"【saveImChatOffline】签名错误校验未通过,str={},sign={}",desStr,sign);
				//签名错误 校验不通过
				return;
			}

			JSONArray bodies = payload.getJSONArray("bodies");
			JSONObject txtObj = bodies.getJSONObject(0);
			String msg = txtObj.getString("msg");
			if(IM_MINSU_FLAG.equals(ziroomFlag)){
				String fid = ext.getString("fid");
				if(Check.NuNStr(fid)){
					fid="";
				}
				int msgSenderType = Integer.parseInt(ext.getString("msgSenderType"));
				String houseCard = ext.getString("houseCard");
				String houseName = ext.getString("houseName");
				String housePicUrl = ext.getString("housePicUrl");
				String rentWay = ext.getString("rentWay");
				LogUtil.info(LOGGER, "imChatOfflineCallback方法，rentWay={}，houseName={}", rentWay,houseName);
				if(Check.NuNObjs(rentWay)){
					rentWay="0";
				}
				MsgBaseOfflineEntity msgBaseOfflineEntity = new MsgBaseOfflineEntity();
				if (msgSenderType == UserTypeEnum.LANDLORD.getUserType()){
					msgBaseOfflineEntity.setMsgSenderType(UserTypeEnum.LANDLORD_HUAXIN.getUserType());
					msgBaseOfflineEntity.setLandlordUid(getTrueUid(from));
					msgBaseOfflineEntity.setTenantUid(getTrueUid(to));
				}
				if (msgSenderType == UserTypeEnum.TENANT.getUserType()){
					msgBaseOfflineEntity.setMsgSenderType(UserTypeEnum.TENANT_HUANXIN.getUserType());
					msgBaseOfflineEntity.setTenantUid(getTrueUid(from));
					msgBaseOfflineEntity.setLandlordUid(getTrueUid(to));
				}
				//截取聊天消息开始，2017-12-8 ，线上有用户发送的msg超过了数据VARCHAR(1024)存贮范围，（民宿，驿站，长租的在线离线都是这样处理的
				if(msg.length()>500){
					msg = msg.substring(0, 500);
				}
				//截取聊天消息结束
				msgBaseOfflineEntity.setMsgRealContent(msg);
				msgBaseOfflineEntity.setHouseFid(fid);
				msgBaseOfflineEntity.setRentWay(Integer.parseInt(rentWay));
				msgBaseOfflineEntity.setHuanxinMsgId(msgId);


				//扩展消息赋值
				AppChatRecordsExt appChatRecordsExt = new AppChatRecordsExt();
				appChatRecordsExt.setFid(fid);
				appChatRecordsExt.setHouseName(houseName);
				appChatRecordsExt.setHousePicUrl(housePicUrl);
				appChatRecordsExt.setRentWay(Integer.parseInt(rentWay));
				appChatRecordsExt.setDomainFlag(domainFlag);
				appChatRecordsExt.setZiroomFlag(ziroomFlag);
				appChatRecordsExt.setHuanxinMsgId(msgId);
				appChatRecordsExt.setMsgType(ext.getString("msgType"));
				appChatRecordsExt.setRoleType(ext.getInteger("roleType"));
				String sharStr = ext.getString("shareHouseMsg");
				if (!Check.NuNStr(sharStr)){

					List<ShareHouseMsg> shareHouseMsgs = JSONArray.parseArray(sharStr, ShareHouseMsg.class);
					appChatRecordsExt.setShareHouseMsg(shareHouseMsgs);
				}
				appChatRecordsExt.setSource(ext.getInteger("source"));
				if (!Check.NuNStr(houseCard)){
					appChatRecordsExt.setHouseCard(houseCard);
				}else{
					appChatRecordsExt.setHouseCard("0");
				}
				appChatRecordsExt.setStartDate(ext.getString("startDate"));
				appChatRecordsExt.setEndDate(ext.getString("endDate"));
				appChatRecordsExt.setPersonNum(ext.getString("personNum"));

				msgBaseOfflineEntity.setMsgContentExt(JsonEntityTransform.Object2Json(appChatRecordsExt));
				
				//填充图片相关字段值
				if(!Check.NuNObj(bodies)){
					JSONObject bodiesObj=bodies.getJSONObject(0);
					msgBaseOfflineEntity.setType(bodiesObj.getString("type"));
					msgBaseOfflineEntity.setUrl(bodiesObj.getString("url"));
					msgBaseOfflineEntity.setFilename(bodiesObj.getString("filename"));
					msgBaseOfflineEntity.setFileLength(bodiesObj.getInteger("file_length"));
					msgBaseOfflineEntity.setSecret(bodiesObj.getString("secret"));
					msgBaseOfflineEntity.setSize(bodiesObj.getString("size"));
				}
				//保存消息
				SendThreadPool.execute(new SaveMsgOfflineThread(msgBaseService,msgBaseOfflineEntity));
				
			}
			//自如驿
			if(IM_CHANGZU_FLAG.equals(ziroomFlag)){
				HuanxinImOfflineEntity huanxinImOffline = new HuanxinImOfflineEntity();
				huanxinImOffline.setMsgId(msgId);
				huanxinImOffline.setFromUid(from);
				huanxinImOffline.setToUid(to);
				huanxinImOffline.setZiroomFlag(ziroomFlag);
				SendThreadPool.execute(new SaveHuanxinOfflineThread(huanxinImRecordService,huanxinImOffline));
			}
			response.getWriter().write(responseStr);

		} catch (IOException e) {
			LogUtil.error(LOGGER, "【IM聊天消息回调异常】e={}", e);
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					LogUtil.error(LOGGER, "saveImChatOffline 关闭流异常", e);
				}
			}
		}


	}

	/**
	 * 
	 * 同步用户最后活跃时间
	 *
	 * @author loushuai
	 * @created 2017年9月1日 下午11:03:28
	 *
	 * @param groupMemberDto
	 * @param request
	 */
	@RequestMapping("/syncLivenessTime")
	public void syncLivenessTime(HttpServletRequest request,HttpServletResponse response){
		try {
			DataTransferObject dto = new DataTransferObject();
			String code = request.getParameter("code");
			String timeStamp = request.getParameter("timeStamp");
			String value = request.getParameter("value");
			String uid = request.getParameter("uid");
			String sycnData = request.getParameter("sycnData");
			LogUtil.info(LOGGER, "syncLivenessTime方法入参，paramJson={}",uid);
			
			//校验授权参数
			if(Check.NuNStr(code) || Check.NuNStr(timeStamp) || Check.NuNStr(value)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("code，timeStamp，value三个参数其中一个为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			
			if(Check.NuNStr(sycnData)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("sycnData同步数据为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			
			//开始校验授权参数
			DataTransferObject verifyAuthDto = verifyAuth(code, timeStamp, value);
		    if(verifyAuthDto.getCode()==DataTransferObject.ERROR){
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(verifyAuthDto));
				return;
		    }
			
			//校验通过，开始同步用户活跃度
			List<MsgUserLivenessEntity> syncLivenessList = new 	ArrayList<MsgUserLivenessEntity>();
			JSONObject parseObject = JSONObject.parseObject(sycnData);
			JSONArray jsonArray = parseObject.getJSONArray("sycnData");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject temp = (JSONObject) jsonArray.get(i);
				if(!Check.NuNStr(temp.getString("uid")) && !Check.NuNObj(temp.getLong("lastLiveTime"))){
					MsgUserLivenessEntity msgUserLiveness = new MsgUserLivenessEntity();
					msgUserLiveness.setUid(temp.getString("uid"));
					msgUserLiveness.setLastLiveTime(new Date(temp.getLong("lastLiveTime")));
					syncLivenessList.add(msgUserLiveness);
				}
			}
			if(Check.NuNCollection(syncLivenessList)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("sycnData同步数据为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			String syncJson = msgUserLivenessService.syncLivenessTime(JsonEntityTransform.Object2Json(syncLivenessList));
			DataTransferObject syncDto = JsonEntityTransform.json2DataTransferObject(syncJson);
			if(syncDto.getCode()==DataTransferObject.ERROR){
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(syncDto));
				return;
				
			}
			syncDto.parseData("countInsert", new TypeReference<Integer>() {});
			dto.setMsg("同步成功");
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "syncLivenessTime错误", e);
		}
		
	}
	
	/**
	 * 
	 * 查询用户24小时内聊天的接口
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午3:58:28
	 *
	 * @param msgUserRel
	 * @param request
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/queryUserChatInTwentyFourHour")
	@ResponseBody
	public void queryUserChatInTwentyFourHour(HttpServletRequest request,PeriodHuanxinRecordRequest queryRequest,HttpServletResponse response){
		LogUtil.info(LOGGER, "【查询用户24小时内聊天】queryUserChatInTwentyFour：参数queryRequest={},uid={}",JsonEntityTransform.Object2Json(queryRequest));
		DataTransferObject dto = new DataTransferObject();
		try{
			String code = request.getParameter("code");
			String timeStamp = request.getParameter("timeStamp");
			String value = request.getParameter("value");
			
			
			//校验授权参数
			if(Check.NuNStr(code) || Check.NuNStr(timeStamp) || Check.NuNStr(value)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("code，timeStamp，value三个参数其中一个为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			
			if (Check.NuNObj(queryRequest.getFromUid())|| Check.NuNObj(queryRequest.getToUid()) || Check.NuNObj(queryRequest.getZiroomFlag())
					|| Check.NuNStr(queryRequest.getTillDateStr())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("fromUid,toUid,tillDate,ziroomFlag四个必传参数其中一个为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			
			DataTransferObject verifyAuthDto = verifyAuth(code, timeStamp, value);
		    if(verifyAuthDto.getCode()==DataTransferObject.ERROR){
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(verifyAuthDto));
				return;
		    }
			
			String tillDateStr = queryRequest.getTillDateStr();
			long datetime = Long.valueOf(tillDateStr);
			String timestampFormat = DateUtil.timestampFormat(datetime);
			Date parseDate = DateUtil.parseDate(timestampFormat, "yyyy-MM-dd HH:mm:ss");
			queryRequest.setTillDate(parseDate);
			Date beginTime = DateUtil.getTime(parseDate, -1);
			queryRequest.setBeginDate(beginTime);
			queryRequest.setLimit(LIMIT);
			LogUtil.info(LOGGER,"【查询用户24小时内聊天】 queryUserChatInTwentyFour：处理后参数={}",JsonEntityTransform.Object2Json(queryRequest));
			
			DataTransferObject imListDto = JsonEntityTransform.json2DataTransferObject(msgBaseService.queryUserChatInTwentyFour(JsonEntityTransform.Object2Json(queryRequest)));
			if (imListDto.getCode() == DataTransferObject.ERROR){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("服务出现异常");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			List<HuanxinImRecordEntity> responseList = imListDto.parseData("list", new TypeReference<List<HuanxinImRecordEntity>>() {});
			dto.putValue("list",responseList);
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
	    }catch (Exception e){
			LogUtil.error(LOGGER,"【queryUserChatInTwentyFour】 异常={}",e);
		}
	}
	
	
	/**
	 * 
	 * 屏蔽聊天用户关系
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午3:58:28
	 *
	 * @param msgUserRel
	 * @param request
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/shieldOrCancalShield")//测试用
	@ResponseBody
	public void shieldOrCancalShield(HttpServletRequest request, MsgUserRelEntity msgUserRel,HttpServletResponse response){
		try {
			DataTransferObject dto = new DataTransferObject();
			String code = request.getParameter("code");
			String timeStamp = request.getParameter("timeStamp");
			String value = request.getParameter("value");
			
			
			//校验授权参数
			if(Check.NuNStr(code) || Check.NuNStr(timeStamp) || Check.NuNStr(value)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("code，timeStamp，value三个参数其中一个为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			String jsonParam = "";
			jsonParam = msgUserRel==null?"":JsonEntityTransform.Object2Json(msgUserRel);
	
			if(Check.NuNObj(msgUserRel)||Check.NuNStr(msgUserRel.getToUid())
					||Check.NuNStr(msgUserRel.getFromUid())
					||Check.NuNObj(msgUserRel.getSourceType())
					||Check.NuNStr(msgUserRel.getZiroomFlag())
					|| (YesOrNoEnum.YES.getCode()==msgUserRel.getSourceType() && Check.NuNStr(msgUserRel.getRemark()))){
				LogUtil.error(LOGGER, "【屏蔽聊天用户】shieldOrCancalShield：参数非法，参数msgUserRel={}",jsonParam);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("不能为空的参数出现了空值");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
			}
			
			DataTransferObject verifyAuthDto = verifyAuth(code, timeStamp, value);
		    if(verifyAuthDto.getCode()==DataTransferObject.ERROR){
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(verifyAuthDto));
				return;
		    }
			
			msgUserRel.setFromUid(getTrueUid(msgUserRel.getFromUid()));
			msgUserRel.setToUid(getTrueUid(msgUserRel.getToUid()));
			
			msgUserRel.setFid(UUIDGenerator.hexUUID());
			msgUserRel.setCreateFid(getTrueUid(msgUserRel.getFromUid()));
			msgUserRel.setCreaterType(MsgUserRelSCreateTypeEnum.ZIROOMKE.getCode());//创建类型是自如客
			String shieldOrCancalShieldJson = huanxinImManagerService.saveOrUpdateMsgUserRel(JsonEntityTransform.Object2Json(msgUserRel));
			DataTransferObject shieldOrCancalShieldDto = JsonEntityTransform.json2DataTransferObject(shieldOrCancalShieldJson);
			if(shieldOrCancalShieldDto.getCode()==DataTransferObject.ERROR){
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(shieldOrCancalShieldDto));
				return ;
			}
			dto.setMsg("执行成功");
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "shieldOrCancalShield错误 e={}",e);
		}
	}
	
	/**
	 * 
	 * 校验授权程序
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午3:58:28
	 * 
	 * @param code
	 * @param timeStamp
	 * @param value
	 * @return
	 */
	public DataTransferObject verifyAuth(String code, String timeStamp, String value){
		//开始校验参数
		DataTransferObject dto = new DataTransferObject();
		String authIdentifyCode = null;
		
		//根据code获取AuthIdentifyEntity（先从redis中取，没有在从数据库中取然后放入redis中）
		String key = RedisKeyConst.getConfigKey("authIdentifyCode");
		authIdentifyCode = redisOperations.get(key);
		if(!Check.NuNStr(authIdentifyCode)){
			if(!code.equals(authIdentifyCode)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("code(校验码)匹配不成功");
				return dto;
			}
		}else{
			//redis获取不到，从数据库中获取，并放入redis中
			AuthIdentifyEntity authIdentifyEntity = new AuthIdentifyEntity();
			authIdentifyEntity.setCode(code);
			String authIdentifyJson = authIdentifyService.getAuthIdentifyByCode(JsonEntityTransform.Object2Json(authIdentifyEntity));
			DataTransferObject authIdentifyDto = JsonEntityTransform.json2DataTransferObject(authIdentifyJson);
			if(authIdentifyDto.getCode()==DataTransferObject.SUCCESS){
				AuthIdentifyEntity resultAuthIdentify = authIdentifyDto.parseData("object", new TypeReference<AuthIdentifyEntity>() {});
				if(Check.NuNObj(resultAuthIdentify) || Check.NuNStr(resultAuthIdentify.getCode())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("code(校验码)匹配不成功");
					return dto;
				}
				authIdentifyCode = resultAuthIdentify.getCode();
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, authIdentifyCode);
			}
		}

		//md5（code+timeStamp）比对value
		String md5Hex = DigestUtils.md5Hex(authIdentifyCode+timeStamp);
		if(!value.equals(md5Hex)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("校验值(value)比对不成功");
			return dto;
		}
		return dto;
	}

}
