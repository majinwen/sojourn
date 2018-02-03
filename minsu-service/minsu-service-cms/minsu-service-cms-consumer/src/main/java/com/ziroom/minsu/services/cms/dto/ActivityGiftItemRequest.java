/**
 * @FileName: ActivityGiftItemRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author yd
 * @created 2016年10月10日 下午1:36:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import java.io.Serializable;

/**
 * <p>活动组相 查询参数</p>
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
public class ActivityGiftItemRequest implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 5804330189349875394L;
	
	/**
	 * 活动sn
	 */
	private String actSn;

	/**
	 * @return the actSn
	 */
	public String getActSn() {
		return actSn;
	}

	/**
	 * @param actSn the actSn to set
	 */
	public void setActSn(String actSn) {
		this.actSn = actSn;
	}
	
	

}
