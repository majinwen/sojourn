/**
 * @FileName: HouseConfDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author zl
 * @created 2017年6月27日 上午11:59:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

/**
 * <p>房源配置参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class HouseConfParamsDto extends HouseBaseParamsDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6158018489495246051L;
 
	/**
	 * 配置编号
	 */
	private String dicCode;
	 
	public String getDicCode() {
		return dicCode;
	}
	 
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

}
