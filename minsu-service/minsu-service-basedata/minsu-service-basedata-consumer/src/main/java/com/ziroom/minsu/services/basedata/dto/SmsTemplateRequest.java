/**
 * @FileName: SmsTemplateRequest.java
 * @Package com.ziroom.minsu.services.basedata.dto
 * 
 * @author yd
 * @created 2016年4月1日 下午3:00:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;

import java.io.Serializable;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>短信模板条件封装</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class SmsTemplateRequest extends PageRequest implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5144016082430588873L;
	/**
	 * 消息编码
	 */
	private String smsCode;
	/**
	 * 消息名称
	 */
	private String smsName;
	
	/**
	 * 消息类型（1=短信消息 2=邮箱消息 3=消息 4=其他消息）
	 */
	private Integer smsType;
	
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getSmsName() {
		return smsName;
	}
	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}
	public Integer getSmsType() {
		return smsType;
	}
	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}
	
	
}
