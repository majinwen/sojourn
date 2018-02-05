package com.ziroom.minsu.api.order.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

public class CheckOutOrderApiRequest extends BaseParamDto{

	/** 订单编号 */
	private String orderSn;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

}
