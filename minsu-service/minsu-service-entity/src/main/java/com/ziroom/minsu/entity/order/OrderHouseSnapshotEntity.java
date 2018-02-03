package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.valenum.house.RentWayEnum;

public class OrderHouseSnapshotEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2399971811068775507L;

	/** id */
    private Integer id;

    /** 订单编号*/
    private String orderSn;

    /** 订单编号*/
    private String houseSn;

    /** 房源 fid */
    private String houseFid;

    /** 房间fid */
    private String roomFid;

    /** 床 fid */
    private String bedFid;

    /** 房源名称 */
    private String houseName;

    /** 房间名称  */
    private String roomName;

    /** 床编号  */
    private String bedSn;

    /** 房源地址  */
    private String houseAddr;

    /** 图片路径 */
    private String picUrl;

    /**
     * 出租类型
     * @see RentWayEnum
     */
    private Integer rentWay;

    /** 出租价格 */
    private Integer price;

    /** 清洁费价格 */
    private Integer cleanMoney;


    /**
     * 订单类型
     * @see com.ziroom.minsu.valenum.house.OrderTypeEnum
     */
    private Integer orderType;

    /** 优惠规则 */
    private String discountRulesCode;

    /** 押金规则 */
    private String depositRulesCode;

    /** 退订政策 */
    private String checkOutRulesCode;

    /** 入住时间 值必须是标准的时间格式 12:00:00 */
    private String checkInTime;

    /** 退订时间 值必须是标准的时间格式 12:00:00 */
    private String checkOutTime;


    /**是否智能锁*/
    private Integer isLock;

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid == null ? null : houseFid.trim();
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid == null ? null : roomFid.trim();
    }

    public String getBedFid() {
        return bedFid;
    }

    public void setBedFid(String bedFid) {
        this.bedFid = bedFid == null ? null : bedFid.trim();
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

    public String getBedSn() {
        return bedSn;
    }

    public void setBedSn(String bedSn) {
        this.bedSn = bedSn;
    }

    public String getHouseAddr() {
        return houseAddr;
    }

    public void setHouseAddr(String houseAddr) {
        this.houseAddr = houseAddr == null ? null : houseAddr.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getDiscountRulesCode() {
        return discountRulesCode;
    }

    public void setDiscountRulesCode(String discountRulesCode) {
        this.discountRulesCode = discountRulesCode;
    }

    public String getDepositRulesCode() {
        return depositRulesCode;
    }

    public void setDepositRulesCode(String depositRulesCode) {
        this.depositRulesCode = depositRulesCode;
    }

    public String getCheckOutRulesCode() {
        return checkOutRulesCode;
    }

    public void setCheckOutRulesCode(String checkOutRulesCode) {
        this.checkOutRulesCode = checkOutRulesCode;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Integer getCleanMoney() {
        return cleanMoney;
    }

    public void setCleanMoney(Integer cleanMoney) {
        this.cleanMoney = cleanMoney;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn;
    }
}