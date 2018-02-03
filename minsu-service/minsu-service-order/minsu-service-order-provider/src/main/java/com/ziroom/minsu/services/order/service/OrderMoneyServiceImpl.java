package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.services.order.dto.OrderProfitRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.valenum.cms.ActTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>订单金额的计算</p>
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
@Service("order.orderMoneyServiceImpl")
public class OrderMoneyServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderMoneyServiceImpl.class);

	@Resource(name = "order.orderSpecialPriceDao")
	private OrderSpecialPriceDao orderSpecialPriceDao;

	@Resource(name = "order.houseSnapshotDao")
	private OrderHouseSnapshotDao orderHouseSnapshotDao;

	@Resource(name = "order.financePunishDao")
	private FinancePunishDao financePunishDao;

	@Resource(name = "order.orderMoneyDao")
	private OrderMoneyDao orderMoneyDao;

	@Resource(name = "order.orderConfigDao")
	private OrderConfigDao orderConfigDao;


	@Resource(name = "order.orderUserServiceImpl")
	private OrderUserServiceImpl orderUserService;
	
	public static String timestampPattern = "yyyy-MM-dd HH:mm:ss";


	/**
	 * 根据订单号获取订单金额
	 * @author lishaochuan
	 * @create 2016年9月12日下午10:09:38
	 * @param orderSn
	 * @return
	 */
	public OrderMoneyEntity getOrderMoneyByOrderSn(String orderSn) {
		return orderMoneyDao.getOrderMoneyByOrderSn(orderSn);
	}


	/**
	 * 获取当前的剩余金额
	 * 做兼容的控制
	 * @author afi
	 * @param orderEntity
	 * @return
	 */
	public int getOtherMoneyLimit(OrderInfoVo orderEntity){
		//获取用户的剩余金额
		int last = getOtherMoneyLimitOrg(orderEntity);
		if(last< 0){
			last = 0;
		}
		last =  ValueUtil.getMin(last, orderEntity.getPayMoney());
		return ValueUtil.getMin(last,orderEntity.getDepositMoney());
	}


	/**
	 * 获取当前的剩余金额
	 * 不做兼容的控制
	 * @author afi
	 * @param orderEntity
	 * @return
	 */
	public int getOtherMoneyLimitOrg(OrderInfoVo orderEntity){
		if(Check.NuNObj(orderEntity.getRealEndTime())){
			LogUtil.info(LOGGER, "【异常数据】 orerSn:",orderEntity.getOrderSn());
			throw new BusinessException("orerSn  "+ orderEntity.getOrderSn() +" realEndTime is null");
		}
		//用户的所有消费情况
		int cost = this.getCostPrice(orderEntity);
		//获取用户的剩余金额  支付金额 + 优惠券金额 + 活动金额 + 长租优惠金额 - 用户佣金 -用户消费
		int last = ValueUtil.getintValue(orderEntity.getPayMoney()) + ValueUtil.getintValue(orderEntity.getDiscountMoney())
				+ ValueUtil.getintValue(orderEntity.getCouponMoney()) + ValueUtil.getintValue(orderEntity.getActMoney()) - ValueUtil.getintValue(orderEntity.getUserCommMoney()) - cost;
		return last;
	}


	/**
	 * 获取当前的剩余金额
	 * @param orderEntity
	 * @return
	 */
	public int getLastPrice(OrderInfoVo orderEntity){
		if(Check.NuNObj(orderEntity.getRealEndTime())){
			LogUtil.info(LOGGER, "【异常数据】 orerSn:",orderEntity.getOrderStatus());
			throw new BusinessException("orerSn  "+ orderEntity.getOrderSn() +" realEndTime is null");
		}
		//用户的所有消费情况
		int cost = this.getCostPrice(orderEntity);
		//获取实际的房租
		int last = orderEntity.getPayMoney()  - cost;
		if(last< 0){
			last = 0;
		}
		return ValueUtil.getMin(last, orderEntity.getPayMoney());
	}

	/**
	 * 获取订单的消费情况
	 * @param orderEntity
	 * @return
	 */
	public int getCostPrice(OrderInfoVo orderEntity){
		if(Check.NuNObj(orderEntity.getRealEndTime())){
			LogUtil.info(LOGGER, "【异常数据】 orerSn:",orderEntity.getOrderStatus());
			throw new BusinessException("orerSn  "+ orderEntity.getOrderSn() +" realEndTime is null");
		}
		//房东发起的用户的额外消费
		Integer otherMoney = orderEntity.getOtherMoney();
		//违约金
		Integer penaltyMoney = orderEntity.getPenaltyMoney();
		//房租
		Integer rentalMoney =  orderEntity.getRentalMoney();
		//用户的所有消费情况
		int cost = ValueUtil.getintValue(rentalMoney) + ValueUtil.getintValue(penaltyMoney) + ValueUtil.getintValue(otherMoney);
		return cost;
	}


	/**
	 * 获取当前房源的价格
	 * @param houseInfo
	 * @return
	 */
	public Integer getHousePrice(OrderNeedHouseVo houseInfo){
		Integer price = null;
		if(!Check.NuNObj(houseInfo)){
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseInfo.getRentWay());
			if(Check.NuNObj(rentWayEnum)){
				LogUtil.info(LOGGER, "【异常数据】 houseFid:",houseInfo.getFid());
				throw new BusinessException("houseInfo.getRentWay() is null");
			}
			if(rentWayEnum.getCode() == RentWayEnum.HOUSE.getCode()){
				price = houseInfo.getLeasePrice();
			}else if(rentWayEnum.getCode() == RentWayEnum.ROOM.getCode()){
				price = houseInfo.getPrice();
			}else {
				throw new BusinessException("rentWay is wrong:"+houseInfo.getRentWay());
			}
		}
		return price;

	}




	/**
	 * 获取当前优惠券的使用时间
	 * 按照折扣之前的来算，因为提前退房是不享受折扣的
	 * 所以按照折扣之后的没有意义
	 * @param order
	 * @return
	 */
	public Date setPaymentRunTime(OrderInfoVo order){
		if(Check.NuNObj(order)){
			return null;
		}
		int couponMoney = order.getCouponMoney();
		if(couponMoney <= 0){
			return null;
		}
		Map<String,Integer> map = orderUserService.getDayCutPricesMap(order,order.getDiscountMoney());
		int priceSum = 0;
		Date couponUseTime = order.getStartTime();
		priceSum += map.get(DateUtil.dateFormat(couponUseTime));
		while (priceSum<=couponMoney){
			Integer everyDayPrice = map.get(DateUtil.dateFormat(couponUseTime));
			if (everyDayPrice != null) { // zhangshaobin  当优惠券的金额大于订单金额的时候， 就会报错， 临时处理方法。 需要从新梳理判断逻辑， 可以加上end_time
				priceSum += everyDayPrice.intValue();
				couponUseTime = DateSplitUtil.getTomorrow(couponUseTime);
			} else {
				break;
			}
		}
		return DateSplitUtil.getTomorrow(couponUseTime);
	}


	/**
	 * 设置收款单执行的时间
	 * @author jixd
	 * @created 2017年06月06日 19:37:07
	 * @param order
	 * @return
	 */
	public void setPaymentRunTime(OrderInfoVo order, List<OrderActivityInfoVo> orderActList){
		if(Check.NuNObj(order)){
			return;
		}
		if (Check.NuNCollection(orderActList)){
			return;
		}
		int couponMoney = order.getCouponMoney();
		int actMoney = order.getActMoney();
		OrderActivityInfoVo couponAct = null;
		OrderActivityInfoVo actReduce = null;
		for (OrderActivityInfoVo orderAct: orderActList){
			if (orderAct.getAcType() == OrderAcTypeEnum.COUPON.getCode()){
				couponAct = orderAct;
			}
			if (orderAct.getAcType() == OrderAcTypeEnum.FIRST_ORDER_REDUC.getCode()){
				actReduce = orderAct;
			}
		}
		int actReduceMoney = 0;
		if (!Check.NuNObj(actReduce)){
			actReduceMoney = actReduce.getAcMoney();
		}

		//先消费优惠券 计算出优惠券收款单执行的时间  然后计算下单立减
		Map<String,Integer> map = orderUserService.getDayCutPricesMap(order,order.getDiscountMoney());
		if (!Check.NuNObj(couponAct) && couponMoney >0){
			couponAct.setPaymentTime(calculatePaymentDay(order.getStartTime(), couponMoney, map));
		}
		if (!Check.NuNObj(actReduce) && actMoney >0){
			actReduce.setPaymentTime(calculatePaymentDay(order.getStartTime(), couponMoney + actReduceMoney, map));
		}
	}

	/**
	 * 计算收款单执行时间
	 * @author jixd
	 * @created 2017年06月06日 19:37:07
	 * @param startDate
	 * @param couponMoney
	 * @param map
	 * @return
	 */
	private Date calculatePaymentDay(Date startDate, int couponMoney, Map<String, Integer> map) {
		int priceSum = 0;
		Date couponUseTime = startDate;
		priceSum += map.get(DateUtil.dateFormat(couponUseTime));
		while (priceSum<=couponMoney){
			Integer dayMoney = map.get(DateUtil.dateFormat(couponUseTime));
			if (Check.NuNObj(dayMoney)){
				break;
			}
			priceSum += dayMoney;
			couponUseTime = DateSplitUtil.getTomorrow(couponUseTime);
		}
		return DateSplitUtil.getTomorrow(couponUseTime);
	}

	/**
	 * 获取订单的价格列表
	 * @param order
	 * @return
	 */
	@Deprecated
	public Map<String,Integer>  getDayCutPricesMap(OrderEntity order,int discountMoney){
		OrderHouseSnapshotEntity houseSnapshotEntity = orderHouseSnapshotDao.findHouseSnapshotByOrderSn(order.getOrderSn());
		if(houseSnapshotEntity == null){
			LogUtil.error(LOGGER,"houseSnapshotEntity is null");
			throw  new BusinessException("houseSnapshotEntity is null");
		}
		Double cut = null;
		//获取当前的房源的折扣
		OrderConfigEntity configEntity = orderConfigDao.getOrderConfigByOrderSnAndCode(order.getOrderSn(), ProductRulesEnum.ProductRulesEnum0019.getValue());
		if (!Check.NuNObj(configEntity)){
			cut = ValueUtil.getdoubleValue(configEntity.getConfigValue());
		}
		Map<String,Integer> priceMap = orderSpecialPriceDao.getOrderSpecialPriceMapByOrderSn(order.getOrderSn(),houseSnapshotEntity.getPrice());
		priceMap.put("discountMoney",discountMoney);
		Map<String,Integer> cutPriceMap = FinanceMoneyUtil.getDayCutPricesMap(order.getStartTime(),order.getEndTime(),houseSnapshotEntity.getPrice(),cut,priceMap);
		return cutPriceMap;
	}

	//    /**
	//     * 获取订单的价格列表
	//     * @param order
	//     * @return
	//     */
	//    public List<OrderDayPriceVo>  getDayPricess(OrderEntity order){
	//        OrderHouseSnapshotEntity houseSnapshotEntity = orderHouseSnapshotDao.findHouseSnapshotByOrderSn(order.getOrderSn());
	//        if(houseSnapshotEntity == null){
	//        	LogUtil.error(LOGGER,"houseSnapshotEntity is null");
	//            throw  new BusinessException("houseSnapshotEntity is null");
	//        }
	//        try{
	//            Map<String,Integer> priceMap = orderSpecialPriceDao.getOrderSpecialPriceMapByOrderSn(order.getOrderSn());
	//            List<OrderDayPriceVo> dayPrice = new ArrayList<>();
	//            List<Date> dayList = DateSplitUtil.dateSplit(order.getStartTime(), order.getEndTime());
	//            for(Date date: dayList){
	//                OrderDayPriceVo dayPriceVo = new OrderDayPriceVo();
	//                dayPriceVo.setPriceDate(DateUtil.dateFormat(date));
	//                if(priceMap.containsKey(DateUtil.dateFormat(date))){
	//                    dayPriceVo.setPriceValue(priceMap.get(DateUtil.dateFormat(date)));
	//                }else {
	//                    dayPriceVo.setPriceValue(houseSnapshotEntity.getPrice());
	//                }
	//                dayPrice.add(dayPriceVo);
	//            }
	//            return dayPrice;
	//        }catch (Exception e){
	//            LogUtil.error(LOGGER, "e:{}", e);
	//            throw new BusinessException(e);
	//        }
	//    }



	/**
	 * 强制取消订单获取当前订单的房东需要支付的违约金
	 * @author afi
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public int getPunishMoney(OrderInfoVo order){
		int rst = 0 ;
		if(Check.NuNObj(order)){
			return rst;
		}
		if(Check.NuNObj(order.getRealEndTime())){
			LogUtil.info(LOGGER, "【异常数据】 orerSn:",order.getOrderSn());
			throw new BusinessException("orerSn  "+ order.getOrderSn() +" realEndTime is null");
		}

		FinancePunishEntity financePunish = financePunishDao.getPunishListByOrderSn(order.getOrderSn());
		if(Check.NuNObj(financePunish)){
			return rst;
		}else {
			rst = financePunish.getPunishFee();
		}
		return rst;
	}


	/**
	 * 计算 一个房源 一个月 实际收益
	 * @author liyingjie
	 * @param profitRequest
	 * @return
	 */
	public long monthRealProfit(OrderProfitRequest profitRequest){
		long result = 0l;
		if(Check.NuNObj(profitRequest)){
			LogUtil.info(LOGGER,"monthRealProfit 参数为空.");
			return result;
		}
		if(Check.NuNStr(profitRequest.getHouseFid())){
			LogUtil.info(LOGGER,"monthRealProfit houseFid为空.");
			return result;
		}
		if(profitRequest.getMonth() == 0){
			LogUtil.info(LOGGER,"monthRealProfit 参数month为0.");
			return result;
		}
		if(profitRequest.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(profitRequest.getRoomFid())){
			LogUtil.info(LOGGER,"monthRealProfit 合租房间，roomFid为空,roomFid:{}.",profitRequest.getRoomFid());
			return result;
		}

        Map<Integer, Date> cycleMonth = DateSplitUtil.getCycleMonth();
        Date monthDate = cycleMonth.get(profitRequest.getMonth());
        profitRequest.setBeginTime(DateSplitUtil.getFirstSecondOfMonth(monthDate));
        profitRequest.setEndTime(DateSplitUtil.getLastSecondOfMonth(monthDate));
		result = orderMoneyDao.countMonthRealProfit(profitRequest);
		return result;
	}


	/**
	 * 计算 一个房源 一个月 实际收益
	 * @author liyingjie
	 * @param profitRequest
	 * @return
	 */
	public long monthPredictProfit(OrderProfitRequest profitRequest){
		long result = 0l;
		if(Check.NuNObj(profitRequest)){
			LogUtil.info(LOGGER,"monthPredictProfit 参数为空.");
			return result;
		}

		if(Check.NuNStr(profitRequest.getHouseFid())){
			LogUtil.info(LOGGER,"monthPredictProfit houseFid为空.");
			return result;
		}

		if(profitRequest.getMonth() == 0){
			LogUtil.info(LOGGER,"monthPredictProfit 参数month为0.");
			return result;
		}

		if(profitRequest.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(profitRequest.getRoomFid())){
			LogUtil.info(LOGGER,"monthPredictProfit 合租房间，roomFid为空,roomFid:{}.",profitRequest.getRoomFid());
			return result;
		}

        Map<Integer, Date> cycleMonth = DateSplitUtil.getCycleMonth();
        Date monthDate = cycleMonth.get(profitRequest.getMonth());
        profitRequest.setBeginTime(DateSplitUtil.getFirstSecondOfMonth(monthDate));
        profitRequest.setEndTime(DateSplitUtil.getLastSecondOfMonth(monthDate));
		result = orderMoneyDao.countMonthPredictProfit(profitRequest);
		return result;
	}

}

