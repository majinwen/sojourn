package com.ziroom.minsu.entity.customer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class CustomerCollectionEntity extends BaseEntity{

    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2027180305210826279L;
	
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 房源逻辑id
     */
    private String houseFid;

    /**
     * 出租方式 0：整租，1：合租
     */
    private Integer rentWay;

    /**
     * 房源名称 (冗余字段仅供已下线房源使用)
     */
    private String houseName;

    /**
     * 房东uid
     */
    private String landlordUid;

    /**
     * 房源图片基础地址 (冗余字段仅供已下线房源使用)
     */
    private String picBaseUrl;

    /**
     * 图片后缀 (冗余字段仅供已下线房源使用)
     */
    private String picSuffix;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid == null ? null : houseFid.trim();
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName == null ? null : houseName.trim();
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid == null ? null : landlordUid.trim();
    }

    public String getPicBaseUrl() {
        return picBaseUrl;
    }

    public void setPicBaseUrl(String picBaseUrl) {
        this.picBaseUrl = picBaseUrl == null ? null : picBaseUrl.trim();
    }

    public String getPicSuffix() {
        return picSuffix;
    }

    public void setPicSuffix(String picSuffix) {
        this.picSuffix = picSuffix == null ? null : picSuffix.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}