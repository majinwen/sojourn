package com.ziroom.minsu.services.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.services.order.dto.AuditCashbackQueryRequest;
import com.ziroom.minsu.services.order.entity.AuditCashbackVo;
import com.ziroom.minsu.valenum.order.CashbackStatusEnum;


/**
 * <p>订单返现Dao</p>
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
@Repository("order.financeCashbackDao")
public class FinanceCashbackDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCashbackDao.class);
	private String SQLID = "order.financeCashbackDao.";
	
	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 根据返现单号，查询返现单
	 * @author lishaochuan
	 * @create 2016年9月12日上午10:27:56
	 * @param cashbackSn
	 * @return
	 */
	public FinanceCashbackEntity queryByCashbackSn(String cashbackSn){
		if(Check.NuNStr(cashbackSn)){
			LogUtil.error(LOGGER, "queryByCashbackSn()参数为空,cashbackSn:{}", cashbackSn);
			throw new BusinessException("queryByCashbackSn()参数为空");
		}
		return mybatisDaoContext.findOne(SQLID + "queryByCashbackSn", FinanceCashbackEntity.class, cashbackSn);
	}
	
	
	/**
	 * 根据订单号、充值单号，查询返现单
	 * @author lishaochuan
	 * @create 2016年9月12日下午8:57:49
	 * @param orderSn
	 * @param fillSn
	 * @return
	 */
	public FinanceCashbackEntity queryByOrderSnFillSn(String orderSn, String fillSn){
		if(Check.NuNObjs(orderSn, fillSn)){
			LogUtil.error(LOGGER, "queryByOrderSnFillSn()参数为空,orderSn:{},fillSn:{}", orderSn, fillSn);
			throw new BusinessException("queryByOrderSnFillSn()参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("orderSn", orderSn);
		params.put("fillSn", fillSn);
		return mybatisDaoContext.findOne(SQLID + "queryByOrderSnFillSn", FinanceCashbackEntity.class, params);
	}
	
	
	
	
	/**
	 * 根据返现单号list查询返现列表
	 * @author lishaochuan
	 * @create 2016年9月8日下午8:41:00
	 * @param orderSn
	 * @return
	 */
	public List<FinanceCashbackEntity> queryByCashbackSns(List<String> cashbackSns){
		if(Check.NuNCollection(cashbackSns)){
			LogUtil.error(LOGGER, "queryByCashbackSns()参数为空,cashbackSns:{}", cashbackSns);
			throw new BusinessException("queryByCashbackSns()参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("cashbackSns", cashbackSns);
		return mybatisDaoContext.findAll(SQLID + "queryByCashbackSns", params);
	}
	
	
	/**
	 * 根据orderSn查询返现数量
	 * @author lishaochuan
	 * @create 2016年9月9日上午9:18:45
	 * @param orderSn
	 * @return
	 */
	public long countByOrderSn(String orderSn){
		if(Check.NuNStr(orderSn)){
			LogUtil.error(LOGGER, "countByOrderSn()参数为空,orderSn:{}", orderSn);
			throw new BusinessException("countByOrderSn()参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("orderSn", orderSn);
		return mybatisDaoContext.count(SQLID + "countByOrderSn", params);
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
			LogUtil.error(LOGGER, "countByOrderSnNew,countByOrderSn()参数为空,orderSn:{}", orderSn);
			throw new BusinessException("countByOrderSn()参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("orderSn", orderSn);
		return mybatisDaoContext.count(SQLID + "countByOrderSnNew", params);
	}
	
	
	/**
	 * 保存返现
	 * @author lishaochuan
	 * @create 2016年9月9日上午9:46:09
	 * @param cashback
	 * @return
	 */
	public int saveCashback(FinanceCashbackEntity cashback){
		if(Check.NuNObj(cashback)){
			LogUtil.error(LOGGER, "saveCashback()参数为空,cashback:{}", cashback);
			throw new BusinessException("saveCashback()参数为空");
		}
		return mybatisDaoContext.save(SQLID + "insert", cashback);
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
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		if(request.getRoleType()>0){
			return mybatisDaoContext.findForPage(SQLID+"specialQueryByCondition", AuditCashbackVo.class, request.toMap(), pageBounds);
		}
		return mybatisDaoContext.findForPage(SQLID + "queryByCondition", AuditCashbackVo.class, request.toMap(), pageBounds);
	}
	
	/**
	 * troy根据条件查询申请返现总额
	 * @author lishaochuan
	 * @create 2016年9月13日下午4:37:02
	 * @param request
	 * @return
	 */
	public long sumFeeByCondition(AuditCashbackQueryRequest request){
		if(Check.NuNObj(request)){
			LogUtil.error(LOGGER, "queryByCondition()参数为空,request:{}", request);
			throw new BusinessException("queryByCondition()参数为空");
		}
		if(request.getRoleType()>0){
			return mybatisDaoContext.countBySlave(SQLID+"specialSumFeeByCondition",request.toMap());
		}
		return mybatisDaoContext.countBySlave(SQLID + "sumFeeByCondition", request.toMap());
	}
	
	
	/**
	 * 审核返现
	 * @author lishaochuan
	 * @create 2016年9月9日下午8:37:13
	 * @param cashbackSns
	 * @return
	 */
	public int auditCashback(String cashbackSn, String pvSn, String fillSn){
		if(Check.NuNStr(cashbackSn)){
			LogUtil.error(LOGGER, "auditCashback()参数为空:cashbackSn:{}", cashbackSn);
			throw new BusinessException("auditCashback()参数为空");
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("cashbackStatus", CashbackStatusEnum.AUDIT.getCode());
		params.put("oldCashbackStatus", CashbackStatusEnum.INIT.getCode());
		params.put("cashbackSn", cashbackSn);
		params.put("pvSn", pvSn);
		params.put("fillSn", fillSn);
		return mybatisDaoContext.update(SQLID + "auditCashbackStatus", params);
	}
	
	
	/**
	 * 批量驳回返现
	 * @author lishaochuan
	 * @create 2016年9月11日下午4:03:58
	 * @param cashbackSns
	 * @return
	 */
	public int rejectCashbacks(List<String> cashbackSns){
		if(Check.NuNCollection(cashbackSns)){
			LogUtil.error(LOGGER, "rejectCashbacks()参数为空:cashbackSns:{}", cashbackSns);
			throw new BusinessException("rejectCashbacks()参数为空");
		}
		
		int num = 0;
		for (String cashbackSn : cashbackSns) {
			Map<String, Object> params = new HashMap<>();
			params.put("cashbackStatus", CashbackStatusEnum.REJECT.getCode());
			params.put("oldCashbackStatus", CashbackStatusEnum.INIT.getCode());
			params.put("cashbackSn", cashbackSn);
			num += mybatisDaoContext.update(SQLID + "rejectCashbackStatus", params);
		}
		return num;
	}


	/**
	 * 根据订单号和付款单号获取t_finance_cashback对象
	 *
	 * @author loushuai
	 * @created 2017年8月7日 上午10:42:54
	 *
	 * @param orderSn
	 * @param pvSn
	 * @return
	 */
	public FinanceCashbackEntity getByOrderSnAndPvsn(String orderSn, String pvSn) {
		if(Check.NuNObjs(orderSn, pvSn)){
			LogUtil.error(LOGGER, "getByOrderSnAndPvsn()参数为空,orderSn:{},pvSn:{}", orderSn, pvSn);
			throw new BusinessException("getByOrderSnAndPvsn()参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("orderSn", orderSn);
		params.put("pvSn", pvSn);
		return mybatisDaoContext.findOne(SQLID + "getByOrderSnAndPvsn", FinanceCashbackEntity.class, params);
	}
	
	/**
	 * 根据orderSn 和 活动号查询返现数量
	 * @author yd
	 * @create 2016年9月9日上午9:18:45
	 * @param orderSn
	 * @return
	 */
	public long countByLanlordUidAndActSn(String lanlordUid,String actSn){
		if(Check.NuNStr(lanlordUid)||Check.NuNStr(actSn)){
			LogUtil.error(LOGGER, "countByOrderSnAndActSn()参数为空,orderSn:{},actSn:{}", lanlordUid,actSn);
			throw new BusinessException("countByOrderSnAndActSn()参数为空");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("lanlordUid", lanlordUid);
		params.put("actSn", actSn);
		return mybatisDaoContext.count(SQLID + "countByLanlordUidAndActSn", params);
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
		return mybatisDaoContext.count(SQLID + "getHasCashBackNum", map);
	}
}
