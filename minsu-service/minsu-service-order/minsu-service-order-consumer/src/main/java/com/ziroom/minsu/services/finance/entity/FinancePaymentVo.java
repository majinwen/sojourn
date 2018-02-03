package com.ziroom.minsu.services.finance.entity;

import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;

/**
 * <p>收款单实体返回实体</p>
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
public class FinancePaymentVo extends FinancePaymentVouchersEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -5875045132207982487L;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 付款状态
	 */
	private Integer payStatus;
	
	/**
	 * 预定人姓名
	 */
	private String userName;
	/**
	 * 预订人手机号
	 */
	private String userTel;
	/**
	 * 订单相关金额
	 */
	private OrderMoneyEntity orderMoneyEntity;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public OrderMoneyEntity getOrderMoneyEntity() {
		return orderMoneyEntity;
	}

	public void setOrderMoneyEntity(OrderMoneyEntity orderMoneyEntity) {
		this.orderMoneyEntity = orderMoneyEntity;
	}

	
}
