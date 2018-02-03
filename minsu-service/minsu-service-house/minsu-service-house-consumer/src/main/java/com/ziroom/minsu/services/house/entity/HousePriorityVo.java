/**
 * @FileName: HousePriorityVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author yd
 * @created 2016年12月7日 上午9:21:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.io.Serializable;

/**
 * <p>夹心价格返回vo</p>
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
public class HousePriorityVo implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -5062396950752539725L;

	/**
	 * 夹心价格 折扣(例如 0.8)
	 */
	private  String priorityDiscount;
	
	/**
	 * 夹心价格 日期(例如 2016-02-12)
	 */
	private  String priorityDate;
	
	/**
	 * 夹心价格展示名称
	 */
	private  String priorityName;
	
	/**
	 * 夹心枚举code
	 */
	private  String priorityCode;

	/**
	 * @return the priorityDiscount
	 */
	public String getPriorityDiscount() {
		return priorityDiscount;
	}

	/**
	 * @param priorityDiscount the priorityDiscount to set
	 */
	public void setPriorityDiscount(String priorityDiscount) {
		this.priorityDiscount = priorityDiscount;
	}

	/**
	 * @return the priorityDate
	 */
	public String getPriorityDate() {
		return priorityDate;
	}

	/**
	 * @param priorityDate the priorityDate to set
	 */
	public void setPriorityDate(String priorityDate) {
		this.priorityDate = priorityDate;
	}

	/**
	 * @return the priorityName
	 */
	public String getPriorityName() {
		return priorityName;
	}

	/**
	 * @param priorityName the priorityName to set
	 */
	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	/**
	 * @return the priorityCode
	 */
	public String getPriorityCode() {
		return priorityCode;
	}

	/**
	 * @param priorityCode the priorityCode to set
	 */
	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}
	
	
}
