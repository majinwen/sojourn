package com.ziroom.minsu.api.house.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.LandStaticsDataTemplate;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.dto.TimeDto;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.common.util.BaseMethodUtil;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.api.house.service.HouseUpdateLogService;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.LandlordBehaviorEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBizMsgEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.common.entity.FieldTitleTextValueVo;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.api.inner.LandlordBehaviorService;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.house.api.inner.HouseIssueAppService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.api.inner.HousePCService;
import com.ziroom.minsu.services.house.api.inner.HouseTonightDiscountService;
import com.ziroom.minsu.services.house.api.inner.TroyPhotogBookService;
import com.ziroom.minsu.services.house.dto.BookHousePhotogDto;
import com.ziroom.minsu.services.house.dto.HouseBaseDto;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HouseLandlordParamsDto;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.dto.ToNightDiscountDto;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.entity.HouseRoomVo;
import com.ziroom.minsu.services.house.issue.dto.HousePriceDto;
import com.ziroom.minsu.services.house.issue.vo.HouseBaseVo;
import com.ziroom.minsu.services.house.photog.dto.PhotoOrderDto;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.LockHouseCountRequest;
import com.ziroom.minsu.valenum.common.WeekendPriceEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HouseButtonEnum;
import com.ziroom.minsu.valenum.house.HouseModifyTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.LockTypeEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderStatuEnum;
import com.ziroom.minsu.valenum.productrules.HouseRankEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;

