/**
 * @FileName: StatsHouseEvaRequest.java
 * @Package com.ziroom.minsu.services.evaluate.dto
 * 
 * @author yd
 * @created 2016年4月8日 下午3:15:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dto;

import java.io.Serializable;

/**
 * <p>统计房源评价 查询条件</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class StatsHouseEvaRequest implements Serializable{
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -5015102414346731445L;

	/**
     * 业务编号
     */
    private String fid;

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

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

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
