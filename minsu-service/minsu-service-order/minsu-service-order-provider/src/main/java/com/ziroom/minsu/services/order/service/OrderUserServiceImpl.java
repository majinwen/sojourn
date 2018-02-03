package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.services.order.dto.CancelOrderServiceRequest;
import com.ziroom.minsu.services.order.dto.CanclOrderRequest;
import com.ziroom.minsu.services.order.dto.OrderEvalRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.*;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.evaluate.EvaStatusEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvalTypeEnum;
import com.ziroom.minsu.valenum.finance.PaymentObjTypeEnum;
import com.ziroom.minsu.valenum.finance.PaymentTypeEnum;
import com.ziroom.minsu.valenum.order.*;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0025Enum;
import com.ziroom.minsu.valenum.traderules.CheckOutStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.Option;

import java.text.ParseException;
import java.util.*;

import static com.ziroom.minsu.services.order.utils.FinanceMoneyUtil.getCleanMoney;


/**
 * <p>用户的service</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Service("order.orderUserServiceImpl")
public class OrderUserServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderUserServiceImpl.class);

	@Resource(name = "order.orderDao")
	private OrderDao orderDao;

	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;

	@Resource(name = "order.orderConfigDao")
	private OrderConfigDao orderConfigDao;

	@Resource(name = "order.orderMoneyDao")
	private OrderMoneyDao orderMoneyDao;

	@Resource(name = "order.houseLockDao")
	private HouseLockDao houseLockDao;

	@Resource(name = "order.orderContactDao")
	private OrderContactDao orderContactDao;

	@Resource(name = "order.usualContactsDao")
	private UsualContactDao usualContactDao;

	@Resource(name = "order.orderSpecialPriceDao")
	private OrderSpecialPriceDao orderSpecialPriceDao;

	@Resource(name = "order.houseSnapshotDao")
	private OrderHouseSnapshotDao orderHouseSnapshotDao;

	@Resource(name = "order.activityDao")
	private OrderActivityDao orderActivityDao;

	@Resource(name = "order.payDao")
	private OrderPayDao orderPayDao;

	@Resource(name = "order.virtualOrderBaseDao")
	private VirtualOrderBaseDao virtualOrderBaseDao;

	@Resource(name = "order.financePayVouchersDao")
	private FinancePayVouchersDao financePayVouchersDao;

	@Resource(name = "order.financeIncomeDao")
	private FinanceIncomeDao financeIncomeDao;

	@Resource(name = "order.virtualPayVouchersDao")
	private VirtualPayVouchersDao virtualPayVouchersDao;

	@Resource(name = "order.financePaymentVouchersDao")
	private FinancePaymentVouchersDao financePaymentVouchersDao;


	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonService;

	@Resource(name = "order.orderLoadlordServiceImpl")
	private OrderLoadlordServiceImpl orderLoadlordServiceImpl;

	@Resource(name="order.financePenaltyDao")
	private FinancePenaltyDao financePenaltyDao;
	
	@Resource(name="order.financePenaltyLogDao")
	private FinancePenaltyLogDao financePenaltyLogDao;
	
	@Resource(name="order.orderCsrCancleDao")
	private OrderCsrCancleDao orderCsrCancleDao;

	/**
	 * 取消订单 只是更改订单状态不涉及退款
	 * @author afi
	 * @param order
	 * @param request
	 */
	public void cancleOrderOnly(OrderInfoVo order,CanclOrderRequest request){
		LogUtil.info(LOGGER, "取消订单 只是更改订单状态不涉及退款，orderSN:{}", request.getOrderSn());
		if(Check.NuNObj(request)){
			LogUtil.error(LOGGER,"请求参数: {},{}", JsonEntityTransform.Object2Json(order),JsonEntityTransform.Object2Json(request));
			throw new BusinessException("参数错误");
		}
		OrderStatusEnum orderStatus =  OrderStatusEnum.getOrderStatusByCode(order.getOrderStatus());
		OrderStatusEnum cancleStatus = orderStatus.getCancleStatus(order);
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(request.getOrderSn());
		orderEntity.setOrderStatus(cancleStatus.getOrderStatus());
		orderEntity.setRealEndTime(new Date());
		virtualOrderBaseDao.updateOrderInfoAndStatus(order.getUserUid(),orderStatus.getOrderStatus(), orderEntity, null, null, null);
		//释放房源
		LogUtil.info(LOGGER, "取消订单 释放房源，orderSN:{}", request.getOrderSn());
		houseLockDao.delLockHouseByOrderSn(request.getOrderSn());

		//当前的订单使用了优惠券 需要取消优惠券信息
		if (order.getCouponMoney() > 0){
			orderActivityDao.updateGetStatusByCoupon(order.getOrderSn());
		}
	}




	/**
	 * 获取当前订单信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderInfoVo getOrderInfoByOrderSn(String orderSn){
		return orderDao.getOrderInfoByOrderSn(orderSn);
	}

	/**
	 * 获取当前房源在当前时间内的占有情况
	 * @author afi
	 * @param houseFid 房源id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Long countHouseLock(String houseFid , Date startTime ,Date endTime){
		List<Date> dateList = new ArrayList<>();
		List<Date> dayList = DateSplitUtil.dateSplit(startTime, endTime);
		for (Date date: dayList){
			dateList.add(DateSplitUtil.connectDate(date,"00:00:00"));
		}
		return houseLockDao.countHouseLock(houseFid, dateList);
	}

	/**
	 * 获取当前房间在当前时间内的占有情况
	 * @author afi
	 * @param roomFid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Long countRoomLock(String roomFid , Date startTime ,Date endTime){
		List<Date> dateList = new ArrayList<>();
		List<Date> dayList = DateSplitUtil.dateSplit(startTime, endTime);
		for (Date date: dayList){
			dateList.add(DateSplitUtil.connectDate(date,"00:00:00"));
		}
		return houseLockDao.countRoomLock(roomFid, dateList);
	}

	/**
	 * 获取当前床位在当前时间内的占有情况
	 * @author afi
	 * @param bedFid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Long countBedLock(String bedFid , Date startTime ,Date endTime){
		List<Date> dateList = new ArrayList<>();
		List<Date> dayList = DateSplitUtil.dateSplit(startTime, endTime);
		for (Date date: dayList){
			dateList.add(DateSplitUtil.connectDate(date,"00:00:00"));
		}
		return houseLockDao.countBedLock(bedFid, dateList);
	}



	/**
	 * 获取当前用户在当前城市下的未支付订单数量
	 * @author afi
	 * @param cityCode
	 * @param uid
	 * @return
	 */
	public Long countCurrentNoPayOrder(String cityCode , String uid){

		return orderBaseDao.countCurrentNoPayOrder(cityCode, uid);

	}




	/**
	 * 创建订单
	 * @author afi
	 * @created 2016年4月1日
	 * @param orderSaveVo
	 */
	public void insertOrder(OrderSaveVo orderSaveVo){
		//1.锁定房源信息 考虑用唯一索引来保证不会重复（移到去支付时锁房源），只有实时订单的0元订单才做当前的操作
		this.insertHouseLock(orderSaveVo);
		//2.订单入住人关系表
		this.insertOrderContact(orderSaveVo);
		//3.保存订单的快照
		this.insertOrderHouse(orderSaveVo);
		//4.保存订单的活动
		this.insertOrderActivitys(orderSaveVo);
		//5.保存订单的特殊价格
		this.insertOrderPrices(orderSaveVo);
		//6.保存订单的钱配置信息
		this.insertOrderConfigs(orderSaveVo);
		//7.保存订单金额信息
		this.insertOrderMoney(orderSaveVo);
		//8.保存订单
		this.insertOrderBase(orderSaveVo);

	}


	/**
	 * 保存订单的配置快照信息
	 * @author afi
	 * @created 2016年4月1日
	 * @param orderSaveVo
	 */
	private void insertOrderConfigs(OrderSaveVo orderSaveVo){
		List<OrderConfigEntity> configs = orderSaveVo.getOrderConfigs();
		for(OrderConfigEntity configEntity: configs){
			orderConfigDao.insertOrderConfig(configEntity);
		}
	}


	/**
	 * 保存订单的活动信息
	 * @author afi
	 * @created 2016年4月1日
	 * @param orderSaveVo
	 */
	private void insertOrderActivitys(OrderSaveVo orderSaveVo){
		List<OrderActivityEntity> activitys = orderSaveVo.getOrderActivitys();
		for(OrderActivityEntity activityEntity: activitys){
			orderActivityDao.insertActivityRes(activityEntity);
		}
	}

	/**
	 * 订单长住人列表
	 * @author afi
	 * @created 2016年4月1日
	 * @param orderSaveVo
	 */
	private void insertOrderPrices(OrderSaveVo orderSaveVo){
		List<OrderSpecialPriceEntity> orderPrices = orderSaveVo.getOrderPrices();
		for(OrderSpecialPriceEntity priceEntity: orderPrices){
			orderSpecialPriceDao.insertOrderSpecialPrice(priceEntity);
		}
	}


	/**
	 * 校验当前的用户并处理过滤
	 * @author afi
	 * @created 2016年4月19日
	 * @param tenantFids
	 */
	public List<String> checkContact(List<String> tenantFids){
		if(Check.NuNCollection(tenantFids)){
			return null;
		}
		List<String> orderContacts = new ArrayList<>();
		Map<String,String> orderContactMap = new HashMap<>();
		for(String tentFid: tenantFids){
			if(orderContactMap.containsKey(tentFid)){
				continue;
			}else {
				orderContactMap.put(tentFid,tentFid);
			}
			if(usualContactDao.checkTenant(tentFid)){
				orderContacts.add(tentFid);
			}
		}
		return orderContacts;
	}

	/**
	 * 订单常主任列表
	 * @author afi
	 * @created 2016年4月1日
	 * @param orderSaveVo
	 */
	private void insertOrderContact(OrderSaveVo orderSaveVo){
		List<OrderContactEntity> orderContacts = orderSaveVo.getOrderContacts();
		for(OrderContactEntity orderContactEntity: orderContacts){
			orderContactDao.insertOrderContact(orderContactEntity);
		}
	}

	/**
	 * 锁定房源（只有实时订单的0元订单才锁房源）
	 * @author afi
	 * @created 2016年4月1日
	 * @param orderSaveVo
	 */
	private void insertHouseLock(OrderSaveVo orderSaveVo){
		List<HouseLockEntity> houseLocks = orderSaveVo.getHouseLocks();
		if (Check.NuNCollection(houseLocks)){
			//当前的房源锁信息为空 直接返回
			return;
		}
		for(HouseLockEntity houseLock:houseLocks){
			houseLockDao.insertHouseLock(houseLock);
		}
	}

	/**
	 * 保存订单房源快照
	 * @author afi
	 * @created 2016年4月1日
	 * @param orderSaveVo
	 */
	private void insertOrderHouse(OrderSaveVo orderSaveVo){
		if(orderHouseSnapshotDao.insertHouseSnapshotRes(orderSaveVo.getOrderHouse())==0){
			throw new BusinessException("insert nothing");
		}
	}

	/**
	 * 保存订单的金额信息
	 * @author afi
	 * @created 2016年4月8日
	 * @param orderSaveVo
	 */
	private void insertOrderMoney(OrderSaveVo orderSaveVo){
		if(orderMoneyDao.insertOrderMoney(orderSaveVo.getOrderMoney())==0){
			throw new BusinessException("insert nothing");
		}
	}

	/**
	 * 保存订单信息
	 * @author afi
	 * @created 2016年4月1日
	 * @param orderSaveVo
	 */
	private void insertOrderBase(OrderSaveVo orderSaveVo){
		//直接保存订单基础信息
		if(orderBaseDao.insertOrderBase(orderSaveVo.getOrder())==0){
			throw new BusinessException("insert nothing");
		}
	}




	/**
	 * 校验用户是否已经支付
	 * 支付状态
	 * @author liyingjie
	 * @created 2016年4月2日
	 *
	 * @param orderRequest
	 * @return
	 */
	@Deprecated
	public boolean checkIsHasPayMoney(OrderRequest orderRequest){
		/** 校验  请求参数存在 */
		if(Check.NuNObj(orderRequest)){
			LogUtil.error(LOGGER,"请求参数为空：orderRequest：{}", orderRequest);
			throw new BusinessException("请求参数为空");
		}

		/** 校验  订单编号是否存在  */
		if(Check.NuNStrStrict(orderRequest.getOrderSn())){
			LogUtil.error(LOGGER,"订单编号不存在：orderRequest：{}", orderRequest);
			throw new BusinessException("订单编号不存在");
		}

		/** 根据订单支付状态 查询是否已支付*/
		boolean hasPaid = this.checkByOrderPayStatus(orderRequest.getOrderSn());

		return hasPaid;
	}



	/**
	 * 根据订单状态  判断是否已经支付
	 * @author liyingjie
	 * @created 2016年4月3日
	 * @param orderSn
	 * @return
	 */
	public boolean checkByOrderPayStatus(String orderSn){
		boolean hasPaid = false;
		//根据订单orderSn获取订单
		OrderEntity oe = orderBaseDao.getOrderBaseByOrderSn(orderSn);
		if(Check.NuNObj(oe)){
			LogUtil.error(LOGGER,"订单号错误, orderSn:{}", orderSn);
			throw new BusinessException("订单号错误");
		}
		int pay_status = oe.getPayStatus();
		/** 根据支付状态  支付金额判断 是否已经支付*/
		if(pay_status == OrderPayStatusEnum.HAS_PAY.getPayStatus()){
			hasPaid = true;
		}
		return hasPaid;
	}

	/**
	 * 根据支付记录  判断是否已经支付
	 * @author liyingjie
	 * @created 2016年4月3日
	 * @param orderSn
	 * @return
	 */
	public boolean checkByOrderPayRecording(String orderSn){
		boolean hasPaid = false;
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderSn", orderSn);

		List<OrderPayEntity> opeList = orderPayDao.findOrderPayByCondiction(param);
		if(Check.NuNCollection(opeList)){
			LogUtil.error(LOGGER,"此订单号已经支付过, orderSn:{}, opeList:{}", orderSn, opeList);
			throw new BusinessException("此订单号已经支付过");
		}
		if(opeList.size() >= 1){
			hasPaid = true;
		}
		return hasPaid;
	}

	/**
	 * 取消订单状态
	 * 并且 生成打款计划
	 * @author afi
	 * @param orderInfo
	 * @param request
	 * @throws ParseException 
	 */
	public int cancelOrderAndRefund(CheckOutStrategy checkOutStrategy,OrderConfigVo config,OrderInfoVo orderInfo,CanclOrderRequest request) throws ParseException{
		if(Check.NuNObj(orderInfo)){
			LogUtil.info(LOGGER, "【取消订单】 失败，orderSN:{}", request.getOrderSn());
			throw new BusinessException("订单信息为空");
		}
		if(Check.NuNObj(request)){
			LogUtil.info(LOGGER, "【取消订单】 参数失败，par:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("参数错误");
		}
		LogUtil.info(LOGGER, "【取消订单】开始取消订单状态 并且 生成打款计划，orderSN:{}", request.getOrderSn());
		OrderStatusEnum orderStatus =  OrderStatusEnum.getOrderStatusByCode(orderInfo.getOrderStatus());
		OrderStatusEnum cancleStatus = orderStatus.getCancleStatus(orderInfo);
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(request.getOrderSn());
		orderEntity.setOrderStatus(cancleStatus.getOrderStatus());
		if (orderInfo.getNeedPay() == 0){
			//当前是0元订单，当前的结算状态为结算完成
			orderEntity.setAccountsStatus(OrderAccountsStatusEnum.FINISH.getAccountsStatus());
		}else {
			//当前的结算状态为结算中
			orderEntity.setAccountsStatus(OrderAccountsStatusEnum.ING.getAccountsStatus());
		}

		orderEntity.setRealEndTime(checkOutStrategy.getDealTime());
		if(orderInfo.getPayMoney() < orderInfo.getNeedPay()){
			LogUtil.info(LOGGER, "【取消订单】 支付金额 小于应付金额，payMoney:{}，needPay:{}", orderInfo.getPayMoney() < orderInfo.getNeedPay());
			throw new BusinessException(" 支付金额 小于应付金额");
		}
		Map<String,Integer> priceMap = this.getDayCutPricesMap(orderInfo,orderInfo.getDiscountMoney());
		//获取当前的房源的清洁费 清洁费不参与违约金
		int cleanMoney = FinanceMoneyUtil.getCleanMoney(orderInfo);
		//违约金
		Integer penaltyMoney =FinanceMoneyUtil.getPenaltyMoney(checkOutStrategy,orderInfo,priceMap);
		//剩余金额 = 支付金额 + 优惠券金额 + 活动金额 - 用户佣金 - 清洁费
		int lastMoney = orderInfo.getPayMoney() + orderInfo.getCouponMoney() + orderInfo.getActMoney() - orderInfo.getUserCommMoney() - orderInfo.getCleanMoney();
		int realPenaltyMoney = ValueUtil.getMin(lastMoney, penaltyMoney);
		//房租
		int rentalMoney = 0;
		//房客真实的佣金 取消订单租客的服务费全部扣除
		//        int realUserMoney = orderInfo.getUserCommMoney();

		int realUserMoney = 0;

		OrderMoneyEntity orderMoney = new OrderMoneyEntity();
		//重新计算优惠券金额
		this.sureCouponInfo(orderInfo,rentalMoney,realPenaltyMoney,realUserMoney,cleanMoney,orderMoney);
		//获取当前用户的消费情况
		int costUser = realPenaltyMoney + cleanMoney + realUserMoney;
		//真正退回给用户的  = 支付金额 + 优惠券金额 + 活动金额 - 用户实际消费的   （当前取消订单）
		int refundMoney = orderInfo.getPayMoney() + orderInfo.getCouponMoney() + orderInfo.getActMoney() - costUser;

		refundMoney = ValueUtil.getMin(refundMoney,orderInfo.getPayMoney());
		orderMoney.setOrderSn(request.getOrderSn());
		orderMoney.setPenaltyMoney(realPenaltyMoney);
		orderMoney.setRealMoney(realPenaltyMoney);
		orderMoney.setRentalMoney(rentalMoney);
		orderMoney.setRefundMoney(refundMoney);
		orderMoney.setRealUserMoney(realUserMoney);
		//设置当前订单的清洁费
		orderMoney.setCleanMoney(cleanMoney);
		//获取违约金的佣金
		Double commMoneyPercent = config.returnRateLandlord();
		Double commMoneyDou = BigDecimalUtil.mul(penaltyMoney, commMoneyPercent);
		//重新处理参加免佣金活动的金额
		this.sureLandCom(config,orderMoney,commMoneyDou.intValue());
		orderMoney.setDiscountMoney(0);
		//        orderMoney.setDepositMoney(0);
		virtualOrderBaseDao.updateOrderInfoAndStatus(orderInfo.getUserUid(), orderStatus.getOrderStatus(), orderEntity, orderMoney, null, null);
		//打给房东的违约金 和 清洁费
		this.dealPenaltyMoney2Land4Cancel(config, orderInfo, realPenaltyMoney + cleanMoney);
		//退给用户的金额 = refundMoney
		this.dealUserRefund4Cancel(orderInfo, refundMoney,realUserMoney);


		LogUtil.info(LOGGER, "取消订单 释放房源，orderSN:{}", request.getOrderSn());
		houseLockDao.delLockHouseByOrderSn(request.getOrderSn());
		//返回真实的违约金
		return realPenaltyMoney;
	}


	/**
	 * 将违约金打给房东
	 * @author afi
	 * @param orderEntity
	 * @param costUser
	 * @throws ParseException 
	 */
	private void dealPenaltyMoney2Land4Cancel(OrderConfigVo config,OrderInfoVo orderEntity,int costUser) throws ParseException{
		if(Check.NuNObjs(orderEntity)){
			return;
		}
		if(Check.NuNObj(config)){
			LogUtil.info(LOGGER, "【异常数据】 orerSn:",orderEntity.getOrderStatus());
			throw new BusinessException("异常数据");
		}
		if(costUser <= 0){
			return;
		}

		Double commMoneyPercent = config.returnRateLandlord();
		Double commMoneyDou = BigDecimalUtil.mul(costUser, commMoneyPercent);

		int commMoney = commMoneyDou.intValue();
		if (!Check.NuNStr(config.getLandCom())){
			//参加了免佣金的活动 佣金金额为0
			commMoney = 0;
		}
		int last = costUser - commMoney;
		FinancePayAndDetailVo financeVo = new FinancePayAndDetailVo();
		List<FinancePayVouchersDetailVo> detailVoList = new ArrayList<>();
		//如果累计金额大于0
		if(last > 0){
			FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
			detail.setFeeItemCode(FeeItemCodeEnum.CHECK.getCode());
			detail.setItemMoney(last);
			detailVoList.add(detail);
			financeVo.setFinancePayVouchersDetailList(detailVoList);
			financeVo.setOrderSn(orderEntity.getOrderSn());
			financeVo.setCityCode(orderEntity.getCityCode());
			financeVo.setPaySourceType(PaySourceTypeEnum.USER_SETTLEMENT.getCode());
			financeVo.setReceiveUid(orderEntity.getLandlordUid());
			financeVo.setReceiveType(UserTypeEnum.LANDLORD.getUserType());
			//ok
			financeVo.setPayType(UserTypeEnum.TENANT.getUserType());
			financeVo.setPayUid(orderEntity.getLandlordUid());
			financeVo.setTotalFee(last);
			financeVo.setGenerateFeeTime(new Date());
			//2017-12-29  协商取消和房客取消，给房东打款，将付款单执行时间设置为，第二天的12点=========开始
			String dayAfterDateStr = DateUtil.getDayAfterDate(new Date());
			dayAfterDateStr = dayAfterDateStr+ " 12:00:00";			
			Date dayAfterDate = DateUtil.parseDate(dayAfterDateStr, "yyyy-MM-dd HH:mm:ss");
			LogUtil.info(LOGGER, "dealPenaltyMoney2Land4Cancel（协商取消和房客取消调用）,dayAfterDateStr={},dayAfterDate={}", dayAfterDateStr, JsonEntityTransform.Object2Json(dayAfterDate));
			//2017-12-29  协商取消和房客取消，给房东打款，将付款单执行时间设置为，第二天的12点=========结束
			financeVo.setRunTime(dayAfterDate);
			financeVo.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
			financeVo.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
			virtualPayVouchersDao.saveFinancePayVouchers(financeVo);
		}
		if(commMoney > 0){
			FinanceIncomeEntity incomeEntity = new FinanceIncomeEntity();
			incomeEntity.setIncomeSourceType(IncomeSourceTypeEnum.USER_SETTLEMENT.getCode());
			incomeEntity.setIncomeType(IncomeTypeEnum.USER_PUNISH_COMMISSION.getCode());
			incomeEntity.setOrderSn(orderEntity.getOrderSn());
			incomeEntity.setCityCode(orderEntity.getCityCode());
			incomeEntity.setPayUid(orderEntity.getLandlordUid());
			incomeEntity.setPayType(UserTypeEnum.LANDLORD.getUserType());
			incomeEntity.setTotalFee(commMoney);
			financeIncomeDao.insertFinanceIncome(incomeEntity);
		}

	}


	/**
	 * 取消订单之后给用户生成打款单
	 * @author afi
	 * @param orderEntity
	 * @param refundMoney
	 * @throws ParseException 
	 */
	private void dealUserRefund4Cancel(OrderInfoVo orderEntity,int refundMoney,int incomeMoney) throws ParseException{
		FinancePayAndDetailVo financeVo = new FinancePayAndDetailVo();
		//设置订单的退款金额
		List<FinancePayVouchersDetailVo> detailVoList = new ArrayList<>();
		//如果累计金额大于0
		if(refundMoney > 0){
			FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
			detail.setFeeItemCode(FeeItemCodeEnum.CHECK.getCode());
			detail.setItemMoney(refundMoney);
			detailVoList.add(detail);
			financeVo.setFinancePayVouchersDetailList(detailVoList);
			financeVo.setOrderSn(orderEntity.getOrderSn());
			financeVo.setCityCode(orderEntity.getCityCode());
			financeVo.setPaySourceType(PaySourceTypeEnum.USER_SETTLEMENT.getCode());
			financeVo.setReceiveUid(orderEntity.getUserUid());
			financeVo.setReceiveType(UserTypeEnum.TENANT.getUserType());
			//ok
			financeVo.setPayType(UserTypeEnum.LANDLORD.getUserType());
			financeVo.setPayUid(orderEntity.getLandlordUid());
			financeVo.setTotalFee(refundMoney);
			financeVo.setGenerateFeeTime(new Date());
			
			//2017-12-29  协商取消和房客取消，给房客打款，将付款单执行时间设置为，第二天的12点=========开始
			String dayAfterDateStr = DateUtil.getDayAfterDate(new Date());
			dayAfterDateStr = dayAfterDateStr+ " 12:00:00";			
			Date dayAfterDate = DateUtil.parseDate(dayAfterDateStr, "yyyy-MM-dd HH:mm:ss");
			LogUtil.info(LOGGER, "dealUserRefund4Cancel（协商取消和房客取消调用）,dayAfterDateStr={},dayAfterDate={}", dayAfterDateStr, JsonEntityTransform.Object2Json(dayAfterDate));
			//2017-12-29  协商取消和房客取消，给房客打款，将付款单执行时间设置为，第二天的12点=========结束
			
			financeVo.setRunTime(dayAfterDate);
			financeVo.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
			financeVo.setPaymentType(OrderPaymentTypeEnum.YLFH.getCode());
			virtualPayVouchersDao.saveFinancePayVouchers(financeVo);
		}
		if(incomeMoney > 0){
			FinanceIncomeEntity incomeEntity = new FinanceIncomeEntity();
			incomeEntity.setIncomeSourceType(IncomeSourceTypeEnum.USER_SETTLEMENT.getCode());
			incomeEntity.setIncomeType(IncomeTypeEnum.USER_RENT_COMMISSION.getCode());
			incomeEntity.setOrderSn(orderEntity.getOrderSn());
			incomeEntity.setCityCode(orderEntity.getCityCode());
			incomeEntity.setPayUid(orderEntity.getLandlordUid());
			incomeEntity.setPayType(UserTypeEnum.TENANT.getUserType());
			incomeEntity.setTotalFee(incomeMoney);
			financeIncomeDao.insertFinanceIncome(incomeEntity);
		}

	}

	/**
	 * 获取违约金的价格
	 * @author
	 * @param orderSn
	 * @param startTime
	 * @return
	 */
	public Integer getFristDayPrice(String orderSn,Date startTime){
		OrderHouseSnapshotEntity houseSnapshotEntity = orderHouseSnapshotDao.findHouseSnapshotByOrderSn(orderSn);
		if(houseSnapshotEntity == null){
			LogUtil.error(LOGGER,"houseSnapshotEntity为空, orderSn:{}, startTime:{}", orderSn,startTime);
			throw  new BusinessException("houseSnapshotEntity为空");
		}
		Map<String,Integer> priceMap = orderSpecialPriceDao.getOrderSpecialPriceMapByOrderSn(orderSn,houseSnapshotEntity.getPrice());
		if(priceMap.containsKey(DateUtil.dateFormat(startTime))){
			return priceMap.get(DateUtil.dateFormat(startTime));
		}else {
			return houseSnapshotEntity.getPrice();
		}
	}

	/**
	 * 获取下一天的价格
	 * @author
	 * @param orderSn
	 * @param startTime
	 * @return
	 */
	public Integer getTomorrowDayPrice(String orderSn,Date startTime){
		OrderHouseSnapshotEntity houseSnapshotEntity = orderHouseSnapshotDao.findHouseSnapshotByOrderSn(orderSn);
		if(houseSnapshotEntity == null){
			LogUtil.error(LOGGER,"houseSnapshotEntity为空, orderSn:{}, startTime:{}", orderSn,startTime);
			throw  new BusinessException("houseSnapshotEntity为空");
		}
		Map<String,Integer> priceMap = orderSpecialPriceDao.getOrderSpecialPriceMapByOrderSn(orderSn,houseSnapshotEntity.getPrice());
		if(priceMap.containsKey(DateUtil.dateFormat(startTime))){
			return priceMap.get(DateUtil.dateFormat(startTime));
		}else {

			return houseSnapshotEntity.getPrice();
		}
	}



	/**
	 * 获取订单的房源信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderHouseSnapshotEntity findHouseSnapshotByOrderSn(String orderSn){
		return orderHouseSnapshotDao.findHouseSnapshotByOrderSn(orderSn);
	}

	/**
	 * 结算订单 提前退房结算
	 * @author afi
	 * @param config
	 * @param orderInfoVo
	 */
	public void updateAndDealCheckOutPre(CheckOutStrategy checkOutStrategy,OrderConfigVo config,OrderInfoVo orderInfoVo){

		if(Check.NuNObj(orderInfoVo)||Check.NuNStr(orderInfoVo.getOrderSn())){
			LogUtil.error(LOGGER,"订单信息错误, orderInfoVo:{}", orderInfoVo);
			throw new BusinessException("订单信息错误");
		}
		//1.取消用户和房东的无效佣金
		this.cancelIncome(orderInfoVo);
		//2.取消无效的打款计算
		this.cancelFinace(orderInfoVo);
		//获取当前的价格列表 折扣之后的
		Map<String,Integer> priceMap = this.getDayCutPricesMap(orderInfoVo,orderInfoVo.getDiscountMoney());
		//获取当前订单在当前时间的房租
		int rentalMoney = FinanceMoneyUtil.getRealRentalMoney(config, orderInfoVo, checkOutStrategy.getDealTime(), priceMap);
		LogUtil.info(LOGGER,"【提前退房】重新计算当前的房租，当前的房租金额为：{}",rentalMoney);

		//设置当前的真实的房租为违约金的计算提供依赖
		checkOutStrategy.setRealRentalMoney(rentalMoney);
		// 理论上的违约金
		int planPenaltyMoney = FinanceMoneyUtil.getPenaltyMoney(checkOutStrategy, orderInfoVo, priceMap);

		//获取当前订单的清洁费
		int cleanMoney = FinanceMoneyUtil.getCleanMoney(orderInfoVo);
		//结算订单  主要确定消费的实际房租和违约金 以及佣金
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(orderInfoVo.getOrderSn());//设置订单编号
		orderEntity.setOrderStatus(OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus()); //设置状态为退租中
		orderEntity.setRealEndTime(new Date());
		OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
		//当前的折扣金额
		int discountMoney = FinanceMoneyUtil.getDiscountMoneyBetweenDays(config, orderInfoVo, orderInfoVo.getStartTime(),checkOutStrategy.getDealTime(), priceMap);
		moneyEntity.setDiscountMoney(discountMoney);
		moneyEntity.setOrderSn(orderInfoVo.getOrderSn());
		//当前的房租为 真实的房租+折扣金额
		moneyEntity.setRentalMoney(rentalMoney + discountMoney);

		//剩余金额公式：支付金额  + 优惠券 + 活动金额 - 租客支付佣金金额  - 实际发生房租金额 - 清洁费
		int lastMoney = orderInfoVo.getPayMoney() + orderInfoVo.getCouponMoney() + orderInfoVo.getActMoney() - orderInfoVo.getUserCommMoney()  - rentalMoney - cleanMoney ;
		LogUtil.info(LOGGER,"【提前退房】剩余金额退款公式： 支付金额:{} + 优惠券:{} + 活动金额:{} - 租客支付佣金金额:{}   - 实际发生房租金额:{} - 清洁费：{}",orderInfoVo.getPayMoney(), orderInfoVo.getCouponMoney(),orderInfoVo.getActMoneyAll(),orderInfoVo.getUserCommMoney(),rentalMoney,cleanMoney);
		if(lastMoney < 0){
			LogUtil.error(LOGGER,"支付金额错误, orderInfoVo:{}", orderInfoVo);
			throw new BusinessException("支付金额错误");
		}
		//3.设置违约金额 需要比对当前的剩余金额是否不够支付违约金
		int realPenaltyMoney = ValueUtil.getMin(planPenaltyMoney, lastMoney);
		moneyEntity.setPenaltyMoney(realPenaltyMoney);
		//4.重新计算当前实际发生的佣金
		//除了房租之外的收取佣金的情况 = 真实的违约金 + 清洁费
		int extMoney = realPenaltyMoney + cleanMoney;
		//6.获取真实的房东的佣金
		int lanCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config, orderInfoVo,rentalMoney,extMoney ,UserTypeEnum.LANDLORD);
		//7.获取真实的用户的佣金
		int userCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config, orderInfoVo,rentalMoney,null,UserTypeEnum.TENANT);
		//8.重新计算优惠券金额
		this.sureCouponInfo(orderInfoVo, rentalMoney, realPenaltyMoney, userCommMoney,cleanMoney,moneyEntity);
		//9.重新处理参加免佣金活动的金额
		this.sureLandCom(config,moneyEntity,lanCommMoney);
		//10.重新处理参加夹心价格的活动金额
		this.updateSandwichOrderAc(orderEntity.getOrderSn(), orderEntity.getRealEndTime());
		
		moneyEntity.setRealUserMoney(userCommMoney);
		//11.设置当前的退款金额
		//真正退回给用户的 公式：支付金额 + 优惠券 + 活动金额 - 真实的房租 - 用户的真实的佣金 -违约金 - 清洁费
		int refundMoneyOrg = orderInfoVo.getPayMoney() + orderInfoVo.getCouponMoney() + orderInfoVo.getActMoney() - rentalMoney - userCommMoney - realPenaltyMoney -cleanMoney;
		int refundMoney = ValueUtil.getMin(refundMoneyOrg,orderInfoVo.getPayMoney());
		LogUtil.info(LOGGER,"【提前退房】退款金额公式：支付金额：{} + 优惠券:{} + 活动金额:{} - 真实的房租:{} - 用户的真实的佣金:{} - 违约金：{} - 清洁费：{}",orderInfoVo.getPayMoney() , orderInfoVo.getCouponMoney() ,orderInfoVo.getActMoney(), rentalMoney , userCommMoney,realPenaltyMoney,cleanMoney);
		LogUtil.info(LOGGER,"【提前退房】实际退款金额是 退款金额：{}和支付金额：{}的最小值{}",refundMoneyOrg,orderInfoVo.getPayMoney(),refundMoney);
		moneyEntity.setRefundMoney(refundMoney);
		//12.直接保存订单的信息
		virtualOrderBaseDao.updateOrderInfoAndStatus(orderInfoVo.getUserUid(), orderInfoVo.getOrderStatus(), orderEntity, moneyEntity, null, null);
		//13.释放多余的房源锁定
		this.unLockHouseNow(orderInfoVo);
	}

	/**
	 * 
	 * 如果夹心价格的优惠金额改变 则更新
	 *
	 * @author yd
	 * @created 2017年3月9日 上午9:44:02
	 *
	 * @param orderSn
	 * @param realEndTime
	 */
	public void updateSandwichOrderAc(String orderSn,Date realEndTime){
		
		
		OrderHouseSnapshotEntity houseSnapshotEntity = orderHouseSnapshotDao.findHouseSnapshotByOrderSn(orderSn);
		if(houseSnapshotEntity == null){
			LogUtil.error(LOGGER,"订单 houseSnapshotEntity is null");
			throw  new BusinessException("houseSnapshotEntity is null");
		}
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderSn", orderSn);
		paramMap.put("acFid", ProductRulesEnum.ProductRulesEnum020.getValue());
		List<OrderActivityEntity> listOrderAc = this.orderActivityDao.findActivityByCondiction(paramMap);
		
		if(Check.NuNCollection(listOrderAc)||listOrderAc.size()!=1){
			LogUtil.info(LOGGER, "当前订单无夹心活动orderSn={},realEndTime={},acFid={}", orderSn,realEndTime,ProductRulesEnum.ProductRulesEnum020.getValue());
			return ;
		}
		int acMoney = this.orderSpecialPriceDao.getOrderSandwichDisCountPriceMapByOrderSn(orderSn, houseSnapshotEntity.getPrice(),realEndTime);
		if(acMoney>0){
			int i = this.orderActivityDao.updateAcMoney(orderSn, ProductRulesEnum.ProductRulesEnum020.getValue(), acMoney);
			if(i!=1){
				LogUtil.error(LOGGER, "提前退房-重新订单夹心价格活动】：订单夹心价格活动更新失败orderSn={},realEndTime={},acFid={},acMoney={}", orderSn,realEndTime,ProductRulesEnum.ProductRulesEnum020.getValue(),acMoney);
				throw new BusinessException("【提前退房-重新订单夹心价格活动】：订单夹心价格活动更新失败");
			}
			
		}
	}

	
	
	/**
	 * 提前退房改变免房东的佣金的金额
	 * @author afi
	 * @param config
	 * @param moneyEntity
	 * @param lanCommMoney
	 */
	private void sureLandCom(OrderConfigVo config,OrderMoneyEntity moneyEntity,int lanCommMoney){
		String landCom = config.getLandCom();
		if (Check.NuNStr(landCom)){
			//没有参加免佣金的活动
			moneyEntity.setRealLanMoney(lanCommMoney);
		}else {
			//参加了免佣金的活动直接改变免佣金的活动金额
			moneyEntity.setRealLanMoney(0);
			//改变当前的活动的金额
			orderActivityDao.updateAcMoney(moneyEntity.getOrderSn(),config.getLandCom(),lanCommMoney);
		}

	}

	/**
	 * 提前退房释放占有的房源
	 * @author afi
	 * @param orderInfoVo
	 */
	private void unLockHouseNow(OrderInfoVo orderInfoVo){
		Date limitDate = null;
		Date now = new Date();
		Date limitDay = DateSplitUtil.connectDate(now, orderInfoVo.getCheckOutTime());
		if(now.before(limitDay)){
			//还未过退房时间
			limitDate = DateSplitUtil.getYesterday(now);
		}else {
			//已经超过退房时间
			limitDate = now;
		}
		houseLockDao.delLockHouseByOrderSn(orderInfoVo.getOrderSn(),limitDate);

	}

	/**
	 * 获取截止时间 未退房
	 * @param orderInfoVo
	 * @return
	 */
	private Date getLimitDate(OrderInfoVo orderInfoVo){
		Date limitDate = null;
		Date now = new Date();
		Date limitDay = DateSplitUtil.connectDate(now, orderInfoVo.getCheckOutTime());
		if(now.before(limitDay)){
			//还未过退房时间
			limitDate = DateSplitUtil.getYesterday(now);
		}else {
			//已经超过退房时间
			limitDate = now;
		}
		return limitDate;
	}


	/**
	 * modify by jixd on 2017.06.07
	 * 确定优惠券与活动 消费的实际情况
	 * 1.先消费优惠券 -> 消费首单立减活动
	 * 2. 计算优惠券与实际消费关系，如果实际消费金额小于优惠券，重新计算优惠券使用金额
	 * 3.计算优惠券的收款单 更改实际金额
	 * 4.计算优惠券剩余金额 生成付款单 虚拟消费冻结金
	 * 5.更新订单金额表和活动表中实际消费金额
	 *
	 *  消费完成之后记录实际消费金额
	 * @author afi
	 * @param orderInfoVo 订单信息
	 * @param rentalMoney 实际租金
	 * @param realPenaltyMoney 实际违约金
	 * @param userCommMoney 用户服务费
	 * @param cleanMoney 清洁费
	 * @param orderMoney  订单金额 设置优惠券和活动实际消费金额
	 */
	private void sureCouponInfo(OrderInfoVo orderInfoVo,int rentalMoney,int realPenaltyMoney,int userCommMoney,int cleanMoney,OrderMoneyEntity orderMoney){
		if(Check.NuNObj(orderInfoVo)){
			return;
		}
		//查询首单立减活动
		String orderSn = orderInfoVo.getOrderSn();

		LogUtil.info(LOGGER,"开始处理优惠券结算逻辑，orderSn={}",orderSn);

		OrderActivityInfoVo orderActivityFirst = null;
		OrderActivityInfoVo orderActivityCoupon = null;
		int firstOrderMoney = 0;
		if (orderInfoVo.getActMoney() > 0 || orderInfoVo.getCouponMoney() >0){
			List<Integer> actTypeList = Arrays.asList(OrderAcTypeEnum.FIRST_ORDER_REDUC.getCode(),OrderAcTypeEnum.COUPON.getCode());
			List<OrderActivityInfoVo> orderActivityInfoVos = orderActivityDao.listOrderActByOrderSnAndType(orderSn, actTypeList);
			if (Check.NuNCollection(orderActivityInfoVos)){
				throw new BusinessException("订单参加活动为空！orderSn:"+orderSn);
			}
			for (OrderActivityInfoVo infoVo : orderActivityInfoVos){
				if (infoVo.getAcType() == OrderAcTypeEnum.FIRST_ORDER_REDUC.getCode()){
					orderActivityFirst = infoVo;
					firstOrderMoney = infoVo.getAcMoney();
				}
				if (infoVo.getAcType() == OrderAcTypeEnum.COUPON.getCode()){
					orderActivityCoupon = infoVo;
				}
			}
		}

		//所有的消费情况 =  实际租金+实际违约金+用户服务费+清洁费
		int costAllMoney = rentalMoney + realPenaltyMoney  + userCommMoney + cleanMoney;
		//优惠券金额
		int couponMoney = orderInfoVo.getCouponMoney();

		//优惠券先消费
		if(couponMoney != 0 && costAllMoney < couponMoney){
			LogUtil.info(LOGGER,"先消费优惠券：房租:{},违约金:{},佣金:{},清洁费：{},优惠券金额:{},活动金额:{},共计:{}",rentalMoney , realPenaltyMoney , userCommMoney,cleanMoney,couponMoney,firstOrderMoney,costAllMoney);
			//更改收款单状态
			if (costAllMoney == 0){
				financePaymentVouchersDao.deletePaymentByOrderSnAndPayType(orderSn,OrderPayTypeEnum.coupon_pay.getPayType());
			}else{
				financePaymentVouchersDao.changeCouponMoney(orderSn, costAllMoney);
			}
			//更新活动实际使用金额
			orderActivityDao.updateAcMoney(orderSn,orderActivityCoupon.getAcFid(),costAllMoney);
			//如果不为0 则 剩余活动不会被消费，直接作废
			if (orderInfoVo.getActMoney() > 0){
				financePaymentVouchersDao.deletePaymentByOrderSnAndPayType(orderSn,OrderPayTypeEnum.act_pay.getPayType());
				//更新实际活动消费金额为 0
				orderActivityDao.updateAcMoney(orderSn,orderActivityFirst.getAcFid(),0);
			}
			//当前优惠券的未使用满 需要重新减去用户余额
			int last = couponMoney - costAllMoney;
			LogUtil.info(LOGGER,"需要消费冻结金：原来的金额：{}消费金额：{}",orderInfoVo.getCouponMoney(),last);
			//生成清空优惠券付款单
			createClearPayVouchers(orderInfoVo, last,PaySourceTypeEnum.CLEAR_COUPON.getCode());
			if (orderInfoVo.getActMoney() > 0){
				//生成清空活动付款单
				createClearPayVouchers(orderInfoVo, orderInfoVo.getActMoney(),PaySourceTypeEnum.CLEAR_ACT.getCode());
			}
			//设置实际使用金额
			orderMoney.setCouponMoney(costAllMoney);
			orderMoney.setActMoney(0);
			return;
		}

		//消费活动
		if (firstOrderMoney !=0 && (costAllMoney < couponMoney + firstOrderMoney)){
			LogUtil.info(LOGGER,"处理首单立减活动：房租：{},违约金：{},佣金：{}: 清洁费：{},优惠券金额:{},活动金额:{},共计：{},",rentalMoney , realPenaltyMoney , userCommMoney,cleanMoney,couponMoney,firstOrderMoney,costAllMoney);
			int use = costAllMoney - couponMoney;
			int last = firstOrderMoney - use;
			//消费活动金额
			orderActivityDao.updateAcMoney(orderSn,orderActivityFirst.getAcFid(),use);
			//修改活动金额
			financePaymentVouchersDao.updateActPaymentMoneyByOrderSn(orderSn,use);
			LogUtil.info(LOGGER,"需要消费未使用完的冻结金：原来的金额：{}消费金额：{}",firstOrderMoney + couponMoney,last);
			createClearPayVouchers(orderInfoVo,last,PaySourceTypeEnum.CLEAR_ACT.getCode());
			//更改orderMoney表中活动实际使用金额
			orderMoney.setActMoney(use);
		}
	}
	/**
	 * 生成消费冻结金的付款单 虚拟消费
	 * @author jixd
	 * @created 2017年06月07日 11:30:30
	 * @param
	 * @return
	 */
	private void createClearPayVouchers(OrderInfoVo orderInfoVo, int last,int paySourceType) {
		FinancePayAndDetailVo financeVo = new FinancePayAndDetailVo();
		List<FinancePayVouchersDetailVo> detailVoList = new ArrayList<>();
		FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
		detail.setFeeItemCode(FeeItemCodeEnum.CHECK.getCode());
		detail.setItemMoney(last);
		detailVoList.add(detail);
		financeVo.setFinancePayVouchersDetailList(detailVoList);
		financeVo.setOrderSn(orderInfoVo.getOrderSn());
		financeVo.setCityCode(orderInfoVo.getCityCode());
		financeVo.setPaySourceType(paySourceType);
		financeVo.setPayUid(orderInfoVo.getLandlordUid());
		financeVo.setPayType(UserTypeEnum.LANDLORD.getUserType());
		financeVo.setTotalFee(last);
		financeVo.setGenerateFeeTime(new Date());
		financeVo.setRunTime(new Date());
		financeVo.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
		financeVo.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
		virtualPayVouchersDao.saveFinancePayVouchers(financeVo);
	}

	/**
	 * 获取订单的价格列表
	 * @param order
	 * @return
	 */
	public Map<String,Integer>  getDayCutPricesMap(OrderEntity order,int discountMoney){
		OrderHouseSnapshotEntity houseSnapshotEntity = orderHouseSnapshotDao.findHouseSnapshotByOrderSn(order.getOrderSn());
		if(houseSnapshotEntity == null){
			LogUtil.error(LOGGER,"订单 houseSnapshotEntity is null");
			throw  new BusinessException("houseSnapshotEntity is null");
		}
		Double cut = null;
		//获取当前的房源的折扣
		OrderConfigEntity configEntity = orderConfigDao.getOrderConfigByOrderSnAndCode(order.getOrderSn(), ProductRulesEnum.ProductRulesEnum0019.getValue());
		if (!Check.NuNObj(configEntity)){
			cut = BigDecimalUtil.div(ValueUtil.getdoubleValue(configEntity.getConfigValue()),100);
		}
		Map<String,Integer> priceMap = orderSpecialPriceDao.getOrderSpecialPriceMapByOrderSn(order.getOrderSn(),houseSnapshotEntity.getPrice());
		priceMap.put("discountMoney",discountMoney);
		Map<String,Integer> cutPriceMap = FinanceMoneyUtil.getDayCutPricesMap(order.getStartTime(),order.getEndTime(),houseSnapshotEntity.getPrice(),cut,priceMap);
		return cutPriceMap;
	}


	/**
	 * 获取订单的价格列表
	 * @param order
	 * @return
	 */
	public List<OrderDayPriceVo>  getDayPrices(OrderEntity order){
		OrderHouseSnapshotEntity houseSnapshotEntity = orderHouseSnapshotDao.findHouseSnapshotByOrderSn(order.getOrderSn());
		OrderMoneyEntity orderMoneyEntity = orderMoneyDao.getOrderMoneyByOrderSn(order.getOrderSn());
		int discountMoney = orderMoneyEntity.getDiscountMoney();
		if(houseSnapshotEntity == null){
			LogUtil.error(LOGGER,"houseSnapshotEntity is null");
			throw  new BusinessException("houseSnapshotEntity is null");
		}
		try{
			Map<String,Integer> priceMap = this.getDayCutPricesMap(order,discountMoney);
			List<OrderDayPriceVo> dayPrice = new ArrayList<>();
			List<Date> dayList = DateSplitUtil.dateSplit(order.getStartTime(), order.getEndTime());
			for(Date date: dayList){
				OrderDayPriceVo dayPriceVo = new OrderDayPriceVo();
				dayPriceVo.setPriceDate(DateUtil.dateFormat(date));
				if(priceMap.containsKey(DateUtil.dateFormat(date))){
					dayPriceVo.setPriceValue(priceMap.get(DateUtil.dateFormat(date)));
				}else {
					dayPriceVo.setPriceValue(houseSnapshotEntity.getPrice());
				}
				dayPrice.add(dayPriceVo);
			}
			return dayPrice;
		}catch (Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			throw new BusinessException(e);
		}
	}





	/**
	 * 取消无效的付款计算
	 * @author afi
	 * @param orderInfoVo
	 */
	private void cancelFinace(OrderInfoVo orderInfoVo){
		//处理用户定时任务打款计算
		this.dealCancelInvalidFinace(orderInfoVo);
	}


	/**
	 * 取消无效的用户和房东的佣金
	 * @author afi
	 * @param orderInfoVo
	 */
	private void cancelIncome(OrderInfoVo orderInfoVo){
		try {
			LogUtil.info(LOGGER, "【清理佣金】 开始清理无效的佣金  orerSn:{}",orderInfoVo.getOrderSn());
			if(orderInfoVo.getUserCommMoney() == 0 && orderInfoVo.getLanCommMoney() == 0){
				LogUtil.info(LOGGER,"【清理佣金】佣金为0，直接返回");
				return;
			}
			List<FinanceIncomeEntity> financeIncomeEntityList = financeIncomeDao.getIncomeListByOrderSn(orderInfoVo.getOrderSn());
			if(Check.NuNCollection(financeIncomeEntityList)){
				LogUtil.info(LOGGER,"【清理佣金】当前还没有收入直接返回");
				return;
			}
			List<FinanceIncomeEntity> userList = new ArrayList<>();
			List<FinanceIncomeEntity> landList = new ArrayList<>();
			for(FinanceIncomeEntity income: financeIncomeEntityList){
				if(income.getIncomeType() == IncomeTypeEnum.USER_RENT_COMMISSION.getCode()){
					userList.add(income);
				}else if(income.getIncomeType() == IncomeTypeEnum.LANDLORD_RENT_COMMISSION.getCode()){
					landList.add(income);
				}
			}
			if(orderInfoVo.getUserCommMoney() > 0){
				//处理用户收入表信息
				this.dealCancelInvalidIncome(userList, orderInfoVo);
			}
			if(orderInfoVo.getLanCommMoney() > 0){
				//处理房东收入表信息
				this.dealCancelInvalidIncome(landList, orderInfoVo);
			}
		}catch (Exception e){
			LogUtil.info(LOGGER, "【清理佣金】 开始清理无效的佣金  orerSn:{}",orderInfoVo.getOrderSn());
			throw new BusinessException(e);
		}
	}



	/**
	 * 取消清洁费佣金
	 * @author lishaochuan
	 * @create 2017/1/6 9:55
	 * @param 
	 * @return 
	 */
	private void cancelCleanIncome(OrderInfoVo orderInfoVo){
		try {
			LogUtil.info(LOGGER, "【清理清洁费佣金】 开始清理清洁费的佣金  orerSn:{}", orderInfoVo.getOrderSn());
			List<FinanceIncomeEntity> financeIncomeEntityList = financeIncomeDao.getCleanIncomeListByOrderSn(orderInfoVo.getOrderSn());
			List<FinanceIncomeEntity> cancelList = new ArrayList<>();
			for (FinanceIncomeEntity financeIncomeEntity : financeIncomeEntityList) {
				if(financeIncomeEntity.getIncomeStatus() == IncomeStatusEnum.NO.getCode()){
					cancelList.add(financeIncomeEntity);
				}
			}
			if(Check.NuNCollection(cancelList)){
				LogUtil.info(LOGGER,"【清理清洁费佣金】当前还没有清洁费的收入直接返回");
				return;
			}
			this.dealCancelInvalidIncome(cancelList, orderInfoVo);
		}catch (Exception e){
			LogUtil.info(LOGGER, "【清理清洁费佣金】 e:{}", e);
			throw new BusinessException(e);
		}
	}


	/**
	 * 取消清洁费付款单
	 * @author lishaochuan
	 * @create 2017/1/6 9:55
	 * @param 
	 * @return 
	 */
	private void cancelCleanFinace(OrderInfoVo orderInfoVo){
		LogUtil.info(LOGGER,"【清理清洁费打款计划表】开始清理清洁费的打款计划");
		List<FinancePayVouchersEntity> financeList = financePayVouchersDao.findCleanByOrderSn(orderInfoVo.getOrderSn());
		if(Check.NuNCollection(financeList)) {
			LogUtil.info(LOGGER, "【清理清洁费打款计划表】当前还没有清洁费打款计划直接返回");
			return;
		}
		try{
			List<String> pvList = new ArrayList<>();
			for(FinancePayVouchersEntity finance: financeList){
				if(finance.getPaymentStatus() == PaymentStatusEnum.UN_PAY.getCode()){
					pvList.add(finance.getPvSn());
				}
			}
			if(Check.NuNCollection(pvList)){
				LogUtil.info(LOGGER,"【清理清洁费打款计划表】当前还没有清洁费的付款单直接返回");
				return;
			}
			//删除无效的打款计算
			LogUtil.info(LOGGER, "【清理清洁费打款计划表】 需要清理的条数为:{} orderSn:{}",pvList.size(),orderInfoVo.getOrderSn());
			financePayVouchersDao.deletePayVouchersByPvSnList(pvList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "【清理清洁费打款计划表】 清理打款计划表异常 orderInfoVo:{},e:{}",JsonEntityTransform.Object2Json(orderInfoVo),e);
			throw new BusinessException(e);
		}
	}


	/**
	 * 处理未生效的收入
	 * @author afi
	 * @param incomeList
	 */
	private void dealCancelInvalidIncome(List<FinanceIncomeEntity> incomeList,OrderInfoVo orderInfoVo)throws Exception{
		if(Check.NuNObjs(incomeList, incomeList)){
			return;
		}
		LogUtil.info(LOGGER, "【退房清理收入表】 开始清理无效的收入表  orerSn:{}",orderInfoVo.getOrderSn());
		if(orderInfoVo.getCheckType() == null){
			LogUtil.info(LOGGER, "【退房清理收入表】【异常数据】 orerSn:",orderInfoVo.getOrderStatus());
			throw new BusinessException("checkType为空");
		}
		List<String> incomeSnList = new ArrayList<>();
		int total = 0;
		int i = 0;
		for(FinanceIncomeEntity income: incomeList){
			if(i == 0 && orderInfoVo.getCheckType() == CheckTypeEnum.DAY.getCode() && !DateSplitUtil.lastTime.equals(orderInfoVo.getCheckOutTime())){
				total += income.getTotalFee();
			}else{
				//这里的设计很巧妙，按照订单和按照天的都走这个方法
				//有效截止时间
				Date endTime = DateUtil.parseDate(DateUtil.dateFormat(income.getGenerateFeeTime()) + " " + orderInfoVo.getCheckOutTime(), "yyyy-MM-dd HH:mm:ss");
				if(new Date().before(endTime)){
					//当前时间在退房之前
					incomeSnList.add(income.getIncomeSn());
				}else {
					//当前时间在退房之后,今天要算天
					total += income.getTotalFee();
				}
			}
			i++;
		}
		if(!Check.NuNCollection(incomeSnList)){
			//删除无效的打款计算
			financeIncomeDao.deleteIncomeByIncomeSnList(incomeSnList);
		}
		LogUtil.info(LOGGER, "【退房清理收入表】当前所有条数：{}，取消了无效的条数{}，有效金额{}",incomeList.size(),incomeSnList.size(),total);
	}




	/**
	 * 处理打款计划表
	 * @author afi
	 * @param orderInfoVo
	 */
	private void dealCancelInvalidFinace(OrderInfoVo orderInfoVo){
		LogUtil.info(LOGGER,"【清理打款计划表】开始清理打款计划表。。。。");
		List<FinancePayVouchersEntity> financeList = financePayVouchersDao.findEffectiveByOrderSn(orderInfoVo.getOrderSn());
		if(Check.NuNCollection(financeList)) {
			LogUtil.info(LOGGER, "【清理打款计划表】当前还没有打款计划直接返回");
			return;
		}
		try{
			if(orderInfoVo.getCheckType() == null){
				LogUtil.info(LOGGER, "【清理打款计划表】【异常数据】 orerSn:{}",orderInfoVo.getOrderSn());
				throw new BusinessException("checkType is null");
			}
			List<String> pvList = new ArrayList<>();
			int i = 0;
			for(FinancePayVouchersEntity finance: financeList){
				if(i == 0 && orderInfoVo.getCheckType() == CheckTypeEnum.DAY.getCode() && !DateSplitUtil.lastTime.equals(orderInfoVo.getCheckOutTime())){
					//
				}else{
					//这里的设计很巧妙，按照订单和按照天的都走这个方法
					//有效截止时间
					Date endTime = DateUtil.parseDate(DateUtil.dateFormat(finance.getGenerateFeeTime()) + " " + orderInfoVo.getCheckOutTime(), "yyyy-MM-dd HH:mm:ss");
					if(new Date().before(endTime)){
						//当前时间在退房之前
						pvList.add(finance.getPvSn());
					}
				}
				i++;
			}
			//删除无效的打款计算
			if(!Check.NuNCollection(pvList)){
				LogUtil.info(LOGGER, "【清理打款计划表】 需要清理的条数为:{} orerSn:{}",pvList.size(),orderInfoVo.getOrderSn());
				financePayVouchersDao.deletePayVouchersByPvSnList(pvList);
			}else {
				LogUtil.info(LOGGER, "【清理打款计划表】 当前所有的打款计划都是有效的 orerSn:",orderInfoVo.getOrderStatus());
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "【清理打款计划表】 清理打款计划表异常 orderInfoVo:{},e:{}",JsonEntityTransform.Object2Json(orderInfoVo),e);
			throw new BusinessException(e);
		}
	}


	/**
	 * 获取订单基本信息
	 *
	 *
	 * @author liyingjie
	 * @created 2016年4月3日
	 *
	 * @param orderSn
	 * @return
	 */
	public OrderEntity getOrderBaseByOrderSn(String orderSn){
		OrderEntity oe = orderBaseDao.getOrderBaseByOrderSn(orderSn);
		return oe;
	}


	/**
	 * 获取当前的订单的一些数量统计
	 * @param uid
	 * @return
	 */
	public   UserOrderCount getOrderCount4UserByUid(String uid){
		UserOrderCount userOrderCount = new UserOrderCount();
		if (Check.NuNStr(uid)){
			return userOrderCount;
		}
		userOrderCount.setApplyNum(orderDao.countUserApplyNum(uid));
		userOrderCount.setWaitCheckInNum(orderDao.countUserWaitCheckInNum(uid));
		userOrderCount.setWaitPayNum(orderDao.countUserWaitPayNum(uid));
		userOrderCount.setWaitEvalNum(orderDao.countUserWaitEvaNum(uid));
		return userOrderCount;
	}

	/**
	 *
	 * 查询订单快照
	 *
	 * @author yd
	 * @created 2016年5月2日 下午6:32:38
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<HouseSnapshotVo> findHouseSnapshotByOrder(OrderRequest orderRequest){
		return this.orderHouseSnapshotDao.findHouseSnapshotByOrder(orderRequest);
	}

	/**
	 * 获取当前最新的待入住的订单
	 * @author afi
	 * @param uid
	 * @return
	 */
	public OrderInfoVo getOrderLastByUid(String uid){
		if (Check.NuNStr(uid)){
			return null;
		}
		return orderDao.getOrderLastByUid(uid);

	}


	/**
	 *
	 * 获取租客订单列表
	 *
	 * @author jixd
	 * @created 2016年5月3日 下午9:54:09
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderList(OrderRequest orderRequest){
		PagingResult<OrderInfoVo> pagingResult = new PagingResult<OrderInfoVo>();
		if(orderRequest.getTenantOrderType() == TenantOrderTypeEnum.LING.getCode()){
			//进行中
			pagingResult = orderDao.getTenantOrderDoingList(orderRequest);
		}else if(orderRequest.getTenantOrderType() == TenantOrderTypeEnum.ENDED.getCode()){
			//已完成
			pagingResult = orderDao.getTenantOrderDoneList(orderRequest);
		}else if (orderRequest.getTenantOrderType() == TenantOrderTypeEnum.LOCKED.getCode()){
			//智能锁
			pagingResult = orderDao.getTenantOrderLockList(orderRequest);
		}else if (orderRequest.getTenantOrderType() == TenantOrderTypeEnum.APPLY.getCode()){
			//预约中
			pagingResult = orderDao.getTenantOrderApplyList(orderRequest);
		}else if (orderRequest.getTenantOrderType() == TenantOrderTypeEnum.WAIT_PAY.getCode()){
			//待支付
			pagingResult = orderDao.getTenantOrderWaitPayList(orderRequest);
		}else if (orderRequest.getTenantOrderType() == TenantOrderTypeEnum.WAIT_CHECK_IN.getCode()){
			//待入住
			pagingResult = orderDao.getTenantOrderWaitCheckInList(orderRequest);
		}else if (orderRequest.getTenantOrderType() == TenantOrderTypeEnum.WAIT_EVA.getCode()){
			//待评价
			pagingResult = orderDao.getTenantOrderWaitEvaList(orderRequest);
		} else if (orderRequest.getTenantOrderType() == TenantOrderTypeEnum.WAIT_CHECK_OUT.getCode()) {
			//待入住+已入住(for自如app，我的旅行)
			pagingResult = orderDao.getTenantOrderWaitCheckOutList(orderRequest);
		} else if (orderRequest.getTenantOrderType() == TenantOrderTypeEnum.ACTIVE.getCode()) {
			//2017-05-18 房客端 待入住+已入住+已经退房且未评价 订单(for自如app，我的旅行)
			pagingResult = orderDao.getTenantActiveList(orderRequest);
		}
		
		return pagingResult;
	}


	/**
	 * 获取租客待评价列表
	 * @author afi
	 * @created 2016年11月3日 下午9:54:09
	 *
	 * @param orderEvalRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getTenantOrderEavlList(OrderEvalRequest orderEvalRequest){
		PagingResult<OrderInfoVo> pagingResult = new PagingResult<OrderInfoVo>();

		if(Check.NuNObj(orderEvalRequest.getLimitDay())){
			LogUtil.error(LOGGER, "房客评价列表参数错误,parames={}", JsonEntityTransform.Object2Json(orderEvalRequest));
			return pagingResult;
		}

		if(orderEvalRequest.getOrderEvalType() == OrderEvalTypeEnum.WAITING.getCode()){
			//待评价的
			pagingResult = orderDao.getTenantOrderEavlWaitingList(orderEvalRequest);
		}else if(orderEvalRequest.getOrderEvalType() == OrderEvalTypeEnum.HAS.getCode()){
			//已评价
			pagingResult = orderDao.getTenantOrderEavlHasList(orderEvalRequest);
		}
		return pagingResult;

	}








	/**
	 * 获取当前的用户的有效锁数量
	 * @author afi
	 * @param uid
	 * @return
	 */
	public Long countLock(String uid){
		return orderDao.countLock(uid);
	}




	/**
	 *
	 * (已支付订单)客服取消订单 ——查找用户 订单金额
	 * 1.获取订单金额相关信息
	 * 2.计算已消费房租
	 * 3.计算房客已消费服务费
	 * 4.计算清洁费
	 * 5.计算折扣金额
	 *
	 * 注意: A.消费费用计算 当天时间过下午2点,则算当天,否则不算  B.订单状态(20 , 40 , 41)
	 *
	 * @author yd
	 * @created 2017年1月4日 下午8:57:02
	 *
	 * @param cancleOrderVo
	 */
	public void findCancleOrderVo(CheckOutStrategy checkOutStrategy,OrderInfoVo orderInfoVo,CancleOrderVo cancleOrderVo,OrderConfigVo config,DataTransferObject dto){


		if(!Check.NuNObj(orderInfoVo)
				&&!Check.NuNStrStrict(orderInfoVo.getOrderSn())){

			String orderSn = orderInfoVo.getOrderSn();
			int orderStatu = orderInfoVo.getOrderStatus();

			LogUtil.info(LOGGER, "客服取消订单,相关费用计算开始,orderSn={},orderStatu={}",orderSn,orderInfoVo.getOrderStatus());

			if(orderStatu == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()
					||orderStatu == OrderStatusEnum.CHECKED_IN.getOrderStatus()
					||orderStatu == OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()){

				if(Check.NuNObj(cancleOrderVo))  cancleOrderVo = new CancleOrderVo();
				cancleOrderVo.setOrderInfoVo(orderInfoVo);

				//获取当前的价格列表 折扣之后的
				Map<String,Integer> priceMap = this.getDayCutPricesMap(orderInfoVo,orderInfoVo.getDiscountMoney());

				Date curTime = new Date();
				//获取当前订单在当前时间的房租
				int rentalMoney = FinanceMoneyUtil.getRealRentalMoney(config, orderInfoVo,curTime, priceMap);


				//获取当前订单的清洁费
				int cleanMoney = FinanceMoneyUtil.getCleanMoney(orderInfoVo);

				//获取真实的用户的佣金
				int userCommMoneyConsume = FinanceMoneyUtil.getRealCommMoneyExt(config, orderInfoVo,rentalMoney,null,UserTypeEnum.TENANT);

				LogUtil.info(LOGGER,"【客服取消订单】重新计算当前的房租，当前的房租金额为：{},当前订单的清洁费：{}，真实的用户的佣金{}",rentalMoney,cleanMoney,userCommMoneyConsume);

				//剩余金额公式：支付金额  + 优惠券 + 活动金额 - 租客实际支付佣金金额  - 实际发生房租金额 - 清洁费
				int realLastMoney = orderInfoVo.getPayMoney() + orderInfoVo.getCouponMoney() + orderInfoVo.getActMoney() - userCommMoneyConsume  - rentalMoney - cleanMoney ;

				setCurDayMoney(orderInfoVo, curTime, priceMap, cancleOrderVo, config);

				//剩余金额公式：支付金额  + 优惠券 - 租客支付佣金金额  - 实际发生房租金额 - 清洁费   (客服可支配金额)
				int lastMoney =  FinanceMoneyUtil.getStaffControMoney(orderInfoVo, curTime, cancleOrderVo, rentalMoney,priceMap,config);

				//当前的折扣金额
				int discountMoney = 0;
				if(orderStatu != OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()){
					discountMoney = FinanceMoneyUtil.getDiscountMoneyBetweenDays(config, orderInfoVo, orderInfoVo.getStartTime(),curTime, priceMap);
				}

				LogUtil.info(LOGGER,"【提前退房】剩余金额(lastMoney)退款公式： 支付金额:{} + 优惠券:{} + 活动金额:{} - 租客支付佣金金额:{}   - 实际发生房租金额:{} - 清洁费：{}",orderInfoVo.getPayMoney(), orderInfoVo.getCouponMoney(),orderInfoVo.getActMoney(),orderInfoVo.getUserCommMoney(),rentalMoney,cleanMoney);
				if(realLastMoney<0 ||lastMoney < 0){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("支付金额错误");
					return ;
				}



				//计算优惠券剩余金额  违约金 当前为 0
				int couponMoneyConsume = getCouponInfo(orderInfoVo, rentalMoney, 0, userCommMoneyConsume, cleanMoney,dto);

				cancleOrderVo.setCouponMoneyConsume(couponMoneyConsume);
				cancleOrderVo.setCleanMoneyConsume(cleanMoney);
				cancleOrderVo.setPayMoney(realLastMoney-couponMoneyConsume);
				cancleOrderVo.setRentalMoneyConsume(rentalMoney);
				cancleOrderVo.setLastMoney(lastMoney);
				cancleOrderVo.setRealLastMoney(realLastMoney);
				cancleOrderVo.setUserCommMoneyConsume(userCommMoneyConsume);
				cancleOrderVo.setDiscountMoney(discountMoney);
				cancleOrderVo.setCommissionRateLandlord(config.returnRateLandlord());


				cancleOrderVo.setCurDay(DateUtil.dateFormat(curTime, "yyyy年MM月dd日"));

				//页面开关值 计算
				cancleOrderVo.setSwitchCleanFee(FinanceMoneyUtil.getCleanMoneySta(orderInfoVo, curTime, cancleOrderVo));

				//是否包含 违约金 服务费
				cancleOrderVo.setSwitchLanMoney(OrderCancleSwi.CLICEK_SELCT.getCode());

				//是否退当天房租
				cancleOrderVo.setSwitchTenCurDay(FinanceMoneyUtil.getCurDayMoneySta(orderInfoVo, curTime,cancleOrderVo));



			}
		}

	}

	/**
	 * 
	 * 设置退款当天价格
	 *
	 * @author yd
	 * @created 2017年1月12日 下午9:51:36
	 *
	 * @param orderInfoVo
	 * @param curTime
	 * @param priceMap
	 * @param cancleOrderVo
	 * @param config
	 */
	private void setCurDayMoney(OrderInfoVo orderInfoVo,Date curTime,Map<String,Integer> priceMap,CancleOrderVo cancleOrderVo,OrderConfigVo config){

		Date startTime = orderInfoVo.getStartTime();
		Date endTime = orderInfoVo.getEndTime();
		if(Check.NuNObjs(startTime,endTime,curTime)){
			LogUtil.error(LOGGER, "request par is {}", JsonEntityTransform.Object2Json(orderInfoVo));
			throw new BusinessException("time is null on getCurDayMoneySta");
		}
		Date time = curTime;
		if(curTime.before(startTime)){
			time = startTime;
		}
		if(!Check.NuNMap(priceMap)){
			cancleOrderVo.setCurDayMoney(0);
			Integer cruDayMo = priceMap.get(DateUtil.dateFormat(time, "yyyy-MM-dd"));
			Double commissionRateUser =  config.getCommissionRateUser();
			if(!Check.NuNObjs(cruDayMo,commissionRateUser)){
				Double commission =  BigDecimalUtil.mul(cruDayMo, commissionRateUser);
				cancleOrderVo.setCurDayMoney(cruDayMo+commission.intValue());
				LogUtil.info(LOGGER, "当天房租cruDayMo={},当前房租服务费commission={}", cruDayMo,commission);
			}

		}
	}

	/**
	 * 确定优惠券的钱
	 * 这个关于优惠券的范围可以再确认下,因为不同的优惠券的优惠券的范围是不确认的
	 * @author afi
	 * @param orderInfoVo
	 * @param rentalMoney
	 * @param realPenaltyMoney
	 */
	private int getCouponInfo(OrderInfoVo orderInfoVo,int rentalMoney,int realPenaltyMoney,int userCommMoney,int cleanMoney,DataTransferObject dto){
		if(Check.NuNObj(orderInfoVo)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("计算优惠卷金额失败");
			LogUtil.error(LOGGER, "取消订单查看-计算优惠卷金额失败");
			throw new BusinessException("计算优惠卷金额失败");
		}
		//当前订单没有优惠券直接返回
		if (orderInfoVo.getCouponMoney() == 0){
			return 0;
		}
		//所有的消费情况
		int costAllMoney = rentalMoney + realPenaltyMoney  + userCommMoney + cleanMoney;
		//所有的消费情况和优惠券的比对
		if(costAllMoney < orderInfoVo.getCouponMoney()){
			LogUtil.info(LOGGER,"修改优惠券的金额：房租：{}违约金：{}佣金：{}: 清洁费：{}  共计：{}",rentalMoney , realPenaltyMoney , userCommMoney,cleanMoney,costAllMoney);
			//当前优惠券的未使用满 需要重新减去用户余额
			int last = orderInfoVo.getCouponMoney() - costAllMoney;

			return last;
		}
		return 0;
	}

	/**
	 * 未入住协商取消
	 *
	 * @param
	 * @return
	 * @author lishaochuan
	 * @throws ParseException 
	 * @create 2017/1/4 16:04
	 */
	public void cancelOrderNegotiateNotIn(CancelOrderServiceRequest request, OrderConfigVo config, OrderInfoVo orderInfo, Date now, DataTransferObject dto) throws ParseException {
		LogUtil.info(LOGGER, "【协商取消订单】开始取消订单状态 并且 生成打款计划，orderSn:{}", request.getOrderSn());
        
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(request.getOrderSn());
		if(Check.NuNObj(request.getCancelType())){
			return ;
		}
		int cancelType = request.getCancelType().intValue();
		if(CancelTypeEnum.NEGOTIATION.getCode() == cancelType){//
			orderEntity.setOrderStatus(OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus());
		}else if (CancelTypeEnum.LANDLOR_APPLY.getCode() == cancelType){
			orderEntity.setOrderStatus(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus());
		}
		orderEntity.setRealEndTime(now);
		if (orderInfo.getNeedPay() == 0) {
			//当前是0元订单，当前的结算状态为结算完成
			orderEntity.setAccountsStatus(OrderAccountsStatusEnum.FINISH.getAccountsStatus());
		} else {
			//当前的结算状态为结算中
			orderEntity.setAccountsStatus(OrderAccountsStatusEnum.ING.getAccountsStatus());
		}


		// 清洁费
		int cleanMoney = getCleanMoney(orderInfo);

		//违约金
		Integer penaltyMoney = request.getPenaltyMoney() * 100;

		// 剩余金额
		int lastMoney = orderInfo.getPayMoney() + orderInfo.getCouponMoney() + orderInfo.getActMoney() - orderInfo.getUserCommMoney() - orderInfo.getCleanMoney();

		if (penaltyMoney > lastMoney) {
			LogUtil.error(LOGGER,"违约金大于剩余金额, penaltyMoney:{},lastMoney:{}", penaltyMoney, lastMoney);
			dto.setErrCode(1);
			dto.setMsg("违约金大于剩余金额");
			throw new BusinessException("违约金大于剩余金额");
		}

		OrderMoneyEntity orderMoney = new OrderMoneyEntity();
		//房客真实违约金
		int realPenaltyMoney = penaltyMoney;
		//房租
		int rentalMoney = 0;
		//房客真实的佣金
		int realUserMoney = 0;
		//重新计算优惠券金额
		this.sureCouponInfo(orderInfo, rentalMoney, realPenaltyMoney, realUserMoney, cleanMoney,orderMoney);
		//获取当前用户的消费情况
		int costUser = realPenaltyMoney + cleanMoney + realUserMoney;
		//真正退回给用户的
		int refundMoney = orderInfo.getPayMoney() + orderInfo.getCouponMoney() + orderInfo.getActMoney() - costUser;

		refundMoney = ValueUtil.getMin(refundMoney, orderInfo.getPayMoney());
		orderMoney.setOrderSn(request.getOrderSn());
		orderMoney.setPenaltyMoney(realPenaltyMoney);
		orderMoney.setRealMoney(realPenaltyMoney);
		orderMoney.setRentalMoney(rentalMoney);
		orderMoney.setRefundMoney(refundMoney);
		orderMoney.setRealUserMoney(realUserMoney);
		//设置当前订单的清洁费
		orderMoney.setCleanMoney(cleanMoney);
		//获取违约金的佣金
		if(request.getIsTakeLandlordComm() == YesOrNoEnum.NO.getCode()){
			config.setCommissionRateLandlord(0D);
		}
		Double commMoneyPercent = config.returnRateLandlord();
		Double commMoneyDou = BigDecimalUtil.mul(penaltyMoney, commMoneyPercent);
		//重新处理参加免佣金活动的金额
		this.sureLandCom(config, orderMoney, commMoneyDou.intValue());
		orderMoney.setDiscountMoney(0);
		virtualOrderBaseDao.updateOrderInfoAndStatus(request.getOperUid(), orderInfo.getOrderStatus(), orderEntity, orderMoney, request.getCancelReason(), OrderParamEnum.NEGOTIATE_CANCEL.getCode());
		//打给房东的违约金 和 清洁费
		this.dealPenaltyMoney2Land4Cancel(config, orderInfo, realPenaltyMoney + cleanMoney);
		//退给用户的金额 = refundMoney
		this.dealUserRefund4Cancel(orderInfo, refundMoney, realUserMoney);


		LogUtil.info(LOGGER, "【协商取消订单】释放房源，orderSn:{}", request.getOrderSn());

		if (OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus() == cancelType ||
				(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus() == cancelType && !Check.NuNObj(request.getIsShieldCalendar()) && YesOrNoEnum.NO.getCode()==request.getIsShieldCalendar())){
			houseLockDao.delLockHouseByOrderSn(request.getOrderSn());
		}

		
		
		/*房东申请取消订单，order库修改部分——开始*/
		
		if(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus() == cancelType){//是CalcelType==房东取消，的时候才会走下列流程
			LogUtil.info(LOGGER, "【cancelOrderNegotiateNotIn方法,房东取消订单】 isEdit={},orderSn={},isTakeOneHundred={},isTakeFirstNightMoney={}",
					request.getIsEdit(), request.getOrderSn(),request.getIsTakeOneHundred(),request.getIsTakeFirstNightMoney());
			if(YesOrNoEnum.YES.getCode()==request.getIsEdit().intValue()){//编辑类型
				OrderConfigEntity orderConfByPenalty = new OrderConfigEntity();
				if(YesOrNoEnum.YES.getCode()==request.getIsTakeOneHundred().intValue()){
					orderConfByPenalty.setConfigValue(YesOrNoEnum.YES.getStr());
					String penaltySn = OrderSnUtil.getPenaltySn();//新生成的penaltySn
					request.setPenaltyMoney(request.getOneHondred()); 
					Integer penaltyType = PenaltyTypeEnum.LAN_CANCLE_FIXED.getCode();
					LogUtil.info(LOGGER, "【cancelOrderNegotiateNotIn方法,房东取消订单】业务人员点击编辑按钮，对惩罚项重新编辑，且取消订单在入住前24小时之外,惩罚100元，惩罚金额={},罚款类型={}",
							request.getOneHondred(),penaltyType);
					int savePenalty = savePenalty(penaltySn, request, penaltyType);
					int savePenaltLog = savePenaltLog(penaltySn, request);
					if(savePenalty < 0 || savePenaltLog < 0){
						orderConfByPenalty.setConfigValue(YesOrNoEnum.NO.getStr());
					}
					insertOrderConf(orderConfByPenalty, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025002.getValue());
				}
			    if(YesOrNoEnum.YES.getCode()==request.getIsTakeFirstNightMoney().intValue()){
			    	String penaltySn = OrderSnUtil.getPenaltySn();//新生成的penaltySn
			    	//24小时之内，收取首晚房费		
			    	Map<String, Integer> priceMap = getDayCutPricesMap(orderInfo, orderInfo.getDiscountMoney());
					request.setPenaltyMoney(priceMap.get(DateUtil.dateFormat(orderInfo.getStartTime())));//获取首晚房费==》放入request中
					LogUtil.info(LOGGER, "【cancelOrderNegotiateNotIn方法,房东取消订单】业务人员点击编辑按钮，对惩罚项重新编辑，且取消订单在入住前24小时之内,惩罚首晚房费，orderInfo.getStartTime()={},首晚金额={},订单每日价格集合={}",
							orderInfo.getStartTime(),priceMap.get(DateUtil.dateFormat(orderInfo.getStartTime())),JsonEntityTransform.Object2Json(priceMap));
			    	Integer penaltyType = PenaltyTypeEnum.LAN_CANCLE_FIRST_RENT.getCode();
					int savePenalty = savePenalty(penaltySn, request, penaltyType);
					int savePenaltLog = savePenaltLog(penaltySn, request);
					orderConfByPenalty.setConfigValue(YesOrNoEnum.NO.getStr());
					if(savePenalty > 0 && savePenaltLog > 0){
						orderConfByPenalty.setConfigValue(YesOrNoEnum.YES.getStr());
					}
					insertOrderConf(orderConfByPenalty, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025001001.getValue());
			    }
			}else if(YesOrNoEnum.NO.getCode()==request.getIsEdit().intValue()){
				//没有点击编辑按钮
				String penaltySn = OrderSnUtil.getPenaltySn();//新生成的penaltySn
					OrderConfigEntity orderConfByPenalty = new OrderConfigEntity();
					Date oneDayAgoByOrderStart = DateUtil.getTime(orderInfo.getStartTime(), -1);//订单开始前24小时时间戳
					if(now.getTime() < oneDayAgoByOrderStart.getTime()){//取消订单这一刻还在24小时之外
						orderConfByPenalty.setConfigValue(YesOrNoEnum.YES.getStr());
							if(request.getLanCancelOrderInSomeTime() != 0){//6个月内有取消订单行为
								request.setPenaltyMoney(request.getOneHondred()); 
								Integer penaltyType = PenaltyTypeEnum.LAN_CANCLE_FIXED.getCode();
								LogUtil.info(LOGGER, "【cancelOrderNegotiateNotIn方法,房东取消订单】业务人员没有点击编辑按钮且取消订单在入住前24小时之外,惩罚100元，惩罚金额={},罚款类型={}",
										request.getOneHondred(),penaltyType);
								int savePenalty = savePenalty(penaltySn, request, penaltyType);
								int savePenaltLog = savePenaltLog(penaltySn, request);
								if(savePenalty < 0 || savePenaltLog < 0){
									orderConfByPenalty.setConfigValue(YesOrNoEnum.NO.getStr());
								}
								insertOrderConf(orderConfByPenalty, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025002.getValue());
					       }
					}else{
						//24小时之内，收取首晚房费		
						if(!Check.NuNStr(orderInfo.getLandlordUid())){
							Map<String, Integer> priceMap = getDayCutPricesMap(orderInfo, orderInfo.getDiscountMoney());
							request.setPenaltyMoney(priceMap.get(DateUtil.dateFormat(orderInfo.getStartTime())));//获取首晚房费==》放入request中
							LogUtil.info(LOGGER, "【cancelOrderNegotiateNotIn方法,房东取消订单】业务人员没有点击编辑按钮且取消订单在入住前24小时之内,惩罚首晚房费，orderInfo.getStartTime()={},首晚金额={},订单每日价格集合={}",
									orderInfo.getStartTime(),priceMap.get(DateUtil.dateFormat(orderInfo.getStartTime())),JsonEntityTransform.Object2Json(priceMap));
						}
						Integer penaltyType = PenaltyTypeEnum.LAN_CANCLE_FIRST_RENT.getCode();
						int savePenalty = savePenalty(penaltySn, request, penaltyType);
						int savePenaltLog = savePenaltLog(penaltySn, request);
						orderConfByPenalty.setConfigValue(YesOrNoEnum.NO.getStr());
						if(savePenalty > 0 && savePenaltLog > 0){
							orderConfByPenalty.setConfigValue(YesOrNoEnum.YES.getStr());
						}
						insertOrderConf(orderConfByPenalty, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025001001.getValue());
					}
			}
		
			//订单房源锁——惩罚项
			upSystemLock(request,orderInfo);

			//无论在什么时候取消  行为记录表一定要插入数据
			saveOrderCsrCancle(request);

			//将五项惩罚措施的执行结果（首次默认执行失败）插入到orderConfig表中
			saveFivePunishOrderConf(request);
		}
		/*房东申请取消订单，order库修改部分——结束*/
		
	}




	/**
	 * 已入住协商提前退房
	 *
	 * @author lishaochuan
	 * @create 2017/1/4 19:51
	 * @param
	 * @return
	 */
	public void cancelOrderNegotiateCheckIn(CancelOrderServiceRequest request, OrderConfigVo config, OrderInfoVo orderInfo, Date now, DataTransferObject dto) {
		Date limitDate = now;
		String checkOutTime = orderInfo.getCheckOutTime();
		if(request.getIsReturnTonightRental() == YesOrNoEnum.YES.getCode()){
			orderInfo.setCheckOutTime(DateSplitUtil.lastTime);
			limitDate = DateSplitUtil.transDateTime2Date(now);
		}

		//1.取消用户和房东的无效佣金
		this.cancelIncome(orderInfo);
		//2.取消无效的打款计算
		this.cancelFinace(orderInfo);
		//获取当前订单的清洁费
		int cleanMoney = FinanceMoneyUtil.getCleanMoney(orderInfo);
		if(request.getIsReturnCleanMoney() == YesOrNoEnum.YES.getCode() && cleanMoney > 0){
			this.cancelCleanIncome(orderInfo);
			this.cancelCleanFinace(orderInfo);
			cleanMoney = 0;
		}

		//获取当前的价格列表 折扣之后的
		Map<String,Integer> priceMap = this.getDayCutPricesMap(orderInfo,orderInfo.getDiscountMoney());
		LogUtil.info(LOGGER,"【协商提前退房，或房东取消退房】，获取每天房价：{}",JsonEntityTransform.Object2Json(priceMap));
		//获取当前订单在当前时间的房租
		int rentalMoney = FinanceMoneyUtil.getRealRentalMoney(config, orderInfo, limitDate, priceMap);
		LogUtil.info(LOGGER,"【协商提前退房】重新计算当前的房租，当前的房租金额为：{}",rentalMoney);

		// 理论上的违约金
		int planPenaltyMoney = request.getPenaltyMoney() * 100;

		//结算订单  主要确定消费的实际房租和违约金 以及佣金
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(orderInfo.getOrderSn());//设置订单编号
		if(CancelTypeEnum.LANDLOR_APPLY.getCode() == request.getCancelType().intValue()){//
			orderEntity.setOrderStatus(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus());
		}else{
			orderEntity.setOrderStatus(OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus());
			orderEntity.setEvaStatus(EvaStatusEnum.NO_NEED.getCode());
		}
		orderEntity.setRealEndTime(now);
		OrderMoneyEntity moneyEntity = new OrderMoneyEntity();
		//当前的折扣金额
		int discountMoney = FinanceMoneyUtil.getDiscountMoneyBetweenDays(config, orderInfo, orderInfo.getStartTime(), limitDate, priceMap);
		moneyEntity.setDiscountMoney(discountMoney);
		moneyEntity.setOrderSn(orderInfo.getOrderSn());
		//当前的房租为 真实的房租+折扣金额
		moneyEntity.setRentalMoney(rentalMoney + discountMoney);
		//真实的清洁费
		moneyEntity.setCleanMoney(cleanMoney);

		//剩余金额公式：支付金额  + 优惠券 + 优惠金额 - 租客支付佣金金额  - 实际发生房租金额 - 清洁费
		int lastMoney = orderInfo.getPayMoney() + orderInfo.getCouponMoney() + orderInfo.getActMoney() - orderInfo.getUserCommMoney()  - rentalMoney - cleanMoney ;
		LogUtil.info(LOGGER,"【协商提前退房】剩余金额退款公式： 支付金额:{} + 优惠券:{} - 租客支付佣金金额:{}   - 实际发生房租金额:{} - 清洁费：{}",orderInfo.getPayMoney(), orderInfo.getCouponMoney(),orderInfo.getUserCommMoney(),rentalMoney,cleanMoney);
		if(lastMoney < 0){
			LogUtil.error(LOGGER,"支付金额错误, orderInfo:{}", orderInfo);
			dto.setErrCode(1);
			dto.setMsg("支付金额错误");
			throw new BusinessException("支付金额错误");
		}
		//3.设置违约金额 需要比对当前的剩余金额是否不够支付违约金
		if(planPenaltyMoney > lastMoney){
			LogUtil.info(LOGGER, "违约金超过剩余金额,planPenaltyMoney:{},lastMoney:{}", planPenaltyMoney, lastMoney);
			dto.setErrCode(1);
			dto.setMsg("违约金超过剩余金额");
			throw new BusinessException("违约金超过剩余金额");
		}
		int realPenaltyMoney = ValueUtil.getMin(planPenaltyMoney, lastMoney);
		moneyEntity.setPenaltyMoney(realPenaltyMoney);
		//4.重新计算当前实际发生的佣金
		//除了房租之外的收取佣金的情况 = 真实的违约金 + 清洁费
		int extMoney = cleanMoney;
		if(request.getIsTakeLandlordComm() == YesOrNoEnum.YES.getCode()){
			extMoney += realPenaltyMoney;
		}
		//6.获取真实的房东的佣金
		int lanCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config, orderInfo,rentalMoney,extMoney ,UserTypeEnum.LANDLORD);
		//7.获取真实的用户的佣金
		int userCommMoney = FinanceMoneyUtil.getRealCommMoneyExt(config, orderInfo,rentalMoney,null,UserTypeEnum.TENANT);
		//8.重新计算优惠券金额
		this.sureCouponInfo(orderInfo, rentalMoney, realPenaltyMoney, userCommMoney,cleanMoney,moneyEntity);
		//9.重新处理参加免佣金活动的金额
		this.sureLandCom(config,moneyEntity,lanCommMoney);
		moneyEntity.setRealUserMoney(userCommMoney);
		//8.设置当前的退款金额
		//真正退回给用户的 公式：支付金额 + 优惠券 + 优惠金额 - 真实的房租 - 用户的真实的佣金 -违约金 - 清洁费
		int refundMoneyOrg = orderInfo.getPayMoney() + orderInfo.getCouponMoney() + orderInfo.getActMoney() - rentalMoney - userCommMoney - realPenaltyMoney -cleanMoney;
		int refundMoney = ValueUtil.getMin(refundMoneyOrg,orderInfo.getPayMoney());
		LogUtil.info(LOGGER,"【协商提前退房】退款金额公式：支付金额：{} + 优惠券:{} - 真实的房租:{} - 用户的真实的佣金:{} - 违约金：{} - 清洁费：{}",orderInfo.getPayMoney() , orderInfo.getCouponMoney() , rentalMoney , userCommMoney,realPenaltyMoney,cleanMoney);
		LogUtil.info(LOGGER,"【协商提前退房】实际退款金额是 退款金额：{}和支付金额：{}的最小值{}",refundMoneyOrg,orderInfo.getPayMoney(),refundMoney);
		moneyEntity.setRefundMoney(refundMoney);
		//9.直接保存订单的信息
		virtualOrderBaseDao.updateOrderInfoAndStatus(orderInfo.getUserUid(), orderInfo.getOrderStatus(), orderEntity, moneyEntity, request.getCancelReason(), OrderParamEnum.NEGOTIATE_CANCEL.getCode());
		//10.释放多余的房源锁定
		orderInfo.setCheckOutTime(checkOutTime);

		
		if(Check.NuNObj(request.getCancelType())){
			return ;
		}
		int cancelType = request.getCancelType().intValue();
		int  orderStatus =  OrderStatusEnum.FINISH_LAN_APPLY.getOrderStatus();
		if (OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus() == cancelType){
			this.unLockHouseNow(orderInfo);
			orderStatus = OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus();
		}
		if(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus() == cancelType && !Check.NuNObj(request.getIsShieldCalendar()) && YesOrNoEnum.NO.getCode()==request.getIsShieldCalendar().intValue()){
			this.unLockHouseNow(orderInfo);
		}

		//结算
		orderInfo = orderCommonService.getOrderInfoByOrderSn(request.getOrderSn());
		orderLoadlordServiceImpl.updateConfirmOtherMoneyAndCheck(config, orderInfo,orderStatus, dto, request.getIsTakeLandlordComm(),request.getOperUid());
		
		
