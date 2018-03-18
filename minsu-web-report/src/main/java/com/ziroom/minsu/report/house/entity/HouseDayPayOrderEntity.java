package com.ziroom.minsu.report.house.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HouseDayPayOrderEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private Integer id;

    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 房东uid
     */
    private String landlordUid;

    /**
     * 出租方式
     */
    private Integer rentWay;

    /**
     * 城市code
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 支付订单量
     */
    private Integer payOrderNum;

    /**
     * 支付订单间夜
     */
    private Integer payOrderNight;

    /**
     * 支付订单租金
     */
    private Integer payOrderRent;

    /**
     * 支付订单押金
     */
    private Integer payOrderDeposit;

    /**
     * 支付订单服务费
     */
    private Integer payOrderService;

    /**
     * 支付订单总额
     */
    private Integer payOrderSum;

    /**
     * 支付订单提前预定天数
     */
    private Integer orderAdvanceDay;

    /**
     * 产生订单日期
     */
    private Date statisticsDate;

    /**
     * 创建日期
     */
    private Date createDate;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
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

    
    public String getLandlordUid() {
        return landlordUid;
    }

    
    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid == null ? null : landlordUid.trim();
    }

    
    public Integer getRentWay() {
        return rentWay;
    }

    
    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    
    public String getCityCode() {
        return cityCode;
    }

    
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    
    public String getCityName() {
        return cityName;
    }

    
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

   
    public Integer getPayOrderNum() {
        return payOrderNum;
    }

    
    public void setPayOrderNum(Integer payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    
    public Integer getPayOrderNight() {
        return payOrderNight;
    }

   
    public void setPayOrderNight(Integer payOrderNight) {
        this.payOrderNight = payOrderNight;
    }

   
    public Integer getPayOrderRent() {
        return payOrderRent;
    }

   
    public void setPayOrderRent(Integer payOrderRent) {
        this.payOrderRent = payOrderRent;
    }

   
    public Integer getPayOrderDeposit() {
        return payOrderDeposit;
    }

    
    public void setPayOrderDeposit(Integer payOrderDeposit) {
        this.payOrderDeposit = payOrderDeposit;
    }

   
    public Integer getPayOrderService() {
        return payOrderService;
    }

    
    public void setPayOrderService(Integer payOrderService) {
        this.payOrderService = payOrderService;
    }

    
    public Integer getPayOrderSum() {
        return payOrderSum;
    }

    public void setPayOrderSum(Integer payOrderSum) {
        this.payOrderSum = payOrderSum;
    }

    
    public Integer getOrderAdvanceDay() {
        return orderAdvanceDay;
    }

    
    public void setOrderAdvanceDay(Integer orderAdvanceDay) {
        this.orderAdvanceDay = orderAdvanceDay;
    }

    
    public Date getStatisticsDate() {
        return statisticsDate;
    }

   
    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    
    public Date getCreateDate() {
        return createDate;
    }

    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}