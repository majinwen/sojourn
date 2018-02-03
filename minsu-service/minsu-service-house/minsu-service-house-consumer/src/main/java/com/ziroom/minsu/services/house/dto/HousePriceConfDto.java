/**
 * @FileName: HousePriceConfDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年4月5日 下午5:23:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HousePriceConfEntity;

/**
 * <p>特殊价格配置Dto</p>
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
public class HousePriceConfDto extends HousePriceConfEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7635405914602699570L;
	
	
	/**
	 * 0:整租，1：合租，2：床位
	 */
	private Integer rentWay;


    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }
}
