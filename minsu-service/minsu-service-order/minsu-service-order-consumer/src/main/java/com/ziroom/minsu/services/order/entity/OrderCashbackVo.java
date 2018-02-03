package com.ziroom.minsu.services.order.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>订单返现申请VO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月7日
 * @since 1.0
 * @version 1.0
 */
public class OrderCashbackVo extends BaseEntity {

	/**序列化ID*/
	private static final long serialVersionUID = -2246127731262869189L;

	/**订单编号 */
    private String orderSn;
    
    /**订单状态*/
    private Integer orderStatus;

	/**下单日期*/
    private Date createOrderTime;
    
    /** 房间 名称 */
	private String houseName;
	
	/** 房源 */
    private String houseFid;
    
    /**支付日期*/
    private Date payTime;
    
    /** 支付金额 */
    private Integer payMoney;
    
    /** 房东uid*/
    private String landlordUid;
    
    /** 房东电话 */
    private String landlordTel;

    /** 房东名称 */
    private String landlordName;
    
    /** 房东是否为在民宿黑名单 */
    private String isLandInMinsuBlack;
    
    /** 房客uid*/
    private String userUid;
    
    /** 用户电话 */
    private String userTel;

    /** 用户名称 */
    private String userName;
    
    /** 房客是否为在民宿黑名单 */
    private String isUserInMinsuBlack;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getLandlordTel() {
		return landlordTel;
	}

	public void setLandlordTel(String landlordTel) {
		this.landlordTel = landlordTel;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsLandInMinsuBlack() {
		return isLandInMinsuBlack;
	}

	public void setIsLandInMinsuBlack(String isLandInMinsuBlack) {
		this.isLandInMinsuBlack = isLandInMinsuBlack;
	}

	public String getIsUserInMinsuBlack() {
		return isUserInMinsuBlack;
	}

	public void setIsUserInMinsuBlack(String isUserInMinsuBlack) {
		this.isUserInMinsuBlack = isUserInMinsuBlack;
	}
    
}
