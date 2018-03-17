/**
 * @FileName: UpCustomerPicDto.java
 * @Package com.ziroom.minsu.api.customer.dto
 * 
 * @author bushujie
 * @created 2016年4月23日 下午5:56:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.mapp.customer.vo;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.mapp.common.dto.BaseParamDto;

/**
 * 
 * <p>房东uid</p>
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
public class LandlordDto extends BaseParamDto{
	/**
	 * 房东uid
	 */
	@NotNull(message = "landlordUid.null")
	private String landlordUid;
	/**
	 * 房源fid
	 */
	@NotNull(message = "house.or.room.fid.null")
	private String houseFid;
	/**
	 * 出租方式
	 */
	@NotNull(message = "house.rentway.null")
	private Integer rentWay;

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
}
