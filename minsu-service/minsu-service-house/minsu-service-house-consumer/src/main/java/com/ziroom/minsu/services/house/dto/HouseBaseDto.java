package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;


/**
 * 
 * <p>房源信息</p>
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
public class HouseBaseDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8798650760011470184L;
	
	/**
	 * 房源或者房间fid
	 */
	private String houseFid;
	
	/**
	 * 删除房间fid
	 */
	private String roomFid;
	
	/**
	 * 0整租 1合租
	 */
	private Integer rentWay;
	
	/**
	 * 房东UID
	 */
	private String landlordUid;
	
	/**
     * 地推管家系统号
     */
    private String empPushCode;
    
    /**
     * 地推管家姓名
     */
    private String empPushName;

    /**
     * 维护管家系统号
     */
    private String empGuardCode;

    /**
     * 维护管家姓名
     */
    private String empGuardName;

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

	public String getEmpPushCode() {
		return empPushCode;
	}

	public void setEmpPushCode(String empPushCode) {
		this.empPushCode = empPushCode;
	}

	public String getEmpPushName() {
		return empPushName;
	}

	public void setEmpPushName(String empPushName) {
		this.empPushName = empPushName;
	}

	public String getEmpGuardCode() {
		return empGuardCode;
	}

	public void setEmpGuardCode(String empGuardCode) {
		this.empGuardCode = empGuardCode;
	}

	public String getEmpGuardName() {
		return empGuardName;
	}

	public void setEmpGuardName(String empGuardName) {
		this.empGuardName = empGuardName;
	}
	
}
