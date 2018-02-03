package com.ziroom.minsu.services.order.proxy;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderFlagEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.WeatherDayInfoVo;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.CatConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.sms.SendService;
import com.ziroom.minsu.services.common.thread.SendThread;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import com.ziroom.minsu.services.order.dto.CheckOutOrderRequest;
import com.ziroom.minsu.services.order.dto.ConfirmOtherMoneyRequest;
import com.ziroom.minsu.services.order.dto.LoadlordRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.dto.OrderTaskRequest;
import com.ziroom.minsu.services.order.dto.SendOrderEmailRequest;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.OrderActivityServiceImpl;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.OrderFlagServiceImpl;
import com.ziroom.minsu.services.order.service.OrderTaskServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.OrderFlagEnum;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;

/**
 * <p>订单定时任务</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/4/06.
 * @version 1.0
 * @since 1.0
 */
@Component("order.orderTaskOrderProxy")
public class OrderTaskServiceProxy implements OrderTaskOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskServiceProxy.class);

    @Resource(name = "order.messageSource")
    private MessageSource messageSource;

    @Resource(name = "order.orderTaskServiceImpl")
    private OrderTaskServiceImpl orderRegularTaskService;
    
    @Resource(name = "order.orderFlagServiceImpl")
    private OrderFlagServiceImpl orderFlagServiceImpl;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;
    
    @Resource(name="order.orderUserServiceProxy")
    private OrderUserServiceProxy orderUserServiceProxy;
    
    @Resource(name="order.orderLandlordServiceProxy")
    private OrderLandlordServiceProxy orderLandlordServiceProxy;
    
    @Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;

	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl ;


	@Resource(name = "order.smsSendService")
	private SendService<String,String,Map<String,String>,Void> smsSendService;
    
    /*@Resource(name="basedata.weatherDayService")
    private WeatherDayService weatherDayService;*/

    @Resource(name = "order.orderActivityServiceImpl")
    private OrderActivityServiceImpl orderActivityServiceImpl;
    
    
    /**
   	 *  查询超过30min未支付的订单,释放房源,取消订单
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月5日 
   	 *
   	 * @param  
     * @return
   	 */
    public void cancelOrderAndUnlockHouse(){
    	LogUtil.info(LOGGER, "【取消订单Job】查询超过30min未支付的订单,释放房源,取消订单，开始");
    	
        /** 封装 查询参数  订单状态  支付状态  30分钟前的时间*/
        String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(TradeRulesEnum.TradeRulesEnum002.getValue())); //支付时限(分钟)
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
        	LogUtil.error(LOGGER, "【取消订单Job】获取支付时限失败,timeStrJson:{}", timeStrJson);
        	throw new BusinessException("【取消订单Job】获取支付时限失败");
		}
        int timeLimit = ValueUtil.getintValue(resultDto.getData().get("textValue"));
        if (timeLimit <= 0) {
        	timeLimit = 30;
		}
        int limit = 20;
        Date limitDate = DateUtil.intervalDate(-timeLimit, IntervalUnit.MINUTE);
    	OrderTaskRequest taskRequest = new OrderTaskRequest();
    	taskRequest.setOrderStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
    	taskRequest.setPayStatus(OrderPayStatusEnum.UN_PAY.getPayStatus());
    	taskRequest.setLimitDate(limitDate);
        Long count = orderRegularTaskService.taskGetOverTimeNum(taskRequest);
        LogUtil.info(LOGGER, "【取消订单Job】条数count:{}", count);
        if(Check.NuNObj(count) || count == 0){
            return;
        }
        taskRequest.setLimit(limit);
        int pageAll = ValueUtil.getPage(count.intValue(),limit);
        for(int page=1 ; page <= pageAll ; page++){
        	List<String> orderList = orderRegularTaskService.taskGetToCanceOrderSnList(taskRequest);
			try{
		    	this.taskBatchUpdateToCancelOrder(orderList);
		    	LogUtil.info(LOGGER,"【取消订单Job】订单超时取消，并释放房源 |orderList:{}",JsonEntityTransform.Object2Json(orderList));
		    }catch (Exception e){
		    	if (orderList != null) {
		    		count = count - orderList.size();
		    	}
                LogUtil.error(LOGGER,"e:{}",e);
            }
		}
        
        Transaction orderPayTran = Cat.newTransaction("OrderTaskServiceProxy", CatConstant.CANCEL_TIME);
    	try {
    		//对超时取消的订单数量的埋点
    		Cat.logMetricForSum("取消订单总数量", count);
            orderPayTran.setStatus(Message.SUCCESS);
    	} catch(Exception ex) {
    		Cat.logError("取消订单总数量 打点异常", ex);
    		orderPayTran.setStatus(ex);
    	} finally {
    		orderPayTran.complete();
		}
       
    }


    /**
     * 超过30min 定时任务批量取消订单
     * @author liyingjie
     * @param orderList
     * @return
     */
    private void taskBatchUpdateToCancelOrder(List<String> orderList){
        if(Check.NuNCollection(orderList)){
            LogUtil.info(LOGGER, "【取消订单Job】取消订单列表的list为空");
            return;
        }
        for(String orderSn : orderList){
            this.updateOrderAndUnLockHouse(orderSn);
        }
    }

    /**
     * 修改订单状态
     * @author afi
     * @param orderSn
     */
    private void updateOrderAndUnLockHouse(String orderSn){
        try {
            LogUtil.info(LOGGER, "【取消订单Job】开始取消订单orderSn:{}", orderSn);
            orderRegularTaskService.updateOrderAndUnLockHouse(orderSn);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【取消订单Job】取消订单 失败orderSn:{}error:{}",orderSn,e);
        }

    }

    
	/**
	 * 下单后订单，房东在【申请预定提醒时限】内未接受订单，发短信提醒 
	 * @author lishaochuan
	 * @create 2016年5月14日上午10:58:54
	 */
    @Override
    public String taskWatiConfimOrder(){
    	// “订单审核时限“的值为0时，“申请预定提醒时限”的值不受“订单审核时限“的值限制；
    	// 当“申请预订提醒时限”的值的状态为停用或者值为0时，不触发定时作业；
    	// 当“申请预订提醒时限”的值大于“订单审核时限“时，“申请预订提醒时限”=“订单审核时限“/2
    	DataTransferObject dto=new DataTransferObject();
    	try {
	    	LogUtil.info(LOGGER, "【taskWatiConfimOrder】下单后订单，房东在申请预定提醒时限内未确认，发短信提醒，开始 ");
	    	int limit = 150;
	    	
			// 申请预定提醒时限（分钟）
			String remindStrJson = cityTemplateService.getEffectiveSelectEnum(null, String.valueOf(TradeRulesEnum.TradeRulesEnum0017.getValue())); 
	        DataTransferObject remindDto = JsonEntityTransform.json2DataTransferObject(remindStrJson);
	        if(remindDto.getCode() != DataTransferObject.SUCCESS){
	        	LogUtil.error(LOGGER, "【taskWatiConfimOrder】获取申请预定提醒时限失败,remindStrJson:{}", remindStrJson);
	        	throw new BusinessException("【taskWatiConfimOrder】获取申请预定提醒时限失败");
			}
	        List<EnumVo> remindList = SOAResParseUtil.getListValueFromDataByKey(remindStrJson, "selectEnum", EnumVo.class);
			if (remindList.size() <= 0) {
				LogUtil.info(LOGGER, "【taskWatiConfimOrder】未查询到申请预定提醒时限配置信息remindStrJson:{}", remindStrJson);
				dto.setErrCode(DataTransferObject.ERROR);
				return dto.toJsonString();
			}
			int remindMinute = ValueUtil.getintValue(remindList.get(0).getKey());
	        if(remindMinute == 0){
	        	LogUtil.info(LOGGER, "【taskWatiConfimOrder】获取申请预定提醒时限为0,不触发定时作业,remindMinute:{}", remindMinute);
				dto.setErrCode(DataTransferObject.ERROR);
				return dto.toJsonString();
	        }
	        LogUtil.info(LOGGER, "【taskWatiConfimOrder】申请预定提醒时限（分钟）remindMinute:{}", remindMinute);
	        
	        
			// 订单审核时限（分钟）
	        String acceptStrJson = cityTemplateService.getEffectiveSelectEnum(null, String.valueOf(TradeRulesEnum.TradeRulesEnum0014.getValue()));      
	        DataTransferObject acceptDto = JsonEntityTransform.json2DataTransferObject(acceptStrJson);
	        if(acceptDto.getCode() != DataTransferObject.SUCCESS){
	        	LogUtil.error(LOGGER, "【taskWatiConfimOrder】获取订单审核时限失败,acceptStrJson:{}", acceptStrJson);
	        	throw new BusinessException("【taskWatiConfimOrder】获取订单审核时限失败");
			}
	        List<EnumVo> acceptList = SOAResParseUtil.getListValueFromDataByKey(acceptStrJson, "selectEnum", EnumVo.class);
			if (acceptList.size() <= 0) {
				LogUtil.info(LOGGER, "【taskWatiConfimOrder】未查询到申请预定提醒时限配置信息remindStrJson:{}", remindStrJson);
				dto.setErrCode(DataTransferObject.ERROR);
				return dto.toJsonString();
			}
	        int acceptMinute = ValueUtil.getintValue(acceptList.get(0).getKey());
	        LogUtil.info(LOGGER, "【taskWatiConfimOrder】订单审核时限（分钟）acceptMinute:{}", acceptMinute);
	        
	        
			if (remindMinute >= acceptMinute && acceptMinute != 0) {
				remindMinute = acceptMinute / 2;
				LogUtil.info(LOGGER, "【taskWatiConfimOrder】提醒时限大于审核时限，并且审核时限不为0，重置提醒时限为审核时限的一半,remindMinute:{}", remindMinute);
			}
	        
			
			Date limitDate = DateUtil.intervalDate(-remindMinute, IntervalUnit.MINUTE);
			LogUtil.info(LOGGER, "【taskWatiConfimOrder】申请预定提醒时限（分钟）:{}，计算出来的时间:{}", remindMinute, limitDate);
			
			
			Long count = orderRegularTaskService.getWaitConfimOrderCount(limitDate);
	        LogUtil.info(LOGGER, "【taskWatiConfimOrder】条数count:{}", count);
	        if(Check.NuNObj(count) || count == 0){
				dto.setErrCode(DataTransferObject.ERROR);
				return dto.toJsonString();
	        }
	        int pageAll = ValueUtil.getPage(count.intValue(),limit);
	        //发邮件需要集合
	        List<SendOrderEmailRequest> list=new ArrayList<>();
	        for(int page=1 ; page <= pageAll ; page++){
	        	List<OrderEntity> orderList = orderRegularTaskService.getWaitConfimOrderList(limit, limitDate);
	        	if(Check.NuNCollection(orderList)){
					dto.setErrCode(DataTransferObject.ERROR);
					return dto.toJsonString();
				}
	        	for (OrderEntity order : orderList) {
	        		OrderFlagEntity orderFlag = new OrderFlagEntity();
	        		orderFlag.setOrderSn(order.getOrderSn());
	        		orderFlag.setFlagCode(OrderFlagEnum.REMIND_ACCEPT_ORDER.getCode());
	        		orderFlag.setFlagValue(YesOrNoEnum.YES.getStr());
	        		int num = orderFlagServiceImpl.saveOrderFlag(orderFlag);
	        		if(num != 1){
	        			LogUtil.error(LOGGER, "【taskWatiConfimOrder】插入orderFlag表失败,num:{},orderFlag:{}", num, JsonEntityTransform.Object2Json(orderFlag));
	        			continue;
	        		}
					LogUtil.info(LOGGER, "【taskWatiConfimOrder】发送短信提醒房东确认订单：orderSn:{}, createTime:{}", order.getOrderSn(), DateUtil.timestampFormat(order.getCreateTime()));
					orderMsgProxy.sendMsg4WatiConfimOrder(order);
					//封装邮件发送对象
					SendOrderEmailRequest orderEmail=new SendOrderEmailRequest();
					orderEmail.setOrderStartDate(order.getStartTime());
					orderEmail.setOrderEndDate(order.getEndTime());
					orderEmail.setOrderStatus("订单请您尽快处理");
					orderEmail.setLandlordUid(order.getLandlordUid());
					orderEmail.setBookName(order.getUserName());
					orderEmail.setCheckInNum(order.getPeopleNum());
					OrderHouseSnapshotEntity houseSnapshotEntity=orderCommonServiceImpl.findHouseSnapshotByOrderSn(order.getOrderSn());
					if(houseSnapshotEntity.getRentWay()==RentWayEnum.HOUSE.getCode()){
						orderEmail.setHouseName(houseSnapshotEntity.getHouseName());
					} else if(houseSnapshotEntity.getRentWay()==RentWayEnum.ROOM.getCode()) {
						orderEmail.setHouseName(houseSnapshotEntity.getRoomName());
					}
					list.add(orderEmail);
				}
	        	dto.putValue("list", list);
	        }
	        return dto.toJsonString();
    	} catch (Exception e) {
    		LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
    	}
    }
    
    
    /**
	 * 下单后订单，房东在订单审核时限内未确认，自动拒绝订单
	 * @author lishaochuan
	 * @create 2016年5月12日下午1:40:31
	 */
	@Override
	public void taskRefusedOrder() {
		LogUtil.info(LOGGER, "【拒绝订单Job】下单后订单，房东在订单审核时限内未确认，自动拒绝订单，开始 ");
		int limit = 150;
		String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(TradeRulesEnum.TradeRulesEnum0014.getValue())); //订单审核时限（分钟）         
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
        	LogUtil.error(LOGGER, "【拒绝订单Job】获取订单审核时限失败,timeStrJson:{}", timeStrJson);
        	throw new BusinessException("【拒绝订单Job】获取订单审核时限失败");
		}
        int limitMinute = ValueUtil.getintValue(resultDto.getData().get("textValue"));
        if (limitMinute <= 0) {
        	limitMinute = 30;
		}
        Date limitTime = DateUtil.intervalDate(-Math.abs(limitMinute), IntervalUnit.MINUTE);
        
        Long count = orderRegularTaskService.taskWaitConfirmOrderCount(limitTime);
        LogUtil.info(LOGGER, "【拒绝订单Job】订单审核时限（分钟）:{}，计算出来的时间:{}，查询出来的条数:{}", limitMinute, limitTime, count);
        if(Check.NuNObj(count) || count == 0){
            return;
        }
        int pageAll = ValueUtil.getPage(count.intValue(), limit);
		for (int page = 1; page <= pageAll; page++) {
			try {
				List<OrderEntity> orderList = orderRegularTaskService.taskWaitConfirmOrderList(limitTime, limit);
				if (Check.NuNCollection(orderList)) {
					return;
				}
				for (OrderEntity orderEntity : orderList) {
					LogUtil.info(LOGGER, "orderSn:{}", orderEntity.getOrderSn());
					LoadlordRequest loadlordRequest = new LoadlordRequest();
					loadlordRequest.setOrderSn(orderEntity.getOrderSn());
					loadlordRequest.setOrderStatus(OrderStatusEnum.REFUSED_OVERTIME.getOrderStatus());
					//loadlordRequest.setParamValue("定时任务");
					loadlordRequest.setRefuseReason("定时任务");
					loadlordRequest.setRefuseCode(SysConst.order_task_refuse_code);
					loadlordRequest.setLandlordUid(orderEntity.getLandlordUid());
					LogUtil.info(LOGGER, "【拒绝订单Job】参数,loadlordRequest:{}", JsonEntityTransform.Object2Json(loadlordRequest));
					orderLandlordServiceProxy.refusedOrder(JsonEntityTransform.Object2Json(loadlordRequest));
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "e:{}", e);
			}
		}
	}
    
    


    /**
   	 *  到入住时间，更新订单状态为已入住
   	 * @author liyingjie
   	 * @created 2016年4月5日 
   	 *
   	 * @param  
     * @return
   	 */
    public void updateOrderStatus(){
    	LogUtil.info(LOGGER, "【自动入住Job】到入住时间，更新订单状态为已入住，开始");
    	
        int limit = 150;
        /** 封装 查询参数  订单状态  支付状态  当前时间小于当前时间*/
    	OrderTaskRequest taskRequest = new OrderTaskRequest();
    	taskRequest.setLimit(limit);
        taskRequest.setOrderStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
        taskRequest.setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus());
        taskRequest.setLimitDate(new Date());
        Long  count = orderRegularTaskService.taskGetOverCheckInNum(taskRequest);
        LogUtil.info(LOGGER, "【自动入住Job】查询出来的条数:{}", count);
        if(Check.NuNObj(count) || count == 0){
            return;
        }
        int pageAll = ValueUtil.getPage(count.intValue(), limit);
    	for(int page=1 ; page <= pageAll ; page++){
            List<String> orderList = orderRegularTaskService.taskGetOverCheckInTimeOrderSnList(taskRequest);
            if(Check.NuNCollection(orderList)){
                return;
            }
            for(String orderSn:orderList){
                this.taskUpdateOrderToCheckInStatus(orderSn);
            }
            LogUtil.info(LOGGER, "【自动入住Job】更新为已入住订单列表，orderList:{}", JsonEntityTransform.Object2Json(orderList));
	    }
    }

    /**
     * 订单状态为已入住
     * @author afi
     * @param orderSn
     */
    private void taskUpdateOrderToCheckInStatus(String orderSn){
        try {
            LOGGER.info("【自动入住Job】订单变成已入住 orderSn:{}",orderSn);
            int num = orderRegularTaskService.taskUpdateOrderToCheckInStatus(orderSn);
            if (num ==0){
                LogUtil.error(LOGGER, "【自动入住Job】订单变成已入住 失败 更新的条数为0 orderSN:{} e{}",orderSn);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "【自动入住Job】将订单状态变更为已入住 orderSN:{} e{}",orderSn,e);
        }
    }

    /**
     * 
     * 发送当地天气信息对于已支付待入住订单
     * @author jixd
     * @created 2016年4月13日 
     */
	/*@Override
	public void sendWeatherMsgForTomorrowOrder() {
			 //TODO
	        int limit = 150;
	        *//** 封装 查询参数  订单状态  支付状态  当前时间小于当前时间*//*
	    	OrderTaskRequest taskRequest = new OrderTaskRequest();
	    	taskRequest.setLimit(limit);
	    	try{
				//抓取符合条件订单
				Long count = orderRegularTaskService.taskGetTomorrowOrderNum();
				int pageAll = ValueUtil.getPage(count.intValue(), limit);
				for(int page=1 ; page <= pageAll ; page++){
					taskRequest.setPage(page);
					List<OrderEntity> list = orderRegularTaskService.taskGetTomorrowOrderList(taskRequest);
					if(list != null && list.size() >0){
						//调用短信天气模板
						String msgJson = smsTemplateService.findEntityBySmsCode("99999999");
						DataTransferObject smsDto = JsonEntityTransform.json2DataTransferObject(msgJson);
						SmsTemplateEntity smsEntity = smsDto.parseData("smsTemplateEntity", new TypeReference<SmsTemplateEntity>(){});
						String smsTemplate = smsEntity.getSmsComment();
						//循环调用遍历用户，查询天气，发送短信
						for(OrderEntity order : list){
							//code换取城市名称，调用查询服务
							String cityNameJson = confCityService.getCityNameByCode(order.getCityCode());
							DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityNameJson);
							String cityName = cityDto.parseData("cityName", new TypeReference<String>(){});
							//第二天日期
							String date = DateUtil.getDayAfterCurrentDate();
							//用户名称
							String userName = order.getUserName();
							//用户手机
							String tel = order.getUserTel();
							//查询参数
							WeatherDayRequest request = new WeatherDayRequest();
							request.setCityName(cityName);
							request.setDate(date);
							//调用天气接口，日期和天气
							String weatherJson = weatherDayService.selectByCityAndDate(JsonEntityTransform.Object2Json(request));
							DataTransferObject weatherDto = JsonEntityTransform.json2DataTransferObject(weatherJson);
							//当天天气信息
							WeatherDayInfoVo vo = weatherDto.parseData("weatherdayvo", new TypeReference<WeatherDayInfoVo>(){});
							//发送短信
							MessageUtils.sendSms(new SmsMessage(tel, smsTemplate), bulidParam(userName, vo));
					}
				}
			}
			
		}catch(Exception e){
                LogUtil.error(LOGGER, "e:{}", e);
		}
		
		
	}*/

	/**
	 * 短信模板替换参数
	 * @author jixd on 2016年4月14日
	 */
	private Map<String, String> bulidParam(String userName, WeatherDayInfoVo vo) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		WeatherDayInfoVo.IntervalDay day = vo.getDay();
		WeatherDayInfoVo.IntervalDay night = vo.getNight();
		paramsMap.put("{userName}", userName);
		paramsMap.put("{cityName}", vo.getCityName());
		
		paramsMap.put("{dayLowTem}", day.getLowTemperature());
		paramsMap.put("{dayHighTem}", day.getHighTemperature());
		paramsMap.put("{dayWeather}", day.getWeather());
		paramsMap.put("{dayWind}", day.getWind());
		paramsMap.put("{dayWindStrong}", day.getWindStrong());
		
		paramsMap.put("{nightLowTem}", night.getLowTemperature());
		paramsMap.put("{nightHighTem}", night.getHighTemperature());
		paramsMap.put("{nightWeather}", night.getWeather());
		paramsMap.put("{nightWind}", night.getWind());
		paramsMap.put("{nightWindStrong}", night.getWindStrong());
		return paramsMap;
	}
	
	
	


    /**
     * 自动退房的逻辑
     * @author afi
     * @created 2016年4月5日
     * @param
     * @return
     */
    public void taskCheckOut(){
    	LogUtil.info(LOGGER, "【自动退房Job】当超过退房时间 用户未点击退房 定时任务触发退房操作，开始");
    	
        int limit = 150;
        Date limitDate = new Date();
        OrderTaskRequest taskRequest = new OrderTaskRequest();
        taskRequest.setLimit(limit);
        taskRequest.setOrderStatus(OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus());
        taskRequest.setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus());
        taskRequest.setLimitDate(limitDate);
        //1.订单状态已入住并开票据
        //2.支付状态已经支付的
        Long count = orderRegularTaskService.taskGetOverCheckOutNum(taskRequest);
        LogUtil.info(LOGGER, "【自动退房Job】查询出来的条数:{}", count);
        if(Check.NuNObj(count) || count == 0){
            return;
        }
        int pageAll = ValueUtil.getPage(count.intValue(),limit);
        for(int page=1 ; page <= pageAll ; page++){
            try{
                List<String> orderList = orderRegularTaskService.taskGetToCheckOutOrderSnList(taskRequest);
                if(Check.NuNCollection(orderList)){
                    return;
                }
                for(String orderSn:orderList){
                    this.taskCheckOutOrder(orderSn);
                }
                LogUtil.info(LOGGER,"【自动退房Job】taskCheckOut |orderList:{}",JsonEntityTransform.Object2Json(orderList));
            }catch (Exception e){
                LogUtil.error(LOGGER, "【自动退房Job】e:{}", e);
            }
        }

    }

    /**
     * 自动退房
     * @author afi
     * @param orderSn
     */
    private void taskCheckOutOrder(String orderSn){
        try {
            LogUtil.info(LOGGER, "【自动退房Job】 orderSn:{}",orderSn);

            // orderRegularTaskService.taskCheckOutOrder(orderSn,OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus());
            // 修改成了直接掉用退房逻辑
            CheckOutOrderRequest request = new CheckOutOrderRequest();
            request.setUid("001");
            request.setOrderSn(orderSn);
            request.setRemark("定时任务自动退房");
            orderUserServiceProxy.checkOutOrder(JsonEntityTransform.Object2Json(request));
        }catch (Exception e){
            LogUtil.error(LOGGER, "【自动退房Job】失败 orderSn :{}，e:{}",orderSn,e);
        }
    }



    /**
     * 房东超时自动确认额外消费
     * @author lishaochuan
     * @create 2016年5月12日上午12:32:20
     */
	@Override
	public void taskConfirmOtherFeeLandlord() {
		LogUtil.info(LOGGER, "【房东自动确认额外消费Job】开始");
		
		String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(TradeRulesEnum.TradeRulesEnum0015.getValue())); //房东额外消费时限（小时）      
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
        	LogUtil.error(LOGGER, "【房东自动确认额外消费Job】获取房东额外消费时限失败,timeStrJson:{}", timeStrJson);
        	throw new BusinessException("【房东自动确认额外消费Job】获取房东额外消费时限失败");
		}
        int limitHours = ValueUtil.getintValue(resultDto.getData().get("textValue"));
        if (limitHours <= 0) {
        	limitHours = 24;
		}
		
		int limit = 150;
        Date limitTime =DateUtil.intervalDate(-Math.abs(limitHours), IntervalUnit.HOUR);
        LogUtil.info(LOGGER, "【房东自动确认额外消费Job】房东额外消费时限（小时）:{}，计算出来的时间：{}", limitHours, limitTime);

        Long count = orderRegularTaskService.taskConfirmOtherFeeLandlordCount(limitTime);
        if(Check.NuNObj(count) || count == 0){
            return;
        }
		int pageAll = ValueUtil.getPage(count.intValue(), limit);
		for (int page = 1; page <= pageAll; page++) {
			try {
				List<OrderEntity> orderList = orderRegularTaskService.taskConfirmOtherFeeLandlordList(limitTime, limit);
				if(Check.NuNCollection(orderList)){
                    return;
                }
				for (OrderEntity orderEntity : orderList) {
					LogUtil.info(LOGGER, "orderSn:{}", orderEntity.getOrderSn());
					LoadlordRequest loadlordRequest = new LoadlordRequest();
					loadlordRequest.setOrderSn(orderEntity.getOrderSn());
					loadlordRequest.setOrderStatus(OrderStatusEnum.getOrderStatusByCode(orderEntity.getOrderStatus()).getOtherMoneyStatus(0).getOrderStatus());
					loadlordRequest.setParamValue("定时任务");
					loadlordRequest.setOtherMoney(0);
					loadlordRequest.setLandlordUid(orderEntity.getLandlordUid());
                    LogUtil.info(LOGGER, "【房东自动确认额外消费Job】房东确认额外消费参数,loadlordRequest:{}", JsonEntityTransform.Object2Json(loadlordRequest));
					orderLandlordServiceProxy.saveOtherMoney(JsonEntityTransform.Object2Json(loadlordRequest));
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "【房东自动确认额外消费Job】e:{}", e);
			}
		}
	}



	/**
     * 房客超时自动同意额外消费
     * @author lishaochuan
     * @create 2016年5月12日上午12:33:13
     */
	@Override
	public void taskConfirmOtherUser() {
		LogUtil.info(LOGGER, "【房客自动同意额外消费Job】开始");
		
		String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(TradeRulesEnum.TradeRulesEnum0016.getValue())); //房客额外消费时限（小时）    
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
        	LogUtil.error(LOGGER, "【房客自动同意额外消费Job】获取房东额外消费时限失败,timeStrJson:{}", timeStrJson);
        	throw new BusinessException("【房客自动同意额外消费Job】获取房东额外消费时限失败");
		}
        int limitHours = ValueUtil.getintValue(resultDto.getData().get("textValue"));
        if (limitHours <= 0) {
        	limitHours = 24;
		}
		
		int limit = 150;
        Date limitTime = DateUtil.intervalDate(-Math.abs(limitHours), IntervalUnit.HOUR);
        LogUtil.info(LOGGER, "【房客自动同意额外消费Job】房客额外消费时限（小时）:{}，计算出来的时间:{}", limitHours, limitTime);
        
        Long count = orderRegularTaskService.taskConfirmOtherFeeUserCount(limitTime);
        if(Check.NuNObj(count) || count == 0){
            return;
        }
        int pageAll = ValueUtil.getPage(count.intValue(), limit);
		for (int page = 1; page <= pageAll; page++) {
			try {
				List<OrderEntity> orderList = orderRegularTaskService.taskConfirmOtherFeeUserList(limitTime, limit);
				if (Check.NuNCollection(orderList)) {
					return;
				}
				for (OrderEntity orderEntity : orderList) {
					LogUtil.info(LOGGER, "orderSn:{}", orderEntity.getOrderSn());
					ConfirmOtherMoneyRequest request = new ConfirmOtherMoneyRequest();
					request.setUid(orderEntity.getUserUid());
					request.setOrderSn(orderEntity.getOrderSn());
					request.setLandlordUid(orderEntity.getLandlordUid());
                    LogUtil.info(LOGGER, "【房客自动同意额外消费Job】房客确认额外消费参数,request:{}", JsonEntityTransform.Object2Json(request));
					orderUserServiceProxy.confirmOtherMoney(JsonEntityTransform.Object2Json(request));
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "【房客自动同意额外消费Job】e:{}", e);
			}
		}
	}


	/**
	 * 房客退房日当天给房客发短信
	 * @author lisc
	 */
	@Override
	public void taskCheckOutRemind() {
		LogUtil.info(LOGGER, "【房客退房日当天给房客发短信Job】开始");
		List<OrderEntity> checkOutTodayList = orderRegularTaskService.getCheckOutTodayList();
		for (OrderEntity orderEntity : checkOutTodayList) {
			orderMsgProxy.sendMsg4CheckOutRemind(orderEntity.getUserTel(), orderEntity.getUserName(),orderEntity.getUserTelCode());
		}
		LogUtil.info(LOGGER, "【房客退房日当天给房客发短信Job】结束");
	}


	/**
	 * 统计三小时内新增的当前的恶意下单数量
	 * @author afi
	 */
	@Override
	public void taskMaliceOrder(Integer num) {
		LogUtil.info(LOGGER, "【统计恶意下单Job】开始");
		List<MinsuEleEntity> maliceList = orderCommonServiceImpl.findMaliceOrder(num);
		if (!Check.NuNCollection(maliceList)){
			LogUtil.info(LOGGER, "【恶意下单】返回结果{}",JsonEntityTransform.Object2Json(maliceList));
			MinsuEleEntity frist = maliceList.get(0);
			Map<String,String> devPar = new HashMap<>();
			devPar.put("{1}", frist.getEleKey());
			devPar.put("{2}", frist.getEleValue());
			String mobiles = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(), EnumMinsuConfig.minsu_mobileList.getCode());
			//恶意下单 给开发人发送信息
			LogUtil.info(LOGGER,"恶意下单 给开发人发送信息, mobiles:{}", mobiles);
			SendThreadPool.execute(new SendThread<>(smsSendService, mobiles, MessageTemplateCodeEnum.ORDER_Malice.getCode(), devPar,null));
		}else {
			LogUtil.info(LOGGER, "没有恶意订单");
		}
		LogUtil.info(LOGGER, "【统计恶意下单Job】结束");
	}


    /**
     * 检查被邀请人是否有已经入住订单
     * @author lishaochuan
     * @param json
     * @return
     */
    @Override
    public String checkIfInviteCheckInOrder(String json) {
        LogUtil.info(LOGGER, "参数:{}", json);
        DataTransferObject dto = new DataTransferObject();
        InviteEntity inviteEntity = JsonEntityTransform.json2Object(json, InviteEntity.class);
        try {
            Boolean haveFlag = false;
            Long count = orderRegularTaskService.checkIfInviteCheckInOrder(inviteEntity);
            if (!Check.NuNObj(count) && count > 0) {
                haveFlag = true;
            }
            dto.putValue("haveFlag", haveFlag);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    @Override
    public String listTodayCheckInOrderAndUseCouponPage(String paramJson) {
        LogUtil.info(LOGGER, "listTodayCheckInOrderAndUseCouponPage 参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        PageRequest pageRequest = JsonEntityTransform.json2Object(paramJson, PageRequest.class);
        if (Check.NuNObj(pageRequest)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        PagingResult<OrderActivityEntity> pageResult = orderActivityServiceImpl.listTodayCheckInOrderAndUseCouponPage(pageRequest);
        dto.putValue("total", pageResult.getTotal());
        dto.putValue("list", pageResult.getRows());
        return dto.toJsonString();
    }


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService#getWaitCheckinList(java.lang.String)
	 */
	@Override
	public String getWaitCheckinList(String paramJson) {
		LogUtil.info(LOGGER, "getWaitCheckinList  参数={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		PageRequest pageRequest = JsonEntityTransform.json2Object(paramJson, PageRequest.class);
		if(Check.NuNObj(pageRequest)){
			 dto.setErrCode(DataTransferObject.ERROR);
	         dto.setMsg("参数为空");
	         return dto.toJsonString();
		}
		PagingResult<OrderHouseVo> pageResult = orderRegularTaskService.getWaitCheckinList(pageRequest);
		dto.putValue("total", pageResult.getTotal());
		dto.putValue("list", pageResult.getRows());
		return dto.toJsonString();
	}


	/**
	 * 
	 * 提前一天 通知房客入住
	 *
	 * @author yd
	 * @created 2017年9月8日 下午2:13:41
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String checkInPreNoticeTenant(String paramJson) {
		
		DataTransferObject dto = new DataTransferObject();
		
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				OrderRequest orderRequest = new OrderRequest();
				orderRequest.setCheckInTimeStart(DateUtil.getDayAfterCurrentDate()+" 00:00:00");
				orderRequest.setCheckInTimeEnd(DateUtil.getDayAfterCurrentDate()+" 23:59:59");
				orderRequest.setPayStatus(OrderPayStatusEnum.HAS_PAY.getPayStatus()+"");
				orderRequest.setOrderStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
				int page = 0;
				orderRequest.setLimit(500);
				do {
					orderRequest.setPage(++page);
					try {
						PagingResult<OrderInfoVo>  pageOrder = orderCommonServiceImpl.getOrderInfoListByCondiction(orderRequest);
						List<OrderInfoVo> list = pageOrder.getRows();
						if(Check.NuNCollection(list)){
							page =0;
							break;
						}
						
						for (OrderInfoVo orderInfoVo : list) {
							orderMsgProxy.sendCheckInPreNotice(orderInfoVo);
						}
					} catch (Exception e) {
						LogUtil.error(LOGGER, "【通知房客入住】异常orderRequest={},e={}", JsonEntityTransform.Object2Json(orderRequest),e);
					}
					
					
				} while (page>0);
			
				
			}
		});
		th.start();
		return dto.toJsonString();
	}
}
