/**
 * @FileName: UserCusInfoVo.java
 * @Package com.ziroom.minsu.report.afi.entity
 * 
 * @author bushujie
 * @created 2016年9月20日 下午3:38:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.customer.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>客户信息报表实体类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class UserCusInfoVo extends BaseEntity {
	
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 2415220280750432809L;
	@FieldMeta(name="客户手机号",order=1)
	private String cusMobile;
	@FieldMeta(name="客户真实姓名",order=2)
	private String cusRealName;
	@FieldMeta(name="申请次数",order=3)
	private Long applayNum;
	@FieldMeta(name="预订成功次数",order=4)
	private Long  orderSuccNum;
	@FieldMeta(name="申请订单量",order=5)
	private Long applayOrderNum;
	@FieldMeta(name="订单间夜",order=6)
	private Long orderDiffNight;
	@FieldMeta(name="订单平均日租金（分）",order=7)
	private Long orderRentMoney;
	@FieldMeta(name="入住间夜",order=8)
	private Long statyNight;
	@FieldMeta(name="入住间夜平均日租金（分）",order=9)
	private Long statyNigthRentMoney;
	@FieldMeta(skip = true)
	private String uid;
	
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the cusMobile
	 */
	public String getCusMobile() {
		return cusMobile;
	}

	/**
	 * @param cusMobile the cusMobile to set
	 */
	public void setCusMobile(String cusMobile) {
		this.cusMobile = cusMobile;
	}

	/**
	 * @return the cusRealName
	 */
	public String getCusRealName() {
		return cusRealName;
	}

	/**
	 * @param cusRealName the cusRealName to set
	 */
	public void setCusRealName(String cusRealName) {
		this.cusRealName = cusRealName;
	}

	/**
	 * @return the applayNum
	 */
	public Long getApplayNum() {
		return applayNum;
	}

	/**
	 * @param applayNum the applayNum to set
	 */
	public void setApplayNum(Long applayNum) {
		this.applayNum = applayNum;
	}

	/**
	 * @return the applayOrderNum
	 */
	public Long getApplayOrderNum() {
		return applayOrderNum;
	}

	/**
	 * @param applayOrderNum the applayOrderNum to set
	 */
	public void setApplayOrderNum(Long applayOrderNum) {
		this.applayOrderNum = applayOrderNum;
	}

	/**
	 * @return the orderSuccNum
	 */
	public Long getOrderSuccNum() {
		return orderSuccNum;
	}

	/**
	 * @param orderSuccNum the orderSuccNum to set
	 */
	public void setOrderSuccNum(Long orderSuccNum) {
		this.orderSuccNum = orderSuccNum;
	}

	/**
	 * @return the orderDiffNight
	 */
	public Long getOrderDiffNight() {
		return orderDiffNight;
	}

	/**
	 * @param orderDiffNight the orderDiffNight to set
	 */
	public void setOrderDiffNight(Long orderDiffNight) {
		this.orderDiffNight = orderDiffNight;
	}

	/**
	 * @return the orderRentMoney
	 */
	public Long getOrderRentMoney() {
		return orderRentMoney;
	}

	/**
	 * @param orderRentMoney the orderRentMoney to set
	 */
	public void setOrderRentMoney(Long orderRentMoney) {
		this.orderRentMoney = orderRentMoney;
	}

	/**
	 * @return the statyNight
	 */
	public Long getStatyNight() {
		return statyNight;
	}

	/**
	 * @param statyNight the statyNight to set
	 */
	public void setStatyNight(Long statyNight) {
		this.statyNight = statyNight;
	}

	/**
	 * @return the statyNigthRentMoney
	 */
	public Long getStatyNigthRentMoney() {
		return statyNigthRentMoney;
	}

	/**
	 * @param statyNigthRentMoney the statyNigthRentMoney to set
	 */
	public void setStatyNigthRentMoney(Long statyNigthRentMoney) {
		this.statyNigthRentMoney = statyNigthRentMoney;
	}
}
