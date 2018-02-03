/**
 * @FileName: ProductRulesTonightDisEnum.java
 * @Package com.ziroom.minsu.valenum.productrules
 * 
 * @author yd
 * @created 2017年5月11日 上午10:09:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.productrules;

/**
 * <p>今夜特价 枚举code</p>
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
public enum ProductRulesTonightDisEnum {

	ProductRulesTonightDisEnum001("ProductRulesTonightDisEnum001","今夜特价");
	
	ProductRulesTonightDisEnum(String code,String val){
		
		this.code = code;
		this.val = val;
	}
	
	private String code;
	
	private String val;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}
	
	
}
