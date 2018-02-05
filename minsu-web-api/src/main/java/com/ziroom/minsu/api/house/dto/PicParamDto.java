/**
 * @FileName: HousePicDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年4月9日 下午5:36:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.dto;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.api.common.dto.BaseParamDto;


/**
 * <p>房源图片dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class PicParamDto extends BaseParamDto{
	
	/**
	 * 房源逻辑id
	 */
	@NotNull(message="{house.base.fid.null}")
	private String houseBaseFid;

	/**
	 * 房间逻辑id
	 */
	private String houseRoomFid;
	
	/**
	 * 图片类型 @HousePicTypeEnum
	 */
	@NotNull(message="{house.pic.type.null}")
	private Integer picType;

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}
}
