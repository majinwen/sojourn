package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;
import org.apache.solr.client.solrj.beans.Field;

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
 * @author afi on 2016/4/13.
 * @version 1.0
 * @since 1.0
 */
public class CommunityInfo extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -21454256564544521L;

    /**
     * id
     */
    @Field
    private String id;

    /**
     * 小区民称
     */
    @Field
    private String communityName;

    /**
     * 1：房源 2：链家
     */
    @Field
    private Integer sourceType;

    /**
     * 区域名称
     */
    @Field
    private String areaName;

    /**
     * 周边信息交通情况
     */
    @Field
    private String houseAroundDesc = "周边信息交通情况";

    /**
     * 描述信息
     */
    @Field
    private String houseDesc= "描述信息";

    /**
     * 街道
     */
    @Field
    private String houseStreet = "街道地址";

    /**
     * 经度
     */
    @Field
    private String longitude;

    /**
     * 纬度
     */
    @Field
    private String latitude;


    /**
     * 城市code
     */
    @Field
    private String cityCode = "";

    /**
     * 创建时间
     */
    @Field
    private Long createTime = new Date().getTime();


    public String getHouseDesc() {
        return houseDesc;
    }

    public void setHouseDesc(String houseDesc) {
        this.houseDesc = houseDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getHouseAroundDesc() {
        return houseAroundDesc;
    }

    public void setHouseAroundDesc(String houseAroundDesc) {
        this.houseAroundDesc = houseAroundDesc;
    }

    public String getHouseStreet() {
        return houseStreet;
    }

    public void setHouseStreet(String houseStreet) {
        this.houseStreet = houseStreet;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
