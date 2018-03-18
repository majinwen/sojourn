package com.zra.system.entity;

import java.util.Date;

/**
 * <p>城市entity</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/7/29 18:54
 * @since 1.0
 */
public class CityEntity {
    /**
     * 主键
     */
    private String id;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 长租合同前缀
     */
    private String longCode;

    /**
     * 短租合同前缀
     */
    private String shortCode;

    /**
     * 收款单前缀
     */
    private String voucher;

    /**
     * 消费单前缀
     */
    private String consum;

    /**
     * 生成随机数的序列名
     */
    private String nextValName;

    /**
     * AFA系统对应公司代码
     */
    private String afaComCode;

    private Double centerLon;

    private Double centerLat;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getConsum() {
        return consum;
    }

    public void setConsum(String consum) {
        this.consum = consum;
    }

    public String getNextValName() {
        return nextValName;
    }

    public void setNextValName(String nextValName) {
        this.nextValName = nextValName;
    }

    public String getAfaComCode() {
        return afaComCode;
    }

    public void setAfaComCode(String afaComCode) {
        this.afaComCode = afaComCode;
    }
}