package com.ziroom.minsu.entity.customer;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class CustomerBlackEntity extends BaseEntity {
    private static final long serialVersionUID = 8345756388821194535L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 逻辑主键
     */
    private String fid;

    /**
     * 用户uid 黑名单用户uid
     */
    private String uid;

    /**
     * imei
     */
    private String imei;

    /**
     * 黑名单类型  0=所有 1=房东 2=房客
     */
    private Integer blackType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 跟进人
     */
    private String followUser;

    /**
     * 跟进状态
     * @see com.ziroom.minsu.valenum.order.FollowStatusEnum
     */
    private String followStatus;


    /**
     * 创建人fid
     */
    private String createFid;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0=未删除  1=已删除
     */
    private Integer isDel;

    private boolean selectImei;

    public String getFollowUser() {
        return followUser;
    }

    public void setFollowUser(String followUser) {
        this.followUser = followUser;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
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

    public Integer getBlackType() {
        return blackType;
    }

    public void setBlackType(Integer blackType) {
        this.blackType = blackType;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public boolean getSelectImei() {
        return selectImei;
    }

    public void setSelectImei(boolean selectImei) {
        this.selectImei = selectImei;
    }
}