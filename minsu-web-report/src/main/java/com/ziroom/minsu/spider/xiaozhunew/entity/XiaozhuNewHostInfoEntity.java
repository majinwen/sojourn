package com.ziroom.minsu.spider.xiaozhunew.entity;

import java.util.Date;

public class XiaozhuNewHostInfoEntity {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 房东编号
     */
    private String hostSn;

    /**
     * 名字
     */
    private String hostName;

    /**
     * 房东url
     */
    private String detailUrl;

    /**
     * 房东信息
     */
    private String hostInfo;

    /**
     * 房源数量
     */
    private Integer houseCount;

    /**
     * 在线回复率
     */
    private Double onlineReplyRate;

    /**
     * 评价数量
     */
    private Integer reviewsCount;

    /**
     * 评价分数
     */
    private Float reviewsScore;

    /**
     * 订单数量
     */
    private Integer orderCount;

    /**
     * 平均确认时间
     */
    private String aveSureTime;

    /**
     * 订单接受率
     */
    private Double orderAcceptRate;

    /**
     * 创建日期
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

    public String getHostSn() {
        return hostSn;
    }

    public void setHostSn(String hostSn) {
        this.hostSn = hostSn == null ? null : hostSn.trim();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName == null ? null : hostName.trim();
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl == null ? null : detailUrl.trim();
    }

    public String getHostInfo() {
        return hostInfo;
    }

    public void setHostInfo(String hostInfo) {
        this.hostInfo = hostInfo == null ? null : hostInfo.trim();
    }

    public Integer getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(Integer houseCount) {
        this.houseCount = houseCount;
    }

    public Double getOnlineReplyRate() {
        return onlineReplyRate;
    }

    public void setOnlineReplyRate(Double onlineReplyRate) {
        this.onlineReplyRate = onlineReplyRate;
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public Float getReviewsScore() {
        return reviewsScore;
    }

    public void setReviewsScore(Float reviewsScore) {
        this.reviewsScore = reviewsScore;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getAveSureTime() {
        return aveSureTime;
    }

    public void setAveSureTime(String aveSureTime) {
        this.aveSureTime = aveSureTime == null ? null : aveSureTime.trim();
    }

    public Double getOrderAcceptRate() {
        return orderAcceptRate;
    }

    public void setOrderAcceptRate(Double orderAcceptRate) {
        this.orderAcceptRate = orderAcceptRate;
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