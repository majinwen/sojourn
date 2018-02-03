package com.ziroom.minsu.services.order.proxy;


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.cms.ActivityCityEntity;
import com.ziroom.minsu.entity.cms.ActivityHouseEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.common.constant.CatConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.OrderHouseDto;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.house.entity.SpecialPriceVo;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.constant.OrderFeeConst;
import com.ziroom.minsu.services.order.constant.OrderMessageConst;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.*;
import com.ziroom.minsu.services.order.service.*;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.cms.ActivityTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.WeekEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.*;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.productrules.ProductRulesTonightDisEnum;
import com.ziroom.minsu.valenum.search.LabelTipsEnum;
import com.ziroom.minsu.valenum.search.LabelTipsStyleEnum;
import com.ziroom.minsu.valenum.traderules.*;
import com.ziroom.minsu.valenum.version.VersionCodeEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * <p>用的代理层</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
@Component("order.orderUserServiceProxy")
public class OrderUserServiceProxy implements OrderUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderUserServiceProxy.class);

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;

	@Resource(name = "order.orderMoneyServiceImpl")
	private OrderMoneyServiceImpl orderMoneyService;

	@Resource(name = "order.orderUserServiceImpl")
	private OrderUserServiceImpl orderUserService;

	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonService;

	@Resource(name = "order.orderConfigServiceImpl")
	private OrderConfigServiceImpl orderConfigService;

	@Resource(name = "order.orderLoadlordServiceImpl")
	private OrderLoadlordServiceImpl orderLoadlordService;

	@Resource(name = "order.houseLockServiceImpl")
	private HouseLockServiceImpl houseLockService;

	// 房源信息
	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name = "order.payVouchersCreateServiceImpl")
	private PayVouchersCreateServiceImpl payVouchersCreateService;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name = "order.financePunishServicesImpl")
	private FinancePunishServicesImpl financePunishServices;

	@Resource(name = "order.orderActivityServiceImpl")
	private OrderActivityServiceImpl orderActivityService;

	@Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;

	@Resource(name = "order.orderSmartLockServiceProxy")
	private OrderSmartLockServiceProxy orderSmartLockServiceProxy;


	@Resource(name = "order.dealPayServiceProxy")
	private DealPayServiceProxy dealPayServiceProxy;

	@Resource(name = "order.orderActivityProxy")
	private OrderActivityProxy orderActivityProxy;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;
	
	@Resource(name="house.queueName")
	private QueueName queueName ;

	/**
	 * 用户确认额外消费
	 *
	 * @param ConfirmOtherMoneyJson
	 * @return
	 */
	public String confirmOtherMoney(String ConfirmOtherMoneyJson) {
		DataTransferObject dto = new DataTransferObject();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(ConfirmOtherMoneyJson);
		}

		ConfirmOtherMoneyRequest request = JsonEntityTransform.json2Object(ConfirmOtherMoneyJson, ConfirmOtherMoneyRequest.class);
		if (Check.NuNStr(request.getUid()) || Check.NuNStr(request.getOrderSn())) {
			//参数异常
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}

		LogUtil.info(LOGGER, "开始确认额外消费，orderSn:{}", request.getOrderSn());
		try {
			OrderInfoVo orderEntity = orderCommonService.getOrderInfoByOrderSn(request.getOrderSn());
			if (Check.NuNObj(orderEntity)) {
				LogUtil.info(LOGGER, "【确认额外消费】 当前订单不存在，orderSn:{}", request.getOrderSn());
				//当前订单不存在
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNStr(orderEntity.getUserUid()) || !orderEntity.getUserUid().equals(request.getUid())) {
				LogUtil.info(LOGGER, "【确认额外消费】 没有操作权限，orderSn:{}，uid：{}", request.getOrderSn(), request.getUid());
				//没有权限
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
				return dto.toJsonString();
			}
			OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(orderEntity.getOrderStatus());
			if (Check.NuNObj(orderStatus)) {

				LogUtil.info(LOGGER, "【确认额外消费】 异常的订单状态，当前状态为：{}，orderSn:{}", orderEntity.getOrderStatus(), request.getOrderSn());
				//异常的订单状态
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}
			//校验当前状态是否可以确认额外消费
			if (orderStatus.getConfirmOtherMoneyStatus() == null) {
				//当前状态异常
				LogUtil.info(LOGGER, "【确认额外消费】 确定之后的订单状态为空，orderSn:{}", request.getOrderSn());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}
			if (orderEntity.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()) {
				//当前状态异常
				LogUtil.info(LOGGER, "【确认额外消费】 当前订单未支付，orderSn:{}", request.getOrderSn());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前订单未支付");
				return dto.toJsonString();
			}
			int last = orderMoneyService.getOtherMoneyLimitOrg(orderEntity);
			if (last < 0) {
				//当前状态异常
				LogUtil.info(LOGGER, "【确认额外消费】 当前订单支付金额异常，orderSn:{}", request.getOrderSn());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前订单支付金额异常");
				return dto.toJsonString();
			}
			//获取当前订单的配置信息
			OrderConfigVo config = orderConfigService.getOrderConfigVo(orderEntity.getOrderSn());
			//直接确定额外消费
			orderLoadlordService.updateConfirmOtherMoneyAndCheck(config, orderEntity, orderStatus.getConfirmOtherMoneyStatus().getOrderStatus(), dto, null, orderEntity.getUserUid());
			/* if (dto.getCode() == DataTransferObject.SUCCESS) {
                //用户确认额外消费成功
                //1.触发房东评价
                orderMsgProxy.sendMsg4CanMarkLand(orderEntity);
                //2.触发用户评价
                orderMsgProxy.sendMsg4CanMarkUser(orderEntity);
            }*/
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_OP_ERROR));
			return dto.toJsonString();
		}

		return dto.toJsonString();
	}


	/**
	 * 获取城市名称
	 * @author afi
	 * @param cityCode
	 * @return
	 */
	private String getCityName(String cityCode){

		try {
			if(Check.NuNObj(cityCode)){
				return cityCode;
			}
			String json = confCityService.getCityNameByCode(cityCode);
			DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(json);
			String cityName = cityDto.parseData("cityName", new TypeReference<String>() {
			});
			return cityName;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询城市名称失败，cityCode:{}", cityCode);
		}
		return "";
	}


	/**
	 * 通过订单号获取快照信息
	 *
	 * @param orderSn
	 * @return
	 * @author afi
	 */
	public String findHouseSnapshotByOrderSn(String orderSn) {
		DataTransferObject dto = new DataTransferObject();

		try {
			if (Check.NuNStr(orderSn)) {
				LogUtil.error(LOGGER, "获取快照信息失败，par:{}", orderSn);
				//参数异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			OrderHouseSnapshotEntity houseSnapshot = orderUserService.findHouseSnapshotByOrderSn(orderSn);
			if (Check.NuNObj(houseSnapshot)) {
				//参数异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前房源快照不存在");
				return dto.toJsonString();
			}
			dto.putValue("houseSnapshot", houseSnapshot);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 锁定房源
	 *
	 * @param locakHouseJson
	 * @return
	 * @author afi
	 */
	public String lockHouse(String locakHouseJson) {
		DataTransferObject dto = new DataTransferObject();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(locakHouseJson);
		}

		LockHouseRequest request = JsonEntityTransform.json2Object(locakHouseJson, LockHouseRequest.class);
		try {
			if (Check.NuNObj(request)) {
				LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
				//参数异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNObj(request.getRentWay()) || Check.NuNCollection(request.getLockDayList())) {
				LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
				//参数异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (request.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.ROOM.getCode()) {
				if (Check.NuNStr(request.getHouseFid()) || Check.NuNStr(request.getRoomFid())) {
					LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.BED.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else {
				LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
				throw new BusinessException("error rentWay");
			}

			List<HouseLockEntity> houseLockEntitys = houseLockService.checkHouseLockList(request);
			if (!Check.NuNObj(houseLockEntitys) && houseLockEntitys.size() > 0) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_LOCK));
				return dto.toJsonString();
			}
			houseLockService.insertHouseLockList(request);
			
			if(!Check.NuNStr(request.getHouseFid())){
				LogUtil.info(LOGGER, "房源锁定成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(request.getHouseFid(), 
						request.getRoomFid(), request.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源锁定成功,推送队列消息结束,推送内容:{}", pushContent);
			}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * PC端锁定房源逻辑(查看选中日期中是已经有锁定记录，则不做处理，否则插入一条新记录)
	 *
	 * @param locakHouseJson
	 * @return
	 * @author jixd
	 * @created 2016年8月4日 下午5:42:41
	 */
	public String lockHouseForPC(String locakHouseJson) {
		DataTransferObject dto = new DataTransferObject();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(locakHouseJson);
		}

		LockHouseRequest request = JsonEntityTransform.json2Object(locakHouseJson, LockHouseRequest.class);
		try {
			if (Check.NuNObj(request)) {
				LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
				//参数异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNObj(request.getRentWay()) || Check.NuNCollection(request.getLockDayList())) {
				LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
				//参数异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (request.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.ROOM.getCode()) {
				if (Check.NuNStr(request.getHouseFid()) || Check.NuNStr(request.getRoomFid())) {
					LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.BED.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else {
				LogUtil.error(LOGGER, "锁房失败，par:{}", locakHouseJson);
				throw new BusinessException("error rentWay");
			}

			int count = houseLockService.lockHouseForPC(request);
			dto.putValue("count", count);
			
			if(!Check.NuNStr(request.getHouseFid())){
				LogUtil.info(LOGGER, "房源锁定成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(request.getHouseFid(), 
						request.getRoomFid(), request.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源锁定成功,推送队列消息结束,推送内容:{}", pushContent);
			}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 获取区间内的房源的锁定信息
	 *
	 * @param houseLockJson
	 * @return
	 * @author afi
	 */
	public String getHouseLockInfo(String houseLockJson) {
		DataTransferObject dto = new DataTransferObject();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(houseLockJson);
		}
		HouseLockRequest request = JsonEntityTransform.json2Object(houseLockJson, HouseLockRequest.class);
		try {
			//返回房源的锁定情况
			dto.putValue("houseLock", houseLockService.getHouseLock(request));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 获取订单的价格列表
	 *
	 * @param orderDetailJson
	 * @return
	 * @author afi
	 */
	public String getOrderPrices(String orderDetailJson) {
		DataTransferObject dto = new DataTransferObject();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(orderDetailJson);
		}
		OrderDetailRequest request = JsonEntityTransform.json2Object(orderDetailJson, OrderDetailRequest.class);
		if (Check.NuNStr(request.getUid()) || Check.NuNStr(request.getOrderSn())) {
			//参数异常
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try {
			OrderEntity orderEntity = orderCommonService.getOrderBaseByOrderSn(request.getOrderSn());
			if (Check.NuNObj(orderEntity)) {
				//当前订单不存在
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNStr(orderEntity.getUserUid()) || !orderEntity.getUserUid().equals(request.getUid())) {
				//没有权限
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
				return dto.toJsonString();
			}
			//订单的每天的价格
			dto.putValue("dayPrice", orderUserService.getDayPrices(orderEntity));

		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 初始化取消订单
	 *
	 * @param orderCancleJson
	 * @return
	 * @author afi
	 */
	public String initCancleOrder(String orderCancleJson) {
		DataTransferObject dto = new DataTransferObject();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(orderCancleJson);
		}
		CanclOrderRequest request = JsonEntityTransform.json2Object(orderCancleJson, CanclOrderRequest.class);
		if (Check.NuNStr(request.getUid()) || Check.NuNStr(request.getOrderSn())) {
			//参数异常
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try {
			OrderInfoVo orderEntity = orderCommonService.getOrderInfoByOrderSn(request.getOrderSn());
			if (Check.NuNObj(orderEntity)) {
				//当前订单不存在
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNStr(orderEntity.getUserUid()) || !orderEntity.getUserUid().equals(request.getUid())) {
				//没有权限
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
				return dto.toJsonString();
			}
			OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(orderEntity.getOrderStatus());
			if (Check.NuNObj(orderStatus)) {
				//异常的订单状态
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}
			//校验当前状态是否可以取消
			OrderStatusEnum cancleStatus = orderStatus.getCancleStatus(orderEntity);
			if (cancleStatus == null) {
				//当前状态不能取消
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_CANCLE));
				return dto.toJsonString();
			}
			CancleStrategyVo cancleStrategyVo = new CancleStrategyVo();
			if (orderEntity.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()) {
				cancleStrategyVo.setHasPay(false);
			} else {
				cancleStrategyVo.setHasPay(true);
				CheckOutStrategy checkOutStrategy = orderConfigService.getCheckOutStrategyByOrderSn(orderEntity.getOrderSn());
				if (Check.NuNObj(checkOutStrategy)) {
					LogUtil.info(LOGGER, "【异常数据】 orerSn:", orderEntity.getOrderSn());
					throw new BusinessException("checkOutStrategy is null");
				}
				BeanUtils.copyProperties(checkOutStrategy, cancleStrategyVo);
				cancleStrategyVo.setHasPayMoney(orderEntity.getPayMoney());
				// 获取订单的价格列表
				Map<String, Integer> priceMap = orderUserService.getDayCutPricesMap(orderEntity, orderEntity.getDiscountMoney());
				int penaltyMoney = FinanceMoneyUtil.getPenaltyMoney(checkOutStrategy, orderEntity, priceMap);
				//清洁费不参与违约金的计算
				int lastMoney = orderEntity.getPayMoney() + orderEntity.getCouponMoney() - orderEntity.getUserCommMoney() - orderEntity.getCleanMoney();
				int realPenaltyMoney = ValueUtil.getMin(lastMoney, penaltyMoney);
				//设置优惠券的金额
				cancleStrategyVo.setCouponMoney(orderEntity.getCouponMoney());
				cancleStrategyVo.setPenaltyMoney(realPenaltyMoney);
				//                cancleStrategyVo.setUserComm(orderEntity.getUserCommMoney());
			}
			dto.putValue("cancleInfo", cancleStrategyVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 取消订单
	 *
	 * @param orderCancleJson
	 * @return
	 * @author afi
	 */
	public String cancleOrder(String orderCancleJson) {
		DataTransferObject dto = new DataTransferObject();
		if (LOGGER.isDebugEnabled()) {
			LogUtil.debug(LOGGER, "{}", orderCancleJson);
		}

		CanclOrderRequest request = JsonEntityTransform.json2Object(orderCancleJson, CanclOrderRequest.class);

		if (Check.NuNStr(request.getUid()) || Check.NuNStr(request.getOrderSn())) {
			//参数异常
			LogUtil.info(LOGGER, "取消失败，par:{}", orderCancleJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try {
			OrderInfoVo orderInfo = orderCommonService.getOrderInfoByOrderSn(request.getOrderSn());
			if (Check.NuNObj(orderInfo)) {
				//当前订单不存在
				LogUtil.info(LOGGER, "取消失败 当前订单不存在，par:{}", orderCancleJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNStr(orderInfo.getUserUid()) || !orderInfo.getUserUid().equals(request.getUid())) {
				//没有权限
				LogUtil.info(LOGGER, "取消失败，没有权限par:{}", orderCancleJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
				return dto.toJsonString();
			}
			OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(orderInfo.getOrderStatus());
			if (Check.NuNObj(orderStatus)) {
				//没有权限
				LogUtil.info(LOGGER, "取消失败，异常的订单状态par:{}", orderCancleJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}
			//校验当前状态是否可以取消
			OrderStatusEnum cancleStatus = orderStatus.getCancleStatus(orderInfo);
			if (cancleStatus == null) {
				//当前状态不能取消
				LogUtil.info(LOGGER, "取消失败，当前状态不能取消par:{}", orderCancleJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_CANCLE));
				return dto.toJsonString();
			}
			//获取退订政策
			CheckOutStrategy checkOutStrategy = orderConfigService.getCheckOutStrategyByOrderSn(request.getOrderSn());
			if (Check.NuNObj(checkOutStrategy)) {
				LogUtil.info(LOGGER, "【异常数据】 取消订单 orerSn:", orderInfo.getOrderSn());
				throw new BusinessException("checkOutStrategy is null");
			}
			//获取订单配置
			OrderConfigVo config = orderConfigService.getOrderConfigVo(request.getOrderSn());
			if (Check.NuNObj(config)) {
				LogUtil.info(LOGGER, "【异常数据】 取消订单 orerSn:", orderInfo.getOrderSn());
				throw new BusinessException("config is null");
			}

			Transaction orderPayTran = Cat.newTransaction("OrderUserServiceProxy", CatConstant.CANCEL_PAY);
			try {
				//支付之后用户主动取消的订单数量的埋点
				Cat.logMetricForCount("支付之后取消订单数量");
				orderPayTran.setStatus(Message.SUCCESS);
			} catch (Exception ex) {
				Cat.logError("支付之后取消订单数量 打点异常", ex);
				orderPayTran.setStatus(ex);
			} finally {
				orderPayTran.complete();
			}
			int penaltyMoney = 0;
			//1.取消订单
			if (orderInfo.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()) {
				//未支付  直接取消  不走退款
				String couponSn = orderActivityService.getCouponByOrderSn(orderInfo.getOrderSn());
				if (!Check.NuNStr(couponSn)) {
					dto.putValue("couponSn", couponSn);
				}
				orderUserService.cancleOrderOnly(orderInfo, request);
			} else {
				//获取违约金  未产生 违约金为0  产生违约金 则返回违约金
				penaltyMoney = orderUserService.cancelOrderAndRefund(checkOutStrategy, config, orderInfo, request);
			}

			//2.退房调用智能锁
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				LogUtil.info(LOGGER, "【退房】退房调用智能锁失效：orderSn:{}", orderInfo.getOrderSn());
				String json = orderSmartLockServiceProxy.closeSmartlockPswd(orderInfo.getOrderSn());
				DataTransferObject lockDto = JsonEntityTransform.json2DataTransferObject(json);
				LogUtil.info(LOGGER, "【退房】调用智能锁返回结果：json:{}", json);
				if (lockDto.getCode() != DataTransferObject.ERROR) {
					LogUtil.error(LOGGER, "【退房】调用智能锁返回失败：json:{}", json);
				}
			}
			//3.取消完成之后发送短信
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				orderMsgProxy.sendMsg4Cancel(orderInfo, penaltyMoney);
				//组合发邮件参数
				SendOrderEmailRequest orderEmailRequest=new SendOrderEmailRequest();
				orderEmailRequest.setOrderStartDate(orderInfo.getStartTime());
				orderEmailRequest.setOrderEndDate(orderInfo.getEndTime());
				orderEmailRequest.setOrderStatus("订单被取消");
				orderEmailRequest.setLandlordUid(orderInfo.getLandlordUid());
				orderEmailRequest.setBookName(orderInfo.getUserName());
				orderEmailRequest.setCheckInNum(orderInfo.getPeopleNum());
				if(orderInfo.getRentWay()==RentWayEnum.HOUSE.getCode()){
					orderEmailRequest.setHouseName(orderInfo.getHouseName());
				} else if(orderInfo.getRentWay()==RentWayEnum.ROOM.getCode()) {
					orderEmailRequest.setHouseName(orderInfo.getRoomName());
				}
				orderEmailRequest.setEmailTitle(orderEmailRequest.getHouseName()+"的"+DateUtil.dateFormat(orderInfo.getStartTime(),"yyyy-MM-dd")+"至"+DateUtil.dateFormat(orderInfo.getEndTime(),"yyyy-MM-dd")+"订单被取消");
				dto.putValue("orderEmailRequest", orderEmailRequest);
			}


		} catch (Exception e) {
			LogUtil.error(LOGGER, "{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 初始化退房
	 *
	 * @param orderCheckOutJson
	 * @return
	 * @author lishaochuan
	 * @create 2016年5月3日
	 */
	@Override
	public String initCheckOutOrder(String orderCheckOutJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			LogUtil.info(LOGGER, "orderCheckOutJson:{}", orderCheckOutJson);
			CheckOutOrderRequest request = JsonEntityTransform.json2Object(orderCheckOutJson, CheckOutOrderRequest.class);

			if (Check.NuNStr(request.getUid()) || Check.NuNStr(request.getOrderSn())) {
				//参数异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}

			String orderSn = request.getOrderSn();
			OrderInfoVo orderInfo = orderCommonService.getOrderInfoByOrderSn(orderSn);
			if (Check.NuNObj(orderInfo)) {
				//该uid用户的订单不存在
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
				return dto.toJsonString();
			}
			if (!request.getUid().equals(orderInfo.getUserUid())) {
				//该uid没有权限查看
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
				return dto.toJsonString();
			}
			if (orderInfo.getOrderStatus() != OrderStatusEnum.CHECKED_IN.getOrderStatus() && orderInfo.getOrderStatus() != OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()) {
				// 非正常状态退房
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}

			CheckOutStrategyVo checkOutStrategyVo = new CheckOutStrategyVo();
			if (orderCommonService.checkOutAdvance(orderInfo)) {
				// 提前退房
				//获取配置信息
				OrderConfigVo config = orderConfigService.getOrderConfigVo(orderSn);
				if (Check.NuNObj(config)) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("订单配置错误");
					return dto.toJsonString();
				}
				//获取退订政策
				CheckOutStrategy checkOutStrategy = orderConfigService.getCheckOutStrategyByOrderSn(request.getOrderSn());
				if (Check.NuNObj(checkOutStrategy)) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("退订政策错误");
					return dto.toJsonString();
				}
				// 获取订单的价格列表
				Map<String, Integer> priceMap = orderUserService.getDayCutPricesMap(orderInfo, orderInfo.getDiscountMoney());
				// 获取当前订单在当前时间的房租
				int rentalMoney = FinanceMoneyUtil.getRealRentalMoney(config, orderInfo, checkOutStrategy.getDealTime(), priceMap);
				//设置当前的真实的房租为违约金的计算提供依赖
				checkOutStrategy.setRealRentalMoney(rentalMoney);
				// 理论上的违约金
				int planPenaltyMoney = FinanceMoneyUtil.getPenaltyMoney(checkOutStrategy, orderInfo, priceMap);
				//用户真实的佣金
				int realUserCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config, orderInfo, rentalMoney, null, UserTypeEnum.TENANT);
				// 公式：支付金额 + 优惠券 + 活动金额 - 租客支付佣金金额  - 实际发生房租金额 - 清洁费
				int lastMoney = orderInfo.getPayMoney() + orderInfo.getCouponMoney() + orderInfo.getActMoney() - orderInfo.getUserCommMoney() - rentalMoney - orderInfo.getCleanMoney();
				if (lastMoney < 0) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("支付金额为空");
					return dto.toJsonString();
				}
				// 设置违约金额 需要比对当前的剩余金额是否不够支付违约金
				int realPenaltyMoney = ValueUtil.getMin(planPenaltyMoney, lastMoney);
				checkOutStrategyVo.setIsEarly(true);
				checkOutStrategyVo.setPenaltyMoney(realPenaltyMoney);
				checkOutStrategyVo.setUserComm(realUserCommMoney);
			} else {
				// 正常退房
				checkOutStrategyVo.setIsEarly(false);
				checkOutStrategyVo.setPenaltyMoney(0);
			}
			checkOutStrategyVo.setIsLock(orderInfo.getIsLock());
			dto.putValue("checkOutInfo", checkOutStrategyVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 退房
	 *
	 * @param orderCheckOutJson
	 * @return
	 * @author afi
	 */
	public String checkOutOrder(String orderCheckOutJson) {
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER, "【退房】参数:{}", orderCheckOutJson);
		CheckOutOrderRequest request = JsonEntityTransform.json2Object(orderCheckOutJson, CheckOutOrderRequest.class);
		;
		if (Check.NuNStr(request.getUid()) || Check.NuNStr(request.getOrderSn())) {
			//参数异常
			LogUtil.error(LOGGER, "【退房】退房失败：参数为空par:{}", orderCheckOutJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}

		String orderSn = request.getOrderSn();
		OrderInfoVo orderInfo = orderCommonService.getOrderInfoByOrderSn(orderSn);

		if (Check.NuNObj(orderInfo)) {
			LogUtil.error(LOGGER, "【退房】退房失败：订单不存在 par:{}", orderCheckOutJson);
			//该uid用户的订单不存在
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
			return dto.toJsonString();
		}
		if (!request.getUid().equals(orderInfo.getUserUid()) && !"001".equals(request.getUid())) {
			LogUtil.error(LOGGER, "【退房】退房失败：无权限 par:{}", orderCheckOutJson);
			//该uid没有权限查看
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
			return dto.toJsonString();
		}


		orderInfo.setCityName(getCityName(orderInfo.getCityCode()));


		//如果当前订单未生成付款单，直接出发生成付款单
		if (orderInfo.getOrderStatus() == OrderStatusEnum.CHECKED_IN.getOrderStatus()) {
			LogUtil.info(LOGGER, "【退房】当前订单还未生成付款计划  开始生成付款计划：par:{}", orderCheckOutJson);
			try {
				// 获取订单配置：优惠折扣、佣金、结算方式
				OrderConfigVo orderConfigVo = orderConfigService.getOrderConfigVo(orderSn);
				// 获取订单每天的价格
				List<OrderDayPriceVo> dayPrice = orderUserService.getDayPrices(orderInfo);
				payVouchersCreateService.createFinance(orderInfo, orderConfigVo, dayPrice);
			} catch (Exception e) {

				LogUtil.error(LOGGER, "【退房】生成退款计划失败：par:{}", orderCheckOutJson);
				throw new BusinessException(e);
			}
			orderInfo.setOrderStatus(OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus());
		} else if (orderInfo.getOrderStatus() == OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()) {
			//正常的退房状态
		} else {
			LogUtil.error(LOGGER, "【退房】退房失败 异常的订单状态：par:{}", orderCheckOutJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
			return dto.toJsonString();
		}
		String msg = "已";
		if (orderCommonService.checkOutAdvance(orderInfo)) {
			if ("001".equals(request.getUid())) {
				LogUtil.error(LOGGER, "【退房】定时任务不能提前退房：orderSn:{}", orderInfo.getOrderSn());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}

			LogUtil.info(LOGGER, "【退房】开始提前退房：orderSn:{}", orderInfo.getOrderSn());
			//获取配置信息
			OrderConfigVo config = orderConfigService.getOrderConfigVo(orderSn);
			if (Check.NuNObj(config)) {
				LogUtil.error(LOGGER, "【异常数据】 orerSn:", orderInfo.getOrderSn());
				throw new BusinessException("config is null");
			}
			//获取退订政策
			CheckOutStrategy checkOutStrategy = orderConfigService.getCheckOutStrategyByOrderSn(request.getOrderSn());
			if (Check.NuNObj(checkOutStrategy)) {
				LogUtil.error(LOGGER, "【异常数据】 orerSn:", orderInfo.getOrderStatus());
				throw new BusinessException("checkOutStrategy is null");
			}

			Transaction orderPayTran2 = Cat.newTransaction("OrderUserServiceProxy", CatConstant.CHECK_OUT_PRE);
			try {
				//支付之后用户主动取消的订单数量的埋点
				Cat.logMetricForCount("提前退房数量");
				orderPayTran2.setStatus(Message.SUCCESS);
			} catch (Exception ex) {
				Cat.logError("提前退房数量 打点异常", ex);
				orderPayTran2.setStatus(ex);
			} finally {
				orderPayTran2.complete();
			}
			//提前退房
			this.checkOutOrderPre(checkOutStrategy, config, dto, orderInfo);
			msg += "提前";
			//封装邮件发送对象提前退房
			SendOrderEmailRequest orderEmail=new SendOrderEmailRequest();
			orderEmail.setOrderStartDate(orderInfo.getStartTime());
			orderEmail.setOrderEndDate(orderInfo.getEndTime());
			orderEmail.setCheckInDate(orderInfo.getStartTime());
			orderEmail.setCheckOutDate(new Date());
			orderEmail.setOrderStatus("订单已提前退房，待您确认其他消费");
			orderEmail.setLandlordUid(orderInfo.getLandlordUid());
			orderEmail.setBookName(orderInfo.getUserName());
			orderEmail.setCheckInNum(orderInfo.getPeopleNum());
			if(orderInfo.getRentWay()==RentWayEnum.HOUSE.getCode()){
				orderEmail.setHouseName(orderInfo.getHouseName());
			} else if(orderInfo.getRentWay()==RentWayEnum.ROOM.getCode()) {
				orderEmail.setHouseName(orderInfo.getRoomName());
			}
			orderEmail.setEmailTitle(orderEmail.getHouseName()+"的"+DateUtil.dateFormat(orderInfo.getStartTime(),"yyyy-MM-dd")+"至"+DateUtil.dateFormat(orderInfo.getEndTime(),"yyyy-MM-dd")+"的订单已提前退房，待您确认其他消费");
			dto.putValue("orderEmail", orderEmail);
		} else {
			//正常退房
			LogUtil.info(LOGGER, "【退房】开始正常退房：orderSn:{}", orderInfo.getOrderSn());
			this.checkOutOrderCommon(dto, orderInfo, request);
			//封装邮件发送对象正常退房
			SendOrderEmailRequest orderEmail=new SendOrderEmailRequest();
			orderEmail.setOrderStartDate(orderInfo.getStartTime());
			orderEmail.setOrderEndDate(orderInfo.getEndTime());
			orderEmail.setCheckInDate(orderInfo.getStartTime());
			orderEmail.setCheckOutDate(orderInfo.getEndTime());
			orderEmail.setOrderStatus("订单待您确认其他消费");
			orderEmail.setLandlordUid(orderInfo.getLandlordUid());
			orderEmail.setBookName(orderInfo.getUserName());
			orderEmail.setCheckInNum(orderInfo.getPeopleNum());
			if(orderInfo.getRentWay()==RentWayEnum.HOUSE.getCode()){
				orderEmail.setHouseName(orderInfo.getHouseName());
			} else if(orderInfo.getRentWay()==RentWayEnum.ROOM.getCode()) {
				orderEmail.setHouseName(orderInfo.getRoomName());
			}
			orderEmail.setEmailTitle(orderEmail.getHouseName()+"的"+DateUtil.dateFormat(orderInfo.getStartTime(),"yyyy-MM-dd")+"至"+DateUtil.dateFormat(orderInfo.getEndTime(),"yyyy-MM-dd")+"的订单待您确认其他消费");
			dto.putValue("orderEmail", orderEmail);
		}
		//退房调用智能锁
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			LogUtil.info(LOGGER, "【退房】退房调用智能锁失效：orderSn:{}", orderInfo.getOrderSn());
			String json = orderSmartLockServiceProxy.closeSmartlockPswd(orderSn);
			LogUtil.info(LOGGER, "【退房】调用智能锁返回结果：json:{}", json);
		}
		//退房之后给房东发送消息
		if (dto.getCode() == DataTransferObject.SUCCESS) {

			orderMsgProxy.sendMsg4CheckOut(orderInfo, msg);
		}

		return dto.toJsonString();
	}

	/**
	 * 提前退房操作
	 *
	 * @param checkOutStrategy
	 * @param config
	 * @param dto
	 * @param orderInfo
	 * @author afi
	 */
	private void checkOutOrderPre(CheckOutStrategy checkOutStrategy, OrderConfigVo config, DataTransferObject dto, OrderInfoVo orderInfo) {
		//参数为空直接返回
		if (Check.NuNObjs(dto, orderInfo)) {
			return;
		}
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			orderUserService.updateAndDealCheckOutPre(checkOutStrategy, config, orderInfo);
		}
	}

	/**
	 * 正常的退房操作
	 *
	 * @param dto
	 * @param orderInfo
	 * @author afi
	 */
	private void checkOutOrderCommon(DataTransferObject dto, OrderInfoVo orderInfo, CheckOutOrderRequest request) {
		//参数为空直接返回
		if (Check.NuNObjs(dto, orderInfo)) {
			return;
		}
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			//订单信息
			OrderEntity order = new OrderEntity();
			order.setOrderSn(orderInfo.getOrderSn());
			order.setOrderStatus(OrderStatusEnum.CHECKING_OUT.getOrderStatus());
			order.setRealEndTime(new Date());
			//订单金额
			OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
			moneyEntity.setOrderSn(orderInfo.getOrderSn());
			moneyEntity.setRealUserMoney(orderInfo.getUserCommMoney());
			moneyEntity.setRealLanMoney(orderInfo.getLanCommMoney());
			//正常退款退款金额是押金
			moneyEntity.setRefundMoney(orderInfo.getDepositMoney());
			LogUtil.info(LOGGER, "【正常结算】 退款金额就是押金：{} 房东佣金就是正常佣金{}，用户佣金就是正常佣金{}", orderInfo.getDepositMoney(), orderInfo.getLanCommMoney(), orderInfo.getUserCommMoney());
			int count = orderCommonService.updateOrderInfoAndStatus(request.getUid(), orderInfo.getOrderStatus(), order, moneyEntity, request.getRemark(), null);
			if (count == 0) {
				//当前状态异常
				LogUtil.error(LOGGER, "【正常结算】 更新订单信息失败 orderSn：{} oldorderStatus：{}", orderInfo.getOrderSn(), orderInfo.getOrderStatus());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
			}
		}
	}


	/**
	 * 查看订单详情
	 * @author afi
	 * @param orderDetailJson
	 * @return
	 */
	/*   public String getOrderDetail(String orderDetailJson){
        DataTransferObject dto = new DataTransferObject();

        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER, "{}", orderDetailJson);
        }
        OrderDetailRequest request = JsonEntityTransform.json2Object(orderDetailJson,OrderDetailRequest.class);
        if(Check.NuNStr(request.getUid()) || Check.NuNStr(request.getOrderSn())){
            //参数异常
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        try{
            OrderDetailVo detail = this.orderCommonService.orderHouseToOrderDetail(request.getOrderSn());
            if(Check.NuNObj(detail)){
                //当前订单不存在
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
                return dto.toJsonString();
            }
            if(Check.NuNStr(detail.getUserUid()) || !detail.getUserUid().equals(request.getUid())){
                //没有权限
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
                return dto.toJsonString();
            }
            dto.putValue("detail",detail);
        }catch (Exception e){
            LogUtil.debug(LOGGER, "{}", e.getMessage());
            e.printStackTrace();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }*/


	/**
	 * 获取订单需支付金额明细
	 *
	 * @param requestJson
	 * @return
	 * @author lishaochuan
	 */
	@Override
	public String getNeedPayFee(String requestJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			NeedPayFeeRequest request = JsonEntityTransform.json2Object(requestJson, NeedPayFeeRequest.class);

			// 处理需要支付金额数据
			OrderNeedHouseVo houseInfo = this.getHouseInfo(dto, request);
			OrderSaveVo orderSaveInfo = this.dealNeedPayData(request, dto, houseInfo);
			if (dto.getCode() != DataTransferObject.SUCCESS) {
				return dto.toJsonString();
			}
			OrderMoneyEntity orderMoney = orderSaveInfo.getOrderMoney();
			// 总房租=折扣后房租 +折扣金额
			dto.putValue("cost", orderMoney.getRentalMoney());
			// 押金
			dto.putValue("depositMoney", orderMoney.getDepositMoney());
			// 用户佣金
			dto.putValue("userCommission", orderMoney.getUserCommMoney());
			// 折扣金额
			dto.putValue("discountMoney", orderMoney.getDiscountMoney());
			// 优惠券金额
			dto.putValue("couponMoney", orderMoney.getCouponMoney());
			// 普通活动金额
			dto.putValue("actMoney", orderMoney.getActMoney());
			// 需要支付金额
			dto.putValue("needPay", orderMoney.getNeedPay());
			// 清洁费
			dto.putValue("cleanMoney", orderMoney.getCleanMoney());
			// 当前的城市
			dto.putValue("cityCode", houseInfo.getCityCode());
			// 需要比对的消费金额
			dto.putValue("baseMoney", orderMoney.getRentalMoney() + orderMoney.getUserCommMoney() + orderMoney.getCleanMoney());
			dto.putValue("startTime", DateUtil.timestampFormat(orderSaveInfo.getOrder().getStartTime()));
			dto.putValue("endTime", DateUtil.timestampFormat(orderSaveInfo.getOrder().getEndTime()));
			dto.putValue("allPriceMap", orderSaveInfo.getAllPriceMap());
			dto.putValue("houseSn", houseInfo.getHouserOrRoomSn());
			if(!Check.NuNObj(orderSaveInfo.getRentCut())){
				dto.putValue("rentCut",orderSaveInfo.getRentCut());
			}


		} catch (Exception e) {
			LogUtil.error(LOGGER, "参数:{}", requestJson);
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 获取订单需支付金额明细-新
	 *
	 * @param requestJson
	 * @return
	 * @author lishaochuan
	 * @create 2016年8月19日下午3:13:39
	 */
	@Override
	public String needPay(String requestJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			NeedPayFeeRequest request = JsonEntityTransform.json2Object(requestJson, NeedPayFeeRequest.class);

			// 处理需要支付金额数据
			OrderNeedHouseVo houseInfo = this.getHouseInfo(dto, request);
			OrderSaveVo orderSaveInfo = this.dealNeedPayData(request, dto, houseInfo);
			if (dto.getCode() != DataTransferObject.SUCCESS) {
				return dto.toJsonString();
			}

			OrderMoneyEntity orderMoney = orderSaveInfo.getOrderMoney();
			NeedPayFeeResponse need = new NeedPayFeeResponse();
			need.setTotalFee(orderMoney.getNeedPay());
			need.setFeeUnit(OrderFeeConst.FEE_UNIT.getShowName());

			need.setLabelTipsList(getLabelTipsList(houseInfo,orderSaveInfo));			

			int originalPrice = orderSaveInfo.getOriginalPrice();

			int originalUserCommMoney =orderSaveInfo.getOrderMoney().getUserCommMoney();
			HouseConfMsgEntity  userCommMoneyConfMsgEntity = null;
			for(HouseConfMsgEntity  confMsgEntity :houseInfo.getHouseConfList()){
				if (confMsgEntity.getDicCode().equals(TradeRulesEnum009Enum.TradeRulesEnum009001.getValue())) {
					userCommMoneyConfMsgEntity = confMsgEntity;
				}
			}

			if (!Check.NuNObj(userCommMoneyConfMsgEntity)) {
				originalUserCommMoney = Double.valueOf(originalPrice * ValueUtil.getdoubleValue(userCommMoneyConfMsgEntity.getDicVal())).intValue(); 
			}

			originalPrice = originalPrice + originalUserCommMoney			
					+ orderSaveInfo.getOrderMoney().getDepositMoney()
					+ orderSaveInfo.getOrderMoney().getCleanMoney();

			if (orderMoney.getNeedPay()<originalPrice && !Check.NuNCollection(need.getLabelTipsList()) && !Check.NuNObj(need.getLabelTipsList().get(0)) 
					&& !Check.NuNStr(need.getLabelTipsList().get(0).getName())) {

				String labelTipsStr = need.getLabelTipsList().get(0).getName();

				if(labelTipsStr.contains(LabelTipsEnum.IS_TODAY_DISCOUNT.getName())
						|| labelTipsStr.contains(LabelTipsEnum.IS_JIAXIN_DISCOUNT1.getName())
						|| labelTipsStr.contains(LabelTipsEnum.IS_JIAXIN_DISCOUNT2.getName())
						|| labelTipsStr.contains(LabelTipsEnum.IS_WEEK_DISCOUNT.getName().substring(0, 2))
						|| labelTipsStr.contains(LabelTipsEnum.IS_MONTH_DISCOUNT.getName().substring(0, 2))){ 
					need.setOriginalPrice(originalPrice);
				}

			}else{
				need.setOriginalPrice(null);
			}
            Integer versionCode = request.getVersionCode();
            this.addItemFee(need, orderMoney.getRentalMoney(), versionCode, OrderFeeConst.NEED_PAY_COST, orderSaveInfo);
            //版本处理 不加押金 不加优惠券 不加首单立减活动金额
            if (!Check.NuNObj(versionCode) && versionCode <= 100019) {
                this.addItemFee(need, orderMoney.getDepositMoney(), versionCode, OrderFeeConst.NEED_PAY_DEPOSIT, orderSaveInfo);
                this.addItemFee(need, orderMoney.getCouponMoney(), versionCode, OrderFeeConst.NEED_PAY_COUPON, orderSaveInfo);
                this.addItemFee(need, orderMoney.getActMoney(), versionCode, OrderFeeConst.FIRST_ORDER_REDUC, orderSaveInfo);
            }
            this.addItemFee(need, orderMoney.getUserCommMoney(), versionCode, OrderFeeConst.NEED_PAY_USER_COMMISSION, orderSaveInfo);
            this.addItemFee(need, orderMoney.getCleanMoney(), versionCode, OrderFeeConst.NEED_PAY_CLEAN, orderSaveInfo);
            this.addItemFee(need, orderMoney.getDiscountMoney(), versionCode, OrderFeeConst.NEED_PAY_DISCOUNT, orderSaveInfo);


            dto = new DataTransferObject(DataTransferObject.SUCCESS, "", need.toMap());
			if(Check.NuNObj(orderMoney.getCouponMoney()) || orderMoney.getCouponMoney() == 0){
				dto.putValue("couponMoney", "");
			}else{
				dto.putValue("couponMoney", "-" + OrderFeeConst.FEE_UNIT.getShowName() + DataFormat.formatHundredPrice(orderMoney.getCouponMoney()));
			}

			if(Check.NuNObj(orderMoney.getActMoney()) || orderMoney.getActMoney() == 0){
				dto.putValue("actMoney", "");
			}else{
				dto.putValue("actMoney", "-" + OrderFeeConst.FEE_UNIT.getShowName() + DataFormat.formatHundredPrice(orderMoney.getActMoney()));
			}

			dto.putValue("notices", orderSaveInfo.getCheckOutRulesCode());

			// 入住人数限制
			int checkInLimit = 0;
			if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode()) {
				checkInLimit = ValueUtil.getintValue(houseInfo.getRoomCheckInLimit());
			} else {
				checkInLimit = ValueUtil.getintValue(houseInfo.getHouseBaseExtEntity().getCheckInLimit());
			}
			dto.putValue("checkInLimit", checkInLimit);

			// 最少入住天数
			int minDay = 0;
			if (!Check.NuNObj(houseInfo.getHouseBaseExtEntity().getMinDay())) {
				minDay = houseInfo.getHouseBaseExtEntity().getMinDay();
			}
            if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNObj(houseInfo.getHouseRoomExtEntity())) {
                minDay = houseInfo.getHouseRoomExtEntity().getMinDay();
            }
            dto.putValue("minDay", minDay);

			dto.putValue("orderType", houseInfo.getHouseBaseExtEntity().getOrderType());
			dto.putValue("orderTypeName", OrderTypeEnum.getEnumMap().get(houseInfo.getHouseBaseExtEntity().getOrderType()));
			dto.putValue("housingDay", orderSaveInfo.getDayCount());

            //需要判断版本号
            if (!Check.NuNObj(request.getVersionCode()) && request.getVersionCode() > 100019) {
                if (!Check.NuNObj(need.getOriginalPrice())) {
                    originalPrice = originalPrice - orderMoney.getDepositMoney();
                    dto.putValue("originalPrice", originalPrice);
                }
                LogUtil.info(LOGGER, "neePay orderMoney={}", JsonEntityTransform.Object2Json(orderMoney));
                LogUtil.info(LOGGER, "totalFee={}", orderMoney.getNeedPay() - orderMoney.getDepositMoney() + orderMoney.getCouponMoney() + orderMoney.getActMoney());
                LogUtil.info(LOGGER, "orderFee={}", orderMoney.getNeedPay() - orderMoney.getDepositMoney());
                //增加押金  新增总额不计算押金
                NeedPayFeeRuleResponse deposit = new NeedPayFeeRuleResponse();
                deposit.setName(OrderFeeConst.NEED_PAY_DEPOSIT.getShowName());
                deposit.setFee(DataFormat.formatHundredPrice(orderMoney.getDepositMoney()));
                deposit.setSymbol(OrderFeeConst.NEED_PAY_DEPOSIT.getSymbol());
                deposit.setColorType(OrderFeeConst.NEED_PAY_DEPOSIT.getColorType());
                deposit.setTitle1(OrderFeeConst.NEED_PAY_DEPOSIT.getDescForTenant());
                dto.putValue("deposit", deposit);
                dto.putValue("totalFee", orderMoney.getNeedPay() - orderMoney.getDepositMoney() + orderMoney.getCouponMoney() + orderMoney.getActMoney());
                dto.putValue("orderFee", orderMoney.getNeedPay() - orderMoney.getDepositMoney());
            }
            //添加活动展示项
            fillActivityItemShow(dto, orderSaveInfo);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "needPay 参数:{}", requestJson);
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 *
	 * 添加活动相关项目
	 *
	 * @author yd
	 * @created 2017年6月8日 上午9:57:29
	 *
	 * @param dto
	 * @param orderSaveInfo
	 */
	public void fillActivityItemShow(DataTransferObject dto,OrderSaveVo orderSaveInfo){

		if(Check.NuNObjs(dto,orderSaveInfo)) return ;
		if(dto.getCode() == DataTransferObject.ERROR){
			return;
		}
		List<OrderActivityEntity> listAcVo  = orderSaveInfo.getOrderActivitys();
		if(!Check.NuNCollection(listAcVo)){
			List<NeedPayFeeAcItemResponse> acItemList = new ArrayList<NeedPayFeeAcItemResponse>();
			for (OrderActivityEntity orderActivityEntity : listAcVo) {
				ActivityTypeEnum acEnum = ActivityTypeEnum.getByActivityType(orderActivityEntity.getAcType());
				if(!Check.NuNObj(acEnum)&&acEnum.getCode() ==ActivityTypeEnum.FIRST_ORDER_REDUC.getCode() ){
					addAcItem(acItemList, orderActivityEntity.getAcMoney(), acEnum, orderSaveInfo);
				}

			}
			dto.putValue("acItemList", acItemList);
		}


	}
	/**
	 * 
	 * 获取提示标签
	 *
	 * @author zl
	 * @created 2017年3月9日 下午5:11:57
	 *
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @return
	 */
	private List<LabelTipsEntity> getLabelTipsList(OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo){

		List<LabelTipsEntity> resultList = new ArrayList<>();

		if (Check.NuNObj(houseInfo) || Check.NuNObj(orderSaveInfo)) {
			return resultList;
		}

		List<LabelTipsEntity> labelTipsList = new ArrayList<>();
		int index = 1;

		Map<String,Object> priceEnumDicMap = orderSaveInfo.getPriceEnumDicMap();
		if (Check.NuNMap(priceEnumDicMap)) {
			return resultList;
		}

		//当天
		if (!Check.NuNObj(priceEnumDicMap.get(ProductRulesEnum020.ProductRulesEnum020001.getValue()))) {
			LabelTipsEntity labelEntity = new LabelTipsEntity();
			labelEntity.setIndex(index);
			labelEntity.setName(LabelTipsEnum.IS_TODAY_DISCOUNT.getName());
			labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
			labelTipsList.add(labelEntity);
			index++;
		}	
		//今夜特价
		if (!Check.NuNObj(priceEnumDicMap.get(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode()))) {
			LabelTipsEntity labelEntity = new LabelTipsEntity();
			labelEntity.setIndex(index);
			labelEntity.setName(LabelTipsEnum.IS_JYTJ_DISCOUNT.getName());
			labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
			labelTipsList.add(labelEntity);
			index++;
		}	
		//周租
		if (!Check.NuNObj(priceEnumDicMap.get(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()))) {
			LabelTipsEntity labelEntity = new LabelTipsEntity();
			labelEntity.setIndex(index);

			Double discount = ValueUtil.getdoubleValue(priceEnumDicMap.get(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()));
			if (!Check.NuNObj(discount)) {
				discount =BigDecimalUtil.div(discount, 10, 1);
			} 
			String discountStr = String.valueOf(discount);
			if (discountStr.length()>3) {
				discountStr = discountStr.substring(0,3);
			} 				
			labelEntity.setName(String.format(LabelTipsEnum.IS_WEEK_DISCOUNT.getName(),discountStr));
			labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
			labelTipsList.add(labelEntity);
			index++;
		}
		//月租
		if (!Check.NuNObj(priceEnumDicMap.get(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()))) {
			LabelTipsEntity labelEntity = new LabelTipsEntity();
			labelEntity.setIndex(index);
			Double discount = ValueUtil.getdoubleValue(priceEnumDicMap.get(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()));
			if (!Check.NuNObj(discount)) {
				discount =BigDecimalUtil.div(discount, 10, 1);
			} 
			String discountStr = String.valueOf(discount);
			if (discountStr.length()>3) {
				discountStr = discountStr.substring(0,3);
			} 
			labelEntity.setName(String.format(LabelTipsEnum.IS_MONTH_DISCOUNT.getName(),discountStr));
			labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
			labelTipsList.add(labelEntity);
			index++;
		}		

		//一天
		if (!Check.NuNObj(priceEnumDicMap.get(ProductRulesEnum020.ProductRulesEnum020002.getValue()))) {
			LabelTipsEntity labelEntity = new LabelTipsEntity();
			labelEntity.setIndex(index);
			labelEntity.setName(LabelTipsEnum.IS_JIAXIN_DISCOUNT1.getName());
			labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
			labelTipsList.add(labelEntity);
			index++;
		}
		//两天
		if (!Check.NuNObj(priceEnumDicMap.get(ProductRulesEnum020.ProductRulesEnum020003.getValue()))) {
			LabelTipsEntity labelEntity = new LabelTipsEntity();
			labelEntity.setIndex(index);
			labelEntity.setName(LabelTipsEnum.IS_JIAXIN_DISCOUNT2.getName());
			labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
			labelTipsList.add(labelEntity);
			index++;
		}



		StringBuilder tipsStr = new StringBuilder();
		
		if(Check.NuNCollection(labelTipsList)){
			return resultList;
		}

		for (LabelTipsEntity labelTipsEntity : labelTipsList) {
			tipsStr.append(labelTipsEntity.getName()).append("+");
		}

		LabelTipsEntity labelEntity = new LabelTipsEntity();
		labelEntity.setIndex(1);
		labelEntity.setName(tipsStr.substring(0, tipsStr.length()-1));
		labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
		resultList.add(labelEntity);

		return resultList;
	}

	/**
	 * 
	 * 获取周租折扣天数配置
	 *
	 * @author zl
	 * @created 2017年3月9日 下午6:11:29
	 *
	 * @return
	 */
	private int getLongTermWeekLimit(){
		Integer limit = ValueUtil.getintValue(cityTemplateService.getTextValue(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, ProductRulesEnum0019.ProductRulesEnum0019001.getValue()));
		if (Check.NuNObj(limit)) {
			limit= ProductRulesEnum0019.ProductRulesEnum0019001.getDayNum();
		}
		return limit;
	}

	/**
	 * 
	 * 获取月租折扣天数配置
	 *
	 * @author zl
	 * @created 2017年3月9日 下午6:11:29
	 *
	 * @return
	 */
	private int getLongTermMonthLimit(){
		Integer limit = ValueUtil.getintValue(cityTemplateService.getTextValue(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, ProductRulesEnum0019.ProductRulesEnum0019002.getValue()));
		if (Check.NuNObj(limit)) {
			limit= ProductRulesEnum0019.ProductRulesEnum0019002.getDayNum();
		}
		return limit;
	}

	/**
	 * 
	 * 获取夹心日期天数配置
	 *
	 * @author zl
	 * @created 2017年3月9日 下午6:11:29
	 *
	 * @return
	 */
	private int getJianxiDaysLimit(){
		Integer limit = ValueUtil.getintValue(cityTemplateService.getTextValue(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, ProductRulesEnum0019.ProductRulesEnum0019003.getValue()));
		if (Check.NuNObj(limit)) {
			limit= ProductRulesEnum0019.ProductRulesEnum0019003.getDayNum();
		}
		return limit;
	}


	/**
	 * 需要支付金额
	 *
	 * @param need
	 * @param money
	 * @param feeEnum
	 * @author lishaochuan
	 * @create 2016年8月22日下午2:24:26
	 */
    private void addItemFee(NeedPayFeeResponse need, Integer money, Integer versionCode, OrderFeeConst feeEnum, OrderSaveVo orderSaveInfo) {
        if (!Check.NuNObj(money) && money > 0) {
			NeedPayFeeItemResponse item = new NeedPayFeeItemResponse();
			need.getFeeItemList().add(item);
			item.setName(feeEnum.getShowName());
			item.setColorType(feeEnum.getColorType());
			item.setFee(DataFormat.formatHundredPriceNoZero(money));
			item.setIndex(feeEnum.getIndex());
			item.setSymbol(feeEnum.getSymbol());
            //增加标题
            if (!Check.NuNObj(versionCode) && versionCode > 100019) {
                item.setTitle1(feeEnum.getDescForTenant());
                //房费名字更改
                if (feeEnum == OrderFeeConst.NEED_PAY_COST) {
                    String title = feeEnum.getTitle1();
                    title = title.replace("{1}", String.valueOf(orderSaveInfo.getDayCount()));
                    item.setName(title);
                }
                if (feeEnum == OrderFeeConst.NEED_PAY_DISCOUNT) {
                    item.setName(feeEnum.getTitle1());
                    String descForTenant = feeEnum.getDescForTenant();
                    Map<String, Object> priceEnumDicMap = orderSaveInfo.getPriceEnumDicMap();
                    String dayNum = "";
                    String title = "";
                    if (!Check.NuNObj(priceEnumDicMap.get(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()))) {
                        title = LabelTipsEnum.IS_WEEK_DISCOUNT.getName().substring(0, 2);
                        dayNum = String.valueOf(ProductRulesEnum0019.ProductRulesEnum0019001.getDayNum());
                    }
                    if (!Check.NuNObj(priceEnumDicMap.get(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()))) {
                        title = LabelTipsEnum.IS_MONTH_DISCOUNT.getName().substring(0, 2);
                        dayNum = String.valueOf(ProductRulesEnum0019.ProductRulesEnum0019002.getDayNum());
                    }
					descForTenant = descForTenant.replace("{1}", dayNum);
					descForTenant = descForTenant.replace("{2}", title);
					item.setTitle1(descForTenant);
				}
            }

			if (feeEnum == OrderFeeConst.NEED_PAY_USER_COMMISSION
					||feeEnum == OrderFeeConst.NEED_PAY_LAN_COMMISSION) {
				if (orderSaveInfo.getSaveUserComm() > 0
						&&feeEnum == OrderFeeConst.NEED_PAY_USER_COMMISSION) {
					String title1 = feeEnum.getTitle1();
					title1 = title1.replace("{1}", orderSaveInfo.getChangzuDay() + "");
					title1 = title1.replace("{2}", DataFormat.formatHundredPriceNoZero(orderSaveInfo.getSaveUserComm()));
					item.setTitle1(title1);
				}
				if (orderSaveInfo.getSaveLanComm()> 0
						&&feeEnum == OrderFeeConst.NEED_PAY_LAN_COMMISSION) {
					String title1 = feeEnum.getTitle1();
					title1 = title1.replace("{1}", orderSaveInfo.getChangzuDay() + "");
					title1 = title1.replace("{2}", DataFormat.formatHundredPriceNoZero(orderSaveInfo.getSaveLanComm()));
					item.setTitle1(title1);
				}
				if(feeEnum == OrderFeeConst.NEED_PAY_USER_COMMISSION){
					item.setTitle2(orderSaveInfo.getCommissionInfo());
				}
				if(feeEnum == OrderFeeConst.NEED_PAY_LAN_COMMISSION){
					item.setIsHasTips(IsHasTipEnum.HAS_TIP.getValue());
					item.setTipContent(orderSaveInfo.getLanCommissionInfo());
				}

			}
		}
	}




	/**
	 * 添加 普通活动项目
	 *
	 * @param acItemList
	 * @param money
	 * @param acEnum
	 * @author lishaochuan
	 * @create 2016年8月22日下午2:24:26
	 */
	private void addAcItem(List<NeedPayFeeAcItemResponse> acItemList, Integer money, ActivityTypeEnum acEnum, OrderSaveVo orderSaveInfo) {
		if (!Check.NuNObj(money) && money > 0) {
			NeedPayFeeAcItemResponse item = new NeedPayFeeAcItemResponse();
			acItemList.add(item);
			item.setName(acEnum.getShowName());
			item.setColorType(acEnum.getColorType());
			item.setFee(DataFormat.formatHundredPrice(money));
			item.setIndex(acEnum.getIndex());
			item.setSymbol(acEnum.getSymbol());
			item.setIsHasTips(IsHasTipEnum.NOT_HAS_TIP.getValue());
		}
	}
	/**
	 * 处理需要支付金额数据
	 *
	 * @param request
	 * @param dto
	 * @param houseInfo
	 * @return
	 * @author lishaochuan
	 * @create 2016年8月19日下午3:40:22
	 */
	private OrderSaveVo dealNeedPayData(NeedPayFeeRequest request, DataTransferObject dto, OrderNeedHouseVo houseInfo) {
		if (Check.NuNObj(request)) {
			LogUtil.error(LOGGER, "request is null on needPay");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, "请求参数不存在"));
			return null;
		}
		if (Check.NuNObj(request.getStartTime()) || Check.NuNObj(request.getEndTime())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请选择入住时间段");
			return null;
		}
		if (!request.getEndTime().after(request.getStartTime())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("退房时间应在入住时间之后");
			return null;
		}
		// 校验并获取订单支付金额
		OrderSaveVo orderSaveInfo = new OrderSaveVo();
		//1.获取房源的信息

		//2.校验房源信息
		this.dealHouseInfoCheck(dto, houseInfo, request);
		//3.获取当前的房东的属性配置 还有房东的角色信息
		CustomerRoleVo customer = this.getCustomerRoleInfo(dto, houseInfo);
		//4.前置转换配置信息
		this.transSysConf2HouseConf(dto, houseInfo, customer, orderSaveInfo, request);
		//7.填充订单的基础信息
		this.fillBaseOrderInfo(dto, orderSaveInfo, houseInfo, request, customer);
		//8.填充订单的开始时间结束时间
		this.fillOrderStartAndEndTime(dto, houseInfo, orderSaveInfo, request);
		//11.获取房源配置信息
		this.fillOrderConfigs(dto, houseInfo, orderSaveInfo);
		//12.获取房源的特殊价格
		this.fillOrderSpecialPrices(dto, houseInfo, orderSaveInfo, request);
		//13. 处理优惠券信息
		this.dealCoupon(dto, orderSaveInfo, houseInfo, request);
		//14.处理订单的活动信息
		orderActivityProxy.fillActivity(dto, request, houseInfo, orderSaveInfo, customer);

		LogUtil.info(LOGGER, "orderSaveInfoorderSaveInfo:{}", JsonEntityTransform.Object2Json(orderSaveInfo));
		return orderSaveInfo;
	}


	/**
	 * 创建订单
	 *
	 * @param orderJson
	 * @return
	 * @author afi
	 */
	public String createOrder(String orderJson) {
		DataTransferObject dto = new DataTransferObject();
		if (LOGGER.isDebugEnabled()) {
			LogUtil.debug(LOGGER, "{}", orderJson);
		}
		try {
			LogUtil.info(LOGGER, "【下单】开始创建订单 par：{}", orderJson);
			OrderSaveVo orderSaveInfo = new OrderSaveVo();
			CreateOrderRequest request = JsonEntityTransform.json2Object(orderJson, CreateOrderRequest.class);
			//0.校验当前的下单的参数
			this.checkOrderPar(dto, request);
			//1.获取房源的信息
			OrderNeedHouseVo houseInfo = this.getHouseInfo(dto, request);
			//2.校验房源信息
			this.dealHouseInfoCheck(dto, houseInfo, request);
			//3.获取当前的房东的属性配置 还有房东的角色信息
			CustomerRoleVo customer = this.getCustomerRoleInfo(dto, houseInfo);
			//3.1填充用户 手机号 国际码
			this.fillCusotmerMobileCode(dto,request);
			//4.前置转换配置信息
			this.transSysConf2HouseConf(dto, houseInfo, customer, orderSaveInfo, request);
			//5.校验当前用户是当前的订单的数量
			this.dealCityInfoCheck(dto, houseInfo, request);
			//6.校验当前的房源是否可租（需要把时间区间转化成天并校验 houseFid rentWay）【ok】
			this.dealHouseStoreCheck(dto, request);
			//7.填充订单的基础信息
			this.fillBaseOrderInfo(dto, orderSaveInfo, houseInfo, request, customer);
			//8.填充订单的开始时间结束时间
			this.fillOrderStartAndEndTime(dto, houseInfo, orderSaveInfo, request);
			//9.校验入住人 并初始化用户信息
			this.fillTenantInfo(dto, houseInfo, orderSaveInfo, request);
			//10.封装房东和房源快照信息
			this.fillOrderHouseInfo(dto, orderSaveInfo, houseInfo, customer);
			//11.获取房源配置信息
			this.fillOrderConfigs(dto, houseInfo, orderSaveInfo);
			//12.获取房源的特殊价格
			this.fillOrderSpecialPrices(dto, houseInfo, orderSaveInfo, request);
			//13.0 处理优惠券信息
			this.dealCoupon(dto, orderSaveInfo, houseInfo, request);
			//14.处理订单的活动信息
			orderActivityProxy.fillActivity(dto, request, houseInfo, orderSaveInfo, customer);
			//16. 校验当前房东信息
			this.checkLandPunish(dto, houseInfo);
			//17. 处理不需要支付的实时订单
			this.fillFreeOrder(dto, orderSaveInfo);
			//占有库存（非0元订单移到去支付时锁房源） 这里只有0元订单 并且是实时订单才走这里
			this.fillOrderHouseLock(dto, houseInfo, orderSaveInfo, request);
			//18. 直接下单
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				orderUserService.insertOrder(orderSaveInfo);
				Transaction orderPayTran3 = Cat.newTransaction("OrderUserServiceProxy", CatConstant.ORDER_COUNT);
				try {
					//对下单数量的埋点
					Cat.logMetricForCount("下单数量");
					orderPayTran3.setStatus(Message.SUCCESS);
				} catch (Exception ex) {
					Cat.logError("下单数量 打点异常", ex);
					orderPayTran3.setStatus(ex);
				} finally {
					orderPayTran3.complete();
				}
			}
			// 处理不需要支付的实时订单
			this.dealFreeOrder(dto, orderSaveInfo);
			//19. 拼装下单之后的提示信息
			this.fillOrderShow(dto, orderSaveInfo);
			//20.下单并发送信息【异步处理】
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				orderMsgProxy.sendMsg4CreateOrder(dto, orderSaveInfo, houseInfo, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 查询客户信息
	 *
	 * @author yd
	 * @created 2017年4月27日 下午3:12:13
	 *
	 * @param dto
	 * @param request
	 * @return
	 */
	private void fillCusotmerMobileCode(DataTransferObject dto ,CreateOrderRequest request){

		CustomerBaseMsgEntity customer = null;
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(request.getUserUid());
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
				if (customerDto.getCode() == DataTransferObject.SUCCESS) {
					customer = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
					});
					if (!Check.NuNObj(customer)) {
						request.setUserTelCode(customer.getCountryCode());
					} 
				} else {
					dto.setErrCode(customerDto.getCode());
					dto.setMsg(customerDto.getMsg());
				}
			}
		}
	}

	/**
	 * 拼接下单之后前端需要的信息
	 *
	 * @param dto
	 * @param orderSaveInfo
	 */
	private void fillOrderShow(DataTransferObject dto, OrderSaveVo orderSaveInfo) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			dto.putValue("orderSn", orderSaveInfo.getOrder().getOrderSn());
			String msg = "";
			String phone = "";
			String expireTime = "";
			if (orderSaveInfo.getOrder().getOrderStatus() == OrderStatusEnum.WAITING_CONFIRM.getOrderStatus()) {
				//如果是普通订单 提示等待审核时限
				//msg = String.format(OrderMsgProxy.msg_common, orderSaveInfo.getCheckTime());
				msg = OrderMsgProxy.msg_common;
				phone = OrderMsgProxy.service_phone;
			} else {
				//实时订单
				Date payDayLimit = DateSplitUtil.jumpMinute(new Date(), ValueUtil.getintValue(orderSaveInfo.getPayTime()));
				expireTime = DateUtil.timestampFormat(payDayLimit);
			}
			dto.putValue("msg", msg);
			dto.putValue("phone", phone);
			dto.putValue("expireTime", expireTime);
		}
	}


	/**
	 * 填充免单的优惠券
	 *
	 * @param dto
	 * @param orderSaveInfo
	 * @author afi
	 */
	private void fillFreeOrder(DataTransferObject dto, OrderSaveVo orderSaveInfo) {
		if (dto.getCode() != DataTransferObject.SUCCESS || Check.NuNObj(orderSaveInfo)) {
			return;
		}
		//当前需要支付的金额为0 并且为实时订单
		if (orderSaveInfo.getOrderMoney().getNeedPay() == 0 && orderSaveInfo.getOrder().getOrderStatus() == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()) {
			orderSaveInfo.getOrder().setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus());
		}
	}

	/**
	 * 处理免单的优惠券
	 *
	 * @param dto
	 * @param orderSaveInfo
	 * @author afi
	 */
	private void dealFreeOrder(DataTransferObject dto, OrderSaveVo orderSaveInfo) {
		if (dto.getCode() != DataTransferObject.SUCCESS || Check.NuNObj(orderSaveInfo)) {
			return;
		}
		//当前需要支付的金额为0 并且为实时订单
		if (orderSaveInfo.getOrderMoney().getNeedPay() == 0 && orderSaveInfo.getOrder().getOrderStatus() == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()) {
			OrderEntity orderEntity = orderSaveInfo.getOrder();
			OrderInfoVo orderInfoVo = new OrderInfoVo();
			orderInfoVo.setCityCode(orderEntity.getCityCode());
			orderInfoVo.setOrderSn(orderEntity.getOrderSn());
			orderInfoVo.setUserUid(orderEntity.getUserUid());
			orderInfoVo.setLandlordUid(orderEntity.getLandlordUid());
			orderInfoVo.setCouponMoney(orderSaveInfo.getOrderMoney().getCouponMoney());
			orderInfoVo.setDiscountMoney(orderSaveInfo.getOrderMoney().getDiscountMoney());
			orderInfoVo.setStartTime(orderSaveInfo.getOrder().getStartTime());
			orderInfoVo.setEndTime(orderSaveInfo.getOrder().getEndTime());
			orderInfoVo.setActMoney(orderSaveInfo.getOrderMoney().getActMoney());


            //处理活动金额
            List<OrderActivityInfoVo> orderActList = new ArrayList<>();
            List<OrderActivityEntity> actList = orderSaveInfo.getOrderActivitys();
            for (OrderActivityEntity orderActivityEntity : actList) {
                OrderActivityInfoVo orderActivityInfoVo = new OrderActivityInfoVo();
                BeanUtils.copyProperties(orderActivityEntity, orderActivityInfoVo);
                orderActList.add(orderActivityInfoVo);
            }
			LogUtil.info(LOGGER, "立即预定处理处理0元订单,orderInfoVo={},actList={}", JsonEntityTransform.Object2Json(orderInfoVo), JsonEntityTransform.Object2Json(orderActList));
			dealPayServiceProxy.dealZeroPayCallBack(orderActList, orderInfoVo);
		}
	}


	/**
	 * 校验当前的房东的罚款信息
	 *
	 * @param dto
	 * @param houseInfo
	 * @author afi
	 */
	private void checkLandPunish(DataTransferObject dto, OrderNeedHouseVo houseInfo) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (financePunishServices.checkLandPunishOverTime(houseInfo.getLandlordUid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_PUNISH_ERROR));
			}
		}
	}

	/**
	 * 获取房东属性
	 * 包括房东的角色信息
	 *
	 * @param houseInfo
	 * @author afi
	 */
	private CustomerRoleVo getCustomerRoleInfo(DataTransferObject dto, OrderNeedHouseVo houseInfo) {
		CustomerRoleVo vo = new CustomerRoleVo();
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			CustomerBaseMsgEntity customer = null;
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				String customerJson = customerInfoService.getCustomerAndRoleInfoByUid(houseInfo.getLandlordUid());
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
				if (customerDto.getCode() == DataTransferObject.SUCCESS) {
					customer = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {
					});
					if (!Check.NuNObj(customer)) {
						BeanUtils.copyProperties(customer, vo);
					} else {
						//当前用户不存在
						return vo;
					}
					List<CustomerRoleEntity> rolse = customerDto.parseData("roles", new TypeReference<List<CustomerRoleEntity>>() {
					});
					vo.setRoles(rolse);
				} else {
					dto.setErrCode(customerDto.getCode());
					dto.setMsg(customerDto.getMsg());
				}
			}
		}
		return vo;
	}

	/**
	 * 获取房源信息
	 *
	 * @param request
	 * @author afi
	 */
	private OrderNeedHouseVo getHouseInfo(DataTransferObject dto, NeedPayFeeRequest request) throws SOAParseException {
		OrderNeedHouseVo houseInfo = null;
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			OrderHouseDto orderHouseDto = new OrderHouseDto();
			orderHouseDto.setFid(request.getFid());
			orderHouseDto.setRentWay(request.getRentWay());
			orderHouseDto.setStartDate(request.getStartTime());
			orderHouseDto.setEndDate(request.getEndTime());
			String resultJson = houseManageService.findOrderNeedHouseVoPlus(JsonEntityTransform.Object2Json(orderHouseDto));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (resultDto.getCode() == DataTransferObject.SUCCESS) {
				houseInfo = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseBase", OrderNeedHouseVo.class);


			} else {
				dto.setErrCode(resultDto.getCode());
				dto.setMsg(resultDto.getMsg());
			}
		}
		return houseInfo;
	}


	/**
	 * 获取订单的参数校验
	 *
	 * @param request
	 * @author afi
	 */
	private void checkOrderPar(DataTransferObject dto, CreateOrderRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (Check.NuNObj(request)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.PARAM_NULL));
				return;
			}
			if (Check.NuNStr(request.getUserUid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.PARAM_NULL));
				return;
			}
			if (!Check.NuNStr(request.getTripPurpose()) && request.getTripPurpose().length() > 256) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("输入文字过长，100字以内");
				return;
			}
			if (Check.NuNObj(request.getStartTime())
					|| Check.NuNObj(request.getEndTime())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("请选择入住时间段");
				return;
			}
			if (!request.getEndTime().after(request.getStartTime())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("退房时间应在入住时间之后");
				return;
			}

			//比对时间
			Date startC = DateSplitUtil.transDateTime2Date(request.getStartTime());
			Date nowC = DateSplitUtil.transDateTime2Date(new Date());
			if (nowC.after(startC)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("只能选择今天及以后的日期");
				return;
			}

			if (Check.NuNStr(request.getUserUid())
					|| Check.NuNObj(request.getRentWay())
					|| Check.NuNObj(request.getTenantFids())) {
				//参数的为空校验
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.PARAM_NULL));
				return;
			}
			if (Check.NuNStr(request.getUserTel())
					|| Check.NuNStr(request.getUserName())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("请完善个人信息");
				return;
			}
			//判断下单是否满足长租，满足的话判断版本是否低于版本100008，否则提示升级
			DataTransferObject textValueDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, TradeRulesEnum0020.TradeRulesEnum0020001.getValue()));
			if (textValueDto.getCode() ==DataTransferObject.SUCCESS){
				Integer setDay = textValueDto.parseData("textValue", new TypeReference<Integer>() {});
				Integer countDay = DateSplitUtil.countDateSplit(request.getStartTime(), request.getEndTime());
				if (countDay>=setDay){
					if (!VersionCodeEnum.checkLongTd(request.getVersionCode())){
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("退订规则已升级，请下载新版本APP");
					}
				}

			}
		}
	}


	public static void main(String[] args) {
		List<String> codeList = new ArrayList<>();
		/*codeList.add(TradeRulesEnum.TradeRulesEnum002.getValue());
		codeList.add(TradeRulesEnum.TradeRulesEnum0014.getValue());
		codeList.add(TradeRulesEnum.TradeRulesEnum006.getValue());
		codeList.add(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue());
		codeList.add(TradeRulesEnum009Enum.TradeRulesEnum009001.getValue());
		codeList.add(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue());
		codeList.add(TradeRulesEnum007Enum.TradeRulesEnum007002.getValue());
		//长租新增配置 佣金比例和天数
		codeList.add(TradeRulesEnum0020.TradeRulesEnum0020001.getValue());
		codeList.add(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue());
		codeList.add(TradeRulesEnum0020003.TradeRulesEnum0020003002.getValue());*/
		
           codeList.add(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue());
		   codeList.add(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue());
	        //长期入住比率
	        codeList.add(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue());
	        //长期入住最小天数
	        codeList.add(TradeRulesEnum0020.TradeRulesEnum0020001.getValue());

		String aa = ValueUtil.transList2Str(codeList);

		String nn = RedisKeyConst.getConfigKey(null, aa);

		System.out.println(nn);
	}

	/**
	 * 将用户和房东的佣金 合并到房源配置
	 *
	 * @param dto
	 * @param houseInfo
	 * @param customer
	 * @param orderSaveInfo
	 * @param request
	 * @author afi
	 */
	private void transSysConf2HouseConf(DataTransferObject dto, OrderNeedHouseVo houseInfo, CustomerBaseMsgEntity customer, OrderSaveVo orderSaveInfo, NeedPayFeeRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (Check.NuNObj(houseInfo)) {
				LogUtil.error(LOGGER, "【下单】【异常数据】 houseInfo 为空 houseInfo:{}", houseInfo.getFid(), houseInfo.getRoomFid(), houseInfo.getRentWay());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("当前房源不存在");
				return;
			}
			if (Check.NuNObj(customer)) {
				LogUtil.error(LOGGER, "【下单】【异常数据】 房东信息为空 houseFid:{}, roomFid:{}, rentway:{}", houseInfo.getFid(), houseInfo.getRoomFid(), houseInfo.getRentWay());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东信息不存在");
				return;
			}

			if (Check.NuNObj(customer.getRealName())) {
				LogUtil.error(LOGGER, "【下单】【异常数据】 房东真实姓名为空 houseFid:{}, roomFid:{}, rentway:{}, realName:{}", houseInfo.getFid(), houseInfo.getRoomFid(), houseInfo.getRentWay(), customer.getRealName());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东真实姓名不存在");
				return;
			}
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				List<String> codeList = new ArrayList<>();
				codeList.add(TradeRulesEnum.TradeRulesEnum002.getValue());
				codeList.add(TradeRulesEnum.TradeRulesEnum0014.getValue());
				codeList.add(TradeRulesEnum.TradeRulesEnum006.getValue());
				codeList.add(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue());
				codeList.add(TradeRulesEnum009Enum.TradeRulesEnum009001.getValue());
				codeList.add(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue());
				codeList.add(TradeRulesEnum007Enum.TradeRulesEnum007002.getValue());
				//长租新增配置 佣金比例和天数
				codeList.add(TradeRulesEnum0020.TradeRulesEnum0020001.getValue());
				codeList.add(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue());
				codeList.add(TradeRulesEnum0020003.TradeRulesEnum0020003002.getValue());

				String confJson = cityTemplateService.getTextListByCodes(null, ValueUtil.transList2Str(codeList));
				DataTransferObject cinfDto = JsonEntityTransform.json2DataTransferObject(confJson);
				List<MinsuEleEntity> confList = null;
				if (cinfDto.getCode() == DataTransferObject.SUCCESS) {
					confList = cinfDto.parseData("confList", new TypeReference<List<MinsuEleEntity>>() {
					});
				} else {
					dto.setErrCode(cinfDto.getCode());
					dto.setMsg(cinfDto.getMsg());
					return;
				}


				//获取退订政策的配置列表
				String returnJson = cityTemplateService.getTextListByLikeCodes(null, TradeRulesEnum.TradeRulesEnum005.getValue());
				DataTransferObject rerunDto = JsonEntityTransform.json2DataTransferObject(returnJson);
				List<MinsuEleEntity> returnList = null;
				if (rerunDto.getCode() == DataTransferObject.SUCCESS) {
					returnList = rerunDto.parseData("confList", new TypeReference<List<MinsuEleEntity>>() {
					});
					confList.addAll(returnList);
				} else {
					dto.setErrCode(rerunDto.getCode());
					dto.setMsg(rerunDto.getMsg());
					return;
				}
				if (Check.NuNCollection(confList)) {
					LogUtil.info(LOGGER, "【下单】【异常数据】 orerSn:", orderSaveInfo.getOrder().getOrderSn());
					throw new BusinessException("confList为空 ");
				}
				if (Check.NuNObj(customer)) {
					LogUtil.info(LOGGER, "【下单】【异常数据】 orerSn:", orderSaveInfo.getOrder().getOrderSn());
					throw new BusinessException("customer is null");
				}
				//长租的下单的配置
				List<HouseConfMsgEntity> changzuConfList = new ArrayList<>();
				for (MinsuEleEntity ele : confList) {
					HouseConfMsgEntity conf = new HouseConfMsgEntity();
					conf.setDicCode(ele.getEleKey());
					conf.setDicVal(ele.getEleValue());
					if (ele.getEleKey().equals(TradeRulesEnum.TradeRulesEnum002.getValue())) {
						//支付时限
						String payTimeMinute = ele.getEleValue();
						orderSaveInfo.setPayTime(payTimeMinute);
						orderSaveInfo.setPayTimeStr(ValueUtil.getTimeInfoByMinute(ValueUtil.getintValue(payTimeMinute)));
					} else if (ele.getEleKey().equals(TradeRulesEnum.TradeRulesEnum0014.getValue())) {
						//审核时限
						String checkTime = ValueUtil.getTimeInfoByMinute(ValueUtil.getintValue(ele.getEleValue()));
						orderSaveInfo.setCheckTime(checkTime);
					} else if (ele.getEleKey().startsWith(TradeRulesEnum.TradeRulesEnum007.getValue())) {
						//处理计算方式
						if (ele.getEleKey().equals(customer.getClearingCode())) {
							//验证当前的订单结算方式
							houseInfo.getHouseConfList().add(conf);
						}
					} else if (ele.getEleKey().equals(TradeRulesEnum0020.TradeRulesEnum0020001.getValue())) {
						int dayLimit = ValueUtil.getintValue(ele.getEleValue());
						if (dayLimit > 0){
							changzuConfList.add(conf);
							//长租入住最小天数
							if (DateSplitUtil.countDateSplit(request.getStartTime(), request.getEndTime()) >= dayLimit) {
								//满足长租配置
								orderSaveInfo.setChangzuFlag(true);
							}
							//长租天数
							orderSaveInfo.setChangzuDay(dayLimit);
							//替换长租天数
							orderSaveInfo.setCommissionInfo(orderSaveInfo.getCommissionInfo().replace("{2}",String.valueOf(dayLimit)));
						}
					} else if (ele.getEleKey().equals(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue())) {
						//长租收取房东服务费类型
						changzuConfList.add(conf);
					} else if (ele.getEleKey().equals(TradeRulesEnum0020003.TradeRulesEnum0020003002.getValue())) {
						//长租收取租客服务费类型
						changzuConfList.add(conf);

						//替换服务费优惠费率
						orderSaveInfo.setCommissionInfo(orderSaveInfo.getCommissionInfo().replace("{3}", ValueUtil.getdoubleValue(ele.getEleValue()) * 100 + "%"));
					} else {
						if (ele.getEleKey().equals(TradeRulesEnum009Enum.TradeRulesEnum009001.getValue())) {
							//替换长租房客佣金
							orderSaveInfo.setCommissionInfo(orderSaveInfo.getCommissionInfo().replace("{1}", ValueUtil.getdoubleValue(ele.getEleValue()) * 100 + "%"));
						}

						if (ele.getEleKey().equals(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue())) {
							//替换长租房东佣金
							orderSaveInfo.setLanCommissionInfo(orderSaveInfo.getLanCommissionInfo().replace("{1}", ValueUtil.getdoubleValue(ele.getEleValue()) * 100 + "%"));
						}
						houseInfo.getHouseConfList().add(conf);
					}
				}
				if (orderSaveInfo.getChangzuFlag()) {
					//如果是长租 将长租的配置保存 默认不保存长租信息
					houseInfo.getHouseConfList().addAll(changzuConfList);
				}
				//所有的设置都标记为有效
				for (HouseConfMsgEntity entity : houseInfo.getHouseConfList()) {
					entity.setIsDel(YesOrNoEnum.NO.getCode());
				}
			}
		}
	}


	/**
	 * 填充房源信息
	 *
	 * @param dto
	 * @param orderSaveInfo
	 * @param houseInfo
	 * @param customer
	 * @author afi
	 */
	private void fillOrderHouseInfo(DataTransferObject dto, OrderSaveVo orderSaveInfo, OrderNeedHouseVo houseInfo, CustomerBaseMsgEntity customer) {
		//填充房源快照信息
		this.fillHouseSnapshotInfo(dto, orderSaveInfo, houseInfo);
		//填充房东信息
		this.fillLandlordInfo(dto, orderSaveInfo, houseInfo, customer);
	}

	/**
	 * 填充房源快照信息
	 *
	 * @param orderSaveInfo
	 * @param houseInfo
	 * @author afi
	 */
	private void fillHouseSnapshotInfo(DataTransferObject dto, OrderSaveVo orderSaveInfo, OrderNeedHouseVo houseInfo) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (!Check.NuNObj(houseInfo)) {
				orderSaveInfo.getOrderHouse().setOrderSn(orderSaveInfo.getOrder().getOrderSn());
				orderSaveInfo.getOrderHouse().setRentWay(houseInfo.getRentWay());
				orderSaveInfo.getOrderHouse().setHouseFid(houseInfo.getFid());
				orderSaveInfo.getOrderHouse().setRoomFid(houseInfo.getRoomFid());
				orderSaveInfo.getOrderHouse().setBedFid(houseInfo.getBedFid());
				orderSaveInfo.getOrderHouse().setHouseName(houseInfo.getHouseName());
				orderSaveInfo.getOrderHouse().setHouseAddr(houseInfo.getHouseAddr());
				orderSaveInfo.getOrderHouse().setRoomFid(houseInfo.getRoomFid());
				orderSaveInfo.getOrderHouse().setBedFid(houseInfo.getBedFid());
				orderSaveInfo.getOrderHouse().setRoomName(houseInfo.getRoomName());
				orderSaveInfo.getOrderHouse().setBedSn(houseInfo.getBedSn());
				orderSaveInfo.getOrderHouse().setPicUrl(this.getHousePic(houseInfo));
				orderSaveInfo.getOrderHouse().setPrice(FinanceMoneyUtil.getHousePrice(houseInfo));
				orderSaveInfo.getOrderHouse().setCleanMoney(ValueUtil.getintValue(FinanceMoneyUtil.getHouseCleanMoney(houseInfo)));

				HouseBaseExtEntity houseBaseExtEntity = houseInfo.getHouseBaseExtEntity();
				HouseRoomExtEntity houseRoomExtEntity = houseInfo.getHouseRoomExtEntity();
                orderSaveInfo.getOrderHouse().setDiscountRulesCode(houseBaseExtEntity.getDiscountRulesCode());
                //兼容处理 如果房间扩展数据为空 还查使用之前房源数据
				if (houseInfo.getRentWay() == RentWayEnum.HOUSE.getCode()
						|| (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNObj(houseRoomExtEntity))){
					orderSaveInfo.getOrderHouse().setOrderType(houseBaseExtEntity.getOrderType());
					orderSaveInfo.getOrderHouse().setDepositRulesCode(houseBaseExtEntity.getDepositRulesCode());
					orderSaveInfo.getOrderHouse().setCheckOutRulesCode(houseBaseExtEntity.getCheckOutRulesCode());
					orderSaveInfo.getOrderHouse().setCheckInTime(houseBaseExtEntity.getCheckInTime());
					orderSaveInfo.getOrderHouse().setCheckOutTime(houseBaseExtEntity.getCheckOutTime());
				}else if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNObj(houseRoomExtEntity)){
					orderSaveInfo.getOrderHouse().setOrderType(houseRoomExtEntity.getOrderType());
					orderSaveInfo.getOrderHouse().setDepositRulesCode(houseRoomExtEntity.getDepositRulesCode());
					orderSaveInfo.getOrderHouse().setCheckOutRulesCode(houseRoomExtEntity.getCheckOutRulesCode());
					orderSaveInfo.getOrderHouse().setCheckInTime(houseRoomExtEntity.getCheckInTime());
					orderSaveInfo.getOrderHouse().setCheckOutTime(houseRoomExtEntity.getCheckOutTime());
				}

				//如果是长租,直接保存长租的退订政策
				if (orderSaveInfo.getChangzuFlag()) {
					orderSaveInfo.getOrderHouse().setCheckOutRulesCode(TradeRulesEnum005Enum.TradeRulesEnum005004.getValue());
				}


				orderSaveInfo.getOrderHouse().setIsLock(houseInfo.getIsLock());
				if (houseInfo.getRentWay() == RentWayEnum.HOUSE.getCode()) {
					orderSaveInfo.getOrderHouse().setHouseSn(houseInfo.getHouseSn());
				} else {
					orderSaveInfo.getOrderHouse().setHouseSn(houseInfo.getRoomSn());
				}
			}
		}
	}


	/**
	 * 获取图片信息-相对路径
	 *
	 * @param houseInfo
	 * @return
	 */
	private String getHousePic(OrderNeedHouseVo houseInfo) {
		if (Check.NuNObj(houseInfo)) {
			return "";
		}
		if (Check.NuNObj(houseInfo.getHouseDefaultPic())) {
			return "";
		}
		if (Check.NuNObj(houseInfo.getHouseDefaultPic().getPicBaseUrl()) || Check.NuNObj(houseInfo.getHouseDefaultPic().getPicSuffix())) {
			return "";
		}
		return houseInfo.getHouseDefaultPic().getPicBaseUrl() + houseInfo.getHouseDefaultPic().getPicSuffix();
	}


	/**
	 * 填充订单的基础信息
	 *
	 * @param orderSaveInfo
	 * @param houseInfo
	 * @author afi
	 */
	private void fillBaseOrderInfo(DataTransferObject dto, OrderSaveVo orderSaveInfo, OrderNeedHouseVo houseInfo, NeedPayFeeRequest request, CustomerBaseMsgEntity customer) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (!Check.NuNObj(houseInfo)) {
				orderSaveInfo.getOrder().setOrderSn(OrderSnUtil.getOrderSn());
				orderSaveInfo.getOrder().setFid(UUIDGenerator.hexUUID());
				orderSaveInfo.getOrder().setCityCode(houseInfo.getCityCode());
				orderSaveInfo.getOrder().setNationCode(houseInfo.getNationCode());
				orderSaveInfo.getOrder().setProvinceCode(houseInfo.getProvinceCode());
				orderSaveInfo.getOrder().setLandlordTelCode(customer.getCountryCode());
				if(Check.NuNStr(houseInfo.getAreaCode())){
					orderSaveInfo.getOrder().setAreaCode("");
				}else {
					orderSaveInfo.getOrder().setAreaCode(houseInfo.getAreaCode());
				}
				orderSaveInfo.getOrder().setOrderSource(request.getSourceType());
				Integer orderType = houseInfo.getHouseBaseExtEntity().getOrderType();
                if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNObj(houseInfo.getHouseRoomExtEntity())) {
                    orderType = houseInfo.getHouseRoomExtEntity().getOrderType();
                }

				if (OrderTypeEnum.CURRENT.getCode() == orderType) {
					orderSaveInfo.getOrder().setOrderStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
				} else {
					orderSaveInfo.getOrder().setOrderStatus(OrderStatusEnum.WAITING_CONFIRM.getOrderStatus());
				}
				if (customer.getClearingCode().equals(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue())) {
					//按照订单结算
					orderSaveInfo.getOrder().setCheckType(CheckTypeEnum.ORDER.getCode());
				} else if (customer.getClearingCode().equals(TradeRulesEnum007Enum.TradeRulesEnum007002.getValue())) {
					//按照天结算
					orderSaveInfo.getOrder().setCheckType(CheckTypeEnum.DAY.getCode());
				} else {
					throw new BusinessException("clearingCode is error");
				}
				orderSaveInfo.getOrder().setTripPurpose(request.getTripPurpose());
			}
		}
	}

	/**
	 * 获取订单的折扣活动和价格
	 *
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @author afi
	 */
	private void fillOrderStartAndEndTime(DataTransferObject dto, OrderNeedHouseVo houseInfo, OrderSaveVo orderSaveInfo, NeedPayFeeRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			try {
				//开始时间 结束时间的时间格式
				String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";
                String checkInTime = "";
                String checkOutTime = "";
                //兼容 房间没有 扩展信息 去房源信息
                HouseRoomExtEntity houseRoomExtEntity = houseInfo.getHouseRoomExtEntity();
                if (houseInfo.getRentWay() == RentWayEnum.HOUSE.getCode()
                        || (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNObj(houseRoomExtEntity))) {
                    checkInTime = houseInfo.getHouseBaseExtEntity().getCheckInTime();
                    checkOutTime = houseInfo.getHouseBaseExtEntity().getCheckOutTime();
                } else if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNObj(houseRoomExtEntity)) {
                    checkInTime = houseRoomExtEntity.getCheckInTime();
                    checkOutTime = houseRoomExtEntity.getCheckOutTime();
                }
                if (Check.NuNStr(checkInTime)) {
					LogUtil.error(LOGGER, "【下单】 入住时间配置为空 :par {}", JsonEntityTransform.Object2Json(request));
					throw new BusinessException(" houseInfo.getHouseBaseExtEntity().getCheckInTime() is null");
				}
				if (Check.NuNStr(checkOutTime)) {
					LogUtil.error(LOGGER, "【下单】 退房的时间配置为空:par {}", JsonEntityTransform.Object2Json(request));
					throw new BusinessException(" houseInfo.getHouseBaseExtEntity().getCheckOutTime() is null");
				}
                //5.入住时间
                orderSaveInfo.getOrder().setStartTime(DateUtil.parseDate(DateUtil.dateFormat(request.getStartTime()) + " " + checkInTime, dateFormatPattern));
                //6.退房时间
                orderSaveInfo.getOrder().setEndTime(DateUtil.parseDate(DateUtil.dateFormat(request.getEndTime()) + " " + checkOutTime, dateFormatPattern));

			} catch (Exception e) {
				LogUtil.error(LOGGER, "【下单】 填充时间失败:par {}", JsonEntityTransform.Object2Json(request));
				throw new BusinessException(e);
			}
		}
	}


	/**
	 * 获取订单特殊价格和价格
	 * 1. 封装订单的特殊价格   设置List<OrderSpecialPriceEntity> orderPrices  入库使用  夹心价格不入库
	 * 2. 返回特殊价格的map  夹心价格已经替换特殊价格  Map<String,Integer> priceMap 前台使用
	 *
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @author afi
	 */
	private void fillCrevice2SpecialPrices(DataTransferObject dto, OrderNeedHouseVo houseInfo, OrderSaveVo orderSaveInfo, NeedPayFeeRequest request) {
		if (dto.getCode() != DataTransferObject.SUCCESS) {
			return;
		}
		//夹缝价格不入库
		this.fillOrderPrice(orderSaveInfo);

		List<HouseConfMsgEntity> houseConfList = houseInfo.getHouseConfList();
		if (Check.NuNCollection(houseConfList)) {
			return;
		}
		// 封装夹心价格的配置信息  以及 今夜特价
		List<OrderConfigEntity> creviceConfigList = new ArrayList<>();
		OrderConfigEntity tonightCon =  null;
		for (HouseConfMsgEntity entity : houseConfList) {
			//过滤夹缝价格
			if (!entity.getDicCode().startsWith(ProductRulesEnum.ProductRulesEnum020.getValue())
					&&!entity.getDicCode().equals(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode())) {
				continue;
			}

			OrderConfigEntity configEntity = new OrderConfigEntity();
			configEntity.setConfigCode(entity.getDicCode());
			configEntity.setConfigValue(entity.getDicVal());
			creviceConfigList.add(configEntity);
			if(entity.getDicCode().equals(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode())){
				tonightCon = configEntity;
			}
		}
		if (Check.NuNCollection(creviceConfigList)) {
			//夹缝价格为空直接返回
			LogUtil.info(LOGGER, "【夹缝价格】 当前房源未设置夹缝价格");
			return;
		}


		LogUtil.info(LOGGER, "【夹缝价格】 当前房源夹缝价格:{}", JsonEntityTransform.Object2Json(creviceConfigList));
		LogUtil.info(LOGGER, "【夹缝价格】 价格map {}", orderSaveInfo.getPriceMap());

		Double realCut = null;
		Double firstCut = null;
		String realCutCode = null;
		String firstCutCode = null;
		int discount = 0;
		int tonightDiscount = 0;
		List<String> codes = new ArrayList<>();
		//当前的折扣天数
		Map<Integer, Double> cuts = new TreeMap<>();
		for (OrderConfigEntity configEntity : creviceConfigList) {
			ProductRulesEnum020 productRulesEnum0020 = ProductRulesEnum020.getByCode(configEntity.getConfigCode());
			if (Check.NuNObj(productRulesEnum0020)) {
				continue;
			}
			codes.add(configEntity.getConfigCode());
			cuts.put(productRulesEnum0020.getDayNum(), ValueUtil.getdoubleValue(configEntity.getConfigValue()));
		}
		if (Check.NuNObj(ProductRulesEnum020.getMaxDay(codes))&&Check.NuNObj(tonightCon)) {
			LogUtil.info(LOGGER, "【夹缝价格】 未匹配任何价格: 夹缝规则 {}", JsonEntityTransform.Object2Json(creviceConfigList));
			return;
		}
		//当前锁的夹缝情况
		Integer countLock = houseLockService.getCreviceCount(request.getFid(), request.getRentWay(), request.getStartTime(), request.getEndTime(), houseInfo.getTillDate());
		LogUtil.info(LOGGER, "【夹缝价格】 countLock:{}", countLock);
		if (!Check.NuNObj(countLock)) {
			for (Map.Entry<Integer, Double> entry : cuts.entrySet()) {
				if (entry.getKey().intValue() == countLock) {
					realCut = ValueUtil.getdoubleValue(entry.getValue());
					ProductRulesEnum020 productRulesEnum020 = ProductRulesEnum020.getByDayNum(entry.getKey());
					if(!Check.NuNObj(productRulesEnum020)){
						realCutCode = productRulesEnum020.getValue();
					}
					break;
				}
			}
		}

		Map<String, String> productRulesMap = new HashMap<String, String>();
		//大于最大的时间 只能计算当天
		//ProductRulesEnum020 first = ProductRulesEnum020.getFristRule(codes);
		String firstDay = DateUtil.dateFormat(request.getStartTime());

		//今夜特价逻辑
		if(DateUtil.dateFormat(new Date()).equals(firstDay)){
			//今夜特价
			if(!Check.NuNObj(tonightCon)){
				firstCut = ValueUtil.getdoubleValue(tonightCon.getConfigValue());
				firstCutCode = tonightCon.getConfigCode();
			}else{
				if(!Check.NuNObj(realCut)){
					firstCut = realCut;
					firstCutCode = realCutCode;
				}
			}
			
		}
		//JYTE:之前 今日特惠 逻辑
		/*if (Check.NuNObj(first)) {
			LogUtil.info(LOGGER, "【夹缝价格】 第一天不是夹缝价格: 夹缝规则 {}", JsonEntityTransform.Object2Json(creviceConfigList));
			//第一天不是今日特惠  但是今夜特价
			if(!Check.NuNObj(tonightCon)&&DateUtil.dateFormat(new Date()).equals(firstDay)){
				firstCut = ValueUtil.getdoubleValue(tonightCon.getConfigValue());
				firstCutCode = tonightCon.getConfigCode();
			}
		} else {
			//入住时间是第一天  此次今日特惠 改成今夜特价  逻辑修改  ：如果满足今夜特价　走今夜特价逻辑 
			if (DateUtil.dateFormat(new Date()).equals(firstDay)) {

				firstCut = cuts.get(first.getDayNum());
				ProductRulesEnum020 productRulesEnum020 = ProductRulesEnum020.getByDayNum(first.getDayNum());
				if(!Check.NuNObj(productRulesEnum020)){
					firstCutCode = productRulesEnum020.getValue();
				}
				if(Check.NuNObj(tonightCon)){
					if (!Check.NuNObj(realCut)
							&& realCut > firstCut) {
						firstCut = realCut;
						firstCutCode = realCutCode;
					}
				}else{
					firstCut = ValueUtil.getdoubleValue(tonightCon.getConfigValue());
					firstCutCode = tonightCon.getConfigCode();
				}

			} else {
				//入住时间不是第一天
				firstCut = realCut;
				firstCutCode = realCutCode;
			}
		}*/

		//处理第一天是当天 的价格
		if(!Check.NuNObj(firstCut)){
			int firstDayPrice;
			if (orderSaveInfo.getPriceMap().containsKey(firstDay)) {
				firstDayPrice = orderSaveInfo.getPriceMap().get(firstDay);
			} else {
				firstDayPrice = orderSaveInfo.getPriceMap().get("price");
			}
			LogUtil.info(LOGGER, "【夹缝价格】 第一天原始价格 {}", firstDayPrice);
			LogUtil.info(LOGGER, "【夹缝价格】 第一天折扣 {}", firstCut);
			Double firstDayPriceDou = BigDecimalUtil.mul(firstDayPrice, firstCut == null ? 1 : firstCut);
			LogUtil.info(LOGGER, "【夹缝价格】 第一天价格 {}", firstDayPriceDou.intValue());
			if (firstDayPrice != firstDayPriceDou.intValue()) {
				orderSaveInfo.getPriceMap().put(firstDay, firstDayPriceDou.intValue());
				if(firstCutCode.equals(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode())){
					Map<String, String> tonightMap = new HashMap<String, String>();
					tonightMap.put(firstCutCode, String.valueOf(firstCut));
					tonightDiscount += firstDayPrice-firstDayPriceDou.intValue();
					setOrderConfigAndAc(orderSaveInfo, tonightMap,tonightDiscount,OrderAcTypeEnum.LAN_TONIGHT_DISCOUNT.getCode());
				}else{
					productRulesMap.put(firstCutCode, String.valueOf(firstCut));
					discount += firstDayPrice-firstDayPriceDou.intValue();
				}

				if(discount<0||tonightDiscount<0){
					throw new BusinessException("【夹缝价格异常1】 折扣为负数discount={"+discount+"},tonightDiscount={"+tonightDiscount+"}");
				}

				//存计算取的枚举
				orderSaveInfo.getPriceEnumDicMap().put(firstCutCode, firstCut);
			}
		}
		LogUtil.info(LOGGER, "【夹缝价格】 realCut:{}", realCut);
		if (Check.NuNObj(realCut)) {
			LogUtil.info(LOGGER, "【夹缝价格】 都为空直接返回");
			//夹缝价格替换特殊价格  稍后去掉
			//  this.fillOrderPrice(orderSaveInfo);
			//设置夹心价格配置
			setOrderConfigAndAc(orderSaveInfo, productRulesMap,discount,OrderAcTypeEnum.LEN_FLEXIBLE_PRICING.getCode());
			return;
		}
		List<Date> dayList = DateSplitUtil.dateSplit(request.getStartTime(), request.getEndTime());
		if (Check.NuNCollection(dayList)) {
			throw new BusinessException("异常的开始时间结束时间");
		}
		int price = orderSaveInfo.getPriceMap().get("price");
		for (Date date : dayList) {
			if (DateUtil.dateFormat(date).equals(DateUtil.dateFormat(request.getStartTime()))) {
				continue;
			}
			String ele = DateUtil.dateFormat(date);
			if (orderSaveInfo.getPriceMap().containsKey(ele)) {
				int elePrice = orderSaveInfo.getPriceMap().get(ele);
				Double elePriceDou = BigDecimalUtil.mul(elePrice, realCut);
				orderSaveInfo.getPriceMap().put(ele, elePriceDou.intValue());
				discount += elePrice-elePriceDou.intValue();

			} else {
				Double elePriceDou = BigDecimalUtil.mul(price, realCut);
				orderSaveInfo.getPriceMap().put(ele, elePriceDou.intValue());
				discount += price-elePriceDou.intValue();
			}
			if(discount<0){
				throw new BusinessException("【夹缝价格异常2】 折扣为负数discount={}"+discount);
			}
			//放真实的折扣
			productRulesMap.put(realCutCode, String.valueOf(realCut));

			//存计算取的枚举
			orderSaveInfo.getPriceEnumDicMap().put(realCutCode, realCut);
		}

		//设置夹心价格配置
		setOrderConfigAndAc(orderSaveInfo, productRulesMap, discount,OrderAcTypeEnum.LEN_FLEXIBLE_PRICING.getCode());



	}

	/**
	 * 
	 * 添加夹心价格配置  以及灵活定价活动
	 *
	 * @author yd
	 * @created 2017年3月6日 下午9:56:44
	 *
	 * @param orderSaveInfo
	 * @param productRulesMap
	 */
	private void setOrderConfigAndAc( OrderSaveVo orderSaveInfo,Map<String, String> productRulesMap,int discount,int acType){

		List<OrderConfigEntity>  listOrderConf = orderSaveInfo.getOrderConfigs();
		if(Check.NuNCollection(listOrderConf)) listOrderConf = new ArrayList<OrderConfigEntity>();
		if(!Check.NuNMap(productRulesMap)){
			for (Entry<String, String> map : productRulesMap.entrySet()) {

				OrderConfigEntity orderConfig = new OrderConfigEntity();
				orderConfig.setConfigCode(map.getKey());
				orderConfig.setConfigValue(map.getValue());
				orderConfig.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
				listOrderConf.add(orderConfig);
			}
			orderSaveInfo.setOrderConfigs(listOrderConf);

			OrderAcTypeEnum orderAcTypeEnum = OrderAcTypeEnum.getByCode(acType);
			
			if(Check.NuNObj(orderAcTypeEnum)) return ;
			//参加夹心折扣的活动
			OrderActivityEntity ac = new OrderActivityEntity();
			ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
			ac.setPreferentialSources(PreferentialEnum.LANLORD.getCode());
			ac.setPreferentialUser(PreferentialEnum.TENANT.getCode());
			if(orderAcTypeEnum.getCode() == OrderAcTypeEnum.LAN_TONIGHT_DISCOUNT.getCode()){
				ac.setAcFid(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode());
				ac.setAcName(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getVal());
			}
			if(orderAcTypeEnum.getCode() == OrderAcTypeEnum.LEN_FLEXIBLE_PRICING.getCode()){
				ac.setAcFid(ProductRulesEnum020.ProductRulesEnum020001.getParentValue());
				ac.setAcName(ProductRulesEnum.ProductRulesEnum020.getName());
			}
		
			//设置当前的夹心折扣的活动金额为当前的房客的佣金
			ac.setAcMoneyAll(discount);
			ac.setAcMoney(discount);
			ac.setAcType(acType);

			orderSaveInfo.getOrderActivitys().add(ac);
		}



	}

	/**
	 * 夹缝价格替换特殊价格
	 *
	 * @param orderSaveInfo
	 */
	private void fillOrderPrice(OrderSaveVo orderSaveInfo) {
		LogUtil.info(LOGGER, "【夹缝价格不入库】 特殊价格:map:{}", JsonEntityTransform.Object2Json(orderSaveInfo.getPriceMap()));
		//覆盖特殊价格
		List<OrderSpecialPriceEntity> specialPriceEntityList = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : orderSaveInfo.getPriceMap().entrySet()) {
			if (!entry.getKey().equals("price")) {
				OrderSpecialPriceEntity priceEntity = new OrderSpecialPriceEntity();
				priceEntity.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
				priceEntity.setPriceDate(entry.getKey());
				priceEntity.setPriceValue(entry.getValue());
				specialPriceEntityList.add(priceEntity);
			}
		}
		orderSaveInfo.setOrderPrices(specialPriceEntityList);
	}

	/**
	 * 获取订单特殊价格和价格
	 *
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @author afi
	 */
	private void fillOrderSpecialPrices(DataTransferObject dto, OrderNeedHouseVo houseInfo, OrderSaveVo orderSaveInfo, NeedPayFeeRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (!Check.NuNObj(houseInfo)) {
				List<SpecialPriceVo> priceList = houseInfo.getHousePriceList();
				List<HousePriceWeekConfEntity> weekPrices = houseInfo.getHouseWeekPriceList();

				List<Date> days = DateSplitUtil.dateSplit(orderSaveInfo.getOrder().getStartTime(), orderSaveInfo.getOrder().getEndTime());
				//已经存在的特殊价格
				Map<String, String> hasPriceMap = new HashMap<>();
				//周末价格
				Map<Integer, Integer> weekPriceMap = new HashMap<>();
				if (!Check.NuNObj(priceList)) {
					Map<String, String> orderDayMap = new HashMap<>();
					if (!Check.NuNCollection(weekPrices)) {
						for (HousePriceWeekConfEntity weekPrice : weekPrices) {
							//当前的周末价格
							weekPriceMap.put(weekPrice.getSetWeek(), weekPrice.getPriceVal());
						}
					}
					for (Date date : days) {
						String str = DateUtil.dateFormat(date);
						orderDayMap.put(str, str);
					}
					for (SpecialPriceVo priceConf : priceList) {
						String priceDay = DateUtil.dateFormat(priceConf.getSetDate());
						if (!orderDayMap.containsKey(priceDay)) {
							continue;
						}
						OrderSpecialPriceEntity priceEntity = new OrderSpecialPriceEntity();
						priceEntity.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
						priceEntity.setPriceDate(priceDay);
						priceEntity.setPriceValue(priceConf.getSetPrice());
						orderSaveInfo.getPriceMap().put(priceDay, priceConf.getSetPrice());
						orderSaveInfo.getOrderPrices().add(priceEntity);
						//设置已经存在的特殊价格
						hasPriceMap.put(priceDay, priceDay);
					}
				}
				//设置周末价格
				for (Date date : days) {
					String priceDay = DateUtil.dateFormat(date);
					if (hasPriceMap.containsKey(priceDay)) {
						//当前已经是特殊价格 就不再设置周末价格
						continue;
					}
					int weekNum = WeekEnum.getWeek(date).getNumber();
					if (weekPriceMap.containsKey(weekNum)) {
						OrderSpecialPriceEntity priceEntity = new OrderSpecialPriceEntity();
						priceEntity.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
						priceEntity.setPriceDate(priceDay);
						priceEntity.setPriceValue(weekPriceMap.get(weekNum));
						orderSaveInfo.getPriceMap().put(priceDay, weekPriceMap.get(weekNum));
						orderSaveInfo.getOrderPrices().add(priceEntity);
					}
				}
				orderSaveInfo.getPriceMap().put("price", FinanceMoneyUtil.getHousePrice(houseInfo));
				orderSaveInfo.getOrderHouse().setPrice(FinanceMoneyUtil.getHousePrice(houseInfo));

				//只包含基础价格，特殊价格及周末价格等优惠之前的总价格
				Integer originalPrice = 0;
				Integer price = FinanceMoneyUtil.getHousePrice(houseInfo);
				if(Check.NuNObj(price)){
					LogUtil.error(LOGGER, "【下单】 获取房钱的房间价格异常 :house {}", JsonEntityTransform.Object2Json(houseInfo));
					throw new BusinessException("房源的基础价格为空 shit!!!!");
				} 
				Set<String> daySet = DateSplitUtil.getDateSplitFullDaySet(orderSaveInfo.getOrder().getStartTime(), orderSaveInfo.getOrder().getEndTime());
				for(String day: daySet){
					if(orderSaveInfo.getPriceMap().containsKey(day)){
						int priceDay = orderSaveInfo.getPriceMap().get(day);
						originalPrice += priceDay;
					}else{
						originalPrice += price;
					}
				}	            
				orderSaveInfo.setOriginalPrice(originalPrice);
			}

			//设置夹心价格
			this.fillCrevice2SpecialPrices(dto, houseInfo, orderSaveInfo, request);

		}
	}

	/**
	 * 填充房源的锁（普通订单移到去支付时锁房源）
	 * 但是 实时订单的0元订单也需要锁房源
	 *
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @author afi
	 */
	private void fillOrderHouseLock(DataTransferObject dto, OrderNeedHouseVo houseInfo, OrderSaveVo orderSaveInfo, CreateOrderRequest request) throws Exception {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			//当前订单是0元订单 并且是实时订单
			if (orderSaveInfo.getOrderMoney().getNeedPay() == 0 && orderSaveInfo.getOrder().getOrderStatus() == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()) {
				List<Date> dateList = DateSplitUtil.dateSplit(request.getStartTime(), request.getEndTime());
				for (int i = 0; i < dateList.size(); i++) {
					HouseLockEntity lockEntity = new HouseLockEntity();
					lockEntity.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
					lockEntity.setCreateTime(orderSaveInfo.getCreateTime());
					lockEntity.setRentWay(orderSaveInfo.getOrderHouse().getRentWay());
					lockEntity.setHouseFid(orderSaveInfo.getOrderHouse().getHouseFid());
					if (orderSaveInfo.getOrderHouse().getRentWay() == RentWayEnum.ROOM.getCode()) {
						lockEntity.setRoomFid(orderSaveInfo.getOrderHouse().getRoomFid());
					}
					lockEntity.setLockTime(dateList.get(i));
					lockEntity.setLockType(LockTypeEnum.ORDER.getCode());
					lockEntity.setPayStatus(YesOrNoEnum.YES.getCode());
					orderSaveInfo.getHouseLocks().add(lockEntity);
				}
			}
		}
	}


	/**
	 * 获取订单的配置信息
	 *
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @author afi
	 */
	private void fillOrderConfigs(DataTransferObject dto, OrderNeedHouseVo houseInfo, OrderSaveVo orderSaveInfo) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (!Check.NuNObj(houseInfo)) {
				List<HouseConfMsgEntity> houseConfList = houseInfo.getHouseConfList();
				if (!Check.NuNObj(houseConfList)) {
					for (HouseConfMsgEntity houseConfig : houseConfList) {
						this.fillOrderConfigEle(orderSaveInfo, houseConfig, houseInfo);
					}
				}
			}
		}
	}

	/**
	 * 将房源的配置转化成订单的配置
	 *
	 * @param orderSaveInfo
	 * @param houseConfig
	 * @author afi
	 */
	private void fillOrderConfigEle(OrderSaveVo orderSaveInfo, HouseConfMsgEntity houseConfig, OrderNeedHouseVo houseInfo) {
		if (!Check.NuNObj(houseConfig)) {
			//订单这边只存储 如下的配置，别的配置用不到不做存储
			String code = ValueUtil.getStrValue(houseConfig.getDicCode());
			if (
					code.startsWith(ProductRulesEnum.ProductRulesEnum008.getValue())  //押金规则
					|| code.startsWith(TradeRulesEnum.TradeRulesEnum005.getValue())  //退订策略
					|| code.startsWith(TradeRulesEnum.TradeRulesEnum007.getValue())  //房东的结算方式
					|| code.startsWith(TradeRulesEnum.TradeRulesEnum009.getValue())  //收取租客佣金类型
					|| code.startsWith(TradeRulesEnum.TradeRulesEnum008.getValue())  //收取房东佣金类型
					|| code.startsWith(ProductRulesEnum.ProductRulesEnum0019.getValue())  //折扣规则[不仅是长租,所有的折扣都走,原来的折扣已经干掉了]
					//                    || code.startsWith(ProductRulesEnum.ProductRulesEnum0020.getValue())  //设置夹缝价格
					|| code.startsWith(TradeRulesEnum0020.TradeRulesEnum0020001.getValue())  //长租入住最小天数
					|| code.startsWith(TradeRulesEnum0020003.TradeRulesEnum0020003002.getValue())
					|| code.startsWith(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue())
					) {
				//当前房源的退订政策 只获取当前的退订政策下的配置信息
				String checkOutRulesCode = houseInfo.getHouseBaseExtEntity().getCheckOutRulesCode();
				if (!Check.NuNObj(houseInfo.getHouseRoomExtEntity()) && houseInfo.getRentWay() == RentWayEnum.ROOM.getCode()) {
					checkOutRulesCode = houseInfo.getHouseRoomExtEntity().getCheckOutRulesCode();
				}
				if (orderSaveInfo.getChangzuFlag()) {
					//如果是长租,直接保存长租的退订政策
					checkOutRulesCode = TradeRulesEnum005Enum.TradeRulesEnum005004.getValue();
				}

				//存计算取的枚举
				if(ProductRulesEnum0019.ProductRulesEnum0019002.getValue().equals(code) && ValueUtil.getintValue(houseConfig.getDicVal())>0 
						&& !Check.NuNObj(houseInfo.getEnumDicMap().get(code)) ){
					orderSaveInfo.getPriceEnumDicMap().put(ProductRulesEnum0019.ProductRulesEnum0019002.getValue(), houseConfig.getDicVal());
				}

				//存计算取的枚举
				if(ProductRulesEnum0019.ProductRulesEnum0019001.getValue().equals(code) && ValueUtil.getintValue(houseConfig.getDicVal())>0
						&& !Check.NuNObj(houseInfo.getEnumDicMap().get(code)) ){
					orderSaveInfo.getPriceEnumDicMap().put(ProductRulesEnum0019.ProductRulesEnum0019001.getValue(), houseConfig.getDicVal());
				}
				//记录当前的退订政策
				if (Check.NuNStr(orderSaveInfo.getCheckOutRulesCode())) {
					orderSaveInfo.setCheckOutRulesCode(checkOutRulesCode);
				}
				//当前的退订政策
				if (code.startsWith(TradeRulesEnum.TradeRulesEnum005.getValue())) {
					if (!code.startsWith(checkOutRulesCode)) {
						return;
					}
				}

				OrderConfigEntity orderConfig = new OrderConfigEntity();
				orderConfig.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
				orderConfig.setConfigCode(code);
				orderConfig.setConfigValue(houseConfig.getDicVal());
				orderConfig.setIsValid(YesOrNoEnum.YES.getCode());
				orderSaveInfo.getOrderConfigs().add(orderConfig);
			}

		}
	}

	/**
	 * 填充房东的信息
	 *
	 * @param orderSaveInfo
	 * @param houseInfo
	 * @param customer
	 * @author afi
	 */
	private void fillLandlordInfo(DataTransferObject dto, OrderSaveVo orderSaveInfo, OrderNeedHouseVo houseInfo, CustomerBaseMsgEntity customer) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (!Check.NuNObj(houseInfo)) {
				orderSaveInfo.getOrder().setLandlordUid(houseInfo.getLandlordUid());
			}
			if (Check.NuNObj(customer)) {
				LogUtil.info(LOGGER, "【下单】【异常数据】 orerSn:", orderSaveInfo.getOrder().getOrderSn());
				throw new BusinessException("customer is null");
			}
			orderSaveInfo.getOrder().setLandlordTel(customer.getCustomerMobile());
			orderSaveInfo.getOrder().setLandlordName(customer.getRealName());
		}
	}


	/**
	 * 填充用户的信息
	 *
	 * @param dto
	 * @param orderSaveInfo
	 * @param request
	 * @author afi
	 */
	private void fillTenantInfo(DataTransferObject dto, OrderNeedHouseVo houseInfo, OrderSaveVo orderSaveInfo, CreateOrderRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			//清理用户的入住人信息
			List<String> tenantFids = orderUserService.checkContact(request.getTenantFids());
			if (Check.NuNCollection(tenantFids)) {
				LogUtil.error(LOGGER, "【下单】 入住人为空 :par {}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_ERROR_TENANT));
			}
			for (String fid : tenantFids) {
				OrderContactEntity orderContactEntity = new OrderContactEntity();
				orderContactEntity.setContactFid(fid);
				orderContactEntity.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
				orderSaveInfo.getOrderContacts().add(orderContactEntity);
			}
			//房源的
			int checkInLimit = 0;
			if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode()) {
				checkInLimit = ValueUtil.getintValue(houseInfo.getRoomCheckInLimit());
			} else {
				checkInLimit = ValueUtil.getintValue(houseInfo.getHouseBaseExtEntity().getCheckInLimit());
			}
			if (checkInLimit > 0 && checkInLimit < tenantFids.size()) {
				LogUtil.error(LOGGER, "【下单】超出最大入住人数 limit：{} :par {}", checkInLimit, JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("超出最大入住人数");
				return;
			}
			orderSaveInfo.getOrder().setPeopleNum(tenantFids.size());
			this.fillUserInfo(dto, orderSaveInfo.getOrder(), request);
		}
	}

	/**
	 * 填充用户的信息
	 *
	 * @param dto
	 * @param order
	 * @param request
	 * @author afi
	 */
	private void fillUserInfo(DataTransferObject dto, OrderEntity order, CreateOrderRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			if (!Check.NuNObj(order) && !Check.NuNStr(request.getUserUid())) {
				order.setUserUid(request.getUserUid());
				order.setUserTel(request.getUserTel());
				order.setUserName(request.getUserName());
				order.setUserTelCode(request.getUserTelCode());
			}
		}
	}


	/**
	 * 校验当前的房源的基础信息
	 *
	 * @param dto
	 * @param houseInfo
	 * @param request
	 * @author afi
	 */
	private void dealCityInfoCheck(DataTransferObject dto, OrderNeedHouseVo houseInfo, NeedPayFeeRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			//1 先获取当前城市的数量
			Integer countCountLimit = null;
			//当前的城市的最大未支付数量
			for (HouseConfMsgEntity conf : houseInfo.getHouseConfList()) {
				if (conf.getDicCode().equals(TradeRulesEnum.TradeRulesEnum006.getValue())) {
					int max = ValueUtil.getintValue(conf.getDicVal());
					countCountLimit = max;
					break;
				}
			}
			if (Check.NuNObj(countCountLimit) || countCountLimit == 0) {
				//不做限制
				return;
			}
			//2 校验是否超过这个数量
			Long current = orderUserService.countCurrentNoPayOrder(houseInfo.getCityCode(), request.getUserUid());
			if (current > countCountLimit) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_COUNT_MORE));
			}

		}
	}


	/**
	 * 处理优惠券信息
	 *
	 * @param dto
	 * @param orderSaveInfo
	 * @param request
	 * @author afi
	 */
	private void dealCoupon(DataTransferObject dto, OrderSaveVo orderSaveInfo, OrderNeedHouseVo houseInfo, NeedPayFeeRequest request) {

		if (dto.getCode() == DataTransferObject.SUCCESS && !Check.NuNStr(request.getCouponSn())) {
			//获取当前的优惠券的信息
			ActCouponUserEntity couponVo = request.getActCouponUserEntity();
			//校验当前优惠券信息是否成功
			if (dto.getCode() != DataTransferObject.SUCCESS) {
				return;
			}
			if (Check.NuNObj(couponVo)) {
				LogUtil.error(LOGGER, "【下单】 优惠券信息不存在 :par {}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_CONPON_NULL));
				return;
			}

			if (Check.NuNObj(couponVo.getUid())) {
				LogUtil.error(LOGGER, "【下单】 当前优惠券未被领取 :par {}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ACT_COUPON_NO_USER));
				return;
			}

			if (!request.getUserUid().equals(couponVo.getUid())) {
				LogUtil.error(LOGGER, "【下单】 盗用别人优惠券 :par {}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("抱歉，您的优惠券已被他人领取，无法使用");
				return;
			}
			//获取当前的优惠券状态
			CouponStatusEnum couponStatusEnum = CouponStatusEnum.getByCode(couponVo.getCouponStatus());
			if (Check.NuNObj(couponStatusEnum)) {
				LogUtil.error(LOGGER, "【下单】 异常的优惠券状态 优惠券信息 {}", JsonEntityTransform.Object2Json(couponVo));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ACT_COUPON_STATUS_ERROR));
				return;
			}
			if (!couponStatusEnum.checkStatus()) {
				LogUtil.error(LOGGER, "【下单】 当前优惠券状态为不可用 优惠券信息 {}", JsonEntityTransform.Object2Json(couponVo));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ACT_COUPON_STATUS_CANOT));
				return;
			}
			//当前优惠券的开始时间
			if (!Check.NuNObj(couponVo.getCouponStartTime())) {
				if (new Date().before(couponVo.getCouponStartTime())) {
					LogUtil.error(LOGGER, "【下单】 优惠券还未到使用时间 优惠券信息 {}", JsonEntityTransform.Object2Json(couponVo));
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("优惠券未到使用日期，");
					return;
				}
			}
			//当前优惠券的结束时间校验
			if (!Check.NuNObj(couponVo.getCouponEndTime())) {
				if (couponVo.getCouponEndTime().before(new Date())) {
					LogUtil.error(LOGGER, "【下单】 优惠券过期 优惠券信息 {}", JsonEntityTransform.Object2Json(couponVo));
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_CONPON_END));
					return;
				}
			}


			//当前优惠券在当前城市不可用
			if (!checkCityCode(orderSaveInfo.getOrder().getCityCode(), couponVo.getCityList())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ACT_COUPON_CITY_CANOT));
				return;
			}

			//校验是否匹配该房源
			if (couponVo.getIsLimitHouse() == YesOrNoEnum.YES.getCode()) {
				if (!checkLimitHouse(houseInfo.getHouserOrRoomSn(), couponVo.getLimitHouseList())) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ACT_COUPON_HOUSE_CANOT));
					return;
				}
			}

			//校验当前优惠券在订单库中是否可用
			this.checkCouponInOrderAc(dto, request.getCouponSn());
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				//赋值优惠券信息
				orderSaveInfo.setCouponEntity(couponVo);
			}
		}
	}

	/**
	 * 校验限制房源
	 *
	 * @param houserOrRoomSn
	 * @param limitHouseList
	 * @return
	 */
	private boolean checkLimitHouse(String houserOrRoomSn, List<ActivityHouseEntity> limitHouseList) {
		boolean flag = false;
		if (Check.NuNCollection(limitHouseList)) {
			return flag;
		}
		for (ActivityHouseEntity acHouse : limitHouseList) {
			if (houserOrRoomSn.equals(acHouse.getHouseSn())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 校验当前城市是否可用
	 *
	 * @param cityCode
	 * @param cityList
	 * @return
	 */
	private boolean checkCityCode(String cityCode, List<ActivityCityEntity> cityList) {
		boolean flag = false;
		if (Check.NuNStr(cityCode) || Check.NuNObj(cityList)) {
			return flag;
		}
		for (ActivityCityEntity cityEntity : cityList) {
			if (cityEntity.getCityCode().equals("0")) {
				return true;
			} else if (cityEntity.getCityCode().equals(cityCode)) {
				return true;
			}
		}
		return flag;
	}

	/**
	 * 验证当前订单活动的使用情况
	 *
	 * @param dto
	 * @param conponSn
	 */
	private void checkCouponInOrderAc(DataTransferObject dto, String conponSn) {
		if (dto.getCode() == DataTransferObject.SUCCESS && !Check.NuNStr(conponSn)) {
			boolean check = orderActivityService.checkCouponOk(conponSn);
			if (!check) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ACT_COUPON_USED));
			}
		}
	}


	/**
	 * 校验当前的房源的基础信息
	 *
	 * @param dto
	 * @param houseInfo
	 * @param request
	 * @author afi
	 */
	private void dealHouseInfoCheck(DataTransferObject dto, OrderNeedHouseVo houseInfo, NeedPayFeeRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {

			//1 校验当前的房源信息
			if (Check.NuNObj(houseInfo)) {
				LogUtil.error(LOGGER, "【下单】 校验房源失败 houseInfo is nullpar：{}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_INFO_ERROR));
				return;
			}
			//获取当前的房源状态
			Integer status = getHouseStatus(houseInfo);
			if (Check.NuNObj(status) || status != HouseStatusEnum.SJ.getCode()) {
				LogUtil.error(LOGGER, "【下单】 房源未上架houseFid：{}", request.getFid());
				//当前房源未上架
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_STATUS_ERROR));
				return;
			}

			if (request.getRentWay() != houseInfo.getRentWay()) {
				LogUtil.error(LOGGER, "【下单】 异常的房源类型：房源fid;{} 参数类型：{} 房源类型：{}", request.getFid(), request.getRentWay(), houseInfo.getRentWay());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_INFO_ERROR));
				return;
			}

			//2 校验房源的截止日期
			if (!Check.NuNObj(houseInfo.getTillDate()) && (DateUtil.getDatebetweenOfDayNum(houseInfo.getTillDate(), request.getEndTime()) >= 1)) {
				LogUtil.error(LOGGER, "【下单】 超过房源的截止时间 houseFid：{} 截止时间:{}", request.getFid(), DateUtil.dateFormat(houseInfo.getTillDate()));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_ERROR_TIME));
				return;
			}

			//3 校验当前的最大最小入住天数
			try {
				int countDays = DateSplitUtil.countDateSplit(request.getStartTime(), request.getEndTime());
                int minDay = 0;
                if (!Check.NuNObj(houseInfo.getHouseBaseExtEntity().getMinDay())) {
                    minDay = houseInfo.getHouseBaseExtEntity().getMinDay();
                }
                if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNObj(houseInfo.getHouseRoomExtEntity())) {
                    minDay = houseInfo.getHouseRoomExtEntity().getMinDay();
                }
                if (minDay > countDays) {
					LogUtil.info(LOGGER, "【下单】 超过最小入住天数 houseFid：{} 最小天数：{}，当天天数：{}", request.getFid(), houseInfo.getHouseBaseExtEntity().getMinDay(), countDays);
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_ERROR_TIME_MIN));
					return;
				}
			} catch (Exception e) {
                LogUtil.error(LOGGER, "【下单】 天数校验失败houseInf:{},e={}", JsonEntityTransform.Object2Json(houseInfo), e);
                throw new BusinessException("error on check the day");
			}
		}
	}

	/**
	 * 获取当前的状态
	 *
	 * @param houseInfo
	 * @return
	 * @author afi
	 */
	private Integer getHouseStatus(OrderNeedHouseVo houseInfo) {
		if (houseInfo.getRentWay() == RentWayEnum.HOUSE.getCode()) {
			return houseInfo.getHouseStatus();
		} else {
			return houseInfo.getRoomStatus();
		}
	}


	/**
	 * 校验当前的房源是否被占有
	 *
	 * @param dto
	 * @param request
	 * @author afi
	 */
	private void dealHouseStoreCheck(DataTransferObject dto, NeedPayFeeRequest request) {
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			Long countCurrent = null;
			if (request.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				//整租
				countCurrent = orderUserService.countHouseLock(request.getFid(), request.getStartTime(), request.getEndTime());
			} else if (request.getRentWay() == RentWayEnum.ROOM.getCode()) {
				//合租
				countCurrent = orderUserService.countRoomLock(request.getFid(), request.getStartTime(), request.getEndTime());
			} else if (request.getRentWay() == RentWayEnum.BED.getCode()) {
				//床位
				countCurrent = orderUserService.countBedLock(request.getFid(), request.getStartTime(), request.getEndTime());
			} else {
				//异常的code
				LogUtil.error(LOGGER, "【下单】 异常的房源类型:{}", request.getRentWay());
				throw new BusinessException("error houseType current rentWay is:" + request.getRentWay());
			}
			if (!Check.NuNObj(countCurrent) && countCurrent > 0) {
				LogUtil.info(LOGGER, "【下单】 房源已经被占有:par {}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_OCCUPY));
			}
		}
	}

	@Override
	public String unlockHouse(String unlocakHouseJson) {
		DataTransferObject dto = new DataTransferObject();
		LockHouseRequest request = JsonEntityTransform.json2Object(unlocakHouseJson, LockHouseRequest.class);
		try {
			LogUtil.info(LOGGER, "【解锁房源】 开始解锁房源。。。。。。。。。。。。。");
			LogUtil.info(LOGGER, "【解锁房源】 开始解锁房源 par:{}", unlocakHouseJson);
			if (Check.NuNObj(request)) {
				//参数异常
				LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNObj(request.getRentWay()) || Check.NuNCollection(request.getLockDayList())) {
				//参数异常
				LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (request.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.ROOM.getCode()) {
				if (Check.NuNStr(request.getHouseFid()) || Check.NuNStr(request.getRoomFid())) {
					LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.BED.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					//参数异常
					LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else {
				LogUtil.error(LOGGER, "【解锁房源】 房源出租类型错误 par:{}", unlocakHouseJson);
				throw new BusinessException("error rentWay");
			}
			List<HouseLockEntity> houseLockEntitys = houseLockService.checkHouseLockList(request);
			if (Check.NuNCollection(houseLockEntitys)) {
				LogUtil.error(LOGGER, "【解锁房源】 当前房源未锁定par:{}", unlocakHouseJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_UNLOCK));
				return dto.toJsonString();
			}
			houseLockService.phyDeleteHouseLockList(houseLockEntitys);
			
			if(!Check.NuNStr(request.getHouseFid())){
				LogUtil.info(LOGGER, "房源解锁成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(request.getHouseFid(), 
						request.getRoomFid(), request.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源解锁成功,推送队列消息结束,推送内容:{}", pushContent);
			}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【解锁房源】e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	@Override
	public String unlockHouseForPC(String unlocakHouseJson) {
		DataTransferObject dto = new DataTransferObject();
		LockHouseRequest request = JsonEntityTransform.json2Object(unlocakHouseJson, LockHouseRequest.class);
		try {
			LogUtil.info(LOGGER, "【解锁房源】 开始解锁房源。。。。。。。。。。。。。");
			LogUtil.info(LOGGER, "【解锁房源】 开始解锁房源 par:{}", unlocakHouseJson);
			if (Check.NuNObj(request)) {
				//参数异常
				LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNObj(request.getRentWay()) || Check.NuNCollection(request.getLockDayList())) {
				//参数异常
				LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (request.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.ROOM.getCode()) {
				if (Check.NuNStr(request.getHouseFid()) || Check.NuNStr(request.getRoomFid())) {
					LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.BED.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					//参数异常
					LogUtil.error(LOGGER, "【解锁房源】 参数异常par:{}", unlocakHouseJson);
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else {
				LogUtil.error(LOGGER, "【解锁房源】 房源出租类型错误 par:{}", unlocakHouseJson);
				throw new BusinessException("error rentWay");
			}
			List<HouseLockEntity> houseLockEntitys = houseLockService.checkHouseLockList(request);
			if (Check.NuNCollection(houseLockEntitys)) {
				return dto.toJsonString();
			}
			houseLockEntitys = houseLockEntitys.stream().filter(lock -> lock.getLockType() == LockTypeEnum.LANDLADY.getCode()).collect(Collectors.toList());
			if (Check.NuNCollection(houseLockEntitys)){
				return dto.toJsonString();
			}
			houseLockService.phyDeleteHouseLockList(houseLockEntitys);
			
			if(!Check.NuNStr(request.getHouseFid())){
				LogUtil.info(LOGGER, "房源解锁成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(request.getHouseFid(), 
						request.getRoomFid(), request.getRentWay(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "房源解锁成功,推送队列消息结束,推送内容:{}", pushContent);
			}
			
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【解锁房源】e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 获取房间或房源30天出租天数
	 */
	@Override
	public String getBookDaysByFid(String requestJson) {
		DataTransferObject dto = new DataTransferObject();
		LockHouseCountRequest request = JsonEntityTransform.json2Object(requestJson, LockHouseCountRequest.class);
		try {
			LogUtil.info(LOGGER, "【获取30天出租天数】  par:{}", requestJson);
			if (Check.NuNObj(request)) {
				//参数异常
				LogUtil.error(LOGGER, "【获取30天出租天数】 参数异常par:{}", requestJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNObj(request.getRentWay()) || Check.NuNObj(request.getStartTime()) || Check.NuNObj(request.getEndTime())) {
				//参数异常
				LogUtil.error(LOGGER, "【获取30天出租天数】 参数异常par:{}", requestJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (request.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					LogUtil.error(LOGGER, "【获取房源30天出租天数】 参数异常par:{}", requestJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.ROOM.getCode()) {
				if (Check.NuNStr(request.getHouseFid()) || Check.NuNStr(request.getRoomFid())) {
					LogUtil.error(LOGGER, "【获取房间30天出租天数】 参数异常par:{}", requestJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.BED.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					//参数异常
					LogUtil.error(LOGGER, "【获取30天出租天数】 参数异常par:{}", requestJson);
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else {
				LogUtil.error(LOGGER, "【获取源30天出租天数】 房源出租类型错误 par:{}", requestJson);
				throw new BusinessException("error rentWay");
			}
			int count = houseLockService.getBookDaysByFid(request);
			dto.putValue("count", count);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【获取30天出租天数】e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 计算房间或者房源30天出租率
	 */
	@Override
	public String getBookRateByFid(String requestJson) {
		DataTransferObject dto = new DataTransferObject();
		LockHouseCountRequest request = JsonEntityTransform.json2Object(requestJson, LockHouseCountRequest.class);
		try {
			LogUtil.info(LOGGER, "【获取30天出租天数】  par:{}", requestJson);
			if (Check.NuNObj(request)) {
				//参数异常
				LogUtil.error(LOGGER, "【获取30天出租天数】 参数异常par:{}", requestJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNObj(request.getRentWay()) || Check.NuNObj(request.getStartTime()) || Check.NuNObj(request.getEndTime())) {
				//参数异常
				LogUtil.error(LOGGER, "【获取30天出租天数】 参数异常par:{}", requestJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if (request.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					LogUtil.error(LOGGER, "【获取房源30天出租天数】 参数异常par:{}", requestJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.ROOM.getCode()) {
				if (Check.NuNStr(request.getHouseFid()) || Check.NuNStr(request.getRoomFid())) {
					LogUtil.error(LOGGER, "【获取房间30天出租天数】 参数异常par:{}", requestJson);
					//参数异常
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else if (request.getRentWay() == RentWayEnum.BED.getCode()) {
				if (Check.NuNStr(request.getHouseFid())) {
					//参数异常
					LogUtil.error(LOGGER, "【获取30天出租天数】 参数异常par:{}", requestJson);
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					return dto.toJsonString();
				}
			} else {
				LogUtil.error(LOGGER, "【获取源30天出租天数】 房源出租类型错误 par:{}", requestJson);
				throw new BusinessException("error rentWay");
			}
			request.setLockType(LockTypeEnum.ORDER.getCode());
			int orderLockCount = houseLockService.getBookDaysByFid(request);
			request.setLockType(LockTypeEnum.LANDLADY.getCode());
			int lanLockCount = houseLockService.getBookDaysByFid(request);
			Double houseBookRate = 0.0;
			if (lanLockCount >= 30) {
				houseBookRate = 0.0;
			} else {
				houseBookRate = BigDecimalUtil.div(orderLockCount * 100, 30.0 - lanLockCount, 1);
			}
			dto.putValue("rate", houseBookRate);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【获取30天出租率异常】e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 获取订单需支付金额明细-新
	 *
	 * @param requestJson
	 * @return
	 * @author lishaochuan
	 * @create 2016年8月19日下午3:13:39
	 */
	@Override
	public String needPayForLan(String requestJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			NeedPayFeeRequest request = JsonEntityTransform.json2Object(requestJson, NeedPayFeeRequest.class);

			// 处理需要支付金额数据
			OrderNeedHouseVo houseInfo = this.getHouseInfo(dto, request);
			OrderSaveVo orderSaveInfo = this.dealNeedPayData(request, dto, houseInfo);
			if (dto.getCode() != DataTransferObject.SUCCESS) {
				return dto.toJsonString();
			}

			OrderMoneyEntity orderMoney = orderSaveInfo.getOrderMoney();
			NeedPayFeeResponse need = new NeedPayFeeResponse();

			//房东预计收入 = 房租-折扣金额-房东佣金+清洁费
			int totalFee = orderSaveInfo.getCost()  - orderSaveInfo.getOrderMoney().getDiscountMoney()
					//  + orderSaveInfo.getOrderMoney().getDepositMoney()
					- orderSaveInfo.getOrderMoney().getLanCommMoney()
					+ orderSaveInfo.getOrderMoney().getCleanMoney();
			need.setTotalFee(totalFee);
			need.setFeeUnit(OrderFeeConst.FEE_UNIT.getShowName());
			need.setLabelTipsList(getLabelTipsList(houseInfo,orderSaveInfo));			

			int originalPrice = orderSaveInfo.getOriginalPrice();

			int originalUserCommMoney =orderSaveInfo.getOrderMoney().getUserCommMoney();
			HouseConfMsgEntity  userCommMoneyConfMsgEntity = null;
			for(HouseConfMsgEntity  confMsgEntity :houseInfo.getHouseConfList()){
				if (confMsgEntity.getDicCode().equals(TradeRulesEnum009Enum.TradeRulesEnum009001.getValue())) {
					userCommMoneyConfMsgEntity = confMsgEntity;
				}
			}

			if (!Check.NuNObj(userCommMoneyConfMsgEntity)) {
				originalUserCommMoney = Double.valueOf(originalPrice * ValueUtil.getdoubleValue(userCommMoneyConfMsgEntity.getDicVal())).intValue(); 
			}

			originalPrice = originalPrice + originalUserCommMoney			
					+ orderSaveInfo.getOrderMoney().getDepositMoney()
					+ orderSaveInfo.getOrderMoney().getCleanMoney();

			if (orderMoney.getNeedPay()<originalPrice) {
				need.setOriginalPrice(originalPrice);
			}else{
				need.setOriginalPrice(null);
			}
            Integer versionCode = request.getVersionCode();
            this.addItemFee(need, orderMoney.getRentalMoney(), versionCode, OrderFeeConst.NEED_PAY_COST, orderSaveInfo);
            //this.addItemFee(need, orderMoney.getDepositMoney(), OrderFeeConst.NEED_PAY_DEPOSIT, orderSaveInfo);
            this.addItemFee(need, orderMoney.getCleanMoney(), versionCode, OrderFeeConst.NEED_PAY_CLEAN, orderSaveInfo);
            this.addItemFee(need, orderMoney.getDiscountMoney(), versionCode, OrderFeeConst.NEED_PAY_DISCOUNT_LAN, orderSaveInfo);
            //this.addItemFee(need, orderMoney.getCouponMoney(), OrderFeeConst.NEED_PAY_COUPON, orderSaveInfo);
            this.addItemFee(need, orderMoney.getLanCommMoney(), versionCode, OrderFeeConst.NEED_PAY_LAN_COMMISSION, orderSaveInfo);


			dto = new DataTransferObject(DataTransferObject.SUCCESS, "", need.toMap());

			if(Check.NuNObj(orderMoney.getCouponMoney()) || orderMoney.getCouponMoney() == 0){
				dto.putValue("couponMoney", "");
			}else{
				dto.putValue("couponMoney", "-" + OrderFeeConst.FEE_UNIT.getShowName() + DataFormat.formatHundredPrice(orderMoney.getCouponMoney()));
			}

			dto.putValue("notices", orderSaveInfo.getCheckOutRulesCode());

			// 入住人数限制
			int checkInLimit = 0;
			if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode()) {
				checkInLimit = ValueUtil.getintValue(houseInfo.getRoomCheckInLimit());
			} else {
				checkInLimit = ValueUtil.getintValue(houseInfo.getHouseBaseExtEntity().getCheckInLimit());
			}
			dto.putValue("checkInLimit", checkInLimit);

			// 最少入住天数
			int minDay = 0;
			if (!Check.NuNObj(houseInfo.getHouseBaseExtEntity().getMinDay())) {
				minDay = houseInfo.getHouseBaseExtEntity().getMinDay();
			}
            if (houseInfo.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNObj(houseInfo.getHouseRoomExtEntity())) {
                minDay = houseInfo.getHouseRoomExtEntity().getMinDay();
            }
            dto.putValue("minDay", minDay);

			dto.putValue("orderType", houseInfo.getHouseBaseExtEntity().getOrderType());
			dto.putValue("orderTypeName", OrderTypeEnum.getEnumMap().get(houseInfo.getHouseBaseExtEntity().getOrderType()));

		} catch (Exception e) {
			LogUtil.error(LOGGER, "needPay 参数:{}", requestJson);
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

}
