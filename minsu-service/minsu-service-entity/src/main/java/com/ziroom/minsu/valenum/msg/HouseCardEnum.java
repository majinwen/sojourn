/**
 * @FileName: HouseCardEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2017年4月11日 上午10:55:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>房源卡类型</p>
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
public enum HouseCardEnum {

	HOUSE_CARD_GENERAL(0,"普通消息"),
	HOUSE_CARD_FIRST_ADVISORY(1,"首咨"),
	HOUSE_CARD_SYSMSG(2,"系统消息");
	
	HouseCardEnum(int val,String name){
		
		this.val = val;
		this.name = name;
	}
	
	private int val;
	
	private String name;

	/**
	 * @return the val
	 */
	public int getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(int val) {
		this.val = val;
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
