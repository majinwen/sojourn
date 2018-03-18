package com.ziroom.zrp.service.trading.dto.customer;

import java.io.Serializable;
/**
 * <p>查询友家个人信息</p>
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
public class Status implements Serializable{
	
	private static final long serialVersionUID = -7125581858267753279L;
	/**
	 * 是否身份认证（1是 2否）
	 */
	private int certOk;	
	/**
	 * 是否学历认证（1是 2否）
	 */
	private int educationOk;	
 	/**
	 * 是否公司认证（1是 2否）
	 */
	private int companyOk;
 	/**
	 * 领英认证状态（1是 2否）
	 */
	private int linkOk;	
 	/**
	 * 是否授权开启自如信用（1是 2否）
	 */
	private int isAuthCredit;
	public int getCertOk() {
		return certOk;
	}
	public void setCertOk(int certOk) {
		this.certOk = certOk;
	}
	public int getEducationOk() {
		return educationOk;
	}
	public void setEducationOk(int educationOk) {
		this.educationOk = educationOk;
	}
	public int getCompanyOk() {
		return companyOk;
	}
	public void setCompanyOk(int companyOk) {
		this.companyOk = companyOk;
	}
	public int getLinkOk() {
		return linkOk;
	}
	public void setLinkOk(int linkOk) {
		this.linkOk = linkOk;
	}
	public int getIsAuthCredit() {
		return isAuthCredit;
	}
	public void setIsAuthCredit(int isAuthCredit) {
		this.isAuthCredit = isAuthCredit;
	}

}
