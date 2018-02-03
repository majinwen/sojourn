package com.ziroom.minsu.services.house.entity;

import java.math.BigDecimal;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * 
 * <p>房源房间信息vo</p>
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
public class RoomMsgVo extends HouseRoomMsgEntity {

	/**
	 * 序列化字段
	 */
	private static final long serialVersionUID = 2127382004433267389L;

	/**
	 * 房间fid(用于长租优惠价格设置第一次没有roomfid的时候)
	 */
	private String houseRoomFid;

	/**
	 * 房间日价格(元)
	 */
	private BigDecimal leasePrice;
	
	/**
	 * 房态中文名称
	 */
	private String houseStauStr;
	
	/**
	 * 房间床位集合
	 */
	private List<HouseBedMsgEntity> bedList;
	
	/**
	 * 房间扩展信息
	 */
	private HouseRoomExtEntity roomExtEntity;


	public HouseRoomExtEntity getRoomExtEntity() {
		return roomExtEntity;
	}

	public void setRoomExtEntity(HouseRoomExtEntity roomExtEntity) {
		this.roomExtEntity = roomExtEntity;
	}

	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	/**
	 * @return the houseStauStr
	 */
	public String getHouseStauStr() {
		return houseStauStr;
	}

	/**
	 * @param houseStauStr the houseStauStr to set
	 */
	public void setHouseStauStr(String houseStauStr) {
		this.houseStauStr = houseStauStr;
	}

	public BigDecimal getLeasePrice() {
		return leasePrice;
	}
	
	public void setLeasePrice(BigDecimal leasePrice) {
		this.leasePrice = leasePrice;
	}

	public List<HouseBedMsgEntity> getBedList() {
		return bedList;
	}

	public void setBedList(List<HouseBedMsgEntity> bedList) {
		this.bedList = bedList;
	}
	
}
