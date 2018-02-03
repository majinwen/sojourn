/**
 * @FileName: UpdateTypeEnum.java
 * @Package com.ziroom.minsu.valenum.photographer
 * 
 * @author yd
 * @created 2016年11月9日 上午9:58:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.photographer;

/**
 * <p>预约订单修改类型</p>
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
public enum UpdateTypeEnum {

	UPDATE_NORMAL(1,"正常修改"),
	UPDATE_APPOINTED_PHOTOG(2,"指定摄影师"),
	UPDATE_PHOTO_FINISHED(3,"摄影拍摄完成"),
	UPDATE_APPOINTED_PHOTOG_MODIFY(4,"重新指定摄影师"),
	DELETE_BOOK_ORDER(5,"作废摄影预约单"),
	UPDATE_RECE_PHOTO(6,"收图操作标志");
	
	
	UpdateTypeEnum(int code,String value){
		this.code = code;
		this.value = value;
	}
	
	/**
	 * 枚举code
	 */
	private int code;
	
	/**
	 * 枚举value
	 */
	private String value;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
