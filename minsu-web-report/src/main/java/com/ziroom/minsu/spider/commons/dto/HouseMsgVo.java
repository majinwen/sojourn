package com.ziroom.minsu.spider.commons.dto;

import java.util.List;

import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;


/**
 * 
 * <p>特洛伊查询详情vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseMsgVo extends HouseBaseMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6629832848802848501L;
	
	// 房源物理信息
	private HousePhyMsgEntity housePhyMsg;

	// 房源基础信息扩展
	private HouseBaseExtEntity houseBaseExt;
	
	// 房源描述信息
	private HouseDescEntity houseDesc;
	
	// 房源配置信息集合
	private List<HouseConfMsgEntity> houseConfList;
	
	// 房源照片信息集合
	private List<HousePicMsgEntity> housePicList;
	
	// 房源房间集合
	private List<RoomMsgVo> roomList;
	
	//房源维护管家
	private HouseGuardRelEntity houseGuardRel;
	

	public HouseGuardRelEntity getHouseGuardRel() {
		return houseGuardRel;
	}

	public void setHouseGuardRel(HouseGuardRelEntity houseGuardRel) {
		this.houseGuardRel = houseGuardRel;
	}

	public HousePhyMsgEntity getHousePhyMsg() {
		return housePhyMsg;
	}
	
	public void setHousePhyMsg(HousePhyMsgEntity housePhyMsg) {
		this.housePhyMsg = housePhyMsg;
	}
	
	public HouseBaseExtEntity getHouseBaseExt() {
		return houseBaseExt;
	}
	
	public void setHouseBaseExt(HouseBaseExtEntity houseBaseExt) {
		this.houseBaseExt = houseBaseExt;
	}
	
	public HouseDescEntity getHouseDesc() {
		return houseDesc;
	}
	
	public void setHouseDesc(HouseDescEntity houseDesc) {
		this.houseDesc = houseDesc;
	}
	
	public List<HouseConfMsgEntity> getHouseConfList() {
		return houseConfList;
	}
	
	public void setHouseConfList(List<HouseConfMsgEntity> houseConfList) {
		this.houseConfList = houseConfList;
	}
	
	public List<HousePicMsgEntity> getHousePicList() {
		return housePicList;
	}
	
	public void setHousePicList(List<HousePicMsgEntity> housePicList) {
		this.housePicList = housePicList;
	}

	public List<RoomMsgVo> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomMsgVo> roomList) {
		this.roomList = roomList;
	}
	
}
