/**
 * @FileName: ColumnCityVo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author bushujie
 * @created 2016年11月9日 上午11:33:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

import com.ziroom.minsu.entity.cms.ColumnCityEntity;

/**
 * <p>城市专栏Vo</p>
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
public class ColumnCityVo extends ColumnCityEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -9188734833433123849L;
	
	/**
	 * 模板名称
	 */
	private String tempName;
	
	/**
	 * 创建人姓名
	 */
	private String empName;
	
	/**
	 * 模板类型
	 */
	private Integer tempType;

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
}
