package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.entity.order.AccountFillLogEntity;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.entity.order.FinanceCashbackLogEntity;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;
import com.ziroom.minsu.services.account.dto.FillMoneyRequest;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerBlackService;
import com.ziroom.minsu.services.order.api.inner.FinanceCashbackService;
import com.ziroom.minsu.services.order.dto.AuditCashbackQueryRequest;
import com.ziroom.minsu.services.order.dto.AuditCashbackRequest;
import com.ziroom.minsu.services.order.dto.OrderCashbackRequest;
import com.ziroom.minsu.services.order.entity.AuditCashbackVo;
import com.ziroom.minsu.services.order.entity.FinancePayAndDetailVo;
import com.ziroom.minsu.services.order.entity.FinancePayVouchersDetailVo;
import com.ziroom.minsu.services.order.entity.OrderCashbackVo;
import com.ziroom.minsu.services.order.service.FinanceCashbackServiceImpl;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.OrderMoneyServiceImpl;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.account.ConsumeTypeEnum;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.account.FillBussinessTypeEnum;
import com.ziroom.minsu.valenum.account.FillTypeEnum;
import com.ziroom.minsu.valenum.common.ErrorCodeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.finance.PaymentSourceTypeEnum;
import com.ziroom.minsu.valenum.finance.PaymentTypeEnum;
import com.ziroom.minsu.valenum.finance.SyncStatusEnum;
import com.ziroom.minsu.valenum.order.AuditStatusEnum;
import com.ziroom.minsu.valenum.order.CashbackStatusEnum;
import com.ziroom.minsu.valenum.order.FeeItemCodeEnum;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderPayTypeChannelEnum;
import com.ziroom.minsu.valenum.order.OrderPaymentTypeEnum;
import com.ziroom.minsu.valenum.order.PaySourceTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;

import org.apache.cxf.binding.soap.Soap11;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>订单返现相关接口proxy层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月8日
 * @since 1.0
 * @version 1.0
 */
