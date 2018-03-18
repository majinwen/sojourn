package com.ziroom.minsu.spider.xiaozhunew.entity;

import java.util.Date;

public class XiaozhuNewHousePriceEntity {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 房源编号
     */
    private String houseSn;

    /**
     * 房源价格
     */
    private Integer housePrice;

    /**
     * 价格类型
     */
    private String priceType;

    /**
     * 是否可租
     */
    private String available;

    /**
     * 配置日期
     */
    private Date date;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn == null ? null : houseSn.trim();
    }

    public Integer getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(Integer housePrice) {
        this.housePrice = housePrice;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType == null ? null : priceType.trim();
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available == null ? null : available.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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