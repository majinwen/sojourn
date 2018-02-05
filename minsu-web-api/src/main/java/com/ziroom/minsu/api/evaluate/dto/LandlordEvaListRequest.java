/**
 * @FileName: LandlordEvaList.java
 * @Package com.ziroom.minsu.api.evaluate.dto
 * 
 * @author jixd
 * @created 2016年5月28日 下午6:29:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.evaluate.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class LandlordEvaListRequest extends BaseParamDto{
	/**
	 * 房东uid
	 */
	private String landlordUid;

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
	
	
}
