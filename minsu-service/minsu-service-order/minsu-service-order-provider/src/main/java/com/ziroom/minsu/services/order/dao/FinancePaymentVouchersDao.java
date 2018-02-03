package com.ziroom.minsu.services.order.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.exception.BusinessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.services.finance.dto.PaymentVouchersRequest;
import com.ziroom.minsu.services.finance.entity.FinancePaymentVo;


/**
 * <p>收款单表操作</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author liyingjie on 2016年4月26日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.financePaymentVouchersDao")
public class FinancePaymentVouchersDao {
	/**
	 * 日志对象
	 */
	private static Logger lOGGER = LoggerFactory.getLogger(FinancePaymentVouchersDao.class);
	private String SQLID = "order.financePaymentVouchersDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


    /**
     * 改变当前的优惠券的金额
     * @author afi
     * @param orderSn
     * @param couponMoney
     */
    public void changeCouponMoney(String orderSn,Integer couponMoney){
        Map<String,Object> par = new HashMap<>();
        par.put("orderSn",orderSn);
        par.put("couponMoney",couponMoney);
        par.put("runTime", new Date());
        int count =  mybatisDaoContext.update(SQLID + "changeCouponMoney", par);
        if(count == 0){
            throw new BusinessException("改变优惠券金额失败，orderSn：{}"+orderSn);
        }
    }
	
	/**
   	 * 给财务系统 调用 收款单
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月26日 
   	 *
   	 * @param financePaymentVouchers
   	 * @return
   	 */
	public int insertPaymentVouchers(FinancePaymentVouchersEntity financePaymentVouchers) {
		if (Check.NuNObj(financePaymentVouchers)) {
			LogUtil.error(lOGGER, "insertFinanceIncome param error");
			return 0;
		}
		
		return mybatisDaoContext.save(SQLID + "insertPaymentVoucher", financePaymentVouchers);
	}
	
	
	/**
	 * 查询订单支付的收款单
	 * @author lishaochuan
	 * @create 2016年8月24日下午9:39:41
	 * @param orderSn
	 * @return
	 */
	public FinancePaymentVouchersEntity getOrderPayment(String orderSn) {
		return mybatisDaoContext.findOne(SQLID + "getOrderPayment", FinancePaymentVouchersEntity.class, orderSn);
	}
	
	/**
	 *
	 * 分页 查询
	 *
	 * @author liyingjie
	 * @created 2016年4月16日
	 *
	 * @return
	 */
	public PagingResult<FinancePaymentVouchersEntity> getPaymentVouchersList(PaymentVouchersRequest paramRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(paramRequest.getLimit());
		pageBounds.setPage(paramRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "getListByCondiction", FinancePaymentVouchersEntity.class, paramRequest, pageBounds);
	}
	
	
	/**
	 * 
	 * 按条件分页查询付款单实体
	 *
	 * @author yd
	 * @created 2016年4月29日 上午10:31:46
	 *
	 * @param paramRequest
	 * @return
	 */
	public PagingResult<FinancePaymentVo> queryPaymentVoByPage(PaymentVouchersRequest paramRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(paramRequest.getLimit());
		pageBounds.setPage(paramRequest.getPage());
		if(paramRequest.getRoleType()>0){
			return mybatisDaoContext.findForPage(SQLID + "specialQueryPaymentVoByPage", FinancePaymentVo.class, paramRequest.toMap(), pageBounds);
		}
		return mybatisDaoContext.findForPage(SQLID + "queryPaymentVoByPage", FinancePaymentVo.class, paramRequest, pageBounds);
	}	
	
	/**
   	 *  更新 收款单
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月26日 
   	 *
   	 * @param fid
   	 * @return
   	 */
	public int taskUpdatePaymentVouchers(String fid) {
		if (Check.NuNObj(fid)) {
			LogUtil.error(lOGGER, "updatePaymentVouchers param:{}",fid);
			return 0;
		}
		Map<String,Object> params = new HashMap<String,Object>(1);
		params.put("fid", fid);
		return mybatisDaoContext.update(SQLID+"updatePaymentVouchersByFid", params);
	}


	/**
   	 *  统计 收款单 数量
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月29日 
   	 *
   	 * @param tradeNo
   	 * @return
   	 */
	public long countPaymentNum(String tradeNo) {
		Map<String,Object> params = new HashMap<String,Object>(1);
		params.put("tradeNo", tradeNo);
		
		return mybatisDaoContext.count(SQLID+"countPaymentVouchersByTradeNo", params);
	}
	
	
	
	
	/**
	 * 查询收款单中未同步收入记录count
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @return
	 */
	public long getNotSyncPaymentCount() {
		return mybatisDaoContext.findOne(SQLID + "getNotSyncPaymentCount", Long.class);
	}

	/**
	 * 查询收款单中未同步收入记录list
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param map
	 * @return
	 */
	public List<FinancePaymentVouchersEntity> getNotSyncPaymentList(Map<String, Object> map) {
		if (Check.NuNObj(map)) {
			LogUtil.error(lOGGER, "getNotSyncPaymentList param:{}",map);
			throw new BusinessException("map is null on getNotSyncPaymentList");
		}
		return mybatisDaoContext.findAllByMaster(SQLID + "getNotSyncPaymentList", FinancePaymentVouchersEntity.class, map);
	}
	
	
	/**
	 * 更新为已同步状态
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param map
	 * @return
	 */
	public int updatePaymentHasSync(Map<String, Object> map){
		if (Check.NuNObj(map)) {
			LogUtil.error(lOGGER, "updatePaymentHasSync param:{}",map);
			throw new BusinessException("map is null on updatePaymentHasSync");
		}
		return mybatisDaoContext.update(SQLID+"updatePaymentHasSync", map);
	}
	/**
	 * 
	 * 查询收款单详情
	 *
	 * @author jixd
	 * @created 2016年5月12日 下午3:38:21
	 *
	 * @param fid
	 * @return
	 */
	public FinancePaymentVo getPaymentVoById(String fid){
		return mybatisDaoContext.findOne(SQLID+"queryPaymentVoByFid", FinancePaymentVo.class, fid);
	}

	/**
	 * 删除无效活动 收款单
	 * @author jixd
	 * @created 2017年06月07日 11:54:37
	 * @param
	 * @return
	 */
	public int deletePaymentByOrderSnAndPayType(String orderSn,int payType){
		Map<String,Object> paraMap = new HashMap<>();
		paraMap.put("orderSn",orderSn);
		paraMap.put("payType",payType);
		return mybatisDaoContext.update(SQLID + "deletePaymentByOrderSnAndPayType",paraMap);
	}
	/**
	 * 更新活动收款单 实际金额
	 * @author jixd
	 * @created 2017年06月07日 12:00:24
	 * @param
	 * @return
	 */
	public int updateActPaymentMoneyByOrderSn(String orderSn,int money){
		Map<String,Object> par = new HashMap<>();
		par.put("orderSn",orderSn);
		par.put("money",money);
		par.put("runTime", new Date());
		return mybatisDaoContext.update(SQLID + "updateActPaymentMoneyByOrderSn",par);
	}
}
