package com.ziroom.minsu.services.order.entity;

import java.util.Date;

import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;

/**
 * <p>房源订单快照实体VO</p>
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
public class HouseSnapshotVo extends OrderHouseSnapshotEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 4257483352703384014L;
	
	/**
	 * 评价状态200：不可评价 100 待评价 101 用户已评价 110 房东已评价 111 都已经评价
	 */
	private Integer evaStatus;
	
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 支付状态 0：未支付 1：已支付
	 */
	private Integer payStatus;
	
	/**
	 * 预订人名称
	 */
	private String userName;
	
	/**
	 * 预订人电话
	 */
	private String userTel;
	
	/**
	 * 用户uid
	 */
	private String userUid;
	
	/**
	 * 入住人数
	 */
	private Integer peopleNum;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;

	   /** 订单开始时间 */
    private Date startTime;

    /** 订单结束时间 */
    private Date endTime;
    
    
    
    
	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public Integer getEvaStatus() {
		return evaStatus;
	}

	public void setEvaStatus(Integer evaStatus) {
		this.evaStatus = evaStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
