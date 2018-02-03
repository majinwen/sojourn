package com.ziroom.minsu.services.common.dto.ssoapi;
/**
* @author jixd on 2016年7月19日
* @version 1.0
* @since 1.0
*/
public class UserSimpleResponse {

	/**
	 * 状态码
	 */
	private String code;
	/**
	 * 消息
	 */
	private String message;
	
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
	
	
}
