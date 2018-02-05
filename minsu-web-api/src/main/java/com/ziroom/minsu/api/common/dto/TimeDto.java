/**
 * @FileName: BaseParamDto.java
 * @Package com.ziroom.minsu.api.common.dto
 * 
 * @author bushujie
 * @created 2016年4月20日 下午8:34:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>返回时间参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class TimeDto {
	
	private String monthAndDay;
	
	private String year;
	
	private Integer month;

	private Integer day;
	
	private List<String> time = new ArrayList<>();

	{
		time.add("09:00");
		time.add("10:00");
		time.add("11:00");
		time.add("12:00");
		time.add("14:00");
		time.add("15:00");
		time.add("16:00");
		time.add("17:00");
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonthAndDay() {
		return monthAndDay;
	}

	public void setMonthAndDay(String monthAndDay) {
		this.monthAndDay = monthAndDay;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public List<String> getTime() {
		return time;
	}

	public void setTime(List<String> time) {
		this.time = time;
	}
	
	
}
