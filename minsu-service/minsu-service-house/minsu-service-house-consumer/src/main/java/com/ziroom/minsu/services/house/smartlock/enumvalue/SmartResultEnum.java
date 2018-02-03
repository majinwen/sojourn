/**
 * @FileName: SmartResultEnum.java
 * @Package com.ziroom.minsu.services.house.smartlock.enumvalue
 * 
 * @author yd
 * @created 2016年6月25日 上午10:42:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.enumvalue;

/**
 * <p>智能锁返回结果</p>
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
public enum SmartResultEnum {
	SUCCESS(1,"成功"),
	FAILED(2,"失败");
	
	SmartResultEnum(int code,String value){
		this.code = code;
		this.value = value;
				
	}
	/**
	 * 枚举值
	 */
	private int code;
	
	/**
	 * 
	 */
	private String value;
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
