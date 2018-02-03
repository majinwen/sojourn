package com.ziroom.minsu.services.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.dao.FinanceIncomeDao;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDao;
import com.ziroom.minsu.services.order.dao.OrderBaseDao;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.AuditStatusEnum;
import com.ziroom.minsu.valenum.order.IncomeStatusEnum;
import com.ziroom.minsu.valenum.order.IncomeTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;

/**
 * 
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月23日
 * @since 1.0
 * @version 1.0
 */
@Service("order.orderTaskFinanceServiceImpl")
public class OrderTaskFinanceServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskFinanceServiceImpl.class);

    @Resource(name = "order.orderBaseDao")
    private OrderBaseDao orderBaseDao;
    
    @Resource(name = "order.financePayVouchersDao")
    private FinancePayVouchersDao financePayVouchersDao;
    
    @Resource(name = "order.financeIncomeDao")
    private FinanceIncomeDao financeIncomeDao;
	
	
	/**
     * 根据订单状态获取订单数量
     * @author lishaochuan
     * @create 2016年4月19日
     * @param orderStatus
     * @return
     */
    public Long getOrderCountByStatus(int orderStatus){
        return orderBaseDao.getOrderCountByStatus(orderStatus);
    }
    
    /**
     * 根据订单状态获取订单list
     * @author lishaochuan
     * @create 2016年4月20日
     * @param orderStatus
     * @param limit
     * @return
     */
    public List<OrderEntity> getOrderListByStatus(int orderStatus, int limit){
        return orderBaseDao.getOrderListByStatus(orderStatus, limit);
    }
    
    

    
    /**
     * 查询付款单中审核通过未付款数量
     * @author lishaochuan
     * @create 2016年4月20日
     * @return
     */
	public Long getNotPayVouchersCount() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditStatus", AuditStatusEnum.COMPLETE.getCode());
		map.put("paymentStatus", PaymentStatusEnum.UN_PAY.getCode());
		map.put("isSend", YesOrNoEnum.NO.getCode());
		return financePayVouchersDao.getNotPayVouchersCount(map);
	}
    
    /**
     * 查询付款单中审核通过未付款记录
     * @author lishaochuan
     * @create 2016年4月20日
     * @param limit
     * @return
     */
	public List<FinancePayVouchersEntity> getNotPayVouchersList(int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditStatus", AuditStatusEnum.COMPLETE.getCode());
		map.put("paymentStatus", PaymentStatusEnum.UN_PAY.getCode());
		map.put("isSend", YesOrNoEnum.NO.getCode());
		map.put("limit", limit);
		return financePayVouchersDao.getNotPayVouchersList(map);
	}
	
	
	/**
	 * 查询失败的付款单数量
	 * @author lishaochuan
	 * @create 2016年9月19日下午9:26:34
	 * @param retryTimes
	 * @return
	 */
	public Long getFailedPayVouchersCount(int retryTimes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditStatus", AuditStatusEnum.COMPLETE.getCode());
		map.put("paymentStatus", PaymentStatusEnum.FAILED.getCode());
		map.put("retryTimes", retryTimes);
		return financePayVouchersDao.getFailedPayVouchersCount(map);
	}
    
	/**
	 * 查询失败的付款单数量
	 * @author lishaochuan
	 * @create 2016年9月19日下午9:26:38
	 * @param retryTimes
	 * @param limit
	 * @return
	 */
	public List<FinancePayVouchersEntity> getFailedPayVouchersList(int retryTimes, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditStatus", AuditStatusEnum.COMPLETE.getCode());
		map.put("paymentStatus", PaymentStatusEnum.FAILED.getCode());
		map.put("retryTimes", retryTimes);
		map.put("limit", limit);
		return financePayVouchersDao.getFailedPayVouchersList(map);
	}
	
	
	/**
	 * 查询收入表中未收款记录数量
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @return
	 */
	public Long getNotIncomeCount(int retryTimes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("incomeStatus", IncomeStatusEnum.NO.getCode());
		List<Integer> incomeTypeList = new ArrayList<Integer>();
		incomeTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH_COMMISSION.getCode());
		incomeTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH.getCode());
		map.put("incomeTypeList", incomeTypeList);
		map.put("retryTimes", retryTimes);
		return financeIncomeDao.getNotIncomeCount(map);
	}
	
	/**
	 * 查询收入表中未收款记录list
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @param limit
	 * @return
	 */
	public List<FinanceIncomeEntity> getNotIncomeList(int retryTimes, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("incomeStatus", IncomeStatusEnum.NO.getCode());
		List<Integer> incomeTypeList = new ArrayList<Integer>();
		incomeTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH_COMMISSION.getCode());
		incomeTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH.getCode());
		map.put("incomeTypeList", incomeTypeList);
		map.put("retryTimes", retryTimes);
		map.put("limit", limit);
		return financeIncomeDao.getNotIncomeList(map);
	}
	
	
}
