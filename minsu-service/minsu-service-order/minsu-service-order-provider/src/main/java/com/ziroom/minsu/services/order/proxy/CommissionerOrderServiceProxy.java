
package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.entity.order.OrderParamEntity;
import com.ziroom.minsu.entity.order.OrderRelationEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.ConfigForceVo;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.order.api.inner.CommissionerOrderService;
import com.ziroom.minsu.services.order.dto.OrderRelationRequest;
import com.ziroom.minsu.services.order.dto.OrderRelationSaveRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.dto.SendOrderEmailRequest;
import com.ziroom.minsu.services.order.entity.CancleOrderVo;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderDayPriceVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.OrderRelationVo;
import com.ziroom.minsu.services.order.service.*;
import com.ziroom.minsu.valenum.order.*;
import com.ziroom.minsu.valenum.traderules.CheckOutStrategy;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.*;

/**
 * <p>房东点击强制取消业务处理
 *  业务说明： 发送场景 —— 订单已支付  且在 '待入住 '和 '已入住'状态下，房东才可操作申请强制取消
 *  1.对于房东：点击强制取消——》校验订单状态成功————》修改订单状态为强制取消申请中，并且记下订单日志，以及保存参数表此时订单的修改前的状态（方便后期客服恢复此订单，强制取消可以
 *  多次，但记录最后一次的修改前状态即可恢复，因为恢复永远是恢复上一次操作修改状态的状态值）
 *  2.对于客服：两种操作，1.恢复订单（即：订单恢复到申请强制取消订单中前一刻的状态，即参数表记录时刻的状态）
 *  
 *   2.同意房东的强制取消申请
 *     针对第二种情况分析：
 *     
 *     A.获取订单配置时间X（单位：天数）
 *       一.房客开始入住时间往前推X天，一直到正常退房完成，这个区间段，都要走结算（房东结算  房客结算），否则直接退房客钱（若有优惠卷  优惠卷处理） 不收房东钱
 *   （  房东：
 *       A.房东这边不发生扣钱情况
 *       B.房东这边发生扣钱情况 
 *     房客：
 *       A.房客决定退钱，走结算
 *       B.房客同意换房，走结算）
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
@Component("order.commissionerOrderServiceProxy")
public class CommissionerOrderServiceProxy implements CommissionerOrderService{

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(CommissionerOrderServiceProxy.class);

	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl;


	@Resource(name = "order.orderConfigServiceImpl")
	private OrderConfigServiceImpl orderConfigService;

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;

	private final static String remark = "客服恢复强制取消订单";

	@Resource(name = "order.orderRelationServiceImpl")
	private OrderRelationServiceImpl orderRelationServiceImpl;

	@Resource(name = "order.commissionerOrderServiceImpl")
	private CommissionerOrderServiceImpl  commissionerOrderServiceImpl;


	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name = "order.orderMoneyServiceImpl")
	private OrderMoneyServiceImpl orderMoneyService;

	@Resource(name = "order.orderUserServiceImpl")
	private OrderUserServiceImpl orderUserService;

	@Resource(name = "order.payVouchersCreateServiceImpl")
	private PayVouchersCreateServiceImpl payVouchersCreateService;



	/* <p>
	 * 房东强制取消订单
	 * </p>
	 * @see com.ziroom.minsu.services.order.api.inner.CommissionerOrderService#compulsoryCancellOrderByLandlord(java.lang.String)
	 * @author yd
	 * @date 2016.04.23 下午
	 * @version 1.0
	 */
	@Override
	public String compulsoryCancellOrderByLandlord(String orderSn) {

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单号为空");
			return dto.toJsonString();
		}
		orderSn = JsonEntityTransform.json2String(orderSn);

		OrderInfoVo orderEntity = this.orderCommonServiceImpl.getOrderInfoByOrderSn(orderSn);

		int orderStatus = orderEntity.getOrderStatus().intValue();
		if(Check.NuNObj(orderEntity)||orderEntity.getPayStatus().intValue() == OrderPayStatusEnum.UN_PAY.getPayStatus()||( orderStatus!=OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()&&orderStatus !=OrderStatusEnum.CHECKED_IN.getOrderStatus()
				&&orderStatus != OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus())){
			LogUtil.info(logger,"当前强制取消订单的订单号orderSn={},查询订单实体orderEntity={}",orderSn,orderEntity);

			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("the OrderEntity does not exist or the orderStatus or payStatus is error ");
			return dto.toJsonString();
		}
		int oldOrderStatus = orderEntity.getOrderStatus();
		orderEntity.setOrderStatus(OrderStatusEnum.FORCED_CANCELLATION.getOrderStatus());
		orderEntity.setLastModifyDate(new Date());
		int result = this.orderCommonServiceImpl.updateCancellOrderByLandlord(orderEntity.getLandlordUid(),oldOrderStatus, orderEntity, OrderParamEnum.STATUS_BAK.getName(), OrderParamEnum.STATUS_BAK.getCode(), String.valueOf(oldOrderStatus));

		if(result<=0){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房东强制取消失败");	
		}
		dto.putValue("result", result);
		return dto.toJsonString();
	}

	/* 
	 * <p>
	 *   客服人员恢复已强制取消申请中的订单
	 *   说明：要恢复到订单已强制取消申请中 之前的状态 （此状态在订单的参数表中存放 code = 'statusBak' ）
	 * </p>
	 * @see com.ziroom.minsu.services.order.api.inner.CommissionerOrderService#recoveryCancellOrde(java.lang.String)
	 * @auther yd
	 * @created 2016.04.23 晚上
	 * @version 1.0
	 * 
	 */
	@Override
	public String recoveryCancelOrde(String orderSn,String opUuid) {

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单号为空");
			return dto.toJsonString();
		}
		orderSn = JsonEntityTransform.json2String(orderSn);

		OrderEntity orderEntity = this.orderCommonServiceImpl.getOrderBaseByOrderSn(orderSn);

		LogUtil.info(logger, "根据订单编号orderSn={},查询实体orderEntity={}",orderSn,orderEntity);
		if(Check.NuNObj(orderEntity)||orderEntity.getPayStatus().intValue() == OrderPayStatusEnum.UN_PAY.getPayStatus()||orderEntity.getOrderStatus().intValue() != OrderStatusEnum.FORCED_CANCELLATION.getOrderStatus()){
			LogUtil.info(logger,"当前强制取消订单的订单号orderSn={},查询订单实体orderEntity={}",orderSn,orderEntity);

			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体已不存在或者订单状态不对或者支付状态不对，当前订单应该是已支付且强制取消中的订单");
			return dto.toJsonString();
		}
		int oldOrderStatus = orderEntity.getOrderStatus();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("orderSn", orderEntity.getOrderSn());
		paramsMap.put("parCode", OrderParamEnum.STATUS_BAK.getCode());

		List<OrderParamEntity> listParamEntities = this.orderCommonServiceImpl.findParamByCondiction(paramsMap);
		if(Check.NuNCollection(listParamEntities)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("强制取消订单申请中的之前的状态无法恢复，已经丢失");
			return dto.toJsonString();
		}
		try {
			orderEntity.setOrderStatus(Integer.valueOf(listParamEntities.get(0).getParValue()));
			orderEntity.setLastModifyDate(new Date());
			int index = this.orderCommonServiceImpl.updateLanCancellOrder(opUuid,oldOrderStatus, orderEntity,CommissionerOrderServiceProxy.remark, null);
			dto.putValue("result", index);
		} catch (Exception e) {
			LogUtil.error(logger,"客服强制取消恢复，获取之前状态赋值错误,orderSn={},e={}",orderSn,e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("客服强制取消恢复，获取之前状态赋值错误");
		}

		return dto.toJsonString();
	}

	/**
	 * 强制取消订单
	 * @param orderSn
	 * @param createId
	 * @return
	 */
	@Override
	public String agreeCancelOrde(String orderSn,String createId,Integer cancelType) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(orderSn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("订单号为空");
				return dto.toJsonString();
			}
			orderSn = JsonEntityTransform.json2String(orderSn);

			OrderInfoVo orderEntity = this.orderCommonServiceImpl.getOrderInfoByOrderSn(orderSn);

			if(Check.NuNObj(orderEntity)||orderEntity.getPayStatus().intValue() == OrderPayStatusEnum.UN_PAY.getPayStatus()||orderEntity.getOrderStatus().intValue() != OrderStatusEnum.FORCED_CANCELLATION.getOrderStatus()){
				LogUtil.info(logger,"当前强制取消订单的订单号orderSn={},查询订单实体orderEntity={}",orderSn,orderEntity);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前订单已不存在或者订单状态不对，正确的状态应该是"+OrderPayStatusEnum.HAS_PAY.getStatusName()+"或者"+OrderStatusEnum.FORCED_CANCELLATION.getStatusName());
				return dto.toJsonString();
			}
			//获取当前的取消类型
			OrderCancelTypeEnum cancelTypeEnum = OrderCancelTypeEnum.getByCode(cancelType);
			if(Check.NuNObj(cancelTypeEnum)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("cancelType错误");
				return dto.toJsonString();
			}
			//获取配置信息
			OrderConfigVo config = orderConfigService.getOrderConfigVo(orderSn);
			if(Check.NuNObj(config)){
				throw new BusinessException("订单配置信息OrderConfigVo为空");
			}

			//获取强制取消配置
			String forceJson = cityTemplateService.getConfigForceVo();
			DataTransferObject forceDto = JsonEntityTransform.json2DataTransferObject(forceJson);
			ConfigForceVo configForceVo = null;
			if(forceDto.getCode() == DataTransferObject.SUCCESS){
				configForceVo = forceDto.parseData("configForceVo", new TypeReference<ConfigForceVo>() {
				});
			}else {
				dto.setErrCode(forceDto.getCode());
				dto.setMsg(forceDto.getMsg());
				return forceDto.toJsonString();
			}
			if(!Check.NuNObj(configForceVo)){
				int fristDayPrice = orderUserService.getFristDayPrice(orderEntity.getOrderSn(), orderEntity.getStartTime());
				configForceVo.setDayPrice(fristDayPrice);
			}else {
				throw new BusinessException("强制取消配置configForceVo为空");
			}
			//获取每天的价格map
			Map<String,Integer> priceMap = orderMoneyService.getDayCutPricesMap(orderEntity,orderEntity.getDiscountMoney());
			if(cancelTypeEnum.getCode() == OrderCancelTypeEnum.REFUND.getCode()){
				commissionerOrderServiceImpl.agreeCancelOrdeAndRefund(configForceVo,config,orderEntity,dto,createId,priceMap);
			}else if(cancelTypeEnum.getCode() == OrderCancelTypeEnum.CONTINUE.getCode()){
				//TODO
				//                commissionerOrderServiceImpl.updateCancelOrderAndContinue(configForceVo,config,orderEntity,dto,createId,priceMap);
			}else {
				throw new BusinessException("cancelType错误");
			}
			//封装邮件发送对象（强制取消）
			SendOrderEmailRequest orderEmail=new SendOrderEmailRequest();
			orderEmail.setOrderStartDate(orderEntity.getStartTime());
			orderEmail.setOrderEndDate(orderEntity.getEndTime());
			orderEmail.setOrderStatus("订单被管理员强制取消");
			orderEmail.setLandlordUid(orderEntity.getLandlordUid());
			orderEmail.setBookName(orderEntity.getUserName());
			orderEmail.setCheckInNum(orderEntity.getPeopleNum());
			orderEmail.setHouseName(orderEntity.getHouseName());
			orderEmail.setEmailTitle(orderEntity.getHouseName()+"的"+DateUtil.dateFormat(orderEntity.getStartTime(),"yyyy-MM-dd")+"至"+DateUtil.dateFormat(orderEntity.getEndTime(),"yyyy-MM-dd")+"的订单被管理员强制取消");
			dto.putValue("orderEmail", orderEmail);
		}catch (Exception e){
			LogUtil.debug(logger, "orderSn:{},e:{}",orderSn,e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/*  按条件分页查询 新旧订单
	 * @see com.ziroom.minsu.services.order.api.inner.CommissionerOrderService#queryOrderRelanionByPage(java.lang.String)
	 * @auther yd
	 * @created 2016.04.25
	 * @version 1.0
	 *  
	 * @param orderRelationRequest 查询参数
	 */
	@Override
	public String queryOrderRelanionByPage(String orderRelationRequest) {

		DataTransferObject dto = new DataTransferObject();

		OrderRelationRequest relationRequest = JsonEntityTransform.json2Entity(orderRelationRequest, OrderRelationRequest.class);

		if(Check.NuNObj(relationRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("OrderRelationRequest is null");
			return dto.toJsonString();
		}

		LogUtil.info(logger, "查询参数orderRelationRequest={}", orderRelationRequest);

		PagingResult<OrderRelationVo> listPagingResult = this.orderRelationServiceImpl.queryOrderRelanionByPage(relationRequest);

		dto.putValue("listOrderRelationVo", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}

	/* 
	 * 新订单支付成功后 插入新旧订单关联关系， 存在即更新
	 * 说明： 在待审批的情况下 校验如下，此时状态应该是 未关联  所以没有一下校验
	 *     1.校验新订单是否支付成功，支付失败的不让插入
	 *     2.校验旧订单是否是强制取消状态  否的话 不让插入 打款单
	 * @see com.ziroom.minsu.services.order.api.inner.CommissionerOrderService#insertOrderRelation(java.lang.String)
	 * @auther yd
	 * @created 2016.04.25
	 * @version 1.0
	 * 
	 * @param orderRelationEntity 当前保存的实体
	 */
	@Override
	public String saveOrderRelation(String orderRelationSaveRequest) {

		DataTransferObject dto = new DataTransferObject();

		OrderRelationSaveRequest orderRelationSave = checkOrderRelation(dto, orderRelationSaveRequest);
		if(dto.getCode()!=0){
			return dto.toJsonString();
		}
		//修旧订单关联 也进行了校验  此处应该有关联人信息 状态更新为 待审批
		LogUtil.info(logger, "新旧订单关联信息OrderRelationSaveRequest={}", orderRelationSave.toString());
		int index = this.commissionerOrderServiceImpl.saveOrUpdateOrderRelation(orderRelationSave.getOrderRelationEntity(), orderRelationSave.getPayUid());
		dto.putValue("result", index);
		return dto.toJsonString();
	}


	/* 
	 * 审核通过走更新，并生成打款单
	 * 说明： 在待审批的情况下 校验如下
	 *     1.校验新订单是否支付成功，支付失败的不让插入
	 *     2.校验旧订单是否是强制取消状态  否的话 不让插入 打款单
	 * @see com.ziroom.minsu.services.order.api.inner.CommissionerOrderService#updateOrderRelationByCondition(java.lang.String)
	 * @auther yd
	 * @created 2016.04.25
	 * @version 1.0
	 * 
	 * @param orderRelationEntity 当前保存的实体
	 */
	@Override
	public String updateOrderRelationByCondition(String orderRelationSaveRequest) {
		DataTransferObject dto = new DataTransferObject();

		OrderRelationSaveRequest orderRelationSave = checkOrderRelation(dto, orderRelationSaveRequest);
		if(dto.getCode()!=0){
			return dto.toJsonString();
		}
		int index = this.commissionerOrderServiceImpl.updateOrderRelation(orderRelationSave.getOrderRelationEntity(), orderRelationSave.getPayUid());
		dto.putValue("result", index);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 参数校验
	 *
	 * @author yd
	 * @created 2016年4月25日 下午9:49:46
	 *
	 * @param dto
	 * @param orderRelationSaveRequest
	 * @return
	 */
	private OrderRelationSaveRequest checkOrderRelation(DataTransferObject dto,String orderRelationSaveRequest){


		if(Check.NuNObj(dto)) dto = new DataTransferObject();

		OrderRelationSaveRequest orderRelationSave = JsonEntityTransform.json2Object(orderRelationSaveRequest, OrderRelationSaveRequest.class);

		if(Check.NuNObj(orderRelationSave)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return orderRelationSave;
		}
		OrderRelationEntity orderRelation = orderRelationSave.getOrderRelationEntity();

		if(Check.NuNObj(orderRelation)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单关联实体为空");
			return orderRelationSave;
		}
		//如果状态是未关联 旧不做一下严格检验了
		if(orderRelation.getCheckedStatus().intValue() == CheckedStatusEnum.INIT_CHECKED.getCode()){
			return orderRelationSave;
		}
		if(Check.NuNObj(orderRelation)||Check.NuNObj(orderRelation.getOldOrderSn())||Check.NuNObj(orderRelation.getNewOrderSn())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("新旧订单号错误");
			return orderRelationSave;
		}



		OrderRequest orderRequest  = new OrderRequest();	
		List<String> listOrderSn = new ArrayList<String>();
		listOrderSn.add(orderRelation.getOldOrderSn());
		listOrderSn.add(orderRelation.getNewOrderSn());
		orderRequest.setListOrderSn(listOrderSn);

		List<OrderEntity> listEntities =  this.orderCommonServiceImpl.queryOrderByCondition(orderRequest);
		if(Check.NuNCollection(listEntities)||listEntities.size()<2){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询订单错误");
			return orderRelationSave;
		}
		String receiveUid =  "";
		LogUtil.info(logger, "根据订单编号集合listOrderSn={},查询结果为listEntities={}", listOrderSn.toArray(),listEntities.toArray());
		for (OrderEntity orderEntity : listEntities) {
			//新订单未支付
			if(orderEntity.getOrderSn().equals(orderRelation.getNewOrderSn())&&orderEntity.getPayStatus().intValue() == OrderPayStatusEnum.UN_PAY.getPayStatus()){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("订单号为newOrderSn={"+orderEntity.getOrderSn()+"},未支付");
				return orderRelationSave;
			}
			//老订单状态不为 强制取消 =30
			if(orderEntity.getOrderSn().equals( orderRelation.getOldOrderSn())){
				receiveUid = orderEntity.getUserUid();
				if(orderEntity.getOrderStatus().intValue() != OrderStatusEnum.CANCLE_FORCE.getOrderStatus()){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("旧订单oldOrderSn={"+orderEntity.getOrderSn()+"},当前状态不为{"+OrderStatusEnum.CANCLE_FORCE.getOrderStatus()+"},而是{"+OrderStatusEnum.getOrderStatusByCode(orderEntity.getOrderStatus().intValue()).getStatusName()+"}");
					return orderRelationSave;
				}

			}
		}

		orderRelationSave.setReceiveUid(receiveUid);
		return orderRelationSave;
	}

	/* 获取两个订单的差额
	 * @see com.ziroom.minsu.services.order.api.inner.CommissionerOrderService#getMoneyLast(java.lang.String, java.lang.String)
	 * 
	 * @auther yd
	 * @created 2016.4.27 中午
	 * @param newOrderSn 新订单编号 为已支付 且待入住状态
	 * @param oldOrderSn 老订单编号 必须为强制取消订单
	 */
	@Override
	public String getMoneyLast(String newOrderSn, String oldOrderSn) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(oldOrderSn)||Check.NuNStr(newOrderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("新订单号或者老订单号不存在");
			return dto.toJsonString();
		}

		OrderInfoVo orderOld = orderCommonServiceImpl.getOrderInfoByOrderSn(oldOrderSn);
		OrderInfoVo orderNew = orderCommonServiceImpl.getOrderInfoByOrderSn(newOrderSn);
		if(Check.NuNObj(orderOld)|Check.NuNObj(orderNew)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("老订单不存在，订单号为oldOrderSn={"+oldOrderSn+"},或者新订单不存在,新订单号OrderSn={"+newOrderSn+"}");
			return dto.toJsonString();
		}
		if(orderOld.getOrderStatus() != OrderStatusEnum.CANCLE_FORCE.getOrderStatus()){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单状态不对，应该是"+OrderStatusEnum.CANCLE_FORCE.getStatusName());
			return dto.toJsonString();
		}
		if(orderNew.getPayStatus()==OrderPayStatusEnum.UN_PAY.getPayStatus()||orderNew.getOrderStatus().intValue()!=OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("新订单必须为已付款且状态为待入住，当前新订单的支付状态为PayStatus={"+OrderPayStatusEnum.getPayStatusByCode(orderNew.getPayStatus()).getStatusName()+"},订单状态为OrderStatus={"+OrderStatusEnum.getOrderStatusByCode(orderNew.getOrderStatus().intValue()).getStatusName()+"}");
			return dto.toJsonString();
		}
		int twoOrderBalance = this.commissionerOrderServiceImpl.getTwoOrderBalance(newOrderSn, orderOld);
		dto.putValue("twoOrderBalance", twoOrderBalance);
		return dto.toJsonString();
	}

	/* 根据旧订单号 查询新旧订单关联记录
	 * @see com.ziroom.minsu.services.order.api.inner.CommissionerOrderService#queryByOldOrderSn(com.ziroom.minsu.services.order.dto.OrderRelationRequest)
	 * @auther yd
	 * @created 2016.4.27 中午
	 * 
	 * @param orderRelationRequest 请求参数
	 */
	@Override
	public String queryByCondition(String orderRelationRequest) {

		DataTransferObject dto = new DataTransferObject();

		OrderRelationRequest relationRequest = JsonEntityTransform.json2Entity(orderRelationRequest, OrderRelationRequest.class);

		if(Check.NuNObj(relationRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}

		LogUtil.info(logger,"查询参数orderRelationRequest={}",orderRelationRequest);

		List<OrderRelationEntity> listOrderRelation = this.orderRelationServiceImpl.queryByOldOrderSn(relationRequest);
		dto.putValue("listOrderRelation", listOrderRelation);
		return dto.toJsonString();
	}

	/**
	 * 
	 * (已支付订单)客服取消订单 ——查找用户 订单金额  
	 * 1.获取订单金额相关信息
	 * 2.计算已消费房租
	 * 3.计算房客已消费服务费
	 * 4.计算清洁费
	 * 
	 * 注意: A.消费费用计算 当天时间过下午2点,则算当天,否则不算  B.订单状态(20 , 40 , 41)
	 * 
	 *
	 * @author yd
	 * @created 2017年1月4日 下午8:57:02
	 *
	 * @param orderSn
	 * @param cancleOrderVo
	 */

	@Override
	public String findCancleOrderVo(String orderSn) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStrStrict(orderSn)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("订单号错误");
			return dto.toJsonString();
		}

		OrderInfoVo orderInfo = orderCommonServiceImpl.getOrderInfoByOrderSn(orderSn);

		if(Check.NuNObj(orderInfo)){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("订单不存在,订单号"+orderSn);
			return dto.toJsonString();
		}

		int orderStatu = orderInfo.getOrderStatus();

		if(orderInfo.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("客服取消订单失败,订单未支付");
				return dto.toJsonString();
		}
		if(orderStatu != OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()
				&&orderStatu != OrderStatusEnum.CHECKED_IN.getOrderStatus()
				&&orderStatu != OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("客服取消订单失败,订单状态错误,当前订单状态为"+OrderStatusEnum.getOrderStatusByCode(orderStatu).getStatusName());
			return dto.toJsonString();
		}

		//获取配置信息
        OrderConfigVo config = orderConfigService.getOrderConfigVo(orderSn);
        if (Check.NuNObj(config)) {
            LogUtil.error(logger, "【异常数据】 orerSn:", orderInfo.getOrderSn());
            throw new BusinessException("config is null");
        }
        
        //获取退订政策
        CheckOutStrategy checkOutStrategy = orderConfigService.getCheckOutStrategyByOrderSn(orderSn);
        if (Check.NuNObj(checkOutStrategy)) {
            LogUtil.error(logger, "【客服取消订单,异常数据】 orerSn:", orderInfo.getOrderStatus());
            throw new BusinessException("checkOutStrategy is null");
        }
		CancleOrderVo cancleOrderVo= new CancleOrderVo();
		
		
		this.orderUserService.findCancleOrderVo(checkOutStrategy,orderInfo, cancleOrderVo, config, dto);
		
		dto.putValue("cancleOrderVo", cancleOrderVo);
		return dto.toJsonString();
	}


}
