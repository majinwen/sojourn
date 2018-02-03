
package com.ziroom.minsu.services.order.service;

import com.ziroom.minsu.services.order.dao.OrderStaticsDao;
import com.ziroom.minsu.services.order.dto.OrderLandlordStaticsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>房东订单管理实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @date 2017-02-22 13:20
 * @version 1.0
 */
@Service("order.orderStaticsServiceImpl")
public class OrderStaticsServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderStaticsServiceImpl.class);

	@Resource(name = "order.orderStaticsDao")
	private OrderStaticsDao orderStaticsDao;

	/**
     * 获取当前定点杆的统计信息
	 * @param landlordUid
     * @return
     */
	public OrderLandlordStaticsDto staticsLandOrderCountInfo(String landlordUid){
		return orderStaticsDao.staticsLandOrderCountInfo(landlordUid);
	}
    
}
