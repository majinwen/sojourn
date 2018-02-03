package com.ziroom.minsu.services.order.api.inner;

public interface ProfitService {
	/**
     * 房源月收益
     * @author liyingjie
     * @param param
     * @return
     */
	String getHouseMonthProfit(String param);
	/**
     * 获取一个月所有房源收益
     * @author liyingjie
     * @param param
     * @return
     */
	String getUserAllHouseMonthProfit(String param);
	/**
     * 获取房源收益列表
     * @author liyingjie
     * @param param
     * @return
     */
	String getUserHouseList(String param);

}
