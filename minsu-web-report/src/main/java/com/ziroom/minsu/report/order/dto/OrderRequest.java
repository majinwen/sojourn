package com.ziroom.minsu.report.order.dto;

import java.util.List;

import com.ziroom.minsu.report.basedata.dto.CityRegionRequest;
import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>订单请求参数</p>
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
public class OrderRequest extends CityRegionRequest {
    private static final long serialVersionUID = -8238938615387241073L;
	
    private  String orderSn ;
	
	/** 城市code */
    private  String cityCode ;
    
    /** 限制 开始时间 */
    private  String beginTime ;
    
    /** 限制 结束时间 */
    private  String endTime ;
    
    /** 用户类型 */
    private Integer evaUserType;
    
    /** 1:整租 2：合租 */
    private Integer requestType;
    
    /** 房东电话*/
    private  String lanCustomerMobile ;
    
    /** 地推管家 */
    private  String empGuardName ;
    
    /** 维护管家 */
    private  String empPushName ;
    
    /**
     * 收入类型
     */
    private List<Integer> listIncomeType;
    
    
    /**
	 * @return the listIncomeType
	 */
	public List<Integer> getListIncomeType() {
		return listIncomeType;
	}

	/**
	 * @param listIncomeType the listIncomeType to set
	 */
	public void setListIncomeType(List<Integer> listIncomeType) {
		this.listIncomeType = listIncomeType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/** 方法名*/
    private  String methodName ;
    
    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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

	public Integer getEvaUserType() {
		return evaUserType;
	}

	public void setEvaUserType(Integer evaUserType) {
		this.evaUserType = evaUserType;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public String getLanCustomerMobile() {
		return lanCustomerMobile;
	}

	public void setLanCustomerMobile(String lanCustomerMobile) {
		this.lanCustomerMobile = lanCustomerMobile;
	}

	public String getEmpGuardName() {
		return empGuardName;
	}

	public void setEmpGuardName(String empGuardName) {
		this.empGuardName = empGuardName;
	}

	public String getEmpPushName() {
		return empPushName;
	}

	public void setEmpPushName(String empPushName) {
		this.empPushName = empPushName;
	}

	
	

}
