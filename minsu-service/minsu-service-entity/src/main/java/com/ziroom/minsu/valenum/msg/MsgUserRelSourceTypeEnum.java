/**
 * @FileName: MsgUserRelSourceTypeEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author loushuai
 * @created 2017年9月4日 下午3:40:34
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
public enum MsgUserRelSourceTypeEnum {
    SHIELD(200,"屏蔽"),
    CANCELSHIELD(201,"取消屏蔽"),
	COMPLAINT(202,"投诉");
    
	
	private int code;
    
    private String name;

    
	private MsgUserRelSourceTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
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
    
	/**
	 * 根据code获取对象
	 *
	 * @author loushuai
	 */
	public static MsgUserRelSourceTypeEnum getByCode(int code){
		for (final MsgUserRelSourceTypeEnum temp : MsgUserRelSourceTypeEnum.values()) {
			if(temp.code == code){
				return temp;
			}
		} 
		return null;
	}
}
