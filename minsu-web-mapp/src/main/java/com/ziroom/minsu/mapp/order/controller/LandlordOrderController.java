package com.ziroom.minsu.mapp.order.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.evaluate.StatsTenantEvaEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderRemarkEntity;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.mapp.common.abs.AbstractController;
import com.ziroom.minsu.mapp.common.logic.ParamCheckLogic;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.mapp.common.util.DateUtils;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.common.utils.CheckIdCardUtils;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.StatsTenantEvaRequest;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderLoadlordService;
import com.ziroom.minsu.services.order.api.inner.OrderRemarkService;
import com.ziroom.minsu.services.order.dto.LoadlordRequest;
import com.ziroom.minsu.services.order.dto.OrderDetailRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.dto.RemarkRequest;
import com.ziroom.minsu.services.order.entity.FinancePenaltyPayRelVo;
import com.ziroom.minsu.services.order.entity.LandlordOrderDetailVo;
import com.ziroom.minsu.services.order.entity.LandlordOrderItemVo;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.UsualContactVo;
import com.ziroom.minsu.valenum.customer.CustomerEduEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import com.ziroom.minsu.valenum.order.OrderParamEnum;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;

/**
 * <p>房东订单管理</p>
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
@RequestMapping("/orderland")
@Controller
public class LandlordOrderController extends AbstractController{

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(LandlordOrderController.class);

	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;

	@Resource(name="order.orderLoadlordService")
	private OrderLoadlordService orderLoadlordService;

	@Resource(name="evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name="mapp.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;

	@Value("#{'${default_head_size}'.trim()}")
	private String default_head_size;

	@Value(value = "${IM_TOMCAT_URL}")
	private String IM_TOMCAT_URL;

	@Resource(name = "cms.actCouponService")
	private ActCouponService actCouponService;

	@Resource(name = "order.orderRemarkService")
	private OrderRemarkService orderRemarkService;

	@Resource(name ="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	/**
	 *
	 * 订单列表展示
	 *
	 * @author jixd
	 * @created 2016年5月15日 下午12:31:19
	 *
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/showlist")
	public String showList(HttpServletRequest request){
		request.setAttribute("menuType", "order");
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		request.setAttribute("landlordUid", customerVo.getUid());
		return "order/orderlist";
	}
	/**
	 *
	 * 房东端订单列表显示
	 * 说明：lanOrderType (1=待处理  2=进行中  3=已处理)
	 * 参数：{"landlordUid":"sssddd","lanOrderType":1}
	 * @author jixd
	 * @created 2016年5月3日 上午11:17:44
	 *
	 * @param oRequest
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/dataList")
	@ResponseBody
	public DataTransferObject queryLandlordOrder(HttpServletRequest request,OrderRequest oRequest){
		DataTransferObject dto = new DataTransferObject();
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		oRequest.setLandlordUid(customerVo.getUid());
		try{
			oRequest.setLimit(5);
			if(Check.NuNStr(oRequest.getLandlordUid())){
				dto.setErrCode(1);
				dto.setMsg("房东UID不能为空");
				return dto;
			}
			//房东请求
			oRequest.setRequestType(2);
			if(Check.NuNObj(oRequest.getLanOrderType())){
				//房东端待处理
				oRequest.setLanOrderType(1);
			}
			String resultJson=orderCommonService.getOrderList(JsonEntityTransform.Object2Json(oRequest));
			LogUtil.debug(LOGGER, "结果："+resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<OrderInfoVo> orderInfoList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);
			List<LandlordOrderItemVo> orderList = getListLandlordOrderVo(orderInfoList);
			dto.putValue("orderHouseList", orderList);
			return dto;
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("服务错误");
		}
		return dto;
	}
	/**
	 *
	 * 显示订单详情
	 * 参数说明
	 *
	 * @author jixd
	 * @created 2016年5月3日 下午4:40:42
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/showDetail")
	public String showLandlordOrderDetail(HttpServletRequest request,Model model,OrderDetailRequest orderDetailRequest){
		LogUtil.info(LOGGER, "showLandlordOrderDetail param:{}",JsonEntityTransform.Object2Json(orderDetailRequest));
		try{
			String flagStr = request.getParameter("flag");
			if(!Check.NuNObj(flagStr)){
				int flag = Integer.valueOf(flagStr);
				model.addAttribute("flag", flag);
			}

			request.setAttribute("imUrl", IM_TOMCAT_URL);

			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			orderDetailRequest.setLandlordUid(customerVo.getUid());
			orderDetailRequest.setRequestType(2);
			String resultJson = orderCommonService.queryOrderInfoBySn(JsonEntityTransform.Object2Json(orderDetailRequest));
			LogUtil.debug(LOGGER, "showLandlordOrderDetail 结果：{}"+resultJson);
			OrderDetailVo orderDetailVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "orderDetailVo", OrderDetailVo.class);
			if(Check.NuNObj(orderDetailVo)){
				return "error/error";
			}
			String userUid = orderDetailVo.getUserUid();

			//重新封装的房东端订单详情对象
			LandlordOrderDetailVo landOrderDetail = getLandOrderDetail(orderDetailVo);

			//请求预订人评价服务
			StatsTenantEvaRequest evaRequest = new StatsTenantEvaRequest();
			evaRequest.setTenantUid(userUid);
			String evaJson = evaluateOrderService.queryStatsTenantEvaByCondion(JsonEntityTransform.Object2Json(evaRequest));
			DataTransferObject evaDto = JsonEntityTransform.json2DataTransferObject(evaJson);
			if(evaDto.getCode() == DataTransferObject.SUCCESS){
				List<StatsTenantEvaEntity> evaList = SOAResParseUtil.getListValueFromDataByKey(evaJson, "lisTenantEvaEntities", StatsTenantEvaEntity.class);
				//如果没有评价 默认为0
				if(evaList.size() >0){
					StatsTenantEvaEntity statsTenantEvaEntity = evaList.get(0);
					//评价数量
					landOrderDetail.setEvaTotal(statsTenantEvaEntity.getEvaTotal());
					//评价值
					landOrderDetail.setLandSatisfAva(statsTenantEvaEntity.getLandSatisfAva());
				}

			}

			//计算星级
			float satisfava = landOrderDetail.getLandSatisfAva() * 2;
			int satisStar = Math.round(satisfava);
			int halfStar =  satisStar/2;
			model.addAttribute("satisStar", satisStar);
			model.addAttribute("halfStar", halfStar);
			//获取预订人头像
			CustomerPicDto picDto = new CustomerPicDto();
			picDto.setUid(landOrderDetail.getUserUid());
			picDto.setPicType(CustomerPicTypeEnum.YHTX.getCode());
			String customerPic = customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picDto));
			DataTransferObject customerPicDto = JsonEntityTransform.json2DataTransferObject(customerPic);
			if(customerPicDto.getCode() == DataTransferObject.SUCCESS){
				CustomerPicMsgEntity picEntity = customerPicDto.parseData("customerPicMsgEntity",new TypeReference<CustomerPicMsgEntity>() {});
				if(!Check.NuNObj(picEntity)){
					model.addAttribute("userPicUrl", PicUtil.getFullPic(picBaseAddrMona, picEntity.getPicBaseUrl(), picEntity.getPicSuffix(), default_head_size));
				}
			}
			model.addAttribute("detail", landOrderDetail);

			//订单备注
			RemarkRequest remarkRequest = new RemarkRequest();
			remarkRequest.setOrderSn(orderDetailRequest.getOrderSn());
			remarkRequest.setUid(customerVo.getUid());
			String remarkResult = orderRemarkService.getRemarkList(JsonEntityTransform.Object2Json(remarkRequest));
			DataTransferObject remarkDto = JsonEntityTransform.json2DataTransferObject(remarkResult);
			if(remarkDto.getCode() == DataTransferObject.SUCCESS){
				List<OrderRemarkEntity> remarkList = SOAResParseUtil.getListValueFromDataByKey(remarkResult, "list", OrderRemarkEntity.class);
				model.addAttribute("remarkList", remarkList);
			}

		}catch(Exception e){
			LogUtil.error(LOGGER, "showLandlordOrderDetail param:{},error:{}",JsonEntityTransform.Object2Json(orderDetailRequest), e);
		}
		return "order/orderDetail";
	}


	/**
	 *
	 * 房东确认订单
	 * 参数{"orderSn":"12222","landlordUid":"112122"}
	 * @author jixd
	 * @created 2016年5月1日 上午10:20:44
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/acceptOrder")
	@ResponseBody
	public DataTransferObject acceptOrder(HttpServletRequest request,LoadlordRequest loadlordRequest){
		DataTransferObject dto = new DataTransferObject();
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		loadlordRequest.setLandlordUid(customerVo.getUid());

		try{
			if(Check.NuNStr(loadlordRequest.getOrderSn())){
				dto.setErrCode(1);
				dto.setMsg("订单号为空");
				return dto;
			}
			if(Check.NuNStr(loadlordRequest.getLandlordUid())){
				dto.setErrCode(1);
				dto.setMsg("房东ID为空");
				return dto;
			}
			//接受订单，更新到待入住状态
			loadlordRequest.setOrderStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
			loadlordRequest.setCountryCode(customerVo.getCountryCode());

			String resultJson = orderLoadlordService.acceptOrder(JsonEntityTransform.Object2Json(loadlordRequest));
			LogUtil.info(LOGGER, "结果："+resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 *
	 * 房东拒绝订单
	 * 参数{"orderSn":"12222","landlordUid":"112122","paramValue":"原因"}
	 *
	 * @author jixd
	 * @created 2016年5月3日 下午5:47:37
	 *
	 * @param loadlordRequest
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/refusedOrder")
	@ResponseBody
	public DataTransferObject refusedOrder(HttpServletRequest request,LoadlordRequest loadlordRequest){
		DataTransferObject dto = new DataTransferObject();
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		loadlordRequest.setLandlordUid(customerVo.getUid());

		try{
			if(Check.NuNStr(loadlordRequest.getOrderSn())){
				dto.setErrCode(1);
				dto.setMsg("订单号为空");
				return dto;
			}
			if(Check.NuNStr(loadlordRequest.getLandlordUid())){
				dto.setErrCode(1);
				dto.setMsg("房东ID为空");
				return dto;
			}

			if(Check.NuNStr(String.valueOf(loadlordRequest.getRefuseCode()))){
				dto.setErrCode(1);
				dto.setMsg("未选择拒绝原因");
				return dto;
			}

			if("50".equals(loadlordRequest.getRefuseCode()) && Check.NuNStr(loadlordRequest.getRefuseReason())){
				dto.setErrCode(1);
				dto.setMsg("请填写具体的拒绝原因");
				return dto;
			}

			if(!"50".equals(loadlordRequest.getRefuseCode())){
				EnumVo eVo = this.getNodeByCode(String.valueOf(loadlordRequest.getRefuseCode()));
				loadlordRequest.setRefuseReason(eVo.getText());
			}

			//拒绝订单,更新订单状态
			loadlordRequest.setOrderStatus(OrderStatusEnum.REFUSED.getOrderStatus());
			String paramStr = JsonEntityTransform.Object2Json(loadlordRequest);
			String resultJson = orderLoadlordService.refusedOrder(paramStr);
			LogUtil.info(LOGGER, "结果："+resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.SUCCESS){
				String couponSn = dto.parseData("couponSn", new TypeReference<String>() {});
				if (!Check.NuNStr(couponSn)){
					//拒绝成功，释放优惠券 不管成功还是失败，失败会有同步机制
					List<OrderActivityEntity> orderActList = new ArrayList<OrderActivityEntity>();
					OrderActivityEntity orderAct = new OrderActivityEntity();
					orderAct.setAcFid(couponSn);
					orderAct.setAcStatus(CouponStatusEnum.GET.getCode());
					orderActList.add(orderAct);

					//不管是否处理成功
					actCouponService.syncCouponStatus(JsonEntityTransform.Object2Json(orderActList));
				}
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 *
	 * 房东确认额外消费
	 * 参数说明：参数值 paramValue otherMoney 额外消费状态
	 * {"landlordUid":"123232","otherMoney":120,"orderSn":"122333555","paramValue":""}
	 *
	 * @author jixd
	 * @created 2016年5月1日 上午11:09:50
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/confirmOtherMoney")
	@ResponseBody
	public DataTransferObject confirmOtherMoney(HttpServletRequest request,LoadlordRequest loadlordRequest){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(loadlordRequest.getOrderSn())){
				dto.setErrCode(1);
				dto.setMsg("订单号为空");
				return dto;
			}
			/*获取额外消费限制*/
			int otherMoney = loadlordRequest.getOtherMoney() * 100;
			String otherParam = loadlordRequest.getParamValue();
			//原因限制
			if(otherMoney > 0 && otherParam.length() > 150){
				dto.setErrCode(1);
				dto.setMsg("最大字数不超过150字");
				return dto;
			}

			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			loadlordRequest.setLandlordUid(customerVo.getUid());
			String loadlordRequestStr =  JsonEntityTransform.Object2Json(loadlordRequest);
			String moneyLimitJson = orderLoadlordService.getOtherMoneyLimit(loadlordRequestStr);
			DataTransferObject limitJsonDto = JsonEntityTransform.json2DataTransferObject(moneyLimitJson);
			int limit = 0 ;
			if(limitJsonDto.getCode() == DataTransferObject.SUCCESS){
				limit = (int) limitJsonDto.getData().get("otherMoneyLimit");
			}


			if(otherMoney > 0 && otherMoney > limit){
				dto.setErrCode(2);
				dto.setMsg("最大限额" + getPriceFormat(limit) + "元");
				return dto;
			}

			//更改为确认额外消费状态
			loadlordRequest.setOrderStatus(OrderStatusEnum.WAITING_EXT.getOrderStatus());
			//输入金额 元   存入分
			loadlordRequest.setOtherMoney(otherMoney);
			String resultJson = orderLoadlordService.saveOtherMoney(JsonEntityTransform.Object2Json(loadlordRequest));
			LogUtil.info(LOGGER, "结果："+resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("操作失败");
		}
		return dto;
	}

	/**
	 *
	 * 显示入住人列表
	 *
	 * @author jixd
	 * @created 2016年5月11日 下午1:44:32
	 *
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/contactlist")
	public String showContactList(HttpServletRequest request,Model model,String orderSn){
		LogUtil.info(LOGGER, "showContactList orderSn={}", orderSn);
		try{
			if(!Check.NuNStr(orderSn)){

				//判断是否显示联系方式
				String orderStatusStr = request.getParameter("orderStatus");
				String payStatusStr = request.getParameter("payStatus");
				String checkOutTime = request.getParameter("checkOutTime");
				boolean isShowMes = this.isShowPhone(orderStatusStr,payStatusStr,checkOutTime);
				model.addAttribute("isShowMes", isShowMes);

				String resultJson = orderCommonService.findOrderContactsByOrderSn(orderSn);

				LogUtil.debug(LOGGER, "showContactList resultJson={}", resultJson);

				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(dto.getCode() == DataTransferObject.SUCCESS){
					List<UsualContactEntity> contactList = dto.parseData("listUsualContact", new TypeReference<List<UsualContactEntity>>() {});
					List<UsualContactVo> listVo = new ArrayList<UsualContactVo>();
					if(!Check.NuNCollection(contactList)){
						for(UsualContactEntity contact : contactList){
							UsualContactVo contactVo  = new UsualContactVo();
							BeanUtils.copyProperties(contactVo, contact);
							contactVo.setConTel(contact.getConTel());
							if(contactVo.getCardType() == CustomerIdTypeEnum.ID.getCode()){
								if(!Check.NuNStr(contact.getCardValue())){
									contactVo.setConAge(CheckIdCardUtils.getAgeByIdCard(contact.getCardValue()));
								}
							}
							listVo.add(contactVo);
						}
					}
					model.addAttribute("contactList", listVo);
				}
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "showContactList orderSn:{},error:{}", orderSn,e);
		}
		return "order/contactlist";
	}
	/**
	 * 判断是否显示联系方式
	 * @author liyingjie
	 * @created 2016年5月11日 下午2:54:16
	 * @return
	 */
	private boolean isShowPhone(String orderStatusStr,String payStatusStr,String checkOutTimeStr){
		boolean result = false;
		if(Check.NuNStr(orderStatusStr)||Check.NuNStr(payStatusStr)){
			return result;
		}

		int orderStatus = Integer.valueOf(orderStatusStr);
		int payStatus = Integer.valueOf(payStatusStr);

		if(orderStatus == 0 || payStatus == OrderPayStatusEnum.UN_PAY.getPayStatus()){
			return result;
		}

		if(orderStatus == OrderStatusEnum.FINISH_COMMON.getOrderStatus() || orderStatus == OrderStatusEnum.FINISH_PRE.getOrderStatus()){
			//判断时间
			if(Check.NuNObj(checkOutTimeStr)){
				result = false;
				return result;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date checkOutTime = null;
			try {
				checkOutTime = sdf.parse(checkOutTimeStr);
			} catch (ParseException e) {
				LogUtil.error(LOGGER, "isShowPhone checkOutTime={}", checkOutTimeStr);
				return result;
			}

			if(DateUtil.getDatebetweenOfDayNum(checkOutTime, new Date()) > 7){
				result = false;
			}else {
				result = true;
			}
		}

		if (orderStatus == OrderStatusEnum.CHECKED_IN.getOrderStatus()
				|| orderStatus == OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()
				|| orderStatus == OrderStatusEnum.CHECKING_OUT.getOrderStatus()
				||orderStatus == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()
				||orderStatus == OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus()
				||orderStatus == OrderStatusEnum.WAITING_EXT.getOrderStatus()
				||orderStatus == OrderStatusEnum.WAITING_EXT_PRE.getOrderStatus()) {
			result = true;
		}

		return result;
	}
	/**
	 *
	 * 查询预订人信息,及订单评价
	 *
	 * @author jixd
	 * @created 2016年5月11日 下午2:54:16
	 *
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/bookingDetail")
	public String showBookingDetail(HttpServletRequest request,Model model,String orderSn,String userUid){
		LogUtil.info(LOGGER, "查询预定人信息userUid={},orderSn={}", userUid,orderSn);

		try {
			//判断是否显示联系方式
			String orderStatusStr = request.getParameter("orderStatus");
			String payStatusStr = request.getParameter("payStatus");
			String checkOutTime = request.getParameter("checkOutTime");
			boolean isShowMes = this.isShowPhone(orderStatusStr,payStatusStr,checkOutTime);
			model.addAttribute("isShowMes", isShowMes);

			String customerJson = customerMsgManagerService.getCutomerVo(userUid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
			if(customerDto.getCode() == DataTransferObject.SUCCESS){
				CustomerVo detailVo = customerDto.parseData("customerVo", new TypeReference<CustomerVo>() {
				});

				if(!Check.NuNObj(detailVo)){
					LogUtil.info(LOGGER, "查询预定人信息userUid={},返回detailVo={}",userUid,JsonEntityTransform.Object2Json(detailVo));
					if(Check.NuNStr(detailVo.getCityCode())){
						model.addAttribute("cityName", "");
					}else{
						String cityJson = confCityService.getCityNameByCode(detailVo.getCityCode());
						DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
						String cityName = (String) cityDto.getData().get("cityName");
						model.addAttribute("cityName", cityName);

					}

					if("".equals(detailVo.getCustomerJob())){
						detailVo.setCustomerJob("未设置");
					}

					model.addAttribute("customerVo", detailVo);
					model.addAttribute("orderSn", orderSn);
					CustomerEduEnum customerEduEnum =  null;
					if (!Check.NuNObj(detailVo.getCustomerEdu())){
						customerEduEnum = CustomerEduEnum.getCustomerEduByCode(detailVo.getCustomerEdu());
					}
					if(customerEduEnum!=null){
						model.addAttribute("education",customerEduEnum.getName());
					}else{
						model.addAttribute("education","");
					}
				}else{
					detailVo = new CustomerVo();
				}

			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取用户信息异常e={}", e);
		}

		return "order/contactDetail";
	}


	/**
	 *
	 * 封装房客端订单列表返回数据
	 *
	 * @author jixd
	 * @created 2016年5月9日 下午3:25:04
	 *
	 * @param landlordOrderList
	 * @return
	 */
	private List<LandlordOrderItemVo> getListLandlordOrderVo(List<OrderInfoVo> landlordOrderList){

		List<LandlordOrderItemVo> listOrderVos = new ArrayList<LandlordOrderItemVo>();
		if(!Check.NuNCollection(landlordOrderList)){
			for (OrderInfoVo orderInfoVo : landlordOrderList) {
				LandlordOrderItemVo itemVo =  new LandlordOrderItemVo();
				itemVo.setOrderSn(orderInfoVo.getOrderSn());
				itemVo.setContactsNum(orderInfoVo.getPeopleNum());
				itemVo.setStartTimeStr(orderInfoVo.getStartTimeStr());
				itemVo.setEndTimeStr(orderInfoVo.getEndTimeStr());
				itemVo.setHouseAddr(orderInfoVo.getHouseAddr());
				//根据整租还是合租显示房源名称
				int rentWay = orderInfoVo.getRentWay();
				itemVo.setRentWay(rentWay);
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					itemVo.setHouseName(orderInfoVo.getHouseName());
				}else if(rentWay == RentWayEnum.ROOM.getCode()){
					itemVo.setHouseName(orderInfoVo.getRoomName());
				}else if(rentWay == RentWayEnum.BED.getCode()){
					itemVo.setHouseName(orderInfoVo.getRoomName());
				}else{
					itemVo.setHouseName(orderInfoVo.getHouseName());
				}

				itemVo.setOrderStatus(orderInfoVo.getOrderStatus());
				itemVo.setHousingDay(orderInfoVo.getPeopleNum());
				//显示订单状态名称
				OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderInfoVo.getOrderStatus());
				if(!Check.NuNObj(orderStatusEnum)){
					itemVo.setOrderStatusShowName(orderStatusEnum.getShowName(orderInfoVo));
				}

				itemVo.setUserUid(orderInfoVo.getUserUid());
				itemVo.setLandlordUid(orderInfoVo.getLandlordUid());
				double money = BigDecimalUtil.div(orderInfoVo.getNeedPay() + orderInfoVo.getCouponMoney()+orderInfoVo.getActMoney(),100);
				itemVo.setNeedMoney(money);
				itemVo.setUserName(orderInfoVo.getUserName());
				//房源照片
				itemVo.setHousePicUrl(PicUtil.getSpecialPic(picBaseAddrMona, orderInfoVo.getPicUrl(), default_head_size));
				int evaStatus = orderInfoVo.getEvaStatus();
				itemVo.setEvaStatus(evaStatus);
				//如果是待评价 和房客已评价 则显示 去评价
				/*if(evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode()){
					itemVo.setEvaStatus(0);
				}else{
					itemVo.setEvaStatus(1);
				}*/
				listOrderVos.add(itemVo);
			}
		}
		return listOrderVos;
	}

	/**
	 *
	 * 重新组装处理数据
	 *
	 * @author jixd
	 * @created 2016年5月4日 下午2:03:45
	 *
	 * @param orderDetailVo
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private LandlordOrderDetailVo getLandOrderDetail(OrderDetailVo orderDetailVo) throws IllegalAccessException, InvocationTargetException{
		LandlordOrderDetailVo vo = new LandlordOrderDetailVo();
		vo.setBedFid(orderDetailVo.getBedFid());
		vo.setCreateTime(DateUtil.dateFormat(orderDetailVo.getCreateTime()));
		//设置实际退房时间
		if(!Check.NuNObj(orderDetailVo.getRealEndTime())){
			vo.setCheckOutTime(DateUtil.dateFormat(orderDetailVo.getRealEndTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		vo.setDepositMoney(getPriceFormat(orderDetailVo.getDepositMoney()));
		vo.setDiscountMoney(getPriceFormat(orderDetailVo.getDiscountMoney()));
		vo.setEvaTotal(0);
		vo.setHouseFid(orderDetailVo.getHouseFid());
		//房源图片
		vo.setHousePicUrl(PicUtil.getSpecialPic(picBaseAddrMona, orderDetailVo.getPicUrl(), detail_big_pic));
		//支付状态
		vo.setPayStatus(orderDetailVo.getPayStatus());

		//实际结束时间
		if(Check.NuNObj(orderDetailVo.getRealEndTime())){
			//押金
			vo.setDepositMoney(getPriceFormat(orderDetailVo.getDepositMoney()));
		}else{
			vo.setDepositMoney(getPriceFormat(0));
		}

		vo.setLandlordName(orderDetailVo.getLandlordName());
		vo.setLandlordTel(orderDetailVo.getLandlordTel());
		vo.setLandlordUid(orderDetailVo.getLandlordUid());
		vo.setLandSatisfAva(0.0f);
		vo.setOrderSn(orderDetailVo.getOrderSn());
		vo.setOrderStatus(orderDetailVo.getOrderStatus());

		//根据整租还是合租显示房源名称
		int rentWay = orderDetailVo.getRentWay();
		//出租方式 0=整租  1=合租
		vo.setRentWay(rentWay);
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			vo.setHouseName(orderDetailVo.getHouseName());
		}else if(rentWay == RentWayEnum.ROOM.getCode()){
			vo.setHouseName(orderDetailVo.getRoomName());
		}else if(rentWay == RentWayEnum.BED.getCode()){
			vo.setHouseName(orderDetailVo.getRoomName());
		}else{
			vo.setHouseName(orderDetailVo.getHouseName());
		}

		//房间id
		vo.setRoomFid(orderDetailVo.getRoomFid());
		//房间名称
		vo.setRoomName(orderDetailVo.getRoomName());
		//订单状态
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(vo.getOrderStatus());
		if(!Check.NuNObj(orderStatusEnum)){
			vo.setOrderStatusName(orderStatusEnum.getShowName(orderDetailVo));
		}


		//退款金额
		vo.setRefundMoney(getPriceFormat(orderDetailVo.getRefundMoney()));
		//额外消费
		vo.setOtherMoney(getPriceFormat(orderDetailVo.getOtherMoney()));
		//违约金
		vo.setPenaltyMoney(getPriceFormat(orderDetailVo.getPenaltyMoney()));
		//清洁费
		vo.setCleanMoney(getPriceFormat(orderDetailVo.getCleanMoney()));
		//房租 优惠前金额
		vo.setRentalMoney(getPriceFormat(orderDetailVo.getRentalMoney()));
		//折扣前金额
		vo.setBfRentalMoney(getPriceFormat(orderDetailVo.getRentalMoney()));
		//当前状态下 房客的佣金
		int userCommMoney = orderStatusEnum.getComm(orderDetailVo.getUserCommMoney(),orderDetailVo.getRealUserMoney(),orderDetailVo);
		//当前状态下房东的佣金
		int lanCommMoney = orderStatusEnum.getComm(orderDetailVo.getLanCommMoney(),orderDetailVo.getRealLanMoney(),orderDetailVo);

		//收入 = 房租+清洁费+额外消费+违约金-房东佣金-房租折扣
		Integer income = orderDetailVo.getRentalMoney()  + orderDetailVo.getCleanMoney() + orderDetailVo.getOtherMoney() + orderDetailVo.getPenaltyMoney()
				-lanCommMoney-orderDetailVo.getDiscountMoney();
		//房东端收入
		vo.setLandlordIncomeMoney(getPriceFormat(income));
		//预计收入还是实际收入
		vo.setIncomeName(orderStatusEnum.getIncomeName(orderDetailVo));
		//获取当前状态房客的佣金
		vo.setUserCommMoney(getPriceFormat(userCommMoney));
		//获取当前状态房东的佣金
		vo.setLanCommMoney(getPriceFormat(lanCommMoney));
		//房客支付
		vo.setTenantNeedPay(getPriceFormat(orderDetailVo.getNeedPay() + orderDetailVo.getCouponMoney()+orderDetailVo.getActMoney()));
		//开始时间
		vo.setStartTime(DateUtils.getDateStr(orderDetailVo.getStartTime(),orderDetailVo.getCheckInTime()));
		//结束时间
		vo.setEndTime(DateUtils.getDateStr(orderDetailVo.getEndTime(),orderDetailVo.getCheckOutTime()));
		vo.setUserTel(orderDetailVo.getUserTel());
		vo.setUserUid(orderDetailVo.getUserUid());
		vo.setUserName(orderDetailVo.getUserName());
		//入住人列表
		vo.setContactsNum(orderDetailVo.getPeopleNum());
		vo.setContactList(orderDetailVo.getListUsualContactEntities());
		Map<String, Object>  paramMap = orderDetailVo.getParamMap();
		//额外消费描述
		vo.setOtherMoneyDes((String)paramMap.get(OrderParamEnum.OTHER_COST_DES.getCode()));
		//出行目地
		vo.setTripPurpose(orderDetailVo.getTripPurpose());
		
		//房东罚款信息
		if(!Check.NuNObj(orderDetailVo.getFinancePenalty())){
			BeanUtils.copyProperties( vo.getFinancePenaltyVo(),orderDetailVo.getFinancePenalty());
		}
		//被罚详情
		if(!Check.NuNObj(orderDetailVo.getListFinancePenaltyPayRelVo())){
			vo.setListFinancePenaltyPayRelVo(orderDetailVo.getListFinancePenaltyPayRelVo());
			int orderPenaltySumMoney = 0;
			for (FinancePenaltyPayRelVo financePenaltyPayRelVo : orderDetailVo.getListFinancePenaltyPayRelVo() ){
				orderPenaltySumMoney +=financePenaltyPayRelVo.getTotalFee();
			}
			vo.setOrderPenaltySumMoney(getPriceFormat(orderPenaltySumMoney));
		}
		return vo;
	}

	private static String getPriceFormat(int price) {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,##0.00");

		double priceD = price / 100.00;
		return myformat.format(priceD);
	}



	/**
	 * 校验订单备注数量
	 * @author lishaochuan
	 * @create 2016年6月27日下午2:00:01
	 * @param request
	 * @param orderSn
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/checkRemarkCount")
	@ResponseBody
	public DataTransferObject checkRemarkCount(HttpServletRequest request,String orderSn){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(orderSn)){
				dto.setErrCode(1);
				dto.setMsg("订单号为空");
				return dto;
			}

			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			RemarkRequest remarkRequest = new RemarkRequest();
			remarkRequest.setOrderSn(orderSn);
			remarkRequest.setUid(customerVo.getUid());
			String resultJson = orderRemarkService.getRemarkCount(JsonEntityTransform.Object2Json(remarkRequest));
			LogUtil.info(LOGGER, "结果："+resultJson);

			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("操作失败");
		}
		return dto;
	}



	/**
	 * 跳转订单备注页面
	 * @author lishaochuan
	 * @create 2016年6月26日下午6:32:02
	 * @param request
	 * @param orderSn
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/toOrderRemark")
	public String toOrderRemark(HttpServletRequest request,String orderSn){
		request.setAttribute("orderSn", orderSn);
		return "order/orderRemark";
	}


	/**
	 * app跳转到添加订单备注
	 * @author afi
	 * @param request
	 * @param orderSn
     * @return
     */
	@RequestMapping("${LOGIN_UNAUTH}/toOrderRemarkApp")
	public String toOrderRemarkApp(HttpServletRequest request,String orderSn){
		request.setAttribute("orderSn", orderSn);
		return "order/orderRemarkApp";
	}

	/**
	 * 添加订单备注
	 * @author lishaochuan
	 * @create 2016年6月26日下午6:38:38
	 * @param request
	 * @param orderSn
	 * @param remark
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/saveOrderRemark")
	@ResponseBody
	public DataTransferObject saveOrderRemark(HttpServletRequest request,String orderSn, String remark){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(orderSn)){
				dto.setErrCode(1);
				dto.setMsg("订单号为空");
				return dto;
			}
			if(Check.NuNStr(remark)){
				dto.setErrCode(1);
				dto.setMsg("备注为空");
				return dto;
			}
			if(remark.length()>60){
				dto.setErrCode(1);
				dto.setMsg("备注过长");
				return dto;
			}

			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			OrderRemarkEntity remarkEntity = new OrderRemarkEntity();
			remarkEntity.setOrderSn(orderSn);
			remarkEntity.setRemarkContent(remark);
			remarkEntity.setUid(customerVo.getUid());
			String resultJson = orderRemarkService.insertRemarkRes(JsonEntityTransform.Object2Json(remarkEntity));
			LogUtil.info(LOGGER, "结果：" + resultJson);

			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("操作失败");
		}
		return dto;
	}


	/**
	 * 删除订单备注
	 * @author lishaochuan
	 * @create 2016年6月27日下午8:47:16
	 * @param request
	 * @param fid
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/delRemark")
	@ResponseBody
	public DataTransferObject delRemark(HttpServletRequest request,String fid){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(fid)){
				dto.setErrCode(1);
				dto.setMsg("fid为空");
				return dto;
			}

			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			RemarkRequest remarkRequest = new RemarkRequest();
			remarkRequest.setFid(fid);
			remarkRequest.setUid(customerVo.getUid());
			String resultJson = orderRemarkService.delRemark(JsonEntityTransform.Object2Json(remarkRequest));
			LogUtil.info(LOGGER, "结果：" + resultJson);

			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("操作失败");
		}
		return dto;
	}

	/**
	 * 获取选择的项
	 * @author liyingjie
	 * @create 2016年6月26日下午6:32:02
	 * @return
	 */
	private EnumVo getNodeByCode(String code){
		EnumVo result = new EnumVo();
		if(Check.NuNStr(code)){
			return result;
		}
		String houseTypeJson = cityTemplateService.getSelectEnum(null, TradeRulesEnum.TradeRulesEnum0018.getValue());
		try {
			List<EnumVo> nodeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
			if(Check.NuNCollection(nodeList)){
				return result;
			}
			for(EnumVo eVo : nodeList){
				if(code.equals(eVo.getKey())){
					result = eVo;
					break;
				}
			}
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "getNodeByCode error:{}", e);
		}
		return result;
	}

	/**
	 * 到拒绝订单页面
	 * @author liyingjie
	 * @create 2016年6月26日下午6:32:02
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/toRefuseOrder")
	public String toRefuseOrder(HttpServletRequest request){
		String orderSn = request.getParameter("orderSn");
		OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
		orderDetailRequest.setRequestType(2);
		orderDetailRequest.setOrderSn(orderSn);
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		orderDetailRequest.setLandlordUid(customerVo.getUid());
		try {
			String resultJson = orderCommonService.queryOrderInfoBySn(JsonEntityTransform.Object2Json(orderDetailRequest));
			LogUtil.debug(LOGGER, "showLandlordOrderDetail 结果：{}"+resultJson);
			OrderDetailVo orderDetailVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "orderDetailVo", OrderDetailVo.class);
			if(!Check.NuNObj(orderDetailVo)){
				//重新封装的房东端订单详情对象
				LandlordOrderDetailVo landOrderDetail = getRefuseInfoDetail(orderDetailVo);
				request.setAttribute("orderDetail", landOrderDetail);
			}else{
				request.setAttribute("orderDetail", new LandlordOrderDetailVo());
			}

			String houseTypeJson = cityTemplateService.getSelectEnum(null, TradeRulesEnum.TradeRulesEnum0018.getValue());
			List<EnumVo> nodeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
			request.setAttribute("nodeList", nodeList);

		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "toRefuseOrder error:{},params:{}", e,JsonEntityTransform.Object2Json(orderDetailRequest));
		}
		request.setAttribute("orderSn", orderSn);
		return "order/refuseOrder";
	}


	/**
	 * 重新组装处理数据
	 * @author liyingjie
	 * @return
	 */
	private LandlordOrderDetailVo getRefuseInfoDetail(OrderDetailVo orderDetailVo){
		LandlordOrderDetailVo vo = new LandlordOrderDetailVo();
		vo.setBedFid(orderDetailVo.getBedFid());
		vo.setCreateTime(DateUtil.dateFormat(orderDetailVo.getCreateTime()));
		vo.setHouseFid(orderDetailVo.getHouseFid());
		//房源图片
		vo.setHousePicUrl(PicUtil.getSpecialPic(picBaseAddrMona, orderDetailVo.getPicUrl(), detail_big_pic));
		vo.setOrderSn(orderDetailVo.getOrderSn());
		vo.setOrderStatus(orderDetailVo.getOrderStatus());

		//根据整租还是合租显示房源名称
		int rentWay = orderDetailVo.getRentWay();
		//出租方式 0=整租  1=合租
		vo.setRentWay(rentWay);
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			vo.setHouseName(orderDetailVo.getHouseName());
			vo.setRentWayName(RentWayEnum.HOUSE.getName());
		}else if(rentWay == RentWayEnum.ROOM.getCode()){
			vo.setHouseName(orderDetailVo.getRoomName());
			vo.setRentWayName(RentWayEnum.ROOM.getName());
		}else if(rentWay == RentWayEnum.BED.getCode()){
			vo.setHouseName(orderDetailVo.getRoomName());
			vo.setRentWayName(RentWayEnum.BED.getName());
		}else{
			vo.setHouseName(orderDetailVo.getHouseName());
			vo.setRentWayName("未知");
		}

		//房间id
		vo.setRoomFid(orderDetailVo.getRoomFid());
		//房间名称
		vo.setRoomName(orderDetailVo.getRoomName());
		return vo;
	}

}
