/**
 * @FileName: Log.java
 * @Package com.asura.amp.authority.entity
 * 
 * @author zhangshaobin
 * @created 2013-2-2 下午3:48:53
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 测试合并
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class Log extends BaseEntity {

	/**
	 *  测试分支 git
	 */
	private static final long serialVersionUID = 793197630810397096L;
	/*
	 * 自增id
	 */
	private Integer logId;

	/*
	 * 登录人姓名
	 */
	private String logonName;

	/*
	 * 工号
	 */
	private String jobNum;

	/*
	 * 登录日期
	 */
	private Long logonDate;

	/*
	 * 是否是合法操作 0非法 1合法
	 */
	private Integer isNormalOperation;

	/*
	 * 操作的url
	 */
	private String operationUrl;

	/*
	 * 请求ip
	 */
	private String clientIp;

	/**
	 * @return the logId
	 */
	public Integer getLogId() {
		return logId;
	}

	/**
	 * @param logId
	 *            the logId to set
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * @return the logonName
	 */
	public String getLogonName() {
		return logonName;
	}

	/**
	 * @param logonName
	 *            the logonName to set
	 */
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	/**
	 * @return the jobNum
	 */
	public String getJobNum() {
		return jobNum;
	}

	/**
	 * @param jobNum
	 *            the jobNum to set
	 */
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	/**
	 * @return the logonDate
	 */
	public Long getLogonDate() {
		return logonDate;
	}

	/**
	 * @param logonDate
	 *            the logonDate to set
	 */
	public void setLogonDate(Long logonDate) {
		this.logonDate = logonDate;
	}

	/**
	 * @return the isNormalOperation
	 */
	public Integer getIsNormalOperation() {
		return isNormalOperation;
	}

	/**
	 * @param isNormalOperation
	 *            the isNormalOperation to set
	 */
	public void setIsNormalOperation(Integer isNormalOperation) {
		this.isNormalOperation = isNormalOperation;
	}

	/**
	 * @return the operationUrl
	 */
	public String getOperationUrl() {
		return operationUrl;
	}

	/**
	 * @param operationUrl
	 *            the operationUrl to set
	 */
	public void setOperationUrl(String operationUrl) {
		this.operationUrl = operationUrl;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}
