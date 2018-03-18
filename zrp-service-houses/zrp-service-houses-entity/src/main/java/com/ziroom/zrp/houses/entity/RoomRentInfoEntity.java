package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class RoomRentInfoEntity extends BaseEntity{
    private String fid;

    /**
     * 项目id
     */
    private String fprojectid;

    /**
     * 房间id
     */
    private String froomid;

    /**
     * 合同id
     */
    private String fcontractid;

    /**
     * 解约协议id
     */
    private String freleaseid;

    /**
     * 起租日期
     */
    private Date fstartdate;

    /**
     * 到期日期
     */
    private Date fenddate;

    /**
     * 离店日期
     */
    private Date fleavedate;

    /**
     * 出房价格
     */
    private Double frentprice;

    /**
     * 是否有效
     */
    private Integer fvalid;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 创建人id
     */
    private String fcreatorid;

    /**
     * 创建人name
     */
    private String fcreatorname;

    /**
     * 创建日期
     */
    private Date fcreatetime;

    /**
     * 关联城市表
     */
    private String cityid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getFprojectid() {
        return fprojectid;
    }

    public void setFprojectid(String fprojectid) {
        this.fprojectid = fprojectid == null ? null : fprojectid.trim();
    }

    public String getFroomid() {
        return froomid;
    }

    public void setFroomid(String froomid) {
        this.froomid = froomid == null ? null : froomid.trim();
    }

    public String getFcontractid() {
        return fcontractid;
    }

    public void setFcontractid(String fcontractid) {
        this.fcontractid = fcontractid == null ? null : fcontractid.trim();
    }

    public String getFreleaseid() {
        return freleaseid;
    }

    public void setFreleaseid(String freleaseid) {
        this.freleaseid = freleaseid == null ? null : freleaseid.trim();
    }

    public Date getFstartdate() {
        return fstartdate;
    }

    public void setFstartdate(Date fstartdate) {
        this.fstartdate = fstartdate;
    }

    public Date getFenddate() {
        return fenddate;
    }

    public void setFenddate(Date fenddate) {
        this.fenddate = fenddate;
    }

    public Date getFleavedate() {
        return fleavedate;
    }

    public void setFleavedate(Date fleavedate) {
        this.fleavedate = fleavedate;
    }

    public Double getFrentprice() {
        return frentprice;
    }

    public void setFrentprice(Double frentprice) {
        this.frentprice = frentprice;
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public String getFcreatorid() {
        return fcreatorid;
    }

    public void setFcreatorid(String fcreatorid) {
        this.fcreatorid = fcreatorid == null ? null : fcreatorid.trim();
    }

    public String getFcreatorname() {
        return fcreatorname;
    }

    public void setFcreatorname(String fcreatorname) {
        this.fcreatorname = fcreatorname == null ? null : fcreatorname.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }
}