package com.ziroom.minsu.services.order.api.inner;

/**
 * <p>付款单收入，相关接口</p>
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
public interface OrderFinanceService {
	
	
	/**
	 * 我的扣款单列表
	 * @author lishaochuan
	 * @create 2016年4月25日
	 * @param request
	 * @return
	 */
	public String getPunishList(String request);
	
	/**
	 * 
	 * 房东日收益列表
	 *
	 * @author bushujie
	 * @created 2016年4月26日 下午4:52:08
	 *
	 * @param request
	 * @return
	 */
	public String landlordDayRevenueList(String request);

}
