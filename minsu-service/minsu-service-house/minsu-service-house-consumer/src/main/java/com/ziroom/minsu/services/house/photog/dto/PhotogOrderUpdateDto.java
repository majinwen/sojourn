/**
 * @FileName: PhotogOrderUpdateDto.java
 * @Package com.ziroom.minsu.services.house.photog.dto
 * 
 * @author yd
 * @created 2016年11月9日 上午9:49:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.photog.dto;

import java.io.Serializable;

import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;

/**
 * <p>预约订单修改  请求参数</p>
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
public class PhotogOrderUpdateDto implements Serializable{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -5164110100888639301L;

	/**
	 * 预约订单实体
	 */
	private PhotographerBookOrderEntity  photographerBookOrder;
	
	/**
	 * 预约订单 修改之前状态
	 */
	private Integer oldStatu;

	/** 修改之前的摄影师uid*/
	private String preGrapherUid;
	
	/**
	 * 创建人fid
	 */
	private String createrFid;
	 
	/**
	 * 创建人类型
	 */
	private Integer createrType;

	//作废原因
	private String remark;

	/**
	 * 修改功能类型(1=正常修改  2=指定摄影师修改  3=摄影师完成 4=重新指定摄影师  5=作废摄影预约单 6=收图操作标志)
	 */
	private Integer updateType;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPreGrapherUid() {
		return preGrapherUid;
	}

	public void setPreGrapherUid(String preGrapherUid) {
		this.preGrapherUid = preGrapherUid;
	}

	/**
	 * @return the updateType
	 */
	public Integer getUpdateType() {
		return updateType;
	}

	/**
	 * @param updateType the updateType to set
	 */
	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}

	/**
	 * @return the photographerBookOrder
	 */
	public PhotographerBookOrderEntity getPhotographerBookOrder() {
		return photographerBookOrder;
	}

	/**
	 * @param photographerBookOrder the photographerBookOrder to set
	 */
	public void setPhotographerBookOrder(
			PhotographerBookOrderEntity photographerBookOrder) {
		this.photographerBookOrder = photographerBookOrder;
	}

	/**
	 * @return the oldStatu
	 */
	public Integer getOldStatu() {
		return oldStatu;
	}

	/**
	 * @param oldStatu the oldStatu to set
	 */
	public void setOldStatu(Integer oldStatu) {
		this.oldStatu = oldStatu;
	}

	/**
	 * @return the createrFid
	 */
	public String getCreaterFid() {
		return createrFid;
	}

	/**
	 * @param createrFid the createrFid to set
	 */
	public void setCreaterFid(String createrFid) {
		this.createrFid = createrFid;
	}

	/**
	 * @return the createrType
	 */
	public Integer getCreaterType() {
		return createrType;
	}

	/**
	 * @param createrType the createrType to set
	 */
	public void setCreaterType(Integer createrType) {
		this.createrType = createrType;
	}


	
	
	
}
