package com.ziroom.minsu.services.order.api.inner;


/**
 * 
 * <p>订单智能锁操作入口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public interface OrderSmartLockService {
	
	/**
	 * 
	 * 查询订单智能锁信息
	 *
	 * @author liujun
	 * @created 2016年6月23日
	 *
	 * @param fid
	 * @return
	 */
	public String findOrderSmartLockByFid(String fid);
	
	/**
	 * 
	 * 查询订单智能锁信息
	 *
	 * @author liujun
	 * @created 2016年6月23日
	 *
	 * @param orderSn
	 * @return
	 */
	public String findOrderSmartLockByOrderSn(String orderSn);
	
	/**
	 * 
	 * 更新订单智能锁信息
	 *
	 * @author liujun
	 * @created 2016年6月23日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateOrderSmartLockByFid(String paramJson);
	
	/**
	 * 
	 * 更新订单智能锁信息
	 *
	 * @author liujun
	 * @created 2016年6月23日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateOrderSmartLockByOrderSn(String paramJson);
	
	/**
	 * 
	 * 更新订单智能锁信息
	 *
	 * @author liujun
	 * @created 2016年6月23日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateOrderSmartLockByServiceId(String paramJson);
	
	/**
	 * 
	 * 关闭智能锁密码
	 *
	 * @author liujun
	 * @created 2016年6月24日
	 *
	 * @param orderSn
	 * @return
	 */
	public String closeSmartlockPswd(String orderSn);
    
}
