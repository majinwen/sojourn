package com.zra.business.entity.vo;

import java.io.Serializable;

import com.zra.business.entity.BusinessResultEntity;

public class BusinessResultVo extends BusinessResultEntity implements Serializable{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2994212269894110160L;
	
	/**
     * 客户电话
     */
	private String customerMobile;
	
	/**
	 * 意向项目id
	 */
    private String projectId;
    
    
    /**
     * 管家id  商机表管家id
     */
    private String businessZoId;

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getBusinessZoId() {
		return businessZoId;
	}

	public void setBusinessZoId(String businessZoId) {
		this.businessZoId = businessZoId;
	}
}