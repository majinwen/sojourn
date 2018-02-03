package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;

/**
 * 
 * <p>房源扩展信息dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class HouseBaseExtDescDto extends HouseBaseExtEntity{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -4131851515017140795L;

	//当前修改的分租roomFid
	private String roomFid;
	
	private HouseDescEntity houseDescEntity;
	
	private HouseConfMsgEntity houseConfMsgEntity;

	/**
	 * 步骤
	 */
	private Integer step;
	
	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public HouseConfMsgEntity getHouseConfMsgEntity() {
		return houseConfMsgEntity;
	}

	public void setHouseConfMsgEntity(HouseConfMsgEntity houseConfMsgEntity) {
		this.houseConfMsgEntity = houseConfMsgEntity;
	}

	public HouseDescEntity getHouseDescEntity() {
		return houseDescEntity;
	}

	public void setHouseDescEntity(HouseDescEntity houseDescEntity) {
		this.houseDescEntity = houseDescEntity;
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	} 
	

}
