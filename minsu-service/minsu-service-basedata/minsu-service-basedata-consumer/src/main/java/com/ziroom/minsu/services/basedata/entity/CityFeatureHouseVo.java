/**
 * @FileName: CityFeatureHouseVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author zl
 * @created 2016年12月1日 下午6:10:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class CityFeatureHouseVo extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7957513110075501649L;

	/**
     * 城市code
     */
    private String cityCode;
    
    /**
     * 房源类型集合
     */
    private List<Integer> houseTypes;

	public String getCityCode() {
		return cityCode;
	}

	public List<Integer> getHouseTypes() {
		return houseTypes;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setHouseTypes(List<Integer> houseTypes) {
		this.houseTypes = houseTypes;
	} 

}
