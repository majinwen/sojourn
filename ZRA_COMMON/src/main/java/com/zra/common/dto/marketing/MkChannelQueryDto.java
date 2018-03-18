package com.zra.common.dto.marketing;
/**
 * 渠道统计的数据查询dto
 * @author tianxf9
 *
 */
public class MkChannelQueryDto {
	
	private String startDate;
	
	private String endDate;
	
	private String cityId;
	
	private int channelType;
	

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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

}
