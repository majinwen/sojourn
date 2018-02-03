/**
 * @FileName: ColumnCityRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author bushujie
 * @created 2016年11月9日 下午2:15:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>城市专栏request</p>
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
public class ColumnCityRequest extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5500206065902036915L;
	
	/**
	 * 模板名称
	 */
	private String tempName;
	
	/**
	 * 专栏标题
	 */
	private String colTitle;
	
	/**
	 * 模板类型
	 */
	private Integer tempType;
	
	/**
	 * 城市code
	 */
	private String cityCode;
	
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
	 * @return the tempName
	 */
	public String getTempName() {
		return tempName;
	}

	/**
	 * @param tempName the tempName to set
	 */
	public void setTempName(String tempName) {
		this.tempName = tempName;
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

	/**
	 * @return the tempType
	 */
	public Integer getTempType() {
		return tempType;
	}

	/**
	 * @param tempType the tempType to set
	 */
	public void setTempType(Integer tempType) {
		this.tempType = tempType;
	}
}
