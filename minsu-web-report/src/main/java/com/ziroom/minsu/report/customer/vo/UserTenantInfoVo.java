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
 * <p>房客信息报表实体类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp  2017/5/4.
 * @since 1.0
 * @version 1.0
 */
public class UserTenantInfoVo extends BaseEntity {
	
	
	/**
	 * 
	 */
	
	@FieldMeta(skip = true)
	private static final long serialVersionUID = -3759291857763486877L;
	@FieldMeta(name="预定人UID",order=1)
	private String tenantUID;
	@FieldMeta(name="房客姓名",order=2)
	private String tenantName;
	@FieldMeta(name="房客电话",order=3)
	private String tenantTel;
	@FieldMeta(name="房客性别",order=4)
	private String  tenantSex;
	@FieldMeta(name="房客年龄",order=5)
	private String tenantAge;
	@FieldMeta(name="活跃城市",order=6)
	private String activeCity;
	
	/**
	 * @return the tenantUID
	 */
	public String getTenantUID() {
		return tenantUID;
	}
	/**
	 * @param tenantUID the tenantUID to set
	 */
	public void setTenantUID(String tenantUID) {
		this.tenantUID = tenantUID;
	}
	/**
	 * @return the tenantName
	 */
	public String getTenantName() {
		return tenantName;
	}
	/**
	 * @param tenantName the tenantName to set
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	/**
	 * @return the tenantTel
	 */
	public String getTenantTel() {
		return tenantTel;
	}
	/**
	 * @param tenantTel the tenantTel to set
	 */
	public void setTenantTel(String tenantTel) {
		this.tenantTel = tenantTel;
	}
	/**
	 * @return the tenantSex
	 */
	public String getTenantSex() {
		return tenantSex;
	}
	/**
	 * @param tenantSex the tenantSex to set
	 */
	public void setTenantSex(String tenantSex) {
		this.tenantSex = tenantSex;
	}
	/**
	 * @return the tenantAge
	 */
	public String getTenantAge() {
		return tenantAge;
	}
	/**
	 * @param tenantAge the tenantAge to set
	 */
	public void setTenantAge(String tenantAge) {
		this.tenantAge = tenantAge;
	}
	/**
	 * @return the activeCity
	 */
	public String getActiveCity() {
		return activeCity;
	}
	/**
	 * @param activeCity the activeCity to set
	 */
	public void setActiveCity(String activeCity) {
		this.activeCity = activeCity;
	}
	
	
}
