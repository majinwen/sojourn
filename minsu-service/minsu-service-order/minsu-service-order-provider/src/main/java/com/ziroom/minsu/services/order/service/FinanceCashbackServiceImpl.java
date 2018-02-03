package com.ziroom.minsu.services.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.AccountFillLogEntity;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.entity.order.FinanceCashbackLogEntity;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.services.order.dao.AccountFillLogDao;
import com.ziroom.minsu.services.order.dao.FinanceCashbackDao;
import com.ziroom.minsu.services.order.dao.FinanceCashbackLogDao;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDao;
import com.ziroom.minsu.services.order.dao.FinancePaymentVouchersDao;
import com.ziroom.minsu.services.order.dao.OrderDao;
import com.ziroom.minsu.services.order.dao.VirtualPayVouchersDao;
import com.ziroom.minsu.services.order.dto.AccountFillLogRequest;
import com.ziroom.minsu.services.order.dto.AuditCashbackQueryRequest;
import com.ziroom.minsu.services.order.dto.OrderCashbackRequest;
import com.ziroom.minsu.services.order.entity.AuditCashbackVo;
import com.ziroom.minsu.services.order.entity.CashbackOrderVo;
import com.ziroom.minsu.services.order.entity.FinancePayAndDetailVo;
import com.ziroom.minsu.services.order.entity.OrderCashbackVo;

