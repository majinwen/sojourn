/**
 * @FileName: ChatTypeEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2017年8月2日 下午4:27:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>聊天类型</p>
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
public enum ChatTypeEnum {
	
	chat("chat"),groupchat("groupchat");
	
	ChatTypeEnum(String val){
		
		this.val =val;
	}
	
	private String val;

	/**
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}
	
	
	
}
