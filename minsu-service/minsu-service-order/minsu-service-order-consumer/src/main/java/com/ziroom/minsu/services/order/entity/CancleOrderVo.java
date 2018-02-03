/**
 * @FileName: CancleOrderVo.java
 * @Package com.ziroom.minsu.services.order.entity
 * 
 * @author yd
 * @created 2017年1月4日 下午7:49:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;

/**
 * <p>客服取消订单 vo</p>
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
public class CancleOrderVo extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 2023141826445515953L;
	
	/**
	 * 订单金额 consume
	 */
	private OrderInfoVo orderInfoVo;
	
	/**
	 * 已消费 房租
	 */
	private Integer rentalMoneyConsume;
	
	/**
	 * 用户已消费服务费
	 */
	private Integer userCommMoneyConsume;
	
	/**
	 * 已消费 清洁费
	 */
	private  Integer cleanMoneyConsume;
	
	/**
	 * 用户支付现金 金额
	 */
	private  Integer payMoney;
	
	/**
	 * 剩余金额  当前客服可支配的
	 */
	private Integer lastMoney;
	
	/**
	 * 折扣金额
	 */
	private Integer discountMoney; 
	
	/**
	 * 收取房东违约金 服务费开关   101 =  不可点击  未选择  110=可点击  已选择    111=可点击  未选择
	 */
	private int switchLanMoney;
	
	/**
	 * 退换房客 当天房费开关
	 */
	private int switchTenCurDay;
	
	/**
	 * 退换清洁费开关
	 * 
	 */
	private int switchCleanFee;
	
	/**
	 * 优惠卷剩余金额
	 */
	private int couponMoneyConsume;
	
	/**
	 * 当前日期 格式 XXXX年XX月XX日
	 */
	private String curDay;
	
	/**
	 * 房东佣金比率
	 */
	private Double commissionRateLandlord;
	
	/**
	 * 房客当天房租
	 */
	private int curDayMoney =0;
	
	/**
	 * 剩余金额是否包含  0=包含 1=不包含
	 */
	private int isHascurDayMoney;
	
	/**
	 * 剩余金额是否包含清洁费  0=包含 1=不包含
	 */
	private int isHasCleanMoney;
	
	/**
	 * 用户真实剩余金额
	 */
	private int realLastMoney;
	
	/**
	 * 100元开关
	 * 
	 */
	private int switchOneHundred;
	
	/**
	 * 订单首夜房费开关
	 * 
	 */
	private int switchFirstNightMoney;
	
	/**
	 * 订单开始时间
	 * 
	 */
	private String startTime;
	
	/**
	 * @return the realLastMoney
	 */
	public int getRealLastMoney() {
		return realLastMoney;
	}

	/**
	 * @param realLastMoney the realLastMoney to set
	 */
	public void setRealLastMoney(int realLastMoney) {
		this.realLastMoney = realLastMoney;
	}

	/**
	 * @return the isHascurDayMoney
	 */
	public int getIsHascurDayMoney() {
		return isHascurDayMoney;
	}

	/**
	 * @param isHascurDayMoney the isHascurDayMoney to set
	 */
	public void setIsHascurDayMoney(int isHascurDayMoney) {
		this.isHascurDayMoney = isHascurDayMoney;
	}

	/**
	 * @return the isHasCleanMoney
	 */
	public int getIsHasCleanMoney() {
		return isHasCleanMoney;
	}

	/**
	 * @param isHasCleanMoney the isHasCleanMoney to set
	 */
	public void setIsHasCleanMoney(int isHasCleanMoney) {
		this.isHasCleanMoney = isHasCleanMoney;
	}

	/**
	 * @return the curDayMoney
	 */
	public int getCurDayMoney() {
		return curDayMoney;
	}

	/**
	 * @param curDayMoney the curDayMoney to set
	 */
	public void setCurDayMoney(int curDayMoney) {
		this.curDayMoney = curDayMoney;
	}

	/**
	 * @return the commissionRateLandlord
	 */
	public Double getCommissionRateLandlord() {
		return commissionRateLandlord;
	}

	/**
	 * @param commissionRateLandlord the commissionRateLandlord to set
	 */
	public void setCommissionRateLandlord(Double commissionRateLandlord) {
		this.commissionRateLandlord = commissionRateLandlord;
	}

	/**
	 * @return the curDay
	 */
	public String getCurDay() {
		return curDay;
	}

	/**
	 * @param curDay the curDay to set
	 */
	public void setCurDay(String curDay) {
		this.curDay = curDay;
	}

	/**
	 * @return the couponMoneyConsume
	 */
	public int getCouponMoneyConsume() {
		return couponMoneyConsume;
	}

	/**
	 * @param couponMoneyConsume the couponMoneyConsume to set
	 */
	public void setCouponMoneyConsume(int couponMoneyConsume) {
		this.couponMoneyConsume = couponMoneyConsume;
	}

	/**
	 * @return the switchLanMoney
	 */
	public int getSwitchLanMoney() {
		return switchLanMoney;
	}

	/**
	 * @param switchLanMoney the switchLanMoney to set
	 */
	public void setSwitchLanMoney(int switchLanMoney) {
		this.switchLanMoney = switchLanMoney;
	}

	/**
	 * @return the switchTenCurDay
	 */
	public int getSwitchTenCurDay() {
		return switchTenCurDay;
	}

	/**
	 * @param switchTenCurDay the switchTenCurDay to set
	 */
	public void setSwitchTenCurDay(int switchTenCurDay) {
		this.switchTenCurDay = switchTenCurDay;
	}

	/**
	 * @return the switchCleanFee
	 */
	public int getSwitchCleanFee() {
		return switchCleanFee;
	}

	/**
	 * @param switchCleanFee the switchCleanFee to set
	 */
	public void setSwitchCleanFee(int switchCleanFee) {
		this.switchCleanFee = switchCleanFee;
	}

	/**
	 * @return the discountMoney
	 */
	public Integer getDiscountMoney() {
		return discountMoney;
	}

	/**
	 * @param discountMoney the discountMoney to set
	 */
	public void setDiscountMoney(Integer discountMoney) {
		this.discountMoney = discountMoney;
	}

	/**
	 * @return the lastMoney
	 */
	public Integer getLastMoney() {
		return lastMoney;
	}

	/**
	 * @param lastMoney the lastMoney to set
	 */
	public void setLastMoney(Integer lastMoney) {
		this.lastMoney = lastMoney;
	}

	


	/**
	 * @return the payMoney
	 */
	public Integer getPayMoney() {
		return payMoney;
	}

	/**
	 * @param payMoney the payMoney to set
	 */
	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	/**
	 * @return the orderInfoVo
	 */
	public OrderInfoVo getOrderInfoVo() {
		return orderInfoVo;
	}

	/**
	 * @param orderInfoVo the orderInfoVo to set
	 */
	public void setOrderInfoVo(OrderInfoVo orderInfoVo) {
		this.orderInfoVo = orderInfoVo;
	}

	/**
	 * @return the rentalMoneyConsume
	 */
	public Integer getRentalMoneyConsume() {
		return rentalMoneyConsume;
	}

	/**
	 * @param rentalMoneyConsume the rentalMoneyConsume to set
	 */
	public void setRentalMoneyConsume(Integer rentalMoneyConsume) {
		this.rentalMoneyConsume = rentalMoneyConsume;
	}

	/**
	 * @return the userCommMoneyConsume
	 */
	public Integer getUserCommMoneyConsume() {
		return userCommMoneyConsume;
	}

	/**
	 * @param userCommMoneyConsume the userCommMoneyConsume to set
	 */
	public void setUserCommMoneyConsume(Integer userCommMoneyConsume) {
		this.userCommMoneyConsume = userCommMoneyConsume;
	}

	/**
	 * @return the cleanMoneyConsume
	 */
	public Integer getCleanMoneyConsume() {
		return cleanMoneyConsume;
	}

	/**
	 * @param cleanMoneyConsume the cleanMoneyConsume to set
	 */
	public void setCleanMoneyConsume(Integer cleanMoneyConsume) {
		this.cleanMoneyConsume = cleanMoneyConsume;
	}

	public int getSwitchOneHundred() {
		return switchOneHundred;
	}

	public void setSwitchOneHundred(int switchOneHundred) {
		this.switchOneHundred = switchOneHundred;
	}

	public int getSwitchFirstNightMoney() {
		return switchFirstNightMoney;
	}

	public void setSwitchFirstNightMoney(int switchFirstNightMoney) {
		this.switchFirstNightMoney = switchFirstNightMoney;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}


}
