/**
 * @FileName: HouseDFBNoticeDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2016年11月22日 下午4:41:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>待发布房源 短信和极光提醒 dto</p>
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
public class HouseDfbNoticeDto extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -7712197753955400380L;

	/**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 当前房源的数量
	 */
	private String houseNum;
	
	/**
	 * 房东手机号
	 */
	private String mobile;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	/**
	 * @return the houseNum
	 */
	public String getHouseNum() {
		return houseNum;
	}

	/**
	 * @param houseNum the houseNum to set
	 */
	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}
	
	
}
