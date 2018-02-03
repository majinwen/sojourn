package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class HouseMonthRevenueEntity extends BaseEntity{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4763867920744361175L;

	/**
	 * 主键
	 */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 房源逻辑id
     */
    private String houseBaseFid;

    /**
     * 月 MM
     */
    private Integer statisticsDateMonth;

    /**
     * 年 yyyy
     */
    private Integer statisticsDateYear;

    /**
     * 房源月收益
     */
    private Integer monthRevenue = 0;

    /**
     * 收益类型
     */
    private Integer revenueType;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

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

    public String getHouseBaseFid() {
        return houseBaseFid;
    }

    public void setHouseBaseFid(String houseBaseFid) {
        this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
    }

    public Integer getStatisticsDateMonth() {
        return statisticsDateMonth;
    }

    public void setStatisticsDateMonth(Integer statisticsDateMonth) {
        this.statisticsDateMonth = statisticsDateMonth;
    }

    public Integer getStatisticsDateYear() {
        return statisticsDateYear;
    }

    public void setStatisticsDateYear(Integer statisticsDateYear) {
        this.statisticsDateYear = statisticsDateYear;
    }

    public Integer getMonthRevenue() {
        return monthRevenue;
    }

    public void setMonthRevenue(Integer monthRevenue) {
        this.monthRevenue = monthRevenue;
    }

    public Integer getRevenueType() {
        return revenueType;
    }

    public void setRevenueType(Integer revenueType) {
        this.revenueType = revenueType;
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