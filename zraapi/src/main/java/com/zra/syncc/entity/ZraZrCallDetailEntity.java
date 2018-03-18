package com.zra.syncc.entity;

import java.util.Date;

import org.springframework.stereotype.Repository;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 
 * @author tianxf9
 *
 */
@Repository
@ApiModel(value="通话详情实体")
public class ZraZrCallDetailEntity {
	
    @ApiModelProperty(value="自增Id")
	private Integer id;
    
    @ApiModelProperty(value="业务id")
	private String bid;
    
    @ApiModelProperty(value="进线CallID")
	private String callId;
    
    @ApiModelProperty(value="转接拨号CallID")
	private String transferedCallId;
    
    @ApiModelProperty(value="进线服务别 1：自如销售；2：自如客服")
	private String entryType;
    
    @ApiModelProperty(value="进线电话号码")
	private String remoteNumberFmt;
    
    @ApiModelProperty(value="输入的分机号码")
	private String extensionNumber;
    
    @ApiModelProperty(value="拨号开始时间")
	private String dialBeginDate;
    
    @ApiModelProperty(value="拨号结束时间")
	private String dialEndDate;
    
    @ApiModelProperty(value="拨打的号码")
	private String dialNumber;
    
    @ApiModelProperty(value="拨号结果")
	private String dialResult;
    
    @ApiModelProperty(value="进线时间")
	private String initiatedDate;
    
    @ApiModelProperty(value="转接成功时间")
	private String connectedDate;
    
    @ApiModelProperty(value="挂断时间")
	private String terminatedDate;
    
    @ApiModelProperty(value="连接时长（秒）")
	private String connectDurationTime;
    
    @ApiModelProperty(value="录音文件路径")
	private String recordingFileName;
    
    @ApiModelProperty(value="挂断状态 IVR：IVR挂断，E：管家挂断，I：客户挂断，CusDisCon：转接中客户挂断，其他请看Eic_State.jpg")
	private String eicState;
    
    @ApiModelProperty(value="ISDN代码，请看Eic_IsdnCauseValue.txt")
	private String isdnCauseCode;
    
    @ApiModelProperty(value="自如服务人工队列:31维修;32保洁;33投诉建议")
	private String serviceNo;
    
    @ApiModelProperty(value="创建时间")
	private Date createTime;
    
    @ApiModelProperty(value="是否删除")
	private Integer isDel;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getTransferedCallId() {
		return transferedCallId;
	}
	public void setTransferedCallId(String transferedCallId) {
		this.transferedCallId = transferedCallId;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public String getRemoteNumberFmt() {
		return remoteNumberFmt;
	}
	public void setRemoteNumberFmt(String remoteNumberFmt) {
		this.remoteNumberFmt = remoteNumberFmt;
	}
	public String getExtensionNumber() {
		return extensionNumber;
	}
	public void setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
	}
	public String getDialBeginDate() {
		return dialBeginDate;
	}
	public void setDialBeginDate(String dialBeginDate) {
		this.dialBeginDate = dialBeginDate;
	}
	public String getDialEndDate() {
		return dialEndDate;
	}
	public void setDialEndDate(String dialEndDate) {
		this.dialEndDate = dialEndDate;
	}
	public String getDialNumber() {
		return dialNumber;
	}
	public void setDialNumber(String dialNumber) {
		this.dialNumber = dialNumber;
	}
	public String getDialResult() {
		return dialResult;
	}
	public void setDialResult(String dialResult) {
		this.dialResult = dialResult;
	}
	public String getInitiatedDate() {
		return initiatedDate;
	}
	public void setInitiatedDate(String initiatedDate) {
		this.initiatedDate = initiatedDate;
	}
	public String getConnectedDate() {
		return connectedDate;
	}
	public void setConnectedDate(String connectedDate) {
		this.connectedDate = connectedDate;
	}
	public String getTerminatedDate() {
		return terminatedDate;
	}
	public void setTerminatedDate(String terminatedDate) {
		this.terminatedDate = terminatedDate;
	}
	public String getConnectDurationTime() {
		return connectDurationTime;
	}
	public void setConnectDurationTime(String connectDurationTime) {
		this.connectDurationTime = connectDurationTime;
	}
	public String getRecordingFileName() {
		return recordingFileName;
	}
	public void setRecordingFileName(String recordingFileName) {
		this.recordingFileName = recordingFileName;
	}
	public String getEicState() {
		return eicState;
	}
	public void setEicState(String eicState) {
		this.eicState = eicState;
	}
	public String getIsdnCauseCode() {
		return isdnCauseCode;
	}
	public void setIsdnCauseCode(String isdnCauseCode) {
		this.isdnCauseCode = isdnCauseCode;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	

}
