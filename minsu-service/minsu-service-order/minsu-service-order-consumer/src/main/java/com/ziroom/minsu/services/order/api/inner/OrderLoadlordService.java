package com.ziroom.minsu.services.order.api.inner;


/**
 * 
 * <p>房东订单管理</p>
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
public interface OrderLoadlordService {


    /**
     * 安装智能锁
     * @author afi
     * @param parJson
     * @return
     */
    String installHouseLock(String parJson);

	
	/**
	 * 
	 * 1.接受订单   更新到 20：待入住      —— 更新状态  更新时间
	 *
	 * @author yd
	 * @created 2016年4月3日 下午8:55:44
	 *
	 * @param orderSn
	 * @param orderStatus
	 * @param createdFid
	 * @return
	 */
	public String acceptOrder(String loadlordRequestStr);
	
	/**
	 * 
	 * 2.拒绝订单 更新到 31：房东已拒绝     —— 更新状态  更新时间  更新参数表 记录拒绝原因
	 * 3.房东输入额外消费金额  订单状态更新到 60：待用户确认额外消费         —— 更新状态  更新时间  更新参数表记录额外消费明细
	 *
	 * @author yd
	 * @created 2016年4月3日 下午8:58:22
	 *
	 * @param orderSn
	 * @param orderStatus
	 * @param paramValue  拒绝原因或额外消费明细
	 * @param otherMoney
	 * @param createdFid
	 * @return
	 */
	public String refusedOrder(String loadlordRequestStr);
	
	/**
	 * 
	 * 3.房东输入额外消费金额  订单状态更新到 60：待用户确认额外消费         —— 更新状态  更新时间  更新参数表记录额外消费明细
	 *
	 * @author yd
	 * @created 2016年4月3日 下午8:58:22
	 *
	 * @param orderSn
	 * @param orderStatus
	 * @param paramValue  拒绝原因或额外消费明细
	 * @param otherMoney
	 * @param createdFid
	 * @returnsaveOtherMoney
	 */
	public String saveOtherMoney(String loadlordRequestStr);


    /**
     * 获取当前订单的额外消费的最大值
     * @param loadlordRequestStr
     * @return
     */
    String getOtherMoneyLimit(String loadlordRequestStr);

	/**
	 * 
	 * 房东端订单列表查询
	 * 1=待处理 2=进行中 3=已完成
	 *
	 * @author jixd
	 * @created 2016年5月4日 下午8:46:08
	 *
	 * @param orderRequest
	 * @return
	 */
	public String queryOrderList(String orderRequest);

	/**
	 * 
	 * 获取 一个房源 一个月 收益 所有的订单列表
	 * @author liyingjie
	 * @created 2016年6月25日 
	 * @param profitRequest
	 * @return
	 */
	String queryProfitOrderList(String profitRequest);
}
