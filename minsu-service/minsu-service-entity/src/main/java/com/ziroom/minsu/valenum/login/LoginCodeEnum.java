
package com.ziroom.minsu.valenum.login;

/**
 * <p>单点登录返回code的状态码</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE> 
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum LoginCodeEnum {

	SUCCESS("20000","成功"),
    VERIFICATION_CODE_ERROR("40001","验证码错误"),
    USER_ALREADY_EXISTS("40002","用户已存在"),
    PASSWORD_ERROR("40003","密码错误"),
    USER_NOT_EXISTS("40004","用户不存在"),
	TOKEN_INVALID("40006","token失效"),
	ACCOUNT_FROZEN("40007","账号被冻结"),
	ACCOUNT_BAN("40008","账号被封禁"),
	NOT_BE_TIED("40009","第三方账号为唯一登录方式无法解绑"),
	BE_TIED("40010","第三方账号已被绑定"),
	ACCESS_TOKEN_INVALID("50000","第三方access_token/openid无效"),
    SERVER_EXCEPTION("40009","服务器异常");
	
	
	LoginCodeEnum(String code,String value){
		this.code = code;
		this.value = value;
	}
	
	/**
	 * code 编码
	 */
	private String code;
	
	/**
	 * 中文含义
	 */
	private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
