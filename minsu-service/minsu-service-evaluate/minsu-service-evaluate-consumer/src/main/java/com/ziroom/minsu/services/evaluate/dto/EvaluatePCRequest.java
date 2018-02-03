/**
 * @FileName: EvaluatePCRequest.java
 * @Package com.ziroom.minsu.services.evaluate.dto
 * 
 * @author jixd
 * @created 2016年8月8日 上午9:22:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>评价请求类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EvaluatePCRequest extends PageRequest{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 1715576935664804504L;
	
	
	 /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 床fid
     */
    private String bedFid;

    /**
     * 出租类型 0：整租 1：合租 2：床位
     */
    private Integer rentWay;
    
    /**
     * 房东的uid
     */
    private String landlordUid;

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
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

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
    
}
