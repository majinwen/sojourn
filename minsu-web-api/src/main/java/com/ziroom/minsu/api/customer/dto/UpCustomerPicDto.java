/**
 * @FileName: UpCustomerPicDto.java
 * @Package com.ziroom.minsu.api.customer.dto
 * 
 * @author bushujie
 * @created 2016年4月23日 下午5:56:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.customer.dto;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>客户图片上传接口</p>
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
public class UpCustomerPicDto extends BaseParamDto{
	
	@NotNull(message="{picType.null}")
	private Integer picType;
	
	/**
	 * @return the picType
	 */
	public Integer getPicType() {
		return picType;
	}

	/**
	 * @param picType the picType to set
	 */
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
}
