/**
 * @FileName: CalendarMonth.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年5月13日 上午1:29:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>日历月份对象</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CalendarMonth {
	
	/**
	 * 月份
	 */
	private String monthStr;
	
	/**
	 * 包含日期
	 */
	private List<CalendarResponseVo> calendarList=new ArrayList<CalendarResponseVo>();
	
	/**
	 * @return the monthStr
	 */
	public String getMonthStr() {
		return monthStr;
	}

	/**
	 * @param monthStr the monthStr to set
	 */
	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	/**
	 * @return the calendarList
	 */
	public List<CalendarResponseVo> getCalendarList() {
		return calendarList;
	}

	/**
	 * @param calendarList the calendarList to set
	 */
	public void setCalendarList(List<CalendarResponseVo> calendarList) {
		this.calendarList = calendarList;
	}

}
