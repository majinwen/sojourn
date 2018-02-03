/**
 * @FileName: HouseBaseListDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年4月2日 上午11:51:29
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/**
 * <p>设置特殊价格参数,灵活定价，间隙价格，折扣率</p>
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
public class SpecialPriceDto {

	//房源逻辑id
	private String houseBaseFid;

	//房间逻辑id
	private String houseRoomFid;

	//出租方式
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;

	//房源日价格
	private Integer leasePrice;

	//房间价格
	private Integer roomPrice;

	//是否打开间隙价格灵活定价开关(打开开关，将在数据库中插入3条定价配置，is_del字段置为2)默认关闭
	private Integer gapAndFlexiblePrice = 0;
	
	//是否开启今日特惠折扣
	private Integer dayDiscount;

	//是否开启空置间夜自动折扣
	private Integer flexDiscount;

	//满天折扣率开关(默认关闭)
	private Integer fullDayRate = 0  ;

	//满7天折扣率
	private Integer sevenDiscountRate;

	//满7天折扣率配置表fid
	private String sevenFid;

	//满30天折扣率
	private Integer thirtyDiscountRate;

	//满30天折扣率配置表fid
	private String thirtyFid;

	//设置房源价格时间list
	private List<String> setTime = new ArrayList<String>();
	
	//特殊价格
	@NotNull(message="{house.special.price.null}")
	@Min(value = 1,message="{house.special.price.min}")
	private Integer specialPrice;
	
	//日历开始时间
	private String startDate;
	
	//日历结束时间
	private String endDate;
	
	//周末价格开关 0:关闭 1:开启
	private Integer weekendPriceSwitch = 0;
	
	//是否设置周末价格标示 0:未设置 1:已设置有效 2:已设置无效
	private Integer weekendPriceFlag = 0;

	public Integer getFullDayRate() {
		return fullDayRate;
	}

	public void setFullDayRate(Integer fullDayRate) {
		this.fullDayRate = fullDayRate;
	}

	public Integer getLeasePrice() {
		return leasePrice;
	}

	public void setLeasePrice(Integer leasePrice) {
		this.leasePrice = leasePrice;
	}

	public Integer getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(Integer roomPrice) {
		this.roomPrice = roomPrice;
	}

	public String getSevenFid() {
		return sevenFid;
	}

	public void setSevenFid(String sevenFid) {
		this.sevenFid = sevenFid;
	}

	public String getThirtyFid() {
		return thirtyFid;
	}

	public void setThirtyFid(String thirtyFid) {
		this.thirtyFid = thirtyFid;
	}

	public Integer getGapAndFlexiblePrice() {
		return gapAndFlexiblePrice;
	}

	public void setGapAndFlexiblePrice(Integer gapAndFlexiblePrice) {
		this.gapAndFlexiblePrice = gapAndFlexiblePrice;
	}

	public Integer getSevenDiscountRate() {
		return sevenDiscountRate;
	}

	public void setSevenDiscountRate(Integer sevenDiscountRate) {
		this.sevenDiscountRate = sevenDiscountRate;
	}

	public Integer getThirtyDiscountRate() {
		return thirtyDiscountRate;
	}

	public void setThirtyDiscountRate(Integer thirtyDiscountRate) {
		this.thirtyDiscountRate = thirtyDiscountRate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

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

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public List<String> getSetTime() {
		return setTime;
	}

	public void setSetTime(List<String> setTime) {
		this.setTime = setTime;
	}

	public Integer getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Integer specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Integer getWeekendPriceSwitch() {
		return weekendPriceSwitch;
	}

	public void setWeekendPriceSwitch(Integer weekendPriceSwitch) {
		this.weekendPriceSwitch = weekendPriceSwitch;
	}

	public Integer getWeekendPriceFlag() {
		return weekendPriceFlag;
	}

	public void setWeekendPriceFlag(Integer weekendPriceFlag) {
		this.weekendPriceFlag = weekendPriceFlag;
	}

	/**
	 * 
	 * 是否执行插入操作
	 *
	 * @author liujun
	 * @created 2016年12月7日
	 *
	 * @return
	 */
	@JsonIgnore
	public boolean isInsert() {
		return weekendPriceSwitch == 1;
	}
	
	/**
	 * 
	 * 是否执行更新操作
	 *
	 * @author liujun
	 * @created 2016年12月7日
	 *
	 * @return
	 */
	@JsonIgnore
	public boolean isUpdate() {
		return weekendPriceFlag == 1 && weekendPriceSwitch == 0;
	}
	
	/**
	 * @return the dayDiscount
	 */
	public Integer getDayDiscount() {
		return dayDiscount;
	}

	/**
	 * @param dayDiscount the dayDiscount to set
	 */
	public void setDayDiscount(Integer dayDiscount) {
		this.dayDiscount = dayDiscount;
	}

	/**
	 * @return the flexDiscount
	 */
	public Integer getFlexDiscount() {
		return flexDiscount;
	}

	/**
	 * @param flexDiscount the flexDiscount to set
	 */
	public void setFlexDiscount(Integer flexDiscount) {
		this.flexDiscount = flexDiscount;
	}
}
