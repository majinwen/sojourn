
package com.ziroom.minsu.mapp.common.enumvalue;

/**
 * <p>用户注册 获取验证码类型枚举
 * 	验证码类型：1.手机/邮箱注册，2.手机/邮箱修改，3重置密码
 * </p>
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
public enum RigisterVerifyCodeType {

	VERIFY_RIGISTER(1,"手机/邮箱注册"),
	VERIFY_UPDATE(2,"手机/邮箱修改"),
	VERIFY_RESET_PASSWORD(3,"重置密码");

	RigisterVerifyCodeType(int code,String value){
		this.code = code;
		this.value = value;
	}

	/**
	 * code 值
	 */
	private int code;

	/**
	 * 中文值
	 */
	private  String value;

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * get RigisterVerifyCodeType by code
	 *
	 * @author yd
	 * @created 2016年5月15日 下午10:38:18
	 *
	 * @param code
	 * @return
	 */
	public static RigisterVerifyCodeType getRigisterVerifyCodeTypeByCode(int code){
		
		for (RigisterVerifyCodeType rigisterVerifyCodeType : RigisterVerifyCodeType.values()) {
			
			if(code == rigisterVerifyCodeType.getCode())
				return rigisterVerifyCodeType;
		} 
		return null;
	}


}
