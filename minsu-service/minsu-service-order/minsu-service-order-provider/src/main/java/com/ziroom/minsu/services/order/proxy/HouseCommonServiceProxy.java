/**
 * @FileName: HouseCommonServiceProxy.java
 * @Package com.ziroom.minsu.services.order.proxy
 * @author yd
 * @created 2016年12月7日 上午10:34:13
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.order.HouseLockLogEntity;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseTonightDiscountService;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.house.entity.HousePriorityVo;
import com.ziroom.minsu.services.house.entity.HouseTonightPriceInfoVo;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.services.order.service.HouseLockServiceImpl;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>房源公共 接口实现</p>
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
@Service("order.houseCommonServiceProxy")
public class HouseCommonServiceProxy implements HouseCommonService {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HouseCommonServiceProxy.class);


	@Resource(name = "order.houseLockServiceImpl")
	private HouseLockServiceImpl houseLockServiceImpl;


	@Autowired
	private RedisOperations redisOperations;

	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name = "house.houseTonightDiscountService")
	private HouseTonightDiscountService houseTonightDiscountService;

	@Resource(name = "basedata.zkSysService")
	private ZkSysService zkSysService;

	static String dateFormatPattern = "yyyy-MM-dd";

	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;

	@Resource(name="house.queueName")
	private QueueName queueName ;

	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl;


	/**
	 *
	 * 获取房源 夹心价格的map
	 * 1. 判断 灵活定价 开关接口  如果关闭直接返回null
	 * 2. 打开状态—— 取获取灵活定价 配置
	 * 3. 返回灵活定价日期
	 *
	 * @author yd
	 * @created 2016年12月7日 上午10:32:54
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findPriorityDate(String paramJson) {

		DataTransferObject dto = new DataTransferObject();
		HousePriorityDto housePriorityDto = JsonEntityTransform.json2Object(paramJson, HousePriorityDto.class);


		if (Check.NuNObj(housePriorityDto)
				|| Check.NuNObj(housePriorityDto.getRentWay())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("查询对象不存在");
			return dto.toJsonString();
		}
		RentWayEnum rentWay = RentWayEnum.getRentWayByCode(housePriorityDto.getRentWay());
		if (Check.NuNObj(rentWay)) {
			LogUtil.error(LOGGER, "获取房源 夹心价格——出租方式错误,rentWay={}", rentWay);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租方式错误");
			return dto.toJsonString();
		}


		HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
		houseConfMsg.setHouseBaseFid(housePriorityDto.getHouseBaseFid());
		if (rentWay.getCode() == RentWayEnum.HOUSE.getCode()) {
			if (Check.NuNStrStrict(housePriorityDto.getHouseBaseFid())) {
				LogUtil.error(LOGGER, "获取房源 夹心价格——房源fid不存在}");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源参数错误");
				return dto.toJsonString();
			}
		}

		if (rentWay.getCode() == RentWayEnum.ROOM.getCode()) {
			if (Check.NuNStrStrict(housePriorityDto.getHouseRoomFid())) {
				LogUtil.error(LOGGER, "获取房源 夹心价格——房间fid不存在}");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间参数错误");
				return dto.toJsonString();
			}
			houseConfMsg.setRoomFid(housePriorityDto.getHouseRoomFid());
		}
		if (Check.NuNObj(housePriorityDto.getStartDate())
				|| Check.NuNObj(housePriorityDto.getEndDate())) {
			LogUtil.error(LOGGER, "获取房源 夹心价格——起始时间或结束时间错误}");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("起始时间或结束时间错误");
		}

		//默认当前日期
		if (Check.NuNObj(housePriorityDto.getNowDate())) housePriorityDto.setNowDate(new Date());

		housePriorityDto.setStartDateStr(DateUtil.dateFormat(housePriorityDto.getStartDate(), dateFormatPattern));
		housePriorityDto.setEndDateStr(DateUtil.dateFormat(housePriorityDto.getEndDate(), dateFormatPattern));
		houseConfMsg.setDicCode(ProductRulesEnum020.ProductRulesEnum020001.getParentValue());
		houseConfMsg.setIsDel(IsDelEnum.NOT_DEL.getCode());

		DataTransferObject flexPrieDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(houseConfMsg)));

		if (flexPrieDto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "获取房源 夹心价格——获取开关失败}");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("获取夹心价格开关失败");
			return dto.toJsonString();
		}


		List<HouseConfMsgEntity> confMsgList = flexPrieDto.parseData("list", new TypeReference<List<HouseConfMsgEntity>>() {
		});
		Map<String, HousePriorityVo> housePriorityMap = null;
		LogUtil.info(LOGGER, "入参housePriorityDto={}", housePriorityDto.toJsonStr());
		if (!Check.NuNCollection(confMsgList)) {
			Map<String, String> priorityValMap = new HashMap<String, String>();
			for (HouseConfMsgEntity houseConfMsgEntity : confMsgList) {
				priorityValMap.put(houseConfMsgEntity.getDicCode(), Check.NuNStrStrict(houseConfMsgEntity.getDicVal()) ? "1" : houseConfMsgEntity.getDicVal());
			}
			LogUtil.info(LOGGER, "灵活定价规则priorityValMap={}", JsonEntityTransform.Object2Json(priorityValMap));
			List<String> lockDateList = this.houseLockServiceImpl.getPriorityDate(housePriorityDto);
			housePriorityMap = getHousePriorityMap(lockDateList, priorityValMap, housePriorityDto);
		}

		dto.putValue("housePriorityMap", Check.NuNMap(housePriorityMap) ? new HashMap<String, HousePriorityVo>() : housePriorityMap);
		return dto.toJsonString();
	}

	@Override
	public String saveHouseLockLog(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		HouseLockLogEntity houseLockLogEntity = JsonEntityTransform.json2Object(paramJson, HouseLockLogEntity.class);
		if (Check.NuNObj(houseLockLogEntity)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		int count = houseLockServiceImpl.saveHouseLockLog(houseLockLogEntity);
		dto.putValue("count", count);
		return dto.toJsonString();
	}

	@Override
	public String syncAirHouseLock(String paramJson) {


		DataTransferObject dto = new DataTransferObject();
		LockHouseRequest lockHouseRequest = JsonEntityTransform.json2Object(paramJson, LockHouseRequest.class);
		if (Check.NuNObj(lockHouseRequest)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		if (Check.NuNStr(lockHouseRequest.getHouseFid())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		try {
			int count = 0;
			List<CalendarDataVo> calendarDataVos = lockHouseRequest.getCalendarDataVos();
			//兼容老代码
			if (!Check.NuNCollection(lockHouseRequest.getLockDayList())&&Check.NuNCollection(calendarDataVos)) {
				count = houseLockServiceImpl.syncAirLock(lockHouseRequest);
			}

			//新版处理 2017-07-29 copy by yd
			if(!Check.NuNCollection(calendarDataVos)){

				List<Date> allDate = new ArrayList<>();
				Date today = new Date();
				String todayStr = DateUtil.dateFormat(today, "yyyy-MM-dd");
				try {
					today= DateUtil.parseDate(todayStr +" 00:00:00","yyyy-MM-dd");
				} catch (ParseException e) {
					e.printStackTrace();
				}

				//处理房源锁状态
				for (CalendarDataVo calendarDataVo : calendarDataVos){
					Date startDate = calendarDataVo.getStartDate();
					Date endDate = calendarDataVo.getEndDate();
					List<Date> dates = DateSplitUtil.dateSplit(startDate, endDate);
					for (Date date : dates){
						if (date.before(today)){
							continue;
						}
						allDate.add(date);
					}
				}

				if(!Check.NuNCollection(allDate)){
					lockHouseRequest.setLockDayList(allDate);
					count = houseLockServiceImpl.syncAirLock(lockHouseRequest);
				}
			}
			dto.putValue("count", count);
		} catch (Exception e) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("同步房源日历失败");
			LogUtil.error(LOGGER, "同步房源日历异常，param={}，e={}", paramJson,e);
		}


		try {
			if(!Check.NuNStr(lockHouseRequest.getHouseFid())){
				LogUtil.info(LOGGER, "同步房源日历成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(lockHouseRequest.getHouseFid(),
						lockHouseRequest.getRoomFid(), lockHouseRequest.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "同步房源日历成功,推送队列消息结束,推送内容:{}", pushContent);
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "同步房源日历，发送mq失败，param={}，e={}", paramJson,e);
		}


		return dto.toJsonString();
	}


	/**
	 *
	 * 获取灵活订单 MAP
	 * 边界考虑：
	 * 1. 如果是查看日历 ：  夹心开始日期  当天即是 夹心1天或2天  取对房东最有利的优惠
	 * 2. 如果结束日期  ： 结束日期 和 结束日期前一天  不走夹心价格
	 *
	 * @author yd
	 * @created 2016年12月7日 上午11:39:51
	 *
	 * @param lockDateList
	 * @return
	 *
	 */
	public Map<String, HousePriorityVo> getHousePriorityMap(List<String> lockDateList, Map<String, String> priorityValMap, HousePriorityDto housePriorityDto) {

		Map<String, HousePriorityVo> housePriorityMap = new HashMap<String, HousePriorityVo>();

		if (Check.NuNMap(priorityValMap) || Check.NuNObj(housePriorityDto)) {
			return housePriorityMap;
		}
		if (Check.NuNObj(housePriorityDto.getNowDate())) housePriorityDto.setNowDate(new Date());
		//如果没有出租日期  当天为灵活定价
		if (Check.NuNCollection(lockDateList)) {
			//JYTJ
			// housePriorityMap.put(DateUtil.dateFormat(housePriorityDto.getNowDate(), dateFormatPattern), setPriorityCur(priorityValMap, housePriorityDto));
			return housePriorityMap;
		}

		Calendar cursor = Calendar.getInstance();
		//没有锁定的日期
		List<Set<String>> notLockContinuousDaysList = new ArrayList<Set<String>>();
		Set<String> notLockContinuousDays = new HashSet<String>();
		cursor.setTime(housePriorityDto.getStartDate());
		for (; ; ) {
			if (cursor.getTime().after(housePriorityDto.getEndDate())) {
				break;
			}
			if (!lockDateList.contains(DateUtil.dateFormat(cursor.getTime(), dateFormatPattern))) {//没有锁定
				notLockContinuousDays.add(DateUtil.dateFormat(cursor.getTime(), dateFormatPattern));
			} else {
				if (!Check.NuNCollection(notLockContinuousDays)) {
					notLockContinuousDaysList.add(notLockContinuousDays);
				}
				notLockContinuousDays = new HashSet<String>();
			}
			cursor.add(Calendar.DATE, 1);
		}

		if (!Check.NuNCollection(notLockContinuousDays)) {
			notLockContinuousDaysList.add(notLockContinuousDays);//最后一部分
		}

		//组装map
		if (!Check.NuNCollection(notLockContinuousDaysList)) {
			for (Set<String> notLockDays : notLockContinuousDaysList) {
				if (notLockDays.size() == ProductRulesEnum020.ProductRulesEnum020002.getDayNum()) {
					for (String data : notLockDays) {
						HousePriorityVo housePriorityVo = new HousePriorityVo();
						housePriorityVo.setPriorityCode(ProductRulesEnum020.ProductRulesEnum020002.getValue());
						housePriorityVo.setPriorityDate(data);
						String val = priorityValMap.get(ProductRulesEnum020.ProductRulesEnum020002.getValue());

						if (!Check.NuNStrStrict(val)) {
							housePriorityVo.setPriorityDiscount(val);
							housePriorityVo.setPriorityName(ProductRulesEnum020.ProductRulesEnum020002.getName());
							housePriorityMap.put(data, housePriorityVo);
						}

					}
				}
				if (notLockDays.size() == ProductRulesEnum020.ProductRulesEnum020003.getDayNum()) {
					for (String data : notLockDays) {
						HousePriorityVo housePriorityVo = new HousePriorityVo();
						housePriorityVo.setPriorityCode(ProductRulesEnum020.ProductRulesEnum020003.getValue());
						housePriorityVo.setPriorityDate(data);
						String val = priorityValMap.get(ProductRulesEnum020.ProductRulesEnum020003.getValue());
						if (!Check.NuNStrStrict(val)) {
							housePriorityVo.setPriorityDiscount(val);
							housePriorityVo.setPriorityName(ProductRulesEnum020.ProductRulesEnum020003.getName());
							housePriorityMap.put(data, housePriorityVo);
						}

					}
				}
			}
		}

		Date tillDate = housePriorityDto.getTillDate();
		//对截至日期处理
		if (!Check.NuNObj(tillDate) && !lockDateList.contains(DateUtil.dateFormat(tillDate, dateFormatPattern))) {

			Date preTillDate = DateSplitUtil.jumpDate(tillDate, -1);
			if (housePriorityMap.containsKey(DateUtil.dateFormat(tillDate, dateFormatPattern))) {
				housePriorityMap.remove(DateUtil.dateFormat(tillDate, dateFormatPattern));
			}
			if (housePriorityMap.containsKey(DateUtil.dateFormat(preTillDate, dateFormatPattern))) {
				housePriorityMap.remove(DateUtil.dateFormat(preTillDate, dateFormatPattern));
			}

		}
		// JYTJ
		// 当天处理    如果当天是 夹心一天 或 二天  取最高
		/* if (!lockDateList.contains(DateUtil.dateFormat(housePriorityDto.getNowDate(), dateFormatPattern))) {

            HousePriorityVo housePriorityVo = housePriorityMap.get(DateUtil.dateFormat(housePriorityDto.getNowDate(), dateFormatPattern));


			if(!Check.NuNObj(housePriorityVo)){
				String valOther = housePriorityVo.getPriorityDiscount();
				String valCur = priorityValMap.get(ProductRulesEnum020.ProductRulesEnum020001.getValue());
				if(!Check.NuNStrStrict(valOther)&&!Check.NuNStrStrict(valCur)){
					Double valCurD = Double.valueOf(valCur);
					Double valOtherD = Double.valueOf(valOther);
					if(valCurD>valOtherD){
						housePriorityMap.put(DateUtil.dateFormat(housePriorityDto.getNowDate(),dateFormatPattern), setPriorityCur(priorityValMap, housePriorityDto));
					}
				}
			}else{
				housePriorityMap.put(DateUtil.dateFormat(housePriorityDto.getNowDate(),dateFormatPattern), setPriorityCur(priorityValMap, housePriorityDto));
			}

        }*/
		LogUtil.info(LOGGER, "当前房源夹心价格housePriorityMap={}", housePriorityMap.toString());

		return housePriorityMap;
	}


	/**
	 *
	 * 设置当天夹心折扣
	 *
	 * @author yd
	 * @created 2016年12月9日 下午4:01:32
	 *
	 * @param priorityValMap
	 * @param housePriorityDto
	 */
	@Deprecated
	private HousePriorityVo setPriorityCur(Map<String, String> priorityValMap, HousePriorityDto housePriorityDto) {

		HousePriorityVo housePriorityVo = null;
		if (!Check.NuNMap(priorityValMap)) {
			housePriorityVo = new HousePriorityVo();
			String valCur = priorityValMap.get(ProductRulesEnum020.ProductRulesEnum020001.getValue());
			housePriorityVo.setPriorityCode(ProductRulesEnum020.ProductRulesEnum020001.getValue());
			housePriorityVo.setPriorityDate(DateUtil.dateFormat(housePriorityDto.getNowDate(), dateFormatPattern));
			housePriorityVo.setPriorityDiscount(valCur);
			housePriorityVo.setPriorityName(ProductRulesEnum020.ProductRulesEnum020001.getName());
		}
		return housePriorityVo;

	}

	/**
	 *
	 * 获取当前房源的今夜特价的相关信息
	 *
	 * @author lusp
	 * @created 2017年5月12日 下午4:01:32
	 *
	 * @param housePriorityDto
	 */
	@Override
	public String getEffectiveOfJYTJInfo(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseTonightPriceInfoVo houseTonightPriceInfoVo = new HouseTonightPriceInfoVo();
			houseTonightPriceInfoVo.setEffective(false);

			LogUtil.info(LOGGER, "【HouseCommonServiceProxy.getEffectiveOfJYTJInfo】参数={}", paramJson);
			TonightDiscountEntity tonightDiscountEntity = JsonEntityTransform.json2Object(paramJson, TonightDiscountEntity.class);
			if (Check.NuNObj(tonightDiscountEntity)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			if (Check.NuNObj(tonightDiscountEntity.getRentWay())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("RentWay为空");
				return dto.toJsonString();
			}
			if (RentWayEnum.HOUSE.getCode() == tonightDiscountEntity.getRentWay() &&
					Check.NuNStr(tonightDiscountEntity.getHouseFid())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto.toJsonString();
			}
			if (RentWayEnum.ROOM.getCode() == tonightDiscountEntity.getRentWay() &&
					Check.NuNStr(tonightDiscountEntity.getRoomFid())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间FID为空");
				return dto.toJsonString();
			}

			//            //先查询当前房源当天是否已经被锁定
			//            if (houseLockServiceImpl.isHousePayLockCurrentDay(tonightDiscountEntity)) {
			//                dto.putValue("data", houseTonightPriceInfoVo);
			//                return dto.toJsonString();
			//            }

			String resultJson = houseTonightDiscountService.findTonightDiscountByRentway(paramJson);
			DataTransferObject houseTonightDiscountDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (houseTonightDiscountDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "查看日历,获取当前房源今夜特价信息失败,参数paramDto={},msg={}", paramJson, dto.getMsg());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("调用 HouseTonightDiscountService.findTonightDiscountByRentway()  方法失败！");
				return dto.toJsonString();
			}
			TonightDiscountEntity resultDiscountEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "data", TonightDiscountEntity.class);
			if (!Check.NuNObj(resultDiscountEntity)) {

				Date startTime = resultDiscountEntity.getStartTime();
				Date endTime = resultDiscountEntity.getEndTime();

				if (!Check.NuNObj(startTime) && !Check.NuNObj(endTime)) {
					houseTonightPriceInfoVo.setStartTime(resultDiscountEntity.getStartTime());
					houseTonightPriceInfoVo.setEndTime(resultDiscountEntity.getEndTime());
					long startTimeLong = startTime.getTime();
					long endTimeLong = endTime.getTime();
					Date currentDate = new Date();
					long currentDateLong = currentDate.getTime();
					long countdownBegin = startTimeLong - currentDateLong;
					long countdownEnd = endTimeLong - currentDateLong;
					if (currentDateLong < startTimeLong) {
						houseTonightPriceInfoVo.setDiscount(resultDiscountEntity.getDiscount());
						houseTonightPriceInfoVo.setCountdownBegin(countdownBegin);
						houseTonightPriceInfoVo.setCountdownEnd(countdownEnd);
					} else if (currentDateLong >= startTimeLong && currentDateLong <= endTimeLong) {
						houseTonightPriceInfoVo.setEffective(true);
						houseTonightPriceInfoVo.setDiscount(resultDiscountEntity.getDiscount());
						houseTonightPriceInfoVo.setCountdownEnd(countdownEnd);
					}
				}
			}

			dto.putValue("data", houseTonightPriceInfoVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查看日历,获取当前房源今夜特价信息失败,参数paramDto={},e={}", paramJson,e);
		}

		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.order.api.inner.HouseCommonService#getLockFidByLockTime(java.lang.String)
	 */
	@Override
	public String getLockFidByLockTime(String lockTime) {
		DataTransferObject dto = new DataTransferObject();
		try {
			LogUtil.info(LOGGER, "【HouseCommonServiceProxy.getLockFidByLockTime】参数={}", lockTime);
			if (Check.NuNObj(lockTime)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			dto.putValue("houseFids", houseLockServiceImpl.findHouseFidsByLockTime(lockTime));
			dto.putValue("roomFids", houseLockServiceImpl.findRoomFidsByLockTime(lockTime));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询锁定时间条件对应房源fid列表,参数paramDto={}", lockTime);
		}

		return dto.toJsonString();
	}

	/**
	 *
	 * 查询当前房源是否被锁定
	 *
	 * @author lusp
	 * @created 2017年5月17日 下午5:43:59
	 *
	 * @param tonightDiscountEntity
	 * @return
	 */
	@Override
	public String isHousePayLockCurrentDay(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			TonightDiscountEntity tonightDiscountEntity = JsonEntityTransform.json2Object(paramJson, TonightDiscountEntity.class);
			if (Check.NuNObj(tonightDiscountEntity)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			if (Check.NuNObj(tonightDiscountEntity.getRentWay())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("RentWay为空");
				return dto.toJsonString();
			}
			if (RentWayEnum.HOUSE.getCode() == tonightDiscountEntity.getRentWay() &&
					Check.NuNStr(tonightDiscountEntity.getHouseFid())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto.toJsonString();
			}
			if (RentWayEnum.ROOM.getCode() == tonightDiscountEntity.getRentWay() &&
					Check.NuNStr(tonightDiscountEntity.getRoomFid())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间FID为空");
				return dto.toJsonString();
			}
			boolean flag = houseLockServiceImpl.isHousePayLockCurrentDay(tonightDiscountEntity);
			dto.putValue("data", flag);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询当前房源是否被下单支付锁定,参数paramDto={}", paramJson);
		}

		return dto.toJsonString();
	}


	/**
	 *
	 * 根据　用户uid 判断用户是否符合首单立减用户
	 * 1. 用户存在 有效订单且发生过入住
	 * 2. 用户存在有效订单且未发生过入住且违约金大于0
	 * 3. 取 满足1且满足2  如果>0 不是新用户  ==0 是新用户
	 *
	 * @author yd
	 * @created 2017年6月5日 下午4:13:31
	 *
	 * @param uid
	 * @return
	 */
	@Override
	public String isNewUserByOrder(String uid){
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(uid)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		dto.putValue("isNewUser", this.orderCommonServiceImpl.isNewUserByOrder(uid)?0:1);
		return dto.toJsonString();
	}





	/**
	 *
	 * 根据　用户uid 判断用户是否是新人  供首页使用
	 *
	 * 说明： 只判断当前用户是否有订单
	 *
	 * @author yd
	 * @created 2017年6月16日 下午12:05:05
	 *
	 * @param uid
	 * @return
	 */
	@Override
	public String isNewUserByOrderForFirstPage(String uid){
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(uid)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		dto.putValue("isNewUser", this.orderCommonServiceImpl.isNewUserByOrderForFirstPage(uid)?0:1);
		return dto.toJsonString();
	}

	/**
	 *
	 * 查询房源日历记录
	 *
	 * @author zyl
	 * @created 2017年6月27日 下午3:10:09
	 * @param paramJson
	 * @return
	 */
	@Override
	public String getHouseLockDayList(String paramJson) {
		LogUtil.info(LOGGER, "【HouseCommonServiceProxy.getHouseLockDayList】参数={}", paramJson);

		DataTransferObject dto = new DataTransferObject();
		HouseLockRequest houseLockRequest = JsonEntityTransform.json2Object(paramJson, HouseLockRequest.class);
		if (Check.NuNObj(houseLockRequest)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		if (Check.NuNStr(houseLockRequest.getFid())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(houseLockRequest.getRentWay())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("RentWay为空");
			return dto.toJsonString();
		}

		// 从30天前为起始
		houseLockRequest.setStarTime(DateUtil.getTime(-30));
		List<CalendarDataVo> list = houseLockServiceImpl.getHouseLockDayList(houseLockRequest);
		dto.putValue("data", list);
		return dto.toJsonString();
	}

}
