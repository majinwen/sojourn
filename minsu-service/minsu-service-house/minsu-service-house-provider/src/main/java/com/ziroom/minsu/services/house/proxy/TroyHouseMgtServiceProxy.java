package com.ziroom.minsu.services.house.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.CatConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.house.logic.HouseCheckLogic;
import com.ziroom.minsu.services.house.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.logic.ValidateResult;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;
import com.ziroom.minsu.services.house.service.TroyHouseMgtServiceImpl;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>特洛伊-房源管理操作接口实现代理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Component("house.troyHouseMgtServiceProxy")
public class TroyHouseMgtServiceProxy implements TroyHouseMgtService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TroyHouseMgtServiceProxy.class);

	@Resource(name="house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;

	@Resource(name="house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;

	@Resource(name="house.messageSource")
	private MessageSource messageSource;

	@Resource(name="house.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Resource(name="house.houseCheckLogic")
	private HouseCheckLogic houseCheckLogic;

	@Resource(name="house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;

	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;

	@Resource(name="house.queueName")
	private QueueName queueName;


	@Override
	public String findHouseBaseByHouseSn(String houseSn) {
		LogUtil.info(LOGGER, "参数:{}", houseSn);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(houseSn)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			HouseBaseMsgEntity houseBase = troyHouseMgtServiceImpl.findHouseBaseByHouseSn(houseSn);
			dto.putValue("houseBase", houseBase);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	/**
	 * 根据房间编号查找房间信息
	 * @author jixd
	 * @created 2016年11月21日 10:41:20
	 * @param roomSn
	 * @return
	 */
	@Override
	public String findHouseRoomMsgByRoomSn(String roomSn){
		LogUtil.info(LOGGER, "参数:{}", roomSn);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(roomSn)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			HouseRoomMsgEntity roomBase = troyHouseMgtServiceImpl.findHouseRoomMsgByRoomSn(roomSn);
			dto.putValue("roomBase", roomBase);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHouseMsgList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		HouseRequestDto houseRequest = JsonEntityTransform.json2Object(paramJson, HouseRequestDto.class);
		DataTransferObject dto = new DataTransferObject();

		// 校验查询参数是否为空
		if (Check.NuNObj(houseRequest)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房屋出租类型是否为空
		if (Check.NuNObj(houseRequest.getRentWay())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			PagingResult<HouseResultVo> pagingResult = new PagingResult<HouseResultVo>(); 
			if(houseRequest.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_HOUSE){
				pagingResult = troyHouseMgtServiceImpl.findHouseMsgListByHouse(houseRequest);
			}else if(houseRequest.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_ROOM){
				pagingResult = troyHouseMgtServiceImpl.findHouseMsgListByRoom(houseRequest);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_ERROR));
				LogUtil.error(LOGGER, "返回结果:{},参数:{}", dto.toJsonString(), paramJson);
				return dto.toJsonString();
			}
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", pagingResult.getRows());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	
	@Override
	public String searchUpateHouseMsgList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		HouseRequestDto houseRequest = JsonEntityTransform.json2Object(paramJson, HouseRequestDto.class);
		DataTransferObject dto = new DataTransferObject();

		// 校验查询参数是否为空
		if (Check.NuNObj(houseRequest)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房屋出租类型是否为空
		if (Check.NuNObj(houseRequest.getRentWay())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			PagingResult<HouseResultVo> pagingResult = new PagingResult<HouseResultVo>(); 
			if(houseRequest.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_HOUSE){
				pagingResult = troyHouseMgtServiceImpl.findUpateHouseMsgListByHouse(houseRequest);
			}else if(houseRequest.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_ROOM){
				pagingResult = troyHouseMgtServiceImpl.findHouseMsgListByRoom(houseRequest);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_ERROR));
				LogUtil.error(LOGGER, "返回结果:{},参数:{}", dto.toJsonString(), paramJson);
				return dto.toJsonString();
			}
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", pagingResult.getRows());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	@Override
	public String searchHouseMsgListNew(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		HouseRequestDto houseRequest = JsonEntityTransform.json2Object(paramJson, HouseRequestDto.class);
		DataTransferObject dto = new DataTransferObject();

		// 校验查询参数是否为空
		if (Check.NuNObj(houseRequest)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房屋出租类型是否为空
		if (Check.NuNObj(houseRequest.getRentWay())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			PagingResult<HouseResultNewVo> pagingResult = new PagingResult<HouseResultNewVo>(); 
			if(houseRequest.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_HOUSE){
				pagingResult = troyHouseMgtServiceImpl.findHouseMsgListByHouseNew(houseRequest);
			}else if(houseRequest.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_ROOM){
				pagingResult = troyHouseMgtServiceImpl.findHouseMsgListByRoomNew(houseRequest);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_ERROR));
				LogUtil.error(LOGGER, "返回结果:{},参数:{}", dto.toJsonString(), paramJson);
				return dto.toJsonString();
			}
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", pagingResult.getRows());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	

	@Override
	public String searchHouseDetailByFid(String houseBaseFid) {
		LogUtil.debug(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseMsgVo houseMsg = troyHouseMgtServiceImpl.findHouseDetailByFid(houseBaseFid);
			dto.putValue("obj", houseMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchRoomDetailByFid(String houseRoomFid) {
		LogUtil.debug(LOGGER, "参数:houseRoomFid={}", houseRoomFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseRoomFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseMsgVo houseMsg = troyHouseMgtServiceImpl.findRoomDetailByFid(houseRoomFid);
			dto.putValue("obj", houseMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String forceDownHouse(String houseBaseFid, String operaterFid, String remark) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={},operaterFid={},remark={}", houseBaseFid, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		//判断房源逻辑id是否为空
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断操作人id是否为空
		if (Check.NuNStr(operaterFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
		if (Check.NuNObj(houseBaseMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源状态是否处于上架状态
		if (HouseStatusEnum.SJ.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("operaterFid", operaterFid);
			paramMap.put("remark", remark);
			int upNum = troyHouseMgtServiceImpl.forceDownHouse(houseBaseMsgEntity, paramMap);
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "强制下架房源成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.QZXJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "强制下架房源成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String forceDownRoom(String houseRoomFid, String operaterFid, String remark) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={},operaterFid={},remark={}", houseRoomFid, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		//判断房间逻辑id是否为空
		if (Check.NuNStr(houseRoomFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断操作人id是否为空
		if (Check.NuNStr(operaterFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(houseRoomFid);
		if (Check.NuNObj(houseRoomMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间状态是否处于上架状态
		if (HouseStatusEnum.SJ.getCode() != houseRoomMsgEntity.getRoomStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("operaterFid", operaterFid);
			paramMap.put("remark", remark);
			int upNum = troyHouseMgtServiceImpl.forceDownRoom(houseRoomMsgEntity, paramMap);
			dto.putValue("upNum", upNum);

			// 统计强制下架房源数量
			//			Cat.logMetricForCount(CatConstant.House.FORCE_DOWN_HOUSE_COUNT);

			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){				
				LogUtil.info(LOGGER, "强制下架房间成功,推送队列消息start...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.QZXJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "强制下架房间成功,推送队列消息end,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String approveHouseInfo(String houseBaseFid, String operaterFid, String remark, String addtionalInfo) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={},operaterFid={},remark={}", houseBaseFid, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		// 判断房源逻辑id是否为空
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(operaterFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
		if (Check.NuNObj(houseBaseMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		// 判断房源状态是否处于已发布或信息审核未通过状态
		if (HouseStatusEnum.YFB.getCode() != houseBaseMsgEntity.getHouseStatus() 
				&& HouseStatusEnum.XXSHWTG.getCode() != houseBaseMsgEntity.getHouseStatus()
				&&HouseStatusEnum.ZPSHWTG.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.info(LOGGER, "房源信息:{}", JsonEntityTransform.Object2Json(houseBaseMsgEntity));
			return dto.toJsonString();
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("operaterFid", operaterFid);
			paramMap.put("remark", remark);
			paramMap.put("addtionalInfo", addtionalInfo);
			int upNum = troyHouseMgtServiceImpl.approveHouseInfo(houseBaseMsgEntity, paramMap);
			dto.putValue("upNum", upNum);


			Transaction troyHouseTran = Cat.newTransaction("TroyHouseMgtServiceProxy", CatConstant.House.APPROVE_HOUSE_INFO_COUNT);
			try {
				// 统计房源信息审核通过数量
				Cat.logMetricForCount("整租审核通过的房源数量");
				troyHouseTran.setStatus(Message.SUCCESS);
			} catch(Exception ex) {
				Cat.logError("整租审核通过的房源数量 打点异常", ex);
				troyHouseTran.setStatus(ex);
			} finally {
				troyHouseTran.complete();
			}

			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "房源信息审核通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.XXSHTG.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源信息审核通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String unApproveHouseInfo(String houseBaseFid, String operaterFid, String remark, String addtionalInfo) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={},operaterFid={},remark={}", houseBaseFid, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		// 判断房源逻辑id是否为空
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(operaterFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
		if (Check.NuNObj(houseBaseMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源状态是否处于已发布或信息审核未通过状态
		if (HouseStatusEnum.YFB.getCode() != houseBaseMsgEntity.getHouseStatus()
				&& HouseStatusEnum.XXSHWTG.getCode() != houseBaseMsgEntity.getHouseStatus()
				&&HouseStatusEnum.ZPSHWTG.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房源信息:{}", JsonEntityTransform.Object2Json(houseBaseMsgEntity));
			return dto.toJsonString();
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("operaterFid", operaterFid);
			paramMap.put("remark", remark);
			paramMap.put("addtionalInfo", addtionalInfo);
			int upNum = troyHouseMgtServiceImpl.unApproveHouseInfo(houseBaseMsgEntity, paramMap);
			dto.putValue("upNum", upNum);


			Transaction troyHouseTran2 = Cat.newTransaction("TroyHouseMgtServiceProxy", CatConstant.House.UNAPPROVE_HOUSE_INFO_COUNT);
			try {
				// 统计房源信息审核未通过数量
				Cat.logMetricForCount("整租房源审核未通过的数量");
				troyHouseTran2.setStatus(Message.SUCCESS);
			} catch(Exception ex) {
				Cat.logError("整租房源审核未通过的数量 打点异常", ex);
				troyHouseTran2.setStatus(ex);
			} finally {
				troyHouseTran2.complete();
			}

			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "房源信息审核未通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.XXSHWTG.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源信息审核未通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String approveRoomInfo(String houseRoomFid, String operaterFid, String remark, String addtionalInfo) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={},operaterFid={},remark={}", houseRoomFid, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		// 判断房间逻辑id是否为空
		if (Check.NuNStr(houseRoomFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(operaterFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(houseRoomFid);
		if (Check.NuNObj(houseRoomMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间状态是否处于已发布或信息审核未通过状态
		if (HouseStatusEnum.YFB.getCode() != houseRoomMsgEntity.getRoomStatus()
				&& HouseStatusEnum.XXSHWTG.getCode() != houseRoomMsgEntity.getRoomStatus()
				&&HouseStatusEnum.ZPSHWTG.getCode() != houseRoomMsgEntity.getRoomStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房间信息:{}", JsonEntityTransform.Object2Json(houseRoomMsgEntity));
			return dto.toJsonString();
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("operaterFid", operaterFid);
			paramMap.put("remark", remark);
			paramMap.put("addtionalInfo", addtionalInfo);
			int upNum = troyHouseMgtServiceImpl.approveRoomInfo(houseRoomMsgEntity, paramMap);
			dto.putValue("upNum", upNum);


			Transaction troyHouseTran3 = Cat.newTransaction("TroyHouseMgtServiceProxy", CatConstant.House.APPROVE_HOUSE_INFO_COUNT);
			try {
				// 统计房源信息审核通过数量
				Cat.logMetricForCount("分租审核通过的房源数量");
				troyHouseTran3.setStatus(Message.SUCCESS);
			} catch(Exception ex) {
				Cat.logError("分租审核通过的房源数量 打点异常", ex);
				troyHouseTran3.setStatus(ex);
			} finally {
				troyHouseTran3.complete();
			}

			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){
				LogUtil.info(LOGGER, "房间信息审核通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.XXSHTG.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房间信息审核通过,推送队列消息结束,推送内容:{}", pushContent);				
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String unApproveRoomInfo(String houseRoomFid, String operaterFid, String remark, String addtionalInfo) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={},operaterFid={},remark={}", houseRoomFid, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		// 判断房间逻辑id是否为空
		if (Check.NuNStr(houseRoomFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(operaterFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(houseRoomFid);
		if (Check.NuNObj(houseRoomMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间状态是否处于已发布或信息审核未通过状态
		if (HouseStatusEnum.YFB.getCode() != houseRoomMsgEntity.getRoomStatus()
				&& HouseStatusEnum.XXSHWTG.getCode() != houseRoomMsgEntity.getRoomStatus()
				&&HouseStatusEnum.ZPSHWTG.getCode() != houseRoomMsgEntity.getRoomStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房间信息:{}", JsonEntityTransform.Object2Json(houseRoomMsgEntity));
			return dto.toJsonString();
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("operaterFid", operaterFid);
			paramMap.put("remark", remark);
			paramMap.put("addtionalInfo", addtionalInfo);
			int upNum = troyHouseMgtServiceImpl.unApproveRoomInfo(houseRoomMsgEntity, paramMap);
			dto.putValue("upNum", upNum);



			Transaction troyHouseTran4 = Cat.newTransaction("TroyHouseMgtServiceProxy", CatConstant.House.UNAPPROVE_HOUSE_INFO_COUNT);
			try {
				// 统计房源信息审核未通过数量
				Cat.logMetricForCount("分租审核未通过的房源数量");
				troyHouseTran4.setStatus(Message.SUCCESS);
			} catch(Exception ex) {
				Cat.logError("分租审核未通过的房源数量 打点异常", ex);
				troyHouseTran4.setStatus(ex);
			} finally {
				troyHouseTran4.complete();
			}

			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){				
				LogUtil.info(LOGGER, "房间信息审核未通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.XXSHWTG.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房间信息审核未通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String approveHousePic(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);

		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseQualityAuditDto> validateResult = paramCheckLogic
				.checkParamValidate(paramJson, HouseQualityAuditDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseQualityAuditDto auditDto = validateResult.getResultObj();

		// 判断房源逻辑id是否为空
		if (Check.NuNStr(auditDto.getHouseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(auditDto.getOperaterFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 现在审核通过不需要选择原因  modified by liujun 2017-02-23
		/*// 判断审核说明是否为空 
		if (Check.NuNObj(auditDto.getAuditCause())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg("审核说明不能为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}*/

		// 判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(auditDto.getHouseFid());
		if (Check.NuNObj(houseBaseMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断房源默认图片是否存在
		HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(auditDto.getHouseFid());
		if(Check.NuNStr(houseBaseExtDto.getHouseBaseExt().getDefaultPicFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DEFAULT_PICFID_NULL));
			return dto.toJsonString();
		} else {
			HousePicMsgEntity housePicMsgEntity=houseIssueServiceImpl.findHousePicByFid(houseBaseExtDto.getHouseBaseExt().getDefaultPicFid());
			if(housePicMsgEntity.getIsDel()==1){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DEFAULT_PICFID_NULL));
				return dto.toJsonString();
			}
		}

		// 判断房源状态是否处于已发布状态 
		if (HouseStatusEnum.YFB.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房源信息:{}", JsonEntityTransform.Object2Json(houseBaseMsgEntity));
			return dto.toJsonString();
		}
		try {
			int upNum = troyHouseMgtServiceImpl.approveHousePic(houseBaseMsgEntity, auditDto.toMap());
			dto.putValue("upNum", upNum);
			
			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "房源照片审核通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.SJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源照片审核通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String unApproveHousePic(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);

		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseQualityAuditDto> validateResult = paramCheckLogic
				.checkParamValidate(paramJson, HouseQualityAuditDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseQualityAuditDto auditDto = validateResult.getResultObj();

		// 判断房源逻辑id是否为空
		if (Check.NuNStr(auditDto.getHouseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(auditDto.getOperaterFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断审核说明是否为空
		if (Check.NuNObj(auditDto.getAuditCause())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg("审核说明不能为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(auditDto.getHouseFid());
		if (Check.NuNObj(houseBaseMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源状态是否处于已发布状态
		if (HouseStatusEnum.YFB.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房源信息:{}", JsonEntityTransform.Object2Json(houseBaseMsgEntity));
			return dto.toJsonString();
		}
		try {
			int upNum = troyHouseMgtServiceImpl.unApproveHousePic(houseBaseMsgEntity, auditDto.toMap());
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "房源照片审核未通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.ZPSHWTG.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源照片审核未通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String approveRoomPic(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);

		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseQualityAuditDto> validateResult = paramCheckLogic
				.checkParamValidate(paramJson, HouseQualityAuditDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseQualityAuditDto auditDto = validateResult.getResultObj();

		// 判断房间逻辑id是否为空
		if (Check.NuNStr(auditDto.getHouseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(auditDto.getOperaterFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 现在审核通过不需要选择原因  modified by liujun 2017-02-23
		/*// 判断审核说明是否为空
		if (Check.NuNObj(auditDto.getAuditCause())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg("审核说明不能为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}*/

		// 判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(auditDto.getHouseFid());
		if (Check.NuNObj(houseRoomMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断房间默认图片是否存在
		if(Check.NuNStr(houseRoomMsgEntity.getDefaultPicFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DEFAULT_PICFID_NULL));
			return dto.toJsonString();
		} else {
			HousePicMsgEntity housePicMsgEntity=houseIssueServiceImpl.findHousePicByFid(houseRoomMsgEntity.getDefaultPicFid());
			if(housePicMsgEntity.getIsDel()==1){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DEFAULT_PICFID_NULL));
				return dto.toJsonString();
			}
		}

		// 判断房间状态是否处于已发布状态
		if (HouseStatusEnum.YFB.getCode() != houseRoomMsgEntity.getRoomStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房间信息:{}", JsonEntityTransform.Object2Json(houseRoomMsgEntity));
			return dto.toJsonString();
		}
		try {
			int upNum = troyHouseMgtServiceImpl.approveRoomPic(houseRoomMsgEntity, auditDto.toMap());
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){
				LogUtil.info(LOGGER, "房间照片审核通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.SJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房间照片审核通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String unApproveRoomPic(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);

		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseQualityAuditDto> validateResult = paramCheckLogic
				.checkParamValidate(paramJson, HouseQualityAuditDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseQualityAuditDto auditDto = validateResult.getResultObj();

		// 判断房间逻辑id是否为空
		if (Check.NuNStr(auditDto.getHouseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(auditDto.getOperaterFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断审核说明是否为空
		if (Check.NuNObj(auditDto.getAuditCause())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg("审核说明不能为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(auditDto.getHouseFid());
		if (Check.NuNObj(houseRoomMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间状态是否处于已发布状态
		if (HouseStatusEnum.YFB.getCode() != houseRoomMsgEntity.getRoomStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房间信息:{}", JsonEntityTransform.Object2Json(houseRoomMsgEntity));
			return dto.toJsonString();
		}
		try {
			int upNum = troyHouseMgtServiceImpl.unApproveRoomPic(houseRoomMsgEntity, auditDto.toMap());
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){				
				LogUtil.info(LOGGER, "房间照片审核未通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.ZPSHWTG.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房间照片审核未通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHouseOperateLogList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseOpLogDto> validateResult = paramCheckLogic.checkParamValidate(paramJson, HouseOpLogDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseOpLogDto houseOpLogDto = validateResult.getResultObj();

		try {
			PagingResult<HouseOperateLogEntity> operateLogList = new PagingResult<HouseOperateLogEntity>();
			if(houseOpLogDto.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()){
				operateLogList = troyHouseMgtServiceImpl.findHouseOperateLogList(houseOpLogDto);
			}else if(houseOpLogDto.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
				operateLogList = troyHouseMgtServiceImpl.findRoomOperateLogList(houseOpLogDto);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_ERROR));
				LogUtil.error(LOGGER, "返回结果:{},参数:{}", dto.toJsonString(), paramJson);
				return dto.toJsonString();
			}
			dto.putValue("list", operateLogList.getRows());
			dto.putValue("total", operateLogList.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHousePhyMsgList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HousePhyListDto housePhyListDto=JsonEntityTransform.json2Object(paramJson, HousePhyListDto.class);
			PagingResult<HousePhyMsgEntity> phyList=troyHouseMgtServiceImpl.findHousePhyMsgListByCondition(housePhyListDto);
			dto.putValue("list", phyList.getRows());
			dto.putValue("count", phyList.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHouseBasePhyFid(String newPhyFid, String oldPhyFid) {
		LogUtil.info(LOGGER, "参数:newPhyFid={},oldPhyFid={}", newPhyFid, oldPhyFid);
		DataTransferObject dto = new DataTransferObject();
		try {
			troyHouseMgtServiceImpl.updateHouseBasePhyFid(newPhyFid, oldPhyFid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHousePhyMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HousePhyMsgEntity housePhyMsgEntity=JsonEntityTransform.json2Object(paramJson, HousePhyMsgEntity.class);
			int upNum=troyHouseMgtServiceImpl.updateHousePhyMsg(housePhyMsgEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	@Deprecated
	public String searchHouseConfListByFidAndCode(String houseBaseFid, String enumCode) {
		LogUtil.debug(LOGGER, "参数:houseBaseFid={},enumCode={}", houseBaseFid, enumCode);
		DataTransferObject dto = new DataTransferObject();

		// 判断房源逻辑id是否为空
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断枚举值是否为空
		if (Check.NuNStr(enumCode)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ENUM_CODE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			List<String> list = troyHouseMgtServiceImpl.findHouseConfListByFidAndCode(houseBaseFid, enumCode);
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String housePicAuditList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			//独立房间默认图片fid
			String defaultPicFid=null;
			HousePicDto housePicDto=JsonEntityTransform.json2Object(paramJson, HousePicDto.class);
			if(Check.NuNStr(housePicDto.getHouseBaseFid())){
				HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(housePicDto.getHouseRoomFid());
				housePicDto.setHouseBaseFid(houseRoomMsgEntity.getHouseBaseFid());
				defaultPicFid=houseRoomMsgEntity.getDefaultPicFid();
			}
			HousePicAuditVo housePicAuditVo=troyHouseMgtServiceImpl.findHousePicAuditVoByHouseBaseFid(housePicDto.getHouseBaseFid(), housePicDto.getHouseRoomFid());
			//判断如果房源为合租设置房间默认图片
			if(RentWayEnum.ROOM.getCode()==housePicAuditVo.getRentWay()){
				housePicAuditVo.setDefaultPicFid(defaultPicFid);
			}
			dto.putValue("housePicAuditVo", housePicAuditVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
	 * 房源图片审核列表(troy专用！！！！！)
	 */
	@Override
	public String housePicAuditListForTroy(String paramJson){
		LogUtil.debug(LOGGER, "[housePicAuditListForTroy]参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			//独立房间默认图片fid
			String defaultPicFid=null;
			HousePicDto housePicDto=JsonEntityTransform.json2Object(paramJson, HousePicDto.class);
			if(Check.NuNStr(housePicDto.getHouseBaseFid())){
				HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(housePicDto.getHouseRoomFid());
				housePicDto.setHouseBaseFid(houseRoomMsgEntity.getHouseBaseFid());
				defaultPicFid=houseRoomMsgEntity.getDefaultPicFid();
			}
			HousePicAuditVo housePicAuditVo=troyHouseMgtServiceImpl.findHousePicAuditVoForTroy(housePicDto.getHouseBaseFid(), housePicDto.getHouseRoomFid());
			//判断如果房源为合租设置房间默认图片
			if(RentWayEnum.ROOM.getCode()==housePicAuditVo.getRentWay()){
				housePicAuditVo.setDefaultPicFid(defaultPicFid);
			}
			dto.putValue("housePicAuditVo", housePicAuditVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "[housePicAuditListForTroy]error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "[housePicAuditListForTroy]结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	@Override
	public String searchPicUnapproveedHouseList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		HouseRequestDto houseRequest = JsonEntityTransform.json2Object(paramJson, HouseRequestDto.class);
		DataTransferObject dto = new DataTransferObject();

		// 校验查询参数是否为空
		if (Check.NuNObj(houseRequest)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房屋出租类型是否为空
		if (Check.NuNObj(houseRequest.getRentWay())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			PagingResult<HouseResultVo> pagingResult = new PagingResult<HouseResultVo>(); 
			long t1 = System.currentTimeMillis();// 开始时间
			if(houseRequest.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_HOUSE){
				pagingResult = troyHouseMgtServiceImpl.findPicUnapproveedHouseList(houseRequest);
			}else if(houseRequest.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_ROOM){
				pagingResult = troyHouseMgtServiceImpl.findPicUnapproveedRoomList(houseRequest);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_ERROR));
				LogUtil.error(LOGGER, "返回结果:{},参数:{}", dto.toJsonString(), paramJson);
				return dto.toJsonString();
			}
			long t2 = System.currentTimeMillis();// 结束时间
			LogUtil.info(LOGGER, "searchPicUnapproveedHouseList耗时{}ms", t2-t1);
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", pagingResult.getRows());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String approveModifiedPic(String houseFid, String rentWay, String operaterFid, String remark) {
		LogUtil.info(LOGGER, "参数:houseFid={},rentWay={},operaterFid={},remark={}",
				houseFid, rentWay, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		// 校验房源逻辑id是否为空
		if (Check.NuNObj(houseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		// 校验房屋出租类型是否为空
		if (Check.NuNStr(rentWay)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			int upNum = 0; 
			
			if(Integer.valueOf(rentWay) == HouseConstant.HOUSE_RENTWAY_HOUSE){
				upNum = troyHouseMgtServiceImpl.approveHouseModifiedPic(houseFid, operaterFid, remark);				
			}else if(Integer.valueOf(rentWay) == HouseConstant.HOUSE_RENTWAY_ROOM){
				upNum = troyHouseMgtServiceImpl.approveRoomModifiedPic(houseFid, operaterFid, remark); 
			}
			dto.putValue("upNum", upNum);
			
			HouseMq houseMq = new HouseMq(houseFid,
					null,Integer.valueOf(rentWay), HouseStatusEnum.SJ.getCode(),
					HouseStatusEnum.SJ.getCode());

			if(!Check.NuNStr(houseMq.getHouseBaseFid())){				
				LogUtil.info(LOGGER, "强制下架房源成功,推送队列消息开始...");
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, JsonEntityTransform.Object2Json(houseMq));
				LogUtil.info(LOGGER, "强制下架房源成功,推送队列消息结束,推送内容:{}", JsonEntityTransform.Object2Json(houseMq));
				// 统计房源修改照片审核通过数量
			}
			
			//			Cat.logMetricForSum(CatConstant.House.APPROVE_MODIFIED_PIC_SUM, upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String findHousePicCountByType(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			HousePicDto housePicDto=JsonEntityTransform.json2Object(paramJson, HousePicDto.class);
			int count=houseIssueServiceImpl.getHousePicCount(housePicDto.getHouseBaseFid(), housePicDto.getHouseRoomFid(),housePicDto.getPicType());
			dto.putValue("count", count);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String batchEditHouseWeight(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			ValidateResult<HouseWeightDto> validateResult = paramCheckLogic.checkParamValidate(paramJson, HouseWeightDto.class);
			if(!validateResult.isSuccess()){
				LogUtil.error(LOGGER, validateResult.getDto().getMsg());
				return validateResult.getDto().toJsonString();
			}
			HouseWeightDto houseWeightDto = validateResult.getResultObj();
			List<String> houseFidList = houseWeightDto.getHouseFidList();

			int upNum = 0;
			if(houseWeightDto.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_HOUSE){
				for (String houseBaseFid : houseFidList) {
					HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
					if(Check.NuNObj(houseBaseMsg)){
						continue;
					}
					houseBaseMsg.setHouseWeight(houseWeightDto.getWeight());
					houseBaseMsg.setLastModifyDate(new Date());
					int line = troyHouseMgtServiceImpl.updateHouseBaseMsg(houseBaseMsg);
					upNum += line;
				}
			}else if(houseWeightDto.getRentWay().intValue() == HouseConstant.HOUSE_RENTWAY_ROOM){
				for (String houseRoomFid : houseFidList) {
					HouseRoomMsgEntity houseRoomMsg = houseManageServiceImpl.getHouseRoomByFid(houseRoomFid);
					if(Check.NuNObj(houseRoomMsg)){
						continue;
					}
					houseRoomMsg.setRoomWeight(houseWeightDto.getWeight());
					houseRoomMsg.setLastModifyDate(new Date());
					int line = troyHouseMgtServiceImpl.updateHouseRoomMsg(houseRoomMsg);
					upNum += line;
				}
			}
			dto.putValue("upNum", upNum);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String houseInput(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			long startTime = System.currentTimeMillis();
			HouseInputDto houseInputDto=JsonEntityTransform.json2Object(paramJson, HouseInputDto.class);
			String mapType = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
			houseInputDto.setMapType(mapType);
			String houseBaseFid=troyHouseMgtServiceImpl.houseInput(houseInputDto);
			dto.putValue("houseBaseFid", houseBaseFid);
			long endTime = System.currentTimeMillis();
			LogUtil.info(LOGGER, "tory录入房源时间---houseInput->time:{}", endTime - startTime);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHouseBaseMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseMsgEntity houseBaseMsgEntity=JsonEntityTransform.json2Object(paramJson, HouseBaseMsgEntity.class);
			// 判断房源逻辑id是否为空
			if (Check.NuNStr(houseBaseMsgEntity.getFid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			int upNum=houseManageServiceImpl.updateHouseBaseMsg(houseBaseMsgEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String reIssueHouse(String houseBaseFid, String operaterFid, String remark) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={},operaterFid={},remark={}", houseBaseFid, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		//判断房源逻辑id是否为空
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断操作人id是否为空
		if (Check.NuNStr(operaterFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
		if (Check.NuNObj(houseBaseMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源状态是否处于下架或强制下架状态
		if (HouseStatusEnum.XJ.getCode() != houseBaseMsgEntity.getHouseStatus()
				&& HouseStatusEnum.QZXJ.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("operaterFid", operaterFid);
			paramMap.put("remark", remark);
			int upNum = troyHouseMgtServiceImpl.reIssueHouse(houseBaseMsgEntity, paramMap);
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "重新发布房源成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.YFB.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "重新发布房源成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String reIssueRoom(String houseRoomFid, String operaterFid, String remark) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={},operaterFid={},remark={}", houseRoomFid, operaterFid, remark);
		DataTransferObject dto = new DataTransferObject();

		//判断房间逻辑id是否为空
		if (Check.NuNStr(houseRoomFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断操作人id是否为空
		if (Check.NuNStr(operaterFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(houseRoomFid);
		if (Check.NuNObj(houseRoomMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间状态是否处于下架或强制下架状态
		if (HouseStatusEnum.XJ.getCode() != houseRoomMsgEntity.getRoomStatus()
				&& HouseStatusEnum.QZXJ.getCode() != houseRoomMsgEntity.getRoomStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("operaterFid", operaterFid);
			paramMap.put("remark", remark);
			int upNum = troyHouseMgtServiceImpl.reIssueRoom(houseRoomMsgEntity, paramMap);
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){
				LogUtil.info(LOGGER, "重新发布房间成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.YFB.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "重新发布房间成功,推送队列消息结束,推送内容:{}", pushContent);				
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService#upHouseMsg(java.lang.String)
	 */
	@Override
	public String upHouseMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseInputDto houseInputDto= JsonEntityTransform.json2Object(paramJson, HouseInputDto.class);
			troyHouseMgtServiceImpl.upHouseMsg(houseInputDto);
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService#findNoAuditHousePicList(java.lang.String)
	 */
	@Override
	public String findNoAuditHousePicList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseDto houseBaseDto=JsonEntityTransform.json2Object(paramJson, HouseBaseDto.class);
			if(RentWayEnum.HOUSE.getCode()==houseBaseDto.getRentWay().intValue()){
				dto.putValue("picList",troyHouseMgtServiceImpl.findNoAuditHousePicList(null, houseBaseDto.getHouseFid()));
			} else if (RentWayEnum.ROOM.getCode()==houseBaseDto.getRentWay().intValue()){
				HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(houseBaseDto.getHouseFid());
				dto.putValue("picList", troyHouseMgtServiceImpl.findNoAuditHousePicList(houseRoomMsgEntity.getFid(), houseRoomMsgEntity.getHouseBaseFid()));
			}
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService#delNoAuditHousePic(java.lang.String, java.lang.String)
	 */
	@Override
	public String delNoAuditHousePic(String fid) {
		LogUtil.info(LOGGER, "参数:{}",fid);
		DataTransferObject dto = new DataTransferObject();
		try {
			houseIssueServiceImpl.deleteHousePicMsgByFid(fid);
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService#auditHousePic(java.lang.String)
	 */
	@Override
	public String auditHousePic(String paramJson) {

		DataTransferObject dto = new DataTransferObject();
		try {
			List<String> picFids= JsonEntityTransform.json2ObjectList(paramJson, String.class);
			if(!Check.NuNCollection(picFids)){
				LogUtil.info(LOGGER, "审核照片参数:picFids={}",picFids);
				for (String fid : picFids) {
					HousePicMsgEntity housePicMsgEntity=new HousePicMsgEntity();
					housePicMsgEntity.setFid(fid);
					housePicMsgEntity.setAuditStatus(1);
					troyHouseMgtServiceImpl.updateHousePicMsg(housePicMsgEntity);
				}
				HousePicMsgEntity housePicMsgEntity=houseIssueServiceImpl.findHousePicByFid(picFids.get(0));
				
				if(!Check.NuNObj(housePicMsgEntity) && !Check.NuNStr(housePicMsgEntity.getHouseBaseFid())){
					LogUtil.info(LOGGER, "照片审核,推送队列消息开始...");					
					String pushContent = JsonEntityTransform.Object2Json(new HouseMq(housePicMsgEntity.getHouseBaseFid(),
							housePicMsgEntity.getRoomFid(), RentWayEnum.ROOM.getCode(), null, null));
					// 推送队列消息
					rabbitMqSendClient.sendQueue(queueName, pushContent);
					LogUtil.info(LOGGER, "照片审核,推送队列消息结束,推送内容:{}", pushContent);
				}

			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}	

	/**
	 * 
	 * 条件查询
	 * 空条件 不让查询
	 *
	 * @author yd
	 * @created 2016年7月5日 下午4:37:52
	 *
	 * @return
	 */
	@Override
	public String findHouseListByPhy(String guardAreaLogRe){

		DataTransferObject dto = new DataTransferObject();
		HousePhyListDto request = JsonEntityTransform.json2Object(guardAreaLogRe, HousePhyListDto.class);

		if(Check.NuNObj(guardAreaLogRe)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}

		List<HouseBaseMsgEntity> listAreaEntities = this.houseManageServiceImpl.findHouseByPhy(request);
		dto.putValue("list",listAreaEntities);
		return dto.toJsonString();
	}


	/**
	 * 根据房源fidList批量查询房源信息
	 * @author lishaochuan
	 * @create 2016年8月4日下午5:40:52
	 * @param paramJson
	 * @return
	 */
	@Override
	public String getHouseCityVoByFids(String paramJson) {
		LogUtil.debug(LOGGER, "参数:paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			List<String> houseFidList= JsonEntityTransform.json2ObjectList(paramJson, String.class);
			List<HouseCityVo> houseCityVoList = troyHouseMgtServiceImpl.getHouseCityVoByFids(houseFidList);
			dto.putValue("houseCityVoList", houseCityVoList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String approveHouseQuality(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);

		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseQualityAuditDto> validateResult = paramCheckLogic
				.checkParamValidate(paramJson, HouseQualityAuditDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseQualityAuditDto auditDto = validateResult.getResultObj();

		// 判断房源逻辑id是否为空
		if (Check.NuNStr(auditDto.getHouseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(auditDto.getOperaterFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断审核说明是否为空
		if (Check.NuNObj(auditDto.getCause())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg("审核说明不能为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(auditDto.getHouseFid());
		if (Check.NuNObj(houseBaseMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		//判断房源默认图片是否存在
		HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(auditDto.getHouseFid());
		if(Check.NuNStr(houseBaseExtDto.getHouseBaseExt().getDefaultPicFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DEFAULT_PICFID_NULL));
			return dto.toJsonString();
		} else {
			HousePicMsgEntity housePicMsgEntity=houseIssueServiceImpl.findHousePicByFid(houseBaseExtDto.getHouseBaseExt().getDefaultPicFid());
			if(housePicMsgEntity.getIsDel()==1){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DEFAULT_PICFID_NULL));
				return dto.toJsonString();
			}
		}

		// 判断房源状态是否处于信息审核通过状态
		if (HouseStatusEnum.XXSHTG.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房源信息:{}", JsonEntityTransform.Object2Json(houseBaseMsgEntity));
			return dto.toJsonString();
		}
		try {
			int upNum = troyHouseMgtServiceImpl.approveHousePic(houseBaseMsgEntity, auditDto.toMap());
			dto.putValue("upNum", upNum);
			
			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "房源照片审核通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.SJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源照片审核通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String approveRoomQuality(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);

		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseQualityAuditDto> validateResult = paramCheckLogic
				.checkParamValidate(paramJson, HouseQualityAuditDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseQualityAuditDto auditDto = validateResult.getResultObj();

		// 判断房间逻辑id是否为空
		if (Check.NuNStr(auditDto.getHouseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(auditDto.getOperaterFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断审核说明是否为空
		if (Check.NuNObj(auditDto.getCause())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg("审核说明不能为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(auditDto.getHouseFid());
		if (Check.NuNObj(houseRoomMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断房间默认图片是否存在
		if(Check.NuNStr(houseRoomMsgEntity.getDefaultPicFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DEFAULT_PICFID_NULL));
			return dto.toJsonString();
		} else {
			HousePicMsgEntity housePicMsgEntity=houseIssueServiceImpl.findHousePicByFid(houseRoomMsgEntity.getDefaultPicFid());
			if(housePicMsgEntity.getIsDel()==1){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DEFAULT_PICFID_NULL));
				return dto.toJsonString();
			}
		}

		// 判断房间状态是否处于信息审核通过状态
		if (HouseStatusEnum.XXSHTG.getCode() != houseRoomMsgEntity.getRoomStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房间信息:{}", JsonEntityTransform.Object2Json(houseRoomMsgEntity));
			return dto.toJsonString();
		}
		try {
			int upNum = troyHouseMgtServiceImpl.approveRoomPic(houseRoomMsgEntity, auditDto.toMap());
			dto.putValue("upNum", upNum);
			
			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){				
				LogUtil.info(LOGGER, "房间照片审核通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.SJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房间照片审核通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String unApproveHouseQuality(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);

		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseQualityAuditDto> validateResult = paramCheckLogic
				.checkParamValidate(paramJson, HouseQualityAuditDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseQualityAuditDto auditDto = validateResult.getResultObj();

		// 判断房源逻辑id是否为空
		if (Check.NuNStr(auditDto.getHouseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(auditDto.getOperaterFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断审核说明是否为空
		if (Check.NuNObj(auditDto.getCause())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg("审核说明不能为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(auditDto.getHouseFid());
		if (Check.NuNObj(houseBaseMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房源状态是否处于信息审核通过状态
		if (HouseStatusEnum.XXSHTG.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房源信息:{}", JsonEntityTransform.Object2Json(houseBaseMsgEntity));
			return dto.toJsonString();
		}
		try {
			int upNum = troyHouseMgtServiceImpl.unApproveHousePic(houseBaseMsgEntity, auditDto.toMap());
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "房源照片审核未通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.ZPSHWTG.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源照片审核未通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String unApproveRoomQuality(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);

		DataTransferObject dto = new DataTransferObject();

		ValidateResult<HouseQualityAuditDto> validateResult = paramCheckLogic
				.checkParamValidate(paramJson, HouseQualityAuditDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseQualityAuditDto auditDto = validateResult.getResultObj();

		// 判断房间逻辑id是否为空
		if (Check.NuNStr(auditDto.getHouseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断操作人id是否为空
		if (Check.NuNStr(auditDto.getOperaterFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATER_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断审核说明是否为空
		if (Check.NuNObj(auditDto.getCause())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg("审核说明不能为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(auditDto.getHouseFid());
		if (Check.NuNObj(houseRoomMsgEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 判断房间状态是否处于信息审核通过状态
		if (HouseStatusEnum.XXSHTG.getCode() != houseRoomMsgEntity.getRoomStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, "房间信息:{}", JsonEntityTransform.Object2Json(houseRoomMsgEntity));
			return dto.toJsonString();
		}
		try {
			int upNum = troyHouseMgtServiceImpl.unApproveRoomPic(houseRoomMsgEntity, auditDto.toMap());
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){				
				LogUtil.info(LOGGER, "房间照片审核未通过,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.ZPSHWTG.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房间照片审核未通过,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService#updateHouseExtByHouseFid(java.lang.String)
	 */
	@Override
	public String updateHouseBaseAndExt(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseExtDto houseBaseExtDto = JsonEntityTransform.json2Object(paramJson, HouseBaseExtDto.class);
			houseIssueServiceImpl.updateHouseBaseAndExt(houseBaseExtDto);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setMsg("系统错误");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 *  根据权限类型 查询房源fid集合
	 *  说明：如果返回集合 为null 说明当前用户 无任何房源的权限
	 *  
	 *  1. 有区域权限2，但无区域，则无权限
	 *
	 * @author yd
	 * @created 2016年10月31日 上午11:52:58
	 *
	 * @param authMenuJson
	 * @return
	 */
	@Override
	public String findHouseFidByAuth(String authMenuJson){

		LogUtil.info(LOGGER, "参数:authMenuJson={}", authMenuJson);
		DataTransferObject dto = new DataTransferObject();
		
		AuthMenuEntity authMenu = JsonEntityTransform.json2Object(authMenuJson, AuthMenuEntity.class);
		if(Check.NuNObj(authMenu)||Check.NuNObj(authMenu.getRoleType())
				||authMenu.getRoleType() == 0){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			
			return dto.toJsonString();
		}
		
		List<String> houseFids = new ArrayList<String>();
		if(authMenu.getRoleType() == 2&&Check.NuNCollection(authMenu.getUserCityList())){
			LogUtil.info(LOGGER, "当前用户是区域权限，但无任何区域，故返回集合为null");
			dto.putValue("houseFids", houseFids);
			return dto.toJsonString();
		}
		
		if(authMenu.getRoleType() == 3 &&Check.NuNStr(authMenu.getEmpCode())){
			
			LogUtil.error(LOGGER, "当前用户角色roleType={}，管家编号参数错误", authMenu.getRoleType());
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("管家编号错误");
			return dto.toJsonString();
		}
		
		if(authMenu.getRoleType() == 1 &&(Check.NuNCollection(authMenu.getUserCityList()) 
				||Check.NuNStr(authMenu.getEmpCode()))){
			LogUtil.error(LOGGER, "当前用户角色roleType={}，管家编号参数错误或者用户区域错误", authMenu.getRoleType());
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("管家编号或者管家区域错误");
		}
		
		houseFids = this.troyHouseMgtServiceImpl.findHouseFidByAuth(authMenu);
		dto.putValue("houseFids", houseFids);
		return dto.toJsonString();

	}
	/**
	 * 
	 *根据 房源fid  以及新房东uid  切换房东
	 *
	 *说明：
	 * 1.校验 房源是否存在  用户uid是否存在(用户当前是否是房东 请在controler 层校验)
	 * 2. 特殊订单状态  ： 待入住20 改 需要转账操作    其他订单不用处理
	 * 3. 切换当前房源的房东uid（旧 换 新）
	 * 4. 切换评价表房东uid
	 *   A. 评价人是房东  根据房源fid  评价人uid=旧uid  修改成新uid
	 *   B. 被评价人是房东  根据房源fid 被评价人uid=旧uid 修改成新uid
	 * 5. 切换待入住的订单
	 *   待入住订单的房东uid  切换成新 uid 并返回当前 已经处理的订单编号 并做日志
	 *   
	 * 问题： 由于分库原因 这些操作无法再一个事物中进行
	 * 
	 *
	 * @author yd
	 * @created 2016年12月6日 上午10:00:37
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String exchanageLanlordHouse(String paramJson) {

		DataTransferObject dto = new DataTransferObject();
		
		ExchangeHouseLanlordDto exchange = JsonEntityTransform.json2Object(paramJson, ExchangeHouseLanlordDto.class);
		
		if(!Check.NuNObj(exchange)||!Check.NuNStrStrict(exchange.getHouseFid())
				||!Check.NuNObj(exchange.getRentWay())
				||!Check.NuNStrStrict(exchange.getNewLanUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数值为null");
		}
		
		HouseBaseMsgEntity houseBaseMsg= this.houseIssueServiceImpl.findHouseBaseMsgByFid(exchange.getHouseFid());
		
		return dto.toJsonString();
	}
	
	@Override
	public String sendRabbitMq(String houseFid, Integer rentWay){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseFid) || Check.NuNObj(rentWay)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try {
			String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseFid, null, rentWay, null, null));
			rabbitMqSendClient.sendQueue(queueName, pushContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseFollowService#getHouseListForIMFollow(java.lang.String)
	 */
	@Override
	public String getHoseFidListForIMFollow(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
		if(Check.NuNMap(paramMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNStr((String) paramMap.get("houseName")) && Check.NuNStr((String) paramMap.get("nationCode"))
				&& Check.NuNStr((String) paramMap.get("provinceCode"))  && Check.NuNStr((String) paramMap.get("cityCode"))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		List<String> houseFidList = troyHouseMgtServiceImpl.getHoseFidListForIMFollow(paramMap);
		dto.putValue("houseFidList", houseFidList);
		return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseFollowService#getHouseListForIMFollow(java.lang.String)
	 */
	@Override
	public String getRoomFidListForIMFollow(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
		if(Check.NuNMap(paramMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNStr((String) paramMap.get("houseName")) && Check.NuNStr((String) paramMap.get("nationCode")) 
				&& Check.NuNStr((String) paramMap.get("provinceCode"))  && Check.NuNStr((String) paramMap.get("cityCode"))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		List<String> roomFidList = troyHouseMgtServiceImpl.getRoomFidListForIMFollow(paramMap);
		dto.putValue("roomFidList", roomFidList);
		return dto.toJsonString();
	}
	
	@Override
	public String getHouseInfoForImFollow(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
		if(Check.NuNMap(paramMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNStr((String) paramMap.get("houseFid"))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		 HouseCityVo houseCityVo = troyHouseMgtServiceImpl.getHouseInfoForImFollow(paramMap);
		 dto.putValue("houseCityVo", houseCityVo);
	     return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService#getRoomInfoForImFollow(java.lang.String)
	 */
	@Override
	public String getRoomInfoForImFollow(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
		if(Check.NuNMap(paramMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNStr((String) paramMap.get("roomFid"))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		 HouseCityVo houseCityVo = troyHouseMgtServiceImpl.getRoomInfoForImFollow(paramMap);
		 dto.putValue("houseCityVo", houseCityVo);
	     return dto.toJsonString();
	}

	@Override
	public String getHouseOrRoomNameList(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		HouseBaseParamsDto houseBaseParamsDto = JsonEntityTransform.json2Object(paramJson,HouseBaseParamsDto.class);
		if(Check.NuNObj(houseBaseParamsDto)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		List<String> nameList = troyHouseMgtServiceImpl.getHouseOrRoomNameList(houseBaseParamsDto);
		dto.putValue("nameList", nameList);
		return dto.toJsonString();
	}

	@Override
	public String getHouseUpdateFieldAuditNewlogByCondition(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try{
			HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = JsonEntityTransform.json2Entity(paramJson,HouseUpdateFieldAuditNewlogEntity.class);
			if(Check.NuNObj(houseUpdateFieldAuditNewlogEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource,MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			List<HouseFieldAuditLogVo> list = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
			dto.putValue("list",list);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("系统异常");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	@Override
	public String updateHouseUpdateFieldAuditNewlogById(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try{
			HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = JsonEntityTransform.json2Entity(paramJson,HouseUpdateFieldAuditNewlogEntity.class);
			if(Check.NuNObj(houseUpdateFieldAuditNewlogEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource,MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			int num = troyHouseMgtServiceImpl.updateHouseUpdateFieldAuditNewlogById(houseUpdateFieldAuditNewlogEntity);
			dto.putValue("num",num);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("系统异常");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	@Override
	public String updateHousePicMsg(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			LogUtil.info(LOGGER, "updateHousePicMsg(),更新照片信息参数:param={}",paramJson);
			HousePicMsgEntity housePicMsgEntity = JsonEntityTransform.json2Object(paramJson, HousePicMsgEntity.class);
			if(Check.NuNObj(housePicMsgEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNStrStrict(housePicMsgEntity.getFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			int num = troyHouseMgtServiceImpl.updateHousePicMsg(housePicMsgEntity);
			dto.putValue("num",num);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String approveGroundingHouseInfo(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			LogUtil.info(LOGGER, "ApproveGroundingHouseInfo(),审核通过上架房源的待审核信息:param={}",paramJson);
			
			HouseUpdateFieldAuditDto houseUpdateFieldAuditDto = JsonEntityTransform.json2Object(paramJson, HouseUpdateFieldAuditDto.class);
			HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = houseUpdateFieldAuditDto.getHouseUpdateFieldAuditNewlog();
			if(Check.NuNObj(houseUpdateFieldAuditNewlogEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNStrStrict(houseUpdateFieldAuditNewlogEntity.getHouseFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			
			List<String> picFids= houseUpdateFieldAuditDto.getPicFids();
			
			List<HouseFieldAuditLogVo> houseFieldAuditLogVos = troyHouseMgtServiceImpl.approveGroundingHouseInfo(houseUpdateFieldAuditNewlogEntity,picFids);
			dto.putValue("houseFieldAuditLogVos",houseFieldAuditLogVos);
			
			HouseBaseMsgEntity house = this.houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseUpdateFieldAuditNewlogEntity.getHouseFid());
			
			if(!Check.NuNObj(house)){
				LogUtil.info(LOGGER, "照片审核,推送队列消息开始houseFid={}",house.getFid());
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(house.getFid(),
						null, house.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "照片审核,推送队列消息结束,推送内容:{}", pushContent);
			}
		
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}