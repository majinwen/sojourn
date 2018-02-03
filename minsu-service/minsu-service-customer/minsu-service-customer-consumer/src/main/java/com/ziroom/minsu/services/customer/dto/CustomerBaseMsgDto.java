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
 * <p>查询用户参数</p>
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
public class CustomerBaseMsgDto extends PageRequest{
	/**
	 * 序列Id
	 */
	private static final long serialVersionUID = -2243156329363768261L;
	/**
	 * uid
	 */
	private String uid;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 用户名
	 */
	private String nickName;
	/**
	 * 手机号码
	 */
	private String customerMobile;
	/**
	 * 是否房东  0：否，1：是
	 */
	private Integer isLandlord;
	/**
	 * 审核状态 0：未审核，1：审核通过，2：审核未通过
	 */
	private Integer auditStatus;
	
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public Integer getIsLandlord() {
		return isLandlord;
	}
	public void setIsLandlord(Integer isLandlord) {
		this.isLandlord = isLandlord;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}
