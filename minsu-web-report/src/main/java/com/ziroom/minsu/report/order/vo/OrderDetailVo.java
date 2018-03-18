package com.ziroom.minsu.report.order.vo;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>OrderDetailVo</p>
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

public class OrderDetailVo extends BaseEntity {
   /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="订单编号",order=1)
	private String orderSn ;
	
	@FieldMeta(skip = true)
	private String cityCode;
	
	@FieldMeta(name="城市名称",order=2)
	private String cityName;
	
	@FieldMeta(name="预订人姓名",order=3)
	private String userName;

	@FieldMeta(name="预订人电话",order=4)
	private String userTel;
	
	@FieldMeta(name="创建时间",order=5)
	private Date createTime;
	
	@FieldMeta(name="开始时间",order=6)
	private Date startTime;
	
	@FieldMeta(name="结束时间",order=6)
	private Date endTime;
	
	@FieldMeta(name="实际退房时间",order=7)
	private Date realEndTime;
	
	@FieldMeta(name="房源/房间",order=8)
	private String houseName;
	
	@FieldMeta(name="出租方式名称",order=9)
	private String rentWayName;
	
	@FieldMeta(name="房源地址",order=10)
	private String houseAddr;
	
	@FieldMeta(name="房东姓名",order=10)
	private String lanRealName;
	
	@FieldMeta(name="房东电话",order=10)
	private String lanMobile;
	
	@FieldMeta(name="维护管家工号",order=10)
	private String empGuardCode;
	
	@FieldMeta(name="维护管家姓名",order=10)
	private String empGuardName;
	
	@FieldMeta(name="地推管家工号",order=10)
	private String empPushCode;
	
	@FieldMeta(name="地推管家姓名",order=10)
	private String empPushName;

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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getRentWayName() {
		return rentWayName;
	}

	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public String getLanRealName() {
		return lanRealName;
	}

	public void setLanRealName(String lanRealName) {
		this.lanRealName = lanRealName;
	}

	public String getLanMobile() {
		return lanMobile;
	}

	public void setLanMobile(String lanMobile) {
		this.lanMobile = lanMobile;
	}

	public String getEmpGuardCode() {
		return empGuardCode;
	}

	public void setEmpGuardCode(String empGuardCode) {
		this.empGuardCode = empGuardCode;
	}

	public String getEmpGuardName() {
		return empGuardName;
	}

	public void setEmpGuardName(String empGuardName) {
		this.empGuardName = empGuardName;
	}

	public String getEmpPushCode() {
		return empPushCode;
	}

	public void setEmpPushCode(String empPushCode) {
		this.empPushCode = empPushCode;
	}

	public String getEmpPushName() {
		return empPushName;
	}

	public void setEmpPushName(String empPushName) {
		this.empPushName = empPushName;
	}
	
	
}
