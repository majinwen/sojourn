package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskFinanceService;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderDayPriceVo;
import com.ziroom.minsu.services.order.proxy.finance.FinanceExecute4Income;
import com.ziroom.minsu.services.order.proxy.finance.FinancePvExecute;
import com.ziroom.minsu.services.order.service.*;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.order.PaySourceTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

/**
 * 
 * <p>
 * 付款单表收入表，定时任务
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月23日
 * @since 1.0
 * @version 1.0
 */
@Component("order.orderTaskFinanceServiceProxy")
public class OrderTaskFinanceServiceProxy implements OrderTaskFinanceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskFinanceServiceProxy.class);

	//扫描失败重试次数
	private static int RETRY_TIMES = 4;

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;

	@Resource(name = "order.orderTaskFinanceServiceImpl")
	private OrderTaskFinanceServiceImpl orderTaskFinanceService;

	@Resource(name = "order.payVouchersCreateServiceImpl")
	private PayVouchersCreateServiceImpl payVouchersCreateService;

	@Resource(name = "order.orderConfigServiceImpl")
	private OrderConfigServiceImpl orderConfigService;

	@Resource(name = "order.orderMoneyServiceImpl")
	private OrderMoneyServiceImpl orderMoneyService;

	@Resource(name = "order.orderUserServiceImpl")
	private OrderUserServiceImpl orderUserService;


	@Resource(name = "order.financeExecute4Income")
	private FinanceExecute4Income execute4Income;

	@Resource(name = "order.financeExecute4Task")
	private FinancePvExecute execute4Task;

	@Resource(name = "order.financeExecute4Settlement")
	private FinancePvExecute execute4Settlement;

	@Resource(name = "order.financeExecute4OverCancel")
	private FinancePvExecute execute4OverCancel;

	@Resource(name = "order.financeExecute4ClearCoupon")
	private FinancePvExecute execute4ClearCoupon;

	@Resource(name = "order.financeExecute4PayFailedRecreate")
	private FinancePvExecute execute4PayFailedRecreate;


	/**
	 * 查询已入住订单，生成付款单，收入记录
	 * @author lishaochuan
	 * @create 2016年4月19日
	 */
	@Override
	public void taskCreateFinance(){

		Thread createFinanceThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Long t1 = System.currentTimeMillis();
					LogUtil.info(LOGGER, "【生成付款收入单据Job】开始......");
					int limit = 150;
					Long count = orderTaskFinanceService.getOrderCountByStatus(OrderStatusEnum.CHECKED_IN.getOrderStatus());
					LogUtil.info(LOGGER, "【生成付款收入单据Job】条数：{}", count);
					if (Check.NuNObj(count) || count == 0) {
						return;
					}
					int pageAll = ValueUtil.getPage(count.intValue(), limit);
					for (int page = 1; page <= pageAll; page++) {
						List<OrderEntity> orderEntityList = orderTaskFinanceService.getOrderListByStatus(OrderStatusEnum.CHECKED_IN.getOrderStatus(), limit);
						for (OrderEntity orderEntity : orderEntityList) {
							createFinance(orderEntity);
						}
					}
					LogUtil.info(LOGGER, "【生成付款收入单据Job】结束......,用时:{}ms",System.currentTimeMillis()-t1);
				}catch (Exception e){
					LogUtil.error(LOGGER, "【生成付款收入单据Job】Exception:{}", e);
				}
			}
		});

		createFinanceThread.start();
	}



	/**
	 * 执行收入表打款 和 扫描执行付款计划
	 * 把连个定时任务合并，目的是保证调用财务那边是按照顺序调用的，否则财务会有锁的概念
	 * @author afi
	 * @create 2016年4月22日
	 */
	@Override
	public void taskRunFinance() {
		try {
			Thread task = new Thread() {
				@Override
				public void run() {
					taskRunFinanceIncome();
					taskRunFinancePayVoucher();
					taskRunFailedFinancePayVoucher();
				}
			};
			SendThreadPool.execute(task);
		} catch (Exception e) {
			// 告警
			LogUtil.error(LOGGER, "【生成付款收入单据Job】Exception:{}", e);
		}

	}




	/**
	 * 生成付款单，收入记录
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param orderEntity
	 */
	public void createFinance(OrderEntity orderEntity){
		try {
			// 获取订单配置：优惠折扣、佣金、结算方式
			OrderConfigVo orderConfigVo = orderConfigService.getOrderConfigVo(orderEntity.getOrderSn());
			// 获取订单每天的价格
			List<OrderDayPriceVo> dayPrice = orderUserService.getDayPrices(orderEntity);
			// 生成付款单，收入记录
			payVouchersCreateService.createFinance(orderEntity, orderConfigVo, dayPrice);
		}catch (Exception e){
			// 告警
			LogUtil.error(LOGGER, "【生成付款收入单据Job】异常,orderEntity:{}, Exception:{}", orderEntity, e);
		}
	}


	/**
	 * 扫描执行付款计划
	 * @author lishaochuan
	 * @create 2016年4月20日
	 */
	private void taskRunFinancePayVoucher(){
		try {
			LogUtil.info(LOGGER, "【执行付款单计划Job】");
			int limit = 150;
			Long count = orderTaskFinanceService.getNotPayVouchersCount();
			if (Check.NuNObj(count) || count == 0) {
				return;
			}
			int pageAll = ValueUtil.getPage(count.intValue(), limit);
			for (int page = 1; page <= pageAll; page++) {
				List<FinancePayVouchersEntity> payVouchersList = orderTaskFinanceService.getNotPayVouchersList(limit);
				for (FinancePayVouchersEntity financePayVouchersEntity : payVouchersList) {
					LogUtil.info(LOGGER, "【执行付款单计划Job】，financePayVouchersEntity：{}", financePayVouchersEntity);
					this.runAllFinancePayVoucher(financePayVouchersEntity);
				}
			}

		}catch (Exception e){
			LogUtil.error(LOGGER, "【执行付款单计划Job】异常，Exception:{}", e);
		}
	}


	/**
	 * 扫描执行失败的付款单计划
	 * @author lishaochuan
	 * @create 2016年9月19日下午8:52:53
	 */
	private void taskRunFailedFinancePayVoucher(){
		try {
			LogUtil.info(LOGGER, "【执行失败付款单重试Job】");
			int limit = 150;
			Long count = orderTaskFinanceService.getFailedPayVouchersCount(RETRY_TIMES);
			if (Check.NuNObj(count) || count == 0) {
				return;
			}
			int pageAll = ValueUtil.getPage(count.intValue(), limit);
			for (int page = 1; page <= pageAll; page++) {
				List<FinancePayVouchersEntity> payVouchersList = orderTaskFinanceService.getFailedPayVouchersList(RETRY_TIMES, limit);
				for (FinancePayVouchersEntity financePayVouchersEntity : payVouchersList) {
					LogUtil.info(LOGGER, "【执行失败付款单重试Job】，financePayVouchersEntity：{}", financePayVouchersEntity);
					this.runAllFinancePayVoucher(financePayVouchersEntity);
				}
			}

		}catch (Exception e){
			LogUtil.error(LOGGER, "【执行失败付款单重试Job】异常，Exception:{}", e);
		}
	}


	/**
	 * 扫描执行收入表计划
	 * @author lishaochuan
	 * @create 2016年4月22日
	 */
	private void taskRunFinanceIncome(){
		try {
			LogUtil.info(LOGGER, "【执行收入计划Job】");
			int limit = 150;
			Long count = orderTaskFinanceService.getNotIncomeCount(RETRY_TIMES);
			if (Check.NuNObj(count) || count == 0) {
				return;
			}
			int pageAll = ValueUtil.getPage(count.intValue(), limit);
			for (int page = 1; page <= pageAll; page++) {
				List<FinanceIncomeEntity> payIncomeList = orderTaskFinanceService.getNotIncomeList(RETRY_TIMES, limit);
				for (FinanceIncomeEntity financeIncomeEntity : payIncomeList) {
					LogUtil.info(LOGGER, "【执行收入计划Job】，financeIncomeEntity：{}", financeIncomeEntity);
					this.runFinanceIncome(financeIncomeEntity);
				}
			}

		}catch (Exception e){
			LogUtil.error(LOGGER, "【执行收入计划Job】异常，Exception:{}", e);
		}
	}

	/**
	 *
	 * modify by jixd on 2017/06/20 清空优惠券增加 清空活动金额
	 * 判断执行付款单表记录
	 * @author lishaochuan
	 * @create 2016年4月23日
	 * @param payVouchers
	 */
	private void runAllFinancePayVoucher(FinancePayVouchersEntity payVouchers){
		try {
			Integer paySourceType = payVouchers.getPaySourceType();
			if (paySourceType == PaySourceTypeEnum.TASK.getCode()
					|| paySourceType == PaySourceTypeEnum.CLEAN.getCode()
					|| paySourceType == PaySourceTypeEnum.CASHBACK.getCode()) {
				execute4Task.run(payVouchers);
			} else if (paySourceType == PaySourceTypeEnum.USER_SETTLEMENT.getCode()) {
				execute4Settlement.run(payVouchers);
			} else if (paySourceType == PaySourceTypeEnum.OVERTIME_CANCEL.getCode()) {
				execute4OverCancel.run(payVouchers);
			} else if(paySourceType == PaySourceTypeEnum.CLEAR_COUPON.getCode()
					|| paySourceType == PaySourceTypeEnum.CLEAR_ACT.getCode()){
				execute4ClearCoupon.run(payVouchers);
			} else if(paySourceType == PaySourceTypeEnum.PAY_FAILED_RECREATE.getCode()){
				execute4PayFailedRecreate.run(payVouchers);
			} else{
				LogUtil.error(LOGGER, "付款单来源错误，pvSn:{},paySourceType：{}", payVouchers.getPvSn(), payVouchers.getPaySourceType());
			} 
		}catch (Exception e){
			LogUtil.error(LOGGER, "【执行付款单计划Job】判断执行付款单记录异常，Exception:{}", e);
		}
	}





	/**
	 * 执行定时任务收入表计划
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @param financeIncomeEntity
	 */
	private void runFinanceIncome(FinanceIncomeEntity financeIncomeEntity){
		try {
			execute4Income.run(financeIncomeEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "执行定时任务收入表计划异常，Exception:{}", e);
		}
	}











}
