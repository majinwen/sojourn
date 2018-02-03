
package com.ziroom.minsu.services.basedata.dto;

import java.io.Serializable;
import java.util.Map;

import com.ziroom.minsu.services.common.sms.base.SmsMessage;

/**
 * <p>发送短信请求参数</p>
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
public class SmsRequest implements Serializable{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 5162341021996896194L;

	/**
	 * 短信编码 
	 */
	private String smsCode;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 参数map
	 */
	private Map<String, String> paramsMap;

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}
	
	
	
}
