/**
 * @FileName: ColumnRegionRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author bushujie
 * @created 2016年11月10日 下午5:00:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>专栏商圈景点request</p>
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
public class ColumnRegionRequest extends PageRequest{


	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -109997739370725434L;
	
	/**
	 * 城市专栏fid
	 */
	private String columnCityFid;

	/**
	 * @return the columnCityFid
	 */
	public String getColumnCityFid() {
		return columnCityFid;
	}

	/**
	 * @param columnCityFid the columnCityFid to set
	 */
	public void setColumnCityFid(String columnCityFid) {
		this.columnCityFid = columnCityFid;
	}
}
