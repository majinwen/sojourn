/**
 * @FileName: OrderHouseRateService.java
 * @Package com.ziroom.minsu.services.order.api.inner
 * 
 * @author bushujie
 * @created 2016年4月11日 下午6:45:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.api.inner;

/**
 * <p>订单相关房源统计接口</p>
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
public interface OrderHouseRateService {
	
	/**
	 * 
	 * 查询房源未来30天预订率
	 *
	 * @author bushujie
	 * @created 2016年4月11日 下午6:47:43
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public String houseBookRate(String fid,Integer rentWay);

}
