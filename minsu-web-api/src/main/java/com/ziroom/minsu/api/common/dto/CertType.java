/**
 * @FileName: CertType.java
 * @Package com.ziroom.minsu.api.common.dto
 * 
 * @author yd
 * @created 2016年5月6日 下午4:30:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.dto;

/**
 * <p>TODO</p>
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
public class CertType {

	
	/**
	 * 证件类型
	 */
	private String cert_type;
	
	/**
	 * 证件号码
	 */
	private String cert_num;
	
	/**
	 * 伤处修改时间
	 */
	private String last_modify_time;

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getLast_modify_time() {
		return last_modify_time;
	}

	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}
	
	
	
}
