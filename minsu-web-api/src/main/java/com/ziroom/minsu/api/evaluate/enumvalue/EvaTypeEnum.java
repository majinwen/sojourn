package com.ziroom.minsu.api.evaluate.enumvalue;

/**
 * <p>评价类型</p>
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
public enum EvaTypeEnum {

	WAITING_EVA(1,"待评价"),

	HAD_EVA(2,"已评价");
	
     EvaTypeEnum(int code,String value){
		this.code = code;
		this.value = value;
	}
	
	/**
	 * 编号
	 */
	private int code;
	
	/**
	 * 中文含义
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
