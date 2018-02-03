/**
 * @FileName: ActivityGiftInfoRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author yd
 * @created 2016年10月9日 下午9:16:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;

/**
 * <p>礼品活动保存 入参</p>
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
public class ActivityGiftInfoRequest implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 7372102812717455915L;
	
	/**
	 * 活动实体
	 */
	private ActivityEntity ac;
	
	/**
	 * 活动礼物项 集合
	 */
	private List<ActivityGiftItemEntity> listAcGiftItems;

	/**
	 * @return the ac
	 */
	public ActivityEntity getAc() {
		return ac;
	}

	/**
	 * @param ac the ac to set
	 */
	public void setAc(ActivityEntity ac) {
		this.ac = ac;
	}

	/**
	 * @return the listAcGiftItems
	 */
	public List<ActivityGiftItemEntity> getListAcGiftItems() {
		return listAcGiftItems;
	}

	/**
	 * @param listAcGiftItems the listAcGiftItems to set
	 */
	public void setListAcGiftItems(List<ActivityGiftItemEntity> listAcGiftItems) {
		this.listAcGiftItems = listAcGiftItems;
	}


}
