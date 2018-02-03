package com.ziroom.minsu.services.basedata.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/7
 */
public class CityArchivesVo extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -4625177728895304166L;

    //城市档案fid
    private String fid;

    //城市
    private String cityCode;

    //城市名称
    private String showName;

    //是否有档案 0:否 1:是
    private Integer isFile;

    //操作人
    private String fullName;

    //修改日期
    private Date lastModifyDate;
    /** 景点商圈内容开始*/
    // 名称
    private String regionName;

    //商圈景点描述
    private String hotRegionBrief;

    /** 推荐项内容开始*/
    //推荐项fid
    private String itemFid;

    //商圈景点fid
    private String hotRegionFid;

    //推荐项名称
    private String itemName;

    //操作人
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getItemFid() {
        return itemFid;
    }

    public void setItemFid(String itemFid) {
        this.itemFid = itemFid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    //商圈景点档案
    private String hotRegionContent;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getHotRegionFid() {
        return hotRegionFid;
    }

    public void setHotRegionFid(String hotRegionFid) {
        this.hotRegionFid = hotRegionFid;
    }

    public String getHotRegionBrief() {
        return hotRegionBrief;
    }

    public void setHotRegionBrief(String hotRegionBrief) {
        this.hotRegionBrief = hotRegionBrief;
    }

    public String getHotRegionContent() {
        return hotRegionContent;
    }

    public void setHotRegionContent(String hotRegionContent) {
        this.hotRegionContent = hotRegionContent;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getIsFile() {
        return isFile;
    }

    public void setIsFile(Integer isFile) {
        this.isFile = isFile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    @Override
    public String toString() {
        return "CityArchivesVo{" +
                "fid='" + fid + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", showName='" + showName + '\'' +
                ", isFile=" + isFile +
                ", fullName='" + fullName + '\'' +
                ", lastModifyDate=" + lastModifyDate +
                ", regionName='" + regionName + '\'' +
                ", hotRegionFid='" + hotRegionFid + '\'' +
                ", hotRegionBrief='" + hotRegionBrief + '\'' +
                ", hotRegionContent='" + hotRegionContent + '\'' +
                '}';
    }
}
