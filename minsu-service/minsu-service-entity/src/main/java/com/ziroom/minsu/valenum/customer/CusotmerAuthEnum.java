
package com.ziroom.minsu.valenum.customer;

/**
 * <p>是否认证</p>
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
public enum CusotmerAuthEnum {

	 NotAuth(0,"未认证"),
	 IsAuth(1,"已认证");
	
	CusotmerAuthEnum(int code,String value){
		this.code = code;
		this.value = value;
	}
	
	/** 编码 */
	private int code;

	/** 名称 */
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
