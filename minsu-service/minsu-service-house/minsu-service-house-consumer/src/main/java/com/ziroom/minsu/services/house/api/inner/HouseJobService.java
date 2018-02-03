/**
 * @FileName: HouseJobService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author bushujie
 * @created 2016年4月9日 下午8:55:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>房源定时任务相关接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public interface HouseJobService {


    /**
     * 房源收益日统计
     * @author afi
     * @param orderJson
     * @return
     */
    String houseDayRevenueStatisticsByInfo(String orderJson);
    
    /**
     * 
     * 查询超过审核时限房源列表
     *
     * @author liujun
     * @created 2016年5月30日
     *
     * @return
     */
    public String findOverAuditLimitHouseList();
    
	/**
	 * 
	 * 超过审核时限房源自动上架（整租）
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午9:03:54
	 *
	 * @return
	 */
	public String houseAuditLimit(String paramJson);
	
	/**
	 * 查询超过审核时限房间列表
	 *
	 * @author liujun
	 * @created 2016年5月30日
	 *
	 * @return
	 */
	public String findOverAuditLimitRoomList();
	
	/**
	 * 
	 * 超过审核时限房源自动上架（合租）
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午10:42:08
	 *
	 * @return
	 */
	public String roomAuditLimit(String paramJson);
	
	/**
	 * 
	 * 房源收益日统计
	 *
	 * @author bushujie
	 * @created 2016年4月26日 下午6:49:26
	 *
	 * @param param
	 * @return
	 */
	public String houseDayRevenueStatistics(String param);
	
	/**
	 * 
	 * 房源月收益统计
	 *
	 * @author liujun
	 * @created 2016年4月28日 下午8:12:14
	 *
	 * @param param 时间字符串
	 * @return
	 */
	public String houseMonthRevenueStatistics(String param);
	
	/**
	 * 
	 * 待发布 房源 发送提醒短信
	 * 
	 * 频次：未发布3天、未发布7天、未发布14天。
	 *
	 * @author yd
	 * @created 2016年11月22日 上午11:50:11
	 *
	 * @param param
	 * @return
	 */
	public String noticeLanDFBHouseMsg(String time,String day);

	/**
	 * 持久化房源统计信息
	 *
	 * @author liujun
	 * @created 2016年12月2日
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseStats(String paramJson);



	/**
	 * 统计房东的房源数量
	 * @author afi
	 * @param param
	 * @return
	 */
	String countLandHouseInfo(String param);
	
	/**
	 * 
	 * 房源审核未通过跟进统计
	 *
	 * @author bushujie
	 * @created 2017年2月22日 下午4:15:53
	 *
	 * @param param
	 * @return
	 */
	public String houseNotAuditFollowStats(String param);
}
