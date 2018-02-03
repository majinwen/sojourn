package com.ziroom.minsu.services.order.api.inner;

/**
 * <p>troy客户服务平台</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月1日
 * @since 1.0
 * @version 1.0
 */
public interface CustomerServeService {

	/**
	 * 申请预订且房东未回复的订单
	 * @author lishaochuan
	 * @create 2016年8月1日下午6:45:04
	 * @param param
	 * @return
	 */
	public String getRemindOrderList(String param);
	
	
	/**
	 * 查询房东拒绝的申请预定（12小时以内）
	 * @author lishaochuan
	 * @create 2016年8月3日下午2:31:05
	 * @param param
	 * @return
	 */
	public String getRefuseOrderList(String param);

	/**
	 * @description: 获取用户咨询时有没有订单信息
	 * @author: lusp
	 * @date: 2017/8/11 21:37
	 * @params: param
	 * @return:
	 */
	public String getAdvisoryOrderInfo(String param);
}
