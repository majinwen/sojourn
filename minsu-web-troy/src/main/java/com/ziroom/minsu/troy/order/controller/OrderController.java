/**
 * @FileName: OrderController.java
 * @Package com.ziroom.minsu.troy.conf
 * 
 * @author liyingjie
 * @created 2016年3月22日 下午10:03:17
 * 
 * Copyright 2011-2015 
 */
package com.ziroom.minsu.troy.order.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.entity.order.OrderLogEntity;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.entity.order.OrderRelationEntity;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
//code.ziroom.com/minsu/minsu-web-troy.git
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.cms.api.inner.ActivityGiftService;
import com.ziroom.minsu.services.cms.api.inner.MobileCouponService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.thread.pool.SendEmailThreadPool;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.api.inner.CustomerRoleService;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.house.api.inner.HouseGuardService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.order.api.inner.CancelOrderService;
import com.ziroom.minsu.services.order.api.inner.CommissionerOrderService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.CancelOrderServiceRequest;
import com.ziroom.minsu.services.order.dto.OrderRelationRequest;
import com.ziroom.minsu.services.order.dto.OrderRelationSaveRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.dto.SendOrderEmailRequest;
import com.ziroom.minsu.services.order.dto.ShowCancelOrderResponse;
import com.ziroom.minsu.services.order.entity.CancleOrderVo;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.OrderRelationVo;
import com.ziroom.minsu.services.order.entity.OrderSpecialOfferVo;
import com.ziroom.minsu.troy.auth.menu.EvaluateAuthUtils;
import com.ziroom.minsu.troy.common.util.SendOrderEmailThread;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerRoleEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.CardTypeEnum;
import com.ziroom.minsu.valenum.order.CheckedStatusEnum;
import com.ziroom.minsu.valenum.order.LockTypeEnum;
import com.ziroom.minsu.valenum.order.OrderOprationType;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.order.PunishedStatusEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0025Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;

/**
 * <p>订单相关controller</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 * @param
 */
