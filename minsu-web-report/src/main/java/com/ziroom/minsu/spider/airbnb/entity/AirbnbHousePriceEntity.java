package com.ziroom.minsu.spider.airbnb.entity;

import java.util.Date;

public class AirbnbHousePriceEntity {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 房源编号
     */
    private String houseSn;

    /**
     * 配置日期
     */
    private Date date;

    /**
     * 当地货币
     */
    private String localCurrency;

    /**
     * 当地货币价格
     */
    private Integer localPrice;

    /**
     * 国家货币
     */
    private String nativeCurrency;

    /**
     * 国家货币价格
     */
    private Integer nativePrice;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;
    
    /**
     * 是否可租 0：否，1：是
     */
    private Integer available;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency == null ? null : localCurrency.trim();
    }

    public Integer getLocalPrice() {
        return localPrice;
    }

    public void setLocalPrice(Integer localPrice) {
        this.localPrice = localPrice;
    }

    public String getNativeCurrency() {
        return nativeCurrency;
    }

    public void setNativeCurrency(String nativeCurrency) {
        this.nativeCurrency = nativeCurrency == null ? null : nativeCurrency.trim();
    }

    public Integer getNativePrice() {
        return nativePrice;
    }

    public void setNativePrice(Integer nativePrice) {
        this.nativePrice = nativePrice;
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

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}
    
}