package com.zra.push.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="")
public class PushLog {
    /**
     * 
     * 表字段 : push_log.id
     * 
     */
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 推送bid
     * 表字段 : push_log.push_id
     * 
     */
    @ApiModelProperty(value="推送bid")
    private String pushId;
    
    /**
     * 
     */
    @ApiModelProperty(value="推送uid")
    private String pushUid;

    /**
     * 推送title
     * 表字段 : push_log.push_title
     * 
     */
    @ApiModelProperty(value="推送title")
    private String pushTitle;

    /**
     * 推送内容
     * 表字段 : push_log.push_content
     * 
     */
    @ApiModelProperty(value="推送内容")
    private String pushContent;
    
    @ApiModelProperty(value="需要打开的URL")
    private String openUrl;

    /**
     * 推送时间也是创建时间
     * 表字段 : push_log.push_time
     * 
     */
    @ApiModelProperty(value="推送时间也是创建时间")
    private Date pushTime;

    /**
     * 推送结果;0:失败;1:成功
     * 表字段 : push_log.push_result
     * 
     */
    @ApiModelProperty(value="推送结果;0:失败;1:成功")
    private Integer pushResult;

    /**
     * 推送失败原因
     * 表字段 : push_log.push_fail_reason
     * 
     */
    @ApiModelProperty(value="推送失败原因")
    private String pushFailReason;

    /**
     * 推送系统的ID;
     * 表字段 : push_log.system_id
     * 
     */
    @ApiModelProperty(value="推送系统的ID;")
    private String systemId;
    
    
    /**
     * 功能标识
     */
    private String funcFlag;
    
    /**
     * 推送流水号
     */
    private String seNo;
    
    /**
     * 默认构造方法.
     */
    public PushLog() {
        
    }

    public PushLog(String pushId, String pushUid, String pushTitle, String pushContent, 
            String openUrl, Date pushTime, Integer pushResult,
            String pushFailReason, String systemId, String funcFlag,
            String seNo) {
        super();
        this.pushId = pushId;
        this.pushUid = pushUid;
        this.pushTitle = pushTitle;
        this.pushContent = pushContent;
        this.openUrl = openUrl;
        this.pushTime = pushTime;
        this.pushResult = pushResult;
        this.pushFailReason = pushFailReason;
        this.systemId = systemId;
        this.funcFlag = funcFlag;
        this.seNo = seNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId == null ? null : pushId.trim();
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle == null ? null : pushTitle.trim();
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent == null ? null : pushContent.trim();
    }

    
    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getPushResult() {
        return pushResult;
    }

    public void setPushResult(Integer pushResult) {
        this.pushResult = pushResult;
    }

    public String getPushFailReason() {
        return pushFailReason;
    }

    public void setPushFailReason(String pushFailReason) {
        this.pushFailReason = pushFailReason == null ? null : pushFailReason.trim();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getPushUid() {
        return pushUid;
    }

    public void setPushUid(String pushUid) {
        this.pushUid = pushUid;
    }

    public String getFuncFlag() {
        return funcFlag;
    }

    public void setFuncFlag(String funcFlag) {
        this.funcFlag = funcFlag;
    }

    public String getSeNo() {
        return seNo;
    }

    public void setSeNo(String seNo) {
        this.seNo = seNo;
    }
    
}