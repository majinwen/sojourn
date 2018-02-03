package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * 
 * <p>房间房东</p>
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
public class RoomLandlordVo extends HouseRoomMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8912178155483466103L;
	
	// 房间逻辑id
	private String landlordUid;

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	
}
