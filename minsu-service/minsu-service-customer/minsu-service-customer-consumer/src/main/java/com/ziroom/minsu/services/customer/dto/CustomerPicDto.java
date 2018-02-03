/**
 * @FileName: CustomerPicDto.java
 * @Package com.ziroom.minsu.services.customer.dto
 * 
 * @author jixd
 * @created 2016年4月25日 下午10:52:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

/**
 * <p>用户图片参数</p>
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
public class CustomerPicDto {
	/**
	 * 客户uid
	 */
	private String uid;
	/**
	 * 图片类型
	 */
	private Integer picType;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getPicType() {
		return picType;
	}
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	
	
}
