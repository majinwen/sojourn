package com.ziroom.minsu.services.order.dto;

import java.io.Serializable;

import com.ziroom.minsu.entity.order.OrderRelationEntity;

/**
 * <p>保存新旧订单关联 实体 的请求参数</p>
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
public class OrderRelationSaveRequest implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -4420306722310338634L;
	
	/**
	 * 新旧订单关联实体
	 */
	private OrderRelationEntity orderRelationEntity;
	
	/**
	 *付款人uid
	 */
	private String payUid;
	
	/**
	 * 收款人uid
	 */
	private String receiveUid;

	public OrderRelationEntity getOrderRelationEntity() {
		return orderRelationEntity;
	}

	public void setOrderRelationEntity(OrderRelationEntity orderRelationEntity) {
		this.orderRelationEntity = orderRelationEntity;
	}

	public String getPayUid() {
		return payUid;
	}

	public void setPayUid(String payUid) {
		this.payUid = payUid;
	}

	public String getReceiveUid() {
		return receiveUid;
	}

	public void setReceiveUid(String receiveUid) {
		this.receiveUid = receiveUid;
	}
	
	

}
