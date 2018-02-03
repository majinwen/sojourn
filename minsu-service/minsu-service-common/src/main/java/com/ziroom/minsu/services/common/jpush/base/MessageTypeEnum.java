/**
 * @FileName: MessageTypeEnum.java
 * @Package com.ziroom.minsu.services.common.jpush.base
 * 
 * @author yd
 * @created 2016年4月19日 下午3:22:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.jpush.base;

/**
 * <p>消息类型枚举</p>
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
public enum MessageTypeEnum {

	NOTIFICATION(100,"通知"),
	MESSAGE(101,"自定义消息");
	
	MessageTypeEnum(int code,String chineseName){
		this.chineseName = chineseName;
		this.code  =code;
	}
	
	/**
	 * 枚举值
	 */
	private int code;
	
	/**
	 * 中文名称
	 */
	private  String chineseName;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	/**
	 * 
	 * get  MessageTypeEnum by code
	 *
	 * @author yd
	 * @created 2016年4月19日 下午3:26:42
	 *
	 * @param code
	 * @return
	 */
	public static MessageTypeEnum  getMessageTypeEnumByCode(int code){
		
		for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
			if(messageTypeEnum.getCode() == code){
				return messageTypeEnum;
			}
		}
		return null;
	}
	
}
