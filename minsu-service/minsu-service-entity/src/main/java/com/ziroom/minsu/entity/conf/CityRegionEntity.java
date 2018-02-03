package com.ziroom.minsu.entity.conf;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class CityRegionEntity extends BaseEntity{
    private static final long serialVersionUID = -1734136845724436240L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 大区名称
     */
    private String regionName;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 是否删除 0=否 1=是
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
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