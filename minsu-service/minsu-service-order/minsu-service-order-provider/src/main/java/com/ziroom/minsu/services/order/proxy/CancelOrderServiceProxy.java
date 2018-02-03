package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.order.api.inner.CancelOrderService;
import com.ziroom.minsu.services.order.dto.CancelOrderServiceRequest;
import com.ziroom.minsu.services.order.dto.SendOrderEmailRequest;
import com.ziroom.minsu.services.order.dto.ShowCancelOrderResponse;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderDayPriceVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.*;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerBehaviorRoleEnum;
import com.ziroom.minsu.valenum.customer.LandlordBehaviorEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.*;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0025Enum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.asura.framework.base.entity.DataTransferObject.LOGGER;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/1/4 11:03
 * @version 1.0
 * @since 1.0
 */
@Component("order.cancelOrderServiceProxy")
public class CancelOrderServiceProxy implements CancelOrderService {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CancelOrderServiceProxy.class);

    @Resource(name = "order.messageSource")
    private MessageSource messageSource;

    @Resource(name = "order.orderCommonServiceImpl")
    private OrderCommonServiceImpl orderCommonService;

    @Resource(name = "order.orderConfigServiceImpl")
    private OrderConfigServiceImpl orderConfigService;

    @Resource(name = "order.orderUserServiceImpl")
    private OrderUserServiceImpl orderUserService;

    @Resource(name = "order.payVouchersCreateServiceImpl")
    private PayVouchersCreateServiceImpl payVouchersCreateService;

    @Resource(name = "order.orderSmartLockServiceProxy")
    private OrderSmartLockServiceProxy orderSmartLockServiceProxy;

    @Resource(name = "order.orderMsgProxy")
    private OrderMsgProxy orderMsgProxy;

    @Resource(name = "order.orderCommonServiceImpl")
    private OrderCommonServiceImpl orderCommonServiceImpl;

    @Resource(name = "order.financePayServiceImpl")
    private FinancePayServiceImpl financePayServiceImpl;

    @Resource(name = "order.payVouchersRunServiceImpl")
    private PayVouchersRunServiceImpl payVouchersRunService;

    @Resource(name = "order.financeIncomeServiceImpl")
    private FinanceIncomeServiceImpl financeIncomeService;

    @Resource(name = "order.orderTaskServiceImpl")
    private OrderTaskServiceImpl orderTaskServiceImpl;

	@Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

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
     * 协商取消订单
     *
     * @param params
     * @return
     * @author lishaochuan
     * @create 2017/1/4 11:02
     */
    @Override
    public String cancelOrderNegotiate(String params) {
        Date now = new Date();
        DataTransferObject dto = new DataTransferObject();
        try {
            CancelOrderServiceRequest request = JsonEntityTransform.json2Object(params, CancelOrderServiceRequest.class);
            if (Check.NuNStr(request.getOrderSn()) || Check.NuNObj(request.getCancelType())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }

            OrderInfoVo orderInfo = orderCommonService.getOrderInfoByOrderSn(request.getOrderSn());
            if (orderInfo.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("订单尚未支付，不能走协商取消");
                return dto.toJsonString();
            }
            if (orderInfo.getPayMoney() < orderInfo.getNeedPay()) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("支付金额小于应付金额");
                return dto.toJsonString();
            }
            if(now.after(DateSplitUtil.getFirstSecondOfDay(orderInfo.getEndTime()))){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("当前为最后一天，不用取消，请走正常退房");
                return dto.toJsonString();
            }
            Calendar dividing = Calendar.getInstance();
            dividing.set(Calendar.HOUR_OF_DAY, 23);
            dividing.set(Calendar.MINUTE, 50);
            dividing.set(Calendar.SECOND, 0);
            if (request.getIsReturnTonightRental() == YesOrNoEnum.YES.getCode()) {
                if (now.after(dividing.getTime())) {
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("当前时间超过23点50分，不可退当天房租");
                    return dto.toJsonString();
                }
            }
            if(request.getIsReturnCleanMoney() == YesOrNoEnum.YES.getCode()){
                if(DateSplitUtil.transDateTime2Date(now).after(DateSplitUtil.transDateTime2Date(orderInfo.getStartTime()))){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("第一天已经入住完毕，不可退清洁费");
                    return dto.toJsonString();
                }
                if(DateSplitUtil.transDateTime2Date(now).equals(DateSplitUtil.transDateTime2Date(orderInfo.getStartTime()))
                        && now.after(dividing.getTime())){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("第一天入住时间已过23点50分，不可退清洁费");
                    return dto.toJsonString();
                }
            }

            //获取订单配置
            OrderConfigVo config = orderConfigService.getOrderConfigVo(request.getOrderSn());
            if (Check.NuNObj(config)) {
                LogUtil.info(LOGGER, "【异常数据】协商取消订单 orderSn:", orderInfo.getOrderSn());
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
                return dto.toJsonString();
            }

            boolean before = now.before(orderInfo.getStartTime());
            if(Check.NuNStr(orderInfo.getLandlordUid())){
            	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("proxy层，OderInfo获取房东uid为空");
                return dto.toJsonString();
            }
            request.setLandlordUid(orderInfo.getLandlordUid());
            
            if(request.getCancelType() == OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus()){
    			//获取取消订单罚款固定金额
    			DataTransferObject oneHoudredDto  = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, ProductRulesEnum0025Enum.ProductRulesEnum0025002.getValue()));
    			if(oneHoudredDto.getCode()==DataTransferObject.ERROR){
    				dto.setErrCode(DataTransferObject.ERROR);
    				dto.setMsg(oneHoudredDto.getMsg());
    				return dto.toJsonString();
    			}
    			String oneHoudred = oneHoudredDto.parseData("textValue", new TypeReference<String>() {});
    			request.setOneHondred(Integer.valueOf(oneHoudred));
            }
        
            if (before) {
                // 未入住取消
                if (orderInfo.getOrderStatus() != OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()) {
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("订单状态错误，不为待入住");
                    return dto.toJsonString();
                }

                // 协商取消订单
                
                orderUserService.cancelOrderNegotiateNotIn(request, config, orderInfo, now, dto);

            } else {
                // 入住后取消
                Date endTime = orderInfo.getEndTime();
                if(now.after(DateSplitUtil.transDateTime2Date(endTime))){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("当前为最后一天，不用取消，请走正常退房");
                    return dto.toJsonString();
                }

                if(orderInfo.getOrderStatus() == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()){
                    LogUtil.info(LOGGER, "【协商退房】当前订单到达入住时间，还未入住，调用入住接口");
                    orderTaskServiceImpl.taskUpdateOrderToCheckInStatus(orderInfo.getOrderSn());
                    orderInfo.setOrderStatus(OrderStatusEnum.CHECKED_IN.getOrderStatus());
                }
                if (orderInfo.getOrderStatus() == OrderStatusEnum.CHECKED_IN.getOrderStatus()) {
                    LogUtil.info(LOGGER, "【协商退房】当前订单还未生成付款计划  开始生成付款计划");
                    List<OrderDayPriceVo> dayPrice = orderUserService.getDayPrices(orderInfo);
                    payVouchersCreateService.createFinance(orderInfo, config, dayPrice);
                    orderInfo.setOrderStatus(OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus());
                }

                if (orderInfo.getOrderStatus() != OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()) {
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("订单状态错误，不为已入住");
                    return dto.toJsonString();
                }

                // 协商退房
                orderUserService.cancelOrderNegotiateCheckIn(request, config, orderInfo, now, dto);
            }


            // 取消完成之后发送短信和邮件
            if (dto.getCode() == DataTransferObject.SUCCESS) {

				if (request.getCancelType() == CancelTypeEnum.NEGOTIATION.getCode()){
					orderMsgProxy.sendMsg4NegotiateCancelLandlord(orderInfo, request.getPenaltyMoney());
					orderMsgProxy.sendMsg4NegotiateCancelUser(orderInfo, request.getPenaltyMoney());
				}else if (request.getCancelType() == CancelTypeEnum.LANDLOR_APPLY.getCode()){
					orderMsgProxy.sendMsg4LandApplyCancelToLan(orderInfo);
					orderMsgProxy.sendMsg4LandApplyCancelToTen(orderInfo);
				}

				//组装邮件发送参数
    			SendOrderEmailRequest orderEmail=new SendOrderEmailRequest();
    			orderEmail.setOrderStartDate(orderInfo.getStartTime());
    			orderEmail.setOrderEndDate(orderInfo.getEndTime());
    			orderEmail.setOrderStatus("订单已协商取消");
    			orderEmail.setLandlordUid(orderInfo.getLandlordUid());
    			orderEmail.setBookName(orderInfo.getUserName());
    			orderEmail.setCheckInNum(orderInfo.getPeopleNum());
                if(orderInfo.getRentWay()==RentWayEnum.HOUSE.getCode()){
                	orderEmail.setHouseName(orderInfo.getHouseName());
                } else if(orderInfo.getRentWay()==RentWayEnum.ROOM.getCode()) {
                	orderEmail.setHouseName(orderInfo.getRoomName());
    			}
    			orderEmail.setEmailTitle(orderEmail.getHouseName()+"的"+DateUtil.dateFormat(orderInfo.getStartTime(),"yyyy-MM-dd")+"至"+DateUtil.dateFormat(orderInfo.getEndTime(),"yyyy-MM-dd")+"的订单已协商取消");
    			dto.putValue("orderEmail", orderEmail);

    			// 取消订单行为记录（成长体系）
				saveCancleOrderBehavior(request);
			}

            //退房调用智能锁
            if (dto.getCode() == DataTransferObject.SUCCESS) {
                LogUtil.info(LOGGER, "【退房】退房调用智能锁失效：orderSn:{}", orderInfo.getOrderSn());
                String json = orderSmartLockServiceProxy.closeSmartlockPswd(orderInfo.getOrderSn());
                LogUtil.info(LOGGER, "【退房】调用智能锁返回结果：json:{}", json);
            }

        }catch (BusinessException e){
            LogUtil.error(logger, "e:{}", e);
            return dto.toJsonString();
        }catch (Exception e) {
            LogUtil.error(logger, "e:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }


    /**
     * 查看取消详情
     *
     * @param orderSn
     * @return
     * @author lishaochuan
     * @create 2017/1/10 14:18
     */
    @Override
    public String showCancelOrderInfo(String orderSn, Integer orderStatus) {
        DataTransferObject dto = new DataTransferObject();
        ShowCancelOrderResponse response = new ShowCancelOrderResponse();
        try {
            // 查询订单信息
            OrderInfoVo orderInfo = orderCommonServiceImpl.getOrderInfoByOrderSn(orderSn);
            response.setOrderInfo(orderInfo);

            // 查询取消原因
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("orderSn", orderSn);
            paramMap.put("parCode", OrderParamEnum.NEGOTIATE_CANCEL.getCode());
            List<OrderParamEntity> orderParams = orderCommonServiceImpl.findParamByCondiction(paramMap);
            if(orderParams.size() >= 1){
                response.setCancelReason(orderParams.get(0).getParValue());
            }

            // 查询付款单
            Map<String, String> lanPay = new HashMap<>();
            Map<String, String> userPay = new HashMap<>();
            List<FinancePayVouchersEntity> settlementPVs = financePayServiceImpl.findSettlementByOrderSn(orderSn);
            for (FinancePayVouchersEntity settlementPV : settlementPVs) {
                List<FinancePayVouchersDetailEntity> pvDetail = payVouchersRunService.findPayVouchersDetailByPvSn(settlementPV.getPvSn());
                for (FinancePayVouchersDetailEntity financePayVouchersDetailEntity : pvDetail) {
                    String name = FeeItemCodeEnum.getByCode(financePayVouchersDetailEntity.getFeeItemCode()).getName();
                    String price = DataFormat.formatHundredPriceNoZero(financePayVouchersDetailEntity.getItemMoney());
                    if(settlementPV.getReceiveType() == ReceiveTypeEnum.LANDLORD.getCode()){
                        lanPay.put(name, price);
                    }else if(settlementPV.getReceiveType() == ReceiveTypeEnum.TENANT.getCode()){
                        userPay.put(name, price);
                    }
                }
            }
            response.setLanPay(lanPay);
            response.setUserPay(userPay);

            // 查询佣金
            Map<String, String> income = new HashMap<>();
            int incomeAll = 0;
            List<FinanceIncomeEntity> settlementIncomes = financeIncomeService.getSettlementIncomeListByOrderSn(orderSn);
            for (FinanceIncomeEntity settlementIncome : settlementIncomes) {
                incomeAll += settlementIncome.getTotalFee();
            }
            income.put("收入", DataFormat.formatHundredPriceNoZero(incomeAll));
            response.setIncome(income);

            // 查询操作日志
            List<OrderLogEntity> orderLogs = orderCommonServiceImpl.getOrderLogListByOrderSn(orderSn);
            response.setOrderLogs(orderLogs);
            
            CancelOrderServiceRequest cancelOrderServiceRequest = new CancelOrderServiceRequest();//作为回显当时取消订单都有哪些惩罚措施
        	cancelOrderServiceRequest.setCancelTypeName(OrderStatusEnum.getOrderStatusByCode(orderStatus).getStatusName());
//如果是房东取消订单——查询惩罚措施
            if(orderStatus.equals(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus()) || orderStatus.equals(OrderStatusEnum.FINISH_LAN_APPLY.getOrderStatus())){
                //订单编号，查询无线惩罚措施
                List<OrderConfigEntity> orderConfigList =  orderUserService.getOrderConfigListByOrderSn(orderSn);
                if(!Check.NuNObj(orderConfigList)){
                	for (OrderConfigEntity orderConfigEntity : orderConfigList) {
                		if(ProductRulesEnum0025Enum.ProductRulesEnum0025001001.getValue().equals(orderConfigEntity.getConfigCode())){//有天使房东惩罚
                			cancelOrderServiceRequest.setTakeFirstNightMoneyName(ProductRulesEnum0025Enum.ProductRulesEnum0025001001.getName());
							cancelOrderServiceRequest.setIsTakeFirstNightMoney(YesOrNoEnum.YES.getCode());
							cancelOrderServiceRequest.setIsTakeFirstNightMoneyDone(orderConfigEntity.getConfigValue());
						}
                		if(ProductRulesEnum0025Enum.ProductRulesEnum0025002.getValue().equals(orderConfigEntity.getConfigCode())){//有天使房东惩罚
							cancelOrderServiceRequest.setIsTakeOneHundred(YesOrNoEnum.YES.getCode());
							cancelOrderServiceRequest.setTakeOneHundredName(ProductRulesEnum0025Enum.ProductRulesEnum0025002.getName());
							cancelOrderServiceRequest.setIsTakeOneHundredDone(orderConfigEntity.getConfigValue());
						}
						if(ProductRulesEnum0025Enum.ProductRulesEnum0025001003.getValue().equals(orderConfigEntity.getConfigCode())){//有天使房东惩罚
							cancelOrderServiceRequest.setIsCancelAngel(YesOrNoEnum.YES.getCode());
							cancelOrderServiceRequest.setCancelAngelName(ProductRulesEnum0025Enum.ProductRulesEnum0025001003.getName());
							cancelOrderServiceRequest.setIsCancelAngelDone(orderConfigEntity.getConfigValue());
						}
                        if(ProductRulesEnum0025Enum.ProductRulesEnum0025001004.getValue().equals(orderConfigEntity.getConfigCode())){//有天使房东惩罚
                    	   cancelOrderServiceRequest.setIsAddSystemEval(YesOrNoEnum.YES.getCode());
                    	   cancelOrderServiceRequest.setAddSystemEvalName(ProductRulesEnum0025Enum.ProductRulesEnum0025001004.getName());
                    	   cancelOrderServiceRequest.setIsAddSystemEvalDone(orderConfigEntity.getConfigValue());
						}
                        if(ProductRulesEnum0025Enum.ProductRulesEnum0025001005.getValue().equals(orderConfigEntity.getConfigCode())){//有天使房东惩罚
                    	   cancelOrderServiceRequest.setIsUpdateRankFactor(YesOrNoEnum.YES.getCode());
                    	   cancelOrderServiceRequest.setUpdateRankFactorName(ProductRulesEnum0025Enum.ProductRulesEnum0025001005.getName());
                    	   cancelOrderServiceRequest.setIsUpdateRankFactorDone(orderConfigEntity.getConfigValue());
                        }
						if(ProductRulesEnum0025Enum.ProductRulesEnum0025001006.getValue().equals(orderConfigEntity.getConfigCode())){//有天使房东惩罚
							cancelOrderServiceRequest.setIsShieldCalendar(YesOrNoEnum.YES.getCode());
							cancelOrderServiceRequest.setShieldCalendarName(ProductRulesEnum0025Enum.ProductRulesEnum0025001006.getName());
							cancelOrderServiceRequest.setIsShieldCalendarDone(orderConfigEntity.getConfigValue());
						}
						if(ProductRulesEnum0025Enum.ProductRulesEnum0025001007.getValue().equals(orderConfigEntity.getConfigCode())){//有天使房东惩罚
							cancelOrderServiceRequest.setIsGiveCoupon(YesOrNoEnum.YES.getCode());
							cancelOrderServiceRequest.setGiveCouponName(ProductRulesEnum0025Enum.ProductRulesEnum0025001007.getName());
							cancelOrderServiceRequest.setIsGiveCouponDone(orderConfigEntity.getConfigValue());
						}
                	  }
					}
                
            }
            response.setCancelOrderServiceRequest(cancelOrderServiceRequest);
            dto.putValue("response", response);
        }catch (Exception e) {
            LogUtil.error(logger, "e:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }


	@Override
	public String updateOrderConfValue(String orderSn, String confCode, String confValue) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn) || Check.NuNStr(confCode) || Check.NuNStr(confValue)){
			  dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			  dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			  return dto.toJsonString();
		}
		int updateOrderConfValue = orderConfigService.updateOrderConfValue(orderSn, confCode, confValue);
		if(updateOrderConfValue==0){
			  dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			  dto.setMsg("更新OrderConfValue的数量为0");
			  return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	@Override
	public String updateOrderCsrCancle(String orderSn, Integer punishStatu) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn) || Check.NuNObj(punishStatu)){
			  dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			  dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			  return dto.toJsonString();
		}
		int updateOrderConfValue = orderUserService.updateOrderCsrCancle(orderSn, punishStatu);
		if(updateOrderConfValue==0){
			  dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			  dto.setMsg("更新OrderCsrCancle表punish_statupunish_statu字段的数量为0");
			  return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	@Override
	public String getCountInTimes(String landlordUid, Date beginDate,Date endDate) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房东uid不能为空");
			return  dto.toJsonString();
		}
		long countInTimes = orderUserService.getCountInTimes(landlordUid, beginDate, endDate);
		dto.putValue("countInTimes", countInTimes);
		return dto.toJsonString();
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
	
	/**
	 * 
	 * 根据orderSn获取orderConfigList
	 *
	 * @author loushuai
	 * @created 2017年5月10日 下午8:26:26
	 *
	 * @param date
	 * @param i 为负数时代表获取之前的日期     为正数,代表获取之后的日期
	 * @return
	 */
	public String getOrderConfigListByOrderSn(String orderSn){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		List<OrderConfigEntity> orderConfigList = orderUserService.getOrderConfigListByOrderSn(orderSn);
		if(Check.NuNCollection(orderConfigList)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("根据orderSn获取的orderConfigList为空");
			return dto.toJsonString();
		}
		dto.putValue("orderConfigList", orderConfigList);
		return dto.toJsonString();
	}


	@Override
	public String getFinancePenaltyByOrderSn(String orderSn) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		
		FinancePenaltyEntity financePenaltyEntity = orderUserService.getFinancePenaltyByOrderSn(orderSn);
		if(Check.NuNObj(financePenaltyEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("根据orderSn获取的financePenaltyEntity为空");
			return dto.toJsonString();
		}
		dto.putValue("financePenaltyEntity", financePenaltyEntity);
		return dto.toJsonString();
	}


	@Override
	public String getIsDoneAllPunish(String orderSn) {
		DataTransferObject dto = new DataTransferObject();
		List<OrderConfigEntity> orderConfigList = orderUserService.getOrderConfigListByOrderSn(orderSn);
		if(Check.NuNCollection(orderConfigList)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("根据orderSn获取的orderConfigList为空");
			return dto.toJsonString();
		}
		int result = YesOrNoEnum.YES.getCode();
		for (OrderConfigEntity orderConfigEntity : orderConfigList) {
			if(ProductRulesEnum0025Enum.ProductRulesEnum0025001001.getValue().equals(orderConfigEntity.getConfigCode())
            		&& YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())){
				result = 0;
				break;
			}
			if(ProductRulesEnum0025Enum.ProductRulesEnum0025002.getValue().equals(orderConfigEntity.getConfigCode())
            		&& YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())){
				result = 0;
				break;
			}
			if(ProductRulesEnum0025Enum.ProductRulesEnum0025001003.getValue().equals(orderConfigEntity.getConfigCode())
            		&& YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())){
				result = 0;
				break;
			}
			if(ProductRulesEnum0025Enum.ProductRulesEnum0025001004.getValue().equals(orderConfigEntity.getConfigCode())
            		&& YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())){
				result = 0;
				break;
			}
			if(ProductRulesEnum0025Enum.ProductRulesEnum0025001005.getValue().equals(orderConfigEntity.getConfigCode())
            		&& YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())){
				result = 0;
				break;
			}
			if(ProductRulesEnum0025Enum.ProductRulesEnum0025001006.getValue().equals(orderConfigEntity.getConfigCode())
            		&& YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())){
				result = 0;
				break;
			}
			if(ProductRulesEnum0025Enum.ProductRulesEnum0025001007.getValue().equals(orderConfigEntity.getConfigCode())
            		&& YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())){
				result = 0;
				break;
			}
		}
		dto.putValue("isDoneAllPunish", result);
		
		return dto.toJsonString();
	} 
	
	@Override
    public String getDoFailLandQXOrderPunish(){
    	DataTransferObject dto = new DataTransferObject();
    	List<OrderCsrCancleEntity> orderCsrList = orderUserService.getDoFailLandQXOrderPunish();
    	if(Check.NuNCollection(orderCsrList)){
    		 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
             dto.setMsg("获取到所有未处理完成的  OrderCsrCancleEntity对象");
    	}
    	dto.putValue("orderCsrList", orderCsrList);
    	return dto.toJsonString();
    }

	@Override
	public String updateOrderSysLock(String orderParam) {
		DataTransferObject dto = new DataTransferObject();
		OrderInfoVo orderInfoVor = JsonEntityTransform.json2Object(orderParam, OrderInfoVo.class);
		if (Check.NuNObj(orderInfoVor)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		int count = orderUserService.updateOrderSysLock(orderInfoVor);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 房东申请取消订单,记录行为（成长体系）
	 * 
	 * @author zhangyl2
	 * @created 2017年10月11日 21:29
	 * @param 
	 * @return 
	 */
	private void saveCancleOrderBehavior(CancelOrderServiceRequest request){
		//房东申请取消,记录行为
		if (request.getCancelType() == OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus()) {
			threadPoolExecutor.execute(() -> {
				LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.APPLY_CANCEL_ORDER;

				String logPreStr = "[记录用户行为]" + behaviorEnum.getDesc() + "-";

				try {
					LogUtil.info(LOGGER, logPreStr + "request={}", request);

					CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
					customerBehaviorEntity.setProveFid(request.getOrderSn());
					customerBehaviorEntity.setAttribute(behaviorEnum.getAttribute());
					customerBehaviorEntity.setRole(CustomerBehaviorRoleEnum.LANDLORD.getCode());
					customerBehaviorEntity.setUid(request.getLandlordUid());
					customerBehaviorEntity.setType(behaviorEnum.getType());
					customerBehaviorEntity.setScore(behaviorEnum.getScore());
					customerBehaviorEntity.setCreateFid(request.getLandlordUid());
					customerBehaviorEntity.setCreateType(behaviorEnum.getCreateType());

					customerBehaviorEntity.setRemark(String.format("行为说明={%s}, 订单编号={%s}, 房东uid={%s}",
							behaviorEnum.getDesc(),
							request.getOrderSn(),
							request.getLandlordUid()));

					LogUtil.info(LOGGER, logPreStr + "customerBehaviorEntity={}", customerBehaviorEntity);
					// 推送队列消息
					rabbitMqSendClient.sendQueue(queueName, JsonEntityTransform.Object2Json(customerBehaviorEntity));
				} catch (Exception e) {
					LogUtil.error(LOGGER, logPreStr + "推送mq异常，request={}, e={}", request, e);
				}
			});
		}
	}
}
