/**
 * @FileName: SpecialPriceVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月3日 下午11:14:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.util.Date;

/**
 * <p>特殊价格</p>
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
public class SpecialPriceVo {
	
	/**
	 * 日期
	 */
	private Date  setDate;
	
	/**
	 * 设置价格
	 */
	private Integer setPrice;
	
	
	/**
	 * 逻辑id
	 */
	private String fid;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the setDate
	 */
	public Date getSetDate() {
		return setDate;
	}

	/**
	 * @param setDate the setDate to set
	 */
	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	/**
	 * @return the setPrice
	 */
	public Integer getSetPrice() {
		return setPrice;
	}

	/**
	 * @param setPrice the setPrice to set
	 */
	public void setSetPrice(Integer setPrice) {
		this.setPrice = setPrice;
	}

}
