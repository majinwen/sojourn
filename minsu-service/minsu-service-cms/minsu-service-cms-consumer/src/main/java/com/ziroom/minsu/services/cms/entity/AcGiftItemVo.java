/**
 * @FileName: AcGiftItemVo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author yd
 * @created 2016年10月10日 下午12:06:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

import java.io.Serializable;

/**
 * <p>当前活动礼物相 信息返回</p>
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
public class AcGiftItemVo implements Serializable{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -968614421695989412L;
	/**
	 * 礼物fid
	 */
	private String giftFid;
	
	/**
	 * 礼物名称
	 */
	private String giftName;
	
	/**
	 * 当前礼物数量
	 */
	private Integer giftCount;
	
	/**
	 * 当前是活动中礼物 还是系统礼物 0=不是当前活动礼物  1=是当前活动中礼物
	 */
	private Integer giftFlag;

	/**
	 * @return the giftFid
	 */
	public String getGiftFid() {
		return giftFid;
	}

	/**
	 * @param giftFid the giftFid to set
	 */
	public void setGiftFid(String giftFid) {
		this.giftFid = giftFid;
	}

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
	 * @return the giftCount
	 */
	public Integer getGiftCount() {
		return giftCount;
	}

	/**
	 * @param giftCount the giftCount to set
	 */
	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}

	/**
	 * @return the giftFlag
	 */
	public Integer getGiftFlag() {
		return giftFlag;
	}

	/**
	 * @param giftFlag the giftFlag to set
	 */
	public void setGiftFlag(Integer giftFlag) {
		this.giftFlag = giftFlag;
	}
	
	
}
