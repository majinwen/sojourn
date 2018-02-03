/**
 * @FileName: HouseUpdateHistoryLogDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2017年7月4日 下午5:15:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.house.*;

import java.util.List;

/**
 * <p>房源 更新记录 待保存 请求参数</p>
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
public class HouseUpdateHistoryLogDto extends BaseEntity{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -2036567457652509931L;

	/**
	 * 房源 基本信息  房源 fid  和 rentWay必传
	 */
	private  HouseBaseMsgEntity houseBaseMsg;
	
	/**
	 * 原 房源 基本信息
	 */
	private  HouseBaseMsgEntity oldHouseBaseMsg;
	
	/**
	 * 房源 房间信息  如果是 分租  fid 必传
	 */
	private  HouseRoomMsgEntity houseRoomMsg;
	
	/**
	 * 原房间信息
	 */
	private HouseRoomMsgEntity oldHouseRoomMsg;
	
	/**
	 * 房源 扩展信息
	 */
	private  HouseBaseExtEntity houseBaseExt;
	
	/**
	 * 原 房源 扩展信息
	 */
	private  HouseBaseExtEntity oldHouseBaseExt;
	
	/**
	 * 房源描述信息
	 */
	private  HouseDescEntity houseDesc;
	
	/**
	 * 原房源描述信息
	 */
	private  HouseDescEntity oldHouseDesc;
	/**
	 * 房源 物理信息
	 */
	private  HousePhyMsgEntity housePhyMsg;
	
	/**
	 * 原房源 物理信息
	 */
	private  HousePhyMsgEntity oldHousePhyMsg;
	
	/**
	 * 房源 配置表
	 */
	private  List<HouseConfMsgEntity>  ListHouseConfMsg;
	
	/**
	 * 房间扩展
	 */
	private HouseRoomExtEntity  houseRoomExt;
	
	/**
	 * 原房间扩展
	 */
	private HouseRoomExtEntity  oldHouseRoomExt;
	
	/**
	 * @return the oldHouseBaseMsg
	 */
	public HouseBaseMsgEntity getOldHouseBaseMsg() {
		return oldHouseBaseMsg;
	}

	/**
	 * @param oldHouseBaseMsg the oldHouseBaseMsg to set
	 */
	public void setOldHouseBaseMsg(HouseBaseMsgEntity oldHouseBaseMsg) {
		this.oldHouseBaseMsg = oldHouseBaseMsg;
	}

	/**
	 * @return the oldHouseRoomMsg
	 */
	public HouseRoomMsgEntity getOldHouseRoomMsg() {
		return oldHouseRoomMsg;
	}

	/**
	 * @param oldHouseRoomMsg the oldHouseRoomMsg to set
	 */
	public void setOldHouseRoomMsg(HouseRoomMsgEntity oldHouseRoomMsg) {
		this.oldHouseRoomMsg = oldHouseRoomMsg;
	}

	/**
	 * @return the oldHouseBaseExt
	 */
	public HouseBaseExtEntity getOldHouseBaseExt() {
		return oldHouseBaseExt;
	}

	/**
	 * @param oldHouseBaseExt the oldHouseBaseExt to set
	 */
	public void setOldHouseBaseExt(HouseBaseExtEntity oldHouseBaseExt) {
		this.oldHouseBaseExt = oldHouseBaseExt;
	}


	/**
	 * @return the oldHouseDesc
	 */
	public HouseDescEntity getOldHouseDesc() {
		return oldHouseDesc;
	}

	/**
	 * @param oldHouseDesc the oldHouseDesc to set
	 */
	public void setOldHouseDesc(HouseDescEntity oldHouseDesc) {
		this.oldHouseDesc = oldHouseDesc;
	}

	/**
	 * @return the oldHousePhyMsg
	 */
	public HousePhyMsgEntity getOldHousePhyMsg() {
		return oldHousePhyMsg;
	}

	/**
	 * @param oldHousePhyMsg the oldHousePhyMsg to set
	 */
	public void setOldHousePhyMsg(HousePhyMsgEntity oldHousePhyMsg) {
		this.oldHousePhyMsg = oldHousePhyMsg;
	}

	/**
	 * @return the oldHouseRoomExt
	 */
	public HouseRoomExtEntity getOldHouseRoomExt() {
		return oldHouseRoomExt;
	}

	/**
	 * @param oldHouseRoomExt the oldHouseRoomExt to set
	 */
	public void setOldHouseRoomExt(HouseRoomExtEntity oldHouseRoomExt) {
		this.oldHouseRoomExt = oldHouseRoomExt;
	}

	/**
	 * @return the houseRoomExt
	 */
	public HouseRoomExtEntity getHouseRoomExt() {
		return houseRoomExt;
	}

	/**
	 * @param houseRoomExt the houseRoomExt to set
	 */
	public void setHouseRoomExt(HouseRoomExtEntity houseRoomExt) {
		this.houseRoomExt = houseRoomExt;
	}

	/**
	 * 房源fid
	 */
	private String houseFid;
	
	/**
	 * 房间fid
	 */
	private String roomFid;
	
	/**
	 * 出租方式
	 */
	private Integer rentWay ;
	
	/**
	 * 创建人fid
	 */
	private  String createrFid;
	
	/**
	 * 创建人 类型
	 */
	private  Integer  createType;
	
	/**
	 * 修改字段来源 0=troy 1=pc 2=IOS 3=android 5=APP 6=其他
	 */
	private  Integer sourceType;

	/**
	 * 新增加字段,操作来源
	 * 用于标识是否来自于运营人员的直接修改
	 * 2.业务人员 枚举类CreaterTypeEnum.GUARD
	 * @author yanb
	 * @return
	 */
	private Integer operateSource;

	public Integer getOperateSource() {
		return operateSource;
	}

	public void setOperateSource(Integer operateSource) {
		this.operateSource = operateSource;
	}

	/**
	 * @return the houseFid
	 */
	public String getHouseFid() {
		return houseFid;
	}

	/**
	 * @param houseFid the houseFid to set
	 */
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	/**
	 * @return the roomFid
	 */
	public String getRoomFid() {
		return roomFid;
	}

	/**
	 * @param roomFid the roomFid to set
	 */
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}

	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
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
	 * @return the createType
	 */
	public Integer getCreateType() {
		return createType;
	}

	/**
	 * @param createType the createType to set
	 */
	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	/**
	 * @return the sourceType
	 */
	public Integer getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @return the houseBaseMsg
	 */
	public HouseBaseMsgEntity getHouseBaseMsg() {
		return houseBaseMsg;
	}

	/**
	 * @param houseBaseMsg the houseBaseMsg to set
	 */
	public void setHouseBaseMsg(HouseBaseMsgEntity houseBaseMsg) {
		this.houseBaseMsg = houseBaseMsg;
	}

	/**
	 * @return the houseRoomMsg
	 */
	public HouseRoomMsgEntity getHouseRoomMsg() {
		return houseRoomMsg;
	}

	/**
	 * @param houseRoomMsg the houseRoomMsg to set
	 */
	public void setHouseRoomMsg(HouseRoomMsgEntity houseRoomMsg) {
		this.houseRoomMsg = houseRoomMsg;
	}

	/**
	 * @return the houseBaseExt
	 */
	public HouseBaseExtEntity getHouseBaseExt() {
		return houseBaseExt;
	}

	/**
	 * @param houseBaseExt the houseBaseExt to set
	 */
	public void setHouseBaseExt(HouseBaseExtEntity houseBaseExt) {
		this.houseBaseExt = houseBaseExt;
	}

	/**
	 * @return the houseDesc
	 */
	public HouseDescEntity getHouseDesc() {
		return houseDesc;
	}

	/**
	 * @param houseDesc the houseDesc to set
	 */
	public void setHouseDesc(HouseDescEntity houseDesc) {
		this.houseDesc = houseDesc;
	}

	/**
	 * @return the housePhyMsg
	 */
	public HousePhyMsgEntity getHousePhyMsg() {
		return housePhyMsg;
	}

	/**
	 * @param housePhyMsg the housePhyMsg to set
	 */
	public void setHousePhyMsg(HousePhyMsgEntity housePhyMsg) {
		this.housePhyMsg = housePhyMsg;
	}

	/**
	 * @return the listHouseConfMsg
	 */
	public List<HouseConfMsgEntity> getListHouseConfMsg() {
		return ListHouseConfMsg;
	}

	/**
	 * @param listHouseConfMsg the listHouseConfMsg to set
	 */
	public void setListHouseConfMsg(List<HouseConfMsgEntity> listHouseConfMsg) {
		ListHouseConfMsg = listHouseConfMsg;
	}

	
	
}
