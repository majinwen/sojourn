package com.ziroom.minsu.report.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.List;

/**
 * <p>刷单信息报表请求参数</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/3/14 9:55
 * @version 1.0
 * @since 1.0
 */
public class OrderFreshRequest extends PageRequest {

    private static final long serialVersionUID = 530643034743448229L;

    private List<String> cityList;

    private String region;
    private String city;
    private String orderNumStart;
    private String orderNumEnd;
    private Double couponMoneyAllStart;
    private Double couponMoneyAllEnd;
    private String createStartTime;
    private String createEndTime;

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getOrderNumStart() {
        return orderNumStart;
    }

    public void setOrderNumStart(String orderNumStart) {
        this.orderNumStart = orderNumStart;
    }

    public String getOrderNumEnd() {
        return orderNumEnd;
    }

    public void setOrderNumEnd(String orderNumEnd) {
        this.orderNumEnd = orderNumEnd;
    }

    public Double getCouponMoneyAllStart() {
        return couponMoneyAllStart;
    }

    public void setCouponMoneyAllStart(Double couponMoneyAllStart) {
        this.couponMoneyAllStart = couponMoneyAllStart;
    }

    public Double getCouponMoneyAllEnd() {
        return couponMoneyAllEnd;
    }

    public void setCouponMoneyAllEnd(Double couponMoneyAllEnd) {
        this.couponMoneyAllEnd = couponMoneyAllEnd;
    }
}
