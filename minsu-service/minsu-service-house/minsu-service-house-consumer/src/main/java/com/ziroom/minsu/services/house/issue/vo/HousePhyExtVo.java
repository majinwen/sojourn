/**
 * @FileName: HousePhyExtVo.java
 * @Package com.ziroom.minsu.services.house.issue.vo
 * 
 * @author bushujie
 * @created 2017年6月19日 下午2:36:20
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.vo;

import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;

/**
 * <p>房源基础、物理、扩展信息vo</p>
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
public class HousePhyExtVo {
	
	/**
	 * 房源基础信息
	 */
	private HouseBaseMsgEntity houseBaseMsgEntity;
	
	/**
	 * 房源物理信息
	 */
	private HousePhyMsgEntity housePhyMsgEntity;
	
	/**
	 * 房源扩展信息
	 */
	private HouseBaseExtEntity houseBaseExtEntity;
	
	/**
	 * @return the houseBaseMsgEntity
	 */
	public HouseBaseMsgEntity getHouseBaseMsgEntity() {
		return houseBaseMsgEntity;
	}

	/**
	 * @param houseBaseMsgEntity the houseBaseMsgEntity to set
	 */
	public void setHouseBaseMsgEntity(HouseBaseMsgEntity houseBaseMsgEntity) {
		this.houseBaseMsgEntity = houseBaseMsgEntity;
	}

	/**
	 * @return the housePhyMsgEntity
	 */
	public HousePhyMsgEntity getHousePhyMsgEntity() {
		return housePhyMsgEntity;
	}

	/**
	 * @param housePhyMsgEntity the housePhyMsgEntity to set
	 */
	public void setHousePhyMsgEntity(HousePhyMsgEntity housePhyMsgEntity) {
		this.housePhyMsgEntity = housePhyMsgEntity;
	}

	/**
	 * @return the houseBaseExtEntity
	 */
	public HouseBaseExtEntity getHouseBaseExtEntity() {
		return houseBaseExtEntity;
	}

	/**
	 * @param houseBaseExtEntity the houseBaseExtEntity to set
	 */
	public void setHouseBaseExtEntity(HouseBaseExtEntity houseBaseExtEntity) {
		this.houseBaseExtEntity = houseBaseExtEntity;
	}
}
