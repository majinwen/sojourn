/**
 * @FileName: CurrentuserCityVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author bushujie
 * @created 2016年10月24日 下午3:48:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;

/**
 * <p>用户负责区域Vo</p>
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
public class CurrentuserCityVo extends CurrentuserCityEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4690469153520266167L;
	
	/**
	 * 国家名称
	 */
	private String nationName;
	/**
	 * 省名称
	 */
	private String provinceName;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * @return the nationName
	 */
	public String getNationName() {
		return nationName;
	}
	/**
	 * @param nationName the nationName to set
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
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
	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
