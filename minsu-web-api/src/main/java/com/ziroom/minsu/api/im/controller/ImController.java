
package com.ziroom.minsu.api.im.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.im.controller.dto.ChatRecordsRequest;
import com.ziroom.minsu.api.im.controller.dto.GoodsFriendsRequest;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.HouseCheckDto;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.dto.MsgCountRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>房客端IM聊天功能</p>
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
public class ImController {


	private static Logger logger = LoggerFactory.getLogger(ImController.class);

	@Resource(name = "message.msgHouseService")
	private MsgHouseService msgHouseService;

	@Resource(name = "message.msgBaseService")
	private MsgBaseService msgBaseService;

	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "api.messageSource")
	private MessageSource messageSource;

	/**
	 * 
	 * 客户端查询IM聊天记录
	 *
	 * @author yd
	 * @created 2016年6月15日 下午10:03:13
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/queryImList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryImList(HttpServletRequest request){


		DataTransferObject dto = new  DataTransferObject();

		String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(logger, "参数："+paramJson);
		MsgHouseRequest msRequest   = JsonEntityTransform.json2Object(paramJson, MsgHouseRequest.class);

		//String uid = request.getParameter("uid");

		try {
			//msRequest.setTenantUid(uid);
			dto = JsonEntityTransform.json2DataTransferObject(msgHouseService.queryTenantList(JsonEntityTransform.Object2Json(msRequest)));

			if(dto.getCode() == DataTransferObject.SUCCESS){
				List<MsgHouseListVo> listHouseListVos = dto.parseData("listMsg", new TypeReference<List<MsgHouseListVo>>() {
				});

				if(!Check.NuNCollection(listHouseListVos)){
					for (MsgHouseListVo msgHouseListVo : listHouseListVos) {

						if(!Check.NuNStr(msgHouseListVo.getLandlordUid())){
							dto =  JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(msgHouseListVo.getLandlordUid()));
							if(dto.getCode() == DataTransferObject.SUCCESS){
								CustomerVo cust = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
								});
								if(!Check.NuNObj(cust)){
									msgHouseListVo.setLanlordPicUrl(cust.getUserPicUrl());
									msgHouseListVo.setNickName(cust.getNickName());
								}
							}
						}
						if(!Check.NuNObj(msgHouseListVo.getRentWay())){
							HouseCheckDto houseCheckDto = new HouseCheckDto();
							houseCheckDto.setRentWay(msgHouseListVo.getRentWay());
							houseCheckDto.setFid(msgHouseListVo.getHouseFid());
							if(msgHouseListVo.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
								houseCheckDto.setFid(msgHouseListVo.getRoomFid());
							}

							dto = JsonEntityTransform.json2DataTransferObject(this.houseManageService.checkHouseOrRoom(JsonEntityTransform.Object2Json(houseCheckDto)));
							if(dto.getCode() == DataTransferObject.SUCCESS){
								String houseName = String.valueOf(dto.getData().get("houseName"));
								if(msgHouseListVo.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
									houseName = String.valueOf(dto.getData().get("roomName"));
								}
								msgHouseListVo.setHouseName(houseName);
							}
						}
					}
					dto.putValue("listMsg", listHouseListVos);
				}

			}
		} catch (Exception e) {
			LogUtil.info(logger, "房客端查询IM聊天记录异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房客端查询IM聊天记录异常");
		}

		return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
	}

	/**
	 * 
	 * 查询房客端未读消息个数
	 *
	 * @author jixd
	 * @created 2016年7月6日 下午8:24:55
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/unReadMsgNum")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> unReadMsgNum(HttpServletRequest request){
		DataTransferObject dto = new  DataTransferObject();
		try{
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "参数："+paramJson);
			MsgCountRequest msRequest = new MsgCountRequest();

			JSONObject jsonObj = JSONObject.parseObject(paramJson);
			String uid = jsonObj.getString("uid");
			msRequest.setMsgSenderType(UserTypeEnum.LANDLORD.getUserType());
			msRequest.setTenantUid(uid);
			if(Check.NuNStr(uid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房客Uid为空");
			}else{
				dto = JsonEntityTransform.json2DataTransferObject(msgBaseService.queryMsgCountByUid(JsonEntityTransform.Object2Json(msRequest)));
			}
		}catch(Exception e){
			LogUtil.info(logger, "房客端查询IM查询未读消息异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房客端查询IM查询未读消息异常");
		}
		return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
	}


	/**
	 * 
	 * 查询好友（根据房东好友uid或查询房客好友uid）
	 * 
	 * 只能单向查询(不能同时包含两个uid或同时为null)
	 *
	 * @author yd
	 * @created 2016年9月14日 下午4:13:29
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/queryFriends")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryFriendsUid(HttpServletRequest request){

		DataTransferObject dto = new  DataTransferObject();

		String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(logger, "参数："+paramJson);
		try {
			GoodsFriendsRequest goodsFriendsRe = JsonEntityTransform.json2Object(paramJson, GoodsFriendsRequest.class);
			String uid =  (String) request.getAttribute("uid");

			if(Check.NuNObj(goodsFriendsRe)||Check.NuNStr(goodsFriendsRe.getUid())||Check.NuNObj(goodsFriendsRe.getSenderType())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}

			if(!goodsFriendsRe.getUid().equals(uid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("用户非法");
				return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
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

		return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
	}

	/**
	 * 
	 * 房东或房客 条件查询IM聊天历史数据（ 数据是当前俩人的聊天数据）
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/queryChatRecord")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryTwoChatRecord(HttpServletRequest request){

		DataTransferObject dto = new  DataTransferObject();

		String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(logger, "参数："+paramJson);
		
		ChatRecordsRequest chatRecordsRe = JsonEntityTransform.json2Object(paramJson, ChatRecordsRequest.class);
		
		if(Check.NuNObj(chatRecordsRe)||Check.NuNStr(chatRecordsRe.getTenantUid())||Check.NuNStr(chatRecordsRe.getLandlordUid())||Check.NuNObj(chatRecordsRe.getSenderType())){
			
			LogUtil.error(logger, "查询聊天记录，参数错误chatRecordsRe={}", chatRecordsRe == null?"":chatRecordsRe);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}
		
		int msgType = chatRecordsRe.getSenderType().intValue();
		String uid = (String) request.getAttribute("uid");
		if((msgType == UserTypeEnum.TENANT.getUserType()&&!uid.equals(chatRecordsRe.getTenantUid()))
				||(msgType == UserTypeEnum.LANDLORD.getUserType()&&!uid.equals(chatRecordsRe.getLandlordUid()))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户非法");
			return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}
		
		if(Check.NuNObj(chatRecordsRe.getLimit())){
			chatRecordsRe.setLimit(50);
		}
		if(Check.NuNObj(chatRecordsRe.getPage())){
			chatRecordsRe.setPage(1);
		}
		
		MsgHouseRequest msgHouseRe  = new MsgHouseRequest();
		msgHouseRe.setTenantUid(chatRecordsRe.getTenantUid());
		msgHouseRe.setLandlordUid(chatRecordsRe.getLandlordUid());
		msgHouseRe.setLimit(chatRecordsRe.getLimit());
		msgHouseRe.setPage(chatRecordsRe.getPage());
		
		dto = JsonEntityTransform.json2DataTransferObject(this.msgBaseService.queryTwoChatRecord(JsonEntityTransform.Object2Json(msgHouseRe)));
		return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
	}
}
