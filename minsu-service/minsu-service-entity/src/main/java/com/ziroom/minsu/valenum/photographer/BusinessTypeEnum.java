package com.ziroom.minsu.valenum.photographer;

/**
 * 
 * <p>摄影师 业务类型 枚举</p>
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
public enum BusinessTypeEnum {

	BUSINESS_MINSU(1,"民宿");

	BusinessTypeEnum(int code,String value){

		this.code = code;
		this.value =value;

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
