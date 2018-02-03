package com.ziroom.minsu.services.cms.entity;

import java.util.List;

import com.ziroom.minsu.entity.cms.ActivityApplyEntity;
import com.ziroom.minsu.entity.cms.ActivityApplyExtEntity;

public class ActivityApplyVo extends ActivityApplyEntity {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -8360941686860639742L;
	
	/** 扩展内容  */
	private List<ActivityApplyExtEntity> extList;
	
	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 区域名称
	 */
	private String areaName;
	
	/**
	 * 房东介绍
	 */
	private String customerIntroduce;
	
	/**
	 * 房源故事
	 */
	private String houseStory;
	
	/**
	 * 角色名称
	 */
    private String roleCodeName;

    /**
     * 申请状态名称
     */
	private String applyStatusName;

	
    public String getRoleCodeName() {
		return roleCodeName;
	}

	public String getApplyStatusName() {
		return applyStatusName;
	}

	public void setRoleCodeName(String roleCodeName) {
		this.roleCodeName = roleCodeName;
	}

	public void setApplyStatusName(String applyStatusName) {
		this.applyStatusName = applyStatusName;
	}

	public String getCustomerIntroduce() {
		return customerIntroduce;
	}

	public String getHouseStory() {
		return houseStory;
	}

	public void setCustomerIntroduce(String customerIntroduce) {
		this.customerIntroduce = customerIntroduce;
	}

	public void setHouseStory(String houseStory) {
		this.houseStory = houseStory;
	}

	public String getCityName() {
		return cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<ActivityApplyExtEntity> getExtList() {
		return extList;
	}

	public void setExtList(List<ActivityApplyExtEntity> extList) {
		this.extList = extList;
	}
	
}
