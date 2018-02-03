package com.ziroom.minsu.services.house.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.constant.CatConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.house.issue.vo.HouseHallVo;
import com.ziroom.minsu.services.house.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.logic.ValidateResult;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;
import com.ziroom.minsu.services.house.service.TenantHouseServiceImpl;
import com.ziroom.minsu.services.house.service.TroyHouseMgtServiceImpl;
import com.ziroom.minsu.services.house.utils.HouseUtils;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.*;
import com.ziroom.minsu.valenum.houseaudit.HouseAuditCauseEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum008Enum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>房东端-房源发布操作接口proxy</p>
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
@Component("house.houseIssueServiceProxy")
public class HouseIssueServiceProxy implements HouseIssueService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseIssueServiceProxy.class);
	@Resource(name="house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;

	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;

	@Resource(name = "house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;

	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name="house.messageSource")
	private MessageSource messageSource;

	@Resource(name="house.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;

	@Resource(name="house.queueName")
	private QueueName queueName ;

	@Resource(name="house.tenantHouseServiceImpl")
	private TenantHouseServiceImpl tenantHouseServiceImpl;
	
	@Autowired
	private RedisOperations redisOperations;

	/**saveHouseConfList
	 * 为灵活定价，长期租住优惠插入记录
	 * updateHouseConfList
	 * 新灵活定价以及长期租住优惠
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/5 17:51
	 */
	@Override
	public String saveHouseConfList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			List<HouseConfMsgEntity> confList = JsonEntityTransform.json2ObjectList(paramJson, HouseConfMsgEntity.class);
			if(Check.NuNCollection(confList)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("没有传入参数");
				return dto.toJsonString();
			}else{
				//查询数据库是否已经有数据
				HouseConfMsgEntity confMsgEntity = confList.get(0);
				HouseConfMsgEntity conf = new HouseConfMsgEntity();
				conf.setHouseBaseFid(confMsgEntity.getHouseBaseFid());
				if(!Check.NuNStrStrict(confMsgEntity.getRoomFid())){
					conf.setRoomFid(confMsgEntity.getRoomFid());
				}
				conf.setDicCode(ProductRulesEnum020.ProductRulesEnum020001.getParentValue());
				List<HouseConfMsgEntity> gapList = houseIssueServiceImpl.findGapAndFlexPrice(conf);
				conf.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getParentValue());
				List<HouseConfMsgEntity> disRateList = houseIssueServiceImpl.findGapAndFlexPrice(conf);
				if(!Check.NuNCollection(disRateList) && !Check.NuNCollection(gapList)){
					return dto.toJsonString();
				}
			}
			houseIssueServiceImpl.saveHouseConfList(confList);
		}catch (Exception e){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
		}
		return dto.toJsonString();
	}

	@Override
	public String saveOrUpHouseConf(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			if(!Check.NuNObj(paramJson)){
				SpecialPriceDto sp = JsonEntityTransform.json2Object(paramJson, SpecialPriceDto.class);
				if(Check.NuNObjs(sp.getSevenFid(),sp.getThirtyFid())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("更新传入参数错误");
					return dto.toJsonString();
				}
				//折扣率更新设置   is_del 0表示开    2表示关
				Integer disRateSwitch = sp.getFullDayRate()==0? IsDelEnum.PRIORITY.getCode():IsDelEnum.NOT_DEL.getCode();
				List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();
				HouseConfMsgEntity sevenEntity = new HouseConfMsgEntity();
				sevenEntity.setFid(sp.getSevenFid());
				sevenEntity.setIsDel(disRateSwitch);
				if(!Check.NuNObj(sp.getSevenDiscountRate()) && !Check.NuNStrStrict(String.valueOf(sp.getSevenDiscountRate()))){
					sevenEntity.setDicVal(String.valueOf(sp.getSevenDiscountRate()));
				}else{
					sevenEntity.setDicVal(String.valueOf(-1));
				}
				confList.add(sevenEntity);

				HouseConfMsgEntity thirtyEntity = new HouseConfMsgEntity();
				thirtyEntity.setFid(sp.getThirtyFid());
				thirtyEntity.setIsDel(disRateSwitch);
				if(!Check.NuNObj(sp.getThirtyDiscountRate()) && !Check.NuNStrStrict(String.valueOf(sp.getThirtyDiscountRate()))){
					thirtyEntity.setDicVal(String.valueOf(sp.getThirtyDiscountRate()));
				}else{
					thirtyEntity.setDicVal(String.valueOf(-1));
				}
				confList.add(thirtyEntity);
				//更新折扣率
				for(HouseConfMsgEntity conf : confList){
					houseIssueServiceImpl.updateHouseConfdicvalByfid(conf);
				}
				if(!Check.NuNCollection(confList)&&!Check.NuNObj(confList.get(0))&&!Check.NuNStr(confList.get(0).getHouseBaseFid())){
					LogUtil.info(LOGGER, "满天折扣率修改成功,推送队列消息开始...");
					String pushContent = JsonEntityTransform.Object2Json(new HouseMq(confList.get(0).getHouseBaseFid(),
							confList.get(0).getRoomFid(), null, null, null));
					// 推送队列消息
					rabbitMqSendClient.sendQueue(queueName, pushContent);
					LogUtil.info(LOGGER, "满天折扣率修改成功,推送队列消息结束,推送内容:{}", pushContent);
				}
			}else{
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			}
		}catch (Exception e){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
		}

		return dto.toJsonString();
	}

	@Override
	public String updateHouseConfList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			List<HouseConfMsgEntity> list=JsonEntityTransform.json2List(paramJson, HouseConfMsgEntity.class);
			houseIssueServiceImpl.updateHouseConfList(list);
			if(!Check.NuNCollection(list) && !Check.NuNObj(list.get(0)) && !Check.NuNStr(list.get(0).getHouseBaseFid())){
				LogUtil.info(LOGGER, "灵活定价修改成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(list.get(0).getHouseBaseFid(),
						list.get(0).getRoomFid(), null, null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "灵活定价修改成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		}catch (Exception e){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
		}
		return dto.toJsonString();
	}

	@Override
	public String mergeHouseRoomMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseRoomMsgDto houseRoomMsg = JsonEntityTransform.json2Object(paramJson, HouseRoomMsgDto.class);
		DataTransferObject dto = new DataTransferObject();
		// 校验房间是否为空
		if (Check.NuNObj(houseRoomMsg)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房源逻辑id不能为空
		if (Check.NuNStr(houseRoomMsg.getHouseBaseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 新增校验操作步骤不能为空
		//		if (Check.NuNStr(houseRoomMsg.getFid()) && Check.NuNObj(houseRoomMsg.getOperateSeq())) {
		//			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
		//			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATE_SEQ_NULL));
		//			LogUtil.error(LOGGER, dto.toJsonString());
		//			return dto.toJsonString();
		//		}

		// 新增校验信息完整率不能为空
		//		if (Check.NuNStr(houseRoomMsg.getFid()) && Check.NuNObj(houseRoomMsg.getIntactRate())) {
		//			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
		//			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_INTACTRATE_NULL));
		//			LogUtil.error(LOGGER, dto.toJsonString());
		//			return dto.toJsonString();
		//		}


		try {
			HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseRoomMsg
					.getHouseBaseFid());
			if(Check.NuNObj(houseBaseMsg)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 首先判断是否可以录入(已录入房间数量<房源卧室数量)
			long roomCount = houseIssueServiceImpl.getHouseRoomCount(houseRoomMsg.getHouseBaseFid());
			if(roomCount >= houseBaseMsg.getRoomNum()){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NUM_OVER_LIMIT));
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			houseIssueServiceImpl.mergeHouseRoomMsg(houseRoomMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 校验当前room是否存在
	 * 如果不存在直接插入
	 * @author afi
	 * @param paramJson
	 * @return
	 */
	@Override
	@Deprecated
	public String mergeCheckHouseRoomMsg(String paramJson,String uid) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseRoomMsgDto houseRoomMsg = JsonEntityTransform.json2Object(paramJson, HouseRoomMsgDto.class);
		DataTransferObject dto = new DataTransferObject();
		// 校验房间是否为空
		if (Check.NuNObj(houseRoomMsg)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房源逻辑id不能为空
		if (Check.NuNStr(houseRoomMsg.getHouseBaseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseRoomMsg
					.getHouseBaseFid());
			if(Check.NuNObj(houseBaseMsg)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			HousePhyMsgEntity housePhyMsgEntity = houseIssueServiceImpl.findHousePhyMsgByHouseBaseFid(houseBaseMsg.getFid());
			if(Check.NuNStr(uid) || !uid.equals(houseBaseMsg.getLandlordUid())){
				//UID 为空 或者 不是当前房东
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("无操作权限");
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			HouseRoomMsgEntity room = null;
			if(!Check.NuNStr(houseRoomMsg.getFid())){
				room = houseManageServiceImpl.getHouseRoomByFid(houseRoomMsg.getFid());
			}

			//当前房间不存在，需要校验当前房间的数量
			if(Check.NuNObj(room)){
				// 首先判断是否可以录入(已录入房间数量<房源卧室数量)
				long roomCount = houseIssueServiceImpl.getHouseRoomCount(houseRoomMsg.getHouseBaseFid());
				if(roomCount >= houseBaseMsg.getRoomNum()){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NUM_OVER_LIMIT));
					LogUtil.info(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
			}
			if(houseBaseMsg.getRentWay()==RentWayEnum.HOUSE.getCode()){
				long defaultCount = houseIssueServiceImpl.getHouseDefaultRoomCount(houseRoomMsg.getHouseBaseFid());
				if(defaultCount == 0){
					//如果当前的默认房间数量为0 那么当前房间就是默认房间，否则当前房间不是默认房间
					houseRoomMsg.setIsDefault(YesOrNoEnum.YES.getCode());
				} else if (defaultCount == 1 && !Check.NuNObj(room) && !Check.NuNObj(room.getIsDefault()) && room.getIsDefault() == YesOrNoEnum.YES.getCode()) {
					houseRoomMsg.setIsDefault(YesOrNoEnum.YES.getCode());
				} else {
					houseRoomMsg.setIsDefault(YesOrNoEnum.NO.getCode());
				}
				//入住人数限制
				houseRoomMsg.setCheckInLimit(0);
				//整租只有一个默认房间
				if(Check.NuNObj(houseRoomMsg.getRoomPrice())){
					houseRoomMsg.setRoomPrice(0);
				}
				//如果是整租，房间状态和房源状态一致
				houseRoomMsg.setRoomStatus(houseBaseMsg.getHouseStatus());
			}else {
				//如果是空房间则设置待发布状态
				if(Check.NuNObj(room)){
					houseRoomMsg.setRoomStatus(HouseStatusEnum.DFB.getCode());
				}
				//所有的合租都是默认房间
				houseRoomMsg.setIsDefault(YesOrNoEnum.YES.getCode());
				//分租的价格必须为空
				if(Check.NuNObj(houseRoomMsg.getRoomPrice())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PRICE_SETTIME_NULL));
					LogUtil.error(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
			}
			//houseRoomMsg.setRoomStatus(houseBaseMsg.getHouseStatus());
			if(!Check.NuNObj(housePhyMsgEntity)){

				String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);

				if(!Check.NuNStr(roomSn)){

					int i = 0;
					while (i<3) {
						Long count = this.houseManageServiceImpl.countByRoomSn(roomSn);
						if(count>0){
							i++;
							roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);
							continue;
						}
						break;
					}
				}
				houseRoomMsg.setRoomSn(roomSn);
			}
			houseIssueServiceImpl.mergeCheckHouseRoomMsg(houseRoomMsg);

			if(!Check.NuNStr(houseBaseMsg.getFid())){
				//通知mq 更新房源索引信息 不区别当前房源的状态
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsg.getFid(), null,
						houseBaseMsg.getRentWay(),null,null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息结束,推送内容:{}", pushContent);
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
	@Deprecated
	public String mergeHouseBedMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBedMsgEntity houseBedMsg = JsonEntityTransform.json2Object(paramJson, HouseBedMsgEntity.class);
		DataTransferObject dto = new DataTransferObject();
		// 校验床位是否为空
		if(Check.NuNObj(houseBedMsg)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BED_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房源逻辑id不能为空
		if(Check.NuNStr(houseBedMsg.getHouseBaseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房间逻辑id不能为空
		if(Check.NuNStr(houseBedMsg.getRoomFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			houseIssueServiceImpl.mergeCheckHouseBedMsg(houseBedMsg,dto);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHouseRoomMsgByFid(String houseRoomFid) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={}", houseRoomFid);
		DataTransferObject dto = new DataTransferObject();
		// 校验房间逻辑id不能为空
		if(Check.NuNStr(houseRoomFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			HouseRoomMsgEntity houseRoomMsg = houseIssueServiceImpl.findHouseRoomMsgByFid(houseRoomFid);
			dto.putValue("obj", houseRoomMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHouseBedMsgByFid(String houseBedFid) {
		LogUtil.info(LOGGER, "参数:houseBedFid={}", houseBedFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseBedFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BED_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseBedMsgEntity houseBedMsg = houseIssueServiceImpl.findHouseBedMsgByFid(houseBedFid);
			dto.putValue("obj", houseBedMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 通过roomFid 获取当前的床数量
	 * @param houseRoomFid
	 * @return
	 */
	@Override
	public String countBedByRoomFid(String houseRoomFid){
		LogUtil.info(LOGGER, "参数:houseRoomFid={}", houseRoomFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseRoomFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			Long count = houseIssueServiceImpl.countBedByRoomFid(houseRoomFid);
			dto.putValue("count", count);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	@Override
	public String searchBedListByRoomFid(String houseRoomFid) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={}", houseRoomFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseRoomFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			List<HouseBedMsgEntity> bedList = houseIssueServiceImpl.findBedListByRoomFid(houseRoomFid);
			dto.putValue("list", bedList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchRoomListByHouseBaseFid(String houseBaseFid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseBaseFid);
			dto.putValue("list", roomList);
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
	 * 根据roomFid 查询房间
	 *
	 * @author yd
	 * @created 2017年9月22日 下午4:40:42
	 *
	 * @param roomFid
	 * @return
	 */
	@Override
	public String searchRoomByRoomFid(String roomFid) {
		LogUtil.info(LOGGER, "参数:roomFid={}", roomFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(roomFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseRoomMsgEntity  houseRoomMsg = houseIssueServiceImpl.findHouseRoomMsgByFid(roomFid);
			dto.putValue("houseRoomMsg", houseRoomMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	@Override
	public String searchHouseBaseMsgByFid(String houseBaseFid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
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
	public String mergeHouseBaseExtAndHouseConfList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBaseExtVo houseBaseExt = JsonEntityTransform.json2Object(paramJson, HouseBaseExtVo.class);
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseExt.getHouseBaseFid());
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(houseBaseExt)) {//
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_EXT_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		if (Check.NuNStr(houseBaseExt.getHouseBaseFid())) {//
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		int dbOperateSeq = houseBaseMsgEntity.getOperateSeq();
		if(houseBaseMsgEntity.getHouseStatus().intValue() == HouseStatusEnum.YFB.getCode()
				&&(dbOperateSeq==HouseIssueStepEnum.SIX.getCode())){
			houseBaseExt.setOperateSeq(HouseIssueStepEnum.SEVEN.getCode());
		}
		try {
			houseIssueServiceImpl.mergeHouseBaseExtAndHouseConfList(houseBaseExt);

			if(!Check.NuNStr(houseBaseExt.getHouseBaseFid())){
				LogUtil.info(LOGGER, "修改房源扩展及配置信息,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseExt.getHouseBaseFid(), null,
						null, null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "修改房源扩展及配置信息,推送队列消息结束,推送内容:{}", pushContent);
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
	public String searchHouseBaseExtAndHouseConfList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBaseExtVo houseBaseExt = JsonEntityTransform.json2Object(paramJson, HouseBaseExtVo.class);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(houseBaseExt)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_EXT_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		if(Check.NuNStr(houseBaseExt.getHouseBaseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseBaseExtVo baseExtVo = houseIssueServiceImpl.findHouseBaseExtAndHouseConfList(houseBaseExt);
			dto.putValue("obj", baseExtVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHouseBaseMsgAndHouseDesc(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		//1 参数校验
		ValidateResult<HouseBaseMsgDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseBaseMsgDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseBaseMsgDto houseBaseMsg = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseBaseMsg.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			houseIssueServiceImpl.updateHouseBaseMsgAndHouseDesc(houseBaseMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHouseBaseMsgAndMergeHouseDesc(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		//1 参数校验
		ValidateResult<HouseBaseMsgDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseBaseMsgDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseBaseMsgDto houseBaseMsg = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();
		// 校验房源逻辑id不能为空
		if (Check.NuNStr(houseBaseMsg.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 新增房源描述不能为空
		if (Check.NuNStr(houseBaseMsg.getHouseDescFid()) && Check.NuNStr(houseBaseMsg.getHouseDesc())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DESC_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验操作步骤不能为空
		if (Check.NuNObj(houseBaseMsg.getOperateSeq())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATE_SEQ_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验信息完整率不能为空
		if (Check.NuNObj(houseBaseMsg.getIntactRate())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_INTACTRATE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			houseIssueServiceImpl.updateHouseBaseMsgAndMergeHouseDesc(houseBaseMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHouseBaseMsgAndHouseDesc(String houseBaseFid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseBaseMsgDto houseBaseMsgDto = houseIssueServiceImpl.findHouseBaseMsgAndHouseDesc(houseBaseFid);
			dto.putValue("obj", houseBaseMsgDto);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String newSaveHousePicMsgList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HousePicDto housePicDto = JsonEntityTransform.json2Object(paramJson, HousePicDto.class);
		List<HousePicMsgEntity> picList=housePicDto.getPicList();
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNCollection(picList)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			Integer picTypeInt = picList.get(0).getPicType();
			if (Check.NuNObj(picTypeInt)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("图片类型不能为空");
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			//图片数量是否超过最大限制
			int count = houseIssueServiceImpl.getHousePicCount(picList.get(0).getHouseBaseFid(), picList.get(0)
					.getRoomFid(), picTypeInt);

			HousePicTypeEnum enumeration = HousePicTypeEnum.getEnumByCode(picTypeInt);
			if (Check.NuNObj(enumeration)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片类型错误");
				return dto.toJsonString();
			}

			if ((count + picList.size()) > enumeration.getMax()) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PIC_TYPE_OVER_LIMIT));
				return dto.toJsonString();
			}
			/***************之前m站的首张照片设置为默认照片*****************/
			/*
			//查询默认图片
			String defaultPicFid=null;
			HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(housePicDto.getHouseBaseFid());
			//新默认图片规则
			if(RentWayEnum.HOUSE.getCode()==houseBaseExtDto.getRentWay()){
				defaultPicFid=houseBaseExtDto.getHouseBaseExt().getDefaultPicFid();
			} else if (RentWayEnum.ROOM.getCode()==houseBaseExtDto.getRentWay()){
				HouseRoomMsgEntity houseRoomMsgEntity=houseIssueServiceImpl.findHouseRoomMsgByFid(housePicDto.getHouseRoomFid());
				if(!Check.NuNObj(houseRoomMsgEntity)){
					defaultPicFid=houseRoomMsgEntity.getDefaultPicFid();
				}
			}
			boolean hasDefault = true;//是否存在默认图片
			if(Check.NuNObj(defaultPicFid)){
				hasDefault = false;
			}*/
			for (HousePicMsgEntity housePicMsgEntity : picList) {
				/*
				if(!hasDefault){
					hasDefault = true;
					//设置默认图片
					if (RentWayEnum.HOUSE.getCode()==houseBaseExtDto.getRentWay()) {
						HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
						houseBaseExtEntity.setDefaultPicFid(housePicMsgEntity.getFid());
						houseBaseExtEntity.setHouseBaseFid(housePicMsgEntity.getHouseBaseFid());
						troyHouseMgtServiceImpl.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
					} else if (RentWayEnum.ROOM.getCode()==houseBaseExtDto.getRentWay()) {
						HouseRoomMsgEntity houseRoomMsgEntity=new HouseRoomMsgEntity();
						houseRoomMsgEntity.setFid(housePicDto.getHouseRoomFid());
						houseRoomMsgEntity.setDefaultPicFid(housePicMsgEntity.getFid());
						houseIssueServiceImpl.updateHouseRoomMsg(houseRoomMsgEntity);
					}
				}*/
				/***************之前m站的首张照片设置为默认照片*****************/
				houseIssueServiceImpl.saveHousePicMsg(housePicMsgEntity);
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
	public String deleteCheckHouseRoomMsgByFid(String houseRoomFid) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={}", houseRoomFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseRoomFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByRoomFid(houseRoomFid);
		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, "删除房间，当前房间不存在：par：{}",dto.toJsonString());
		}
		if(houseBaseMsgEntity.getRentWay() == RentWayEnum.HOUSE.getCode()){
			//删除整租
			delRoomForZ(dto,houseBaseMsgEntity.getFid(),houseRoomFid);
		}else {
			//删除合租
			delRoomForH(dto, houseRoomFid);
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 删除和租的房间
	 * @author afi
	 * @param dto
	 * @param houseRoomFid
	 */
	private void delRoomForH(DataTransferObject dto,String houseRoomFid){
		if(dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		//获取当前房间的兄弟房间的数量
		Long broCount = houseIssueServiceImpl.getHouseDefaultRoomCountByRoomFid(houseRoomFid);
		if(broCount == 0 ){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
			LogUtil.error(LOGGER, "删除房间，当前房间不存在：par：{}",dto.toJsonString());
			return ;
		}else if(broCount == 1){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("至少包含一个房间");
			LogUtil.error(LOGGER, "删除房间，只有一个房间不能删除：par：{}",dto.toJsonString());
			return ;
		}
		try {
			houseIssueServiceImpl.deleteHouseRoomMsgByFid(houseRoomFid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
	}


	/**
	 * 删除整租的房间
	 * @author afi
	 * @param dto
	 * @param houseRoomFid
	 */
	private void delRoomForZ(DataTransferObject dto,String houseFid,String houseRoomFid){
		if(dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		try {
			//获取当前房间信息
			HouseRoomListVo current = null;
			List<HouseRoomListVo> rooms = houseManageServiceImpl.getRoomListByHouseFid(houseFid);
			for(HouseRoomListVo room: rooms){
				if(room.getFid().equals(houseRoomFid)){
					//获取当前房间信息
					current = room;
					break;
				}
			}
			if(Check.NuNObj(current)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前房间不存在");
				return;
			}
			//兄弟非默认房间
			HouseRoomListVo brother = null;
			if(current.getIsDefault() == YesOrNoEnum.NO.getCode()){
				//当前房间不是默认房间 直接删除
				houseIssueServiceImpl.deleteHouseRoomMsgByFid(houseRoomFid);
			}else {
				//当前房间是默认房间
				if(rooms.size() == 1){
					//1. 校验当前的房间数量只有当前房间
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("至少包含一个房间");
					return;
				}else {
					//2. 当前的房间还有别的房间
					for(HouseRoomListVo room: rooms){
						if(!room.getFid().equals(houseRoomFid)){
							//获取当前房间信息
							brother = room;
							break;
						}
					}
					//3. 将当前的房间删除 并将兄弟房间设置为默认房间
					houseIssueServiceImpl.deleteHouseRoomMsgByFidAndDefaultNext(houseRoomFid,brother.getFid());
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
	}

	@Override
	public String deleteHouseRoomMsgByFid(String houseRoomFid) {
		LogUtil.info(LOGGER, "参数:houseRoomFid={}", houseRoomFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseRoomFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseRoomMsgEntity houseRoomMsgEntity = houseManageServiceImpl.getHouseRoomByFid(houseRoomFid);
			if (houseRoomMsgEntity.getRoomStatus() == HouseStatusEnum.SJ.getCode()
					|| houseRoomMsgEntity.getRoomStatus() == HouseStatusEnum.YFB.getCode()){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("非法状态，不能删除");
				return dto.toJsonString();
			}
			houseIssueServiceImpl.deleteHouseRoomMsgByFid(houseRoomFid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String deleteHouseBedMsgByFid(String houseBedFid) {
		LogUtil.info(LOGGER, "参数:houseBedFid={}", houseBedFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseBedFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BED_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			houseIssueServiceImpl.deleteHouseBedMsgByFid(houseBedFid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * @Description: app删除房源图片
	 * 1.分租只有首次发布房源在第五步上传房源图片时删除图片不用传roomFid,其他情况必须传，这个要校验
	 * 2.获取房源或房间的status
	 * 3.已发布，也就是待审核的房源或房间的图片是不能删除的
	 * 4.封面照片不能删除
	 * 5.上架情况下：
	 * ①需要区域图片最低数量校验，达到临界值不能删除
	 * ②房间图片总数校验：整租判断该房源下的图片总数不能小于发布总数限制，达到临界值不能删除
	 * ③房间图片总数校验：分租判断该房源下所有上架房间的图片总数不能小于发布总数限制，达到临界值不能删除
	 *
	 * @Author: lusp
	 * @Date: 2017/7/15 12:40
	 * @Params: paramJson
	 */
	@Override
	public String deleteHousePicMsgByFid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HousePicBaseParamsDto> validateResult =paramCheckLogic.checkParamValidate(paramJson, HousePicBaseParamsDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		try {
			HousePicBaseParamsDto housePicBaseParamsDto = validateResult.getResultObj();

			String houseBaseFid = housePicBaseParamsDto.getHouseBaseFid();
			String roomFid = housePicBaseParamsDto.getHouseRoomFid();
			String housePicFid= housePicBaseParamsDto.getHousePicFid();
			Integer picType = housePicBaseParamsDto.getPicType();

			/********************校验图片类型*******************/
			if(Check.NuNObj(picType)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("图片类型不能为空");
				return dto.toJsonString();
			}
			HousePicTypeEnum enumeration = HousePicTypeEnum.getEnumByCode(picType.intValue());
			if(Check.NuNObj(enumeration)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("图片类型错误");
				return dto.toJsonString();
			}
			/********************校验图片类型*******************/

			HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(houseBaseFid);
			if(Check.NuNObj(houseBaseExtDto)){
				LogUtil.error(LOGGER, "deleteHousePicMsgByFid(),当前房源信息不存在,houseBaseFid={}",houseBaseFid);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("该房源信息异常!");
				return dto.toJsonString();
			}
			Integer rentWay = houseBaseExtDto.getRentWay();

			int status = -1;
			HouseRoomMsgEntity houseRoomMsgEntity = null;
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				/************待审核状态中的房源的图片不能删除(troy后台不受此限制)************/
				if(housePicBaseParamsDto.getPicSource()==null||housePicBaseParamsDto.getPicSource()!=1){
					status = houseBaseExtDto.getHouseStatus();
					if(status == HouseStatusEnum.YFB.getCode()){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("审核中的房源照片不能删除");
						return dto.toJsonString();
					}
				}
				/************待审核状态中的房源的图片不能删除************/

				/***********整租校验是不是封面照片，如果是，不能删除***********/
				if(Check.NuNObj(houseBaseExtDto.getHouseBaseExt())){
					LogUtil.error(LOGGER, "deleteHousePicMsgByFid(),当前房源扩展信息不存在,houseBaseFid={}",houseBaseFid);
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("该房源信息异常!");
					return dto.toJsonString();
				}
				String houseDefaultPicFid = houseBaseExtDto.getHouseBaseExt().getDefaultPicFid();
				if(housePicFid.equals(houseDefaultPicFid)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("封面照片不允许删除");
					return dto.toJsonString();
				}
				/***********整租校验是不是封面照片，如果是，不能删除***********/

				/*
				 * 上架情况下，
				 * ①需要区域图片最低数量校验、
				 * ②房间图片总数校验：整租判断该房源下的图片总数不能小于发布总数限制
				 * ③房间图片总数校验：分租判断该房源下所有上架房间的图片总数不能小于发布总数限制
				 * */
				if(status == HouseStatusEnum.SJ.getCode()){
					/**当客厅为零并且删除客厅照片时（troy会出现这种情况），
					 * 不进行总数校验和区域最低数校验，下面是这个条件取反，进行区域最低数和总数校验
					 */
					if (picType.intValue() != HousePicTypeEnum.KT.getCode()||houseBaseExtDto.getHallNum() != 0){
						int count = 0;
						/*****************区域图片最低数量校验*****************/
						if(HousePicTypeEnum.WS.getCode()!=picType.intValue()){
							count=houseIssueServiceImpl.getHousePicCount(houseBaseFid,null, picType);
						} else {
							if(Check.NuNStrStrict(roomFid)){
								LogUtil.error(LOGGER, "deleteHousePicMsgByFid(),前端删除上架中的整租房源的卧室图片没传roomFid,paramJson={}",housePicBaseParamsDto);
								roomFid = houseManageServiceImpl.getRoomFidByPicFid(housePicFid);
							}
							if(Check.NuNStrStrict(roomFid)){
								dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
								dto.setMsg("房间信息异常");
								LogUtil.error(LOGGER, "deleteHousePicMsgByFid(),该照片fid对应的roomFid 不存在,housePicFid={}",housePicFid);
								return dto.toJsonString();
							}
							count=houseIssueServiceImpl.getHousePicCount(houseBaseFid,roomFid, picType);
						}
						int min = enumeration.getMin();// 上架房源图片最小数量
						if(count<=min){
							dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
							dto.setMsg("需要至少保留"+count+"张照片");
							LogUtil.info(LOGGER, dto.toJsonString());
							return dto.toJsonString();
						}
						/*****************区域图片最低数量校验*****************/

						/*********************图片总数校验********************/
						Map resultMap = this.validatePicTotalNum(houseBaseFid,rentWay,roomFid);
						Boolean totalFlag = (Boolean)resultMap.get("flag");
						if(!totalFlag){
							Integer num = (Integer) resultMap.get("num");
							dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
							dto.setMsg("照片总数不能少于"+num+"张");
							return dto.toJsonString();
						}
						/*********************图片总数校验********************/
					}
				}
			}else if(rentWay == RentWayEnum.ROOM.getCode()){
				boolean hasYFB = false;
				boolean hasSJ = false;
				List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseBaseFid);
				// app分租首次待发布时,上传图片页面先上传后删除未传RoomFid,也只有这种情况下RoomFid 可为空
				if (Check.NuNStrStrict(roomFid)) {
					boolean isDFB = true;
					for (HouseRoomMsgEntity houseRoomMsg : roomList) {
						if (houseRoomMsg.getRoomStatus() != HouseStatusEnum.DFB.getCode()) {
							isDFB = false;
							break;
						}
					}
					if (isDFB) {
						status = HouseStatusEnum.DFB.getCode();
					} else {
						LogUtil.error(LOGGER, "非首次发布房源时，分租删除照片没有传houseRoomFid,paramJson={}", paramJson);
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("参数错误");
						return dto.toJsonString();
					}
				} else {
					for (HouseRoomMsgEntity houseRoomMsg : roomList) {
						if (houseRoomMsg.getRoomStatus() == HouseStatusEnum.YFB.getCode()) {
							hasYFB = true;
							break;
						}
					}
					for (HouseRoomMsgEntity houseRoomMsg : roomList) {
						if (houseRoomMsg.getRoomStatus() == HouseStatusEnum.SJ.getCode()) {
							hasSJ = true;
							break;
						}
					}
					houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(roomFid);
					if(Check.NuNObj(houseRoomMsgEntity)){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("当前房间不再存在");
						LogUtil.info(LOGGER, "当前请求参数housePicDto={}，待删除房间不存在",housePicBaseParamsDto.toString());
						return dto.toJsonString();
					}
					//troy 中的审核中的照片可以删除   picSource =1  表示troy
					if(housePicBaseParamsDto.getPicSource()!=1){
						status = houseRoomMsgEntity.getRoomStatus();
						/************待审核状态中的房间的图片不能删除************/
						if(status == HouseStatusEnum.YFB.getCode()){
							dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
							dto.setMsg("待审核的房间照片不允许删除");
							return dto.toJsonString();
						}
						if(hasYFB && picType!=HousePicTypeEnum.WS.getCode()){
							dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
							dto.setMsg("该照片为此房源下其他房间的审核中照片，不允许删除");
							return dto.toJsonString();
						}
						/************待审核状态中的房间的图片不能删除************/
					}
				}

				/***********分租校验是不是封面照片，如果是，不能删除***********/
				List<String> roomFidList = houseManageServiceImpl.getRoomFidListByDefaultPicFid(housePicFid);
				if(!Check.NuNCollection(roomFidList)){
					if(Check.NuNStrStrict(roomFid)){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("封面照片不允许删除");
						return dto.toJsonString();
					}else{
						boolean innerFlag = false;
						for(String s:roomFidList){
							if(roomFid.equals(s)){
								innerFlag = true;
								break;
							}
						}
						if(innerFlag){
							dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
							dto.setMsg("封面照片不允许删除");
							return dto.toJsonString();
						}
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("此照片为其他房间封面照片，不允许删除");
						return dto.toJsonString();
					}
				}
				/***********分租校验是不是封面照片，如果是，不能删除***********/

				/*
				 * 上架情况下，只要该房源下有上架房间都要判断
				 * ①需要区域图片最低数量校验、
				 * ②房间图片总数校验：整租判断该房源下的图片总数不能小于发布总数限制
				 * ③房间图片总数校验：分租判断该房源下所有上架房间的图片总数不能小于发布总数限制
				 * */
				if(hasSJ){
					/**当客厅为零并且删除客厅照片时（troy会出现这种情况），
					 * 不进行总数校验和区域最低数校验，下面是这个条件取反，进行区域最低数和总数校验
					 */
					if (picType.intValue() != HousePicTypeEnum.KT.getCode()||houseBaseExtDto.getHallNum() != 0){
						int count = 0;
						if(picType != HousePicTypeEnum.WS.getCode()||status==HouseStatusEnum.SJ.getCode()){
							/*****************区域图片最低数量校验*****************/
							if(HousePicTypeEnum.WS.getCode()!=picType.intValue()){
								count=houseIssueServiceImpl.getHousePicCount(houseBaseFid,null, picType);
							} else {
								if(Check.NuNStrStrict(roomFid)){
									LogUtil.error(LOGGER, "deleteHousePicMsgByFid(),前端删除含有上架房间的分租房源的卧室图片时没传roomFid,paramJson={}",housePicBaseParamsDto);
									roomFid = houseManageServiceImpl.getRoomFidByPicFid(housePicFid);
								}
								if(Check.NuNStrStrict(roomFid)){
									dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
									dto.setMsg("房间信息异常");
									LogUtil.error(LOGGER, "deleteHousePicMsgByFid(),该照片fid对应的roomFid 不存在,housePicFid={}",housePicFid);
									return dto.toJsonString();
								}
								count=houseIssueServiceImpl.getHousePicCount(houseBaseFid,roomFid, picType);
							}
							int min = enumeration.getMin();// 上架房源图片最小数量
							if(count<=min){
								dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
								dto.setMsg("需要至少保留"+count+"张照片");
								LogUtil.info(LOGGER, dto.toJsonString());
								return dto.toJsonString();
							}
							/*****************区域图片最低数量校验*****************/
						}

						if(!Check.NuNStrStrict(roomFid)){
							/*********************图片总数校验********************/
							if(picType == HousePicTypeEnum.WS.getCode()&&status==HouseStatusEnum.SJ.getCode()){
								Map resultMap = this.validatePicTotalNum(houseBaseFid,rentWay,roomFid);
								Boolean totalFlag = (Boolean)resultMap.get("flag");
								if(!totalFlag){
									Integer num = (Integer) resultMap.get("num");
									dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
									dto.setMsg("照片总数不能少于"+num+"张!");
									return dto.toJsonString();
								}
							}else if(picType != HousePicTypeEnum.WS.getCode()){
								HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
								HousePicDto housePicDto = new HousePicDto();
								housePicDto.setHouseBaseFid(houseBaseFid);
								List<HousePicMsgEntity> housePicMsgList = houseIssueServiceImpl.findHousePicMsgList(housePicDto);
								Integer picTotalMax = HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC;
								//不算的照片数量
								int subNum=0;
								List<HousePicMsgEntity> ktList = new ArrayList<HousePicMsgEntity>();
								List<HousePicMsgEntity> cfList = new ArrayList<HousePicMsgEntity>();
								List<HousePicMsgEntity> wsjList = new ArrayList<HousePicMsgEntity>();
								List<HousePicMsgEntity> swList = new ArrayList<HousePicMsgEntity>();
								for (HousePicMsgEntity housePicMsgEntity : housePicMsgList) {
									Integer picTypeTemp = housePicMsgEntity.getPicType();
									if (!Check.NuNObj(picTypeTemp)) {
										if (picTypeTemp.intValue() == HousePicTypeEnum.WS.getCode()) {
											continue;
										} else if (picTypeTemp.intValue() == HousePicTypeEnum.KT.getCode()) {
											ktList.add(housePicMsgEntity);
										} else if (picTypeTemp.intValue() == HousePicTypeEnum.CF.getCode()) {
											cfList.add(housePicMsgEntity);
										} else if (picTypeTemp.intValue() == HousePicTypeEnum.WSJ.getCode()) {
											wsjList.add(housePicMsgEntity);
										} else if (picTypeTemp.intValue() == HousePicTypeEnum.SW.getCode()) {
											swList.add(housePicMsgEntity);
										}
									}
								}

								int totalPicNumOutWS = 0;// 除了卧室之外的总数
								int ktPicNum = ktList.size();
								//如果客厅为0 不进行判断
								if(houseBaseMsg.getHallNum()!=0){
									totalPicNumOutWS += ktPicNum;
								} else {
									subNum+=HousePicTypeEnum.KT.getMin();
								}
								int cfPicNum = cfList.size();
								//如果厨房为0 不进行判断
								if(houseBaseMsg.getKitchenNum()!=0){
									totalPicNumOutWS += cfPicNum;
								}else{
									subNum+=HousePicTypeEnum.CF.getMin();
								}
								int wsjPicNum = wsjList.size();
								//如果卫生间为0 不进行判断
								if(houseBaseMsg.getToiletNum()!=0){
									totalPicNumOutWS += wsjPicNum;
								} else {
									subNum+=HousePicTypeEnum.WSJ.getMin();
								}
								int swPicNum = swList.size();
								totalPicNumOutWS += swPicNum;
								//1.先判断客厅是否为 0 厅，分为两种
								//2.先根据houseBaseFid 获取房间集合（包括roomStatus）
								//3.遍历每个roomStatus = 上架  的房间，判断上架状态的房间图片总数是否已到临界值
								boolean flag = true;
								Integer tempNum = 0;
								boolean isThisRoom = true;
								List<HouseRoomListVo> roomListVos = houseManageServiceImpl.getRoomListByHouseFid(houseBaseFid);
								for(HouseRoomListVo houseRoomListVo:roomListVos){
									if(houseRoomListVo.getRoomStatus() != HouseStatusEnum.SJ.getCode()){
										continue;
									}
									List<HousePicMsgEntity> wsList = new ArrayList<HousePicMsgEntity>();
									for (HousePicMsgEntity housePicMsgEntity : housePicMsgList) {
										Integer picTypeTemp = housePicMsgEntity.getPicType();
										if (!Check.NuNObj(picTypeTemp)) {
											if (picTypeTemp.intValue() == HousePicTypeEnum.WS.getCode()) {
												if(housePicMsgEntity.getRoomFid().equals(houseRoomListVo.getFid())){
													wsList.add(housePicMsgEntity);
												}else{
													continue;
												}
											}
										}
									}
									if((wsList.size()+totalPicNumOutWS)<=(picTotalMax-subNum)){
										flag = false;
										tempNum = picTotalMax-subNum;
										if(roomFid.equals(houseRoomListVo.getFid())){
											isThisRoom = true;
										}else {
											isThisRoom = false;
										}
										break;
									}
								}
								if(!flag){
									if(isThisRoom){
										dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
										dto.setMsg("总数不能少于"+tempNum+"张");
										return dto.toJsonString();
									}
									dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
									dto.setMsg("该房源下有其他已上架房间,照片总数不能少于"+tempNum+"张");
									return dto.toJsonString();
								}
							}
							/*********************图片总数校验********************/

						}
					}
				}
			}

			houseIssueServiceImpl.deleteHousePicMsgByFid(housePicBaseParamsDto.getHousePicFid());

			if(!Check.NuNStr(housePicBaseParamsDto.getHouseBaseFid())){
				LogUtil.info(LOGGER, "删除图片成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(housePicBaseParamsDto.getHouseBaseFid(),
						housePicBaseParamsDto.getHouseRoomFid(), RentWayEnum.HOUSE.getCode(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "删除图片成功,推送队列消息结束,推送内容:{}", pushContent);
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	private Map validatePicTotalNum(String houseBaseFid,Integer rentWay,String roomFid){
		Map resultMap = new HashMap();
		resultMap.put("flag",true);
		HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
		HousePicDto housePicDto = new HousePicDto();
		housePicDto.setHouseBaseFid(houseBaseFid);
		List<HousePicMsgEntity> housePicMsgList = houseIssueServiceImpl.findHousePicMsgList(housePicDto);
		Integer picTotalMax = HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC;

		//不算的照片数量
		int subNum=0;
		List<HousePicMsgEntity> wsList = new ArrayList<HousePicMsgEntity>();
		List<HousePicMsgEntity> ktList = new ArrayList<HousePicMsgEntity>();
		List<HousePicMsgEntity> cfList = new ArrayList<HousePicMsgEntity>();
		List<HousePicMsgEntity> wsjList = new ArrayList<HousePicMsgEntity>();
		List<HousePicMsgEntity> swList = new ArrayList<HousePicMsgEntity>();
		for (HousePicMsgEntity housePicMsgEntity : housePicMsgList) {
			Integer picType = housePicMsgEntity.getPicType();
			if (!Check.NuNObj(picType)) {
				if (picType.intValue() == HousePicTypeEnum.WS.getCode()) {
					if(rentWay == RentWayEnum.HOUSE.getCode()){
						wsList.add(housePicMsgEntity);
					}else if(rentWay == RentWayEnum.ROOM.getCode()){
						if(housePicMsgEntity.getRoomFid().equals(roomFid)){
							wsList.add(housePicMsgEntity);
						}else{
							continue;
						}
					}
				} else if (picType.intValue() == HousePicTypeEnum.KT.getCode()) {
					ktList.add(housePicMsgEntity);
				} else if (picType.intValue() == HousePicTypeEnum.CF.getCode()) {
					cfList.add(housePicMsgEntity);
				} else if (picType.intValue() == HousePicTypeEnum.WSJ.getCode()) {
					wsjList.add(housePicMsgEntity);
				} else if (picType.intValue() == HousePicTypeEnum.SW.getCode()) {
					swList.add(housePicMsgEntity);
				}
			}
		}

		int totalPicNum = 0;// 整套出租:所有图片 独立房间: 房间图片 + 公区图片
		int ktPicNum = ktList.size();
		//如果客厅为0 不进行判断
		if(houseBaseMsg.getHallNum()!=0){
			totalPicNum += ktPicNum;
		} else {
			subNum+=HousePicTypeEnum.KT.getMin();
		}
		int cfPicNum = cfList.size();
		//如果厨房为0 不进行判断
		if(houseBaseMsg.getKitchenNum()!=0){
			totalPicNum += cfPicNum;
		}else{
			subNum+=HousePicTypeEnum.CF.getMin();
		}
		int wsjPicNum = wsjList.size();
		//如果卫生间为0 不进行判断
		if(houseBaseMsg.getToiletNum()!=0){
			totalPicNum += wsjPicNum;
		} else {
			subNum+=HousePicTypeEnum.WSJ.getMin();
		}
		int swPicNum = swList.size();
		totalPicNum += swPicNum;

		//此时totalPicNum 为除了卧室之外的所有的照片数量
		resultMap.put("swNum",totalPicNum);

		int wsPicNum = wsList.size();
		totalPicNum += wsPicNum;
		if(totalPicNum<=(picTotalMax-subNum)){
			resultMap.put("flag",false);
			resultMap.put("num",(picTotalMax-subNum));
			return resultMap;
		}
		return resultMap;
	}


	/**
	 * 删除所有图片
	 * 1.未上架状态的图片可以删除
	 * 2.已上架状态图片，根据图片类型最后几张不能删除({@link HousePicTypeEnum#getMin()})，默认图片不能删除
	 * 3.分租时，当前照片是不是该房源其他房间的默认照片，如果是不让删除
	 */
	@Override
	public String delAllHousePicMsgByFid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//用于存放符合被删除条件的图片fid的集合
		List<String> picFidList = new ArrayList<String>();
		//1 参数校验
		ValidateResult<HousePicDto> validateResult =paramCheckLogic.checkParamValidate(paramJson, HousePicDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		try {
			HousePicDto housePicDto = validateResult.getResultObj();
			HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(housePicDto.getHouseBaseFid());
			Integer rentWay = houseBaseExtDto.getRentWay();
			//判断房源或者房间状态
			int status = -1;
			HouseRoomMsgEntity houseRoomMsgEntity = null;
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				status = houseBaseExtDto.getHouseStatus();
			}else if(rentWay == RentWayEnum.ROOM.getCode()){
				houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(housePicDto.getHouseRoomFid());
				if(Check.NuNObj(houseRoomMsgEntity)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("当前房间不再存在");
					LogUtil.info(LOGGER, "当前请求参数housePicDto={}，待删除房间不存在",housePicDto.toString());
					return dto.toJsonString();
				}
				status = houseRoomMsgEntity.getRoomStatus();
			}
			//获得要删除的所有图片fid(从数据库实时去查)
			List<String> picFidS = getPicList(housePicDto);
			if(Check.NuNCollection(picFidS)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("没有要删除的图片存在");
				return dto.toJsonString();
			}
			//根据housebasefid  roomFid  还有type去数据库查询所有图片，参与删除逻辑
			if(rentWay == RentWayEnum.ROOM.getCode()&&!Check.NuNStr(houseRoomMsgEntity.getHouseBaseFid())){
				for (String fid : picFidS) {
					//分租校验当前房间的图片是否是此房源其他房间的默认图片
					HouseRoomMsgEntity houseRoomMsgDto = new HouseRoomMsgEntity();
					houseRoomMsgDto.setHouseBaseFid(houseRoomMsgEntity.getHouseBaseFid());
					houseRoomMsgDto.setDefaultPicFid(fid);
					houseRoomMsgDto.setRoomStatus(HouseStatusEnum.SJ.getCode());
					LogUtil.info(LOGGER, "此房源其他房间的默认图片的查询条件houseRoomMsgDto={}", houseRoomMsgDto.toString());
					Long coun = this.houseManageServiceImpl.countByCondition(houseRoomMsgDto);
					if(coun>0){
						dto.putValue("defaultPic", "该图片是其他房源默认图片");
						continue;
					}else{
						picFidList.add(fid);
					}
				}
			}else{
				picFidList.addAll(picFidS);
			}
			LogUtil.info(LOGGER, "传入的所有图片fid集合:{}", picFidS);
			//对于上架房源的删除处理
			if(status == HouseStatusEnum.SJ.getCode()){
				int count = 0;
				Integer picType = housePicDto.getPicType();
				//判断是否公共空间图片
				if(HousePicTypeEnum.WS.getCode()!=picType.intValue()){
					count=houseIssueServiceImpl.getHousePicCount(housePicDto.getHouseBaseFid(),null, picType);
				} else {
					count=houseIssueServiceImpl.getHousePicCount(housePicDto.getHouseBaseFid(),housePicDto.getHouseRoomFid(), picType);
				}

				HousePicTypeEnum enumeration = HousePicTypeEnum.getEnumByCode(picType.intValue());
				if(Check.NuNObj(enumeration)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("图片类型错误");
					return dto.toJsonString();
				}
				int min = enumeration.getMin();// 上架房源图片最小数量
				if(count<=min){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("最后"+count+"张图片不能删除!");
					LogUtil.info(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
				/**判断是否默认图片开始**/
				String defaultPicFid=null;
				if(RentWayEnum.HOUSE.getCode() == houseBaseExtDto.getRentWay()){
					defaultPicFid = houseBaseExtDto.getHouseBaseExt().getDefaultPicFid();
				}else if(RentWayEnum.ROOM.getCode()==houseBaseExtDto.getRentWay()){
					if(!Check.NuNObj(houseRoomMsgEntity)){
						defaultPicFid = houseRoomMsgEntity.getDefaultPicFid();
					}
				}
				//从可被删除的图片fid集合移除不可被删除的默认图片
				if(!Check.NuNObj(defaultPicFid)){
					picFidList.remove(defaultPicFid);
				}
				//不可被删除图片数量
				int cut = picFidS.size()-picFidList.size();
				//上架房源保留最少图片数量
				if(cut >= min){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("图片不符合被删除的条件!");
					return dto.toJsonString();
				}
				int len =  picFidList.size();
				for(int i=0; i < min-cut; i++){
					picFidList.remove(len-1-i);
				}
				/**判断是否默认图片结束**/
				if(Check.NuNCollection(picFidList)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("没有符合删除条件的图片!");
					return dto.toJsonString();
				}
				String paramSJ = JsonEntityTransform.Object2Json(picFidList);
				LogUtil.info(LOGGER, "可被删除的图片fid集合:{}", picFidList);
				houseIssueServiceImpl.delAllHousePicMsgByFids(paramSJ);
			} else if (status==HouseStatusEnum.ZPSHWTG.getCode()){
				/**判断是否默认图片开始**/
				String defaultPicFid=null;
				if(RentWayEnum.HOUSE.getCode() == houseBaseExtDto.getRentWay()){
					defaultPicFid = houseBaseExtDto.getHouseBaseExt().getDefaultPicFid();
				}else if(RentWayEnum.ROOM.getCode()==houseBaseExtDto.getRentWay()){
					if(!Check.NuNObj(houseRoomMsgEntity)){
						defaultPicFid = houseRoomMsgEntity.getDefaultPicFid();
					}
				}
				//从可被删除的图片fid集合移除不可被删除的默认图片
				if(!Check.NuNObj(defaultPicFid)){
					picFidList.remove(defaultPicFid);
				}
				/**判断是否默认图片结束**/
				if(Check.NuNCollection(picFidList)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("没有符合删除条件的图片!");
					return dto.toJsonString();
				}
				String paramSJ = JsonEntityTransform.Object2Json(picFidList);
				LogUtil.info(LOGGER, "可被删除的图片fid集合:{}", picFidList);
				houseIssueServiceImpl.delAllHousePicMsgByFids(paramSJ);
			} else {
				if(Check.NuNCollection(picFidList)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("没有符合删除条件的图片!");
					return dto.toJsonString();
				}
				//未上架房源整体删除
				String paramWSJ = JsonEntityTransform.Object2Json(picFidList);
				houseIssueServiceImpl.delAllHousePicMsgByFids(paramWSJ);
			}
			dto.putValue("okPicList", picFidList);

			if(!Check.NuNStr(housePicDto.getHouseBaseFid())){
				LogUtil.info(LOGGER, "删除图片成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(housePicDto.getHouseBaseFid(),
						housePicDto.getHouseRoomFid(), RentWayEnum.HOUSE.getCode(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "删除图片成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	//获得要被删除的图片
	private List<String> getPicList(HousePicDto housePicDto){
		Integer picType = housePicDto.getPicType();
		List<String> picList = new ArrayList<String>();
		//判断是否公共空间图片
		if(HousePicTypeEnum.WS.getCode()!=picType.intValue()){
			List<HousePicMsgEntity> otArea = houseIssueServiceImpl.getHousePicList(housePicDto.getHouseBaseFid(),null, picType);
			for(HousePicMsgEntity pic : otArea){
				picList.add(pic.getFid());
			}
		} else {
			List<HousePicMsgEntity> wsArea = houseIssueServiceImpl.getHousePicList(housePicDto.getHouseBaseFid(),housePicDto.getHouseRoomFid(), picType);
			for(HousePicMsgEntity pic : wsArea){
				picList.add(pic.getFid());
			}
		}
		return picList;
	}

	@Override
	public String searchHousePicMsgByFid(String housePicFid) {
		LogUtil.info(LOGGER, "参数:housePicFid={}", housePicFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(housePicFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PIC_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HousePicMsgEntity housePicMsg = houseIssueServiceImpl.findHousePicMsgByFid(housePicFid);
			dto.putValue("obj", housePicMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHouseBaseAndRoomList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseRoomListDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseRoomListDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}

		HouseRoomListDto houseRoomListDto = validateResult.getResultObj();
		try {
			HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseRoomListDto
					.getHouseBaseFid());
			if(Check.NuNObj(houseBaseMsg)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			houseIssueServiceImpl.updateHouseBaseAndRoomList(houseBaseMsg,houseRoomListDto);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String saveHouseBaseMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBaseMsgEntity houseBaseMsg = JsonEntityTransform.json2Object(paramJson, HouseBaseMsgEntity.class);
		DataTransferObject dto = new DataTransferObject();
		// 校验房源不能为空
		if (Check.NuNObj(houseBaseMsg)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房源逻辑id必须为空
		if (!Check.NuNStr(houseBaseMsg.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_MUST_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验出租方式不能为空
		if (Check.NuNObj(houseBaseMsg.getRentWay())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房源类型不能为空
		if (Check.NuNObj(houseBaseMsg.getHouseType())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_TYPE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房源来源不能为空
		if (Check.NuNObj(houseBaseMsg.getHouseSource())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_SOURCE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验操作步骤不能为空
		if (Check.NuNObj(houseBaseMsg.getOperateSeq())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATE_SEQ_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 新增校验信息完整率不能为空
		if (Check.NuNObj(houseBaseMsg.getIntactRate())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_INTACTRATE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			String houseBaseFid = houseIssueServiceImpl.mergeHouseBaseMsg(houseBaseMsg);
			dto.putValue("houseBaseFid", houseBaseFid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHouseRoomMsg(String paramJson){
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseRoomMsgEntity houseRoom = JsonEntityTransform.json2Object(paramJson, HouseRoomMsgEntity.class);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(houseRoom)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			houseIssueServiceImpl.updateHouseRoomMsg(houseRoom);
		} catch (Exception e) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	@Override
	public String updateHouseBaseMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBaseMsgEntity houseBaseMsg = JsonEntityTransform.json2Object(paramJson, HouseBaseMsgEntity.class);
		DataTransferObject dto = new DataTransferObject();
		// 校验房源不能为空
		if (Check.NuNObj(houseBaseMsg)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验操作步骤不能为空
		/*if (Check.NuNObj(houseBaseMsg.getOperateSeq())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATE_SEQ_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}*/

		// 校验信息完整率不能为空
		/*if (Check.NuNObj(houseBaseMsg.getIntactRate())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_INTACTRATE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}*/
		try {
			houseIssueServiceImpl.mergeHouseBaseMsg(houseBaseMsg);
			if(!Check.NuNStr(houseBaseMsg.getFid())){
				LogUtil.info(LOGGER, "修改房源基础信息,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsg.getFid(), null,
						houseBaseMsg.getRentWay(), houseBaseMsg.getHouseStatus(), null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "修改房源基础信息,推送队列消息结束,推送内容:{}", pushContent);
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
	public String searchHouseBaseAndExtByFid(String houseBaseFid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseBaseExtDto houseBaseExt = houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(houseBaseFid);
			dto.putValue("obj", houseBaseExt);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHouseBaseAndExt(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBaseExtDto houseBase = JsonEntityTransform.json2Object(paramJson, HouseBaseExtDto.class);
		DataTransferObject dto = new DataTransferObject();
		// 校验房源不能为空
		if (Check.NuNObj(houseBase)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验房源逻辑id不能为空
		if (Check.NuNObj(houseBase.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			houseIssueServiceImpl.updateHouseBaseAndExt(houseBase);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		//房源发布以后推送mq
		if(HouseIssueStepEnum.SIX.getCode()<=houseBase.getOperateSeq()){
			if(!Check.NuNStr(houseBase.getFid())){
				//通知mq 更新房源索引信息 不区别当前房源的状态
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBase.getFid(), null,
						houseBase.getRentWay(),null,null));
				// 推送队列消息
				try {
					rabbitMqSendClient.sendQueue(queueName, pushContent);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "mq推送异常:{}", pushContent);
					e.printStackTrace();
				}
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHousePhyMsgByHouseBaseFid(String houseBaseFid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		// 校验房源逻辑id不能为空
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HousePhyMsgEntity housePhyMsg = houseIssueServiceImpl.findHousePhyMsgByHouseBaseFid(houseBaseFid);
			dto.putValue("obj", housePhyMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String mergeHousePhyMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		//1 参数校验
		ValidateResult<HousePhyMsgDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HousePhyMsgDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HousePhyMsgDto housePhyMsgDto = validateResult.getResultObj();

		DataTransferObject dto = new DataTransferObject();

		// 是否新增
		boolean isNew = Check.NuNStr(housePhyMsgDto.getFid());
		// 新增小区名称不能为空
		if(isNew && Check.NuNStr(housePhyMsgDto.getCommunityName())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_COMMUNITY_NAME_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 新增国家编码不能为空
		if(isNew && Check.NuNStr(housePhyMsgDto.getNationCode())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_NATION_CODE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 新增省份编码不能为空
		if(isNew && Check.NuNStr(housePhyMsgDto.getNationCode())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_PROVINCE_CODE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 新增城市编码不能为空
		if(isNew && Check.NuNStr(housePhyMsgDto.getCityCode())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_CITY_CODE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 新增城市编码不能为空
		if(isNew && Check.NuNStr(housePhyMsgDto.getCityCode())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_AREA_CODE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验操作步骤不能为空
		if(Check.NuNObj(housePhyMsgDto.getOperateSeq())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATE_SEQ_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		// 校验信息完整率不能为空
		if (Check.NuNStr(housePhyMsgDto.getFid()) && Check.NuNObj(housePhyMsgDto.getIntactRate())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_INTACTRATE_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			houseIssueServiceImpl.mergeHousePhyMsg(housePhyMsgDto);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String searchHousePicMsgList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		ValidateResult<HousePicDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HousePicDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HousePicDto housePicDto = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(housePicDto)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			List<HousePicMsgEntity> housePicMsgList = houseIssueServiceImpl.findHousePicMsgList(housePicDto);
			/**查询默认图片开始**/
			String defaultPicFid=null;
			HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(housePicDto.getHouseBaseFid());
			if(RentWayEnum.HOUSE.getCode()==houseBaseExtDto.getRentWay()){
				defaultPicFid=houseBaseExtDto.getHouseBaseExt().getDefaultPicFid();
			}else if(RentWayEnum.ROOM.getCode()==houseBaseExtDto.getRentWay()){
				HouseRoomMsgEntity houseRoomMsgEntity = null;
				if(!Check.NuNStr(housePicDto.getHouseRoomFid())){
					houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(housePicDto.getHouseRoomFid());
				}

				if(!Check.NuNObj(houseRoomMsgEntity)){
					defaultPicFid=houseRoomMsgEntity.getDefaultPicFid();
				}else{
					LogUtil.info(LOGGER, "houseRoomMsgEntity is null or blank,参数:{}", paramJson);
				}
			}

			if(!Check.NuNStr(defaultPicFid)){
				for(HousePicMsgEntity pic:housePicMsgList){
					pic.setIsDefault(0);
					if(defaultPicFid.equals(pic.getFid())){
						pic.setIsDefault(1);
					}
				}
			}
			/**查询默认图片结束**/
			dto.putValue("list", housePicMsgList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{},参数:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String issueHouse(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseBaseMsgEntity houseBaseMsg = JsonEntityTransform.json2Object(paramJson, HouseBaseMsgEntity.class);
		DataTransferObject dto = new DataTransferObject();
		// 校验房源逻辑id不能为空
		if (Check.NuNStr(houseBaseMsg.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			houseIssueServiceImpl.issueHouse(houseBaseMsg);

			// TODO 修改用户类型为房东
			Transaction houseTran = Cat.newTransaction("HouseIssueServiceProxy", CatConstant.House.HOUSE_COUNT);
			try {
				//统计发布房源数量
				Cat.logMetricForCount("发布房源的数量");
				houseTran.setStatus(Message.SUCCESS);
			} catch(Exception ex) {
				Cat.logError("发布房源的数量 打点异常", ex);
				houseTran.setStatus(ex);
			} finally {
				houseTran.complete();
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
	public String searchHouseDescAndBaseExt(String houseBaseFid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseDescDto housePhyMsg = houseIssueServiceImpl.findHouseDescDtoByHouseBaseFid(houseBaseFid);
			dto.putValue("obj", housePhyMsg);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateHouseDescAndBaseExt(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		HouseDescDto houseDescDto = JsonEntityTransform.json2Object(paramJson, HouseDescDto.class);

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(houseDescDto)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_DESC_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		if(Check.NuNStr(houseDescDto.getHouseBaseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			houseIssueServiceImpl.updateHouseDescAndBaseExt(houseDescDto);

			if(!Check.NuNStr(houseDescDto.getHouseBaseFid())){
				LogUtil.info(LOGGER, "修改房源扩展及描述信息,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseDescDto.getHouseBaseFid(), null,
						null, null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "修改房源扩展及信息,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 更新房源默认图片信息
	 */
	@Override
	public String updateHouseDefaultPic(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HousePicDto> validateResult =paramCheckLogic.checkParamValidate(paramJson, HousePicDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HousePicDto housePicDto = validateResult.getResultObj();
		if(Check.NuNObj(housePicDto)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		String housePicFid = housePicDto.getHousePicFid();
		if(Check.NuNStr(housePicFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PIC_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		HousePicMsgEntity housePicEntity = houseIssueServiceImpl.findHousePicMsgByFid(housePicFid);
		try{
			String houseBaseFid = housePicEntity.getHouseBaseFid();
			LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);

			HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(houseBaseFid);

			if(Check.NuNObj(houseBaseExtDto)){
				LogUtil.info(LOGGER, "根据房源fid={}，查询房源，不存在houseBaseMsgEntity={}",houseBaseFid, houseBaseExtDto);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前房源不存在");
				return dto.toJsonString();
			}

			int rentWay = houseBaseExtDto.getRentWay().intValue();
			int count = 0;
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				//判断是否已经是默认图片
//				if(housePicFid.equals(houseBaseExtDto.getHouseBaseExt().getDefaultPicFid())){
//					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
//					dto.setMsg("已经是封面照片");
//					return dto.toJsonString();
//				}
				//如果是整租，替换房源的默认图片
				HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
				//如果房源是已上架要审核 ，其他状态不需要审核，troy修改也不需要审核
				if(HouseStatusEnum.SJ.getCode()!=houseBaseExtDto.getHouseStatus()||(!Check.NuNObj(housePicDto.getPicSource())&&housePicDto.getPicSource()==1)){
					houseBaseExtEntity.setHouseBaseFid(houseBaseFid);
					houseBaseExtEntity.setDefaultPicFid(housePicFid);
					count=troyHouseMgtServiceImpl.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity,1);
				}
				//暂时不用通知搜索
//				if(!Check.NuNStr(houseBaseFid)){
//					LogUtil.info(LOGGER, "更新房源默认图片成功,推送队列消息开始...");
//					String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseFid,
//							null, RentWayEnum.HOUSE.getCode(), null, null));
//					// 推送队列消息
//					rabbitMqSendClient.sendQueue(queueName, pushContent);
//					LogUtil.info(LOGGER, "更新房源默认图片成功,推送队列消息结束,推送内容:{}", pushContent);
//				}
			} else if (rentWay == RentWayEnum.ROOM.getCode()){
				//判断是否已经是默认图片
				HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(housePicDto.getHouseRoomFid());
//				if(housePicFid.equals(houseRoomMsgEntity.getDefaultPicFid())){
//					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
//					dto.setMsg("已经是封面照片");
//					return dto.toJsonString();
//				}
				//如果是合租，替换房间默认图片
				HouseRoomMsgEntity roomMsgEntity=new HouseRoomMsgEntity();
				//如果房源是已上架要审核 ，其他状态不需要审核，troy修改也不需要审核
				if(HouseStatusEnum.SJ.getCode()!=houseRoomMsgEntity.getRoomStatus()||(!Check.NuNObj(housePicDto.getPicSource())&&housePicDto.getPicSource()==1)){
					roomMsgEntity.setFid(housePicDto.getHouseRoomFid());
					roomMsgEntity.setDefaultPicFid(housePicFid);
					houseIssueServiceImpl.updateHouseRoomMsg(roomMsgEntity);
				}
				//暂时不用通知搜索
//				if(!Check.NuNStr(houseBaseFid)){
//					LogUtil.info(LOGGER, "更新房间默认图片成功,推送队列消息开始...");
//					String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseFid,
//							housePicEntity.getRoomFid(), RentWayEnum.ROOM.getCode(), null, null));
//					// 推送队列消息
//					rabbitMqSendClient.sendQueue(queueName, pushContent);
//					LogUtil.info(LOGGER, "更新房间默认图片成功,推送队列消息结束,推送内容:{}", pushContent);
//				}
			}
			dto.putValue("count", count);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#isDefaultPic(java.lang.String)
	 */
	@Override
	public String newIsDefaultPic(String picFid,String fid,Integer rentWay) {
		DataTransferObject dto=new DataTransferObject();
		try {
			Integer num=0;
			if (RentWayEnum.HOUSE.getCode()==rentWay) {
				HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(fid);
				if(houseBaseExtDto.getHouseBaseExt().getDefaultPicFid().equals(picFid)){
					num=1;
				}
			} else if (RentWayEnum.ROOM.getCode()==rentWay) {
				HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(fid);
				if(houseRoomMsgEntity.getDefaultPicFid().equals(picFid)){
					num=1;
				}
			}
			dto.putValue("num", num);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#findHouseInputDetail(java.lang.String)
	 */
	@Override
	public String findHouseInputDetail(String houseBaseFid) {
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseBaseDetailVo houseBaseDetailVo=houseIssueServiceImpl.getHouseBaseDetailVoByFid(houseBaseFid);
			dto.putValue("houseDetail", houseBaseDetailVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 新增或更新【房源基础信息、房源物理信息、房源扩展信息】
	 * @author lishaochuan
	 * @create 2016年5月26日下午2:50:22
	 * @param paramJson
	 * @return
	 */
	@Override
	public String mergeHouseBaseAndPhyAndExt(String paramJson) {
		LogUtil.info(LOGGER, "新增或更新【房源基础信息、房源物理信息、房源扩展信息】，参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseMsgVo houseMsgVo = JsonEntityTransform.json2Object(paramJson, HouseMsgVo.class);
			// 校验房源不能为空
			if (Check.NuNObj(houseMsgVo)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 是否新增
			boolean isNew = Check.NuNStr(houseMsgVo.getFid());

			// 新增房源类型不能为空
			if (isNew && Check.NuNObj(houseMsgVo.getHouseType())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_TYPE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 新增出租方式不能为空
			if (isNew && Check.NuNObj(houseMsgVo.getRentWay())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 新增房源来源不能为空
			if (isNew && Check.NuNObj(houseMsgVo.getHouseSource())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_SOURCE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 校验操作步骤不能为空
			if (Check.NuNObj(houseMsgVo.getFid())&&Check.NuNObj(houseMsgVo.getOperateSeq())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_OPERATE_SEQ_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 校验房东uid不能为空
			if (Check.NuNObj(houseMsgVo.getLandlordUid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_LANDLORDUID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 房源物理信息
			HousePhyMsgEntity housePhyMsg = houseMsgVo.getHousePhyMsg();

			// 新增小区名称不能为空
			//			if(isNew && Check.NuNStr(housePhyMsg.getCommunityName())){
			//				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			//				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_COMMUNITY_NAME_NULL));
			//				LogUtil.error(LOGGER, dto.toJsonString());
			//				return dto.toJsonString();
			//			}

			if(Check.NuNStr(housePhyMsg.getCommunityName())){
				housePhyMsg.setCommunityName(" ");
			}

			String nationCode = housePhyMsg.getNationCode();
			// 新增国家编码不能为空
			if(isNew && Check.NuNStr(nationCode)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_NATION_CODE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 新增省份编码不能为空
			if(isNew && Check.NuNStr(housePhyMsg.getNationCode())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_PROVINCE_CODE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 新增城市编码不能为空
			if(isNew && Check.NuNStr(housePhyMsg.getCityCode())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_CITY_CODE_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			//中国
			if(nationCode.equals(SysConst.nation_code)){

				// 新增区域编码不能为空  
				if(isNew && Check.NuNStr(housePhyMsg.getAreaCode())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_AREA_CODE_NULL));
					LogUtil.error(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
			}else{

				// 新增区域编码不能为空  空字符串 可以存 日本只有三级
				if(isNew && housePhyMsg.getAreaCode() == null){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_AREA_CODE_NULL));
					LogUtil.error(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
			}


			// 房源扩展信息
			HouseBaseExtEntity houseBaseExt = houseMsgVo.getHouseBaseExt();

			// 新增扩展信息不能为空
			if(isNew && Check.NuNObj(houseBaseExt)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_EXT_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			// 新增街道信息不能为空
			if(isNew && Check.NuNObj(houseBaseExt)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PHY_HOUSESTREET_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			String houseBaseFid = houseIssueServiceImpl.mergeHouseBaseAndPhyAndExt(houseMsgVo);
			dto.putValue("houseBaseFid", houseBaseFid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#findHouseFacilityAndService(java.lang.String, java.lang.Integer)
	 */
	@Override
	public String findHouseFacilityAndService(String houseBaseFid) {
		DataTransferObject dto=new DataTransferObject();
		try {
			//配套设施列表
			List<HouseConfVo> facilityList=tenantHouseServiceImpl.findHouseConfListByCode(houseBaseFid, 0, ProductRulesEnum.ProductRulesEnum002.getValue());
			//服务列表
			List<HouseConfVo> serveList=tenantHouseServiceImpl.findHouseConfListByCode(houseBaseFid, 0, ProductRulesEnum.ProductRulesEnum0015.getValue());
			dto.putValue("serveList", serveList);
			dto.putValue("facilityList", facilityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#updateHouseInputDetail(java.lang.String)
	 * 
	 * 说明：
	 * 房源步骤保存：
	 * 此处整租 和 分租 处理方式一样
	 * 1. 发布房源 保存为第4步
	 * 2. 修改，条件：a.当前房态为 待发布 状态 b. 当前步骤为1   改成4  否则 不改
	 */
	@Override
	public String updateHouseInputDetail(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseBaseDetailVo houseBaseDetailVo=JsonEntityTransform.json2Object(paramJson, HouseBaseDetailVo.class);
			Integer rentWay = houseBaseDetailVo.getRentWay();
			HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseDetailVo.getFid());
			if(houseBaseDetailVo.getIsIssue() == 1){
				//校验房间的逻辑
				long roomCount = houseIssueServiceImpl.getHouseRoomCount(houseBaseDetailVo.getFid());
				int roomNum = houseBaseMsgEntity.getRoomNum();
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					if(roomNum == 0){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("请选择卧室数量");
						return dto.toJsonString();
					}
					if(roomNum != roomCount){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("您需要添加与户型中卧室数量相同的房间");
						return dto.toJsonString();
					}
				}else{
					if(roomNum == 0){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("请选择卧室数量");
						return dto.toJsonString();
					}
					if(roomCount < 1 || (roomCount > roomNum)){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("您需要最少添加1个房间,最多添加与户型中卧室数量相同的房间");
						return dto.toJsonString();
					}
				}

			}

			if(Check.NuNObj(houseBaseMsgEntity)){
				LogUtil.error(LOGGER, "待修改房源不存在，待修改信息houseBaseDetailVo={}", houseBaseDetailVo.toJsonStr());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("待修改房源不存在");
				return dto.toJsonString();
			}
			int dbOperateSeq = houseBaseMsgEntity.getOperateSeq();
			if(houseBaseMsgEntity.getHouseStatus().intValue() == HouseStatusEnum.DFB.getCode()&&houseBaseDetailVo.getIsIssue() == 2
					&&(dbOperateSeq==HouseIssueStepEnum.ONE.getCode()||dbOperateSeq==HouseIssueStepEnum.TWO.getCode()||dbOperateSeq==HouseIssueStepEnum.THREE.getCode())){
				houseBaseDetailVo.setOperateSeq(HouseIssueStepEnum.FOUR.getCode());
				houseBaseDetailVo.setIntactRate(HouseIssueStepEnum.FOUR.getValue());
			}

			//更新房源基本信息
			houseIssueServiceImpl.upHouseBaseDetailVoByFid(houseBaseDetailVo);

			if(!Check.NuNStr(houseBaseDetailVo.getFid())){
				LogUtil.info(LOGGER, "更新房源第4步,请求参数houseBaseDetailVo={},推送队列消息开始...",houseBaseDetailVo.toJsonStr());
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseDetailVo.getFid(),
						null, houseBaseDetailVo.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
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
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#updateHouseInputDetail(java.lang.String)
	 * 
	 * 说明：
	 * 录入房源，保存用户输入信息
	 */
	@Override
	public String updateHouseInfo(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseBaseDetailVo houseBaseDetailVo=JsonEntityTransform.json2Object(paramJson, HouseBaseDetailVo.class);
			HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseDetailVo.getFid());
			if(Check.NuNObj(houseBaseMsgEntity)){
				LogUtil.error(LOGGER, "待修改房源不存在，待修改信息houseBaseDetailVo={}", houseBaseDetailVo.toJsonStr());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("待修改房源不存在");
				return dto.toJsonString();
			}
			//更新房源基本信息
			houseIssueServiceImpl.upHouseBaseDetailVoByFid(houseBaseDetailVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#updateHouseConf(java.lang.String)
	 */
	@Override
	public String updateHouseConf(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			List<HouseConfMsgEntity> list=JsonEntityTransform.json2ObjectList(paramJson, HouseConfMsgEntity.class);
			if(Check.NuNCollection(list)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			//更新房源基本信息
			houseIssueServiceImpl.updateHouseConf(list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#findDefaultPic(java.lang.String, java.lang.Integer)
	 */
	@Override
	public String findDefaultPic(String fid, Integer rentWay) {
		LogUtil.info(LOGGER, "#findDefaultPic,fid={},rentWay={}", fid, rentWay);
		DataTransferObject dto=new DataTransferObject();
		try {
			String picBaseUrl=houseIssueServiceImpl.findDefaultPic(fid, rentWay);
			if(Check.NuNStr(picBaseUrl)){
				LogUtil.info(LOGGER, "#findDefaultPic默认图片为空,fid={},rentWay={}", fid, rentWay);
			}
			dto.putValue("picBaseUrl", picBaseUrl);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "#findDefaultPic,结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 逻辑删除房源或者房间
	 */
	@Override
	public String deleteHouseOrRoomByFid(String paramJson) {
		LogUtil.info(LOGGER, "deleteHouseOrRoomByFid={}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		HouseBaseDto houseBaseDto = JsonEntityTransform.json2Object(paramJson,HouseBaseDto.class);

		if(Check.NuNObj(houseBaseDto)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数不能为空");
			return dto.toJsonString();
		}
		String houseFid = houseBaseDto.getHouseFid();
		if(Check.NuNObj(houseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			return dto.toJsonString();
		}
		Integer rentWay = houseBaseDto.getRentWay();
		if(Check.NuNObj(rentWay)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			return dto.toJsonString();
		}

		try{
			int count = 0;
			//判断房源是否存在
			HouseBaseMsgEntity houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseDto.getHouseFid());
			if(Check.NuNObj(houseBaseMsgEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			if (!houseBaseMsgEntity.getLandlordUid().equals(houseBaseDto.getLandlordUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg("没有权限");
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			//整租
			if(houseBaseDto.getRentWay() == RentWayEnum.HOUSE.getCode()){
				Integer houseStatus = houseBaseMsgEntity.getHouseStatus();
				//已发布 上架 状态不能删除
				if (HouseStatusEnum.YFB.getCode() == houseStatus || HouseStatusEnum.SJ.getCode() == houseStatus) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
					LogUtil.info(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
				count = houseIssueServiceImpl.deleteHouseByFid(houseBaseDto.getHouseFid());
				LogUtil.info(LOGGER, "删除房源成功，houseFid={},", houseBaseDto.getHouseFid());
			}
			//合租
			if(houseBaseDto.getRentWay() == RentWayEnum.ROOM.getCode()){
				String roomFid = houseBaseDto.getRoomFid();
				//如果房间fid为空，则需要删除整个房源，判断房间状态是否有不能删除状态
				if(Check.NuNStr(roomFid)){
					List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseFid);
					if(!Check.NuNCollection(roomList)){
						for (HouseRoomMsgEntity room : roomList){
							if(HouseStatusEnum.YFB.getCode() == room.getRoomStatus() || HouseStatusEnum.SJ.getCode() == room.getRoomStatus()){
								dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
								dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
								return dto.toJsonString();
							}
						}
					}
					count = houseIssueServiceImpl.deleteRoomByFid(houseFid,null);
				}else{
					HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(houseBaseDto.getRoomFid());
					if(Check.NuNObj(houseRoomMsgEntity)){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
						return dto.toJsonString();
					}
					Integer roomStatus = houseRoomMsgEntity.getRoomStatus();
					//已发布 上架 状态不能删除
					if(HouseStatusEnum.YFB.getCode() == roomStatus || HouseStatusEnum.SJ.getCode() == roomStatus){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
						LogUtil.info(LOGGER, dto.toJsonString());
						return dto.toJsonString();
					}
					count = houseIssueServiceImpl.deleteRoomByFid(houseBaseDto.getHouseFid(), houseBaseDto.getRoomFid());
					LogUtil.info(LOGGER, "删除房源成功，houseFid={},roomFid={}", houseBaseDto.getHouseFid(),houseBaseDto.getRoomFid());
				}


			}
			dto.putValue("count", count);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("删除房源错误异常");
		}
		return dto.toJsonString();
	}


	/**
	 * 取消房源或者房间发布
	 */
	@Override
	public String cancleHouseOrRoomByFid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		HouseBaseDto houseBaseDto = JsonEntityTransform.json2Object(paramJson, HouseBaseDto.class);
		//判断房源id是否为null
		if(Check.NuNStr(houseBaseDto.getHouseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		//判断房东id是否为null
		if(Check.NuNStr(houseBaseDto.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.LANDLORD_UID_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		if(Check.NuNObj(houseBaseDto.getRentWay())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		Integer RentWay = houseBaseDto.getRentWay();
		try{
			//房源
			if(RentWay == RentWayEnum.HOUSE.getCode()){
				//判断房源是否存在
				HouseBaseMsgEntity houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseDto.getHouseFid());
				if(Check.NuNObj(houseBaseMsgEntity)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					LogUtil.info(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
				//判断是否符合取消发布状态
				if (!validateCancleStatus(houseBaseMsgEntity.getHouseStatus())) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
					LogUtil.info(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}

				int cancleNum = houseManageServiceImpl.upDownHouse(houseBaseMsgEntity,houseBaseDto.getLandlordUid(),HouseStatusEnum.DFB.getCode());
				dto.putValue("cancleNum", cancleNum);
				return dto.toJsonString();
			}

			//房间
			if(RentWay == RentWayEnum.ROOM.getCode()){
				//判断房间是否存在
				HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(houseBaseDto.getRoomFid());
				if(Check.NuNObj(houseRoomMsgEntity)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
					LogUtil.info(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
				//判断是否符合取消发布状态
				if(!validateCancleStatus(houseRoomMsgEntity.getRoomStatus())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
					LogUtil.info(LOGGER, dto.toJsonString());
					return dto.toJsonString();
				}
				int cancleNum = houseManageServiceImpl.upDownHouseRoom(houseRoomMsgEntity, houseBaseDto.getLandlordUid(), HouseStatusEnum.DFB.getCode());
				dto.putValue("cancleNum", cancleNum);
				return dto.toJsonString();
			}

		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}



	/**
	 *
	 * 列表页进入房源详情页发布房源
	 */
	@Override
	public String issueHouseInDetail(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		HouseBaseDto houseBaseDto = JsonEntityTransform.json2Object(paramJson, HouseBaseDto.class);
		Integer rentWay = houseBaseDto.getRentWay();
		String houseFid = houseBaseDto.getHouseFid();
		String landlordUid = houseBaseDto.getLandlordUid();
		String roomFid = houseBaseDto.getRoomFid();
		if(Check.NuNObj(rentWay)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			return dto.toJsonString();
		}

		if(Check.NuNStr(houseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			return dto.toJsonString();
		}

		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_LANDLORDUID_NULL));
			return dto.toJsonString();
		}

		try{
			//校验发布房源前需要填写数据
			HouseBaseExtDto houseBaseExtDto = houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(houseFid);

			if(Check.NuNObj(houseBaseExtDto)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				return dto.toJsonString();
			}

			String houseName = houseBaseExtDto.getHouseName();

			if(rentWay.intValue() == RentWayEnum.HOUSE.getCode()){
				if(Check.NuNObj(houseName)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg("请填写房源名称");
					return dto.toJsonString();
				}
				Double houseArea = houseBaseExtDto.getHouseArea();
				if(Check.NuNObj(houseArea) || houseArea == 0){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg("请填写房源面积");
					return dto.toJsonString();
				}
				//如果是整租 才校验房源价格是否填写
				Integer leasePrice = houseBaseExtDto.getLeasePrice();
				if(Check.NuNObj(leasePrice)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg("请填写房源价格");
					return dto.toJsonString();
				}
			}


			Date tillDate = houseBaseExtDto.getTillDate();
			if(Check.NuNObj(tillDate)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg("请填写出租期限");
				return dto.toJsonString();
			}
			//判断是否修改过押金和床位信息key
			StringBuilder flagKey=new StringBuilder();
			String issue=null;
			if(rentWay.intValue() == RentWayEnum.HOUSE.getCode()){
				flagKey.append(houseFid).append("issue");
				try {
					issue=redisOperations.get(flagKey.toString());
				} catch (Exception e){
					LogUtil.error(LOGGER, "发布时获取是否修改过押金和床位标志key{},{}",flagKey, e.getMessage());
				}
				HouseBaseExtEntity houseBaseExt = houseBaseExtDto.getHouseBaseExt();
				Integer checkInLimit = houseBaseExt == null ? null : houseBaseExt.getCheckInLimit();
				if(Check.NuNObj(checkInLimit)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg("请填写入住人数限制");
					return dto.toJsonString();
				}
				/****************校验是否是审核驳回没有修改就进行发布房源 @Author:lusp @Date:2017/9/6**************/
				//查询审核不通过原因
				Map<String, Object> paramMap=new HashMap<String, Object>();
				paramMap.put("houseFid", houseFid);
				HouseOperateLogEntity operateLogEntity=houseIssueServiceImpl.findLastHouseLog(paramMap);
				if (houseBaseExtDto.getHouseStatus() == HouseStatusEnum.ZPSHWTG.getCode() && !Check.NuNObj(operateLogEntity)
						&& !(HouseAuditCauseEnum.REJECT.LANDLORD_INFO.getCode() + "").equals(operateLogEntity.getAuditCause())
						&& !(HouseAuditCauseEnum.REJECT.OTHER.getCode() + "").equals(operateLogEntity.getAuditCause())
						&& Check.NuNStr(issue)) {
					HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
					houseUpdateFieldAuditNewlogEntity.setHouseFid(houseFid);
					houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.HOUSE.getCode());
					houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
					List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
					List<HousePicMsgEntity> noAuditPicList = troyHouseMgtServiceImpl.findNoAuditAddHousePicList(houseFid, null);
					if (Check.NuNCollection(houseFieldAuditLogVoList) && Check.NuNCollection(noAuditPicList)) {
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg("请按照审核未通过原因修改后再次提交发布房源");
						return dto.toJsonString();
					}
				}
				/****************校验是否是审核驳回没有修改就进行发布房源**************/
			}else if(rentWay.intValue() == RentWayEnum.ROOM.getCode()){
				if(!Check.NuNStrStrict(roomFid)){
					flagKey.append(roomFid).append("issue");
					try {
						issue=redisOperations.get(flagKey.toString());
					} catch (Exception e){
						LogUtil.error(LOGGER, "发布时获取是否修改过押金和床位标志key{},{}",flagKey, e.getMessage());
					}
					HouseRoomMsgEntity houseRoomMsgEntity = houseIssueServiceImpl.findHouseRoomMsgByFid(roomFid);
					if(Check.NuNObj(houseRoomMsgEntity)){
						LogUtil.error(LOGGER,"issueHouseInDetail(),获取房间信息为空, roomFid ：{}",roomFid);
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
						return dto.toJsonString();
					}
					if(Check.NuNObj(houseRoomMsgEntity.getCheckInLimit())){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg("请填写入住人数限制");
						return dto.toJsonString();
					}

					/****************校验是否是审核驳回没有修改就进行发布房源 @Author:lusp @Date:2017/9/6**************/
					//查询审核不通过原因
					Map<String, Object> paramMap=new HashMap<String, Object>();
					paramMap.put("houseFid", houseFid);
					paramMap.put("roomFid", roomFid);
					HouseOperateLogEntity operateLogEntity=houseIssueServiceImpl.findLastHouseLog(paramMap);
					LogUtil.info(LOGGER, "查询到的是否修改过押金和床位信息标志key：{},value:{}",flagKey,issue);
					if(houseRoomMsgEntity.getRoomStatus() == HouseStatusEnum.ZPSHWTG.getCode() && !Check.NuNObj(operateLogEntity)
							&&!(HouseAuditCauseEnum.REJECT.LANDLORD_INFO.getCode()+"").equals(operateLogEntity.getAuditCause())
							&&!(HouseAuditCauseEnum.REJECT.OTHER.getCode()+"").equals(operateLogEntity.getAuditCause())
							&&Check.NuNStr(issue)){
						HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
						houseUpdateFieldAuditNewlogEntity.setHouseFid(houseFid);
						houseUpdateFieldAuditNewlogEntity.setRoomFid(roomFid);
						houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.ROOM.getCode());
						houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
						List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
						List<HousePicMsgEntity> noAuditPicList = troyHouseMgtServiceImpl.findNoAuditAddHousePicList(houseFid, roomFid);
						//查询下别的房间有没有新的修改记录
						houseUpdateFieldAuditNewlogEntity.setHouseFid(houseFid);
						houseUpdateFieldAuditNewlogEntity.setRoomFid(null);
						houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.HOUSE.getCode());
						List<HouseFieldAuditLogVo> houseAllFieldAuditLogVoList =troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
						//是否有公共属性
						boolean isLog=false;
						for(HouseFieldAuditLogVo vo:houseAllFieldAuditLogVoList){
							if(HouseUpdateLogEnum.House_Base_Msg_Room_Num.getFieldPath().equals(vo.getFieldPath())){
								isLog=true;
								break;
							}
							if(HouseUpdateLogEnum.House_Base_Msg_Kitchen_Num.getFieldPath().equals(vo.getFieldPath())){
								isLog=true;
								break;
							}
							if(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath().equals(vo.getFieldPath())){
								isLog=true;
								break;
							}
							if(HouseUpdateLogEnum.House_Base_Msg_Toilet_Num.getFieldPath().equals(vo.getFieldPath())){
								isLog=true;
								break;
							}
							if(HouseUpdateLogEnum.House_Base_Msg_Balcony_Num.getFieldPath().equals(vo.getFieldPath())){
								isLog=true;
								break;
							}
							if(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath().equals(vo.getFieldPath())){
								isLog=true;
								break;
							}
							if(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath().equals(vo.getFieldPath())){
								isLog=true;
								break;
							}
						}
						//公共图片修改
						boolean isPicLog=false;
						List<HousePicMsgEntity> noAllAuditPicList = troyHouseMgtServiceImpl.findNoAuditAddHousePicList(houseFid, null);
						for(HousePicMsgEntity pic:noAllAuditPicList){
							if(pic.getPicType()!=HousePicTypeEnum.WS.getCode()){
								isPicLog=true;
								break;
							}
						}
						if(!isPicLog&&!isLog&&Check.NuNCollection(houseFieldAuditLogVoList)&&Check.NuNCollection(noAuditPicList)){
							dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
							dto.setMsg("请按照审核未通过原因修改后再次提交发布房源");
							return dto.toJsonString();
						}
					}
					/****************校验是否是审核驳回没有修改就进行发布房源**************/

				}else{
					List<HouseRoomListVo> roomListVos = houseManageServiceImpl.getRoomListByHouseFid(houseFid);
					if(Check.NuNCollection(roomListVos)){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("请完善房间信息");
						return dto.toJsonString();
					}
					boolean limitFlag = false;
					for(HouseRoomListVo houseRoomListVo:roomListVos){
						if(!Check.NuNObj(houseRoomListVo)){
							if(Check.NuNObj(houseRoomListVo.getCheckInLimit())){
								limitFlag = true;
								break;
							}
						}
					}
					if(limitFlag){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg("请填写入住人数限制");
						return dto.toJsonString();
					}
				}
			}


			HouseDescEntity houseDescEntity = houseIssueServiceImpl.findhouseDescEntityByHouseFid(houseFid);
			if(Check.NuNObj(houseDescEntity)||Check.NuNStr(houseDescEntity.getHouseDesc())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg("请填写房源描述");
				return dto.toJsonString();
			}
			if(Check.NuNObj(houseDescEntity)||Check.NuNStr(houseDescEntity.getHouseAroundDesc())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg("请填写房源周边情况");
				return dto.toJsonString();
			}
			//房源与房间数量匹配校验
			long roomCount = houseIssueServiceImpl.getHouseRoomCount(houseFid);
			int roomNum = houseBaseExtDto.getRoomNum();
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				if(roomNum == 0||roomCount==0){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("请完善房间信息");
					return dto.toJsonString();
				}
				//整租 创建相同数量的房间
				if(roomNum>roomCount){
					this.houseIssueServiceImpl.saveRoomS(roomNum-(int)roomCount, houseBaseExtDto);
					/*dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("您需要添加与户型中卧室数量相同的房间");
					return dto.toJsonString();*/
				}
			}else{
				if(roomNum == 0){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("请选择卧室数量");
					return dto.toJsonString();
				}
				if(roomCount < 1 || (roomCount > roomNum)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("您需要最少添加1个房间,最多添加与户型中卧室数量相同的房间");
					return dto.toJsonString();
				}
			}

			if(Check.NuNStrStrict(houseBaseExtDto.getCameramanMobile())
					||Check.NuNStrStrict(houseBaseExtDto.getCameramanName())){
				houseBaseExtDto.setCameramanName(HouseConstant.HOUSE_CAMERAMAN_NAME);
				houseBaseExtDto.setCameramanMobile(HouseConstant.HOUSE_CAMERAMAN_MOBILE);
			}

			/**
			 * yanb
			 * 共享客厅用到,判断OperateSeq
			 */
			Integer hallOperateSeq = HouseIssueStepEnum.SEVEN.getCode();
			if (!Check.NuNObj(houseBaseExtDto.getOperateSeq())) {
				hallOperateSeq = houseBaseExtDto.getOperateSeq();
			}
			//发布房源逻辑处理
			houseBaseExtDto.setOperateSeq(HouseIssueStepEnum.SEVEN.getCode());
			houseBaseExtDto.setIntactRate(HouseIssueStepEnum.getValueByCode(HouseIssueStepEnum.SEVEN.getCode()));
			int num = 0;
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				//校验发布房源状态是否正确
				if(validateIssueStatus(houseBaseExtDto.getHouseStatus())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
					return dto.toJsonString();
				}
				//发布房源
				num = houseIssueServiceImpl.issueHouseInDetail(houseBaseExtDto, houseBaseDto.getLandlordUid());
			}
			if(rentWay == RentWayEnum.ROOM.getCode()){
				/**
				 * 共享客厅校验
				 * 做发布流程兼容
				 */
				Integer isHall = houseIssueServiceImpl.isHall(houseBaseDto.getHouseFid());

				//如果为合租，并且房间fid为空，说明没有发过房间，直接走发布更新房源逻辑，更新房源，并且更新
				if (Check.NuNStr(roomFid) || (isHall.equals(YesOrNoEnum.YES.getCode()) && hallOperateSeq.equals(HouseIssueStepEnum.SIX.getCode()))
						||(!Check.NuNStr(roomFid)&&hallOperateSeq.equals(HouseIssueStepEnum.SIX.getCode()))) {

					//校验发布房源状态是否正确
					if (houseBaseExtDto.getHouseStatus() != HouseStatusEnum.DFB.getCode()) {
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
						return dto.toJsonString();
					}

					//发布房源
					num = houseIssueServiceImpl.issueHouseInDetail(houseBaseExtDto, houseBaseDto.getLandlordUid());
					dto.putValue("result", "发布成功");
				} else {
					//如果存在房间fid，更新房间逻辑走
					HouseRoomMsgEntity roomMsgEntity = houseIssueServiceImpl.findHouseRoomMsgByFid(roomFid);
					if (Check.NuNObj(roomMsgEntity)) {
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
						return dto.toJsonString();
					}
					if (validateIssueStatus(roomMsgEntity.getRoomStatus())) {
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
						dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ILLEGAL_OPERATION));
						return dto.toJsonString();
					}
					num = houseIssueServiceImpl.issueRoomInDetail(roomMsgEntity, landlordUid);
					dto.putValue("result", "发布成功");
				}
			}

			if(!Check.NuNStr(houseFid)){
				//通知mq 更新房源索引信息 不区别当前房源的状态
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseFid, null,
						rentWay,null,null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息结束,推送内容:{}", pushContent);
			}
			try {
				redisOperations.del(flagKey.toString());
			} catch (Exception e){
				LogUtil.error(LOGGER, "redis删除修改押金和床位信息标志！");
			}
			dto.putValue("num", num);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/**
	 *
	 * 校验是发布房源状态，true
	 *
	 * @author jixd
	 * @created 2016年6月15日 下午12:24:16
	 *
	 * @param status
	 * @return
	 */
	private boolean validateIssueStatus(int status){
		return (status == HouseStatusEnum.YFB.getCode() || status == HouseStatusEnum.XXSHTG.getCode()
				|| status == HouseStatusEnum.SJ.getCode());
	}
	/**
	 *
	 * 校验取消发布状态是否合法
	 *
	 * @author jixd
	 * @created 2016年6月16日 下午5:22:12
	 *
	 * @param status
	 * @return
	 */
	private boolean validateCancleStatus(int status){
		return (status == HouseStatusEnum.YFB.getCode() || status == HouseStatusEnum.XXSHTG.getCode());
	}



	/**
	 * 查询房源操作日志
	 */
	@Override
	public String findOperateLogList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		HouseOpLogSpDto houseOpLogSpDto = JsonEntityTransform.json2Object(paramJson, HouseOpLogSpDto.class);
		PagingResult<HouseOperateLogEntity> operLogList = houseIssueServiceImpl.findOperateLogList(houseOpLogSpDto);
		dto.putValue("list", operLogList.getRows());
		dto.putValue("total", operLogList.getTotal());
		return dto.toJsonString();
	}

	/**
	 * 根据房源fid或者房间fid查询配置集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/3 11:55
	 */
	@Override
	public String findGapAndFlexPrice(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try{
			List<HouseConfMsgEntity> confMsgList = houseIssueServiceImpl.findGapAndFlexPrice(JsonEntityTransform.json2Object(paramJson,HouseConfMsgEntity.class));
			if(!Check.NuNCollection(confMsgList)){
				for(HouseConfMsgEntity houseConf : confMsgList){
					if(houseConf.getDicCode().contains(ProductRulesEnum020.ProductRulesEnum020001.getParentValue())){
						String value = cityTemplateService.getTextValue(null, houseConf.getDicCode());
						String text = SOAResParseUtil.getValueFromDataByKey(value, "textValue", String.class);
						if(!Check.NuNObj(text)){
							houseConf.setDicVal(text);
						}
					}
				}
			}
			dto.putValue("list", confMsgList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#isDefaultPic(java.lang.String)
	 */
	@Override
	public String isDefaultPic(String picFid) {
		DataTransferObject dto=new DataTransferObject();
		try {
			int num=houseIssueServiceImpl.getDefaultPicByFid(picFid);
			dto.putValue("num", num);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "返回结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#saveHousePicMsgList(java.lang.String)
	 */
	@Override
	public String saveHousePicMsgList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		List<HousePicMsgEntity> picList = JsonEntityTransform.json2List(paramJson, HousePicMsgEntity.class);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNCollection(picList)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			//图片数量是否超过最大限制
			int count = houseIssueServiceImpl.getHousePicCount(picList.get(0).getHouseBaseFid(), picList.get(0)
					.getRoomFid(), picList.get(0).getPicType());
			if((count+picList.size())>HouseConstant.HOUSE_PIC_TYPE_LIMIT){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_PIC_TYPE_OVER_LIMIT));
				return dto.toJsonString();
			}
			//查询默认图片
			HousePicMsgEntity pic=null;
			HouseBaseMsgEntity houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(picList.get(0).getHouseBaseFid());
			if(RentWayEnum.HOUSE.getCode()==houseBaseMsgEntity.getRentWay()){
				pic=houseManageServiceImpl.getHouseDefaultPic(picList.get(0).getHouseBaseFid());
			} else if (RentWayEnum.ROOM.getCode()==houseBaseMsgEntity.getRentWay()){
				pic=houseIssueServiceImpl.findDefaultPicByType(houseBaseMsgEntity.getFid(),picList.get(0).getRoomFid(),0);
			}
			boolean hasDefault = true;//是否存在默认图片
			if(Check.NuNObj(pic)){
				hasDefault = false;
			}
			for (HousePicMsgEntity housePicMsgEntity : picList) {
				if(!hasDefault){
					hasDefault = true;
					housePicMsgEntity.setIsDefault(HouseConstant.IS_TRUE);
				}
				//如果是合租判断图片类型不是卧室不设置默认图片
				if(RentWayEnum.ROOM.getCode()==houseBaseMsgEntity.getRentWay()&&housePicMsgEntity.getPicType() !=HousePicTypeEnum.WS.getCode()){
					housePicMsgEntity.setIsDefault(HouseConstant.IS_NOT_TRUE);
				}
				houseIssueServiceImpl.saveHousePicMsg(housePicMsgEntity);
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
	public String mergeHouseExtAndDesc(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		HouseBaseExtDescDto houseBase = JsonEntityTransform.json2Object(paramJson, HouseBaseExtDescDto.class);
		// 校验房源不能为空
		if (Check.NuNObj(houseBase)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		if (Check.NuNStr(houseBase.getHouseBaseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
			return dto.toJsonString();
		}

		if (Check.NuNObj(houseBase.getHouseDescEntity())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, HouseMessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_EXT_NULL));
			return dto.toJsonString();
		}

		try {
			int count = houseIssueServiceImpl.mergeHouseExtAndDesc(houseBase);
			dto.putValue("count", count);
			//推送mq
			HouseBaseMsgEntity houseBaseMsg=houseIssueServiceImpl.findHouseBaseMsgByFid(houseBase.getHouseBaseFid());
			if(!Check.NuNObj(houseBaseMsg)&&HouseIssueStepEnum.SIX.getCode()<=houseBaseMsg.getOperateSeq()){
				if(!Check.NuNStr(houseBaseMsg.getFid())){
					//通知mq 更新房源索引信息 不区别当前房源的状态
					LogUtil.info(LOGGER, "发布房源成功,推送队列消息开始...");
					String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsg.getFid(), null,
							houseBaseMsg.getRentWay(),null,null));
					// 推送队列消息
					rabbitMqSendClient.sendQueue(queueName, pushContent);
					LogUtil.info(LOGGER, "发布房源成功,推送队列消息结束,推送内容:{}", pushContent);
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();

	}

	/**
	 * 点击户型选择功能(
	 *   状态条件说明：
	 *     整/分租： 可修改状态 ： 待发布=10  已发布=11   下架=41  强制下架=50
	 *
	 * )
	 *
	 * 更新房源户型信息，以及删除多余的房间（删除房间同时删除房间信息）
	 * 整租房间可全部删除
	 * 分租必须包含一个房间
	 *   说明：
	 * 1.初始化，房源不存在，直接提示不让添加房间
	 * 2.房源存在，带出户型，户型情况：A. 0室   B. 大于 0 室
	 * 3. A.0室处理   房间默认没有  当选择户型，保存入库，  并带出相应的房间展示位置（页面dom操作）
	 * 4. B.大于0室，选择户型，保存入库
	 *       效果： 选择X室， 下面展示相应X个房间（当前已存在且未删除房间数量Y<=X， 若Y<X,dom展示Z = X-Y个房间出来）
	 *     设 数据库当前已保存且未删除房间数量为 M(X>=M>=0)
	 *     4.1 选择X室，数据库查询M
	 *         若M<=X,展示M个房间，展示X-M个dom（房间数量不够，用dom元素展示，不保存入库）的房间数量
	 *         若M>X, 按照房间创建时间排序，删除M-X个房间，查询返回聊表FID,取前M-X个fid逻辑删除
	 *
	 *
	 *
	 * @author yd
	 * @created 2016年8月19日 下午3:50:52
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String  updateHouseAndDelRoom(String paramJson){

		DataTransferObject dto = new DataTransferObject();

		HouseBaseMsgEntity houseBaseMsg = JsonEntityTransform.json2Object(paramJson, HouseBaseMsgEntity.class);

		if(Check.NuNObj(houseBaseMsg)||Check.NuNStr(houseBaseMsg.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}

		HouseBaseMsgEntity houseDb= this.houseIssueServiceImpl.findHouseBaseMsgByFid(houseBaseMsg.getFid());

		if(Check.NuNObj(houseDb)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源不存在");
			return dto.toJsonString();
		}
		int rentWay = houseDb.getRentWay();
		int houseStatus = houseDb.getHouseStatus();
		//校验状态  
		if(RentWayEnum.HOUSE.getCode() ==rentWay
				&&houseStatus!=HouseStatusEnum.DFB.getCode()
				&&houseStatus!=HouseStatusEnum.YFB.getCode()
				&&houseStatus!=HouseStatusEnum.QZXJ.getCode()
				&&houseStatus!=HouseStatusEnum.ZPSHWTG.getCode()
				&&houseStatus!=HouseStatusEnum.XJ.getCode()){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(HouseStatusEnum.getHouseStatusByCode(houseStatus).getShowStatusName()+"房源，户型不能修改");
			return dto.toJsonString();
		}

		//分租只要有一个房间 不是 待发布=10  已发布=11   下架=41  强制下架=50 状态就不能修改
		if(RentWayEnum.ROOM.getCode() == rentWay ){

			Map<String, Object> params = new HashMap<String, Object>();

			List<Integer> roomStatusList = new ArrayList<Integer>();
			roomStatusList.add(HouseStatusEnum.XXSHTG.getCode());
			roomStatusList.add(HouseStatusEnum.XXSHWTG.getCode());
			roomStatusList.add(HouseStatusEnum.ZPSHWTG.getCode());
			roomStatusList.add(HouseStatusEnum.SJ.getCode());
			params.put("roomStatusList", roomStatusList);
			params.put("houseBaseFid", houseDb.getFid());
			Long count = 	this.houseManageServiceImpl.countByRoomInfo(params);
			if(count>0){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("存在审核中，或 未审核，或上架的房间,户型不能修改");
				return dto.toJsonString();
			}
		}
		int oldRoomNum = houseDb.getRoomNum();

		LogUtil.info(LOGGER, "待更新房源fid={}，更新之前房间数量oldRoomNum={}，待更新房间数量roomNum={}", houseDb.getFid(),oldRoomNum,houseBaseMsg.getRoomNum());
		//去更新户型 并删除房间  deleteCheckHouseRoomMsgByFid,获取未被删除的房间fid集合  在一个事物中完成
		this.houseIssueServiceImpl.updateHouseAndRoom(oldRoomNum, houseDb.getRentWay(), houseBaseMsg);
		List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseBaseMsg.getFid());

		List<HouseRoomInfoDto> listHouseRoomInfoDtos = new ArrayList<HouseRoomInfoDto>();
		for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
			HouseRoomInfoDto houseRoomInfoDto = new HouseRoomInfoDto();
			BeanUtils.copyProperties(houseRoomMsgEntity, houseRoomInfoDto);
			Long count = this.houseIssueServiceImpl.countBedByRoomFid(houseRoomMsgEntity.getFid());
			houseRoomInfoDto.setRoomFinishStatus(RoomFinishStatusEnum.UN_FINISH.getName());
			if(count>0){
				houseRoomInfoDto.setRoomFinishStatus(RoomFinishStatusEnum.FINISH.getName());
			}

			listHouseRoomInfoDtos.add(houseRoomInfoDto);
		}
		dto.putValue("HouseRoomInfoDto", listHouseRoomInfoDtos);

		return dto.toJsonString();
	}

	/**
	 * M站 分租添加房间保存信息
	 *
	 * 更新房源步骤逻辑：
	 * 当前房间状态为待发布，且房源步骤为第4步
	 */
	@Override
	public String mergeRoomAndBedList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		RoomMsgVo roomMsgVo = JsonEntityTransform.json2Object(paramJson, RoomMsgVo.class);
		if(Check.NuNObj(roomMsgVo)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNCollection(roomMsgVo.getBedList())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNStr(roomMsgVo.getHouseBaseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			return dto.toJsonString();
		}
		try {
			houseIssueServiceImpl.mergeRoomAndBedList(roomMsgVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 房源状态情况
	 *    1. 房源信息状态计算    （0=未完成  1=完成）
	 *      计算公式：
	 *         整租：要校验发布房源整租第4步是否都已填写，全填写，就认为完成，包括字段：房源名称，房源面积，出租期限，入住人数限制，价格，
	 *         配套设施，房源描述，周边情况。   由于正常情况下，这几个字段都必填后，才能到下一步，故校验任意字段不为null即可，
	 *         这边选择校验，房源名称，房源面积，出租期限,房源描述，周边情况
	 *
	 *         分租：要校验发布房源分租第4步是否都已填写，全填写，就认为完成，包括字段： 与房东同住，出租期限，配套设施，房源描述，周边情况
	 *         这块需要校验：出租期限,房源描述，周边情况
	 *    2. 房间信息状态计算    （0=未完成  1=完成） 校验当前房源下 所有房间是否都有床位，有就完成，无就未完成
	 *      计算公式：
	 *         整租，分租都校验是否当前房间有床位，即可，有就是完成，无就是未完成
	 *
	 *    3. 可选信息状态计算   （0=未完成  1=完成）  分：交易信息和入住信息，都完成就算完成，否则未完成
	 *      计算公式：
	 *        交易信息： 校验交易信息页面的所有字段是否都完成，这里都会有默认值，所以不好校验 暂时校验房屋守则
	 *
	 * @author yd
	 * @created 2016年8月22日 上午11:31:44
	 *
	 * @param houseFid
	 * @return
	 */
	@Override
	@Deprecated
	public String houseStatusSituation(String houseFid) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(houseFid)){

			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");

			return dto.toJsonString();
		}

		HouseBaseMsgEntity houseDb= this.houseIssueServiceImpl.findHouseBaseMsgByFid(houseFid);

		if(Check.NuNObj(houseDb)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源不存在");
			return dto.toJsonString();
		}

		HouseDescEntity  houseDescEntity = this.houseIssueServiceImpl.findhouseDescEntityByHouseFid(houseFid);
		//校验房源完整度
		int rentWay = houseDb.getRentWay().intValue();
		HouseInfoStaSit houseInfoStaSit = new HouseInfoStaSit();
		houseInfoStaSit.setExtStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
		houseInfoStaSit.setHouseStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
		houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.FINISH.getCode());

		if(!Check.NuNObj(houseDescEntity)){

			if(!Check.NuNObj(houseDb.getTillDate())&&!Check.NuNStr(houseDescEntity.getHouseAroundDesc())
					&&!Check.NuNStr(houseDescEntity.getHouseDesc())){

				houseInfoStaSit.setHouseStatusSit(RoomFinishStatusEnum.FINISH.getCode());

				if(rentWay == RentWayEnum.HOUSE.getCode()){
					if(Check.NuNStr(houseDb.getHouseName())
							||Check.NuNObj(houseDb.getHouseArea())||houseDb.getHouseArea().doubleValue()==0
							||Check.NuNObj(houseDb.getLeasePrice())||houseDb.getLeasePrice().doubleValue()==0){
						houseInfoStaSit.setHouseStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
					}
				}
			}
			//校验可选信息完整度 因为都有一个默认值  暂时以 房屋守则为准
			if(!Check.NuNStr(houseDescEntity.getHouseRules())){
				houseInfoStaSit.setExtStatusSit(RoomFinishStatusEnum.FINISH.getCode());
			}
		}

		//校验房间完整度

		List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseFid);

		if(!Check.NuNCollection(roomList)){
			for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
				Long count = this.houseIssueServiceImpl.countBedByRoomFid(houseRoomMsgEntity.getFid());
				if(count == 0){
					houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
					break;
				}
			}
			//校验户型
			if(houseInfoStaSit.getRoomStatusSit() ==RoomFinishStatusEnum.FINISH.getCode()&&houseDb.getRoomNum()!=roomList.size()){
				houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
			}
		}else{
			houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
		}

		dto.putValue("houseInfoStaSit", houseInfoStaSit);
		return dto.toJsonString();
	}

	/**
	 *
	 * 整租保存床位信息
	 *
	 * @author yd
	 * @created 2016年8月23日 下午9:46:08
	 *
	 * @param roomBedZDto
	 * @return
	 */
	@Override
	public String saveRoomBedZ(String roomBedZDto){

		DataTransferObject dto = new DataTransferObject();
		RoomBedZDto roomBed = JsonEntityTransform.json2Object(roomBedZDto, RoomBedZDto.class);

		if(Check.NuNObj(roomBed)||Check.NuNCollection(roomBed.getListBeds())){

			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");

			return dto.toJsonString();
		}
		if(Check.NuNStr(roomBed.getHouseBaseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源fid不存在");

			return dto.toJsonString();
		}

		HouseBaseMsgEntity houseBaseMsg = this.houseIssueServiceImpl.findHouseBaseMsgByFid(roomBed.getHouseBaseFid());

		if(Check.NuNObj(houseBaseMsg)){
			LogUtil.error(LOGGER, "保存房源fid={}的床位时候，房源不存在,入参roomBed={}", roomBed.getHouseBaseFid(),roomBed.toString());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源不存在");
			return dto.toJsonString();
		}
		if(!Check.NuNStr(roomBed.getRoomFid())){
			HouseRoomMsgEntity room = this.houseIssueServiceImpl.findHouseRoomMsgByFid(roomBed.getRoomFid());
			if(Check.NuNObj(room)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前房间已经不存在");

				return dto.toJsonString();
			}
		}
		this.houseIssueServiceImpl.saveRoomBedZ(roomBed, houseBaseMsg);
		return dto.toJsonString();
	}

	/**
	 *
	 * 发布房源
	 * 发布流程：
	 * 1. 修改房源基础信息
	 * 2. 房源默认值设置
	 * 3. 保存默认优惠规则
	 * 4. 保存默认押金规则
	 * 5. 保存房源操作日志 （ 整租保存房源  分租保存每个房间 ）
	 * 6. 级联更新房间 以及 床位的状态 （整租 和 分租  一样）
	 *
	 * @author yd
	 * @created 2016年8月24日 下午2:03:55
	 *
	 * @param fid 是否发布  0=不发布  1 = 发布
	 * @return
	 *
	 */
	@Override
	public String releaseHouse(String fid){

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}

		HouseBaseMsgEntity houseBaseMsgEntity = houseIssueServiceImpl.findHouseBaseMsgByFid(fid);

		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源不存在");
			return dto.toJsonString();
		}

		if(houseBaseMsgEntity.getHouseStatus() != HouseStatusEnum.DFB.getCode()){
			LogUtil.info(LOGGER, "发布房源：房源状态错误,当前房源状态为houseSatus={}，fid={}", houseBaseMsgEntity.getHouseStatus(),houseBaseMsgEntity.getFid());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源已发布");
			return dto.toJsonString();
		}

		//1.至少要保证 有一个房间 且 此房间有床位    2. 整租 户型 和 房间数量 必须保持一致
		Long count = this.houseIssueServiceImpl.countBedNumByHouseFid(fid,0);
		if(count<1){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请完善房间信息");
			return dto.toJsonString();
		}

		if(houseBaseMsgEntity.getRentWay() == RentWayEnum.HOUSE.getCode()){
			Long roomNum = this.houseIssueServiceImpl.getHouseRoomCount(fid);

			if(roomNum.intValue()!=houseBaseMsgEntity.getRoomNum()){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("请完善房间，与户型数量保持一致");
				return dto.toJsonString();
			}
		}

		if(!this.houseIssueServiceImpl.releaseHouse(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源发布失败");
			return dto.toJsonString();
		}

		try {
			if(!Check.NuNStr(fid)){
				//通知mq 更新房源索引信息 不区别当前房源的状态
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(fid, null,
						houseBaseMsgEntity.getRentWay(),null,null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "发布房源fid={}，成功，推送mq失败，e={}", fid,e);
		}

		dto.putValue("count", count);
		return dto.toJsonString();
	}

	/**
	 *
	 * 说明：这里是 M站发布整租房源 第5步，选择户型，添加完房间后，点击下一步的功能
	 * 根据房源fid或房间fid查询床位数量
	 * 1. 校验房源存在
	 * 2. 校验至少有一个房间有床
	 * 3. 查询房间数量 是否 和 房源存放数量一致 不一致 保存 差的房间
	 *
	 * 房源步骤：
	 * 1.发布房源时候，为第5步
	 * 5.修改户型时候，a.房源状态必须为待发布  b.房源发布步骤为4 修改成5  否则不修改
	 *
	 * @author yd
	 * @created 2016年8月24日 下午2:03:55
	 *
	 * @param fid
	 * @return
	 */
	@Override
	public String  countBedNumByHouseFid(String fid,String rentWay){

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}

		HouseBaseMsgEntity house = houseIssueServiceImpl.findHouseBaseMsgByFid(fid);

		if(Check.NuNObj(house)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源不存在");
			return dto.toJsonString();
		}
		Long count = this.houseIssueServiceImpl.countBedNumByHouseFid(fid, house.getRentWay());

		if(count<=0){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请完善房间信息");
			return dto.toJsonString();
		}

		if(house.getRentWay() == RentWayEnum.HOUSE.getCode()){
			Long roomNum = this.houseIssueServiceImpl.getHouseRoomCount(fid);

			int addRoomNum = house.getRoomNum() - roomNum.intValue();

			if(addRoomNum>0){
				LogUtil.info(LOGGER, "当前户型数量roomNum = {},数据库当前房间数量dbRoomNum={},需要添加空房间数量addRoomNum={}",house.getRoomNum(), roomNum,addRoomNum);
				if(!this.houseIssueServiceImpl.saveRoomS(addRoomNum, house)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("房间保存失败");
					return dto.toJsonString();
				}
				HouseBaseMsgEntity houseNew = new HouseBaseMsgEntity();
				if(house.getHouseStatus() == HouseStatusEnum.DFB.getCode()&&house.getOperateSeq()==HouseIssueStepEnum.FOUR.getCode()){
					houseNew.setOperateSeq(HouseIssueStepEnum.FIVE.getCode());
					houseNew.setIntactRate(HouseIssueStepEnum.FIVE.getValue());
				}
				houseNew.setFid(house.getFid());
				houseNew.setLastModifyDate(new Date());
				this.troyHouseMgtServiceImpl.updateHouseBaseMsg(houseNew);
			}
		}


		dto.putValue("count", count);
		return dto.toJsonString();
	}


	/**
	 * 房源状态情况
	 *    1. 房源信息状态计算    （0=未完成  1=完成）
	 *      计算公式：
	 *         整租：要校验发布房源整租第4步是否都已填写，全填写，就认为完成，包括字段：房源名称，房源面积，出租期限，入住人数限制，价格，
	 *         配套设施，房源描述，周边情况。   由于正常情况下，这几个字段都必填后，才能到下一步，故校验任意字段不为null即可，
	 *         这边选择校验，房源名称，房源面积，出租期限,房源描述，周边情况
	 *
	 *         分租：要校验发布房源分租第4步是否都已填写，全填写，就认为完成，包括字段： 与房东同住，出租期限，配套设施，房源描述，周边情况
	 *         这块需要校验：出租期限,房源描述，周边情况
	 *    2. 房间信息状态计算    （0=未完成  1=完成） 校验当前房源下 所有房间是否都有床位，有就完成，无就未完成
	 *      计算公式：
	 *         整租，分租都校验是否当前房间有床位，即可，有就是完成，无就是未完成
	 *
	 *    3. 可选信息状态计算   （0=未完成  1=完成）  分：交易信息和入住信息，都完成就算完成，否则未完成
	 *      计算公式：
	 *        交易信息： 校验交易信息页面的所有字段是否都完成，这里都会有默认值，所以不好校验 暂时校验房屋守则
	 *
	 * @author yd
	 * @created 2016年8月22日 上午11:31:44
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String houseInfoSituation(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		//1 参数校验
		ValidateResult<HouseBaseVo> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseBaseVo.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseBaseVo houseBaseVo = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();
		if (houseBaseVo.getRentWay().intValue() == RentWayEnum.ROOM.getCode() && Check.NuNStr(houseBaseVo.getRoomFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			HouseBaseMsgEntity houseDb= this.houseIssueServiceImpl.findHouseBaseMsgByFid(houseBaseVo.getHouseFid());

			if(Check.NuNObj(houseDb)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				return dto.toJsonString();
			}

			HouseDescEntity houseDescEntity = this.houseIssueServiceImpl.findhouseDescEntityByHouseFid(houseBaseVo.getHouseFid());
			//校验房源完整度
			int rentWay = houseDb.getRentWay().intValue();
			HouseInfoStaSit houseInfoStaSit = new HouseInfoStaSit();
			houseInfoStaSit.setExtStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
			houseInfoStaSit.setHouseStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
			houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.FINISH.getCode());

			if(!Check.NuNObj(houseDescEntity)){
				if(!Check.NuNObj(houseDb.getTillDate())&&!Check.NuNStr(houseDescEntity.getHouseAroundDesc())
						&&!Check.NuNStr(houseDescEntity.getHouseDesc())){
					houseInfoStaSit.setHouseStatusSit(RoomFinishStatusEnum.FINISH.getCode());
					if(rentWay == RentWayEnum.HOUSE.getCode()){
						if(Check.NuNStr(houseDb.getHouseName())
								||Check.NuNObj(houseDb.getHouseArea())||houseDb.getHouseArea().doubleValue()==0
								||Check.NuNObj(houseDb.getLeasePrice())||houseDb.getLeasePrice().doubleValue()==0){
							houseInfoStaSit.setHouseStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
						}
					}
				}
				//校验可选信息完整度 因为都有一个默认值  暂时以 房屋守则为准
				if(!Check.NuNStr(houseDescEntity.getHouseRules())){
					houseInfoStaSit.setExtStatusSit(RoomFinishStatusEnum.FINISH.getCode());
				}
			}

			//校验房间完整度
			if (houseBaseVo.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
				List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseBaseVo.getHouseFid());
				if (!Check.NuNCollection(roomList)) {
					for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
						Long count = this.houseIssueServiceImpl.countBedByRoomFid(houseRoomMsgEntity.getFid());
						if (count == 0) {
							houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
							break;
						}
					}
					// 校验户型
					if (houseInfoStaSit.getRoomStatusSit() == RoomFinishStatusEnum.FINISH.getCode()
							&& houseDb.getRoomNum() != roomList.size()) {
						houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
					}
				} else {
					houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
				}
			} else if (houseBaseVo.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
				HouseRoomMsgEntity houseRoomMsgEntity = houseIssueServiceImpl.findHouseRoomMsgByFid(houseBaseVo.getRoomFid());
				if (!Check.NuNObj(houseRoomMsgEntity)) {
					Long count = this.houseIssueServiceImpl.countBedByRoomFid(houseRoomMsgEntity.getFid());
					if (count == 0) {
						houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
					}
				} else {
					houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
				}
			}

			dto.putValue("houseInfoStaSit", houseInfoStaSit);
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
	 * 获取当前房源的押金
	 *
	 * 1. 固定收取，查询房源配置项，返回，如果没有，返回0元 （分租 整租处理相同）
	 * 2. 按天收取  
	 *    整租 ：查询当前配置项目值，计算押金（按照当前房源基本价格计算）
	 *    分租 ：查询当前配置项目值，计算押金（按照当前房间基本价格计算）
	 *
	 * @author yd
	 * @created 2016年11月16日 下午3:49:12
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	@Deprecated
	public String findHouseDeposit(String houseFid) {


		DataTransferObject dto = new DataTransferObject();

		HouseConfMsgEntity houseConfMsg = null;

		if(Check.NuNStrStrict(houseFid)){

			houseConfMsg = new HouseConfMsgEntity();
			houseConfMsg.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
			houseConfMsg.setDicVal("0");
			dto.putValue("checkOutRulesCode",TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());
			dto.putValue("houseConfMsg", houseConfMsg);

			return dto.toJsonString();
		}

		houseConfMsg = this.houseIssueServiceImpl.findHouseDepositConfByHouseFid(houseFid,dto);
		dto.putValue("houseConfMsg", houseConfMsg);
		return dto.toJsonString();
	}

	/**
	 *
	 * 获取当前房源的押金
	 *
	 * 1. 固定收取，查询房源配置项，返回，如果没有，返回0元 （分租 整租处理相同）
	 * 2. 按天收取  
	 *    整租 ：查询当前配置项目值，计算押金（按照当前房源基本价格计算）
	 *    分租 ：查询当前配置项目值，计算押金（按照当前房间基本价格计算）
	 *
	 * @author zl
	 * @created 2017年06月26日 下午3:49:12
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findHouseOrRoomDeposit(String paramJson){


		LogUtil.info(LOGGER, "参数:{}", paramJson);
		//1 参数校验
		ValidateResult<HouseBaseVo> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseBaseVo.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseBaseVo houseBaseVo = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();

		HouseConfMsgEntity houseConfMsg = this.houseIssueServiceImpl.findHouseDepositConfByHouseFid(houseBaseVo.getHouseFid(),houseBaseVo.getRoomFid(),houseBaseVo.getRentWay(),dto);
		if(Check.NuNObj(houseConfMsg)){
			houseConfMsg = new HouseConfMsgEntity();
			houseConfMsg.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
			houseConfMsg.setDicVal("0");
			dto.putValue("checkOutRulesCode",TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());
			dto.putValue("houseConfMsg", houseConfMsg);
		}

		dto.putValue("houseConfMsg", houseConfMsg);
		return dto.toJsonString();

	}



	@Override
	public String searchHousePicMsgListByHouseFid(String houseBaseFid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStrStrict(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			HousePicDto housePicDto = new HousePicDto();
			housePicDto.setHouseBaseFid(houseBaseFid);
			List<HousePicMsgEntity> housePicMsgList = houseIssueServiceImpl.findHousePicMsgList(housePicDto );
			dto.putValue("list", housePicMsgList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "searchHousePicMsgListByHouseFid error:{},houseBaseFid:{}", e, houseBaseFid);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#getHouseBaseExtByHouseBaseFid(java.lang.String)
	 */
	@Override
	public String getHouseBaseExtByHouseBaseFid(String houseBaseFid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseExtEntity  houseBaseExtEntity=houseIssueServiceImpl.getHouseBaseExtByHouseBaseFid(houseBaseFid);
			dto.putValue("houseBaseExt", houseBaseExtEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "getHouseBaseExtByHouseBaseFid error:{},houseBaseFid:{}", e, houseBaseFid);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#updateHouseBaseExtByHouseBaseFid(java.lang.String)
	 */
	@Override
	public String updateHouseBaseExtByHouseBaseFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseExtEntity houseBaseExtEntity=JsonEntityTransform.json2Entity(paramJson, HouseBaseExtEntity.class);
			troyHouseMgtServiceImpl.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity,0);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updateHouseBaseExtByHouseBaseFid error:{},paramJson:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 *
	 * 更新房屋守则中的可选守则
	 *
	 * @author baiwei
	 * @created 2017年4月7日 下午2:11:14
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String updateSelectHouseRules(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			List<HouseConfMsgEntity> list=JsonEntityTransform.json2ObjectList(paramJson, HouseConfMsgEntity.class);
			if(Check.NuNCollection(list)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			//更新房屋守则中的可选守则
			if(!Check.NuNCollection(list)&&list.size()>0){
				houseIssueServiceImpl.updateSelectHouseRules(list,list.get(0).getRoomFid());
			}
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
	 * 查询审核未通过次数
	 *
	 * @author baiwei
	 * @created 2017年4月13日 下午8:03:07
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findHouseAuditNoLogTime(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		HouseOperateLogEntity houseOperateLogEntity = JsonEntityTransform.json2Entity(paramJson, HouseOperateLogEntity.class);
		int houseAuditNoLogTime = houseIssueServiceImpl.findHouseAuditNoLogTime(houseOperateLogEntity);
		dto.putValue("count", houseAuditNoLogTime);
		return dto.toJsonString();
	}

	@Override
	public String updateHouseConfList(String paramJson,List<String> gapList) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			HouseConfMsgEntity upConf = JsonEntityTransform.json2Object(paramJson, HouseConfMsgEntity.class);
			List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();
			for(String gap : gapList){
				HouseConfMsgEntity newConf = new HouseConfMsgEntity();
				newConf.setHouseBaseFid(upConf.getHouseBaseFid());
				if(!Check.NuNStrStrict(upConf.getRoomFid())){
					newConf.setRoomFid(upConf.getRoomFid());
				}
				newConf.setDicCode(gap);
				newConf.setIsDel(upConf.getIsDel());
				confList.add(newConf);
			}
			houseIssueServiceImpl.updateHouseConfList(confList);

			if(!Check.NuNCollection(confList) && !Check.NuNObj(confList.get(0)) && !Check.NuNStr(confList.get(0).getHouseBaseFid())){
				LogUtil.info(LOGGER, "灵活定价修改成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(confList.get(0).getHouseBaseFid(),
						confList.get(0).getRoomFid(), null, null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "灵活定价修改成功,推送队列消息结束,推送内容:{}", pushContent);
			}
		}catch (Exception e){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueService#getRoomExtByRoomFid(java.lang.String)
	 */
	@Override
	public String getRoomExtByRoomFid(String roomFid) {
		LogUtil.info(LOGGER, "参数:{}", roomFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(roomFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		HouseRoomExtEntity houseRoomExtEntity =  houseIssueServiceImpl.getRoomExtByRoomFid(roomFid);
		dto.putValue("roomExt", houseRoomExtEntity);
		return dto.toJsonString();
	}

	@Override
	public String findDefaultPicListInfo(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseParamsDto houseBaseParamsDto = JsonEntityTransform.json2Object(paramJson,HouseBaseParamsDto.class);
			List<HouseDefaultPicInfoVo> coverPicList = houseIssueServiceImpl.findDefaultPicListInfo(houseBaseParamsDto);
			dto.putValue("coverPicList", coverPicList);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "findDefaultPicListInfo error:{},paramJson:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	@Override
	public String saveDealPolicy(String paramJson) {
		LogUtil.info(LOGGER, "[saveDealPolicy]参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			CancellationPolicyDto paramsDto = JsonEntityTransform.json2Object(paramJson,CancellationPolicyDto.class);


			HouseBaseMsgEntity houseBaseMsgEntity = houseIssueServiceImpl.findHouseBaseMsgByFid(paramsDto.getHouseBaseFid());
			if(Check.NuNObj(houseBaseMsgEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto.toJsonString();
			}
			
			/**添加一个判断押金是否修改的判断**/
			Integer houseStatus=HouseStatusEnum.DFB.getCode();
			HouseConfParamsDto confDto=new HouseConfParamsDto();
			confDto.setRentWay(paramsDto.getRentWay());
			confDto.setHouseBaseFid(paramsDto.getHouseBaseFid());
			StringBuilder flagKey=new StringBuilder();
			if(paramsDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				houseStatus=houseBaseMsgEntity.getHouseStatus();
				flagKey.append(paramsDto.getHouseBaseFid());
			} else if(paramsDto.getRentWay()==RentWayEnum.ROOM.getCode()&&!Check.NuNStr(paramsDto.getRoomFid())) {
				HouseRoomMsgEntity roomMsgEntity=houseIssueServiceImpl.findHouseRoomMsgByFid(paramsDto.getRoomFid());
				houseStatus=roomMsgEntity.getRoomStatus();
				confDto.setRoomFid(paramsDto.getRoomFid());
				flagKey.append(paramsDto.getRoomFid());
			}
			flagKey.append("issue");
			if(houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
				confDto.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
				List<HouseConfMsgEntity> confList=houseIssueServiceImpl.findHouseConfValidList(confDto);
				if(!Check.NuNCollection(confList)){
					Integer deposit=paramsDto.getDepositMoney()*100;
					//如果押金有修改
					if(!deposit.toString().equals(confList.get(0).getDicVal())){
						try {
							redisOperations.setex(flagKey.toString(), 24*60*60, "1");
						} catch (Exception e){
							LogUtil.error(LOGGER, "redis保持修改押金标识错误key{},{}",flagKey, e);
						}
					}
				}
			}
			/**添加一个判断押金是否修改的判断**/
			HouseBaseExtDescDto houseDescDto = new  HouseBaseExtDescDto();
			houseDescDto.setHouseBaseFid(paramsDto.getHouseBaseFid());
			houseDescDto.setOrderType(paramsDto.getOrderType());
			houseDescDto.setDepositRulesCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
			houseDescDto.setCheckOutRulesCode(paramsDto.getCancellationPolicy());

			HouseDescEntity houseDescEntity =  new HouseDescEntity();
			houseDescEntity.setHouseBaseFid(paramsDto.getHouseBaseFid());
			houseDescEntity.setHouseRules(paramsDto.getHouseRules());

			HouseConfMsgEntity houseConfMsgEntity = new HouseConfMsgEntity();
			houseConfMsgEntity.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
			houseConfMsgEntity.setDicVal(String.valueOf(paramsDto.getDepositMoney()*100));
			houseConfMsgEntity.setHouseBaseFid(paramsDto.getHouseBaseFid());

			if(paramsDto.getRentWay() == RentWayEnum.ROOM.getCode()){
				houseConfMsgEntity.setRoomFid(paramsDto.getRoomFid());
				houseDescDto.setRoomFid(paramsDto.getRoomFid());
			}

			houseDescDto.setHouseConfMsgEntity(houseConfMsgEntity);
			houseDescDto.setHouseDescEntity(houseDescEntity);
			houseDescDto.setStep(paramsDto.getStep());

			houseIssueServiceImpl.mergeHouseExtAndDesc(houseDescDto);


			//房屋守则可选项
			List<HouseConfMsgEntity> confList=new ArrayList<>();
			if(!Check.NuNStr(paramsDto.getHouseRulesArray()) && paramsDto.getHouseRulesArray().split(",").length>0){
				List<HouseRoomMsgEntity> roomList = new ArrayList<>();
				if(Check.NuNStr(paramsDto.getRoomFid())){
					roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(paramsDto.getHouseBaseFid());
				}

				for(String val :paramsDto.getHouseRulesArray().split(",")){
					HouseConfMsgEntity confMsgEntity  = new HouseConfMsgEntity();
					confMsgEntity.setDicCode(ProductRulesEnum.ProductRulesEnum0024.getValue());
					confMsgEntity.setDicVal(val);
					confMsgEntity.setHouseBaseFid(paramsDto.getHouseBaseFid());

					if(paramsDto.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(paramsDto.getRoomFid())){
						if(!Check.NuNCollection(roomList)){//发布时每个房间下面存一份
							for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {

								HouseConfMsgEntity confMsg  = new HouseConfMsgEntity();
								BeanUtils.copyProperties(confMsgEntity, confMsg);
								confMsg.setRoomFid(houseRoomMsgEntity.getFid());

								confList.add(confMsg);
							}
						}
					}else{
						if(paramsDto.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNStr(paramsDto.getRoomFid())){
							confMsgEntity.setRoomFid(paramsDto.getRoomFid());
						}
						confList.add(confMsgEntity);
					}

				}
			}
			if (!Check.NuNCollection(confList)) {
				houseIssueServiceImpl.updateSelectHouseRules(confList,paramsDto.getRoomFid());
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "saveDealPolicy error:{},paramJson:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "[saveDealPolicy]结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String findHouseConfValidList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseConfParamsDto paramsDto = JsonEntityTransform.json2Object(paramJson,HouseConfParamsDto.class);
			List<HouseConfMsgEntity> list = houseIssueServiceImpl.findHouseConfValidList(paramsDto);
			dto.putValue("list", list);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "findHouseConfValidList error:{},paramJson:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();

	}

	@Override
	public String isSetDefaultPic(String paramJson) {
		LogUtil.info(LOGGER, "isSetDefaultPic(),参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "isSetDefaultPic(), 方法入参错误,paramJson:{}", paramJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return dto.toJsonString();
			}
			HouseBaseParamsDto houseBaseParamsDto = validateResult.getResultObj();

			boolean hasDefault = true;//是否存在默认图片
			String defaultPicFid=null;
			HouseBaseExtDto houseBaseExtDto = houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(houseBaseParamsDto.getHouseBaseFid());
			if (Check.NuNObj(houseBaseExtDto)) {
				LogUtil.error(LOGGER, "isSetDefaultPic(),该房源不存在,fid:{}",houseBaseParamsDto.getHouseBaseFid());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return dto.toJsonString();
			}
			if (Check.NuNObj(houseBaseExtDto.getHouseBaseExt())) {
				LogUtil.error(LOGGER, "isSetDefaultPic(),该房源扩展信息不存在,fid:{}",houseBaseParamsDto.getHouseBaseFid());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return dto.toJsonString();
			}
			//查询下是否有在审核的默认图片 TODO
			HousePicMsgEntity auditDefaultPic=null;
			if(!Check.NuNStr(houseBaseParamsDto.getAuditDefaultPic())){
				auditDefaultPic=houseIssueServiceImpl.findHousePicMsgByFid(houseBaseParamsDto.getAuditDefaultPic());
			}
			//查询下是否有在审核的默认图片
			if(RentWayEnum.HOUSE.getCode()== houseBaseParamsDto.getRentWay()){
				defaultPicFid=houseBaseExtDto.getHouseBaseExt().getDefaultPicFid();
				if(Check.NuNStrStrict(defaultPicFid)&&Check.NuNObj(auditDefaultPic)){
					hasDefault = false;
					dto.putValue("hasDefault", hasDefault);
					return dto.toJsonString();
				}

				/*******增加封面照片不可用判断，比如封面照为客厅照片，结果客厅又设置为零********/
				if(!Check.NuNStrStrict(defaultPicFid)){
					HousePicMsgEntity housePicMsgEntity = houseIssueServiceImpl.findHousePicMsgByFid(defaultPicFid);
					if(Check.NuNObj(housePicMsgEntity)&&Check.NuNObj(auditDefaultPic)){
						hasDefault = false;
						dto.putValue("hasDefault", hasDefault);
						return dto.toJsonString();
					}
					if(houseBaseExtDto.getHallNum()==0||(!Check.NuNObj(houseBaseParamsDto.getAuditHallNum())&&houseBaseParamsDto.getAuditHallNum()==0)){
						if(!Check.NuNObj(housePicMsgEntity)&&Check.NuNObj(auditDefaultPic)&&housePicMsgEntity.getPicType()==HousePicTypeEnum.KT.getCode()){
							hasDefault = false;
							dto.putValue("hasDefault", hasDefault);
							return dto.toJsonString();
						}
						if(Check.NuNObj(housePicMsgEntity)&&!Check.NuNObj(auditDefaultPic)&&auditDefaultPic.getPicType()==HousePicTypeEnum.KT.getCode()){
							hasDefault = false;
							dto.putValue("hasDefault", hasDefault);
							return dto.toJsonString();
						}
						if(!Check.NuNObj(housePicMsgEntity)&&!Check.NuNObj(auditDefaultPic)&&auditDefaultPic.getPicType()==HousePicTypeEnum.KT.getCode()&&housePicMsgEntity.getPicType()==HousePicTypeEnum.KT.getCode()){
							hasDefault = false;
							dto.putValue("hasDefault", hasDefault);
							return dto.toJsonString();
						}
					}
				}
				/*******增加封面照片不可用判断，比如封面照为客厅照片，结果客厅又设置为零********/

			} else if (RentWayEnum.ROOM.getCode()==houseBaseParamsDto.getRentWay()){
				//先判断roomFid 是否为空，如果为空，则根据houseFid 查询出roomFid的集合
				if(Check.NuNStr(houseBaseParamsDto.getRoomFid())){
					List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseBaseParamsDto.getHouseBaseFid());
					if(Check.NuNCollection(roomList)){
						LogUtil.error(LOGGER, "isSetDefaultPic(),该房源房间不存在,fid:{}",houseBaseParamsDto.getHouseBaseFid());
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("参数错误");
						return dto.toJsonString();
					}
					//循环遍历每个房间有没有默认照片
					for (HouseRoomMsgEntity houseRoomMsgEntity:roomList){
						if(Check.NuNStrStrict(houseRoomMsgEntity.getDefaultPicFid())){
							hasDefault = false;
							break;
						}

						/*******增加封面照片不可用判断，比如封面照为客厅照片，结果客厅又设置为零********/
						if(!Check.NuNStrStrict(houseRoomMsgEntity.getDefaultPicFid())){
							HousePicMsgEntity housePicMsgEntity = houseIssueServiceImpl.findHousePicMsgByFid(houseRoomMsgEntity.getDefaultPicFid());
							if(Check.NuNObj(housePicMsgEntity)){
								hasDefault = false;
								dto.putValue("hasDefault", hasDefault);
								return dto.toJsonString();
							}
							if((houseBaseExtDto.getHallNum()==0&&housePicMsgEntity.getPicType()==HousePicTypeEnum.KT.getCode())
									||Check.NuNObj(housePicMsgEntity)){
								hasDefault = false;
								break;
							}
						}
						/*******增加封面照片不可用判断，比如封面照为客厅照片，结果客厅又设置为零********/
					}
					if(!hasDefault){
						dto.putValue("hasDefault", hasDefault);
						return dto.toJsonString();
					}
				} else {
					HouseRoomMsgEntity houseRoomMsg = houseIssueServiceImpl.findHouseRoomMsgByFid(houseBaseParamsDto.getRoomFid());
					if (Check.NuNObj(houseRoomMsg)) {
						LogUtil.error(LOGGER, "isSetDefaultPic(),该房间不存在,roomFid:{}",houseBaseParamsDto.getRoomFid());
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("参数错误");
						return dto.toJsonString();
					}
					if(Check.NuNStrStrict(houseRoomMsg.getDefaultPicFid())&&Check.NuNObj(auditDefaultPic)){
						hasDefault = false;
						dto.putValue("hasDefault", hasDefault);
						return dto.toJsonString();
					}
					/*******增加封面照片不可用判断，比如封面照为客厅照片，结果客厅又设置为零********/
					if(!Check.NuNStrStrict(houseRoomMsg.getDefaultPicFid())){
						HousePicMsgEntity housePicMsgEntity = houseIssueServiceImpl.findHousePicMsgByFid(houseRoomMsg.getDefaultPicFid());
						if(Check.NuNObj(housePicMsgEntity)&&Check.NuNObj(auditDefaultPic)){
							hasDefault = false;
							dto.putValue("hasDefault", hasDefault);
							return dto.toJsonString();
						}
						if(houseBaseExtDto.getHallNum()==0||(!Check.NuNObj(houseBaseParamsDto.getAuditHallNum())&&houseBaseParamsDto.getAuditHallNum()==0)){
							if(!Check.NuNObj(housePicMsgEntity)&&Check.NuNObj(auditDefaultPic)&&housePicMsgEntity.getPicType()==HousePicTypeEnum.KT.getCode()){
								hasDefault = false;
								dto.putValue("hasDefault", hasDefault);
								return dto.toJsonString();
							}
							if(Check.NuNObj(housePicMsgEntity)&&!Check.NuNObj(auditDefaultPic)&&auditDefaultPic.getPicType()==HousePicTypeEnum.KT.getCode()){
								hasDefault = false;
								dto.putValue("hasDefault", hasDefault);
								return dto.toJsonString();
							}
							if(!Check.NuNObj(housePicMsgEntity)&&!Check.NuNObj(auditDefaultPic)&&auditDefaultPic.getPicType()==HousePicTypeEnum.KT.getCode()&&housePicMsgEntity.getPicType()==HousePicTypeEnum.KT.getCode()){
								hasDefault = false;
								dto.putValue("hasDefault", hasDefault);
								return dto.toJsonString();
							}
						}
					}
					/*******增加封面照片不可用判断，比如封面照为客厅照片，结果客厅又设置为零********/

				}
			}
			dto.putValue("hasDefault", hasDefault);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "isSetDefaultPic error:{},paramJson:{}", e.getStackTrace(), paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "isSetDefaultPic(),结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 查询房间类型roomType
	 * 校验是否为共享客厅
	 * 默认返回为0,若为共享客厅则返回1
	 * 可改名为isHall
	 * @author yanb
	 * @created 2017年11月21日 16:25:59
	 * @param  * @param null
	 * @return
	 */
	@Override
	public String isHallByHouseBaseFid(String houseBaseFid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			Integer isHall=houseIssueServiceImpl.isHall(houseBaseFid);
			dto.putValue("isHall", isHall);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "isHallByHouseBaseFid error:{},paramJson:{}", e.getStackTrace(), houseBaseFid);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String getHallRoomFidByHouseBaseFid(String houseBaseFid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseHallVo houseHallVo = houseIssueServiceImpl.findHall(houseBaseFid);
			String roomFid = null;
			if (!Check.NuNObj(houseHallVo)) {
				roomFid = houseHallVo.getRoomFid();
			}
			dto.putValue("roomFid",roomFid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "getHallRoomFidByHouseBaseFid error:{},paramJson:{}", e.getStackTrace(), houseBaseFid);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
