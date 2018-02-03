
package com.ziroom.minsu.valenum.customer;

/**
 * <p>推送人群</p>
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
public enum JpushPersonType {

	ALL(0,"所有"),
	ONE(1,"单个"),
	MANY(2,"多个");
	
	JpushPersonType(int code,String value){
		
		this.code =  code;
		this.value = value;
	}
	/**
	 * code值
	 */
	private  int code;
	
	/**
	 * z中文含义
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
