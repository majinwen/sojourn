/**
 * @FileName: OrderCommonService.java
 * @Package com.ziroom.minsu.services.order.api.inner
 * 
 * @author yd
 * @created 2016年4月5日 下午5:03:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.api.inner;



/**
 * <p>订单公共接口</p>
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
public interface OrderCommonService {


	/**
	 * 获取评级列表
	 * @author  afi
	 * @param orderEvalRequestStr
	 * @return
	 */
	String getOrderEavlList(String orderEvalRequestStr);

	/**
	 * 获取当前用户待评价的订单数量
	 * @author afi
	 * @param userUid
	 * @return
	 */
	public String countWaitEvaNumAll(String userUid,int userType);





	/**
	 * 获取最近的一个待入住的订单@
	 * @author afi
	 * @param uid
	 * @return
	 */
	public String getOrderLast(String uid);


	/**
	 * 获取当前的订单的数量
	 * @author afi
	 * @param uid
	 * @return
	 */
	public String getOrderCount4User(String uid);

	/**
	 * 
	 * 按订单类型 (1=用户订单  2=房东订单 3=后台订单)条件查询 分页 获取用户订单列表
	 *
	 * @author yd
	 * @created 2016年4月5日 下午5:10:25
	 *
	 * @param orderRequest
	 * @return
	 */
	public String getOrderListByCondiction(String orderRequest);
	
	/**
	 * 
	 * 按订单类型 (1=用户订单  2=房东订单 3=后台订单)
	 * 在根据用户订单 请求类型判断 请求类型
	 *
	 * @author jixd
	 * @created 2016年5月4日 下午9:20:49
	 *
	 * @param orderRequest
	 * @return
	 */
	public String getOrderList(String orderRequest);


    /**
     * 获取当前用户下的智能锁的数量
     * @author afi
     * @param uid
     * @return
     */
    String countLock(String uid);

	/**
	 * 
	 * 根据订单Sn查询订单详情信息
	 *
	 * @author yd
	 * @created 2016年4月3日 下午12:59:27
	 * @param request 请求参数
	 * @return
	 */
	public String queryOrderInfoBySn(String request);


	/**
	 * 获取当前的订单的全部信息
	 * @param request
	 * @return
     */
	public String getOrderAllBySn(String request);
	
	/**
     * 
     * 条件查询订单
     *
     * @author yd
     * @created 2016年4月12日 下午3:07:26
     *
     * @param orderRequest
     * @return
     */
    public String queryOrderByCondition(String  listOrderSn);
    /**
     * 
     * 评价订单时候,根据订单编号修改订单的评价状态
     *
     * @author yd
     * @created 2016年4月12日 下午5:57:02
     *
     * @param orderRequest
     * @return
     */
    public String  updateEvaStatuByOrderSn(String  orderRequest);

	/**
	 * 更新订单的初见或者评价状态
	 * @author afi
	 * @param orderRequest
	 * @return
	 */
	public String  updateStatuByOrderSn(String  orderRequest);
    
    /**
     * 
     * 保存常用联系人
     *
     * @author yd
     * @created 2016年4月30日 下午5:33:32
     *
     * @param usualContact
     * @return
     */
    public String saveUsualContact(String usualContact);
    /**
     * 
     * 根据用户orderSn查询当前用户的当前订单的常用联系人
     *
     * @author yd
     * @created 2016年4月30日 下午5:50:15
     *
     * @param userUid
     * @return
     */
    public String findOrderContactsByOrderSn(String orderSn);
    /**
	 * 获取用户常用联系人列表
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 上午11:20:09
	 *
	 * @param userUid
	 * @return
	 */
	public String findUsualContactsByUid(String userUid);
	/**
	 * 按照fid集合查询 常用联系人列表
	 *
	 * @author yd
	 * @created 2016年4月1日 上午11:20:09
	 *
	 * @param usualConRe
	 * @return
	 */
	public String findUsualContactsByContion(String usualConRe);


	/**
	 * 获取预订人信息
	 * @author lishaochuan
	 * @create 2016/12/2 11:29
	 * @param
	 * @return
	 */
	public String getBookerContact(String userUid);


	/**
	 * 更新预订人信息
	 * @author lishaochuan
	 * @create 2016/12/2 16:13
	 * @param 
	 * @return 
	 */
	public String updateBookerContact(String request);

	 /**
   	 * 获取订单基本信息
   	 *
   	 *
   	 * @author yd
   	 * @created 2016年4月3日 
   	 *
   	 * @param orderSn
   	 * @return
   	 */
    public String getOrderByOrderSn(String orderSn);
    /**
	 * 获取当前订单信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public String getOrderInfoByOrderSn(String orderSn);
	 /**
     * 获取订单的房源信息
     * @author afi
     * @param orderSn
     * @return
     */
     public String findHouseSnapshotByOrderSn(String orderSn);
     /**
 	 * 更新订单的信息
 	 * @author afi
 	 * @param orderEntity
 	 * @return
 	 */
 	public String updateOrderBaseByOrderSn(String orderEntity);
 	 /**
     * 
     * update by fid
     *
     * @author yd
     * @created 2016年5月2日 上午11:54:10
     *
     * @param usualContactEntity
     * @return
     */
    public String updateByFid(String usualContactEntity);


	/**
	 * 修改联系人
	 * 1、逻辑删除
	 * 2、新增
	 *
	 * @author lishaochuan
	 * @create 2016/12/21 16:45
	 * @param
	 * @return
	 */
    public String updateLogicDelete(String usualContactEntity);



	/**
	 * 逻辑删除联系人
	 * @author lishaochuan
	 * @create 2016/12/2 9:36
	 * @param 
	 * @return 
	 */
	public String deleteContact(String fid, String userUid);

	/**
	 * 
	 * 查询订单快照(用户uid必须给定)
	 *
	 * @author yd
	 * @created 2016年5月2日 下午6:32:38
	 *
	 * @param orderRequest
	 * @return
	 */
	public String findHouseSnapshotByOrder(String orderRequest);
	
	/**
	 * 
	 * 获取订单退订政策
	 *
	 * @author jixd
	 * @created 2016年5月8日 下午1:32:59
	 *
	 * @param orderSn
	 * @return
	 */
	public String getCheckOutStrategyByOrderSn(String orderSn);
	
	
	/**
	 * 
	 * 根据订单号获取该订单所有的活动
	 *
	 * @author loushuai
	 * @created 2017年7月14日 下午3:04:26
	 *
	 * @param orderSn
	 * @return
	 */
	public String findOrderAcByOrderSn(String orderSn);

	/**
	 * 未支付订单取消
	 *
	 * @author loushuai
	 * @created 2017年10月10日 下午6:37:28
	 *
	 * @param object2Json
	 * @return
	 */
	String cancelUnPayOrder(String object2Json);

	/**
	 *
	 * 查询昨天遗漏的订单行为(房东行为成长体系定时任务)
	 *
	 * @author zhangyl2
	 * @created 2017年10月13日 13:02
	 * @param
	 * @return
	 */
	String queryOrderForCustomerBehaviorJob(String paramJson);

	/**
	 * 获取房东（集合）60天内的接单率（troy地图找房功能：60天内创建的订单中，该房东所有申请预定的订单通过数/该房东所有申请预定的订单数*100%）
	 *
	 * @author loushuai
	 * @created 2017年10月25日 下午8:04:22
	 *
	 * @param object2Json
	 * @return
	 */
	String getLandAcceptOrderRateIn60Days(String object2Json);

	/**
	 * 批量获取被邀请用户，订单及状态，填充其被邀请状态
	 *
	 * @author loushuai
	 * @created 2017年12月4日 下午1:07:21
	 *
	 * @param object2Json
	 * @return
	 */
	String getBeInviterStatusInfo(String object2Json);

	/**
	 * 查询当前时间4个小时内的已结算的订单的uid和orderSn
	 * @author yanb
	 * @created 2017年12月13日 15:02:02
	 * @param  * @param null
	 * @return
	 */
	public String queryOrder4Hour();

}
