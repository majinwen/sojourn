package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

import java.util.Date;

/**
 * <p>OrderInformationVo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan
 * @version 1.0
 * @since 1.0
 */

public class OrderInformationVo extends BaseEntity {
    /**
     *
     */
    @FieldMeta(skip = true)
    private static final long serialVersionUID = 1449723418011236882L;

    @FieldMeta(name = "国家", order = 1)
    private String nation = "";

    @FieldMeta(name = "大区", order = 2)
    private String regionName = "";

    @FieldMeta(name = "城市", order = 3)
    private String cityName = "";

    @FieldMeta(skip = true)
    private String cityCode;

    @FieldMeta(name = "订单号", order = 4)
    private String orderSn;

    @FieldMeta(skip = true)
    private String orderStatus;

    @FieldMeta(name = "订单状态", order = 5)
    private String orderStatusName;

    @FieldMeta(name = "预订人UID", order = 6)
    private String userUid;

    @FieldMeta(name = "预定时间", order = 7)
    private String createTime;

    @FieldMeta(name = "预计入住时间", order = 8)
    private Date startTime;

    @FieldMeta(name = "预计退租时间", order = 9)
    private Date endTime;

    @FieldMeta(name = "实际退租时间", order = 10)
    private Date realEndTime;

    @FieldMeta(name = "取消订单时间", order = 11)
    private Date cancelTime;

    @FieldMeta(skip = true)
    private String payStatus;

    @FieldMeta(name = "是否支付", order = 12)
    private String payStatusName;

    @FieldMeta(skip = true)
    private String rentWay;

    @FieldMeta(name = "出租类型", order = 14)
    private String rentWayName;

    @FieldMeta(skip = true)
    private String orderType;

    @FieldMeta(name = "预定类型", order = 15)
    private String orderTypeName;

    @FieldMeta(name = "房源编号", order = 16)
    private String houseSn = "";

    @FieldMeta(name = "房源fid", order = 17)
    private String houseFid;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getRentWay() {
        return rentWay;
    }

    public void setRentWay(String rentWay) {
        this.rentWay = rentWay;
    }

    public String getRentWayName() {
        return rentWayName;
    }

    public void setRentWayName(String rentWayName) {
        this.rentWayName = rentWayName;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn;
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }
}

