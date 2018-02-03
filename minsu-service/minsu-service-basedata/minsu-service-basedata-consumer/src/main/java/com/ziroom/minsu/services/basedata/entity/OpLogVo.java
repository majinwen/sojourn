/**
 * @FileName: OpLogVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author liyingjie
 * @created 2016年3月13日 下午6:40:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

import java.util.Date;

import com.ziroom.minsu.entity.sys.OpLogEntity;

/**
 * <p>操作日志vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp 2017/5/9
 * @since 1.0
 * @version 1.0
 */
public class OpLogVo extends OpLogEntity {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6706136972949070599L;
	
	/**
	 * 员工fid
	 */
	private String opEmployeeId;
	
	/**
	 * 操作人姓名
	 */
	private String opEmployeeName;
	
	/**
	 * 员工号
	 */
	private String opEmployeeCode;
	
	/**
	 * 操作系统名称
	 */
	private String opAppName;
	
	/**
	 * 操作URL
	 */
	private String opUrl;
	
	/**
	 * 操作时间
	 */
	private Date createTime;

	/**
	 * @return the opEmployeeId
	 */
	public String getOpEmployeeId() {
		return opEmployeeId;
	}

	/**
	 * @param opEmployeeId the opEmployeeId to set
	 */
	public void setOpEmployeeId(String opEmployeeId) {
		this.opEmployeeId = opEmployeeId;
	}

	/**
	 * @return the opEmployeeName
	 */
	public String getOpEmployeeName() {
		return opEmployeeName;
	}

	/**
	 * @param opEmployeeName the opEmployeeName to set
	 */
	public void setOpEmployeeName(String opEmployeeName) {
		this.opEmployeeName = opEmployeeName;
	}

	/**
	 * @return the opEmployeeCode
	 */
	public String getOpEmployeeCode() {
		return opEmployeeCode;
	}

	/**
	 * @param opEmployeeCode the opEmployeeCode to set
	 */
	public void setOpEmployeeCode(String opEmployeeCode) {
		this.opEmployeeCode = opEmployeeCode;
	}

	/**
	 * @return the opAppName
	 */
	public String getOpAppName() {
		return opAppName;
	}

	/**
	 * @param opAppName the opAppName to set
	 */
	public void setOpAppName(String opAppName) {
		this.opAppName = opAppName;
	}

	/**
	 * @return the opUrl
	 */
	public String getOpUrl() {
		return opUrl;
	}

	/**
	 * @param opUrl the opUrl to set
	 */
	public void setOpUrl(String opUrl) {
		this.opUrl = opUrl;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpLogVo [opEmployeeId=" + opEmployeeId + ", opEmployeeName="
				+ opEmployeeName + ", opEmployeeCode=" + opEmployeeCode
				+ ", opAppName=" + opAppName + ", opUrl=" + opUrl
				+ ", createTime=" + createTime + "]";
	}
	
	

	
}
