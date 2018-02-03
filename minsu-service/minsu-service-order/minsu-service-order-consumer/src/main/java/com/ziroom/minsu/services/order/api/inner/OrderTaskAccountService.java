package com.ziroom.minsu.services.order.api.inner;

/**
 * 
 * <p>账户相关定时任务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月4日
 * @since 1.0
 * @version 1.0
 */
public interface OrderTaskAccountService {

	
	/**
	 * 对账户充值失败的单子进行重新充值
	 * @author lishaochuan
	 * @create 2016年5月4日
	 */
	public void repeatAccountFillFailed();
}
