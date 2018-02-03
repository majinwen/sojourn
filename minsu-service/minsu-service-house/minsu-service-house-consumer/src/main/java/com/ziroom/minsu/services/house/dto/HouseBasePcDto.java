/**
 * @FileName: HouseBasePcDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年8月9日 下午7:19:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;

/**
 * <p>pc发布房源基本信息dto</p>
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
public class HouseBasePcDto extends HouseBaseMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6766811574635571552L;
	
	/**
	 * 入住人数限制
	 */
	private Integer checkInLimit;
	
	/**
	 * 截止日期
	 */
	private String dateLimit;

	/**
	 * 日价格
	 */
	private Double dayPrice;
	
	/**
	 * 清洁费
	 */
	private Double clearPrice;
	
	/**
	 * 是否与房东同住
	 */
	private Integer isTogetherLandlord;
	/**
	 * @return the isTogetherLandlord
	 */
	public Integer getIsTogetherLandlord() {
		return isTogetherLandlord;
	}

	/**
	 * @param isTogetherLandlord the isTogetherLandlord to set
	 */
	public void setIsTogetherLandlord(Integer isTogetherLandlord) {
		this.isTogetherLandlord = isTogetherLandlord;
	}

	/**
	 * @return the checkInLimit
	 */
	public Integer getCheckInLimit() {
		return checkInLimit;
	}

	/**
	 * @param checkInLimit the checkInLimit to set
	 */
	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	/**
	 * @return the dateLimit
	 */
	public String getDateLimit() {
		return dateLimit;
	}

	/**
	 * @param dateLimit the dateLimit to set
	 */
	public void setDateLimit(String dateLimit) {
		this.dateLimit = dateLimit;
	}

	/**
	 * @return the dayPrice
	 */
	public Double getDayPrice() {
		return dayPrice;
	}

	/**
	 * @param dayPrice the dayPrice to set
	 */
	public void setDayPrice(Double dayPrice) {
		this.dayPrice = dayPrice;
	}
	
	/**
	 * @return the clearPrice
	 */
	public Double getClearPrice() {
		return clearPrice;
	}

	/**
	 * @param clearPrice the clearPrice to set
	 */
	public void setClearPrice(Double clearPrice) {
		this.clearPrice = clearPrice;
	}
}
