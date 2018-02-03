/**
 * @FileName: CustomerBaseExtDto.java
 * @Package com.ziroom.minsu.services.customer.dto
 * 
 * @author bushujie
 * @created 2016年7月26日 下午2:55:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;

/**
 * <p>客户个人资料Dto</p>
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
public class CustomerBaseExtDto extends CustomerBaseMsgEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3191208940085030521L;
	/**
	 * 个人介绍
	 */
	private String customerIntroduce;
	
	/**
	 * 生日日期字符串
	 */
	private String customerBirthdayStr;

	/**
	 * @return the customerBirthdayStr
	 */
	public String getCustomerBirthdayStr() {
		return customerBirthdayStr;
	}

	/**
	 * @param customerBirthdayStr the customerBirthdayStr to set
	 */
	public void setCustomerBirthdayStr(String customerBirthdayStr) {
		this.customerBirthdayStr = customerBirthdayStr;
	}

	/**
	 * @return the customerIntroduce
	 */
	public String getCustomerIntroduce() {
		return customerIntroduce;
	}

	/**
	 * @param customerIntroduce the customerIntroduce to set
	 */
	public void setCustomerIntroduce(String customerIntroduce) {
		this.customerIntroduce = customerIntroduce;
	}
}
