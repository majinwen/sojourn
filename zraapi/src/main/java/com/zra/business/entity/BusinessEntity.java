package com.zra.business.entity;

import java.util.Date;

public class BusinessEntity {
    /*主键*/
    private Integer id;
    /*业务id*/
    private String businessBid;
    /*创建人*/
    private String createrId;
    /*创建时间*/
    private Date createTime;
    /*操作人*/
    private String updaterId;
    /*更新时间*/
    private Date updateTime;
    /*'是否删除(0:否,1:是)',*/
    private Byte isDel;
    /*城市id*/
    private String cityId;
    /*客户bid*/
    private String customerId;
    /*商机阶段 1：待约看 2：待带看 3：待回访 4：成交 5：未成交*/
    private Byte step;
    /*截止时间*/
    private Date endTime;
    /*处理状态（处理进度）  1：超时未处理  2：今日代办  3：次日代办  4：周内代办  5:7天后代办  6：完成*/
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
    /*modify by tianxf9 渠道统计启用该字段，用来存储渠道统计的bid*/
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
    
    //and by tianxf9
    /*400来电bid*/
    private String callBid;
    
	/*400来电是否接听 "Success"：已接听，"noanswer"：未接听*/
    private String isAnswer;

	private String comeSource;   //add by xiaona 2016年11月1日  记录M站预约的渠道来源
    
    public BusinessEntity() {
    }
    
    public BusinessEntity(String businessBid, String updaterId, Date updateTime,
			String cityId, String customerId, Byte step, Date endTime, Byte handState,
			String houseTypeId, Date expectInStartTime,
			Date expectInEndTime, Double expectLowPrice, Double expectHighPrice, Byte type, String remark, String zoId,
			String zoName) {
		this.businessBid = businessBid;
		this.updaterId = updaterId;
		this.updateTime = updateTime;
		this.cityId = cityId;
		this.customerId = customerId;
		this.step = step;
		this.endTime = endTime;
		this.handState = handState;
		this.houseTypeId = houseTypeId;
		this.expectInStartTime = expectInStartTime;
		this.expectInEndTime = expectInEndTime;
		this.expectLowPrice = expectLowPrice;
		this.expectHighPrice = expectHighPrice;
		this.type = type;
		this.remark = remark;
		this.zoId = zoId;
		this.zoName = zoName;
	}

	public BusinessEntity( String businessBid, String createrId, Date createTime, String updaterId,
			Date updateTime, String cityId, String customerId, Byte step, Date endTime, Byte handState,
			String message, String projectId, String houseTypeId, Byte source, String sourceZra, Date expectTime,
			Date expectInStartTime, Date expectInEndTime, Double expectLowPrice, Double expectHighPrice, Byte type,
			String remark, String zoId, String zoName) {
		this.businessBid = businessBid;
		this.createrId = createrId;
		this.createTime = createTime;
		this.updaterId = updaterId;
		this.updateTime = updateTime;
		this.cityId = cityId;
		this.customerId = customerId;
		this.step = step;
		this.endTime = endTime;
		this.handState = handState;
		this.message = message;
		this.projectId = projectId;
		this.houseTypeId = houseTypeId;
		this.source = source;
		this.sourceZra = sourceZra;
		this.expectTime = expectTime;
		this.expectInStartTime = expectInStartTime;
		this.expectInEndTime = expectInEndTime;
		this.expectLowPrice = expectLowPrice;
		this.expectHighPrice = expectHighPrice;
		this.type = type;
		this.remark = remark;
		this.zoId = zoId;
		this.zoName = zoName;
	}

	/**
     * 客户电话号码，该字段不在数据库里存储，只是方便去除重复商机（自动任务生成商机）
     */
    private String customerNumber;
    //end by tianxf9
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessBid() {
        return businessBid;
    }

    public void setBusinessBid(String businessBid) {
        this.businessBid = businessBid == null ? null : businessBid.trim();
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
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
        this.updaterId = updaterId == null ? null : updaterId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getCustomerId() {
        return customerId;
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

    public String getCallBid() {
		return callBid;
	}

	public void setCallBid(String callBid) {
		this.callBid = callBid;
	}


	public String getIsAnswer() {
		return isAnswer;
	}

	public void setIsAnswer(String isAnswer) {
		this.isAnswer = isAnswer;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getComeSource() {
		return comeSource;
	}

	public void setComeSource(String comeSource) {
		this.comeSource = comeSource;
	}
}