/*房东申请取消订单，order库修改部分——开始*/
		if(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus()==cancelType){//是CalcelType==房东取消，的时候才会走下列流程
			LogUtil.info(LOGGER, "【cancelOrderNegotiateCheckIn方法,房东取消订单个项处理开始】 isEdit={},orderSn={},isTakeOneHundred={},isTakeFirstNightMoney={}",
					request.getIsEdit(), request.getOrderSn(),request.getIsTakeOneHundred(),request.getIsTakeFirstNightMoney());
			if(YesOrNoEnum.YES.getCode()==request.getIsEdit().intValue()){//编辑类型
				OrderConfigEntity orderConfByPenalty = new OrderConfigEntity();
				if(YesOrNoEnum.YES.getCode()==request.getIsTakeOneHundred().intValue()){
					orderConfByPenalty.setConfigValue(YesOrNoEnum.YES.getStr());
					String penaltySn = OrderSnUtil.getPenaltySn();//新生成的penaltySn
					request.setPenaltyMoney(request.getOneHondred()); 
					Integer penaltyType = PenaltyTypeEnum.LAN_CANCLE_FIXED.getCode();
					LogUtil.info(LOGGER, "【cancelOrderNegotiateCheckIn方法,房东取消订单】业务人员点击编辑按钮，对惩罚项重新编辑，且取消订单在入住前24小时之外,惩罚100元，惩罚金额={},罚款类型={}",
							request.getOneHondred(),penaltyType);
					int savePenalty = savePenalty(penaltySn, request, penaltyType);
					int savePenaltLog = savePenaltLog(penaltySn, request);
					if(savePenalty < 0 || savePenaltLog < 0){
						orderConfByPenalty.setConfigValue(YesOrNoEnum.NO.getStr());
					}
					insertOrderConf(orderConfByPenalty, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025002.getValue());
				}
			    if(YesOrNoEnum.YES.getCode()==request.getIsTakeFirstNightMoney().intValue()){
			    	String penaltySn = OrderSnUtil.getPenaltySn();//新生成的penaltySn
			    	//24小时之内，收取首晚房费		
					request.setPenaltyMoney(priceMap.get(DateUtil.dateFormat(orderInfo.getStartTime())));//获取首晚房费==》放入request中
					LogUtil.info(LOGGER, "【cancelOrderNegotiateCheckIn方法,房东取消订单】业务人员点击编辑按钮，重新编辑了惩罚项目，且取消订单在入住前24小时之内,惩罚首晚房费，orderInfo.getStartTime()={},首晚金额={},订单每日价格集合={}",
							orderInfo.getStartTime(),priceMap.get(DateUtil.dateFormat(orderInfo.getStartTime())),JsonEntityTransform.Object2Json(priceMap));
			    	Integer penaltyType = PenaltyTypeEnum.LAN_CANCLE_FIRST_RENT.getCode();
					int savePenalty = savePenalty(penaltySn, request, penaltyType);
					int savePenaltLog = savePenaltLog(penaltySn, request);
					orderConfByPenalty.setConfigValue(YesOrNoEnum.NO.getStr());
					if(savePenalty > 0 && savePenaltLog > 0){
						orderConfByPenalty.setConfigValue(YesOrNoEnum.YES.getStr());
					}
					insertOrderConf(orderConfByPenalty, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025001001.getValue());
			    }
			}else if(YesOrNoEnum.NO.getCode()==request.getIsEdit().intValue()){
				OrderConfigEntity orderConfByPenalty = new OrderConfigEntity();//插入罚款单表成功==》向conf表插入数据
				String penaltySn = OrderSnUtil.getPenaltySn();//新生成的penaltySn	
				//24小时之内，收取首晚房费		
				request.setPenaltyMoney(priceMap.get(DateUtil.dateFormat(orderInfo.getStartTime())));//获取首晚房费==》放入request中
				LogUtil.info(LOGGER, "【cancelOrderNegotiateCheckIn方法,房东取消订单】业务人员没有点击编辑按钮且取消订单在入住前24小时之内,惩罚首晚房费，orderInfo.getStartTime()={},首晚金额={},订单每日价格集合={}",
						orderInfo.getStartTime(),priceMap.get(DateUtil.dateFormat(orderInfo.getStartTime())),JsonEntityTransform.Object2Json(priceMap));
				Integer penaltyType = PenaltyTypeEnum.LAN_CANCLE_FIRST_RENT.getCode();
				int savePenalty = savePenalty(penaltySn, request, penaltyType);
				int savePenaltLog = savePenaltLog(penaltySn, request);
				orderConfByPenalty.setConfigValue(YesOrNoEnum.NO.getStr());
				if(savePenalty > 0 && savePenaltLog > 0){
					orderConfByPenalty.setConfigValue(YesOrNoEnum.YES.getStr());
				}
				insertOrderConf(orderConfByPenalty, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025001001.getValue());
			}
			 //订单房源锁——惩罚项
			upSystemLock(request,orderInfo);
			//无论在什么时候取消  行为记录表一定要插入数据
			saveOrderCsrCancle(request);
			//将五项惩罚措施的执行结果（首次默认执行失败）插入到orderConfig表中
			saveFivePunishOrderConf(request);
		}


	}

	private void upSystemLock(CancelOrderServiceRequest request,OrderInfoVo orderInfo){
		LogUtil.info(LOGGER,"【房东取消订单】-房源系统锁定,orderSn={}",orderInfo.getOrderSn());
		if(!Check.NuNObj(request.getIsShieldCalendar()) && YesOrNoEnum.YES.getCode()==request.getIsShieldCalendar().intValue()){
			houseLockDao.updateSystemLock(request.getOrderSn(),getLimitDate(orderInfo));
			OrderConfigEntity orderConfByShield = new OrderConfigEntity();//t_order_config ==》插入实体
			//orderConf==>屏蔽日历
			orderConfByShield.setConfigValue(YesOrNoEnum.YES.getStr());
			insertOrderConf(orderConfByShield, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025001006.getValue());
		}
	}
	
	/**
	 * 
	 * 将五项惩罚措施的执行结果（首次默认执行失败）插入到orderConfig表中
	 *
	 * @author loushuai
	 * @created 2017年5月15日 上午10:12:33
	 *
	 */
	public void saveFivePunishOrderConf(CancelOrderServiceRequest request){
		if(!Check.NuNObj(request.getIsCancelAngel()) && request.getIsCancelAngel().equals(YesOrNoEnum.YES.getCode())){
			//orderConf==>取消天使房东
			OrderConfigEntity orderConfByAngel = new OrderConfigEntity();//t_order_config ==》插入实体
			orderConfByAngel.setConfigValue(YesOrNoEnum.NO.getStr());//此措施执行失败
			insertOrderConf(orderConfByAngel, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025001003.getValue());
		}
		 
		/*if(!Check.NuNObj(request.getIsAddSystemEval()) && request.getIsAddSystemEval().equals(YesOrNoEnum.YES.getCode())){
			//orderConf==>增加系统评价
			OrderConfigEntity orderConfByEval = new OrderConfigEntity();//t_order_config ==》插入实体
			orderConfByEval.setConfigValue(YesOrNoEnum.NO.getStr());
			insertOrderConf(orderConfByEval, request.getOrderSn(),  ProductRulesEnum0025Enum.ProductRulesEnum0025001004.getValue());
		}*/
		
		if(!Check.NuNObj(request.getIsUpdateRankFactor()) && request.getIsUpdateRankFactor().equals(YesOrNoEnum.YES.getCode())){
			//orderConf==>排序因子
			OrderConfigEntity orderConfByRank = new OrderConfigEntity();//t_order_config ==》插入实体
			orderConfByRank.setConfigValue(YesOrNoEnum.NO.getStr());
			insertOrderConf(orderConfByRank, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025001005.getValue());
		}
		
		if(!Check.NuNObj(request.getIsGiveCoupon()) && request.getIsGiveCoupon().equals(YesOrNoEnum.YES.getCode())){
		    //orderConf==>优惠券
			OrderConfigEntity orderConfByCoupon = new OrderConfigEntity();//t_order_config ==》插入实体
			orderConfByCoupon.setConfigValue(YesOrNoEnum.NO.getStr());
			insertOrderConf(orderConfByCoupon, request.getOrderSn(), ProductRulesEnum0025Enum.ProductRulesEnum0025001007.getValue());
		}
	}
	
	/**
	 * 
	 * 分别向t_finance_penalty，t_finance_penalty_log，t_order_csr_cancle插入对象
	 *
	 * @author loushuai
	 * @created 2017年5月10日 下午7:49:29
	 *
	 * @param requestDto
	 * @param penaltyType
	 */
	public int savePenalty(String penaltySn, CancelOrderServiceRequest requestDto, Integer penaltyType) {
		
		//插入罚款单表==》插入
		FinancePenaltyEntity financePenalty = new FinancePenaltyEntity();
		financePenalty.setPenaltySn(penaltySn);
		financePenalty.setPenaltyFee(requestDto.getPenaltyMoney());//首夜房费
		financePenalty.setPenaltyLastFee(requestDto.getPenaltyMoney());//第一次插入——首夜房费
		financePenalty.setOrderSn(requestDto.getOrderSn());
		financePenalty.setPenaltyUid(requestDto.getLandlordUid());
		financePenalty.setPenaltyUserType(UserTypeEnum.LANDLORD.getUserType());
		financePenalty.setPenaltyStatus(PenaltyStatusEnum.WAITING.getCode());// 罚款单待处理，初次0
		financePenalty.setPenaltyType(penaltyType);
		if (!Check.NuNStr(requestDto.getCancelReason())) {
			financePenalty.setRemark(requestDto.getCancelReason());
		}
		return financePenaltyDao.saveFinancePenalty(financePenalty);
	}
	
	/**
	 * 
	 * t_finance_penalty_log插入对象
	 *
	 * @author loushuai
	 * @created 2017年5月10日 下午7:49:29
	 *
	 * @param requestDto
	 */
	public int savePenaltLog(String penaltySn, CancelOrderServiceRequest requestDto) {
		//罚款单日志表==》插入
				FinancePenaltyLogEntity financePenaltyLogEntity = new FinancePenaltyLogEntity();
				financePenaltyLogEntity.setFromStatus(PenaltyStatusEnum.WAITING.getCode());
				financePenaltyLogEntity.setToStatus(PenaltyStatusEnum.WAITING.getCode());
				financePenaltyLogEntity.setRemark("房东申请取消");
				financePenaltyLogEntity.setEmpName(requestDto.getOperName());
				financePenaltyLogEntity.setEmpCode(requestDto.getEmpCode());
				financePenaltyLogEntity.setPenaltySn(penaltySn);
				financePenaltyLogEntity.setFromFee(requestDto.getPenaltyMoney());
				financePenaltyLogEntity.setToFee(requestDto.getPenaltyMoney());
				return financePenaltyLogDao.saveFinancePenaltyLog(financePenaltyLogEntity);
	}
	
	/**
	 * 
	 * 分别向t_finance_penalty，t_finance_penalty_log，t_order_csr_cancle插入对象
	 *
	 * @author loushuai
	 * @created 2017年5月10日 下午7:49:29
	 *
	 * @param requestDto
	 */
	public int saveOrderCsrCancle( CancelOrderServiceRequest requestDto) {
		//罚款单日志表==》插入
		//行为表==》插入
		OrderCsrCancleEntity orderCsrCancleEntity = new OrderCsrCancleEntity();
		orderCsrCancleEntity.setOrderSn(requestDto.getOrderSn());
		orderCsrCancleEntity.setCancleType(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus());
		orderCsrCancleEntity.setCancleReason(requestDto.getCancelReasonCode());
		orderCsrCancleEntity.setRemark(requestDto.getCancelReason());
		orderCsrCancleEntity.setEmpCode(requestDto.getEmpCode());
		orderCsrCancleEntity.setEmpName(requestDto.getOperName());
		orderCsrCancleEntity.setPunishStatu(PunishedStatusEnum.DEALUNDONE.getCode());
		return orderCsrCancleDao.saveOrderCsrCancle(orderCsrCancleEntity);
	}
	
	/**
	 * 
	 * 向orderConf表中插入记录
	 *
	 * @author loushuai
	 * @created 2017年5月11日 下午9:25:35
	 *
	 */
	public void insertOrderConf(OrderConfigEntity orderConfigEntity, String orderSn, String configCode){
		orderConfigEntity.setConfigCode(configCode);
		orderConfigEntity.setOrderSn(orderSn);
		orderConfigDao.insertOrderConfig(orderConfigEntity);
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
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,i);
		Date beforeMonthsDate=calendar.getTime();
		return beforeMonthsDate;
	}




	/**
	 * 根据orderSn更新t_order_csr_cancle中的punish_statu
	 *
	 * @author loushuai
	 * @created 2017年5月12日 下午2:30:38
	 *
	 * @param orderSn
	 */
	public int updateOrderCsrCancle(String orderSn, Integer punishStatu) {
		return orderCsrCancleDao.updateOrderCsrCancle(orderSn, punishStatu);
	}




	/**
	 * 获取一定时间段内landlordUid取消行为总数
	 *
	 * @author loushuai
	 * @created 2017年5月12日 下午5:34:04
	 *
	 * @param landlordUid
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public long getCountInTimes(String landlordUid, Date beginDate, Date endDate) {
		return orderCsrCancleDao.getCountInTimes(landlordUid, beginDate, endDate);
	}




	/**
	 * 根据orderSn获取罚款单详情 
	 *
	 * @author loushuai
	 * @created 2017年5月16日 上午10:50:06
	 *
	 * @param orderSn
	 * @return
	 */
	public FinancePenaltyEntity getPenaltyEntityByOrderSn(String orderSn) {
		return financePenaltyDao.getPenaltyEntityByOrderSn(orderSn);
	}




	/**
	 * 
	 * 获取当前的订单配置列表
	 * @author loushuai
	 * @created 2017年5月16日 上午11:28:57
	 *
	 * @param orderSn
	 * @return
	 */
	public List<OrderConfigEntity> getOrderConfigListByOrderSn(String orderSn) {
		return orderConfigDao.getOrderConfigListByOrderSn(orderSn);
	}




	/**
	 * 
	 *1：查询t_order_csr_cancle（订单取消表）所有cancle_type=38（房东取消订单）punish_statu=10（处理未完成）is_del=0（未失效）.
     * 2：遍历集合，从orderConfig找出未执行成功的项，在执行一遍
	 * @author loushuai
	 * @created 2017年5月16日 下午5:32:49
	 *
	 */
	public List<OrderCsrCancleEntity> getDoFailLandQXOrderPunish() {
		//获取到所有未处理完成的   ==》   房东取消行为对象
		return orderCsrCancleDao.getDoFailLandQXOrderPunish(CancelTypeEnum.LANDLOR_APPLY.getCode(), null, PunishedStatusEnum.DEALUNDONE.getCode());
	}




	/**
	 * 获取到所有未处理完成的  OrderCsrCancleEntity对象
	 *
	 * @author loushuai
	 * @created 2017年5月16日 下午9:24:30
	 *
	 * @param orderSn
	 * @return
	 */
	public FinancePenaltyEntity getFinancePenaltyByOrderSn(String orderSn) {
		return financePenaltyDao.getPenaltyEntityByOrderSn(orderSn);
	}

	/**
	 * 更新系统锁定
	 * @author jixd
	 * @created 2017年05月25日 14:18:28
	 * @param
	 * @return
	 */
	public int updateOrderSysLock(OrderInfoVo orderInfoVo){
		return houseLockDao.updateSystemLock(orderInfoVo.getOrderSn(),getLimitDate(orderInfoVo));
	}
	
}

