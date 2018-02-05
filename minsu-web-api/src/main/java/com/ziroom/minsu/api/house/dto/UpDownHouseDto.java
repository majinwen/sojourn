package com.ziroom.minsu.api.house.dto;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>房源上下架参数对象</p>
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
public class UpDownHouseDto extends BaseParamDto {

	/**
	 * 房源逻辑id
	 */
	private String houseBaseFid;
	
	/**
	 * 房间逻辑id
	 */
	private String houseRoomFid;
	
	/**
	 * 出租方式 @RentWayEnum
	 */
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;
	
	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	
}
