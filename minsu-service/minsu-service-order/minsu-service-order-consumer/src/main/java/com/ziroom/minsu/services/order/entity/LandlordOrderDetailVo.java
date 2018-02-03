/**
 * @FileName: LandlordOrderDetailVo.java
 * @Package com.ziroom.minsu.mapp.order.entity
 * 
 * @author jixd
 * @created 2016年5月4日 下午12:06:20
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.entity;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.entity.order.UsualContactEntity;

/**
 * <p>房东端订单详情</p>
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
public class LandlordOrderDetailVo implements Serializable{
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 1225980717108394377L;
	/**
	 * 订单编号
	 */
	private String orderSn;
	/** 
	 * 房东Uid 
	 */
    private String landlordUid;
    /** 
     * 房东电话 
     * 
     */
    private String landlordTel;
    /**
     * 房东名称 
     */
    private String landlordName;
	
	/** 订单状态
     * 10:待确认
     * 20:待入住
     * 30:强制取消
     * 31:房东已拒绝
     * 32:房客取消
     * 33:未支付超时取消
     * 40:已入住
     * 50:退房中
     * 60:待用户确认额外消费
     * 71:提前退房完成
     * 72:正常退房完成 */
    private Integer orderStatus;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 订单状态名称
     */
    private String orderStatusName;
    /**
     * 预定时间
     */
    private String createTime;
    /** 
     * 房源 
     */
    private String houseFid;
    /**
     * 房间fid
     */
    private String roomFid;
    /**
     * 床铺fid
     */
    private String bedFid;
    
    /**房间名称
     * 
     */
	private String roomName;
	/** 
	 * 房源 名称 
	 */
	private String houseName;
	/**
	 * 出租方式
	 */
	private Integer rentWay;
	/**
	 * 入住时间
	 */
	private String startTime;
	/**
	 * 退房时间
	 */
	private String endTime;
	
	/**
	 * 实际退房时间
	 */
	private String checkOutTime;
	
	/**出租方式名称*/
	private String rentWayName;
	
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	/**
	 * 房客支付金额（元）
	 */
	private String tenantNeedPay;
	/**
	 * 退款金额
	 */
	private String refundMoney;
	/**
	 * 房租金额（元）
	 */
	private String rentalMoney;
	/**
	 * 折扣前金额
	 */
	private String bfRentalMoney;
	/**
	 * 折扣金额(元)
	 */
	private String discountMoney;
	/**
	 * 额外消费(元)
	 */
	private String otherMoney;
	/**
	 * 押金（元）
	 */
	private String depositMoney;
	/**
	 * 违约金
	 */
	private String penaltyMoney;
    /**
     * 用户的佣金
     * 
     */
    private String userCommMoney;
    /**
     * 清洁费
     */
    private String cleanMoney;
    /**
     * 本次订单预计收入
     */
    private String landlordIncomeMoney;
    /**
     * 房东的佣金
     * 
     */
    private String lanCommMoney;
    /**
     * 入住人列表
     */
    private List<UsualContactEntity> contactList;
    /**
     * 入住人数量
     */
    private Integer contactsNum;
    /**
	 * 预订人UId
	 */
	private String userUid;
	/**
	 * 预订人名称
	 */
	private String userName;
	 /**
	  * 用户电话 
	  */
    private String userTel;
    /**
     * 预订人收到评价数量
     */
    private Integer evaTotal; 
    /**
     * 满意程度平均值
     */
    private Float landSatisfAva;
    /**
     * 额外消费明细
     */
    private String otherMoneyDes;
    /**
     * 房源图片
     */
    private String housePicUrl;
    /**
     * 出行目地
     */
    private String tripPurpose;
    
    /**
     *收入名称
     */
    private String incomeName;
   
    /**
     * 房东取消 对房东的罚款vo
     * 
     */
    private  FinancePenaltyVo financePenaltyVo = new FinancePenaltyVo();
    

    /**
     * 订单被罚详情
     */
    private List<FinancePenaltyPayRelVo> listFinancePenaltyPayRelVo;
    
    /**
     * 订单罚款总金额(元)
     */
    private String orderPenaltySumMoney;
    
    
	/**
	 * @return the orderPenaltySumMoney
	 */
	public String getOrderPenaltySumMoney() {
		return orderPenaltySumMoney;
	}
	/**
	 * @param orderPenaltySumMoney the orderPenaltySumMoney to set
	 */
	public void setOrderPenaltySumMoney(String orderPenaltySumMoney) {
		this.orderPenaltySumMoney = orderPenaltySumMoney;
	}
	/**
	 * @return the listFinancePenaltyPayRelVo
	 */
	public List<FinancePenaltyPayRelVo> getListFinancePenaltyPayRelVo() {
		return listFinancePenaltyPayRelVo;
	}
	/**
	 * @param listFinancePenaltyPayRelVo the listFinancePenaltyPayRelVo to set
	 */
	public void setListFinancePenaltyPayRelVo(
			List<FinancePenaltyPayRelVo> listFinancePenaltyPayRelVo) {
		this.listFinancePenaltyPayRelVo = listFinancePenaltyPayRelVo;
	}
	/**
	 * @return the financePenaltyVo
	 */
	public FinancePenaltyVo getFinancePenaltyVo() {
		return financePenaltyVo;
	}
	/**
	 * @param financePenaltyVo the financePenaltyVo to set
	 */
	public void setFinancePenaltyVo(FinancePenaltyVo financePenaltyVo) {
		this.financePenaltyVo = financePenaltyVo;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getContactsNum() {
		return contactsNum;
	}
	public void setContactsNum(Integer contactsNum) {
		this.contactsNum = contactsNum;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatusName() {
		return orderStatusName;
	}
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public Integer getRentWay() {
		return rentWay;
	}
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
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
	public String getTenantNeedPay() {
		return tenantNeedPay;
	}
	public void setTenantNeedPay(String tenantNeedPay) {
		this.tenantNeedPay = tenantNeedPay;
	}
	public String getRentalMoney() {
		return rentalMoney;
	}
	public void setRentalMoney(String rentalMoney) {
		this.rentalMoney = rentalMoney;
	}
	public String getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(String discountMoney) {
		this.discountMoney = discountMoney;
	}
	public String getOtherMoney() {
		return otherMoney;
	}
	public void setOtherMoney(String otherMoney) {
		this.otherMoney = otherMoney;
	}
	public String getDepositMoney() {
		return depositMoney;
	}
	public void setDepositMoney(String depositMoney) {
		this.depositMoney = depositMoney;
	}
	public String getPenaltyMoney() {
		return penaltyMoney;
	}
	public void setPenaltyMoney(String penaltyMoney) {
		this.penaltyMoney = penaltyMoney;
	}
	public String getUserCommMoney() {
		return userCommMoney;
	}
	public void setUserCommMoney(String userCommMoney) {
		this.userCommMoney = userCommMoney;
	}
	public String getLandlordIncomeMoney() {
		return landlordIncomeMoney;
	}
	public void setLandlordIncomeMoney(String landlordIncomeMoney) {
		this.landlordIncomeMoney = landlordIncomeMoney;
	}
	public String getLanCommMoney() {
		return lanCommMoney;
	}
	public void setLanCommMoney(String lanCommMoney) {
		this.lanCommMoney = lanCommMoney;
	}
	public List<UsualContactEntity> getContactList() {
		return contactList;
	}
	public void setContactList(List<UsualContactEntity> contactList) {
		this.contactList = contactList;
	}
	public String getUserUid() {
		return userUid;
	}
	public void setUserUid(String userUid) {
		this.userUid = userUid;
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
	public Integer getEvaTotal() {
		return evaTotal;
	}
	public void setEvaTotal(Integer evaTotal) {
		this.evaTotal = evaTotal;
	}
	public Float getLandSatisfAva() {
		return landSatisfAva;
	}
	public void setLandSatisfAva(Float landSatisfAva) {
		this.landSatisfAva = landSatisfAva;
	}
	public String getOtherMoneyDes() {
		return otherMoneyDes;
	}
	public void setOtherMoneyDes(String otherMoneyDes) {
		this.otherMoneyDes = otherMoneyDes;
	}
	public String getRefundMoney() {
		return refundMoney;
	}
	public String getCleanMoney() {
		return cleanMoney;
	}
	public void setCleanMoney(String cleanMoney) {
		this.cleanMoney = cleanMoney;
	}
	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}
	public String getBfRentalMoney() {
		return bfRentalMoney;
	}
	public void setBfRentalMoney(String bfRentalMoney) {
		this.bfRentalMoney = bfRentalMoney;
	}
	public String getHousePicUrl() {
		return housePicUrl;
	}
	public void setHousePicUrl(String housePicUrl) {
		this.housePicUrl = housePicUrl;
	}
	public String getTripPurpose() {
		return tripPurpose;
	}
	public void setTripPurpose(String tripPurpose) {
		this.tripPurpose = tripPurpose;
	}
	public String getIncomeName() {
		return incomeName;
	}
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}
	public String getRentWayName() {
		return rentWayName;
	}
	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}
	
}
