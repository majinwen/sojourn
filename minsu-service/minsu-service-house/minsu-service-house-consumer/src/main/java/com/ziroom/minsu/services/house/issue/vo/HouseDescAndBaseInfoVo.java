/**
 * @FileName: HouseDescAndBaseInfoVo.java
 * @Package com.ziroom.minsu.services.house.issue.vo
 * 
 * @author lusp
 * @created 2017年6月28日 上午9:51:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.vo;

import com.ziroom.minsu.services.common.entity.FieldSelectListVo;
import com.ziroom.minsu.services.common.entity.FieldTextValueVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>房源描述及基础信息vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class HouseDescAndBaseInfoVo extends HouseBaseVo{


	/**
	 * 房源名称
	 */
	private FieldTextValueVo<String> houseName;
	/**
	 * 房源描述
	 */
	private FieldTextValueVo<String> houseDesc;
	/**
	 * 房源周边情况
	 */
	private FieldTextValueVo<String> houseAroundDesc;

	/**
	 * 房源面积
	 */
	private FieldTextValueVo<Double> houseArea;

	/**
	 * 配套设施
	 */
	private FieldTextValueVo<String> houseFacility;

	/**
	 * 分组配套设施
	 */
	private List<Map<String, Object>> groupFacilityList;

	/**
	 * 入住人数限制
	 */
	private FieldSelectListVo checkInLimit=new FieldSelectListVo();

	/**
	 * 房源户型
	 */
	private FieldTextValueVo<String> houseModel;

	/**
	 * 房间列表
	 */
	private List<Map<String, Object>> houseRoomList=new ArrayList<Map<String,Object>>();

	/**
	 * 床类型列表
	 */
	private FieldSelectListVo bedTypeList= new FieldSelectListVo();

	/**
	 * 最大房间数量
	 */
	private Integer maxRoom;

	/**
	 * 最大客厅数量
	 */
	private Integer maxParlor;
	/**
	 * 最大厕所数量
	 */
	private Integer maxToilet;
	/**
	 * 最大厨房数量
	 */
	private Integer maxKitchen;
	/**
	 * 最大阳台数量
	 */
	private Integer maxBalcony;

	/**
	 * 卧室数量
	 */
	private Integer roomNum;
	/**
	 * 客厅数量
	 */
	private Integer parlorNum;
	/**
	 * 厕所数量
	 */
	private Integer toiletNum;
	/**
	 * 厨房数量
	 */
	private Integer kitchenNum;
	/**
	 * 阳台数量
	 */
	private Integer balconyNum;

	public FieldTextValueVo<String> getHouseName() {
		return houseName;
	}

	public void setHouseName(FieldTextValueVo<String> houseName) {
		this.houseName = houseName;
	}

	public FieldTextValueVo<String> getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(FieldTextValueVo<String> houseDesc) {
		this.houseDesc = houseDesc;
	}

	public FieldTextValueVo<String> getHouseAroundDesc() {
		return houseAroundDesc;
	}

	public void setHouseAroundDesc(FieldTextValueVo<String> houseAroundDesc) {
		this.houseAroundDesc = houseAroundDesc;
	}

	public FieldTextValueVo<Double> getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(FieldTextValueVo<Double> houseArea) {
		this.houseArea = houseArea;
	}

	public FieldTextValueVo<String> getHouseFacility() {
		return houseFacility;
	}

	public void setHouseFacility(FieldTextValueVo<String> houseFacility) {
		this.houseFacility = houseFacility;
	}

	public List<Map<String, Object>> getGroupFacilityList() {
		return groupFacilityList;
	}

	public void setGroupFacilityList(List<Map<String, Object>> groupFacilityList) {
		this.groupFacilityList = groupFacilityList;
	}

	public FieldSelectListVo getCheckInLimit() {
		return checkInLimit;
	}

	public void setCheckInLimit(FieldSelectListVo checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	public FieldTextValueVo<String> getHouseModel() {
		return houseModel;
	}

	public void setHouseModel(FieldTextValueVo<String> houseModel) {
		this.houseModel = houseModel;
	}

	public List<Map<String, Object>> getHouseRoomList() {
		return houseRoomList;
	}

	public void setHouseRoomList(List<Map<String, Object>> houseRoomList) {
		this.houseRoomList = houseRoomList;
	}

	public FieldSelectListVo getBedTypeList() {
		return bedTypeList;
	}

	public void setBedTypeList(FieldSelectListVo bedTypeList) {
		this.bedTypeList = bedTypeList;
	}

	public Integer getMaxRoom() {
		return maxRoom;
	}

	public void setMaxRoom(Integer maxRoom) {
		this.maxRoom = maxRoom;
	}

	public Integer getMaxParlor() {
		return maxParlor;
	}

	public void setMaxParlor(Integer maxParlor) {
		this.maxParlor = maxParlor;
	}

	public Integer getMaxToilet() {
		return maxToilet;
	}

	public void setMaxToilet(Integer maxToilet) {
		this.maxToilet = maxToilet;
	}

	public Integer getMaxKitchen() {
		return maxKitchen;
	}

	public void setMaxKitchen(Integer maxKitchen) {
		this.maxKitchen = maxKitchen;
	}

	public Integer getMaxBalcony() {
		return maxBalcony;
	}

	public void setMaxBalcony(Integer maxBalcony) {
		this.maxBalcony = maxBalcony;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getParlorNum() {
		return parlorNum;
	}

	public void setParlorNum(Integer parlorNum) {
		this.parlorNum = parlorNum;
	}

	public Integer getToiletNum() {
		return toiletNum;
	}

	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}

	public Integer getKitchenNum() {
		return kitchenNum;
	}

	public void setKitchenNum(Integer kitchenNum) {
		this.kitchenNum = kitchenNum;
	}

	public Integer getBalconyNum() {
		return balconyNum;
	}

	public void setBalconyNum(Integer balconyNum) {
		this.balconyNum = balconyNum;
	}
}
