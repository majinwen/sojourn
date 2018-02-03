package com.ziroom.minsu.services.house.dto;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.services.common.dto.PageRequest;


/**
 * 
 * <p>房源审核记录dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class HouseOpLogSpDto extends PageRequest{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6599317569660421354L;

	@NotNull(message="{house.fid.null}")
	private String houseFid;
	//房间fid
	private String roomFid;
	//更改状态
	private Integer toStatus;
	//操作人类型
	private Integer operateType;
	public String getHouseFid() {
		return houseFid;
	}
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}
	public String getRoomFid() {
		return roomFid;
	}
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}
	public Integer getToStatus() {
		return toStatus;
	}
	public void setToStatus(Integer toStatus) {
		this.toStatus = toStatus;
	}
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	
	
	
}
