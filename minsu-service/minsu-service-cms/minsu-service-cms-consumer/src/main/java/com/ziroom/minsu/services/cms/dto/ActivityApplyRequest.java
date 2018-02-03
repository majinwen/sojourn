package com.ziroom.minsu.services.cms.dto;

import java.util.List;

import com.ziroom.minsu.entity.cms.ActivityApplyEntity;

public class ActivityApplyRequest extends ActivityApplyEntity {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -8360941686860639742L;

	private List<String> houseUrlList;

	public List<String> getHouseUrlList() {
		return houseUrlList;
	}

	public void setHouseUrlList(List<String> houseUrlList) {
		this.houseUrlList = houseUrlList;
	}

}
