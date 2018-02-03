package com.ziroom.minsu.services.order.api.inner;



/**
 * 
 * <p>付款单、收入表，定时任务</p>
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
public interface OrderTaskFinanceService {
	
	
	/**
     * 查询已入住订单，生成付款单，收入记录
     * @author lishaochuan
     * @create 2016年4月19日
     */
	public void taskCreateFinance();

    /**
     * 执行收入表打款 和 扫描执行付款计划
     * @author lishaochuan
     * @create 2016年4月22日
     */
    public void taskRunFinance();
}
