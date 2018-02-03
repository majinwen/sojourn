/**
 * @FileName: HouseAuditVo.java
 * @Package com.ziroom.minsu.services.house.entity
 *
 * @author lusp
 * @created 2017年8月1日 下午9:33:49
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * 
 * <p>房源审核信息vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class HouseAuditVo extends BaseEntity {


	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8916319108213460882L;

	/**
	 * 房源基础信息表
	 */
	private HouseBaseMsgEntity houseBaseMsgEntity = new HouseBaseMsgEntity();

	/**
	 * 房源基础信息扩展表
	 */
	private HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();

	/**
	 * 房源描述信息表
	 */
	private HouseDescEntity houseDescEntity = new HouseDescEntity();

	/**
	 * 房源配置信息表
	 */
	private HouseConfMsgEntity houseConfMsgEntity = new HouseConfMsgEntity();

	/**
	 * 房源物理信息表
	 */
	private HousePhyMsgEntity housePhyMsgEntity = new HousePhyMsgEntity();

	/**
	 * 房间基础信息表
	 */
	private HouseRoomMsgEntity houseRoomMsgEntity = new HouseRoomMsgEntity();

	/**
	 * 房间基础信息扩展表
	 */
	private HouseRoomExtEntity houseRoomExtEntity = new HouseRoomExtEntity();

	public HouseBaseMsgEntity getHouseBaseMsgEntity() {
		return houseBaseMsgEntity;
	}

	public void setHouseBaseMsgEntity(HouseBaseMsgEntity houseBaseMsgEntity) {
		this.houseBaseMsgEntity = houseBaseMsgEntity;
	}

	public HouseBaseExtEntity getHouseBaseExtEntity() {
		return houseBaseExtEntity;
	}

	public void setHouseBaseExtEntity(HouseBaseExtEntity houseBaseExtEntity) {
		this.houseBaseExtEntity = houseBaseExtEntity;
	}

	public HouseDescEntity getHouseDescEntity() {
		return houseDescEntity;
	}

	public void setHouseDescEntity(HouseDescEntity houseDescEntity) {
		this.houseDescEntity = houseDescEntity;
	}

	public HouseConfMsgEntity getHouseConfMsgEntity() {
		return houseConfMsgEntity;
	}

	public void setHouseConfMsgEntity(HouseConfMsgEntity houseConfMsgEntity) {
		this.houseConfMsgEntity = houseConfMsgEntity;
	}

	public HousePhyMsgEntity getHousePhyMsgEntity() {
		return housePhyMsgEntity;
	}

	public void setHousePhyMsgEntity(HousePhyMsgEntity housePhyMsgEntity) {
		this.housePhyMsgEntity = housePhyMsgEntity;
	}

	public HouseRoomMsgEntity getHouseRoomMsgEntity() {
		return houseRoomMsgEntity;
	}

	public void setHouseRoomMsgEntity(HouseRoomMsgEntity houseRoomMsgEntity) {
		this.houseRoomMsgEntity = houseRoomMsgEntity;
	}

	public HouseRoomExtEntity getHouseRoomExtEntity() {
		return houseRoomExtEntity;
	}

	public void setHouseRoomExtEntity(HouseRoomExtEntity houseRoomExtEntity) {
		this.houseRoomExtEntity = houseRoomExtEntity;
	}
}
