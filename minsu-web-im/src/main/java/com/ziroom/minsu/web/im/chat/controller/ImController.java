
package com.ziroom.minsu.web.im.chat.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.jpush.JpushUtils;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.HouseCheckDto;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.api.inner.StatusConfService;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.dto.MsgBookAdviceRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.web.im.chat.controller.dto.MsgBookInfoDto;
import com.ziroom.minsu.web.im.common.enumvalue.ImSourceEnum;

/**
 * <p>留言消息视图层</p>
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
@RequestMapping("im")
public class ImController {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(ImController.class);

	@Resource(name = "message.msgHouseService")
	private MsgHouseService msgHouseService;

	@Resource(name = "message.msgBaseService")
	private MsgBaseService msgBaseService;

	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Resource(name = "message.statusConfService")
	private StatusConfService statusConfService;

	@Value("${MAPP_DOMAIN}")
	private String MAPP_DOMAIN;
	
	/**
	 * 分页条数
	 */
	private final static int pageNum = 20;

	/**
	 * 消息字数控制
	 */
	private final static int msgContentNum = 500;
	
	/*
	 * 开IM键
	 */
	private final String IM_OPEN_FLAG = "IM_OPEN_FLAG";
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${detail_big_pic}'.trim()}")
	private String detailBigPic;

	/**
	 * 
	 * 查询当前用户和房东的留言  轮询查询
	 * 入口：
	 * 1.房源详情（app_ios app_android）
	 * 2.消息列表（mapp） 订单列表
	 * 
	 * 3.也可能是客户端（client）
	 * 4.点击极光进入jpushFlag
	 * 
	 * 说明：
	 * 1.校验房源或房间信息 并返回房源的fid
	 * 2.查询房源留言关联表 返回msgHouseFid
	 * 3.如果msgHouseFid为null 插入
	 *
	 * @author yd
	 * @created 2016年4月19日 下午6:21:18
	 *
	 * @param request
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryMessageBase")
	public String queryMessageBase(HttpServletRequest request){

		String imSource  = request.getParameter("imSource"); //请求来源
		String msgSenderType = request.getParameter("msgSenderType");//消息发送人类型（1=房东 2=房客） 
		String jpushFlag = request.getParameter("jpushFlag")==null?"":request.getParameter("jpushFlag");//来源2=代表来源极光的推送链接
		String version =request.getParameter("version") == null ?"-1":request.getParameter("version");
		
		//IM打开标志位
		//Integer isOpen = isOpenNewVersion();
		request.setAttribute("isOpen", 1);

		if(Check.NuNStr(imSource)||Check.NuNStr(msgSenderType)){
			LogUtil.error(logger, "IM请求参数错误,请求来源imSource={},msgSenderType={},version={}",imSource,msgSenderType,version);
			return "/im/imChat";
		}
		LogUtil.info(logger, "IM请求参数错误,请求来源imSource={},msgSenderType={},version={}",imSource,msgSenderType,version);
		request.setAttribute("version", version);
		request.setAttribute("nowTime",DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
		request.setAttribute("msgSenderType", msgSenderType);
		request.setAttribute("imSource", imSource);
		request.setAttribute("jpushFlag", jpushFlag);
		request.setAttribute("dSource", request.getParameter("dSource"));
		request.setAttribute("MAPP_DOMAIN", MAPP_DOMAIN);

		int sendNum = 0;

		if(!Check.NuNStr(jpushFlag)){
			sendNum = 2;
		}
		request.setAttribute("sendNum", sendNum);

		int currImSource = Integer.valueOf(imSource);
		if(currImSource == ImSourceEnum.APP_IOS.getCode()||currImSource == ImSourceEnum.APP_ANDROID.getCode()){
			return imFromApp(request,jpushFlag ,msgSenderType);
		}
		if(currImSource == ImSourceEnum.MAPP.getCode()){
			return imFromMapp(request,jpushFlag ,msgSenderType);
		}

		return "/im/imChat";
	}

	/**
	 * im 来源mapp
	 *
	 * @author yd
	 * @created 2016年6月14日 下午4:32:02
	 *
	 * @param request
	 * @return
	 */
	private String imFromMapp(HttpServletRequest request,String jpushFlag,String msgSenderType){
		String orderSn = request.getParameter("orderSn");
		if(Check.NuNStr(orderSn)){
			return imMappChatList(request,jpushFlag,msgSenderType);
		}else{
			return imMappOrder(request,jpushFlag,msgSenderType);
		}
	}

	/**
	 * im 来自于 mapp 聊天列表
	 * @author jixd on 2016年7月6日
	 */
	
	
	private String imMappChatList(HttpServletRequest request,String jpushFlag,String msgSenderType){
		String  msgHouseFid = request.getParameter("msgHouseFid"); 
		String imSourceList = request.getParameter("imSourceList");
		if(Check.NuNStr(msgHouseFid)){
			LogUtil.error(logger,"请求参数错误msgHouseFid={}",msgHouseFid);
		}
		LogUtil.info(logger,"请求参数错误msgHouseFid={},imSourceList={}",msgHouseFid,imSourceList);
		DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(msgHouseService.queryByFid(msgHouseFid));

		if(dto.getCode() == DataTransferObject.SUCCESS){
			MsgHouseEntity msgHouseEntity = dto.parseData("msgHouse", new TypeReference<MsgHouseEntity>() {
			});
			if(!Check.NuNObj(msgHouseEntity)){
				setUserPicUrl(request, msgHouseEntity.getTenantUid(), msgHouseEntity.getLandlordUid());

				//设置消息已读
				MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
				msgBaseRequest.setMsgHouseFid(msgHouseEntity.getFid());
				msgBaseRequest.setMsgSenderType(Integer.parseInt(msgSenderType) == 1?UserTypeEnum.TENANT.getUserType():UserTypeEnum.LANDLORD.getUserType());
				msgBaseService.updateByMsgHouseReadFid(JsonEntityTransform.Object2Json(msgBaseRequest));
				
				int rentWay = msgHouseEntity.getRentWay().intValue();
				String fid = "";
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					fid = msgHouseEntity.getHouseFid();
				}else{
					fid = msgHouseEntity.getRoomFid();
				}
				getHouseInfo(fid, rentWay, request);
				request.setAttribute("fid", fid);
				request.setAttribute("rentWay", rentWay);
			}
		}
		
		
		request.setAttribute("msgHouseFid", msgHouseFid);
		request.setAttribute("imSourceList", imSourceList);
		return "/im/imChat";
	}

	/**
	 * im 来自 mapp 订单列表
	 * @author jixd on 2016年7月6日
	 */
	private String imMappOrder(HttpServletRequest request,String jpushFlag,String msgSenderType){
		String orderSn = request.getParameter("orderSn");
		String orderDetailJson = orderCommonService.getOrderInfoByOrderSn(orderSn);
		String uid = request.getParameter("uid");
		try{
			DataTransferObject orderDetailDto = JsonEntityTransform.json2DataTransferObject(orderDetailJson);

			if(orderDetailDto.getCode() == DataTransferObject.SUCCESS){
				OrderInfoVo orderInfo = SOAResParseUtil.getValueFromDataByKey(orderDetailJson, "orderInfoVo", OrderInfoVo.class);
				String landUid = orderInfo.getLandlordUid();
				if(!landUid.equals(uid)){
					LogUtil.error(logger, "没有权限,不是当前房东uid订单uid={},orderSn={}", uid,orderSn);
					return "/im/imChat";
				}
				MsgBookAdviceRequest msgBookRequest =  null;

				String tenUid = orderInfo.getUserUid();
				Integer rentWay = orderInfo.getRentWay();
				String houseFid = orderInfo.getHouseFid();
				String roomFid = orderInfo.getRoomFid();
				String fid= "";

				//查询
				MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
				msgHouseRequest.setTenantUid(tenUid);
				msgHouseRequest.setLandlordUid(landUid);
				msgHouseRequest.setRentWay(rentWay);
				msgHouseRequest.setHouseFid(houseFid);
				fid = houseFid;
				if(rentWay == RentWayEnum.ROOM.getCode()){
					msgHouseRequest.setRoomFid(roomFid);
					fid = roomFid;
				}
				if(rentWay == RentWayEnum.BED.getCode()){
					msgHouseRequest.setRoomFid(roomFid);
					msgHouseRequest.setBedFid(orderInfo.getBedFid());
				}
				DataTransferObject msgHouseListDto = JsonEntityTransform.json2DataTransferObject(msgHouseService.queryAllByPage(JsonEntityTransform.Object2Json(msgHouseRequest)));
				List<MsgHouseEntity> msgHouseEntityList = msgHouseListDto.parseData("listMsgHouse", new TypeReference<List<MsgHouseEntity>>() {});
				if(Check.NuNCollection(msgHouseEntityList)){
					MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
					msgHouseEntity.setFid(UUIDGenerator.hexUUID());
					msgHouseEntity.setHouseFid(houseFid);
					msgHouseEntity.setRentWay(rentWay);
					if(rentWay == RentWayEnum.ROOM.getCode()){
						msgHouseEntity.setRoomFid(roomFid); 
					}
					msgHouseEntity.setTenantUid(tenUid);
					msgHouseEntity.setLandlordUid(landUid);
					DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(msgHouseService.save(JsonEntityTransform.Object2Json(msgHouseEntity)));
					if(resultDto.getCode() == DataTransferObject.SUCCESS){
						request.setAttribute("msgHouseFid", msgHouseEntity.getFid());
					}

				}else{
					MsgHouseEntity msgHouseEntity = msgHouseEntityList.get(0);
					String msgHouseFid = msgHouseEntity.getFid();

					//设置消息已读
					MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
					msgBaseRequest.setMsgHouseFid(msgHouseEntity.getFid());
					msgBaseRequest.setMsgSenderType(Integer.parseInt(msgSenderType) == 1?UserTypeEnum.TENANT.getUserType():UserTypeEnum.LANDLORD.getUserType());
					msgBaseService.updateByMsgHouseReadFid(JsonEntityTransform.Object2Json(msgBaseRequest));

					request.setAttribute("msgHouseFid", msgHouseFid);
					
				}
				setUserPicUrl(request, tenUid, landUid);
				getHouseInfo(fid, rentWay, request);
				request.setAttribute("fid", fid);
				request.setAttribute("rentWay", rentWay);
				return "/im/imChat";
			}

		}catch(Exception e){
			LogUtil.error(logger, "im来自订单异常:error={}", e);
			return "/im/imChat";
		}
		return "/im/imChat";
	}

	/**
	 * 
	 * 设置房客和房东头像
	 *
	 * @author yd
	 * @created 2016年6月15日 下午4:46:43
	 *
	 * @param request
	 * @param msgHouseFid
	 */
	private void setUserPicUrl(HttpServletRequest request,String tenantUid,String lanlordUid){

		DataTransferObject dto = null;
		if(!Check.NuNStr(tenantUid)){
			dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(tenantUid));
			if(dto.getCode() == DataTransferObject.SUCCESS){
				CustomerVo customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
				});
				if(!Check.NuNObj(customerVo)){
					request.setAttribute("tenantUrl", customerVo.getUserPicUrl());
					String nickName = customerVo.getNickName();
					if(Check.NuNStr(nickName)){
						nickName = "我的消息";
					}
					request.setAttribute("tenantName",nickName);
				}
			}


		}
		if(!Check.NuNStr(lanlordUid)){
			dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(lanlordUid));
			if(dto.getCode() == DataTransferObject.SUCCESS){
				CustomerVo customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
				});
				if(!Check.NuNObj(customerVo)){
					request.setAttribute("lanlordUrl", customerVo.getUserPicUrl());
					String nickName = customerVo.getNickName();
					if(Check.NuNStr(nickName)){
						nickName = "我的消息";
					}
					request.setAttribute("lanlordName",nickName);
				}
			}
		}
	}

	/**
	 * 
	 * 第一次聊天时候给对方极光推送
	 * 场景
	 *  房客：
	 *  1.房客在房源详情点击，推送给房东，房东点击推送开始聊天
	 *  2.房客在房客消息列表点解，推送给房客，房客点击开始聊天
	 *  房东：
	 *  1.房东在消息列表点击，推送给房客，房客点击推送开始聊天
	 *
	 * @author yd
	 * @created 2016年6月18日 下午3:42:54
	 *
	 * @param uid
	 */
	private void jpushIm(String uid,String fid,int rentWay,String jpushFlag,String msgSenderType,String jpUid){

		try {
			if(Check.NuNStr(jpushFlag)){
				JpushConfig jpushConfig = new JpushConfig();

				Map<String, String> extrasMap = new HashMap<String, String>();
				extrasMap.put("msg_body_type", "minsu_notify");
				extrasMap.put("msg_sub_type", "5");
				extrasMap.put("uid",uid);
				extrasMap.put("fid",fid);
				extrasMap.put("rentWay",String.valueOf(rentWay));
				if(!Check.NuNStr(msgSenderType)){
					int type = Integer.valueOf(msgSenderType).intValue();
					if(type == UserTypeEnum.LANDLORD.getUserType()){
						extrasMap.put("msgSenderType",String.valueOf(UserTypeEnum.TENANT.getUserType()));
					}
					if(type == UserTypeEnum.TENANT.getUserType()){
						extrasMap.put("msgSenderType",String.valueOf(UserTypeEnum.LANDLORD.getUserType()));
					}
				}

				jpushConfig.setContent("您有新的消息,请到消息管理查看！");
				jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
				jpushConfig.setExtrasMap(extrasMap);

				LogUtil.info(logger, "IM聊天极光推送参数jpushConfig={},推送者uid={}，接收者uid={}", jpushConfig.toString(),uid,jpUid);
				JpushUtils.sendPushOne(jpUid, jpushConfig, null);

			}
		} catch (Exception e) {
			LogUtil.error(logger, "IM聊天极光推送失败e={}", e);
		}


	}

	/**
	 * im 当天第一次聊天发送短信
	 * @author jixd on 2016年7月6日
	 */
	private void sendMsgIm(String uid,int userType){
		try {
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(uid));
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
					smsRequest.setParamsMap(paramsMap);
					String msgCode = "";
					if(userType == UserTypeEnum.LANDLORD.getUserType()){
						msgCode = "704";
					}
					if(userType == UserTypeEnum.TENANT.getUserType()){
						msgCode = "703";
					}
					if(!Check.NuNStr(msgCode)){
						smsRequest.setSmsCode(msgCode);
						LogUtil.info(logger, "IM短信发送接口调用开始，参数smsRequest={}", smsRequest);
						smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));

					}
				}

			}
		} catch (Exception e) {
			LogUtil.error(logger, "I短信发送异常e={}", e);
		}
	}

	/**
	 * 
	 * im来源app 房客主动请求打开
	 *
	 * @author yd
	 * @created 2016年6月14日 下午4:26:28
	 *
	 * @param request
	 * @return
	 */
	private String imFromApp(HttpServletRequest request,String jpushFlag, String msgSenderType ){

		String fid = request.getParameter("fid");//房源或者房间fid
		String rentWay  = request.getParameter("rentWay");
		String tenantUid  = request.getParameter("uid"); //房客uid

		if(Check.NuNStr(fid)||Check.NuNStr(rentWay)||Check.NuNStr(tenantUid)){
			LogUtil.error(logger, "IM请求参数错误,房源或房间fid={},出租方式rentWay={},房客uid={}", fid,rentWay,tenantUid);
			return "/im/imChat";
		}


		HouseCheckDto houseCheckDto = new HouseCheckDto();

		houseCheckDto.setFid(fid);
		houseCheckDto.setRentWay(Integer.valueOf(rentWay));

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseManageService.checkHouseOrRoom(JsonEntityTransform.Object2Json(houseCheckDto)));

		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(logger, dto.getMsg());
			return "/im/imChat";
		}
		int currRentWay = Integer.valueOf(rentWay).intValue();
		MsgHouseRequest msRequest = new MsgHouseRequest();
		msRequest.setLimit(1);
		String houseFid = "";
		String landlordUid="";
		String  msgHouseFid = "";
		landlordUid = String.valueOf(dto.getData().get("landlordUid"));
		if(currRentWay == RentWayEnum.HOUSE.getCode()){
			houseFid = fid;

		}
		if(currRentWay == RentWayEnum.ROOM.getCode()){
			houseFid = String.valueOf(dto.getData().get("houseFid"));
			msRequest.setRoomFid(fid);
		} 
		msRequest.setHouseFid(houseFid);
		msRequest.setRentWay(houseCheckDto.getRentWay());
		msRequest.setTenantUid(tenantUid);
		msRequest.setLandlordUid(landlordUid);
		//查询当前IM主记录
		dto = JsonEntityTransform.json2DataTransferObject(msgHouseService.queryAllByPage(JsonEntityTransform.Object2Json(msRequest)));
		MsgBookAdviceRequest msgBookRequest   = null;
		if(dto.getCode() == DataTransferObject.SUCCESS){
			List<MsgHouseEntity> listMsgHouse = dto.parseData("listMsgHouse", new TypeReference<List<MsgHouseEntity>>() {
			});

			if(!Check.NuNCollection(listMsgHouse)){
				msgHouseFid =  listMsgHouse.get(0).getFid();
				//房客设置消息已读 （房东发的消息置为已读）
				//房东发消息，把房客发的消息置已读
				MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
				msgBaseRequest.setMsgHouseFid(msgHouseFid);
				msgBaseRequest.setMsgSenderType(Integer.parseInt(msgSenderType) == 1?UserTypeEnum.TENANT.getUserType():UserTypeEnum.LANDLORD.getUserType());
				msgBaseService.updateByMsgHouseReadFid(JsonEntityTransform.Object2Json(msgBaseRequest));

			}else{
				MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
				msgHouseEntity.setFid(UUIDGenerator.hexUUID());
				msgHouseEntity.setHouseFid(houseFid);
				msgHouseEntity.setRentWay(houseCheckDto.getRentWay());
				if(currRentWay == RentWayEnum.ROOM.getCode()){
					msgHouseEntity.setRoomFid(msRequest.getRoomFid()); 
				}
				msgHouseEntity.setTenantUid(tenantUid);
				msgHouseEntity.setLandlordUid(landlordUid);
				dto = JsonEntityTransform.json2DataTransferObject(msgHouseService.save(JsonEntityTransform.Object2Json(msgHouseEntity)));
				if(dto.getCode() == DataTransferObject.SUCCESS){
					msgHouseFid = msgHouseEntity.getFid();
				}
			}

			//保存房源预定信息
		  /*String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");

			String tripPurpose = request.getParameter("tripPurpose");
			
			if (!Check.NuNStr(startTime) && !Check.NuNStr(endTime) && !Check.NuNStr(request.getParameter("peopleNum"))) {

				int peopleNum = Integer.valueOf( request.getParameter("peopleNum"));
				msgBookRequest   = new MsgBookAdviceRequest();
				msgBookRequest.setMsgHouseFid(msgHouseFid);
				msgBookRequest.setEndTime(endTime);
				msgBookRequest.setPeopleNum(peopleNum);
				msgBookRequest.setStartTime(startTime); 
				msgBookRequest.setTripPurpose(tripPurpose);

				saveBookAdviceMsg(msgBookRequest);
				sendMsgIm(landlordUid,UserTypeEnum.TENANT.getUserType());
				
			}*/

		}
		setUserPicUrl(request, tenantUid, landlordUid);
		getHouseInfo(fid,currRentWay, request);

		request.setAttribute("msgHouseFid", msgHouseFid);
		request.setAttribute("fid", fid);
		request.setAttribute("rentWay", rentWay);
		return "/im/imChat";
	}

	/**
	 * 设置房源信息
	 * @param fid
	 * @param rentWay
	 * @param request
	 */
	private void getHouseInfo(String fid,int rentWay,HttpServletRequest request){
		
		if(!Check.NuNStr(fid)){
			
			DataTransferObject dto =JsonEntityTransform.json2DataTransferObject( this.houseManageService.findOrderNeedHouseVo(fid, rentWay));
			
			if(dto.getCode() == DataTransferObject.SUCCESS){
				OrderNeedHouseVo orderNeedHouseVo = dto.parseData("houseBase", new TypeReference<OrderNeedHouseVo>() {
				});
				
				if(!Check.NuNObj(orderNeedHouseVo)){
					request.setAttribute("rentWayStr", RentWayEnum.getRentWayByCode(rentWay).getName());
					
					String houseName = "";
					if(rentWay == RentWayEnum.HOUSE.getCode()){
						houseName = orderNeedHouseVo.getHouseName();
					}else{
						houseName = orderNeedHouseVo.getRoomName();
					}
					request.setAttribute("houseName", houseName);
					if(!Check.NuNObj(orderNeedHouseVo.getHouseDefaultPic())){
						request.setAttribute("housePicUrl", PicUtil.getSpecialPic(picBaseAddrMona, orderNeedHouseVo.getHouseDefaultPic().getPicBaseUrl()+orderNeedHouseVo.getHouseDefaultPic().getPicSuffix(), detailBigPic));
					}
					
				}
			}
		}
		
	}

	/**
	 * 
	 * 
	 * @author zl
	 * 
	 * @param msgBookRequest
	 * @return
	 */
	public MsgBaseEntity saveBookAdviceMsg(MsgBookAdviceRequest msgBookRequest) {
		
		MsgBaseEntity msgBaseEntity = null;
		if(!Check.NuNObj(msgBookRequest)&&!Check.NuNStr(msgBookRequest.getStartTime())
				&&!Check.NuNStr(msgBookRequest.getEndTime())
				&&!Check.NuNStr(msgBookRequest.getMsgHouseFid())
				&&!Check.NuNObj(msgBookRequest.getPeopleNum())){

			LogUtil.info(logger, "带保存咨询信息实体msgBookRequest={}", msgBookRequest.toString());
			Date curDate = new Date();
			msgBookRequest.setCreateTime(DateUtil.dateFormat(curDate, "yyyy-MM-dd HH:mm:ss"));
			msgBaseEntity = new MsgBaseEntity();
			msgBaseEntity.setMsgContent(JsonEntityTransform.Object2Json(msgBookRequest));
			msgBaseEntity.setMsgHouseFid(msgBookRequest.getMsgHouseFid());
			msgBaseEntity.setMsgSenderType(UserTypeEnum.All.getUserType());
			msgBaseEntity.setCreateTime(curDate);
			JsonEntityTransform.json2DataTransferObject(this.msgBaseService.save(JsonEntityTransform.Object2Json(msgBaseEntity)));
		}
		
		return msgBaseEntity;
	}
	
	/**
	 * 查询当前记录的咨询信息
	 * 条件：主记录fid  发送类型 = 3
	 * @param msgHouseFid
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryMsgBook")
	@ResponseBody
	public String queryMsgBook(HttpServletRequest request){
		
		
		String msgHouseFid = request.getParameter("msgHouseFid");
		MsgBookInfoDto msgBookInfo  = new MsgBookInfoDto();
		if (!Check.NuNStr(msgHouseFid)) {
			try {
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgBaseService.queryCurrMsgBook(msgHouseFid));

				if(dto.getCode() == DataTransferObject.SUCCESS){
					MsgBaseEntity msgBase = dto.parseData("msgBase", new TypeReference<MsgBaseEntity>() {
					});
					if(!Check.NuNObj(msgBase)){
						MsgBookAdviceRequest msgBookAdviceRequest = JsonEntityTransform.json2Object(msgBase.getMsgContent(), MsgBookAdviceRequest.class);
						if(!Check.NuNObj(msgBookAdviceRequest)){
							msgBookInfo.setEndTime(msgBookAdviceRequest.getEndTime());
							msgBookInfo.setStartTime(msgBookAdviceRequest.getStartTime());
							msgBookInfo.setTripPurpose(msgBookAdviceRequest.getTripPurpose());
							msgBookInfo.setPeopleNum(msgBookAdviceRequest.getPeopleNum());
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(logger, "查询咨询信息异常e={}", e);
			}
		
		}
		return JsonEntityTransform.Object2Json(msgBookInfo);
	}

	/**
	 * 
	 * 根据时间查询当前用户和房东的留言
	 * 算法实现：
	 * 1.翻页条数增加
	 * 2.每次增加pageCount*pageNum
	 * 3.最多500页  1000 条记录
	 * @author yd
	 * @created 2016年4月19日 下午8:14:43
	 *
	 * @param request
	 * @param msgBaseRequest
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/listMsgBaseByTime")
	public @ResponseBody List<MsgBaseEntity> listMsgBaseByTime(HttpServletRequest request,@ModelAttribute("msgBaseRequest") MsgBaseRequest msgBaseRequest){
		List<MsgBaseEntity> lisBaseEntities = null;
		if(!Check.NuNStr(msgBaseRequest.getMsgHouseFid())){

			int pageCount = msgBaseRequest.getPage();
			if(pageCount<=500){
				msgBaseRequest.setLimit(pageNum*pageCount);
				msgBaseRequest.setPage(1);

				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(msgBaseService.queryAllMsgByCondition(JsonEntityTransform.Object2Json(msgBaseRequest)));
				lisBaseEntities =  dto.parseData("listMsgBase",new TypeReference<List<MsgBaseEntity>>() {
				});
				Collections.reverse(lisBaseEntities);
			}

		}

		if(lisBaseEntities == null){
			lisBaseEntities = new ArrayList<MsgBaseEntity>();
		}


		return lisBaseEntities;
	}

	/**
	 * 
	 * 根据时间查询当前用户和房东的IM信息
	 * @author yd
	 * @created 2016年4月19日 下午8:14:43
	 *
	 * @param request
	 * @param msgBaseRequest
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/saveMsgBase")
	@ResponseBody
	public String saveMsgBase(HttpServletRequest request){

		String msgContent = request.getParameter("msgContent");
		String msgSentType = request.getParameter("msgSentType");
		String msgHouseFid = request.getParameter("msgHouseFid");
		String sendNum = request.getParameter("sendNum") == null?"1":request.getParameter("sendNum");//本次会话 发送次数
		String jpushFlag = request.getParameter("jpushFlag");

		String reslut = "0";
		if(Check.NuNStr(msgHouseFid)||Check.NuNStr(msgSentType)||Check.NuNStr(msgHouseFid)||UserTypeEnum.getUserTypeByCode(Integer.valueOf(msgSentType))==null
				||Check.NuNStr(msgContent)||msgContent.length()>msgContentNum){
			return reslut;
		}

		MsgBaseEntity msgBaseEntity = new  MsgBaseEntity();
		msgBaseEntity.setMsgContent(msgContent);
		msgBaseEntity.setMsgHouseFid(msgHouseFid);
		msgBaseEntity.setMsgSenderType(Integer.valueOf(msgSentType));
		Date currTime = new Date();
		msgBaseEntity.setCreateTime(currTime);

		LogUtil.info(logger, "待保存消息实体msgBaseEntity={}", msgBaseEntity.toJsonStr());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgBaseService.save(JsonEntityTransform.Object2Json(msgBaseEntity)));
		if(dto.getCode() ==DataTransferObject.SUCCESS){
			reslut = dto.getData().get("result").toString();
			if("1".equals(sendNum)&&Integer.valueOf(reslut).intValue()>0){
				MsgBaseRequest msgBaseRe  = new MsgBaseRequest();
				msgBaseRe.setMsgHouseFid(msgHouseFid);
				dto = JsonEntityTransform.json2DataTransferObject(msgHouseService.queryByFid(msgHouseFid));
				if(dto.getCode() == DataTransferObject.SUCCESS){
					MsgHouseEntity msgHouseEntity = dto.parseData("msgHouse", new TypeReference<MsgHouseEntity>() {});
					if(!Check.NuNObj(msgHouseEntity)){
						//极光推送
						int msgSentTypeInt = Integer.valueOf(msgSentType).intValue();
						String fid = msgHouseEntity.getHouseFid();
						if(!Check.NuNObj(msgHouseEntity.getRentWay())&&msgHouseEntity.getRentWay().intValue()==RentWayEnum.ROOM.getCode()){
							fid = msgHouseEntity.getRoomFid();
						}
						if(msgSentTypeInt == UserTypeEnum.LANDLORD.getUserType()){
							jpushIm(msgHouseEntity.getLandlordUid(), fid, msgHouseEntity.getRentWay().intValue(), jpushFlag,msgSentType, msgHouseEntity.getTenantUid());
						}else{
							jpushIm(msgHouseEntity.getTenantUid(), fid, msgHouseEntity.getRentWay().intValue(), jpushFlag,msgSentType, msgHouseEntity.getLandlordUid());
						}
						LogUtil.info(logger, "消息的当前时间currTime={}", DateUtil.dateFormat(currTime, "yyyy-MM-dd HH:mm:ss"));
						//发送短信
						todayFirstChatSendMsg(msgHouseEntity,msgSentTypeInt,currTime);
					}
				}
			}

		}

		return reslut;
	}

	/**
	 * 
	 * 房东设置消息已读状态（房客发的消息设置成已读）
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午6:33:00
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/setMsgRead")
	@ResponseBody
	public DataTransferObject setMsgRead(MsgBaseRequest msgBaseRequest){
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(msgBaseRequest.getMsgHouseFid())){
			dto.setErrCode(1);
			dto.setMsg("消息Fid为空");
			return dto;
		}
		if(Check.NuNObj(msgBaseRequest.getMsgSenderType())){
			dto.setErrCode(1);
			dto.setMsg("类型为空");
			return dto;
		}
		if(msgBaseRequest.getMsgSenderType() == UserTypeEnum.LANDLORD.getUserType()){
			msgBaseRequest.setMsgSenderType(UserTypeEnum.TENANT.getUserType());
		}else{
			msgBaseRequest.setMsgSenderType(UserTypeEnum.LANDLORD.getUserType());
		}
		return JsonEntityTransform.json2DataTransferObject(msgBaseService.updateByMsgHouseReadFid(JsonEntityTransform.Object2Json(msgBaseRequest)));
	}

	/**
	 * 说明：
	 * 半小时内无聊天记录就发短信提醒
	 * @author jixd on 2016年7月6日
	 */
	private void todayFirstChatSendMsg(MsgHouseEntity msgHouseEntity,int msgSendTypeInt,Date currTime){

		try {
			String fid = msgHouseEntity.getFid();
			MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
			String currentDate = DateUtil.dateFormat(currTime,"yyyy-MM-dd HH:mm:ss");
			String hafHourStr = DateUtil.dateFormat(new Date(currTime.getTime()-30*60*1000),"yyyy-MM-dd HH:mm:ss");
			msgBaseRequest.setStartTime(hafHourStr);
			msgBaseRequest.setEndTime(currentDate);
			msgBaseRequest.setMsgSenderType(msgSendTypeInt);
			msgBaseRequest.setMsgHouseFid(fid);
			DataTransferObject countDto = JsonEntityTransform.json2DataTransferObject(msgBaseService.queryMsgCountByItem(JsonEntityTransform.Object2Json(msgBaseRequest)));
			
			LogUtil.info(logger, "查询IM是否发短信的信息，参数msgBaseRequest={}，返回结果countDto={}", msgBaseRequest.toJsonStr(),countDto.toJsonString());
			if(countDto.getCode() == DataTransferObject.SUCCESS){
				int count = (int)countDto.getData().get("count");
				LogUtil.info(logger, "IM短信发送开始.....,查询返回消息数量count={}", count);
				if(count == 1){
					String uid = msgHouseEntity.getLandlordUid();
					if(msgSendTypeInt == UserTypeEnum.LANDLORD.getUserType()){
						uid = msgHouseEntity.getTenantUid();
					}
					//发送短信
					sendMsgIm(uid,msgSendTypeInt);
				}
			}else{
				LogUtil.info(logger, "查询失败countDto={}", countDto.toJsonString());
			}
		} catch (Exception e) {
			LogUtil.error(logger, "IM聊天短信发送失败，聊天记录msgHouseEntity={}，发送类型msgSendTypeInt={}", msgHouseEntity.toString(),msgSendTypeInt);
		}

	}
	
	/**
	 * 是否打开
	 * @author jixd on 2016年9月27日
	 */
	private Integer isOpenNewVersion(){
		Integer isOpen = 0;
		String valueResult = statusConfService.getStatusConfByKey(IM_OPEN_FLAG);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(valueResult);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(logger, "【获取IM标志位失败】msg={}", dto.getMsg());
		}
		String str = dto.parseData("value", new TypeReference<String>() {});
		isOpen = Integer.parseInt(str);
		return isOpen;
	}
	
}
