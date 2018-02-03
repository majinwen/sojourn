/**
 * @FileName: CustomerAuditRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author lunan
 * @created 2017年8月9日 下午2:32:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerOperHistoryEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class CustomerAuditRequest extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6877580773382442681L;
	
	//审核后，需要改变的用户基本信息
	private CustomerBaseMsgEntity customerBaseMsg;
	
	//审核后，需要改变的用户个人介绍
	private CustomerBaseMsgExtEntity customerBaseMsgExt;
	
	//审核后，需要改变的用户头像
	private CustomerPicMsgEntity customerPicMsg;
	
	//审核后，t_customer_update_field_audit_newlog表中，需要修改的对象map
	private Map<String, Object> fieldAuditNewLogMap;
	
	//审核记录
	private CustomerOperHistoryEntity historyEntity;
	
	
	/**
	 * @return the historyEntity
	 */
	public CustomerOperHistoryEntity getHistoryEntity() {
		return historyEntity;
	}

	/**
	 * @param historyEntity the historyEntity to set
	 */
	public void setHistoryEntity(CustomerOperHistoryEntity historyEntity) {
		this.historyEntity = historyEntity;
	}

	public CustomerBaseMsgEntity getCustomerBaseMsg() {
		return customerBaseMsg;
	}

	public void setCustomerBaseMsg(CustomerBaseMsgEntity customerBaseMsg) {
		this.customerBaseMsg = customerBaseMsg;
	}

	public CustomerBaseMsgExtEntity getCustomerBaseMsgExt() {
		return customerBaseMsgExt;
	}

	public void setCustomerBaseMsgExt(CustomerBaseMsgExtEntity customerBaseMsgExt) {
		this.customerBaseMsgExt = customerBaseMsgExt;
	}

	public CustomerPicMsgEntity getCustomerPicMsg() {
		return customerPicMsg;
	}

	public void setCustomerPicMsg(CustomerPicMsgEntity customerPicMsg) {
		this.customerPicMsg = customerPicMsg;
	}

	public Map<String, Object> getFieldAuditNewLogMap() {
		return fieldAuditNewLogMap;
	}

	public void setFieldAuditNewLogMap(Map<String, Object> fieldAuditNewLogMap) {
		this.fieldAuditNewLogMap = fieldAuditNewLogMap;
	}

	

}
