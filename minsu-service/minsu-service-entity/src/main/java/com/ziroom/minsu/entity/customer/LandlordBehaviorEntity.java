package com.ziroom.minsu.entity.customer;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class LandlordBehaviorEntity extends BaseEntity{
    private static final long serialVersionUID = 1751782700477566087L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String fid;

    /**
     * 房东uid
     */
    private String landlordUid;

    /**
     * 咨询条数
     */
    private Integer advisoryNum;

    /**
     * 回复条数
     */
    private Integer replyNum;

    /**
     * 平均回复时长
     */
    private Integer replyTimeAvg;

    /**
     * 最长回复时间
     */
    private Integer replyTimeMax;

    /**
     * 最短回复时长
     */
    private Integer replyTimeMin;

    /**
     * 房客待评价条数
     */
    private Integer tenWaitEvaNum;

    /**
     * 已评价条数
     */
    private Integer tenHasEvaNum;

    /**
     * 房东待评价订单数
     */
    private Integer lanWaitEvaNum;

    /**
     * 房东已评价条数
     */
    private Integer lanHasEvaNum;

    /**
     * 订单总数
     */
    private Integer totalOrderNum;

    /**
     * 接受订单个数
     */
    private Integer acceptOrderNum;

    /**
     * 拒绝订单数量
     */
    private Integer refuseOrderNum;

    /**
     * 未处理订单数量
     */
    private Integer notdoOrderNum;


    /**
     * 房源数
     */
    private Integer houseNum;


    /**
     * 房源的sku
     */
    private Integer houseSkuNum;


    /**
     * 身份证的城市编码
     */
    private String cityCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
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

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid == null ? null : landlordUid.trim();
    }

    public Integer getAdvisoryNum() {
        return advisoryNum;
    }

    public void setAdvisoryNum(Integer advisoryNum) {
        this.advisoryNum = advisoryNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getReplyTimeAvg() {
        return replyTimeAvg;
    }

    public void setReplyTimeAvg(Integer replyTimeAvg) {
        this.replyTimeAvg = replyTimeAvg;
    }

    public Integer getReplyTimeMax() {
        return replyTimeMax;
    }

    public void setReplyTimeMax(Integer replyTimeMax) {
        this.replyTimeMax = replyTimeMax;
    }

    public Integer getReplyTimeMin() {
        return replyTimeMin;
    }

    public void setReplyTimeMin(Integer replyTimeMin) {
        this.replyTimeMin = replyTimeMin;
    }

    public Integer getTenWaitEvaNum() {
        return tenWaitEvaNum;
    }

    public void setTenWaitEvaNum(Integer tenWaitEvaNum) {
        this.tenWaitEvaNum = tenWaitEvaNum;
    }

    public Integer getTenHasEvaNum() {
        return tenHasEvaNum;
    }

    public void setTenHasEvaNum(Integer tenHasEvaNum) {
        this.tenHasEvaNum = tenHasEvaNum;
    }

    public Integer getLanWaitEvaNum() {
        return lanWaitEvaNum;
    }

    public void setLanWaitEvaNum(Integer lanWaitEvaNum) {
        this.lanWaitEvaNum = lanWaitEvaNum;
    }

    public Integer getLanHasEvaNum() {
        return lanHasEvaNum;
    }

    public void setLanHasEvaNum(Integer lanHasEvaNum) {
        this.lanHasEvaNum = lanHasEvaNum;
    }

    public Integer getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(Integer totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public Integer getAcceptOrderNum() {
        return acceptOrderNum;
    }

    public void setAcceptOrderNum(Integer acceptOrderNum) {
        this.acceptOrderNum = acceptOrderNum;
    }

    public Integer getRefuseOrderNum() {
        return refuseOrderNum;
    }

    public void setRefuseOrderNum(Integer refuseOrderNum) {
        this.refuseOrderNum = refuseOrderNum;
    }

    public Integer getNotdoOrderNum() {
        return notdoOrderNum;
    }

    public void setNotdoOrderNum(Integer notdoOrderNum) {
        this.notdoOrderNum = notdoOrderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }

    public Integer getHouseSkuNum() {
        return houseSkuNum;
    }

    public void setHouseSkuNum(Integer houseSkuNum) {
        this.houseSkuNum = houseSkuNum;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}