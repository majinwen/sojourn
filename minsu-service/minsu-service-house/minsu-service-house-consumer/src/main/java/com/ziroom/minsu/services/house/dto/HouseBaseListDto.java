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

import org.hibernate.validator.constraints.NotEmpty;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>房源列表查询参数</p>
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
public class HouseBaseListDto extends PageRequest {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8921496455545458410L;

	/**
	 * 房东uid
	 */
	@NotEmpty(message = "{landlordUid.null}")
	private String landlordUid;
	
	/**
	 * 房源状态 10:待发布,11:已发布,20:信息审核通过,21:信息审核未通过,30:照片审核未通过,40:上架,41:下架,50:强制下架
	 */
	private Integer houseStatus;
	
	private String housePhyFid;
	
	/**
	 * 房源fid
	 */
	private String houseBaseFid;

	/**
	 * 出租方式 0=整租  1=分租
	 */
	public Integer rentWay;
	
	/**
	 * 房间fid
	 */
	public String houseRoomFid;
	
	/**
	 * @return the houseBaseFid
	 */
	public String getHouseBaseFid() {
		return houseBaseFid;
	}
	
	
	/**
	 * @return the houseRoomFid
	 */
	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	/**
	 * @param houseRoomFid the houseRoomFid to set
	 */
	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	/**
	 * @param houseBaseFid the houseBaseFid to set
	 */
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getHousePhyFid() {
		return housePhyFid;
	}

	public void setHousePhyFid(String housePhyFid) {
		this.housePhyFid = housePhyFid;
	}
}
