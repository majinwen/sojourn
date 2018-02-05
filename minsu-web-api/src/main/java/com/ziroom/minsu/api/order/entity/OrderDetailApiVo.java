/**
 * @FileName: OrderDetailVo.java
 * @Package com.ziroom.minsu.api.order.entity
 * 
 * @author jixd
 * @created 2016年5月1日 下午5:03:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ziroom.minsu.services.order.dto.OrderDetailFeeResponse;

/**
 * <p>订单详情</p>
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
public class OrderDetailApiVo implements Serializable{

	/**
	 * 序列Id
	 */
	private static final long serialVersionUID = -1461882270789161853L;
	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 房源图片url
	 */
	private String picUrl;
	/**
	 * 房源fid
	 */
	private String houseFid;
	/**
	 * 房源名称
	 */
	private String houseName;
	/**
	 * 房源地址
	 */
	private String houseAddr;
	/**
	 * 房间fid
	 */
	private String roomFid;
	/**
	 * 房间名称
	 */
	private String roomName;
	/**
	 * 床铺fid
	 */
	private String bedFid;
	/**
	 * 出租方式
	 */
	private Integer rentWay;
	
	/**
	 * 共享客厅特殊处理
	 */
	private String rentWayName;
	
	/**
	 * 开始时间   6月3日14:00入住
	 */
	private String startTimeStr;
	/**
	 * 结束时间  6月17日 12：00离开
	 */
	private String endTimeStr;
	/**
	 * 用户UID
	 */
	private String userUid;
	/**
	 * 用户电话
	 */
	private String userTel;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 入住人列表
	 */
	private List<UsualContactVo> usualContactList;
	/**
	 * 押金
	 */
	private Integer depositMoney;
	/**
	 * 用户的佣金
	 */
	private Integer userCommMoney;
	/**
	 * 房租
	 */
	private Integer rentalMoney;
	/**
	 * 优惠券金额
	 */
	 private Integer couponMoney;
	/**
	 * 折扣金额
	 */
    private Integer discountMoney;
    /**
     * 实际支付金额
     */
    private Integer needPay;
    /**
     * 实际消费金额 订单结算之后实际发生的消费金额（违约金 + 额外消费 + 房租）
     */
    private Integer realMoney;
    /**
     * 实际支付金额 支付回调（可能是多个）的和
     */
    private Integer payMoney;
    /**
     * 违约金
     */
    private Integer penaltyMoney;
    /**
     * 其他消费
     */
    private Integer otherMoney;
    /**
     * 退款金额
     */
    private Integer refundMoney;
    /**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 订单支付状态
	 */
	private Integer payStatus;
	/**
	 * 订单状态名称
	 */
	private String orderStatusName;
	/**
	 * 前端显示状态码
	 */
	private Integer orderStatusShowCode;
	/**
	 * 0:没有结算 1：结算中  2：结算完 
	 */
	private Integer accountsStatus;
	/**
	 * 免费退订最小小时数,退订规则
	 */
	private Integer preFreeHour;


	/**
	 * 退订规则 等提示信息
	 */
	private String  msgInfo;

	/**
	 * 其他消费明细
	 */
	private String otherMoneyDes;
	/**
	 * 订单支付超时时间
	 */
	private String expireTime;
	
	/**
	 * 城市code
	 */
	private String cityCode;
	
	/**
	 * 城市名字
	 */
	private String cityName;
	
	
    /**
     * 房东的联系方式
     */
    private String landlordMobile;


	/**
	 * 房东的联系方式
	 */
	private String landlordUid;
    
    /**
     * 门锁在线状态：
     * 1：在线
     * 2：离线
     */
    private Integer smartLockSta;
    
    /**
     * 是否有智能锁(0:否,1:是)
     */
    private int isLock;
    
    /**
     * 是否是入住前一天 0：否 1：是
     */
    private int isOneDay;
    
    /**
     * 是否是入住前半小时到入住结束 0：否 1：是
     */
    private int isHalfHoure;
    
    /**
     * 用户智能锁密码
     */
    private String persistPswd;
    
    /**
     * 智能锁密码状态 0:下发中,1:下发失败,2:下发成功,3:关闭 4：异常 5：过期 6:去获取门锁
     */
    private Integer persistPswdStatus;

    /**
     * 智能锁 用户临时密码
     */
    private String dynaPswd;
    
    /**
     * 智能锁  动态密码失效时间
     */
    private Date dynaExpiredTime;
    /**
     * 动态密码获取次数
     */
    private Integer dynaNum;
    
    /**
     * 智能锁  动态密码失效时间
     */
    private String dynaExpiredTimeStr;
    
    
    /**分享链接*/
	private String shareLinks;
	
	
	/**
	 * 订单详情金额
	 */
	private OrderDetailFeeResponse detailFee;
	
	/**
	 * 普通活动金额
	 */
	private Integer actMoney;
	
	

	/**
	 * @return the actMoney
	 */
	public Integer getActMoney() {
		return actMoney;
	}

	/**
	 * @param actMoney the actMoney to set
	 */
	public void setActMoney(Integer actMoney) {
		this.actMoney = actMoney;
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public String getDynaExpiredTimeStr() {
		return dynaExpiredTimeStr;
	}

	public void setDynaExpiredTimeStr(String dynaExpiredTimeStr) {
		this.dynaExpiredTimeStr = dynaExpiredTimeStr;
	}

	public Integer getDynaNum() {
		return dynaNum;
	}

	public void setDynaNum(Integer dynaNum) {
		this.dynaNum = dynaNum;
	}

	public Date getDynaExpiredTime() {
		return dynaExpiredTime;
	}

	public void setDynaExpiredTime(Date dynaExpiredTime) {
		this.dynaExpiredTime = dynaExpiredTime;
	}

	public int getIsLock() {
		return isLock;
	}

	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}

	public int getIsOneDay() {
		return isOneDay;
	}

	public void setIsOneDay(int isOneDay) {
		this.isOneDay = isOneDay;
	}

	public int getIsHalfHoure() {
		return isHalfHoure;
	}

	public void setIsHalfHoure(int isHalfHoure) {
		this.isHalfHoure = isHalfHoure;
	}

	public String getPersistPswd() {
		return persistPswd;
	}

	public void setPersistPswd(String persistPswd) {
		this.persistPswd = persistPswd;
	}

	

	public Integer getPersistPswdStatus() {
		return persistPswdStatus;
	}

	public void setPersistPswdStatus(Integer persistPswdStatus) {
		this.persistPswdStatus = persistPswdStatus;
	}

	public String getDynaPswd() {
		return dynaPswd;
	}

	public void setDynaPswd(String dynaPswd) {
		this.dynaPswd = dynaPswd;
	}

	public Integer getSmartLockSta() {
		return smartLockSta;
	}

	public void setSmartLockSta(Integer smartLockSta) {
		this.smartLockSta = smartLockSta;
	}

	public String getLandlordMobile() {
        return landlordMobile;
    }

    public void setLandlordMobile(String landlordMobile) {
        this.landlordMobile = landlordMobile;
    }

    public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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
	
	public List<UsualContactVo> getUsualContactList() {
		return usualContactList;
	}

	public void setUsualContactList(List<UsualContactVo> usualContactList) {
		this.usualContactList = usualContactList;
	}

	public Integer getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(Integer depositMoney) {
		this.depositMoney = depositMoney;
	}

	public Integer getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Integer discountMoney) {
		this.discountMoney = discountMoney;
	}


	public Integer getNeedPay() {
		return needPay;
	}

	public void setNeedPay(Integer needPay) {
		this.needPay = needPay;
	}

	public Integer getRentalMoney() {
		return rentalMoney;
	}

	public void setRentalMoney(Integer rentalMoney) {
		this.rentalMoney = rentalMoney;
	}

	public Integer getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(Integer couponMoney) {
		this.couponMoney = couponMoney;
	}

	public Integer getUserCommMoney() {
		return userCommMoney;
	}

	public void setUserCommMoney(Integer userCommMoney) {
		this.userCommMoney = userCommMoney;
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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getAccountsStatus() {
		return accountsStatus;
	}

	public void setAccountsStatus(Integer accountsStatus) {
		this.accountsStatus = accountsStatus;
	}

	public Integer getPreFreeHour() {
		return preFreeHour;
	}

	public void setPreFreeHour(Integer preFreeHour) {
		this.preFreeHour = preFreeHour;
	}

	public Integer getOrderStatusShowCode() {
		return orderStatusShowCode;
	}

	public void setOrderStatusShowCode(Integer orderStatusShowCode) {
		this.orderStatusShowCode = orderStatusShowCode;
	}

	public Integer getPenaltyMoney() {
		return penaltyMoney;
	}

	public void setPenaltyMoney(Integer penaltyMoney) {
		this.penaltyMoney = penaltyMoney;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getOtherMoney() {
		return otherMoney;
	}

	public void setOtherMoney(Integer otherMoney) {
		this.otherMoney = otherMoney;
	}

	public Integer getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(Integer refundMoney) {
		this.refundMoney = refundMoney;
	}

	public String getOtherMoneyDes() {
		return otherMoneyDes;
	}

	public void setOtherMoneyDes(String otherMoneyDes) {
		this.otherMoneyDes = otherMoneyDes;
	}

	public Integer getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(Integer realMoney) {
		this.realMoney = realMoney;
	}

	public Integer getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getShareLinks() {
		return shareLinks;
	}

	public void setShareLinks(String shareLinks) {
		this.shareLinks = shareLinks;
	}

	public OrderDetailFeeResponse getDetailFee() {
		return detailFee;
	}

	public void setDetailFee(OrderDetailFeeResponse detailFee) {
		this.detailFee = detailFee;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
	
	/**
	 * @return the rentWayName
	 */
	public String getRentWayName() {
		return rentWayName;
	}

	/**
	 * @param rentWayName the rentWayName to set
	 */
	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}
}
