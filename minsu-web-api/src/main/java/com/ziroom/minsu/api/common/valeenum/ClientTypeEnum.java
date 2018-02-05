
package com.ziroom.minsu.api.common.valeenum;

/**
 * <p>客户端请求类型枚举</p>
 *  客户端类型：0.未知，1.Android，2.iOS，3.mobile web，4.PC web
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
public enum ClientTypeEnum {

	UNKNOWN(0,"未知"),
	ANDROID(1,"Android"),
	IOS(2,"IOS"),
	MOBILE_WEB(3,"mobile web"),
	PC_WEB(4,"PC web");
	
	ClientTypeEnum(int code,String value){
		this.code = code;
		this.value = value;
	}
	/**
	 * code
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
