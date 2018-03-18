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

public class OrderContactVo extends BaseEntity {
    /**
     *
     */
    @FieldMeta(skip = true)
    private static final long serialVersionUID = 1449723418011236882L;

    @FieldMeta(name = "国家", order = 1)
    private String nationName;

    @FieldMeta(name = "大区", order = 2)
    private String regionName;

    @FieldMeta(name = "城市", order = 3)
    private String cityName;

    @FieldMeta(skip = true)
    private String cityCode;

    @FieldMeta(name = "订单号", order = 4)
    private String orderSn;

    @FieldMeta(name = "房客姓名", order = 4)
    private String userName;

    @FieldMeta(name = "房客电话", order = 4)
    private String userTel;

    @FieldMeta(name = "房源编号", order = 4)
    private String houseSn;

    @FieldMeta(name = "房东姓名", order = 4)
    private String landName;

    @FieldMeta(name = "房东电话", order = 4)
    private String landTel;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
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

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getLandTel() {
        return landTel;
    }

    public void setLandTel(String landTel) {
        this.landTel = landTel;
    }
}

