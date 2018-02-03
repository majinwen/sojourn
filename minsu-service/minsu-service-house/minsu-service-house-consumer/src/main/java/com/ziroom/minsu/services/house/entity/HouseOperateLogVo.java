package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseOperateLogEntity;

public class HouseOperateLogVo extends HouseOperateLogEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6955894876347227571L;
	
	// 审核人
	private String createName;

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
