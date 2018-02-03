/**
 * @FileName: dealImYellowPicRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author loushuai
 * @created 2017年9月7日 下午8:59:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

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
public class DealImYellowPicRequest extends PageRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4393675590342942364L;
	
	private String ziroomFlag;
	
	private Integer isCertified;

	public String getZiroomFlag() {
		return ziroomFlag;
	}

	public void setZiroomFlag(String ziroomFlag) {
		this.ziroomFlag = ziroomFlag;
	}

	public Integer getIsCertified() {
		return isCertified;
	}

	public void setIsCertified(Integer isCertified) {
		this.isCertified = isCertified;
	}
}
