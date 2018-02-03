package com.ziroom.minsu.services.message.dto;

import java.util.Date;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 系统消息管理基本查询参数
 * @author jixd on 2016年4月16日
 * @version 1.0
 * @since 1.0
 */
public class SysMsgManagerRequest extends PageRequest {
	/**
	 * 
	 * 序列化id
	 */
	private static final long serialVersionUID = -6198818103376475494L;
	
	/*业务逻辑id*/
	private String fid;
	/*创建人id*/
	private String createUid;
	/*消息标题*/
    private String msgTitle;
    /*消息内容*/
    private String msgContent;
    /*是否发布*/
    private Integer isRelease;
    /*是否删除*/
    private Integer isDel;
    /*  通知类型 1：指定用户 2:全部房东 3：全部房客 4：全部用户*/
    private Integer msgTargetType;
    /* 通知对象uid 如果通知类型为1 则有通知对象id*/
    private String msgTargetUid;
    /*修改人id*/
    private String modifyUid;
    /*创建时间id*/
    private Date createTime;
    /*修改时间id*/
    private Date lastModifyDate;
    
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getMsgTargetType() {
		return msgTargetType;
	}
	public void setMsgTargetType(Integer msgTargetType) {
		this.msgTargetType = msgTargetType;
	}
	public String getMsgTargetUid() {
		return msgTargetUid;
	}
	public void setMsgTargetUid(String msgTargetUid) {
		this.msgTargetUid = msgTargetUid;
	}
	public String getModifyUid() {
		return modifyUid;
	}
	public void setModifyUid(String modifyUid) {
		this.modifyUid = modifyUid;
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
    
    
    
}
