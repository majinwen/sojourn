package com.ziroom.minsu.services.order.api.inner;

public interface StaticsService {
	/**
     * 计算房东订单总数量
     * @author liyingjie
     * @param param
     * @return
     */
	public String getStaticsCountLanOrderNum(String param);
	/**
     * 房东X分钟内响应的订单数量
     * @author liyingjie
     * @param param
     * @return
     */
	public String getStaticsCountLanReplyOrderNum(String param);
	/**
     * 房东拒绝的订单数量
     * @author liyingjie
     * @param param
     * @return
     */
	public String getStaticsCountLanRefuseOrderNum(String param);
	/**
     * 超时系统拒绝的订单数量
     * @author liyingjie
     * @param param
     * @return
     */
	public String getStaticsCountSysRefuseOrderNum(String param);
	/**
     * 房东响应时间总和
     * @author liyingjie
     * @param param
     * @return
     */
	public String getStaticsCountLanReplyOrderTime(String param);
	
	/**
     * 定时任务统计调用接口
     * @author liyingjie
     * @param param
     * @return
     */
	public String taskStaticsCountInfo(String param);
	
	/**
     * 统计房东的订单数据
     * @author zl
     * @param param
     * @return
     */
	public String staticsLandOrderCountInfo(String param);

}
