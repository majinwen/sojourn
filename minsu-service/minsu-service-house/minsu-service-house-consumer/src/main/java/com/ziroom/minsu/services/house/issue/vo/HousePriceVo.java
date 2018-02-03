/**
 * @FileName: HousePriceVo.java
 * @Package com.ziroom.minsu.services.house.issue.vo
 * 
 * @author bushujie
 * @created 2017年6月15日 上午9:51:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.vo;

import com.ziroom.minsu.services.common.entity.FieldSelectListVo;
import com.ziroom.minsu.services.common.entity.FieldTextValueVo;
import com.ziroom.minsu.services.common.entity.FieldTextVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>发布房源2-2Vo( 整租)</p>
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
public class HousePriceVo extends HouseBaseVo{
	
	/**
	 * 房源面积
	 */
	private FieldTextValueVo<Integer> houseArea;
	
	/**
	 * 配套设施
	 */
	private FieldTextValueVo<String> houseFacility;
	
	/**
	 * 分组配套设施
	 */
	private List<Map<String, Object>> groupFacilityList;
	
	/**
	 * 房源基础价格
	 */
	private FieldTextVo<Integer> leasePrice=new FieldTextVo<Integer>(true, 0);
	
	/**
	 * 房源基础价格最小值
	 */
	private Integer minPrice;
	
	/**
	 * 房源基础价格最大值
	 */
	private Integer maxPrice;
	
	/**
	 * 清洁费
	 */
	private FieldTextVo<Integer> cleaningFees=new FieldTextVo<Integer>(true, 0);
	
	/**
	 * 清洁费最大比例
	 */
	private Integer cleaningFeesPer;
	
	/**
	 * 周末价格列表
	 */
	private FieldSelectListVo weekendList=new FieldSelectListVo();
	
	/**
	 * 周末价格开关 0：关，1：开
	 */
	private Integer weekendPriceSwitch;
	
	/**
	 * 周末价格值
	 */
	private FieldTextVo<Integer> weekendPrice=new FieldTextVo<Integer>(true, 0);
	
	/**
	 * 长租折扣开关 0：关，1：开
	 */
	private Integer fullDayRateSwitch;
	
	/**
	 *  7天折扣
	 */
	private FieldTextVo<Double> sevenDiscountRate=new FieldTextVo<Double>(true, 0d);
	
	/**
	 * 30天折扣
	 */
	private FieldTextVo<Double> thirtyDiscountRate=new FieldTextVo<Double>(true, 0d);
	
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
	 * 最大床数量限制
	 */
	private Integer maxBedNumLimit;
	
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
	/**
	 * @return the roomNum
	 */
	public Integer getRoomNum() {
		return roomNum;
	}
	/**
	 * @param roomNum the roomNum to set
	 */
	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}
	/**
	 * @return the parlorNum
	 */
	public Integer getParlorNum() {
		return parlorNum;
	}
	/**
	 * @param parlorNum the parlorNum to set
	 */
	public void setParlorNum(Integer parlorNum) {
		this.parlorNum = parlorNum;
	}
	/**
	 * @return the toiletNum
	 */
	public Integer getToiletNum() {
		return toiletNum;
	}
	/**
	 * @param toiletNum the toiletNum to set
	 */
	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}
	/**
	 * @return the kitchenNum
	 */
	public Integer getKitchenNum() {
		return kitchenNum;
	}
	/**
	 * @param kitchenNum the kitchenNum to set
	 */
	public void setKitchenNum(Integer kitchenNum) {
		this.kitchenNum = kitchenNum;
	}
	/**
	 * @return the balconyNum
	 */
	public Integer getBalconyNum() {
		return balconyNum;
	}
	/**
	 * @param balconyNum the balconyNum to set
	 */
	public void setBalconyNum(Integer balconyNum) {
		this.balconyNum = balconyNum;
	}
	/**
	 * @return the houseArea
	 */
	public FieldTextValueVo<Integer> getHouseArea() {
		return houseArea;
	}
	/**
	 * @param houseArea the houseArea to set
	 */
	public void setHouseArea(FieldTextValueVo<Integer> houseArea) {
		this.houseArea = houseArea;
	}
	/**
	 * @return the houseFacility
	 */
	public FieldTextValueVo<String> getHouseFacility() {
		return houseFacility;
	}
	/**
	 * @param houseFacility the houseFacility to set
	 */
	public void setHouseFacility(FieldTextValueVo<String> houseFacility) {
		this.houseFacility = houseFacility;
	}
	/**
	 * @return the checkInLimit
	 */
	public FieldSelectListVo getCheckInLimit() {
		return checkInLimit;
	}
	/**
	 * @param checkInLimit the checkInLimit to set
	 */
	public void setCheckInLimit(FieldSelectListVo checkInLimit) {
		this.checkInLimit = checkInLimit;
	}
	/**
	 * @return the houseModel
	 */
	public FieldTextValueVo<String> getHouseModel() {
		return houseModel;
	}
	/**
	 * @param houseModel the houseModel to set
	 */
	public void setHouseModel(FieldTextValueVo<String> houseModel) {
		this.houseModel = houseModel;
	}
	/**
	 * @return the maxRoom
	 */
	public Integer getMaxRoom() {
		return maxRoom;
	}
	/**
	 * @param maxRoom the maxRoom to set
	 */
	public void setMaxRoom(Integer maxRoom) {
		this.maxRoom = maxRoom;
	}
	/**
	 * @return the maxParlor
	 */
	public Integer getMaxParlor() {
		return maxParlor;
	}
	/**
	 * @param maxParlor the maxParlor to set
	 */
	public void setMaxParlor(Integer maxParlor) {
		this.maxParlor = maxParlor;
	}
	/**
	 * @return the maxToilet
	 */
	public Integer getMaxToilet() {
		return maxToilet;
	}
	/**
	 * @param maxToilet the maxToilet to set
	 */
	public void setMaxToilet(Integer maxToilet) {
		this.maxToilet = maxToilet;
	}
	/**
	 * @return the maxKitchen
	 */
	public Integer getMaxKitchen() {
		return maxKitchen;
	}
	/**
	 * @param maxKitchen the maxKitchen to set
	 */
	public void setMaxKitchen(Integer maxKitchen) {
		this.maxKitchen = maxKitchen;
	}
	/**
	 * @return the maxBalcony
	 */
	public Integer getMaxBalcony() {
		return maxBalcony;
	}
	/**
	 * @param maxBalcony the maxBalcony to set
	 */
	public void setMaxBalcony(Integer maxBalcony) {
		this.maxBalcony = maxBalcony;
	}

	/**
	 * @return the groupFacilityList
	 */
	public List<Map<String, Object>> getGroupFacilityList() {
		return groupFacilityList;
	}
	/**
	 * @param groupFacilityList the groupFacilityList to set
	 */
	public void setGroupFacilityList(List<Map<String, Object>> groupFacilityList) {
		this.groupFacilityList = groupFacilityList;
	}
	/**
	 * @return the leasePrice
	 */
	public FieldTextVo<Integer> getLeasePrice() {
		return leasePrice;
	}
	/**
	 * @param leasePrice the leasePrice to set
	 */
	public void setLeasePrice(FieldTextVo<Integer> leasePrice) {
		this.leasePrice = leasePrice;
	}
	/**
	 * @return the minPrice
	 */
	public Integer getMinPrice() {
		return minPrice;
	}
	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	/**
	 * @return the maxPrice
	 */
	public Integer getMaxPrice() {
		return maxPrice;
	}
	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}
	/**
	 * @return the cleaningFees
	 */
	public FieldTextVo<Integer> getCleaningFees() {
		return cleaningFees;
	}
	/**
	 * @param cleaningFees the cleaningFees to set
	 */
	public void setCleaningFees(FieldTextVo<Integer> cleaningFees) {
		this.cleaningFees = cleaningFees;
	}
	/**
	 * @return the cleaningFeesPer
	 */
	public Integer getCleaningFeesPer() {
		return cleaningFeesPer;
	}
	/**
	 * @param cleaningFeesPer the cleaningFeesPer to set
	 */
	public void setCleaningFeesPer(Integer cleaningFeesPer) {
		this.cleaningFeesPer = cleaningFeesPer;
	}
	/**
	 * @return the weekendPriceSwitch
	 */
	public Integer getWeekendPriceSwitch() {
		return weekendPriceSwitch;
	}
	/**
	 * @param weekendPriceSwitch the weekendPriceSwitch to set
	 */
	public void setWeekendPriceSwitch(Integer weekendPriceSwitch) {
		this.weekendPriceSwitch = weekendPriceSwitch;
	}
	/**
	 * @return the fullDayRateSwitch
	 */
	public Integer getFullDayRateSwitch() {
		return fullDayRateSwitch;
	}
	/**
	 * @param fullDayRateSwitch the fullDayRateSwitch to set
	 */
	public void setFullDayRateSwitch(Integer fullDayRateSwitch) {
		this.fullDayRateSwitch = fullDayRateSwitch;
	}
	/**
	 * @return the sevenDiscountRate
	 */
	public FieldTextVo<Double> getSevenDiscountRate() {
		return sevenDiscountRate;
	}
	/**
	 * @param sevenDiscountRate the sevenDiscountRate to set
	 */
	public void setSevenDiscountRate(FieldTextVo<Double> sevenDiscountRate) {
		this.sevenDiscountRate = sevenDiscountRate;
	}
	/**
	 * @return the thirtyDiscountRate
	 */
	public FieldTextVo<Double> getThirtyDiscountRate() {
		return thirtyDiscountRate;
	}
	/**
	 * @param thirtyDiscountRate the thirtyDiscountRate to set
	 */
	public void setThirtyDiscountRate(FieldTextVo<Double> thirtyDiscountRate) {
		this.thirtyDiscountRate = thirtyDiscountRate;
	}
	
	/**
	 * @return the bedTypeList
	 */
	public FieldSelectListVo getBedTypeList() {
		return bedTypeList;
	}
	/**
	 * @param bedTypeList the bedTypeList to set
	 */
	public void setBedTypeList(FieldSelectListVo bedTypeList) {
		this.bedTypeList = bedTypeList;
	}
	
	/**
	 * @return the houseRoomList
	 */
	public List<Map<String, Object>> getHouseRoomList() {
		return houseRoomList;
	}
	/**
	 * @param houseRoomList the houseRoomList to set
	 */
	public void setHouseRoomList(List<Map<String, Object>> houseRoomList) {
		this.houseRoomList = houseRoomList;
	}
	/**
	 * @return the weekendList
	 */
	public FieldSelectListVo getWeekendList() {
		return weekendList;
	}
	/**
	 * @param weekendList the weekendList to set
	 */
	public void setWeekendList(FieldSelectListVo weekendList) {
		this.weekendList = weekendList;
	}
	
	/**
	 * @return the weekendPrice
	 */
	public FieldTextVo<Integer> getWeekendPrice() {
		return weekendPrice;
	}
	/**
	 * @param weekendPrice the weekendPrice to set
	 */
	public void setWeekendPrice(FieldTextVo<Integer> weekendPrice) {
		this.weekendPrice = weekendPrice;
	}
	
	/**
	 * @return the maxBedNumLimit
	 */
	public Integer getMaxBedNumLimit() {
		return maxBedNumLimit;
	}
	/**
	 * @param maxBedNumLimit the maxBedNumLimit to set
	 */
	public void setMaxBedNumLimit(Integer maxBedNumLimit) {
		this.maxBedNumLimit = maxBedNumLimit;
	}
}
