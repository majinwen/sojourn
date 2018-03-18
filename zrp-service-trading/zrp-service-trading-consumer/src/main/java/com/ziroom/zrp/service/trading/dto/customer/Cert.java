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
public class Cert implements Serializable{
	
	private static final long serialVersionUID = -3661114604066207011L;
	/**
	 * ID
	 */
	private String id;
	
	private String base_id;
	 
	private String uid;
	/**
	 * 证件类型 1身份证
	 */
	private String  cert_type;
	/**
	 * 证件号码
	 */
	private String  cert_num;
	
	private String last_modify_time;
	
	private String user_cert1;
	
	private String user_cert2;
	
	private String user_cert3;
	
	
	/**
	 * 用户真实姓名
	 */
	private String  real_name;
	
	private String user_type;
	
	private String company_type;
	
	private String is_default;
	
	private String create_time;
	
	private String cert_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBase_id() {
		return base_id;
	}

	public void setBase_id(String base_id) {
		this.base_id = base_id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getLast_modify_time() {
		return last_modify_time;
	}

	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}

	public String getUser_cert1() {
		return user_cert1;
	}

	public void setUser_cert1(String user_cert1) {
		this.user_cert1 = user_cert1;
	}

	public String getUser_cert2() {
		return user_cert2;
	}

	public void setUser_cert2(String user_cert2) {
		this.user_cert2 = user_cert2;
	}

	public String getUser_cert3() {
		return user_cert3;
	}

	public void setUser_cert3(String user_cert3) {
		this.user_cert3 = user_cert3;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getCompany_type() {
		return company_type;
	}

	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCert_id() {
		return cert_id;
	}

	public void setCert_id(String cert_id) {
		this.cert_id = cert_id;
	}

}
