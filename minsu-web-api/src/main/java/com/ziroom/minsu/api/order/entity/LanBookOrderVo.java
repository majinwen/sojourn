/**
 * @FileName: LanBookOrderVo.java
 * @Package com.ziroom.minsu.api.order.entity
 * 
 * @author yd
 * @created 2017年3月28日 下午6:19:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.entity;

import java.io.Serializable;

/**
 * <p>房东端申请预定页vo</p>
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
public class LanBookOrderVo implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 9207595996839335280L;


	/**
	 * 房源名称，根据出租方式，显示房源名称还是房间名称
	 */
	private String houseName;
	
	/**
	 * 开始时间 yyyy-MM-dd
	 */
	private String startTime;
	
	/**
	 * 结束时间 yyyy-MM-dd
	 */
	private String endTime;
	
	/**
	 * 开始星期
	 */
	private String startWeek;
	
	/**
	 * 结束星期
	 */
	private String endWeek;
	
	/**
	 * 押金
	 */
	private Integer depositMoney;
	
	/**
	 * 订单预计收入
	 */
	private Integer orderExpectedIncome;
	
	/**
	 * 房租
	 */
	private Integer rentalMoney;
	
	/**
	 * 清洁费
	 */
	private Integer cleanFeeMoney;
	
	/**
	 * 折扣金额
	 */
    private Integer discountMoney;
    
	/**
	 * 房东的佣金
	 */
	private Integer lanCommMoney;

	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the startWeek
	 */
	public String getStartWeek() {
		return startWeek;
	}

	/**
	 * @param startWeek the startWeek to set
	 */
	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}

	/**
	 * @return the endWeek
	 */
	public String getEndWeek() {
		return endWeek;
	}

	/**
	 * @param endWeek the endWeek to set
	 */
	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}

	/**
	 * @return the depositMoney
	 */
	public Integer getDepositMoney() {
		return depositMoney;
	}

	/**
	 * @param depositMoney the depositMoney to set
	 */
	public void setDepositMoney(Integer depositMoney) {
		this.depositMoney = depositMoney;
	}

	/**
	 * @return the orderExpectedIncome
	 */
	public Integer getOrderExpectedIncome() {
		return orderExpectedIncome;
	}

	/**
	 * @param orderExpectedIncome the orderExpectedIncome to set
	 */
	public void setOrderExpectedIncome(Integer orderExpectedIncome) {
		this.orderExpectedIncome = orderExpectedIncome;
	}

	/**
	 * @return the rentalMoney
	 */
	public Integer getRentalMoney() {
		return rentalMoney;
	}

	/**
	 * @param rentalMoney the rentalMoney to set
	 */
	public void setRentalMoney(Integer rentalMoney) {
		this.rentalMoney = rentalMoney;
	}

	/**
	 * @return the cleanFeeMoney
	 */
	public Integer getCleanFeeMoney() {
		return cleanFeeMoney;
	}

	/**
	 * @param cleanFeeMoney the cleanFeeMoney to set
	 */
	public void setCleanFeeMoney(Integer cleanFeeMoney) {
		this.cleanFeeMoney = cleanFeeMoney;
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
	 * @return the lanCommMoney
	 */
	public Integer getLanCommMoney() {
		return lanCommMoney;
	}

	/**
	 * @param lanCommMoney the lanCommMoney to set
	 */
	public void setLanCommMoney(Integer lanCommMoney) {
		this.lanCommMoney = lanCommMoney;
	}
	
	
}
