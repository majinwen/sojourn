/**
 * @FileName: ToNightDiscountDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2017年5月15日 下午9:29:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.TonightDiscountEntity;

/**
 * <p>今日特价Dto</p>
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
public class ToNightDiscountDto extends TonightDiscountEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3595537991272327544L;
	
	/**
	 * 结束时间字符串
	 */
	private String endTimeStr;

	/**
	 * @return the endTimeStr
	 */
	public String getEndTimeStr() {
		return endTimeStr;
	}

	/**
	 * @param endTimeStr the endTimeStr to set
	 */
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}
