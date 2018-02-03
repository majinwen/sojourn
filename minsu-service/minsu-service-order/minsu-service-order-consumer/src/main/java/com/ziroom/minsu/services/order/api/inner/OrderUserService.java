package com.ziroom.minsu.services.order.api.inner;


/**
 * <p>用户的订单操作入口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public interface OrderUserService {




    /**
     * 通过订单号获取快照信息
     * @author afi
     * @param orderSn
     * @return
     */
    String findHouseSnapshotByOrderSn(String orderSn);

    /**
     * 锁定房源
     * @author afi
     * @param locakHouseJson
     * @return
     */
    String lockHouse(String locakHouseJson);
    
    /**
     * 
     * 锁定房源 pc用
     *
     * @author jixd
     * @created 2016年8月4日 下午6:27:41
     *
     * @param locakHouseJson
     * @return
     */
    String lockHouseForPC(String locakHouseJson);

    /**
     * 获取区间内的房源的锁定信息
     * @author afi
     * @param houseLockJson
     * @return
     */
    String getHouseLockInfo(String houseLockJson);


    /**
     * 获取订单的价格列表
     * @author afi
     * @param orderDetailJson
     * @return
     */
    String getOrderPrices(String orderDetailJson);



    /**
     * 初始化取消订单
     * @author afi
     * @param orderCancleJson
     * @return
     */
    String initCancleOrder(String orderCancleJson);



    /**
     * 取消订单
     * @author afi
     * @param orderCancleJson
     * @return
     */
    String cancleOrder(String orderCancleJson);


    /**
     * 初始化退房
     * @author lishaochuan
     * @create 2016年5月3日
     * @param checkOutOrder
     * @return
     */
	public String initCheckOutOrder(String orderCheckOutJson);

    /**
     * 退房
     * @author afi
     * @param orderCancleJson
     * @return
     */
    String checkOutOrder(String orderCancleJson);

    /**
     * 查看订单详情
     * @author afi
     * @param orderDetailJson
     * @return
     */
   // String getOrderDetail(String orderDetailJson);


    /**
     * 创建订单
     * @author afi
     * @param orderJson
     * @return
     */
    String createOrder(String orderJson);
    
    
    /**
     * 获取订单需支付金额明细
     * @author lishaochuan
     * @param requestJson
     * @return
     */
	String getNeedPayFee(String requestJson);
	
	
	/**
	 * 获取订单需支付金额明细-新
	 * @author lishaochuan
	 * @create 2016年8月19日下午3:13:39
	 * @param requestJson
	 * @return
	 */
	String needPay(String requestJson);


    /**
     * 用户确认额外消费
     * @param ConfirmOtherMoneyJson
     * @return
     */
    String confirmOtherMoney(String ConfirmOtherMoneyJson);

    /**
     * 
     * 房东解锁房源
     *
     * @author liujun
     * @created 2016年5月13日
     *
     * @param unlocakHouseJson
     * @return
     */
	String unlockHouse(String unlocakHouseJson);
	/**
	 * 
	 * 房东解锁房源 pc使用
	 *
	 * @author jixd
	 * @created 2016年8月4日 下午6:32:17
	 *
	 * @param unlocakHouseJson
	 * @return
	 */
	String unlockHouseForPC(String unlocakHouseJson);
	
	/**
	 * 
	 * 获取房间或者房源30天出租天数
	 *
	 * @author jixd
	 * @created 2016年6月26日 下午2:46:47
	 *
	 * @param requestJson
	 * @return
	 */
	String getBookDaysByFid(String requestJson);
    
	/**
	 * 
	 * 获取房间或房源30天出租率
	 *
	 * @author jixd
	 * @created 2016年8月1日 下午6:47:02
	 *
	 * @param requestJson
	 * @return
	 */
	String getBookRateByFid(String requestJson);
	
	/**
	 * 
	 * 房东端申请预定页（IM聊天）
	 *
	 * @author yd
	 * @created 2017年4月5日 下午5:34:45
	 *
	 * @param requestJson
	 * @return
	 */
	String needPayForLan(String requestJson);
}