/**
 * <p>订单返现相关service</p>
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
@Service("order.financeCashbackServiceImpl")
public class FinanceCashbackServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCashbackServiceImpl.class);
	
	@Resource(name = "order.orderDao")
	private OrderDao orderDao;

	@Resource(name = "order.financeCashbackDao")
	private FinanceCashbackDao financeCashbackDao;
	
	@Resource(name = "order.financeCashbackLogDao")
	private FinanceCashbackLogDao financeCashbackLogDao;
	
	@Resource(name ="order.financePaymentVouchersDao")
	private FinancePaymentVouchersDao financePaymentVouchersDao;
	
	@Resource(name = "order.accountFillLogDao")
    private AccountFillLogDao accountFillLogDao;
	
	@Resource(name = "order.virtualPayVouchersDao")
	private VirtualPayVouchersDao virtualPayVouchersDao;
	
	@Resource(name = "order.financePayVouchersDao")
	private FinancePayVouchersDao financePayVouchersDao;
	
	
	/**
	 * troy查询返现申请列表
	 * @author lishaochuan
	 * @create 2016年9月8日下午2:10:17
	 * @param request
	 * @return
	 */
	public PagingResult<OrderCashbackVo> getOrderCashbackList(OrderCashbackRequest request){
		return orderDao.getOrderCashbackList(request);
	}
	
	
	/**
	 * 根据返现单号，查询返现单
	 * @author lishaochuan
	 * @create 2016年9月12日上午10:29:30
	 * @param cashbackSn
	 * @return
	 */
	public FinanceCashbackEntity queryByCashbackSn(String cashbackSn){
		if(Check.NuNStr(cashbackSn)){
			LogUtil.error(LOGGER, "queryByCashbackSn()参数为空,cashbackSn:{}", cashbackSn);
			throw new BusinessException("queryByCashbackSn()参数为空");
		}
		return financeCashbackDao.queryByCashbackSn(cashbackSn);
	}
	
	
	/**
	 * 根据订单号、充值单号，查询返现单
	 * @author lishaochuan
	 * @create 2016年9月12日下午9:00:25
	 * @param orderSn
	 * @param fillSn
	 * @return
	 */
	public FinanceCashbackEntity queryByOrderSnFillSn(String orderSn, String fillSn){
		if(Check.NuNObjs(orderSn, fillSn)){
			LogUtil.error(LOGGER, "queryByOrderSnFillSn()参数为空,orderSn:{},fillSn:{}", orderSn, fillSn);
			throw new BusinessException("queryByOrderSnFillSn()参数为空");
		}
		return financeCashbackDao.queryByOrderSnFillSn(orderSn, fillSn);
	}
	
	
	/**
	 * 根据返现单号list查询返现列表
	 * @author lishaochuan
	 * @create 2016年9月8日下午8:52:13
	 * @param orderSn
	 * @return
	 */
	public List<FinanceCashbackEntity> queryByCashbackSns(List<String> cashbackSns){
		if(Check.NuNCollection(cashbackSns)){
			LogUtil.error(LOGGER, "queryByCashbackSns()参数为空,cashbackSns:{}", cashbackSns);
			throw new BusinessException("queryByCashbackSns()参数为空");
		}
		return financeCashbackDao.queryByCashbackSns(cashbackSns);
	}
	
	/**
	 * 根据orderSn查询返现数量
	 * @author lishaochuan
	 * @create 2016年9月9日上午9:25:01
	 * @param orderSn
	 * @return
	 */
	public long countByOrderSn(String orderSn){
		if(Check.NuNStr(orderSn)){
			LogUtil.error(LOGGER, "queryByOrderSn()参数为空,orderSn:{}", orderSn);
			throw new BusinessException("queryByOrderSn()参数为空");
		}
		return financeCashbackDao.countByOrderSn(orderSn);
	}
	
	/**
	 * 根据orderSn查询返现数量
	 * @author loushuai
	 * @create 2018年2月1日上午9:25:01
	 * @param orderSn
	 * @return
	 */
	public long countByOrderSnNew(String orderSn){
		if(Check.NuNStr(orderSn)){
			LogUtil.error(LOGGER, "queryByOrderSn()参数为空,orderSn:{}", orderSn);
			throw new BusinessException("queryByOrderSn()参数为空");
		}
		return financeCashbackDao.countByOrderSnNew(orderSn);
	}
	
	/**
	 * 保存返现
	 * @author lishaochuan
	 * @create 2016年9月9日上午9:55:13
	 * @param cashback
	 * @return
	 */
	public void saveCashback(FinanceCashbackEntity cashback, FinanceCashbackLogEntity cashbackLog){
		if(Check.NuNObjs(cashback, cashbackLog)){
			LogUtil.error(LOGGER, "saveCashback()参数为空,cashback:{},cashbackLog:{}", cashback, cashbackLog);
			throw new BusinessException("saveCashback()参数为空");
		}
		int num = financeCashbackDao.saveCashback(cashback);
		if(num != 1){
			LogUtil.error(LOGGER, "保存返现失败，num:{}, cashback:{}", num, JsonEntityTransform.Object2Json(cashback));
			throw new BusinessException("保存返现失败");
		}
		num = financeCashbackLogDao.saveCashbackLog(cashbackLog);
		if(num != 1){
			LogUtil.error(LOGGER, "保存返现log失败，num:{}, cashbackLog:{}", num, JsonEntityTransform.Object2Json(cashbackLog));
			throw new BusinessException("保存返现log失败");
		}
	}
	
	
	
	/**
	 * troy根据条件查询申请返现列表
	 * @author lishaochuan
	 * @create 2016年9月9日下午3:52:31
	 * @param request
	 * @return
	 */
	public PagingResult<AuditCashbackVo> queryByCondition(AuditCashbackQueryRequest request){
		if(Check.NuNObj(request)){
			LogUtil.error(LOGGER, "queryByCondition()参数为空,request:{}", request);
			throw new BusinessException("queryByCondition()参数为空");
		}
		return financeCashbackDao.queryByCondition(request);
	}
	
	/**
	 * troy根据条件查询申请返现总额
	 * @author lishaochuan
	 * @create 2016年9月13日下午4:37:50
	 * @param request
	 * @return
	 */
	public long sumFeeByCondition(AuditCashbackQueryRequest request){
		if(Check.NuNObj(request)){
			LogUtil.error(LOGGER, "queryByCondition()参数为空,request:{}", request);
			throw new BusinessException("queryByCondition()参数为空");
		}
		return financeCashbackDao.sumFeeByCondition(request);
	}
	
	
	/**
	 * 根据返现单号，查询返现日志
	 * @author lishaochuan
	 * @create 2016年9月11日下午2:56:02
	 * @param cashbackSn
	 * @return
	 */
	public List<FinanceCashbackLogEntity> queryLogByCashbackSn(String cashbackSn){
		if(Check.NuNStr(cashbackSn)){
			LogUtil.error(LOGGER, "queryByCashbackSn()参数为空,cashbackSn:{}", cashbackSn);
			throw new BusinessException("queryByCashbackSn()参数为空");
		}
		return financeCashbackLogDao.queryByCashbackSn(cashbackSn);
	}
	
	
	/**
	 * 批量审核返现
	 * @author lishaochuan
	 * @create 2016年9月9日下午8:43:05
	 * @param cashbackSns
	 * @return
	 */
	public void auditCashback(String cashbackSn, FinanceCashbackLogEntity cashbackLog, FinancePaymentVouchersEntity payment, AccountFillLogEntity accountFill, FinancePayAndDetailVo payVourcher){
		if(Check.NuNStr(cashbackSn) || Check.NuNObj(cashbackLog)){
			LogUtil.error(LOGGER, "auditCashback()参数为空:cashbackSn:{},cashbackLog:{}", cashbackSn, cashbackLog);
			throw new BusinessException("auditCashback()参数为空");
		}
		//审核返现
		int num = financeCashbackDao.auditCashback(cashbackSn, payVourcher.getPvSn(), accountFill.getFillSn());
		if(num != 1){
			LogUtil.error(LOGGER, "审核返现失败，num:{}, cashbackSn:{}", num, cashbackSn);
			throw new BusinessException("审核返现失败");
		}
		//审核log
		num = financeCashbackLogDao.saveCashbackLog(cashbackLog);
		if(num != 1){
			LogUtil.error(LOGGER, "审核返现保存log失败, num:{}, cashbackLogs:{}", num, JsonEntityTransform.Object2Json(cashbackLog));
			throw new BusinessException("审核返现保存log失败");
		}
		
		//1.生成收款单
		financePaymentVouchersDao.insertPaymentVouchers(payment);
		
		//2.记录失败的充值log
		accountFillLogDao.insertAccountFillLogRes(accountFill);
		
		//3.生成未生效的付款单
		virtualPayVouchersDao.saveFinancePayVouchers(payVourcher);
		
	}
	
	
	/**
	 * 充值成功后，修改充值记录状态、付款单状态
	 * @author lishaochuan
	 * @create 2016年9月12日下午8:13:28
	 * @param accountFillUpdate
	 * @param pvSn
	 */
	public void updateManyStatus(AccountFillLogEntity accountFill, String pvSn){
		//1、修改充值成功
        AccountFillLogRequest accountFillUpdate = new AccountFillLogRequest();
        accountFillUpdate.setFillSn(accountFill.getFillSn());
        accountFillUpdate.setOrderSn(accountFill.getOrderSn());
        accountFillUpdate.setTradeNo(accountFill.getTradeNo());
        int num = accountFillLogDao.taskUpdateFillFailRes(accountFillUpdate);
		if (num != 1) {
			LogUtil.error(LOGGER, "修改充值状态失败，交给定时任务补刀，accountFill：{}", JsonEntityTransform.Object2Json(accountFillUpdate));
			throw new BusinessException("修改充值状态失败");
		}
        //2、修改付款单状态，未生效=>未付款
		num = financePayVouchersDao.updateEffectiveStatus(pvSn);     
		if (num != 1) {
			LogUtil.error(LOGGER, "修改付款单状态失败，交给定时任务补刀，pvSn：{}", pvSn);
			throw new BusinessException("修改付款单状态失败");
		}
		
	}
	
	
	
	/**
	 * 批量驳回返现
	 * @author lishaochuan
	 * @create 2016年9月11日下午4:05:08
	 * @param cashbackSns
	 * @param cashbackLogs
	 */
	public void rejectCashbacks(List<String> cashbackSns, List<FinanceCashbackLogEntity> cashbackLogs){
		if(Check.NuNCollection(cashbackSns)){
			LogUtil.error(LOGGER, "auditCashbacks()参数为空:cashbackSns:{}", cashbackSns);
			throw new BusinessException("auditCashbacks()参数为空");
		}
		int num = financeCashbackDao.rejectCashbacks(cashbackSns);
		if(num != cashbackSns.size()){
			LogUtil.error(LOGGER, "驳回返现失败，num:{}, cashback:{}", num, JsonEntityTransform.Object2Json(cashbackSns));
			throw new BusinessException("驳回返现失败");
		}
		num = financeCashbackLogDao.saveCashbackLogs(cashbackLogs);
		if(num != cashbackLogs.size()){
			LogUtil.error(LOGGER, "驳回返现保存log失败，cashbackLogs.size():{}, num:{}, cashbackLogs:{}", cashbackLogs.size(), num, JsonEntityTransform.Object2Json(cashbackLogs));
			throw new BusinessException("驳回返现保存log失败");
		}
	}


	/**
	 * 根据订单号和付款单号获取t_finance_cashback对象
	 *
	 * @author loushuai
	 * @created 2017年8月7日 上午10:40:01
	 *
	 * @param orderSn
	 * @param pvSn
	 */
	public FinanceCashbackEntity getByOrderSnAndPvsn(String orderSn, String pvSn) {
		 if(Check.NuNStr(orderSn) || Check.NuNStr(pvSn)){
			 return null;
		 }
		 return financeCashbackDao.getByOrderSnAndPvsn(orderSn, pvSn);
	}
	
	/**
	 * 
	 *  房东进击计划：查询 房东 下单返现活动 订单
	 *
	 * @author yd
	 * @created 2017年8月31日 上午11:58:03
	 *
	 * @return
	 */
	public PagingResult<CashbackOrderVo>  queryCashbackOrderVo(OrderCashbackRequest request){
		return this.orderDao.queryCashbackOrderVo(request);
	}
	
	/**
	 * 根据orderSn 和 活动号查询返现数量
	 * @author yd
	 * @create 2016年9月9日上午9:18:45
	 * @param orderSn
	 * @return
	 */
	public long countByLanlordUidAndActSn(String lanlordUid,String actSn){
		return financeCashbackDao.countByLanlordUidAndActSn(lanlordUid, actSn);
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
	public long getHasCashBackNum(Map<String, Object> map) {
		return financeCashbackDao.getHasCashBackNum(map);
	}
}
