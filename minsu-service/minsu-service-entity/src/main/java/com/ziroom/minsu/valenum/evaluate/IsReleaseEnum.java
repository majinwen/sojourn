
package com.ziroom.minsu.valenum.evaluate;

/**
 * <p>评价状态是否发布</p>
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
public enum IsReleaseEnum {

	Release(0,"发布"),
	NotRelease(1,"不发布");
	
	IsReleaseEnum(int code,String value){
		
		this.code = code;
		this.value = value;
	}
	
	private int code;
	
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
}
