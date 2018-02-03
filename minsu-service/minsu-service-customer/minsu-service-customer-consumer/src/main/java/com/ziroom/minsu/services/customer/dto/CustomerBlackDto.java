/**
 * @FileName: CustomerBaseMsgDto.java
 * @Package com.ziroom.minsu.services.customer.dto
 * 
 * @author jixd
 * @created 2016年4月23日 下午9:59:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>黑名单查询dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class CustomerBlackDto extends PageRequest{

	private static final long serialVersionUID = 7794401087055069802L;


	/**
	 * 用户imei 黑名单用户imei
	 */
	private String imei;

	/**
	 * 用户uid 黑名单用户uid
	 */
	private String uid;

	/**
	 * 用户真实姓名
	 */
	private String realName;

	/**
	 * 用户电话
	 */
	private String customerMobile;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
