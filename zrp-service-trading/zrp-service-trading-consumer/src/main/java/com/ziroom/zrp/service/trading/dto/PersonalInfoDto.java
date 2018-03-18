package com.ziroom.zrp.service.trading.dto;

import com.ziroom.zrp.service.trading.dto.customer.*;

import java.io.Serializable;
/**
 * <p>查询友家个人资质信息实体类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月21日
 * @since 1.0
 */
public class PersonalInfoDto  implements Serializable {
	
	
	private static final long serialVersionUID = 2922472867513565699L;

	private Profile profile;
	
	private Base base;
	
	private Cert cert;
	
	private Login login;
	
	private Extend extend;
	
	private Status status;
	
	private Education education;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public Cert getCert() {
		return cert;
	}

	public void setCert(Cert cert) {
		this.cert = cert;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Extend getExtend() {
		return extend;
	}

	public void setExtend(Extend extend) {
		this.extend = extend;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}
}
