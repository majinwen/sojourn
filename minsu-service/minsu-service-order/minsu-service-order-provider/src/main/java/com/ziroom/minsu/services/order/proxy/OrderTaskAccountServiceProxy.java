package com.ziroom.minsu.services.order.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.AccountFillLogEntity;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.services.account.dto.FillMoneyRequest;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskAccountService;
import com.ziroom.minsu.services.order.dto.AccountFillLogRequest;
import com.ziroom.minsu.services.order.service.AccountFillLogServiceImpl;
import com.ziroom.minsu.services.order.service.FinanceCashbackServiceImpl;
import com.ziroom.minsu.services.order.service.FinancePayServiceImpl;
import com.ziroom.minsu.valenum.account.ConsumeTypeEnum;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.account.FillBussinessTypeEnum;
import com.ziroom.minsu.valenum.account.FillTypeEnum;
import com.ziroom.minsu.valenum.common.ErrorCodeEnum;

/**
 * 
 * <p>
 * 账户相关定时任务
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年5月4日
 * @since 1.0
 * @version 1.0
 */
@Component("order.orderTaskAccountServiceProxy")
public class OrderTaskAccountServiceProxy implements OrderTaskAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskAccountServiceProxy.class);

	@Resource(name = "order.messageSource")
	private MessageSource messageSource;

	@Resource(name = "order.callAccountServiceProxy")
	private CallAccountServiceProxy callAccountServiceProxy;

	@Resource(name = "order.accountFillLogServiceImpl")
	private AccountFillLogServiceImpl accountFillLogServiceImpl;
	
	@Resource(name = "order.financePayServiceImpl")
	private FinancePayServiceImpl financePayServiceImpl;
	
	@Resource(name = "order.financeCashbackServiceImpl")
	private FinanceCashbackServiceImpl financeCashbackServiceImpl;

	/**
	 * 对账户充值失败的单子进行重新充值
	 * 
	 * @author lishaochuan
	 * @create 2016年5月4日
	 */
	@Override
	public void repeatAccountFillFailed() {
		try {
			LogUtil.info(LOGGER, "【账户重新充值Job】");

            Thread task = new Thread(){
                @Override
                public void run() {
                    int limit = 150;
                    AccountFillLogRequest taskRequest = new AccountFillLogRequest();
                    taskRequest.setLimit(limit);

                    Long count = accountFillLogServiceImpl.taskCountFillFailNum();
                    LogUtil.info(LOGGER, "【账户重新充值Job】,条数:{}", count);
                    if (Check.NuNObj(count) || count == 0) {
                        return;
                    }
                    int pageAll = ValueUtil.getPage(count.intValue(), limit);
                    for (int page = 1; page <= pageAll; page++) {
                        List<AccountFillLogEntity> fillList = accountFillLogServiceImpl.taskGetFillFailList(taskRequest);
                        for (AccountFillLogEntity accountFillLog : fillList) {
                            LogUtil.info(LOGGER, "【账户重新充值Job】发现需要重新充值的accountFillLog:{}", JsonEntityTransform.Object2Json(accountFillLog));
                            try {
                                int fillMoneyType = accountFillLog.getFillMoneyType();
                                FillMoneyRequest btfr = new FillMoneyRequest();
                                btfr.setTotalFee(accountFillLog.getTotalFee());
                                btfr.setUid(accountFillLog.getTargetUid());
                                btfr.setUidType(CustomerTypeEnum.getCodeByStatusCode(accountFillLog.getTargetType()));
                                btfr.setFillType(accountFillLog.getFillType());
                                btfr.setTradeNo(accountFillLog.getTradeNo());
                                btfr.setOrderSn(accountFillLog.getOrderSn());
                                btfr.setPayType(accountFillLog.getPayType());
                                btfr.setPayTime(new Date());
                                btfr.setFillMoneyType(fillMoneyType);
                                if(accountFillLog.getBussinessType() != FillBussinessTypeEnum.coupon_fill.getCode()){
                                	//不是优惠券才传此参数
                                	btfr.setBusiness_type(accountFillLog.getBussinessType());
                                }

                                Map<String, String> result = new HashMap<String, String>();
                                if (fillMoneyType == ConsumeTypeEnum.filling_freeze.getCode()) {
                                    if(FillTypeEnum.cash.getCode() == accountFillLog.getFillType()){
                                    	// 现金充值
                                        btfr.setBiz_common_type(SysConst.account_fill_money);
                                    }else if(FillTypeEnum.coupon.getCode() == accountFillLog.getFillType() || FillTypeEnum.cashback.getCode() == accountFillLog.getFillType()){
                                    	// 虚拟充值
                                        btfr.setBiz_common_type(SysConst.account_fill_money+"."+FillBussinessTypeEnum.receive_fill.getCode());
                                    }
                                    result = callAccountServiceProxy.fillFreezeAmount(btfr);
                                    LogUtil.info(LOGGER, "【账户重新充值Job】对充值失败的单子进行重新充值冻结结束,result:{}", result);
                                } else if (fillMoneyType == ConsumeTypeEnum.filling_balance.getCode()) {
                                    // 充值余额
                                    result = callAccountServiceProxy.fillBalanceAmount(btfr);
                                    LogUtil.info(LOGGER, "【账户重新充值Job】对充值失败的单子进行重新充值余额结束,result:{}", result);
                                }

                                String resultStatus = result.get("status");
                                if (ErrorCodeEnum.success.getCodeEn().equals(resultStatus)) {
                                    AccountFillLogRequest request = new AccountFillLogRequest();
                                    request.setFillSn(accountFillLog.getFillSn());
                                    request.setOrderSn(accountFillLog.getOrderSn());
                                    request.setTradeNo(accountFillLog.getTradeNo());
                                    int num = accountFillLogServiceImpl.taskUpdateFillFailRes(request);
                                    LogUtil.info(LOGGER, "【账户重新充值Job】更新为充值成功条数：{}, fillSn:{}", num, accountFillLog.getFillSn());
                                    
                                    
                                    //如果是返现充值，充值成功需要修改付款单状态，未生效=>未付款
                                    if(FillTypeEnum.cashback.getCode() == accountFillLog.getFillType()){
                                    	FinanceCashbackEntity cashback = financeCashbackServiceImpl.queryByOrderSnFillSn(accountFillLog.getOrderSn(), accountFillLog.getFillSn());
                                    	if(Check.NuNObj(cashback)){
                                    		LogUtil.error(LOGGER, "【账户重新充值Job】查询返现单为空！cashback:{},accountFillLog:{}", cashback, JsonEntityTransform.Object2Json(accountFillLog));
                                    		return;
                                    	}
                                    	if(Check.NuNStr(cashback.getPvSn())){
                                    		LogUtil.error(LOGGER, "【账户重新充值Job】查询返现单中的pvSn为空！cashback:{},accountFillLog:{}", JsonEntityTransform.Object2Json(cashback), JsonEntityTransform.Object2Json(accountFillLog));
                                    		return;
                                    	}
                                    	num = financePayServiceImpl.updateEffectiveStatus(cashback.getPvSn());
                                    	LogUtil.info(LOGGER, "【账户重新充值Job】更新付款单状态条数：{}, pvSn:{}", num, cashback.getPvSn());
                                    }
                                    return;
                                }

                            } catch (Exception e) {
                                LogUtil.error(LOGGER, "【账户重新充值Job】accountFillLog:{},e:{}", JsonEntityTransform.Object2Json(accountFillLog), e);
                            }
                        }
                    }
                }
            };
            SendThreadPool.execute(task);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【账户重新充值Job】e:{}", e);
		}
	}


}
