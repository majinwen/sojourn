package com.ziroom.minsu.services.finance.entity;

import java.util.Date;

import com.ziroom.minsu.entity.order.FinanceIncomeEntity;

/**
 * <p>后台管理查询收入返回实体</p>
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
public class FinanceIncomeVo extends FinanceIncomeEntity{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 8082523064273398680L;

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
	 * 房东姓名
	 */
	private String landlordName;
	
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	

	/**
	 * 结束时间
	 */
	private Date endTime;
	
	
	/**
	 * 实际退房时间时间
	 */
	private Date realEndTime;
	
	
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}
	
}
