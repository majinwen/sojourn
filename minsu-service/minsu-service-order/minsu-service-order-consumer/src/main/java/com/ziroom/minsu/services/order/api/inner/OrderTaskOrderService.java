/**
 * @FileName: OrderTaskOrderService.java
 * @Package com.ziroom.minsu.services.order.api.inner
 * 
 * @author yd
 * @created 2016年4月5日 下午5:03:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.api.inner;



/**
 * <p>订单定时任务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public interface OrderTaskOrderService {


	/**
	 * 统计三小时内新增的当前的恶意下单数量
	 * @author afi
	 */
	public void taskMaliceOrder(Integer num);

	/**
   	 *  查询超过30min未支付的订单,释放房源,取消订单
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月5日 
   	 *
   	 * @param  
     * @return
   	 */
    public void cancelOrderAndUnlockHouse();
    
	/**
	 * 下单后订单，房东一段时间内未确认，发短信提醒
	 * @author lishaochuan
	 * @create 2016年5月14日上午10:58:54
	 */
	public String taskWatiConfimOrder();
	
	/**
	 * 下单后订单，房东超时未确认，自动拒绝订单
	 * @author lishaochuan
	 * @create 2016年5月12日下午1:40:31
	 */
	public void taskRefusedOrder();
	
    /**
   	 *  到入住时间，更新订单状态为已入住
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月5日 
   	 *
   	 * @param  
     * @return
   	 */
    public void updateOrderStatus();
    
    /**
     * 
     * 发送当地天气信息对于已支付待入住订单
     * @author jixd
     * @created 2016年4月13日 
     */
    /*public void sendWeatherMsgForTomorrowOrder();*/


    /**
     * 自动退房的逻辑
     * @author afi
     * @created 2016年4月5日
     * @param
     * @return
     */
    public void taskCheckOut();
    
    
    /**
     * 房东自动确认额外消费
     * @author lishaochuan
     * @create 2016年5月12日上午12:32:20
     */
    public void taskConfirmOtherFeeLandlord();
    
    
    /**
     * 房客自动同意额外消费
     * @author lishaochuan
     * @create 2016年5月12日上午12:33:13
     */
    public void taskConfirmOtherUser();


	/**
	 * 房客退房日当天给房客发短信
	 * @author lisc
	 */
	public void taskCheckOutRemind();


	/**
	 * 检查被邀请人是否有已经入住订单
	 * @param json
	 * @return
	 */
	public String checkIfInviteCheckInOrder(String json);

    /**
     * 查询 当天入住并且使用优惠券的订单
     *
     * @param paramJson
     * @return
     */
    String listTodayCheckInOrderAndUseCouponPage(String paramJson);

	/**
	 * 获取每天将要入住的订单，给房东发短信
	 *
	 * @author loushuai
	 * @created 2017年7月28日 下午5:48:23
	 *
	 * @param paramJson
	 */
	String getWaitCheckinList(String paramJson);
	
	/**
	 * 
	 * 提前一天 通知房客入住
	 *
	 * @author yd
	 * @created 2017年9月8日 下午2:13:41
	 *
	 * @param paramJson
	 * @return
	 */
	String checkInPreNoticeTenant(String paramJson);
}
