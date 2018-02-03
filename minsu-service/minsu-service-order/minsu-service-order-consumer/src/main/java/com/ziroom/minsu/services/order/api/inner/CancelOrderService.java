package com.ziroom.minsu.services.order.api.inner;

import java.util.Date;

import com.ziroom.minsu.entity.order.FinancePenaltyEntity;
import com.ziroom.minsu.entity.order.OrderConfigEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/1/4 11:00
 * @version 1.0
 * @since 1.0
 */
public interface CancelOrderService {

    /**
     * 协商取消订单
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/1/4 11:02
     */
    String cancelOrderNegotiate(String params);


    /**
     * 查看取消详情
     * @author lishaochuan
     * @create 2017/1/10 14:18
     * @param 
     * @return 
     */
    String showCancelOrderInfo(String orderSn, Integer orderStatus);


	/**
	 * 根据confCode，orderSn更改confValue
	 *
	 * @author loushuai
	 * @created 2017年5月12日 下午1:38:55
	 *
	 * @param confValue
	 */
	String updateOrderConfValue(String orderSn, String confCode, String confValue);


	/**
	 * 根据orderSn更新t_order_csr_cancle中的punish_statu
	 *
	 * @author loushuai
	 * @created 2017年5月12日 下午2:27:26
	 *
	 * @param orderSn
	 */
	String updateOrderCsrCancle(String orderSn, Integer punishStatu);


	/**
	 * 查询t_order_csr_cancle==》获取一段时间内，房东取消订单的次数
	 *
	 * @author loushuai
	 * @created 2017年5月12日 下午5:28:17
	 *
	 * @param landlordUid
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	String getCountInTimes(String landlordUid, Date beginTime,Date endTime);
	
	/**
	 * 
	 * 根据orderSn获取orderConfigList
	 *
	 * @author loushuai
	 * @created 2017年5月16日 下午9:02:40
	 *
	 * @param orderSn
	 * @return
	 */
	String getOrderConfigListByOrderSn(String orderSn);


	/**
	 * 根据orderSn查询t_finance_penalty    获取罚款单对象
	 *
	 * @author loushuai
	 * @created 2017年5月16日 下午9:16:44
	 *
	 * @param orderSn
	 * @return
	 */
	String getFinancePenaltyByOrderSn(String orderSn);


	/**
	 * loushuai
	 *
	 * @author loushuai
	 * @created 2017年5月17日 上午9:47:47
	 *
	 * @param orderSn
	 * @return
	 */
	String getIsDoneAllPunish(String orderSn);


	/**
	 * 获取到所有未处理完成的  OrderCsrCancleEntity对象
	 *
	 * @author loushuai
	 * @created 2017年5月17日 下午12:03:31
	 *
	 * @return
	 */
	String getDoFailLandQXOrderPunish();

	/**
	 * 更新订单系统锁定
	 * @author jixd
	 * @created 2017年05月25日 10:43:37
	 * @param
	 * @return
	 */
	String updateOrderSysLock(String orderParam);


}
