package com.zra.common.dto.business;

import java.util.Date;

/**
 * 商机dto
 * @author wangws21 2016-8-2
 */
public class BusinessDto {
    /*业务id*/
    private String businessBid;
    /*客户bid*/
    private String customerId;
    /*创建时间*/
    private Date createTime;
    /*操作人*/
    private String updaterId;
    /*商机阶段 1：待约看 2：待带看 3：待回访 4：成交 5：未成交*/
    private Byte step;
    /*截止时间*/
    private Date endTime;
    /*处理状态（处理进度）  1：超时未处理  2：今日待办  3：次日待办  4：周内待办  5:7天后待办  6：完成*/
    private Byte handState;
    /*约看留言*/
    private String message;
    /*意向项目*/
    private String projectId;
    /*意向房型*/
    private String houseTypeId;
    /*来源渠道——产品*/
    private Byte source;
    /*来源渠道 1：M站 2：客户端 3:400来电 4：网站 5：其他*/
    private String sourceZra;
    /*期望约看时间*/
    private Date expectTime;
    /*期望入住最早时间*/
    private Date expectInStartTime;
    /*期望入住最晚时间*/
    private Date expectInEndTime;
    /*期望最低价格*/
    private Double expectLowPrice;
    /*期望最高价格*/
    private Double expectHighPrice;
    /*商机分类*/
    private Byte type;
    /*商机备注*/
    private String remark;
    /*zo管家bid*/
    private String zoId;
    /*zo管家姓名*/
    private String zoName;

    public String getBusinessBid() {
        return businessBid;
    }

    public void setBusinessBid(String businessBid) {
        this.businessBid = businessBid == null ? null : businessBid.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public Byte getStep() {
        return step;
    }

    public void setStep(Byte step) {
        this.step = step;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getHandState() {
        return handState;
    }

    public void setHandState(Byte handState) {
        this.handState = handState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
        this.houseTypeId = houseTypeId == null ? null : houseTypeId.trim();
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public String getSourceZra() {
        return sourceZra;
    }

    public void setSourceZra(String sourceZra) {
        this.sourceZra = sourceZra;
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public Date getExpectInStartTime() {
        return expectInStartTime;
    }

    public void setExpectInStartTime(Date expectInStartTime) {
        this.expectInStartTime = expectInStartTime;
    }

    public Date getExpectInEndTime() {
        return expectInEndTime;
    }

    public void setExpectInEndTime(Date expectInEndTime) {
        this.expectInEndTime = expectInEndTime;
    }

    public Double getExpectLowPrice() {
        return expectLowPrice;
    }

    public void setExpectLowPrice(Double expectLowPrice) {
        this.expectLowPrice = expectLowPrice;
    }

    public Double getExpectHighPrice() {
        return expectHighPrice;
    }

    public void setExpectHighPrice(Double expectHighPrice) {
        this.expectHighPrice = expectHighPrice;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getZoId() {
        return zoId;
    }

    public void setZoId(String zoId) {
        this.zoId = zoId == null ? null : zoId.trim();
    }

    public String getZoName() {
        return zoName;
    }

    public void setZoName(String zoName) {
        this.zoName = zoName == null ? null : zoName.trim();
    }
}