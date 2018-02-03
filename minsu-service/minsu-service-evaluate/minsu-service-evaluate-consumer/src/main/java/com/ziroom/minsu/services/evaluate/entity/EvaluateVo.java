/**
 * @FileName: EvaluateVo.java
 * @Package com.ziroom.minsu.services.evaluate.entity
 * 
 * @author yd
 * @created 2016年4月11日 下午2:03:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.entity;

import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;

/**
 * <p>评价管理公共实体VO</p>
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
public class EvaluateVo extends EvaluateOrderEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5815107222517182111L;
	
	/**
	 * 评价内容
	 */
	private String content;
	
	/**
	 * 评价人姓名
	 */
	private String evaUserName;
	
	/**
	 * 被评人姓名
	 */
	private String ratedUserName;


	/**
	 * 城市code
	 */
	private String cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 房源编号
	 */
	private String houseSn;

	/**
	 * 维护管家
	 */
	private String empGuardName;
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEvaUserName() {
		return evaUserName;
	}

	public void setEvaUserName(String evaUserName) {
		this.evaUserName = evaUserName;
	}

	public String getRatedUserName() {
		return ratedUserName;
	}

	public void setRatedUserName(String ratedUserName) {
		this.ratedUserName = ratedUserName;
	}


	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getEmpGuardName() {
		return empGuardName;
	}

	public void setEmpGuardName(String empGuardName) {
		this.empGuardName = empGuardName;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
}
