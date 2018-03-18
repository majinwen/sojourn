package com.ziroom.minsu.report.house.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>房源摄影记录request</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/5
 * @version 1.0
 * @since 1.0
 */
public class HouseGrapherRequest extends PageRequest {

    private static final long serialVersionUID = 4771635169907265937L;
    private Integer rentWay;
    private Integer houseStatus;
    private String nationCode;
    private String regionFid;
    private String cityCode;
    private String deployBeginTime;
    private String deployEndTime;

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

    public String getRegionFid() {
        return regionFid;
    }

    public void setRegionFid(String regionFid) {
        this.regionFid = regionFid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDeployBeginTime() {
        return deployBeginTime;
    }

    public void setDeployBeginTime(String deployBeginTime) {
        this.deployBeginTime = deployBeginTime;
    }

    public String getDeployEndTime() {
        return deployEndTime;
    }

    public void setDeployEndTime(String deployEndTime) {
        this.deployEndTime = deployEndTime;
    }
}
