/**
 * @FileName: IsReplayEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2016年4月18日 上午11:50:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>是否需要自动回复</p>
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
public enum IsReplayEnum {

	AUTO_REPLAY(0,"自动回复"),
	UNAUTO_REPLAY(1,"不自动回复");
	
	IsReplayEnum(int code,String chineseName){
		
		this.code  = code;
		this.chineseName = chineseName;
	}
	
	/**
	 * 枚举编码
	 */
	private int code;
	
	/**
	 * 中文名称
	 */
	private String chineseName;

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
	 * get IsReplayEnum by code
	 *
	 * @author yd
	 * @created 2016年4月18日 上午11:52:57
	 *
	 * @param code
	 * @return
	 */
	public static IsReplayEnum getIsReplayEnumByCode(int code){
		
		for (IsReplayEnum isReplayEnum : IsReplayEnum.values()) {
			if(isReplayEnum.getCode() == code){
				return isReplayEnum;
			}
		}
		return null;
	}
}
