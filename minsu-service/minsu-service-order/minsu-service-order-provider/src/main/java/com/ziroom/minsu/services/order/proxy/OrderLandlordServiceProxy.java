package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.house.dto.SmartLockDto;
import com.ziroom.minsu.services.order.api.inner.OrderLoadlordService;
import com.ziroom.minsu.services.order.constant.OrderMessageConst;
import com.ziroom.minsu.services.order.dto.LoadlordRequest;
import com.ziroom.minsu.services.order.dto.OrderProfitRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.*;
import com.ziroom.minsu.valenum.customer.CustomerBehaviorRoleEnum;
import com.ziroom.minsu.valenum.customer.LandlordBehaviorEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 
 * <p>订单服务代理层</p>
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
@Component("order.orderLandlordServiceProxy")
public class OrderLandlordServiceProxy implements OrderLoadlordService{

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderLandlordServiceProxy.class);
	@Resource(name = "order.messageSource")
	private MessageSource messageSource;
	@Resource(name = "order.orderLoadlordServiceImpl")
	private OrderLoadlordServiceImpl orderLoadlordServiceImpl;
	@Resource(name="order.orderMoneyServiceImpl")
	private OrderMoneyServiceImpl orderMoneyService;

	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonService;

	@Resource(name = "order.orderConfigServiceImpl")
	private OrderConfigServiceImpl orderConfigService;

	@Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;

    @Resource(name = "order.orderActivityServiceImpl")
    private OrderActivityServiceImpl orderActivityService;

    @Resource(name = "order.dealPayServiceProxy")
    private DealPayServiceProxy dealPayServiceProxy;

	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;

	@Resource(name = "behavior.queueName")
	private QueueName queueName;

	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<>());

    /**
     * 安装智能锁
     * @author afi
     * @param parJson
     * @return
     */
    @Override
    public String installHouseLock(String parJson) {
        DataTransferObject dto = new DataTransferObject();


        SmartLockDto lockDto = JsonEntityTransform.json2Object(parJson, SmartLockDto.class);

        if(Check.NuNObj(lockDto)){
            //参数异常
            dto.setErrCode(MessageSourceUtil.getIntMessage(  messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }

        if (Check.NuNStr(lockDto.getHouseFid()) && Check.NuNCollection(lockDto.getRoomFidList())){
            //参数异常
            dto.setErrCode(MessageSourceUtil.getIntMessage(  messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        installHouse(lockDto);
        return dto.toJsonString();
    }


    /**
     * 给房源安装智能锁
     * @author afi
     * @param lockDto
     */
    private void installHouse(final SmartLockDto lockDto){
        Thread task = new Thread(){
            @Override
            public void run() {
                try {
                    List<String> orderSnList = orderLoadlordServiceImpl.getOrderSnList4Lock(lockDto);
                    if (!Check.NuNCollection(orderSnList)){
                        for (String orderSn : orderSnList) {
                            orderLoadlordServiceImpl.installLockByOrderSn(orderSn);
                        }
                    }
                }catch (Exception e){
                    LogUtil.error(LOGGER, "安装智能锁同步订单失败：houseFid{}", JsonEntityTransform.Object2Json(lockDto));
                    LogUtil.error(LOGGER, "{}", e);
                }

            }
        };
        SendThreadPool.execute(task);
    }


	/**
	 * 
	 * 接受订单  
	 * 1.校验订单上一状态 只能为10=待确认  支付状态 为0 = 未支付
	 *  更新到 20：待入住
	 * —— 更新状态  更新时间
	 * @author yd
	 * @created 2016年4月3日 下午8:55:44
	 * @param loadlordRequestStr
	 * @return
	 */
	@Override
	public String acceptOrder(String loadlordRequestStr) {

		LoadlordRequest loadlordRequest = JsonEntityTransform.json2Object(loadlordRequestStr, LoadlordRequest.class);
		DataTransferObject dto = new DataTransferObject();

		if(checkOrder(loadlordRequest) && loadlordRequest.getOrderStatus() != OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()){
			LogUtil.info(LOGGER,"房东对订单更新，更新后的状态只能是{}",OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房东对订单更新，更新后的状态只能是["+OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()+"]");
			dto.putValue("result", "failed");
		}
        LogUtil.info(LOGGER,"【接受订单】，开始接受订单 orderSN:{}",loadlordRequest.getOrderSn());
		OrderInfoVo orderEntity = orderCommonService.getOrderInfoByOrderSn(loadlordRequest.getOrderSn());
		
		if(Check.NuNObj(orderEntity)){
            LogUtil.info(LOGGER,"【接受订单】，当前订单不存在 orderSN:{}",loadlordRequest.getOrderSn());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
			return dto.toJsonString();
		}
		
		if(Check.NuNStr(orderEntity.getLandlordUid()) || !orderEntity.getLandlordUid().equals(loadlordRequest.getLandlordUid())){
			//没有权限
            LogUtil.info(LOGGER,"【接受订单】，没有权限 par:{}",loadlordRequestStr);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
			return dto.toJsonString();
		}
		
		if(Check.NuNObj(orderEntity.getOrderStatus())
                ||Check.NuNObj(orderEntity.getPayStatus())
                ||orderEntity.getOrderStatus()!=OrderStatusEnum.WAITING_CONFIRM.getOrderStatus()){

            LogUtil.info(LOGGER,"【接受订单】，房钱状态异常 orderEntity:{}",JsonEntityTransform.Object2Json(orderEntity));
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
			return dto.toJsonString();

		}
        int payStatus = OrderPayStatusEnum.UN_PAY.getPayStatus();
        if (orderEntity.getNeedPay() == 0){
            payStatus = OrderPayStatusEnum.HAS_PAY.getPayStatus();
        }
		if(this.orderLoadlordServiceImpl.acceptOrder(loadlordRequest.getOrderSn(), loadlordRequest.getOrderStatus(),payStatus,loadlordRequest.getLandlordUid())){
            //当前订单为0元订单，直接优惠券支付
            if (orderEntity.getNeedPay() == 0){

				List<Integer> types = Arrays.asList(OrderAcTypeEnum.COUPON.getCode(), OrderAcTypeEnum.FIRST_ORDER_REDUC.getCode());
				List<OrderActivityInfoVo> orderActList = orderActivityService.listOrderActByOrderSnAndType(orderEntity.getOrderSn(),types);
                //直接优惠券支付
                dealPayServiceProxy.dealZeroPayCallBack(orderActList,orderEntity);
                LogUtil.info(LOGGER, "【接受订单】，【0元订单】 orderSN:{}", loadlordRequest.getOrderSn());
            }

			dto.putValue("result", "success");
            LogUtil.info(LOGGER, "【接受订单】，接受订单成功 orderSN:{}", loadlordRequest.getOrderSn());
		}
		
		if(!Check.NuNStr(loadlordRequest.getCountryCode())){
			orderEntity.setCountryCode(loadlordRequest.getCountryCode());
		}
        //如果处理成功直接发送消息
        orderMsgProxy.sendMsg4AcceptOrder(dto,orderEntity);
		return dto.toJsonString();
	}


	/**
	 * 3.房东输入额外消费金额  订单状态更新到 60：待用户确认额外消费
	 * —— 更新状态  更新时间  更新参数表记录额外消费明细
	 * @author yd
	 * @created 2016年4月3日 下午8:58:22
	 * @param loadlordRequestStr  orderStatus 待更新的状态值 60 paramValue  拒绝原因或额外消费明细
	 * @returnsaveOtherMoney
	 */
	//	public String saveOtherMoney(String loadlordRequestStr){
	//		LoadlordRequest loadlordRequest = JsonEntityTransform.json2Object(loadlordRequestStr, LoadlordRequest.class);
	//		DataTransferObject dto = new DataTransferObject();
	//		if(checkOrder(loadlordRequest) && loadlordRequest.getOrderStatus() != OrderStatusEnum.WAITING_EXT.getOrderStatus()){
	//
	//			LogUtil.debug(LOGGER, "房东对订单更新，更新后的状态只能是{}", OrderStatusEnum.WAITING_EXT.getOrderStatus());
	//			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	//			dto.setMsg("房东对订单更新，更新后的状态只能是["+OrderStatusEnum.WAITING_EXT.getOrderStatus()+"]");
	//			dto.putValue("result", "failed");
	//
	//		}
	//		if(this.orderLoadlordServiceImpl.saveOtherMoney(loadlordRequest.getOrderSn(), loadlordRequest.getOrderStatus(), loadlordRequest.getParamValue(), loadlordRequest.getOtherMoney(), loadlordRequest.getLandlordUid())){
	//			dto.putValue("result", "success");
	//
	//		}
	//		return dto.toJsonString();
	//	}




	/**
	 * 获取当前订单的额外消费的最大值
	 * @author afi
	 * @param loadlordRequestStr
	 * @return
	 */
	public String getOtherMoneyLimit(String loadlordRequestStr){
		DataTransferObject dto = new DataTransferObject();
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug(loadlordRequestStr);
		}
		LoadlordRequest request = JsonEntityTransform.json2Object(loadlordRequestStr, LoadlordRequest.class);
		if(Check.NuNStr(request.getLandlordUid()) || Check.NuNStr(request.getOrderSn())){
			//参数异常
			dto.setErrCode(MessageSourceUtil.getIntMessage(  messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try{
			OrderInfoVo orderEntity = orderCommonService.getOrderInfoByOrderSn(request.getOrderSn());
			if(Check.NuNObj(orderEntity)){
				//当前订单不存在
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNStr(orderEntity.getLandlordUid()) || !orderEntity.getLandlordUid().equals(request.getLandlordUid())){
				//没有权限
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
				return dto.toJsonString();
			}
			OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(orderEntity.getOrderStatus());
			if(Check.NuNObj(orderStatus)){
				//异常的订单状态
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}
			int otherMoneyLimit = orderMoneyService.getOtherMoneyLimit(orderEntity);
			dto.putValue("otherMoneyLimit",otherMoneyLimit);
		}catch (Exception e){
			LogUtil.error(LOGGER, "{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_OP_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}


	/**
	 * 房东确认额外消费
	 * @author afi
	 * @param loadlordRequestStr
	 * @return
	 */
	public String saveOtherMoney(String loadlordRequestStr){
		DataTransferObject dto = new DataTransferObject();
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug(loadlordRequestStr);
		}
		LoadlordRequest request = JsonEntityTransform.json2Object(loadlordRequestStr, LoadlordRequest.class);
		if(Check.NuNStr(request.getLandlordUid()) || Check.NuNStr(request.getOrderSn())){
            LogUtil.info(LOGGER,"【确认额外消费】 确认额外消费参数 par:{}",loadlordRequestStr);
			//参数异常
			dto.setErrCode(MessageSourceUtil.getIntMessage(  messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
        LogUtil.info(LOGGER,"【确认额外消费】，开始确认额外消费 orderSN:{}",request.getOrderSn());

		try{
			OrderInfoVo orderEntity = orderCommonService.getOrderInfoByOrderSn(request.getOrderSn());
			if(Check.NuNObj(orderEntity)){
				//当前订单不存在
                LogUtil.info(LOGGER,"【确认额外消费】，订单不存在 orderSN:{}",request.getOrderSn());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNStr(orderEntity.getLandlordUid()) || !orderEntity.getLandlordUid().equals(request.getLandlordUid())){
				//没有权限
                LogUtil.info(LOGGER,"【确认额外消费】，无权限 par:{}",loadlordRequestStr);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
				return dto.toJsonString();
			}
			OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(orderEntity.getOrderStatus());
			if(Check.NuNObj(orderStatus)){
				//异常的订单状态
                LogUtil.info(LOGGER,"【确认额外消费】，订单状态异常 order:{}",JsonEntityTransform.Object2Json(orderEntity));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}
			//校验当前的额外消费金额
			this.checkOtherMoney(dto,orderEntity,request.getOtherMoney());
			if(dto.getCode() != DataTransferObject.SUCCESS){
				//异常的金额
				return dto.toJsonString();
			}
			//校验当前状态是否可以确认额外消费
			OrderStatusEnum nextStatus = orderStatus.getOtherMoneyStatus(request.getOtherMoney());
			if(nextStatus == null){
				//当前状态异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
				return dto.toJsonString();
			}
			if(!Check.NuNObj(request.getOtherMoney()) && request.getOtherMoney() >  0){
                LogUtil.info(LOGGER,"【确认额外消费】，当前金额大于0 只保存额外消费信息 orderSN:{}",request.getOrderSn());
				//金额大于0 只是更改订单状态
				orderLoadlordServiceImpl.updateOtherMoneyOnly(orderEntity,request.getOtherMoney(), request.getParamValue(), dto);
			}else {
				//获取当前订单的配置信息
				OrderConfigVo config =orderConfigService.getOrderConfigVo(orderEntity.getOrderSn());
				//直接走结算
                LogUtil.info(LOGGER,"【确认额外消费】，当前金额为0，直接走结算的逻辑 orderSN:{}",request.getOrderSn());
				orderLoadlordServiceImpl.updateOtherMoneyAndCheck(config, orderEntity, request.getOtherMoney(), dto);
			}
			if(dto.getCode() == DataTransferObject.SUCCESS){
				//如果处理成功 发送消息
				orderMsgProxy.sendMsg4SaveOtherMoney(orderEntity, request.getOtherMoney());
			}
		}catch (Exception e){
            LogUtil.error(LOGGER, "【确认额外消费】，系统异常 orderSN:{}", request.getOrderSn());
			LogUtil.error(LOGGER, "{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_OP_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 额外消费金额校验
	 * @param dto
	 * @param orderEntity
	 * @param otherMoney
	 */
	private void checkOtherMoney(DataTransferObject dto,OrderInfoVo orderEntity,int otherMoney){
		if(Check.NuNObj(orderEntity)){
			return;
		}
		if(dto.getCode() == DataTransferObject.SUCCESS){
			if(otherMoney > orderMoneyService.getOtherMoneyLimit(orderEntity)
					|| otherMoney<0){
				//异常的金额
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_OTHER_MONRY_ERROR));
			}

		}
	}

	/**
	 * 
	 * 2.拒绝订单 更新到 31：房东已拒绝     —— 更新状态  更新时间  更新参数表 记录拒绝原因
	 * paramValue 拒绝原因
	 * @author yd
	 * @created 2016年4月3日 下午8:58:22
	 *
	 * @param loadlordRequestStr
	 * @return
	 */
	@Override
	public String refusedOrder(String loadlordRequestStr) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(loadlordRequestStr)){
			LogUtil.info(LOGGER, "【拒绝订单】 refusedOrder 参数为空 param:{}",loadlordRequestStr);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数为空");
			return dto.toJsonString();
		}
		LoadlordRequest loadlordRequest = JsonEntityTransform.json2Object(loadlordRequestStr, LoadlordRequest.class);
		// 参数校验
		this.checkResuseRequest(dto, loadlordRequest);
		if (dto.getCode() == DataTransferObject.ERROR){
			return dto.toJsonString();
		}
		
        LogUtil.info(LOGGER, "【拒绝订单】，开始拒绝订单。。。。。。 orderSN:{}", loadlordRequest.getOrderSn());
		OrderInfoVo orderEntity = orderCommonService.getOrderInfoByOrderSn(loadlordRequest.getOrderSn());
		
		if(Check.NuNObj(orderEntity)){
            LogUtil.info(LOGGER, "【拒绝订单】 当前订单不存在 orderSN:{}",loadlordRequest.getOrderSn());
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_EXIT_NULL));
			return dto.toJsonString();
		}
		
		//校验权限
		if(Check.NuNStr(orderEntity.getLandlordUid()) || !orderEntity.getLandlordUid().equals(loadlordRequest.getLandlordUid())){
            LogUtil.info(LOGGER,"【拒绝订单】 没有权限 par:{}",loadlordRequestStr);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_AUTH_NULL));
			return dto.toJsonString();
		}
		// 校验状态
		if(Check.NuNObj(orderEntity.getOrderStatus())||Check.NuNObj(orderEntity.getPayStatus())||orderEntity.getOrderStatus()!=OrderStatusEnum.WAITING_CONFIRM.getOrderStatus()
				||orderEntity.getPayStatus()!=OrderPayStatusEnum.UN_PAY.getPayStatus()){
            LogUtil.info(LOGGER, "【拒绝订单】 当前订单不能被拒绝 order:{}",JsonEntityTransform.Object2Json(orderEntity));
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_STATUS_ERROR));
			return dto.toJsonString();
		}
		
		// 开始拒绝
		boolean refuseFlag = this.orderLoadlordServiceImpl.refusedOrder(loadlordRequest,orderEntity.getCouponMoney());
		
		if (!refuseFlag) {
			LogUtil.error(LOGGER, "【拒绝订单】 拒绝失败 loadlordRequest:{},refuseFlag:{}", JsonEntityTransform.Object2Json(loadlordRequest), refuseFlag);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "【拒绝订单】，拒绝成功。。。。。。 orderSN:{}", loadlordRequest.getOrderSn());

        if (dto.getCode() == DataTransferObject.SUCCESS  && orderEntity.getCouponMoney() > 0){
            //拒绝成功 并且大于0 直接返回优惠券号码
            String couponSn = orderActivityService.getCouponByOrderSn(orderEntity.getOrderSn());
            if (!Check.NuNStr(couponSn)){
                dto.putValue("couponSn",couponSn);
            }
        }
		//处理成功发送短信
		orderMsgProxy.sendMsg4OrderRefused2User(orderEntity,dto);
        //记录用户行为
		saveRefuseOrderBehavior(loadlordRequest);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 校验订单
	 *
	 * @author yd
	 * @created 2016年4月3日 下午9:03:10
	 *
	 * @param loadlordRequest
	 * @return
	 */
	private boolean checkOrder(LoadlordRequest loadlordRequest){
		// 校验订单 1.校验传入状态  2.校验订单状态   

		if(Check.NuNObj(loadlordRequest)){
			LOGGER.error("请求参数为null");
			return false;
		}
		int orderStatus = loadlordRequest.getOrderStatus();
		String orderSn = loadlordRequest.getOrderSn();
		if( orderStatus != OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()&& orderStatus != OrderStatusEnum.REFUSED.getOrderStatus()&&orderStatus != OrderStatusEnum.WAITING_EXT.getOrderStatus()){
			LogUtil.info(LOGGER, "房东对订单更新，更新后的状态只能是{},{},{}", OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus(), OrderStatusEnum.REFUSED.getOrderStatus(), OrderStatusEnum.WAITING_EXT.getOrderStatus());
			return false;
		}
		if(Check.NuNStr(orderSn)){
			LOGGER.error("订单号不存在");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 拒绝订单校验参数
	 * @author lishaochuan
	 * @create 2016年7月28日下午4:31:25
	 * @param dto
	 * @param loadlordRequest
	 */
	private void checkResuseRequest(DataTransferObject dto, LoadlordRequest loadlordRequest) {
		if (Check.NuNObj(loadlordRequest)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数为null");
			LOGGER.error("请求参数为null");
			return;
		}
		if (Check.NuNStr(loadlordRequest.getOrderSn())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("订单号不存在");
			LOGGER.error("订单号不存在");
			return;
		}
		int orderStatus = loadlordRequest.getOrderStatus();
		if (orderStatus != OrderStatusEnum.REFUSED.getOrderStatus() && orderStatus != OrderStatusEnum.REFUSED_OVERTIME.getOrderStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房东拒绝订单，更新后的状态只能是[" + OrderStatusEnum.REFUSED.getOrderStatus() + "],[" + OrderStatusEnum.REFUSED_OVERTIME.getOrderStatus() + "]");
			LogUtil.info(LOGGER, "房东拒绝订单，更新后的状态只能是{},{}", OrderStatusEnum.REFUSED.getOrderStatus(), OrderStatusEnum.REFUSED_OVERTIME.getOrderStatus());
			return;
		}
	}


	/**
	 * 
	 * 房东端订单列表查询
	 * 1=待处理 2=进行中 3=已完成
	 *
	 * @author jixd
	 * @created 2016年5月4日 下午8:46:08
	 *
	 * @param orderRequest
	 * @return
	 */
	@Override
	public String queryOrderList(String orderRequest) {
		DataTransferObject dto = new DataTransferObject();
		OrderRequest request = JsonEntityTransform.json2Object(orderRequest, OrderRequest.class);
		if(Check.NuNStr(request.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("没有权限");
			return dto.toJsonString();
		}
		if(request.getLanOrderType() != 1 && request.getLanOrderType() != 2 && request.getLanOrderType() != 3){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("没有查询类型");
			return dto.toJsonString();
		}
		try{
			PagingResult<OrderInfoVo> loadlordOrderList = orderLoadlordServiceImpl.queryLoadlordOrderList(request);
			dto.putValue("loadlordOrderList", loadlordOrderList.getRows());
			dto.putValue("total", loadlordOrderList.getTotal());
		}catch(Exception e){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务异常");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 获取 一个房源 一个月 收益 所有的订单列表
	 * @author liyingjie
	 * @created 2016年6月25日 
	 * @param profitRequest
	 * @return
	 */
	@Override
	public String queryProfitOrderList(String profitRequest) {
		DataTransferObject dto = new DataTransferObject();
		OrderProfitRequest request = JsonEntityTransform.json2Object(profitRequest, OrderProfitRequest.class);
		if(Check.NuNObj(request)){
			LogUtil.info(LOGGER, "queryProfitOrderList 参数为空");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("queryProfitOrderList 参数为空");
			return dto.toJsonString();
		}
		
		if(Check.NuNStr(request.getHouseFid())){
			LogUtil.info(LOGGER, "queryProfitOrderList houseFid参数为空");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("queryProfitOrderList houseFid参数为空");
			return dto.toJsonString();
		}
		if(request.getMonth() == 0){
			LogUtil.info(LOGGER, "queryProfitOrderList month月份为0");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("queryProfitOrderList month月份为0");
			return dto.toJsonString();
		}
		
		if(request.getType() != 1 && request.getType() != 2){
			LogUtil.info(LOGGER, "queryProfitOrderList 请求类型type错误");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("queryProfitOrderList 请求类型type错误");
			return dto.toJsonString();
		}
		
		if(request.getRentWay() != RentWayEnum.HOUSE.getCode() && request.getRentWay() != RentWayEnum.ROOM.getCode()){
			LogUtil.info(LOGGER, "queryProfitOrderList 出租方式rentWay错误");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("queryProfitOrderList 出租方式rentWay错误");
			return dto.toJsonString();
		}
		
		if(request.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(request.getRoomFid())){
			LogUtil.info(LOGGER, "queryProfitOrderList 合租房子,roomFid:{}",request.getRoomFid());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("queryProfitOrderList 合租房子,roomFid 为空");
			return dto.toJsonString();
		}
		
		try{
			PagingResult<OrderInfoVo> loadlordOrderList = orderLoadlordServiceImpl.queryMonthProfitOrderList(request);
            if (Check.NuNObj(loadlordOrderList)){
                dto.putValue("orderList", new ArrayList());
                dto.putValue("total", 0);
            }else {
                dto.putValue("orderList", loadlordOrderList.getRows());
                dto.putValue("total", loadlordOrderList.getTotal());
            }
		}catch(Exception e){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务异常");
			return dto.toJsonString();
		}
		
		return dto.toJsonString();
	}


	/**
	 * 保存评价成功则记录用户行为
	 *
	 * @param
	 * @return
	 * @author zhangyl2
	 * @created 2017年10月11日 20:20
	 */
	private void saveRefuseOrderBehavior(LoadlordRequest loadlordRequest) {
		threadPoolExecutor.execute(() -> {
			LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.REFUSE_APPLY;

			String logPreStr = "[记录用户行为]" + behaviorEnum.getDesc() + "-";

			try {
				LogUtil.info(LOGGER, logPreStr + "loadlordRequest={}", loadlordRequest);

				CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
				customerBehaviorEntity.setProveFid(loadlordRequest.getOrderSn());
				customerBehaviorEntity.setAttribute(behaviorEnum.getAttribute());
				customerBehaviorEntity.setRole(CustomerBehaviorRoleEnum.LANDLORD.getCode());
				customerBehaviorEntity.setUid(loadlordRequest.getLandlordUid());
				customerBehaviorEntity.setType(behaviorEnum.getType());
				customerBehaviorEntity.setScore(behaviorEnum.getScore());
				customerBehaviorEntity.setCreateFid(loadlordRequest.getLandlordUid());
				customerBehaviorEntity.setCreateType(behaviorEnum.getCreateType());

				customerBehaviorEntity.setRemark(String.format("行为说明={%s}, 订单编号={%s}, 房东uid={%s}",
						behaviorEnum.getDesc(),
						loadlordRequest.getOrderSn(),
						loadlordRequest.getLandlordUid()));

				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, JsonEntityTransform.Object2Json(customerBehaviorEntity));
			} catch (Exception e) {
				LogUtil.error(LOGGER, logPreStr + "推送mq异常，loadlordRequest={}, e={}", loadlordRequest, e);
			}
		});
	}
}
