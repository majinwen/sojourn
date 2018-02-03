/**
 * @FileName: ColumnRegionVo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author bushujie
 * @created 2016年11月10日 下午5:26:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

import com.ziroom.minsu.entity.cms.ColumnRegionEntity;

/**
 * <p>专栏景点商圈查询参数</p>
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
public class ColumnRegionVo extends ColumnRegionEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -728283733056725852L;
	
	/**
	 * 城市code
	 */
	private String cityCode;
	
	/**
	 * 专栏标题
	 */
	private String colTitle;
	
	/**
	 * 景点商圈名称
	 */
	private String regionName;
	
	/**
	 * 创建人姓名
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
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
	 * @return the colTitle
	 */
	public String getColTitle() {
		return colTitle;
	}

	/**
	 * @param colTitle the colTitle to set
	 */
	public void setColTitle(String colTitle) {
		this.colTitle = colTitle;
	}

}
