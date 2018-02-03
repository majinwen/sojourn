/**
 * @FileName: HouseManageServiceProxy.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author bushujie
 * @created 2016年4月3日 上午11:29:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.house.logic.HouseCheckLogic;
import com.ziroom.minsu.services.house.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.logic.ValidateResult;
import com.ziroom.minsu.services.house.service.*;
import com.ziroom.minsu.services.house.smartlock.dto.MSmartLockDto;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HouseIssueStepEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum008Enum;
import com.ziroom.minsu.valenum.productrules.ProductRulesTonightDisEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>房东端-房源管理接口实现代理</p>
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
@Component("house.houseManageServiceProxy")
public class HouseManageServiceProxy implements HouseManageService {

	/**
	 * 日志工具类
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseManageServiceProxy.class);

	private static final String YEAR_FORMAT_PATTERN = "yyyy";

	private static final String MONTH_FORMAT_PATTERN = "MM";
	
	private static final String YEAR_MONTH_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Resource(name="house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;


	@Resource(name="house.messageSource")
	private MessageSource messageSource;

	@Resource(name="house.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Resource(name="house.houseCheckLogic")
	private HouseCheckLogic houseCheckLogic;

	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;

	@Resource(name="house.houseSmartlockServiceImpl")
	private HouseSmartlockServiceImpl houseSmartlockServiceImpl;

	@Resource(name = "house.tenantHouseServiceImpl")
	private TenantHouseServiceImpl tenantHouseServiceImpl;

	@Value("#{'${fresh.search.system}'.trim()}")
	private String system;

	@Value("#{'${fresh.search.module}'.trim()}")
	private String module;

	@Value("#{'${fresh.search.function}'.trim()}")
	private String function;

	@Resource(name="house.queueName")
	private QueueName queueName ;

	@Autowired
	private RedisOperations redisOperations;

	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;

	@Resource(name = "house.houseTonightDiscountServiceImpl")
	private HouseTonightDiscountServiceImpl houseTonightDiscountServiceImpl;
	
	@Resource(name="house.houseBizServiceImpl")
	private HouseBizServiceImpl houseBizServiceImpl;

	/**
	 * 根据你房源编号集合查询房源信息集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 11:01
	 */
	@Override
	public String getHouseListByHouseSns(String paramJson) {
		LogUtil.debug(LOGGER, "参数:houseSns={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			List<String> houseSns = JsonEntityTransform.json2ObjectList(paramJson, String.class);
			if(Check.NuNObj(houseSns)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("未传入房源编号集合");
				return dto.toJsonString();
			}
			List<HouseBaseMsgEntity> houseList = houseManageServiceImpl.getHouseBaseListByHouseSns(houseSns);
			dto.putValue("houseList",houseList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 根据房间编号集合查询房间信息集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 11:01
	 */
	@Override
	public String getRoomListByRoomSns(String paramJson) {
		LogUtil.debug(LOGGER, "参数:houseSns={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			List<String> roomSns = JsonEntityTransform.json2ObjectList(paramJson, String.class);
			if(Check.NuNObj(roomSns)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("未传入房间编号集合");
				return dto.toJsonString();
			}
			List<HouseRoomMsgEntity> roomList = houseManageServiceImpl.getRoomBaseListByRoomSns(roomSns);
			dto.putValue("roomList",roomList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String refreshHouse(String houseBaseFid) {
		LogUtil.debug(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		//判断房源id是否为null
		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		Date nowDate=new Date();
		//判断房源是否刷新
		if(DateUtil.dateFormat(nowDate, "yyyy-MM-dd").equals(DateUtil.dateFormat(houseBaseMsgEntity.getRefreshDate(), "yyyy-MM-dd"))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_REPEAT_REFRESH));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			int upNum= houseManageServiceImpl.refreshHouse(houseBaseFid);
			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){
				LogUtil.info(LOGGER, "刷新房源成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.SJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "刷新房源成功,推送队列消息结束,推送内容:{}", pushContent);
			}
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String houseList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseBaseListDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseBaseListDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		try {
			HouseBaseListDto houseBaseListDto=validateResult.getResultObj();
			PagingResult<HouseBaseListVo> pagingResult=houseManageServiceImpl.findHouseBaseList(houseBaseListDto);
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
	public String houseRoomList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseBaseListDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseBaseListDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		try {
			HouseBaseListDto houseBaseListDto=validateResult.getResultObj();
			PagingResult<HouseRoomListVo> pagingResult=houseManageServiceImpl.findHouseRoomList(houseBaseListDto);
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
	public String upDownHouse(String houseBaseFid, String landlordUid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={},landlordUid={}", houseBaseFid, landlordUid);
		DataTransferObject dto = new DataTransferObject();
		//判断房源id是否为null
		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断房东id是否为null
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.LANDLORD_UID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断房源是否存在
		HouseBaseMsgEntity houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断是否符合下架状态
		if (HouseStatusEnum.SJ.getCode() != houseBaseMsgEntity.getHouseStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			int upNum=houseManageServiceImpl.upDownHouse(houseBaseMsgEntity, landlordUid,HouseStatusEnum.XJ.getCode());
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){				
				LogUtil.info(LOGGER, "房源下架成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						RentWayEnum.HOUSE.getCode(), houseBaseMsgEntity.getHouseStatus(), HouseStatusEnum.XJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源下架成功,推送队列消息结束,推送内容:{}", pushContent);
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
	public String upDownHouseRoom(String houseRoomFid, String landlordUid) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={},landlordUid={}", houseRoomFid, landlordUid);
		DataTransferObject dto = new DataTransferObject();
		//判断房间id是否为null
		if(Check.NuNStr(houseRoomFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断房东id是否为null
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.LANDLORD_UID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断房间是否存在
		HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(houseRoomFid);
		if(Check.NuNObj(houseRoomMsgEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断是否符合下架状态
		if(HouseStatusEnum.SJ.getCode()!=houseRoomMsgEntity.getRoomStatus()){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			int upNum=houseManageServiceImpl.upDownHouseRoom(houseRoomMsgEntity, landlordUid,HouseStatusEnum.XJ.getCode());
			dto.putValue("upNum", upNum);

			if(!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){
				LogUtil.info(LOGGER, "房间下架成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseRoomMsgEntity.getHouseBaseFid(),
						houseRoomMsgEntity.getFid(), RentWayEnum.ROOM.getCode(), houseRoomMsgEntity.getRoomStatus(),
						HouseStatusEnum.XJ.getCode()));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房间下架成功,推送队列消息结束,推送内容:{}", pushContent);
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
	public String leaseCalendar(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<LeaseCalendarDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, LeaseCalendarDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		try {
			LeaseCalendarDto leaseCalendarDto=validateResult.getResultObj();

			//整租判断房源fid是否为空
			if(HouseConstant.HOUSE_RENTWAY_HOUSE == leaseCalendarDto.getRentWay() && Check.NuNStr(leaseCalendarDto.getHouseBaseFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			//合租判断房间fid是否为空
			if(HouseConstant.HOUSE_RENTWAY_ROOM == leaseCalendarDto.getRentWay() && Check.NuNStr(leaseCalendarDto.getHouseRoomFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			LeaseCalendarVo leaseCalendarVo=new LeaseCalendarVo();
			HouseBaseMsgEntity houseBaseMsgEntity = null;
			if(HouseConstant.HOUSE_RENTWAY_HOUSE == leaseCalendarDto.getRentWay()){
				houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(leaseCalendarDto.getHouseBaseFid());
				//判断房源是否存在
				if(Check.NuNObj(houseBaseMsgEntity)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					LogUtil.error(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
				leaseCalendarVo.setUsualPrice(houseBaseMsgEntity.getLeasePrice());
				leaseCalendarVo.setTillDate(houseBaseMsgEntity.getTillDate());
			}
			if(HouseConstant.HOUSE_RENTWAY_ROOM == leaseCalendarDto.getRentWay()){
				HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(leaseCalendarDto.getHouseRoomFid());
				//判断房间是否存在
				if(Check.NuNObj(houseRoomMsgEntity)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
					LogUtil.error(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
				houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseRoomMsgEntity.getHouseBaseFid());
				//判断房源是否存在
				if(Check.NuNObj(houseBaseMsgEntity)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					LogUtil.error(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
				leaseCalendarVo.setUsualPrice(houseRoomMsgEntity.getRoomPrice());
				leaseCalendarVo.setTillDate(houseBaseMsgEntity.getTillDate());
			}
			//查询特殊价格列表
			leaseCalendarVo.setSpecialPriceList(houseManageServiceImpl.findSpecialPriceList(leaseCalendarDto));
			dto.putValue("calendarData", leaseCalendarVo);
			dto.putValue("houseBaseFid", houseBaseMsgEntity.getFid());
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String setSpecialPrice(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			List<HousePriceConfDto> housePriceList=JsonEntityTransform.json2ObjectList(paramJson, HousePriceConfDto.class);
			if(Check.NuNCollection(housePriceList)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			//判断出租方式是否为空
			if(Check.NuNObj(housePriceList.get(0).getRentWay())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			//判断房子是否存在
			if(housePriceList.get(0).getRentWay()==0){
				if(!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, housePriceList.get(0).getHouseBaseFid())){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					return dto.toJsonString();
				}
			}else if(housePriceList.get(0).getRentWay()==1){
				if(!houseCheckLogic.checkHouseRoomNull(houseManageServiceImpl, dto, housePriceList.get(0).getRoomFid())){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
					return dto.toJsonString();
				}
			}
			//判断日期是否为空
			if(Check.NuNObj(housePriceList.get(0).getSetTime())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PRICE_SETTIME_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			//设置价格必须大于0
			if(Check.NuNObj(housePriceList.get(0).getPriceVal())||housePriceList.get(0).getPriceVal()==0){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PRICE_VALUE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			//循环设置价格
			for(HousePriceConfDto housePriceConfDto:housePriceList){
				houseManageServiceImpl.setSpecialPrice(housePriceConfDto);
			}

			if(!Check.NuNCollection(housePriceList) && !Check.NuNObj(housePriceList.get(0)) && !Check.NuNStr(housePriceList.get(0).getHouseBaseFid())){
				LogUtil.info(LOGGER, "特殊价格保存成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(housePriceList.get(0).getHouseBaseFid(), 
						housePriceList.get(0).getRoomFid(), housePriceList.get(0).getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "特殊价格保存成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String saveHousePriceWeekConf(String paramJson) {
		LogUtil.info(LOGGER, "saveHousePriceWeekConf param:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			// 1 参数校验
			ValidateResult<HousePriceWeekConfDto> validateResult = paramCheckLogic
					.checkParamValidate(paramJson, HousePriceWeekConfDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "参数:{},错误信息:{}", paramJson, validateResult.getDto().getMsg());
				return validateResult.getDto().toJsonString();
			}
			HousePriceWeekConfDto weekPriceDto = validateResult.getResultObj();

			if (Check.NuNCollection(weekPriceDto.getSetWeeks())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			if (Check.NuNStr(weekPriceDto.getHouseBaseFid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			if (Check.NuNStr(weekPriceDto.getCreateUid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.LANDLORD_UID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			//判断房源(房间)是否存在
			if (weekPriceDto.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
				if (!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, weekPriceDto.getHouseBaseFid())) {
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					return dto.toJsonString();
				}
			} else if (weekPriceDto.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
				if (Check.NuNStr(weekPriceDto.getHouseRoomFid())) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
					LogUtil.error(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}

				if (!houseCheckLogic.checkHouseRoomNull(houseManageServiceImpl, dto, weekPriceDto.getHouseRoomFid())) {
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
					return dto.toJsonString();
				}
			} else {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_ERROR));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 设置价格必须大于0
			if (Check.NuNObj(weekPriceDto.getPriceVal()) || weekPriceDto.getPriceVal() <= 0) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PRICE_VALUE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			int num = houseManageServiceImpl.saveHousePriceWeekConf(weekPriceDto);
			dto.putValue("saveNum", num);

			if(!Check.NuNStr(weekPriceDto.getHouseBaseFid())){
				LogUtil.info(LOGGER, "周末价格保存成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(weekPriceDto.getHouseBaseFid(), 
						weekPriceDto.getHouseRoomFid(), weekPriceDto.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "周末价格保存成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "saveHousePriceWeekConf error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String houseDetail(String houseBaseFid, String landlordUid) {
		LogUtil.debug(LOGGER, "参数:houseBaseFid={},landlordUid={}", houseBaseFid, landlordUid);
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断房东id是否为null
			if(Check.NuNStr(landlordUid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.LANDLORD_UID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			if(!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto,houseBaseFid)){
				LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				return dto.toJsonString();
			}
			HouseDetailVo houseDetailVo=houseManageServiceImpl.findHouseDetail(houseBaseFid, landlordUid);
			dto.putValue("houseDetail", houseDetailVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 根据房东uid查询已上架房源
	 */
	@Override
	public String searchHouseBaseMsgByLandlorduid(String landlordUid) {
		LogUtil.info(LOGGER, "参数:landlordUid={}", landlordUid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_LANDLORDUID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByLandlordUid(landlordUid);
			dto.putValue("obj", houseBaseMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String findOrderNeedHouseVoPlus(String paramJson){
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		OrderHouseDto orderHouseDto = JsonEntityTransform.json2Object(paramJson, OrderHouseDto.class);
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断房源是否存在
			String houseFid = orderHouseDto.getFid();
			String fid = orderHouseDto.getFid();
			Integer rentWay = orderHouseDto.getRentWay();

			TonightDiscountEntity tonightDiscountEntity = new TonightDiscountEntity();
			tonightDiscountEntity.setRentWay(rentWay);
			if(rentWay==0){
				if(!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, fid)){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					return dto.toJsonString();
				}
				tonightDiscountEntity.setHouseFid(fid);
			}else if(rentWay==1){
				if(!houseCheckLogic.checkHouseRoomNull(houseManageServiceImpl, dto, fid)){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
					return dto.toJsonString();
				}
				houseFid = String.valueOf(dto.getData().get("houseFid"));
				tonightDiscountEntity.setHouseFid(houseFid);
				tonightDiscountEntity.setRoomFid(fid);
			}

			OrderNeedHouseVo houseBase =  houseManageServiceImpl.findOrderNeedHouseVo(fid, rentWay);
			List<HouseConfMsgEntity> houseConfList = houseBase.getHouseConfList();
            //设置房屋守则为空，订单用不到，先删除方式特殊字符导致解析异常
            if (!Check.NuNObj(houseBase.getHouseRoomExtEntity())) {
                HouseRoomExtEntity houseRoomExtEntity = houseBase.getHouseRoomExtEntity();
                houseRoomExtEntity.setRoomRules("");
            }
            //押金计算  兼容之前的 按房租收取  统一按固定收取
			HouseConfMsgEntity con = this.houseIssueServiceImpl.findHouseDepositConfByHouseFid(houseBase.getFid(),houseBase.getRoomFid(),rentWay,dto);
			if(!Check.NuNCollection(houseConfList)){
				for (HouseConfMsgEntity houseConfMsgEntity : houseConfList) {
					if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue())
							||houseConfMsgEntity.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue())){
						houseConfMsgEntity.setDicCode(con.getDicCode());
						houseConfMsgEntity.setDicVal(con.getDicVal());
					}
				}
			}
			//存放长租优惠
			LogUtil.info(LOGGER,"相差天数参数:{}",orderHouseDto.calcDaysByDate());
			List<HouseConfMsgEntity> disRateList = new ArrayList<HouseConfMsgEntity>();
			HouseConfMsgEntity houseConf = new HouseConfMsgEntity();
			//查询用户设置了的且开关打开的状态
			houseConf.setIsDel(IsDelEnum.NOT_DEL.getCode());
			if(orderHouseDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				houseConf.setHouseBaseFid(orderHouseDto.getFid());
				houseConf.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getParentValue());
				disRateList = houseIssueServiceImpl.findGapAndFlexPrice(houseConf);
			}else if(orderHouseDto.getRentWay()==RentWayEnum.ROOM.getCode()){
				houseConf.setRoomFid(orderHouseDto.getFid());
				houseConf.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getParentValue());
				disRateList = houseIssueServiceImpl.findGapAndFlexPrice(houseConf);
			}
			//放置符合订单天数的折扣优惠
			if(!Check.NuNCollection(disRateList)){
				HouseConfMsgEntity houseConfChangZu = null; 
				ProductRulesEnum0019 longTermEnum = null;
				for(HouseConfMsgEntity conf : disRateList){

					if(orderHouseDto.calcDaysByDate() >= ProductRulesEnum0019.ProductRulesEnum0019002.getDayNum()
							&&conf.getDicCode().equals(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()) 
							&& Integer.parseInt(conf.getDicVal())>=0 && Integer.parseInt(conf.getDicVal())<100){
						conf.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getParentValue());
						houseConfChangZu = conf;
						longTermEnum = ProductRulesEnum0019.ProductRulesEnum0019002;
						break;
					}else if(orderHouseDto.calcDaysByDate() >= ProductRulesEnum0019.ProductRulesEnum0019001.getDayNum()
							&&conf.getDicCode().equals(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()) 
							&& Integer.parseInt(conf.getDicVal())>=0 && Integer.parseInt(conf.getDicVal())<100
							&&Check.NuNObj(houseConfChangZu)){
						conf.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getParentValue());
						houseConfChangZu = conf;
						longTermEnum = ProductRulesEnum0019.ProductRulesEnum0019001;
					}
				}
				if(!Check.NuNObj(houseConfChangZu)){
					houseConfList.add(houseConfChangZu);
					houseBase.getEnumDicMap().put(longTermEnum.getValue(), houseConfChangZu.getDicVal());
				}
			}

			//存放今夜特价配置 房东自己设置的折扣
			fillOrderTonightDiscount(tonightDiscountEntity, houseConfList);

			dto.putValue("houseBase", houseBase);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 查询订单 今夜特价配置
	 *
	 * @author yd
	 * @created 2017年5月15日 下午7:16:59
	 *
	 * @param tonightDiscountEntity
	 * @param houseConfList
	 */
	private  void fillOrderTonightDiscount(TonightDiscountEntity tonightDiscountEntity,List<HouseConfMsgEntity> houseConfList){
		
		//存放今夜特价配置 房东自己设置的折扣
		TonightDiscountEntity tonightDis = houseTonightDiscountServiceImpl.findTonightDiscountByRentway(tonightDiscountEntity);
		if(!Check.NuNObj(tonightDis)){

			Date startTime = tonightDis.getStartTime();
			Date endTime = tonightDis.getEndTime();
			if(!Check.NuNObj(startTime)&&!Check.NuNObj(endTime)){
				long startTimeLong = startTime.getTime();
				long endTimeLong = endTime.getTime();
				Date currentDate = new Date();
				long currentDateLong = currentDate.getTime();
				LogUtil.info(LOGGER, "【获取房源今夜特价配置】houseFid={},roomFid={},rentWay={},discount={},startTime={},endTime={},currentDate={}",
						tonightDis.getHouseFid(),tonightDis.getRoomFid(),
						tonightDis.getRentWay(),tonightDis.getDiscount(),
						DateUtil.dateFormat(startTime, HouseManageServiceProxy.YEAR_MONTH_PATTERN),
						DateUtil.dateFormat(endTime, HouseManageServiceProxy.YEAR_MONTH_PATTERN),
						DateUtil.dateFormat(currentDate, HouseManageServiceProxy.YEAR_MONTH_PATTERN));
				if(currentDateLong >= startTimeLong && currentDateLong <= endTimeLong){
					HouseConfMsgEntity conTonight = new HouseConfMsgEntity();
					conTonight.setHouseBaseFid(tonightDis.getHouseFid());
					conTonight.setRoomFid(tonightDis.getRoomFid());
					conTonight.setDicCode(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode());
					conTonight.setDicVal(String.valueOf(tonightDis.getDiscount()));
					houseConfList.add(conTonight);
				}
			}
		}

	}

	@Override
	public String findOrderNeedHouseVo(String fid, Integer rentWay) {
		LogUtil.debug(LOGGER, "参数:fid={},rentWay={}", fid, rentWay);
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断房源是否存在
			String houseFid = fid;
			if(rentWay==0){
				if(!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, fid)){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					return dto.toJsonString();
				}
			}else if(rentWay==1){
				if(!houseCheckLogic.checkHouseRoomNull(houseManageServiceImpl, dto, fid)){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
					return dto.toJsonString();
				}
				houseFid = String.valueOf(dto.getData().get("houseFid"));
			}

			OrderNeedHouseVo houseBase =  houseManageServiceImpl.findOrderNeedHouseVo(fid, rentWay);
			List<HouseConfMsgEntity> houseConfList = houseBase.getHouseConfList();

			//押金计算 
			HouseConfMsgEntity con = this.houseIssueServiceImpl.findHouseDepositConfByHouseFid(houseBase.getFid(),houseBase.getRoomFid(),rentWay,dto);
			if(!Check.NuNCollection(houseConfList)){
				for (HouseConfMsgEntity houseConfMsgEntity : houseConfList) {
					if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue())
							||houseConfMsgEntity.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue())){
						houseConfMsgEntity.setDicCode(con.getDicCode());
						houseConfMsgEntity.setDicVal(con.getDicVal());
					}
				}
			}
			dto.putValue("houseBase", houseBase);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	@Override
	public String modifyHouseLeaseType(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseMsgEntity houseBaseMsgEntity=JsonEntityTransform.json2Entity(paramJson, HouseBaseMsgEntity.class);
			//判断房源是否存在
			if(!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, houseBaseMsgEntity.getFid())){
				LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
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
	public String communityNameList(String landlordUid) {
		LogUtil.debug(LOGGER, "参数:landlordUid={}", landlordUid);
		DataTransferObject dto = new DataTransferObject();
		try {
			List<SearchTerm> list=houseManageServiceImpl.getCommunityListByLandlordUid(landlordUid);
			Map<Integer,String> houseStatusMap=HouseStatusEnum.getEnumMap();
			List<SearchTerm> houseStatusList=new ArrayList<SearchTerm>();
			for(Integer key:houseStatusMap.keySet()){
				SearchTerm searchTerm=new SearchTerm();
				searchTerm.setTermValue(key.toString());
				searchTerm.setTermName(houseStatusMap.get(key));
				houseStatusList.add(searchTerm);
			}
			dto.putValue("list", list);
			dto.putValue("houseStatusList", houseStatusList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHouseRoomList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseBaseListDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseBaseListDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}

		try {
			HouseBaseListDto houseBaseListDto=validateResult.getResultObj();
			PagingResult<HouseRoomVo> pagingResult = houseManageServiceImpl.findHouseRoomVoList(houseBaseListDto);

			List<HouseRoomVo> list = pagingResult.getRows();
			//查询房源是否发布过
			for(HouseRoomVo vo:list){
				if(houseBizServiceImpl.getHouseBizNumByHouseBaseFid(vo.getHouseBaseFid())>0||houseBizServiceImpl.findToStatusNum(vo.getHouseBaseFid(), HouseStatusEnum.YFB.getCode())>0){
					vo.setIsIssue(YesOrNoEnum.YES.getCode());
				} else {
					vo.setIsIssue(YesOrNoEnum.NO.getCode());
				}
				//判断是否可删除
				if(vo.getRoomStatus()==HouseStatusEnum.DFB.getCode()||vo.getRoomStatus()==HouseStatusEnum.ZPSHWTG.getCode()||vo.getRoomStatus()==HouseStatusEnum.XJ.getCode()){
					vo.setIsNoDel(YesOrNoEnum.YES.getCode());
				} else {
					vo.setIsNoDel(YesOrNoEnum.NO.getCode());
				}
				//状态处理
				vo.setStatus(vo.getRoomStatus());
				vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getRoomStatus()).getShowStatusName());
			}
			list = getHouseRoomVo(list);
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 
	 * 获取房源列表vo
	 *  说明：默认照片逻辑：房客端和房东端的房源列表页显示封面照片，如没有设置封面照片，则默认显示卧室、客厅、室外三个区域中第一张上传的照片
	 * 1. 默认照片没有
	 *  a. 整租 默认显示卧室、客厅、室外三个区域中第一张上传的照片
	 *  b. 分租  默认显示当前卧室、客厅、室外三个区域中第一张上传的照片
	 *
	 * @author yd
	 * @created 2016年10月18日 上午11:53:19
	 *
	 * @param list
	 * @return
	 */
	private List<HouseRoomVo> getHouseRoomVo(List<HouseRoomVo> list){

		if(!Check.NuNCollection(list)){
			for (HouseRoomVo houseRoomVo : list) {

				HousePicMsgEntity housePicMsg=null;
				if(houseRoomVo.getRentWay()==RentWayEnum.HOUSE.getCode()){
					houseRoomVo.setName(houseRoomVo.getHouseName());
					Integer houseStatus = houseRoomVo.getHouseStatus();
					houseRoomVo.setStatus(houseStatus);
					if(houseStatus != null){
						HouseStatusEnum houseStatusEnum = HouseStatusEnum.getHouseStatusByCode(houseStatus);
						houseRoomVo.setStatusName(houseStatusEnum == null ? null : houseStatusEnum.getShowStatusName());
					}
					housePicMsg = houseManageServiceImpl.getLandlordHouseDefaultPic(houseRoomVo.getHouseBaseFid());

				} 
				if(houseRoomVo.getRentWay()==RentWayEnum.ROOM.getCode()){
					houseRoomVo.setName(houseRoomVo.getRoomName());
					Integer roomStatus = houseRoomVo.getRoomStatus();
					houseRoomVo.setStatus(roomStatus);
					if(roomStatus != null){
						HouseStatusEnum houseStatusEnum = HouseStatusEnum.getHouseStatusByCode(roomStatus);
						houseRoomVo.setStatusName(houseStatusEnum == null ? null : houseStatusEnum.getShowStatusName());
					}
					housePicMsg=houseManageServiceImpl.findLandlordRoomDefaultPic(houseRoomVo.getHouseRoomFid());
				}
				//获取缓存pv
				houseRoomVo.setHousePv(this.tenantHouseServiceImpl.getHousePv(houseRoomVo.getHouseBaseFid(), houseRoomVo.getHouseRoomFid(), houseRoomVo.getRentWay(), redisOperations));

				if(Check.NuNObj(housePicMsg)){
					housePicMsg = this.houseManageServiceImpl.findHousePicFirstByHouseFid(houseRoomVo.getHouseBaseFid(), houseRoomVo.getHouseRoomFid(), houseRoomVo.getRentWay());
				}
				if(Check.NuNObj(housePicMsg)){
					continue;
				}
				houseRoomVo.setDefaultPic(housePicMsg.getPicBaseUrl()+housePicMsg.getPicSuffix());
			}
		}
		return list;
	}
	@Override
	public String searchLandlordRevenue(String landlordUid) {
		LogUtil.debug(LOGGER, "参数:landlordUid={}", landlordUid);
		DataTransferObject dto = new DataTransferObject();
		//判断房东id是否为null
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.LANDLORD_UID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			LandlordRevenueVo RevenueVo = houseManageServiceImpl.findLandLordRevenue(landlordUid);
			dto.putValue("obj", RevenueVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHouseRevenueListByLandlordUid(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<LandlordRevenueDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, LandlordRevenueDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		LandlordRevenueDto landlordRevenueDto = validateResult.getResultObj();

		// 不传年参数默认为当前年
		if(Check.NuNObj(landlordRevenueDto.getStatisticsDateYear())){
			LogUtil.info(LOGGER, "statisticsDateYear is null or blank");
			landlordRevenueDto.setStatisticsDateYear(Integer.valueOf(DateUtil.dateFormat(new Date(), YEAR_FORMAT_PATTERN)));
		}

		try {
			List<LandlordRevenueVo> revenueVoList = houseManageServiceImpl
					.findHouseRevenueListByLandlordUid(landlordRevenueDto);
			dto.putValue("list", revenueVoList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchMonthRevenueListByHouseBaseFid(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		LandlordRevenueDto landlordRevenueDto = JsonEntityTransform.json2Object(paramJson, LandlordRevenueDto.class);

		if(Check.NuNStr(landlordRevenueDto.getHouseBaseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		if(Check.NuNObj(landlordRevenueDto.getStatisticsDateYear())){
			Date currentDate = new Date();
			landlordRevenueDto.setStatisticsDateYear(Integer.valueOf(DateUtil.dateFormat(currentDate, YEAR_FORMAT_PATTERN)));
			landlordRevenueDto.setStatisticsDateMonth(Integer.valueOf(DateUtil.dateFormat(currentDate, MONTH_FORMAT_PATTERN)));
		}

		try {
			List<HouseMonthRevenueVo> voList = houseManageServiceImpl
					.findMonthRevenueListByLandlordUidAndHouseBaseFid(landlordRevenueDto);
			dto.putValue("list", voList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchMonthRevenueList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<LandlordRevenueDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, LandlordRevenueDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		LandlordRevenueDto landlordRevenueDto = validateResult.getResultObj();

		if(Check.NuNObj(landlordRevenueDto.getStatisticsDateYear())){
			LogUtil.info(LOGGER, "statisticsDateYear is null or blank");
			landlordRevenueDto.setStatisticsDateYear(Integer.valueOf(DateUtil.dateFormat(new Date(), YEAR_FORMAT_PATTERN)));
		}

		try {
			List<LandlordRevenueVo> monthRevenueList = houseManageServiceImpl
					.findMonthRevenueListByLandlordUid(landlordRevenueDto);
			dto.putValue("list", monthRevenueList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}



	/**
	 * 校验房源或房间是否存在
	 * @param paramJson
	 * @return
	 */
	@Override
	public String checkHouseOrRoom(String hosueCheck){

		DataTransferObject dto = new DataTransferObject();

		HouseCheckDto houseCheckDto = JsonEntityTransform.json2Object(hosueCheck, HouseCheckDto.class);

		if(Check.NuNObj(houseCheckDto)||Check.NuNStr(houseCheckDto.getFid())||Check.NuNObj(houseCheckDto.getRentWay())){

			LogUtil.info(LOGGER, "请求入参houseCheckDto={}", houseCheckDto);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}

		int rentWay = houseCheckDto.getRentWay().intValue();
		HouseCheckLogic houseCheckLogic  = new HouseCheckLogic();
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, houseCheckDto.getFid());
		}
		if(rentWay == RentWayEnum.ROOM.getCode()){
			houseCheckLogic.checkHouseRoomNull(houseManageServiceImpl, dto, houseCheckDto.getFid());
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 房东端房源列表（以房源的维度）
	 * 
	 * 说明：默认照片逻辑：房客端和房东端的房源列表页显示封面照片，如没有设置封面照片，则默认显示卧室、客厅、室外三个区域中第一张上传的照片
	 * 1. 默认照片没有
	 *  a. 整租 默认显示卧室、客厅、室外三个区域中第一张上传的照片
	 *  b. 分租  默认显示当前卧室、客厅、室外三个区域中第一张上传的照片
	 *
	 * @author bushujie
	 * @created 2016年6月14日 下午2:11:45
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String searchLandlordHouseList(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseBaseListDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseBaseListDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}

		try {
			HouseBaseListDto houseBaseListDto=validateResult.getResultObj();
			PagingResult<HouseRoomVo> pagingResult = houseManageServiceImpl.getLandlordHouseList(houseBaseListDto);

			List<HouseRoomVo> list = pagingResult.getRows();
			//查询房源是否发布过
			for(HouseRoomVo vo:list){
				if(houseBizServiceImpl.getHouseBizNumByHouseBaseFid(vo.getHouseBaseFid())>0||houseBizServiceImpl.findToStatusNum(vo.getHouseBaseFid(), HouseStatusEnum.YFB.getCode())>0){
					vo.setIsIssue(YesOrNoEnum.YES.getCode());
				} else {
					vo.setIsIssue(YesOrNoEnum.NO.getCode());
				}
				//判断是否可删除
				if(vo.getHouseStatus()==HouseStatusEnum.DFB.getCode()||vo.getHouseStatus()==HouseStatusEnum.ZPSHWTG.getCode()||vo.getHouseStatus()==HouseStatusEnum.XJ.getCode()){
					vo.setIsNoDel(YesOrNoEnum.YES.getCode());
				} else {
					vo.setIsNoDel(YesOrNoEnum.NO.getCode());
				}
				//状态处理
				vo.setStatus(vo.getHouseStatus());
				vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getHouseStatus()).getShowStatusName());
			}
			list = getHouseRoomVo(list);
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseManageService#saveHouseSmartlock(java.lang.String)
	 */
	public String saveHouseSmartlock(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			MSmartLockDto smartLockDto = JsonEntityTransform.json2Entity(paramJson, MSmartLockDto.class);
			dto = validateSmartOper(smartLockDto);
			if(dto.getCode() == DataTransferObject.ERROR){
				return dto.toJsonString();
			}
			int count = houseSmartlockServiceImpl.saveHouseSmartLock(smartLockDto);
			dto.putValue("count", count);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseManageService#updateHouseSmartlock(java.lang.String)
	 */
	@Override
	public String updateHouseSmartlock(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			MSmartLockDto smartLockDto = JsonEntityTransform.json2Entity(paramJson, MSmartLockDto.class);
			dto = validateSmartOper(smartLockDto);
			if(dto.getCode() == DataTransferObject.ERROR){
				return dto.toJsonString();
			}
			int count = houseSmartlockServiceImpl.updateHouseSmartlock(smartLockDto);
			dto.putValue("count", count);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseManageService#findHouseSmartlock(java.lang.String)
	 */
	@Override
	public String findHouseSmartlock(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			MSmartLockDto smartLockDto = JsonEntityTransform.json2Entity(paramJson, MSmartLockDto.class);
			if(Check.NuNStr(smartLockDto.getServiceId())){
				dto = validateSmartOper(smartLockDto);
			}
			if(dto.getCode() == DataTransferObject.ERROR){
				return dto.toJsonString();
			}
			List<HouseSmartlockEntity> list = houseSmartlockServiceImpl.findHouseSmartlock(smartLockDto);
			dto.putValue("list", list);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 智能锁操作校验
	 *
	 * @author jixd
	 * @created 2016年6月24日 下午1:30:44
	 *
	 * @param smartLockDto
	 * @return
	 */
	private DataTransferObject validateSmartOper(MSmartLockDto smartLockDto){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(smartLockDto)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			return dto;
		}
		String landlordUid = smartLockDto.getLandlordUid();
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房东uid为空");
			return dto;
		}
		if(Check.NuNStr(smartLockDto.getHouseBaseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源fid为空");
			return dto;
		}

		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(smartLockDto.getHouseBaseFid());
		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源不存在");
			return dto;
		}
		if(!landlordUid.equals(houseBaseMsgEntity.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("没有权限");
			return dto;
		}
		return dto;
	}

	public String bindSmartLock(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			SmartLockDto smartLockDto = JsonEntityTransform.json2Object(paramJson, SmartLockDto.class);
			if (Check.NuNStr(smartLockDto.getHouseFid()) && Check.NuNCollection(smartLockDto.getRoomFidList())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSEFID_AND_ROOMFID_LIST_NULL));
				LogUtil.info(LOGGER, "error:{}", dto.toJsonString());
				return dto.toJsonString();
			}
			houseManageServiceImpl.bindSmartLock(smartLockDto);
		} catch (BusinessException e) {
			LogUtil.info(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}

		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();

	}



	/**
	 * 
	 * 房源是否有智能锁
	 *
	 * @author liujun
	 * @created 2016年6月24日
	 *
	 * @param roomSn
	 * @return
	 */
	@Override
	public String isHasSmartLock(String houseBaseFid){
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源fid不存在");

			return dto.toJsonString();
		}
		Boolean flag = this.houseManageServiceImpl.isHasSmartLock(houseBaseFid);
		if(flag){
			dto.putValue("result", 1);
		}else{
			dto.putValue("result", 2);
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 房源房间数目总和
	 *
	 * @author liyingjie
	 * @created 2016年6月24日
	 *
	 * @param uid
	 * @return
	 */
	@Override
	public String countHouseRoomNum(String uid){
		LogUtil.info(LOGGER, "参数:uid={}", uid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户uid为空");
			return dto.toJsonString();
		}
		long result = houseManageServiceImpl.countHouseRoomNum(uid);

		dto.putValue("houseRoomNum", result);
		return dto.toJsonString();
	}




	@Override
	public String findHouseBaseExtListByCondition(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseExtRequest request = JsonEntityTransform.json2Object(paramJson, HouseBaseExtRequest.class);
			if (Check.NuNObj(request) || (Check.NuNStr(request.getBuildingNum()) && Check.NuNStr(request.getUnitNum())
					&& Check.NuNStr(request.getFloorNum()) && Check.NuNStr(request.getHouseNum()))) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
				LogUtil.info(LOGGER, "error:{}", dto.toJsonString());
				return dto.toJsonString();
			}
			PagingResult<HouseBaseExtEntity> pagingResult = houseManageServiceImpl
					.findHouseBaseExtListByCondition(request);
			dto.putValue("rows", pagingResult.getRows());
			dto.putValue("total", pagingResult.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseManageService#findPicListByHouseAndRoomFid(java.lang.String)
	 */
	@Override
	public String findPicListByHouseAndRoomFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		JSONObject jsonObj = JSONObject.parseObject(paramJson);
		String fid = jsonObj.getString("fid");
		Integer rentWay = jsonObj.getInteger("rentWay");
		if(Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("FID为空");
			return dto.toJsonString();
		}
		if(Check.NuNObj(rentWay)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租方式为空");
			return dto.toJsonString();
		}
		List<HousePicMsgEntity> list = houseManageServiceImpl.findPicListByHouseAndRoomFid(fid, rentWay);
		dto.putValue("list", list);
		return dto.toJsonString();
	}


	/**
	 * 
	 * 分租发布点击下一步： 更新房源步骤
	 *
	 * @author yd
	 * @created 2016年9月8日 上午10:30:05
	 *
	 * @param houseFid
	 * @return
	 */
	@Override
	public String updateHouseBaseOpSeq(String houseBase) {
		LogUtil.info(LOGGER, "参数:{}", houseBase);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseMsgEntity houseBaseMsg=JsonEntityTransform.json2Object(houseBase, HouseBaseMsgEntity.class);
			// 判断房源逻辑id是否为空
			if (Check.NuNStr(houseBaseMsg.getFid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			HouseBaseMsgEntity houseBaseMsgEntity = this.houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseMsg.getFid());

			if(Check.NuNObj(houseBaseMsgEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg("房源不存在");
				return dto.toJsonString();
			}
			if(houseBaseMsgEntity.getHouseStatus() != HouseStatusEnum.DFB.getCode()&&houseBaseMsgEntity.getOperateSeq() == HouseIssueStepEnum.FOUR.getCode()){
				LogUtil.info(LOGGER, "方法：updateHouseBaseOpSeq当前房源信息houseBaseMsgEntity={}", houseBaseMsgEntity.toJsonStr());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg("非法更新");
				return dto.toJsonString();
			}
			houseBaseMsg.setOperateSeq(HouseIssueStepEnum.FIVE.getCode());
			houseBaseMsg.setIntactRate(HouseIssueStepEnum.FIVE.getValue());
			houseBaseMsg.setLastModifyDate(new Date());
			houseBaseMsg.setFid(houseBaseMsgEntity.getFid());
			int upNum=houseManageServiceImpl.updateHouseBaseMsg(houseBaseMsg);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 根据房源fid 和房间fid查询周末价格
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findWeekPriceByFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		if (Check.NuNStr(paramJson)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		JSONObject param = JSONObject.parseObject(paramJson);
		String houseFid = param.getString("houseFid");
		String roomFid = param.getString("roomFid");
		if (Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源fid为空");
			return dto.toJsonString();
		}
		List<HousePriceWeekConfEntity> weekPriceList = houseManageServiceImpl.findWeekPriceByFid(houseFid, roomFid);
		dto.putValue("list",weekPriceList);
		return dto.toJsonString();
	}

	@Override
	public String getLastModifyCalendarDate(String landlordUid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(landlordUid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}

		Date date=houseManageServiceImpl.getLastModifyCalendarDate(landlordUid);

		dto.putValue("result",date);
		return dto.toJsonString();
	}

	/**
	 * 更新房源周末价格信息列表
	 * 仅限于priceVal isDel isValid字段
	 */
	@Override
	public String updateHousePriceWeekListByFid(String paramJson) {
		LogUtil.debug(LOGGER, "updateHousePriceWeekConfByFid param:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		List<HousePriceWeekConfEntity> weekendPriceList = JsonEntityTransform.json2List(paramJson, HousePriceWeekConfEntity.class);

		if (Check.NuNCollection(weekendPriceList)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();

		}

		for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
			if (Check.NuNStr(housePriceWeekConf.getFid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			if (Check.NuNObj(housePriceWeekConf.getRentWay())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
		}
		try {
			int upNum = houseManageServiceImpl.updateHousePriceWeekListByFid(weekendPriceList);
			dto.putValue("upNum", upNum);

			if(!Check.NuNCollection(weekendPriceList) && !Check.NuNObj(weekendPriceList.get(0)) && !Check.NuNStr(weekendPriceList.get(0).getHouseBaseFid())){
				LogUtil.info(LOGGER, "周末价格修改成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(weekendPriceList.get(0).getHouseBaseFid(), 
						weekendPriceList.get(0).getRoomFid(), weekendPriceList.get(0).getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "周末价格修改成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

}
