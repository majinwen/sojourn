/**
 * @FileName: ImAppController.java
 * @Package com.ziroom.minsu.web.im.chat.controller
 * 
 * @author yd
 * @created 2016年9月18日 下午11:11:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fortuna.ical4j.model.WeekDay.Day;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.zookeeper.txn.CreateTxn;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.google.gson.JsonObject;
import com.ziroom.minsu.entity.base.AuthIdentifyEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgHuanxinImLogEntity;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.thread.pool.SynLocationThreadPool;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerChatService;
import com.ziroom.minsu.services.customer.api.inner.CustomerLocationService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerChatRequest;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.services.message.api.inner.HuanxinImManagerService;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.dto.AppChatRecordsDto;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;
import com.ziroom.minsu.services.message.dto.GroupMemberDto;
import com.ziroom.minsu.services.message.dto.MsgBookAdviceRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.dto.MsgSyncRequest;
import com.ziroom.minsu.services.message.dto.PeriodHuanxinRecordRequest;
import com.ziroom.minsu.services.message.dto.UserLivenessReqeust;
import com.ziroom.minsu.services.message.entity.AppMsgBaseVo;
import com.ziroom.minsu.services.message.entity.ImExtForChangzuVo;
import com.ziroom.minsu.services.message.entity.ImExtVo;
import com.ziroom.minsu.services.message.entity.ImMsgSyncVo;
import com.ziroom.minsu.services.message.entity.ImSessionListVo;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;
import com.ziroom.minsu.services.message.entity.MsgUserInfoVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.valenum.msg.ChatTypeEnum;
import com.ziroom.minsu.valenum.msg.CreaterTypeEnum;
import com.ziroom.minsu.valenum.msg.ImForTxtMsgTypeEnum;
import com.ziroom.minsu.valenum.msg.ImTypeEnum;
import com.ziroom.minsu.valenum.msg.MsgTypeEnum;
import com.ziroom.minsu.valenum.msg.MsgUserRelSCreateTypeEnum;
import com.ziroom.minsu.valenum.msg.MsgUserRelSourceTypeEnum;
import com.ziroom.minsu.valenum.msg.SourceTypeEnum;
import com.ziroom.minsu.web.im.chat.controller.dto.ChatRecordsRequest;
import com.ziroom.minsu.web.im.chat.controller.dto.GoodsFriendsRequest;
import com.ziroom.minsu.web.im.chat.controller.dto.MsgCheckErrorLogDto;
import com.ziroom.minsu.web.im.chat.controller.entity.MsgBaseAppVo;
import com.ziroom.minsu.web.im.chat.controller.entity.MsgBaseForIos;
import com.ziroom.minsu.web.im.chat.controller.utils.SaveMsgBaseErrorThred;
import com.ziroom.minsu.web.im.chat.controller.utils.SaveMsgLogThread;
import com.ziroom.minsu.web.im.common.constant.LoginAuthConst;
import com.ziroom.minsu.web.im.common.controller.abs.AbstractController;
import com.ziroom.minsu.web.im.common.header.Header;
import com.ziroom.minsu.web.im.common.tasks.SaveHeaderTask;
import com.ziroom.minsu.web.im.common.utils.DtoToResponseDto;

/**
 * <p>app端IM接口</p>
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
@RequestMapping("imApp")
public class ImAppController extends AbstractController{

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(ImAppController.class);


	@Resource(name = "im.messageSource")
	private MessageSource messageSource;

	@Resource(name = "message.msgHouseService")
	private MsgHouseService msgHouseService;

	@Resource(name = "message.msgBaseService")
	private MsgBaseService msgBaseService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "house.tenantHouseService")
	private TenantHouseService tenantHouseService;

	@Resource(name="customer.customerLocationService")
	private CustomerLocationService customerLocationService;
	
    @Autowired
    private RedisOperations redisOperations;

	@Value("${IM_APP_IOS}")
	private String IM_APP_IOS;

	@Value("${IM_APP_ANDROID}")
	private String IM_APP_ANDROID;

	@Value("${IM_APP_IOS_HOUSELIST}")
	private String IM_APP_IOS_HOUSELIST;

	@Value("${IM_APP_ANDROID_HOUSELIST}")
	private String IM_APP_ANDROID_HOUSELIST;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;


	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;

	@Value("#{'${IM_ZRY_FLAG}'.trim()}")
	private String IM_ZRY_FLAG;
	
	@Value("#{'${IM_CHANGZU_FLAG}'.trim()}")
	private String IM_CHANGZU_FLAG;
	
	@Resource(name = "message.huanxinImManagerService")
	private HuanxinImManagerService  huanxinImManagerService;
	
	@Resource(name="customer.customerChatService")
	private CustomerChatService customerChatService;

	/**
	 * 消息字数控制
	 */
	private final static int msgContentNum = 500;

	/**
	 * 每页条数
	 */
	private final static int LIMIT = 500;
	/**
	 * 天数
	 */
	private final static int DAY = 90;
	
	/**
	 * 
	 * 查询好友（根据房东好友uid或查询房客好友uid）
	 * 
	 * 只能单向查询(不能同时包含两个uid或同时为null)
	 *
	 * @author yd
	 * @created 2016年9月14日 下午4:13:29
	 *
	 * @param request
	 * @return
	 */

	@RequestMapping("${LOGIN_UNAUTH}/queryFriendsUid")
	@ResponseBody
	public DataTransferObject queryFriendsUid(HttpServletRequest request,GoodsFriendsRequest goodsFriendsRe ){

		DataTransferObject dto = new DataTransferObject();

		String uid =  (String) request.getAttribute("uid");

		if(Check.NuNObj(goodsFriendsRe)||Check.NuNStr(goodsFriendsRe.getUid())||Check.NuNObj(goodsFriendsRe.getSenderType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto;
		}

		if(!goodsFriendsRe.getUid().equals(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户非法");
			return dto;
		}

		try {
			MsgHouseRequest msRequest = new MsgHouseRequest();
			int msgType = goodsFriendsRe.getSenderType().intValue();
			if(msgType == UserTypeEnum.LANDLORD.getUserType()){
				msRequest.setLandlordUid(goodsFriendsRe.getUid());
			}else{
				msRequest.setTenantUid(goodsFriendsRe.getUid());
			}
			dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseService.queryFriendsUid(JsonEntityTransform.Object2Json(msRequest)));

			if(dto.getCode() == DataTransferObject.SUCCESS){
				List<String> uids = dto.parseData("frendsUid", new TypeReference<List<String>>() {
				});
				List<MsgHouseListVo> listMsgHouseListVos = new ArrayList<MsgHouseListVo>();
				dto = new DataTransferObject();
				if(!Check.NuNCollection(uids)){

					Long t1 = System.currentTimeMillis();
					for (String goodFriendUid : uids) {

						MsgHouseListVo  msgHouseListVo = new MsgHouseListVo();
						String nickName = "房客";
						if(msgType == UserTypeEnum.TENANT.getUserType()){
							nickName = "房东";
						}

						DataTransferObject dtoN =  JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(goodFriendUid));
						if(dtoN.getCode() == DataTransferObject.SUCCESS){
							CustomerVo cust = dtoN.parseData("customerVo", new TypeReference<CustomerVo>() {
							});
							if(!Check.NuNObj(cust)){
								if(msgType == UserTypeEnum.TENANT.getUserType()){
									msgHouseListVo.setLanlordPicUrl(cust.getUserPicUrl());
									msgHouseListVo.setTenantUid(uid);
									msgHouseListVo.setLandlordUid(goodFriendUid);
								}else{
									msgHouseListVo.setTenantPicUrl(cust.getUserPicUrl());
									msgHouseListVo.setTenantUid(goodFriendUid);
									msgHouseListVo.setLandlordUid(uid);
								}

								msgHouseListVo.setNickName(cust.getNickName());
								if(Check.NuNStr(msgHouseListVo.getNickName())){
									msgHouseListVo.setNickName(nickName);
								}
							}
						}

						listMsgHouseListVos.add(msgHouseListVo);
					}
					Long t2 = System.currentTimeMillis();
					LogUtil.info(logger, "封装好友列表用时t2-t1={}ms", t2-t1);
					dto.putValue("listGoodFriends", listMsgHouseListVos);
				}
			}
		} catch (Exception e) {
			LogUtil.info(logger, "查询好友异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询异常");
		}
		return dto;
	}


	/**
	 * 
	 * 查询当前用户的所有会话以及会话的聊天列表
	 *
	 * @author yd
	 * @created 2016年9月27日 上午3:54:38
	 *
	 * @param request
	 * @param goodsFriendsRe
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryCurrUserSession")
	@ResponseBody
	public void   queryCurrUserSession(HttpServletRequest request,GoodsFriendsRequest goodsFriendsRe ,HttpServletResponse response){


		DataTransferObject dto = new DataTransferObject();

		String uid =  (String) request.getAttribute("uid");
		try {
			if(Check.NuNObj(goodsFriendsRe)||Check.NuNStr(goodsFriendsRe.getUid())||Check.NuNObj(goodsFriendsRe.getSenderType())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
			}

			if(!goodsFriendsRe.getUid().equals(uid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("用户非法");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
			}

			MsgHouseRequest msRequest = new MsgHouseRequest();
			int msgType = goodsFriendsRe.getSenderType().intValue();
			if(msgType == UserTypeEnum.LANDLORD.getUserType()){
				msRequest.setLandlordUid(goodsFriendsRe.getUid());
			}else{
				msRequest.setTenantUid(goodsFriendsRe.getUid());
			}
			dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseService.queryFriendsUid(JsonEntityTransform.Object2Json(msRequest)));

			if(dto.getCode() == DataTransferObject.SUCCESS){
				List<String> uids = dto.parseData("frendsUid", new TypeReference<List<String>>() {
				});//好友uid
				dto = new DataTransferObject();
				if(!Check.NuNCollection(uids)){

					Long t1 = System.currentTimeMillis();
					List<MsgBaseForIos> listMsgBaseForIos = new ArrayList<MsgBaseForIos>();
					for (String goodFriendUid : uids) {

						MsgHouseRequest msgHouseRe  = new MsgHouseRequest();

						if(msgType == UserTypeEnum.LANDLORD.getUserType()){

							msgHouseRe.setTenantUid(goodFriendUid);
							msgHouseRe.setLandlordUid(uid);
						}else{
							msgHouseRe.setTenantUid(uid);
							msgHouseRe.setLandlordUid(goodFriendUid);
						}

						dto = JsonEntityTransform.json2DataTransferObject(this.msgBaseService.queryOneChatRecord(JsonEntityTransform.Object2Json(msgHouseRe)));

						if(dto.getCode() == DataTransferObject.SUCCESS){
							try {
								List<AppMsgBaseVo> listMsgBase = SOAResParseUtil.getListValueFromDataByKey(dto.toJsonString(), "listMsg", AppMsgBaseVo.class);
								//int count = (int) dto.getData().get("count");
								if(!Check.NuNCollection(listMsgBase)){
									dto = new DataTransferObject();
									List<MsgBaseAppVo> listMsgBaseAppVo = new ArrayList<MsgBaseAppVo>();

									for (AppMsgBaseVo msgBaseEntity : listMsgBase) {
										MsgBaseAppVo msgBaseAppVo = new MsgBaseAppVo();
										msgBaseAppVo.setMsgSenderType(msgBaseEntity.getMsgSenderType());
										msgBaseAppVo.setCreateTime(DateUtil.dateFormat(msgBaseEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
										msgBaseAppVo.setMsgContent(msgBaseEntity.getMsgContent());
										msgBaseAppVo.setFroms(msgBaseEntity.getFroms());
										msgBaseAppVo.setTos(msgBaseEntity.getTos());
										msgBaseAppVo.setFid(msgBaseEntity.getHouseFid());
										msgBaseAppVo.setRentWay(msgBaseEntity.getRentWay());
										int msgSenderType = msgBaseEntity.getMsgSenderType().intValue();


										if((msgSenderType== UserTypeEnum.TENANT.getUserType()
												||msgSenderType== UserTypeEnum.LANDLORD.getUserType())){
											AppChatRecordsExt appChatRecordsExt = new AppChatRecordsExt();

											appChatRecordsExt.setHouseCard("0");
											appChatRecordsExt.setFid(msgBaseEntity.getHouseFid());
											appChatRecordsExt.setRentWay(msgBaseEntity.getRentWay());
											appChatRecordsExt.setHuanxinMsgId(UUIDGenerator.hexUUID());
											appChatRecordsExt.setZiroomFlag("ZIROOM_MINSU_IM");
											msgBaseAppVo.setAppChatRecordsExt(appChatRecordsExt);

										}
										//咨询消息 转化成 房客的消息  
										if(msgSenderType== UserTypeEnum.All.getUserType()){
											MsgBookAdviceRequest msgBookAdviceRequest = JsonEntityTransform.json2Object(msgBaseEntity.getMsgContent(), MsgBookAdviceRequest.class);
											if(!Check.NuNObj(msgBookAdviceRequest)){
												msgBaseAppVo.setMsgContent(msgBookAdviceRequest.getTripPurpose());
												msgBaseAppVo.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
												AppChatRecordsExt appChatRecordsExt = new AppChatRecordsExt();
												appChatRecordsExt.setEndDate(msgBookAdviceRequest.getEndTime());

												appChatRecordsExt.setHouseCard("1");
												appChatRecordsExt.setPersonNum(Check.NuNObj(msgBookAdviceRequest.getPeopleNum())?"":msgBookAdviceRequest.getPeopleNum()+"");
												appChatRecordsExt.setStartDate(msgBookAdviceRequest.getStartTime());
												appChatRecordsExt.setZiroomFlag("ZIROOM_MINSU_IM");

												HouseDetailDto houseDetailDto = new HouseDetailDto();
												houseDetailDto.setFid(msgBaseEntity.getHouseFid());
												houseDetailDto.setRentWay(msgBaseEntity.getRentWay());
												appChatRecordsExt.setHouseName(msgBookAdviceRequest.getHouseName());
												appChatRecordsExt.setHousePicUrl(msgBookAdviceRequest.getHousePicUrl());

												// 填充 房源名称和房源图片
												if(Check.NuNStr(msgBookAdviceRequest.getHouseName())){
													//查询房源详情  咨询消息 需要重新查库  并且更新 当前库的 消息
													String resultJson=tenantHouseService.findHouseDetail(JsonEntityTransform.Object2Json(houseDetailDto));
													DataTransferObject dtoHou =  JsonEntityTransform.json2DataTransferObject(resultJson);

													if(dtoHou.getCode() == DataTransferObject.SUCCESS){
														TenantHouseDetailVo tenantHouseDetailVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", TenantHouseDetailVo.class);
														if(!Check.NuNObj(tenantHouseDetailVo)){
															appChatRecordsExt.setHouseName(tenantHouseDetailVo.getHouseName());
															appChatRecordsExt.setHousePicUrl(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), detail_big_pic));

															if(!Check.NuNStr(msgBookAdviceRequest.getMsgHouseFid())){
																msgBookAdviceRequest.setHouseName(tenantHouseDetailVo.getHouseName());
																msgBookAdviceRequest.setHousePicUrl(appChatRecordsExt.getHousePicUrl());
																MsgBaseEntity msgBase= new MsgBaseEntity();
																msgBase.setMsgContent(JsonEntityTransform.Object2Json(msgBookAdviceRequest));
																msgBase.setMsgHouseFid(msgBaseEntity.getMsgHouseFid());
																msgBase.setFid(msgBaseEntity.getFid());
																this.msgBaseService.updateByCondition(JsonEntityTransform.Object2Json(msgBase));
															}

														}
													}
												}
												appChatRecordsExt.setFid(msgBaseEntity.getHouseFid());
												appChatRecordsExt.setRentWay(msgBaseEntity.getRentWay());
												if(Check.NuNStr(appChatRecordsExt.getHuanxinMsgId())){
													appChatRecordsExt.setHuanxinMsgId(UUIDGenerator.hexUUID());
												}
												msgBaseAppVo.setAppChatRecordsExt(appChatRecordsExt);

											}
										}
										if((msgSenderType== UserTypeEnum.TENANT_HUANXIN.getUserType()
												||msgSenderType== UserTypeEnum.LANDLORD_HUAXIN.getUserType())
												&&!Check.NuNStr(msgBaseEntity.getMsgContent())){
											Map<String, Object> msgContentMap = (Map<String, Object>) JsonEntityTransform.json2Map(msgBaseEntity.getMsgContent());
											if(!Check.NuNMap(msgContentMap)){
												Object msgContent = msgContentMap.get("msgContent");
												Object  appChatRecordsExt = msgContentMap.get("appChatRecordsExt");
												if(!Check.NuNObj(msgContent)){
													msgBaseAppVo.setMsgContent(msgContent.toString());
												}
												if(!Check.NuNObj(appChatRecordsExt)){
													msgBaseAppVo.setAppChatRecordsExt(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(appChatRecordsExt), AppChatRecordsExt.class));
												}
											}
											msgBaseAppVo.setMsgSenderType(UserTypeEnum.LANDLORD.getUserType());
											if(msgSenderType== UserTypeEnum.TENANT_HUANXIN.getUserType()){
												msgBaseAppVo.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
											}
										}

										MsgBaseForIos msgBaseForIos = new MsgBaseForIos();
										listMsgBaseAppVo.add(msgBaseAppVo);
										msgBaseForIos.setAppKey("app_"+goodFriendUid);
										msgBaseForIos.setListMsg(listMsgBaseAppVo);
										listMsgBaseForIos.add(msgBaseForIos);
									}
								}
							} catch (SOAParseException e) {

								LogUtil.error(logger, "参数解析异常e={}", e);
								dto.setErrCode(DataTransferObject.ERROR);
								dto.setMsg("参数解析异常");
							}
						}
					}

					if(dto.getCode() == DataTransferObject.SUCCESS){
						dto = new DataTransferObject();
						dto.putValue("listAllMsg", listMsgBaseForIos);
					}
					Long t2 = System.currentTimeMillis();
					LogUtil.info(logger, "封装好友列表用时t2-t1={}ms", t2-t1);
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "查询好友异常e={}", e);
		}

	}
	/**
	 * 
	 * 房东或房客 条件查询IM聊天历史数据（ 数据是当前俩人的聊天数据）
	 * 
	 * 说明：
	 * 对于房源名称  房源图片  咨询消息 重新查询数据库
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryOneChatRecord")
	@ResponseBody
	public void queryOneChatRecord(HttpServletRequest request,ChatRecordsRequest chatRecordsRe,HttpServletResponse response){


		try {
			DataTransferObject dto = new  DataTransferObject();
			if(Check.NuNObj(chatRecordsRe)){

				LogUtil.error(logger, "查询聊天记录，参数错误chatRecordsRe={}", chatRecordsRe == null?"":chatRecordsRe);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数对象不存在");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
			}
			String uid = (String) request.getAttribute("uid");

			if(Check.NuNObj(chatRecordsRe.getLimit())){
				chatRecordsRe.setLimit(50);
			}
			if(Check.NuNObj(chatRecordsRe.getPage())){
				chatRecordsRe.setPage(1);
			}
			MsgHouseRequest msgHouseRe  = new MsgHouseRequest();
			msgHouseRe.setTenantUid(uid);
			msgHouseRe.setLandlordUid(uid);
			msgHouseRe.setLimit(chatRecordsRe.getLimit());
			msgHouseRe.setPage(chatRecordsRe.getPage());

			dto = JsonEntityTransform.json2DataTransferObject(this.msgBaseService.queryOneChatRecord(JsonEntityTransform.Object2Json(msgHouseRe)));

			if(dto.getCode() == DataTransferObject.SUCCESS){
				try {
					List<AppMsgBaseVo> listMsgBase = SOAResParseUtil.getListValueFromDataByKey(dto.toJsonString(), "listMsg", AppMsgBaseVo.class);
					int count = (int) dto.getData().get("count");
					if(!Check.NuNCollection(listMsgBase)){
						dto = new DataTransferObject();
						List<MsgBaseAppVo> listMsgBaseAppVo = new ArrayList<MsgBaseAppVo>();
						for (AppMsgBaseVo msgBaseEntity : listMsgBase) {
							MsgBaseAppVo msgBaseAppVo = new MsgBaseAppVo();
							msgBaseAppVo.setMsgSenderType(msgBaseEntity.getMsgSenderType());
							msgBaseAppVo.setCreateTime(DateUtil.dateFormat(msgBaseEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
							msgBaseAppVo.setMsgContent(msgBaseEntity.getMsgContent());
							msgBaseAppVo.setFroms(msgBaseEntity.getFroms());
							msgBaseAppVo.setTos(msgBaseEntity.getTos());
							msgBaseAppVo.setFid(msgBaseEntity.getHouseFid());
							msgBaseAppVo.setRentWay(msgBaseEntity.getRentWay());
							int msgSenderType = msgBaseEntity.getMsgSenderType().intValue();


							if((msgSenderType== UserTypeEnum.TENANT.getUserType()
									||msgSenderType== UserTypeEnum.LANDLORD.getUserType())){
								AppChatRecordsExt appChatRecordsExt = new AppChatRecordsExt();

								appChatRecordsExt.setHouseCard("0");
								appChatRecordsExt.setFid(msgBaseEntity.getHouseFid());
								appChatRecordsExt.setRentWay(msgBaseEntity.getRentWay());
								appChatRecordsExt.setHuanxinMsgId(UUIDGenerator.hexUUID());
								appChatRecordsExt.setZiroomFlag("ZIROOM_MINSU_IM");
								TenantHouseDetailVo  tenantHouseDetailVo =  getTenantHouseDetailVo(msgBaseEntity.getHouseFid(), msgBaseEntity.getRentWay());
								if(!Check.NuNObj(tenantHouseDetailVo)){
									appChatRecordsExt.setHouseName(tenantHouseDetailVo.getHouseName());
									appChatRecordsExt.setHousePicUrl(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), detail_big_pic));
								}
								msgBaseAppVo.setAppChatRecordsExt(appChatRecordsExt);

							}
							//咨询消息 转化成 房客的消息  
							if(msgSenderType== UserTypeEnum.All.getUserType()){
								MsgBookAdviceRequest msgBookAdviceRequest = JsonEntityTransform.json2Object(msgBaseEntity.getMsgContent(), MsgBookAdviceRequest.class);
								if(!Check.NuNObj(msgBookAdviceRequest)){
									msgBaseAppVo.setMsgContent(msgBookAdviceRequest.getTripPurpose());
									msgBaseAppVo.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
									AppChatRecordsExt appChatRecordsExt = new AppChatRecordsExt();
									appChatRecordsExt.setEndDate(msgBookAdviceRequest.getEndTime());

									appChatRecordsExt.setHouseCard("1");
									appChatRecordsExt.setPersonNum(Check.NuNObj(msgBookAdviceRequest.getPeopleNum())?"":msgBookAdviceRequest.getPeopleNum()+"");
									appChatRecordsExt.setStartDate(msgBookAdviceRequest.getStartTime());
									appChatRecordsExt.setZiroomFlag("ZIROOM_MINSU_IM");
									appChatRecordsExt.setHouseName(msgBookAdviceRequest.getHouseName());
									appChatRecordsExt.setHousePicUrl(msgBookAdviceRequest.getHousePicUrl());

									// 填充 房源名称和房源图片
									if(Check.NuNStr(msgBookAdviceRequest.getHouseName())){
										TenantHouseDetailVo  tenantHouseDetailVo =  getTenantHouseDetailVo(msgBaseEntity.getHouseFid(), msgBaseEntity.getRentWay());
										if(!Check.NuNObj(tenantHouseDetailVo)){
											appChatRecordsExt.setHouseName(tenantHouseDetailVo.getHouseName());
											appChatRecordsExt.setHousePicUrl(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), detail_big_pic));

											if(!Check.NuNStr(msgBookAdviceRequest.getMsgHouseFid())){
												msgBookAdviceRequest.setHouseName(tenantHouseDetailVo.getHouseName());
												msgBookAdviceRequest.setHousePicUrl(appChatRecordsExt.getHousePicUrl());
												MsgBaseEntity msgBase= new MsgBaseEntity();
												msgBase.setMsgContent(JsonEntityTransform.Object2Json(msgBookAdviceRequest));
												msgBase.setMsgHouseFid(msgBaseEntity.getMsgHouseFid());
												msgBase.setFid(msgBaseEntity.getFid());
												this.msgBaseService.updateByCondition(JsonEntityTransform.Object2Json(msgBase));
											}
										}
									}
									appChatRecordsExt.setFid(msgBaseEntity.getHouseFid());
									appChatRecordsExt.setRentWay(msgBaseEntity.getRentWay());
									if(Check.NuNStr(appChatRecordsExt.getHuanxinMsgId())){
										appChatRecordsExt.setHuanxinMsgId(UUIDGenerator.hexUUID());
									}
									msgBaseAppVo.setAppChatRecordsExt(appChatRecordsExt);

								}
							}
							if((msgSenderType== UserTypeEnum.TENANT_HUANXIN.getUserType()
									||msgSenderType== UserTypeEnum.LANDLORD_HUAXIN.getUserType())
									&&!Check.NuNStr(msgBaseEntity.getMsgContent())){
								Map<String, Object> msgContentMap = (Map<String, Object>) JsonEntityTransform.json2Map(msgBaseEntity.getMsgContent());
								if(!Check.NuNMap(msgContentMap)){
									Object msgContent = msgContentMap.get("msgContent");
									Object  appChatRecordsExt = msgContentMap.get("appChatRecordsExt");
									if(!Check.NuNObj(msgContent)){
										msgBaseAppVo.setMsgContent(msgContent.toString());
									}
									if(!Check.NuNObj(appChatRecordsExt)){
										msgBaseAppVo.setAppChatRecordsExt(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(appChatRecordsExt), AppChatRecordsExt.class));
									}
								}
								msgBaseAppVo.setMsgSenderType(UserTypeEnum.LANDLORD.getUserType());
								if(msgSenderType== UserTypeEnum.TENANT_HUANXIN.getUserType()){
									msgBaseAppVo.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
								}
							}

							listMsgBaseAppVo.add(msgBaseAppVo);
							dto.putValue("listMsg", listMsgBaseAppVo);
							dto.putValue("count", count);
						}
					}
				} catch (SOAParseException e) {

					LogUtil.error(logger, "参数解析异常e={}", e);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("参数解析异常");
				}
			}
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		} catch (Exception e) {
			LogUtil.error(logger, "查询聊天记录异常e={}", e);
		}

	}

	/**
	 * 
	 * 查询房源详情
	 *
	 * @author yd
	 * @created 2016年10月13日 下午8:32:20
	 *
	 * @param houseFid
	 * @param rentWay
	 * @return
	 * @throws SOAParseException
	 */
	private TenantHouseDetailVo getTenantHouseDetailVo(String houseFid,Integer rentWay) throws SOAParseException{

		HouseDetailDto houseDetailDto = new HouseDetailDto();
		houseDetailDto.setFid(houseFid);
		houseDetailDto.setRentWay(rentWay);
		TenantHouseDetailVo tenantHouseDetailVo = null;
		//查询房源详情  咨询消息 需要重新查库  并且更新 当前库的 消息
		String resultJson=tenantHouseService.findHouseDetail(JsonEntityTransform.Object2Json(houseDetailDto));
		DataTransferObject dtoHou =  JsonEntityTransform.json2DataTransferObject(resultJson);

		if(dtoHou.getCode() == DataTransferObject.SUCCESS){
			tenantHouseDetailVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", TenantHouseDetailVo.class);
		}

		return tenantHouseDetailVo;
	}





	/**
	 * 
	 * 保存APP端信息
	 * 
	 * 算法：
	 * 1. 校验参数
	 * 2. 保存消息 按好友保存（备注：房源信息 不在存在，房源信息保存到消息中  消息按map的json格式存储）   
	 *
	 * @author yd
	 * @created 2016年9月21日 上午11:01:00
	 *
	 * @param request
	 * @param appChatRecordsDto
	 */
	@RequestMapping("${LOGIN_UNAUTH}/saveImMsg")
	@ResponseBody
	public void saveImMsg(HttpServletRequest request,AppChatRecordsDto appChatRecordsDto,AppChatRecordsExt appChatRecordsExt, MsgCheckErrorLogDto msgCheckErrorLogDto){

		LogUtil.info(logger, "【IM回调】appChatRecordsDto={},appChatRecordsExt={},msgCheckErrorLogDto={}",JsonEntityTransform.Object2Json(appChatRecordsDto),JsonEntityTransform.Object2Json(appChatRecordsExt),JsonEntityTransform.Object2Json(msgCheckErrorLogDto));

		if (checkAppChatRecordsDto(appChatRecordsDto)){
			String uid = (String) request.getAttribute("uid");
			int userType = appChatRecordsDto.getMsgSentType();
			if((userType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()&&!uid.equals(appChatRecordsDto.getLandlordUid()))
					||(userType == UserTypeEnum.TENANT_HUANXIN.getUserType()&&!uid.equals(appChatRecordsDto.getTenantUid()))){

				LogUtil.info(logger, "saveImMsg：保存环信聊天记录，用户非法，参数appChatRecordsDto={}",appChatRecordsDto.toJsonStr());
				return;
			}
			Object source = request.getAttribute("source");
			if(!Check.NuNObj(source)){
				appChatRecordsExt.setSource(Integer.valueOf(source.toString()));
			}
			//SendThreadPool.execute(new SaveMsgLogThread(appChatRecordsDto,appChatRecordsExt, msgBaseService));
		}


		try {
			Header header = getHeader(request);
			String uid = getUserId(request);
			LogUtil.info(logger, "IM save header：header={}",JsonEntityTransform.Object2Json(header));
			SaveHeaderTask task = new SaveHeaderTask(uid, header, getIpAddress(request), LocationTypeEnum.IM,appChatRecordsExt.getFid(),appChatRecordsExt.getRentWay(), customerLocationService, redisOperations);
			SynLocationThreadPool.execute(task);
		} catch (Exception e) {
			LogUtil.error(logger, "saveImMsg：保存头信息异常，e={}",e);
		}
		
	}
	/**
	 * 
	 * 校验 扩展信息以及消息体信息
	 *
	 * @author yd
	 * @created 2016年9月21日 下午4:33:53
	 *
	 * @param appChatRecordsDto
	 * @return
	 */
	private boolean checkAppChatRecordsDto(AppChatRecordsDto appChatRecordsDto){

		if(Check.NuNObj(appChatRecordsDto)||Check.NuNStr(appChatRecordsDto.getLandlordUid())
				||Check.NuNStr(appChatRecordsDto.getMsgContent())
				||Check.NuNObj(appChatRecordsDto.getMsgSentType())
				||Check.NuNStr(appChatRecordsDto.getTenantUid())){

			LogUtil.info(logger, "来自于IM聊天参数错误", Check.NuNObj(appChatRecordsDto)?"":appChatRecordsDto.toJsonStr());
			return false;
		}

		return true;
	}

	/**
	 * 
	 * 来自于IM短信的请求   去到app房源详情页面
	 * 
	 * 说明：
	 * 1. 短信点击链接请求  到达跳转页面  重定向到app端
	 *
	 * @author yd
	 * @created 2016年9月22日 上午10:03:38
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/goToApp")
	public String goToApp(HttpServletRequest request){
		String toChatUsername = request.getParameter("toChatUsername");
		String msgSenderType  = request.getParameter("msgSenderType");
		String uid  = request.getParameter("uid");
		LogUtil.info(logger, "来自IM短信跳转app，参数toChatUsername={},msgSenderType={},uid={}", toChatUsername,msgSenderType,uid);
		String params = "?toChatUsername="+toChatUsername+"&msgSenderType="+msgSenderType+"&uid="+uid;
		request.setAttribute("IM_APP_IOS", IM_APP_IOS);
		request.setAttribute("IM_APP_ANDROID", IM_APP_ANDROID);
		request.setAttribute("params", params);
		return "/im/goToApp";
	}

	/**
	 * 短信跳转房源列表页
	 * @param request
	 * @return
     */
	@RequestMapping("${NO_LOGIN_AUTH}/goToHouseList")
	public String goToHouseList(HttpServletRequest request){
//		String toChatUsername = request.getParameter("toChatUsername");
//		String msgSenderType  = request.getParameter("msgSenderType");
//		String uid  = request.getParameter("uid");
//		LogUtil.info(logger, "来自短信跳转app房源列表，参数toChatUsername={},msgSenderType={},uid={}", toChatUsername,msgSenderType,uid);
//		String params = "?toChatUsername="+toChatUsername+"&msgSenderType="+msgSenderType+"&uid="+uid;
		request.setAttribute("IM_APP_IOS_HOUSELIST", IM_APP_IOS_HOUSELIST);
		request.setAttribute("IM_APP_ANDROID_HOUSELIST", IM_APP_ANDROID_HOUSELIST);
//		request.setAttribute("params", params);
		return "/im/goToHouseList";
	}
	
	
	/**
	 * 
	 * IM消息 出错 打印日志
	 *
	 * @author yd
	 * @created 2016年10月20日 下午7:50:05
	 *
	 * @param request
	 * @param appChatRecordsDto
	 * @param appChatRecordsExt
	 */
	@RequestMapping("${LOGIN_UNAUTH}/imMsgError")
	@ResponseBody
	public void imMsgError(HttpServletRequest request,AppChatRecordsDto appChatRecordsDto,AppChatRecordsExt appChatRecordsExt){
		
		LogUtil.info(logger, "IMERROR:消息错误,来源source={},appChatRecordsDto={},appChatRecordsExt={}",request.getParameter("source"), appChatRecordsDto.toJsonStr(),JsonEntityTransform.Object2Json(appChatRecordsExt));
		
		if(checkAppChatRecordsDto(appChatRecordsDto)){
			String uid = (String) request.getAttribute("uid");
			int userType = appChatRecordsDto.getMsgSentType();
			if((userType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()&&!uid.equals(appChatRecordsDto.getLandlordUid()))
					||(userType == UserTypeEnum.TENANT_HUANXIN.getUserType()&&!uid.equals(appChatRecordsDto.getTenantUid()))){

				LogUtil.info(logger, "saveImMsg：保存环信聊天记录，用户非法，参数appChatRecordsDto={}",appChatRecordsDto.toJsonStr());
				return;
			}
			Object source = request.getAttribute("source");
			if(!Check.NuNObj(source)){
				appChatRecordsExt.setSource(Integer.valueOf(source.toString()));
			}
			appChatRecordsDto.setAppChatRecordsExt(appChatRecordsExt);
			//SendThreadPool.execute(new SaveMsgBaseErrorThred(appChatRecordsDto, msgBaseService));
		}
	}
	
	/**
	 * 
	 * 房东或房客 条件查询IM聊天历史数据（ 数据是当前俩人的聊天数据）
	 * 
	 * 说明：
	 * 对于房源名称  房源图片  咨询消息 重新查询数据库
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/syncImChatMsg")
	@ResponseBody
	public void syncImChatMsg(HttpServletRequest request,ChatRecordsRequest chatRecordsRe,HttpServletResponse response){

		try {
			DataTransferObject dto = new  DataTransferObject();
			if(Check.NuNObjs(chatRecordsRe,chatRecordsRe.getSenderType())
					||Check.NuNStr(chatRecordsRe.getTenantUid())
					||Check.NuNStr(chatRecordsRe.getLandlordUid())){

				LogUtil.error(logger, "查询聊天记录，参数错误chatRecordsRe={}", chatRecordsRe == null?"":chatRecordsRe);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
			}
			
			chatRecordsRe.setPage(LoginAuthConst.PAGE);
			chatRecordsRe.setLimit(LoginAuthConst.PAGE_COUNT);
			
			MsgHouseRequest msgHouseRe  = new MsgHouseRequest();
			msgHouseRe.setTenantUid(chatRecordsRe.getTenantUid());
			msgHouseRe.setLandlordUid(chatRecordsRe.getLandlordUid());
			msgHouseRe.setLimit(chatRecordsRe.getLimit());
			msgHouseRe.setPage(chatRecordsRe.getPage());

			dto = JsonEntityTransform.json2DataTransferObject(this.msgBaseService.findIMChatRecord(JsonEntityTransform.Object2Json(msgHouseRe)));

			if(dto.getCode() == DataTransferObject.SUCCESS){
				try {
					List<AppMsgBaseVo> listMsgBase = SOAResParseUtil.getListValueFromDataByKey(dto.toJsonString(), "listMsg", AppMsgBaseVo.class);
					if(!Check.NuNCollection(listMsgBase)){
						dto = new DataTransferObject();
						List<MsgBaseAppVo> listMsgBaseAppVo = new ArrayList<MsgBaseAppVo>();
						for (AppMsgBaseVo msgBaseEntity : listMsgBase) {
							MsgBaseAppVo msgBaseAppVo = new MsgBaseAppVo();
							msgBaseAppVo.setMsgSenderType(msgBaseEntity.getMsgSenderType());
							msgBaseAppVo.setCreateTime(DateUtil.dateFormat(msgBaseEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
							msgBaseAppVo.setMsgSendTime(DateUtil.dateFormat(msgBaseEntity.getMsgSendTime(), "yyyy-MM-dd HH:mm:ss"));
							msgBaseAppVo.setMsgContent(msgBaseEntity.getMsgContent());
							msgBaseAppVo.setFroms(msgBaseEntity.getFroms());
							msgBaseAppVo.setTos(msgBaseEntity.getTos());
							msgBaseAppVo.setFid(msgBaseEntity.getHouseFid());
							msgBaseAppVo.setRentWay(msgBaseEntity.getRentWay());
							int msgSenderType = msgBaseEntity.getMsgSenderType().intValue();

						
							if((msgSenderType== UserTypeEnum.TENANT_HUANXIN.getUserType()
									||msgSenderType== UserTypeEnum.LANDLORD_HUAXIN.getUserType())
									&&!Check.NuNStr(msgBaseEntity.getMsgContent())){
								Map<String, Object> msgContentMap = (Map<String, Object>) JsonEntityTransform.json2Map(msgBaseEntity.getMsgContent());
								if(!Check.NuNMap(msgContentMap)){
									Object msgContent = msgContentMap.get("msgContent");
									Object  appChatRecordsExt = msgContentMap.get("appChatRecordsExt");
									if(!Check.NuNObj(msgContent)){
										msgBaseAppVo.setMsgContent(msgContent.toString());
									}
									if(!Check.NuNObj(appChatRecordsExt)){
										msgBaseAppVo.setAppChatRecordsExt(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(appChatRecordsExt), AppChatRecordsExt.class));
									}
								}
								msgBaseAppVo.setMsgSenderType(UserTypeEnum.LANDLORD.getUserType());
								if(msgSenderType== UserTypeEnum.TENANT_HUANXIN.getUserType()){
									msgBaseAppVo.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
								}
							}

							listMsgBaseAppVo.add(msgBaseAppVo);
							dto.putValue("listMsg", listMsgBaseAppVo);
							//dto.putValue("count", count);
						}
					}
				} catch (SOAParseException e) {

					LogUtil.error(logger, "参数解析异常e={}", e);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("参数解析异常");
				}
			}
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		} catch (Exception e) {
			LogUtil.error(logger, "查询聊天记录异常e={}", e);
		}

	}

	/**
	 * 同步聊天列表
	 * @author jixd
	 * @created 2017年04月01日 17:26:58
	 * @param
	 * @return
	 */
	//@RequestMapping("${NO_LOGIN_AUTH}/syncLoginUserMsg")//测试用
	@RequestMapping("${LOGIN_UNAUTH}/syncLoginUserMsg")
	@ResponseBody
	public void syncLoginUserMsg(MsgSyncRequest msgSyncRequest,HttpServletResponse response){
		DataTransferObject dto = new DataTransferObject();
		try{
			if (Check.NuNObj(msgSyncRequest)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			LogUtil.info(logger,"【syncLoginUserMsg】参数={}",JsonEntityTransform.Object2Json(msgSyncRequest));
			if (Check.NuNStr(msgSyncRequest.getUid()) || Check.NuNObj(msgSyncRequest.getPage())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return;
			}
			String dateStr = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
			Date date = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
			Date time = DateUtil.getTime(date, -DAY);
			msgSyncRequest.setTillDate(time);
			msgSyncRequest.setLimit(LIMIT);
			LogUtil.info(logger,"【syncLoginUserMsg】处理后参数={}",JsonEntityTransform.Object2Json(msgSyncRequest));
			DataTransferObject imListDto = JsonEntityTransform.json2DataTransferObject(msgBaseService.listImMsgSyncList(JsonEntityTransform.Object2Json(msgSyncRequest)));
			if (imListDto.getCode() == DataTransferObject.ERROR){
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(imListDto));
				return;
			}
			Object total = imListDto.getData().get("total");
			List<ImMsgSyncVo> list = imListDto.parseData("list", new TypeReference<List<ImMsgSyncVo>>() {});
			List<ImSessionListVo> responseList = new ArrayList<>();
			if (!Check.NuNCollection(list)){
				for (ImMsgSyncVo imMsgSyncVo : list){
					ImSessionListVo imSessionListVo = new ImSessionListVo();
					Integer msgSenderType = imMsgSyncVo.getMsgSenderType();
					if (msgSenderType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()){
						imSessionListVo.setFromUid(imMsgSyncVo.getLandlordUid());
						imSessionListVo.setToUid(imMsgSyncVo.getTenantUid());
						imSessionListVo.setMsgSenderType(UserTypeEnum.LANDLORD.getUserType());
					}
					if (msgSenderType == UserTypeEnum.TENANT_HUANXIN.getUserType()){
						imSessionListVo.setFromUid(imMsgSyncVo.getTenantUid());
						imSessionListVo.setToUid(imMsgSyncVo.getLandlordUid());
						imSessionListVo.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
					}
					imSessionListVo.setMsgSendTime(imMsgSyncVo.getMsgSendTime());
					String msgContent = imMsgSyncVo.getMsgContent();
					if (Check.NuNStr(msgContent)){
						continue;
					}
					
					imSessionListVo.setChatType(ChatTypeEnum.chat.getVal());
					JSONObject extObj =  null;
					try {
						 extObj = JSONObject.parseObject(msgContent);
					} catch (Exception e) {
						LogUtil.error(logger, "【同步消息解析异常】imMsgSyncVo={},e={}", JsonEntityTransform.Object2Json(imMsgSyncVo),e);
						continue;
					}
					String content = extObj.getString("msgContent");
					String recext = extObj.getString("appChatRecordsExt");
					imSessionListVo.setType(imMsgSyncVo.getType());
					//填充图片相关字段
					if(!Check.NuNStr(imMsgSyncVo.getType()) && ImTypeEnum.IMG_MSG.getType().equals(imMsgSyncVo.getType())){
						imSessionListVo.setFileLength(imMsgSyncVo.getFileLength());
						imSessionListVo.setFilename(imMsgSyncVo.getFilename());
						imSessionListVo.setSecret(imMsgSyncVo.getSecret());
						imSessionListVo.setSize(imMsgSyncVo.getSize());
						imSessionListVo.setType(imMsgSyncVo.getType());
						imSessionListVo.setUrl(imMsgSyncVo.getUrl());
					}
					
					ImExtVo imExtVo = JSONObject.parseObject(recext, ImExtVo.class);
					//真正的消息内容
					String msgRealContent = imMsgSyncVo.getMsgRealContent();
					if(Check.NuNStr(content)){
						imSessionListVo.setMsgContent(msgRealContent);
					}else{
						imSessionListVo.setMsgContent(content);
					}
					imSessionListVo.setAppChatRecordsExt(imExtVo);
					responseList.add(imSessionListVo);
				}
			}
			
			//填充 除自如宿的聊天信息
			fillHuanxinImRecord(responseList, imListDto);
/*			for (ImSessionListVo imSessionListVo : responseList) {
				if(imSessionListVo.getType().equals("img")){
					System.out.println(imSessionListVo.getFilename());
					System.out.println(imSessionListVo.getSecret());
					System.out.println(imSessionListVo.getSize());
					System.out.println(imSessionListVo.getUrl());
					System.out.println(imSessionListVo.getFileLength());
					System.out.println(imSessionListVo.getMsgSendTime());
					System.out.println(imSessionListVo.getFilename());
				}
			}*/
			dto.putValue("total",total);
			dto.putValue("list",responseList);
			LogUtil.info(logger,"【syncLoginUserMsg】返回结果={}",dto.toJsonString());
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		}catch (Exception e){
			LogUtil.error(logger,"【syncLoginUserMsg】 异常={}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
			try {
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

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
	 * 填充 除 自如宿的 聊天信息
	 *
	 * @author yd
	 * @created 2017年8月2日 下午3:36:57
	 *
	 * @param responseList
	 * @param imListDto
	 */
	private  void fillHuanxinImRecord(List<ImSessionListVo> responseList,DataTransferObject imListDto){
		
		try {

			if(responseList != null&&!Check.NuNObj(imListDto)){
				
				List<HuanxinImRecordEntity>  listHuanxinImRecord = imListDto.parseData("listHuanxinImRecord", new TypeReference<List<HuanxinImRecordEntity>>() {
				});
				List<String>  allAbnormalPicList = imListDto.parseData("allAbnormalPic", new TypeReference<List<String>>() {
				});
				if(!Check.NuNCollection(listHuanxinImRecord)){
					List<ImSessionListVo> listVo = new ArrayList<ImSessionListVo>();
					for (HuanxinImRecordEntity huanxinImRecordEntity : listHuanxinImRecord) {
						ImSessionListVo imSessionListVo = new ImSessionListVo();
						imSessionListVo.setFromUid(huanxinImRecordEntity.getFromUid());
						imSessionListVo.setMsgContent(huanxinImRecordEntity.getMsg());
						imSessionListVo.setMsgSendTime(huanxinImRecordEntity.getTimestampSend());
						imSessionListVo.setToUid(huanxinImRecordEntity.getToUid());
						imSessionListVo.setChatType(huanxinImRecordEntity.getChatType());
						imSessionListVo.setType(huanxinImRecordEntity.getType());
						if(!Check.NuNStr(huanxinImRecordEntity.getType()) && ImTypeEnum.IMG_MSG.getType().equals(huanxinImRecordEntity.getType())){
							imSessionListVo.setFileLength(huanxinImRecordEntity.getFileLength());
							imSessionListVo.setFilename(huanxinImRecordEntity.getFilename());
							imSessionListVo.setSecret(huanxinImRecordEntity.getSecret());
							imSessionListVo.setSize(huanxinImRecordEntity.getSize());
							imSessionListVo.setType(huanxinImRecordEntity.getType());
							imSessionListVo.setUrl(huanxinImRecordEntity.getUrl());
						}
						//区分自如驿和长租
						if(IM_ZRY_FLAG.equals(huanxinImRecordEntity.getZiroomFlag())){
							ImExtVo imExtVo = JSONObject.parseObject(huanxinImRecordEntity.getExt(), ImExtVo.class);
							imExtVo.setHuanxinMsgId(huanxinImRecordEntity.getMsgId());
							imSessionListVo.setAppChatRecordsExt(imExtVo);
							listVo.add(imSessionListVo);
						}
						if(IM_CHANGZU_FLAG.equals(huanxinImRecordEntity.getZiroomFlag())){
							ImExtForChangzuVo imExtForChangzuVo = JSONObject.parseObject(huanxinImRecordEntity.getExt(), ImExtForChangzuVo.class);
							imExtForChangzuVo.setHuanxinMsgId(huanxinImRecordEntity.getMsgId());
							//文本消息
								if(!Check.NuNObj(imExtForChangzuVo) && !Check.NuNObj(imExtForChangzuVo.getZiroomType()) 
										&& (MsgUserRelSourceTypeEnum.SHIELD.getCode()==imExtForChangzuVo.getZiroomType()
										|| MsgUserRelSourceTypeEnum.CANCELSHIELD.getCode()==imExtForChangzuVo.getZiroomType()
										|| MsgUserRelSourceTypeEnum.COMPLAINT.getCode()==imExtForChangzuVo.getZiroomType())){
									continue;
								}
							//填充“图片是否需要模糊处理”
							if(ImTypeEnum.IMG_MSG.getType().equals(huanxinImRecordEntity.getType()) && allAbnormalPicList.contains(huanxinImRecordEntity.getFid())){
								imExtForChangzuVo.setIsPicNeedVagueDeal(YesOrNoEnum.YES.getCode());
							}
							imSessionListVo.setChangZuExt(imExtForChangzuVo);
							listVo.add(imSessionListVo);
						}
					}
					responseList.addAll(listVo);
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "【消息同步异常】e={}", e);
		}
		
		
	}

	/**
	 * 
	 * 保存APP端信息
	 * 
	 * 算法：
	 * 1. 校验参数
	 * 2. 保存消息 按好友保存（备注：房源信息 不在存在，房源信息保存到消息中  消息按map的json格式存储）   
	 *
	 * @author yd
	 * @created 2016年9月21日 上午11:01:00
	 *
	 * @param request
	 * @param appChatRecordsDto
	 */
	@RequestMapping("${LOGIN_UNAUTH}/saveMsgLog")
	@ResponseBody
	public void saveMsgLog(HttpServletRequest request,MsgHuanxinImLogEntity msgHuanxinImLog){


		String jsonParam = "";
		jsonParam = msgHuanxinImLog==null?"":JsonEntityTransform.Object2Json(msgHuanxinImLog);
		if(Check.NuNObj(msgHuanxinImLog)||Check.NuNStr(msgHuanxinImLog.getToUid())
				||Check.NuNStr(msgHuanxinImLog.getFromUid())
				||Check.NuNStr(msgHuanxinImLog.getChatType())
				||Check.NuNStr(msgHuanxinImLog.getZiroomFlag())
				||Check.NuNObj(msgHuanxinImLog.getChatStatu())){
			LogUtil.error(logger, "【IM回调】saveMsgLog：保存环信聊天记录，参数非法，参数msgHuanxinImLog={}",jsonParam);
			return;
		}
		
		msgHuanxinImLog.setFromUid(getTrueUid(msgHuanxinImLog.getFromUid()));
		msgHuanxinImLog.setToUid(getTrueUid(msgHuanxinImLog.getToUid()));
		if(ImTypeEnum.TXT_MSG.getType().equals(msgHuanxinImLog.getType())){
			msgHuanxinImLog.setZiroomType(ImForTxtMsgTypeEnum.NORMAL_MSG.getCode());
			if(!Check.NuNStr(msgHuanxinImLog.getExt())){
				ImExtForChangzuVo imExtForChangzuVo = JSONObject.parseObject(msgHuanxinImLog.getExt(), ImExtForChangzuVo.class);
				if(!Check.NuNObj(imExtForChangzuVo)){
					msgHuanxinImLog.setZiroomType(imExtForChangzuVo.getZiroomType());
				}
			}
		}
		if (!Check.NuNObj(msgHuanxinImLog)&&!Check.NuNStr(msgHuanxinImLog.getFromUid())){
			String uid = (String) request.getAttribute("uid");
			if(!uid.equals(msgHuanxinImLog.getFromUid())){
				LogUtil.info(logger, "【IM回调】saveMsgLog：保存环信聊天记录，用户非法，参数msgHuanxinImLog={},uid={}",jsonParam,uid);
				return;
			}
		}

		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//huanxinImManagerService.saveMsgHuanxinImLog(JsonEntityTransform.Object2Json(msgHuanxinImLog));
				
			}
		});
		th.start();
	}
	
	/**
	 * 
	 * 退群
	 *
	 * @author yd
	 * @created 2017年8月11日 下午4:58:28
	 *
	 * @param groupMemberDto
	 * @param request
	 */
	@RequestMapping("${LOGIN_UNAUTH}/exitGroup")
	@ResponseBody
	public void exitGroup(GroupMemberDto groupMemberDto ,HttpServletRequest request,HttpServletResponse response){
		
		if(Check.NuNObj(groupMemberDto)||Check.NuNStr(groupMemberDto.getGroupId())){
			LogUtil.error(logger, "【退群】参数错误：groupMemberDto={}",JsonEntityTransform.Object2Json(groupMemberDto));
			return;
		}
		String uid =  (String) request.getAttribute("uid");
		
		List<String> memberList = new ArrayList<String>();
		memberList.add(uid);
		groupMemberDto.setOpFid(uid);
		groupMemberDto.setOpType(CreaterTypeEnum.ZIROOM_USER.getCode());
		groupMemberDto.setMembers(memberList);
		groupMemberDto.setSourceType(SourceTypeEnum.ZRY_TENANT_EXIT_GROUP.getCode());
		
		try {
		  String result = 	huanxinImManagerService.removeGroupMembers(JsonEntityTransform.Object2Json(groupMemberDto));
		  LogUtil.info(logger, "【退群结果】result={}", result);
		  DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(JsonEntityTransform.json2DataTransferObject(result)));
		} catch (Exception e) {
			LogUtil.error(logger, "【退群异常】：groupMemberDto={}",JsonEntityTransform.Object2Json(groupMemberDto));
		}
	
	}

	
	/**
	 * 
	 * 聊天好友关系查询
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午3:58:28
	 *
	 * @param msgUserRel
	 * @param request
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryMsgUserRel")
	@ResponseBody
	public void queryMsgUserRel(HttpServletRequest request, MsgUserRelEntity msgUserRel,HttpServletResponse response){
		DataTransferObject dto = new DataTransferObject();
		String jsonParam = "";
		jsonParam = msgUserRel==null?"":JsonEntityTransform.Object2Json(msgUserRel);
		try {
			String uid =  (String) request.getAttribute("uid");
			//String uid =  (String) request.getParameter("uid");//测试用
			if(Check.NuNStr(uid)){
	        	LogUtil.error(logger, "聊天好友关系查询，用户uid为空，uid={}",uid);
	        	dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("登录uid为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
	        }
			
			if(Check.NuNObj(msgUserRel)||Check.NuNStr(msgUserRel.getToUid())
					||Check.NuNStr(msgUserRel.getFromUid())
					||Check.NuNStr(msgUserRel.getZiroomFlag())){
				LogUtil.error(logger, "【聊天好友关系查询】，fromUid,toUid,ziroomFlag三个参数其中一个为空，参数msgUserRel={}",jsonParam);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("fromUid,toUid,ziroomFlag三个参数其中一个为空");
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
			}
			
			msgUserRel.setFromUid(getTrueUid(msgUserRel.getFromUid()));
			msgUserRel.setToUid(getTrueUid(msgUserRel.getToUid()));
			if (!Check.NuNObj(msgUserRel)&&!Check.NuNStr(msgUserRel.getFromUid())){
				if(!uid.equals(msgUserRel.getFromUid())){
					LogUtil.error(logger, "【聊天好友关系查询】queryMsgUserRel：登录uid和fromUid不匹配，参数msgUserRel={},uid={}",jsonParam,uid);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("登录uid和fromUid不匹配");
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
					return ;
				}
			}
			//获取关系
			String queryMsgUserRel = huanxinImManagerService.queryMsgUserRel(JsonEntityTransform.Object2Json(msgUserRel));
			DataTransferObject queryMsgUserRelDto 	 = JsonEntityTransform.json2DataTransferObject(queryMsgUserRel);
			if(queryMsgUserRelDto.getCode()==DataTransferObject.SUCCESS){
				Integer shieldFlag = queryMsgUserRelDto.parseData("shieldFlag", new TypeReference<Integer>() {});
				if(!Check.NuNObj(shieldFlag)){
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(queryMsgUserRelDto));
					return ;
				}
			}
			dto.putValue("shieldFlag", 0);
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		} catch (BusinessException | IOException e) {
			LogUtil.error(logger, "好友关系查询错误",e);
		}
	}
	
	/**
	 * 
	 * 查询用户活跃度
	 *
	 * @author loushuai
	 * @created 2017年9月14日 下午3:58:28
	 * @param request
	 * @param msgUserLiveness
	 * @param response
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryLiveness")
	@ResponseBody
	public void queryLiveness(HttpServletRequest request,UserLivenessReqeust userLivenessReqeust, HttpServletResponse response){
		DataTransferObject dto = new DataTransferObject();
		String jsonParam = "";
		
		jsonParam = userLivenessReqeust==null?"":JsonEntityTransform.Object2Json(userLivenessReqeust);
		try {
				String uid =  (String) request.getAttribute("uid");
				
				if(Check.NuNStr(uid)){
		        	LogUtil.error(logger, "查询用户活跃度，用户uid为空，uid={}",uid);
		        	dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("登录uid为空");
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
					return ;
		        }
				String parameter = request.getParameter("uidList");
				JSONObject jsonObject = new JSONObject();
				List<String> uidList = (List<String>) JSON.parse(parameter);
				userLivenessReqeust.setUidList(uidList);
						
				if(Check.NuNCollection(uidList)){
					LogUtil.error(logger, "查询用户活跃度，参数非法，参数msgUserRel={}",jsonParam);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("参数uidList为空");
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
					return ;
				}
				
				List<MsgUserInfoVo> msgUserInfoList = new ArrayList<MsgUserInfoVo>();
				
				//获取所有用户的头像和昵称
				List<CustomerVo> customerVoList = null;
				CustomerChatRequest chatRequest = new CustomerChatRequest();
				chatRequest.setUidList(uidList);
				String userPicAndNickName = customerChatService.getListUserPicAndNickName(JsonEntityTransform.Object2Json(chatRequest));
				DataTransferObject userPicDto  = JsonEntityTransform.json2DataTransferObject(userPicAndNickName);
				if(userPicDto.getCode()==DataTransferObject.SUCCESS){
					customerVoList = userPicDto.parseData("list", new TypeReference<List<CustomerVo>>() {});
				}

				//填充所有用户活跃度
				List<MsgUserLivenessEntity> msgUserLivenessList = null;
				
				String result = msgBaseService.queryLiveness(JsonEntityTransform.Object2Json(userLivenessReqeust));
				DataTransferObject livenessDto  = JsonEntityTransform.json2DataTransferObject(result);
				if(livenessDto.getCode()==DataTransferObject.SUCCESS){
				    msgUserLivenessList = livenessDto.parseData("list", new TypeReference<List<MsgUserLivenessEntity>>() {});
					
				}
				
				//填充用户头像，个人介绍 ，活跃度
				if(!Check.NuNCollection(customerVoList)){
					for (CustomerVo customerVo : customerVoList) {
						MsgUserInfoVo msgUserInfoVo = new MsgUserInfoVo();
						if(uidList.contains(customerVo.getUid())){
							msgUserInfoVo.setUid(customerVo.getUid());
							msgUserInfoVo.setUserPicUrl(customerVo.getUserPicUrl());
							msgUserInfoVo.setNickName(customerVo.getNickName());
							for (MsgUserLivenessEntity msgUserLiveness : msgUserLivenessList) {
								if(msgUserLiveness.getUid().equals(customerVo.getUid())){
									String liveness = getLiveness(new Date(), msgUserLiveness.getLastLiveTime());
									msgUserInfoVo.setLiveness(liveness);
								}
							}
							msgUserInfoList.add(msgUserInfoVo);
						}
						
					}
				}
				dto.putValue("userInfoList", msgUserInfoList);
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));	
	        }catch (BusinessException | IOException e) {
				LogUtil.error(logger, "查询用户活跃度",e);
			}
	
    }
	
	/**
	 * 
	 * 获取客户活跃度（在前端展示文案）
	 *
	 * @author loushuai
	 * @created 2017年9月1日 下午3:36:57
	 *
	 * @param endDate
	 * @param nowDate
	 */
	public static String getLiveness(Date nowDate, Date lastLiveTime) {
		 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = nowDate.getTime() - lastLiveTime.getTime();
	    // 计算差多少天
	    long day = diff / nd;
	    // 计算差多少小时
	    long hour = diff % nd / nh;
	    // 计算差多少分钟
	    long min = diff % nd % nh / nm;
	    
	    if(day==0 && hour==0){
	    	if(min<1){
	    		return "正在活跃";
	    	}
	    	return String.valueOf(min)+"分钟前活跃";
	    }else if(day==0 && hour!=0){
	    	return String.valueOf(hour)+"小时前活跃";
	    }else if(day==1){
	    	return "昨天活跃";
	    }else if(day>=2 && day < 30){
	    	return String.valueOf(day)+"天前活跃";
	    }
	    return null;
	}

}
