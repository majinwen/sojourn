/**
 * @FileName: IsPhotographyEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2016年11月7日 下午2:49:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

/**
 * <p>是否预约摄影</p>
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
public enum IsPhotographyEnum {

	IS_NOT_PHOTOGRAPHY(0,"未预约摄影"),
	IS_PHOTOGRAPHY(1,"已预约摄影"),
	IS_PHOTOGRAPHY_ING(2,"预约中");
	
	IsPhotographyEnum(int code,String value){
		this.code = code;
		this.value = value;
	}
	
	
	/**
	 * 枚举code
	 */
	private int code;
	
	/**
	 * 枚举value
	 */
	private String value;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
