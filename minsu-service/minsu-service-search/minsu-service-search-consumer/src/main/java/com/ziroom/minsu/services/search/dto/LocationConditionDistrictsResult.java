package com.ziroom.minsu.services.search.dto;

/**
 * <p>位置条件搜索-行政区出参</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月23日
 * @since 1.0
 * @version 1.0
 */
public class LocationConditionDistrictsResult {

	/**
	 * 行政区code
	 */
	String code;
	
	/**
	 * 行政区名称
	 */
	String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
