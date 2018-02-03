/**
 * @FileName: HouseFollowLogDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2017年3月3日 上午11:49:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HouseFollowLogEntity;

/**
 * <p>房源跟进明细参数DTO</p>
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
public class HouseFollowLogDto extends HouseFollowLogEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7450899830507295563L;
	
	/**
	 * 跟进结束原因
	 */
	private Integer followEndCause;
	
	/**
	 * 锁业务code 11001：客服跟进审核未通过房源，11002：专员跟进审核未通过房源
	 */
	private String houseLockCode;
	
	/**
	 * 出租方式
	 */
	private Integer rentWay;
	
	/**
	 * 房间fid
	 */
	private String roomFid;
	
	/**
	 * 房源fid
	 */
	private String houseBaseFid;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;

	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	/**
	 * @return the roomFid
	 */
	public String getRoomFid() {
		return roomFid;
	}

	/**
	 * @param roomFid the roomFid to set
	 */
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	/**
	 * @return the houseBaseFid
	 */
	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	/**
	 * @param houseBaseFid the houseBaseFid to set
	 */
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	/**
	 * @return the followEndCause
	 */
	public Integer getFollowEndCause() {
		return followEndCause;
	}

	/**
	 * @param followEndCause the followEndCause to set
	 */
	public void setFollowEndCause(Integer followEndCause) {
		this.followEndCause = followEndCause;
	}

	/**
	 * @return the houseLockCode
	 */
	public String getHouseLockCode() {
		return houseLockCode;
	}

	/**
	 * @param houseLockCode the houseLockCode to set
	 */
	public void setHouseLockCode(String houseLockCode) {
		this.houseLockCode = houseLockCode;
	}
}
