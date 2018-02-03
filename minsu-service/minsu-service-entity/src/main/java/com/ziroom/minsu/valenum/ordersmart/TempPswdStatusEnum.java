/**
 * @FileName: PersistPswdStatusEnum.java
 * @Package com.ziroom.minsu.valenum.ordersmart
 * 
 * @author yd
 * @created 2016年6月23日 下午8:37:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.ordersmart;

/**
 * <p>智能锁状态</p>
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
public enum TempPswdStatusEnum {

	SENDING(0,"下发中"),
	FAILED(1,"下发失败"),
	SUCCESS(2,"下发成功"),
	CLOSED(3,"关闭");
	
	TempPswdStatusEnum(int code,String value){
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
