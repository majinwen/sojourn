/**
 * @FileName: MobileAuthVo.java
 * @Package com.ziroom.minsu.portal.fd.center.customer.vo
 * 
 * @author bushujie
 * @created 2016年7月21日 下午5:38:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.customer.dto;

/**
 * <p>联系方式认证Vo</p>
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
public class MobileAuthDto {
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 图形验证码uid
	 */
	private String imgId;
	
	/**
	 * 图形验证码值
	 */
	private String imgVal;

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the imgId
	 */
	public String getImgId() {
		return imgId;
	}

	/**
	 * @param imgId the imgId to set
	 */
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	/**
	 * @return the imgVal
	 */
	public String getImgVal() {
		return imgVal;
	}

	/**
	 * @param imgVal the imgVal to set
	 */
	public void setImgVal(String imgVal) {
		this.imgVal = imgVal;
	}
}
