package com.zra.system.entity;

import java.util.Date;


public class EmployeeEntity {

	public EmployeeEntity() {
	}

	public EmployeeEntity(String _id) {
	}
	
	private String id;
	private String code;
	private String name;
	private Integer sex = 0;
	private String idCardNo;
	private Date birthday;
	private String email;
	private String mobile;
	private String telNo;
	private String faxNo;
	private String qqNo;
	private String msnNo;
	private String address_DL;
	private String address;
	private String zipCode;
	private String memo;
	private boolean valid = true;
	private Integer csrType;
	private String cicUserId;
	private String cicPwd;

	/**
	 * 非数据库信息、临时存储
	 */
	private String postId;
	
	private String departmentId;
	/**
	 * 为获取数据方便
	 */
	private String cityId;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getQqNo() {
		return qqNo;
	}

	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	public String getMsnNo() {
		return msnNo;
	}

	public void setMsnNo(String msnNo) {
		this.msnNo = msnNo;
	}

	public String getAddress_DL() {
		return address_DL;
	}

	public void setAddress_DL(String address_DL) {
		this.address_DL = address_DL;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Integer getCsrType() {
		return csrType;
	}

	public void setCsrType(Integer csrType) {
		this.csrType = csrType;
	}

	public String getCicUserId() {
		return cicUserId;
	}

	public void setCicUserId(String cicUserId) {
		this.cicUserId = cicUserId;
	}

	public String getCicPwd() {
		return cicPwd;
	}

	public void setCicPwd(String cicPwd) {
		this.cicPwd = cicPwd;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
}

