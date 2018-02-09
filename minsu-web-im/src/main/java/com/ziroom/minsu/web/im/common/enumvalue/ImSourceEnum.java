package com.ziroom.minsu.web.im.common.enumvalue;

/**
 * 
 * <p></p>
 * 1.房源详情（app）
 * 2.消息列表（mapp）
 * 3.也可能是客户端（client）
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
public enum ImSourceEnum {

	APP_ANDROID(1,"app_ios"),
	APP_IOS(4,"app_android"),
	MAPP(2,"mapp"),
	CLIENT(3,"client");
	
	ImSourceEnum(int code,String value){
		
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

