/**
 * @FileName: PasswordTypeEnum.java
 * @Package com.ziroom.minsu.services.house.smartlock.enumvalue
 * 
 * @author yd
 * @created 2016年6月23日 下午5:30:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.enumvalue;

/**
 * <p>智能锁密码类型
 *  密码类型：1：租客密码 2：内部员工 3：第三方
 * </p>
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
public enum PasswordTypeEnum {

	USER_PWD(1,"租客密码"),
	INTERNAL_STAFF(2,"内部员工"),
    THIRD_PARTY(3,"第三方");
	
	PasswordTypeEnum(int code,String value){
		
		this.code  = code;
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