@Controller
@RequestMapping("order/")
public class OrderController {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Resource(name="order.orderCommonService")
	private OrderCommonService orderCommonService;

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name = "order.commissionerOrderService")
	private CommissionerOrderService commissionerOrderService;

	@Resource(name = "house.houseGuardService")
	private HouseGuardService houseGuardService;

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	@Resource(name = "order.cancelOrderService")
	private CancelOrderService cancelOrderService;
	
	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name = "customer.customerRoleService")
	private CustomerRoleService customerRoleService;

	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;
	
	@Resource(name = "cms.mobileCouponService")
	private MobileCouponService mobileCouponService;
	
	@Resource(name = "cms.activityGiftService")
	private ActivityGiftService activityGiftService;
	
	@Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;
	
	@Value("#{'${LAN_CANCEL_ACT_CODE}'.trim()}")
    private String LAN_CANCEL_ACT_CODE;
	/**
	 * 
	 * 到订单列表
	 *
	 * @author liyingjie
	 * @created 2016年4月7日 
	 *
	 */
	@RequestMapping("/orderList")
	public void toOrderList(HttpServletRequest request){
		//全列表
		request.setAttribute("orderOprationType", OrderOprationType.ALL_ORDER_LIST.getCode());
	}

	/**
	 *
	 * 到强制取消订单列表
	 *
	 * @author yd
	 * @created 2016年4月7日 
	 *
	 */
	@RequestMapping("/listForceCanOrder")
	public ModelAndView toForceCaOrderList(HttpServletRequest request){

		ModelAndView mv = new ModelAndView("/order/orderList");
		//强制取消
		mv.addObject("orderOprationType", OrderOprationType.FORCECAN_ORDER_LIST.getCode());
		return mv;
	}

	/**
	 * 
	 * 到强制取消订单差额列表
	 *
	 * @author yd
	 * @created 2016年4月7日 
	 *
	 */
	@RequestMapping("/toOrderReList")
	public ModelAndView toOrderReList(HttpServletRequest request,String editFlag){
		request.setAttribute("editFlag", editFlag);
		ModelAndView mv = new ModelAndView("/order/orderReList");
		return mv;
	}

	/**
	 * 
	 * 分页获取新旧
	 *
	 * @author yd
	 * @created 2016年4月7日 
	 * 
	 * @param relationRequest  此参数的构造函数 来确认订单的操作类型
	 *
	 */
	@RequestMapping("/orderReDataList")
	public @ResponseBody PageResult orderReList(@ModelAttribute("relationRequest") OrderRelationRequest relationRequest ,HttpServletRequest request) {



		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderService.queryOrderRelanionByPage(JsonEntityTransform.Object2Json(relationRequest)));

		List<OrderRelationVo> listOrderRelationVos = dto.parseData("listOrderRelationVo", new TypeReference<List<OrderRelationVo>>() {
		});

		PageResult pageResult = new PageResult();
		pageResult.setRows(listOrderRelationVos);
		pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
		return pageResult;
	}
	/**
	 * 
	 * 获取订单列表数据
	 *
	 * @author yd
	 * @created 2016年4月7日 
	 * 
	 * @param paramRequest  此参数的构造函数 来确认订单的操作类型
	 *
	 */
	@RequestMapping("/orderDataList")
	public @ResponseBody PageResult dataList(@ModelAttribute("paramRequest") OrderRequest paramRequest,HttpServletRequest request) {
		try {
			int orderOprationType = paramRequest.getOrderOprationType();
			Integer orderStatus = paramRequest.getOrderStatus();
			if(Check.NuNObj(orderStatus)&&Check.NuNCollection(paramRequest.getListOrderStatus())){
				if(orderOprationType == 2){
					List<Integer> listOrderStatu = new ArrayList<Integer>();
					listOrderStatu.add(OrderStatusEnum.CANCLE_FORCE.getOrderStatus());
					listOrderStatu.add(OrderStatusEnum.CANCLE_TENANT.getOrderStatus());
					listOrderStatu.add(OrderStatusEnum.CANCLE_TIME.getOrderStatus());
					listOrderStatu.add(OrderStatusEnum.FORCED_CANCELLATION.getOrderStatus());
					paramRequest.setListOrderStatus(listOrderStatu);
				}
			}
			if(!Check.NuNStr(paramRequest.getCreateTimeStart())){
				paramRequest.setCreateTimeStart(paramRequest.getCreateTimeStart()+" 00:00:00");
			}
			if(!Check.NuNStr(paramRequest.getCreateTimeEnd())){
				paramRequest.setCreateTimeEnd(paramRequest.getCreateTimeEnd()+" 23:59:59");
			}
			/**
			 * 增加入住和退房开始和结束日期的筛选
			 */
			if(!Check.NuNStr(paramRequest.getCheckInTimeStart())){
				paramRequest.setCheckInTimeStart(paramRequest.getCheckInTimeStart()+" 00:00:00");
			}
			if(!Check.NuNStr(paramRequest.getCheckInTimeEnd())){
				paramRequest.setCheckInTimeEnd(paramRequest.getCheckInTimeEnd()+" 23:59:59");
			}
			if(!Check.NuNStr(paramRequest.getCheckOutTimeStart())){
				paramRequest.setCheckOutTimeStart(paramRequest.getCheckOutTimeStart()+" 00:00:00");
			}
			if(!Check.NuNStr(paramRequest.getCheckOutTimeEnd())){
				paramRequest.setCheckOutTimeEnd(paramRequest.getCheckOutTimeEnd()+" 23:59:59");
			}
			paramRequest.setRequestType(3);

			if(!Check.NuNStr(paramRequest.getEmpPushName()) || !Check.NuNStr(paramRequest.getEmpGuardName())){
				HouseGuardRelEntity houseGuard = new HouseGuardRelEntity();
				if(!Check.NuNStr(paramRequest.getEmpPushName())){
					houseGuard.setEmpPushName(paramRequest.getEmpPushName());
				}
				if(!Check.NuNStr(paramRequest.getEmpGuardName())){
					houseGuard.setEmpGuardName(paramRequest.getEmpGuardName());
				}
				String resultJson = houseGuardService.findHouseGuardRelByCondition(JsonEntityTransform.Object2Json(houseGuard));
				List<HouseGuardRelEntity> houseGuardRels = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseGuardRelEntity.class);
				if(Check.NuNCollection(houseGuardRels)){
					return new PageResult();
				}
				for (HouseGuardRelEntity houseGuardRel : houseGuardRels) {
					paramRequest.getHouseFids().add(houseGuardRel.getHouseFid());
				}
			}
			Object authMenu = request.getAttribute("authMenu");
			paramRequest.setRoleType(RoleTypeEnum.ADMIN.getCode());
			if(!addAuthData(authMenu,paramRequest)){
				return null;
			}
			LogUtil.info(logger, "订单查询条件参数={}", JsonEntityTransform.Object2Json(paramRequest));
			String resultJson = orderCommonService.getOrderListByCondiction(JsonEntityTransform.Object2Json(paramRequest));
			LogUtil.info(logger, "订单查询结果={}", resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<OrderInfoVo> orderHouseList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);
			if (!Check.NuNCollection(orderHouseList)){
				for (OrderInfoVo infoVo : orderHouseList) {
					if(Check.NuNObj(infoVo.getRentWay())){
						break;
					}
					if (infoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
						infoVo.setHouseName(infoVo.getRoomName());
					}
					String houseGuardRelJson = houseGuardService.findHouseGuardRelByHouseBaseFid(infoVo.getHouseFid());
					HouseGuardRelEntity houseGuardRelEntity = SOAResParseUtil.getValueFromDataByKey(houseGuardRelJson, "houseGuardRel", HouseGuardRelEntity.class);
					if(!Check.NuNObj(houseGuardRelEntity)){
						infoVo.setEmpGuardCode(houseGuardRelEntity.getEmpGuardCode());
						infoVo.setEmpGuardName(houseGuardRelEntity.getEmpGuardName());
						infoVo.setEmpPushCode(houseGuardRelEntity.getEmpPushCode());
						infoVo.setEmpPushName(houseGuardRelEntity.getEmpPushName());
					}
				}
			}

			//返回结果
			PageResult pageResult = new PageResult();
			pageResult.setRows(orderHouseList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("size").toString()));
			return pageResult;
		} catch (SOAParseException e) {
			LogUtil.error(logger,"数据转化异常e={}", e);
		}
		return null;
	}

	/**
	 * 
	 * 客服恢复订单
	 *
	 * @author yd
	 * @created 2016年4月26日 下午9:21:42
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/recoveryCancelOrder")
	public @ResponseBody String recoveryCancelOrder(HttpServletRequest request){
		String orderSn = request.getParameter("orderSn");
		return this.commissionerOrderService.recoveryCancelOrde(JsonEntityTransform.Object2Json(orderSn), UserUtil.getCurrentUserFid());
	}
	/**
	 * 
	 * 客服同意强制取消订单
	 *
	 * @author yd
	 * @created 2016年4月26日 下午9:21:42
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/agreeCancelOrder")
	public @ResponseBody String agreeCancelOrder(HttpServletRequest request) throws SOAParseException{
		String orderSn = request.getParameter("orderSn");
		String orderCancelType = request.getParameter("orderCancelType");
		if(!Check.NuNStr(orderSn)&&!Check.NuNStr(orderCancelType)){
			String resultJson=this.commissionerOrderService.agreeCancelOrde(JsonEntityTransform.Object2Json(orderSn), UserUtil.getCurrentUserFid(), Integer.valueOf(orderCancelType));
			SendOrderEmailRequest orderEmailRequest=SOAResParseUtil.getValueFromDataByKey(resultJson, "orderEmail", SendOrderEmailRequest.class);
			if(!Check.NuNObj(orderEmailRequest)){
				SendEmailThreadPool.execute(new SendOrderEmailThread(orderEmailRequest,customerMsgManagerService,smsTemplateService));
			}
			return this.commissionerOrderService.agreeCancelOrde(JsonEntityTransform.Object2Json(orderSn), UserUtil.getCurrentUserFid(), Integer.valueOf(orderCancelType));
		}
		return null;
	}

	/**
	 * 
	 * 获取两个订单的差额
	 *
	 * @author yd
	 * @created 2016年4月27日 下午1:49:56
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMoneyLast")
	public @ResponseBody  String getMoneyLast(HttpServletRequest request){
		String oldOrderSn = request.getParameter("oldOrderSn");
		String newOrderSn = request.getParameter("newOrderSn");
		if(!Check.NuNStr(oldOrderSn)&&!Check.NuNStr(newOrderSn)){

			return this.commissionerOrderService.getMoneyLast(newOrderSn, oldOrderSn);
		}

		return null;
	}
	/**
	 * 
	 * 新订单关联旧订单
	 *
	 * @author yd
	 * @created 2016年4月27日 下午2:14:05
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveOrderRelation")
	public @ResponseBody String saveOrderRelation(HttpServletRequest request){

		DataTransferObject dto = null;
		String oldOrderSn = request.getParameter("oldOrderSn");
		String newOrderSn = request.getParameter("newOrderSn");
		if(!Check.NuNStr(oldOrderSn)&&!Check.NuNStr(newOrderSn)){

			OrderRelationRequest relationRequest = new OrderRelationRequest();
			relationRequest.setOldOrderSn(oldOrderSn);
			relationRequest.setCheckedStatus(CheckedStatusEnum.INIT_CHECKED.getCode());

			OrderRelationEntity  orderRelationEntity  = orderRelationEntity(relationRequest, dto);

			if(!Check.NuNObj(orderRelationEntity)){
				orderRelationEntity.setCheckedStatus(CheckedStatusEnum.WAITTING_CHECKED.getCode());
				orderRelationEntity.setReUserUid(UserUtil.getCurrentUserFid());
				orderRelationEntity.setNewOrderSn(newOrderSn);
				orderRelationEntity.setLastModifyDate(new Date());

				OrderRelationSaveRequest orderRelationSaveRequest  = new OrderRelationSaveRequest();
				orderRelationSaveRequest.setOrderRelationEntity(orderRelationEntity);
				dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderService.saveOrderRelation(JsonEntityTransform.Object2Json(orderRelationSaveRequest)));
			}
		}else{
			if(dto == null) dto = new DataTransferObject();
			dto.setErrCode(1);
			dto.setMsg("oldOrderSn or newOrderSn is null");
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * get OrderRelationEntity by relationRequest
	 *
	 * @author yd
	 * @created 2016年4月27日 下午4:05:49
	 *
	 * @param relationRequest
	 * @param dto
	 * @return
	 */
	private OrderRelationEntity orderRelationEntity(OrderRelationRequest relationRequest,DataTransferObject dto){


		OrderRelationEntity  orderRelationEntity  =  null;
		if(relationRequest == null) relationRequest = new OrderRelationRequest();


		dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderService.queryByCondition(JsonEntityTransform.Object2Json(relationRequest)));

		List<OrderRelationEntity> listOrderRelation = dto.parseData("listOrderRelation", new TypeReference<List<OrderRelationEntity>>() {
		});
		if(!Check.NuNCollection(listOrderRelation)&&listOrderRelation.size()==1){
			orderRelationEntity  = listOrderRelation.get(0);
		}else{
			dto.setErrCode(1);
			dto.setMsg("此旧订单已经关联已经错误，请联系客服");
		}

		return orderRelationEntity;
	}
	/**
	 * 
	 * 待审批 强制取消订单审核
	 *
	 * @author yd
	 * @created 2016年4月27日 下午3:58:21
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/auditOrderRelation")
	public @ResponseBody String auditOrderRelation(HttpServletRequest request){

		DataTransferObject dto = null;
		String oldOrderSn = request.getParameter("oldOrderSn");
		String newOrderSn = request.getParameter("newOrderSn");
		String checkedStatu = request.getParameter("checkedStatu");
		if(!Check.NuNStr(oldOrderSn)&&!Check.NuNStr(newOrderSn)&&!Check.NuNStr(checkedStatu)){

			int checkedStatus = Integer.valueOf(checkedStatu);
			if(Check.NuNObj(CheckedStatusEnum.getCheckedStatusEnumByCode(checkedStatus))){
				if(dto == null) dto = new DataTransferObject();
				dto.setErrCode(1);
				dto.setMsg("待修改的状态不存在");
				return dto.toJsonString();
			}
			OrderRelationRequest relationRequest = new OrderRelationRequest();
			relationRequest.setOldOrderSn(oldOrderSn);
			relationRequest.setNewOrderSn(newOrderSn);

			OrderRelationEntity  orderRelationEntity  = orderRelationEntity(relationRequest, dto);

			if(!Check.NuNObj(orderRelationEntity)){
				orderRelationEntity.setCheckedStatus(checkedStatus);
				orderRelationEntity.setAuditPersonFid(UserUtil.getCurrentUserFid());;
				orderRelationEntity.setLastModifyDate(new Date());

				OrderRelationSaveRequest orderRelationSaveRequest  = new OrderRelationSaveRequest();
				orderRelationSaveRequest.setOrderRelationEntity(orderRelationEntity);
				dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderService.updateOrderRelationByCondition(JsonEntityTransform.Object2Json(orderRelationSaveRequest)));
			}
		}else{
			if(dto == null) dto = new DataTransferObject();
			dto.setErrCode(1);
			dto.setMsg("oldOrderSn or newOrderSn or checkedStatus is null");
		}

		return dto.toJsonString();
	}

	/**
	 * 
	 * get order detail by orderSn 
	 *
	 * @author yd
	 * @created 2016年5月8日 上午
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getOrderDetail", method = RequestMethod.GET)
	public ModelAndView getOrderDetail(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/order/orderDetail");

		String orderSn = request.getParameter("orderSn");
		DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderAllBySn(orderSn));
		if(dto.getCode() == DataTransferObject.SUCCESS){
			OrderDetailVo order = dto.parseData("orderDetailVo", new TypeReference<OrderDetailVo>() {});
			mv.addObject("detail",order);

			OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(order.getOrderStatus());

			String name = orderStatusEnum.getIncomeName(order).substring(0,2);
			mv.addObject("name",name);

			int userComm = orderStatusEnum.getComm(order.getUserCommMoney(),order.getRealUserMoney(),order);
			mv.addObject("userComm",userComm);

			int landComm = orderStatusEnum.getComm(order.getLanCommMoney(),order.getRealLanMoney(),order);
			mv.addObject("landComm",landComm);


			OrderPayEntity pay = dto.parseData("pay", new TypeReference<OrderPayEntity>() {});
			mv.addObject("pay",pay);


			List<UsualContactEntity> contacts = dto.parseData("contacts", new TypeReference<List<UsualContactEntity>>() {});
			mv.addObject("contacts",this.transContacts(contacts));

			Map<String,Integer> prices = dto.parseData("prices", new TypeReference<Map<String,Integer>>() {});
			mv.addObject("prices",this.transPrices(prices,mv));

			List<OrderLogEntity> logs = dto.parseData("log", new TypeReference<List<OrderLogEntity>>() {});
			mv.addObject("logs",this.transLog(logs));

			//订单活动信息
			List<OrderActivityEntity> listOrderAc =  dto.parseData("orderAc", new TypeReference< List<OrderActivityEntity>>() {});
			mv.addObject("listOrderAc",listOrderAc);

			//订单优惠信息
			List<OrderSpecialOfferVo> listOrderSpecialOfferVo =  dto.parseData("listOrderSpecialOfferVo", new TypeReference<List<OrderSpecialOfferVo>>() {});
			mv.addObject("listOrderSpecialOfferVo",listOrderSpecialOfferVo);
			
			//退订政策
			String checkOutRulesCode = order.getCheckOutRulesCode();
			if(!Check.NuNStr(checkOutRulesCode)){
				TradeRulesEnum005Enum tradeRulesEnum005Enum = TradeRulesEnum005Enum.getEnumByValue(checkOutRulesCode);
				mv.addObject("checkOutRulesName",Check.NuNObj(tradeRulesEnum005Enum) ? null : tradeRulesEnum005Enum.getName());
			}
		}
		return mv;
	}



	/**
	 * 处理当前的价格
	 * @param prices
	 * @return
	 */
	private List<Map>  transPrices(Map<String,Integer> prices,ModelAndView mv){
		List<Map> prriceList= new ArrayList<>();
		int all = 0;
		if (!Check.NuNMap(prices)){
			Map<String,Integer> pricesSort = new TreeMap<>(prices);
			for (Map.Entry<String,Integer> entry : pricesSort.entrySet()) {
				String key = entry.getKey();
				if (key.startsWith("price_org")){
					continue;
				}
				Map<String,Object> ele = new HashMap<>();
				ele.put("day",entry.getKey());
				ele.put("price",entry.getValue());
				all += entry.getValue();
				prriceList.add(ele);
			}
		}
		mv.addObject("all",all);
		return prriceList;

	}


	/**
	 * 处理当前的联系人信息
	 * @param contacts
	 * @return
	 */
	private List<Map>  transContacts(List<UsualContactEntity> contacts){

		List<Map> opLogs = new ArrayList<>();
		if (!Check.NuNCollection(contacts)){
			for (UsualContactEntity contact : contacts) {
				Map<String,Object> ele = new HashMap<>();
				CardTypeEnum cardTypeEnum = CardTypeEnum.getByCode(contact.getCardType());
				String cardType = "异常的证件类型";
				if (!Check.NuNObj(cardTypeEnum)){
					cardType = cardTypeEnum.getName();
				}
				ele.put("cardType",cardType);
				ele.put("conName",contact.getConName());
				ele.put("cardValue",contact.getCardValue());
				ele.put("conTel",contact.getConTel());
				opLogs.add(ele);
			}
		}
		return opLogs;

	}


	/**
	 * 获取当前的订单的操作记录
	 * @param logs
	 * @return
	 */
	private List<Map>  transLog(List<OrderLogEntity> logs){
		List<Map> opLogs = new ArrayList<>();
		if (!Check.NuNCollection(logs)){
			for (OrderLogEntity log : logs) {
				Map<String,Object> ele = new HashMap<>();
				ele.put("createDate",log.getCreateDate());
				ele.put("mark",log.getRemark());
				OrderStatusEnum statusEnumFrom = OrderStatusEnum.getOrderStatusByCode(log.getFromStatus());
				if (!Check.NuNObj(statusEnumFrom)){
					ele.put("fromStatus",statusEnumFrom.getStatusName());
				}else {
					ele.put("fromStatus","异常的订单状态");
				}
				OrderStatusEnum statusEnumTo = OrderStatusEnum.getOrderStatusByCode(log.getToStatus());
				if (!Check.NuNObj(statusEnumTo)){
					ele.put("toStatus",statusEnumTo.getStatusName());
				}else {
					ele.put("toStatus","异常的订单状态");
				}
				opLogs.add(ele);
			}
		}
		return opLogs;
	}

	/**
	 * 
	 * 订单权限配置
	 *
	 * @author yd
	 * @created 2016年10月31日 下午3:51:31
	 *
	 * @param authMenu
	 * @param paramRequest
	 */
	private boolean addAuthData(Object authMenu,OrderRequest paramRequest){

		boolean addFlag = false;
		//权限过滤
		if(!Check.NuNObj(authMenu)){
			AuthMenuEntity authMenuEntity = (AuthMenuEntity)authMenu;
			if(!Check.NuNObj(authMenuEntity.getRoleType())&&authMenuEntity.getRoleType().intValue()>0){
				paramRequest.setRoleType(authMenuEntity.getRoleType());
				DataTransferObject authDto =  EvaluateAuthUtils.getAuthHouseFids(authMenuEntity, troyHouseMgtService);
				if(authDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(logger, "当前菜单类型：{},权限异常error={}", "查看评价",authDto.getMsg());
					return addFlag;
				}
				try {
					List<String> fids = SOAResParseUtil.getListValueFromDataByKey(authDto.toJsonString(), "houseFids", String.class);
					if(Check.NuNCollection(fids)){
						LogUtil.error(logger, "当前菜单类型：{},无权限，fids={}", "查看评价",fids);
						return addFlag;
					}
					paramRequest.setHouseFids(fids);
				} catch (SOAParseException e) {
					LogUtil.error(logger, "评价权限查询房源集合异常e={}", e);
					return addFlag;
				}
			}
		}
		return true;
	}

	/**
	 * 跳转客服取消订单页面
	 * @author lishaochuan
	 * @create 2017/1/6 16:12
	 * @param 
	 * @return 
	 */
	@RequestMapping("/toCancelOrderList")
	public String toCancelOrderList(HttpServletRequest request){
		return "/order/cancelOrderList";
	}

	/**
	 * 查询客服取消订单列表
	 * @author lishaochuan
	 * @create 2017/1/6 16:23
	 * @param 
	 * @return 
	 */
	@RequestMapping("/findCancelOrderList")
	@ResponseBody
	public PageResult findCancelOrderList(HttpServletRequest request, OrderRequest orderRequest){
		try {
			//orderRequest.setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus()+"");
			if (!Check.NuNObj(orderRequest.getOrderStatus()) && orderRequest.getOrderStatus()==370) {
				//370=未支付协商取消，在数据库中没有对应的状态，只做查询使用 
				orderRequest.setOrderStatus(OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus());
				orderRequest.setPayStatus(String.valueOf(YesOrNoEnum.NO.getCode()));
			}
			orderRequest.setRequestType(3);
			if(Check.NuNObj(orderRequest.getOrderStatus())){
				List<Integer> listOrderStatus = new ArrayList<>();
				listOrderStatus.add(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
				listOrderStatus.add(OrderStatusEnum.CHECKED_IN.getOrderStatus());
				listOrderStatus.add(OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus());
				listOrderStatus.add(OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus());
				listOrderStatus.add(OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus());
				listOrderStatus.add(OrderStatusEnum.FINISH_LAN_APPLY.getOrderStatus());
				listOrderStatus.add(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus());
				orderRequest.setListOrderStatus(listOrderStatus);
			}
			String resultJson = orderCommonService.getOrderListByCondiction(JsonEntityTransform.Object2Json(orderRequest));

			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<OrderInfoVo> orderHouseList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);
			if (!Check.NuNCollection(orderHouseList)){
				for (OrderInfoVo infoVo : orderHouseList) {
					if(Check.NuNObj(infoVo.getRentWay())){
						break;
					}
					if (infoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
						infoVo.setHouseName(infoVo.getRoomName());
					}
				}
			}

			//返回结果
			PageResult pageResult = new PageResult();
			pageResult.setRows(orderHouseList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("size").toString()));
			return pageResult;
		} catch (SOAParseException e) {
			LogUtil.error(logger,"数据转化异常e={}", e);
		}
		return null;
	}

	/**
	 * 
	 * 到 可取取消订单页面
	 *
	 * @author yd
	 * @created 2017年1月6日 下午3:51:16
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/toCancleOrderInfo")
	public String toCancleOrderInfo(HttpServletRequest request){
		String orderSn = request.getParameter("orderSn");
		request.setAttribute("orderSn", orderSn);
		//订单状态=20，支付状态=0，跳转到新的页面
		String orderJson = orderCommonService.getOrderByOrderSn(orderSn);
	    DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
		if(orderDto.getCode() == DataTransferObject.SUCCESS){
			try {
				OrderEntity orderEntity = SOAResParseUtil.getValueFromDataByKey(orderJson, "order", OrderEntity.class);
				if(orderEntity.getOrderStatus()==OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus() && orderEntity.getPayStatus()==YesOrNoEnum.NO.getCode()){
					return "/order/cancelUnPayOrderInfo";
				}
			} catch (SOAParseException e) {
				LogUtil.error(logger, "toCancleOrderInfo方法异常", e);
			}
			
		}
		return "/order/cancleOrderInfo";
	}

	/**
	 * 
	 * 根据订单编号 获取 客服取消订单的信息
	 *
	 * @author yd
	 * @created 2017年1月4日 下午5:56:13
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/findCancleOrderInfo")
	@ResponseBody
	public DataTransferObject findCancleOrderInfo(HttpServletRequest request){

		String orderSn = request.getParameter("orderSn");
		DataTransferObject dto  = null;
		if(Check.NuNStrStrict(orderSn)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数");
			return dto;
		}

		
		dto = JsonEntityTransform.json2DataTransferObject(commissionerOrderService.findCancleOrderVo(orderSn));
		CancleOrderVo cancleOrderVo=null;
		if(dto.getCode()== DataTransferObject.SUCCESS){
			 cancleOrderVo = dto.parseData("cancleOrderVo", new TypeReference<CancleOrderVo>() {});
			
		
//取消订单——房东取消订单数据
			String orderJson = orderCommonService.getOrderByOrderSn(orderSn);                                                     
			DataTransferObject orderDto  = JsonEntityTransform.json2DataTransferObject(orderJson);
			OrderEntity orderEntity = orderDto.parseData("order", new TypeReference<OrderEntity>() {});
			if(Check.NuNObj(orderEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("根据orderSn获取订单实体为空");
				return dto;
			}
			
			cancleOrderVo.setSwitchFirstNightMoney(YesOrNoEnum.NO.getCode());
			cancleOrderVo.setSwitchOneHundred(YesOrNoEnum.NO.getCode());
			Date now = new Date();
			Date oneDayAgoByOrderStart = DateUtil.getTime(orderEntity.getStartTime(), -1);//订单开始前24小时  时间戳
			DataTransferObject selectEnumDto  = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, ProductRulesEnum0025Enum.ProductRulesEnum0025001.getValue()));
			if(selectEnumDto.getCode()==DataTransferObject.ERROR){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("获取枚举失败");
				return dto;
			}
			String inSomeMonth = selectEnumDto.parseData("textValue", new TypeReference<String>() {
			});
			if(Check.NuNStr(inSomeMonth)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("获取罚金失败");
				return dto;
			}
			//获取几个月之内   开始
		    Date beforeSixMonth = getNowAroundMonths(now, -Integer.valueOf(inSomeMonth));
            if(Check.NuNObj(beforeSixMonth)){
            	 dto.setErrCode(DataTransferObject.ERROR);
                 dto.setMsg("调用获取从现在开始多少个月之内方法失败");
                 return dto;
            }
			String countInTimesJson = cancelOrderService.getCountInTimes(orderEntity.getLandlordUid(), beforeSixMonth, now);
			DataTransferObject countInTimesDto  = JsonEntityTransform.json2DataTransferObject(countInTimesJson);
			if(countInTimesDto.getCode()==DataTransferObject.ERROR){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("根据orderSn获取订单实体为空");
				return dto;
			}
			int lanCancelOrderInSixMonth = (int) countInTimesDto.getData().get("countInTimes");
			dto.putValue("switchCancelAngel", YesOrNoEnum.NO.getCode());
			if(lanCancelOrderInSixMonth>0){
				dto.putValue("switchCancelAngel", YesOrNoEnum.YES.getCode());
			}
			if(now.getTime() < oneDayAgoByOrderStart.getTime()){//取消订单这一刻还在24小时之外
				//获取几个月之内   枚举
				if(lanCancelOrderInSixMonth>0){
					cancleOrderVo.setSwitchOneHundred(YesOrNoEnum.YES.getCode());
				}
			}else if(now.getTime() >= oneDayAgoByOrderStart.getTime()){
				cancleOrderVo.setSwitchFirstNightMoney(YesOrNoEnum.YES.getCode());
			}
			cancleOrderVo.setStartTime(DateUtil.dateFormat(orderEntity.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
//取消订单——房东取消订单数据  结束			
		}
		
		request.setAttribute("cancleOrderVo", cancleOrderVo);
		dto.putValue("cancleOrderVo", cancleOrderVo);
		return dto;
	}

	/**
	 * 
	 * 客服取消订单 提交申请
	 *
	 * @author yd
	 * @created 2017年1月9日 下午4:59:30
	 *
	 * @param request
	 * @param cancelOrder
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/cancelOrderNegotiate")
	@ResponseBody
	public DataTransferObject cancelOrderNegotiate(HttpServletRequest request,CancelOrderServiceRequest cancelOrder) throws SOAParseException{
		cancelOrder.setEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
		cancelOrder.setOperUid(UserUtil.getCurrentUserFid());
		cancelOrder.setOperName(UserUtil.getFullCurrentUser().getFullName());
		
		//先确定该订单是否是，未支付取消订单
		String orderJson = orderCommonService.getOrderByOrderSn(cancelOrder.getOrderSn());
		DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
		if(orderDto.getCode() == DataTransferObject.SUCCESS){
				try {
					OrderEntity orderEntity = SOAResParseUtil.getValueFromDataByKey(orderJson, "order", OrderEntity.class);
					if(Check.NuNObj(orderEntity)){
						orderDto.setErrCode(YesOrNoEnum.NO.getCode());
						orderDto.setMsg("订单信息不存在");
						return  orderDto;
					}
					if(orderEntity.getOrderStatus()==OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus() && orderEntity.getPayStatus()==YesOrNoEnum.NO.getCode()){
						if(Check.NuNStr(cancelOrder.getCancelReason())){
							orderDto.setErrCode(YesOrNoEnum.NO.getCode());
							orderDto.setMsg("备注信息不能为空");
							return  orderDto;
						}
						//未支付订单取消，开始处理
						String cancelUnPayOrderJson = orderCommonService.cancelUnPayOrder(JsonEntityTransform.Object2Json(cancelOrder));
						DataTransferObject cancelUnPayOrderDto = JsonEntityTransform.json2DataTransferObject(cancelUnPayOrderJson);
						return cancelUnPayOrderDto;
					}
				} catch (SOAParseException e) {
					LogUtil.error(logger, "未支付取消订单方法异常", e);
				}
					
		}

		OrderInfoVo orderInfoVo = null;
		int countInTimes = 0;
		if(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus()==cancelOrder.getCancelType().intValue()){
			String orderSn = cancelOrder.getOrderSn();
			DataTransferObject orderInfoDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderInfoByOrderSn(orderSn));
			if (orderInfoDto.getCode() == DataTransferObject.ERROR){
				return orderInfoDto;
			}
			orderInfoVo = orderInfoDto.parseData("orderInfoVo", new TypeReference<OrderInfoVo>() {});
			//获取6个月枚举类
			DataTransferObject selectEnumDto  = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, ProductRulesEnum0025Enum.ProductRulesEnum0025001.getValue()));
			if (selectEnumDto.getCode() == DataTransferObject.ERROR){
				return selectEnumDto;
			}
			String inSomeMonth = selectEnumDto.parseData("textValue", new TypeReference<String>() {});

			Date beforeSixMonth = getNowAroundMonths(new Date(), -Integer.valueOf(inSomeMonth));
			DataTransferObject countTimesDto = JsonEntityTransform.json2DataTransferObject(cancelOrderService.getCountInTimes(orderInfoVo.getLandlordUid(), beforeSixMonth, new Date()));
			countInTimes = (int)countTimesDto.getData().get("countInTimes");
			cancelOrder.setLanCancelOrderInSomeTime(countInTimes);
		}

		String resultJson=this.cancelOrderService.cancelOrderNegotiate(JsonEntityTransform.Object2Json(cancelOrder));
		DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(resultJson);
		
		
         //房东强制取消
		if(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus()==cancelOrder.getCancelType().intValue() && dto.getCode()==DataTransferObject.SUCCESS){
			String orderSn = orderInfoVo.getOrderSn();

			//取消天使房东  customer库
			if(!Check.NuNObj(cancelOrder.getIsCancelAngel()) && YesOrNoEnum.YES.getCode()==cancelOrder.getIsCancelAngel().intValue() && (countInTimes >0 || cancelOrder.getIsEdit() == YesOrNoEnum.YES.getCode())){
				//将天使房东免佣金记录删掉(逻辑删除)
				ActivityFreeEntity activityFreeEntity =new ActivityFreeEntity();
				activityFreeEntity.setUid(orderInfoVo.getLandlordUid());
				activityFreeEntity.setIsDel(YesOrNoEnum.YES.getCode());
				String paramJson = JsonEntityTransform.Object2Json(activityFreeEntity);
				String cancelFreeCommissionJson = activityGiftService.cancelFreeCommission(paramJson);
				DataTransferObject cancelFreeCommissionDto = JsonEntityTransform.json2DataTransferObject(cancelFreeCommissionJson);
				//只有将免佣金取消之后，再去取消天使房东资格
                if(cancelFreeCommissionDto.getCode()==DataTransferObject.SUCCESS){//取消天使房东免佣金成功
                	String cancelAngelJson = customerRoleService.cancelAngelLandlord(orderInfoVo.getLandlordUid(), CustomerRoleEnum.SEED.getStr());
    				DataTransferObject cancelAngelDto  = JsonEntityTransform.json2DataTransferObject(cancelAngelJson);
    				if(cancelAngelDto.getCode()==DataTransferObject.SUCCESS){
    					 cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001003.getValue(), YesOrNoEnum.YES.getStr());
    				}
                }
			}
			
			//3.增加系统评价   去掉
			/*if(!Check.NuNObj(cancelOrder.getIsAddSystemEval()) && YesOrNoEnum.YES.getCode()==cancelOrder.getIsAddSystemEval().intValue()){
					String saveSystemEvalJson= evaluateOrderService.saveSystemEval(JsonEntityTransform.Object2Json(orderInfoVo));
					DataTransferObject saveSystemEvalDto  = JsonEntityTransform.json2DataTransferObject(saveSystemEvalJson);
					if(saveSystemEvalDto.getCode()==DataTransferObject.SUCCESS){
						 cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001004.getValue(), YesOrNoEnum.YES.getStr());
					}
			}*/
			
				
			//更改排序因子发送mq
			if(!Check.NuNObj(cancelOrder.getIsUpdateRankFactor()) &&  YesOrNoEnum.YES.getCode()==cancelOrder.getIsUpdateRankFactor().intValue()){
				   String sendRabbitMqJson = troyHouseMgtService.sendRabbitMq(orderInfoVo.getHouseFid(), orderInfoVo.getRentWay());
				   DataTransferObject sendRabbitMqDto = JsonEntityTransform.json2DataTransferObject(sendRabbitMqJson); 
				   if(sendRabbitMqDto.getCode()==DataTransferObject.SUCCESS){
					   cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001005.getValue(), YesOrNoEnum.YES.getStr());
				   }
			}
			
		
			//赠送优惠券
			if(!Check.NuNObj(cancelOrder.getIsGiveCoupon()) && YesOrNoEnum.YES.getCode()==cancelOrder.getIsGiveCoupon().intValue()){
				if(!Check.NuNStr(orderInfoVo.getUserUid()) && !Check.NuNStr(LAN_CANCEL_ACT_CODE)){
					Map<String, String> map = new HashMap<String, String>();
					map.put("uid", orderInfoVo.getUserUid());
					map.put("groupSn", LAN_CANCEL_ACT_CODE);
					String paramJson = JsonEntityTransform.Object2Json(map);
					String groupCouponJson = mobileCouponService.pullGroupCouponByUid(paramJson);
					DataTransferObject groupCouponDto  = JsonEntityTransform.json2DataTransferObject(groupCouponJson);
					if(groupCouponDto.getCode()==DataTransferObject.SUCCESS){
						cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001007.getValue(), YesOrNoEnum.YES.getStr());
					}
				}
			}
            
			//当所有的措施都被执行成功==》修改t_order_csr_cancle表中的punish_statu
			String isDoneAllPunishJson = cancelOrderService.getIsDoneAllPunish(orderSn);
			DataTransferObject isDoneAllPunishDto = JsonEntityTransform.json2DataTransferObject(isDoneAllPunishJson);
			if(isDoneAllPunishDto.getCode()==DataTransferObject.SUCCESS){
				int result = (int) isDoneAllPunishDto.getData().get("isDoneAllPunish");
				if(result==YesOrNoEnum.YES.getCode()){//所有的惩罚措施都已经执行成功了
					cancelOrderService.updateOrderCsrCancle(orderSn, PunishedStatusEnum.DEALHAVEDONE.getCode());
				}
			}
		}
        //房东强制取消订单结束
		
		//取消发邮件
		if(dto.getCode()==DataTransferObject.SUCCESS){
			SendOrderEmailRequest orderEmailRequest=SOAResParseUtil.getValueFromDataByKey(resultJson, "orderEmail", SendOrderEmailRequest.class);
			if(!Check.NuNObj(orderEmailRequest)){
				SendEmailThreadPool.execute(new SendOrderEmailThread(orderEmailRequest,customerMsgManagerService,smsTemplateService));
			}
		}
		return dto;
	}
	


	/**
	 * 查询协商取消详情
	 * @author lishaochuan
	 * @create 2017/1/10 10:57
	 * @param
	 * @return
	 */
	@RequestMapping("/showCancleOrderInfo")
	public String showCancleOrderInfo(HttpServletRequest request){
		String orderSn = request.getParameter("orderSn");
		String orderStatusParam = request.getParameter("orderStatusParam");
		Integer orderStatus = Integer.valueOf(orderStatusParam);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.cancelOrderService.showCancelOrderInfo(orderSn, orderStatus));
		if(dto.getCode() == DataTransferObject.SUCCESS){
			ShowCancelOrderResponse response = dto.parseData("response", new TypeReference<ShowCancelOrderResponse>() {});
			List<Map> logs = this.transLog(response.getOrderLogs());

			request.setAttribute("response", response);
			request.setAttribute("logs", logs);
			request.setAttribute("orderStatus", orderStatus);
		}
		return "/order/showCancleOrderInfo";
	}
	
	/**
	 * 
	 * 某个时间点==》向前或向后推几个月的时间点
	 *
	 * @author loushuai
	 * @created 2017年5月10日 下午8:26:26
	 *
	 * @param date
	 * @param i 为负数时代表获取之前的日期     为正数,代表获取之后的日期
	 * @return
	 */
	public Date getNowAroundMonths(Date date, Integer i){
		if(!Check.NuNObj(date) && !Check.NuNObj(i)){
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH,i);
			Date beforeMonthsDate=calendar.getTime();
			return beforeMonthsDate;
		}
		return null;
	}
}
