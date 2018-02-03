package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单跟进的请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class OrderFollowRequest extends PageRequest {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -6482342824233964L;

	/** 用户uid */
	private String userUid;

	private List<String> userUidList;

	/**  用户名称  */
	private String userName;

	/** 预订人手机号 */
	private String userTel;

	/** 房东uid */
	private String landlordUid;

	/** 房东姓名  */
	private String landlordName;

	/** 房东姓名  */
	private String landlordTel;

	/** 订单编号*/
	private String houseSn;

	/** 订单编号 */
	private String orderSn;

	/** 房源别名 */
	private String houseName;

	/** 城市code */
	private String cityCode;

	/** 国家code */
	private String nationCode;

	/** 省code */
	private String provinceCode;

	/**  区code  */
	private String areaCode;

	/** 订单状态 */
	private Integer orderStatus;

	/** 支付状态  */
	private String payStatus;

	/** 有效时间  */
	private Date limitTime;

	public List<String> getUserUidList() {
		return userUidList;
	}

	public void setUserUidList(List<String> userUidList) {
		this.userUidList = userUidList;
	}

	public Date getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
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

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordTel() {
		return landlordTel;
	}

	public void setLandlordTel(String landlordTel) {
		this.landlordTel = landlordTel;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
}
