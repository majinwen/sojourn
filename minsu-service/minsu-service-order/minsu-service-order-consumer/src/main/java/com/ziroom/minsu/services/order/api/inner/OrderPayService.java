package com.ziroom.minsu.services.order.api.inner;


public interface OrderPayService {
	
	/**
	 * 去支付
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param toPayRequestStr
	 * @return
	 */
	public String checkToPay(String params);
	
	/**
	 * 
	 * 支付回调
	 *
	 * @author liyingjie
	 * @created 2016年4月25日 
	 *
	 * @param params
	 * @return
	 */
	public String payCallBack(String encryption,int type);
	
	
	/**
	 * 获取账户余额
	 * @author lishaochuan
	 * @create 2016年5月5日下午9:45:59
	 * @param uid
	 * @param city
	 * @return
	 */
	public String getAccountBalance(String uid, int userType);

	/**
	 * 
	 * 查询单位时间内房源(房间)的交易量(已支付订单数量)
	 *
	 * @author liujun
	 * @created 2016年12月2日
	 *
	 * @param paramJson
	 * @return
	 */
	public String queryTradeNumByHouseFid(String paramJson);
	
}
