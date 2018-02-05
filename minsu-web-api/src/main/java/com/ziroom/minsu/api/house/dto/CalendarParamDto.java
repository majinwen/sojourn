package com.ziroom.minsu.api.house.dto;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>出租日历查询参数</p>
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
public class CalendarParamDto extends BaseParamDto {

	//房源逻辑id
	private String houseBaseFid;
	
	//房间逻辑id
	private String houseRoomFid;
	
	//床位逻辑id
	private String bedFid;
	
	//出租方式
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;
	
	//开始时间
	@NotNull(message="{startDate.null}")
	private String startDate;
	
	//结束时间
	@NotNull(message="{endDate.null}")
	private String endDate;

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

	public String getBedFid() {
		return bedFid;
	}

	public void setBedFid(String bedFid) {
		this.bedFid = bedFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
