/**
 * @FileName: OrderSpecialOfferVo.java
 * @Package com.ziroom.minsu.services.order.entity
 * 
 * @author yd
 * @created 2017年4月12日 下午4:14:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 说明： 由配置表获取
 * 订单 特殊优惠信息：
 * 1. 订单间隙优惠
 * 2. 订单折扣信息
 * </p>
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
public class OrderSpecialOfferVo extends BaseEntity{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 4911066030829596035L;

	/**
	 * 优惠code
	 */
	private String code;
	
	/**
	 * 展示名称
	 */
	private String showName;
	
	/**
	 * 优惠原始值
	 */
	private String val;
	
	/**
	 * 优惠展示值
	 */
	private String showVal;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the showName
	 */
	public String getShowName() {
		return showName;
	}

	/**
	 * @param showName the showName to set
	 */
	public void setShowName(String showName) {
		this.showName = showName;
	}

	/**
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}

	/**
	 * @return the showVal
	 */
	public String getShowVal() {
		return showVal;
	}

	/**
	 * @param showVal the showVal to set
	 */
	public void setShowVal(String showVal) {
		this.showVal = showVal;
	}
	
	
}
