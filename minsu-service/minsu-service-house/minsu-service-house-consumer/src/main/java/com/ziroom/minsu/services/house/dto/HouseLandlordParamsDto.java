package com.ziroom.minsu.services.house.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class HouseLandlordParamsDto implements Serializable{


	private static final long serialVersionUID = -1646800608154219250L;
	/**
	 * 房源fid
	 */
	@NotNull(message="{house.base.fid.null}")
	private String houseBaseFid;
	
	/**
	 * 房间fid
	 */ 
	private String roomFid;
	
	/**
	 * 0整租 1合租
	 */
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;

	/**
	 * 房东fid
	 */
	@NotNull(message="{landlordUid.null}")
	private String landlordUid;

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
}
