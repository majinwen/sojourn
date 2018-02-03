/**
 * @FileName: MenuOperRequest.java
 * @Package com.ziroom.minsu.services.basedata.logic
 * 
 * @author liyingjie
 * @created 2016年3月9日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 后台国家、城市查询参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class ConfCityRequest extends PageRequest {

	/**
	 * 父类id查询
	 */
	private String pcode;

	/**
	 * 层级查询
	 */
	private Integer level;
	
	/**
	 * 根据 code 查询
	 */
	private String code;
	
	
	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
