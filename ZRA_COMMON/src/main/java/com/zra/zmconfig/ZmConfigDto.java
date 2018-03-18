package com.zra.zmconfig;

import java.util.Date;

public class ZmConfigDto {

	private String systemId;
	
	private String key;
	
	private String value;
	
	private String desc;
	
	private Date createTime;
	
	public ZmConfigDto(){
		
	}
			

	public ZmConfigDto(String systemId, String key, String value, String desc, Date createTime) {
		super();
		this.systemId = systemId;
		this.key = key;
		this.value = value;
		this.desc = desc;
		this.createTime = createTime;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
