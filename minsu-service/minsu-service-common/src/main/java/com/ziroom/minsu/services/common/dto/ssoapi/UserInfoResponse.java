package com.ziroom.minsu.services.common.dto.ssoapi;

import java.util.Date;

/**
* @author jixd on 2016年7月19日
* @version 1.0
* @since 1.0
*/
public class UserInfoResponse {

	public class Resp {
		
		private String id;
		private String uid;
		private String phone;
		private String email;
		private String lastOnlineTime;
		private Integer status;
		private Integer phoneChecked;
		private Integer emailChecked;
		private Date createTime;
		private Boolean hasPassword;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getLastOnlineTime() {
			return lastOnlineTime;
		}
		public void setLastOnlineTime(String lastOnlineTime) {
			this.lastOnlineTime = lastOnlineTime;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getPhoneChecked() {
			return phoneChecked;
		}
		public void setPhoneChecked(Integer phoneChecked) {
			this.phoneChecked = phoneChecked;
		}
		public Integer getEmailChecked() {
			return emailChecked;
		}
		public void setEmailChecked(Integer emailChecked) {
			this.emailChecked = emailChecked;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Boolean getHasPassword() {
			return hasPassword;
		}
		public void setHasPassword(Boolean hasPassword) {
			this.hasPassword = hasPassword;
		}
	}


	private String code;
	
	private String message;
	
	private Resp resp;
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Resp getResp() {
		return resp;
	}


	public void setResp(Resp resp) {
		this.resp = resp;
	}

	
}
