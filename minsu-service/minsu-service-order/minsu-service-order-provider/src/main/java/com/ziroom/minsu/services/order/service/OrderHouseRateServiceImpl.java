/**
 * @FileName: OrderHouseRateServiceImpl.java
 * @Package com.ziroom.minsu.services.order.service
 * 
 * @author bushujie
 * @created 2016年4月11日 下午9:43:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.minsu.services.order.dao.HouseLockDao;

/**
 * <p>订单相关房源统计业务实现</p>
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
@Service("house.orderHouseRateServiceImpl")
public class OrderHouseRateServiceImpl {
	
	@Resource(name="order.houseLockDao")
	private HouseLockDao houseLockDao;

}
