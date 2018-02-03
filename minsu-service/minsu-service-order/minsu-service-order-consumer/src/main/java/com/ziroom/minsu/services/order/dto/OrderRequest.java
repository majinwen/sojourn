/**
 * @FileName: OrderRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author liyingjie
 * @created 2016年4月2日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class OrderRequest extends PageRequest {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6485230181824233964L;

	/**
	 * 用户uid
	 */
	private String userUid;

	/**
	 * 房东uid
	 */
	private String landlordUid;

	/**
	 * 房源 houseFid
	 */
	private String houseFid;

	/**
	 * 房源列表
	 */
	private List<String> houseFids = new ArrayList<>();

	/**
	 * 房间 roomFid
	 */
	private String roomFid;

	/**
	 * 请求类型  1:用户 2：房东 3：后台
	 */
	private Integer requestType;
	
	/**
	 * 订单活动类型
	 */
	private Integer orderActityType;


	/** 订单编号*/
	private String houseSn;

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	/**

	 * 订单状态 10:待确认 20:待入住 30:强制取消 31:房东已拒绝 32:房客取消 33:未支付超时取消 40:已入住 50:退房中 60:待用户确认额外消费 71:提前退房完成 72:正常退房完成
	 */
	private List<Integer> listOrderStatus;

	/**
	 * 订单集合
	 */
	private List<String> listOrderSn;

	/**
	 * 订单编号
	 */
	private String orderSn;

	/**
	 * 评价状态 100 未评价 101 用户已评价 110 房东已评价 111 都已经评价
	 */
	private Integer evaStatus;
	
	/**
	 * 评价状态集合 100 未评价 101 用户已评价 110 房东已评价 111 都已经评价
	 */
	private List<Integer> listEvaStatus;

	/**
	 * 房东订单状态类型 ：1=待处理  2=进行中  3=已处理
	 */
	private Integer lanOrderType;
	/**
	 * 房客订单状态类型：1=进行中 2=已完成
	 */
	private Integer tenantOrderType;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 预订人手机号 
	 */
	private String userTel;

	/**
	 * 房源别名
	 */
	private String houseName;

	/**
	 * 城市code
	 */
	private String cityCode;

	/**
	 * 国家code
	 */
	private String nationCode;
	/**
	 * 省code
	 */
	private String provinceCode;

	/**
	 * 区code
	 */
	private String areaCode;

	/**
	 * 房东姓名
	 */
	private String landlordName;

	/**
	 * 房东电话
	 */
	private String landlordTel;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 创建时间的开始
	 */
	private String createTimeStart;
	/**
	 * 创建时间的结束
	 */
	private String createTimeEnd;

	/**
	 * 订单的操作类型
	 */
	private Integer orderOprationType;
	
	/**
	 * 是否删除 0：否，1：是
	 */
	private Integer isDel;
	
	
	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 地推管家
	 */
	private String empPushName;

	/**
	 * 维护管家
	 */
	private String empGuardName;
	
	/**
	 * 结算状态
	 */
	private Integer accountsStatus;
	
	/**
	 * 角色类型
	 */
	private Integer roleType;
	
	
	/**
	 * 入住时间的开始
	 */
	private String checkInTimeStart;
	
	/**
	 * 入住时间的结束
	 */
	private String checkInTimeEnd;

	/**
	 * 退房时间的开始
	 */
	private String checkOutTimeStart;

	/**
	 * 退房时间的结束
	 */
	private String checkOutTimeEnd;

	public String getCheckOutTimeStart() {
		return checkOutTimeStart;
	}

	public void setCheckOutTimeStart(String checkOutTimeStart) {
		this.checkOutTimeStart = checkOutTimeStart;
	}

	public String getCheckOutTimeEnd() {
		return checkOutTimeEnd;
	}

	public void setCheckOutTimeEnd(String checkOutTimeEnd) {
		this.checkOutTimeEnd = checkOutTimeEnd;
	}

	/** 
	 * 订单的实际结束时间开始 
	 */
    private String realEndTimeStart;
    
	/** 
	 * 订单的实际结束时间结束 
	 */
    private String realEndTimeEnd;
    
    
    /**
	 * 评价类型  1=待评价  2=已评价
	 */
	private  Integer evaType;
	
	/**
     * 评价有效天数
     */
    private Integer  limitDay;
    
    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
	
	/**
	 * @return the orderType
	 */
	public Integer getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(Integer limitDay) {
		this.limitDay = limitDay;
	}

	public Integer getEvaType() {
		return evaType;
	}

	public void setEvaType(Integer evaType) {
		this.evaType = evaType;
	}

	public String getCheckInTimeStart() {
		return checkInTimeStart;
	}

	public String getCheckInTimeEnd() {
		return checkInTimeEnd;
	}

	public String getRealEndTimeStart() {
		return realEndTimeStart;
	}

	public String getRealEndTimeEnd() {
		return realEndTimeEnd;
	}

	public void setCheckInTimeStart(String checkInTimeStart) {
		this.checkInTimeStart = checkInTimeStart;
	}

	public void setCheckInTimeEnd(String checkInTimeEnd) {
		this.checkInTimeEnd = checkInTimeEnd;
	}

	public void setRealEndTimeStart(String realEndTimeStart) {
		this.realEndTimeStart = realEndTimeStart;
	}

	public void setRealEndTimeEnd(String realEndTimeEnd) {
		this.realEndTimeEnd = realEndTimeEnd;
	}

	/**
	 * @return the roleType
	 */
	public Integer getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getEvaStatus() {
		return evaStatus;
	}

	public void setEvaStatus(Integer evaStatus) {
		this.evaStatus = evaStatus;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public List<Integer> getListOrderStatus() {
		return listOrderStatus;
	}

	public void setListOrderStatus(List<Integer> listOrderStatus) {
		this.listOrderStatus = listOrderStatus;
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

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public List<String> getListOrderSn() {
		return listOrderSn;
	}

	public void setListOrderSn(List<String> listOrderSn) {
		this.listOrderSn = listOrderSn;
	}

	public Integer getLanOrderType() {
		return lanOrderType;
	}

	public void setLanOrderType(Integer lanOrderType) {
		this.lanOrderType = lanOrderType;
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

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
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

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	
	public Integer getOrderOprationType() {
		return orderOprationType;
	}

	public void setOrderOprationType(Integer orderOprationType) {
		this.orderOprationType = orderOprationType;
	}

	public List<Integer> getListEvaStatus() {
		return listEvaStatus;
	}
	
	public void setListEvaStatus(List<Integer> listEvaStatus) {
		this.listEvaStatus = listEvaStatus;
	}
	public Integer getTenantOrderType() {
		return tenantOrderType;
	}
	public void setTenantOrderType(Integer tenantOrderType) {
		this.tenantOrderType = tenantOrderType;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getAccountsStatus() {
		return accountsStatus;
	}

	public void setAccountsStatus(Integer accountsStatus) {
		this.accountsStatus = accountsStatus;
	}

	public String getEmpPushName() {
		return empPushName;
	}

	public void setEmpPushName(String empPushName) {
		this.empPushName = empPushName;
	}

	public String getEmpGuardName() {
		return empGuardName;
	}

	public void setEmpGuardName(String empGuardName) {
		this.empGuardName = empGuardName;
	}

	public List<String> getHouseFids() {
		return houseFids;
	}

	public void setHouseFids(List<String> houseFids) {
		this.houseFids = houseFids;
	}

	public String getLandlordTel() {
		return landlordTel;
	}

	public void setLandlordTel(String landlordTel) {
		this.landlordTel = landlordTel;
	}

	public Integer getOrderActityType() {
		return orderActityType;
	}

	public void setOrderActityType(Integer orderActityType) {
		this.orderActityType = orderActityType;
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
