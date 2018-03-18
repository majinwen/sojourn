package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>OrderFreshVo</p>
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

public class OrderFreshVo extends BaseEntity {

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

    @FieldMeta(name = "房东UID", order = 4)
    private String landlordUid;

    @FieldMeta(name = "房源条数", order = 5)
    private String houseNum;

    @FieldMeta(name = "订单量", order = 6)
    private String orderNum;

    @FieldMeta(name = "优惠券订单量", order = 7)
    private String couponNum;

    @FieldMeta(name = "订单房租金额", order = 8)
    private String rentalMoneyAll;

    @FieldMeta(name = "优惠券金额", order = 9)
    private String couponMoneyAll;

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public String getRentalMoneyAll() {
        return rentalMoneyAll;
    }

    public void setRentalMoneyAll(String rentalMoneyAll) {
        this.rentalMoneyAll = rentalMoneyAll;
    }

    public String getCouponMoneyAll() {
        return couponMoneyAll;
    }

    public void setCouponMoneyAll(String couponMoneyAll) {
        this.couponMoneyAll = couponMoneyAll;
    }
}

