package com.ziroom.minsu.api.message.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.message.dto.HouseMsgParamDto;
import com.ziroom.minsu.api.message.dto.MsgLableDto;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.api.inner.MsgLableService;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.dto.MsgLableRequest;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsGloabalEnum;
import com.ziroom.minsu.valenum.msg.IsReleaseEnum;

/**
 * 
 * <p>房源留言API</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("auth/housemsg")
public class HouseMsgController {
	
	@Resource(name="message.msgBaseService")
	private MsgBaseService msgBaseService;
	
	@Resource(name="message.msgHouseService")
	private MsgHouseService msgHouseService;
	
	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	@Resource(name="message.msgLableService")
	private MsgLableService msgLableService;
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseMsgController.class);
	
	/**
	 * 
	 * 查询房客端留言列表（只显示最近的一条记录）
	 * 参数{"limit":"10","loginToken":"sdfsdfsdfsdf","uid":"4f5ds6f45d6sf","page":"1","type","2"}
	 * 返回{}
	 * @author jixd
	 * @created 2016年4月21日 下午3:12:23
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> houseMsgist(HttpServletRequest request){
		
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			HouseMsgParamDto baseParamDto = JsonEntityTransform.json2Object(paramJson,HouseMsgParamDto.class);
			MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
			msgHouseRequest.setLimit(baseParamDto.getLimit());
			msgHouseRequest.setPage(baseParamDto.getPage());
			
			String result = null;
			if(baseParamDto.getType() == 1){
				//房东
				msgHouseRequest.setLandlordUid(baseParamDto.getUid());
				result = msgHouseService.queryLandlordList(JsonEntityTransform.Object2Json(msgHouseRequest));
			}else if(baseParamDto.getType() == 2){
				//房客
				msgHouseRequest.setTenantUid(baseParamDto.getUid());
				result = msgHouseService.queryTenantList(JsonEntityTransform.Object2Json(msgHouseRequest));
			}else{
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("类型错误"),HttpStatus.OK);
			}
			
			if(result == null){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("返回结果错误"),HttpStatus.OK);
			}
			LogUtil.info(LOGGER, "result", result);
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(result);
			List<MsgHouseListVo> listvo = dto.parseData("listMsg", new TypeReference<List<MsgHouseListVo>>() {});
			String resultJson = "";
			//获取聊天房源名称
			for(int i = 0;i< listvo.size() ;i++){
				MsgHouseListVo msg = listvo.get(i);
				if(msg.getRentWay() == RentWayEnum.HOUSE.getCode()){
					//resultJson = houseIssueService.searchHouseBaseByFid(msg.getHouseFid());
					DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(resultJson);
					HouseBaseExtDto houseBase = houseDto.parseData("obj", new TypeReference<HouseBaseExtDto>() {});
					msg.setHouseName(houseBase.getHouseName());
				}else if(msg.getRentWay() == RentWayEnum.ROOM.getCode() || msg.getRentWay() == RentWayEnum.BED.getCode()){
					//resultJson = houseIssueService.searchHouseRoomMsgByFid(msg.getRoomFid());
					DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(resultJson);
					HouseRoomMsgEntity roomBase = houseDto.parseData("obj", new TypeReference<HouseRoomMsgEntity>() {});
					msg.setHouseName(roomBase.getRoomName());
				}else{
					//resultJson = houseIssueService.searchHouseBaseByFid(msg.getHouseFid());
					DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(resultJson);
					HouseBaseExtDto houseBase = houseDto.parseData("obj", new TypeReference<HouseBaseExtDto>() {});
					msg.setHouseName(houseBase.getHouseName());
				}
			}
			dto.putValue("listMsg", listvo);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	
	/**
	 * 
	 * 删除聊天消息
	 *参数{"msgHouseFid":"8a9e9c8b541e32c001541e32c0150000","loginToken":"sdfsdfsdfsdf","uid":"4f5ds6f45d6sf"}
	 * @author jixd
	 * @created 2016年4月21日 下午3:12:03
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="del",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> deleteHouseMsg(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			HouseMsgParamDto baseParamDto = JsonEntityTransform.json2Object(paramJson,HouseMsgParamDto.class);
			String fid = baseParamDto.getMsgHouseFid();
			if(Check.NuNStr(fid)){
				LogUtil.info(LOGGER, "fid为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
			}
			MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
			msgHouseEntity.setFid(fid);
			String delJson = msgHouseService.deleteByFid(JsonEntityTransform.Object2Json(msgHouseEntity));
			LogUtil.info(LOGGER, "删除返回："+delJson);
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(delJson);
			Integer count = (Integer) dto.getData().get("result");
			if(count > 0){
				msgBaseService.updateByMsgHouseFid(fid);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK("删除成功"), HttpStatus.OK);
			}else{
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("删除失败"), HttpStatus.OK);
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	/**
	 * 
	 * 更新系统消息为已读状态
	 *	参数 {"msgHouseFid":"8a9e9c8b541e32c001541e32c0150000","loginToken":"sdfsdfsdfsdf","uid":"4f5ds6f45d6sf"}
	 * @author jixd
	 * @created 2016年4月21日 下午4:14:35
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateRead",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> updateHouserMsgRead(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			HouseMsgParamDto baseParamDto = JsonEntityTransform.json2Object(paramJson,HouseMsgParamDto.class);
			String msgHouseFid = baseParamDto.getMsgHouseFid();
			if(Check.NuNStr(msgHouseFid)){
				LogUtil.info(LOGGER, "msgHouseFid为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
			}
			String delJson = msgBaseService.updateByMsgHouseReadFid(msgHouseFid);
			LogUtil.info(LOGGER, "更新已读返回："+delJson);
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(delJson);
			Integer count = (Integer) dto.getData().get("result");
			if(count > 0){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK("更新成功"), HttpStatus.OK);
			}else{
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("更新失败"), HttpStatus.OK);
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	
	/**
	 * 
	 * 获取聊天列表
	 *
	 * @author jixd
	 * @created 2016年4月30日 上午10:37:47
	 *
	 * @return
	 */
	@RequestMapping(value="chatList",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> chatList(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			HouseMsgParamDto baseParamDto = JsonEntityTransform.json2Object(paramJson,HouseMsgParamDto.class);
			String msgHouseFid = baseParamDto.getMsgHouseFid();
			if(Check.NuNStr(msgHouseFid)){
				LogUtil.info(LOGGER, "msgHouseFid为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("msgHouseFid为空"),HttpStatus.OK);
			}
			
			MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
			msgBaseRequest.setMsgHouseFid(msgHouseFid);
			msgBaseRequest.setPage(baseParamDto.getPage());
			msgBaseRequest.setLimit(baseParamDto.getLimit());
			msgBaseRequest.setIsDel(IsDelEnum.NOT_DEL.getCode());
			
			String jsonResult = msgBaseService.queryAllMsgByCondition(JsonEntityTransform.Object2Json(msgBaseRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(jsonResult);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultDto.getData()), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	
	/**
	 * 
	 * 显示房源标签
	 *
	 * @author jixd
	 * @created 2016年4月30日 上午11:18:07
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showlable",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> showHouseLable(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			MsgLableDto msgLableDto = JsonEntityTransform.json2Object(paramJson,MsgLableDto.class);
			if(Check.NuNStr(msgLableDto.getHouseFid())){
				LogUtil.info(LOGGER, "houseFid为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("houseFid为空"),HttpStatus.OK);
			}
			//个性标签
			MsgLableRequest lableRequest = new MsgLableRequest();
			lableRequest.setHouseFid(msgLableDto.getHouseFid());
			lableRequest.setIsRelease(IsReleaseEnum.RELEASE.getCode());
			lableRequest.setIsGlobal(IsGloabalEnum.NOT_ADAPT_GOLBAL.getCode());
			lableRequest.setLandlordFid(msgLableDto.getLandlordFid());
			
			String resultJson = msgLableService.queryByPage(JsonEntityTransform.Object2Json(lableRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultDto.getData()), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
}
