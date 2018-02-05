/**
 * @FileName: HouseBaseListDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年4月2日 上午11:51:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>房源列表查询参数</p>
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
public class RoomParamDto extends BaseParamDto {

	// 房源状态 @HouseStatusEnum
	private Integer houseStatus;
	
	// 房源物理逻辑id
	private String housePhyFid;

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public String getHousePhyFid() {
		return housePhyFid;
	}

	public void setHousePhyFid(String housePhyFid) {
		this.housePhyFid = housePhyFid;
	}
}
