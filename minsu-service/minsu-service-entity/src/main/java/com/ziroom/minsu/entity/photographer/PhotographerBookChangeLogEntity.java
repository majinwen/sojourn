package com.ziroom.minsu.entity.photographer;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class PhotographerBookChangeLogEntity extends BaseEntity {

    private static final long serialVersionUID = 2453997254257364786L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 预约摄影师订单编号
     */
    private String bookOrderSn;

    /**
     * 更改之前的摄影师uid
     */
    private String fromPhotographerUid;

    /**
     * 更改之后的摄影师uid
     */
    private String toPhotographerUid;

    /**
     * 创建人fid
     */
    private String createrFid;

    /**
     * 创建人类型（1=民宿管家）
     */
    private Integer createrType;

    /**
     * 是否删除(0=不删除 1=删除)
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date lastModifyDate;

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

    public String getBookOrderSn() {
        return bookOrderSn;
    }

    public void setBookOrderSn(String bookOrderSn) {
        this.bookOrderSn = bookOrderSn == null ? null : bookOrderSn.trim();
    }

    public String getFromPhotographerUid() {
        return fromPhotographerUid;
    }

    public void setFromPhotographerUid(String fromPhotographerUid) {
        this.fromPhotographerUid = fromPhotographerUid == null ? null : fromPhotographerUid.trim();
    }

    public String getToPhotographerUid() {
        return toPhotographerUid;
    }

    public void setToPhotographerUid(String toPhotographerUid) {
        this.toPhotographerUid = toPhotographerUid == null ? null : toPhotographerUid.trim();
    }

    public String getCreaterFid() {
        return createrFid;
    }

    public void setCreaterFid(String createrFid) {
        this.createrFid = createrFid == null ? null : createrFid.trim();
    }

    public Integer getCreaterType() {
        return createrType;
    }

    public void setCreaterType(Integer createrType) {
        this.createrType = createrType;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
}