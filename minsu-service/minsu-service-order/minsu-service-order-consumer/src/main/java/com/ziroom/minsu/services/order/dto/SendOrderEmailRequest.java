/**
 * @FileName: SendOrderEmailRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author bushujie
 * @created 2017年4月25日 上午10:16:37
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.dto;

import java.util.Date;

/**
 * <p>发邮件需要参数</p>
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
public class SendOrderEmailRequest {
	
	/**
	 * 订单开始时间
	 */
	private Date orderStartDate;
	
	/**
	 * 订单结束时间
	 */
	private Date orderEndDate;
	
	/**
	 * 入住时间
	 */
	private Date checkInDate;
	
	/**
	 * 退房时间
	 */
	private Date checkOutDate;
	
	/**
	 * 预定人姓名
	 */
	private String bookName;
	
	/**
	 * 入住人数量
	 */
	private Integer checkInNum;
	
	/**
	 * 房东邮箱地址
	 */
	private String sendEmailAddr;
	
	/**
	 * 房源名称
	 */
	private String houseName;
	
	/**
	 * 订单状态描述
	 */
	private String orderStatus;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 邮件标题
	 */
	private String emailTitle;

	/**
	 * @return the emailTitle
	 */
	public String getEmailTitle() {
		return emailTitle;
	}

	/**
	 * @param emailTitle the emailTitle to set
	 */
	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
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
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the orderStartDate
	 */
	public Date getOrderStartDate() {
		return orderStartDate;
	}

	/**
	 * @param orderStartDate the orderStartDate to set
	 */
	public void setOrderStartDate(Date orderStartDate) {
		this.orderStartDate = orderStartDate;
	}

	/**
	 * @return the orderEndDate
	 */
	public Date getOrderEndDate() {
		return orderEndDate;
	}

	/**
	 * @param orderEndDate the orderEndDate to set
	 */
	public void setOrderEndDate(Date orderEndDate) {
		this.orderEndDate = orderEndDate;
	}

	/**
	 * @return the checkInDate
	 */
	public Date getCheckInDate() {
		return checkInDate;
	}

	/**
	 * @param checkInDate the checkInDate to set
	 */
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	/**
	 * @return the checkOutDate
	 */
	public Date getCheckOutDate() {
		return checkOutDate;
	}

	/**
	 * @param checkOutDate the checkOutDate to set
	 */
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return the checkInNum
	 */
	public Integer getCheckInNum() {
		return checkInNum;
	}

	/**
	 * @param checkInNum the checkInNum to set
	 */
	public void setCheckInNum(Integer checkInNum) {
		this.checkInNum = checkInNum;
	}

	/**
	 * @return the sendEmailAddr
	 */
	public String getSendEmailAddr() {
		return sendEmailAddr;
	}

	/**
	 * @param sendEmailAddr the sendEmailAddr to set
	 */
	public void setSendEmailAddr(String sendEmailAddr) {
		this.sendEmailAddr = sendEmailAddr;
	}
}
