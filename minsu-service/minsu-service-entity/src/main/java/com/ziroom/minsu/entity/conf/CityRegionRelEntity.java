package com.ziroom.minsu.entity.conf;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class CityRegionRelEntity extends BaseEntity{
    private static final long serialVersionUID = 7058347337396663215L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑主键
     */
    private String fid;

    /**
     * 大区fid
     */
    private String regionFid;
    /**
     * 国家code
     */
    private String countryCode;
    /**
     * 省份code
     */
    private String provinceCode;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 是否删除
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getRegionFid() {
        return regionFid;
    }

    public void setRegionFid(String regionFid) {
        this.regionFid = regionFid == null ? null : regionFid.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}