/**
 * @FileName: OrderRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author liyingjie
 * @created 2016年4月2日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.dto;

import java.util.Date;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 订单定时任务请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class OrderTaskRequest extends PageRequest {

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 支付状态
	 */
	private Integer payStatus;
	
	/**
	 * 最后修改时间
	 */
	private Date limitDate;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}


    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }
}
