package com.ziroom.minsu.report.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/3/7 17:22
 * @version 1.0
 * @since 1.0
 */
public class OrderEvaluate2Request extends PageRequest {

    private static final long serialVersionUID = -6092140132260971016L;

    private List<String> cityList;

    private String region;
    private String city;
    private String createStartTime;
    private String createEndTime;
    private String tenantEvaStartTime;
    private String tenantEvaEndTime;
    private String landlordEvaStartTime;
    private String landlordEvaEndTime;
    private String realEndTimeStart;
    private String realEndTimeEnd;

    /**
	 * @return the realEndTimeStart
	 */
	public String getRealEndTimeStart() {
		return realEndTimeStart;
	}

	/**
	 * @param realEndTimeStart the realEndTimeStart to set
	 */
	public void setRealEndTimeStart(String realEndTimeStart) {
		this.realEndTimeStart = realEndTimeStart;
	}

	/**
	 * @return the realEndTimeEnd
	 */
	public String getRealEndTimeEnd() {
		return realEndTimeEnd;
	}

	/**
	 * @param realEndTimeEnd the realEndTimeEnd to set
	 */
	public void setRealEndTimeEnd(String realEndTimeEnd) {
		this.realEndTimeEnd = realEndTimeEnd;
	}

	public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getTenantEvaStartTime() {
        return tenantEvaStartTime;
    }

    public void setTenantEvaStartTime(String tenantEvaStartTime) {
        this.tenantEvaStartTime = tenantEvaStartTime;
    }

    public String getTenantEvaEndTime() {
        return tenantEvaEndTime;
    }

    public void setTenantEvaEndTime(String tenantEvaEndTime) {
        this.tenantEvaEndTime = tenantEvaEndTime;
    }

    public String getLandlordEvaStartTime() {
        return landlordEvaStartTime;
    }

    public void setLandlordEvaStartTime(String landlordEvaStartTime) {
        this.landlordEvaStartTime = landlordEvaStartTime;
    }

    public String getLandlordEvaEndTime() {
        return landlordEvaEndTime;
    }

    public void setLandlordEvaEndTime(String landlordEvaEndTime) {
        this.landlordEvaEndTime = landlordEvaEndTime;
    }
}