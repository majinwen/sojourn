package com.ziroom.minsu.services.finance.entity;

import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;

/**
 * <p>后台付款单 查询返回实体信息</p>
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
public class FinancePayVouchersVo extends FinancePayVouchersEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 1L;

	
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
	 * 收款人名称
	 */
	private String receiveName;
	
	/**
	 * 收款人电话
	 */
	private String receiveTel;
	

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

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceiveTel() {
		return receiveTel;
	}

	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}


}
