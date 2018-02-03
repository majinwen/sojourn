/**
 * @FileName: ImForChangzuTxtMsgTypeEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author loushuai
 * @created 2017年8月31日 下午8:38:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public enum ImForTxtMsgTypeEnum {

	NORMAL_MSG(100,"一般文本消息"),
	GREET_MSG(101,"打招呼消息"),
	CARD_MSG(102,"卡片消息"),
	ACTIVITY_MSG(103,"活动消息"),
	GIF_MSG(104,"动画表情消息");
	
	ImForTxtMsgTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	private int code;
	
	private String name;

	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	//根据code获取对象
	public static ImForTxtMsgTypeEnum getByCode(int code){
		for (ImForTxtMsgTypeEnum temp : ImForTxtMsgTypeEnum.values()) {
			if(temp.getCode()==code){
				return temp;
			}
		}
		return null;
	}
	
	
	
}
