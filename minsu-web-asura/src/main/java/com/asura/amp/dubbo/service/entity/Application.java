package com.asura.amp.dubbo.service.entity;

import com.asura.framework.base.entity.BaseEntity;

public class Application extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String status;
	
	private String mockType;
	
	private boolean comsumer = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMockType() {
		return mockType;
	}

	public void setMockType(String mockType) {
		this.mockType = mockType;
	}

	public boolean isComsumer() {
		return comsumer;
	}

	public void setComsumer(boolean comsumer) {
		this.comsumer = comsumer;
	}
}
