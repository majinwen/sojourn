package com.ziroom.minsu.report.house.dto;

import java.util.Date;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
public class HouseRequest extends PageRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7610942746679148374L;
	/** 房源状态 */
    private  Integer houseStatus ;
    /** 改变后状态 */
    private  Integer toStatus ;
    /** 房东uid */
    private  String landlordUid ;
    /** 维护管家员工号 */
    private  String empGuardCode ;
    /** 城市code */
    private  String cityCode ;
    
    /** 限制 开始时间 */
    private  String beginTime ;
    
    /** 限制 结束时间 */
    private  String endTime ;

    /** 出租方式 */
    private  Integer rentWay ;
    
    /** excel名字 */
    private  String excelName ;
    
    /** methodName名字 */
    private  String methodName ;
    
	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public Integer getToStatus() {
		return toStatus;
	}

	public void setToStatus(Integer toStatus) {
		this.toStatus = toStatus;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getEmpGuardCode() {
		return empGuardCode;
	}

	public void setEmpGuardCode(String empGuardCode) {
		this.empGuardCode = empGuardCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
    
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
   
}
