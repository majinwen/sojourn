/**
 * @FileName: FailUrlRecordTypeEnum.java
 * @Package com.ziroom.minsu.spider.airbnb.entity.enums
 * 
 * @author zl
 * @created 2016年10月12日 下午3:23:06
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.failurls.entity.enums;

import java.util.HashMap;
import java.util.Map;



/**
 * 
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum FailUrlRecordTypeEnum {
	
	AirbnbHouse(11,"airbnb房源"),
	AirbnbHost(21,"airbnb房东"),
	AirbnbList(31,"airbnb列表"),
	AirbnbPrice(41,"airbnb价格"),
	XiaozhuHouse(12,"小猪房源"),
	XiaozhuHost(22,"小猪房东"),
	XiaozhuList(32,"小猪列表"),
	XiaozhuPrice(42,"小猪价格");
	
	FailUrlRecordTypeEnum(Integer code,String name) {
		this.code = code;
		this.name = name; 
	}

	/** code */
	private Integer code;

	/** 名称 */
	private String name;
	
	private static Map<Integer,String> codeNameMap=new HashMap<Integer, String>();
	
	private static  Map<Integer,String> selectListMap=new HashMap<Integer, String>();
	
	static{
		
		for (FailUrlRecordTypeEnum fenum : FailUrlRecordTypeEnum.values()) {
			codeNameMap.put(fenum.getCode(), fenum.getName());
			if (fenum.getCode()!=41 && fenum.getCode()!=42) {
				selectListMap.put(fenum.getCode(), fenum.getName());
			}
		}
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	
	public static Map<Integer,String> getSelectList(){
		 
		return selectListMap;
	}
		
    
}
