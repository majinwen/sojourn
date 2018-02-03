package com.ziroom.minsu.services.order.proxy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.entity.order.FinanceCashbackLogEntity;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.order.api.inner.OrderTaskSyncService;
import com.ziroom.minsu.services.order.dto.OrderCashbackRequest;
import com.ziroom.minsu.services.order.entity.CashbackOrderVo;
import com.ziroom.minsu.services.order.service.FinanceCashbackServiceImpl;
import com.ziroom.minsu.services.order.service.FinanceIncomeServiceImpl;
import com.ziroom.minsu.services.order.service.FinancePaymentServiceImpl;
import com.ziroom.minsu.services.order.service.OrderActivityServiceImpl;
import com.ziroom.minsu.services.order.service.OrderMoneyServiceImpl;
import com.ziroom.minsu.valenum.order.CashOrderRankEnum;
import com.ziroom.minsu.valenum.order.CashbackStatusEnum;
import com.ziroom.minsu.valenum.order.ReceiveTypeEnum;

/**
 * <p>同步定时任务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月30日
 * @since 1.0
 * @version 1.0
 */
@Component("order.orderTaskSyncServiceProxy")
public class OrderTaskSyncServiceProxy implements OrderTaskSyncService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskSyncServiceProxy.class);

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;

	@Resource(name = "order.callFinanceServiceProxy")
	private CallFinanceServiceProxy callFinanceServiceProxy;

	@Resource(name = "order.financeIncomeServiceImpl")
	private FinanceIncomeServiceImpl financeIncomeServiceImpl;

	@Resource(name = "order.financePaymentServiceImpl")
	private FinancePaymentServiceImpl financePaymentServiceImpl;

	@Resource(name = "order.orderActivityServiceImpl")
	private OrderActivityServiceImpl orderActivityServiceImpl;

	@Resource(name = "order.financeCashbackServiceImpl")
	private FinanceCashbackServiceImpl financeCashbackServiceImpl;

	@Resource(name = "order.orderMoneyServiceImpl")
	private OrderMoneyServiceImpl orderMoneyServiceImpl;

	@Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;
	/**
	 * 同步收入（每个月1号上午10点执行）
	 * @author lishaochuan
	 * @create 2016年4月30日
	 */
	@Override
	public void syncIncomeData() {
		LogUtil.info(LOGGER, "【同步收入Job】（每个月2号执行）");
		final Date runTimeStart = DateUtil.getFirstDayOfMonth(-1);
		final Date runTimeEnd = DateUtil.getFirstDayOfMonth(0);


		try {

			final int limit = 150;
			final Long count = financeIncomeServiceImpl.getNotSyncIncomeCount(runTimeStart, runTimeEnd);
			LogUtil.info(LOGGER, "【同步收入Job】查询收入表runTime开始时间:{}，结束时间:{}，条数：{}", runTimeStart, runTimeEnd, count);
			if (Check.NuNObj(count) || count == 0) {
				return;
			}
			final int pageAll = ValueUtil.getPage(count.intValue(), limit);
			Thread task = new Thread(){
				@Override
				public void run() {
					for(int page = 1; page <= pageAll; page++) {
						List<FinanceIncomeVo> incomeList = financeIncomeServiceImpl.getNotSyncIncomeList(runTimeStart, runTimeEnd, limit);
						for (FinanceIncomeVo financeIncomeVo : incomeList) {
							try {
								Map<String,String> resultMap = callFinanceServiceProxy.syncIncomeData(financeIncomeVo);
								LogUtil.info(LOGGER, "【同步收入Job】,financeIncomeVo:{},resultMap:{}", financeIncomeVo, resultMap);
								financeIncomeServiceImpl.updateSyncIncomeStatus(financeIncomeVo, resultMap);
							} catch (Exception e) {

								LogUtil.error(LOGGER, "【同步收入Job】e:{}", e);
							}
						}
					}
				}
			};
			SendThreadPool.execute(task);

		}catch (Exception e){
			LogUtil.error(LOGGER, "【同步收入Job】e:{}", e);
		}
	}





	/**
	 * 同步业务帐
	 * @author lishaochuan
	 * @create 2016年4月30日
	 */
	@Override
	public void syncPaymentData() {
		LogUtil.info(LOGGER, "【同步业务帐Job】");
		try {
			int limit = 150;
			Long count = financePaymentServiceImpl.getNotSyncPaymentCount();
			LogUtil.info(LOGGER, "【同步业务帐Job】条数:{}", count);
			if (Check.NuNObj(count) || count == 0) {
				return;
			}
			int pageAll = ValueUtil.getPage(count.intValue(), limit);
			for (int page = 1; page <= pageAll; page++) {
				List<FinancePaymentVouchersEntity> paymentList = financePaymentServiceImpl.getNotSyncPaymentList(limit);
				for (FinancePaymentVouchersEntity paymentEntity : paymentList) {
					try {
						Map<String,String> resultMap = callFinanceServiceProxy.syncPaymentData(paymentEntity);
						LogUtil.info(LOGGER, "【同步业务帐Job】,paymentEntity:{},resultMap:{}", JsonEntityTransform.Object2Json(paymentEntity), JsonEntityTransform.Object2Json(resultMap));
						financePaymentServiceImpl.updatePaymentHasSync(paymentEntity, resultMap);
					} catch (Exception e) {
						LogUtil.error(LOGGER, "【同步业务帐Job】e:{}", e);
					}
				}
			}

		}catch (Exception e){
			LogUtil.error(LOGGER, "【同步业务帐Job】e:{}", e);
		}
	}



	/**
	 * 获取未同步状态的活动count
	 * @author lishaochuan
	 * @create 2016年6月18日上午11:00:59
	 * @return
	 */
	@Override
	public String getNotSyncActivityCount() {
		DataTransferObject dto = new DataTransferObject();
		try {
			Long count = orderActivityServiceImpl.getNotSyncCount();
			dto.putValue("count", count);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 获取未同步状态的活动list
	 * @author lishaochuan
	 * @create 2016年6月18日上午11:28:55
	 * @param limit
	 * @return
	 */
	@Override
	public String getNotSyncActivityList(Integer limit) {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<OrderActivityEntity> orderActList = orderActivityServiceImpl.getNotSyncList(limit);
			dto.putValue("orderActList", orderActList);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 更新未同步的活动为已同步
	 * @author lishaochuan
	 * @create 2016年6月18日上午11:45:31
	 * @param paramJson
	 * @return
	 */
	@Override
	public String updateActivityHasSync(String paramJson){
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			List<OrderActivityEntity> orderActList = JsonEntityTransform.json2ObjectList(paramJson, OrderActivityEntity.class);
			orderActivityServiceImpl.updateHasSync(orderActList);

		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}





	/**
	 * 
	 * 房东进击活动填充 返现单
	 *
	 * 1. 查询出 符合条件的订单 
	 * @author yd
	 * @created 2017年8月31日 下午1:40:02
	 *
	 * @return
	 */
	@Override
	public String fillLanlordCashOrder(String actSn) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				
				String actSn = "8a9091a15e37ba52015e37ba528f0000";
				OrderCashbackRequest request = new OrderCashbackRequest();

				int page = 1;

				request.setLimit(100);

				do {
					request.setPage(page);
					PagingResult<CashbackOrderVo> pageResult = financeCashbackServiceImpl.queryCashbackOrderVo(request);
					if(Check.NuNObj(pageResult)){
						page = 0;
						break;
					}

					List<CashbackOrderVo> listCashbackOrderVo = pageResult.getRows();
					if(Check.NuNCollection(listCashbackOrderVo)){
						page = 0;
						break;
					}

					page++;
					for (CashbackOrderVo cashbackOrderVo : listCashbackOrderVo) {
						try {
							//返现表
							String orderSn = cashbackOrderVo.getOrderSn();

							int orderNum = cashbackOrderVo.getNum();

							CashOrderRankEnum cash = CashOrderRankEnum.getCashOrderRankEnumByNum(orderNum);
							if(Check.NuNObj(cash)){
								continue;
							}
							long count = financeCashbackServiceImpl.countByLanlordUidAndActSn(cashbackOrderVo.getLandlordUid(), actSn);
							if(cash.getRank() > Integer.valueOf(count+"")){
								
								for (int i =Integer.valueOf(count+"") ;i<cash.getRank() ;i++) {
									++count;
									CashOrderRankEnum cashR = CashOrderRankEnum.getCashOrderRankEnumByRank(Integer.valueOf(count+""));
									FinanceCashbackEntity cashback =  new FinanceCashbackEntity();
									cashback.setActSn(actSn);
									cashback.setOrderSn(orderSn);
									cashback.setTotalFee(cashR.getCashMoney());
									cashback.setCreateId("001");
									cashback.setReceiveType(ReceiveTypeEnum.LANDLORD.getCode());
									cashback.setReceiveUid(cashbackOrderVo.getLandlordUid());
									String cashbackSn = "FX" + orderSn + "_RANK"+"_"+count;
									cashback.setCashbackSn(cashbackSn);
									cashback.setCashbackStatus(CashbackStatusEnum.INIT.getCode());
									cashback.setApplyRemark(cashR.getDesc());

									//返现操作日志表
									FinanceCashbackLogEntity cashbackLog = new FinanceCashbackLogEntity();
									cashbackLog.setFid(UUIDGenerator.hexUUID());
									cashbackLog.setCashbackSn(cashback.getCashbackSn());
									cashbackLog.setToStatus(cashback.getCashbackStatus());
									cashbackLog.setCreateId(cashback.getCreateId());
									financeCashbackServiceImpl.saveCashback(cashback, cashbackLog);
								}
							}

						

						} catch (Exception e) {
							LogUtil.error(LOGGER, "【房东进击生成返现单异常】e={},cashbackOrderVo={}", e,JsonEntityTransform.Object2Json(cashbackOrderVo));
							continue;
						}
					}
				} while (page>0);
			}
		});

		th.start();
		return null;
	}



}
