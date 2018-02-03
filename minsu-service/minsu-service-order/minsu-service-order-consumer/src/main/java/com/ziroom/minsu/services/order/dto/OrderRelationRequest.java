
package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;


/**
 * <p>新旧订单的查询条件</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class OrderRelationRequest extends PageRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 97643741147430199L;
	
	  /**
     * 新订单编号
     */
    private String newOrderSn;

    /**
     * 旧订单编号
     */
    private String oldOrderSn;
    /**
	 * 新订单预订人姓名
	 */
	private String newUserName;
	/**
	 * 新订单预订人手机号
	 */
	private String newUserTel;
	
	/**
	 * 新订单创建时间开始
	 */
	private String createTimeStart;
	/**
	 * 新订单创建时间结束
	 */
	private String createTimeEnd;
	 /**
     * 审核状态 1=待审批 2=人工审核通过 3=系统审核通过 4=已拒绝 5=已付款
     */
    private Integer checkedStatus;
    
    /**
     * 新订单的房东姓名
     */
    private String newLanlordName;
    
    /**
     * 新订单国家code
     */
    private String nationCode;
    
    /**
     * 新订单的省code
     */
    private String provinceCode;
    /**
     * 新订单的城市code
     */
    private String cityCode;
    
    
	public String getNewOrderSn() {
		return newOrderSn;
	}
	public void setNewOrderSn(String newOrderSn) {
		this.newOrderSn = newOrderSn;
	}
	public String getOldOrderSn() {
		return oldOrderSn;
	}
	public void setOldOrderSn(String oldOrderSn) {
		this.oldOrderSn = oldOrderSn;
	}
	public String getNewUserName() {
		return newUserName;
	}
	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}
	public String getNewUserTel() {
		return newUserTel;
	}
	public void setNewUserTel(String newUserTel) {
		this.newUserTel = newUserTel;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Integer getCheckedStatus() {
		return checkedStatus;
	}
	public void setCheckedStatus(Integer checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
	public String getNewLanlordName() {
		return newLanlordName;
	}
	public void setNewLanlordName(String newLanlordName) {
		this.newLanlordName = newLanlordName;
	}
	public String getNationCode() {
		return nationCode;
	}
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	

}
