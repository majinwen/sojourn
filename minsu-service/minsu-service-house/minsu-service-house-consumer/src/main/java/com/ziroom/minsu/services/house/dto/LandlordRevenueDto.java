package com.ziroom.minsu.services.house.dto;

import javax.validation.constraints.NotNull;


/**
 * <p>房东收益dto</p>
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
public class LandlordRevenueDto{
	
	/**
     * 房东uid
     */
	@NotNull(message="{landlordUid.null}")
    private String landlordUid;
	
	/**
	 * 房源逻辑id
	 */
	private String houseBaseFid;

	/**
	 * 房源名称
	 */
	private String houseName;
    
    /**
     * 日期（月）MM
     */
    private Integer statisticsDateMonth;
    
    /**
     * 日期（年）yyyy
     */
    private Integer statisticsDateYear;

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public Integer getStatisticsDateMonth() {
		return statisticsDateMonth;
	}

	public void setStatisticsDateMonth(Integer statisticsDateMonth) {
		this.statisticsDateMonth = statisticsDateMonth;
	}

	public Integer getStatisticsDateYear() {
		return statisticsDateYear;
	}

	public void setStatisticsDateYear(Integer statisticsDateYear) {
		this.statisticsDateYear = statisticsDateYear;
	}

}
