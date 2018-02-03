/**
 * @FileName: RunStatusEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2017年4月8日 下午1:28:35
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>IM5分钟回复 定时任务执行状态</p>
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
public enum RunStatusEnum {

	NOT_RUN(0,"未执行"),
	RUN_SUCCESS(1,"执行成功"),
	RUN_FAILED(2,"执行失败"),
	SYS_DELETE(3,"系统删除");
	
	RunStatusEnum(int value,String name){
		this.value = value;
		this.name = name;
	}
	
	private int value;
	
	private String name;

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
