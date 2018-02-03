/**
 * @FileName: AsyAbHouseDto.java
 * @Package com.ziroom.minsu.services.house.airbnb.dto
 * 
 * @author yd
 * @created 2017年7月29日 下午12:41:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.airbnb.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.services.common.entity.CalendarDataVo;

/**
 * <p>TODO</p>
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
public class AsyAbHouseDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 7060107953007842293L;
	
	
	/**
	 * 待处理的日历
	 */
	private List<CalendarDataVo> calendarDataVos;
	
	/**
	 * airbnb房源编号
	 */
	private  String abSn;

	/**
	 * @return the calendarDataVos
	 */
	public List<CalendarDataVo> getCalendarDataVos() {
		return calendarDataVos;
	}

	/**
	 * @param calendarDataVos the calendarDataVos to set
	 */
	public void setCalendarDataVos(List<CalendarDataVo> calendarDataVos) {
		this.calendarDataVos = calendarDataVos;
	}

	/**
	 * @return the abSn
	 */
	public String getAbSn() {
		return abSn;
	}

	/**
	 * @param abSn the abSn to set
	 */
	public void setAbSn(String abSn) {
		this.abSn = abSn;
	}
	
	

}
