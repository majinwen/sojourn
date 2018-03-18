/**
 * @FileName: HouseAddressRequest.java
 * @Package com.ziroom.minsu.report.house.dto
 * 
 * @author baiwei
 * @created 2017年5月2日 下午8:01:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.dto;

import java.util.List;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.basedata.dto.NationRegionCityRequest;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author baiwei
 * @since 1.0
 * @version 1.0
 */
public class HouseAddressRequest extends NationRegionCityRequest {

	private static final long serialVersionUID = 1635974118758783807L;
	
	protected String nationCode;
	protected String regionFid;
	protected String cityCode;
	private List<String> cityList;
	private String createStartTime;
	private String createEndTime;
	private String houseStatus;
	/** 出租方式 */
    private  Integer rentWay ;
    /** methodName名字 */
    private  String methodName ;
    
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
	 * @return the regionFid
	 */
	public String getRegionFid() {
		return regionFid;
	}
	/**
	 * @param regionFid the regionFid to set
	 */
	public void setRegionFid(String regionFid) {
		this.regionFid = regionFid;
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
	/**
	 * @return the cityList
	 */
	public List<String> getCityList() {
		return cityList;
	}
	/**
	 * @param cityList the cityList to set
	 */
	public void setCityList(List<String> cityList) {
		this.cityList = cityList;
	}
	/**
	 * @return the createStartTime
	 */
	public String getCreateStartTime() {
		return createStartTime;
	}
	/**
	 * @param createStartTime the createStartTime to set
	 */
	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}
	/**
	 * @return the createEndTime
	 */
	public String getCreateEndTime() {
		return createEndTime;
	}
	/**
	 * @param createEndTime the createEndTime to set
	 */
	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}
	/**
	 * @return the houseStatus
	 */
	public String getHouseStatus() {
		return houseStatus;
	}
	/**
	 * @param houseStatus the houseStatus to set
	 */
	public void setHouseStatus(String houseStatus) {
		this.houseStatus = houseStatus;
	}
	/**
	 * @return the rentWay
	 */
	public Integer getRentWay() {
		return rentWay;
	}
	/**
	 * @param rentWay the rentWay to set
	 */
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 
	 * 校验当前的参数是否全部为空
	 *
	 * @author baiwei
	 * @created 2017年5月2日 下午8:09:30
	 *
	 * @return
	 */
	public boolean checkEmpty(){
        if (Check.NuNStr(this.nationCode)
                && Check.NuNStr(this.regionFid)
                && Check.NuNStr(this.cityCode)
                ){
            return true;
        }else {
            return false;
        }
    }
	
}
