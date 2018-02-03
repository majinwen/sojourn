/**
 * @FileName: CalendarMsgVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月4日 上午12:27:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>出租日历Vo</p>
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
public class LeaseCalendarVo {
	
	/**
	 * 房源或房间普通价格
	 */
	private Integer UsualPrice;
	
	/**
	 * 房源或房间特殊价格集合
	 */
	private List<SpecialPriceVo> specialPriceList=new ArrayList<SpecialPriceVo>();
	
	/**
	 * 房源或房间已出租日期集合
	 */
	private List<Date> leaseDateList=new ArrayList<Date>();
	
	/**
	 * 房源或房间设置为不可租日期集合
	 */
	private List<Date> setLeaseDateList=new ArrayList<Date>();
	
	/**
	 * 截止日期
	 */
	private Date tillDate;
	
	/**
	 * @return the tillDate
	 */
	public Date getTillDate() {
		return tillDate;
	}

	/**
	 * @param tillDate the tillDate to set
	 */
	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}
	
	/**
	 * @return the usualPrice
	 */
	public Integer getUsualPrice() {
		return UsualPrice;
	}

	/**
	 * @param usualPrice the usualPrice to set
	 */
	public void setUsualPrice(Integer usualPrice) {
		UsualPrice = usualPrice;
	}

	/**
	 * @return the specialPriceList
	 */
	public List<SpecialPriceVo> getSpecialPriceList() {
		return specialPriceList;
	}

	/**
	 * @param specialPriceList the specialPriceList to set
	 */
	public void setSpecialPriceList(List<SpecialPriceVo> specialPriceList) {
		this.specialPriceList = specialPriceList;
	}

	/**
	 * @return the leaseDateList
	 */
	public List<Date> getLeaseDateList() {
		return leaseDateList;
	}

	/**
	 * @param leaseDateList the leaseDateList to set
	 */
	public void setLeaseDateList(List<Date> leaseDateList) {
		this.leaseDateList = leaseDateList;
	}

	/**
	 * @return the setLeaseDateList
	 */
	public List<Date> getSetLeaseDateList() {
		return setLeaseDateList;
	}

	/**
	 * @param setLeaseDateList the setLeaseDateList to set
	 */
	public void setSetLeaseDateList(List<Date> setLeaseDateList) {
		this.setLeaseDateList = setLeaseDateList;
	}
}
