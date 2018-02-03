/**
 * @FileName: OrderCommonServiceProxy.java
 * @Package com.ziroom.minsu.services.order.proxy
 * 
 * @author yd
 * @created 2016年4月5日 下午5:10:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.constant.OrderMessageConst;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.*;
import com.ziroom.minsu.services.order.service.*;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.evaluate.EvaStatusEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.traderules.CheckOutStrategy;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>订单公共接口代理</p>
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
@Component("order.orderCommonServiceProxy")
public class OrderCommonServiceProxy implements OrderCommonService{


	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderUserServiceProxy.class);
	@Resource(name = "order.messageSource")
	private MessageSource messageSource;
	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl ;
	@Resource(name = "order.orderUserServiceImpl")
	private OrderUserServiceImpl orderUserServiceImpl;
	@Resource(name = "order.orderLoadlordServiceImpl")
	private OrderLoadlordServiceImpl orderLoadlordServiceImpl;
	@Resource(name = "order.usualContactServiceImpl")
	private UsualContactServiceImpl usualContactServiceImpl;
	@Resource(name = "order.orderConfigServiceImpl")
	private OrderConfigServiceImpl orderConfigServiceImpl;

	@Resource(name = "order.orderPayServiceImpl")
	private OrderPayServiceImpl orderPayService;

    @Resource(name = "basedata.confCityService")
    private ConfCityService confCityService;

	@Resource(name = "order.orderEvalServiceImpl")
	private OrderEvalServiceImpl orderEvalServiceImpl ;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Resource(name = "order.orderActivityServiceImpl")
	private OrderActivityServiceImpl orderActivityServiceImpl;

	@Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;


	/**
	 * 获取当前用户待评价的订单数量
	 * @author afi
	 * @param userUid
	 * @return
	 */
	public String countWaitEvaNumAll(String userUid,int userType){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(userUid)){
			LogUtil.info(LOGGER,"请求参数userUid为空");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数userUid为空");
			return dto.toJsonString();
		}
		int limitDay = this.getEvalTimeDay();
		Long count = null;
		if (userType == UserTypeEnum.LANDLORD.getUserType()){
			count = orderEvalServiceImpl.countLandWaitEvaNumAll(userUid,limitDay);
		}else if (userType == UserTypeEnum.TENANT.getUserType()){
			count = orderEvalServiceImpl.countUserWaitEvaNumAll(userUid,limitDay);
		}else {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("异常的请求类型");
			return dto.toJsonString();
		}
		dto.putValue("count",count);
		return dto.toJsonString();
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
	 * 按订单类型 (1=用户订单  2=房东订单 3=后台订单)条件查询 分页 获取用户订单列表
	 * 说明,如果是房东订单查询：此处房东端分待处理和已处理订单
	 * 待处理：10=待确认  50=退房中
	 * 进行中： 20=待入住   40.已入住   60=待用户确认额外消费
	 * 已结束： 30=强制取消  31=房东已拒绝  32=房客取消  33=未支付超时取消 71=提前退房完成  72=正常退房完成
	 *
	 * @param orderRequestStr
	 * @return
	 * @author yd
	 * @created 2016年4月5日 下午5:10:25
	 */
	@Override
	public String getOrderListByCondiction(String orderRequestStr) {
		OrderRequest orderRequest = JsonEntityTransform.json2Object(orderRequestStr, OrderRequest.class);
		DataTransferObject dto = new DataTransferObject();
		/** 校验  请求参数存在 */
		if (checkOrderRequest(orderRequest)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数不存在，包括查询类型");
			return dto.toJsonString();
		}
		int requestType = orderRequest.getRequestType().intValue();

		if (requestType != 1 && requestType != 2 && requestType != 3) {

			LogUtil.info(LOGGER, "订单查询类型错误");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单查询类型错误");
			return dto.toJsonString();
		}


		if (requestType == 1) {
			/** 用户请求  userUid必须存在 */
			if (Check.NuNStr(orderRequest.getUserUid())) {
				LogUtil.info(LOGGER, "请求参数userUid为空,orderRequest:{}", orderRequest);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("请求参数userUid为空");
				return dto.toJsonString();
			}
		} else if (requestType == 2) {
			/** 房东请求  landlordUid必须存在  订单状态 */
			if (Check.NuNStr(orderRequest.getLandlordUid())) {
				LogUtil.info(LOGGER, "请求参数landlordUid为空,orderRequest:{}", orderRequest);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("请求参数landlordUid为空");
				return dto.toJsonString();
			}

		} else if (orderRequest.getRequestType().intValue() == 3) {

			LogUtil.info(LOGGER, "后台订单查询");
		}

		PagingResult<OrderInfoVo> pagingResult = orderCommonServiceImpl.getOrderInfoListByCondiction(orderRequest);

		dto.putValue("orderHouseList", pagingResult.getRows());
		dto.putValue("size", pagingResult.getTotal());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 公共校验订单请求参数
	 *
	 * @author yd
	 * @created 2016年4月5日 下午5:47:14
	 *
	 * @param orderRequest
	 * @return
	 */
	private boolean checkOrderRequest(OrderRequest orderRequest){

		/** 校验  请求参数存在 */
		if(Check.NuNObj(orderRequest)||Check.NuNObj(orderRequest.getRequestType())){
			LogUtil.info(LOGGER,"参数错误,orderRequest:{}",orderRequest);
			return true;
		}
		return false;
	}

	/**
	 *  按订单类型 (1=用户订单  2=房东订单 3=后台订单)
	 *  根据订单编码查询订单详情 
	 *  @see com.ziroom.minsu.services.order.api.inner.OrderCommonService。
	 *  #queryLoadlordOrderBySn(java.lang.String, com.ziroom.minsu.services.order.dto.OrderDetailRequest)
	 *  @param requestStr
	 *  @author yd
	 *  @date 2016-04-05
	 *  @version 1.0
	 */
	@Override
	public String queryOrderInfoBySn(String requestStr) {

		OrderDetailRequest request = JsonEntityTransform.json2Object(requestStr, OrderDetailRequest.class);
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(request)||Check.NuNObj(request.getRequestType())){

			LogUtil.info(LOGGER,"请求参数不存在或请求类型不存在");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数不存在或请求类型不存在");
			return dto.toJsonString();
		}
		String orderSn  = request.getOrderSn();
		if(Check.NuNStr(orderSn)){
			LogUtil.info(LOGGER,"请求参数orderSn为空,request:{}",request);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数orderSn为空");
			return dto.toJsonString();
		}
		OrderDetailVo orderDetailVo = this.orderCommonServiceImpl.orderHouseToOrderDetail(orderSn);
		if(Check.NuNObj(orderDetailVo)){
			LogUtil.info(LOGGER,"当前订单不存在,request:{}",request);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("当前订单不存在");
			return dto.toJsonString();
		}
		//房客无权限
		if(request.getRequestType() == 1&&!orderDetailVo.getUserUid().equals(request.getUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
			return dto.toJsonString();
		}
		//房东无权限
		if(request.getRequestType() == 2&&!orderDetailVo.getLandlordUid().equals(request.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
			return dto.toJsonString();
		}
        //处理城市名称
        dealCityName(orderDetailVo);
		getOrderStatuName(orderDetailVo);
		dto.putValue("orderDetailVo",orderDetailVo);


		LogUtil.info(LOGGER,"当前订单信息orderDetailVo={}", orderDetailVo);
		return dto.toJsonString();
	}


	/**
	 * 获取当前的订单的全部信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public String getOrderAllBySn(String orderSn){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			LogUtil.info(LOGGER,"请求参数orderSn为空");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数orderSn为空");
			return dto.toJsonString();
		}
		OrderDetailVo orderDetailVo = this.orderCommonServiceImpl.orderHouseToOrderDetail(orderSn);
		if (Check.NuNObj(orderDetailVo)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("当前订单不存在");
			return dto.toJsonString();
		}
		//处理城市名称
		dealCityName(orderDetailVo);
		getOrderStatuName(orderDetailVo);
		dto.putValue("orderDetailVo",orderDetailVo);
		dto.putValue("contacts",orderCommonServiceImpl.findOrderContactsByOrderSn(orderSn));
		dto.putValue("log",orderCommonServiceImpl.getOrderLogListByOrderSn(orderSn));
		dto.putValue("pay",orderPayService.getOrderPayByOrderSn(orderSn));
		//订单活动信息
		dto.putValue("orderAc", orderActivityServiceImpl.findOrderAcByOrderSn(orderSn));
		//订单特殊优惠
		dto.putValue("listOrderSpecialOfferVo", orderConfigServiceImpl.findOrderSpecialOffer(orderSn));
		//TODO 设置折扣之后的价格
		dto.putValue("prices",orderUserServiceImpl.getDayCutPricesMap(orderDetailVo,orderDetailVo.getDiscountMoney()));
		LogUtil.info(LOGGER,"当前订单信息orderDetailVo={}", orderDetailVo);
		return dto.toJsonString();


	}

    /**
     * 处理城市的名称
     * @author afi
     * @param orderDetailVo
     */
    private void dealCityName(OrderDetailVo orderDetailVo){
        if(!Check.NuNObj(orderDetailVo)){
            if(!Check.NuNStr(orderDetailVo.getCityCode())){
                orderDetailVo.setCityName(getCityName(orderDetailVo.getCityCode()));
            }
            if(!Check.NuNStr(orderDetailVo.getNationCode())){
                orderDetailVo.setNationName(getCityName(orderDetailVo.getNationCode()));
            }
        }
    }


    /**
     * 获取城市名称
     * @author afi
     * @param cityCode
     * @return
     */
    private String getCityName(String cityCode){
        if(Check.NuNObj(cityCode)){
            return cityCode;
        }
        String json = confCityService.getCityNameByCode(cityCode);
        DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(json);
        String cityName = cityDto.parseData("cityName", new TypeReference<String>() {
        });
        return cityName;
    }

	/**
	 * 
	 * 得到状态的
	 *
	 * @author yd
	 * @created 2016年4月5日 上午10:20:05
	 *
	 * @param orderDetailVo
	 */
	private void getOrderStatuName(OrderDetailVo orderDetailVo){

		if(!Check.NuNObj(orderDetailVo)&&!Check.NuNObj(orderDetailVo.getOrderStatus())){
			OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderDetailVo.getOrderStatus());
			if(orderStatusEnum!=null){
				orderDetailVo.setOrderStatuChineseName(orderStatusEnum.getStatusName());
				orderDetailVo.setOrderStatusEnum(orderStatusEnum);
			}
		}
	}

	/**
	 * 
	 * 条件查询订单
	 *
	 * @author yd
	 * @created 2016年4月12日 下午3:07:26
	 *
	 * @param orderRequest
	 * @return
	 */
	@Override
	public String queryOrderByCondition(String orderRequest) {

		OrderRequest orderRe = JsonEntityTransform.json2Object(orderRequest, OrderRequest.class);
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(orderRe)||Check.NuNCollection(orderRe.getListOrderSn())){

			LogUtil.info(LOGGER,"请求参数不存在或请求类型不存在");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数为null 或者 请求订单号集合没有");
			return dto.toJsonString();
		}
		dto.putValue("listOrder", this.orderCommonServiceImpl.queryOrderByCondition(orderRe));
		return dto.toJsonString();
	}
	/**
	 * 
	 * 评价订单时候,根据订单编号修改订单的评价状态
	 *
	 * @author yd
	 * @created 2016年4月12日 下午5:57:02
	 *
	 * @param orderRequestStr
	 * @return
	 */
	@Override
	public String updateEvaStatuByOrderSn(String orderRequestStr) {

		OrderRequest orderRequest = JsonEntityTransform.json2Object(orderRequestStr, OrderRequest.class);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(orderRequestStr)||Check.NuNCollection(orderRequest.getListOrderSn())||Check.NuNObj(orderRequest.getEvaStatus())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("待修改的订单集合为null，或者无修改的订单评价状态");
			return dto.toJsonString();
		}

		int evaStatus = orderRequest.getEvaStatus().intValue();
		EvaStatusEnum evaStatusEnum = EvaStatusEnum.getEvaStatusByCode(evaStatus);

		if(evaStatusEnum == null){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("待修改订单的评价状态为evaStatus="+evaStatus+",此值错误，只能为EvaStatusEnum={"+EvaStatusEnum.values()+"}");
			return dto.toJsonString();
		}

		int result = this.orderCommonServiceImpl.updateEvaStatuByOrderSn(orderRequest);

		dto.putValue("result", result);
		return dto.toJsonString();
	}


	/**
	 * 更新订单的初见或者评价状态
	 * @author afi
	 * @param evalSynRequestJson
	 * @return
	 */
	public String  updateStatuByOrderSn(String  evalSynRequestJson){
		EvalSynRequest evalSynRequest = JsonEntityTransform.json2Object(evalSynRequestJson, EvalSynRequest.class);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(evalSynRequest)||Check.NuNCollection(evalSynRequest.getListOrderSn())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("待修改的订单集合为null");
			return dto.toJsonString();
		}
		if (Check.NuNObj(evalSynRequest.getEvlStatus())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("同步状态参数为空");
			return dto.toJsonString();
		}
		Integer result = 0;
		if (!Check.NuNObj(evalSynRequest.getEvlStatus())){
			EvaStatusEnum evaStatusEnum = EvaStatusEnum.getEvaStatusByCode(ValueUtil.getintValue(evalSynRequest.getEvlStatus()));
			if(evaStatusEnum == null){
				LogUtil.error(LOGGER, "异常的评价状态，par:{}", evalSynRequestJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("待修改订单的评价状态异常");
				return dto.toJsonString();
			}
			result = orderCommonServiceImpl.updatePjStatuByOrderSn(evalSynRequest);
		}
		dto.putValue("result", result);
		return dto.toJsonString();

	}



	/**
	 * 
	 * 保存常用联系人
	 *
	 * @author yd
	 * @created 2016年4月30日 下午5:33:32
	 *
	 * @param usualContact
	 * @return
	 */
	@Override
	public String saveUsualContact(String usualContact){

		UsualContactEntity usualContactEntity = JsonEntityTransform.json2Object(usualContact, UsualContactEntity.class);
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(usualContactEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("待保存实体不存在");
			return dto.toJsonString();
		}
		if(Check.NuNObj(usualContactEntity.getCardType())||Check.NuNStr(usualContactEntity.getConName())||Check.NuNStr(usualContactEntity.getCardValue())||Check.NuNStr(usualContactEntity.getUserUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("入住人姓名，证件类型和证件号,以及预订人uid为必填项");
			return dto.toJsonString();
		}

		if(Check.NuNObj(usualContactEntity.getIsBooker()) || YesOrNoEnum.YES.getCode() != usualContactEntity.getIsBooker()){
			// 判重校验，是否有相同证件类型、证件号码的联系人，不能重复添加
			long repeatContact = usualContactServiceImpl.checkHaveContact(usualContactEntity);
			if(repeatContact >= 1){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("已存在该入住人");
				return dto.toJsonString();
			}
		}

		// 如果是预订人，判断是否存在相同信息的入住人，如果有，把入住人更新为预订人
		if(!Check.NuNObj(usualContactEntity.getIsBooker()) && YesOrNoEnum.YES.getCode() == usualContactEntity.getIsBooker()){
            UsualContactEntity contactByInfo = usualContactServiceImpl.getContactByInfo(usualContactEntity);
            if(!Check.NuNObj(contactByInfo)){
                UsualContactEntity updateContact = new UsualContactEntity();
                updateContact.setFid(contactByInfo.getFid());
                updateContact.setIsBooker(YesOrNoEnum.YES.getCode());
                usualContactServiceImpl.updateByFid(updateContact);
                return dto.toJsonString();
            }
        }


		int result = 0;
		try {
			result = this.orderCommonServiceImpl.saveUsualContact(usualContactEntity);
		} catch (Exception e) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("保存实体异常");
			LogUtil.error(LOGGER, "保存usualContactEntity异常{}", e);
			return dto.toJsonString();

		}
		dto.putValue("result", result);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 根据用户orderSn查询当前用户的当前订单的常用联系人
	 *
	 * @author yd
	 * @created 2016年4月30日 下午5:50:15
	 *
	 * @param orderSn
	 * @return
	 */
	@Override
	public String findOrderContactsByOrderSn(String orderSn) {


		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("无订单号");
			return dto.toJsonString();
		}
		List<UsualContactEntity> listUsualContact = this.orderCommonServiceImpl.findOrderContactsByOrderSn(orderSn);
		dto.putValue("listUsualContact", listUsualContact);
		dto.putValue("size", listUsualContact.size());
		return dto.toJsonString();
	}

	/**
	 * 获取用户常用联系人列表
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 上午11:20:09
	 *
	 * @param userUid
	 * @return
	 */
	@Override
	public String findUsualContactsByUid(String userUid) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(userUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("用户uid不存在");
			return dto.toJsonString();
		}
		List<UsualContactEntity> listUsualContact = this.orderCommonServiceImpl.findUsualContactsByUid(userUid);
		dto.putValue("listUsualContact", listUsualContact);
		dto.putValue("size", listUsualContact.size());
		return dto.toJsonString();
	}

	/**
	 * 获取订单基本信息
	 *
	 *
	 * @author yd
	 * @created 2016年4月3日 
	 *
	 * @param orderSn
	 * @return
	 */
	@Override
	public String getOrderByOrderSn(String orderSn){

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单号不存在");
			return dto.toJsonString();
		}
		OrderEntity oe = orderUserServiceImpl.getOrderBaseByOrderSn(orderSn);

		if(Check.NuNObj(oe)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("根据订单号orderSn={}"+orderSn+"查询，不存在此订单");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "根据订单号orderSn={}查询，返回结果OrderEntity={}",orderSn, JsonEntityTransform.Object2Json(oe));
		dto.putValue("order", oe);
		return dto.toJsonString();
	}

	/**
	 * 获取当前订单信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	@Override
	public String getOrderInfoByOrderSn(String orderSn){

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单号不存在");
			return dto.toJsonString();
		}
		OrderInfoVo oe =  orderCommonServiceImpl.getOrderInfoByOrderSn(orderSn);

		if(Check.NuNObj(oe)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("根据订单号orderSn={}"+orderSn+"查询，不存在此订单");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "根据订单号orderSn={}查询，返回结果OrderEntity={}", oe);
		dto.putValue("orderInfoVo", oe);
		return dto.toJsonString();
	}

	/**
	 * 获取订单的房源信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	@Override
	public String findHouseSnapshotByOrderSn(String orderSn){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单号不存在");
			return dto.toJsonString();
		}

		OrderHouseSnapshotEntity orderHouseSnapshot =  orderCommonServiceImpl.findHouseSnapshotByOrderSn(orderSn);
		if(Check.NuNObj(orderHouseSnapshot)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("根据订单号orderSn={"+orderSn+"}查询快照不存在");
			return dto.toJsonString();
		}
		dto.putValue("orderHouseSnapshot", orderHouseSnapshot);
		return dto.toJsonString();
	}
	/**
	 * 更新订单的信息
	 * @author afi
	 * @param orderEntity
	 * @return
	 */
	@Override
	public String updateOrderBaseByOrderSn(String orderEntity){
		
		DataTransferObject dto = new DataTransferObject();
		
		OrderEntity order = JsonEntityTransform.json2Object(orderEntity, OrderEntity.class);
		if(Check.NuNObj(orderEntity)||Check.NuNObj(order.getOrderStatus())||Check.NuNObj(order.getOldStatus())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体不存在,或者状态不对");
			return dto.toJsonString();
		}
		int index = this.orderCommonServiceImpl.updateOrderBaseByOrderSn(order);
		if(index>0){
			dto.putValue("result", index);
		}else{
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("修改失败");
		}
		
		return dto.toJsonString();
	}
	/**
	 * 按照fid集合查询 常用联系人列表
	 *
	 * @author yd
	 * @created 2016年4月1日 上午11:20:09
	 *
	 * @param ususalConRe
	 * @return
	 */
	@Override
	public String findUsualContactsByContion(String ususalConRe) {

		DataTransferObject dto = new DataTransferObject();

		UsualConRequest usualConRequest = JsonEntityTransform.json2Object(ususalConRe,UsualConRequest.class);
		if(Check.NuNObj(usualConRequest)||Check.NuNStr(usualConRequest.getUserUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数非法");
			return dto.toJsonString();
		}
		PagingResult<UsualContactEntity> listUsualContact = this.orderCommonServiceImpl.findUsualContactsByFid(usualConRequest);
		dto.putValue("listUsualContact", listUsualContact.getRows());
		dto.putValue("size", listUsualContact.getTotal());
		return dto.toJsonString();
	}


	/**
	 * 获取预订人信息
	 *
	 * @param userUid
	 * @return
	 * @author lishaochuan
	 * @create 2016/12/2 11:29
	 */
	@Override
	public String getBookerContact(String userUid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if (Check.NuNStr(userUid)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return dto.toJsonString();
			}

			UsualContactEntity bookerContact = usualContactServiceImpl.getBookerContact(userUid);
			dto.putValue("bookerContact", bookerContact);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 更新预订人信息
	 *
	 * @param request
	 * @return
	 * @author lishaochuan
	 * @create 2016/12/2 16:13
	 */
	@Override
	public String updateBookerContact(String request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			UsualContactEntity bookerContact = JsonEntityTransform.json2Object(request, UsualContactEntity.class);
			bookerContact.setIsBooker(YesOrNoEnum.YES.getCode());

			String fid = this.usualContactServiceImpl.deleteAndInsertContact(bookerContact);
			dto.putValue("fid", fid);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}



	/**
	 *
	 * update by fid
	 *
	 * @author yd
	 * @created 2016年5月2日 上午11:54:10
	 *
	 * @param usualContactEntity
	 * @return
	 */
	@Override
	public String updateByFid(String usualContactEntity){
		DataTransferObject dto = new DataTransferObject();
		UsualContactEntity usualContact = JsonEntityTransform.json2Object(usualContactEntity,UsualContactEntity.class);
		if(Check.NuNObj(usualContact)||Check.NuNStr(usualContact.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求非法");
			return dto.toJsonString();
		}

		// 获取到要修改的联系人
		UsualContactEntity myContact = usualContactServiceImpl.getContactByFid(usualContact.getFid(), usualContact.getUserUid());
		if(Check.NuNObj(myContact)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("联系人不存在");
			return dto.toJsonString();
		}

		// 判断是否 预订人，预订人不可修改
		if(myContact.getIsBooker() == YesOrNoEnum.YES.getCode()){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("如需修改个人基本资料，请到个人中心修改");
			return dto.toJsonString();
		}

		// 判断是否做过修改，如未修改，直接返回成功
		if(usualContact.getConName().equals(myContact.getConName())
				&& usualContact.getCardType().equals(myContact.getCardType())
				&& usualContact.getCardValue().equals(myContact.getCardValue())
				&& usualContact.getConTel().equals(myContact.getConTel())){
			LogUtil.info(LOGGER, "信息相同，不做修改");
			return dto.toJsonString();
		}


		// 判重校验，是否有相同证件类型、证件号码的联系人，不能重复添加
		long repeatContact = usualContactServiceImpl.checkHaveContact(usualContact);
		if(repeatContact >= 1){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("已存在该入住人");
			return dto.toJsonString();
		}

		usualContact.setIsBooker(YesOrNoEnum.NO.getCode());
		dto.putValue("result", this.usualContactServiceImpl.updateByFid(usualContact));
		return dto.toJsonString();
	}



	/**
	 * 修改联系人
	 * 1、逻辑删除
	 * 2、新增
	 *
	 * @author lishaochuan
	 * @create 2016/12/21 16:45
	 * @param
	 * @return
	 */
	@Override
    public String updateLogicDelete(String usualContactEntity){
		DataTransferObject dto = new DataTransferObject();

		UsualContactEntity usualContact = JsonEntityTransform.json2Object(usualContactEntity,UsualContactEntity.class);
		if(Check.NuNObj(usualContact)||Check.NuNStr(usualContact.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求非法");
			return dto.toJsonString();
		}

		// 获取到要修改的联系人
		UsualContactEntity myContact = usualContactServiceImpl.getContactByFid(usualContact.getFid(), usualContact.getUserUid());
		if(Check.NuNObj(myContact)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("联系人不存在");
			return dto.toJsonString();
		}

		// 判断是否 预订人，预订人不可修改
		if(myContact.getIsBooker() == YesOrNoEnum.YES.getCode()){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("如需修改个人基本资料，请到个人中心修改");
			return dto.toJsonString();
		}

		// 判断是否做过修改，如未修改，直接返回成功
		if(usualContact.getConName().equals(myContact.getConName())
				&& usualContact.getCardType().equals(myContact.getCardType())
				&& usualContact.getCardValue().equals(myContact.getCardValue())
				&& usualContact.getConTel().equals(myContact.getConTel())){
			LogUtil.info(LOGGER, "信息相同，不做修改");
			return dto.toJsonString();
		}


		// 判重校验，是否有相同证件类型、证件号码的联系人，不能重复添加
		long repeatContact = usualContactServiceImpl.checkHaveContact(usualContact);
		if(repeatContact >= 1){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("已存在该入住人");
			return dto.toJsonString();
		}

		usualContact.setIsBooker(YesOrNoEnum.NO.getCode());
		this.usualContactServiceImpl.deleteAndInsertContact(usualContact);
    	return dto.toJsonString();
    }


	/**
	 * 逻辑删除联系人
	 *
	 * @param fid
	 * @param userUid @return
	 * @author lishaochuan
	 * @create 2016/12/2 9:36
	 */
	@Override
	public String deleteContact(String fid, String userUid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if (Check.NuNObj(fid) || Check.NuNStr(userUid)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return dto.toJsonString();
			}


			usualContactServiceImpl.deleteContact(fid, userUid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 查询订单快照(用户uid必须给定)
	 *
	 * @author yd
	 * @created 2016年5月2日 下午6:32:38
	 *
	 * @param orderRequest
	 * @return
	 */
	@Override
	public String findHouseSnapshotByOrder(String orderRequest){
		
		DataTransferObject dto = new DataTransferObject();
		OrderRequest orderRe= JsonEntityTransform.json2Object(orderRequest,OrderRequest.class);
		if(Check.NuNObj(orderRe)||(Check.NuNStr(orderRe.getUserUid())&&Check.NuNStr(orderRe.getLandlordUid()))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求非法");
			return dto.toJsonString();
		}
		PagingResult<HouseSnapshotVo> listPagingResult = this.orderUserServiceImpl.findHouseSnapshotByOrder(orderRe);
		dto.putValue("listSnapshot", listPagingResult.getRows());
		dto.putValue("total",listPagingResult.getTotal());
    	return dto.toJsonString();
	}



    /**
     * 获取当前用户下的智能锁的数量
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String countLock(String uid){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        dto.putValue("total", orderUserServiceImpl.countLock(uid));
        return dto.toJsonString();

    }


	/**
	 * 获取当前的订单的数量
	 * @author afi
	 * @param uid
	 * @return
     */
	@Override
	public String getOrderCount4User(String uid){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		dto = new DataTransferObject(DataTransferObject.SUCCESS,"",orderUserServiceImpl.getOrderCount4UserByUid(uid).toMap());
		return dto.toJsonString();
	}


	/**
	 * 获取最近的一个待入住的订单@
	 * @author afi
	 * @param uid
	 * @return
	 */
	public String getOrderLast(String uid){

		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("uid为空");
			return dto.toJsonString();
		}
		//获取当前最近的待入住的订单
		OrderInfoVo orderInfoVo = orderUserServiceImpl.getOrderLastByUid(uid);
		if (!Check.NuNObj(orderInfoVo)){
			int housingDay  = DateSplitUtil.countDateSplit(orderInfoVo.getStartTime(), orderInfoVo.getEndTime());
			orderInfoVo.setHousingDay(housingDay);
			orderInfoVo.setStartTimeStr(DateUtil.dateFormat(orderInfoVo.getStartTime(),"yyyy-MM-dd"));
			orderInfoVo.setEndTimeStr(DateUtil.dateFormat(orderInfoVo.getEndTime(), "yyyy-MM-dd"));
		}
		dto.putValue("orderInfoVo", orderInfoVo);
		return dto.toJsonString();

	}


	/**
	 * 获取评级列表
	 * @author  afi
	 * @param orderEvalRequestStr
	 * @return
	 */
	@Override
	public String getOrderEavlList(String orderEvalRequestStr) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(orderEvalRequestStr)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		OrderEvalRequest orderEvalRequest = JsonEntityTransform.json2Object(orderEvalRequestStr, OrderEvalRequest.class);

		PagingResult<OrderInfoVo> pageResult = new PagingResult<>();
		if(Check.NuNObjs(orderEvalRequest.getUid(),orderEvalRequest.getRequestType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数异常");
			return dto.toJsonString();
		}
		int limitDay = this.getEvalTimeDay();
		orderEvalRequest.setLimitDay(limitDay);

		//获取当前的请求类型
		RequestTypeEnum requestTypeEnum = RequestTypeEnum.getRequestTypeByCode(orderEvalRequest.getRequestType());
		if(Check.NuNObj(requestTypeEnum)){
			LogUtil.info(LOGGER,"异常的请求类型,par:{}",orderEvalRequestStr);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询类型错误");
			return dto.toJsonString();
		}
		if(requestTypeEnum.checkTeant()){
			/** 用户请求  userUid必须存在 */
			if(Check.NuNStr(orderEvalRequest.getUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("uid为空");
				return dto.toJsonString();
			}else if(Check.NuNObj(orderEvalRequest.getOrderEvalType())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("评价类型为空");
				return dto.toJsonString();
			}else{ 
				pageResult = orderUserServiceImpl.getTenantOrderEavlList(orderEvalRequest);
			}
		}else if(requestTypeEnum.checkLandlord()){
			/** 房东请求  landlordUid必须存在  订单状态 */
			if(Check.NuNStr(orderEvalRequest.getUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("uid为空");
				return dto.toJsonString();
			}else{
				pageResult = orderLoadlordServiceImpl.getLandOrderEavlList(orderEvalRequest);
			}
		}else {
			LogUtil.info(LOGGER,"订单查询类型错误,par:{}",orderEvalRequestStr);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单查询类型错误");
			return dto.toJsonString();
		}
		//统一处理数据，列表需要显示间隔天数，至今在服务里处理好
		List<OrderInfoVo> list = new ArrayList<>();
		if(!Check.NuNObj(pageResult)&&!Check.NuNCollection(pageResult.getRows())){
			//状态转换
			for (OrderInfoVo orderInfoVo : pageResult.getRows()) {
				int housingDay  = DateSplitUtil.countDateSplit(orderInfoVo.getStartTime(), orderInfoVo.getEndTime());
				orderInfoVo.setHousingDay(housingDay);
				orderInfoVo.setStartTimeStr(DateUtil.dateFormat(orderInfoVo.getStartTime(),"yyyy-MM-dd"));
				orderInfoVo.setEndTimeStr(DateUtil.dateFormat(orderInfoVo.getEndTime(), "yyyy-MM-dd"));
				//如果是 房东端才需要入住人数量
				if(requestTypeEnum.checkTeant()){
					orderInfoVo.setContactsNum((int)usualContactServiceImpl.queryOrderContactNum(orderInfoVo.getOrderSn()));
				}
				list.add(orderInfoVo);


			}
		}
		dto.putValue("list", list);
		dto.putValue("total", pageResult.getTotal());
		return dto.toJsonString();
	}



	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.order.api.inner.OrderCommonService#getOrderList(java.lang.String)
	 */
	@Override
	public String getOrderList(String orderRequestStr) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(orderRequestStr)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		OrderRequest orderRequest = JsonEntityTransform.json2Object(orderRequestStr, OrderRequest.class);
		
		PagingResult<OrderInfoVo> pageResult = new PagingResult<>();

		if(checkOrderRequest(orderRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数不存在，包括查询类型");
			return dto.toJsonString();
		}
		//获取当前的请求类型
		RequestTypeEnum requestTypeEnum = RequestTypeEnum.getRequestTypeByCode(orderRequest.getRequestType());
		if(Check.NuNObj(requestTypeEnum)){
			LogUtil.info(LOGGER,"订单查询类型错误,par:{}",orderRequestStr);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单查询类型错误");
			return dto.toJsonString();
		}
		if(requestTypeEnum.checkTeant()){
			/** 用户请求  userUid必须存在 */
			if(Check.NuNStr(orderRequest.getUserUid())){
				LogUtil.info(LOGGER,"current orderRequest userUid is null on getOrderForPageByCondiction");
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("current orderRequest userUid is null on getOrderForPageByCondiction");
				return dto.toJsonString();
			}else{
				pageResult = orderUserServiceImpl.getTenantOrderList(orderRequest);
			}
		}else if(requestTypeEnum.checkLandlord()){
			/** 房东请求  landlordUid必须存在  订单状态 */
			if(Check.NuNStr(orderRequest.getLandlordUid())){
				LogUtil.info(LOGGER,"current orderRequest landlordUid is null or orderStatus collection is null on getOrderForPageByCondiction ,param is{}",orderRequest);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("current orderRequest landlordUid is null on getOrderForPageByCondiction");
				return dto.toJsonString();
			}else{
				pageResult = orderLoadlordServiceImpl.queryLoadlordOrderList(orderRequest);
			}
		}else {
			LogUtil.info(LOGGER,"订单查询类型错误,par:{}",orderRequestStr);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单查询类型错误");
			return dto.toJsonString();
		}
		//统一处理数据，列表需要显示间隔天数，至今在服务里处理好
		List<OrderInfoVo> list = new ArrayList<>();
		if(!Check.NuNObj(pageResult)&&!Check.NuNCollection(pageResult.getRows())){
            //状态转换
            for (OrderInfoVo orderInfoVo : pageResult.getRows()) {
            	int housingDay  = DateSplitUtil.countDateSplit(orderInfoVo.getStartTime(), orderInfoVo.getEndTime());
            	orderInfoVo.setHousingDay(housingDay);
            	orderInfoVo.setStartTimeStr(DateUtil.dateFormat(orderInfoVo.getStartTime(),"yyyy-MM-dd"));
            	orderInfoVo.setEndTimeStr(DateUtil.dateFormat(orderInfoVo.getEndTime(), "yyyy-MM-dd"));
            	//如果是 房东端才需要入住人数量
            	if(requestTypeEnum.checkTeant()){
            		orderInfoVo.setContactsNum((int)usualContactServiceImpl.queryOrderContactNum(orderInfoVo.getOrderSn()));
            	}
                list.add(orderInfoVo);
            }
		}
		dto.putValue("orderHouseList", list);
		dto.putValue("size", pageResult.getTotal());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.order.api.inner.OrderCommonService#getCheckOutStrategyByOrderSn(java.lang.String)
	 */
	@Override
	public String getCheckOutStrategyByOrderSn(String orderSn) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(orderSn)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单号为空");
			return dto.toJsonString();
		}
		CheckOutStrategy checkOutStrategy = orderConfigServiceImpl.getCheckOutStrategyByOrderSn(orderSn);
		dto.putValue("checkOutStrategy", checkOutStrategy);
		return dto.toJsonString();
	}


	public String findOrderAcByOrderSn(String orderSn){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单号为空");
			return dto.toJsonString();
		}
		List<OrderActivityEntity> orderAcListByOrderSn = orderActivityServiceImpl.findOrderAcByOrderSn(orderSn);
		dto.putValue("orderAcListByOrderSn", orderAcListByOrderSn);
		return dto.toJsonString();
	}
	
	/**
	 * 未支付订单取消
	 *
	 * @author loushuai
	 * @created 2017年10月10日 下午6:37:28
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String cancelUnPayOrder(String object2Json) {
		LogUtil.info(LOGGER, "cancelUnPayOrder方法，object2Json={}", object2Json);
		DataTransferObject dto = new DataTransferObject();
		CancelOrderServiceRequest cancelOrder = JsonEntityTransform.json2Object(object2Json, CancelOrderServiceRequest.class);
		if(Check.NuNObj(cancelOrder)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		if(Check.NuNStr(cancelOrder.getCancelReason())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("备注不能为空");
			return dto.toJsonString();
		}
		int result = orderCommonServiceImpl.cancelUnPayOrder(cancelOrder);
		if(result>0){
			OrderInfoVo orderInfo = orderCommonServiceImpl.getOrderInfoByOrderSn(cancelOrder.getOrderSn());
			orderMsgProxy.sendMsg4CancelUnPayOrder(orderInfo);
		}
		return dto.toJsonString();
	}

	/**
	 *
	 * 查询昨天遗漏的订单行为(房东行为成长体系定时任务)
	 *
	 * @author zhangyl2
	 * @created 2017年10月13日 13:02
	 * @param
	 * @return
	 */
    @Override
    public String queryOrderForCustomerBehaviorJob(String paramJson) {

        OrderRequest orderRequest = JsonEntityTransform.json2Object(paramJson, OrderRequest.class);
        DataTransferObject dto = new DataTransferObject();

        if(Check.NuNObj(orderRequest)
                || Check.NuNObj(orderRequest.getOrderStatus())
                || Check.NuNStr(orderRequest.getStartTime())
                || Check.NuNStr(orderRequest.getEndTime())){

            LogUtil.info(LOGGER,"请求参数不存在或请求类型不存在");
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("参数错误");
            return dto.toJsonString();
        }
        dto.putValue("listOrder", this.orderCommonServiceImpl.queryOrderForCustomerBehaviorJob(orderRequest));
        return dto.toJsonString();
    }
    
    /**
	 * 获取房东（集合）60天内的接单率（troy地图找房功能：60天内创建的订单中，该房东所有申请预定的订单通过数/该房东所有申请预定的订单数*100%）
	 *
	 * @author loushuai
	 * @created 2017年10月25日 下午8:04:22
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String getLandAcceptOrderRateIn60Days(String object2Json) {
		LogUtil.info(LOGGER, "getLandAcceptOrderRateIn60Days方法   参数={}", object2Json);
		DataTransferObject dto = new DataTransferObject();
		Set<String> uidSet = JsonEntityTransform.json2Object(object2Json, Set.class);
		if(Check.NuNCollection(uidSet)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("集合参数为空");
		}
		Map<String, Object> resultMap =  new HashMap<String, Object>();
		for (String  uid: uidSet) {
			long countAcceptApplyOrder = orderCommonServiceImpl.countAcceptApplyOrder(uid);
			long countAllApplyOrder = orderCommonServiceImpl.countAllApplyOrder(uid);
			if(countAllApplyOrder == 0){
				resultMap.put(uid, null);
				continue;
			}
			if(countAcceptApplyOrder==0){
				resultMap.put(uid, "0");
				continue;
			}
			double div = BigDecimalUtil.div(countAcceptApplyOrder, countAllApplyOrder);
			double mul = BigDecimalUtil.mul(div, 100.00);
			String percent=String.valueOf(mul)+"%";
			resultMap.put(uid, percent);
		}
		dto.putValue("result", resultMap);
		LogUtil.info(LOGGER, "getLandAcceptOrderRateIn60Days方法 resultMap={}", resultMap.toString());
		return dto.toJsonString();
	}

	/**
	 * 批量获取被邀请用户，订单及状态，填充其被邀请状态
	 *
	 * @author loushuai
	 * @created 2017年12月4日 下午1:07:21
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String getBeInviterStatusInfo(String object2Json) {
		LogUtil.info(LOGGER, "getBeInviterStatusInfo方法   参数={}", object2Json);
		DataTransferObject dto = new DataTransferObject();
		BeInviterStatusInfoRequest request = JsonEntityTransform.json2Object(object2Json, BeInviterStatusInfoRequest.class);
		List<String> beInviterInfoList = request.getBeInviterInfoList();
		if(Check.NuNObj(request) || Check.NuNCollection(beInviterInfoList)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("集合参数为空");
			return dto.toJsonString();
		}
		List<BeInviterStatusInfoVo> result = orderCommonServiceImpl.getBeInviterStatusInfo(request);
		List<String> hasOrderList = new ArrayList<String>();
		List<BeInviterStatusInfoVo> beInviterStatusInfos = new ArrayList<BeInviterStatusInfoVo>();
		for (BeInviterStatusInfoVo beInviterStatusInfoVo : result) {
			hasOrderList.add(beInviterStatusInfoVo.getBeInviterUid());
		}
		for (String uid : beInviterInfoList) {
			BeInviterStatusInfoVo beInviterStatusInfoVo = new BeInviterStatusInfoVo();
			if(!hasOrderList.contains(uid)){
				beInviterStatusInfoVo.setBeInviterUid(uid);
				beInviterStatusInfoVo.setOrderNum(YesOrNoEnum.NO.getCode());
				beInviterStatusInfoVo.setIsHasEffectiveOrder(YesOrNoEnum.NO.getCode());
				beInviterStatusInfos.add(beInviterStatusInfoVo);
				continue;
			}
			//查询出改用户的订单的创建时间
			BeInviterStatusInfoVo resultVO = orderCommonServiceImpl.getEarliestOrderTime(uid);
			beInviterStatusInfoVo.setBeInviterUid(uid);
			beInviterStatusInfoVo.setOrderNum(YesOrNoEnum.YES.getCode());
			beInviterStatusInfoVo.setOrderCreateTime(resultVO.getOrderCreateTime());
			beInviterStatusInfos.add(beInviterStatusInfoVo);
		}
		dto.putValue("beInviterStatusInfos", beInviterStatusInfos);
		return dto.toJsonString();
	}

	/**
	 * 查询当前时间4个小时内的已结算的订单的uid和orderSn
	 * @author yanb
	 * @created 2017年12月13日 15:02:02
	 * @param  * @param null
	 * @return
	 */
	@Override
	public String queryOrder4Hour() {
		DataTransferObject dto = new DataTransferObject();
		List<OrderInviteVo> orderList = orderCommonServiceImpl.queryOrder4Hour();
		LogUtil.info(LOGGER, "queryOrder4Hour方法 orderList={}", orderList);
		dto.putValue("orderList",orderList);
		return dto.toJsonString();
	}


}
