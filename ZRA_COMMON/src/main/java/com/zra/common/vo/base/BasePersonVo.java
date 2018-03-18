package com.zra.common.vo.base;

public class BasePersonVo {
	/**
	 * 签约人姓名
	 */
	private String name;
	/**
	 * 签约人证件号
	 */
	private String certNo;
	/**
	 * 签约人证件类型
	 */
	private String certTypeName;
	
	private Integer certType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCertTypeName() {
		return certTypeName;
	}
	public void setCertTypeName(String certTypeName) {
		this.certTypeName = certTypeName;
	}
	public Integer getCertType() {
		return certType;
	}
	public void setCertType(Integer certType) {
		this.certType = certType;
	}
}
