/**
 * @FileName: SmsTemplateRequest.java
 * @Package com.ziroom.minsu.services.basedata.dto
 * 
 * @author yd
 * @created 2016年4月1日 下午3:00:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;

import java.io.Serializable;

import com.ziroom.minsu.services.common.dto.PageRequest;
/**
 * 
 * @author jixd 
 *
 */
public class WeatherDayRequest implements Serializable{
	
	private static final long serialVersionUID = -7875221833530399461L;
	/*城市名称*/
	private String cityName;
	/*查询日期*/
	private String date;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
