package com.ziroom.minsu.report.customer.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>房东信息查询条件</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/19.
 * @version 1.0
 * @since 1.0
 */
public class LandlordRequest extends PageRequest {

    private static final long serialVersionUID = 323422341446703L;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;


    /**
	 * 国家code
	 */
	private String nationCode;
    
    /**
     * 大区code
     */
    private String regionCode;
    
    /**
     * 城市code
     */
    private String cityCode;

	/**
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the nationCode
	 */
	public String getNationCode() {
		return nationCode;
	}

	/**
	 * @param nationCode the nationCode to set
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

    
}
