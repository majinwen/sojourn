/**
 * @FileName: BookOrderStatuEnum.java
 * @Package com.ziroom.minsu.valenum.photographer
 * 
 * @author yd
 * @created 2016年11月4日 下午7:42:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.photographer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>摄影师预定订单状态</p>
 * 10=预约中 11=预约成功 12.完成
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
public enum BookOrderStatuEnum {

	ORDER_BOOKING(10,"预约中","预约中"),
	ORDER_BOOK_SUCCESS(11,"预约成功","已派单"),
	ORDER_RECEIVE_PHOTO(110,"收图","已拍摄"),
	ORDER_BOOK_FINISHED(12,"完成","已完成"),
	ORDER_BOOK_CANCEL(13,"作废","已拒绝"),
	DOOR_NOT_PHOTO(14,"上门未拍摄","上门未拍摄"),
	NOT_DOORANDPHOTO(15,"未上门未拍摄","未上门未拍摄");
	
	
	BookOrderStatuEnum(int code,String value,String showName){
		
		this.code = code;
		this.value = value;
		this.showName = showName;
	}
	
    private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (BookOrderStatuEnum enm : BookOrderStatuEnum.values()) {
			enumMap.put(enm.getCode(), enm.getShowName());  
        }  
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
	 * 枚举showName
	 */
	private String showName;
	

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

	/**
	 * @return the showName
	 */
	public String getShowName() {
		return showName;
	}

	/**
	 * @param showName the showName to set
	 */
	public void setShowName(String showName) {
		this.showName = showName;
	}

	public static Map<Integer, String> getEnummap() {
		return enumMap;
	}
	
}
