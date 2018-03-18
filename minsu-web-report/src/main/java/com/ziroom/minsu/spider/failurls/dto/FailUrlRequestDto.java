/**
 * @FileName: FailUrlRequestDto.java
 * @Package com.ziroom.minsu.spider.failurls.dto
 * 
 * @author zl
 * @created 2016年10月27日 下午2:52:29
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.failurls.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.spider.failurls.entity.enums.FailUrlRecordTypeEnum;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class FailUrlRequestDto extends PageRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7425826820629783985L;

	/**
	 * 失败ids，有就按照id查
	 */
	List<Integer> ids;
	
	/**
	 * url类型
	 * @see FailUrlRecordTypeEnum
	 */
	Integer[] urlTypes;
	
	/**
	 * url类型
	 * @see FailUrlRecordTypeEnum
	 */
	List<Integer> types;
	

	public List<Integer> getIds() {
		return ids;
	}

	public Integer[] getUrlTypes() {
		return urlTypes;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public void setUrlTypes(Integer[] urlTypes) {
		this.urlTypes = urlTypes;
	}

	public List<Integer> getTypes() {
		return types;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}
	
}
