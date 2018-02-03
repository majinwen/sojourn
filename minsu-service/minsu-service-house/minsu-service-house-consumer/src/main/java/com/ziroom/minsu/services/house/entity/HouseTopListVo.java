/**
 * @FileName: HouseTopListVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2017年3月17日 下午2:56:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseTopEntity;

/**
 * <p>top房源列表Vo</p>
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
public class HouseTopListVo extends HouseTopEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8830543676899880155L;
	
	/**
	 * 房源编号
	 */
	private String houseSn;
	/**
	 * 房源名称
	 */
	private String houseName;
	/**
	 * 城市code
	 */
	private String cityCode;
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 操作人姓名
	 */
	private String empName;
	
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
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
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
