/**
 * @FileName: HousePriceDto.java
 * @Package com.ziroom.minsu.services.house.issue.dto
 * 
 * @author bushujie
 * @created 2017年6月27日 上午10:22:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.dto;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.services.house.issue.vo.HouseBaseVo;
import com.ziroom.minsu.services.house.issue.vo.HouseRoomVo;

/**
 * <p>房源价格保存Dto</p>
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
public class HousePriceDto extends HouseBaseVo{

	/**
	 * 面积
	 */
	private Double houseArea;
	
	/**
	 * 选择的配套设施
	 */
	private String houseFacility;
	
	/**
	 * 基础价格
	 */
	private Double leasePrice;
	/**
	 * 清洁费
	 */
	private Double cleaningFees;
	/**
	 * 周末价格开关 0：关，1：开
	 */
	private Integer weekendPriceSwitch;
	/**
	 * 周末价格类型
	 */
	private String weekendPriceType;
	/**
	 * 周末价格值
	 */
	private Double weekendPriceVal;
	/**
	 * 长租折扣开关 0：关，1：开
	 */
	private Integer fullDayRateSwitch;
	
	/**
	 * 7天折扣值
	 */
	private Double sevenDiscountRate;
	
	/**
	 * 30天折扣值
	 */
	private Double thirtyDiscountRate;
	
	/**
	 * 入住人数限制
	 */
	private Integer checkInLimit;
	
	/**
	 * 户型
	 */
	private String houseModel;
	
	/**
	 * 房间信息和床信息
	 */
	private List<HouseRoomVo> houseRoomList=new ArrayList<HouseRoomVo>();
	/**
	 * 删除房间的FID
	 */
	private List<String> delRoomFidList = new ArrayList<>();
	
	/**
	 * 创建人fid
	 */
	private String createFid;
	
	/**
	 * 步骤
	 */
	private Integer step;	
	
	
	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	/**
	 * @return the createFid
	 */
	public String getCreateFid() {
		return createFid;
	}

	/**
	 * @param createFid the createFid to set
	 */
	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}

	/**
	 * @return the houseArea
	 */
	public Double getHouseArea() {
		return houseArea;
	}

	/**
	 * @param houseArea the houseArea to set
	 */
	public void setHouseArea(Double houseArea) {
		this.houseArea = houseArea;
	}

	/**
	 * @return the houseFacility
	 */
	public String getHouseFacility() {
		return houseFacility;
	}

	/**
	 * @param houseFacility the houseFacility to set
	 */
	public void setHouseFacility(String houseFacility) {
		this.houseFacility = houseFacility;
	}

	/**
	 * @return the leasePrice
	 */
	public Double getLeasePrice() {
		return leasePrice;
	}

	/**
	 * @param leasePrice the leasePrice to set
	 */
	public void setLeasePrice(Double leasePrice) {
		this.leasePrice = leasePrice;
	}

	/**
	 * @return the cleaningFees
	 */
	public Double getCleaningFees() {
		return cleaningFees;
	}

	/**
	 * @param cleaningFees the cleaningFees to set
	 */
	public void setCleaningFees(Double cleaningFees) {
		this.cleaningFees = cleaningFees;
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
	 * @return the weekendPriceType
	 */
	public String getWeekendPriceType() {
		return weekendPriceType;
	}

	/**
	 * @param weekendPriceType the weekendPriceType to set
	 */
	public void setWeekendPriceType(String weekendPriceType) {
		this.weekendPriceType = weekendPriceType;
	}

	/**
	 * @return the weekendPriceVal
	 */
	public Double getWeekendPriceVal() {
		return weekendPriceVal;
	}

	/**
	 * @param weekendPriceVal the weekendPriceVal to set
	 */
	public void setWeekendPriceVal(Double weekendPriceVal) {
		this.weekendPriceVal = weekendPriceVal;
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
	public Double getSevenDiscountRate() {
		return sevenDiscountRate;
	}

	/**
	 * @param sevenDiscountRate the sevenDiscountRate to set
	 */
	public void setSevenDiscountRate(Double sevenDiscountRate) {
		this.sevenDiscountRate = sevenDiscountRate;
	}

	/**
	 * @return the thirtyDiscountRate
	 */
	public Double getThirtyDiscountRate() {
		return thirtyDiscountRate;
	}

	/**
	 * @param thirtyDiscountRate the thirtyDiscountRate to set
	 */
	public void setThirtyDiscountRate(Double thirtyDiscountRate) {
		this.thirtyDiscountRate = thirtyDiscountRate;
	}

	/**
	 * @return the checkInLimit
	 */
	public Integer getCheckInLimit() {
		return checkInLimit;
	}

	/**
	 * @param checkInLimit the checkInLimit to set
	 */
	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	/**
	 * @return the houseModel
	 */
	public String getHouseModel() {
		return houseModel;
	}

	/**
	 * @param houseModel the houseModel to set
	 */
	public void setHouseModel(String houseModel) {
		this.houseModel = houseModel;
	}

	/**
	 * @return the houseRoomList
	 */
	public List<HouseRoomVo> getHouseRoomList() {
		return houseRoomList;
	}

	/**
	 * @param houseRoomList the houseRoomList to set
	 */
	public void setHouseRoomList(List<HouseRoomVo> houseRoomList) {
		this.houseRoomList = houseRoomList;
	}

	public List<String> getDelRoomFidList() {
		return delRoomFidList;
	}

	public void setDelRoomFidList(List<String> delRoomFidList) {
		this.delRoomFidList = delRoomFidList;
	}
}
