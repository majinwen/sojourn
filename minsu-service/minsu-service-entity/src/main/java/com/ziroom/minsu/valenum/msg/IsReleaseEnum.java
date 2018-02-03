/**
 * @FileName: IsReleaseEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2016年4月18日 上午11:01:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>是否发布</p>
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
public enum IsReleaseEnum {

	RELEASE(1,"已发布"),
	NOT_RELEASE(0,"未发布");
	
	IsReleaseEnum(int code,String chineseName){
		this.code = code;
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
	 * 根据code获取标签类型枚举
	 *
	 * @author yd
	 * @created 2016年4月18日 上午11:14:25
	 *
	 * @param code
	 * @return
	 */
	public static IsReleaseEnum getIsReleaseEnumByCode(int code){
		
		for (IsReleaseEnum isReleaseEnum : IsReleaseEnum.values()) {
			if(isReleaseEnum.getCode() == code){
				return isReleaseEnum;
			}
		}
		return null;
				 
	}
}
