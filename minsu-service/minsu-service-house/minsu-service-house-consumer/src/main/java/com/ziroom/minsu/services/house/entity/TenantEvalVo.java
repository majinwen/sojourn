/**
 * @FileName: TenantEvalVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年5月2日 下午5:30:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

/**
 * <p>评价信息vo</p>
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
public class TenantEvalVo {
	
	/**
	 * 评价用户名称
	 */
	private String customerName;
	
	/**
	 * 评价内容
	 */
	private String evalContent;
	
	/**
	 * 评价时间
	 */
	private String evalDate;
	
	/**
	 * 客户头像信息
	 */
	private String customerIcon;
	
	/**
	 * 房东评价
	 */
	private String landlordEvalContent;

	/**
	 * @return the landlordEvalContent
	 */
	public String getLandlordEvalContent() {
		return landlordEvalContent;
	}

	/**
	 * @param landlordEvalContent the landlordEvalContent to set
	 */
	public void setLandlordEvalContent(String landlordEvalContent) {
		this.landlordEvalContent = landlordEvalContent;
	}

	/**
	 * @return the customerIcon
	 */
	public String getCustomerIcon() {
		return customerIcon;
	}

	/**
	 * @param customerIcon the customerIcon to set
	 */
	public void setCustomerIcon(String customerIcon) {
		this.customerIcon = customerIcon;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the evalContent
	 */
	public String getEvalContent() {
		return evalContent;
	}

	/**
	 * @param evalContent the evalContent to set
	 */
	public void setEvalContent(String evalContent) {
		this.evalContent = evalContent;
	}

	/**
	 * @return the evalDate
	 */
	public String getEvalDate() {
		return evalDate;
	}

	/**
	 * @param evalDate the evalDate to set
	 */
	public void setEvalDate(String evalDate) {
		this.evalDate = evalDate;
	}
}