import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>移动端房东房源管理</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liujun
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/houseMgt")
public class LandlordHouseMgtController extends AbstractController {


	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordHouseMgtController.class);

	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name = "api.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Resource(name = "basedata.employeeService")
	private EmployeeService employeeService;

	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;

	@Resource(name = "photographer.troyPhotogBookService")
	private TroyPhotogBookService troyPhotogBookService;

	@Resource(name = "order.orderUserService")
	private OrderUserService orderUserService;

	@Resource(name = "api.messageSource")
	private MessageSource messageSource;

	@Resource(name = "house.housePCService")
	private HousePCService housePCService;

	@Resource(name = "customer.landlordBehaviorService")
	private LandlordBehaviorService landlordBehaviorService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String pic_base_addr_mona;

	@Value("#{'${pic_size}'.trim()}")
	private String pic_size;

	@Value("#{'${LOGIN_UNAUTH}'.trim()}")
	private String LOGIN_UNAUTH;

	@Value("#{'${bookphoto_start_time}'.trim()}")
	private String bookStartTime;

	@Value("#{'${bookphoto_end_time}'.trim()}")
	private String bookEndTime;

	@Value("#{'${book_photo_days}'.trim()}")
	private String bookPhotoDays;

	@Value("#{'${add_day_am}'.trim()}")
	private int addDayAm;

	@Value("#{'${add_day_pm}'.trim()}")
	private int addDayPm;

	@Resource(name = "house.houseTonightDiscountService")
	private HouseTonightDiscountService houseTonightDiscountService;

	@Resource(name = "basedata.staticResourceService")
	private StaticResourceService staticResourceService;

	@Resource(name = "basedata.zkSysService")
	private ZkSysService zkSysService;

	@Resource(name = "order.houseCommonService")
	private HouseCommonService houseCommonService;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;

	@Resource(name = "api.houseService")
	private HouseService houseService;

	@Resource(name = "house.houseIssueAppService")
	private HouseIssueAppService houseIssueAppService;

	@Value("#{'${MINSU_M_SMARTLOCK}'.trim()}")
	private String MINSU_M_SMARTLOCK;
	
	@Resource(name = "api.houseUpdateLogService")
	private HouseUpdateLogService houseUpdateLogService;


	/**
	 * 房东房源列表
	 *
	 * @param request
	 * @return
	 * @author bushujie
	 * @created 2017年7月11日 上午9:56:01
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/houseRoomList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> houseRoomList(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);

			ValidateResult<HouseBaseListDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseBaseListDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseBaseListDto paramDto = validateResult.getResultObj();

			//以房源维度查询房东房源列表
			String resultJson = houseManageService.searchLandlordHouseList(paramJson);
			LogUtil.debug(LOGGER, "房源查询返回结果："+resultJson);
			List<HouseRoomVo> houseList = SOAResParseUtil
					.getListValueFromDataByKey(resultJson, "list", HouseRoomVo.class);

			// 循环查询合租房源的房间
			List<HouseRoomVo> list = new ArrayList<HouseRoomVo>();
			for (HouseRoomVo vo : houseList) {
				if (!Check.NuNStr(vo.getZoCode())) {
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

				if (vo.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
					if (Check.NuNStrStrict(vo.getName())) {
						vo.setName("待完善房源");
					}
					if(!Check.NuNStr(vo.getDefaultPic())){
						vo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, vo.getDefaultPic(), pic_size));
					}
					//默认不可以预约，如果品质审核未通过，且是照片原因，则置为可预约
					vo.setWhetherBookPhoto(0);
					if(!Check.NuNObj(vo.getHouseStatus()) && vo.getHouseStatus().intValue() == HouseStatusEnum.ZPSHWTG.getCode()){
						HouseBizMsgEntity houseBizMsg = this.getHouseBizMsg(vo.getHouseBaseFid(), vo.getRentWay());
						if (!Check.NuNObj(houseBizMsg)) {
//							StringBuilder sb = new StringBuilder();
//							if (!Check.NuNStrStrict(houseBizMsg.getRefuseReason())) {
//								String refuseReason = HouseAuditCauseEnum.getNameStr(houseBizMsg.getRefuseReason());
//								sb.append(refuseReason);
//							}
//							if (!Check.NuNStrStrict(sb.toString())) {
//								sb.append(":");
//							}
//							if (!Check.NuNStrStrict(houseBizMsg.getRefuseMark())) {
//								sb.append(houseBizMsg.getRefuseMark());
//							}
							if(!Check.NuNStrStrict(houseBizMsg.getRefuseMark())){
								vo.setRefuseReason(houseBizMsg.getRefuseMark());
							}
						}
						//是否可以预约摄影
						Map<String, Object> map = whetherBookPhoto(paramDto.getLandlordUid(),vo.getHouseBaseFid(),null);
						Integer i =(Integer)map.get("whether");
						if(i>0){
							//可以预约
							vo.setWhetherBookPhoto(i);
						}else{
							//不可以预约
							vo.setWhetherBookPhoto(i);
						}
						String bookTime = (String)map.get("bookTime");
						if(!Check.NuNObj(map.get("bookTime"))){
							bookTime = bookTime.substring(5,bookTime.length()-3);
							vo.setBookStartTime(bookTime);
						}
						if(!Check.NuNObj(map.get("photoStatus"))){
							vo.setPhotoStatus((Integer)map.get("photoStatus"));
						}
						if(!Check.NuNObj(map.get("photoStatName"))){
							vo.setPhotoStatName((String)map.get("photoStatName"));
						}
					}

					list.add(vo);
				} else if (vo.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
					if (vo.getStatus() == null || HouseStatusEnum.DFB.getCode() == vo.getStatus()) {
						vo.setName("待完善房源");
					}
					HouseBaseListDto dto = new HouseBaseListDto();
					dto.setHouseBaseFid(vo.getHouseBaseFid());
					dto.setLandlordUid(paramDto.getLandlordUid());
					// 查询房源包含房间列表
					String roomJson = houseManageService.searchHouseRoomList(JsonEntityTransform.Object2Json(dto));
					LogUtil.debug(LOGGER, "房源fid:" + dto.getHouseBaseFid() + ",查询房间列表返回结果：" + roomJson);
					List<HouseRoomVo> roomList = SOAResParseUtil.getListValueFromDataByKey(roomJson, "list", HouseRoomVo.class);
					if (!Check.NuNCollection(roomList)) {
						for (HouseRoomVo houseRoomVo : roomList) {
							/*if(!Check.NuNStrStrict(vo.getZoMobile())){
	        					houseRoomVo.setZoMobile(vo.getZoMobile());
	        					houseRoomVo.setZoName(vo.getZoName());
		        			}*/
							if (!Check.NuNStr(houseRoomVo.getDefaultPic())) {
								houseRoomVo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, houseRoomVo.getDefaultPic(), pic_size));
							}
							//默认不可以预约，如果品质审核未通过，且是照片原因，则置为可预约
							houseRoomVo.setWhetherBookPhoto(0);
							if (!Check.NuNObj(houseRoomVo.getRoomStatus()) && houseRoomVo.getRoomStatus().intValue() == HouseStatusEnum.ZPSHWTG.getCode()) {
								HouseBizMsgEntity houseBizMsg = this.getHouseBizMsg(houseRoomVo.getHouseRoomFid(), vo.getRentWay());
								if (!Check.NuNObj(houseBizMsg)) {
//									StringBuilder sb = new StringBuilder();
//									if (!Check.NuNStrStrict(houseBizMsg.getRefuseReason())) {
//										String refuseReason = HouseAuditCauseEnum.getNameStr(houseBizMsg.getRefuseReason());
//										sb.append(refuseReason);
//									}
//									if (!Check.NuNStrStrict(sb.toString())) {
//										sb.append(":");
//									}
//									if (!Check.NuNStrStrict(houseBizMsg.getRefuseMark())) {
//										sb.append(houseBizMsg.getRefuseMark());
//									}
									if(Check.NuNStrStrict(houseBizMsg.getRefuseMark())){
										houseRoomVo.setRefuseReason(houseBizMsg.getRefuseMark());
									}
								}
								Map<String, Object> map = whetherBookPhoto(paramDto.getLandlordUid(),houseRoomVo.getHouseBaseFid(),houseRoomVo.getHouseRoomFid());
								Integer i =(Integer)map.get("whether");
								if(i>0){
									//可以预约
									houseRoomVo.setWhetherBookPhoto(i);
								}else{
									//不可以预约
									houseRoomVo.setWhetherBookPhoto(i);
								}
								String bookTime = (String)map.get("bookTime");
								if(!Check.NuNObj(map.get("bookTime"))){
									bookTime = bookTime.substring(5,bookTime.length()-3);
									houseRoomVo.setBookStartTime(bookTime);
								}
								if(!Check.NuNObj(map.get("photoStatus"))){
									houseRoomVo.setPhotoStatus((Integer)map.get("photoStatus"));
								}
								if(!Check.NuNObj(map.get("photoStatName"))){
									houseRoomVo.setPhotoStatName((String)map.get("photoStatName"));
								}
							}


						}

						list.addAll(roomList);
					} else {
						if (Check.NuNObj(vo.getHouseStatus())) {
							vo.setHouseStatus(HouseStatusEnum.DFB.getCode());
							vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getHouseStatus()).getShowStatusName());
							vo.setStatus(HouseStatusEnum.DFB.getCode());
						} else {
							vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getHouseStatus()).getShowStatusName());
							vo.setStatus(vo.getHouseStatus());
						}
						if (!Check.NuNStr(vo.getDefaultPic())){
							vo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, vo.getDefaultPic(), pic_size));
						}
						list.add(vo);
					}
				}
			}

			//30天出租率
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startTime = DateUtil.parseDate(sdf.format(new Date()), "yyyy-MM-dd");
			Date endTime = DateUtils.addDays(startTime, 30);
			for (HouseRoomVo vo : list) {
				LockHouseCountRequest paramRequest = new LockHouseCountRequest();
				paramRequest.setRentWay(vo.getRentWay());
				paramRequest.setHouseFid(vo.getHouseBaseFid());
				paramRequest.setRoomFid(vo.getHouseRoomFid());
				paramRequest.setStartTime(startTime);
				paramRequest.setEndTime(endTime);
				paramRequest.setLockType(LockTypeEnum.ORDER.getCode());

				Double houseBookRate = 0.0d;
				int orderLockCount = 0;
				int lanLockCount = 0;

				//排除房间没有的房源
				if (!Check.NuNObj(paramRequest.getRentWay())
						&& (paramRequest.getRentWay().intValue() != RentWayEnum.ROOM.getCode()
						|| !Check.NuNStr(paramRequest.getRoomFid()))) {
					DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(
							orderUserService.getBookDaysByFid(JsonEntityTransform.Object2Json(paramRequest)));
					if (orderDto.getCode() == DataTransferObject.SUCCESS) {
						orderLockCount = (int) orderDto.getData().get("count");
					}
					paramRequest.setLockType(LockTypeEnum.LANDLADY.getCode());
					DataTransferObject lanDto = JsonEntityTransform.json2DataTransferObject(
							orderUserService.getBookDaysByFid(JsonEntityTransform.Object2Json(paramRequest)));

					if (lanDto.getCode() == DataTransferObject.SUCCESS) {
						lanLockCount = (int) lanDto.getData().get("count");
					}

					if (lanLockCount >= 30) {
						houseBookRate = 0.0;
					} else {
						houseBookRate = BigDecimalUtil.div(orderLockCount * 100, 30.0 - lanLockCount, 1);
					}

				}

				vo.setHouseBookRate(houseBookRate);

				//查询评论数
				StatsHouseEvaRequest statsHouseEvaRequest=new StatsHouseEvaRequest();
				statsHouseEvaRequest.setHouseFid(vo.getHouseBaseFid());
				statsHouseEvaRequest.setRoomFid(vo.getHouseRoomFid());
				statsHouseEvaRequest.setRentWay(vo.getRentWay());
				LogUtil.debug(LOGGER, "查询评论数参数：" + JsonEntityTransform.Object2Json(statsHouseEvaRequest));
				String evaluateCountJson = evaluateOrderService
						.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));

				List<StatsHouseEvaEntity> evaluateStats = SOAResParseUtil
						.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
				if(!Check.NuNCollection(evaluateStats)){
					LogUtil.debug(LOGGER, "查询评论数结果：" + JsonEntityTransform.Object2Json(evaluateStats));
					vo.setHouseEvaScore("无评分");
					StatsHouseEvaEntity statsHouseEvaEntity = evaluateStats.get(0);
					if (statsHouseEvaEntity.getEvaTotal() > 0) {
						double evalScore = BigDecimalUtil.div(statsHouseEvaEntity.getHouseCleanAva()
								+ statsHouseEvaEntity.getDesMatchAva() + statsHouseEvaEntity.getSafeDegreeAva()
								+ statsHouseEvaEntity.getTrafPosAva() + statsHouseEvaEntity.getCostPerforAva(), 5.0, 1);
						vo.setHouseEvaScore(String.valueOf(evalScore));
					}
				}
				//今夜特价逻辑加入
				//LogUtil.info(LOGGER, "vo数据那个是空" + JsonEntityTransform.Object2Json(vo));
				if((!Check.NuNObj(vo.getHouseStatus())&&vo.getHouseStatus()==HouseStatusEnum.SJ.getCode())||(!Check.NuNObj(vo.getRoomStatus())&&vo.getRoomStatus()==HouseStatusEnum.SJ.getCode())){
					TonightDiscountEntity tonightDiscountEntity=new TonightDiscountEntity();
					tonightDiscountEntity.setHouseFid(vo.getHouseBaseFid());
					tonightDiscountEntity.setRoomFid(vo.getHouseRoomFid());
					tonightDiscountEntity.setRentWay(vo.getRentWay());
					String todayDiscountJson=houseTonightDiscountService.findTonightDiscountByRentway(JsonEntityTransform.Object2Json(tonightDiscountEntity));
					TonightDiscountEntity discountEntity=SOAResParseUtil.getValueFromDataByKey(todayDiscountJson, "data", TonightDiscountEntity.class);
					if(Check.NuNObj(discountEntity)){
						vo.setIsTodayDiscount(1);
					} else {
						vo.setIsTodayDiscount(2);
						String endTimeStr=DateUtil.dateFormat(discountEntity.getEndTime(), "HH:mm");
						if("23:59".equals(endTimeStr)){
							endTimeStr="24:00";
						}
						vo.setTodayDiscount(endTimeStr+"结束"+BigDecimalUtil.mul(discountEntity.getDiscount(), 10)+"折优惠");
					}
					//判断今天是否已经有订单
					String isOrderJson=houseCommonService.isHousePayLockCurrentDay(JsonEntityTransform.Object2Json(tonightDiscountEntity));
					LogUtil.info(LOGGER, "今天是否已经有订单的判断：{}",isOrderJson);
					boolean isOrder=SOAResParseUtil.getBooleanFromDataByKey(isOrderJson, "data");
					LogUtil.info(LOGGER, "今天是否已经有订单的判断：{}",isOrder);
					if(isOrder){
						vo.setIsTodayDiscount(0);
					}
				} else {
					vo.setIsTodayDiscount(0);
				}
			}
			DataTransferObject dto = new DataTransferObject();
			dto.putValue("list", list);

			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 *
	 * 房东房源列表（新）
	 *
	 * @author bushujie
	 * @created 2017年7月11日 上午9:56:26
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "${LOGIN_AUTH}/houseRoomListNew")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> houseRoomListNew(HttpServletRequest request) {
		try {
			Header header=getHeader(request);
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);

			ValidateResult<HouseBaseListDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseBaseListDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseBaseListDto paramDto = validateResult.getResultObj();

			//以房源维度查询房东房源列表
			String resultJson = houseManageService.searchLandlordHouseList(paramJson);
			List<HouseRoomVo> houseList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomVo.class);

			List<HouseRoomVo> list = new ArrayList<HouseRoomVo>();
			//循环房源处理数据
			for (HouseRoomVo vo : houseList) {
				if (!Check.NuNStr(vo.getZoCode())) {
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

				if (vo.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
					//查询正在审核字段map
					Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(vo.getHouseBaseFid(), null, RentWayEnum.HOUSE.getCode(), 0);
					if (Check.NuNStrStrict(vo.getName())) {
						vo.setName("待完善房源");
					}
					//替换房源名称和默认图片
					if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath())){
						vo.setName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath()).getNewValue());
					}
					if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath())){
		            	String defaultPicJson=houseIssueService.searchHousePicMsgByFid(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath()).getNewValue());
		            	HousePicMsgEntity housePicMsgEntity=SOAResParseUtil.getValueFromDataByKey(defaultPicJson, "obj", HousePicMsgEntity.class);
		            	if(!Check.NuNObj(housePicMsgEntity)){
		            		vo.setDefaultPic(housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix());
		            	}
					}
					if (!Check.NuNStr(vo.getDefaultPic())) {
						vo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, vo.getDefaultPic(), pic_size));
					}
					//默认不可以预约，如果品质审核未通过，且是照片原因，则置为可预约
					vo.setWhetherBookPhoto(0);
					if (!Check.NuNObj(vo.getHouseStatus()) && vo.getHouseStatus().intValue() == HouseStatusEnum.ZPSHWTG.getCode()) {
						HouseBizMsgEntity houseBizMsg = this.getHouseBizMsg(vo.getHouseBaseFid(), vo.getRentWay());
						if (!Check.NuNObj(houseBizMsg)) {
//							StringBuilder sb = new StringBuilder();
//							if (!Check.NuNStrStrict(houseBizMsg.getRefuseReason())) {
//								String refuseReason = HouseAuditCauseEnum.getNameStr(houseBizMsg.getRefuseReason());
//								sb.append(refuseReason);
//							}
//							if (!Check.NuNStrStrict(sb.toString())) {
//								sb.append(":");
//							}
//							if (!Check.NuNStrStrict(houseBizMsg.getRefuseMark())) {
//								sb.append(houseBizMsg.getRefuseMark());
//							}
							if(!Check.NuNStrStrict(houseBizMsg.getRefuseMark())){
								vo.setRefuseReason(houseBizMsg.getRefuseMark());
							}
						}
						//是否可以预约摄影
						Map<String, Object> map = whetherBookPhoto(paramDto.getLandlordUid(), vo.getHouseBaseFid(), null);
						Integer i = (Integer) map.get("whether");
						if (i > 0) {
							//可以预约
							vo.setWhetherBookPhoto(i);
						} else {
							//不可以预约
							vo.setWhetherBookPhoto(i);
						}
						String bookTime = (String) map.get("bookTime");
						if (!Check.NuNObj(map.get("bookTime"))) {
							bookTime = bookTime.substring(5, bookTime.length() - 3);
							vo.setBookStartTime(bookTime);
						}
						if (!Check.NuNObj(map.get("photoStatus"))) {
							vo.setPhotoStatus((Integer) map.get("photoStatus"));
						}
						if (!Check.NuNObj(map.get("photoStatName"))) {
							vo.setPhotoStatName((String) map.get("photoStatName"));
						}
					}
					
					//填充房源预定类型
                    if(!Check.NuNObj(vo.getOrderType())){
                    	OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(vo.getOrderType());
                    	if(!Check.NuNObj(orderTypeEnum) && !Check.NuNStr(orderTypeEnum.getName())){
                    		vo.setOrderTypeStr(orderTypeEnum.getName());
                    	}
                    }
					list.add(vo);
				} else if (vo.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
					HouseBaseListDto dto = new HouseBaseListDto();
					dto.setHouseBaseFid(vo.getHouseBaseFid());
					dto.setLandlordUid(paramDto.getLandlordUid());
					//查询房源是否共享客厅
					String isHallJson=houseIssueService.isHallByHouseBaseFid(vo.getHouseBaseFid());
					Integer isHall=SOAResParseUtil.getIntFromDataByKey(isHallJson, "isHall");
					vo.setRoomType(isHall);
					// 查询房源包含房间列表
					String roomJson = houseManageService.searchHouseRoomList(JsonEntityTransform.Object2Json(dto));
					LogUtil.debug(LOGGER, "房源fid:" + dto.getHouseBaseFid() + ",查询房间列表返回结果：" + roomJson);
					List<HouseRoomVo> roomList = SOAResParseUtil.getListValueFromDataByKey(roomJson, "list", HouseRoomVo.class);
					//如果是共享客厅并且版本号是旧版，去掉
					if(isHall==1&&header.getVersionCode()<=100025){
						continue;
					}
					//判断是否发布过 房源
					if (vo.getIsIssue() == YesOrNoEnum.YES.getCode()||isHall==1) {
						if (!Check.NuNCollection(roomList)) {
							for (HouseRoomVo houseRoomVo : roomList) {
								//查询待审核字段map
								Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(houseRoomVo.getHouseBaseFid(), houseRoomVo.getHouseRoomFid(), RentWayEnum.ROOM.getCode(), 0);
								//替换房源名称和默认图片
								if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath())){
									houseRoomVo.setName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath()).getNewValue());
								}
								if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath())){
					            	String defaultPicJson=houseIssueService.searchHousePicMsgByFid(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath()).getNewValue());
					            	HousePicMsgEntity housePicMsgEntity=SOAResParseUtil.getValueFromDataByKey(defaultPicJson, "obj", HousePicMsgEntity.class);
					            	if(!Check.NuNObj(housePicMsgEntity)){
					            		houseRoomVo.setDefaultPic(housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix());
					            	}
								}
								if (!Check.NuNStr(houseRoomVo.getDefaultPic())) {
									houseRoomVo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, houseRoomVo.getDefaultPic(), pic_size));
								}
								//默认不可以预约，如果品质审核未通过，且是照片原因，则置为可预约
								houseRoomVo.setWhetherBookPhoto(0);
								houseRoomVo.setIsIssue(vo.getIsIssue());
								houseRoomVo.setOperateSeq(vo.getOperateSeq());
								if (!Check.NuNObj(houseRoomVo.getRoomStatus()) && houseRoomVo.getRoomStatus().intValue() == HouseStatusEnum.ZPSHWTG.getCode()) {
									HouseBizMsgEntity houseBizMsg = this.getHouseBizMsg(houseRoomVo.getHouseRoomFid(), vo.getRentWay());
									if (!Check.NuNObj(houseBizMsg)) {
//										StringBuilder sb = new StringBuilder();
//										if (!Check.NuNStrStrict(houseBizMsg.getRefuseReason())) {
//											String refuseReason = HouseAuditCauseEnum.getNameStr(houseBizMsg.getRefuseReason());
//											sb.append(refuseReason);
//										}
//										if (!Check.NuNStrStrict(sb.toString())) {
//											sb.append(":");
//										}
//										if (!Check.NuNStrStrict(houseBizMsg.getRefuseMark())) {
//											sb.append(houseBizMsg.getRefuseMark());
//										}
										if(!Check.NuNStrStrict(houseBizMsg.getRefuseMark())){
											houseRoomVo.setRefuseReason(houseBizMsg.getRefuseMark());
										}
									}
									Map<String, Object> map = whetherBookPhoto(paramDto.getLandlordUid(), houseRoomVo.getHouseBaseFid(), houseRoomVo.getHouseRoomFid());
									Integer i = (Integer) map.get("whether");
									if (i > 0) {
										//可以预约
										houseRoomVo.setWhetherBookPhoto(i);
									} else {
										//不可以预约
										houseRoomVo.setWhetherBookPhoto(i);
									}
									String bookTime = (String) map.get("bookTime");
									if (!Check.NuNObj(map.get("bookTime"))) {
										bookTime = bookTime.substring(5, bookTime.length() - 3);
										houseRoomVo.setBookStartTime(bookTime);
									}
									if (!Check.NuNObj(map.get("photoStatus"))) {
										houseRoomVo.setPhotoStatus((Integer) map.get("photoStatus"));
									}
									if (!Check.NuNObj(map.get("photoStatName"))) {
										houseRoomVo.setPhotoStatName((String) map.get("photoStatName"));
									}
								}
								//共享客厅名称不存在
								if(Check.NuNStr(houseRoomVo.getName())&&isHall==1){
									houseRoomVo.setName("待完善房源");
								}
								//填充房源预定类型
			                    if(!Check.NuNObj(vo.getOrderType())){
			                    	OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(vo.getOrderType());
			                    	if(!Check.NuNObj(orderTypeEnum) && !Check.NuNStr(orderTypeEnum.getName())){
			                    		vo.setOrderTypeStr(orderTypeEnum.getName());
			                    	}
			                    }
							}
							list.addAll(roomList);
						} else {
							if (!Check.NuNStr(vo.getDefaultPic())) {
								vo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, vo.getDefaultPic(), pic_size));
							}
							list.add(vo);
						}
					} else {
						//合租按房源显示，需要找到房源名称
						if (!Check.NuNCollection(roomList)) {
							String houseName = null;
							for (HouseRoomVo houseRoomVo : roomList) {
								if (!Check.NuNStr(houseRoomVo.getRoomName())) {
									houseName = houseRoomVo.getRoomName() + "等";
									break;
								}
							}
							if (Check.NuNStr(houseName)) {
								vo.setName("待完善房源");
							} else {
								vo.setName(houseName);
							}
						} else {
							vo.setName("待完善房源");
						}
						if (!Check.NuNStr(vo.getDefaultPic())) {
							vo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, vo.getDefaultPic(), pic_size));
						}
						list.add(vo);
					}
				}
			}

			//30天出租率
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startTime = DateUtil.parseDate(sdf.format(new Date()), "yyyy-MM-dd");
			Date endTime = DateUtils.addDays(startTime, 30);
			for (HouseRoomVo vo : list) {
				LockHouseCountRequest paramRequest = new LockHouseCountRequest();
				paramRequest.setRentWay(vo.getRentWay());
				paramRequest.setHouseFid(vo.getHouseBaseFid());
				paramRequest.setRoomFid(vo.getHouseRoomFid());
				paramRequest.setStartTime(startTime);
				paramRequest.setEndTime(endTime);
				paramRequest.setLockType(LockTypeEnum.ORDER.getCode());

				Double houseBookRate = 0.0d;
				int orderLockCount = 0;
				int lanLockCount = 0;

				//排除房间没有的房源
				if (!Check.NuNObj(paramRequest.getRentWay())
						&& (paramRequest.getRentWay().intValue() != RentWayEnum.ROOM.getCode()
						|| !Check.NuNStr(paramRequest.getRoomFid()))) {
					DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(
							orderUserService.getBookDaysByFid(JsonEntityTransform.Object2Json(paramRequest)));
					if (orderDto.getCode() == DataTransferObject.SUCCESS) {
						orderLockCount = (int) orderDto.getData().get("count");
					}
					paramRequest.setLockType(LockTypeEnum.LANDLADY.getCode());
					DataTransferObject lanDto = JsonEntityTransform.json2DataTransferObject(
							orderUserService.getBookDaysByFid(JsonEntityTransform.Object2Json(paramRequest)));

					if (lanDto.getCode() == DataTransferObject.SUCCESS) {
						lanLockCount = (int) lanDto.getData().get("count");
					}

					if (lanLockCount >= 30) {
						houseBookRate = 0.0;
					} else {
						houseBookRate = BigDecimalUtil.div(orderLockCount * 100, 30.0 - lanLockCount, 1);
					}

				}

				vo.setHouseBookRate(houseBookRate);

				//查询评论数
				StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
				statsHouseEvaRequest.setHouseFid(vo.getHouseBaseFid());
				statsHouseEvaRequest.setRoomFid(vo.getHouseRoomFid());
				statsHouseEvaRequest.setRentWay(vo.getRentWay());
				LogUtil.debug(LOGGER, "查询评论数参数：" + JsonEntityTransform.Object2Json(statsHouseEvaRequest));
				String evaluateCountJson = evaluateOrderService
						.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));

				List<StatsHouseEvaEntity> evaluateStats = SOAResParseUtil
						.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
				if (!Check.NuNCollection(evaluateStats)) {
					LogUtil.debug(LOGGER, "查询评论数结果：" + JsonEntityTransform.Object2Json(evaluateStats));
					vo.setHouseEvaScore("无评分");
					StatsHouseEvaEntity statsHouseEvaEntity = evaluateStats.get(0);
					if (statsHouseEvaEntity.getEvaTotal() > 0) {
						double evalScore = BigDecimalUtil.div(statsHouseEvaEntity.getHouseCleanAva()
								+ statsHouseEvaEntity.getDesMatchAva() + statsHouseEvaEntity.getSafeDegreeAva()
								+ statsHouseEvaEntity.getTrafPosAva() + statsHouseEvaEntity.getCostPerforAva(), 5.0, 1);
						vo.setHouseEvaScore(String.valueOf(evalScore));
					}
				}
				//今夜特价逻辑加入
				//LogUtil.info(LOGGER, "vo数据那个是空" + JsonEntityTransform.Object2Json(vo));
				if ((!Check.NuNObj(vo.getHouseStatus()) && vo.getHouseStatus() == HouseStatusEnum.SJ.getCode()) || (!Check.NuNObj(vo.getRoomStatus()) && vo.getRoomStatus() == HouseStatusEnum.SJ.getCode())) {
					TonightDiscountEntity tonightDiscountEntity = new TonightDiscountEntity();
					tonightDiscountEntity.setHouseFid(vo.getHouseBaseFid());
					tonightDiscountEntity.setRoomFid(vo.getHouseRoomFid());
					tonightDiscountEntity.setRentWay(vo.getRentWay());
					String todayDiscountJson = houseTonightDiscountService.findTonightDiscountByRentway(JsonEntityTransform.Object2Json(tonightDiscountEntity));
					TonightDiscountEntity discountEntity = SOAResParseUtil.getValueFromDataByKey(todayDiscountJson, "data", TonightDiscountEntity.class);
					if (Check.NuNObj(discountEntity)) {
						vo.setIsTodayDiscount(1);
					} else {
						vo.setIsTodayDiscount(2);
						String endTimeStr = DateUtil.dateFormat(discountEntity.getEndTime(), "HH:mm");
						if ("23:59".equals(endTimeStr)) {
							endTimeStr = "24:00";
						}
						vo.setTodayDiscount(endTimeStr + "结束" + BigDecimalUtil.mul(discountEntity.getDiscount(), 10) + "折优惠");
					}
					//判断今天是否已经有订单
					String isOrderJson = houseCommonService.isHousePayLockCurrentDay(JsonEntityTransform.Object2Json(tonightDiscountEntity));
					LogUtil.info(LOGGER, "今天是否已经有订单的判断：{}", isOrderJson);
					boolean isOrder = SOAResParseUtil.getBooleanFromDataByKey(isOrderJson, "data");
					LogUtil.info(LOGGER, "今天是否已经有订单的判断：{}", isOrder);
					if (isOrder) {
						vo.setIsTodayDiscount(0);
					}
				} else {
					vo.setIsTodayDiscount(0);
				}
				
				//填充房源预定类型
                if(!Check.NuNObj(vo.getOrderType())){
                	OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(vo.getOrderType());
                	if(!Check.NuNObj(orderTypeEnum) && !Check.NuNStr(orderTypeEnum.getName())){
                		vo.setOrderTypeStr(orderTypeEnum.getName());
                	}
                }
			}
			DataTransferObject dto = new DataTransferObject();
			dto.putValue("list", list);
			LogUtil.info(LOGGER, "房东房源列表返回值：{}", JsonEntityTransform.Object2Json(list));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 获取房源(房间)业务信息
	 *
	 * @param houseFid
	 * @param rentWay
	 * @return
	 * @author liujun
	 * @created 2017年3月6日
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
	 * 是否有上架房源
	 *
	 * @param request
	 * @return
	 * @author jixd
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/hasOnlineHouse", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> hasOnlineHouse(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		obj.put("hasOnlineHouse", 0);
		try {
			String uid = (String) request.getAttribute("uid");
			HouseBaseListDto houseBaseListDto = new HouseBaseListDto();
			houseBaseListDto.setLandlordUid(uid);
			String resultJson = housePCService.getOnlineHouseRoomList(uid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<HouseRoomVo> list = dto.parseData("list", new TypeReference<List<HouseRoomVo>>() {
			});
			if (!Check.NuNCollection(list) && list.size() > 0) {
				obj.put("hasOnlineHouse", 1);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(obj), HttpStatus.OK);
	}

	/**
	 * 提升房源排名
	 *
	 * @param request
	 * @return
	 * @author jixd
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/improveHouseRank")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> improveHouseRank(HttpServletRequest request) {

		String uid = (String) request.getAttribute("uid");
		if (Check.NuNStr(uid)) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
		}


		String resultJson = landlordBehaviorService.findLandlordBehavior(uid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务异常"), HttpStatus.OK);
		}

		LandlordBehaviorEntity landlordBehavior = dto.parseData("obj", new TypeReference<LandlordBehaviorEntity>() {
		});
		if (Check.NuNObj(landlordBehavior)) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务异常"), HttpStatus.OK);
		}

		//设置后台配置的默认值
		String defaultAvgValJson = cityTemplateService.getTextListByLikeCodes(null, "HouseRankEnum");
		dto = JsonEntityTransform.json2DataTransferObject(defaultAvgValJson);
		if (dto.getCode() == DataTransferObject.SUCCESS) {

			List<MinsuEleEntity> defaultAvgValList = dto.parseData("confList", new TypeReference<List<MinsuEleEntity>>() {
			});
			if (!Check.NuNCollection(defaultAvgValList)) {

				for (MinsuEleEntity dicItemEntity : defaultAvgValList) {
					try {
						if (dicItemEntity.getEleKey().equals(HouseRankEnum.HouseRankEnum001.getValue())) {
							LandStaticsDataTemplate.REPLY_RATE_AVG_DEFAULT_VALUE = Integer.valueOf(dicItemEntity.getEleValue());
						} else if (dicItemEntity.getEleKey().equals(HouseRankEnum.HouseRankEnum002.getValue())) {
							LandStaticsDataTemplate.TENEVA_RATE_DEFAULT_VALUE = Integer.valueOf(dicItemEntity.getEleValue());
						} else if (dicItemEntity.getEleKey().equals(HouseRankEnum.HouseRankEnum003.getValue())) {
							LandStaticsDataTemplate.LANDEVA_RATE_DEFAULT_VALUE = Integer.valueOf(dicItemEntity.getEleValue());
						} else if (dicItemEntity.getEleKey().equals(HouseRankEnum.HouseRankEnum004.getValue())) {
							LandStaticsDataTemplate.ORDER_RATE_DEFAULT_VALUE = Integer.valueOf(dicItemEntity.getEleValue());
						}
					} catch (Exception e) {
						LogUtil.info(LOGGER, "解析房源排名默认值异常e={}", e);
					}
				}
			}
		}

		JSONObject obj = new JSONObject();
		JSONObject habit = new JSONObject();
		habit.put("title", LandStaticsDataTemplate.PAGE_TITLE);

		JSONArray habitList = new JSONArray();

   /*-------------------------咨询------------------------*/
		JSONObject chatObj = new JSONObject();
		chatObj.put("title", LandStaticsDataTemplate.MSG_TITLE);

		JSONArray chatArray = new JSONArray();
		JSONObject queryItem = new JSONObject();

		Double replyRate = Double.valueOf(0L);
		if (!Check.NuNObj(landlordBehavior.getAdvisoryNum()) && !Check.NuNObj(landlordBehavior.getReplyNum()) && landlordBehavior.getAdvisoryNum() != 0) {
			replyRate = Double.valueOf(100 * landlordBehavior.getReplyNum() / landlordBehavior.getAdvisoryNum());
		}

		queryItem.put("title", String.format(LandStaticsDataTemplate.REPLY_RATE_TITLE, replyRate.intValue()));
		queryItem.put("subTitle", String.format(LandStaticsDataTemplate.REPLY_RATE_SUBTITLE, landlordBehavior.getAdvisoryNum(), landlordBehavior.getReplyNum()));
		JSONObject zixunProgressBar = new JSONObject();
		zixunProgressBar.put("value", replyRate.intValue());
		if (replyRate.intValue() < LandStaticsDataTemplate.REPLY_RATE_AVG_DEFAULT_VALUE) {
			zixunProgressBar.put("color", LandStaticsDataTemplate.REPLY_RATE_COLOR_RED);
			zixunProgressBar.put("warnMSG", LandStaticsDataTemplate.REPLY_RATE_WARNMSG);
		} else {
			zixunProgressBar.put("color", LandStaticsDataTemplate.REPLY_RATE_COLOR_OK);
		}
		queryItem.put("progressBar", zixunProgressBar);

		JSONObject replayItem = new JSONObject();
		replayItem.put("title", String.format(LandStaticsDataTemplate.REPLY_TIME_AVG_TITLE, landlordBehavior.getReplyTimeAvg()));
		replayItem.put("subTitle", String.format(LandStaticsDataTemplate.REPLY_TIME_AVG_SUBTITLE, landlordBehavior.getReplyTimeMax(), landlordBehavior.getReplyTimeMin()));
		chatArray.add(0, queryItem);
		chatArray.add(1, replayItem);

		chatObj.put("itemList", chatArray);
	/*------------------------评价------------------------------*/
		JSONObject evaObj = new JSONObject();
		evaObj.put("title", LandStaticsDataTemplate.EVA_TITLE);
		JSONArray evaArray = new JSONArray();

		JSONObject tenEva = new JSONObject();

		Double tenEvaRate = Double.valueOf(0L);
		if (!Check.NuNObj(landlordBehavior.getTenHasEvaNum()) && !Check.NuNObj(landlordBehavior.getTotalOrderNum()) && landlordBehavior.getTotalOrderNum() != 0) {
			tenEvaRate = Double.valueOf(100 * landlordBehavior.getTenHasEvaNum() / landlordBehavior.getTotalOrderNum());
		}
		tenEva.put("title", String.format(LandStaticsDataTemplate.TENEVA_RATE_TITLE, tenEvaRate.intValue()));
		tenEva.put("subTitle", String.format(LandStaticsDataTemplate.TENEVA_RATE_SUBTITLE, landlordBehavior.getTenWaitEvaNum(), landlordBehavior.getTenHasEvaNum()));
		JSONObject tenEvaProgressBar = new JSONObject();
		tenEvaProgressBar.put("value", tenEvaRate.intValue());
		if (tenEvaRate.intValue() < LandStaticsDataTemplate.TENEVA_RATE_DEFAULT_VALUE) {
			tenEvaProgressBar.put("color", LandStaticsDataTemplate.TENEVA_RATE_COLOR_RED);
			tenEvaProgressBar.put("warnMSG", LandStaticsDataTemplate.TENEVA_RATE_WARNMSG);
		} else {
			tenEvaProgressBar.put("color", LandStaticsDataTemplate.TENEVA_RATE_COLOR_OK);
		}
		tenEva.put("progressBar", tenEvaProgressBar);


		JSONObject lanEva = new JSONObject();
		Double lanEvaRate = Double.valueOf(0L);
		if (!Check.NuNObj(landlordBehavior.getLanHasEvaNum()) && !Check.NuNObj(landlordBehavior.getTotalOrderNum()) && landlordBehavior.getTotalOrderNum() != 0) {
			lanEvaRate = Double.valueOf(100 * landlordBehavior.getLanHasEvaNum() / landlordBehavior.getTotalOrderNum());
		}
		lanEva.put("title", String.format(LandStaticsDataTemplate.LANDEVA_RATE_TITLE, lanEvaRate.intValue()));
		lanEva.put("subTitle", String.format(LandStaticsDataTemplate.LANDEVA_RATE_SUBTITLE, landlordBehavior.getLanWaitEvaNum(), landlordBehavior.getLanHasEvaNum()));
		JSONObject lanEvaProgressBar = new JSONObject();
		lanEvaProgressBar.put("value", lanEvaRate.intValue());
		if (tenEvaRate.intValue() < LandStaticsDataTemplate.LANDEVA_RATE_DEFAULT_VALUE) {
			lanEvaProgressBar.put("color", LandStaticsDataTemplate.LANDEVA_RATE_COLOR_RED);
			lanEvaProgressBar.put("warnMSG", LandStaticsDataTemplate.LANDEVA_RATE_WARNMSG);
		} else {
			lanEvaProgressBar.put("color", LandStaticsDataTemplate.LANDEVA_RATE_COLOR_OK);
		}
		lanEva.put("progressBar", lanEvaProgressBar);

		evaArray.add(0, tenEva);
		evaArray.add(1, lanEva);
		evaObj.put("itemList", evaArray);

     /*---------------------------订单------------------------------*/
		JSONObject orderObj = new JSONObject();
		orderObj.put("title", LandStaticsDataTemplate.ORDER_TITLE);
		JSONArray orderArray = new JSONArray();

		JSONObject orderItem = new JSONObject();
		Double orderRate = Double.valueOf(0L);
		if (!Check.NuNObj(landlordBehavior.getTotalOrderNum()) && !Check.NuNObj(landlordBehavior.getAcceptOrderNum()) && landlordBehavior.getTotalOrderNum() != 0) {
			orderRate = Double.valueOf(100 * landlordBehavior.getAcceptOrderNum() / landlordBehavior.getTotalOrderNum());
		}
		orderItem.put("title", String.format(LandStaticsDataTemplate.ORDER_RATE_TITLE, orderRate.intValue()));
		orderItem.put("subTitle", String.format(LandStaticsDataTemplate.ORDER_RATE_SUBTITLE, landlordBehavior.getTotalOrderNum(), landlordBehavior.getAcceptOrderNum(), landlordBehavior.getRefuseOrderNum(), landlordBehavior.getNotdoOrderNum()));

		JSONObject orderProgressBar = new JSONObject();
		orderProgressBar.put("value", orderRate.intValue());
		if (orderRate.intValue() < LandStaticsDataTemplate.ORDER_RATE_DEFAULT_VALUE) {
			orderProgressBar.put("color", LandStaticsDataTemplate.ORDER_RATE_COLOR_RED);
			orderProgressBar.put("warnMSG", LandStaticsDataTemplate.ORDER_RATE_WARNMSG);
		} else {
			orderProgressBar.put("color", LandStaticsDataTemplate.ORDER_RATE_COLOR_OK);
		}
		orderItem.put("progressBar", orderProgressBar);

		orderArray.add(orderItem);
		orderObj.put("itemList", orderArray);


		habitList.add(chatObj);
		habitList.add(evaObj);
		habitList.add(orderObj);
		habit.put("list", habitList);

        /*------------------------提升房源任务排名---------------------------*/

		JSONObject task = new JSONObject();
		task.put("title", LandStaticsDataTemplate.TASK_TITLE);

		JSONArray taskArray = new JSONArray();

		JSONObject taskItem0 = new JSONObject();

		taskItem0.put("title", LandStaticsDataTemplate.LANWAITEVA_LABLE);
		if (landlordBehavior.getLanWaitEvaNum() == null || landlordBehavior.getLanWaitEvaNum() == 0) {
			taskItem0.put("result", LandStaticsDataTemplate.LANWAITEVA_NO_RESULT);
		} else {
			taskItem0.put("result", String.format(LandStaticsDataTemplate.LANWAITEVA_RESULT, landlordBehavior.getLanWaitEvaNum()));
		}
		taskItem0.put("type", 1);
		taskItem0.put("url", "/landlordEva/" + LOGIN_UNAUTH + "/index");
		if (!Check.NuNObj(landlordBehavior.getLanWaitEvaNum()) && landlordBehavior.getLanWaitEvaNum() > 0) {
			taskItem0.put("hasFinish", 0);
		}
		if (Check.NuNObj(landlordBehavior.getLanWaitEvaNum()) || landlordBehavior.getLanWaitEvaNum() == 0) {
			taskItem0.put("hasFinish", 1);
		}

		JSONObject taskItem1 = new JSONObject();
		taskItem1.put("title", LandStaticsDataTemplate.TENWAITEVA_LABLE);
		if (landlordBehavior.getLanWaitEvaNum() == null || landlordBehavior.getLanWaitEvaNum() == 0) {
			taskItem1.put("result", LandStaticsDataTemplate.TENWAITEVA_NO_RESULT);
		} else {
			taskItem1.put("result", String.format(LandStaticsDataTemplate.TENWAITEVA_RESULT, landlordBehavior.getTenWaitEvaNum()));
		}
		taskItem1.put("type", 2);
		taskItem1.put("hasFinish", 1);

		//日历最后修改时间
		dto = JsonEntityTransform.json2DataTransferObject(houseManageService.getLastModifyCalendarDate(uid));
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "查询房东日历最后修改时间失败，uid:{},msg:{}", uid, dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务异常"), HttpStatus.OK);
		}
		Date lastModifyDate = dto.parseData("result", new TypeReference<Date>() {
		});

		JSONObject taskItem2 = new JSONObject();
		taskItem2.put("title", LandStaticsDataTemplate.LASTMODIFYDATE_LABLE);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (Check.NuNObj(lastModifyDate)) {
			taskItem2.put("result", LandStaticsDataTemplate.LASTMODIFYDATE_NO_RESULT);
		} else {
			taskItem2.put("result", String.format(LandStaticsDataTemplate.LASTMODIFYDATE_RESULT, dateFormat.format(lastModifyDate)));
		}
		taskItem2.put("type", 2);

		if (!Check.NuNObj(lastModifyDate) && dateFormat.format(lastModifyDate).equals(dateFormat.format(new Date()))) {
			taskItem2.put("hasFinish", 1);
		} else {
			taskItem2.put("hasFinish", 0);
		}

		JSONObject taskItem3 = new JSONObject();
		taskItem3.put("title", LandStaticsDataTemplate.CUSTOMERINFO_LABLE);

		dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCustomerDetailImage(uid));
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "查询房东信息失败，uid:{},msg:{}", uid, dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务异常"), HttpStatus.OK);
		}
		int hasNotFinish = 9;//暂时是个人资料页的字段
		CustomerDetailImageVo customerMsg = dto.parseData("customerImageVo", new TypeReference<CustomerDetailImageVo>() {
		});
		if (!Check.NuNObj(customerMsg)) {
			if (!Check.NuNStr(customerMsg.getNickName())) {
				hasNotFinish -= 1;
			}
			if (!Check.NuNObj(customerMsg.getCustomerSex())) {
				hasNotFinish -= 1;
			}
			if (!Check.NuNObj(customerMsg.getCustomerBirthday())) {
				hasNotFinish -= 1;
			}
			if (!Check.NuNStr(customerMsg.getCustomerMobile())) {
				hasNotFinish -= 1;
			}
			if (!Check.NuNStr(customerMsg.getCustomerEmail())) {
				hasNotFinish -= 1;
			}
			if (!Check.NuNStr(customerMsg.getResideAddr())) {
				hasNotFinish -= 1;
			}
			if (!Check.NuNObj(customerMsg.getCustomerEdu())) {
				hasNotFinish -= 1;
			}
			if (!Check.NuNStr(customerMsg.getCustomerJob())) {
				hasNotFinish -= 1;
			}
		}

		//查询房东的个人介绍
		dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.selectCustomerExtByUid(uid));
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "查询房东信息失败，uid:{},msg:{}", uid, dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务异常"), HttpStatus.OK);
		}
		CustomerBaseMsgExtEntity customerBaseMsgExt = dto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {
		});
		if (!Check.NuNObj(customerBaseMsgExt)) {
			if (!Check.NuNStr(customerBaseMsgExt.getCustomerIntroduce())) {
				hasNotFinish -= 1;
			}
		}
		if (hasNotFinish == 0) {
			taskItem3.put("result", LandStaticsDataTemplate.CUSTOMERINFO_NO_RESULT);
		} else {
			taskItem3.put("result", String.format(LandStaticsDataTemplate.CUSTOMERINFO_RESULT, hasNotFinish));
		}
		taskItem3.put("type", 2);
		if (hasNotFinish == 0) {
			taskItem3.put("hasFinish", 1);
		} else {
			taskItem3.put("hasFinish", 0);
		}

		taskArray.add(taskItem0);
		taskArray.add(taskItem1);
		taskArray.add(taskItem2);
		taskArray.add(taskItem3);
		task.put("list", taskArray);

		obj.put("habit", habit);
		obj.put("task", task);
		return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(obj), HttpStatus.OK);
	}

	/**
	 * 房东是否可以预约摄影
	 *
	 * @param
	 * @return
	 * @author lunan
	 */
	private Map<String, Object> whetherBookPhoto(String uid, String houseFid, String roomFid) {
		//初始值，可以预约
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			/** 根据houseFid去表中查询 是否此房源已预约过摄影,以及房源的一些基本信息*/
			String houseJson = houseIssueService.searchHouseBaseAndExtByFid(houseFid);
			if (Check.NuNStrStrict(houseJson)) {
				//房源不存在 故无法预约
				resultMap.put("whether", 0);
				return resultMap;
			} else {
				HouseBaseExtDto baseExtDto = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
				HouseBaseExtEntity houseBaseExt = baseExtDto.getHouseBaseExt();
				if (houseBaseExt.getIsPhotography() != YesOrNoEnum.NO.getCode()) {//此房源预约过摄影师
					/** 根据houseFid去预约摄影单表中查询*/
					PhotoOrderDto photoOrderDto = new PhotoOrderDto();
					photoOrderDto.setHouseFid(houseFid);
					String photogOrderDetail = troyPhotogBookService.findPhotogOrderDetail(JsonEntityTransform.Object2Json(photoOrderDto));
					if (!Check.NuNStr(photogOrderDetail)) {
						PhotographerBookOrderEntity bookOrderEntity = SOAResParseUtil.getValueFromDataByKey(photogOrderDetail, "bookOrderForAPI", PhotographerBookOrderEntity.class);
						if (!Check.NuNObj(bookOrderEntity)) {
							//已预约过订单，返回订单信息
							if (!Check.NuNObj(bookOrderEntity.getDoorHomeTime())) {
								resultMap.put("bookTime", DateUtil.dateFormat(bookOrderEntity.getDoorHomeTime(), "yyyy-MM-dd HH:mm:ss"));
							} else {
								resultMap.put("bookTime", DateUtil.dateFormat(bookOrderEntity.getBookStartTime(), "yyyy-MM-dd HH:mm:ss"));
							}
							resultMap.put("photoStatus", bookOrderEntity.getBookOrderStatu());
							resultMap.put("photoStatName", BookOrderStatuEnum.getEnummap().get(bookOrderEntity.getBookOrderStatu()));
						}
					}
					resultMap.put("whether", 0);
					return resultMap;
				}
			}
			/** 根据houseFid去house_biz_msg表中查询 是否房源品质审核未通过是因为照片原因*/
			//判断roomFid是否为空，不为空则分租，为空则整租，分别走不通的查询方法
			HousePicDto houseBiz = new HousePicDto();
			if (!Check.NuNStrStrict(roomFid)) {
				houseBiz.setHouseRoomFid(roomFid);
			} else {
				houseBiz.setHouseBaseFid(houseFid);
			}
			String reason = troyPhotogBookService.findHouseBizReason(JsonEntityTransform.Object2Json(houseBiz));
			DataTransferObject reasonDto = JsonEntityTransform.json2DataTransferObject(reason);
			if (reasonDto.getCode() == DataTransferObject.ERROR) {
				//未查询到品质审核未通过原因
				resultMap.put("whether", 0);
				return resultMap;
			}
			if (!Check.NuNStr(reason)) {
				HouseBizMsgEntity biz = SOAResParseUtil.getValueFromDataByKey(reason, "biz", HouseBizMsgEntity.class);
				//10 : 照片原因需要预约摄影师
				if (!Check.NuNObj(biz)) {
					String result = this.checkReason(biz.getRefuseReason(), "10");
					if (Check.NuNStr(result)) {
						//品质审核未通过没有照片原因
						resultMap.put("whether", 0);
						return resultMap;
					}
				} else {
					//未查询到品质审核未通过原因
					resultMap.put("whether", 0);
					return resultMap;
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			resultMap.put("whether", 0);
			return resultMap;
		}
		resultMap.put("whether", 1);
		return resultMap;
	}

	/**
	 * 处理品质审核未通过原因，可通用于其他逗号间隔的原因
	 */
	private String checkReason(String reason, String rule) {
		String[] arrs = reason.split(",");
		String result = null;
		for (int i = 0; i <= arrs.length - 1; i++) {
			if (rule.equals(arrs[i])) {
				result = rule;
			}
		}
		return result;
	}

	/**
	 * 根据开始时间获取时间段
	 *
	 * @param
	 * @return
	 * @author lunan
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/${LOGIN_AUTH}/bookPhotoTime")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> getTimeParams(HttpServletRequest request) {
		try {
			DataTransferObject dto = new DataTransferObject();

			Calendar rightNow = Calendar.getInstance();
			Calendar calendar = new GregorianCalendar();
			//当前 月   日   时
			/*int month = calendar.get(Calendar.MONTH) + 1; 
		    int day = calendar.get(Calendar.DAY_OF_MONTH);*/
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			Date date = new Date(System.currentTimeMillis());
			if (hour < 14) {
				calendar.setTime(date);
				calendar.add(calendar.DATE, addDayAm);//把日期往后增加一天.整数往后推,负数往前移动
				date = calendar.getTime();
			} else {
				calendar.setTime(date);
				calendar.add(calendar.DATE, addDayPm);
				date = calendar.getTime();
			}
			int days = Integer.parseInt(bookPhotoDays);

			List<TimeDto> times = new ArrayList<>();
//			Date date = new Date(start.getTime());
			for (int i = 0; i < days; i++) {

				TimeDto time = new TimeDto();
				String dateString = DateUtil.dateFormat(date, "yyyy-MM-dd");
				time.setMonthAndDay(dateString.substring(dateString.length() - 5, dateString.length()));
				time.setYear(dateString.substring(0, 4));
				//放入日期集合
				times.add(time);
				//时间加1
				Calendar cale = new GregorianCalendar();
				cale.setTime(date);
				cale.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
				date = cale.getTime();
			}
			dto.putValue("times", times);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
		}
	}

	/**
	 * 房东预约摄影
	 *
	 * @param request
	 * @return
	 * @author lunan
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/${LOGIN_AUTH}/bookPhoto")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> bookPhoto(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		//初始值，预约成功
		dto.putValue("ok", YesOrNoEnum.YES.getCode());
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.debug(LOGGER, "bookPhoto params:{}", paramJson);
			Map par = JsonEntityTransform.json2Map(paramJson);
			String uid = ValueUtil.getStrValue(par.get("uid"));
			String houseFid = ValueUtil.getStrValue(par.get("houseFid"));

			if (Check.NuNStrStrict(uid) || Check.NuNStrStrict(houseFid)) {
				dto.putValue("ok", YesOrNoEnum.NO.getCode());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}

			//根据房源fid查询房源基本信息
			String houseJson = houseIssueService.searchHouseBaseMsgByFid(houseFid);
			if (Check.NuNStr(houseJson)) {
				dto.putValue("ok", YesOrNoEnum.NO.getCode());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}

			HouseBaseMsgEntity houseBase = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseMsgEntity.class);
			BookHousePhotogDto bookPhotoDto = new BookHousePhotogDto();
			bookPhotoDto.setHouseFid(houseBase.getFid());
			bookPhotoDto.setHouseSn(houseBase.getHouseSn());
			bookPhotoDto.setHouseName(houseBase.getHouseName());
			bookPhotoDto.setShotAddr(houseBase.getHouseAddr());
			bookPhotoDto.setBookOrderStatu(BookOrderStatuEnum.ORDER_BOOKING.getCode());
			//根据房源fid查询citycode
			String phyHouse = houseIssueService.searchHousePhyMsgByHouseBaseFid(houseFid);
			if (Check.NuNStrStrict(phyHouse)) {
				//如果没有物理信息，则房源不存在
				dto.putValue("ok", YesOrNoEnum.NO.getCode());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}
			HousePhyMsgEntity phyMsgEntity = SOAResParseUtil.getValueFromDataByKey(phyHouse, "obj", HousePhyMsgEntity.class);
			//给bookOrder设置citycode
			bookPhotoDto.setCityCode(phyMsgEntity.getCityCode());
			//根据uid查询房东的信息
			dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerInfoByUid(uid));
			if (dto.getCode() == DataTransferObject.ERROR) {
				dto.putValue("ok", YesOrNoEnum.NO.getCode());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}
			CustomerBaseMsgEntity customerBase = dto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {
			});
			if (!Check.NuNObj(customerBase)) {
				bookPhotoDto.setCustomerUid(customerBase.getUid());
				bookPhotoDto.setCustomerName(customerBase.getRealName());
				bookPhotoDto.setCustomerMobile(customerBase.getCustomerMobile());

				bookPhotoDto.setBookerUid(customerBase.getUid());
				bookPhotoDto.setBookerName(customerBase.getRealName());
				bookPhotoDto.setBookerMobile(customerBase.getCustomerMobile());
			}
			//设置房东选择的开始和结束时间
			String startTimeStr = ValueUtil.getStrValue(par.get("startTimeStr")) + ":00";
//			String endTimeStr = ValueUtil.getStrValue(par.get("endTimeStr"));
			if (!Check.NuNStrStrict(startTimeStr)) {
				bookPhotoDto.setBookStartTime(DateUtil.parseDate(startTimeStr, "yyyy-MM-dd HH:mm"));
			}
			//设置房东预约备注
			String bookOrderRemark = ValueUtil.getStrValue(par.get("bookOrderRemark"));
			if (!Check.NuNStrStrict(bookOrderRemark)) {
				bookPhotoDto.setBookOrderRemark(bookOrderRemark);
			}
			dto = JsonEntityTransform.json2DataTransferObject(this.troyPhotogBookService.bookHousePhotographer(JsonEntityTransform.Object2Json(bookPhotoDto)));
			if (dto.getCode() == DataTransferObject.ERROR) {
				dto.putValue("ok", YesOrNoEnum.NO.getCode());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}
			dto.putValue("ok", YesOrNoEnum.YES.getCode());
			dto.putValue("okMsg", "您已成功预约自如免费摄影服务，拍摄时间" + startTimeStr + "，请按照预约须知提前做好准备！");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
	}

	/**
	 * 设置房源今夜特价
	 *
	 * @param request
	 * @return
	 * @author bushujie
	 * @created 2017年5月11日 下午2:35:44
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/setHouseTodayDiscount")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> setHouseTodayDiscount(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(LOGGER, "参数：" + paramJson);
		ToNightDiscountDto nightDiscountDto = JsonEntityTransform.json2Object(paramJson, ToNightDiscountDto.class);
		TonightDiscountEntity tonightDiscountEntity = new TonightDiscountEntity();
		BeanUtils.copyProperties(nightDiscountDto, tonightDiscountEntity);
		if (!Check.NuNStr(nightDiscountDto.getEndTimeStr())) {
			try {
				tonightDiscountEntity.setEndTime(DateUtil.parseDate(nightDiscountDto.getEndTimeStr(), "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				LogUtil.error(LOGGER, "error:{}", e);
				e.printStackTrace();
			}
		}
		String resultJson = houseTonightDiscountService.setHouseTodayDiscount(JsonEntityTransform.Object2Json(tonightDiscountEntity));
		dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
	}

	/**
	 * 今夜特价提示信息接口
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException
	 * @author bushujie
	 * @created 2017年5月11日 下午4:06:01
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/houseTodayDiscountHint")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> houseTodayDiscountHint(HttpServletRequest request) throws SOAParseException {
		DataTransferObject dto = new DataTransferObject();
		//查询第一个提醒
		String hintJson = staticResourceService.findStaticResListByResCode("TODAY_DISCOUNT_FIRST_HINT");
		List<StaticResourceVo> resList = SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
		if (!Check.NuNCollection(resList)) {
			dto.putValue("guideHint", PicUtil.getSpecialPic(pic_base_addr_mona, resList.get(resList.size() - 1).getPicUrl(), pic_size, resList.get(resList.size() - 1).getPicSuffix()));
		}
		//查询第二个提醒
		hintJson = staticResourceService.findStaticResListByResCode("TODAY_DISCOUNT_TWO_HINT");
		resList = SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
		if (!Check.NuNCollection(resList)) {
			dto.putValue("setHint", PicUtil.getSpecialPic(pic_base_addr_mona, resList.get(resList.size() - 1).getPicUrl(), pic_size, resList.get(resList.size() - 1).getPicSuffix()));
		}
		//查询第三个提醒
		hintJson = staticResourceService.findStaticResListByResCode("TODAY_DISCOUNT_THREE_HINT");
		resList = SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
		if (!Check.NuNCollection(resList)) {
			dto.putValue("saveHint", resList.get(resList.size() - 1).getResContent());
		}
		//十一活动地址
		hintJson = staticResourceService.findStaticResListByResCode("HOUSE_ACT_INTRODUC_20171001");
		resList = SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
		if (!Check.NuNCollection(resList)) {
			dto.putValue("actIntroduc", resList.get(resList.size() - 1).getResContent());
		}
		//十一活动图片
		hintJson = staticResourceService.findStaticResListByResCode("HOUSE_ACT_INTRODUC_PIC_20171001");
		resList = SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
		if (!Check.NuNCollection(resList)) {
			dto.putValue("actIntroducPic", PicUtil.getSpecialPic(pic_base_addr_mona, resList.get(resList.size() - 1).getPicUrl(), pic_size, resList.get(resList.size() - 1).getPicSuffix()));
		}
		return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
	}

	/**
	 * 查询列表单个房源
	 *
	 * @param request
	 * @return
	 * @author bushujie
	 * @created 2017年6月19日 下午7:09:25
	 */
	@RequestMapping("/${LOGIN_AUTH}/getHouseRoom")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> getHouseRoom(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "getHouseRoom参数：" + paramJson);
			HouseBaseListDto paramDto = JsonEntityTransform.json2Object(paramJson, HouseBaseListDto.class);
			paramDto.setPage(1);
			paramDto.setLimit(1);
			paramDto.setLandlordUid(getUserId(request));
			HouseRoomVo vo = null;
			String resultJson = houseManageService.searchLandlordHouseList(JsonEntityTransform.Object2Json(paramDto));
			LogUtil.debug(LOGGER, "房源查询返回结果：" + resultJson);
			List<HouseRoomVo> houseList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomVo.class);
			if (!Check.NuNCollection(houseList)) {
				vo = houseList.get(0);
			}
			//判断房源是否删除
			if(Check.NuNObj(vo)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源已被删除"), HttpStatus.OK);
			}
			//查询房源是否共享客厅
			String isHallJson=houseIssueService.isHallByHouseBaseFid(vo.getHouseBaseFid());
			Integer isHall=SOAResParseUtil.getIntFromDataByKey(isHallJson, "isHall");
			vo.setRoomType(isHall);
			//步骤
			Integer seq = vo.getOperateSeq();
			if ((RentWayEnum.ROOM.getCode() == paramDto.getRentWay() && vo.getIsIssue() == YesOrNoEnum.YES.getCode())||isHall==1) {
				String roomJson = houseManageService.searchHouseRoomList(JsonEntityTransform.Object2Json(paramDto));
				LogUtil.debug(LOGGER, "查询房间列表返回结果：" + roomJson);
				List<HouseRoomVo> roomList = SOAResParseUtil.getListValueFromDataByKey(roomJson, "list", HouseRoomVo.class);
				if (!Check.NuNCollection(roomList)) {
					vo = roomList.get(0);
					vo.setOperateSeq(seq);
					//默认不可以预约，如果品质审核未通过，且是照片原因，则置为可预约
					vo.setWhetherBookPhoto(0);
					if (!Check.NuNObj(vo.getRoomStatus()) && vo.getRoomStatus() == HouseStatusEnum.ZPSHWTG.getCode()) {
						HouseBizMsgEntity houseBizMsg = this.getHouseBizMsg(vo.getHouseRoomFid(), vo.getRentWay());
						if (!Check.NuNObj(houseBizMsg)) {
//							StringBuilder sb = new StringBuilder();
//							if (!Check.NuNStrStrict(houseBizMsg.getRefuseReason())) {
//								String refuseReason = HouseAuditCauseEnum.getNameStr(houseBizMsg.getRefuseReason());
//								sb.append(refuseReason);
//							}
//							if (!Check.NuNStrStrict(sb.toString())) {
//								sb.append(":");
//							}
//							if (!Check.NuNStrStrict(houseBizMsg.getRefuseMark())) {
//								sb.append(houseBizMsg.getRefuseMark());
//							}
							if(!Check.NuNStrStrict(houseBizMsg.getRefuseMark())){
								vo.setRefuseReason(houseBizMsg.getRefuseMark());
							}
						}
						//是否可以预约摄影
						Map<String, Object> map = whetherBookPhoto(paramDto.getLandlordUid(), vo.getHouseBaseFid(), vo.getHouseRoomFid());
						Integer i = (Integer) map.get("whether");
						if (i > 0) {
							//可以预约
							vo.setWhetherBookPhoto(i);
						} else {
							//不可以预约
							vo.setWhetherBookPhoto(i);
						}
						String bookTime = (String) map.get("bookTime");
						if (!Check.NuNObj(map.get("bookTime"))) {
							bookTime = bookTime.substring(5, bookTime.length() - 3);
							vo.setBookStartTime(bookTime);
						}
						if (!Check.NuNObj(map.get("photoStatus"))) {
							vo.setPhotoStatus((Integer) map.get("photoStatus"));
						}
						if (!Check.NuNObj(map.get("photoStatName"))) {
							vo.setPhotoStatName((String) map.get("photoStatName"));
						}
					}
					//填充房源预定类型
                    if(!Check.NuNObj(vo.getOrderType())){
                    	OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(vo.getOrderType());
                    	if(!Check.NuNObj(orderTypeEnum) && !Check.NuNStr(orderTypeEnum.getName())){
                    		vo.setOrderTypeStr(orderTypeEnum.getName());
                    	}
                    }
				}
			}

			//完善vo信息
			if (!Check.NuNStr(vo.getZoCode())) {
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

			if (vo.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
				//查询正在审核字段map
				Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(paramDto.getHouseBaseFid(), null, RentWayEnum.HOUSE.getCode(), 0);
				if (Check.NuNStrStrict(vo.getName())) {
					vo.setName("待完善房源");
				}
				//替换房源名称和默认图片
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath())){
					vo.setName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath()).getNewValue());
				}
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath())){
	            	String defaultPicJson=houseIssueService.searchHousePicMsgByFid(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath()).getNewValue());
	            	HousePicMsgEntity housePicMsgEntity=SOAResParseUtil.getValueFromDataByKey(defaultPicJson, "obj", HousePicMsgEntity.class);
	            	if(!Check.NuNObj(housePicMsgEntity)){
	            		vo.setDefaultPic(housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix());
	            	}
				}
				if (!Check.NuNStr(vo.getDefaultPic())) {
					vo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, vo.getDefaultPic(), pic_size));
				}
				//默认不可以预约，如果品质审核未通过，且是照片原因，则置为可预约
				vo.setWhetherBookPhoto(0);
				if (!Check.NuNObj(vo.getHouseStatus()) && vo.getHouseStatus().intValue() == HouseStatusEnum.ZPSHWTG.getCode()) {
					HouseBizMsgEntity houseBizMsg = this.getHouseBizMsg(vo.getHouseBaseFid(), vo.getRentWay());
					if (!Check.NuNObj(houseBizMsg)) {
//						StringBuilder sb = new StringBuilder();
//						if (!Check.NuNStrStrict(houseBizMsg.getRefuseReason())) {
//							String refuseReason = HouseAuditCauseEnum.getNameStr(houseBizMsg.getRefuseReason());
//							sb.append(refuseReason);
//						}
//						if (!Check.NuNStrStrict(sb.toString())) {
//							sb.append(":");
//						}
//						if (!Check.NuNStrStrict(houseBizMsg.getRefuseMark())) {
//							sb.append(houseBizMsg.getRefuseMark());
//						}
						if(!Check.NuNStrStrict(houseBizMsg.getRefuseMark())){
							vo.setRefuseReason(houseBizMsg.getRefuseMark());
						}
					}
					//是否可以预约摄影
					Map<String, Object> map = whetherBookPhoto(paramDto.getLandlordUid(), vo.getHouseBaseFid(), null);
					Integer i = (Integer) map.get("whether");
					if (i > 0) {
						//可以预约
						vo.setWhetherBookPhoto(i);
					} else {
						//不可以预约
						vo.setWhetherBookPhoto(i);
					}
					String bookTime = (String) map.get("bookTime");
					if (!Check.NuNObj(map.get("bookTime"))) {
						bookTime = bookTime.substring(5, bookTime.length() - 3);
						vo.setBookStartTime(bookTime);
					}
					if (!Check.NuNObj(map.get("photoStatus"))) {
						vo.setPhotoStatus((Integer) map.get("photoStatus"));
					}
					if (!Check.NuNObj(map.get("photoStatName"))) {
						vo.setPhotoStatName((String) map.get("photoStatName"));
					}
				}
				//填充房源预定类型
                if(!Check.NuNObj(vo.getOrderType())){
                	OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(vo.getOrderType());
                	if(!Check.NuNObj(orderTypeEnum) && !Check.NuNStr(orderTypeEnum.getName())){
                		vo.setOrderTypeStr(orderTypeEnum.getName());
                	}
                }
			} else if (vo.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
				//查询待审核字段map
				Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(paramDto.getHouseBaseFid(), paramDto.getHouseRoomFid(), RentWayEnum.ROOM.getCode(), 0);
				//替换房源名称和默认图片
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath())){
					vo.setName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath()).getNewValue());
				}
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath())){
	            	String defaultPicJson=houseIssueService.searchHousePicMsgByFid(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath()).getNewValue());
	            	HousePicMsgEntity housePicMsgEntity=SOAResParseUtil.getValueFromDataByKey(defaultPicJson, "obj", HousePicMsgEntity.class);
	            	if(!Check.NuNObj(housePicMsgEntity)){
	            		vo.setDefaultPic(housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix());
	            	}
				}
				HouseBaseListDto dto = new HouseBaseListDto();
				dto.setHouseBaseFid(vo.getHouseBaseFid());
				dto.setLandlordUid(paramDto.getLandlordUid());
				// 查询房源包含房间列表
				String roomJson = houseManageService.searchHouseRoomList(JsonEntityTransform.Object2Json(dto));
				List<HouseRoomVo> roomList = SOAResParseUtil.getListValueFromDataByKey(roomJson, "list", HouseRoomVo.class);
				if (vo.getIsIssue() == YesOrNoEnum.NO.getCode()) {
					//合租按房源显示，需要找到房源名称
					if (!Check.NuNCollection(roomList)) {
						String houseName = null;
						//不是共享客厅才拼写名字
						if(isHall==0){
							for (HouseRoomVo houseRoomVo : roomList) {
								if (!Check.NuNStr(houseRoomVo.getRoomName())) {
									houseName = houseRoomVo.getRoomName() + "等";
									break;
								}
							}
						}
						if (Check.NuNStr(houseName)) {
							vo.setName("待完善房源");
						} else {
							vo.setName(houseName);
						}
					} else {
						vo.setName("待完善房源");
					}
				}
				if (Check.NuNObj(vo.getRoomStatus())) {
					vo.setHouseStatus(HouseStatusEnum.DFB.getCode());
					vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getHouseStatus()).getShowStatusName());
					vo.setStatus(HouseStatusEnum.DFB.getCode());
				} else {
					vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getRoomStatus()).getShowStatusName());
					vo.setStatus(vo.getRoomStatus());
				}
				if (!Check.NuNStr(vo.getDefaultPic())) {
					vo.setDefaultPic(PicUtil.getSpecialPic(pic_base_addr_mona, vo.getDefaultPic(), pic_size));
				}
				//填充房源预定类型
                if(!Check.NuNObj(vo.getOrderType())){
                	OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(vo.getOrderType());
                	if(!Check.NuNObj(orderTypeEnum) && !Check.NuNStr(orderTypeEnum.getName())){
                		vo.setOrderTypeStr(orderTypeEnum.getName());
                	}
                }
			}
			//30天出租率
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startTime = DateUtil.parseDate(sdf.format(new Date()), "yyyy-MM-dd");
			Date endTime = DateUtils.addDays(startTime, 30);
			LockHouseCountRequest paramRequest = new LockHouseCountRequest();
			paramRequest.setRentWay(vo.getRentWay());
			paramRequest.setHouseFid(vo.getHouseBaseFid());
			paramRequest.setRoomFid(vo.getHouseRoomFid());
			paramRequest.setStartTime(startTime);
			paramRequest.setEndTime(endTime);
			paramRequest.setLockType(LockTypeEnum.ORDER.getCode());

			Double houseBookRate = 0.0d;
			int orderLockCount = 0;
			int lanLockCount = 0;

			//排除房间没有的房源
			if (!Check.NuNObj(paramRequest.getRentWay())
					&& (paramRequest.getRentWay().intValue() != RentWayEnum.ROOM.getCode()
					|| !Check.NuNStr(paramRequest.getRoomFid()))) {
				DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(
						orderUserService.getBookDaysByFid(JsonEntityTransform.Object2Json(paramRequest)));
				if (orderDto.getCode() == DataTransferObject.SUCCESS) {
					orderLockCount = (int) orderDto.getData().get("count");
				}
				paramRequest.setLockType(LockTypeEnum.LANDLADY.getCode());
				DataTransferObject lanDto = JsonEntityTransform.json2DataTransferObject(
						orderUserService.getBookDaysByFid(JsonEntityTransform.Object2Json(paramRequest)));

				if (lanDto.getCode() == DataTransferObject.SUCCESS) {
					lanLockCount = (int) lanDto.getData().get("count");
				}

				if (lanLockCount >= 30) {
					houseBookRate = 0.0;
				} else {
					houseBookRate = BigDecimalUtil.div(orderLockCount * 100, 30.0 - lanLockCount, 1);
				}

			}

			vo.setHouseBookRate(houseBookRate);

			//查询评论数
			StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
			statsHouseEvaRequest.setHouseFid(vo.getHouseBaseFid());
			statsHouseEvaRequest.setRoomFid(vo.getHouseRoomFid());
			statsHouseEvaRequest.setRentWay(vo.getRentWay());
			LogUtil.debug(LOGGER, "查询评论数参数：" + JsonEntityTransform.Object2Json(statsHouseEvaRequest));
			String evaluateCountJson = evaluateOrderService
					.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));

			List<StatsHouseEvaEntity> evaluateStats = SOAResParseUtil
					.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
			if (!Check.NuNCollection(evaluateStats)) {
				LogUtil.debug(LOGGER, "查询评论数结果：" + JsonEntityTransform.Object2Json(evaluateStats));
				vo.setHouseEvaScore("无评分");
				StatsHouseEvaEntity statsHouseEvaEntity = evaluateStats.get(0);
				if (statsHouseEvaEntity.getEvaTotal() > 0) {
					double evalScore = BigDecimalUtil.div(statsHouseEvaEntity.getHouseCleanAva()
							+ statsHouseEvaEntity.getDesMatchAva() + statsHouseEvaEntity.getSafeDegreeAva()
							+ statsHouseEvaEntity.getTrafPosAva() + statsHouseEvaEntity.getCostPerforAva(), 5.0, 1);
					vo.setHouseEvaScore(String.valueOf(evalScore));
				}
			}
			//今夜特价逻辑加入
			//LogUtil.info(LOGGER, "vo数据那个是空" + JsonEntityTransform.Object2Json(vo));
			if ((!Check.NuNObj(vo.getHouseStatus()) && vo.getHouseStatus() == HouseStatusEnum.SJ.getCode()) || (!Check.NuNObj(vo.getRoomStatus()) && vo.getRoomStatus() == HouseStatusEnum.SJ.getCode())) {
				TonightDiscountEntity tonightDiscountEntity = new TonightDiscountEntity();
				tonightDiscountEntity.setHouseFid(vo.getHouseBaseFid());
				tonightDiscountEntity.setRoomFid(vo.getHouseRoomFid());
				tonightDiscountEntity.setRentWay(vo.getRentWay());
				String todayDiscountJson = houseTonightDiscountService.findTonightDiscountByRentway(JsonEntityTransform.Object2Json(tonightDiscountEntity));
				TonightDiscountEntity discountEntity = SOAResParseUtil.getValueFromDataByKey(todayDiscountJson, "data", TonightDiscountEntity.class);
				if (Check.NuNObj(discountEntity)) {
					vo.setIsTodayDiscount(1);
				} else {
					vo.setIsTodayDiscount(2);
					String endTimeStr = DateUtil.dateFormat(discountEntity.getEndTime(), "HH:mm");
					if ("23:59".equals(endTimeStr)) {
						endTimeStr = "24:00";
					}
					vo.setTodayDiscount(endTimeStr + "结束" + BigDecimalUtil.mul(discountEntity.getDiscount(), 10) + "折优惠");
				}
				//判断今天是否已经有订单
				String isOrderJson = houseCommonService.isHousePayLockCurrentDay(JsonEntityTransform.Object2Json(tonightDiscountEntity));
				LogUtil.info(LOGGER, "今天是否已经有订单的判断：{}", isOrderJson);
				boolean isOrder = SOAResParseUtil.getBooleanFromDataByKey(isOrderJson, "data");
				LogUtil.info(LOGGER, "今天是否已经有订单的判断：{}", isOrder);
				if (isOrder) {
					vo.setIsTodayDiscount(0);
				}
			} else {
				vo.setIsTodayDiscount(0);
			}
			LogUtil.info(LOGGER, "查询单个房源结果：{}", JsonEntityTransform.Object2Json(vo));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(vo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
		}
	}

	/**
	 * 下架房源
	 *
	 * @param
	 * @return
	 * @author wangwt
	 * @created 2017年06月23日 10:32:28
	 */
	@RequestMapping(value = "${LOGIN_AUTH}/upDownHouse", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> upDownHouse(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[upDownHouse] param : {}", paramJson);
			ValidateResult<HouseLandlordParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseLandlordParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseLandlordParamsDto requestDto = validateResult.getResultObj();

			if (requestDto.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(requestDto.getRoomFid())) {
				LogUtil.info(LOGGER, "[upDownHouse] roomFid is null");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间fid不能为空"), HttpStatus.OK);
			}
			String resultJson = null;
			if (requestDto.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				// 整租 houseFid为房源逻辑id
				resultJson = houseManageService.upDownHouse(requestDto.getHouseBaseFid(), requestDto.getLandlordUid());
			} else if (requestDto.getRentWay() == RentWayEnum.ROOM.getCode()) {
				// 合租 houseFid为房间逻辑id
				resultJson = houseManageService.upDownHouseRoom(requestDto.getRoomFid(), requestDto.getLandlordUid());
			} else {
				// 出租方式错误 目前不支持床位出租类型
				LogUtil.info(LOGGER, "[upDownHouse] unsupport rentway");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("不支持的出租类型"), HttpStatus.OK);
			}
			LogUtil.info(LOGGER, "[upDownHouse] return ：{}", resultJson);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "[upDownHouse] error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 取消发布房源
	 *
	 * @param
	 * @return
	 * @author wangwt
	 * @created 2017年06月23日 14:11:07
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/cancleHouse", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> cancleHouse(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[cancleHouse] param : {}", paramJson);
			ValidateResult<HouseLandlordParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseLandlordParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseLandlordParamsDto requestDto = validateResult.getResultObj();
			if (requestDto.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(requestDto.getRoomFid())) {
				LogUtil.info(LOGGER, "[cancleHouse] roomFid is null");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("租住类型为分租，房间fid不能为空"), HttpStatus.OK);
			}
			HouseBaseDto houseBaseDto = new HouseBaseDto();
			houseBaseDto.setHouseFid(requestDto.getHouseBaseFid());
			houseBaseDto.setRoomFid(requestDto.getRoomFid());
			houseBaseDto.setRentWay(requestDto.getRentWay());
			houseBaseDto.setLandlordUid(requestDto.getLandlordUid());
			String param = JsonEntityTransform.Object2Json(houseBaseDto);
			LogUtil.info(LOGGER, "[cancleHouse] cancleHouseOrRoomByFid param :{}", param);
			String resultJson = houseIssueService.cancleHouseOrRoomByFid(param);
			LogUtil.info(LOGGER, "[cancleHouse] return ：{}", resultJson);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() != DataTransferObject.SUCCESS) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(jsonObject), HttpStatus.OK);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "[cancleHouse] error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 修改信息页面查询接口
	 *
	 * @param
	 * @return
	 * @author wangwt
	 * @created 2017年06月23日 17:06:22
	 */
	@RequestMapping("/${LOGIN_AUTH}/getHouseBase")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> getHouseBase(HttpServletRequest request) {
		try {
			Header header = getHeader(request);
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[getHouseBase] param : {}", paramJson);
			ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();

			if (requestDto.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(requestDto.getRoomFid())) {
				LogUtil.info(LOGGER, "[getHouseBase] roomFid is null");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间fid不能为空"), HttpStatus.OK);
			}

			//房源状态 编号
			Integer houseStatus = 0;
			String houseSn = "", houseStatusName = "";
			String houseJson = houseIssueService.searchHouseBaseMsgByFid(requestDto.getHouseBaseFid());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if (dto.getCode() != DataTransferObject.SUCCESS) {
				LogUtil.info(LOGGER, "[getHouseBase] searchHouseBaseMsgByFid error, resultJson:{}", houseJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("获取房间信息失败"), HttpStatus.OK);
			}
			HouseBaseMsgEntity houseBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseMsgEntity.class);

			Integer hasLock = houseBaseMsgEntity.getIsLock();
			
			//找到待审核的默认图片
			String defaultPicFid=null;
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay(), 0);

			if (RentWayEnum.HOUSE.getCode() == requestDto.getRentWay()) {
				houseStatus = houseBaseMsgEntity.getHouseStatus();
				houseSn = houseBaseMsgEntity.getHouseSn();
				houseStatusName = HouseStatusEnum.getHouseStatusByCode(houseBaseMsgEntity.getHouseStatus()).getShowStatusName();
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
	            	defaultPicFid=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath()).getNewValue();
				}
			}

			if (RentWayEnum.ROOM.getCode() == requestDto.getRentWay()) {
				String roomJson = houseIssueService.searchHouseRoomMsgByFid(requestDto.getRoomFid());
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(roomJson);
				if (resultDto.getCode() != DataTransferObject.SUCCESS) {
					LogUtil.info(LOGGER, "[getHouseBase] searchHouseRoomMsgByFid error, resultJson:{}", roomJson);
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("获取房间信息失败"), HttpStatus.OK);
				}
				HouseRoomMsgEntity houseRoomMsgEntity = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				houseStatus = houseRoomMsgEntity.getRoomStatus();
				houseSn = houseRoomMsgEntity.getRoomSn();
				houseStatusName = HouseStatusEnum.getHouseStatusByCode(houseRoomMsgEntity.getRoomStatus()).getShowStatusName();
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
	            	defaultPicFid=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath()).getNewValue();
				}
			}
			String defaultPicUrl = null;
			try {
				//房源默认图片
				String fid = requestDto.getHouseBaseFid();
				if (RentWayEnum.ROOM.getCode() == requestDto.getRentWay()) {
					fid = requestDto.getRoomFid();
				}
				String picJson = houseIssueService.findDefaultPic(fid, requestDto.getRentWay());
				String defaultPic = SOAResParseUtil.getStrFromDataByKey(picJson, "picBaseUrl");
				if(!Check.NuNStr(defaultPicFid)){
	            	String defaultPicJson=houseIssueService.searchHousePicMsgByFid(defaultPicFid);
	            	HousePicMsgEntity housePicMsgEntity=SOAResParseUtil.getValueFromDataByKey(defaultPicJson, "obj", HousePicMsgEntity.class);
	            	if(!Check.NuNObj(housePicMsgEntity)){
	            		defaultPic=housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix();
	            	}
				}
				defaultPicUrl = PicUtil.getSpecialPic(pic_base_addr_mona, defaultPic, detail_big_pic);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "[getHouseBase] defaultPicUrl error:{}", e);
			}


			String buttonStr = "";
			String buttonColor = "0";
			Map<String, Object> resultMap = new HashMap<>();

			List<FieldTitleTextValueVo> titleList = new ArrayList<>();

			if (houseStatus == HouseStatusEnum.YFB.getCode() || houseStatus == HouseStatusEnum.XXSHTG.getCode()) {
				//审核中 都不允许修改
				buttonStr = HouseButtonEnum.QXFB.getName();
				setTitleTip(titleList, Boolean.FALSE, houseSn, hasLock, null, false);
			} else if (houseStatus == HouseStatusEnum.DFB.getCode() || houseStatus == HouseStatusEnum.XXSHWTG.getCode()
					|| houseStatus == HouseStatusEnum.ZPSHWTG.getCode() || houseStatus == HouseStatusEnum.XJ.getCode()
					|| houseStatus == HouseStatusEnum.QZXJ.getCode()){
				buttonStr = HouseButtonEnum.FBFY.getName();
				buttonColor = "1";
				setTitleTip(titleList, Boolean.TRUE, houseSn, hasLock, null, false);
			} else if (houseStatus == HouseStatusEnum.SJ.getCode()) {
				buttonStr = HouseButtonEnum.XJFY.getName();
				String url = MINSU_M_SMARTLOCK;
				if ("1".equals(header.getOsType())) {
					url += "?houseBaseFid=" + houseBaseMsgEntity.getFid();
				}
				//上架状态才有智能锁
				setTitleTip(titleList, Boolean.TRUE, houseSn, hasLock, url, true);
			}

			String labelColor = "000000";
			if (houseStatus == HouseStatusEnum.XXSHWTG.getCode() || houseStatus == HouseStatusEnum.ZPSHWTG.getCode()) {
				labelColor = "FF6262";
			}

			/****************校验是否已经设置默认照片**************/
			HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
			houseBaseParamsDto.setHouseBaseFid(requestDto.getHouseBaseFid());
			houseBaseParamsDto.setRentWay(requestDto.getRentWay());
			houseBaseParamsDto.setRoomFid(requestDto.getRoomFid());
			String isSetDefaultPicJson = houseIssueService.isSetDefaultPic(JsonEntityTransform.Object2Json(houseBaseParamsDto));
			DataTransferObject isSetDefaultPicDto = JsonEntityTransform.json2DataTransferObject(isSetDefaultPicJson);
			if (isSetDefaultPicDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "getHouseBase(),获取房源或房间是否设置了封面照片失败,msg:{}",isSetDefaultPicDto.getMsg());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
			}
			Boolean hasDefault = SOAResParseUtil.getBooleanFromDataByKey(isSetDefaultPicJson,"hasDefault");
			if (hasDefault) {
				resultMap.put("defaultPic", defaultPicUrl);
			} else {
				resultMap.put("defaultPic", null);
			}
			/****************校验是否已经设置默认照片**************/

			resultMap.put("houseStatus", houseStatus);
			resultMap.put("houseStatusName", houseStatusName);
			resultMap.put("labelColor", labelColor);
			resultMap.put("houseSn", houseSn);
			resultMap.put("buttonStr", buttonStr);
			resultMap.put("buttonColor", buttonColor);
			resultMap.put("titleList", titleList);
			LogUtil.info(LOGGER, "[getHouseBase] return {}", JsonEntityTransform.Object2Json(resultMap));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap), HttpStatus.OK);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "[getHouseBase] error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 修改房源页面列表：标题， 文案， 是否可修改
	 * @author wangwt
	 * @created 2017年06月28日 11:29:21
	 * @param
	 * @return
	 */
	private void setTitleTip(List<FieldTitleTextValueVo> list, boolean isEdit, String houseSn, int isLock, String lockUrl, boolean isSJ) {
		for (HouseModifyTypeEnum houseModifyTypeEnum : HouseModifyTypeEnum.values()) {
			if (houseModifyTypeEnum == HouseModifyTypeEnum.SMART_LOCK) {
				if (isLock == 1 && !Check.NuNStr(lockUrl)) {
					FieldTitleTextValueVo titleTextValueVo = new FieldTitleTextValueVo(houseModifyTypeEnum.getTitle(), lockUrl, houseModifyTypeEnum.getModifyTip(), Boolean.TRUE);
					list.add(titleTextValueVo);
				}
			} else if (houseModifyTypeEnum == HouseModifyTypeEnum.HOUSE_SN) {
				FieldTitleTextValueVo titleTextValueVo = new FieldTitleTextValueVo(houseModifyTypeEnum.getTitle(), houseSn, Boolean.FALSE);
				list.add(titleTextValueVo);
			} else if(isSJ && houseModifyTypeEnum == HouseModifyTypeEnum.TYPELOCATION){
				FieldTitleTextValueVo titleTextValueVo = new FieldTitleTextValueVo(houseModifyTypeEnum.getTitle(), "查看房源类型、出租类型、位置信息", isEdit);
				list.add(titleTextValueVo);
			}else {
				String msg = houseModifyTypeEnum.getModifyTip();
				if (isEdit == Boolean.FALSE) {
					msg = houseModifyTypeEnum.getTip();
				}
				FieldTitleTextValueVo titleTextValueVo = new FieldTitleTextValueVo(houseModifyTypeEnum.getTitle(), msg, isEdit);
				list.add(titleTextValueVo);
			}
		}
	}

	/**
	 * 删除房源接口
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年06月28日 10:01:19
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/deleteHouse", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> deleteHouse(HttpServletRequest request) {
		try {
			String uid = getUserId(request);
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "【deleteHouse】参数={}", paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()), HttpStatus.OK);
			}
			HouseBaseParamsDto resultObj = validateResult.getResultObj();
			HouseBaseDto houseBaseDto = new HouseBaseDto();
			houseBaseDto.setHouseFid(resultObj.getHouseBaseFid());
			houseBaseDto.setRoomFid(resultObj.getRoomFid());
			houseBaseDto.setRentWay(resultObj.getRentWay());
			houseBaseDto.setLandlordUid(uid);
			String resultJson = houseIssueService.deleteHouseOrRoomByFid(JsonEntityTransform.Object2Json(houseBaseDto));
			LogUtil.info(LOGGER, "【deleteHouse】result={}", resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【deleteHouse】 e:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}


	/**
	 * 修改房源时， 价格回显接口（整租/分租）
	 *
	 * @param
	 * @return
	 * @author wangwt
	 * @created 2017年06月29日 14:32:30
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/initSetPrice")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initSetPrice(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initSetPrice]参数={}", paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()), HttpStatus.OK);
			}
			HouseBaseParamsDto resultObj = validateResult.getResultObj();
			String houseBaseFid = resultObj.getHouseBaseFid();
			String roomFid = resultObj.getRoomFid();
			Integer rentWay = resultObj.getRentWay();
			//要审核的字段map
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(houseBaseFid, roomFid, rentWay, 0);
			
			Map<String, Object> priceMap = new HashMap<>();
			if (rentWay == RentWayEnum.HOUSE.getCode()) {
				String resultJson = houseIssueAppService.searchHouseConfAndPrice(houseBaseFid);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if (dto.getCode() != DataTransferObject.SUCCESS) {
					LogUtil.error(LOGGER, "[initSetPrice] searchHouseConfAndPrice error :{}", resultJson);
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("查询房源信息失败"), HttpStatus.OK);
				}
				HouseBaseExtDto houseBaseExtDto = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseBaseExtDto", HouseBaseExtDto.class);
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_house_Cleaning_Fees.getFieldPath())){
					houseBaseExtDto.setHouseCleaningFees(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_house_Cleaning_Fees.getFieldPath()).getNewValue()));
				}
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Lease_Price.getFieldPath())){
					houseBaseExtDto.setLeasePrice(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Lease_Price.getFieldPath()).getNewValue()));
				}
				houseService.initSetPrice(priceMap, houseBaseExtDto.getLeasePrice(), houseBaseExtDto.getHouseCleaningFees(), houseBaseExtDto.getFid(), null, houseBaseExtDto.getRentWay(), null);
			}
			if (rentWay == RentWayEnum.ROOM.getCode()) {
				if (Check.NuNStr(roomFid)) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间fid不能为空"), HttpStatus.OK);
				}
				String roomJson = houseIssueService.searchHouseRoomMsgByFid(roomFid);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(roomJson);
				if (dto.getCode() != DataTransferObject.SUCCESS) {
					LogUtil.error(LOGGER, "[initSetPrice] searchHouseRoomMsgByFid error :{}", roomJson);
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("查询房间信息失败"), HttpStatus.OK);
				}
				HouseRoomMsgEntity houseRoomMsg = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				if (Check.NuNObj(houseRoomMsg)) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间不存在"), HttpStatus.OK);
				}
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Cleaning_Fees.getFieldPath())){
					houseRoomMsg.setRoomCleaningFees(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Cleaning_Fees.getFieldPath()).getNewValue()));
				}
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Price.getFieldPath())){
					houseRoomMsg.setRoomPrice(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Price.getFieldPath()).getNewValue()));
				}
				houseService.initSetPrice(priceMap, houseRoomMsg.getRoomPrice(), houseRoomMsg.getRoomCleaningFees(), houseBaseFid, roomFid, rentWay, null);
			}

			LogUtil.info(LOGGER, "[initSetPrice]result={}", JsonEntityTransform.Object2Json(priceMap));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(priceMap), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "[initSetPrice] e:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 修改房源时， 价格保存接口（整租/分租）
	 *
	 * @param
	 * @return
	 * @author wangwt
	 * @created 2017年06月29日 17:34:46
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/savePrice")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveHouseOrRoomPriceForModify(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initSetPrice] [saveHouseOrRoomPriceForModify]参数：" + paramJson);

			if (Check.NuNObj(paramJson)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数不能为空"), HttpStatus.OK);
			}
			/**判断参数类型是否正确**/
			if(BaseMethodUtil.isClassByJsonKey(paramJson, "leasePrice", String.class)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("基础价格不合法"), HttpStatus.OK);
			}
			if(BaseMethodUtil.isContainKey(paramJson, "cleaningFees")&&BaseMethodUtil.isClassByJsonKey(paramJson, "cleaningFees", String.class)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务费不合法"), HttpStatus.OK);
			}
			if(BaseMethodUtil.isContainKey(paramJson, "weekendPriceVal")&&BaseMethodUtil.isClassByJsonKey(paramJson, "weekendPriceVal", String.class)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("周末价格不合法"), HttpStatus.OK);
			}
			/**判断参数类型是否正确**/
			HousePriceDto housePriceDto = JsonEntityTransform.json2Object(paramJson, HousePriceDto.class);
			if (Check.NuNStr(housePriceDto.getHouseBaseFid())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源fid不能为空"), HttpStatus.OK);
			}
			if (Check.NuNObj(housePriceDto.getRentWay())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租类型不能为空"), HttpStatus.OK);
			}

			Integer fullDayRateSwitch = housePriceDto.getFullDayRateSwitch();
			//长租折扣开关打开，校验折扣格式
			if (!Check.NuNObj(fullDayRateSwitch) && fullDayRateSwitch == YesOrNoEnum.YES.getCode()) {
				Double thirtyDiscountRate = housePriceDto.getThirtyDiscountRate();
				Double sevenDiscountRate = housePriceDto.getSevenDiscountRate();
				if (Check.NuNObjs(thirtyDiscountRate, sevenDiscountRate)) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("入住满30天折扣, 入住满7天折扣不能同时为空"), HttpStatus.OK);
				}
				if (!Check.NuNObj(thirtyDiscountRate) && !thirtyDiscountRate.toString().matches("\\d{1}\\.\\d{1}")) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("入住满30天折扣格式不正确"), HttpStatus.OK);
				}
				if (!Check.NuNObj(sevenDiscountRate) && !sevenDiscountRate.toString().matches("\\d{1}\\.\\d{1}")) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("入住满7天折扣格式不正确"), HttpStatus.OK);
				}
			}

			//校验周末价格
			Integer weekendPriceSwitch = housePriceDto.getWeekendPriceSwitch();
			if (!Check.NuNObj(weekendPriceSwitch) && weekendPriceSwitch == YesOrNoEnum.YES.getCode()) {
				Double weekendPriceVal = housePriceDto.getWeekendPriceVal();
				if (Check.NuNObj(weekendPriceVal) ) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请填写基础价格"), HttpStatus.OK);
				}
				if (StringUtils.isNumeric(weekendPriceVal.toString())) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("周末价格格式只能为数字"), HttpStatus.OK);
				}
				//房源价格限制
				String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
				Integer priceLow = SOAResParseUtil.getIntFromDataByKey(priceLowJson, "textValue");

				String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
				Integer priceHigh = SOAResParseUtil.getIntFromDataByKey(priceHighJson, "textValue");

				if (!Check.NuNObj(priceLow) && weekendPriceVal.intValue() < priceLow.intValue()) {
					String msg = "每晚价格不能低于"+ priceLow.intValue() +"元";
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(msg), HttpStatus.OK);
				}

				if (!Check.NuNObj(priceHigh) && weekendPriceVal.intValue() > priceHigh.intValue()) {
					String msg = "每晚价格不能高于"+ priceHigh.intValue() +"元";
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(msg), HttpStatus.OK);
				}

				String weekendPriceType = housePriceDto.getWeekendPriceType();
				if (!WeekendPriceEnum.FRI_SAT.getValue().equals(weekendPriceType)
						&& !WeekendPriceEnum.SAT_SUN.getValue().equals(weekendPriceType)
						&& !WeekendPriceEnum.FRI_SAT_SUN.getValue().equals(weekendPriceType)) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("周末价格类型不正确"), HttpStatus.OK);
				}

			}

			//获取房东uid
			String uid = getUserId(request);
			housePriceDto.setCreateFid(uid);
			
			//保存修改记录 ---- 查询历史数据
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(housePriceDto.getHouseBaseFid(), housePriceDto.getRoomFid(), housePriceDto.getRentWay());
			
			String resultJson = houseIssueAppService.saveHouseOrRoomPriceForModify(JsonEntityTransform.Object2Json(housePriceDto));
			LogUtil.info(LOGGER, "[initSetPrice] saveHouseOrRoomPriceForModify return:{}", resultJson);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() != DataTransferObject.SUCCESS) {
				LogUtil.error(LOGGER, "[initSetPrice] saveHouseOrRoomPriceForModify error");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
			}
			
			//保存修改记录 ---- 保存
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateLogService.saveHistoryLog(housePriceDto, request,houseUpdateHistoryLogDto);
			}
			
			HouseBaseVo houseBaseVo = new HouseBaseVo();
			houseBaseVo.setHouseBaseFid(housePriceDto.getHouseBaseFid());
			houseBaseVo.setRoomFid(housePriceDto.getRoomFid());
			houseBaseVo.setRentWay(housePriceDto.getRentWay());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(houseBaseVo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "[initSetPrice] saveHouseOrRoomPriceForModify error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

}
