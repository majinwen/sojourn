/**
 * @FileName: HouseJobServiceProxy.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author bushujie
 * @created 2016年4月9日 下午9:05:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseDayRevenueEntity;
import com.ziroom.minsu.entity.house.HouseFollowEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseStatsDayMsgEntity;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst.House;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dto.HouseDfbNoticeDto;
import com.ziroom.minsu.services.house.dto.LandlordRevenueDto;
import com.ziroom.minsu.services.house.entity.HouseFollowVo;
import com.ziroom.minsu.services.house.entity.HouseMonthRevenueVo;
import com.ziroom.minsu.services.house.entity.RoomLandlordVo;
import com.ziroom.minsu.services.house.service.HouseFollowServiceImpl;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.services.house.service.HouseJobServiceImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.house.FollowStatusEnum;
import com.ziroom.minsu.valenum.house.FollowTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>房源相关定时任务代理层</p>
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
@Component("house.houseJobServiceProxy")
public class HouseJobServiceProxy implements HouseJobService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseJobServiceProxy.class);
	
	@Resource(name="house.houseJobServiceImpl")
	private HouseJobServiceImpl houseJobServiceImpl;
	
	@Resource(name="house.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;
	
	@Resource(name="house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;
	
	@Resource(name="house.houseFollowServiceImpl")
	private HouseFollowServiceImpl houseFollowServiceImpl;
	
	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Value("#{'${MAPP_URL}'.trim()}")
	private String MAPP_URL;
	
	@Override
	public String findOverAuditLimitHouseList() {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<HouseBaseMsgEntity> houseList = houseJobServiceImpl.findOverAuditLimitHouse(
					HouseStatusEnum.YFB.getCode(), DateUtil.intervalDate(-House.OVER_AUDIT_DAY_LIMIT, IntervalUnit.HOUR));
			LogUtil.info(LOGGER, "超时未审核房源列表:{},size={}", JsonEntityTransform.Object2Json(houseList), houseList.size());
			dto.putValue("list", houseList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
			
	}

	@Override
	public String houseAuditLimit(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		// 自动上架房源列表
		Map<String, String> houseUpMap = new HashMap<String, String>();
		try {
			List<HouseBaseMsgEntity> houseList = JsonEntityTransform.json2List(paramJson, HouseBaseMsgEntity.class);
			// 更新超时未审核房源为已上架状态
			for (HouseBaseMsgEntity house : houseList) {
				houseJobServiceImpl.updateHouseSJ(house.getFid());
				houseUpMap.put(house.getFid(), house.getLandlordUid());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		} finally {
			dto.putValue("set", houseUpMap.values());
		}
		
		LogUtil.info(LOGGER, "自动上架房源逻辑id列表:{},size={}", JsonEntityTransform.Object2Json(houseUpMap.keySet()),
				houseUpMap.size());
		return dto.toJsonString();
	}
	
	@Override
	public String findOverAuditLimitRoomList() {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<RoomLandlordVo> roomList = houseJobServiceImpl.findOverAuditLimitRoom(
					HouseStatusEnum.YFB.getCode(), DateUtil.intervalDate(-House.OVER_AUDIT_DAY_LIMIT, IntervalUnit.HOUR));
			LogUtil.info(LOGGER, "超时未审核房间列表:{},size={}", JsonEntityTransform.Object2Json(roomList), roomList.size());
			dto.putValue("list", roomList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();

	}

	@Override
	public String roomAuditLimit(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		// 自动上架房间列表
		Map<String, String> roomUpMap = new HashMap<String, String>();
		try {
			List<RoomLandlordVo> roomList = JsonEntityTransform.json2List(paramJson, RoomLandlordVo.class);
			// 更新超时未审核房源为已上架状态
			for (RoomLandlordVo room : roomList) {
				houseJobServiceImpl.updateRoomSJ(room.getFid());
				roomUpMap.put(room.getFid(), room.getLandlordUid());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		} finally {
			dto.putValue("set", roomUpMap.values());
		}
		LogUtil.info(LOGGER, "自动上架房间逻辑id列表:{},size={}", JsonEntityTransform.Object2Json(roomUpMap.keySet()),
				roomUpMap.size());
		return dto.toJsonString();
	}

    @Override
    public String houseDayRevenueStatisticsByInfo(String orderJson) {
    	LogUtil.info(LOGGER, "参数:{}", orderJson);
        //TODO 房源收益列表 需要优化，暂时还未使用 所以先放一放
        DataTransferObject dto=new DataTransferObject();
        // 同步房源日收益列表
		List<HouseDayRevenueEntity> houseDayRevenueList = new ArrayList<HouseDayRevenueEntity>();
		try {
			List<HouseDayRevenueEntity> list = SOAResParseUtil.getListValueFromDataByKey(orderJson, "list",
					HouseDayRevenueEntity.class);
			String runDate = SOAResParseUtil.getStrFromDataByKey(orderJson, "runDate");
			if (Check.NuNCollection(list)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房源日收益列表为空");
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			// 循环保存日收益
			for (HouseDayRevenueEntity he : list) {
				he.setFid(UUIDGenerator.hexUUID());
				he.setStatisticsDateDay(DateUtil.parseDate(runDate, "yyyy-MM-dd"));
				he.setStatisticsDateMonth(Integer.valueOf(DateUtil.dateFormat(he.getStatisticsDateDay(), "MM")));
				he.setStatisticsDateYear(Integer.valueOf(DateUtil.dateFormat(he.getStatisticsDateDay(), "yyyy")));
				houseManageServiceImpl.insertLandlordRevenue(he);
				houseDayRevenueList.add(he);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "同步房源日收益列表:{},size={}", JsonEntityTransform.Object2Json(houseDayRevenueList),
				houseDayRevenueList.size());



		return dto.toJsonString();
    }

	@Override
	public String houseDayRevenueStatistics(String param) {
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto = new DataTransferObject();
		// 同步房源日收益列表
		List<HouseDayRevenueEntity> houseDayRevenueList = new ArrayList<HouseDayRevenueEntity>();
		try {
			Date runDate = DateUtil.parseDate(param, "yyyy-MM-dd");
			String resultJson = "";// orderFinanceService.landlordDayRevenueList(param);
			System.out.println(resultJson);
			List<HouseDayRevenueEntity> list = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list",
					HouseDayRevenueEntity.class);

			// 循环保存日收益
			for (HouseDayRevenueEntity he : list) {
				he.setFid(UUIDGenerator.hexUUID());
				he.setStatisticsDateDay(DateUtil.parseDate(DateUtil.getDayBeforeDate(runDate), "yyyy-MM-dd"));
				he.setStatisticsDateMonth(Integer.valueOf(DateUtil.dateFormat(he.getStatisticsDateDay(), "MM")));
				he.setStatisticsDateYear(Integer.valueOf(DateUtil.dateFormat(he.getStatisticsDateDay(), "yyyy")));
				houseManageServiceImpl.insertLandlordRevenue(he);
				houseDayRevenueList.add(he);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "同步房源日收益列表:{},size={}", JsonEntityTransform.Object2Json(houseDayRevenueList),
				houseDayRevenueList.size());
		return dto.toJsonString();
	}

	/**
	 * 幂等方法
	 */
	@Override
	public String houseMonthRevenueStatistics(String param) {
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto=new DataTransferObject();
		// 逻辑删除房源逻辑id列表
		List<String> logicDeleteList = new ArrayList<String>();
		// 新生成房源月收益列表
		List<HouseMonthRevenueVo> voList = new ArrayList<HouseMonthRevenueVo>();
		try{
			//定时任务运行时间-定时任务每月一号执行
			Date runDate = DateUtil.parseDate(param, "yyyy-MM-dd");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(runDate);
			cal.add( Calendar.MONTH, -1);
			
			Integer statisticsDateYear = cal.get(Calendar.YEAR);
			Integer statisticsDateMonth = cal.get(Calendar.MONTH) + 1;
			
			//查询上月产生日收益的所有房源集合
			List<String> houseBaseFidList = houseManageServiceImpl.findHouseBaseFidListFromHouseDayRevenue(
					statisticsDateYear, statisticsDateMonth);
			if(Check.NuNCollection(houseBaseFidList)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("上月房源月收益列表为空");
				LogUtil.info(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			LogUtil.info(LOGGER, "房源月收益列表:{},size={}", JsonEntityTransform.Object2Json(houseBaseFidList),
					houseBaseFidList.size());
			
			//查询上月已生成月收益的所有房源集合
			List<String> list = houseManageServiceImpl.findHouseBaseFidListFromHouseMonthRevenue(
					statisticsDateYear, statisticsDateMonth);
			LogUtil.info(LOGGER, "已生成月收益房源逻辑id列表:{},size={}", JsonEntityTransform.Object2Json(list), list.size());
			Set<String> houseBaseFidSet = new HashSet<String>(list);
			
			LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
			landlordRevenueDto.setStatisticsDateYear(statisticsDateYear);
			landlordRevenueDto.setStatisticsDateMonth(statisticsDateMonth);
			for (String houseBaseFid : houseBaseFidList) {
				landlordRevenueDto.setHouseBaseFid(houseBaseFid);
				HouseMonthRevenueVo vo = houseManageServiceImpl.calculateRevenue(landlordRevenueDto);
				if(Check.NuNObj(vo)){
					continue;
				}
				
				if(houseBaseFidSet.contains(houseBaseFid)){
					// 表明定时任务已执行,先逻辑删除上次定时任务产生数据
					houseManageServiceImpl.logicDeleteHouseMonthRevenueByHouseBaseFid(landlordRevenueDto);
					logicDeleteList.add(houseBaseFid);
				}
				// 新增房源月收益与房间月收益
				houseManageServiceImpl.insertHouseMonthRevenueVo(vo);
				voList.add(vo);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "删除月收益房源逻辑id列表:{},size={}", JsonEntityTransform.Object2Json(logicDeleteList),
				logicDeleteList.size());
		LogUtil.info(LOGGER, "新生成房源月收益列表:{},size={}", JsonEntityTransform.Object2Json(logicDeleteList),
				logicDeleteList.size());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 待发布 房源 发送提醒短信
	 * 第3天  第7天 第14天 发送短信
	 * 频次：未发布3天、未发布7天、未发布14天。
	 * 
	 * 算法:
	 * 1.定时任务 每天晚上8点执行
	 * 2.查询当前时间往前推14天 7天  3天的的房源（待发布状态）
	 *   A. 当前时间往前推3天到4天的房源 包括第3天 和第4天
	 *   B. 当前时间 往前推7天到8天的房源 包括第7天 和第8天
	 *   C. 当前时间 往前推14天到15天的房源 包括第7天 和第8天
	
	 * 3. 查询3批房源 不在一起 分批发送短信
	 *   
	 *
	 * @author yd
	 * @created 2016年11月22日 上午11:50:11
	 * @return
	 */
	@Override
	public String noticeLanDFBHouseMsg(String time,String day) {
		
		DataTransferObject dto = new DataTransferObject();
		List<HouseDfbNoticeDto> listDfbNotice = null;
		if(!Check.NuNStrStrict(time)
				&&!Check.NuNStrStrict(day)){
			 listDfbNotice = this.houseJobServiceImpl.findNoticeLanDfbHouseMsg(time, Integer.valueOf(day));
		}
		dto.putValue("listDfbNotice", listDfbNotice);
		return dto.toJsonString();
	}

	/**
	 * 保存房源统计信息
	 */
	@Override
	public String houseStats(String paramJson) {
		LogUtil.info(LOGGER, "houseStats param:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try{
			List<HouseStatsDayMsgEntity> list = JsonEntityTransform.json2List(paramJson, HouseStatsDayMsgEntity.class);
			if (Check.NuNCollection(list)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			
			Set<Date> statsDates = new HashSet<>();
			
			List<HouseStatsDayMsgEntity> deleteHouseStatsMsg = new ArrayList<HouseStatsDayMsgEntity>();
			for (HouseStatsDayMsgEntity houseStatsMsg : list) {
				if (Check.NuNObjs(houseStatsMsg, houseStatsMsg.getHouseFid(), houseStatsMsg.getRentWay(), houseStatsMsg.getStatsDate())) {
					deleteHouseStatsMsg.add(houseStatsMsg);
					continue;
				}
				
				if (RentWayEnum.HOUSE.getCode() == houseStatsMsg.getRentWay().intValue()) {
					HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseStatsMsg.getHouseFid());
					if (Check.NuNObj(houseBaseMsg)|| Check.NuNStr(houseBaseMsg.getHouseSn())) {
						LogUtil.info(LOGGER, "houseBaseMsg error, houseBaseFid:{}", houseStatsMsg.getHouseFid());
						deleteHouseStatsMsg.add(houseStatsMsg);
						continue;
					}
					HousePhyMsgEntity housePhyMsg = houseIssueServiceImpl.findHousePhyMsgByHouseBaseFid(houseStatsMsg.getHouseFid());
					if (Check.NuNObj(housePhyMsg)||Check.NuNStr(housePhyMsg.getProvinceCode())
							||Check.NuNStr(housePhyMsg.getCityCode())) {
						LogUtil.info(LOGGER, "housePhyMsg error, houseBaseFid:{}", houseStatsMsg.getHouseFid());
						deleteHouseStatsMsg.add(houseStatsMsg);
						continue;
					}
					houseStatsMsg.setHouseSn(houseBaseMsg.getHouseSn());
					houseStatsMsg.setProvinceCode(housePhyMsg.getProvinceCode());
					houseStatsMsg.setCityCode(housePhyMsg.getCityCode());
				} else if (RentWayEnum.ROOM.getCode() == houseStatsMsg.getRentWay().intValue()) {
					HouseRoomMsgEntity houseRoomMsg = houseManageServiceImpl.getHouseRoomByFid(houseStatsMsg.getHouseFid());
					if (Check.NuNObj(houseRoomMsg) ||Check.NuNStr(houseRoomMsg.getRoomSn())
							||Check.NuNStr(houseRoomMsg.getHouseBaseFid())) {
						LogUtil.info(LOGGER, "houseRoomMsg error, houseRoomFid:{}", houseStatsMsg.getHouseFid());
						deleteHouseStatsMsg.add(houseStatsMsg);
						continue;
					}
					HousePhyMsgEntity housePhyMsg = houseIssueServiceImpl.findHousePhyMsgByHouseBaseFid(houseRoomMsg.getHouseBaseFid());
					if (Check.NuNObj(housePhyMsg)||Check.NuNStr(housePhyMsg.getProvinceCode())
							||Check.NuNStr(housePhyMsg.getCityCode())) {
						LogUtil.info(LOGGER, "housePhyMsg error, houseBaseFid:{}", houseRoomMsg.getHouseBaseFid());
						deleteHouseStatsMsg.add(houseStatsMsg);
						continue;
					}
					houseStatsMsg.setHouseSn(houseRoomMsg.getRoomSn());
					houseStatsMsg.setProvinceCode(housePhyMsg.getProvinceCode());
					houseStatsMsg.setCityCode(housePhyMsg.getCityCode());
				} else {
					LogUtil.info(LOGGER, "rentWay error:{}", houseStatsMsg.getRentWay());
					deleteHouseStatsMsg.add(houseStatsMsg);
					continue;
				}
				
				statsDates.add(houseStatsMsg.getStatsDate());
			}
			
			if (statsDates.size() != 1) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
				LogUtil.info(LOGGER, "statsDates error:{}", JsonEntityTransform.Object2Json(statsDates));
				return dto.toJsonString();
			} else {
				Date statsDate = statsDates.iterator().next();
				houseJobServiceImpl.deleteHouseStatsMsgByStatsDate(statsDate);
			}
			list.removeAll(deleteHouseStatsMsg);
			houseJobServiceImpl.saveHouseStatsList(list);
		}catch (Exception e){
			LogUtil.error(LOGGER, "houseStats error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/**
	 * 获取房东的房源数量,
	 * @author afi
	 * @param uid
	 * @return
	 */
	public String countLandHouseInfo(String uid){
		LogUtil.info(LOGGER, "countLandHouseInfo uid:{}", uid);
		DataTransferObject dto=new DataTransferObject();
		try{
			Long houseNum = houseJobServiceImpl.countLandHouseNum(uid);
			Long houseSkuNum = houseJobServiceImpl.countLandHouseSkuNum(uid);
			dto.putValue("houseNum", ValueUtil.getintValue(houseNum));
			dto.putValue("houseSkuNum",ValueUtil.getintValue(houseSkuNum));
		}catch (Exception e){
			LogUtil.error(LOGGER, "countLandHouseInfo error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();

	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseJobService#houseNotAuditFollowStats(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String houseNotAuditFollowStats(String param) {
		LogUtil.info(LOGGER, "houseNotAuditFollowStats param:{}", param);
		DataTransferObject dto=new DataTransferObject();
		try{
			Set<String> landlordUidSet=new HashSet<String>();
			Map<String, Object> paramMap=(Map<String, Object>) JsonEntityTransform.json2Map(param);
			LogUtil.info(LOGGER, "houseNotAuditFollowStats查询参数：{}",JsonEntityTransform.Object2Json(paramMap));
			List<HouseFollowVo> followList= houseFollowServiceImpl.findServicerFollowHouseList(paramMap);
			LogUtil.info(LOGGER, "houseNotAuditFollowStats结果：{}",JsonEntityTransform.Object2Json(followList));
			for(HouseFollowVo followVo:followList){
				HouseFollowEntity  followEntity=new HouseFollowEntity();
				if (followVo.getRentWay()==RentWayEnum.HOUSE.getCode()) {
					followEntity.setFid(MD5Util.MD5Encode(followVo.getHouseBaseFid()+DateUtil.dateFormat(followVo.getAuditStatusTime(), "yyyyMMddHHmmss")));
				} else if(followVo.getRentWay()==RentWayEnum.ROOM.getCode()) {
					followEntity.setFid(MD5Util.MD5Encode(followVo.getRoomFid()+DateUtil.dateFormat(followVo.getAuditStatusTime(), "yyyyMMddHHmmss")));
				}
				followEntity.setHouseBaseFid(followVo.getHouseBaseFid());
				followEntity.setRoomFid(followVo.getRoomFid());
				followEntity.setRentWay(followVo.getRentWay());
				followEntity.setAuditStatusTime(followVo.getAuditStatusTime());
				followEntity.setAuditCause(followVo.getAuditCause());
				followEntity.setFollowType(FollowTypeEnum.FYWSHTG.getCode());
				followEntity.setFollowStatus(FollowStatusEnum.KFDGJ.getCode());
				followEntity.setCreateFid("001");
				HouseFollowEntity houseFollowEntity=houseFollowServiceImpl.getHouseFollowEntityByFid(followEntity.getFid());
				if(Check.NuNObj(houseFollowEntity)){
					houseFollowServiceImpl.insertHouseFollow(followEntity);
					//收集房东uid
					landlordUidSet.add(followVo.getLandlordUid());
					//发送极光推送
					try{
						Map<String, String> jpushMap=new HashMap<>();
						JpushRequest jpushRequest = new JpushRequest();
						jpushMap.put("{1}", followVo.getRefuseMark());
						jpushRequest.setParamsMap(jpushMap);
						jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
						jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
						jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_OVERTIME_LANDLORD_MSG.getCode()));
						jpushRequest.setTitle("您有审核未通过房源待修改");
						jpushRequest.setUid(followVo.getLandlordUid());
						//自定义消息
						Map<String, String> extrasMap = new HashMap<>();
						extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
						extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_6);
						extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
						extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
						extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
					/*	String url = MAPP_URL+String.format(JpushConst.HOUSE_ROOM_DETAIL_URL,followVo.getHouseBaseFid(),followVo.getRoomFid(),followVo.getRentWay());
						extrasMap.put("url",url);*/
						jpushRequest.setExtrasMap(extrasMap);
						smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
					} catch (Exception e){
						LogUtil.error(LOGGER,"houseNotAuditFollowStats 推消息error:{}",e);
					}
				}
			}
			dto.putValue("landlordUidSet", landlordUidSet);
		}catch (Exception e){
			LogUtil.error(LOGGER, "houseNotAuditFollowStats error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
