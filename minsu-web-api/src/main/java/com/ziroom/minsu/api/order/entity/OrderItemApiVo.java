/**
 * @FileName: OrderItemVo.java
 * @Package com.ziroom.minsu.api.order.entity
 * 
 * @author jixd
 * @created 2016年5月2日 下午2:00:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.entity;

import java.io.Serializable;

import com.ziroom.minsu.valenum.evaluate.EvaluateClientBtnStatuEnum;

/**
 * <p>订单列表显示数据</p>
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
public class OrderItemApiVo implements Serializable{
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -410509735122413093L;
	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 房源图片url
	 */
	private String picUrl;
	/**
	 * 出租方式
	 */
	private Integer rentWay;
	/**
	 * 房源fid，根据出租类型返回fid
	 */
	private String fid;
	/**
	 * 房间名称
	 */
	private String roomName;
	/**
	 * 房源名称，根据出租方式，显示房源名称还是房间名称
	 */
	private String houseName;
	/**
	 * 开始时间 yyyy-MM-dd
	 */
	private String startTimeStr;
	/**
	 * 结束时间 yyyy-MM-dd
	 */
	private String endTimeStr;
	
	/**
	 * 时间戳
	 */
	private Long startTimeStamp;
	
	/**
	 * 订单实际支付金额
	 */
	private Integer needPay;
	/**
	 * 入住几晚
	 */
	private Integer housingDay;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 订单状态名称
	 */
	private String orderStatusName;
	/**
	 * 前端显示订单状态code
	 */
	private Integer orderStatusShowCode;
	/**
	 * 城市code
	 */
	private String cityCode;
	
	/**
	 * 评价状态200：不可评价 100 待评价 101 用户已评价 110 房东已评价 111 都已经评价
	 *  返回给客户端，只需要 是否显示<去评价> 0:未评价   1：已评价
	 */
	private Integer evaStatus;
	
	
	/**
	 * 订单原始评价状态200：不可评价 100 待评价 101 用户已评价 110 房东已评价 111 都已经评价 
	 */
	private Integer orderEvaStatus;


    /**
     * 是否智能锁
     */
    private Integer isLock;
	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 房源地址
	 */
	private String houseAddr;
	/**
	 * 房东手机号
	 */
	private String landlordMobile;
	
    /** 
     * 用户名称
     */
    private String userName;
	
    /**
     * 评价状态
     */
    private Integer pjStatus  = EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuCode();


    /**
     * 评价显示按钮
     */
    private String pjButton = "";
    
    /**
     * 排序从0开始
     */
    private Integer sortIndex;
    
    /**
     * 订单类型
     * @see
     * com.ziroom.minsu.api.common.valeenum.OrderProjectTypeEnum
     */
    private Integer orderProjectType;
    
    public Integer getOrderEvaStatus() {
		return orderEvaStatus;
	}

	public void setOrderEvaStatus(Integer orderEvaStatus) {
		this.orderEvaStatus = orderEvaStatus;
	}

	public Integer getOrderProjectType() {
		return orderProjectType;
	}

	public void setOrderProjectType(Integer orderProjectType) {
		this.orderProjectType = orderProjectType;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getPjStatus() {
		return pjStatus;
	}

	public String getPjButton() {
		return pjButton;
	}

	public void setPjStatus(Integer pjStatus) {
		this.pjStatus = pjStatus;
	}

	public void setPjButton(String pjButton) {
		this.pjButton = pjButton;
	}

	public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public Integer getNeedPay() {
		return needPay;
	}
	public void setNeedPay(Integer needPay) {
		this.needPay = needPay;
	}
	public Integer getHousingDay() {
		return housingDay;
	}
	public void setHousingDay(Integer housingDay) {
		this.housingDay = housingDay;
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
	public Integer getEvaStatus() {
		return evaStatus;
	}
	public void setEvaStatus(Integer evaStatus) {
		this.evaStatus = evaStatus;
	}
	public Integer getRentWay() {
		return rentWay;
	}
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Integer getOrderStatusShowCode() {
		return orderStatusShowCode;
	}
	public void setOrderStatusShowCode(Integer orderStatusShowCode) {
		this.orderStatusShowCode = orderStatusShowCode;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Long getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}
	
	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}


}
