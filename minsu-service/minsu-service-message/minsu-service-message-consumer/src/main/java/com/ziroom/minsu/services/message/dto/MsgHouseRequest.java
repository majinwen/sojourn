/**
 * @FileName: MsgHouseRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2016年4月16日 下午3:35:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>留言房源请求参数</p>
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
public class MsgHouseRequest extends PageRequest{
	 /**
	 * 序列id
	 */
	private static final long serialVersionUID = 4420567123228523619L;

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
     * 床位fid
     */
    private String bedFid;

    /**
     * 房客uid
     */
    private String tenantUid;

    /**
     * 房东uid
     */
    private String landlordUid;

    /**
     * 出租类型 0：整租，1：合租，2：床位
     */
    private Integer rentWay;

    /**
     * 整条记录是否删除 默认0 (0：不删除 1：删除)
     */
    private Integer isDel;
    
    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

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

	public String getTenantUid() {
		return tenantUid;
	}

	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
    
}
