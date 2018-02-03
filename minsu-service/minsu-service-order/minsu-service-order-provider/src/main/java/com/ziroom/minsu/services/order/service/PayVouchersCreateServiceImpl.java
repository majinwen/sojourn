package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.services.order.entity.FinancePayVouchersSaveVo;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderDayPriceVo;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

@Service("order.payVouchersCreateServiceImpl")
public class PayVouchersCreateServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PayVouchersCreateServiceImpl.class);
	
	@Resource(name = "order.financePayVouchersDao")
    private FinancePayVouchersDao financePayVouchersDao;
    
    @Resource(name = "order.financePayVouchersDetailDao")
    private FinancePayVouchersDetailDao financePayVouchersDetailDao;
    
    @Resource(name = "order.financeIncomeDao")
    private FinanceIncomeDao financeIncomeDao;
    
    @Resource(name = "order.orderDao")
    private OrderDao orderDao;
    
    @Resource(name = "order.orderLogDao")
    private OrderLogDao orderLogDao;
	
	@Resource(name = "order.orderMoneyDao")
    private OrderMoneyDao orderMoneyDao;
	
	@Value(value = "${PAY.RUN_AFTER_DAYS}")
    private String RUN_AFTER_DAYS; 
	
	@Value(value = "${PAY.RUN_TIME}")
    private String RUN_TIME;
	
	private final String dateFormatPattern =  "yyyy-MM-dd";
    private final String dateTimeFormatPattern =  "yyyy-MM-dd HH:mm:ss";



    /**
	 * 生成付款单，收入记录
	 * @author lishaochuan
	 * @create 2016年4月19日
	 * @param orderEntity
	 */
	public void createFinance(OrderEntity orderEntity, OrderConfigVo orderConfigVo, List<OrderDayPriceVo> dayPrice) throws Exception {
		OrderMoneyEntity orderMoneyEntity = orderMoneyDao.getOrderMoneyByOrderSn(orderEntity.getOrderSn());
		// 房东免佣金活动
		if(!Check.NuNStr(orderConfigVo.getLandCom())){
			orderConfigVo.setCommissionRateLandlord(0.0);
		}
		LogUtil.info(LOGGER, "总付款单金额= 房租-折扣-房东佣金");
		LogUtil.info(LOGGER, "折扣租金 = 租金*折扣");
		LogUtil.info(LOGGER, "房东佣金 = 折扣租金*房东佣金费率*房东佣金折扣费率");
		LogUtil.info(LOGGER, "租客佣金 = 折扣租金*租客佣金费率");
		LogUtil.info(LOGGER, "付款单金额 = 折扣租金-房东佣金");
		LogUtil.info(LOGGER, "orderEntity:{}", orderEntity);
		LogUtil.info(LOGGER, "orderMoneyEntity:{}", orderMoneyEntity);
		LogUtil.info(LOGGER, "dayPrice:{}", dayPrice);
		LogUtil.info(LOGGER, "orderConfigVo:{}", JsonEntityTransform.Object2Json(orderConfigVo));
    	
		if(orderMoneyEntity.getCleanMoney() > 0){ //清洁费收入、付款单
			this.saveCleanFee(orderEntity, orderMoneyEntity, orderConfigVo);
		}
		if (orderConfigVo.getCheckType() == CheckTypeEnum.DAY.getCode()) { // 按天结算
			this.saveLandlordFinanceByDay(orderEntity, orderMoneyEntity, dayPrice, orderConfigVo);
		}
		if (orderConfigVo.getCheckType() == CheckTypeEnum.ORDER.getCode()) { // 按订单结算
			this.saveLandlordFinanceByOrder(orderEntity, orderMoneyEntity, dayPrice, orderConfigVo);
		}
		this.updateOrderStatusCheckedBill(orderEntity);
	}
	
	
	/**
	 * 生成清洁费的打款计划
	 * @author lishaochuan
	 * @create 2016年8月22日下午7:43:21
	 * @param orderEntity
	 * @param orderMoneyEntity
	 * @param orderConfigVo
	 * @throws Exception
	 */
    private void saveCleanFee(OrderEntity orderEntity, OrderMoneyEntity orderMoneyEntity, OrderConfigVo orderConfigVo) throws Exception {
    	Date runTime = DateSplitUtil.jumpDate(DateUtil.parseDate(DateUtil.dateFormat(orderEntity.getStartTime()) + " " + RUN_TIME, dateTimeFormatPattern), ValueUtil.getintValue(RUN_AFTER_DAYS));
    	//清洁费佣金
    	int cleanCommMoney = this.getCleanCommMoney(orderMoneyEntity.getCleanMoney(), orderConfigVo.returnRateLandlord());
    	//付给房东的清洁费=总清洁费-清洁费佣金
    	int totalFee = orderMoneyEntity.getCleanMoney() - cleanCommMoney;
    	
    	FinancePayVouchersSaveVo payVouchersSaveVo = new FinancePayVouchersSaveVo();
    	payVouchersSaveVo.setPvSn(OrderSnUtil.getPvSn());
		payVouchersSaveVo.setLandlordCommission(cleanCommMoney);
		payVouchersSaveVo.setUserCommission(0);
		payVouchersSaveVo.setTotalFee(totalFee);
		payVouchersSaveVo.setGenerateFeeTime(orderEntity.getStartTime());
		payVouchersSaveVo.setRunTime(runTime);
		payVouchersSaveVo.setPaySourceType(PaySourceTypeEnum.CLEAN.getCode());
    	
		//生成房东清洁费付款计划
		this.fillFinancePayVouchersEntity(orderEntity, payVouchersSaveVo);
		// 生成房东付款计划明细
		this.fillFinancePayVouchersDetailEntity(payVouchersSaveVo);;
		// 生成公司收入计划
		this.fillFinanceIncomeEntity(orderEntity, payVouchersSaveVo);
		// 保存付款单表、收入表记录
		this.savePayVouchersAndIncome(payVouchersSaveVo);
		
    }

	
	/**
     * 按天：生成房东的付款单，公司的收入记录
     * @author lishaochuan
     * @create 2016年4月22日
     * @param orderEntity
     * @param dayPrice
     * @param orderConfigVo
     * @throws Exception
     */
    private void saveLandlordFinanceByDay(OrderEntity orderEntity, OrderMoneyEntity orderMoneyEntity, List<OrderDayPriceVo> dayPrice, OrderConfigVo orderConfigVo) throws Exception {
    	// dayPrice排序
    	this.sortDayPrice(dayPrice);
    	
    	int cleanCommMoney = this.getCleanCommMoney(orderMoneyEntity.getCleanMoney(), orderConfigVo.returnRateLandlord());// 清洁费佣金
		int allLanCommMoney = orderMoneyEntity.getLanCommMoney() - cleanCommMoney; //总房东佣金
		int allUserCommMoney = orderMoneyEntity.getUserCommMoney(); //总租客房租佣金(减去清洁费佣金后的)
		int allPayVouchers = orderMoneyEntity.getRentalMoney() - orderMoneyEntity.getDiscountMoney() - allLanCommMoney; //总付款单金额=折扣后房租-房东佣金
		
		LogUtil.info(LOGGER, "按天生成开始-----");
		LogUtil.info(LOGGER, "清洁费佣金：{}", cleanCommMoney);
		LogUtil.info(LOGGER, "总房东佣金(减去清洁费佣金后的)：{}", allLanCommMoney);
		LogUtil.info(LOGGER, "总租客房租佣金：{}", allUserCommMoney);
		LogUtil.info(LOGGER, "总付款单金额：{}", allPayVouchers);
    	
		
		int i = 0;
		int sumLanCommMoney = 0; //累加房东总佣金
		int sumUserCommMoney = 0; //累加租客总佣金
		int sumPayFee = 0; //累加付款单总金额
		for (OrderDayPriceVo orderDayPriceVo : dayPrice) {
			i++;
			FinancePayVouchersSaveVo payVouchersSaveVo = new FinancePayVouchersSaveVo();
			
			String pvSn = OrderSnUtil.getPvSn();
			LogUtil.info(LOGGER, "pvSn：{}", pvSn);
			
			Date generateFeeTime = DateUtil.parseDate(orderDayPriceVo.getPriceDate(), dateFormatPattern);
			LogUtil.info(LOGGER, "产生费用日期，generateFeeTime：{}", generateFeeTime);
			
			Date runTime = DateSplitUtil.jumpDate(DateUtil.parseDate(orderDayPriceVo.getPriceDate() + " " + RUN_TIME, dateTimeFormatPattern), ValueUtil.getintValue(RUN_AFTER_DAYS));
			LogUtil.info(LOGGER, " 执行时间：住房时间+N天 ，RUN_AFTER_DAYS,:{}, RUN_TIME:{}, runTime:{}", RUN_AFTER_DAYS, RUN_TIME, runTime);
			
			// double discountRent = BigDecimalUtil.mul(orderDayPriceVo.getPriceValue(), BigDecimalUtil.sub(1, orderConfigVo.getDiscountRate()));
			Integer discountRent = orderDayPriceVo.getPriceValue();
			LogUtil.info(LOGGER, "折扣租金 ，discountRent:{}", discountRent);
			
			
			Double landlordCommission = BigDecimalUtil.mul(discountRent, orderConfigVo.returnRateLandlord());
			LogUtil.info(LOGGER, "房东佣金 = 折扣租金*房东佣金费率*房东佣金折扣费率，landlordCommission：{}", landlordCommission);

			Double userCommission = BigDecimalUtil.mul(discountRent, orderConfigVo.getCommissionRateUser());
			LogUtil.info(LOGGER, "租客佣金 = 折扣租金*租客佣金费率，userCommission：{}", userCommission);
			
			Double payFee =  BigDecimalUtil.sub(discountRent, landlordCommission);
			LogUtil.info(LOGGER, "付款单金额 = 折扣租金-房东佣金，payFee：{}", payFee);
			
			if (i == dayPrice.size()) {
				landlordCommission = BigDecimalUtil.sub(allLanCommMoney, sumLanCommMoney);
				userCommission = BigDecimalUtil.sub(allUserCommMoney, sumUserCommMoney);
				payFee = BigDecimalUtil.sub(allPayVouchers, sumPayFee);
			}else{
				sumLanCommMoney += landlordCommission;
				sumUserCommMoney += userCommission;
				sumPayFee += payFee;
			}
			
			
			
			// 填充基础金额信息
			payVouchersSaveVo.setPvSn(pvSn);
			payVouchersSaveVo.setLandlordCommission(landlordCommission.intValue());
			payVouchersSaveVo.setUserCommission(userCommission.intValue());
			payVouchersSaveVo.setTotalFee(payFee.intValue());
			payVouchersSaveVo.setGenerateFeeTime(generateFeeTime);
			payVouchersSaveVo.setRunTime(runTime);
			payVouchersSaveVo.setPaySourceType(PaySourceTypeEnum.TASK.getCode());
			
			
			// 生成房东付款计划
			this.fillFinancePayVouchersEntity(orderEntity, payVouchersSaveVo);
			// 生成房东付款计划明细
			this.fillFinancePayVouchersDetailEntity(payVouchersSaveVo);;
			// 生成公司收入计划
			this.fillFinanceIncomeEntity(orderEntity, payVouchersSaveVo);
			// 保存付款单表、收入表记录
			this.savePayVouchersAndIncome(payVouchersSaveVo);
		}
    }
    
    
    /**
     * 按订单：生成房东的付款计划，公司的收入记录
     * @author lishaochuan
     * @create 2016年4月22日
     * @param orderEntity
     * @param dayPrice
     * @param orderConfigVo
     * @throws Exception
     */
    private void saveLandlordFinanceByOrder(OrderEntity orderEntity, OrderMoneyEntity orderMoneyEntity, List<OrderDayPriceVo> dayPrice, OrderConfigVo orderConfigVo) throws Exception {
    	// dayPrice排序
    	this.sortDayPrice(dayPrice);
    	
    	int cleanCommMoney = this.getCleanCommMoney(orderMoneyEntity.getCleanMoney(), orderConfigVo.returnRateLandlord());// 清洁费佣金
		int allLanCommMoney = orderMoneyEntity.getLanCommMoney() - cleanCommMoney; //总房东佣金
		int allUserCommMoney = orderMoneyEntity.getUserCommMoney(); //总租客房租佣金(减去清洁费后的)
		int allPayFee = orderMoneyEntity.getRentalMoney() - orderMoneyEntity.getDiscountMoney() - allLanCommMoney; //总付款单金额=折扣后房租-房东佣金
		
		LogUtil.info(LOGGER, "按订单生成开始-----");
		LogUtil.info(LOGGER, "清洁费佣金：{}", cleanCommMoney);
		LogUtil.info(LOGGER, "总房东佣金(减去清洁费后的)：{}", allLanCommMoney);
		LogUtil.info(LOGGER, "总租客房租佣金：{}", allUserCommMoney);
		LogUtil.info(LOGGER, "总付款单金额：{}", allPayFee);
    	
		int i = 0;
    	int sum7LandlordCommission = 0; //7日房东佣金
    	int sum7UserCommission = 0; //7日租客佣金
    	int sum7PayFee = 0; //7日付款单金额
    	
    	int sumLanCommMoney = 0; //累加房东总佣金
    	int sumUserCommMoney = 0; //累加租客总佣金
    	int sumPayFee = 0; //累加付款单总金额
		for (OrderDayPriceVo orderDayPriceVo : dayPrice) {
			FinancePayVouchersSaveVo payVouchersSaveVo = new FinancePayVouchersSaveVo();
			
			// 折扣后租金 = 租金*折扣
			// Double discountRent = BigDecimalUtil.mul(orderDayPriceVo.getPriceValue(), BigDecimalUtil.sub(1, orderConfigVo.getDiscountRate()));
			Integer discountRent = orderDayPriceVo.getPriceValue();
			// 房东佣金 = 折扣后租金*房东佣金费率 *房东佣金折扣费率
			Double landlordCommission = BigDecimalUtil.mul(discountRent, orderConfigVo.returnRateLandlord());
			// 租客佣金 = 折扣后租金*租客佣金费率
			Double userCommission = BigDecimalUtil.mul(discountRent, orderConfigVo.getCommissionRateUser());
			// 付款单金额 = 折扣租金-房东佣金
			Double payFee = BigDecimalUtil.sub(discountRent, landlordCommission);
			
			
			sum7LandlordCommission += landlordCommission;
			sum7UserCommission += userCommission;
			sum7PayFee += payFee;
			
			if ((++i % 7) == 0 || i == dayPrice.size()) {
				String pvSn = OrderSnUtil.getPvSn();
				Date generateFeeTime = DateUtil.parseDate(orderDayPriceVo.getPriceDate(), dateFormatPattern);
				Date runTime = DateSplitUtil.jumpDate(DateUtil.parseDate(orderDayPriceVo.getPriceDate() + " " + RUN_TIME, dateTimeFormatPattern), Integer.valueOf(RUN_AFTER_DAYS)); // 执行时间：住房时间+N天
				
				if (i < dayPrice.size()) {
					sumLanCommMoney += sum7LandlordCommission;
					sumUserCommMoney += sum7UserCommission;
					sumPayFee += sum7PayFee;
				}
				if(i == dayPrice.size()){
					sum7LandlordCommission = allLanCommMoney - sumLanCommMoney;
					sum7UserCommission = allUserCommMoney - sumUserCommMoney;
					sum7PayFee = allPayFee - sumPayFee;
				}
				
				// 填充基础金额信息
				payVouchersSaveVo.setPvSn(pvSn);
				payVouchersSaveVo.setLandlordCommission(sum7LandlordCommission);
				payVouchersSaveVo.setUserCommission(sum7UserCommission);
				payVouchersSaveVo.setTotalFee(sum7PayFee);
				payVouchersSaveVo.setGenerateFeeTime(generateFeeTime);
				payVouchersSaveVo.setRunTime(runTime);
				payVouchersSaveVo.setPaySourceType(PaySourceTypeEnum.TASK.getCode());
				
				// 生成房东付款计划
				this.fillFinancePayVouchersEntity(orderEntity, payVouchersSaveVo);
				// 生成房东付款计划明细
				this.fillFinancePayVouchersDetailEntity(payVouchersSaveVo);;
				// 生成公司收入计划
				this.fillFinanceIncomeEntity(orderEntity, payVouchersSaveVo);
				// 保存付款单表、收入表记录
				this.savePayVouchersAndIncome(payVouchersSaveVo);
				
				sum7PayFee = 0;
				sum7LandlordCommission = 0;
				sum7UserCommission = 0;
			}
		}
    }
   
    
    /**
     * 填充付款单表信息
     * @author lishaochuan
     * @create 2016年4月22日
     * @param orderEntity
     * @param payVouchersSaveVo
     */
    private void fillFinancePayVouchersEntity(OrderEntity orderEntity, FinancePayVouchersSaveVo payVouchersSaveVo){
    	FinancePayVouchersEntity payVouchersEntity = new FinancePayVouchersEntity();
		payVouchersEntity.setPvSn(payVouchersSaveVo.getPvSn());
		payVouchersEntity.setPaySourceType(payVouchersSaveVo.getPaySourceType()); //定时任务、清洁费
		payVouchersEntity.setOrderSn(orderEntity.getOrderSn());
		payVouchersEntity.setCityCode(orderEntity.getCityCode());
		payVouchersEntity.setReceiveUid(orderEntity.getLandlordUid());
		payVouchersEntity.setReceiveType(UserTypeEnum.LANDLORD.getUserType());
		payVouchersEntity.setPayUid(orderEntity.getLandlordUid());
		payVouchersEntity.setPayType(UserTypeEnum.TENANT.getUserType());
		payVouchersEntity.setPaymentType(null);
		payVouchersEntity.setTotalFee(payVouchersSaveVo.getTotalFee());
		payVouchersEntity.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
		payVouchersEntity.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
		payVouchersEntity.setGenerateFeeTime(payVouchersSaveVo.getGenerateFeeTime());
		payVouchersEntity.setRunTime(payVouchersSaveVo.getRunTime());
		payVouchersEntity.setIsSend(YesOrNoEnum.NO.getCode());
		
		payVouchersSaveVo.setPayVouchersEntity(payVouchersEntity);
    }
    /**
     * 填充付款单明细表信息
     * @author lishaochuan
     * @create 2016年4月22日
     * @param payVouchersSaveVo
     */
    private void fillFinancePayVouchersDetailEntity(FinancePayVouchersSaveVo payVouchersSaveVo){
    	FinancePayVouchersDetailEntity financePayVouchersDetailEntity = new FinancePayVouchersDetailEntity();
		financePayVouchersDetailEntity.setPvSn(payVouchersSaveVo.getPvSn());
		if(payVouchersSaveVo.getPaySourceType() == PaySourceTypeEnum.CLEAN.getCode()){
			financePayVouchersDetailEntity.setFeeItemCode(FeeItemCodeEnum.CLEAN.getCode());
		}else{
			financePayVouchersDetailEntity.setFeeItemCode(FeeItemCodeEnum.RENT.getCode());
		}
		financePayVouchersDetailEntity.setItemMoney(payVouchersSaveVo.getTotalFee());
		
		List<FinancePayVouchersDetailEntity> financePayVouchersDetailEntityList = new ArrayList<FinancePayVouchersDetailEntity>();
		financePayVouchersDetailEntityList.add(financePayVouchersDetailEntity);
		payVouchersSaveVo.setFinancePayVouchersDetailEntityList(financePayVouchersDetailEntityList);;
    }
    /**
     * 填充公司收入表信息
     * @author lishaochuan
     * @create 2016年4月22日
     * @param orderEntity
     * @param payVouchersSaveVo
     */
    private void fillFinanceIncomeEntity(OrderEntity orderEntity, FinancePayVouchersSaveVo payVouchersSaveVo){
    	List<FinanceIncomeEntity> financeIncomeEntityList = new ArrayList<FinanceIncomeEntity>();
    	
    	if(payVouchersSaveVo.getLandlordCommission() != 0){
    		FinanceIncomeEntity landlordIncomeEntity = new FinanceIncomeEntity();
    		landlordIncomeEntity.setIncomeSn(OrderSnUtil.getIncomeSn());
    		landlordIncomeEntity.setIncomeSourceType(IncomeSourceTypeEnum.TASK.getCode());
    		if(payVouchersSaveVo.getPaySourceType() == PaySourceTypeEnum.CLEAN.getCode()){
    			landlordIncomeEntity.setIncomeType(IncomeTypeEnum.USER_CLEAN_COMMISSION.getCode()); // 房东佣金
    		}else{
    			landlordIncomeEntity.setIncomeType(IncomeTypeEnum.LANDLORD_RENT_COMMISSION.getCode()); // 房东佣金
    		}
    		landlordIncomeEntity.setOrderSn(orderEntity.getOrderSn());
    		landlordIncomeEntity.setCityCode(orderEntity.getCityCode());
    		landlordIncomeEntity.setPayUid(orderEntity.getLandlordUid());
    		landlordIncomeEntity.setPayType(UserTypeEnum.LANDLORD.getUserType());
    		landlordIncomeEntity.setTotalFee(payVouchersSaveVo.getLandlordCommission());
    		landlordIncomeEntity.setIncomeStatus(IncomeStatusEnum.NO.getCode()); //未收款
    		landlordIncomeEntity.setGenerateFeeTime(payVouchersSaveVo.getGenerateFeeTime());
    		landlordIncomeEntity.setRunTime(payVouchersSaveVo.getRunTime());
    		
    		financeIncomeEntityList.add(landlordIncomeEntity);
    	}
		
    	if(payVouchersSaveVo.getUserCommission() != 0){
    		FinanceIncomeEntity userIncomeEntity = new FinanceIncomeEntity();
    		userIncomeEntity.setIncomeSn(OrderSnUtil.getIncomeSn());
    		userIncomeEntity.setIncomeSourceType(IncomeSourceTypeEnum.TASK.getCode());
			userIncomeEntity.setIncomeType(IncomeTypeEnum.USER_RENT_COMMISSION.getCode()); // 租客佣金
    		userIncomeEntity.setOrderSn(orderEntity.getOrderSn());
    		userIncomeEntity.setCityCode(orderEntity.getCityCode());
    		userIncomeEntity.setPayUid(orderEntity.getLandlordUid());
    		userIncomeEntity.setPayType(UserTypeEnum.TENANT.getUserType());
    		userIncomeEntity.setTotalFee(payVouchersSaveVo.getUserCommission());
    		userIncomeEntity.setIncomeStatus(IncomeStatusEnum.NO.getCode()); //未收款
    		userIncomeEntity.setGenerateFeeTime(payVouchersSaveVo.getGenerateFeeTime());
    		userIncomeEntity.setRunTime(payVouchersSaveVo.getRunTime());
    		
    		financeIncomeEntityList.add(userIncomeEntity);
    	}
		payVouchersSaveVo.setFinanceIncomeEntityList(financeIncomeEntityList);
    }
    
    /**
     * 保存付款单表，明细表，收入表信息
     * @author lishaochuan
     * @create 2016年4月22日
     * @param payVouchersSaveVo
     */
    public void savePayVouchersAndIncome(FinancePayVouchersSaveVo payVouchersSaveVo){
    	int pvNum = financePayVouchersDao.insertPayVouchers(payVouchersSaveVo.getPayVouchersEntity());
		if(pvNum != 1){
			LogUtil.info(LOGGER, "付款单表插入条数不是1 | pvNum:{}, payVouchers:{}", pvNum, payVouchersSaveVo.getPayVouchersEntity());
			throw new BusinessException("付款单表插入条数不是1,请检查订单状态");
		}
		for (FinancePayVouchersDetailEntity financePayVouchersDetailEntity : payVouchersSaveVo.getFinancePayVouchersDetailEntityList()) {
			int pvDetailNum = financePayVouchersDetailDao.insertPayVouchersDetail(financePayVouchersDetailEntity);
			if(pvDetailNum != 1){
				LogUtil.info(LOGGER, "付款单详情表插入条数不是 1 | pvDetailNum:{}, financePayVouchersDetailEntity:{}", pvDetailNum, financePayVouchersDetailEntity);
				throw new BusinessException("付款单详情表插入条数不是 1");
			}
		}
		for (FinanceIncomeEntity financeIncomeEntity : payVouchersSaveVo.getFinanceIncomeEntityList()) {
			int incomeNum = financeIncomeDao.insertFinanceIncome(financeIncomeEntity);
			if(incomeNum != 1){
				LogUtil.info(LOGGER, "收入表插入条数不是1 | incomeNum:{}, financeIncomeEntity:{}", incomeNum, financeIncomeEntity);
				throw new BusinessException("收入表插入条数不是1");
			}
		}
		
    }
   
    /**
     * 更新订单状态，已入住→生成单据
     * @author lishaochuan
     * @create 2016年4月22日
     * @param orderEntity
     */
    private void updateOrderStatusCheckedBill(OrderEntity orderEntity){
    	int oldStatus = OrderStatusEnum.CHECKED_IN.getOrderStatus();
    	int newStatus = OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus();
    	if(orderEntity.getOrderStatus() != oldStatus){
    		LogUtil.info(LOGGER, "订单状态不是40，已入住未生成单据. orderSn:{}, orderStatus:{}", orderEntity.getOrderSn(), orderEntity.getOrderStatus());
			throw new BusinessException("订单状态不是40，已入住未生成单据");
    	}
    	int orderNum = orderDao.updateOrderStatuByOrderSn(orderEntity.getOrderSn(), newStatus, oldStatus);
		if(orderNum != 1){
			LogUtil.info(LOGGER, "更新订单状态失败 | orderSn:{}, orderNum:{}", orderEntity.getOrderSn(), orderNum);
			throw new BusinessException("更新订单状态失败");
		}
		
		OrderLogEntity log = new OrderLogEntity();
		log.setOrderSn(orderEntity.getOrderSn());
		log.setToStatus(newStatus);
		log.setFromStatus(oldStatus);
		log.setRemark("定时任务生成单据");
		log.setCreateId("001");
		orderLogDao.insertOrderLog(log);
		
    }
    /**
     * dayPrice排序
     * @author lishaochuan
     * @create 2016年4月22日
     * @param dayPrice
     */
    private void sortDayPrice(List<OrderDayPriceVo> dayPrice){
    	Collections.sort(dayPrice, new Comparator<OrderDayPriceVo>(){
			@Override
			public int compare(OrderDayPriceVo o1, OrderDayPriceVo o2) {
				try {
					Date date1 = DateUtil.parseDate(o1.getPriceDate(), dateFormatPattern);
					Date date2 = DateUtil.parseDate(o2.getPriceDate(), dateFormatPattern);
					if (date1.after(date2)) {
						return 1;
					} else if(date1.before(date2)){
						return -1;
					} else {
						return 0;
					}
				} catch (ParseException e) {
					LogUtil.error(LOGGER,"e:{}",e);
					return 0;
				}
			}
    	});
    }
    
    
    /**
     * 清洁费佣金 = 清洁费*房东佣金比率
     * @author lishaochuan
     * @create 2016年8月22日下午5:51:42
     * @param cleanMoney
     * @param rate
     * @return
     */
    private int getCleanCommMoney(int cleanMoney, double rate){
    	if(Check.NuNObj(cleanMoney)){
    		return 0;
    	}
    	Double comm = BigDecimalUtil.mul(cleanMoney, rate);
    	return comm.intValue();
    }
    
}
