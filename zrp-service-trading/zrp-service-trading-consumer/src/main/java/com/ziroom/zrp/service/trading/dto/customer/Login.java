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
public class Login implements Serializable{
	
	private static final long serialVersionUID = 9060022873344326041L;
	/**
	 * 登录名（一般为手机号或邮箱）
	 */
	private String login_name;
	/**
	 * 对一个的token
	 */
	private String token;
	
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
