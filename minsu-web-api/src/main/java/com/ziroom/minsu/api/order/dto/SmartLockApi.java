/**
 * @FileName: SmartLockApi.java
 * @Package com.ziroom.minsu.api.order.dto
 * 
 * @author yd
 * @created 2016年6月25日 上午10:18:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>TODO</p>
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
public class SmartLockApi extends BaseParamDto{

	/**
	 * 订单编号
	 */
	private String orderSn;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	
}
