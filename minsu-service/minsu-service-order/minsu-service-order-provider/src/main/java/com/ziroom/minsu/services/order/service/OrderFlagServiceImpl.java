package com.ziroom.minsu.services.order.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.order.OrderFlagEntity;
import com.ziroom.minsu.services.order.dao.OrderFlagDao;

/**
 * <p>订单标记service</p>
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
@Service("order.orderFlagServiceImpl")
public class OrderFlagServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderFlagServiceImpl.class);
	
	@Resource(name="order.orderFlagDao")
	private OrderFlagDao orderFlagDao;
	
	/**
	 * 保存
	 * @author lishaochuan
	 * @create 2016年8月1日上午10:25:29
	 * @param orderFlag
	 * @return
	 */
	public int saveOrderFlag(OrderFlagEntity orderFlag){
		return orderFlagDao.saveOrderFlag(orderFlag);
	}
}
