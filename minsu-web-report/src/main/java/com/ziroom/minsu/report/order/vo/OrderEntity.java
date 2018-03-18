package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>OrderEntity</p>
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

public class OrderEntity extends BaseEntity {
   /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="订单编号",order=1)
	private String orderSn ;
	
	@FieldMeta(name="城市名称",order=2)
	private String cityCode;
	
	@FieldMeta(name="房东姓名",order=3)
	private String landlordName;

	@FieldMeta(name="客户姓名",order=4)
	private String userName;
	
	@FieldMeta(name="订单总额",order=5)
	private Integer sumMoney;
	
	@FieldMeta(name="房租总额",order=6)
	private Integer rentalMoney;
	
	@FieldMeta(name="房东预计佣金",order=7)
	private Integer lanCommMoney;
	
	@FieldMeta(name="客户预计佣金",order=8)
	private Integer userCommMoney;
	
	@FieldMeta(name="房东实际佣金",order=9)
	private Integer realLanMoney;
	
	@FieldMeta(name="客户实际佣金",order=10)
	private Integer realUserMoney;
	
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
	public String getLandlordName() {
		return landlordName;
	}
	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(Integer sumMoney) {
		this.sumMoney = sumMoney;
	}
	public Integer getRentalMoney() {
		return rentalMoney;
	}
	public void setRentalMoney(Integer rentalMoney) {
		this.rentalMoney = rentalMoney;
	}
	public Integer getLanCommMoney() {
		return lanCommMoney;
	}
	public void setLanCommMoney(Integer lanCommMoney) {
		this.lanCommMoney = lanCommMoney;
	}
	public Integer getUserCommMoney() {
		return userCommMoney;
	}
	public void setUserCommMoney(Integer userCommMoney) {
		this.userCommMoney = userCommMoney;
	}
	public Integer getRealLanMoney() {
		return realLanMoney;
	}
	public void setRealLanMoney(Integer realLanMoney) {
		this.realLanMoney = realLanMoney;
	}
	public Integer getRealUserMoney() {
		return realUserMoney;
	}
	public void setRealUserMoney(Integer realUserMoney) {
		this.realUserMoney = realUserMoney;
	}
	
	
	
	
}
