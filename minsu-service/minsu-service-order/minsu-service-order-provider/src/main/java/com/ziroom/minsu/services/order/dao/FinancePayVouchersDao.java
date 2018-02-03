package com.ziroom.minsu.services.order.dao;

import java.util.Date;
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
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseDayRevenueEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.finance.dto.FinancePayVosRequest;
import com.ziroom.minsu.services.finance.entity.FinancePayVouchersVo;
import com.ziroom.minsu.services.order.entity.FinancePayDetailInfoVo;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;

/**
 * <p>
 * 付款单表操作
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/4/16.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.financePayVouchersDao")
public class FinancePayVouchersDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(FinancePayVouchersDao.class);
	private String SQLID = "order.financePayVouchersDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	
	/**
	 * troy查看付款单详情 
	 * @author lishaochuan
	 * @create 2016年5月17日上午11:59:53
	 * @param pvSn
	 * @return
	 */
	public FinancePayDetailInfoVo getFinancePayDetailInfoVo(String pvSn){
		if (Check.NuNObj(pvSn)) {
			LogUtil.error(logger, "getFinancePayDetailInfoVo pvSn:{}",pvSn);
			return null;
		}
		return mybatisDaoContext.findOne(SQLID + "getFinancePayDetailInfoVo", FinancePayDetailInfoVo.class, pvSn);
	}

	/**
	 * 根据付款单号，查询付款记录信息
	 * @author lishaochuan
	 * @param pvSn
	 * @return
	 */
	public FinancePayVouchersEntity findPayVouchersByPvSn(String pvSn) {
		if (Check.NuNObj(pvSn)) {
			LogUtil.error(logger, "findPayVouchersByPvSn pvSn:{}",pvSn);
			return null;
		}
		return mybatisDaoContext.findOne(SQLID + "findPayVouchersByPvSn", FinancePayVouchersEntity.class, pvSn);
	}

	/**
	 * 保存付款记录信息
	 * 
	 * @author lishaochuan
	 * @param payVouchersEntity
	 * @return
	 */
	public int insertPayVouchers(FinancePayVouchersEntity payVouchersEntity) {
		if (Check.NuNObj(payVouchersEntity)) {
			LogUtil.error(logger, "insertPayVouchers param error");
			return 0;
		}
		return mybatisDaoContext.save(SQLID + "insertSelective", payVouchersEntity);
	}

	/**
	 * 更新付款记录信息
	 * 
	 * @author lishaochuan
	 * @param payVouchersEntity
	 * @return
	 */
	public int updatePayVouchersByPvSn(FinancePayVouchersEntity payVouchersEntity) {
		if (Check.NuNObj(payVouchersEntity) || Check.NuNStr(payVouchersEntity.getPvSn())) {
			LogUtil.error(logger, "updatePayVouchers param:{}", payVouchersEntity);
			return 0;
		}
		return mybatisDaoContext.update(SQLID + "updatePayVouchersByPvSn", payVouchersEntity);
	}
	
	/**
	 * 提前退房标志为无效
	 * @author lishaochuan
	 * @create 2016年5月14日下午8:18:46
	 * @return
	 */
	public int deletePayVouchersByPvSnList(List<String> pvSnList) {
		if (Check.NuNObj(pvSnList)) {
			LogUtil.error(logger, "deletePayVouchersByPvSnList pvSnList:{}",pvSnList);
			return 0;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pvSnList", pvSnList);
		return mybatisDaoContext.update(SQLID + "deletePayVouchersByPvSnList", map);
	}

	/**
	 * 逻辑删除付款记录信息
	 * 
	 * @author lishaochuan
	 * @param pvSn
	 * @return
	 */
	public int deletePayVouchers(String pvSn) {
		if (Check.NuNObj(pvSn)) {
			LogUtil.error(logger, "deletePayVouchers pvSn:{}",pvSn);
			return 0;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pvSn", pvSn);
		return mybatisDaoContext.update(SQLID + "deletePayVouchers", map);
	}
	
	
	/**
	 * 查询付款单中审核通过未付款数量
	 * @author lishaochuan
	 * @create 2016年4月20日
	 * @param map
	 * @return
	 */
	public Long getNotPayVouchersCount(Map<String, Object> map){
		if (Check.NuNObj(map)) {
			LogUtil.error(logger, "getNotPayVouchersCount param:{}",map);
			return 0L;
		}
		return mybatisDaoContext.findOne(SQLID + "getNotPayVouchersCount", Long.class, map);
	}
	
	/**
	 * 查询付款单中审核通过未付款记录
	 * @author lishaochuan
	 * @create 2016年4月20日
	 * @param map
	 * @return
	 */
	public List<FinancePayVouchersEntity> getNotPayVouchersList(Map<String, Object> map){
		if (Check.NuNObj(map)) {
			LogUtil.error(logger, "getNotPayVouchersList param:{}",map);
			return null;
		}
		return mybatisDaoContext.findAll(SQLID + "getNotPayVouchersList", FinancePayVouchersEntity.class, map);
	}
	
	
	/**
	 * 查询失败的付款单数量
	 * @author lishaochuan
	 * @create 2016年9月19日下午9:21:30
	 * @param map
	 * @return
	 */
	public Long getFailedPayVouchersCount(Map<String, Object> map){
		if (Check.NuNObj(map)) {
			LogUtil.error(logger, "getFailedPayVouchersCount param:{}",map);
			return 0L;
		}
		return mybatisDaoContext.findOne(SQLID + "getFailedPayVouchersCount", Long.class, map);
	}
	
	/**
	 * 查询失败的付款单记录
	 * @author lishaochuan
	 * @create 2016年9月19日下午9:21:41
	 * @param map
	 * @return
	 */
	public List<FinancePayVouchersEntity> getFailedPayVouchersList(Map<String, Object> map){
		if (Check.NuNObj(map)) {
			LogUtil.error(logger, "getFailedPayVouchersList param:{}",map);
			return null;
		}
		return mybatisDaoContext.findAll(SQLID + "getFailedPayVouchersList", FinancePayVouchersEntity.class, map);
	}
	
	
	
	
	/**
	 * 提前退房  取消 付款单 
	 *
	 * @author liyingjie
	 * @created 2016年4月19日 
	 *
	 * @param orderSn
	 * @param cancelDate
	 * @return
	 */
	public int cancelPayVouchers(String orderSn , Date cancelDate){
		Map<String,Object> param = new HashMap<String,Object>(2);
		param.put("orderSn", orderSn);
		param.put("cancelDate", cancelDate);
		return mybatisDaoContext.update(SQLID + "payment_status", param);
	}
	
	/**
	 * 根据订单id 获取付款单列表 
	 *
	 * @author liyingjie
	 * @created 2016年4月19日 
	 *
	 * @param orderSn
	 * @return
	 */
	public List<FinancePayVouchersEntity> findByOrderSn(String orderSn){
		Map<String,Object> param = new HashMap<String,Object>(2);
		param.put("orderSn", orderSn);
		return mybatisDaoContext.findAll(SQLID + "findPayVouchersByOrderSn", FinancePayVouchersEntity.class, param);
	}


    /**
     * 根据orderSn获取付款单列表
     * @author afi
     * @created 2016年4月22日
     *
     * @param orderSn
     * @return
     */
    public List<FinancePayVouchersEntity> findEffectiveByOrderSn(String orderSn){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("orderSn", orderSn);
        return mybatisDaoContext.findAllByMaster(SQLID + "findEffectiveByOrderSn", FinancePayVouchersEntity.class, param);
    }


	/**
	 * 根据订单号查询清洁费付款单
	 * @author lishaochuan
	 * @create 2017/1/6 10:01
	 * @param
	 * @return
	 */
	public List<FinancePayVouchersEntity> findCleanByOrderSn(String orderSn){
		Map<String,Object> param = new HashMap<>();
		param.put("orderSn", orderSn);
		return mybatisDaoContext.findAllByMaster(SQLID + "findCleanByOrderSn", FinancePayVouchersEntity.class, param);
	}

	
	/**
	 * 查询用户结算生成的付款单
	 * @author lishaochuan
	 * @create 2017/1/10 17:19
	 * @param 
	 * @return 
	 */
	public List<FinancePayVouchersEntity> findSettlementByOrderSn(String orderSn){
		Map<String,Object> param = new HashMap<>();
		param.put("orderSn", orderSn);
		return mybatisDaoContext.findAll(SQLID + "findSettlementByOrderSn", FinancePayVouchersEntity.class, param);
	}

    
    /**
     * 
     * 查询房东日收益和列表
     *
     * @author bushujie
     * @created 2016年4月26日 下午3:02:44
     *
     * @param paramMap
     * @return
     */
    public List<HouseDayRevenueEntity> findLandlordDayRevenueList(Map<String, Object> paramMap){
    	return mybatisDaoContext.findAll(SQLID+"findLandlordDayRevenueList", HouseDayRevenueEntity.class, paramMap);
    }
    
    /**
     * 
     * 按条件分页查询付款单记录
     *
     * @author yd
     * @created 2016年4月28日 下午10:46:29
     *
     * @param financePayVosRequest
     * @return
     */
    public PagingResult<FinancePayVouchersVo> queryFinancePayVosByPage(FinancePayVosRequest financePayVosRequest){
    	
    	if(Check.NuNObj(financePayVosRequest)) financePayVosRequest = new FinancePayVosRequest();
    	
    	PageBounds pageBounds = new PageBounds();
    	pageBounds.setLimit(financePayVosRequest.getLimit());
    	pageBounds.setPage(financePayVosRequest.getPage());
    	if(financePayVosRequest.getRoleType()>0){
    		return this.mybatisDaoContext.findForPage(SQLID+"specialQueryFinancePayVosByPage", FinancePayVouchersVo.class, financePayVosRequest.toMap(), pageBounds);
    	}
    	return this.mybatisDaoContext.findForPage(SQLID+"queryFinancePayVosByPage", FinancePayVouchersVo.class, financePayVosRequest.toMap(), pageBounds);
    }
    
    /**
     * 查询重新生成的付款单数量
     * @author lishaochuan
     * @create 2016年8月16日下午6:00:37
     * @param parentPvSn
     * @return
     */
    public long countReCreatePvs(String parentPvSn){
		Map<String,Object> param = new HashMap<String,Object>(1);
		param.put("parentPvSn", parentPvSn);
		return mybatisDaoContext.count(SQLID+"countReCreatePvs", param);
	}
    
    /**
	 * 统计 结算类型的 付款单 数量
	 *
	 * @author liyingjie
	 * @created 2016年5月8日 
	 *
	 * @param orderSn
	 * @return
	 */
	/*public long countAllPayVouchers(String orderSn){
		Map<String,Object> param = new HashMap<String,Object>(1);
		param.put("orderSn", orderSn);
		param.put("noPaymentStatus", PaymentStatusEnum.FAILED_PAY_DONE.getCode());
		return mybatisDaoContext.count(SQLID+"countAllVouchersByOrderSn", param);
	}*/
	/**
	 * 统计 结算类型的 已付款 付款单 数量
	 *
	 * @author liyingjie
	 * @created 2016年5月8日 
	 *
	 * @param orderSn
	 * @return
	 */
	/*public long countHasPayVouchers(String orderSn){
		Map<String,Object> param = new HashMap<String,Object>(1);
		param.put("orderSn", orderSn);
		return mybatisDaoContext.count(SQLID+"countHasPayVouchers", param);
	}*/
    
    /**
     * 查询未付款的付款单数量
     * @author lishaochuan
     * @create 2016年8月31日下午5:19:39
     * @param orderSn
     * @return
     */
	public long countHasNotPayVouchers(String orderSn) {
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("orderSn", orderSn);
		return mybatisDaoContext.count(SQLID + "countHasNotPayVouchers", param);
	}

	public List<FinancePayVouchersEntity> listHasNotPayVouchers(String orderSn){
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("orderSn", orderSn);
		return mybatisDaoContext.findAll(SQLID + "countHasNotPayVouchers", param);
	}
    
	
	/**
	 * 重新生成付款单，更新旧付款单状态为打款失败已处理
	 * @author lishaochuan
	 * @create 2016年8月17日下午5:45:22
	 * @param pvSn
	 * @return
	 */
	public int updateReCreatePvsOldStatus(String pvSn) {
		if (Check.NuNStr(pvSn)) {
			LogUtil.error(logger, "重新生成付款单，更新旧付款单状态参数错误,pvSn:{}", pvSn);
			return 0;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pvSn", pvSn);
		param.put("newPaymentStatus", PaymentStatusEnum.FAILED_PAY_DONE.getCode());
		param.put("oldPaymentStatus", PaymentStatusEnum.FAILED_PAY_UNDO.getCode());
		return mybatisDaoContext.update(SQLID + "updateReCreatePvsOldStatus", param);
	}
	
	
	
	/**
	 * 修改付款单状态，未生效=>未付款
	 * @author lishaochuan
	 * @create 2016年9月12日下午8:06:32
	 * @param pvSn
	 * @return
	 */
	public int updateEffectiveStatus(String pvSn) {
		if (Check.NuNStr(pvSn)) {
			LogUtil.error(logger, "updateEffectiveStatus()参数为空,pvSn:{}", pvSn);
			throw new BusinessException("参数为空");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pvSn", pvSn);
		param.put("newPaymentStatus", PaymentStatusEnum.UN_PAY.getCode());
		param.put("oldPaymentStatus", PaymentStatusEnum.INEFFECTIVE.getCode());
		return mybatisDaoContext.update(SQLID + "updatePaymentStatus", param);
	}
	
	/**
	 * 更新付款单发送状态
	 * @author lishaochuan
	 * @create 2016年9月22日上午11:22:51
	 * @param pvSn
	 * @return
	 */
	public int updateIsSend(String pvSn){
		if (Check.NuNStr(pvSn)) {
			LogUtil.error(logger, "updateIsSend()参数为空,pvSn:{}", pvSn);
			throw new BusinessException("参数为空");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pvSn", pvSn);
		return mybatisDaoContext.update(SQLID + "updateIsSend", params);
	}


	/**
	 * 将当前的付款单状态变更为黑名单不打款
	 * @author afi
	 * @param pvSn
	 * @return
	 */
	public int updatePvBlack(String pvSn){
		if (Check.NuNStr(pvSn)) {
			LogUtil.error(logger, "updatePvBlack()参数为空,pvSn:{}", pvSn);
			throw new BusinessException("参数为空");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pvSn", pvSn);
		param.put("newPaymentStatus", PaymentStatusEnum.BLACK.getCode());
		param.put("oldPaymentStatus", PaymentStatusEnum.UN_PAY.getCode());
		return mybatisDaoContext.update(SQLID + "updatePaymentStatus", param);
	}

	/**
	 * 更新付款单状态 罚款单冲抵
	 *  1.罚款冲抵取消
	 * @author jixd
	 * @created 2017年05月11日 09:35:24
	 * @param
	 * @return
	 */
	public int updatePvOffsetCancel(String pvSn){
		if (Check.NuNStr(pvSn)) {
			LogUtil.error(logger, "updatePvOffsetCancel()参数为空,pvSn:{}", pvSn);
			throw new BusinessException("参数为空");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pvSn", pvSn);
		param.put("newPaymentStatus", PaymentStatusEnum.OFFSET_CANCEL.getCode());
		param.put("oldPaymentStatus", PaymentStatusEnum.UN_PAY.getCode());
		return mybatisDaoContext.update(SQLID + "updatePaymentStatus", param);
	}

	/**
	 * 更新付款单状态 罚款单冲抵
	 *  1.罚款冲抵重新生成付款单 取消
	 * @author jixd
	 * @created 2017年05月11日 09:35:24
	 * @param
	 * @return
	 */
	public int updatePvOffsetReproCancel(String pvSn){
		if (Check.NuNStr(pvSn)) {
			LogUtil.error(logger, "updatePvOffsetReproCancel()参数为空,pvSn:{}", pvSn);
			throw new BusinessException("参数为空");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pvSn", pvSn);
		param.put("newPaymentStatus", PaymentStatusEnum.OFFSET_REPRO_CANCEL.getCode());
		param.put("oldPaymentStatus", PaymentStatusEnum.UN_PAY.getCode());
		return mybatisDaoContext.update(SQLID + "updatePaymentStatus", param);
	}


	/**
	 * 更新付款单为未发送状态
	 * @param pvSn
	 * @return
     */
	public int updateNoSend(String pvSn){
		if (Check.NuNStr(pvSn)) {
			LogUtil.error(logger, "updateNoSend()参数为空,pvSn:{}", pvSn);
			throw new BusinessException("参数为空");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pvSn", pvSn);
		return mybatisDaoContext.update(SQLID + "updateNoSend", params);
	}
	
	/**
	 * 定时任务执行付款单后修改状态
	 * @author lishaochuan
	 * @create 2016年9月21日下午9:33:28
	 * @param payVouchers
	 * @return
	 */
	public int updateRunPaymentStatus(FinancePayVouchersEntity payVouchers) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pvSn", payVouchers.getPvSn());
		param.put("paymentStatus", payVouchers.getPaymentStatus());
		param.put("previousPaymentStatus", payVouchers.getPreviousPaymentStatus());
		param.put("paymentType", payVouchers.getPaymentType());
		return mybatisDaoContext.update(SQLID + "updateRunPaymentStatus", param);
	}


	/**
	 * 更新付款单执行时间
	 *
	 * @author lishaochuan
	 * @create 2016/12/13 14:34
	 * @param 
	 * @return 
	 */
	public int updateDelayRunTime(Map<String, Object> param){
		return mybatisDaoContext.update(SQLID + "updateDelayRunTime", param);
	}

}
