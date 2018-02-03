/**
 * @FileName: ImTypeEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author loushuai
 * @created 2017年8月31日 下午9:00:44
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

import java.io.ObjectInputStream.GetField;

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
public enum ImTypeEnum {

	TXT_MSG(0,"文本消息","txt"),
	IMG_MSG(1,"图片消息","img"),
	LOC_MSG(2,"位置消息","loc"),
	AUDIO_MSG(3,"语音消息","audio"),
	CMD_MSG(10,"透传消息","cmd");
	
	public int code;
	
	public String name;
	
	private String type;

	private ImTypeEnum(int code, String name, String type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static ImTypeEnum getByCode(int code){
		for (ImTypeEnum temp : ImTypeEnum.values()) {
			if(code==temp.getCode()){
				return temp;
			}
		}
		return null;
	}
	
}
