package com.ziroom.minsu.report.house.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/2
 * @version 1.0
 * @since 1.0
 */
public class HouseEvaluateRequest extends PageRequest {

    private static final long serialVersionUID = 4771635169907265937L;
    private Integer rentWay;
    private Integer houseStatus;
    private String nationCode;
    private String regionName;
    private String cityCode;
    private String putawayBeginTime;
    private String putawayEndTime;
    private String beginTime;
    private String endTime;

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPutawayBeginTime() {
        return putawayBeginTime;
    }

    public void setPutawayBeginTime(String putawayBeginTime) {
        this.putawayBeginTime = putawayBeginTime;
    }

    public String getPutawayEndTime() {
        return putawayEndTime;
    }

    public void setPutawayEndTime(String putawayEndTime) {
        this.putawayEndTime = putawayEndTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
