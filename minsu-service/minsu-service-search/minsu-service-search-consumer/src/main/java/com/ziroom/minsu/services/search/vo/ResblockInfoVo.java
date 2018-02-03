package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>楼盘信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/29.
 * @version 1.0
 * @since 1.0
 */
public class ResblockInfoVo extends BaseEntity {

    /** 序列化id */
    private static final long serialVersionUID = -2147121231215641L;


    /** id */
    private String standardId;

    /** 交通信息 */
    private String trafficInfo;

    /** 是否聚焦 60010002非聚焦 60010001 聚焦 */
    private String isFocus;

    /** 楼盘名字 */
    private String resblockName;

    /** 物业地址 */
    private String propertyAddress;

    /** 行政地址 */
    private String executiveAddress;

    /** 楼盘房屋数量 */
    private String houseAmount;

    /** id */
    private String zrStandardId;

    /** 城市编号 */
    private String territoryCode;

    /** 商圈名字 */
    private String districtName;

    /** 商圈id */
    private String districtId;

    /** id */
    private Date lastModifyDate;

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    /** 周边信息 */
    private String surrounding;


    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getTrafficInfo() {
        return trafficInfo;
    }

    public void setTrafficInfo(String trafficInfo) {
        this.trafficInfo = trafficInfo;
    }

    public String getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus;
    }

    public String getResblockName() {
        return resblockName;
    }

    public void setResblockName(String resblockName) {
        this.resblockName = resblockName;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getExecutiveAddress() {
        return executiveAddress;
    }

    public void setExecutiveAddress(String executiveAddress) {
        this.executiveAddress = executiveAddress;
    }

    public String getHouseAmount() {
        return houseAmount;
    }

    public void setHouseAmount(String houseAmount) {
        this.houseAmount = houseAmount;
    }

    public String getZrStandardId() {
        return zrStandardId;
    }

    public void setZrStandardId(String zrStandardId) {
        this.zrStandardId = zrStandardId;
    }

    public String getTerritoryCode() {
        return territoryCode;
    }

    public void setTerritoryCode(String territoryCode) {
        this.territoryCode = territoryCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSurrounding() {
        return surrounding;
    }

    public void setSurrounding(String surrounding) {
        this.surrounding = surrounding;
    }
}
