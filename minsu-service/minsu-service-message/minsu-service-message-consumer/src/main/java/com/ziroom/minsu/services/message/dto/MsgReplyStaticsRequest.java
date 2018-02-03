package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;
import java.util.List;

public class MsgReplyStaticsRequest implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4695440900062412786L;

	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 房源或者房间fid
	 */
	private String houseFid;
	
	/**
	 * 出租方式
	 */
	private Integer rentWay=0;
	
	/**
	 * 多少天内
	 */
	private Integer days=30;
	
	/**
	 * 多少天内
	 */
	private Integer days2=90;
	
	/**
	 * 多少条
	 */
	private Integer minLimit=10;
	
	/**
	 * 聊天关系fid
	 */
	private List<String>  msgHouseFidList;
	

	public Integer getDays2() {
		return days2;
	}

	public Integer getMinLimit() {
		return minLimit;
	}

	public void setDays2(Integer days2) {
		this.days2 = days2;
	}

	public void setMinLimit(Integer minLimit) {
		this.minLimit = minLimit;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public Integer getDays() {
		return days;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public List<String> getMsgHouseFidList() {
		return msgHouseFidList;
	}

	public void setMsgHouseFidList(List<String> msgHouseFidList) {
		this.msgHouseFidList = msgHouseFidList;
	}
	
}
