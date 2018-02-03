package com.ziroom.minsu.services.order.utils;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.order.entity.CancleOrderVo;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.IncomeSourceTypeEnum;
import com.ziroom.minsu.valenum.order.IncomeTypeEnum;
import com.ziroom.minsu.valenum.order.OrderCancleSwi;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.productrules.ProductRulesTonightDisEnum;
import com.ziroom.minsu.valenum.traderules.CheckOutStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.ziroom.minsu.services.common.utils.DateSplitUtil.getDateSplitFullDaySet;

/**
 * <p>收入表相关</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月3日
 * @since 1.0
 * @version 1.0
 */
public class FinanceMoneyUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceMoneyUtil.class);

	/**
	 * 房源的原始价格前缀
	 */
	public static final String price_org = "price_org";

	/**
	 * 统计处理打款计划表
	 * @author afi
	 * @param orderInfoVo
	 */
	public static int countHasFinace(OrderInfoVo orderInfoVo,List<FinancePayVouchersEntity> financeList){
		try {
			LogUtil.info(LOGGER,"【统计打款】 开始统计已经打款信息。。。。。");
			int total = 0;
			if(Check.NuNCollection(financeList)){
				return total;
			}
			if(Check.NuNObjs(orderInfoVo)){
				LogUtil.info(LOGGER,"【统计打款】 orderInfoVo is null");
				throw new BusinessException("orderInfoVo is null");
			}
			if(orderInfoVo.getCheckType() == null){
				LogUtil.info(LOGGER,"【统计打款】 checkType is null");
				throw new BusinessException("checkType is null");
			}
			for(FinancePayVouchersEntity finance: financeList){
				//当前的付款单肯定是有效的 如果出现无效的 就死了
				total += ValueUtil.getintValue(finance.getTotalFee());
			}
			LogUtil.info(LOGGER,"【统计打款】 total：{}",total);
			return total;
		}catch (Exception e){
			throw new BusinessException(e);
		}
	}



	/**
	 * 统计有效的金额信息
	 * @author afi
	 * @param incomeList
	 */
	public static int countHasIncome(List<FinanceIncomeEntity> incomeList,OrderInfoVo orderInfoVo)throws Exception{
		int total = 0;
		LogUtil.info(LOGGER,"【统计有效收入】 开始统计income。。。。。");
		if(Check.NuNCollection(incomeList)){
			LogUtil.info(LOGGER,"【统计有效收入】incomeList 为空，直接返回");
			return total;
		}
		if(Check.NuNObj(orderInfoVo)){
			throw new BusinessException("par is null") ;
		}
		if(orderInfoVo.getCheckType() == null){
			throw new BusinessException("checkType is null");
		}
		int i = 0;
		for(FinanceIncomeEntity income: incomeList){
			total += ValueUtil.getintValue(income.getTotalFee());
		}
		LogUtil.info(LOGGER,"【统计有效收入】 total:{}",total);
		return total;
	}

	/**
	 * 获取收入信息，所有的收入的结算信息都走这里
	 * @author afi
	 * @param config
	 * @param orderEntity
	 * @param hasCommMoney
	 * @param incomeType
	 */
	public static FinanceIncomeEntity getFinanceIncome(OrderConfigVo config, OrderInfoVo orderEntity,Integer hasCommMoney,IncomeTypeEnum incomeType){
		if(Check.NuNObj(orderEntity)){
			return null;
		}
		LogUtil.info(LOGGER,"【计算佣金】 开始计算佣金。。。。。");
		IncomeSourceTypeEnum incomeSourceType;
		int last = 0;
		String  uid = orderEntity.getLandlordUid();
		if(incomeType.getCode() == IncomeTypeEnum.USER_RENT_COMMISSION.getCode()){
			//处理 客户房租佣金
			// int total =  orderEntity.getUserCommMoney();
			int total =  transMoney2Comm(config, orderEntity.getRentalMoney() -  orderEntity.getDiscountMoney(),UserTypeEnum.TENANT);
			last = total - hasCommMoney;
			incomeSourceType = IncomeSourceTypeEnum.USER_SETTLEMENT;
			LogUtil.info(LOGGER,"计算用户的佣金 用户总佣金:{}，已经有效的佣金：{}，剩余的佣金{}",total,hasCommMoney,last);

		}else if(incomeType.getCode() == IncomeTypeEnum.LANDLORD_RENT_COMMISSION.getCode()){
			int total =  transMoney2Comm(config, orderEntity.getRentalMoney() -  orderEntity.getDiscountMoney(),UserTypeEnum.LANDLORD);
			last = total - hasCommMoney;
			incomeSourceType = IncomeSourceTypeEnum.USER_SETTLEMENT;
			LogUtil.info(LOGGER,"计算房东的佣金 房东总佣金:{}，已经有效的佣金：{}，剩余的佣金{}",total,hasCommMoney,last);

		}else if(incomeType.getCode() == IncomeTypeEnum.USER_PUNISH_COMMISSION.getCode()){
			// 用户违约金违约金
			last =  transMoney2Comm(config, orderEntity.getPenaltyMoney(), UserTypeEnum.LANDLORD);
			incomeSourceType = IncomeSourceTypeEnum.USER_SETTLEMENT;

			LogUtil.info(LOGGER,"计算用户违约金的佣金 总佣金:{}",last);

		}else if(incomeType.getCode() == IncomeTypeEnum.LANDLORD_PUNISH_COMMISSION.getCode()){
			//房东罚款佣金
			last =  transMoney2Comm(config, orderEntity.getPunishMoney(), UserTypeEnum.TENANT);
			incomeSourceType = IncomeSourceTypeEnum.FORCE_CANCEL;

			LogUtil.info(LOGGER,"房东的罚金给用户 总佣金:{}",last);

		}else if(incomeType.getCode() == IncomeTypeEnum.LANDLORD_PUNISH.getCode()){
			//全额房东罚款
			last =  orderEntity.getPunishMoney();
			incomeSourceType = IncomeSourceTypeEnum.FORCE_CANCEL;
			LogUtil.info(LOGGER,"房东的罚金全部给公司 总佣金:{}",last);

		}else {
			LogUtil.info(LOGGER,"incomeType is not support ：{}",incomeType.getCode());
			throw new BusinessException("incomeType is not support");
		}
		if (last == -1 || last == 1){
			last = 0;
			LogUtil.info(LOGGER,"当前金额正好差一分钱，直接兼容 ：{}",last);
		}
		if (last < 0){
			LogUtil.info(LOGGER,"异常的收入金额 ：{}",last);
			throw new BusinessException("异常的收入金额");
		}
		if(last>0){
			FinanceIncomeEntity incomeEntity = new FinanceIncomeEntity();
			incomeEntity.setIncomeSourceType(incomeSourceType.getCode());
			incomeEntity.setIncomeType(incomeType.getCode());
			incomeEntity.setOrderSn(orderEntity.getOrderSn());
			incomeEntity.setCityCode(orderEntity.getCityCode());
			incomeEntity.setPayUid(uid);
			//增加收入类型
			incomeEntity.setPayType(incomeType.getUserType().getUserType());
			incomeEntity.setTotalFee(last);
			return incomeEntity;
		}
		return null;
	}


	/**
	 * 将当前的金额转化成应的佣金
	 * @author afi
	 * @param config
	 * @param money
	 * @param userTypeEnum
	 * @return
	 */
	public static int transMoney2Comm(OrderConfigVo config, Integer money,UserTypeEnum userTypeEnum) {
		LogUtil.info(LOGGER,"【佣金转换】开始佣金转换。。。。。。转换类型：{}",userTypeEnum);
		//开始佣金转换
		if(Check.NuNObj(money)){
			LogUtil.info(LOGGER, "【佣金转换】开始佣金转换money:{}", money);
			return 0;
		}
		if(Check.NuNObj(userTypeEnum)){
			LogUtil.error(LOGGER,"request par is{}",userTypeEnum);
			throw new BusinessException("current orderSn is null on transMoney2Comm");
		}
		if(Check.NuNObj(config)){
			LogUtil.error(LOGGER,"config is null ");
			throw new BusinessException("config is null");
		}
		Double commissionRate = null;
		if(userTypeEnum.getUserType() == UserTypeEnum.TENANT.getUserType()){
			commissionRate = config.getCommissionRateUser();
		}else if(userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()) {
			if (Check.NuNStr(config.getLandCom())){
				commissionRate = config.returnRateLandlord();
			}else {
				//房东参加了免佣金的活动
				commissionRate = 0.00;
			}
		}
		if(!Check.NuNObjs(money,commissionRate)){
			Double commission =  BigDecimalUtil.mul(ValueUtil.getintValue(money), commissionRate);
			return commission.intValue();
		}else {
			LogUtil.error(LOGGER,"【佣金转换】 佣金类型异常 ");
			throw new BusinessException("par is null" );
		}
	}


	/**
	 * 实际发生的佣金 包含扩展金额
	 * @author afi
	 * @created 2016年4月22日
	 * @param config
	 * @param order
	 * @param extMoney 扩展金额
	 * @param userTypeEnum
	 * @see com.ziroom.minsu.valenum.common.UserTypeEnum
	 * @return
	 */
	public static int getRealCommMoneyExt(OrderConfigVo config,OrderInfoVo order,int realRentalMoney,Integer extMoney,UserTypeEnum userTypeEnum) {

		LogUtil.info(LOGGER,"【实际佣金】 开始计算实际佣金，额外金额extMoney：{}",ValueUtil.getintValue(extMoney));
		if( Check.NuNObj(userTypeEnum)){
			LogUtil.error(LOGGER,"【实际佣金】request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("current orderSn is null on getRealCommMoney");
		}
		if(Check.NuNObj(config)){
			LogUtil.error(LOGGER,"【实际佣金】config par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("config is null orderSN:"+order.getOrderSn());
		}
		//当前订单状态
		OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(order.getOrderStatus());
		if(Check.NuNObj(orderStatus)){
			LogUtil.error(LOGGER,"【实际佣金】request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("error orderStatus orderSN:"+order.getOrderSn());
		}
		//房租
		Integer  total =realRentalMoney;
		//当前扩展金额非空 收取扩展金额的佣金
		if(!Check.NuNObj(extMoney)){
			total = total + extMoney;
		}
		Double commissionRate = null;
		if(userTypeEnum.getUserType() == UserTypeEnum.TENANT.getUserType()){
			commissionRate = config.getCommissionRateUser();
		}else if(userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()) {
			commissionRate = config.returnRateLandlord();
		}
		if(!Check.NuNObjs(total,commissionRate)){
			Double commission =  BigDecimalUtil.mul(total, commissionRate);
			return commission.intValue();
		}else {
			LogUtil.error(LOGGER,"【实际佣金】total:{},commissionRate {}", total,commissionRate);
			throw new BusinessException("par is null  orderSN:"+order.getOrderSn());
		}
	}



	//    /**
	//     * 实际发生的佣金 包含扩展金额
	//     * @author afi
	//     * @created 2016年4月22日
	//     * @param config
	//     * @param order
	//     * @param extMoney 扩展金额
	//     * @param userTypeEnum
	//     * @see com.ziroom.minsu.valenum.common.UserTypeEnum
	//     * @return
	//     */
	//    public static int getRealCommMoneyExt(OrderConfigVo config,OrderInfoVo order,Integer extMoney,UserTypeEnum userTypeEnum) {
	//
	//        LogUtil.info(LOGGER,"【实际佣金】 开始计算实际佣金，额外金额extMoney：{}",ValueUtil.getintValue(extMoney));
	//        if( Check.NuNObj(userTypeEnum)){
	//            LogUtil.error(LOGGER,"【实际佣金】request par is {}", JsonEntityTransform.Object2Json(order));
	//            throw new BusinessException("current orderSn is null on getRealCommMoney");
	//        }
	//        if(Check.NuNObj(config)){
	//            LogUtil.error(LOGGER,"【实际佣金】config par is {}", JsonEntityTransform.Object2Json(order));
	//            throw new BusinessException("config is null");
	//        }
	//        //当前订单状态
	//        OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(order.getOrderStatus());
	//        if(Check.NuNObj(orderStatus)){
	//            LogUtil.error(LOGGER,"【实际佣金】request par is {}", JsonEntityTransform.Object2Json(order));
	//            throw new BusinessException("error orderStatus");
	//        }
	//        //房租
	//        Integer  total = 0;
	//        Integer rental = order.getRentalMoney();
	//        //当前违约金非空 并且是房东的时候收取违约金不收取用户违约金的佣金
	//        if(Check.NuNObj(order.getPenaltyMoney()) && userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()){
	//            total = rental + order.getPenaltyMoney();
	//        }else {
	//            total = rental;
	//        }
	//        //当前扩展金额非空 收取扩展金额的佣金
	//        if(!Check.NuNObj(extMoney)){
	//            total = total + extMoney;
	//        }
	//        Double commissionRate = null;
	//        if(userTypeEnum.getUserType() == UserTypeEnum.TENANT.getUserType()){
	//            commissionRate = config.getCommissionRateUser();
	//        }else if(userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()) {
	//            commissionRate = config.returnRateLandlord();
	//        }
	//        if(!Check.NuNObjs(total,commissionRate)){
	//            Double commission =  BigDecimalUtil.mul(total, commissionRate);
	//            return commission.intValue();
	//        }else {
	//            LogUtil.error(LOGGER,"【实际佣金】total:{},commissionRate {}", total,commissionRate);
	//            throw new BusinessException("par is null" );
	//        }
	//    }

	/**
	 * 获取当前房源的清洁费
	 * @param houseInfo
	 * @return
	 */
	public static Integer getHouseCleanMoney(OrderNeedHouseVo houseInfo){
		Integer cleanMoney = null;
		if(!Check.NuNObj(houseInfo)){
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseInfo.getRentWay());
			if(Check.NuNObj(rentWayEnum)){
				throw new BusinessException("houseInfo.getRentWay() is null");
			}
			if(rentWayEnum.getCode() == RentWayEnum.HOUSE.getCode()){
				cleanMoney = houseInfo.getHouseCleaningFees();
			}else if(rentWayEnum.getCode() == RentWayEnum.ROOM.getCode()){
				cleanMoney = houseInfo.getRoomCleaningFees();
			}else {
				throw new BusinessException("rentWay is wrong:"+houseInfo.getRentWay());
			}
		}
		return cleanMoney;

	}


	/**
	 * 获取当前房源的价格
	 * @param houseInfo
	 * @return
	 */
	public static Integer getHousePrice(OrderNeedHouseVo houseInfo){
		Integer price = null;
		if(!Check.NuNObj(houseInfo)){
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseInfo.getRentWay());
			if(Check.NuNObj(rentWayEnum)){
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
	 * 未入住 取消订单 获取当前是否有清洁费的产生
	 * @author afi
	 * @param order
	 * @return
	 */
	public static Integer getCleanMoney(OrderInfoVo order) {
		if(Check.NuNObj(order)){
			throw new BusinessException("order is null");
		}
		if(Check.NuNStr(order.getOrderSn())){
			LogUtil.error(LOGGER,"request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("current orderSn is null on getPenaltyMoney");
		}
		OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(order.getOrderStatus());
		if(Check.NuNObj(orderStatus)){
			LogUtil.error(LOGGER,"request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("error orderStatus");
		}

		Integer cleanMoneyCost  = 0;
		Integer cleanMoney = order.getCleanMoney();
		//当前的订单并没有清洁费的逻辑
		if (ValueUtil.getintValue(cleanMoney) == 0){
			return cleanMoneyCost;
		}
		//校验当前订单是否已经入住
		if (checkHasIn(order)){
			cleanMoneyCost = cleanMoney;
		}
		return cleanMoneyCost;
	}


	/**
	 * 
	 * 获取清洁费 状态
	 * 1. 无优惠卷 不可选择 不退换
	 * 2. 有优惠卷 ： A. 
	 *
	 * @author yd
	 * @created 2017年1月9日 下午9:37:03
	 *
	 * @param orderInfoVo
	 * @param curTime
	 * @param cancleOrderVo
	 * @return
	 */
	public static int getCleanMoneySta(OrderInfoVo orderInfoVo,Date curTime,CancleOrderVo cancleOrderVo){

		if(Check.NuNObj(orderInfoVo)){
			LogUtil.error(LOGGER, "参数错误");
			throw new BusinessException("orderInfoVo is null on getCurDayMoneySta");
		}

		Date startTime = orderInfoVo.getStartTime();
		Date endTime = orderInfoVo.getEndTime();
		if(Check.NuNObjs(startTime,endTime,curTime)){
			LogUtil.error(LOGGER, "request par is {}", JsonEntityTransform.Object2Json(orderInfoVo));
			throw new BusinessException("time is null on getCurDayMoneySta");
		}

		Integer cleanMoney = orderInfoVo.getCleanMoney();

		//入住之前
		if (curTime.before(startTime)){
			return OrderCancleSwi.CLICEK_NO_SELCT.getCode();
		}

		//当前的订单并没有清洁费的逻辑
		if (ValueUtil.getintValue(cleanMoney) == 0){
			return OrderCancleSwi.CLICEK_NO_SELCT_NO.getCode();
		}



		//入住当天  可以选择
		Date curTimeCheckInDawn = DateSplitUtil.connectDate(startTime,"23:59:59");

		if(curTime.before(curTimeCheckInDawn)){
			return OrderCancleSwi.CLICEK_SELCT_NO.getCode();
		}

		return OrderCancleSwi.CLICEK_NO_SELCT_NO.getCode();
	}


	/**
	 *
	 * 获取当天房费是否可退 状态
	 *
	 * @author yd
	 * @created 2017年1月5日 下午5:24:12
	 *
	 * @param orderInfoVo
	 * @return
	 */
	public static int getCurDayMoneySta(OrderInfoVo orderInfoVo,Date curTime,CancleOrderVo cancleOrderVo){

		if(Check.NuNObj(orderInfoVo)){
			LogUtil.error(LOGGER, "参数错误");
			throw new BusinessException("orderInfoVo is null on getCurDayMoneySta");
		}

		Date startTime = orderInfoVo.getStartTime();
		Date endTime = orderInfoVo.getEndTime();
		if(Check.NuNObjs(startTime,endTime,curTime)){
			LogUtil.error(LOGGER, "request par is {}", JsonEntityTransform.Object2Json(orderInfoVo));
			throw new BusinessException("time is null on getCurDayMoneySta");
		}
		if(curTime.before(startTime)){
			cancleOrderVo.setIsHascurDayMoney(0);
			cancleOrderVo.setCurDay(DateUtil.dateFormat(startTime, "yyyy年MM月dd日"));
			//还未入住，没有实际房租
			return OrderCancleSwi.CLICEK_NO_SELCT.getCode();
		}

		Date endBeforeDawn = DateSplitUtil.connectDate(endTime, "00:00:00");
		if(curTime.after(endBeforeDawn)){
			cancleOrderVo.setIsHascurDayMoney(1);

			return OrderCancleSwi.CLICEK_NO_SELCT_NO.getCode();
		}
		String checkInStr = DateSplitUtil.getDateHMS(startTime);

		Date startBeforeDawn = DateSplitUtil.connectDate(startTime, "00:00:00");

		if(curTime.after(startBeforeDawn)){
			checkInStr = DateSplitUtil.getDateHMS(endTime);
		}

		Date curTimeCheckIn = DateSplitUtil.connectDate(curTime,checkInStr);
		// 当前时间在退房时间之前
		if(curTime.before(curTimeCheckIn)){
			cancleOrderVo.setIsHascurDayMoney(0);
			return OrderCancleSwi.CLICEK_NO_SELCT.getCode();
		}else{
			cancleOrderVo.setIsHascurDayMoney(1);
		}
		return OrderCancleSwi.CLICEK_SELCT_NO.getCode();
	}


	/**
	 * 
	 * 获取客服可支配金额
	 *
	 * @author yd
	 * @created 2017年1月13日 下午4:41:02
	 *
	 * @param orderInfoVo
	 * @param curTime
	 * @param cancleOrderVo
	 * @return
	 */
	public static int getStaffControMoney(OrderInfoVo orderInfoVo,Date curTime,CancleOrderVo cancleOrderVo,int rentalMoney,Map<String,Integer> priceMap,OrderConfigVo config ){


		if(Check.NuNObj(orderInfoVo)){
			LogUtil.error(LOGGER, "参数错误");
			throw new BusinessException("orderInfoVo is null on getCurDayMoneySta");
		}

		Date startTime = orderInfoVo.getStartTime();
		Date endTime = orderInfoVo.getEndTime();
		if(Check.NuNObjs(startTime,endTime,curTime)){
			LogUtil.error(LOGGER, "request par is {}", JsonEntityTransform.Object2Json(orderInfoVo));
			throw new BusinessException("time is null on getCurDayMoneySta");
		}
		//剩余金额公式：支付金额  + 优惠券 + 活动金额 - 租客支付佣金金额  - 实际发生房租金额 - 清洁费   (客服可支配金额)
		int lastMoney = orderInfoVo.getPayMoney() + orderInfoVo.getCouponMoney() + orderInfoVo.getActMoney() - orderInfoVo.getUserCommMoney() - rentalMoney - orderInfoVo.getCleanMoney() ;

		LogUtil.info(LOGGER, "客服可支配金额lastMoney=支付金额: {} + 优惠券 :{}- 租客支付佣金金额 :{} - 实际发生房租金额:{} - 清洁费   (客服可支配金额):{}",orderInfoVo.getPayMoney() ,orderInfoVo.getCouponMoney() , orderInfoVo.getUserCommMoney() , rentalMoney , orderInfoVo.getCleanMoney());
		String checkInStr = DateSplitUtil.getDateHMS(startTime);

		Date startBeforeDawn = DateSplitUtil.connectDate(startTime, "00:00:00");

		if(curTime.after(startBeforeDawn)){
			checkInStr = DateSplitUtil.getDateHMS(endTime);
		}
		Date curTimeCheckIn = DateSplitUtil.connectDate(curTime,checkInStr);

		// 当前时间在退房时间之前, 客服可支配金额=lastMoney-房客当天房费-房客当天房费佣金
		if(curTime.before(curTimeCheckIn)||curTime.before(startTime)){

			Date time = curTime;
			if(curTime.before(startTime)){
				time = startTime;
			}
			if(!Check.NuNMap(priceMap)){
				cancleOrderVo.setCurDayMoney(0);
				Integer cruDayMo = priceMap.get(DateUtil.dateFormat(time, "yyyy-MM-dd"));
				if(Check.NuNObj(cruDayMo)){
					cruDayMo = 0;
				}
				LogUtil.info(LOGGER, "客服可支配金额计算：lastMoney-curDayMoney:{}-{}",lastMoney,cruDayMo);
				lastMoney = lastMoney -cruDayMo;
				if(lastMoney<0){
					LogUtil.error(LOGGER, "客服可支配金额错误lastMoney:{}",lastMoney);
					throw new BusinessException("lastMoney error on getStaffControMoney");
				}
			}

		}
		return lastMoney;
	}
	/**
	 * 校验当亲订单是否已经入住
	 * @author
	 * @param order
	 * @return
	 */
	public static boolean checkHasIn(OrderInfoVo order){
		boolean checkInFalg = false;
		try {
			//获取当前的入住时间
			String checkIntime = order.getCheckInTime();
			checkIntime = checkIntime.replace(" ", "");
			//校验是否入住的逻辑
			Date checkinTime = DateUtil.parseDate(DateUtil.dateFormat(order.getStartTime()) +" "+ checkIntime, "yyyy-MM-dd HH:mm:ss");
			if (checkinTime.getTime() < new Date().getTime()){
				checkInFalg = true;
			}
		}catch (Exception e){
			throw new BusinessException(e);
		}
		return checkInFalg;
	}


	/**
	 * 未入住 取消订单 获取 违约金额  已支付 待入住 取消订单时，如提取一天取消，会产生违约金
	 * @author afi
	 * @created 2016年4月2日
	 * @param checkOutStrategy
	 * @param order
	 * @param priceMap 每天的价格
	 * @return
	 */
	public static Integer getPenaltyMoney(CheckOutStrategy checkOutStrategy,OrderInfoVo order,Map<String,Integer> priceMap) {
		if(Check.NuNObj(order)){
			throw new BusinessException("order is null");
		}
		if(Check.NuNStr(order.getOrderSn())){
			LogUtil.error(LOGGER,"request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("current orderSn is null on getPenaltyMoney");
		}
		OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(order.getOrderStatus());
		if(Check.NuNObj(orderStatus)){
			LogUtil.error(LOGGER,"request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("error orderStatus");
		}
		Integer penaltyMoney = null;
		try {
			if(orderStatus.getOrderStatus() == OrderStatusEnum.WAITING_CONFIRM.getOrderStatus()
					|| orderStatus.getOrderStatus() == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()){
				//取消订单的违约金
				if (checkOutStrategy.getChangzuFlag()){
					//长租
					penaltyMoney = getCanclePenaltyMoneyChangzu(checkOutStrategy, order,priceMap);
				}else {
					//普通订单的取消
					penaltyMoney = getCanclePenaltyMoney(checkOutStrategy, order,priceMap);
				}
			}else if(orderStatus.getOrderStatus() == OrderStatusEnum.CHECKED_IN.getOrderStatus()
					|| orderStatus.getOrderStatus() == OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()){
				//退房的违约金
				if (checkOutStrategy.getChangzuFlag()){
					//长租提前退房
					penaltyMoney = getCheckOutPenaltyMoneyChangzu(checkOutStrategy,order,priceMap);
				}else {
					//普通提前退房
					penaltyMoney = getCheckOutPenaltyMoney(checkOutStrategy,order,priceMap);
				}
			}else {
				LogUtil.error(LOGGER,"request par is {}", JsonEntityTransform.Object2Json(order));
				throw  new BusinessException("error orderStatus");
			}
		}catch (Exception e){
			throw new BusinessException(e);
		}
		return penaltyMoney;
	}

	/**
	 * 已入住  退房  获取 违约金额
	 * 已支付 已入住 退房时，提前一天或两天退房，会产生违约金
	 * @author afi
	 * @created 2016年4月2日
	 * @param checkOutStrategy
	 * @param order
	 * @param
	 * @return
	 */
	public static Integer getCheckOutPenaltyMoney(CheckOutStrategy checkOutStrategy,OrderInfoVo order,Map<String,Integer> priceMap) throws Exception{
		if(Check.NuNObj(checkOutStrategy)){
			LogUtil.info(LOGGER, "【获取退房违约金】 orerSn:",order.getOrderSn());
			throw new BusinessException("checkOutStrategy is null");
		}
		int penaltyMoney = 0;
		//获取当前的提前退房的违约金的开始天
		Date punishDay = getCheckOutPunishDay(checkOutStrategy,order);
		//获取当前的退房时间
		String checkOutTime = order.getCheckOutTime();
		//去掉空格
		checkOutTime = checkOutTime.replace(" ", "");
		//取消逻辑
		Date shouldTime = DateUtil.parseDate(DateUtil.dateFormat(order.getEndTime()) + " " + checkOutTime, "yyyy-MM-dd HH:mm:ss");
		Date freeTime = DateSplitUtil.getYesterday(shouldTime);
		if(freeTime.getTime() > checkOutStrategy.getDealTime().getTime()){
			//获取违约金的天数
			int dayCount = checkOutStrategy.getSuffixCost();
			penaltyMoney = getPenaltyPriceFromMap(punishDay,dayCount,priceMap);
			if (Check.NuNObj(checkOutStrategy.getRealRentalMoney())){
				LogUtil.error(LOGGER, "【获取退房违约金】 orerSn:{}",order.getOrderSn());
				throw new BusinessException("当前的实际房租未设置，请在调用违约金之前设置当前的实际房租");
			}
			/**
			 * 退房之后，计算剩余的房费的百分比
			 * 计算公式  预计房租 - 实际房租 - 违约金
			 */
			int moneyLast = order.getRentalMoney() - order.getDiscountMoney() - penaltyMoney - ValueUtil.getintValue(checkOutStrategy.getRealRentalMoney());
			if (moneyLast < 0){
				moneyLast = 0;
			}
			penaltyMoney += BigDecimalUtil.mul(moneyLast,checkOutStrategy.getCheckOutLastPercent());
		}
		return penaltyMoney;

	}


	/**
	 * 计算违约金
	 * @author afi
	 * @param punishDay
	 * @param dayCount
	 * @param priceMap
	 * @return
	 */
	private static int getPenaltyPriceFromMap(Date punishDay,int dayCount,Map<String,Integer> priceMap){
		if (Check.NuNObj(punishDay)){
			throw new BusinessException("异常的开始时间");
		}
		if (Check.NuNObj(priceMap)){
			throw new BusinessException("异常的价格map");
		}
		int penaltyMoney = 0;
		if (dayCount > 0){
			int tmpMoney = 0;
			//设置违约金
			for (int i = 0; i < dayCount; i++) {
				String key = DateUtil.dateFormat(punishDay);
				if (priceMap.containsKey(key)){
					LogUtil.info(LOGGER, "【违约金】 第{}天：金额：{}",i,ValueUtil.getintValue(priceMap.get(key)));
					penaltyMoney += ValueUtil.getintValue(priceMap.get(key));
					tmpMoney = ValueUtil.getintValue(priceMap.get(key));
					punishDay = DateSplitUtil.getTomorrow(punishDay);
				}else {
					LogUtil.info(LOGGER, "【违约金】 第{}天：金额：{}",i,tmpMoney);
					penaltyMoney +=tmpMoney;
				}
			}
		}
		return penaltyMoney;
	}

	/**
	 * 获取当前订单的结算违约天
	 * @param checkOutStrategy
	 * @param order
	 * @return
	 * @throws Exception
	 */
	private static Date getCheckOutPunishDay(CheckOutStrategy checkOutStrategy,OrderInfoVo order)throws Exception{
		//获取当前的退房时间
		String checkIntime = order.getCheckInTime();
		//去掉空格
		checkIntime = checkIntime.replace(" ", "");
		Date now = checkOutStrategy.getDealTime();
		Date shouldCheckInTime = DateUtil.parseDate(DateUtil.dateFormat(now) + " " + checkIntime, "yyyy-MM-dd HH:mm:ss");
		if (now.before(order.getStartTime())){
			LogUtil.error(LOGGER, "【获取退房违约金】[长租]当前时间在入住之前不能触发退房逻辑： orerSn:",order.getOrderSn());
			throw new BusinessException("当前时间在入住之前不能触发退房逻辑");
		}
		Date punishDay = now;
		if (punishDay.after(shouldCheckInTime)){
			//今天已经入住，则下一天是明天的房租
			punishDay = DateSplitUtil.getTomorrow(punishDay);
		}
		return punishDay;
	}


	/**
	 * 已入住  退房  获取 违约金额 -[长租]
	 * 已支付 已入住 退房时，提前一天或两天退房，会产生违约金
	 * @author afi
	 * @created 2016年4月2日
	 * @param checkOutStrategy
	 * @param order
	 * @param
	 * @return
	 */
	public static Integer getCheckOutPenaltyMoneyChangzu(CheckOutStrategy checkOutStrategy,OrderInfoVo order,Map<String,Integer> priceMap) throws Exception{
		if(Check.NuNObj(checkOutStrategy)){
			LogUtil.info(LOGGER, "【获取退房违约金】[长租] orerSn:",order.getOrderSn());
			throw new BusinessException("checkOutStrategy is null");
		}
		int penaltyMoney = getCheckOutPenaltyMoney(checkOutStrategy,order,priceMap);
		//获取当前的提前退房的违约金的开始天
		Date punishDay = getCheckOutPunishDay(checkOutStrategy,order);
		//获取当前的长租设置期限
		int penaltyMoneyLimit = getPenaltyPriceFromMap(punishDay,checkOutStrategy.getChangzuCount(),priceMap);
		LogUtil.info(LOGGER, "【获取退房违约金】[长租] orerSn:{},总违约金：{},违约金上限：{},",order.getOrderSn(),penaltyMoney,penaltyMoneyLimit);
		if (penaltyMoney > penaltyMoneyLimit){
			penaltyMoney = penaltyMoneyLimit;
			LogUtil.info(LOGGER, "【获取退房违约金】[长租] 超过了违约金的上限,依照违约金上限为准");
		}
		return penaltyMoney;
	}


	/**
	 * 获取取消订单的违约金-长租
	 * @author afi
	 * @param order
	 * @return
	 * @throws Exception
	 */
	private static int getCanclePenaltyMoneyChangzu(CheckOutStrategy checkOutStrategy,OrderInfoVo order,Map<String,Integer> priceMap) throws Exception{
		if(Check.NuNObj(checkOutStrategy)){
			LogUtil.info(LOGGER, "【获取取消订单违约金】[长租] orerSn:",order.getOrderSn());
			throw new BusinessException("checkOutStrategy is null");
		}
		if(Check.NuNObj(order)){
			LogUtil.info(LOGGER, "【获取取消订单违约金】[长租] orerSn:",order.getOrderSn());
			throw new BusinessException("order is null");
		}
		//当前的取消订单
		int penaltyMoney = getCanclePenaltyMoney(checkOutStrategy,order,priceMap);
		//获取当前的长租设置期限
		int penaltyMoneyLimit = getPenaltyPriceFromMap(order.getStartTime(),checkOutStrategy.getChangzuCount(),priceMap);
		LogUtil.info(LOGGER, "【获取取消订单违约金】[长租] orerSn:{},总违约金：{},违约金上限：{},",order.getOrderSn(),penaltyMoney,penaltyMoneyLimit);
		if (penaltyMoney > penaltyMoneyLimit){
			penaltyMoney = penaltyMoneyLimit;
			LogUtil.info(LOGGER, "【获取取消订单违约金】[长租] 超过了违约金的上限,依照违约金上限为准");
		}
		return penaltyMoney;
	}


	/**
	 * 获取取消订单的违约金
	 * @author afi
	 * @param order
	 * @return
	 * @throws Exception
	 */
	private static int getCanclePenaltyMoney(CheckOutStrategy checkOutStrategy,OrderInfoVo order,Map<String,Integer> priceMap) throws Exception{
		if(Check.NuNObj(checkOutStrategy)){
			LogUtil.info(LOGGER, "【获取取消订单违约金】 orerSn:",order.getOrderSn());
			throw new BusinessException("checkOutStrategy is null");
		}
		if(Check.NuNObj(order)){
			LogUtil.info(LOGGER, "【获取取消订单违约金】 orerSn:",order.getOrderSn());
			throw new BusinessException("order is null");
		}
		//获取当前的入住时间
		String checkIntime = order.getCheckInTime();
		checkIntime = checkIntime.replace(" ", "");
		//取消逻辑
		Date shouldTime = DateUtil.parseDate(DateUtil.dateFormat(order.getStartTime()) +" "+ checkIntime, "yyyy-MM-dd HH:mm:ss");
		Date freeTime = DateSplitUtil.jumpDate(shouldTime, -checkOutStrategy.getPreFreeDayCount());
		int punishDayCount = 0;

		int penaltyMoney = 0;
		Date punishDay = order.getStartTime();
		if(freeTime.getTime() > checkOutStrategy.getDealTime().getTime()){
			//无责任取消
			punishDayCount = ValueUtil.getintValue(checkOutStrategy.getFreeCost());
			penaltyMoney = getPenaltyPriceFromMap(punishDay,punishDayCount,priceMap);
			/**
			 * 取消订单之后，计算剩余的房费的百分比
			 * 计算公式  预计房租 - 违约金
			 */
			int moneyLast = order.getRentalMoney() - order.getDiscountMoney() - penaltyMoney;
			if (moneyLast < 0){
				moneyLast = 0;
			}
			penaltyMoney += BigDecimalUtil.mul(moneyLast,checkOutStrategy.getCancelFreePercent());
		}else {
			//非无责任取消
			punishDayCount = ValueUtil.getintValue(checkOutStrategy.getPreCost());
			penaltyMoney = getPenaltyPriceFromMap(punishDay,punishDayCount,priceMap);

			/**
			 * 取消订单之后，计算剩余的房费的百分比
			 * 计算公式  预计房租 - 违约金
			 */
			int moneyLast = order.getRentalMoney() - order.getDiscountMoney() - penaltyMoney;
			if (moneyLast < 0){
				moneyLast = 0;
			}
			penaltyMoney += BigDecimalUtil.mul(moneyLast,checkOutStrategy.getCancelLastPercent());
		}
		LogUtil.info(LOGGER, "【获取取消订单违约金】 orerSn:{},总违约金：{}",order.getOrderSn(),penaltyMoney);
		return penaltyMoney;
	}


	/**
	 * 实际发生的房租，享受折扣
	 * @author afi
	 * @created 2016年4月2日
	 * @param order
	 * @return
	 */
	public static int getRealRentalMoney(OrderConfigVo config,OrderInfoVo order,Date limitDate,Map<String,Integer> priceMap) {

		LogUtil.info(LOGGER, "【计算当前房租】 开始。。。。");

		if(Check.NuNStr(order.getOrderSn())){
			LogUtil.error(LOGGER,"request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("current orderSn is null on getRentalMoney");
		}
		OrderStatusEnum orderStatus = OrderStatusEnum.getOrderStatusByCode(order.getOrderStatus());
		if(Check.NuNObj(orderStatus)){
			LogUtil.error(LOGGER,"request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("error orderStatus");
		}
		Date startTime = order.getStartTime();
		Date endTime = order.getEndTime();
		if(Check.NuNObjs(startTime,endTime,limitDate)){
			LogUtil.error(LOGGER, "request par is {}", JsonEntityTransform.Object2Json(order));
			throw new BusinessException("time is null on getRentalMoney");
		}
		if(limitDate.before(startTime)){
			//还未入住，没有实际房租
			LogUtil.info(LOGGER,"【计算当前房租】 还为入住，当前的房租金额：{}",0);
			return 0;
		}
		if(orderStatus.getOrderStatus() == OrderStatusEnum.CHECKED_IN.getOrderStatus()
				|| orderStatus.getOrderStatus() == OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()){

			LogUtil.info(LOGGER,"【计算当前房租】 当前状态为已入住：{}",orderStatus.getOrderStatus());
			//已入住 计算入住的房租
			if(limitDate.after(DateSplitUtil.getYesterday(order.getEndTime()))){
				//已经超过了提前退房的24小时，默认已经注满
				LogUtil.info(LOGGER,"【计算当前房租】 超过提前退房24小时，默认已经注满,房租：{}",order.getRentalMoney());
				return order.getRentalMoney();
			}else{
				//直接返回应付的房租
				return getRentalMoneyBetweenDays(config, order, startTime, limitDate,priceMap);
			}
		}else{
			//非已入住，要不未入住，要不已经结算，直接返回房租金额即可
			LogUtil.info(LOGGER,"【计算当前房租】 非已入住，要不未入住，要不已经结算，直接返回房租金额即可：{}",order.getRentalMoney());
			return order.getRentalMoney();
		}
	}


	/**
	 * 计算折扣
	 * @author
	 * @param config
	 * @param order
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static Integer getDiscountMoneyBetweenDays(OrderConfigVo config,OrderInfoVo order,Date startTime,Date endTime,Map<String,Integer> priceMap){
		LogUtil.info(LOGGER,"【计算当前折扣金额】 开始计算折扣金额,");
		int discountMoney = 0;
		if(Check.NuNObj(priceMap)){
			//兼容当前的价格
			priceMap = new HashMap<>();
		}
		LogUtil.info(LOGGER,"【计算当前折扣金额】 当前的价格map：{}",JsonEntityTransform.Object2Json(priceMap));
		Set<String> daySet = new HashSet<>();
		if(startTime.before(endTime)){
			daySet = DateSplitUtil.getDateSplitFullDaySet(startTime, endTime);
		}
		if(endTime.after(DateSplitUtil.connectDate(endTime,order.getCheckOutTime()))){
			//退房时间超出了当前的退房时间
			LogUtil.info(LOGGER, "【计算当前折扣金额】退房时间超出了当前的退房时间,今天也需要计算折扣");
			daySet.add(DateUtil.dateFormat(endTime));
		}else {
			LogUtil.info(LOGGER, "【计算当前折扣金额】退房时间未超过当前的退房时间,今天不算折扣");
		}
		LogUtil.info(LOGGER, "【计算当前折扣金额】 房租的天数列表:{}",JsonEntityTransform.Object2Json(daySet));
		for(String day: daySet){
			int disEle = ValueUtil.getintValue(priceMap.get(FinanceMoneyUtil.price_org+day)) -ValueUtil.getintValue(priceMap.get(day)) ;
			discountMoney += disEle;
		}
		LogUtil.info(LOGGER,"【计算当前折扣金额】.............当前折扣：{}",discountMoney);
		return discountMoney;
	}



	/**
	 * 计算房租
	 * @author
	 * @param config
	 * @param order
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private static Integer getRentalMoneyBetweenDays(OrderConfigVo config,OrderInfoVo order,Date startTime,Date endTime,Map<String,Integer> priceMap){

		LogUtil.info(LOGGER,"【计算当前房租】 开始计算房租,");
		if(Check.NuNObj(order.getPrice())){
			LogUtil.info(LOGGER, "price is null");
			throw new BusinessException("price is null");
		}
		int cost = 0;
		if(Check.NuNObj(priceMap)){
			//兼容当前的价格
			priceMap = new HashMap<>();
		}
		LogUtil.info(LOGGER,"【计算当前房租】 当前的特殊价格map：{}",JsonEntityTransform.Object2Json(priceMap));
		Set<String> daySet = getDateSplitFullDaySet(startTime, endTime);
		if(endTime.after(DateSplitUtil.connectDate(endTime,order.getCheckOutTime()))){
			//退房时间超出了当前的退房时间
			LogUtil.info(LOGGER, "【计算当前房租】退房时间超出了当前的退房时间,今天也需要算钱");
			daySet.add(DateUtil.dateFormat(endTime));
		}else {
			LogUtil.info(LOGGER, "【计算当前房租】退房时间未超过当前的退房时间,今天不算钱");
		}
		LogUtil.info(LOGGER, "【计算当前房租】 房租的天数列表:{}",JsonEntityTransform.Object2Json(daySet));
		for(String day: daySet){
			if(priceMap.containsKey(day)){
				cost += priceMap.get(day);
			}else{
				cost += order.getPrice();
			}
		}
		LogUtil.info(LOGGER,"【计算当前房租】.............实际房租：{}",cost);
		return cost;
	}


	/**
	 * 填充夹缝价格
	 * @param start
	 * @param end
	 * @param basePrice
	 * @param priceMap
	 * @param creviceConfigList
	 */
	public static void   fillCrevice2SpecialPrices(Date start,Date end,int basePrice,Map<String,Integer> priceMap,List<OrderConfigEntity>  creviceConfigList){
		if (Check.NuNCollection(creviceConfigList)){
			//当前的夹缝配置为空,直接返回
			return;
		}
		List<String> codes = new ArrayList<>();
		//当前的折扣天数
		Map<Integer, Double> cuts = new TreeMap<>();
		OrderConfigEntity tonightDisCon = null;
		for (OrderConfigEntity configEntity : creviceConfigList) {
			ProductRulesEnum020 productRulesEnum0020 = ProductRulesEnum020.getByCode(configEntity.getConfigCode());
			if (Check.NuNObj(productRulesEnum0020)) {
				if(configEntity.getConfigCode().equals(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode())){
					tonightDisCon = configEntity;
				}
				continue;
			}
			codes.add(configEntity.getConfigCode());
			cuts.put(productRulesEnum0020.getDayNum(), ValueUtil.getdoubleValue(configEntity.getConfigValue()));
		}
		if (Check.NuNObj(ProductRulesEnum020.getMaxDay(codes))&&Check.NuNObj(tonightDisCon)) {
			LogUtil.info(LOGGER, "【夹缝价格】 未匹配任何价格: 夹缝规则 {}", JsonEntityTransform.Object2Json(creviceConfigList));
			return;
		}
		ProductRulesEnum020 first = ProductRulesEnum020.getFristRule(codes);
		//第一天 是夹心当天 是老订单 兼容老逻辑  新订单 是 今夜特价
		Double fristCut  = null;
		if (!Check.NuNObj(first)) {
			//第一天的折扣 今日特惠
			fristCut = cuts.get(first.getDayNum());
		}else{
			//今夜特价
			if(!Check.NuNObj(tonightDisCon)){
				fristCut =ValueUtil.getdoubleValue(tonightDisCon.getConfigValue());
			}
		}
		//第一天折扣价格
		if(!Check.NuNObj(fristCut)){
			LogUtil.info(LOGGER, "【计算第一天特殊价格】fristCut={}", fristCut);
			int firstDayPrice = basePrice;
			if (priceMap.containsKey(DateUtil.dateFormat(start))){
				firstDayPrice = priceMap.get(DateUtil.dateFormat(start));
			}
			Double firstDayPriceDou = BigDecimalUtil.mul(firstDayPrice, fristCut == null ? 1 : fristCut);
			priceMap.put(DateUtil.dateFormat(start),firstDayPriceDou.intValue());
		}

		//非第一天的折扣
		Double realCut = null;
		for (OrderConfigEntity configEntity : creviceConfigList) {
			if(configEntity.getConfigCode().equals(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode())){
				continue;
			}
			if (!Check.NuNObj(first)){
				if (!configEntity.getConfigCode().equals(first.getValue())){
					realCut = ValueUtil.getdoubleValue(configEntity.getConfigValue());
					break;
				}
			}else {
				realCut = ValueUtil.getdoubleValue(configEntity.getConfigValue());
				break;
			}
		}
		List<Date> dayList = DateSplitUtil.dateSplit(start, end);
		if (Check.NuNCollection(dayList)) {
			throw new BusinessException("异常的开始时间结束时间");
		}


		for (Date date : dayList) {
			int  dayPrice = basePrice;
			if ((!Check.NuNObj(tonightDisCon)||!Check.NuNObj(first))&& DateUtil.dateFormat(date).equals(DateUtil.dateFormat(start))) {
				continue;
			}
			if (Check.NuNObj(realCut)){
				break;
			}
			String ele = DateUtil.dateFormat(date);
			if (priceMap.containsKey(ele)){
				dayPrice = priceMap.get(ele);
			}
			Double dayPriceDou = BigDecimalUtil.mul(dayPrice, realCut == null ? 1 : realCut);
			priceMap.put(DateUtil.dateFormat(date),dayPriceDou.intValue());
		}
	}

	/**
	 * 
	 * TODO
	 *
	 * @author yd
	 * @created 2017年3月9日 上午10:44:38
	 *
	 * @param start
	 * @param end
	 * @param realEndTime
	 * @param basePrice
	 * @param specialPriceMap
	 * @param creviceConfigList
	 * @param discountPriceMap
	 */
	public static void  fillSandwichDiscountMoneyMap(Date start,Date end,Date realEndTime,int basePrice,
			Map<String,Integer> specialPriceMap,List<OrderConfigEntity>  creviceConfigList,Map<String,Integer> discountPriceMap){

		if (Check.NuNCollection(creviceConfigList)){
			//当前的夹缝配置为空,直接返回
			return;
		}
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
		if (Check.NuNObj(ProductRulesEnum020.getMaxDay(codes))) {
			LogUtil.info(LOGGER, "【夹缝价格】 未匹配任何价格: 夹缝规则 {}", JsonEntityTransform.Object2Json(creviceConfigList));
			return;
		}
		ProductRulesEnum020 first = ProductRulesEnum020.getFristRule(codes);

		if (!Check.NuNObj(first)) {
			//第一天的折扣
			Double fristCut = cuts.get(first.getDayNum());
			int firstDayPrice = basePrice;
			if (specialPriceMap.containsKey(DateUtil.dateFormat(start))){
				firstDayPrice = specialPriceMap.get(DateUtil.dateFormat(start));
			}
			if(!Check.NuNObj(fristCut)){
				Double firstDayPriceDou = BigDecimalUtil.mul(firstDayPrice, BigDecimalUtil.sub(1,fristCut));
				discountPriceMap.put(DateUtil.dateFormat(start),firstDayPriceDou.intValue());
			}

		}

		//非第一天的折扣
		Double realCut = null;
		for (OrderConfigEntity configEntity : creviceConfigList) {
			if (!Check.NuNObj(first)){
				if (!configEntity.getConfigCode().equals(first.getValue())){
					realCut = ValueUtil.getdoubleValue(configEntity.getConfigValue());
					break;
				}
			}else {
				realCut = ValueUtil.getdoubleValue(configEntity.getConfigValue());
				break;
			}
		}
		List<Date> dayList = DateSplitUtil.dateSplit(start, realEndTime);
		if (Check.NuNCollection(dayList)) {
			throw new BusinessException("异常的开始时间结束时间");
		}

		for (Date date : dayList) {
			int  dayPrice = basePrice;
			if (!Check.NuNObj(first) && DateUtil.dateFormat(date).equals(DateUtil.dateFormat(start))) {
				continue;
			}
			if (Check.NuNObj(realCut)){
				break;
			}
			String ele = DateUtil.dateFormat(date);
			if (specialPriceMap.containsKey(ele)){
				dayPrice = specialPriceMap.get(ele);
			}
			Double dayPriceDou = BigDecimalUtil.mul(dayPrice, BigDecimalUtil.sub(1,realCut));
			discountPriceMap.put(DateUtil.dateFormat(date),dayPriceDou.intValue());
		}

	}
	/**
	 * 获取当前的折扣价格
	 * @param start
	 * @param end
	 * @param basePrice
	 * @param cut
	 * @param priceMap
	 * @return
	 */
	public static Map<String,Integer>  getDayCutPricesMap(Date start,Date end,int basePrice,Double cut,Map<String,Integer> priceMap){
		Map<String,Integer> priceMapRst = new HashMap<>();
		//获取
		try{
			if (Check.NuNObjs(start,end)){
				throw new BusinessException("开始时间或者结束时间为空");
			}

			List<Date> dayList = DateSplitUtil.dateSplit(start,end);
			if (Check.NuNCollection(dayList)){
				throw new BusinessException("异常的开始时间结束时间");
			}
			double  cutValue = 0;
			//获取当前的房源的折扣
			if (ValueUtil.getdoubleValue(cut) > 0){
				//当前订单满足折扣
				cutValue = ValueUtil.getdoubleValue(cut);
			}
			int cutSum = 0;
			//当前房源的折扣金额
			int cutAll = ValueUtil.getintValue(priceMap.get("discountMoney"));
			int i = 0;
			for(Date date: dayList){
				i ++;
				int price = basePrice;
				if(priceMap.containsKey(DateUtil.dateFormat(date))){
					price = priceMap.get(DateUtil.dateFormat(date));
				}
				priceMapRst.put(price_org+DateUtil.dateFormat(date), price);
				if (i == dayList.size()){
					//最后一天用所有的折扣减去
					int cutEle = cutAll- cutSum;
					price -= cutEle;
				}else {
					Double cutEle;
					if (cutValue > 0){
						cutEle = BigDecimalUtil.mul(price,BigDecimalUtil.sub(1,cutValue));
					}else {
						cutEle = 0.0;
					}
					cutSum += cutEle.intValue();
					price -= cutEle.intValue();
				}
				priceMapRst.put(DateUtil.dateFormat(date), price);
			}
			return priceMapRst;
		}catch (Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			throw new BusinessException(e);
		}
	}
}


