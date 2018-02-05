/**
 * @FileName: HouseEvaluate.java
 * @Package com.ziroom.minsu.api.house.dto
 * 
 * @author bushujie
 * @created 2016年5月3日 上午11:20:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>房源评价列表参数</p>
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
public class HouseEvaluate extends BaseParamDto{
	
	/**
	 * 房源或者房间fid
	 */
	@NotEmpty(message="{house.fid.null}")
	private String fid;
	
	 /**
	  * 出租方式
	  */
	@NotNull(message="house.rentway.null")
	private Integer rentWay;

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
}
