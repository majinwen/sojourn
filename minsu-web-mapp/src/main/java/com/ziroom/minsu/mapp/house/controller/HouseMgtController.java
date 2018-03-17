package com.ziroom.minsu.mapp.house.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.mapp.common.constant.MappConst;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.logic.ParamCheckLogic;
import com.ziroom.minsu.mapp.common.logic.ValidateResult;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.mapp.common.util.UserUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.api.inner.HouseTonightDiscountService;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.dto.HouseLockDto;
import com.ziroom.minsu.services.house.dto.HousePriceConfDto;
import com.ziroom.minsu.services.house.dto.HousePriceWeekConfDto;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.dto.SpecialPriceDto;
import com.ziroom.minsu.services.house.entity.CalendarMonth;
import com.ziroom.minsu.services.house.entity.CalendarResponseVo;
import com.ziroom.minsu.services.house.entity.HousePriorityVo;
import com.ziroom.minsu.services.house.entity.HouseRoomVo;
import com.ziroom.minsu.services.house.entity.HouseTonightPriceInfoVo;
import com.ziroom.minsu.services.house.entity.LeaseCalendarVo;
import com.ziroom.minsu.services.house.entity.SpecialPriceVo;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.LockHouseCountRequest;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.IsValidEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.LockTypeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;

/**
 * 
 * <p>m站房源管理</p>
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
@RequestMapping("/houseMgt")
@Controller
public class HouseMgtController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseMgtController.class);

	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	
	private static final String DATE_FORMAT_MONTH = "yyyy-MM";
	
	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="order.orderUserService")
	private OrderUserService orderUserService;
	
	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name="mapp.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@Resource(name="house.tenantHouseService")
	private TenantHouseService tenantHouseService;
	
	@Resource(name="evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;
	
	@Resource(name = "basedata.employeeService")
	private EmployeeService employeeService;
	
	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;
	
	@Value("#{'${list_small_pic}'.trim()}")
	private String list_small_pic;

	@Resource(name = "order.houseCommonService")
	private HouseCommonService houseCommonService;
	
	@Resource(name="house.houseTonightDiscountService")
	private HouseTonightDiscountService houseTonightDiscountService;

    @Resource(name = "basedata.zkSysService")
    private ZkSysService zkSysService;

    /**
	 * 
	 * m站-跳转我的房源列表
	 *
	 * @author liujun
	 * @created 2016年5月3日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/myHouses")
	public String listHouse(HttpServletRequest request){
		CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
		request.setAttribute("landlordUid",customerVo.getUid());
		request.setAttribute("picSize_375_211", detail_big_pic);
		request.setAttribute("menuType", "house");
		return "houseMgt/myHouses";
	}
	
	/**
	 * 
	 * 查询房东房源列表
	 *
	 * @author liujun
	 * @created 2016年5月3日
	 *
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/houseRoomList")
	@ResponseBody
	public DataTransferObject houseRoomList(HouseBaseListDto paramDto) {
		try {
			paramDto.setLimit(5);
			String paramJson = JsonEntityTransform.Object2Json(paramDto);
			LogUtil.info(LOGGER, "参数:{}", paramJson);
			
			ValidateResult<HouseBaseListDto> validateResult =
	                paramCheckLogic.checkParamValidate(paramJson, HouseBaseListDto.class);
	        if (!validateResult.isSuccess()) {
	        	LogUtil.error(LOGGER, "错误信息:{}", validateResult.getDto().getMsg());
				return validateResult.getDto();
	        }
	        //以房源维度查询房东房源列表
	        String resultJson = houseManageService.searchLandlordHouseList(paramJson);
	        LogUtil.debug(LOGGER,"房源查询返回结果："+resultJson);
	        List<HouseRoomVo> houseList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomVo.class);
	        //循环查询合租房源的房间
	        List<HouseRoomVo> list=new ArrayList<HouseRoomVo>();
	        for(HouseRoomVo vo:houseList){
	        	if(!Check.NuNStr(vo.getZoCode())){
	        		String empJson = employeeService.findEmployeeByEmpCode(vo.getZoCode());
	        		DataTransferObject empDto = JsonEntityTransform.json2DataTransferObject(empJson);
	        		if (empDto.getCode() == DataTransferObject.ERROR) {
	        			LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}", vo.getZoCode());
	        		} else {
	        			EmployeeEntity emp = SOAResParseUtil.getValueFromDataByKey(empJson, "employee", EmployeeEntity.class);
	        			if (!Check.NuNObj(emp)) {
	        				vo.setZoName(emp.getEmpName());
	        				vo.setZoMobile(emp.getEmpMobile());
	        			}
	        		}
	        	}
	        	
	        	if(vo.getRentWay()==RentWayEnum.HOUSE.getCode()){
	        		if(Check.NuNStrStrict(vo.getName())){
	        			vo.setName("待完善房源");
	        		}
	        		list.add(vo);
	        	} else if(vo.getRentWay()==RentWayEnum.ROOM.getCode()) {
	        		if(vo.getStatus()==null||HouseStatusEnum.DFB.getCode()==vo.getStatus()){
		        		vo.setName("待完善房源");
		        	}
	        		HouseBaseListDto dto=new HouseBaseListDto();
	        		dto.setHouseBaseFid(vo.getHouseBaseFid());
	        		dto.setLandlordUid(paramDto.getLandlordUid());
	        		//查询房源包含房间列表
	        		String roomJson = houseManageService.searchHouseRoomList(JsonEntityTransform.Object2Json(dto));
	        		LogUtil.info(LOGGER,"房源fid:"+dto.getHouseBaseFid()+",查询房间列表返回结果："+roomJson);
	        		List<HouseRoomVo> roomList=SOAResParseUtil.getListValueFromDataByKey(roomJson, "list", HouseRoomVo.class);
	        		if(!Check.NuNCollection(roomList)){
	        			if(!Check.NuNStrStrict(vo.getZoMobile())){
	        				for (HouseRoomVo houseRoomVo : roomList) {
	        					houseRoomVo.setZoMobile(vo.getZoMobile());
	        					houseRoomVo.setZoName(vo.getZoName());
	        				}
	        			}
	        			list.addAll(roomList);
	        		} else {
	        			if(Check.NuNObj(vo.getHouseStatus())){
		        			vo.setHouseStatus(HouseStatusEnum.DFB.getCode());
		        			vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getHouseStatus()).getShowStatusName());
		        			vo.setStatus(HouseStatusEnum.DFB.getCode());
	        			} else {
		        			vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getHouseStatus()).getShowStatusName());
		        			vo.setStatus(vo.getHouseStatus());
						}
	        			list.add(vo);
	        		}
				}
	        }
	        
	        //30天出租率
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date startTime = DateUtil.parseDate(sdf.format(new Date()), "yyyy-MM-dd");
	        Date endTime = DateUtils.addDays(startTime, 30);
	        for(HouseRoomVo vo:list){
	        	LockHouseCountRequest request = new LockHouseCountRequest();
	        	request.setRentWay(vo.getRentWay());
	        	request.setHouseFid(vo.getHouseBaseFid());
	        	request.setRoomFid(vo.getHouseRoomFid());
	        	request.setStartTime(startTime);
	        	request.setEndTime(endTime);
	        	request.setLockType(LockTypeEnum.ORDER.getCode());
	        	
	        	Double houseBookRate = 0.0;
	        	int orderLockCount = 0;
	        	int lanLockCount = 0;
	        	
	        	//排除房间没有的房源
	        	if(!Check.NuNObj(request.getRentWay())
	        			&&(request.getRentWay().intValue()!=RentWayEnum.ROOM.getCode()
	        			||!Check.NuNStr(request.getRoomFid()))){
	        		DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderUserService.getBookDaysByFid(JsonEntityTransform.Object2Json(request)));
		        	if(orderDto.getCode() == DataTransferObject.SUCCESS){
		        		orderLockCount = (int) orderDto.getData().get("count");
		        	}
		        	request.setLockType(LockTypeEnum.LANDLADY.getCode());
		        	DataTransferObject lanDto = JsonEntityTransform.json2DataTransferObject(orderUserService.getBookDaysByFid(JsonEntityTransform.Object2Json(request)));

		        	if(lanDto.getCode() == DataTransferObject.SUCCESS){
		        		lanLockCount = (int) lanDto.getData().get("count");
		        	}
		        	
		        	if(lanLockCount >= 30){
		        		houseBookRate = 0.0;
		        	}else{
		        		houseBookRate = BigDecimalUtil.div(orderLockCount * 100,30.0 - lanLockCount,1);
		        	}
		        	
	        	}
	        	
	        	vo.setHouseBookRate(houseBookRate);

	        	//查询评论数
				StatsHouseEvaRequest statsHouseEvaRequest=new StatsHouseEvaRequest();
				statsHouseEvaRequest.setHouseFid(vo.getHouseBaseFid());
				statsHouseEvaRequest.setRoomFid(vo.getHouseRoomFid());
				
				statsHouseEvaRequest.setRentWay(vo.getRentWay());
				LogUtil.debug(LOGGER,"查询评论数参数："+JsonEntityTransform.Object2Json(statsHouseEvaRequest));
				String evaluateCountJson=evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));

				List<StatsHouseEvaEntity> evaluateStats=SOAResParseUtil.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
				if(!Check.NuNCollection(evaluateStats)){
					LogUtil.debug(LOGGER,"查询评论数结果："+JsonEntityTransform.Object2Json(evaluateStats));
					vo.setHouseEvaScore("无评分");
					StatsHouseEvaEntity statsHouseEvaEntity=evaluateStats.get(0);
					if(statsHouseEvaEntity.getEvaTotal()>0){
						double evalScore = BigDecimalUtil.div(statsHouseEvaEntity.getHouseCleanAva()+statsHouseEvaEntity.getDesMatchAva()+statsHouseEvaEntity.getSafeDegreeAva()+statsHouseEvaEntity.getTrafPosAva()+statsHouseEvaEntity.getCostPerforAva(), 5.0, 1);
						vo.setHouseEvaScore(String.valueOf(evalScore));
					}
				}
	        }
	        DataTransferObject dto = new DataTransferObject();
	        dto.putValue("list", list);
			return dto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.UNKNOWN_ERROR));
			return dto;
		}
	}
	
	/**
	 * 
	 * 上下架房源
	 *
	 * @author liujun
	 * @created 2016年5月3日
	 *
	 * @param rentWay
	 * @param houseFid
	 * @param landlordUid
	 * 
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/upDownHouse")
	@ResponseBody
	public DataTransferObject upDownHouse(String houseFid, String landlordUid, Integer rentWay) {
		try {
			LogUtil.info(LOGGER, "参数：houseFid={},landlordUid={},rentWay={}", houseFid, landlordUid, rentWay);
			String resultJson = null;
			if (rentWay == RentWayEnum.HOUSE.getCode()) {
				// 整租 houseFid为房源逻辑id
				resultJson = houseManageService.upDownHouse(houseFid, landlordUid);
			} else if (rentWay == RentWayEnum.ROOM.getCode()) {
				// 合租 houseFid为房间逻辑id
				resultJson = houseManageService.upDownHouseRoom(houseFid, landlordUid);
			} else {
				// 出租方式错误 目前不支持床位出租类型
				DataTransferObject dto = new DataTransferObject();
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_RENTWAY_ERROR));
				return dto;
			}
			LogUtil.debug(LOGGER,"返回结果："+resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.UNKNOWN_ERROR));
			return dto;
		}
	}
	
	
	/**
	 * 
	 * 房东房源日历
	 *
	 * @author bushujie
	 * @created 2016年5月12日 上午11:58:16
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/leaseCalendar")
	@ResponseBody
	public DataTransferObject leaseCalendar(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			//开始时间
			Date startDate = DateUtil.parseDate(request.getParameter("startDate"), DATE_FORMAT_PATTERN);
			
			//结束时间
			Date endDate = DateUtil.parseDate(request.getParameter("endDate"), DATE_FORMAT_PATTERN);
			
			// 房源逻辑id
			String houseBaseFid = request.getParameter("houseBaseFid");
			
			// 房间逻辑id
			String houseRoomFid = request.getParameter("houseRoomFid");
			
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
			LogUtil.debug(LOGGER, "房源参数：{}", houseParams);
			String houseJson = houseManageService.leaseCalendar(houseParams);
			DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
			//判断调用状态
			if(houseDto.getCode() == MappConst.OPERATION_FAILURE){
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
			LogUtil.debug(LOGGER, "订单参数：{}", orderParams);
			String orderJson = orderUserService.getHouseLockInfo(orderParams);
			DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
			//判断调用状态
			if(orderDto.getCode() == MappConst.OPERATION_FAILURE){
				LogUtil.error(LOGGER, "invoke orderUserService getHouseLockInfo failed");
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
				String dateMonth = this.getDateMonth(startDate, i);
				String dateDay = this.getDate(startDate, i);
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
					CalendarResponseVo vo = map.get(DateUtil.dateFormat(specialPriceVo.getSetDate(), DATE_FORMAT_PATTERN));
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
				CalendarResponseVo currentDayVo = maptemp.get(DateUtil.dateFormat(currentDate, DATE_FORMAT_PATTERN));
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
					CalendarResponseVo vo = map.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_FORMAT_PATTERN));
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
			
			LogUtil.debug(LOGGER, "结果：" + JsonEntityTransform.Object2Json(calendarList));
			dto.putValue("list",calendarList);
			dto.putValue("tillDate", DateUtil.dateFormat(leaseCalendarVo.getTillDate(), DATE_FORMAT_PATTERN));
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
	 * @created 2017年5月12日 上午10:29:14
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
						CalendarResponseVo vo = map.get(DateUtil.dateFormat(date, DATE_FORMAT_PATTERN));
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
				HousePriorityDto housePriorityDt = new HousePriorityDto();
				housePriorityDt.setHouseBaseFid(lockRequest.getFid());
				housePriorityDt.setRentWay(lockRequest.getRentWay());
				housePriorityDt.setHouseRoomFid(lockRequest.getRoomFid());
				housePriorityDt.setStartDate(lockRequest.getStarTime());
				housePriorityDt.setEndDate(lockRequest.getEndTime());
				housePriorityDt.setTillDate(tillDate);
				housePriorityDt.setNowDate(new Date());
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
						
						if(Check.NuNStrStrict(key)) 
							continue;
						Map<String, CalendarResponseVo> map= null;
						try {
							map=monthMap.get(DateUtil.dateFormat(DateUtil.parseDate(key, DATE_FORMAT_MONTH), DATE_FORMAT_MONTH));
						} catch (ParseException e) {
							LogUtil.error(LOGGER, "查看日历——时间解析异常key={},e={}",key,e);
							continue;
						}
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
				LogUtil.error(LOGGER, "查看日历-夹心价格设置失败paramDto={},e={}", JsonEntityTransform.Object2Json(lockRequest),e);
			}

		}
	}
	
	/**
	 * 获取开始时间指定间隔天数后的日期字符串
	 *
	 * @author liujun
	 * @created 2016年4月22日 下午2:09:27
	 *
	 * @param startDate 开始时间
	 * @param interval 间隔天数
	 * @return 
	 */
	private String getDate(Date startDate, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, interval);
		return DateUtil.dateFormat(calendar.getTime(), DATE_FORMAT_PATTERN);
	}
	
	/**
	 * 获取开始时间指定间隔天数后的日期字符串 yyyy-MM
	 *
	 * @author liujun
	 * @created 2016年4月22日 下午2:09:27
	 *
	 * @param startDate 开始时间
	 * @param interval 间隔天数
	 * @return 
	 */
	private String getDateMonth(Date startDate, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, interval);
		return DateUtil.dateFormat(calendar.getTime(), DATE_FORMAT_MONTH);
	}
	
	/**
	 * 
	 * 设置特殊价格
	 *
	 * @author bushujie
	 * @created 2016年5月12日 下午5:30:45
	 *
	 * @param request
	 * @param sp
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/setSpecialPrice")
	@ResponseBody
	public DataTransferObject setSpecialPrice(HttpServletRequest request,SpecialPriceDto sp) {
		DataTransferObject dto = new DataTransferObject();
		try {			
			String paramJson = JsonEntityTransform.Object2Json(sp);
			LogUtil.info(LOGGER, "参数：{}", paramJson);
			
			ValidateResult<SpecialPriceDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, SpecialPriceDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{}", validateResult.getDto().getMsg());
				return validateResult.getDto();
			}
			SpecialPriceDto requestDto = validateResult.getResultObj();
			
			//查询房源状态
			HouseLockRequest lockRequest = new HouseLockRequest();
			lockRequest.setFid(sp.getHouseBaseFid());
			if(!Check.NuNObj(sp.getRentWay()) && sp.getRentWay() == RentWayEnum.ROOM.getCode()){
                lockRequest.setRoomFid(sp.getHouseRoomFid());
            }

			lockRequest.setRentWay(sp.getRentWay());
			lockRequest.setStarTime(DateUtil.parseDate(sp.getStartDate(), DATE_FORMAT_PATTERN));
			lockRequest.setEndTime(DateUtil.parseDate(sp.getEndDate(), DATE_FORMAT_PATTERN));
			
			String orderParams = JsonEntityTransform.Object2Json(lockRequest);
			LogUtil.info(LOGGER, "订单参数：{}", orderParams);
			String orderJson = orderUserService.getHouseLockInfo(orderParams);
			DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
			//判断调用状态
			if(orderDto.getCode() == MappConst.OPERATION_FAILURE){
				LogUtil.error(LOGGER, "invoke orderUserService getHouseLockInfo failed");
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto;
			}
			//房源锁定信息列表
			List<HouseLockEntity> houseLockList = orderDto.parseData("houseLock",
					new TypeReference<List<HouseLockEntity>>() {});
			Map<String, HouseLockEntity> lockMap = new HashMap<String, HouseLockEntity>();
			if(!Check.NuNCollection(houseLockList)){
				for (HouseLockEntity lockEntity:houseLockList) {
					lockMap.put(DateUtil.dateFormat(lockEntity.getLockTime(), DATE_FORMAT_PATTERN), lockEntity);
				}
			}
			
			List<HousePriceConfDto> housePriceList=new ArrayList<HousePriceConfDto>();
			for(String setTime:requestDto.getSetTime()){
				if (lockMap.size()>0) {
					HouseLockEntity lockEntity = lockMap.get(setTime);
					if (!Check.NuNObj(lockEntity)) {//已经锁定的不能设置特殊价格
						continue;
					}
				}
				
				HousePriceConfDto paramDto = new HousePriceConfDto();
				paramDto.setHouseBaseFid(requestDto.getHouseBaseFid());
                if(requestDto.getRentWay() == RentWayEnum.ROOM.getCode()){
                    //设置房间的roomFid
                    paramDto.setRoomFid(requestDto.getHouseRoomFid());
                }
				paramDto.setRentWay(requestDto.getRentWay());
				BigDecimal price= new BigDecimal(requestDto.getSpecialPrice()+"");
				BigDecimal setPrice=price.multiply(new BigDecimal("100"));
				paramDto.setPriceVal(setPrice.intValue());
				paramDto.setSetTime(DateUtil.parseDate(setTime, DATE_FORMAT_PATTERN));
				CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
				paramDto.setCreateUid(customerVo.getUid());
				housePriceList.add(paramDto);
			}
			
			String resultJson = houseManageService.setSpecialPrice(JsonEntityTransform.Object2Json(housePriceList));
			LogUtil.debug(LOGGER, "结果：{}", resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}
	}
	
	
	/**
	 * 
	 * 设置周末特殊价格
	 *
	 * @author zl
	 * @created 2016年9月11日 
	 *
	 * @param request 
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/setWeekSpecialPrice")
	@ResponseBody
	public DataTransferObject setWeekSpecialPrice(SpecialPriceDto sp) {
		try {
			String paramJson = JsonEntityTransform.Object2Json(sp);
			LogUtil.info(LOGGER, "setWeekSpecialPrice param：{}", paramJson);

			ValidateResult<SpecialPriceDto> validateResult = paramCheckLogic.checkParamValidate(paramJson, SpecialPriceDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{}", validateResult.getDto().getMsg());
				return validateResult.getDto();
			}
			SpecialPriceDto requestDto = validateResult.getResultObj();
			DataTransferObject dto = new DataTransferObject();
			if (Check.NuNStr(requestDto.getHouseBaseFid())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源fid不能为空");
				return dto;
			}
			
			if (RentWayEnum.ROOM.getCode() == requestDto.getRentWay().intValue()) {
				if (Check.NuNStr(requestDto.getHouseRoomFid())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("房间fid不能为空");
					return dto;
				}
			}
			
			if (Check.NuNCollection(requestDto.getSetTime())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("设置时间不能为空");
				return dto;
			}
			
			if (Check.NuNObj(requestDto.getSpecialPrice())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("周末价格不能为空");
				return dto;
			}

			//房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			Integer priceLow = SOAResParseUtil.getIntFromDataByKey(priceLowJson, "textValue");
			
			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			Integer priceHigh = SOAResParseUtil.getIntFromDataByKey(priceHighJson, "textValue");

			if (!Check.NuNObjs(priceLow, priceHigh)
					&& (requestDto.getSpecialPrice().intValue() < priceLow.intValue()
							|| requestDto.getSpecialPrice().intValue() > priceHigh.intValue())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("周末价格超出限制");
				return dto;
			}
			
			String uid = UserUtil.getCurrentUserFid();
			if (Check.NuNStr(uid)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("请重新登录");
				return dto;
			}
			
			HousePriceWeekConfDto weekPriceDto = new HousePriceWeekConfDto();
			weekPriceDto.setCreateUid(uid);
			weekPriceDto.setRentWay(requestDto.getRentWay());
			weekPriceDto.setPriceVal((int)BigDecimalUtil.mul(requestDto.getSpecialPrice().intValue(), 100));
			weekPriceDto.setHouseBaseFid(requestDto.getHouseBaseFid());
			if(requestDto.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
            	weekPriceDto.setHouseRoomFid(requestDto.getHouseRoomFid());
            }
			
			Set<Integer> weekSet = new HashSet<>();
			List<String> weekList = requestDto.getSetTime();
			for (String weekStr : weekList) {
				weekSet.add(Integer.valueOf(weekStr));
			}
			weekPriceDto.setSetWeeks(weekSet);
			
			if (!Check.NuNStr(requestDto.getStartDate())) {
				Date startDate = DateUtil.parseDate(requestDto.getStartDate(), DATE_FORMAT_PATTERN);			
				weekPriceDto.setStartDate(startDate);
			}
			
			if (!Check.NuNStr(requestDto.getEndDate())) {
				Date endDate = DateUtil.parseDate(requestDto.getEndDate(), DATE_FORMAT_PATTERN);
				weekPriceDto.setEndDate(endDate);
			}
			
			String resultJson = houseManageService.saveHousePriceWeekConf(JsonEntityTransform.Object2Json(weekPriceDto));
			LogUtil.debug(LOGGER, "结果：{}", resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}
		
	}

	/**
	 * 查询周末价格
	 *
	 * @author jixd
	 * @created 2016年10月19日 下午6:31:36
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/getWeekPrice")
	@ResponseBody
	public DataTransferObject getHouseWeekPriceList(HttpServletRequest request){
		DataTransferObject rDto = new DataTransferObject();
		try{
			String houseFid = request.getParameter("houseFid");
			String roomFid = request.getParameter("roomFid");
			JSONObject param = new JSONObject();
			param.put("houseFid",houseFid);
			if (roomFid == null || "null".equals(roomFid) || "".equals(roomFid)){
				roomFid = null;
			}
			param.put("roomFid",roomFid);
			String resultJson = houseManageService.findWeekPriceByFid(param.toJSONString());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			Integer tab = -1;
			Integer price = 0;
			if (dto.getCode() == DataTransferObject.SUCCESS){
				List<HousePriceWeekConfEntity> list = dto.parseData("list", new TypeReference<List<HousePriceWeekConfEntity>>() {});
				if (!Check.NuNCollection(list)){
					if (list.size() == 2){
						for (HousePriceWeekConfEntity conf : list){
							if (conf.getSetWeek() == 5){
								tab = 0;
							}
							if (conf.getSetWeek() == 7){
								tab = 1;
							}
						}
					}
					if (list.size() == 3){
						tab = 2;
					}
					price = list.get(0).getPriceVal()/100;
				}
			}
			rDto.putValue("tab",tab);
			rDto.putValue("price",price);
		}catch (Exception e){
			rDto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			rDto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return rDto;
	}
	
	
	/**
	 * 
	 * 日历详情
	 *
	 * @author bushujie
	 * @created 2016年5月12日 下午6:31:36
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("${LOGIN_UNAUTH}/calendarDetail")
	public String calendarDetail(HttpServletRequest request) throws SOAParseException{
		String houseBaseFid=request.getParameter("houseBaseFid");
		String houseRoomFid=request.getParameter("houseRoomFid");
		String backFalg=request.getParameter("backFalg");
		Integer rentWay=0;
		if(!Check.NuNStr(request.getParameter("rentWay"))){
			rentWay=Integer.valueOf(request.getParameter("rentWay"));
		}
		HouseDetailDto houseDetailDto=new HouseDetailDto();
		if(RentWayEnum.HOUSE.getCode()==rentWay){
			houseDetailDto.setFid(houseBaseFid);
		} else if(RentWayEnum.ROOM.getCode()==rentWay){
			houseDetailDto.setFid(houseRoomFid);
		}
		houseDetailDto.setRentWay(rentWay);
		String resultJson=tenantHouseService.houseListDetail(JsonEntityTransform.Object2Json(houseDetailDto));
		TenantHouseDetailVo tenantHouseDetailVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", TenantHouseDetailVo.class);
		//查询评论数
		StatsHouseEvaRequest statsHouseEvaRequest=new StatsHouseEvaRequest();
		if(tenantHouseDetailVo.getRentWay()==0){
			statsHouseEvaRequest.setHouseFid(tenantHouseDetailVo.getFid());
		} else if(tenantHouseDetailVo.getRentWay()==1) {
			statsHouseEvaRequest.setRoomFid(tenantHouseDetailVo.getFid());
		}
		statsHouseEvaRequest.setRentWay(tenantHouseDetailVo.getRentWay());
		LogUtil.debug(LOGGER,"查询评论数参数："+JsonEntityTransform.Object2Json(statsHouseEvaRequest));
		String evaluateCountJson=evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));
		List<StatsHouseEvaEntity> evaluateStats=SOAResParseUtil.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
		if(!Check.NuNCollection(evaluateStats)){
			LogUtil.debug(LOGGER,"查询评论数结果："+JsonEntityTransform.Object2Json(evaluateStats));
			StatsHouseEvaEntity statsHouseEvaEntity=evaluateStats.get(0);
			Float sumStar=(statsHouseEvaEntity.getHouseCleanAva()+statsHouseEvaEntity.getDesMatchAva()+statsHouseEvaEntity.getSafeDegreeAva()+statsHouseEvaEntity.getTrafPosAva()+statsHouseEvaEntity.getCostPerforAva())/5;
			BigDecimal bigDecimal=new BigDecimal(sumStar.toString());
			tenantHouseDetailVo.setGradeStar(bigDecimal.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue());
			tenantHouseDetailVo.setEvaluateCount(statsHouseEvaEntity.getEvaTotal());
		}
		
		//房源价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
		request.setAttribute("priceLow", priceLow);
		
		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
		request.setAttribute("priceHigh", priceHigh);
		
		tenantHouseDetailVo.setDefaultPic(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), list_small_pic));
		request.setAttribute("startDate", DateUtil.getFirstDayOfMonth(new Date()));
		LogUtil.debug(LOGGER, "结果：" + JsonEntityTransform.Object2Json(tenantHouseDetailVo));
		request.setAttribute("tenantHouseDetailVo", tenantHouseDetailVo);
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("houseRoomFid", houseRoomFid);
		request.setAttribute("rentWay", rentWay);
		String tillDate = DateUtil.dateFormat(tenantHouseDetailVo.getTillDate(), "yyyy/MM/dd");
		request.setAttribute("tillDate",tillDate);
		request.setAttribute("backFalg", backFalg);
		String sourceType = request.getParameter("sourceType");//请求来源
		request.setAttribute("sourceType", sourceType);

        //提示升级
        String zkSysValue = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
        request.setAttribute("zkSysValue", zkSysValue);

		return "houseMgt/calendarDetail";
	}
	

	@RequestMapping("/${LOGIN_UNAUTH}/lockHouse")
	@ResponseBody
	public DataTransferObject lockHouse(HttpServletRequest request, HouseLockDto houseLock) {
		try {
			String paramJson = JsonEntityTransform.Object2Json(houseLock);
			LogUtil.info(LOGGER, "参数：{}", paramJson);
			
			ValidateResult<HouseLockDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseLockDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{}", validateResult.getDto().getMsg());
				return validateResult.getDto();
			}
			HouseLockDto requestDto = validateResult.getResultObj();
			
			LockHouseRequest paramDto = new LockHouseRequest();
			paramDto.setHouseFid(requestDto.getHouseBaseFid());
			paramDto.setRoomFid(requestDto.getHouseRoomFid());
			paramDto.setRentWay(requestDto.getRentWay());
			paramDto.setLockType(requestDto.getLockType());
			paramDto.setLockDayList(getTransformDateList(requestDto.getLockDateList()));
			
			String resultJson = orderUserService.lockHouse(JsonEntityTransform.Object2Json(paramDto));
			if(!Check.NuNStr(resultJson)){
				resultJson =resultJson.replace( "房源已经被锁定","当前日期已是设为已租状态");//替换提示信息
			}
			LogUtil.debug(LOGGER, "结果：" + resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}
	}
	
	/**
	 * 
	 * 将日期字符串集合转换为日期集合
	 *
	 * @author liujun
	 * @created 2016年4月25日 下午2:16:53
	 *
	 * @param dateStrList
	 * @return
	 * @throws ParseException 
	 */
	private List<Date> getTransformDateList(List<String> dateStrList) throws ParseException {
		if (Check.NuNCollection(dateStrList)) {
			return null;
		}
		
		List<Date> dateList = new ArrayList<Date>();
		for (String string : dateStrList) {
			dateList.add(DateUtil.parseDate(string, DATE_FORMAT_PATTERN));
		}
		return dateList;
	}
	
	/**
	 * 
	 * 房东解锁房源
	 *
	 * @author liujun
	 * @created 2016年5月13日
	 *
	 * @param request
	 * @param houseLock
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/unlockHouse")
	@ResponseBody
	public DataTransferObject unlockHouse(HttpServletRequest request, HouseLockDto houseLock) {
		try {
			String paramJson = JsonEntityTransform.Object2Json(houseLock);
			LogUtil.info(LOGGER, "参数：{}", paramJson);
			
			ValidateResult<HouseLockDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseLockDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{}", validateResult.getDto().getMsg());
				return validateResult.getDto();
			}
			HouseLockDto requestDto = validateResult.getResultObj();
			
			LockHouseRequest paramDto = new LockHouseRequest();
			paramDto.setHouseFid(requestDto.getHouseBaseFid());
			paramDto.setRoomFid(requestDto.getHouseRoomFid());
			paramDto.setRentWay(requestDto.getRentWay());
			paramDto.setLockDayList(getTransformDateList(requestDto.getLockDateList()));
			
			String resultJson = orderUserService.unlockHouse(JsonEntityTransform.Object2Json(paramDto));
			if(!Check.NuNStr(resultJson)){
				resultJson =resultJson.replace( "房源未锁定","当前日期已是可租状态");//替换提示信息
			}
			LogUtil.debug(LOGGER, "结果：{}", resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}
	}
}
