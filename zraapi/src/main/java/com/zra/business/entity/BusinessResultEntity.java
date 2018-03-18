package com.zra.business.entity;

import java.util.Date;

public class BusinessResultEntity {
    /**主键*/
    private Integer id;

    /**处理结果bid*/
    private String resultBid;

    /**关联商机bid*/
    private String businessId;

    /**创建人*/
    private String createrId;

    /**创建时间*/
    private Date createTime;

    /**'是否删除(0:否,1:是)',*/
    private Byte isDel;

    /**城市id*/
    private String cityId;

    /**商机阶段 1：待约看 2：待带看 3：待回访 4：成交 5：未成交*/
    private Byte step;

    /**处理状态*/
    private Byte handState;

    /***/
    private Date endTime;

    /**处理时间*/
    private Date handTime;

    /**处理结果  1:待和用户确认约看时间  2:确认约看 3:已带看，待进一步跟进回访 4:已签约，完成商机 5:关闭商机*/
    private Byte handResult;

    /**处理结果时间  根据不同处理结果 时间意义不同：约看时间 、带看时间、回访时间*/
    private Date handResultTime;

    /**处理结果 原因或合同号*/
    private String handResultContent;

    /**跟进记录*/
    private String record;

    /**处理管家bid*/
    private String handleZoBid;

    /**是否进行短信约看提醒 */
    private Byte isSms;
    
    /**是否进行评价提醒 */
    private Byte isEvaluateSms;

    
    /** 空构造函数 */
    public BusinessResultEntity() {
	}
    
    /** 构造函数 */
	public BusinessResultEntity( String resultBid, Byte isSms, Byte isEvaluateSms) {
		this.resultBid = resultBid;
		this.isSms = isSms;
		this.isEvaluateSms = isEvaluateSms;
	}
    
    /** 构造函数 */
	public BusinessResultEntity( String resultBid, String businessId, String createrId,
			String cityId, Byte step, Byte handState, Date endTime, Byte handResult,
			Date handResultTime, String handResultContent, String record, String handleZoBid, Byte isSms, Byte isEvaluateSms) {
		this.resultBid = resultBid;
		this.businessId = businessId;
		this.createrId = createrId;
		this.cityId = cityId;
		this.step = step;
		this.handState = handState;
		this.endTime = endTime;
		this.handResult = handResult;
		this.handResultTime = handResultTime;
		this.handResultContent = handResultContent;
		this.record = record;
		this.handleZoBid = handleZoBid;
		this.isSms = isSms;
		this.isEvaluateSms = isEvaluateSms;
	}
    
    /** 全构造函数 */
	public BusinessResultEntity(Integer id, String resultBid, String businessId, String createrId, Date createTime,
			Byte isDel, String cityId, Byte step, Byte handState, Date endTime, Date handTime, Byte handResult,
			Date handResultTime, String handResultContent, String record, String handleZoBid, Byte isSms, Byte isEvaluateSms) {
		this.id = id;
		this.resultBid = resultBid;
		this.businessId = businessId;
		this.createrId = createrId;
		this.createTime = createTime;
		this.isDel = isDel;
		this.cityId = cityId;
		this.step = step;
		this.handState = handState;
		this.endTime = endTime;
		this.handTime = handTime;
		this.handResult = handResult;
		this.handResultTime = handResultTime;
		this.handResultContent = handResultContent;
		this.record = record;
		this.handleZoBid = handleZoBid;
		this.isSms = isSms;
		this.isEvaluateSms = isEvaluateSms;
	}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResultBid() {
        return resultBid;
    }

    public void setResultBid(String resultBid) {
        this.resultBid = resultBid == null ? null : resultBid.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
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

    public Byte getStep() {
        return step;
    }

    public void setStep(Byte step) {
        this.step = step;
    }

    public Byte getHandState() {
        return handState;
    }

    public void setHandState(Byte handState) {
        this.handState = handState;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getHandTime() {
        return handTime;
    }

    public void setHandTime(Date handTime) {
        this.handTime = handTime;
    }

    public Byte getHandResult() {
        return handResult;
    }

    public void setHandResult(Byte handResult) {
        this.handResult = handResult;
    }

    public Date getHandResultTime() {
        return handResultTime;
    }

    public void setHandResultTime(Date handResultTime) {
        this.handResultTime = handResultTime;
    }

    public String getHandResultContent() {
        return handResultContent;
    }

    public void setHandResultContent(String handResultContent) {
        this.handResultContent = handResultContent == null ? null : handResultContent.trim();
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record == null ? null : record.trim();
    }

    public String getHandleZoBid() {
        return handleZoBid;
    }

    public void setHandleZoBid(String handleZoBid) {
        this.handleZoBid = handleZoBid == null ? null : handleZoBid.trim();
    }

    public Byte getIsSms() {
        return isSms;
    }

    public void setIsSms(Byte isSms) {
        this.isSms = isSms;
    }

	public Byte getIsEvaluateSms() {
		return isEvaluateSms;
	}

	public void setIsEvaluateSms(Byte isEvaluateSms) {
		this.isEvaluateSms = isEvaluateSms;
	}
}