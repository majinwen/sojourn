/**
 * @FileName: CityOrderNumEntity.java
 * @Package com.ziroom.minsu.report.common.entity
 * 
 * @author bushujie
 * @created 2016年9月23日 下午5:05:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.order.entity;

/**
 * <p>城市订单量统计</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CityOrderNumEntity {
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 城市总订单量
	 */
	private Long orderTotal;
	
	/**
	 * 城市日订单量
	 */
	private Long orderDayNum;
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the orderTotal
	 */
	public Long getOrderTotal() {
		return orderTotal;
	}
	/**
	 * @param orderTotal the orderTotal to set
	 */
	public void setOrderTotal(Long orderTotal) {
		this.orderTotal = orderTotal;
	}
	/**
	 * @return the orderDayNum
	 */
	public Long getOrderDayNum() {
		return orderDayNum;
	}
	/**
	 * @param orderDayNum the orderDayNum to set
	 */
	public void setOrderDayNum(Long orderDayNum) {
		this.orderDayNum = orderDayNum;
	}
}
