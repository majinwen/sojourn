/**
 * @FileName: SystemsRequest.java
 * @Package com.ziroom.minsu.ups.dto
 * 
 * @author bushujie
 * @created 2016年12月1日 下午3:57:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>系统信息查询参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class SystemsRequest extends PageRequest {

	/**
	 * 序列化fid
	 */
	private static final long serialVersionUID = -4274181237858397762L;
	
	/**
	 * 系统名称
	 */
	private String systemName;
	
	/**
	 * 系统code
	 */
	private String systemCode;
	
	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
}
