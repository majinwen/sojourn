/**
 * @FileName: CustomerSearchVo.java
 * @Package com.ziroom.minsu.services.customer.entity
 * 
 * @author loushuai
 * @created 2017年10月29日 下午12:28:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class CustomerSearchVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1274655660328438299L;

	private CustomerBaseMsgEntity customerBaseMsg;
	
	 /** 房东是否是是在30天审核通过 */
    private Integer isAuditPassIn30Days = YesOrNoEnum.NO.getCode();

	public CustomerBaseMsgEntity getCustomerBaseMsg() {
		return customerBaseMsg;
	}

	public void setCustomerBaseMsg(CustomerBaseMsgEntity customerBaseMsg) {
		this.customerBaseMsg = customerBaseMsg;
	}

	public Integer getIsAuditPassIn30Days() {
		return isAuditPassIn30Days;
	}

	public void setIsAuditPassIn30Days(Integer isAuditPassIn30Days) {
		this.isAuditPassIn30Days = isAuditPassIn30Days;
	}
    
}
