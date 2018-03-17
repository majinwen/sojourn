package com.ziroom.minsu.mapp.evaluate.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.mapp.common.util.DateUtils;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.evaluate.entity.EvaluateOrderShowVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.dto.HouseSnapshotRequest;
import com.ziroom.minsu.mapp.common.dto.LandlordEvaDetailDto;
import com.ziroom.minsu.mapp.common.dto.LanlordEvaRequest;
import com.ziroom.minsu.mapp.common.dto.TenantEvaRequest;
import com.ziroom.minsu.mapp.common.entity.EvaluateInfoVo;
import com.ziroom.minsu.mapp.common.entity.TenantEvaVos;
import com.ziroom.minsu.mapp.common.enumvalue.EvaTypeEnum;
import com.ziroom.minsu.mapp.common.logic.ParamCheckLogic;
import com.ziroom.minsu.mapp.common.logic.ValidateResult;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.LandlordEvaluateVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.OrderDetailRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.HouseSnapshotVo;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.evaluate.IsReleaseEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvaFlagEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;

/**
 * <p>房东评价相关接口API
 *   说明： 把房东 和 房客的评价分开写，主要为了 以后能分开考虑 
 * </p>
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
@RequestMapping("/landlordEva")
@Controller
public class LandlordEvaController {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordEvaController.class);

	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;


	@Resource(name = "order.orderUserService")
	private OrderUserService orderUserService;


	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;


	@Resource(name = "search.searchServiceApi")
	private SearchService searchService;


	@Resource(name = "mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name="mapp.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${list_small_pic}'.trim()}")
	private String list_small_pic;
	
	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;
	
	@Value("#{'${default_head_size}'.trim()}")
	private String default_head_size;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	
	@Resource(name = "basedata.smsTemplateService")
	private  SmsTemplateService smsTemplateService;
	
	@Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
	private String userDefaultUrl;
	
	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	

	
	/**
	 * 
	 * 到评价首页
	 *
	 * @author yd
	 * @created 2016年5月14日 上午10:56:21
	 *
	 * @param request
	 */
	@RequestMapping("${LOGIN_UNAUTH}/index")
	public String toEvaIndex(HttpServletRequest request){
		
		return "/evaluate/evaIndex";
	}
	/**
	 * 
	 * 分页查询房客的评价列表（包括已评价和未评价的）
	 * 
	 * 在未评价状态下：需要查出 当前的评价信息
	 * 条件：1.用户uid 2.评价状态 ：房客待评价  或 房客已评价  3.订单状态 必须为  正常退房完成 或 提前退房完成订单
	 *
	 * @author yd
	 * @created 2016年5月2日 下午3:36:05
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryEvaluate")
	@ResponseBody
	public  DataTransferObject queryEvaluate(HttpServletRequest request,HouseSnapshotRequest houseSnapshotApiRe) {
		
		
		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		houseSnapshotApiRe.setUid(customerVo.getUid());
		houseSnapshotApiRe.setLimit(5);
		//待返回实体
		List<TenantEvaVos> listTenantEvaVos = new ArrayList<TenantEvaVos>();
		PageResult pageResult = new PageResult();

		DataTransferObject dto = null;
		//查询参数
		OrderRequest orderRe = new OrderRequest();
		orderRe.setLandlordUid(houseSnapshotApiRe.getUid());
//		orderRe.setListEvaStatus(houseSnapshotApiRe.returnEvastatus());
//		orderRe.setListOrderStatus(houseSnapshotApiRe.returnOrderStatus());
		orderRe.setLimit(houseSnapshotApiRe.getLimit());
		orderRe.setPage(houseSnapshotApiRe.getPage());
		orderRe.setIsDel(IsDelEnum.NOT_DEL.getCode());
		orderRe.setEvaType(houseSnapshotApiRe.getEvaType());
		orderRe.setRequestType(UserTypeEnum.LANDLORD.getUserCode());
		orderRe.setLimitDay(getEvalTimeDay());

		String evaTips="";
		if(houseSnapshotApiRe.getEvaType().intValue() == EvaTypeEnum.WAITING_EVA.getCode()){
			evaTips = "待评价";
		}else{
			evaTips = "已评价";
		}
		dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.findHouseSnapshotByOrder(JsonEntityTransform.Object2Json(orderRe)));
		if(dto.getCode()==0){
			List<HouseSnapshotVo> lisHouseSnapshot = dto.parseData("listSnapshot", new TypeReference<List<HouseSnapshotVo>>() {
			});
			if(!Check.NuNCollection(lisHouseSnapshot)){
				for (HouseSnapshotVo orderHouseSnapshot : lisHouseSnapshot) {
					TenantEvaVos tenantEvaVos = new TenantEvaVos();
					BeanUtils.copyProperties(orderHouseSnapshot, tenantEvaVos);
					tenantEvaVos.setStartTimeStr(DateUtil.dateFormat(orderHouseSnapshot.getStartTime(), "yyyy-MM-dd"));
					tenantEvaVos.setEndTimeStr(DateUtil.dateFormat(orderHouseSnapshot.getEndTime(), "yyyy-MM-dd"));
					if(!Check.NuNStr(orderHouseSnapshot.getUserUid())){
						//查询房客头像
						String picUrl = getPicUrlByUid(orderHouseSnapshot.getUserUid());
						if(!Check.NuNStr(picUrl)){
							tenantEvaVos.setUserPicUrl(picUrl);
						}
					}
					int housingDay  = DateUtil.getDatebetweenOfDayNum(orderHouseSnapshot.getStartTime(), orderHouseSnapshot.getEndTime());
					tenantEvaVos.setHousingDay(housingDay);
					
					//添加房客评价的审核状态
					EvaluateRequest evaluateRequest  = new EvaluateRequest();
					evaluateRequest.setOrderSn(orderHouseSnapshot.getOrderSn());
					evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
					evaluateRequest.setEvaUserUid(orderHouseSnapshot.getUserUid());
					tenantEvaVos.setFid(tenantEvaVos.returnFid(orderHouseSnapshot.getHouseFid(), orderHouseSnapshot.getRoomFid(), orderHouseSnapshot.getBedFid()));
					
					dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.findOrderContactsByOrderSn(orderHouseSnapshot.getOrderSn()));
					if(dto.getCode() == 0){
						if(!Check.NuNObj(dto.getData().get("size"))){
							tenantEvaVos.setPeopleNum(Integer.valueOf(dto.getData().get("size").toString()));
						}
					}
					dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));
					tenantEvaVos.setLanEvaStatu(0);
					tenantEvaVos.setEvaTips(evaTips);
					if(dto.getCode() == 0){
						Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
						});
						List<EvaluateOrderEntity>  listOrderEvaluateOrderEntities = null;
						if(map.get("listOrderEvaluateOrder") !=null){
							listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(map.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
						}
						if(!Check.NuNCollection(listOrderEvaluateOrderEntities)){
							for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
								if(evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.TENANT.getUserType()){
									tenantEvaVos.setLanEvaStatu(evaluateOrderEntity.getEvaStatu());
									if(tenantEvaVos.getLanEvaStatu().intValue() ==EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
										if(houseSnapshotApiRe.getEvaType().intValue() == EvaTypeEnum.HAD_EVA.getCode()){
											tenantEvaVos.setEvaTips("房东、房客均已评价");
										}
									}else{
										tenantEvaVos.setEvaTips(tenantEvaVos.getEvaTips()+"、房客未评价");
									}
								}
							}
						}
							
					}
					listTenantEvaVos.add(tenantEvaVos);
				}

			}
			pageResult.setRows(listTenantEvaVos);
			dto.putValue("pageResult", pageResult);
		}

		return dto;
	}
	
	/**
	 * 获取评价时间
	 * @return
	 */
	private int  getEvalTimeDay(){
		/** 获取评价时间*/
		String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum003.getValue()));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
		if(resultDto.getCode() != DataTransferObject.SUCCESS){
			LogUtil.error(LOGGER, "获取评价时间,timeStrJson:{}", timeStrJson);
			throw new BusinessException("获取评价时间");
		}
		int limitDay = ValueUtil.getintValue(resultDto.getData().get("textValue"));
		return limitDay;
	}

	/**
	 * 
	 * 查询 评价详情
	 * 
	 * @author yd
	 * @created 2016年5月2日 下午3:36:05
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryEvaluateInfo")
	public  String queryEvaluateInfo(HttpServletRequest request,TenantEvaRequest tenantEvaApiRequest) {
		DataTransferObject dto  = new DataTransferObject();

		try {
			
			CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			tenantEvaApiRequest.setUid(customerVo.getUid());
			EvaluateInfoVo evaluateInfoVo = new EvaluateInfoVo();
			OrderDetailRequest orderDetailRequest  = new OrderDetailRequest();
			orderDetailRequest.setOrderSn(tenantEvaApiRequest.getOrderSn());
			orderDetailRequest.setRequestType(2);
			orderDetailRequest.setLandlordUid(customerVo.getUid());
			//查询评价的订单信息
			dto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.queryOrderInfoBySn(JsonEntityTransform.Object2Json(orderDetailRequest)));
			//订单快照无
			if(dto.getCode() == 1){
				//去失败页面  TODO
				return  "/evaluate/evaluateInfo";
			}
			OrderDetailVo oe = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderDetailVo", OrderDetailVo.class);

			//查询房东照片
			CustomerPicDto picDto =  new CustomerPicDto();
			picDto.setPicType(PicTypeEnum.USER_PHOTO.getCode());
			picDto.setUid(oe.getLandlordUid());
			dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picDto)));

			//此处查询不成功 不做处理
			LogUtil.info(LOGGER, "查询房东头像返回dto={}", dto);
			if(dto.getCode() == 0){
				CustomerPicMsgEntity customerPicMsg  = dto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {
				});
				if(!Check.NuNObj(customerPicMsg)&&!Check.NuNStr(customerPicMsg.getPicBaseUrl())){
					if(customerPicMsg.getPicBaseUrl().contains("http")){
						evaluateInfoVo.setLandlordPicUrl(customerPicMsg.getPicBaseUrl());
					}else{
						evaluateInfoVo.setLandlordPicUrl(PicUtil.getFullPic(picBaseAddrMona, customerPicMsg.getPicBaseUrl(), customerPicMsg.getPicSuffix(), default_head_size));
					}

				}
			}

			//查询房客头像 直接取redis
			if(!Check.NuNStr(oe.getUserUid())){
				//查询房客头像
				dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVo(oe.getUserUid()));
				if(dto.getCode() == 0){
					  customerVo  = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
					});
					if(!Check.NuNObj(customerVo)&&!Check.NuNStr(customerVo.getUserPicUrl())){
						evaluateInfoVo.setUserPicUrl(customerVo.getUserPicUrl());
					}
				}
			}
			evaluateInfoVo.setLandlordName(oe.getLandlordName());
			evaluateInfoVo.setHouseName(oe.getHouseName());
			if(!Check.NuNObj(oe.getRentWay())&&oe.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
				evaluateInfoVo.setHouseName(oe.getRoomName());
			}
			evaluateInfoVo.setUserName(oe.getUserName());
			evaluateInfoVo.setCheckInTime(oe.getCheckInTime());
			evaluateInfoVo.setCheckOutTime(oe.getCheckOutTime());
			evaluateInfoVo.setSumMoney(oe.getSumMoney());
			evaluateInfoVo.setContactsNum(oe.getContactsNum());
			evaluateInfoVo.setEvaStatus(oe.getEvaStatus());
			evaluateInfoVo.setHousingDay(DateSplitUtil.countDateSplit(oe.getStartTime(), oe.getEndTime()));
			if(!Check.NuNStr(oe.getPicUrl())){
				evaluateInfoVo.setPicUrl(PicUtil.getSpecialPic(picBaseAddrMona, oe.getPicUrl(), detail_big_pic));
			}
			evaluateInfoVo.setStartTimeStr(DateUtil.dateFormat(oe.getStartTime(), "yyyy-MM-dd"));
			evaluateInfoVo.setEndTimeStr(DateUtil.dateFormat(oe.getEndTime(), "yyyy-MM-dd"));
			EvaluateRequest evaluateRequest  = new EvaluateRequest();
			evaluateRequest.setOrderSn(tenantEvaApiRequest.getOrderSn());
			// 是否要加评价状态 审核未通过的给不给 展示  evaluateRequest.setEvaStatu(evaStatu);
			evaluateRequest.setEvaUserType(UserTypeEnum.All.getUserType());
			evaluateRequest.setEvaUserUid(tenantEvaApiRequest.getUid());
			dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));

			if(dto.getCode()!=MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE)){
				Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
				});
				List<EvaluateOrderEntity>  listOrderEvaluateOrderEntities = null;
				if(map.get("listOrderEvaluateOrder") !=null){
					listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(map.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
				}



				Date realEndTime = oe.getRealEndTime();
				int pjTime = getEvalShowLimitDay();
				if (pjTime == -1){
					pjTime = 7;
				}
				if(!Check.NuNCollection(listOrderEvaluateOrderEntities)){
					for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
						if(evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()){
							evaluateInfoVo.setEvaluateOrder(evaluateOrderEntity);
						}

						//房东端房客评价展示逻辑   房客评价 评价上线状态   如果是都评价显示   如果房客评价但是是超时时间展示
						if(evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.TENANT.getUserType()
								&& evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
							if (oe.getEvaStatus() == OrderEvaStatusEnum.ALL_EVA.getCode()
								|| (oe.getEvaStatus() == OrderEvaStatusEnum.UESR_HVA_EVA.getCode() && !Check.NuNObj(realEndTime) && DateUtils.isEvaExpire(realEndTime,pjTime))){
								if(map.get("tenantEvaluate") !=null){
									evaluateInfoVo.setTenantEvaluate(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(map.get("tenantEvaluate")), TenantEvaluateEntity.class));
									if(!Check.NuNObj(evaluateInfoVo.getTenantEvaluate().getCreateTime())){
										evaluateInfoVo.setTenTimeStr(DateUtil.dateFormat(evaluateInfoVo.getTenantEvaluate().getCreateTime(), "yyyy-MM"));
									}
								}
							}
						}
					}

				} 

				if(map.get("landlordEvaluate") !=null){
					evaluateInfoVo.setLandlordEvaluate(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(map.get("landlordEvaluate")), LandlordEvaluateEntity.class));
					
					if(!Check.NuNObj(evaluateInfoVo.getLandlordEvaluate().getCreateTime())){
						evaluateInfoVo.setLanTimeStr(DateUtil.dateFormat(evaluateInfoVo.getLandlordEvaluate().getCreateTime(), "yyyy-MM"));
					}
				} 
				request.setAttribute("evaluateInfoVo", evaluateInfoVo);
				request.setAttribute("orderSn", tenantEvaApiRequest.getOrderSn());
			}else{
				LogUtil.error(LOGGER, "查询房客的已评价列表时，封装房客的评价信息出现异常dto={},信息为",dto.toJsonString(),dto.getMsg());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询房客的已评价列表时，封装房客的评价信息出现异常");
		}
		return "/evaluate/evaluateInfo";
	}

	/**
	 * 获取评价展示的天数
	 * @return
	 */
	public int getEvalShowLimitDay(){
		String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum005.getValue()));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
		if(resultDto.getCode() != DataTransferObject.SUCCESS){
			LogUtil.error(LOGGER, "获取订单结束后展示评价天数,timeStrJson:{}", timeStrJson);
			return -1;
		}
		int limitDay = ValueUtil.getintValue(resultDto.getData().get("textValue"));
		return limitDay;
	}

	/**
	 * 
	 * 其他房东对该房客的评价记录
	 *
	 * @author yd
	 * @created 2016年5月12日 下午3:51:27
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/tenEvaRecord")
	@ResponseBody
	public DataTransferObject tenEvaRecord(HttpServletRequest request,LandlordEvaDetailDto paramDto) {
		DataTransferObject dto = new DataTransferObject();
		try{
//			ValidateResult<LandlordEvaDetailDto> validateResult = paramCheckLogic.checkParamValidate(JsonEntityTransform.Object2Json(evaRequest),
//					LandlordEvaDetailDto.class);
//			if (!validateResult.isSuccess()) {
//				return validateResult.getDto();
//			}
//			LandlordEvaDetailDto paramDto = JsonEntityTransform.json2Object(evaRequest,LandlordEvaDetailDto.class);
			if(Check.NuNObj(paramDto)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto;
			}
			if (Check.NuNStr(paramDto.getRatedUserUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("uid为空");
				return dto;
			}
			/** 查询其他房东评价房客列表 **/
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setEvaUserUid(paramDto.getRatedUserUid());
			evaluateRequest.setPage(paramDto.getPage());
			evaluateRequest.setLimit(5);

			dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.queryOtherLanEvaByPage(JsonEntityTransform
					.Object2Json(evaluateRequest)));
			//判断调用状态
			if (dto.getCode() == 1) {
				return dto;
			}

			List<LandlordEvaluateVo> listEvaluateVos = dto.parseData("listLandlordEvaluate", new TypeReference<List<LandlordEvaluateVo>>() {
			});
			
			int total = 0;
			if(!Check.NuNObj(dto.getData().get("total"))){
				total = Integer.valueOf(dto.getData().get("total").toString());
			}
			

			if(!Check.NuNCollection(listEvaluateVos)){
				for (LandlordEvaluateVo landlordEvaluateVo : listEvaluateVos) {
					//查询评价的订单信息
					dto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderInfoByOrderSn(landlordEvaluateVo.getOrderSn()));
					//订单快照无
					if(dto.getCode() == 1){
						return dto;
					}
					OrderInfoVo oe = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);
					//房源照片
					if(!Check.NuNStr(oe.getPicUrl())){
						landlordEvaluateVo.setPicUrl(PicUtil.getSpecialPic(picBaseAddrMona, oe.getPicUrl(), list_small_pic));
					}
					if(oe.getRentWay()==RentWayEnum.HOUSE.getCode()){
						landlordEvaluateVo.setHouseName(oe.getHouseName());
					}else if(oe.getRentWay()==RentWayEnum.ROOM.getCode()){
						landlordEvaluateVo.setHouseName(oe.getRoomName());
					}
					
					landlordEvaluateVo.setStartTimeStr(DateUtil.dateFormat(oe.getStartTime(), "yyyy-MM-dd"));

					evaluateRequest.setOrderSn(oe.getOrderSn());
					//TODO 是否要加评价状态 审核未通过的给不给 展示  evaluateRequest.setEvaStatu(evaStatu);
					evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
					dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));

					if(dto.getCode()!=MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE)){
						Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
						});
						if(map.get("tenantEvaluate") !=null){
							landlordEvaluateVo.setTenantEvaluateEntity(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(map.get("tenantEvaluate")), TenantEvaluateEntity.class));
							//封装房客图片
							String picUrl = getPicUrlByUid(oe.getUserUid());
							if(!Check.NuNStr(picUrl)){
								landlordEvaluateVo.setUserPicUrl(picUrl);
							}
							
						}

					}else{
						LogUtil.error(LOGGER, "查询房客的已评价列表时，封装房客的评价信息出现异常dto={},信息为",dto.toJsonString(),dto.getMsg());
					}
					
					try {//房东信息
						dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVo(oe.getLandlordUid()));
						if(dto.getCode() == DataTransferObject.SUCCESS){
							CustomerVo  customerVo  = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
							});
							landlordEvaluateVo.setLandlordNickName(customerVo.getNickName());
							LogUtil.info(LOGGER, "根据用户uid={},查询返回customerVo={}", oe.getLandlordUid(),JsonEntityTransform.Object2Json(customerVo));
						}
						
						//封装房东头像					
						String picUrl2 = getPicUrlByUid(oe.getLandlordUid());
						if(!Check.NuNStr(picUrl2)){
							landlordEvaluateVo.setLandlordPicUrl(picUrl2);
						}
					} catch (Exception e) {
						LogUtil.error(LOGGER, "查询房客的已评价列表时，封装房客的评价信息出现异常dto={},信息为",dto.toJsonString(),dto.getMsg());
					}
					
				}

				//封装预定人信息
				CustomerVo  customerVo  = null;
				if(!Check.NuNStr(paramDto.getRatedUserUid())){

					//查询预定人信息
					dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVo(paramDto.getRatedUserUid()));
					if(dto.getCode() == 0){
						customerVo  = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
						});
						LogUtil.info(LOGGER, "根据用户uid={},查询返回customerVo={}", paramDto.getRatedUserUid(),JsonEntityTransform.Object2Json(customerVo));
					}else{
						LogUtil.info(LOGGER, "根据用户uid={},查询返回失败返回结果dto={}", paramDto.getRatedUserUid(),dto.getMsg());
					}

				}

				dto = new DataTransferObject();
				dto.putValue("customerVo", customerVo);
				dto.putValue("listEvaluateVos", listEvaluateVos);
				dto.putValue("total", total);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询异常");
		}
		return dto;
	}

	
	/**
	 * 
	 * 根据uid获取图片地址
	 *
	 * @author yd
	 * @created 2016年5月19日 上午1:00:11
	 *
	 * @param uid
	 * @return
	 */
	private String getPicUrlByUid(String uid){
		
		//封装房客图片
		if(!Check.NuNStr(uid)){
			//查询房客头像
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVo(uid));
			if(dto.getCode() == 0){
				 CustomerVo  customerVo  = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
				});
				if(!Check.NuNObj(customerVo)&&!Check.NuNStr(customerVo.getUserPicUrl())){
					return customerVo.getUserPicUrl();
				}
			}
		}
		return this.userDefaultUrl;
	}
	/**
	 * 
	 * 房东评价（入口 点击评价按钮）
	 * 
	 * 业务说明：
	 * 1.分别校验两个实体参数  校验订单是否存在
	 * 2.订单房东是否已评价
	 * 3.该订单未评价，查看当前房东当前订单的评价记录 ，查询没有插入评价记录
	 * 4.插入成功后，修改订单状态为房东已评价，若此时房客已评价，修改为都已评价
	 * 5.修改订单状态成功后，修改此房东的评价记录标识为1，代表已评价
	 * 6.修改成功后，返回。
	 * 7.第2步订单房东为已评价，修改评价标识为1，成功返回。
	 * 8.第4步，插入失败后，返回失败结果
	 * 9.第5步，修改失败后，返回失败结果，此时，评论类容已插入，直接返回评论成功。
	 * 10.第5步中，修改评论标识错误后，直接返回评论成功。
	 *
	 *
	 * @author yd
	 * @created 2016年5月3日 上午11:21:51
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/lanEvaluate")
	@ResponseBody
	public DataTransferObject lanEvaluate(HttpServletRequest request,LanlordEvaRequest lanlordEvaRequest ){
		DataTransferObject dto = new  DataTransferObject();
		try {

			CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			lanlordEvaRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
			lanlordEvaRequest.setUid(customerVo.getUid());
			dto = checkEvaluateOrder(lanlordEvaRequest, dto);

			if(dto.getCode()!=0){
				return dto;
			}
			//查询当前订单
			dto  = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(lanlordEvaRequest.getOrderSn()));

			if(dto.getCode() == 0){
				OrderInfoVo order = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);
				//用户非法
				if(!lanlordEvaRequest.getUid().equals(order.getLandlordUid())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("当前评价用户非法");
					return dto;
				}
				//校验订单状态
				int orderStatu = order.getOrderStatus().intValue();
				if(orderStatu < OrderStatusEnum.CHECKING_OUT.getOrderStatus()){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("订单状态不对，不能评价");
					return dto;
				}
				//查询订单快照
				dto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.findHouseSnapshotByOrderSn(lanlordEvaRequest.getOrderSn()));
				//订单快照无
				if(dto.getCode() == 1){
					return dto;
				}
				OrderHouseSnapshotEntity orderHouseSnapshot = dto.parseData("orderHouseSnapshot", new TypeReference<OrderHouseSnapshotEntity>() {
				});
				//评论内容过滤
				dto =JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(lanlordEvaRequest.getContent()));

				List<String> listStrings = dto.parseData("ikList", new TypeReference<List<String>>() {
				});
				if(Check.NuNCollection(listStrings)){
					//修改订单的评价状态
					dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderByOrderSn(lanlordEvaRequest.getOrderSn()));
					OrderEntity orderEntity  = dto.parseData("order", new TypeReference<OrderEntity>() {
					});
					//校验订单房东是否已评价
					int evaStatus = orderEntity.getEvaStatus().intValue();
					if(evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode()||evaStatus==OrderEvaStatusEnum.UESR_HVA_EVA.getCode()){
						//保存房东信息
						EvaluateOrderEntity evaluateOrder = saveLandlordEvaluate(order, orderHouseSnapshot, dto, lanlordEvaRequest,orderEntity);
						if(dto.getCode() == 0){
							if(!Check.NuNObj(orderEntity)){
								orderEntity.setOldStatus(orderEntity.getOrderStatus());
								if(evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode()){
									orderEntity.setEvaStatus(OrderEvaStatusEnum.LANLORD_EVA.getCode());
								}else{
									orderEntity.setEvaStatus(OrderEvaStatusEnum.ALL_EVA.getCode());
								}
								//修改评价状态
								dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.updateOrderBaseByOrderSn(JsonEntityTransform.Object2Json(orderEntity)));
								if(dto.getCode() == 0){
									evaluateOrder.setOrderEvaFlag(OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode());
									evaluateOrder.setLastModifyDate(new Date());
									dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.updateEvaluateOrderByCondition(JsonEntityTransform.Object2Json(evaluateOrder)));
									return dto;
								}
							}
						}
					}
					if(dto.getCode() == 0){
						dto = new DataTransferObject();
						LogUtil.info(LOGGER,"评价已经存在");
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("不能重复评价");
					}

				}else{
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("评论包含敏感词，不能包含"+listStrings);
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务错误-点评异常");
		}
		return dto;
	}

	
	/**
	 * 
	 * 保存房客评价信息
	 *
	 * @author yd
	 * @created 2016年5月1日 下午5:31:15
	 *
	 * @param order
	 * @param orderHouseSnapshot
	 * @param dto
	 */
	private EvaluateOrderEntity saveLandlordEvaluate(OrderHouseVo order,OrderHouseSnapshotEntity orderHouseSnapshot,DataTransferObject dto,LanlordEvaRequest lanlordEvaApiRequest,OrderEntity orderEntity){
		
	    DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum001.getValue())));
		//房客未评价
		EvaluateOrderEntity evaluateOrder =  new EvaluateOrderEntity();
		evaluateOrder.setCreateTime(new Date());
		evaluateOrder.setEvaStatu(EvaluateStatuEnum.AUDIT.getEvaStatuCode());
		evaluateOrder.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
		evaluateOrder.setEvaUserUid(order.getLandlordUid());
		evaluateOrder.setFid(UUIDGenerator.hexUUID());
		evaluateOrder.setHouseFid(orderHouseSnapshot.getHouseFid());
		evaluateOrder.setLastModifyDate(new Date());
		evaluateOrder.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
		evaluateOrder.setOrderSn(order.getOrderSn());
		evaluateOrder.setRatedUserUid(order.getUserUid());
		evaluateOrder.setRentWay(order.getRentWay());
		evaluateOrder.setRoomFid(orderHouseSnapshot.getRoomFid());
		evaluateOrder.setBedFid(orderHouseSnapshot.getBedFid());

		if(resultDto.getCode() == DataTransferObject.SUCCESS
				&&!Check.NuNObj(resultDto.getData().get("textValue"))
				&&Integer.valueOf(resultDto.getData().get("textValue").toString()).intValue() == IsReleaseEnum.Release.getCode()){
			evaluateOrder.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		}
		LandlordEvaluateEntity landlordEvaluateEntity = new LandlordEvaluateEntity();
		landlordEvaluateEntity.setContent(lanlordEvaApiRequest.getContent());
		landlordEvaluateEntity.setCreateTime(new Date());
		landlordEvaluateEntity.setEvaOrderFid(evaluateOrder.getFid());
		landlordEvaluateEntity.setFid(UUIDGenerator.hexUUID());
		landlordEvaluateEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		landlordEvaluateEntity.setLandlordSatisfied(lanlordEvaApiRequest.getLandlordSatisfied());
		landlordEvaluateEntity.setLastModifyDate(new Date());

		dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.saveLandlordEvaluate(JsonEntityTransform.Object2Json(landlordEvaluateEntity), JsonEntityTransform.Object2Json(evaluateOrder),JsonEntityTransform.Object2Json(orderEntity)));

		return evaluateOrder;
	}
	
	/**
	 * 
	 * 校验评价订单关联实体
	 * 
	 *
	 * @author yd
	 * @created 2016年5月1日 上午11:16:37
	 *
	 * @param lanlordEvaRequest
	 */
	private DataTransferObject checkEvaluateOrder(LanlordEvaRequest lanlordEvaRequest ,DataTransferObject dto){

		if(dto == null) dto = new DataTransferObject();

		if(Check.NuNObj(lanlordEvaRequest)||Check.NuNStr(lanlordEvaRequest.getOrderSn())
				||Check.NuNStr(lanlordEvaRequest.getContent())
				||Check.NuNObj(lanlordEvaRequest.getLandlordSatisfied())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("评价内容不能为空");
			return dto;
		}
		
		if(lanlordEvaRequest.getContent().length()>MappMessageConst.EVA_CONTENT_MAX_LENGTH){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("评价内容字数过多");
		}

		return dto;
	}

}
