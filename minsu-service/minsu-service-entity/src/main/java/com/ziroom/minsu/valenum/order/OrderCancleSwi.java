/**
 * @FileName: Swi.java
 * @Package com.ziroom.minsu.valenum.order
 * 
 * @author yd
 * @created 2017年1月5日 下午4:53:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.order;

/**
 * <p>客服取消订单 页面开关</p>
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
public enum OrderCancleSwi {
	
	
	CLICEK_SELCT_NO(110,"可点击未选择"),
	CLICEK_SELCT(111,"可点击已选择"),
	CLICEK_NO_SELCT(100,"不可点击已选择"),
	CLICEK_NO_SELCT_NO(101,"不可点击未选择");
	
	OrderCancleSwi(int code,String value){
		
		this.code = code;
		this.value = value;
	}
	
	/**
	 * 枚举code
	 */
	private int code;
	
	/**
	 * 枚举值
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
