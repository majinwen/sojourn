package com.ziroom.minsu.services.order.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>订单返现申请请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月8日
 * @since 1.0
 * @version 1.0
 */
public class OrderCashbackRequest extends PageRequest {

	/**序列化ID*/
	private static final long serialVersionUID = 7534462291703659567L;
	
	/**房源fid*/
	private String houseFid;
	
	/**订单号*/
	private String orderSn;
	
	/**订单状态*/
	private Integer orderStatus;
	
	/**支付状态*/
	private String payStatus;
	
	/**支付开始时间*/
	String payDateStart;
	
	/**支付结束时间*/
	String payDateEnd;
	
	/**下单开始时间*/
	String createOrderDateStart;
	
	/**下单结束时间*/
	String createOrderDateEnd;
	
	/**
	 * 角色类型
	 */
	private Integer roleType;
	
	/**
	 * 房源fid集合
	 */
	private List<String> houseFids;

	/**
	 * @return the houseFids
	 */
	public List<String> getHouseFids() {
		return houseFids;
	}

	/**
	 * @param houseFids the houseFids to set
	 */
	public void setHouseFids(List<String> houseFids) {
		this.houseFids = houseFids;
	}

	/**
	 * @return the roleType
	 */
	public Integer getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayDateStart() {
		return payDateStart;
	}

	public void setPayDateStart(String payDateStart) {
		this.payDateStart = payDateStart;
	}

	public String getPayDateEnd() {
		return payDateEnd;
	}

	public void setPayDateEnd(String payDateEnd) {
		this.payDateEnd = payDateEnd;
	}

	public String getCreateOrderDateStart() {
		return createOrderDateStart;
	}

	public void setCreateOrderDateStart(String createOrderDateStart) {
		this.createOrderDateStart = createOrderDateStart;
	}

	public String getCreateOrderDateEnd() {
		return createOrderDateEnd;
	}

	public void setCreateOrderDateEnd(String createOrderDateEnd) {
		this.createOrderDateEnd = createOrderDateEnd;
	}
	
}
