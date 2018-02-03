package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.account.dto.AccountDetailRequest;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.constant.CatConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.order.api.inner.OrderPayService;
import com.ziroom.minsu.services.order.constant.OrderMessageConst;
import com.ziroom.minsu.services.order.dto.PayCallBackRequest;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.PayVo;
import com.ziroom.minsu.services.order.service.*;
import com.ziroom.minsu.services.order.utils.PayUtil;
import com.ziroom.minsu.valenum.account.ConsumeTypeEnum;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.common.ErrorCodeEnum;
import com.ziroom.minsu.valenum.finance.PaymentSourceTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.*;
import com.ziroom.minsu.valenum.pay.PayErrorCodeEnum;
import com.ziroom.minsu.valenum.pay.PlatFormPayEnum;
import com.ziroom.minsu.valenum.pay.ToPayTypeEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


/**
 * <p>订单支付</p>
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
@Component("order.orderPayServiceProxy")
public class OrderPayServiceProxy implements OrderPayService {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderPayServiceProxy.class);

	@Resource(name = "order.messageSource")
    private MessageSource messageSource;
	
	@Resource(name = "order.callAccountServiceProxy")
	private CallAccountServiceProxy callAccountServiceProxy;

	
	@Resource(name = "order.orderPayServiceImpl")
	private OrderPayServiceImpl orderPayServiceImpl;
	
	@Resource(name = "order.orderUserServiceImpl")
	private OrderUserServiceImpl orderUserService;
	
	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl;
	
	@Resource(name = "order.financePunishServicesImpl")
	private FinancePunishServicesImpl financePunishServicesImpl;
	
	@Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;
	
	@Resource(name ="order.orderActivityServiceImpl")
	private OrderActivityServiceImpl orderActivityServiceImpl;

    @Resource(name ="order.dealPayServiceProxy")
    private DealPayServiceProxy dealPayServiceProxy;
    
    @Resource(name ="order.houseLockServiceImpl")
    private HouseLockServiceImpl houseLockServiceImpl;
    
    
    @Resource(name = "order.orderToPayServiceImpl")
	private OrderToPayServiceImpl orderToPayServiceImpl;

	@Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;
	
	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;
	
	@Resource(name="house.queueName")
	private QueueName queueName ;

	@Value(value = "${PAY.PAY_AES_KEY}")
    private String PAY_AES_KEY;
	
	@Value(value = "${SYS.LOCAL_URL}")
    private String LOCAL_URL;

	/**
	 * 去支付
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param params
	 * @return
	 */
	@Override
	public String checkToPay(String params) {
		LogUtil.info(LOGGER, "【去支付参数】params:{}", params);
		DataTransferObject dto = new DataTransferObject();
		try {
			ToPayRequest toPayRequest = JsonEntityTransform.json2Object(params, ToPayRequest.class);
			
			// 公共参数校验
			this.checkCommonParam(toPayRequest, dto);
			if (dto.getCode() != DataTransferObject.SUCCESS) {
				return dto.toJsonString();
			}
			
			
			if (ToPayTypeEnum.NORMAL_PAY.getCode() == toPayRequest.getToPayType()) {
				// 获取订单信息
				OrderInfoVo orderInfoVo = orderCommonServiceImpl.getOrderInfoByOrderSn(toPayRequest.getOrderSn());
				
				// 正常支付校验
				this.checkOrderPayParam(toPayRequest, orderInfoVo, dto);
				if (dto.getCode() != DataTransferObject.SUCCESS) {
					return dto.toJsonString();
				}
				//去支付 锁房源逻辑
				this.lockHouseLogic(orderInfoVo,dto);
				if (dto.getCode() != DataTransferObject.SUCCESS) {
					return dto.toJsonString();
				}
				// 获取支付单号
//				String payCode = this.getPayCode(toPayRequest.getOrderSn());
//				dto.putValue("payCode", payCode);

			} else if (ToPayTypeEnum.PUNISH_PAY.getCode() == toPayRequest.getToPayType()) {
				// 罚款单支付
				this.checkPunishPayParam(toPayRequest, dto);
				if (dto.getCode() != DataTransferObject.SUCCESS) {
					return dto.toJsonString();
				}

			} else {
				PayUtil.parseFailDto(dto, PayErrorCodeEnum.common104.getCode());
			}
			
			return dto.toJsonString();
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【去支付参数校验】error:{}", e);
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.common105.getCode());
			return dto.toJsonString();
		}
	}
	
	/**
	 * @category 订单锁房源逻辑
	 * @author liyingjie
	 * @param orderInfoVo
	 * @param dto
	 * @return
	 */
	private void lockHouseLogic(OrderInfoVo orderInfoVo,DataTransferObject dto){
		// 获取房源快照
		OrderHouseSnapshotEntity orderHouse = this.getHouseSnapshot(orderInfoVo.getOrderSn(), dto);
		if (dto.getCode() != DataTransferObject.SUCCESS) {
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order207.getCode());
			return;
		}
		
		// 获取房源锁列表
		List<String> lockSnList = this.getLockHouse(orderInfoVo, orderHouse, dto);
		if (dto.getCode() != DataTransferObject.SUCCESS) {
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order208.getCode());
			return;
		}
		
		// 如果不是当前订单，锁房源
		this.lockHouse(orderInfoVo, orderHouse, lockSnList, dto);
		//暂时改为错误信息
		if (dto.getCode() != DataTransferObject.SUCCESS) {
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order209.getCode());
			return;
		}
	}
	
	
	
	/**
	 * 获取支付单号
	 * @author lishaochuan
	 * @create 2016年9月22日下午9:39:36
	 * @param orderSn
	 * @return
	 */
	private String getPayCode(String orderSn){
		OrderToPayEntity toPayEntity = orderToPayServiceImpl.selectByOrderSn(orderSn);
		if(!Check.NuNObj(toPayEntity)){
			return toPayEntity.getPayCode();
		}
		return "";
	}
	
	
	
	
	/**
	 * 公共参数校验
	 * @author lishaochuan
	 * @create 2016年7月5日下午3:20:10
	 * @param toPayRequest
	 * @param dto
	 */
	private void checkCommonParam(ToPayRequest toPayRequest, DataTransferObject dto){
		// 校验请求参数存在
		if (Check.NuNObj(toPayRequest)) {
			LogUtil.error(LOGGER, "【去支付参数校验】{}params:{}",PayErrorCodeEnum.common100.getMessage(), toPayRequest);
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.common100.getCode());
			return;
		}
		//校验订单号
		if(Check.NuNStr(toPayRequest.getOrderSn())){
			LogUtil.error(LOGGER, "【去支付参数校验】{}orderSn:{}",PayErrorCodeEnum.common101.getMessage(), toPayRequest.getOrderSn());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.common101.getCode());
			return;
		}
		//校验 是否有去支付list
		if( Check.NuNObj(toPayRequest.getPayList()) || (toPayRequest.getPayList().size() <= 0)){
			LogUtil.error(LOGGER, "【去支付参数校验】{}payList:{}",PayErrorCodeEnum.common102.getMessage(), toPayRequest.getPayList().size());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.common102.getCode());
			return;
		}
	}

	/**
	 * 校验正常支付参数
	 * 
	 * @author liyingjie
	 * @create 2016年5月10日
	 * @param dto
	 * @return
	 */
	private void checkOrderPayParam(ToPayRequest toPayRequest, OrderInfoVo orderInfoVo, DataTransferObject dto){
		LogUtil.info(LOGGER, "【去支付参数校验,订单支付】");
		
		//校验订单 
		if (Check.NuNObj(orderInfoVo)) {
            LogUtil.error(LOGGER, "{}orderSn:{}", PayErrorCodeEnum.order201.getMessage(), toPayRequest.getOrderSn());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order201.getCode());
			return;
		}
		
		//校验订单状态
		if (orderInfoVo.getOrderStatus() != OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()) {
            LogUtil.error(LOGGER, "{}orderStatus:{}orderSn:{}", PayErrorCodeEnum.order206.getMessage(), orderInfoVo.getOrderStatus(), toPayRequest.getOrderSn());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order206.getCode());
			return;
		}
		
		//校验用户id是否与订单上的是否一致
		if (!orderInfoVo.getUserUid().equals(toPayRequest.getUserUid())) {
            LogUtil.error(LOGGER, "{}reqUserId:{}orderSn:{}", PayErrorCodeEnum.order202.getMessage(), orderInfoVo.getUserUid(), "orderUserId:{}", toPayRequest.getUserUid(), toPayRequest.getOrderSn());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order202.getCode());
			return;
		}
		
		//是否已经支付
		if(orderInfoVo.getPayStatus() == OrderPayStatusEnum.HAS_PAY.getPayStatus()){
			LogUtil.error(LOGGER, "{}payStatus:{}orderSn:{}",PayErrorCodeEnum.order203.getMessage(),orderInfoVo.getPayStatus(), toPayRequest.getOrderSn());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order203.getCode());
			return;
        }
		
		//校验支付金额  是否与订单金额一致
		if(orderInfoVo.getNeedPay() != getCustomerPayMoney(toPayRequest)){
			LogUtil.error(LOGGER, "{}orderMoney:{}orderSn:{}",PayErrorCodeEnum.order204.getMessage(),orderInfoVo.getNeedPay(),"paramMoney:{}",getCustomerPayMoney(toPayRequest), toPayRequest.getOrderSn());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order204.getCode());
			return;
		}
		
		//获取支付订单有效时间
		long expireTime = this.getExpireTime(orderInfoVo);
		
		//校验订单有效时间
		if(expireTime <= 0){
			LogUtil.info(LOGGER, "{}expireTime:{}orderSn:{}",PayErrorCodeEnum.order205.getMessage(),expireTime, toPayRequest.getOrderSn());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.order205.getCode());
			return;
		}
		
		toPayRequest.setTotalFee(orderInfoVo.getNeedPay());//取订单上needPay金额为支付总金额
		toPayRequest.setBizeCode(toPayRequest.getOrderSn());
		toPayRequest.setNotifyUrl(LOCAL_URL+SysConst.pay_order_callback_url);//设置回调url
		toPayRequest.setExpiredDate(expireTime);//设置订单有效时间，单位秒
		toPayRequest.setPassAccount(ConsumeTypeEnum.un_fill.getCode()); //是否需要支付平台充值
		toPayRequest.setUidType(CustomerTypeEnum.roomer.getPayName());//支付客户的类型
		dto.putValue("toPayRequest", toPayRequest);
		PayUtil.parseSuccessDto(dto); //TODO:ttttttttt
	}
	
	
	/**
	 * 获取房源快照
	 * @author lishaochuan
	 * @create 2016年7月6日上午9:25:57
	 * @param orderSn
	 * @param dto
	 * @return
	 */
	private OrderHouseSnapshotEntity getHouseSnapshot(String orderSn, DataTransferObject dto){
		LogUtil.info(LOGGER, "【去支付校验完毕,开始锁房源逻辑】");
		
		// 获取房源快照
		OrderHouseSnapshotEntity orderHouse = orderCommonServiceImpl.findHouseSnapshotByOrderSn(orderSn);
		if(Check.NuNObj(orderHouse)){
			LogUtil.error(LOGGER, "【去支付】 房源快照不存在,orderSn:{}, orderHouseSnapshot:{}", orderSn, orderHouse);
            /*dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("房源快照不存在");*/
			return null;
		}
		return orderHouse;
	}
	
	
	/**
	 * 获取房源被锁的订单list
	 * @author lishaochuan
	 * @create 2016年7月6日上午9:27:48
	 * @param orderInfoVo
	 * @param dto
	 */
	private List<String> getLockHouse(OrderInfoVo orderInfoVo, OrderHouseSnapshotEntity orderHouse, DataTransferObject dto) {
		LogUtil.info(LOGGER, "【去支付,校验当前的房源是否被占有】");
		Date startTime = DateSplitUtil.transDateTime2Date(orderInfoVo.getStartTime());
		Date endTime = DateSplitUtil.transDateTime2Date(orderInfoVo.getEndTime());

		List<String> lockSnList = null;
		if (orderHouse.getRentWay() == RentWayEnum.HOUSE.getCode()) {
			// 整租
			lockSnList = houseLockServiceImpl.getHouseLockOrderSn(orderHouse.getHouseFid(), startTime, endTime);
		} else if (orderHouse.getRentWay() == RentWayEnum.ROOM.getCode()) {
			// 合租
			lockSnList = houseLockServiceImpl.getRoomLockOrderSn(orderHouse.getRoomFid(), startTime, endTime);
		} else if (orderHouse.getRentWay() == RentWayEnum.BED.getCode()) {
			// 床位
			lockSnList = houseLockServiceImpl.getBedLockOrderSn(orderHouse.getBedFid(), startTime, endTime);
		} else {
			// 异常的code
			LogUtil.error(LOGGER, "【去支付】 异常的房源类型:{}", orderHouse.getRentWay());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源类型错误");
			//throw new BusinessException("error houseType current rentWay is:" + orderHouse.getRentWay());
		}

		if (Check.NuNObj(lockSnList)) {
			LogUtil.error(LOGGER, "【去支付】校验是否房源已经被锁错误,orderHouse:{}", JsonEntityTransform.Object2Json(orderHouse));
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("校验房源锁错误");
		}
		
		return lockSnList;
	}
	
	
	/**
	 * 锁房源
	 * @author lishaochuan
	 * @create 2016年7月5日下午3:30:56
	 * @param dto
	 */
	private void lockHouse(OrderInfoVo orderInfoVo, OrderHouseSnapshotEntity orderHouse, List<String> lockSnList, DataTransferObject dto){
		if (!Check.NuNCollection(lockSnList) && lockSnList.size() != 1) {
			LogUtil.info(LOGGER, "【去支付】 房源已经被占有,orderHouse:{},占用订单list：{}", JsonEntityTransform.Object2Json(orderHouse), lockSnList);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));	
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_OCCUPY));
			return;
		}
		
		if (!Check.NuNCollection(lockSnList) && lockSnList.size() == 1) {
			String orderSn = lockSnList.get(0);
			if (!orderInfoVo.getOrderSn().equals(orderSn)) {
				LogUtil.info(LOGGER, "【去支付】 房源已经被其他订单占有,orderHouse:{},占用订单号：{}", JsonEntityTransform.Object2Json(orderHouse), orderSn);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_OCCUPY));
				return;
			}
			LogUtil.info(LOGGER, "【去支付】 房源是被自己占用的，不用再次锁房源");
			return;
		}
		LogUtil.info(LOGGER, "【去支付,锁房源】");
		
		Date startTime = DateSplitUtil.transDateTime2Date(orderInfoVo.getStartTime());
		Date endTime = DateSplitUtil.transDateTime2Date(orderInfoVo.getEndTime());
		
		List<Date> dateList = DateSplitUtil.dateSplit(startTime, endTime);
		List<HouseLockEntity> lockEntitys = new ArrayList<HouseLockEntity>();
		for (int i = 0; i < dateList.size(); i++) {
			HouseLockEntity lockEntity = new HouseLockEntity();
			lockEntity.setOrderSn(orderHouse.getOrderSn());
			lockEntity.setCreateTime(new Date());
			lockEntity.setRentWay(orderHouse.getRentWay());
			lockEntity.setHouseFid(orderHouse.getHouseFid());
			if (orderHouse.getRentWay() == RentWayEnum.ROOM.getCode()) {
				lockEntity.setRoomFid(orderHouse.getRoomFid());
			}
			if (orderHouse.getRentWay() == RentWayEnum.BED.getCode()) {
				lockEntity.setRoomFid(orderHouse.getBedFid());
			}
			lockEntity.setLockTime(dateList.get(i));
			lockEntity.setLockType(LockTypeEnum.ORDER.getCode());
			lockEntitys.add(lockEntity);
		}
        try{
            //afi 插入房源锁信息
            houseLockServiceImpl.insertHouseLockListByOrder(lockEntitys);
        }catch (Exception e){
            LogUtil.error(LOGGER,"order:{};抢房出现并发的情况，捕获并发信息：{}",e,JsonEntityTransform.Object2Json(orderInfoVo));
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_HOUSE_OCCUPY));
            return;
        }

	}
	
	
	/**
	 * 校验罚款支付参数
	 * 
	 * @author liyingjie
	 * @create 2016年5月10日
	 * @param toPayRequestStr 
	 * @param dto
	 * @return
	 */
	private void checkPunishPayParam(ToPayRequest toPayRequest , DataTransferObject dto){
		LogUtil.info(LOGGER, "【去支付参数校验,账单支付】");
		FinancePunishEntity fpe = financePunishServicesImpl.getPunishByPunishSn(toPayRequest.getBizeCode());
        
        //校验账单单号 
		if (Check.NuNObj(fpe)){
			LogUtil.info(LOGGER, "{}punishSn:{}", PayErrorCodeEnum.punish301.getMessage(), toPayRequest.getBizeCode());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.punish301.getCode());
			return;
		}
        
		//校验用户id是否与订单上的是否一致
		if (!fpe.getPunishUid().equals(toPayRequest.getUserUid())){
			LogUtil.info(LOGGER, "{}reqUserId:{}", PayErrorCodeEnum.punish302.getMessage(), fpe.getPunishUid(), "punishUserId:{}", toPayRequest.getUserUid());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.punish302.getCode());
			return;
		}
		
		//账单 已支付
		if(fpe.getPunishStatus() == PunishStatusEnum.YES.getCode()){
			LogUtil.info(LOGGER, "{}payStatus:{}", PayErrorCodeEnum.punish303.getMessage(), fpe.getPunishStatus());
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.punish303.getCode());
			return;
		}
		
		//校验用户支付金额是否与订单上的金额一致
		if (fpe.getPunishFee() != getPunishPayMoney(toPayRequest)) {
			LogUtil.info(LOGGER, "{}punishMoney:{}",PayErrorCodeEnum.punish304.getMessage(),fpe.getPunishFee(),"paramMoney:{}",getCustomerPayMoney(toPayRequest));
			PayUtil.parseFailDto(dto,PayErrorCodeEnum.punish304.getCode());
			return;
		}
		
		toPayRequest.setTotalFee(fpe.getPunishFee());
		toPayRequest.setNotifyUrl(LOCAL_URL+SysConst.pay_punish_callback_url);
		toPayRequest.setExpiredDate(SysConst.time_limit*60l);
		toPayRequest.setPassAccount(ConsumeTypeEnum.un_fill.getCode());
		toPayRequest.setUidType(CustomerTypeEnum.landlord.getPayName());
		dto.putValue("toPayRequest", toPayRequest);
		PayUtil.transSuccessDto(dto);
	}
	
	
	
	/**
	 * 获取金额
	 * @param toPayRequest
	 * @return
	 */
	private int getCustomerPayMoney(ToPayRequest toPayRequest){
		int reqPay = 0;//实际支付金额
		for (PayVo pay : toPayRequest.getPayList()) {
			if (pay.getOrderType().equals(PlatformOrderTypeEnum.out_pay.getOrderType())
					|| pay.getOrderType().equals(PlatformOrderTypeEnum.consume_pay.getOrderType())) {
				reqPay += pay.getPayMoney();
			}
		}
		return reqPay;
	}
	
	
	/**
   	 * 获取订单支付剩余时间
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月27日 
   	 * @param orderInfoVo
   	 * @return
   	 */
	private long getExpireTime(OrderInfoVo orderInfoVo) {
		// 生成订单有效时间
        // OrderEntity oe = orderUserService.getOrderBaseByOrderSn(orderSn);
        Date lastUpdateTime = orderInfoVo.getLastModifyDate();
        
        long time = (new Date().getTime() - lastUpdateTime.getTime())/1000; //时间差
        
        String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(TradeRulesEnum.TradeRulesEnum002.getValue())); //有效时长          
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
        	throw new BusinessException("getExpireTime error");
		}
        int timeLimit = ValueUtil.getintValue(resultDto.getData().get("textValue"));
		int timeSeconds = Integer.valueOf(timeLimit)*60;
		long resTime = timeSeconds - time;
		Map<String, String> resMap = new HashMap<String,String>(3);
		resMap.put("timeLimit", String.valueOf(timeLimit));
		resMap.put("resTime", String.valueOf(resTime));
		
        return resTime;
	}


	/**
   	 * 支付回调接口
   	 * 
   	 * @author liyingjie
   	 * @created 2016年4月8日 
   	 * @param encryption   
   	 * @param type
   	 * @return
   	 */
	@Override
    public String payCallBack(String encryption,int type){
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER, "回调请求的参数为 encryption:{},type : {}", encryption,type);
		try{
			if(Check.NuNStr(encryption)){//参数为空
	    		PayUtil.transFailDto(dto);
	    		LogUtil.error(LOGGER, "参数为空,encryption:{}", encryption);
	    		return dto.toJsonString();
	    	}
			String newEncryption = decode(encryption);//解码
    		if(Check.NuNStr(newEncryption)){
        	    PayUtil.transFailDto(dto);
        		LogUtil.error(LOGGER, "解码失败:{}", encryption);
        		return dto.toJsonString();
        	}
        	String payBackJson = CryptAES.AES_Decrypt(PAY_AES_KEY, newEncryption);//解密
        	LogUtil.info(LOGGER, "回调请求的参数为 payBackJson：{}", payBackJson);
        	if(Check.NuNStr(payBackJson)){
        		PayUtil.transFailDto(dto);
        		LogUtil.error(LOGGER, "解码为空:{}", newEncryption);
        		return dto.toJsonString();
        	}
        	PayCallBackRequest payBackRequest = JsonEntityTransform.json2Object(payBackJson, PayCallBackRequest.class);//封装成回调对象
        	if(checkPayCallBackParam(payBackRequest)){
        		PayUtil.transFailDto(dto);
        		LogUtil.error(LOGGER, "参数校验不通过，BizCode、OrderCode、PayType为空，payBackRequest:{}", payBackRequest);
        		return dto.toJsonString();
        	}
            //订单支付回调
        	if(type == PlatFormPayEnum.order.getCode()){
        		// 正常支付回调
        		dto = orderPayLogic(payBackRequest,dto);
        	}else if(type == PlatFormPayEnum.punish.getCode()){ 
        		//账单支付回调
        		dto = punishPayLogic(payBackRequest,dto);
        	}else {
				PayUtil.transFailDto(dto);
				LogUtil.error(LOGGER, "异常的支付类型，payBackRequest:{}", payBackRequest);
				return dto.toJsonString();
			}
        	return dto.toJsonString();
    	}catch(Exception e){
    		PayUtil.transFailDto(dto);
    		LogUtil.error(LOGGER, "exception :{}", e);
    		return dto.toJsonString();
    	}
    }
	
	

	
	/**
   	 * 订单支付回调具体处理业务
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月8日 
   	 * @param 
   	 * @return
   	 */
    private DataTransferObject orderPayLogic(PayCallBackRequest payBackRequest,DataTransferObject dto){
    	String payCode= payBackRequest.getOrder_code();
    	OrderToPayEntity orderToPayEntity = orderToPayServiceImpl.selectByPayCode(payCode);
		final String orderSn = orderToPayEntity.getOrderSn();

    	OrderInfoVo orderInfo = orderUserService.getOrderInfoByOrderSn(orderSn);//查询订单
    	if(Check.NuNObj(orderInfo)){
    		PayUtil.transFailDto(dto);
    		LogUtil.error(LOGGER, "非法的订单编号.orderSn:{}", orderSn);
    		return dto;
    	}
    	LogUtil.info(LOGGER, "【支付回调】订单信息为.order:{}", orderInfo);

    	payBackRequest.setNeedMoney(orderInfo.getNeedPay());
    	payBackRequest.setPayUid(orderInfo.getUserUid());
    	payBackRequest.setFillUid(orderInfo.getLandlordUid());
    	payBackRequest.setOrderSn(orderInfo.getOrderSn());
    	payBackRequest.setPaymentSourceType(PaymentSourceTypeEnum.customer.getCode());
    	//添加城市code
    	if(!Check.NuNStr(orderInfo.getCityCode())){
    		payBackRequest.setCityCode(orderInfo.getCityCode());
    	}

		if (orderInfo.getPayStatus() == OrderPayStatusEnum.HAS_PAY.getPayStatus()) {
			OrderPayEntity orderPay = orderPayServiceImpl.getOrderPayByOrderSn(orderSn);
			if (!Check.NuNStr(payBackRequest.getOut_trade_no()) && payBackRequest.getOut_trade_no().equals(orderPay.getTradeNo())) {
				LogUtil.info(LOGGER, "【支付回调】订单已经被支付 幂等成功.orderSn:{},payStatus:{}", orderSn, orderInfo.getPayStatus());
				PayUtil.transSuccessDto(dto);
				return dto;
			} else {
				LogUtil.error(LOGGER, "【支付回调】订单已经被支付，但已成功回调过，orderSn：{}，beforeTradeNo:{}，payBackRequest:{}，", orderSn, orderPay.getTradeNo(), JsonEntityTransform.Object2Json(payBackRequest));
				orderMsgProxy.sendMsg4RepeatPayCallBack(orderSn, payBackRequest.getOut_trade_no());
				PayUtil.transSuccessDto(dto);
				return dto;
			}
		}
		//活动类型
		List<Integer> types = new ArrayList<>();
        if(orderInfo.getCouponMoney() >0){
			types.add(OrderAcTypeEnum.COUPON.getCode());
        }
        //该订单参与活动 （首单立减）
		if (orderInfo.getActMoney() > 0){
        	types.add(OrderAcTypeEnum.FIRST_ORDER_REDUC.getCode());
		}
		//今夜特价
		types.add(OrderAcTypeEnum.LAN_TONIGHT_DISCOUNT.getCode());

		List<OrderActivityInfoVo> orderActList = orderActivityServiceImpl.listOrderActByOrderSnAndType(orderSn, types);
		//防止空指针
		if (orderActList == null){
			orderActList = new ArrayList<>();
		}
		LogUtil.info(LOGGER,"【支付回调】订单符合条件的活动列表list={}", com.alibaba.fastjson.JSONArray.toJSONString(orderActList));
		//使用流处理 获取符合条件的元素
		Optional<OrderActivityInfoVo> orderActTonightOpt = orderActList.stream().filter(act -> act.getAcType() == OrderAcTypeEnum.LAN_TONIGHT_DISCOUNT.getCode()).findAny();
		if(orderActTonightOpt.isPresent()){
        	orderInfo.setLanTonightDiscount(true);
			OrderActivityInfoVo orderActivityInfoVo = orderActTonightOpt.get();
			orderActList.remove(orderActivityInfoVo);
		}
        
		//获取当前的订单状态
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderInfo.getOrderStatus());
		if (Check.NuNObj(orderStatusEnum)){
			LogUtil.info(LOGGER, "【支付回调】异常的数据库订单状态.order:{}", JsonEntityTransform.Object2Json(orderInfo));
			PayUtil.transFailDto(dto);
			return dto;
		}
        
        if(orderStatusEnum.getOrderStatus() == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()){
			//校验订单状态  待入住
            LogUtil.info(LOGGER, "【支付回调】正常的支付回调处理 orderSn:{}", orderSn);
    		dealPayServiceProxy.dealNormalPayCallBack(dto,payBackRequest,orderActList,orderInfo);
    	}else if(orderStatusEnum.isCancel()){
			//校验订单状态 为 已取消订单
    		LogUtil.info(LOGGER, "【支付回调】订单被取消 orderSn:{}", orderSn);
            dealPayServiceProxy.dealCancelPayCallBack(dto, payBackRequest, orderInfo);
    	}else {
            LogUtil.error(LOGGER, "【支付回调】异常的订单状态的支付回调 order:{}", JsonEntityTransform.Object2Json(orderInfo));
            PayUtil.transFailDto(dto);
        }
    	
        if(dto.getCode() == DataTransferObject.SUCCESS){
        	
        	Thread mqThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					try {						
						OrderHouseSnapshotEntity orderHouseSnapshotEntity = orderCommonServiceImpl.findHouseSnapshotByOrderSn(orderSn);
						if (!Check.NuNObj(orderHouseSnapshotEntity) 
								&& !Check.NuNStr(orderHouseSnapshotEntity.getHouseFid()) && !Check.NuNObj(orderHouseSnapshotEntity.getRentWay())) {
							LogUtil.info(LOGGER, "订单支付成功,推送队列消息开始...");
							String pushContent = JsonEntityTransform.Object2Json(new HouseMq(orderHouseSnapshotEntity.getHouseFid(), null,
									orderHouseSnapshotEntity.getRentWay(), null, null));
							// 推送队列消息
							rabbitMqSendClient.sendQueue(queueName, pushContent);
							LogUtil.info(LOGGER, "订单支付成功,推送队列消息结束,推送内容:{}", pushContent);
							
						}
					} catch (Exception e) {
						LogUtil.error(LOGGER, "支付回掉推送mq异常，orderSn={},e={}",orderSn,e);
					}
					
				}
			});
        	mqThread.start();
        	
        	Transaction orderPayTran = Cat.newTransaction("OrderPayServiceProxy", CatConstant.PAY_SUM);
        	try {
        		//埋点用户的支付金额
                Cat.logMetricForSum("支付回调成功销售金额", payBackRequest.getTotal_price());
                orderPayTran.setStatus(Message.SUCCESS);
        	} catch(Exception ex) {
        		Cat.logError("支付回调成功销售金额 打点异常" + payBackRequest.toJsonStr(), ex);
        		orderPayTran.setStatus(ex);
        	} finally {
        		orderPayTran.complete();
    		}
            
        }
        return dto;
    }


    /**
     * 账单支付回调具体处理业务
     * @author liyingjie
     * @created 2016年4月8日
     * @param payBackRequest
     * @param dto
     * @return
     */
    private DataTransferObject punishPayLogic(PayCallBackRequest payBackRequest,DataTransferObject dto){
    	//获取账单  校验
    	FinancePunishEntity fpe = financePunishServicesImpl.getPunishListByOrderSn(payBackRequest.getBizCode());
    	
    	if(Check.NuNObj(fpe)){
            LogUtil.info(LOGGER, "非法的账单编号.punishSn:{}", payBackRequest.getBizCode());
    		PayUtil.transFailDto(dto);
    		return dto;
    	}
    	//添加城市code
    	if(!Check.NuNStr(fpe.getCityCode())){
    		payBackRequest.setCityCode(fpe.getCityCode());
    	}
    	/// 查询  订单的应付金额 账单应付人
    	payBackRequest.setNeedMoney(fpe.getPunishFee());
    	payBackRequest.setPayUid(fpe.getPunishUid());
    	payBackRequest.setOrderSn(fpe.getOrderSn());
        payBackRequest.setPaymentSourceType(PaymentSourceTypeEnum.landlord.getCode());
    	//修改账单状态、生成收款单
    	boolean res = orderPayServiceImpl.punishPayCallBack(payBackRequest);
    	if(res){
    		PayUtil.transSuccessDto(dto);
    	}else{
    		PayUtil.transFailDto(dto);
    	}
    	return dto;
    }
    


    
    /**
   	 * 支付回调接口 參數校驗
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月8日 
   	 * @param 
   	 * @return
   	 */
    private boolean checkPayCallBackParam(PayCallBackRequest payBackRequest){
    	boolean res = false;
		if (Check.NuNObj(payBackRequest)
				|| Check.NuNStr(payBackRequest.getBizCode())
				|| Check.NuNStr(payBackRequest.getOrder_code())
				|| Check.NuNStr(payBackRequest.getPay_type())) {
			res = true;
		}
    	return res;
    }
    
	/**
	 * 获取账单金额
	 * 
	 * @author liyingjie
	 * @param toPayRequest
	 * @return
	 */
	private int getPunishPayMoney(ToPayRequest toPayRequest){
		int reqPay = 0;//实际支付金额
		for (PayVo pay : toPayRequest.getPayList()) {
			if (pay.getOrderType().equals(PlatformOrderTypeEnum.out_pay)
					|| pay.getOrderType().equals(PlatformOrderTypeEnum.consume_pay)) {
				reqPay += pay.getPayMoney();
			}
		}
		return reqPay;
	}
	
	
	/**
   	 * 解码
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月8日 
   	 * @param 
   	 * @return
   	 */
	private String decode(String encryption) throws UnsupportedEncodingException{
		String newEncryption = "";
		if(Check.NuNStr(encryption)){
            LogUtil.info(LOGGER, "参数为空encryption:{}", encryption);
			return newEncryption;
		}
		newEncryption = URLDecoder.decode(encryption, "UTF-8");
		return newEncryption;
	}


	/**
	 * 获取账户余额
	 * @author lishaochuan
	 * @create 2016年5月5日下午9:45:59
	 * @param uid
	 * @param userType
	 * @return
	 */
	@Override
	public String getAccountBalance(String uid, int userType) {
		DataTransferObject dto = new DataTransferObject();
		try {
			AccountDetailRequest adr = new AccountDetailRequest();	
			adr.setUid(uid);
			adr.setUidType(CustomerTypeEnum.getCodeByStatusCode(userType));
			Map<String,String> res = callAccountServiceProxy.accountDetails(adr);
			if(!ErrorCodeEnum.success.getCodeEn().equals(res.get("status"))){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("获取账户余额失败");
				return dto.toJsonString();
			}
			dto.putValue("balance", res.get("balance"));
			dto.putValue("frozenBalance", res.get("frozen_balance"));
			return dto.toJsonString();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("获取账户余额失败");
			return dto.toJsonString();
		}
	}

	/**
	 * 查询单位时间内房源(房间)的交易量(已支付订单数量)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String queryTradeNumByHouseFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			Map<String, Object> paramMap = JsonEntityTransform.json2Object(paramJson, Map.class);
			List<HouseStatsVo> list = orderPayServiceImpl.queryTradeNumByHouseFid(paramMap);
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "queryTradeNumByHouseFid error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
    
}
