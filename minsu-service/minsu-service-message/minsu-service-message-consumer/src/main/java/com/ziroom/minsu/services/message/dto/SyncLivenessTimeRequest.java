/**
 * @FileName: SyncLivenessTimeRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author loushuai
 * @created 2017年9月1日 上午11:31:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class SyncLivenessTimeRequest extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8793440698502603945L;
	
	/**
	 * 授权code
	 */
	private String code;
	
	/**
	 * 授权时间戳
	 */
	private String timeStamp;
	
	/**
	 * 授权值
	 */
	private String value;
	
	/**
	 * 用户活跃度时间集合
	 */
	private String sycnData;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSycnData() {
		return sycnData;
	}

	public void setSycnData(String sycnData) {
		this.sycnData = sycnData;
	}

}
