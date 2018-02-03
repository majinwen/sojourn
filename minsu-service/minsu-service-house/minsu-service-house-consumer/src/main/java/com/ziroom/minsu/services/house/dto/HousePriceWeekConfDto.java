package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;


/**
 * 
 * @author zl
 * @created 2016年9月9日 
 */
public class HousePriceWeekConfDto implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1148235971200363404L;

	/**
	 * 房源或者房间逻辑id
	 */ 
	private String houseBaseFid;
	
	/**
	 * 房间信息表逻辑id
	 */
	private String houseRoomFid;

	/**
	 * 配置星期
	 */
	private Set<Integer> setWeeks;

	/**
	 * 设置价格
	 */
	private Integer priceVal; 

	/**
	 * 创建人uid
	 */
	private String createUid; 
	
	/**
	 * 某天
	 */
	private Date day;
	
	/**
	 * 0:整租，1：合租，2：床位
	 */
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;
	
	/**
	 * 日历开始时间
	 */
	private Date startDate;
	
	/**
	 * 日历结束时间
	 */
	private Date endDate;
	
	
	/**
	 *  是否有效(0:否,1:是)
	 */
	private Integer isValid; 

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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

	public Set<Integer> getSetWeeks() {
		return setWeeks;
	}

	public void setSetWeeks(Set<Integer> setWeeks) {
		this.setWeeks = setWeeks;
	}

	public Integer getPriceVal() {
		return priceVal;
	}

	public void setPriceVal(Integer priceVal) {
		this.priceVal = priceVal;
	}

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	
	
	public List<Integer> getWeeksList() {
		if (this.setWeeks==null) {
			return null;
		}
		return new ArrayList<Integer>(this.setWeeks);
	}
	 
}
