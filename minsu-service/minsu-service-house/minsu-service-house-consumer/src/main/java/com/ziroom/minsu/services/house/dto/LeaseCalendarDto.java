/**
 * @FileName: LeaseCalendarDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年4月5日 上午10:14:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>出租日历vo</p>
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
public class LeaseCalendarDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaseCalendarDto.class);
	/**
	 * 房源fid
	 */
	private String houseBaseFid;
	
	/**
	 * 房间fid
	 */
	private String houseRoomFid;
	
	/**
	 * 床位fid
	 */
	private String bedFid;
	
	/**
	 * 出租方式
	 */
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;
	
	/**
	 * 日历开始时间
	 */
	@NotNull(message="{startDate.null}")
	private Date startDate;
	
	/**
	 * 日历结束时间
	 */
	@NotNull(message="{endDate.null}")
	private Date endDate;
	
	/**
	 * 当天时间
	 */
	private Date nowDate;
	
	/**
	 * 是否有效: 0:否 1:是
	 */
	private Integer isValid;

	/**
	 * @return the isValid
	 */
	public Integer getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public LeaseCalendarDto(){
		try {
			this.nowDate=DateUtil.parseDate(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
		} catch (ParseException e) {
            LogUtil.error(LOGGER,"e:{}",e);
		}
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
	
	public String getBedFid() {
		return bedFid;
	}

	public void setBedFid(String bedFid) {
		this.bedFid = bedFid;
	}
	
	public Integer getRentWay() {
		return rentWay;
	}
	
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	
	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}
}
