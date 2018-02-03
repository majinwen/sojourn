package com.ziroom.minsu.services.order.api.inner;

/**
 * 
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
public interface OrderTaskSyncService {

	/**
	 * 同步收入（每个月2号执行）
	 * @author lishaochuan
	 * @create 2016年4月30日
	 */
	public void syncIncomeData();
	
	
	/**
	 * 同步业务帐（每1个小时执行一次）
	 * @author lishaochuan
	 * @create 2016年4月30日
	 */
	public void syncPaymentData();
	
	
	/**
	 * 获取未同步优惠券状态的活动
	 * @author lishaochuan
	 * @create 2016年6月18日上午11:00:59
	 * @return
	 */
	public String getNotSyncActivityCount();
	
	/**
	 * 获取未同步优惠券状态的活动list
	 * @author lishaochuan
	 * @create 2016年6月18日上午11:28:55
	 * @param limit
	 * @return
	 */
	public String getNotSyncActivityList(Integer limit);
	
	
	/**
	 * 更新未同步的优惠券活动为已同步
	 * @author lishaochuan
	 * @create 2016年6月18日上午11:45:31
	 * @param paramJson
	 * @return
	 */
	public String updateActivityHasSync(String paramJson);
	
	
	/**
	 * 
	 * 房东进击活动填充 返现单
	 *
	 * @author yd
	 * @created 2017年8月31日 下午1:40:02
	 *
	 * @return
	 */
	public String fillLanlordCashOrder(String actSn);
}
