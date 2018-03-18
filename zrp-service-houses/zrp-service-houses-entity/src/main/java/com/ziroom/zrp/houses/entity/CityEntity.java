package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class CityEntity extends BaseEntity{
    /**
     * 主键
     */
    private String fid;

    /**
     * 城市编码--在实际进行关联操作时，使用的是fid字段
     */
    private String citycode;

    /**
     * 城市名称
     */
    private String cityname;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 修改时间
     */
    private Date fupdatetime;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 长租合同前缀
     */
    private String flongcode;

    /**
     * 短租合同前缀
     */
    private String fshortcode;

    /**
     * 收款单前缀
     */
    private String fvoucher;

    /**
     * 消费单前缀
     */
    private String fconsum;

    /**
     * 生成随机数的序列名
     */
    private String fnextvalname;

    /**
     * AFA系统对应公司代码
     */
    private String fafacomcode;

    /**
     * 城市中心经度
     */
    private Double centerLon;

    /**
     * 城市中心纬度
     */
    private Double centerLat;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public String getFlongcode() {
        return flongcode;
    }

    public void setFlongcode(String flongcode) {
        this.flongcode = flongcode == null ? null : flongcode.trim();
    }

    public String getFshortcode() {
        return fshortcode;
    }

    public void setFshortcode(String fshortcode) {
        this.fshortcode = fshortcode == null ? null : fshortcode.trim();
    }

    public String getFvoucher() {
        return fvoucher;
    }

    public void setFvoucher(String fvoucher) {
        this.fvoucher = fvoucher == null ? null : fvoucher.trim();
    }

    public String getFconsum() {
        return fconsum;
    }

    public void setFconsum(String fconsum) {
        this.fconsum = fconsum == null ? null : fconsum.trim();
    }

    public String getFnextvalname() {
        return fnextvalname;
    }

    public void setFnextvalname(String fnextvalname) {
        this.fnextvalname = fnextvalname == null ? null : fnextvalname.trim();
    }

    public String getFafacomcode() {
        return fafacomcode;
    }

    public void setFafacomcode(String fafacomcode) {
        this.fafacomcode = fafacomcode == null ? null : fafacomcode.trim();
    }

    public Double getCenterLon() {
        return centerLon;
    }

    public void setCenterLon(Double centerLon) {
        this.centerLon = centerLon;
    }

    public Double getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(Double centerLat) {
        this.centerLat = centerLat;
    }
}