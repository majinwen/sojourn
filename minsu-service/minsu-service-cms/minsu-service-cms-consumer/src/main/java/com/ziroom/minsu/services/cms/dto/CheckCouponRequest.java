package com.ziroom.minsu.services.cms.dto;


import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 校验当前优惠券的使用条件
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class CheckCouponRequest extends OutCouponRequest {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 674418242340830260L;

    /** 城市编码 */
    private String cityCode;

    /** 当前的价格 */
    private Integer price = 0;

    /** 入住时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 房源的价格 */
    Map<String,Integer> allPriceMap;

    /** 房源编号 */
    private String houseSn;

    /** 房租折扣 */
    private Double rentCut;

    /** 清洁费 */
    private Integer cleanMoney;

    /** 用户佣金 */
    private Integer userCommission;

    /** 用户佣金比例 */
    private Double userCommissionRate;

    public Double getUserCommissionRate() {
        return userCommissionRate;
    }

    public void setUserCommissionRate(Double userCommissionRate) {
        this.userCommissionRate = userCommissionRate;
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

    public Double getRentCut() {
        return rentCut;
    }

    public void setRentCut(Double rentCut) {
        this.rentCut = rentCut;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Map<String, Integer> getAllPriceMap() {
        return allPriceMap;
    }

    public void setAllPriceMap(Map<String, Integer> allPriceMap) {
        this.allPriceMap = allPriceMap;
    }

    public Integer getUserCommission() {
        return userCommission;
    }

    public void setUserCommission(Integer userCommission) {
        this.userCommission = userCommission;
    }
}
