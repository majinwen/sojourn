/**
 * @FileName: CarouselTypeDto.java
 * @Package com.ziroom.minsu.activity.dto
 * 
 * @author bushujie
 * @created 2016年5月13日 下午5:47:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.search.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>轮播图标示</p>
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
public class CarouselTypeRequest {
	
	/**
	 * 轮播图类型
	 */
	private String carouseType;
	
	/**
	 * 轮播图数据fid
	 */
	private List<HouseSearchRequest> carouselDtoList=new ArrayList<HouseSearchRequest>();
	
	/**
	 * @return the carouseType
	 */
	public String getCarouseType() {
		return carouseType;
	}

	/**
	 * @param carouseType the carouseType to set
	 */
	public void setCarouseType(String carouseType) {
		this.carouseType = carouseType;
	}

	/**
	 * @return the carouselDtoList
	 */
	public List<HouseSearchRequest> getCarouselDtoList() {
		return carouselDtoList;
	}

	/**
	 * @param carouselDtoList the carouselDtoList to set
	 */
	public void setCarouselDtoList(List<HouseSearchRequest> carouselDtoList) {
		this.carouselDtoList = carouselDtoList;
	}
}
