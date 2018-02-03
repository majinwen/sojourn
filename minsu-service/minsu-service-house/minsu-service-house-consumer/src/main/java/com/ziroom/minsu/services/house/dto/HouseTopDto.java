/**
 * @FileName: HouseTopDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2017年3月17日 下午3:40:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>top房源分页列表参数</p>
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
public class HouseTopDto extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -571722752628684003L;
	
	/**
	 * 房源名称
	 */
	private String houseName;
	/**
	 * 城市code
	 */
	private String cityCode;
	/**
	 * 房源编号
	 */
	private String houseSn;
	/**
	 * top房源状态
	 */
	private Integer topStatus;
	/**
	 * 查询开始时间
	 */
	private String startDate;
	
	/**
	 * 查询结束时间
	 */
	private String endDate;
	
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the houseSn
	 */
	public String getHouseSn() {
		return houseSn;
	}

	/**
	 * @param houseSn the houseSn to set
	 */
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	/**
	 * @return the topStatus
	 */
	public Integer getTopStatus() {
		return topStatus;
	}

	/**
	 * @param topStatus the topStatus to set
	 */
	public void setTopStatus(Integer topStatus) {
		this.topStatus = topStatus;
	}
}