@Service("order.financeCashbackServiceProxy")
public class FinanceCashbackServiceProxy implements FinanceCashbackService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCashbackServiceProxy.class);
	
	@Resource(name = "order.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "order.financeCashbackServiceImpl")
	private FinanceCashbackServiceImpl financeCashbackServiceImpl;

	@Resource(name = "order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl;
	
	@Resource(name = "order.callAccountServiceProxy")
	private CallAccountServiceProxy callAccountServiceProxy;
	
	@Resource(name = "order.orderMoneyServiceImpl")
	private OrderMoneyServiceImpl orderMoneyServiceImpl;
	
	@Resource(name = "order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;
	
	@Resource(name="customer.customerBlackService")
	private CustomerBlackService customerBlackService;	
	
	
	/**
	 * troy查询返现申请列表
	 * @author lishaochuan
	 * @create 2016年9月8日下午2:23:11
	 * @param param
	 * @return
	 */
	@Override
	public String getOrderCashbackList(String param) {
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto = new DataTransferObject();
		try {
			OrderCashbackRequest request = JsonEntityTransform.json2Object(param, OrderCashbackRequest.class);
			PagingResult<OrderCashbackVo> pageResult = financeCashbackServiceImpl.getOrderCashbackList(request);
			dto.putValue("total", pageResult.getTotal());
        	dto.putValue("list", pageResult.getRows());
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	
	/**
	 * 保存返现
	 * @author lishaochuan
	 * @create 2016年9月9日上午10:14:05
	 * @param param
	 * @return
	 */
	@Override
	public String saveCashback(String param) {
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto = new DataTransferObject();
		try {
			FinanceCashbackEntity cashback = JsonEntityTransform.json2Object(param, FinanceCashbackEntity.class);
			if(Check.NuNObj(cashback)){
				LogUtil.error(LOGGER, "参数错误param:{}", param);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
			
			//返现表
			String orderSn = cashback.getOrderSn();
			long count = financeCashbackServiceImpl.countByOrderSn(orderSn);
			String cashbackSn = "FX" + orderSn + "_" + ++count;
			cashback.setCashbackSn(cashbackSn);
			cashback.setCashbackStatus(CashbackStatusEnum.INIT.getCode());
			
			//返现操作日志表
			FinanceCashbackLogEntity cashbackLog = new FinanceCashbackLogEntity();
			cashbackLog.setFid(UUIDGenerator.hexUUID());
			cashbackLog.setCashbackSn(cashback.getCashbackSn());
			cashbackLog.setToStatus(cashback.getCashbackStatus());
			cashbackLog.setCreateId(cashback.getCreateId());
			financeCashbackServiceImpl.saveCashback(cashback, cashbackLog);
			
			dto.putValue("cashbackSn", cashbackSn);
			//返现表==》如果返现单生成次数>3，发短信
			long countNeedSms = financeCashbackServiceImpl.countByOrderSnNew(orderSn);
			try {
				// 1、如果返现单生成次数>3，发短信
				if(countNeedSms > 3){
					orderMsgProxy.sendMsg4CashbackManyTimes(countNeedSms, cashbackSn, orderSn);
				}
				// 2、如果返现单金额>订单金额，发短信
				OrderMoneyEntity orderMoney = orderMoneyServiceImpl.getOrderMoneyByOrderSn(cashback.getOrderSn());
				if(cashback.getTotalFee() > orderMoney.getPayMoney()){
					orderMsgProxy.sendMsg4CashbackHighFee(cashbackSn, orderSn, cashback.getTotalFee(), orderMoney.getPayMoney());
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "返现单监控告警发送短信失败：e：{}", e);
			}
			
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	

	/**
	 * troy查询返现审核列表
	 * @author lishaochuan
	 * @create 2016年9月9日下午4:03:35
	 * @param param
	 * @return
	 */
	@Override
	public String getAuditCashbackList(String param) {
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto = new DataTransferObject();
		try {
			AuditCashbackQueryRequest request = JsonEntityTransform.json2Object(param, AuditCashbackQueryRequest.class);
			if(Check.NuNObj(request)){
				LogUtil.error(LOGGER, "参数错误param:{}", param);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
			PagingResult<AuditCashbackVo> pageResult = financeCashbackServiceImpl.queryByCondition(request);
			dto.putValue("total", pageResult.getTotal());
        	dto.putValue("list", pageResult.getRows());
		
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	/**
	 * troy查询返现总金额
	 * @author lishaochuan
	 * @create 2016年9月13日下午4:57:42
	 * @param param
	 * @return
	 */
	public String getAuditCashbackSumFee(String param){
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto = new DataTransferObject();
		try {
			AuditCashbackQueryRequest request = JsonEntityTransform.json2Object(param, AuditCashbackQueryRequest.class);
			if(Check.NuNObj(request)){
				LogUtil.error(LOGGER, "参数错误param:{}", param);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
        	
        	long sumFee = financeCashbackServiceImpl.sumFeeByCondition(request);
        	dto.putValue("sumFee", sumFee);
		
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	/**
	 * troy查询返现日志
	 * @author lishaochuan
	 * @create 2016年9月11日下午2:56:58
	 * @param params
	 * @return
	 */
	@Override
	public String getLogByCashbackSn(String cashbackSn) {
		LogUtil.info(LOGGER, "参数cashbackSn:{}", cashbackSn);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(cashbackSn)){
				LogUtil.error(LOGGER, "参数错误cashbackSn:{}", cashbackSn);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
			List<FinanceCashbackLogEntity> cashbackLogs = financeCashbackServiceImpl.queryLogByCashbackSn(cashbackSn);
			dto.putValue("logs", cashbackLogs);
		
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	/**
	 * 审核返现
	 * @author lishaochuan
	 * @create 2016年9月9日下午8:20:32
	 * @param param
	 * @return
	 */
	@Override
	public String auditCashback(String param) {
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto = new DataTransferObject();
		try {
			AuditCashbackRequest request = JsonEntityTransform.json2Object(param, AuditCashbackRequest.class);
			if(Check.NuNObj(request) || Check.NuNCollection(request.getCashbackSns())){
				LogUtil.error(LOGGER, "auditCashback()参数为空:param:{}", param);
				throw new BusinessException("auditCashback()参数为空");
			}
			List<String> cashbackSns = request.getCashbackSns();

			if (Check.NuNCollection(cashbackSns)) {
				LogUtil.error(LOGGER, "auditCashback() cashbackSns 为空:cashbackSns:{}", JsonEntityTransform.Object2Json(cashbackSns));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("返现单号不能为空");
				return dto.toJsonString();
			}

			for (String cashBackSn : cashbackSns) {
				//查询返现单
				FinanceCashbackEntity cashback = financeCashbackServiceImpl.queryByCashbackSn(cashBackSn);
				if (Check.NuNObj(cashback)) {
					LogUtil.info(LOGGER, "此返现单不存在，param:{},cashback:{}", param, JsonEntityTransform.Object2Json(cashback));
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("返现单不存在，返现单号：" + cashBackSn);
					return dto.toJsonString();
				}

				if(cashback.getCashbackStatus() != CashbackStatusEnum.INIT.getCode()){
					LogUtil.info(LOGGER, "此返现单不为初始状态，param:{},cashback:{}", param, JsonEntityTransform.Object2Json(cashback));
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("此返现单已经审核过");
					return dto.toJsonString();
				}

				//生成返现Log
				FinanceCashbackLogEntity cashbackLog = new FinanceCashbackLogEntity();
				cashbackLog.setFid(UUIDGenerator.hexUUID());
				cashbackLog.setCashbackSn(cashback.getCashbackSn());
				cashbackLog.setFromStatus(CashbackStatusEnum.INIT.getCode());
				cashbackLog.setToStatus(CashbackStatusEnum.AUDIT.getCode());
				cashbackLog.setCreateId(request.getUserId());

				//查询订单
				OrderEntity order = orderCommonServiceImpl.getOrderBaseByOrderSn(cashback.getOrderSn());

				//1.生成收款单
				FinancePaymentVouchersEntity payment = this.getVirtulPayment(cashback, order);
				//2.记录失败的充值log
				AccountFillLogEntity accountFill = this.getVirtulAccountFill(cashback, order);
				//3.生成未生效的付款单
				FinancePayAndDetailVo payVourcher = this.getVirtulPayVourchers(cashback, order);

				//审核
				financeCashbackServiceImpl.auditCashback(cashBackSn, cashbackLog, payment, accountFill, payVourcher);

				//充值，失败不影响返回结果
				boolean fillFlag = this.fillCuponFreeze(accountFill, payVourcher);

				//充值成功，修改充值记录、付款单状态
				if(fillFlag){
					try {
						financeCashbackServiceImpl.updateManyStatus(accountFill, payVourcher.getPvSn());
						//如果是 房东进击活动 给房东发送短信
						 if(cashBackSn.contains("RANK")){
							 String rankArr[] = cashBackSn.split("_");
							 LogUtil.info(LOGGER, "【房东进击活动发现审核通过发送短信】cashBackSn={}", cashBackSn);
							 if(rankArr.length == 3){
								 this.orderMsgProxy.sendCashOrderToLan(order, rankArr[2]);
							 }
						 }
					} catch (Exception e) {
						//不影响前台提示
						LogUtil.error(LOGGER, "充值成功，修改充值记录、付款单状态失败");
					}
				}
			}
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	
	/**
	 * 生成收款单实体
	 * @author lishaochuan
	 * @create 2016年9月12日上午11:11:39
	 * @param cashback
	 * @param order
	 * @return
	 */
	private FinancePaymentVouchersEntity getVirtulPayment(FinanceCashbackEntity cashback, OrderEntity order){
		FinancePaymentVouchersEntity payment = new FinancePaymentVouchersEntity();
		payment.setFid(UUIDGenerator.hexUUID());
		payment.setPaymentSn(OrderSnUtil.getPaymentSn());
		payment.setOrderSn(order.getOrderSn());
		payment.setCityCode(order.getCityCode());
		payment.setSourceType(PaymentSourceTypeEnum.customer.getCode());
		payment.setPayType(OrderPayTypeChannelEnum.getPayTypeByPlateFormName(SysConst.cash_fill).getPayType());
		payment.setPayTime(new Date());
		payment.setPaymentType(PaymentTypeEnum.cashback.getCode());
		payment.setPaymentUid(cashback.getReceiveUid());
		payment.setTotalFee(cashback.getTotalFee());
		payment.setNeedMoney(cashback.getTotalFee());
		payment.setTradeNo(cashback.getCashbackSn());
		payment.setSyncStatus(SyncStatusEnum.unsync.getCode());
		payment.setIsSend(YesOrNoEnum.NO.getCode());
		payment.setPayTime(new Date());
		payment.setRunTime(new Date());
		return payment;
	}
	
	/**
	 * 生成充值log实体
	 * @author lishaochuan
	 * @create 2016年9月12日上午11:11:55
	 * @param cashback
	 * @param order
	 * @return
	 */
	private AccountFillLogEntity getVirtulAccountFill(FinanceCashbackEntity cashback, OrderEntity order){
        AccountFillLogEntity accountFill = new AccountFillLogEntity();
        accountFill.setFillSn(OrderSnUtil.getFillSn());
        accountFill.setCityCode(order.getCityCode());
        accountFill.setOrderSn(order.getOrderSn());
        accountFill.setBussinessType(FillBussinessTypeEnum.coupon_fill.getCode());
        accountFill.setPayTime(new Date());
        accountFill.setFillType(FillTypeEnum.cashback.getCode());
        accountFill.setFillMoneyType(ConsumeTypeEnum.filling_freeze.getCode());
        accountFill.setTradeNo(cashback.getCashbackSn());
        accountFill.setTargetUid(cashback.getReceiveUid());
        accountFill.setTargetType(cashback.getReceiveType());
        accountFill.setTotalFee(cashback.getTotalFee());
        accountFill.setFillStatus(YesOrNoEnum.NO.getCode()); //先生成失败的充值记录
        accountFill.setResultMsg("返现生成充值记录");
        accountFill.setPayType(SysConst.cash_fill);
        accountFill.setRunTime(DateUtil.intervalDate(5, IntervalUnit.MINUTE)); //5分钟后
		return accountFill;
    }
	
	/**
	 * 生成付款单实体
	 * @author lishaochuan
	 * @create 2016年9月12日上午11:12:10
	 * @param cashback
	 * @param order
	 * @return
	 */
	private FinancePayAndDetailVo getVirtulPayVourchers(FinanceCashbackEntity cashback, OrderEntity order){
		FinancePayAndDetailVo payVourcher = new FinancePayAndDetailVo();
		List<FinancePayVouchersDetailVo> details = new ArrayList<>();
		payVourcher.setPvSn(OrderSnUtil.getPvSn());
		payVourcher.setFinancePayVouchersDetailList(details);
        payVourcher.setOrderSn(order.getOrderSn());
        payVourcher.setCityCode(order.getCityCode());
        payVourcher.setPaySourceType(PaySourceTypeEnum.CASHBACK.getCode());
        payVourcher.setReceiveUid(cashback.getReceiveUid());
        payVourcher.setReceiveType(cashback.getReceiveType());
        payVourcher.setPayUid(cashback.getReceiveUid());
        payVourcher.setPayType(cashback.getReceiveType());
        payVourcher.setTotalFee(cashback.getTotalFee());
        payVourcher.setGenerateFeeTime(new Date());
        
		//2018-01-05  返现单审核通过，将付款单执行时间设置为，第二天的12点=========开始
		String dayAfterDateStr = DateUtil.getDayAfterDate(new Date());
		dayAfterDateStr = dayAfterDateStr+ " 12:00:00";			
		Date dayAfterDate = null;
		try {
			dayAfterDate = DateUtil.parseDate(dayAfterDateStr, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			LogUtil.error(LOGGER, "getVirtulPayVourchers（返现单审核通过，生成付款单）,dayAfterDateStr={},dayAfterDate={}", dayAfterDateStr, JsonEntityTransform.Object2Json(dayAfterDate));
			throw new BusinessException("返现单审核通过生成付款单，设置runTime异常");
		}
		//2018-01-05  返现单审核通过，将付款单执行时间设置为，第二天的12点=========结束
        payVourcher.setRunTime(dayAfterDate);
        payVourcher.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
        payVourcher.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
        payVourcher.setPaymentStatus(PaymentStatusEnum.INEFFECTIVE.getCode()); //未生效的状态
		
        FinancePayVouchersDetailVo detail = new FinancePayVouchersDetailVo();
        detail.setFeeItemCode(FeeItemCodeEnum.CASHBACK.getCode());
        detail.setItemMoney(cashback.getTotalFee());
        details.add(detail);
		return payVourcher;
	}
	
	
	/**
	 * 调账户充值接口
	 * @author lishaochuan
	 * @create 2016年9月12日下午8:27:54
	 * @param accountFill
	 * @param payVourcher
	 * @return
	 */
	private boolean fillCuponFreeze(AccountFillLogEntity accountFill, FinancePayAndDetailVo payVourcher){
        FillMoneyRequest fillRequest = new FillMoneyRequest();
        //fill.setFillType(FillTypeEnum.coupon.getCode());
		fillRequest.setOrderSn(accountFill.getOrderSn());
        //fill.setFillMoneyType(accountFill.getFillMoneyType());
        //fillRequest.setBusiness_type(FillBussinessTypeEnum.coupon_fill.getCode());
        fillRequest.setTotalFee(accountFill.getTotalFee());
        fillRequest.setDzCityCode(accountFill.getCityCode());
        fillRequest.setUid(accountFill.getTargetUid());
        fillRequest.setUidType(CustomerTypeEnum.getCodeByStatusCode(accountFill.getTargetType()));
        fillRequest.setTradeNo(accountFill.getTradeNo());
        fillRequest.setPayType(SysConst.cash_fill);
        fillRequest.setPayTime(new Date());
        fillRequest.setBiz_common_type(SysConst.account_fill_money+"."+ FillBussinessTypeEnum.receive_fill.getCode());
        
        LogUtil.info(LOGGER, "fillCuponFreeze参数:{}", JsonEntityTransform.Object2Json(fillRequest));
        try {
        	Map<String,String> resMap =  callAccountServiceProxy.fillFreezeAmount(fillRequest);
            if(!ErrorCodeEnum.success.getCodeEn().equals(resMap.get("status"))){
            	LogUtil.error(LOGGER, "返现充值失败，交给定时任务补刀，resMap：{}", resMap);
            	return false;
            }
            return true;
            
        } catch (Exception e) {
            LogUtil.error(LOGGER, "fillCuponFreeze error:{}", e);
        }
        return false;
    }
	
	
	/**
	 * 驳回返现
	 * @author lishaochuan
	 * @create 2016年9月11日下午3:59:38
	 * @param param
	 * @return
	 */
	@Override
	public String rejectCashback(String param) {
		LogUtil.info(LOGGER, "参数:{}", param);
		DataTransferObject dto = new DataTransferObject();
		try {
			AuditCashbackRequest request = JsonEntityTransform.json2Object(param, AuditCashbackRequest.class);
			if(Check.NuNObj(request) || Check.NuNCollection(request.getCashbackSns())){
				LogUtil.error(LOGGER, "rejectCashback()参数为空:param:{}", param);
				throw new BusinessException("rejectCashback()参数为空");
			}
			List<String> cashbackSns = request.getCashbackSns();
			
			//查询过滤掉审核状态不为"初始"的
			List<FinanceCashbackEntity> cashbacks = financeCashbackServiceImpl.queryByCashbackSns(cashbackSns);
			cashbackSns.clear();
			for (FinanceCashbackEntity cashback : cashbacks) {
				if(cashback.getCashbackStatus() == CashbackStatusEnum.INIT.getCode()){
					cashbackSns.add(cashback.getCashbackSn());
				}
			}
			
			//记录log
			List<FinanceCashbackLogEntity> cashbackLogs = new ArrayList<>();
			for (String cashbackSn : cashbackSns) {
				FinanceCashbackLogEntity cashbackLog = new FinanceCashbackLogEntity();
				cashbackLog.setFid(UUIDGenerator.hexUUID());
				cashbackLog.setCashbackSn(cashbackSn);
				cashbackLog.setFromStatus(CashbackStatusEnum.INIT.getCode());
				cashbackLog.setToStatus(CashbackStatusEnum.REJECT.getCode());
				cashbackLog.setCreateId(request.getUserId());
				cashbackLogs.add(cashbackLog);
			}
			
			financeCashbackServiceImpl.rejectCashbacks(cashbackSns, cashbackLogs);
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}



	/**
	 * 根据uid和actSn，获取该uid（无论房东房客）在该活动下，已经有了多少返现单（无论什么状态）
	 *
	 * @author loushuai
	 * @created 2018年1月22日 下午12:45:24
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String getHasCashBackNum(String object2Json) {
		LogUtil.info(LOGGER, "getHasCashBackNum方法，object2Json={}", object2Json);
		DataTransferObject dto = new DataTransferObject();
		try {
			Map<String, Object> param = (Map<String, Object>) JsonEntityTransform.json2Map(object2Json);
			if(Check.NuNMap(param)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
			if(Check.NuNObj(param.get("uid")) || Check.NuNObj(param.get("actSn"))){
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空");
	            return dto.toJsonString();
			}
			long hasCashBackNu = financeCashbackServiceImpl.getHasCashBackNum(param);
			dto.putValue("hasCashBackNu", hasCashBackNu);
		} catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 批量申请返现单，单个处理没一个返现单
	 *
	 * @author loushuai
	 * @created 2018年1月24日 下午2:25:48
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String saveCashbackBatch(String param) {
		LogUtil.info(LOGGER, "saveCashbackFromBatch，参数:{}", param);
		long start = System.currentTimeMillis();
		DataTransferObject dto = new DataTransferObject();
		StringBuilder orOrderBuilder = null;
		StringBuilder upPayOrderBuilder = null;
		StringBuilder customerBlackBuilder = null;
		StringBuilder numGreateOneBuilder = null;//收款人为房东且超过一笔的
		StringBuilder numGreateOneTenBuilder = null;//收款人为房客且超过一笔的
		try {
			List<FinanceCashbackEntity> cashbackList = JsonEntityTransform.json2ObjectList(param, FinanceCashbackEntity.class);
			if(Check.NuNCollection(cashbackList)){
				LogUtil.error(LOGGER, "saveCashbackFromBatch方法，集合参数为空");
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto.toJsonString();
			}
			orOrderBuilder = new StringBuilder();
			upPayOrderBuilder = new StringBuilder();
			customerBlackBuilder = new StringBuilder();
			numGreateOneBuilder = new StringBuilder();
			numGreateOneTenBuilder = new StringBuilder();
			for (FinanceCashbackEntity cashback : cashbackList) {
				String orderSn = cashback.getOrderSn();
				//批量申请的的excel中，没有填写uid，所以要去order表中查，然后根据receiveType判断
				OrderEntity orderEntity = orderCommonServiceImpl.getOrderBaseByOrderSn(orderSn);
				if(Check.NuNObj(orderEntity)){
					orOrderBuilder.append(orderSn);
					orOrderBuilder.append(";");
					continue;
				}
				//未支付订单直接返回
				if(OrderPayStatusEnum.HAS_PAY.getPayStatus()!=orderEntity.getPayStatus()){
					upPayOrderBuilder.append(orderSn);
					upPayOrderBuilder.append(";");
					continue;
				}
				if(cashback.getReceiveType().intValue()==2){
					cashback.setReceiveUid(orderEntity.getUserUid());
				}else{
					cashback.setReceiveUid(orderEntity.getLandlordUid());
				}
				//校验receiveUid是否在民宿黑名单
				String result = customerBlackService.findCustomerBlackByUid(cashback.getReceiveUid());
				LogUtil.info(LOGGER, "saveCashbackFromBatch方法,校验没一个receiveUid是否为黑名单,result={}", result);
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
				if(resultDto.getCode()==DataTransferObject.SUCCESS){
					CustomerBlackEntity customerBlack = resultDto.parseData("obj", new TypeReference<CustomerBlackEntity>() {});
					if(!Check.NuNObj(customerBlack)){
						customerBlackBuilder.append(orderSn);
						customerBlackBuilder.append(";");
						continue;
					}
				}
				//receiveUid在该actSn下，如果有超过一比的返现单（无论状态，无论房东房客），继续生成，但是需要返回页面提醒
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("uid", cashback.getReceiveUid());
				paramMap.put("actSn", cashback.getActSn());
				long hasCashBackNum = financeCashbackServiceImpl.getHasCashBackNum(paramMap);
				if(hasCashBackNum>=1){
					if(cashback.getReceiveType().intValue()==1){
						numGreateOneBuilder.append(orderSn);
						numGreateOneBuilder.append(";");
					}else{
						numGreateOneTenBuilder.append(orderSn);
						numGreateOneTenBuilder.append(";");
					}
					
				}
				
				long count = financeCashbackServiceImpl.countByOrderSn(orderSn);
				String cashbackSn = "FX" + orderSn + "_" + ++count;
				cashback.setCashbackSn(cashbackSn);
				cashback.setCashbackStatus(CashbackStatusEnum.INIT.getCode());
				
				//返现操作日志表
				FinanceCashbackLogEntity cashbackLog = new FinanceCashbackLogEntity();
				cashbackLog.setFid(UUIDGenerator.hexUUID());
				cashbackLog.setCashbackSn(cashback.getCashbackSn());
				cashbackLog.setToStatus(cashback.getCashbackStatus());
				cashbackLog.setCreateId(cashback.getCreateId());
				financeCashbackServiceImpl.saveCashback(cashback, cashbackLog);
				dto.putValue("cashbackSn", cashbackSn);
				//返现表==》如果返现单生成次数>3，发短信
				long countNeedSms = financeCashbackServiceImpl.countByOrderSnNew(orderSn);
				try {
					// 1、如果返现单生成次数>3，发短信
					if(countNeedSms > 3){
						orderMsgProxy.sendMsg4CashbackManyTimes(countNeedSms, cashbackSn, orderSn);
					}
					// 2、如果返现单金额>订单金额，发短信
					OrderMoneyEntity orderMoney = orderMoneyServiceImpl.getOrderMoneyByOrderSn(cashback.getOrderSn());
					if(cashback.getTotalFee() > orderMoney.getPayMoney()){
						orderMsgProxy.sendMsg4CashbackHighFee(cashbackSn, orderSn, cashback.getTotalFee(), orderMoney.getPayMoney());
					}
				} catch (Exception e) {
					LogUtil.error(LOGGER, "返现单监控告警发送短信失败：e：{}", e);
					continue;
				}
			}
			
		} catch(Exception e){
        	LogUtil.error(LOGGER, "saveCashbackFromBatch发生异常:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
		if(orOrderBuilder != null){
			dto.putValue("noOrderList", orOrderBuilder.toString());
		}
		if(upPayOrderBuilder != null){
			dto.putValue("upPayOrderList", upPayOrderBuilder.toString());
		}
		if(customerBlackBuilder != null){
			dto.putValue("customerBlackList", customerBlackBuilder.toString());
		}
		if(numGreateOneBuilder != null){
			dto.putValue("numGreateOneList", numGreateOneBuilder.toString());
		}
		if(numGreateOneTenBuilder != null){
			dto.putValue("numGreateOneTenList", numGreateOneTenBuilder.toString());
		}
		long end = System.currentTimeMillis();
        LogUtil.info(LOGGER, "saveCashbackFromBatch方法,结果={},耗时={}", dto.toJsonString(),end-start);
		return dto.toJsonString();
	}
}
