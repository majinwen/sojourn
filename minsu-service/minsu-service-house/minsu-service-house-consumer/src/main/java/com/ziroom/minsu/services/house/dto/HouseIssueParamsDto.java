/**
 * @FileName: HouseIssueParamsDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * @author lusp
 * @created 2017年6月27日 上午9:55:41
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>房源发布操作app参数</p>
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
public class HouseIssueParamsDto implements Serializable{


	private static final long serialVersionUID = 7485624144994559420L;

	/**
	 * 用户uid
	 */
	@NotNull(message="用户uid不能为空")
	private String landlordUid;

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

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

}
