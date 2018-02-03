/**
 * @FileName: ActivityGiftRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author yd
 * @created 2016年10月9日 上午10:41:37
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>活动 礼物 请求参数 封装</p>
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
public class ActivityGiftRequest extends PageRequest{



	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 696215682425419943L;

	/**
	 * 礼品名称
	 */
	private String giftName;

	/**
	 * 礼品值
	 */
	private String giftValue;

	/**
	 * 礼品单位
	 */
	private String giftUnit;

	/**
	 * 礼品类型 1：免佣金 2：礼品实物
	 */
	private Integer giftType;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建人
	 */
	private String createId;

	/**
	 * 是否删除 0：否，1：是
	 */
	private Integer isDel;

	/**
	 * @return the giftName
	 */
	public String getGiftName() {
		return giftName;
	}

	/**
	 * @param giftName the giftName to set
	 */
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	/**
	 * @return the giftValue
	 */
	public String getGiftValue() {
		return giftValue;
	}

	/**
	 * @param giftValue the giftValue to set
	 */
	public void setGiftValue(String giftValue) {
		this.giftValue = giftValue;
	}

	/**
	 * @return the giftUnit
	 */
	public String getGiftUnit() {
		return giftUnit;
	}

	/**
	 * @param giftUnit the giftUnit to set
	 */
	public void setGiftUnit(String giftUnit) {
		this.giftUnit = giftUnit;
	}

	/**
	 * @return the giftType
	 */
	public Integer getGiftType() {
		return giftType;
	}

	/**
	 * @param giftType the giftType to set
	 */
	public void setGiftType(Integer giftType) {
		this.giftType = giftType;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}

	/**
	 * @param createId the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	/**
	 * @return the isDel
	 */
	public Integer getIsDel() {
		return isDel;
	}

	/**
	 * @param isDel the isDel to set
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	
}
