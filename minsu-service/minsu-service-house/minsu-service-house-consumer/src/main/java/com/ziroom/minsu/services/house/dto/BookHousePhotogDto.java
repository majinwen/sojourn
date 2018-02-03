/**
 * @FileName: BookHousePhotogDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2016年11月8日 上午10:05:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;
import java.util.Date;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>预约摄影师dto</p>
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
public class BookHousePhotogDto extends PageRequest implements Serializable {

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 2441625717061674463L;

	/**
	 * 房源fid
	 */
	private String houseFid;

	//城市code
	private String cityCode;

	/**
	 * 预约开始时间
	 */
	private Date bookStartTime;
	
	/**
	 * 预约结束时间
	 */
	private Date bookEndTime;
	/**
	 * 预约开始时间
	 */
	private String bookStartTimeStr;
	
	/**
	 * 预约结束时间
	 */
	private String bookEndTimeStr;
	/**
	 * 预约创建时间
	 */
	private String bookCreateStartTimeStr;
	/**
	 * 预约创建结束时间
	 */
	private String bookCreateEndTimeStr;
	/**
	 * 上门时间
	 */
	private String doorHomeStartTimeStr;
	/**
	 * 上门时间
	 */
	private String doorHomeEndTimeStr;

	/**
	 * 预约人uid
	 */
	private String bookerUid;
	
	/**
	 * 预约人姓名
	 */
	private String bookerName;
	
	/**
	 * 预约人手机号
	 */
	private String bookerMobile;
	
	/**
	 * 客户人uid
	 */
	private String customerUid;
	
	/**
	 * 客户人真实姓名
	 */
	private String customerName;
	
	/**
	 * 客户人手机号
	 */
	private String customerMobile;
	
	/**
	 * 房源名称
	 */
	private String houseName;
	
	/**
	 * 房源编号
	 */
	private String houseSn;
	
	/**
	 * 摄影地址
	 */
	private String shotAddr;
	
	/**
	 * 预约备注
	 */
	private String bookOrderRemark;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	
	 /**
     * 预约订单状态（10=预约中  11=预约成功   12.完成）
     */
    private Integer bookOrderStatu;
    
    /**
     * 预约摄影师订单编号
     */
    private String bookOrderSn;
    
    /**
     * 摄影师手机号(当时预约快照值)
     */
    private String photographerMobile;

    /**
     * 摄影师真实姓名(当时预约快照值)
     */
    private String photographerName;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getBookOrderStatu() {
		return bookOrderStatu;
	}

	public String getBookOrderSn() {
		return bookOrderSn;
	}

	public String getPhotographerMobile() {
		return photographerMobile;
	}

	public String getPhotographerName() {
		return photographerName;
	}

	public void setBookOrderStatu(Integer bookOrderStatu) {
		this.bookOrderStatu = bookOrderStatu;
	}

	public void setBookOrderSn(String bookOrderSn) {
		this.bookOrderSn = bookOrderSn;
	}

	public void setPhotographerMobile(String photographerMobile) {
		this.photographerMobile = photographerMobile;
	}

	public void setPhotographerName(String photographerName) {
		this.photographerName = photographerName;
	}

	/**
	 * @return the bookStartTimeStr
	 */
	public String getBookStartTimeStr() {
		return bookStartTimeStr;
	}

	/**
	 * @param bookStartTimeStr the bookStartTimeStr to set
	 */
	public void setBookStartTimeStr(String bookStartTimeStr) {
		this.bookStartTimeStr = bookStartTimeStr;
	}

	/**
	 * @return the bookEndTimeStr
	 */
	public String getBookEndTimeStr() {
		return bookEndTimeStr;
	}

	/**
	 * @param bookEndTimeStr the bookEndTimeStr to set
	 */
	public void setBookEndTimeStr(String bookEndTimeStr) {
		this.bookEndTimeStr = bookEndTimeStr;
	}

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
	 * @return the bookOrderRemark
	 */
	public String getBookOrderRemark() {
		return bookOrderRemark;
	}

	/**
	 * @param bookOrderRemark the bookOrderRemark to set
	 */
	public void setBookOrderRemark(String bookOrderRemark) {
		this.bookOrderRemark = bookOrderRemark;
	}

	/**
	 * @return the houseFid
	 */
	public String getHouseFid() {
		return houseFid;
	}

	/**
	 * @param houseFid the houseFid to set
	 */
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	/**
	 * @return the bookStartTime
	 */
	public Date getBookStartTime() {
		return bookStartTime;
	}

	/**
	 * @param bookStartTime the bookStartTime to set
	 */
	public void setBookStartTime(Date bookStartTime) {
		this.bookStartTime = bookStartTime;
	}

	/**
	 * @return the bookEndTime
	 */
	public Date getBookEndTime() {
		return bookEndTime;
	}

	/**
	 * @param bookEndTime the bookEndTime to set
	 */
	public void setBookEndTime(Date bookEndTime) {
		this.bookEndTime = bookEndTime;
	}

	/**
	 * @return the bookerUid
	 */
	public String getBookerUid() {
		return bookerUid;
	}

	/**
	 * @param bookerUid the bookerUid to set
	 */
	public void setBookerUid(String bookerUid) {
		this.bookerUid = bookerUid;
	}

	/**
	 * @return the bookerName
	 */
	public String getBookerName() {
		return bookerName;
	}

	/**
	 * @param bookerName the bookerName to set
	 */
	public void setBookerName(String bookerName) {
		this.bookerName = bookerName;
	}

	/**
	 * @return the bookerMobile
	 */
	public String getBookerMobile() {
		return bookerMobile;
	}

	/**
	 * @param bookerMobile the bookerMobile to set
	 */
	public void setBookerMobile(String bookerMobile) {
		this.bookerMobile = bookerMobile;
	}

	/**
	 * @return the customerUid
	 */
	public String getCustomerUid() {
		return customerUid;
	}

	/**
	 * @param customerUid the customerUid to set
	 */
	public void setCustomerUid(String customerUid) {
		this.customerUid = customerUid;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerMobile
	 */
	public String getCustomerMobile() {
		return customerMobile;
	}

	/**
	 * @param customerMobile the customerMobile to set
	 */
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	/**
	 * @return the houseSn
	 */
	public String getHouseSn() {
		return houseSn;
	}

	/**
	 * @param houseSn the houseSn to set
	 */
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	/**
	 * @return the shotAddr
	 */
	public String getShotAddr() {
		return shotAddr;
	}

	/**
	 * @param shotAddr the shotAddr to set
	 */
	public void setShotAddr(String shotAddr) {
		this.shotAddr = shotAddr;
	}

	public String getBookCreateStartTimeStr() {
		return bookCreateStartTimeStr;
	}

	public void setBookCreateStartTimeStr(String bookCreateStartTimeStr) {
		this.bookCreateStartTimeStr = bookCreateStartTimeStr;
	}

	public String getBookCreateEndTimeStr() {
		return bookCreateEndTimeStr;
	}

	public void setBookCreateEndTimeStr(String bookCreateEndTimeStr) {
		this.bookCreateEndTimeStr = bookCreateEndTimeStr;
	}

	public String getDoorHomeStartTimeStr() {
		return doorHomeStartTimeStr;
	}

	public void setDoorHomeStartTimeStr(String doorHomeStartTimeStr) {
		this.doorHomeStartTimeStr = doorHomeStartTimeStr;
	}

	public String getDoorHomeEndTimeStr() {
		return doorHomeEndTimeStr;
	}

	public void setDoorHomeEndTimeStr(String doorHomeEndTimeStr) {
		this.doorHomeEndTimeStr = doorHomeEndTimeStr;
	}
}
