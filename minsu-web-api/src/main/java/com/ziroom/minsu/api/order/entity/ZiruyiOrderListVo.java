/**
 * @FileName: ZiruyiOrderListVo.java
 * @Package com.ziroom.minsu.api.order.entity
 * 
 * @author zl
 * @created 2017年5月5日 下午2:21:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class ZiruyiOrderListVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8275930599861180736L;
	/**
	 * 订单的业务id
	 */
	private  String orderBid;
	/**
	 * 订单号 
	 */
	private String orderNumber;
	/**
	 * 项目名称 
	 */
	private String projectName;
	/**
	 * 总金额 
	 */
	private Double amount;
	/** 
	 * 订单状态1-已支付；2-已入住；3-退款申请中；4-已退款；5-待支付；6-已退房；7-已取消 
	 */
	private Integer orderStatus;
	/**
	 *  订单开始时间  2017-05-15
	 */
	private String startDate;
	/** 
	 * 订单结束时间 2017-05-15
	 */
	private String endDate;
	/**
	 *  项目的销售电话 
	 */
	private String sellPhone;
	/** 
	 * 多少晚
	 */
	private Integer nights;
	/**
	 * 预订人名称(申请退款时需要此信息) 
	 */
	private String name;
	/**
	 * 房型信息
	 */
	private List<ZiruyiHouseForOrderListVo> houseTypeInfo;
	/** 
	 * 支付订单号
	 */
	private String paymentNumber;

	/**
	 *订单是否已评价0-无需评价；1-未评价；2-已评价 
	 */
	private String isEvaluate;
	/**
	 *  预订人手机号
	 */
	private String phone;
	/**
	 * 时间戳-for App改版
	 */
	private String createTime;
	/**
	 *  项目地址-for App改版
	 */
	private String address;	
	
    /**
     * 排序从0开始
     */
    private Integer sortIndex;
    
    /**
     * 订单类型
     * @see
     * com.ziroom.minsu.api.common.valeenum.OrderProjectTypeEnum
     */
    private Integer orderProjectType;

	public String getOrderBid() {
		return orderBid;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public Double getAmount() {
		return amount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getSellPhone() {
		return sellPhone;
	}

	public Integer getNights() {
		return nights;
	}

	public String getName() {
		return name;
	}

	public List<ZiruyiHouseForOrderListVo> getHouseTypeInfo() {
		return houseTypeInfo;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public String getIsEvaluate() {
		return isEvaluate;
	}

	public String getPhone() {
		return phone;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getAddress() {
		return address;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public Integer getOrderProjectType() {
		return orderProjectType;
	}

	public void setOrderBid(String orderBid) {
		this.orderBid = orderBid;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setSellPhone(String sellPhone) {
		this.sellPhone = sellPhone;
	}

	public void setNights(Integer nights) {
		this.nights = nights;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHouseTypeInfo(List<ZiruyiHouseForOrderListVo> houseTypeInfo) {
		this.houseTypeInfo = houseTypeInfo;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public void setOrderProjectType(Integer orderProjectType) {
		this.orderProjectType = orderProjectType;
	}
	
}
