package com.ziroom.minsu.services.house.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * 
 * <p>房源房间信息dto</p>
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
public class HouseRoomListDto {
	
	// 房源逻辑id
	@NotNull(message="{house.base.fid.null}")
	private String houseBaseFid;
	
	// 操作步骤
	@NotNull(message="{house.operate.seq.null}")
	private Integer operateSeq;
	
	// 信息完成率
	@NotNull(message="{house.intactRate.null}")
	private Double intactRate;
	
	@NotEmpty(message="{house.room.list.null}")
	private List<HouseRoomMsgEntity> roomList = new ArrayList<HouseRoomMsgEntity>();
	
	public String getHouseBaseFid() {
		return houseBaseFid;
	}
	
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}
	
	public Integer getOperateSeq() {
		return operateSeq;
	}

	public void setOperateSeq(Integer operateSeq) {
		this.operateSeq = operateSeq;
	}

	public Double getIntactRate() {
		return intactRate;
	}

	public void setIntactRate(Double intactRate) {
		this.intactRate = intactRate;
	}

	public List<HouseRoomMsgEntity> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<HouseRoomMsgEntity> roomList) {
		this.roomList = roomList;
	}

}
