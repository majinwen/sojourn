package com.ziroom.minsu.portal.fd.center.house.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.portal.fd.center.common.utils.UserUtils;
import com.ziroom.minsu.portal.fd.center.house.dto.CalendarHouseDto;
import com.ziroom.minsu.portal.fd.center.house.service.HouseUpdateLogService;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.airbnb.vo.AbHouseRelateVo;
import com.ziroom.minsu.services.house.api.inner.*;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.LockHouseCountRequest;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.valenum.common.WeekendPriceEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.IsValidEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum007Enum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020;

import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * <p>房东端房源管理</p>
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
@RequestMapping("/house")
@Controller
public class HouseManController {
	
	private static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
	
	private static final String DATE_FORMAT_MONTH = "yyyy-MM";
	
	private static final String DATE_FORMATE_PATTERN_DAY = "yyyy/MM/dd";

    @Value("#{'${minsu.spider.url}'.trim()}")
    private String minsuSpiderUrl;
	
	@Resource(name="fd.messageSource")
	private MessageSource messageSource;

	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="order.orderUserService")
	private OrderUserService orderUserService;

	@Resource(name="evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;

	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Resource(name="house.housePCService")
	private HousePCService housePCService;
	
	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	@Resource(name = "order.houseCommonService")
	private HouseCommonService houseCommonService;
	
	@Resource(name = "house.abHouseService")
	private AbHouseService abHouseService;

	@Resource(name = "fd.houseUpdateLogService")
	private HouseUpdateLogService houseUpdateLogService;
	
	@Resource(name = "photographer.troyPhotogBookService")
	private TroyPhotogBookService troyPhotogBookService;
	
	/** 存放空置间夜自动定价的配置*/
	private static List<String> gapFlexlist = new ArrayList<>();
	static {
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020001.getValue());
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020002.getValue());
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020003.getValue());
	}

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HouseManController.class);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 
	 * 到房东的房源列表首页
	 *
	 * @author yd
	 * @created 2016年7月21日 下午5:50:57
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/lanHouseList")
	public String lanHouseList(HttpServletRequest request){
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
		try{
			if(!Check.NuNStr(uid)){
				String resultJson = housePCService.countHouseAndRoomNumByUid(uid);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(dto.getCode() == DataTransferObject.SUCCESS){
					Integer houseNum = dto.parseData("houseNum", new TypeReference<Integer>() {});
					Integer roomNum = dto.parseData("roomNum", new TypeReference<Integer>() {});
					request.setAttribute("houseNum", houseNum);
					request.setAttribute("roomNum", roomNum);
				}
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "房东列表展示房源数量异常：e={}", e);
		}
		return "house/lanHouseList";
	}

	/**
	 * 
	 * 查询房东列表
	 *
	 * @author jixd
	 * @created 2016年8月3日 下午7:44:21
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/queryLanHouse")
	@ResponseBody
	public DataTransferObject queryLanHouse(HttpServletRequest request,HouseBaseListDto paramDto){
		DataTransferObject dto =  new DataTransferObject();
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
		if(Check.NuNStr(uid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("用户UID为空");
			return dto;
		}
		//在服务端设置分页参数
		paramDto.setLimit(5);
		//测试用
		paramDto.setLandlordUid(uid);
		if(Check.NuNStr(paramDto.getLandlordUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER, "queryLanHouse-房东uid不存在");
			dto.setMsg("出租方式错误");
			return dto;
		}
		if(Check.NuNObj(paramDto.getRentWay())){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER, "queryLanHouse-错误信息:{}，出租方式错误rentWay={}",paramDto.getRentWay());
			dto.setMsg("出租方式错误");
			return dto;
		}
		try {
			String resultJson = housePCService.getLandlordHouseOrRoomList(JsonEntityTransform.Object2Json(paramDto));
			DataTransferObject houseListDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(houseListDto.getCode() == DataTransferObject.SUCCESS){
				LogUtil.info(LOGGER,"房源查询返回结果："+resultJson);
				List<HouseRoomVo> houseList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomVo.class);
				getHouseRoomVo(houseList);
				int total = SOAResParseUtil.getIntFromDataByKey(resultJson, "total");
				dto.putValue("houseList", houseList);
				dto.putValue("total", total);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(houseListDto.getMsg());
			}
		} catch (SOAParseException e) {
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER, "queryLanHouse-根据房东uid={}查询房源信息失败，rentWay={}",paramDto.getLandlordUid(),paramDto.getRentWay());
			dto.setMsg("查询异常");
			e.printStackTrace();
		}
		return dto;
	}

	
	

	/**
	 * 
	 * 查询房源
	 *
	 * @author jixd
	 * @created 2016年8月22日 下午6:28:03
	 *
	 * @param list
	 * @return
	 */
	private List<HouseRoomVo> getHouseRoomVo(List<HouseRoomVo> list){
		try {
			//30天出租率
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startTime = DateUtil.parseDate(sdf.format(new Date()), "yyyy-MM-dd");
			Date endTime = DateUtils.addDays(startTime, 30);
			for(HouseRoomVo vo : list){
				LockHouseCountRequest request = new LockHouseCountRequest();
				request.setRentWay(vo.getRentWay());
				request.setHouseFid(vo.getHouseBaseFid());
				request.setRoomFid(vo.getHouseRoomFid());
				request.setStartTime(startTime);
				request.setEndTime(endTime);
				
				if(vo.getHouseBookRate() == null || vo.getHouseBookRate() != -1.0){
					//排除房间没有的房源
					DataTransferObject bookRateDto = JsonEntityTransform.json2DataTransferObject(orderUserService.getBookRateByFid(JsonEntityTransform.Object2Json(request)));
					if(bookRateDto.getCode() == DataTransferObject.SUCCESS){
						Double rate = bookRateDto.parseData("rate", new TypeReference<Double>() {});
						vo.setHouseBookRate(rate);
					}
				}else{
					vo.setHouseBookRate(0.0);
				}
				
				if(!"-1".equals(vo.getHouseEvaScore())){
					//查询评论数
					StatsHouseEvaRequest statsHouseEvaRequest=new StatsHouseEvaRequest();
					statsHouseEvaRequest.setHouseFid(vo.getHouseBaseFid());
					statsHouseEvaRequest.setRoomFid(vo.getHouseRoomFid());
					
					statsHouseEvaRequest.setRentWay(vo.getRentWay());
					//LogUtil.info(LOGGER,"查询评论数参数："+JsonEntityTransform.Object2Json(statsHouseEvaRequest));
					String evaluateCountJson=evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));
					List<StatsHouseEvaEntity> evaluateStats=SOAResParseUtil.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
					if(!Check.NuNCollection(evaluateStats)){
						LogUtil.info(LOGGER,"查询评论数结果："+JsonEntityTransform.Object2Json(evaluateStats));
						vo.setHouseEvaScore("无评分");
						StatsHouseEvaEntity statsHouseEvaEntity=evaluateStats.get(0);
						if(statsHouseEvaEntity.getEvaTotal()>0){
							double evalScore = BigDecimalUtil.div(statsHouseEvaEntity.getHouseCleanAva()+statsHouseEvaEntity.getDesMatchAva()+statsHouseEvaEntity.getSafeDegreeAva()+statsHouseEvaEntity.getTrafPosAva()+statsHouseEvaEntity.getCostPerforAva(), 5.0, 1);
							vo.setHouseEvaScore(String.valueOf(evalScore)+"分");
						}
					}
				}else{
					vo.setHouseEvaScore("0分");
				}
				//审核中默认图片处理
				Map<String , HouseFieldAuditLogVo> houseFieldAuditMap = houseUpdateLogService.houseFieldAuditLogVoConvertMap(vo.getHouseBaseFid(), vo.getHouseRoomFid(), vo.getRentWay(), 0);
				if(RentWayEnum.HOUSE.getCode()==vo.getRentWay()){
					if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath())){
		            	String defaultPicJson=houseIssueService.searchHousePicMsgByFid(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath()).getNewValue());
		            	HousePicMsgEntity housePicMsgEntity=SOAResParseUtil.getValueFromDataByKey(defaultPicJson, "obj", HousePicMsgEntity.class);
		            	if(!Check.NuNObj(housePicMsgEntity)){
		            		vo.setDefaultPic(housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix());
		            	}
					}
					if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath())){
						vo.setName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath()).getNewValue());
					}
				} else if(RentWayEnum.ROOM.getCode()==vo.getRentWay()) {
					if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath())){
		            	String defaultPicJson=houseIssueService.searchHousePicMsgByFid(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath()).getNewValue());
		            	HousePicMsgEntity housePicMsgEntity=SOAResParseUtil.getValueFromDataByKey(defaultPicJson, "obj", HousePicMsgEntity.class);
		            	if(!Check.NuNObj(housePicMsgEntity)){
		            		vo.setDefaultPic(housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix());
		            	}
					}
					if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath())){
						vo.setName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath()).getNewValue());
					}
				}
				//审核未通过原因赋值
				HouseBizMsgEntity houseBizMsg = null;
				if(RentWayEnum.HOUSE.getCode()==vo.getRentWay()){
					houseBizMsg = this.getHouseBizMsg(vo.getHouseBaseFid(), vo.getRentWay());
				} else if(RentWayEnum.ROOM.getCode()==vo.getRentWay()) {
					houseBizMsg = this.getHouseBizMsg(vo.getHouseRoomFid(), vo.getRentWay());
				}
				if (!Check.NuNObj(houseBizMsg)) {
					vo.setRefuseReason(houseBizMsg.getRefuseMark());
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取房源评价得分或者出租率失败");
		}
		return list;
	}
	
	/**
	 * 
	 * 查询审核未通过原因
	 *
	 * @author bushujie
	 * @created 2017年10月28日 下午3:21:55
	 *
	 * @param houseFid
	 * @param rentWay
	 * @return
	 */
	private HouseBizMsgEntity getHouseBizMsg(String houseFid, Integer rentWay) {
		HousePicDto param = new HousePicDto();
		if (rentWay.intValue() == RentWayEnum.HOUSE.getCode()) {
			param.setHouseBaseFid(houseFid);
		} else if (rentWay.intValue() == RentWayEnum.ROOM.getCode()) {
			param.setHouseRoomFid(houseFid);
			;
		} else {
			return null;
		}
		String reasonJson = troyPhotogBookService.findHouseBizReason(JsonEntityTransform.Object2Json(param));
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(reasonJson);
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			HouseBizMsgEntity houseBizMsg = dto.parseData("biz", new TypeReference<HouseBizMsgEntity>() {
			});
			return houseBizMsg;
		}
		return null;
	}
	
	/**
	 * 
	 * 显示出租日历
	 *
	 * @author jixd
	 * @created 2016年8月2日 下午3:30:59
	 *
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/{rentWay}/{fid}/calendar")
	public String rentCalendar(HttpServletRequest request,@PathVariable Integer rentWay,@PathVariable String fid) throws SOAParseException{
		
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
		Integer houseStatus=HouseStatusEnum.DFB.getCode();
		HouseBaseDto baseDto = new HouseBaseDto();
		baseDto.setRentWay(rentWay);
		if (rentWay == RentWayEnum.HOUSE.getCode()) {
			baseDto.setHouseFid(fid);
			//查询房源状态
			String houseResultJson=houseIssueService.searchHouseBaseMsgByFid(fid);
			HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(houseResultJson, "obj", HouseBaseMsgEntity.class);
			houseStatus=houseBaseMsgEntity.getHouseStatus();
		}else if(rentWay == RentWayEnum.ROOM.getCode()){
			baseDto.setRoomFid(fid);
			//查询房间状态
			String roomResultJson=houseIssueService.searchHouseRoomMsgByFid(fid);
			HouseRoomMsgEntity houseRoomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomResultJson, "obj", HouseRoomMsgEntity.class);
			houseStatus=houseRoomMsgEntity.getRoomStatus();
		}
		request.setAttribute("houseStatus", houseStatus);
		String dateJson = housePCService.getCalendarDate(JsonEntityTransform.Object2Json(baseDto));
		//获取从上架到截止日期的时间戳
		DataTransferObject dateListDto = JsonEntityTransform.json2DataTransferObject(dateJson);
		if(dateListDto.getCode() == DataTransferObject.SUCCESS){
			String startDate = dateListDto.parseData("startDate", new TypeReference<String>() {});
			String endDate = dateListDto.parseData("endDate", new TypeReference<String>() {});
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
		}
		
		HouseBaseListDto listDto = new HouseBaseListDto();
		listDto.setLandlordUid(uid);
		//获取日历上要展示的房源
		String onlineHouseRoomJson = housePCService.getCalendarHouseList(JsonEntityTransform.Object2Json(listDto));
		DataTransferObject onLineListDto = JsonEntityTransform.json2DataTransferObject(onlineHouseRoomJson);
		CalendarHouseDto selectHouseDto = null;
		
		List<CalendarHouseDto> reList = new ArrayList<CalendarHouseDto>();
		if(onLineListDto.getCode() == DataTransferObject.SUCCESS){
			List<HouseRoomVo> list = onLineListDto.parseData("list", new TypeReference<List<HouseRoomVo>>() {});
			for(HouseRoomVo hrv : list){
				CalendarHouseDto calendarHouse = new CalendarHouseDto();
				calendarHouse.setRentWay(hrv.getRentWay());
				calendarHouse.setName(hrv.getName());
				calendarHouse.setHouseFid(hrv.getHouseBaseFid());
				calendarHouse.setRoomFid(hrv.getHouseRoomFid());
				if(hrv.getRentWay() == RentWayEnum.HOUSE.getCode()){
					if(hrv.getHouseBaseFid().equals(fid)){
						calendarHouse.setIsSelect(1);
						selectHouseDto = calendarHouse;
					}
				}else if(hrv.getRentWay() == RentWayEnum.ROOM.getCode()){
					if(hrv.getHouseRoomFid().equals(fid)){
						calendarHouse.setIsSelect(1);
						selectHouseDto = calendarHouse;
					}
				}
				
				reList.add(calendarHouse);
			}
			if(Check.NuNObj(selectHouseDto)){
				selectHouseDto = new CalendarHouseDto();
			}
			request.setAttribute("selectHR", selectHouseDto);
			request.setAttribute("houseList", reList);
		}else{
			LogUtil.error(LOGGER, "获取已上架房源列表失败,result={}", onlineHouseRoomJson);
		}
		//查询是否放开价格设置的开关判断
		Integer longTermLimit =null;
		try {
			//长租天数 设置
			String longTermLimitStr = cityTemplateService.getTextValue(null, TradeRulesEnum0020.TradeRulesEnum0020001.getValue());
			if(!Check.NuNObj(longTermLimitStr)){
				longTermLimit = SOAResParseUtil.getValueFromDataByKey(longTermLimitStr, "textValue", Integer.class);
			}else{
				longTermLimit = 1000;
			}
			request.setAttribute("longTermLimit", longTermLimit);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "长租入住最小天数查询失败e={}", e);
		}

		AbHouseDto abHouseDto = new AbHouseDto();
		abHouseDto.setHouseFid(baseDto.getHouseFid());
		abHouseDto.setRentWay(baseDto.getRentWay());
		abHouseDto.setRoomFid(baseDto.getRoomFid());
		try {
			String relateJson = abHouseService.findHouseRelateByFid(JsonEntityTransform.Object2Json(abHouseDto));
			AbHouseRelateVo abHouseRelateVo = SOAResParseUtil.getValueFromDataByKey(relateJson,"obj",AbHouseRelateVo.class);
			if (!Check.NuNObj(abHouseRelateVo)){
				request.setAttribute("abHouseRelateVo", abHouseRelateVo);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "查询日历绑定关系失败param={},e={}",JsonEntityTransform.Object2Json(abHouseDto), e);
		}
		return "house/rentCalendar";
	}

	
	/**
	 * 
	 * 获取出租日历数据
	 *
	 * @author jixd
	 * @created 2016年8月2日 上午11:25:10
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/leaseCalendar")
	@ResponseBody
	public DataTransferObject leaseCalendar(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			//开始时间
			Date startDate = DateUtil.parseDate(request.getParameter("startDate"), DATE_FORMATE_PATTERN_DAY);
			//结束时间
			Date endDate = DateUtil.parseDate(request.getParameter("endDate"), DATE_FORMATE_PATTERN_DAY);
			// 房源逻辑id
			String houseBaseFid = request.getParameter("houseBaseFid");
			// 房间逻辑id
			String houseRoomFid = request.getParameter("houseRoomFid");
			String r = request.getParameter("rentWay");
			if(Check.NuNStr(r)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");
				return dto;
			}
			// 房间逻辑id
			Integer rentWay = Integer.valueOf(request.getParameter("rentWay"));
			
			
			/** 查询出租日历特殊价格 **/
			LeaseCalendarDto requestDto = new LeaseCalendarDto();
			requestDto.setHouseBaseFid(houseBaseFid);
            if(!Check.NuNObj(rentWay) && rentWay == RentWayEnum.ROOM.getCode()){
                requestDto.setHouseRoomFid(houseRoomFid);
            }
			requestDto.setStartDate(startDate);
			requestDto.setEndDate(endDate);
			requestDto.setRentWay(rentWay);
			requestDto.setIsValid(IsValidEnum.WEEK_OPEN.getCode());
			
			String houseParams = JsonEntityTransform.Object2Json(requestDto);
			LogUtil.info(LOGGER, "房源参数：{}", houseParams);
			String houseJson = houseManageService.leaseCalendar(houseParams);
			DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
			//判断调用状态
			if(houseDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "invoke houseManageService leaseCalendar failed");
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto;
			}
			/** 查询出租日历特殊价格 **/
			
			/** 查询出租日历出租状态 **/
			HouseLockRequest lockRequest = new HouseLockRequest();
			lockRequest.setFid(houseBaseFid);
			if(!Check.NuNObj(rentWay) && rentWay == RentWayEnum.ROOM.getCode()){
                lockRequest.setRoomFid(houseRoomFid);
            }

			lockRequest.setRentWay(rentWay);
			lockRequest.setStarTime(startDate);
			lockRequest.setEndTime(endDate);
			
			String orderParams = JsonEntityTransform.Object2Json(lockRequest);
			LogUtil.info(LOGGER, "订单参数：{}", orderParams);
			String orderJson = orderUserService.getHouseLockInfo(orderParams);
			DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
			//判断调用状态
			if(orderDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "出租状态获取失败");
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto;
			}
			/** 查询出租日历出租状态 **/
			
			/** 封装返回信息**/
			LeaseCalendarVo leaseCalendarVo = houseDto.parseData("calendarData",
					new TypeReference<LeaseCalendarVo>() {});
			
			Map<String, LinkedHashMap<String,CalendarResponseVo>> monthMap=new LinkedHashMap<String, LinkedHashMap<String,CalendarResponseVo>>();
			for (int i = 0, j = DateUtil.getDatebetweenOfDayNum(startDate, endDate) + 1; i < j; i++) {
				CalendarResponseVo vo = new CalendarResponseVo();
				Date addStart = DateUtil.getTime(startDate, i);
				String dateMonth = DateUtil.dateFormat(addStart, DATE_FORMAT_MONTH);
				String dateDay = DateUtil.dateFormat(addStart, DATE_FORMAT_DAY);
				vo.setDate(dateDay);
				vo.setPrice(leaseCalendarVo.getUsualPrice());
				LinkedHashMap<String, CalendarResponseVo> dayMap=monthMap.get(dateMonth);
				if(!Check.NuNMap(dayMap)){
					dayMap.put(dateDay, vo);
				} else {
					dayMap=new LinkedHashMap<String, CalendarResponseVo>();
					dayMap.put(dateDay, vo);
					monthMap.put(dateMonth, dayMap);
				}
			}
			
			//设置特殊价格
			List<SpecialPriceVo> specialPriceList = leaseCalendarVo.getSpecialPriceList();
			for (SpecialPriceVo specialPriceVo : specialPriceList) {
				Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(specialPriceVo.getSetDate(), DATE_FORMAT_MONTH));
				if(!Check.NuNMap(map)){
					CalendarResponseVo vo = map.get(DateUtil.dateFormat(specialPriceVo.getSetDate(), DATE_FORMAT_DAY));
					if(!Check.NuNObj(vo)){
						vo.setPrice(specialPriceVo.getSetPrice());
					}
				}
			}
			
			//房源当天的基本价格，供后面计算今夜特价时使用
			Integer currentDayBasePrice = null;
			Date currentDate = new Date();
			Map<String, CalendarResponseVo> maptemp=monthMap.get(DateUtil.dateFormat(currentDate, DATE_FORMAT_MONTH));
			if(!Check.NuNMap(maptemp)){
				CalendarResponseVo currentDayVo = maptemp.get(DateUtil.dateFormat(currentDate, DATE_FORMAT_DAY));
				if(!Check.NuNObj(currentDayVo)){
					currentDayBasePrice = currentDayVo.getPrice();
				}
			}
			

			//设置夹心价格
			setHousePriorityDate(monthMap,lockRequest,leaseCalendarVo.getTillDate());
			
			
			//设置今夜特价
			setHouseTonightDiscount(monthMap,requestDto,currentDayBasePrice);
			
			//房源锁定信息列表
			List<HouseLockEntity> houseLockList = orderDto.parseData("houseLock",
					new TypeReference<List<HouseLockEntity>>() {});
			//设置房源状态
			for (HouseLockEntity houseLockEntity : houseLockList) {
				Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_FORMAT_MONTH));
				if(!Check.NuNMap(map)){
					CalendarResponseVo vo = map.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_FORMAT_DAY));
					if(!Check.NuNObj(vo)){
						vo.setStatus(houseLockEntity.getLockType());
					}
				}
			}
			//对象封装
			List<CalendarMonth> calendarList=new ArrayList<CalendarMonth>();
			for(String key:monthMap.keySet()){
				CalendarMonth calendarMonth=new CalendarMonth();
				calendarMonth.setMonthStr(key);
				calendarMonth.getCalendarList().addAll(monthMap.get(key).values());
				calendarList.add(calendarMonth);
			}
			/** 封装返回信息**/
			LogUtil.info(LOGGER, "结果：" + JsonEntityTransform.Object2Json(calendarList));
			dto.putValue("list",calendarList);
			dto.putValue("tillDate", DateUtil.dateFormat(leaseCalendarVo.getTillDate(), DATE_FORMAT_DAY));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto;
	}
	
	/**
	 * 设置今夜特价价格
	 * @author lusp
	 * @created 2017年5月10日 上午20:29:14
	 * @param monthMap
	 * @param requestDto
	 */
	private void setHouseTonightDiscount(
			Map<String, LinkedHashMap<String, CalendarResponseVo>> monthMap,
			LeaseCalendarDto requestDto,Integer basePrice) {
		if(!Check.NuNMap(monthMap)&&!Check.NuNObj(requestDto)){
			TonightDiscountEntity tonightDiscountEntity = new TonightDiscountEntity();
			try {
				tonightDiscountEntity.setHouseFid(requestDto.getHouseBaseFid());
				if(!Check.NuNObj(requestDto.getRentWay()) && requestDto.getRentWay() == RentWayEnum.ROOM.getCode()){
					tonightDiscountEntity.setRoomFid(requestDto.getHouseRoomFid());
				}
				tonightDiscountEntity.setRentWay(requestDto.getRentWay());
				String paramJson = JsonTransform.Object2Json(tonightDiscountEntity);
				String resultJson = houseCommonService.getEffectiveOfJYTJInfo(paramJson);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				//判断调用状态
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "查看日历,获取当前房源今夜特价信息失败,参数paramDto={},msg={}", paramJson,dto.getMsg());
					return;
				}
				HouseTonightPriceInfoVo houseTonightPriceInfoVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "data", HouseTonightPriceInfoVo.class);
				
				if(Check.NuNObj(houseTonightPriceInfoVo)){
					return;
				}
				if(houseTonightPriceInfoVo.isEffective()){
					double discount = houseTonightPriceInfoVo.getDiscount();
					if (Check.NuNObj(discount)) {
						return;
					}
					Date date = new Date();
					//当前系统时间大于今夜特价生效时间，今夜特价生效
					if(Check.NuNObj(basePrice)){
						return;
					}
					Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(date, DATE_FORMAT_MONTH));
					if(!Check.NuNMap(map)){
						CalendarResponseVo vo = map.get(DateUtil.dateFormat(date, DATE_FORMAT_DAY));
						if(!Check.NuNObj(vo)){
							if(discount>0&discount<=1){
								Double priceD = BigDecimalUtil.mul(discount, basePrice);
								int price = priceD.intValue()/100;
								vo.setPrice(price*100);
							}
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "查看日历,获取当前房源今夜特价信息失败,参数paramDto={}", requestDto);
			}
		}
	}

	/**
	 * 
	 * 设置房源夹心价格 查看日期  是当前时间 往后推 6个月  
	 *
	 * @author yd
	 * @created 2016年12月8日 上午10:29:14
	 *
	 * @param monthMap
	 */
	private void setHousePriorityDate(Map<String, LinkedHashMap<String,CalendarResponseVo>> monthMap,HouseLockRequest lockRequest,Date tillDate ){

		if(!Check.NuNMap(monthMap)&&!Check.NuNObj(lockRequest)){

			try {
				
				Date curDate = new Date();
				if(Check.NuNObj(tillDate))tillDate = lockRequest.getEndTime();
				if(Check.NuNObj(lockRequest.getStarTime()))lockRequest.setStarTime(curDate);
				
				if(!(lockRequest.getStarTime().getTime()-curDate.getTime()>=0)){
					lockRequest.setStarTime(curDate);
				}
				Date endDate = sdf.parse(sdf.format(lockRequest.getEndTime()));
				endDate =  DateSplitUtil.jumpDate(endDate, ProductRulesEnum020.ProductRulesEnum020003.getDayNum()+1);
				
				HousePriorityDto housePriorityDt = new HousePriorityDto();
				housePriorityDt.setHouseBaseFid(lockRequest.getFid());
				housePriorityDt.setRentWay(lockRequest.getRentWay());
				housePriorityDt.setHouseRoomFid(lockRequest.getRoomFid());
				housePriorityDt.setStartDate(curDate);
				housePriorityDt.setEndDate(endDate);
				housePriorityDt.setTillDate(tillDate);
				housePriorityDt.setNowDate(curDate);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseCommonService.findPriorityDate(JsonEntityTransform.Object2Json(housePriorityDt)));

				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "查看日历,夹心价格获取失败,参数paramDto={},msg={}", JsonEntityTransform.Object2Json(lockRequest),dto.getMsg());
					return ;
				}

				Map<String,HousePriorityVo> housePriorityMap  = dto.parseData("housePriorityMap", new TypeReference<Map<String,HousePriorityVo> >() {
				});
				if(!Check.NuNMap(housePriorityMap)){
					for (Map.Entry<String, HousePriorityVo>  entry : housePriorityMap.entrySet()) {
						String key = entry.getKey();
						Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(DateUtil.parseDate(key, DATE_FORMAT_MONTH), DATE_FORMAT_MONTH));
						if(!Check.NuNMap(map)){
							CalendarResponseVo vo = map.get(key);
							HousePriorityVo val = entry.getValue();
							if(!Check.NuNObj(vo)&&!Check.NuNObj(val)
									&&!Check.NuNStrStrict(val.getPriorityDiscount())){
								vo.setPrice(DataFormat.getPriorityPrice(val.getPriorityDiscount(), vo.getPrice()));
							}
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "查看日历-夹心价格设置失败paramDto={}", JsonEntityTransform.Object2Json(lockRequest));
			}

		}
	}

	private List<HouseConfMsgEntity> searchGapFlexList() throws SOAParseException {
		List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();
		for(String gapPrice : gapFlexlist){
			/** 在配置对象中添加要插入的value*/
			HouseConfMsgEntity confEntity = new HouseConfMsgEntity();
			confEntity.setDicCode(gapPrice);
			String textValue = cityTemplateService.getTextValue(null, gapPrice);
			String text = SOAResParseUtil.getValueFromDataByKey(textValue, "textValue", String.class);
			text = text.charAt(text.length() - 1)+"";
			confEntity.setDicVal(text);
			/** 放入集合*/
			confList.add(confEntity);
		}
		return confList;
	}

	/**
	 * 到价格设置 页面
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/22 16:34
	 */
	@RequestMapping("/toPriceDetail")
	public String toPriceDetail(HttpServletRequest request,SpecialPriceDto sp) throws SOAParseException {
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
		//用于查询空置间夜自动定价以及长租优惠
		HouseConfMsgEntity confMsgEntity = new HouseConfMsgEntity();
		Map<String, String> paramMap = new HashMap<String, String>();
		if (Check.NuNObj(sp.getHouseBaseFid()) || Check.NuNObj(sp.getRentWay())) {
			return "error/500";
		}
		if(Check.NuNStrStrict(sp.getHouseRoomFid()) && RentWayEnum.ROOM.getCode()==sp.getRentWay()){
			return "error/500";
		}
		if(!Check.NuNStrStrict(sp.getHouseRoomFid())){
			confMsgEntity.setRoomFid(sp.getHouseRoomFid());
			paramMap.put("roomFid", sp.getHouseRoomFid());
			request.setAttribute("houseRoomFid",sp.getHouseRoomFid());
		}
		if(Check.NuNStrStrict(sp.getHouseBaseFid()) && RentWayEnum.HOUSE.getCode()==sp.getRentWay()){
			return "error/500";
		}else{
			confMsgEntity.setHouseBaseFid(sp.getHouseBaseFid());
			paramMap.put("houseFid", sp.getHouseBaseFid());
		}
		request.setAttribute("houseBaseFid",sp.getHouseBaseFid());
		request.setAttribute("rentWay",sp.getRentWay());
		try{
			/** 去模板查询空置间夜自动定价配置，返回给页面展示*/
			request.setAttribute("gapList",searchGapFlexList());
			/** */
			//房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
			request.setAttribute("priceLow", priceLow);

			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
			request.setAttribute("priceHigh", priceHigh);

			/** 周末价格start*/
			String weekendPriceJson = houseManageService.findWeekPriceByFid(JsonEntityTransform.Object2Json(paramMap));
			List<HousePriceWeekConfEntity> weekendPriceList = SOAResParseUtil
					.getListValueFromDataByKey(weekendPriceJson, "list", HousePriceWeekConfEntity.class);
			request.setAttribute("weekendPriceList", weekendPriceList);
			int weekendPriceFlag = 0;
			if (!Check.NuNCollection(weekendPriceList)) {
				weekendPriceFlag = 2;
				List<Integer> setWeek = new ArrayList<Integer>();
				Set<Integer> priceVal = new HashSet<Integer>();
				for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
					if (!Check.NuNObj(housePriceWeekConf.getIsDel())
							&& housePriceWeekConf.getIsDel().intValue() == YesOrNoEnum.NO.getCode()
							&& !Check.NuNObj(housePriceWeekConf.getIsValid())
							&& housePriceWeekConf.getIsValid().intValue() == YesOrNoEnum.YES.getCode()) {
						weekendPriceFlag = 1;
					}
					// 设置时间集合
					if (!Check.NuNObj(housePriceWeekConf.getSetWeek())) {
						setWeek.add(housePriceWeekConf.getSetWeek());
					}
					// 周末价格
					if (!Check.NuNObj(housePriceWeekConf.getPriceVal())) {
						priceVal.add(housePriceWeekConf.getPriceVal());
					}
				}
				WeekendPriceEnum weekendPrice = WeekendPriceEnum.getEnumByColl(setWeek);
				if (!Check.NuNObj(weekendPrice)) {
					request.setAttribute("weekendPriceValue", weekendPrice.getValue());
					request.setAttribute("weekendPriceText", weekendPrice.getText());
				}
				if (priceVal.size() == 1) {
					request.setAttribute("weekendPrice", priceVal.iterator().next()/100);
				}
			}
			request.setAttribute("weekendPriceFlag", weekendPriceFlag);
			List<Map<String, Object>> weekenddata = WeekendPriceEnum.getWeekenddata();
			request.setAttribute("weekendData", weekenddata);
			/** 周末价格end*/

			//分别给页面返回空置间夜自动定价，折扣优惠的集合
			//开关打开   is_del : 0,表示打开开关  2,表示关闭开关
			confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
			confMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			String sevenJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
			List<HouseConfMsgEntity> sevenList = SOAResParseUtil.getListValueFromDataByKey(sevenJson, "list", HouseConfMsgEntity.class);
			if(!Check.NuNCollection(sevenList)){
				request.setAttribute("sevenRate",sevenList.get(0));
			}/*else{
				request.setAttribute("sevenRate",null);
			}*/
			confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
			String thirtyJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
			List<HouseConfMsgEntity> thirtyList = SOAResParseUtil.getListValueFromDataByKey(thirtyJson, "list", HouseConfMsgEntity.class);
			if(!Check.NuNCollection(thirtyList)){
				request.setAttribute("thirtyRate",thirtyList.get(0));
			}
			confMsgEntity.setDicCode(ProductRulesEnum020.ProductRulesEnum020002.getValue());
			String gapFlexJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
			List<HouseConfMsgEntity> gapFlexList = SOAResParseUtil.getListValueFromDataByKey(gapFlexJson, "list", HouseConfMsgEntity.class);
			request.setAttribute("gapFlexList",gapFlexList);
			if(!Check.NuNCollection(gapFlexList) && gapFlexList.size()>0){
				request.setAttribute("gapSwitch",0);//打开了空置间夜自动定价
			}
			//是否开启今日特惠折扣20170418 busj
			confMsgEntity.setDicCode(ProductRulesEnum020.ProductRulesEnum020001.getValue());
			String gapFlexTJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
			List<HouseConfMsgEntity> gapFlexTList = SOAResParseUtil.getListValueFromDataByKey(gapFlexTJson, "list", HouseConfMsgEntity.class);
			if(!Check.NuNCollection(gapFlexTList)&&gapFlexTList.size()>0){
				request.setAttribute("gapFlexT", 0);//打开了今日特惠折扣
			}
			//开关关闭 的情况取值
			confMsgEntity.setIsDel(IsDelEnum.PRIORITY.getCode());
			confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
			String seven = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
			List<HouseConfMsgEntity> sevenRate = SOAResParseUtil.getListValueFromDataByKey(seven, "list", HouseConfMsgEntity.class);
			if(!Check.NuNCollection(sevenRate)){
				request.setAttribute("seven",sevenRate.get(0));
			}
			confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
			String thirty = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
			List<HouseConfMsgEntity> thirtyRate = SOAResParseUtil.getListValueFromDataByKey(thirty, "list", HouseConfMsgEntity.class);
			if(!Check.NuNCollection(thirtyRate)){
				request.setAttribute("thirty",thirtyRate.get(0));
			}
			//判断插入还是更新的标志  0：插入   1：更新
			Integer statFlag = 0;
			if(!Check.NuNCollection(sevenList) || !Check.NuNCollection(thirtyList) || !Check.NuNCollection(thirtyRate) ||!Check.NuNCollection(sevenRate)){
				statFlag = 1;
			}
			request.setAttribute("statFlag",statFlag);

			//通过判断rentWay,分别取房间房源表中查询普通价格
			if(RentWayEnum.ROOM.getCode()==sp.getRentWay()){
				String roomJson = houseIssueService.searchHouseRoomMsgByFid(sp.getHouseRoomFid());
				HouseRoomMsgEntity roomEntity = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				request.setAttribute("roomPrice",roomEntity.getRoomPrice()/100);
			}else if(RentWayEnum.HOUSE.getCode()==sp.getRentWay()){
				String house = houseIssueService.searchHouseBaseMsgByFid(sp.getHouseBaseFid());
				HouseBaseMsgEntity houseEntyty = SOAResParseUtil.getValueFromDataByKey(house, "obj", HouseBaseMsgEntity.class);
				request.setAttribute("housePrice",houseEntyty.getLeasePrice()/100);
			}

			/** 切换房源start*/
			//获取已上架房源列表
			HouseBaseListDto listDto = new HouseBaseListDto();
			listDto.setLandlordUid(uid);
			//获取日历上要展示的房源
			String onlineHouseRoomJson = housePCService.getCalendarHouseList(JsonEntityTransform.Object2Json(listDto));
			CalendarHouseDto selectHouseDto = null;
			DataTransferObject onLineListDto=JsonEntityTransform.json2DataTransferObject(onlineHouseRoomJson);
			List<CalendarHouseDto> reList = new ArrayList<CalendarHouseDto>();
			if(onLineListDto.getCode() == DataTransferObject.SUCCESS){
				List<HouseRoomVo> list = SOAResParseUtil.getListValueFromDataByKey(onlineHouseRoomJson, "list", HouseRoomVo.class);
				for(HouseRoomVo hrv : list){
					CalendarHouseDto calendarHouse = new CalendarHouseDto();
					calendarHouse.setRentWay(hrv.getRentWay());
					calendarHouse.setName(hrv.getName());
					calendarHouse.setHouseFid(hrv.getHouseBaseFid());
					calendarHouse.setRoomFid(hrv.getHouseRoomFid());
					if(hrv.getRentWay() == RentWayEnum.HOUSE.getCode()){
						if(hrv.getHouseBaseFid().equals(sp.getHouseBaseFid())){
							calendarHouse.setIsSelect(1);
							selectHouseDto = calendarHouse;
						}
					}else if(hrv.getRentWay() == RentWayEnum.ROOM.getCode()){
						if(hrv.getHouseRoomFid().equals(sp.getHouseRoomFid())){
							calendarHouse.setIsSelect(1);
							selectHouseDto = calendarHouse;
						}
					}

					reList.add(calendarHouse);
				}
				if(Check.NuNObj(selectHouseDto)){
					selectHouseDto = new CalendarHouseDto();
				}
				request.setAttribute("selectHR", selectHouseDto);
				request.setAttribute("houseList", reList);
			}
			/** 切换房源end*/
		}catch (Exception e){
			LogUtil.error(LOGGER,"错误信息:{}",e);
			return "error/500";
		}
		return "house/priceDetail";
	}

	/**
	 * 保存价格设置
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/22 11:02
	 */
	@RequestMapping("/savePriceSet")
	@ResponseBody
	public DataTransferObject savePriceSet(HttpServletRequest request, SpecialPriceDto sp) {
		DataTransferObject dto = new DataTransferObject();
		try{
			if (Check.NuNObj(sp.getHouseBaseFid()) || Check.NuNObj(sp.getRentWay())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("传入参数有误");
				return dto;
			}
			//获取登录用户uid
			String uid=UserUtils.getCurrentUid();
			if (Check.NuNStr(uid)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("登录用户信息不存在");
				return dto;
			}
			//房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			Integer priceLow = SOAResParseUtil.getIntFromDataByKey(priceLowJson, "textValue");
			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			Integer priceHigh = SOAResParseUtil.getIntFromDataByKey(priceHighJson, "textValue");

			request.setAttribute("priceHigh", priceHigh);

			/** 周末价格START */
			if (sp.isInsert()) {
				if (Check.NuNCollection(sp.getSetTime())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("周末时间不能为空");
					return dto;
				}
				if (Check.NuNObj(sp.getSpecialPrice())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("周末价格不能为空");
					return dto;
				}
				if (!Check.NuNObjs(priceLow, priceHigh)
						&& (sp.getSpecialPrice().intValue() < priceLow.intValue()
						|| sp.getSpecialPrice().intValue() > priceHigh.intValue())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("周末价格超出限制");
					return dto;
				}
				HousePriceWeekConfDto weekPriceDto = new HousePriceWeekConfDto();
				weekPriceDto.setCreateUid(uid);
				weekPriceDto.setRentWay(sp.getRentWay());
				weekPriceDto.setPriceVal((int)BigDecimalUtil.mul(sp.getSpecialPrice().intValue(), 100));
				weekPriceDto.setHouseBaseFid(sp.getHouseBaseFid());
				if(sp.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
					weekPriceDto.setHouseRoomFid(sp.getHouseRoomFid());
				}

				Set<Integer> weekSet = new HashSet<>();
				List<String> weekList = sp.getSetTime();
				for (String weekStr : weekList) {
					weekSet.add(Integer.valueOf(weekStr));
				}
				weekPriceDto.setSetWeeks(weekSet);
				houseManageService.saveHousePriceWeekConf(JsonEntityTransform.Object2Json(weekPriceDto));
			} else if (sp.isUpdate()) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("houseFid", sp.getHouseBaseFid());
				paramMap.put("roomFid", sp.getHouseRoomFid());
				String weekendPriceJson = houseManageService.findWeekPriceByFid(JsonEntityTransform.Object2Json(paramMap));
				List<HousePriceWeekConfEntity> weekendPriceList = SOAResParseUtil
						.getListValueFromDataByKey(weekendPriceJson, "list", HousePriceWeekConfEntity.class);
				if (!Check.NuNCollection(weekendPriceList)) {
					for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
						housePriceWeekConf.setIsValid(YesOrNoEnum.NO.getCode());
						housePriceWeekConf.setRentWay(sp.getRentWay());
					}
					houseManageService.updateHousePriceWeekListByFid(JsonEntityTransform.Object2Json(weekendPriceList));
				}
			}
			/** 周末价格END */

			//普通价格判断
			Integer price = 0;
			if(sp.getRentWay()==RentWayEnum.ROOM.getCode() && !Check.NuNObj(sp.getRoomPrice())){
				price = sp.getRoomPrice();
			}
			if(sp.getRentWay()==RentWayEnum.HOUSE.getCode() && !Check.NuNObj(sp.getLeasePrice())){
				price = sp.getLeasePrice();
			}
			if (!Check.NuNObjs(priceLow, priceHigh)
					&& (price.intValue() < priceLow.intValue()
					|| price.intValue() > priceHigh.intValue())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("普通价格超出限制");
				return dto;
			}
			//满天折扣率判断
			if(!Check.NuNObjs(sp.getSevenDiscountRate(),sp.getThirtyDiscountRate())){
				if(sp.getSevenDiscountRate().intValue() <= 0 || sp.getSevenDiscountRate().intValue() >= 100){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("输入的满7天折扣率非法");
					return dto;
				}
				if(sp.getThirtyDiscountRate().intValue() <= 0 || sp.getThirtyDiscountRate().intValue() >= 100){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("输入的满30天折扣率非法");
					return dto;
				}
			}
			List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();
			//关于空置间夜自动定价和折扣率的处理
			confList = confListAddM(sp,dto,confList);
			/** 插入配置表操作(插入和修改分离)*/
			houseIssueService.saveHouseConfList(JsonEntityTransform.Object2Json(confList));
			//更新普通价格(普通价格需要上限和下限的判断)
			updateOrdinaryPrice(sp);


		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/** 更新普通价格*/
	private void updateOrdinaryPrice(SpecialPriceDto sp){
		if(!Check.NuNObj(sp.getHouseBaseFid()) && sp.getRentWay()==RentWayEnum.HOUSE.getCode()){
			//整租，去base表更新房价
			HouseBaseMsgEntity houseBase = new HouseBaseMsgEntity();
			houseBase.setFid(sp.getHouseBaseFid());
			houseBase.setLeasePrice(sp.getLeasePrice()*100);
			houseIssueService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBase));
		}else if(!Check.NuNObj(sp.getHouseRoomFid()) && sp.getRentWay()==RentWayEnum.ROOM.getCode()){
			//分租，去room表更新房价
			HouseRoomMsgEntity houseRoom = new HouseRoomMsgEntity();
			houseRoom.setFid(sp.getHouseRoomFid());
			houseRoom.setRoomPrice(sp.getRoomPrice()*100);
			houseIssueService.updateHouseRoomMsg(JsonEntityTransform.Object2Json(houseRoom));
		}
	}

	/** 关于添加灵活间隙定价，折扣率到集合的方法*/
	private List<HouseConfMsgEntity> confListAddM(SpecialPriceDto sp,DataTransferObject dto,List<HouseConfMsgEntity> confList) throws SOAParseException {
//		/** 空置间夜自动定价间隙价格插入三条记录，用户每设置一个折扣率增加插入一条*/
//		//灵活以及间隙价格开关判断(0：关闭   1：打开)
//		Integer gapFlexIsDel = IsDelEnum.PRIORITY.getCode();//默认开关空置间夜自动定价以及间隙价格开关是关闭的
//		if(sp.getGapAndFlexiblePrice()==1){
//			gapFlexIsDel = IsDelEnum.NOT_DEL.getCode(); //如果用户打开了开关  is_del 值为0  表示默认值也就是打开开关
//		}
		//循环置入三种不同的价格
		for(String gapPrice : gapFlexlist){
			/** 在配置对象中添加要插入的value*/
			HouseConfMsgEntity confEntity = new HouseConfMsgEntity();
			confEntity.setFid(UUIDGenerator.hexUUID());
			confEntity.setHouseBaseFid(sp.getHouseBaseFid());
			if(!Check.NuNStrStrict(sp.getHouseRoomFid()) && RentWayEnum.ROOM.getCode()==sp.getRentWay()){
				confEntity.setRoomFid(sp.getHouseRoomFid());
			}else{
				confEntity.setRoomFid(null);
			}
			confEntity.setBedFid(null);
			confEntity.setDicCode(gapPrice);
			String textValue = cityTemplateService.getTextValue(null, gapPrice);
			String text = SOAResParseUtil.getValueFromDataByKey(textValue, "textValue", String.class);
			confEntity.setDicVal(text);
			//默认关闭灵活定价
			confEntity.setIsDel(IsDelEnum.PRIORITY.getCode());
			//4月7号修改，判断是否开启今日特惠折扣
			if(gapPrice.equals(ProductRulesEnum020.ProductRulesEnum020001.getValue())&&sp.getDayDiscount()==1){
				confEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			}
			if((gapPrice.equals(ProductRulesEnum020.ProductRulesEnum020002.getValue())||gapPrice.equals(ProductRulesEnum020.ProductRulesEnum020002.getValue()))&&sp.getFlexDiscount()==1){
				confEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			}
			/** 放入集合*/
			confList.add(confEntity);
		}
		/** 折扣率判断*/
		Integer fullDayIsDel = IsDelEnum.PRIORITY.getCode();//默认满天折扣率的开关也是关闭的
		if(sp.getFullDayRate()==1){
			fullDayIsDel = IsDelEnum.NOT_DEL.getCode();//用户打开了开关
		}
		//满七天的判断处理
		HouseConfMsgEntity sevenEntity = new HouseConfMsgEntity();
		sevenEntity.setFid(UUIDGenerator.hexUUID());
		sevenEntity.setHouseBaseFid(sp.getHouseBaseFid());
		if(!Check.NuNObj(sp.getHouseRoomFid()) && (RentWayEnum.ROOM.getCode()==sp.getRentWay()) ){
			sevenEntity.setRoomFid(sp.getHouseRoomFid());
		}
		sevenEntity.setBedFid(null);
		sevenEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
		if(!Check.NuNObj(sp.getSevenDiscountRate())){
			//如果用户输入满7天折扣率
			sevenEntity.setDicVal(String.valueOf(sp.getSevenDiscountRate()));
		}else{
			//如果用户没有输入，给一个默认值-1代表用户未设置具体的折扣率
			sevenEntity.setDicVal(String.valueOf(-1));
		}
		sevenEntity.setIsDel(fullDayIsDel);
		/** 放入集合*/
		confList.add(sevenEntity);
		//满30天的判断处理
		HouseConfMsgEntity thirtyEntity = new HouseConfMsgEntity();
		thirtyEntity.setFid(UUIDGenerator.hexUUID());
		thirtyEntity.setHouseBaseFid(sp.getHouseBaseFid());
		if(!Check.NuNObj(sp.getHouseRoomFid()) && (RentWayEnum.ROOM.getCode()==sp.getRentWay()) ){
			thirtyEntity.setRoomFid(sp.getHouseRoomFid());
		}
		thirtyEntity.setBedFid(null);
		thirtyEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
		if(!Check.NuNObj(sp.getThirtyDiscountRate())){
			//用户输入了满30天折扣率
			thirtyEntity.setDicVal(String.valueOf(sp.getThirtyDiscountRate()));
		}else{
			//如果用户没有输入，给一个默认值-1代表用户未设置具体的折扣率
			thirtyEntity.setDicVal(String.valueOf(-1));
		}
		thirtyEntity.setIsDel(fullDayIsDel);
		/** 放入集合*/
		confList.add(thirtyEntity);

		return confList;
	}

	/**
	 * 修改价格设置
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/22 10:02
	 */
	@RequestMapping("/updatePriceSet")
	@ResponseBody
	public DataTransferObject updatePriceSet(HttpServletRequest request,SpecialPriceDto sp){
		DataTransferObject dto = new DataTransferObject();
		//处理null字符串的问题
		if("null".equals(sp.getHouseRoomFid())){
			sp.setHouseRoomFid(null);
		}
		//控制灵活间隙价格开关实体
		HouseConfMsgEntity gapFlexEntity = new HouseConfMsgEntity();
		if(Check.NuNObj(sp.getHouseBaseFid()) || Check.NuNObj(sp.getRentWay())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("传入参数有误");
			return dto;
		}else{ gapFlexEntity.setHouseBaseFid(sp.getHouseBaseFid()); }
		if(Check.NuNObj(sp.getHouseRoomFid()) && (RentWayEnum.ROOM.getCode()==sp.getRentWay()) ){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("未传入房间fid");
			return dto;
		}else{ gapFlexEntity.setRoomFid(sp.getHouseRoomFid()); }
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
		if (Check.NuNStr(uid)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("登录用户信息不存在");
			return dto;
		}
		try{
			//房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum007Enum.ProductRulesEnum007001.getValue());
			Integer priceLow = SOAResParseUtil.getIntFromDataByKey(priceLowJson, "textValue");

			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum007Enum.ProductRulesEnum007002.getValue());
			Integer priceHigh = SOAResParseUtil.getIntFromDataByKey(priceHighJson, "textValue");

			//如果用户打开了灵活间隙价格开关
			if(sp.getGapAndFlexiblePrice() == 1){
				gapFlexEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());//更新为打开 0
			}else{
				gapFlexEntity.setIsDel(IsDelEnum.PRIORITY.getCode());//更新为关闭 2
			}
			//今日特惠价格和空置间夜自动折扣是否开启判断 4月7号
			List<HouseConfMsgEntity> confList=new ArrayList<>();
			for(String gapFlex:gapFlexlist){
				HouseConfMsgEntity houseConfMsgEntity=new HouseConfMsgEntity();
				houseConfMsgEntity.setHouseBaseFid(sp.getHouseBaseFid());
				houseConfMsgEntity.setRoomFid(sp.getHouseRoomFid());
				houseConfMsgEntity.setDicCode(gapFlex);
				houseConfMsgEntity.setIsDel(IsDelEnum.PRIORITY.getCode());
				if(gapFlex.equals(ProductRulesEnum020.ProductRulesEnum020001.getValue())&&sp.getDayDiscount()==1){
					houseConfMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
				}
				if((gapFlex.equals(ProductRulesEnum020.ProductRulesEnum020002.getValue())||gapFlex.equals(ProductRulesEnum020.ProductRulesEnum020003.getValue()))&&sp.getFlexDiscount()==1){
					houseConfMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
				}
				confList.add(houseConfMsgEntity);
			}
			//更新灵活间隙价格开关状态
			houseIssueService.updateHouseConfList(JsonEntityTransform.Object2Json(confList));
			//根据fid 和用户输入的折扣率去更新数据
			houseIssueService.saveOrUpHouseConf(JsonEntityTransform.Object2Json(sp));
			//普通价格判断
			Integer price = 0;
			if(sp.getRentWay()==RentWayEnum.ROOM.getCode() && !Check.NuNObj(sp.getRoomPrice())){
				price = sp.getRoomPrice();
			}
			if(sp.getRentWay()==RentWayEnum.HOUSE.getCode() && !Check.NuNObj(sp.getLeasePrice())){
				price = sp.getLeasePrice();
			}
			if (!Check.NuNObjs(priceLow, priceHigh)
					&& (price.intValue() < priceLow.intValue()
					|| price.intValue() > priceHigh.intValue())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("普通价格超出限制");
				return dto;
			}
			//更新普通价格
			updateOrdinaryPrice(sp);
			/** 周末价格START */
			if (sp.isInsert()) {
				if (Check.NuNCollection(sp.getSetTime())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("周末时间不能为空");
					return dto;
				}
				if (Check.NuNObj(sp.getSpecialPrice())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("周末价格不能为空");
					return dto;
				}
				if (!Check.NuNObjs(priceLow, priceHigh)
						&& (sp.getSpecialPrice().intValue() < priceLow.intValue()
						|| sp.getSpecialPrice().intValue() > priceHigh.intValue())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("周末价格超出限制");
					return dto;
				}
				HousePriceWeekConfDto weekPriceDto = new HousePriceWeekConfDto();
				weekPriceDto.setCreateUid(uid);
				weekPriceDto.setRentWay(sp.getRentWay());
				weekPriceDto.setPriceVal((int)BigDecimalUtil.mul(sp.getSpecialPrice().intValue(), 100));
				weekPriceDto.setHouseBaseFid(sp.getHouseBaseFid());
				if(sp.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
					weekPriceDto.setHouseRoomFid(sp.getHouseRoomFid());
				}

				Set<Integer> weekSet = new HashSet<>();
				List<String> weekList = sp.getSetTime();
				for (String weekStr : weekList) {
					weekSet.add(Integer.valueOf(weekStr));
				}
				weekPriceDto.setSetWeeks(weekSet);
				houseManageService.saveHousePriceWeekConf(JsonEntityTransform.Object2Json(weekPriceDto));
			} else if (sp.isUpdate()) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("houseFid", sp.getHouseBaseFid());
				paramMap.put("roomFid", sp.getHouseRoomFid());
				String weekendPriceJson = houseManageService.findWeekPriceByFid(JsonEntityTransform.Object2Json(paramMap));
				List<HousePriceWeekConfEntity> weekendPriceList = SOAResParseUtil
						.getListValueFromDataByKey(weekendPriceJson, "list", HousePriceWeekConfEntity.class);
				if (!Check.NuNCollection(weekendPriceList)) {
					for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
						housePriceWeekConf.setIsValid(YesOrNoEnum.NO.getCode());
						housePriceWeekConf.setRentWay(sp.getRentWay());
					}
					houseManageService.updateHousePriceWeekListByFid(JsonEntityTransform.Object2Json(weekendPriceList));
				}
			}
			/** 周末价格END */
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 * 
	 * 锁定房源，设置不可租
	 *
	 * @author jixd
	 * @created 2016年8月4日 下午3:21:41
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/lockHouse")
	@ResponseBody
	public DataTransferObject lockHouse(HttpServletRequest request, LockHouseRequest paramDto) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(Check.NuNObj(paramDto)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");;
				return dto;
			}
			if(Check.NuNStr(startDate)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("开始时间为空");;
				return dto;
			}
			if(Check.NuNStr(endDate)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("结束时间为空");;
				return dto;
			}
			if(Check.NuNObj(paramDto.getRentWay())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");;
				return dto;
			}
			if(Check.NuNStr(paramDto.getHouseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源ID为空");;
				return dto;
			}
			if(!isSelfOper(paramDto.getHouseFid(), UserUtils.getCurrentUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("非法操作");
				return dto;
			}
			if(paramDto.getRentWay() == RentWayEnum.ROOM.getCode()){
				if(Check.NuNStr(paramDto.getRoomFid())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("房间ID为空");
				}
			}
			
			Date sDate = DateUtil.parseDate(startDate, DATE_FORMAT_DAY);
			Date eDate = DateUtil.parseDate(endDate, DATE_FORMAT_DAY);
			List<Date> setDateList = new ArrayList<>();
			List<String> reDateList = new ArrayList<>();
			for (int i = 0, j = DateUtil.getDatebetweenOfDayNum(sDate, eDate) + 1; i < j; i++) {
				Date tempDate = DateUtil.getTime(sDate, i);
				String tempRe = "td_"+DateUtil.dateFormat(tempDate, DATE_FORMAT_DAY);
				setDateList.add(tempDate);
				reDateList.add(tempRe);
			}
			paramDto.setLockDayList(setDateList);
			String resultJson = orderUserService.lockHouseForPC(JsonEntityTransform.Object2Json(paramDto));
			LogUtil.info(LOGGER, "结果：" + resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() == DataTransferObject.SUCCESS){
				dto.putValue("lockDayList", reDateList);
			}else{
				dto = resultDto;
			}
			return dto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【锁定房源异常】error={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}
	}
	
	/**
	 * 
	 * 设为可租，并且可以设置价格
	 *
	 * @author jixd
	 * @created 2016年8月4日 下午3:18:13
	 *
	 * @param request
	 * @param houseLock
	 * @return
	 */
	@RequestMapping("/unlockHouse")
	@ResponseBody
	public DataTransferObject unlockHouse(HttpServletRequest request, LockHouseRequest paramDto) {
		DataTransferObject dto = new DataTransferObject();
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String specialPrice = request.getParameter("specialPrice");
			if(Check.NuNObj(paramDto)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");;
				return dto;
			}
			if(Check.NuNStr(startDate)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("开始时间为空");;
				return dto;
			}
			if(Check.NuNStr(endDate)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("结束时间为空");;
				return dto;
			}
			if(Check.NuNObj(paramDto.getRentWay())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");;
				return dto;
			}
			if(Check.NuNStr(paramDto.getHouseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源ID为空");;
				return dto;
			}
			if(!isSelfOper(paramDto.getHouseFid(), UserUtils.getCurrentUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("非法操作");
				return dto;
			}
			if(paramDto.getRentWay() == RentWayEnum.ROOM.getCode()){
				if(Check.NuNStr(paramDto.getRoomFid())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("房间ID为空");
				}
			}
			Date sDate = DateUtil.parseDate(startDate, DATE_FORMAT_DAY);
			Date eDate = DateUtil.parseDate(endDate, DATE_FORMAT_DAY);
			List<Date> setDateList = new ArrayList<>();
			List<String> reDateList = new ArrayList<>();
			for (int i = 0, j = DateUtil.getDatebetweenOfDayNum(sDate, eDate) + 1; i < j; i++) {
				Date tempDate = DateUtil.getTime(sDate, i);
				String tempRe = "td_"+DateUtil.dateFormat(tempDate, DATE_FORMAT_DAY);
				setDateList.add(tempDate);
				reDateList.add(tempRe);
			}
			paramDto.setLockDayList(setDateList);
			//如果价格不为空，先设置价格
			if(!Check.NuNStr(specialPrice)){
				List<HousePriceConfDto> housePriceList=new ArrayList<HousePriceConfDto>();
				for(Date setDate:setDateList){
					HousePriceConfDto priceConf = new HousePriceConfDto();
					priceConf.setHouseBaseFid(paramDto.getHouseFid());
					if(paramDto.getRentWay() == RentWayEnum.ROOM.getCode()){
						//设置房间的roomFid
						priceConf.setRoomFid(paramDto.getRoomFid());
					}
					priceConf.setRentWay(paramDto.getRentWay());
					BigDecimal price= new BigDecimal(specialPrice+"");
					BigDecimal setPrice=price.multiply(new BigDecimal("100"));
					priceConf.setPriceVal(setPrice.intValue());
					priceConf.setSetTime(setDate);
					priceConf.setCreateUid(uid);
					housePriceList.add(priceConf);
				}
				DataTransferObject priceDto = JsonEntityTransform.json2DataTransferObject(houseManageService.setSpecialPrice(JsonEntityTransform.Object2Json(housePriceList)));
				//先设置价格，如果价格设置成功，才设置可租状态
				if(priceDto.getCode() == DataTransferObject.SUCCESS){
					
				}else{
					return priceDto;
				}
			}
			
			//更改出租方式为可租
			String resultJson = orderUserService.unlockHouseForPC(JsonEntityTransform.Object2Json(paramDto));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() == DataTransferObject.SUCCESS){
				dto.putValue("lockDayList", reDateList);
			}else{
				dto = resultDto;
			}
			return dto;
		} catch (Exception e) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}
	}
	
	
	/**
	 * 
	 * 删除房源
	 *
	 * @author jixd
	 * @created 2016年8月11日 下午6:07:44
	 *
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public DataTransferObject delLanHouse(HouseBaseDto houseBaseDto){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(houseBaseDto)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto;
			}
			Integer rentWay = houseBaseDto.getRentWay();
			if(Check.NuNObj(rentWay)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");
				return dto;
			}
			if(Check.NuNStr(houseBaseDto.getHouseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源fid为空");
				return dto;
			}
			if(!isSelfOper(houseBaseDto.getHouseFid(), UserUtils.getCurrentUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("非法操作");
				return dto;
			}
            houseBaseDto.setLandlordUid(UserUtils.getCurrentUid());
            String resultJson = houseIssueService.deleteHouseOrRoomByFid(JsonEntityTransform.Object2Json(houseBaseDto));
			LogUtil.info(LOGGER, "【删除房源结果】result={}", resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "【删除房源异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作失败");
		}
		return dto;
	}
	
	/**
	 * 
	 * 下架房源
	 *
	 * @author jixd
	 * @created 2016年8月22日 下午4:20:44
	 *
	 * @return
	 */
	@RequestMapping("/down")
	@ResponseBody
	public DataTransferObject downHouse(String houseFid,String roomFid,Integer rentWay){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(rentWay)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租方式为空");
			return dto;
		}
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源fid为空");
			return dto;
		}
		
		if(!isSelfOper(houseFid, UserUtils.getCurrentUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("非法操作");
			return dto;
		}
		try{
			String resultJson = "";
			String uid = UserUtils.getCurrentUid();
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				resultJson = houseManageService.upDownHouse(houseFid,uid);
			}else if(rentWay == RentWayEnum.ROOM.getCode()){
				resultJson = houseManageService.upDownHouseRoom(roomFid, uid);
			}
			LogUtil.info(LOGGER, "【下架房源返回结果】result={}", resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch(Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作失败");
			return dto;
		}
	}
	
	
	/**
	 * 
	 * 取消发布
	 *
	 * @author jixd
	 * @created 2016年8月22日 下午4:21:48
	 *
	 * @return
	 */
	@RequestMapping("/cancle")
	@ResponseBody
	public DataTransferObject cancleHouse(HouseBaseDto houseBaseDto){
		DataTransferObject dto = new DataTransferObject();
		try{
			String houseFid = houseBaseDto.getHouseFid();
			String uid = UserUtils.getCurrentUid();
			if(Check.NuNStr(houseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto;
			}
			if(!isSelfOper(houseFid, uid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("非法操作");
				return dto;
			}
			houseBaseDto.setLandlordUid(UserUtils.getCurrentUid());
			String cancleJson = houseIssueService.cancleHouseOrRoomByFid(JsonEntityTransform.Object2Json(houseBaseDto));
			LogUtil.info(LOGGER, "【房源取消发布返回结果】result={}", cancleJson);
			return JsonEntityTransform.json2DataTransferObject(cancleJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "【房源取消发布异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作失败");
		}
		return dto;
	}
	
	/**
	 * 
	 * 发布房源(该方)
	 *
	 * @author jixd
	 * @created 2016年8月23日 下午3:04:43
	 *
	 * @return
	 */
	/*@Deprecated
	@RequestMapping("/release")
	@ResponseBody
	public DataTransferObject releaseHouse(HouseBaseDto houseBaseDto){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(houseBaseDto.getRentWay())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");
				return dto;
			}
			
			houseBaseDto.setLandlordUid(UserUtils.getCurrentUid());
			String houseIssueResult = houseIssueService.issueHouseInDetail(JsonEntityTransform.Object2Json(houseBaseDto));
			LogUtil.info(LOGGER, "【发布房源返回结果】result={}",houseIssueResult);
			dto = JsonEntityTransform.json2DataTransferObject(houseIssueResult);
		}catch(Exception e){
			LogUtil.error(LOGGER, "【发布房源异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}*/
	
	/**
	 * 
	 * 跳转到对应的发布步奏
	 *
	 * @author jixd
	 * @created 2016年8月22日 下午6:15:34
	 *
	 * @return
	 */
	@RequestMapping("/{fid}/goIssue")
	public String goIssue(@PathVariable String fid, String roomFid){
		DataTransferObject baseDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseMsgByFid(fid));
		if(baseDto.getCode() == DataTransferObject.ERROR){
			//TODO 跳转错误页
		}
		HouseBaseMsgEntity baseMsgEntity = baseDto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {});
		Integer operateSeq = baseMsgEntity.getOperateSeq();
		String toStep = "";
		/*switch(operateSeq){
			case 1:
				toStep = "/houseIssue/basicMsg/"+fid;
				break;
			case 2:
				toStep = "/houseIssue/configMsg/"+fid;
				break;
			case 3:
				toStep = "/houseIssue/desc/"+fid;
				break;
			case 4:
				if(baseMsgEntity.getRentWay() == RentWayEnum.HOUSE.getCode()){
					toStep = "/houseIssue/toWholeOrSublet/"+fid;
				}
				if(baseMsgEntity.getRentWay() == RentWayEnum.ROOM.getCode()){
					toStep = "/houseIssue/rooms/"+fid;
				}
				break;
			case 5:
			case 6:
			case 7:
				toStep = "/houseIssue/pic/"+fid;
				break;
			default:
				toStep = "/houseIssue/basicMsg/"+fid;
				break;
		}*/
	    //发布房源PC端修改开始
		switch(operateSeq){
		case 6:
			if(Check.NuNStr(roomFid)){
				toStep = "/houseIssue/pic/"+fid;
			}else{
				toStep = "/houseIssue/pic/"+fid+"?roomFid="+roomFid;
			}
			break;
		case 7:
			if(Check.NuNStr(roomFid)){
				toStep = "/houseIssue/pic/"+fid;
			}else{
				toStep = "/houseIssue/pic/"+fid+"?roomFid="+roomFid;
			}
			break;
		default:
			if(Check.NuNStr(roomFid)){
				toStep = "/houseIssue/basicMsg/"+fid;
			}else{
				toStep = "/houseIssue/basicMsg/"+fid+"?roomFid="+roomFid;
			}
			break;
	    }
		//发布房源PC端修改结束
		if(Check.NuNStr(toStep)){
			toStep = "/houseIssue/basicMsg/"+fid;
		}
		return "redirect:"+toStep;
	}
	
	
	/**
	 * 
	 * 判断是否是房源是否为当前用户
	 *
	 * @author jixd
	 * @created 2016年9月14日 下午3:43:41
	 *
	 * @return
	 */
	private boolean isSelfOper(String houseFid,String uid){
		boolean flag = true;
		String jsonStr = houseIssueService.searchHouseBaseMsgByFid(houseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(jsonStr);
		if(dto.getCode() == DataTransferObject.SUCCESS){
			HouseBaseMsgEntity houseBaseMsgEntity = dto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {});
			if(Check.NuNObj(houseBaseMsgEntity) || !uid.equals(houseBaseMsgEntity.getLandlordUid())){
				flag = false;
			}
		}
		return flag;
	}


	/**
	 * 绑定ab日历
	 * @author zl
	 * @created 2017/8/3 11:42
	 * @param
	 * @return 
	 */
	@RequestMapping("/saveHouseRelate")
	@ResponseBody
	public DataTransferObject saveHouseRelate(AbHouseRelateEntity abHouseRelateEntity,HttpServletRequest request){
		LogUtil.info(LOGGER,"参数{},{}",JsonEntityTransform.Object2Json(abHouseRelateEntity),request.getParameter("houseStatus"));
		Integer houseStatus=HouseStatusEnum.DFB.getCode();
		if(!Check.NuNStr(request.getParameter("houseStatus"))){
			houseStatus=Integer.valueOf(request.getParameter("houseStatus"));
		}
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(abHouseRelateEntity.getRentWay())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租方式为空");
			return dto;
		}
		if (Check.NuNStr(abHouseRelateEntity.getHouseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto;
		}
		if (abHouseRelateEntity.getRentWay() == RentWayEnum.ROOM.getCode()){
			if (Check.NuNStr(abHouseRelateEntity.getRoomFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间FID为空");
				return dto;
			}
		}
		if (Check.NuNStr(abHouseRelateEntity.getCalendarUrl())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("日历url为空");
			return dto;
		}

        if(!checkCalendarUrl(abHouseRelateEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("当前日历url无效，请输入到浏览器地址栏看看是否可以下载日历文件，如果可以下载请稍后再次添加！");
            return dto;
        }

		String calendarUrl = abHouseRelateEntity.getCalendarUrl();
		String abSn = calendarUrl.substring(calendarUrl.lastIndexOf("/") + 1, calendarUrl.indexOf(".ics"));
		abHouseRelateEntity.setAbSn(abSn);
		String s = abHouseService.saveHouseRelate(JsonEntityTransform.Object2Json(abHouseRelateEntity));
		LogUtil.info(LOGGER,"保存结果result={}",s);
		DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(s);
		if (dataTransferObject.getCode()==DataTransferObject.ERROR){
			return dataTransferObject;
		}

		if(HouseStatusEnum.SJ.getCode()==houseStatus){
            // 同步单个日历
            dto = syncSingleHouse(abHouseRelateEntity);
		}
		return dto;
	}

	/**
	 *
	 * 更新房源同步关系
	 *
	 * @author zyl
	 * @created 2017年6月29日 上午10:18:17
	 *
	 * @param abHouseRelateVo
	 * @return
	 */
	@RequestMapping("/updateHouseRelate")
	@ResponseBody
	public DataTransferObject updateHouseRelate(AbHouseRelateVo abHouseRelateVo){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(abHouseService.updateHouseRelateByFid(JsonEntityTransform.Object2Json(abHouseRelateVo)));
		return dto;
	}


	@RequestMapping("/toCalenderGuid")
	public String toCalenderGuid(HttpServletRequest request,SpecialPriceDto sp) throws SOAParseException {

		return "house/calenderGuid";
	}

    /**
     *
     * 检查日历url是否有效
     *
     * @author zhangyl2
     * @created 2017年10月21日 16:23
     * @param
     * @return
     */
    private boolean checkCalendarUrl(AbHouseRelateEntity abHouseRelateEntity) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("houseFid", abHouseRelateEntity.getHouseFid());
        paramMap.put("roomFid", abHouseRelateEntity.getRoomFid());
        paramMap.put("rentWay", String.valueOf(abHouseRelateEntity.getRentWay()));
        paramMap.put("calendarUrl", abHouseRelateEntity.getCalendarUrl());

        String resultJson = CloseableHttpUtil.sendFormPost(minsuSpiderUrl + "/calendar/checkCalendarUrlAvailable", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(resultJson);
        if (jsonObject.getInteger("status") == 200) {
            return jsonObject.getBoolean("data");
        } else {
            return false;
        }
    }

    /**
     *
     * 同步单个日历
     *
     * @author zhangyl2
     * @created 2017年10月20日 17:45
     * @param
     * @return
     */
    private DataTransferObject syncSingleHouse(AbHouseRelateEntity abHouseRelateEntity){
        //请求spider项目操作
        DataTransferObject dto = new DataTransferObject();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("houseFid", abHouseRelateEntity.getHouseFid());
        paramMap.put("roomFid", abHouseRelateEntity.getRoomFid());
        paramMap.put("rentWay", String.valueOf(abHouseRelateEntity.getRentWay()));
        paramMap.put("abSn", abHouseRelateEntity.getAbSn());
        paramMap.put("calendarUrl", abHouseRelateEntity.getCalendarUrl());
        String resultJson = CloseableHttpUtil.sendFormPost(minsuSpiderUrl + "/calendar/syncSingleHouse", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(resultJson);
        boolean success = false;
        if (jsonObject.getInteger("status") == 200) {
            success = jsonObject.getBoolean("data");
        }
        if(!success){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("同步失败，系统会稍后重试");
        }
        return dto;
    }


}
