/**
 * @FileName: MsgAdvisoryFollowRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author loushuai
 * @created 2017年5月25日 下午12:56:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.List;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class MsgAdvisoryFollowRequest extends PageRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3700627581229129858L;

	/**
	 * 预订人uid
	 */
	private List<String> tenantUids;
	
	/**
	 * 预订人姓名
	 */
	private String tenantName;
	
	/**
	 * 预订人电话
	 */
	private String tenantTel;
	
	/**
	 * 房源名称
	 */
	private String houseName;
	
	/**
	 * 房东uid
	 */
	private List<String> landlordUids;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 房东电话
	 */
	private String landlordTel;
	
	/**
	 * 跟进状态
	 */
	private Integer followStatus;
	
	/**
	 * 国家code
	 */
	private String nationCode;
	
	/**
	 * 城市code
	 */
	private String provinceCode;
	
	/**
	 * 城市code
	 */
	private String cityCode;
	

	/**
	 * 整租fid
	 */
	private String houseFid;
	
	/**
	 * 分租fid
	 */
	private String roomFid;
	
	/**
	 * 整租fid集合
	 */
	private List<String> houseFidList;
	
	/**
	 * 分租fid集合
	 */
	private List<String> roomFidList;
	
	/**
	 * 预订人uidList
	 */
	private  List<String> tenantUidList;
	
	/**
	 * 首次咨询开始时间
	 */
	private String msgSendStartTime;

	/**
	 *  房东是否已回复,0:未回复  1:已回复   @Author:lusp  @Date:2017/8/11
	 */
	private Integer isReplay;

	/**
	 *  房客是否已下单,0:未下单  1:已下单未支付  2:已下单并支付  @Author:lusp  @Date:2017/8/11
	 */
	private Integer isOrder;
	
	
	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantTel() {
		return tenantTel;
	}

	public void setTenantTel(String tenantTel) {
		this.tenantTel = tenantTel;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	

	public List<String> getTenantUids() {
		return tenantUids;
	}

	public void setTenantUids(List<String> tenantUids) {
		this.tenantUids = tenantUids;
	}

	public List<String> getLandlordUids() {
		return landlordUids;
	}

	public void setLandlordUids(List<String> landlordUids) {
		this.landlordUids = landlordUids;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordTel() {
		return landlordTel;
	}

	public void setLandlordTel(String landlordTel) {
		this.landlordTel = landlordTel;
	}

	public Integer getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(Integer followStatus) {
		this.followStatus = followStatus;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public List<String> getHouseFidList() {
		return houseFidList;
	}

	public void setHouseFidList(List<String> houseFidList) {
		this.houseFidList = houseFidList;
	}

	public List<String> getRoomFidList() {
		return roomFidList;
	}

	public void setRoomFidList(List<String> roomFidList) {
		this.roomFidList = roomFidList;
	}

	public List<String> getTenantUidList() {
		return tenantUidList;
	}

	public void setTenantUidList(List<String> tenantUidList) {
		this.tenantUidList = tenantUidList;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getMsgSendStartTime() {
		return msgSendStartTime;
	}

	public void setMsgSendStartTime(String msgSendStartTime) {
		this.msgSendStartTime = msgSendStartTime;
	}

	public Integer getIsReplay() {
		return isReplay;
	}

	public void setIsReplay(Integer isReplay) {
		this.isReplay = isReplay;
	}

	public Integer getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
	}
}
