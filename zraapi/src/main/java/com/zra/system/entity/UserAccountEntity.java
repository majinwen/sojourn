package com.zra.system.entity;

import java.util.Date;

/**
 * 用户实体
 * 
 * @author wangws21 2016-8-1
 *
 */
public class UserAccountEntity {
	private String id;

	private String userAccount;

	private String username;

	private String password;

	private Integer isAdmin;

	private String linkEmployeeId;

	private Date updPswDate;

	private Integer requireUpdatePw;

	private Date lastLoginDate;

	private String dataBelong;

	private String lastLoginIp;

	private Integer loginFailTime;

	private Date acctLockTime;

	private Integer acctStatus;

	private String providerId;

	private String tempGjId;

	private Integer valid;

	private Date createTime;

	private String createrId;

	private Date updateTime;

	private String updaterId;

	private Integer isDel;

	private String cityId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLinkEmployeeId() {
		return linkEmployeeId;
	}

	public void setLinkEmployeeId(String linkEmployeeId) {
		this.linkEmployeeId = linkEmployeeId;
	}

	public Date getUpdPswDate() {
		return updPswDate;
	}

	public void setUpdPswDate(Date updPswDate) {
		this.updPswDate = updPswDate;
	}

	public Integer getRequireUpdatePw() {
		return requireUpdatePw;
	}

	public void setRequireUpdatePw(Integer requireUpdatePw) {
		this.requireUpdatePw = requireUpdatePw;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getDataBelong() {
		return dataBelong;
	}

	public void setDataBelong(String dataBelong) {
		this.dataBelong = dataBelong;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginFailTime() {
		return loginFailTime;
	}

	public void setLoginFailTime(Integer loginFailTime) {
		this.loginFailTime = loginFailTime;
	}

	public Date getAcctLockTime() {
		return acctLockTime;
	}

	public void setAcctLockTime(Date acctLockTime) {
		this.acctLockTime = acctLockTime;
	}

	public Integer getAcctStatus() {
		return acctStatus;
	}

	public void setAcctStatus(Integer acctStatus) {
		this.acctStatus = acctStatus;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getTempGjId() {
		return tempGjId;
	}

	public void setTempGjId(String tempGjId) {
		this.tempGjId = tempGjId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
}
