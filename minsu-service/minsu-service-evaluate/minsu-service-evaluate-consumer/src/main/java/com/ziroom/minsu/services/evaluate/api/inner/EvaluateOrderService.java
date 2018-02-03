/**
 * @FileName: EvaluateOrderService.java
 * @Package com.ziroom.minsu.services.evaluate.api.inner
 * 
 * @author yd
 * @created 2016年4月7日 下午8:34:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.api.inner;


import java.util.List;

/**
 * <p>评价服务接口</p>
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
public interface EvaluateOrderService {


    /**
     *
     * 根据订单号取修改OrderEvaflag 为1
     *
     * @author yd
     * @created 2016年4月12日 下午6:46:12
     *
     * @param listOrderSn
     * @param orderEvaflag
     */
    public void updateOrderEvaFlag(List<String> listOrderSn ,int orderEvaflag,Integer evaUserType);



	/**
	 * 更新已经同步的订单状态
	 * @author afi
	 * @created 2016年11月12日 下午6:46:12
	 *
	 * @param listFid
	 */
	public void updateSynOrderEvaFlagByFid(List<String> listFid);


    /**
     * 获取评价未同步的列表
     * @author afi
     * @return
     */
    String getEvaluteList();



	/**
	 * 
	 * 用户uid给定，分页查出其他房东给该用户的评价内容
	 * 说明：被评人 rated_user_uid 必须给定； 状态必须审核成功  eva_statu = 3 ； 必须为房东的评价 eva_user_type = 1;
	 *  is_del  未被删除
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:40:38个
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public String  queryLandlordEvaluateByPage(String evaluateRequest);
	/**
	 * 
	 * 查看其他房东 对 特定用户评价   互评记录才能展示sql - 单独给房东端 M站使用 
	 *
	 * @author yd
	 * @created 2016年4月7日 下午2:41:37
	 *
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public String queryOtherLanEvaByPage(String evaluateRequest);
	
	/**
	 * 
	 * 按类型eva_user_type 分页查询该类型下所有的评价信息 此接口给后台评价管理列表使用
	 * 
	 * @author yd
	 * @created 2016年4月7日 下午8:44:50
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public String queryEvaluateByPage(String evaluateRequest);
	
	/**
	 * 
	 * 条件分页查询所有房客的审核通过的评价 
	 *
	 * 说明：必须为房客的评价 eva_user_type = 2;  is_del  未被删除； 状态必须审核成功  eva_statu = 3 ；
	 * @author yd
	 * @created 2016年4月7日 下午8:46:23
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public String queryTenantEvaluateByPage(String evaluateRequest);
	
	/**
	 * 
	 * 添加房客评论 
	 * 说明：先添加EvaluateOrder  在添加TenantEvaluate 在一个事物中
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:49:38
	 *
	 * @param tenantEvaluate
	 * @return
	 */
	public String saveTenantEvaluate(String tenantEvaluate,String evaluateOrder,String orderEntity);
	
	/**
	 * 
	 * 添加房东评论 
	 * 说明：先添加EvaluateOrder  在添加LandlordEvaluate 在一个事物中
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:54:09
	 *
	 * @param landlordEvaluate
	 * @return
	 */
	public String saveLandlordEvaluate(String landlordEvaluate,String evaluateOrder,String orderEntity);

	/**
	 * 添加房东回复
	 * @author jixd
	 * @created 2017年02月09日 14:27:28
	 * @param
	 * @return
	 */
	public String saveLandlordReply(String landlordReply,String evaluateOrder);
	/**
	 * 
	 * 根据fid修改EvaluateOrder 这里只能修改状态和最后修改时间
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:55:56
	 *
	 * @param evaluateOrder
	 * @return
	 */
	public String updateEvaluateOrderByFid(String evaluateOrder,String orderEntity);
	
	/**
	 * 
	 * 根据eva_order_fid修改：LandlordEvaluate  只能修改部分信息 ，包括内容  最后修改时间  满意度 是否删除
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:57:53
	 *
	 * @param landlordEvaluate
	 * @return
	 */
	public String updateLandlordEvaluate(String landlordEvaluate);
	
	/**
	 * 
	 * 根据eva_order_fid修改：TenantEvaluate 只能修改部分信息 包括 内容  星级的相关项  最后修改时间 是否删除
	 *
	 * @author yd
	 * @created 2016年4月7日 下午9:00:24
	 *
	 * @param tenantEvaluate
	 * @return
	 */
	public String updateTenantEvaluate(String tenantEvaluate);
	
	/**
	 * 
	 * 条件查询统计房源信息
	 *
	 * @author yd
	 * @created 2016年4月9日 下午10:58:17
	 *
	 * @param statsHouseEva
	 */
	public String queryStatsHouseEvaByCondition(String statsHouseEva);
	
	/**
	 * 
	 * 条件查询统计房客信息
	 *
	 * @author yd
	 * @created 2016年4月9日 下午11:22:07
	 *
	 * @param statsTenantEvaRequest
	 * @return
	 */
	public String queryStatsTenantEvaByCondion(String statsTenantEvaRequest);
	
	/**
	 * 
	 * 通过订单sn查询 用户对房源的评价以及房东对房客的评价
	 * 前端可以增加评价状态evaStatu(1=待审核 2=系统下线 3=人工下线 4=已发布 5=已举报)字段过滤
	 *
	 * @author yd
	 * @created 2016年4月9日 下午11:45:51
	 *
	 * @param paramJson
	 * @return
	 */
	public String queryEvaluateByOrderSn(String paramJson);
	/**
	 * 
	 * 查询评价公共信息(后台使用)
	 *
	 * @author yd
	 * @created 2016年4月11日 下午2:35:48
	 *
	 * @param evaluateRequest
	 * @return
	 */
	public String queryAllEvaluateByPage(String evaluateRequest);
	
	/**
	 * 
	 * 按条件修改评价订单关系实体
	 *
	 * @author yd
	 * @created 2016年4月12日 上午11:54:34
	 *
	 * @param evaluateOrder
	 * @return
	 */
	public String updateEvaluateOrderByCondition(String evaluateOrder);
	

	
	/**
	 * 
	 * 评价上线的定时任务(半小时 扫秒一次  每次扫描  往前推一个小时状态为待审核的数据 )
	 * 
	 * 说明：扫描t_evaluate_order表， 创建时间 往前推hours小时，修改状态为 已发布
	 *
	 * @author yd
	 * @created 2016年5月3日 下午8:51:05
	 *
	 * @param hours 超时时间(即评价创建时间 超过这个时间  就上线) 单位 ：小时
	 * @return
	 */
	public String evaluateOnline(String hours);
	/**
	 * 
	 * 1.统计信息 
	 * 2.极光推送
	 *
	 * @author yd
	 * @created 2016年5月20日 上午7:11:59
	 *
	 * @param evaluateOrder
	 * @param orderEntity
	 * @return
	 */
	public String statsEvaluateMessage(String evaluateOrder,String orderEntity);
	
	/**
	 * 
	 * PC端房源详情显示评价相关信息
	 *
	 * @author jixd
	 * @created 2016年8月6日 下午2:32:51
	 *
	 * @param param
	 * @return
	 */
	String houseDetailEvaInfo(String param);
	
	
	/**
	 * 
	 * 查询房源详情评价列表
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午10:27:59
	 *
	 * @param param
	 * @return
	 */
	String queryHouseDetailEvaPage(String param);
	
	
	/**
	 * 
	 * 查询房客对房东评价列表
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午10:28:36
	 *
	 * @param param
	 * @return
	 */
	String queryLanEvaPage(String param);
	
	

	/**
	 * 根据房东uid查询房东的所有评分的平均值
	 *
	 * @author zl
	 * @created 2016年9月18日 
	 * 
	 * @param landlordUid
	 * @return
	 */
	String selectByAVEScoreByUid(String landlordUid);




	/**
	 * 查询 房东对房客 或者房客对房东评价数量
	 * @author jixd
	 * @created 2016年11月15日 11:31:06
	 * @param
	 * @return
	 */
	String countEvaNum(String param);
	/**
	 * 查询房客对房源评价数量
	 * @author jixd
	 * @created 2016年11月15日 11:31:08
	 * @param
	 * @return
	 */
	String countTenToHouseEvaNum(String param);

	/**
	 *
	 * @author jixd
	 * @created 2017年02月10日 18:52:27
	 * @param
	 * @return
	 */
	void checkInNoticeTenantEva(String param);

	/**
	 * 订单结束两天对房东或者房客发送通知
	 * @author jixd
	 * @created 2017年02月13日 11:51:50
	 * @param
	 * @return
	 */
	void orderFinish2DayNoticeEva(String param);

	/**
	 * 查询未显示的评价列表
	 * @author jixd
	 * @created 2017年02月14日 14:48:27
	 * @param
	 * @return
	 */
	String listEvaluateUncheckShow(String param);

	/**
	 * 保存评价显示结果
	 * @author jixd
	 * @created 2017年02月14日 15:56:00
	 * @param
	 * @return
	 */
	String saveEvaluateShow(String param);
	/**
	 * 查询符合条件的评价订单列表
	 * @author jixd
	 * @created 2017年02月23日 16:25:48
	 * @param
	 * @return
	 */
	String listAllEvaOrderByCondition(String param);


	String updateShowAndStatEva(String list,String entity,String evaType);
	/**
	 * 发送上线后推送评价提醒
	 * @author jixd
	 * @created 2017年02月27日 16:24:58
	 * @param
	 * @return
	 */
	void sendOnlineEvaMsg(String order,String evaType);

	/**
	 * 根据订单号查询评价的回复
	 * @author jixd
	 * @created 2017年03月14日 14:29:19
	 * @param
	 * @return
	 */
	String findEvaReplyByOrderSn(String orderSn);

	/**
	 * 清除房源或者房客评价统计
	 * @author jixd
	 * @created 2017年03月15日 13:56:58
	 * @param
	 * @return
	 */
	String delStatsData(String param);




	/**
	 * 房东取消订单——增加系统评价
	 *
	 * @author loushuai
	 * @created 2017年5月11日 下午6:41:13
	 *
	 * @param param
	 */
	 String saveSystemEval(String param);

	/**
	 * 重新计算房源的评分
	 * @param param
	 * @return
	 */
	 String updateEvaluateAndStatsHouseEva(String statList, String showEntity, int count);

	 /**
	 * 重新计算房客的评分
	 * @param param
	 * @return
	 */
	String updateLandAndStatsTenantEva(String statList, String showEntity, int count);

	/**
	 * 
	 * 查询昨天遗漏的评价行为(房东行为成长体系定时任务)
	 * 
	 * @author zhangyl2
	 * @created 2017年10月12日 11:46
	 * @param 
	 * @return 
	 */
    String queryTenantEvaluateForCustomerBehaviorJob(String param);
}
