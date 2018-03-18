package com.zra.common.dto.business;

import java.util.Date;

/**
 * 商机处理结果dto
 * @author wangws21 2016-8-2
 */
public class BusinessResultDto {
	
    /*处理结果bid*/
    private String resultBid;
    /*关联商机bid*/
    private String businessId;
    /*商机阶段 1：待约看 2：待带看 3：待回访 4：成交 5：未成交*/
    private Byte step;
    /*处理状态*/
    private Byte handState;
    /**/
    private Date endTime;
    /*处理时间*/
    private Date handTime;
    /*处理结果*/
    private Byte handResult;
    /*处理结果时间  根据不同处理结果 时间意义不同：约看时间 、带看时间、回访时间*/
    private Date handResultTime;
    /*处理结果 原因或合同号*/
    private String handResultContent;
    /*跟进记录*/
    private String record;
    /*处理管家bid*/
    private String handleZoBid;
    /*关闭商机发送短信模板*/
    private Integer closeMsgDescContent;

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

	public Integer getCloseMsgDescContent() {
		return closeMsgDescContent;
	}

	public void setCloseMsgDescContent(Integer closeMsgDescContent) {
		this.closeMsgDescContent = closeMsgDescContent;
	}
}