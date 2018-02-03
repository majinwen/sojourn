/**
 * @FileName: MSmartLockDto.java
 * @Package com.ziroom.minsu.services.house.smartlock.dto
 * 
 * @author jixd
 * @created 2016年6月24日 下午12:53:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.dto;

import com.ziroom.minsu.entity.house.HouseSmartlockEntity;

/**
 * <p>智能锁请求数据对象</p>
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
public class MSmartLockDto extends HouseSmartlockEntity{
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 6504762026430029702L;
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
