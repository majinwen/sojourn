package com.ziroom.minsu.api.evaluate.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价详情 Vo
 * @author jixd
 * @created 2017年02月08日 10:12:34
 */
public class EvaluateDetailVo implements Serializable{

    private static final long serialVersionUID = 8084282539032621646L;
    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 标题1
     */
    private String titleName;
    /**
     * 标题2
     */
    private String titleTime;
    /**
     * 标题3
     */
    private String titleTips;
    /**
     * 房源名称
     */
    private String houseName;
    /**
     * 开始时间
     */
    private String startTimeStr;
    /**
     * 结束时间
     */
    private String endTimeStr;
    /**
     *间夜
     */
    private Integer housingDay;
    /**
     * 预订人
     */
    private String userName;
    /**
     * 房东名字
     */
    private String landlordName;
    /**
     * 订单详情链接
     */
    private String orderDetailUrl;
    /**
     * 头像
     */
    private String picUrl;
    /**
     * 入住人数
     */
    private Integer peopleNum;
    /**
     * 评价状态
     */
    private Integer evaStatus;
    /**
     * 评价状态 0：不可评价，1：写评价，2：写公开回复
     */
    private Integer pjStatus;
    /**
     * 评价按钮
     */
    private String pjButton;
    /**
     * 房客评价
     */
    private UserEvaInfo  userEvaInfo;
    /**
     * 房东评价
     */
    private LanEvaInfo lanEvaInfo;
    /**
     * 房东回复
     */
    private LanRepInfo lanRepInfo;

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleTime() {
        return titleTime;
    }

    public void setTitleTime(String titleTime) {
        this.titleTime = titleTime;
    }

    public String getTitleTips() {
        return titleTips;
    }

    public void setTitleTips(String titleTips) {
        this.titleTips = titleTips;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Integer getHousingDay() {
        return housingDay;
    }

    public void setHousingDay(Integer housingDay) {
        this.housingDay = housingDay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getOrderDetailUrl() {
        return orderDetailUrl;
    }

    public void setOrderDetailUrl(String orderDetailUrl) {
        this.orderDetailUrl = orderDetailUrl;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getEvaStatus() {
        return evaStatus;
    }

    public void setEvaStatus(Integer evaStatus) {
        this.evaStatus = evaStatus;
    }

    public Integer getPjStatus() {
        return pjStatus;
    }

    public void setPjStatus(Integer pjStatus) {
        this.pjStatus = pjStatus;
    }

    public String getPjButton() {
        return pjButton;
    }

    public void setPjButton(String pjButton) {
        this.pjButton = pjButton;
    }

    public UserEvaInfo getUserEvaInfo() {
        return userEvaInfo;
    }

    public void setUserEvaInfo(UserEvaInfo userEvaInfo) {
        this.userEvaInfo = userEvaInfo;
    }

    public LanEvaInfo getLanEvaInfo() {
        return lanEvaInfo;
    }

    public void setLanEvaInfo(LanEvaInfo lanEvaInfo) {
        this.lanEvaInfo = lanEvaInfo;
    }

    public LanRepInfo getLanRepInfo() {
        return lanRepInfo;
    }

    public void setLanRepInfo(LanRepInfo lanRepInfo) {
        this.lanRepInfo = lanRepInfo;
    }

    /**
     * 房客评价详情
     */
    public class UserEvaInfo{
        /**
         * 评价内容
         */
        private String content;
        /**
         * 清洁度
         */
        private Integer houseClean;
        /**
         * 描述相符
         */
        private Integer descriptionMatch;
        /**
         * 周边环境
         */
        private Integer trafficPosition;

        /**
         * 房东印象
         */
        private Integer safetyDegree;

        /**
         * 性价比
         */
        private Integer costPerformance;
        /**
         * 评论时间
         */
        private String createTime;


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getHouseClean() {
            return houseClean;
        }

        public void setHouseClean(Integer houseClean) {
            this.houseClean = houseClean;
        }

        public Integer getDescriptionMatch() {
            return descriptionMatch;
        }

        public void setDescriptionMatch(Integer descriptionMatch) {
            this.descriptionMatch = descriptionMatch;
        }

        public Integer getTrafficPosition() {
            return trafficPosition;
        }

        public void setTrafficPosition(Integer trafficPosition) {
            this.trafficPosition = trafficPosition;
        }

        public Integer getSafetyDegree() {
            return safetyDegree;
        }

        public void setSafetyDegree(Integer safetyDegree) {
            this.safetyDegree = safetyDegree;
        }

        public Integer getCostPerformance() {
            return costPerformance;
        }

        public void setCostPerformance(Integer costPerformance) {
            this.costPerformance = costPerformance;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }


    }

    /**
     * 房东评价详情
     */
    public class LanEvaInfo{

        private String content;

        private Integer landlordSatisfied;

        private String createTime;


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getLandlordSatisfied() {
            return landlordSatisfied;
        }

        public void setLandlordSatisfied(Integer landlordSatisfied) {
            this.landlordSatisfied = landlordSatisfied;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

    }

    /**
     * 房东回复详情
     */
    public class LanRepInfo{
        private String content;

        private String createTime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

    }
}
