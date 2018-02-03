/**
 * @FileName: ContactAuthDto.java
 * @Package com.ziroom.minsu.services.customer.dto
 * 
 * @author bushujie
 * @created 2016年4月22日 下午6:02:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>联系方式认证参数</p>
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
public class ContactAuthDto {
	
	//手机号码
	@NotEmpty(message = "{mobileNo.null}")
	private String mobileNo;
	//验证码
	@NotEmpty(message = "{authCode.null}")
	private String authCode;
	//uid
	@NotEmpty(message = "{uid.null}")
	private String uid;
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}
	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
}
