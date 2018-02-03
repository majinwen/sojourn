
package com.ziroom.minsu.services.basedata.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>发送邮件请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author busj
 * @since 1.0
 * @version 1.0
 */
public class EmailRequest implements Serializable{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 5162341021996896194L;

	/**
	 * 邮箱编码 
	 */
	private String emailCode;
	
	/**
	 * 邮箱地址
	 */
	private String emailAddr;
	
	/**
	 *  邮件title
	 */
	private String emailTitle;
	

	/**
	 * 参数map
	 */
	private Map<String, String> paramsMap=new HashMap<String, String>();


	/**
	 * @return the emailCode
	 */
	public String getEmailCode() {
		return emailCode;
	}

	/**
	 * @param emailCode the emailCode to set
	 */
	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}

	/**
	 * @return the emailAddr
	 */
	public String getEmailAddr() {
		return emailAddr;
	}

	/**
	 * @param emailAddr the emailAddr to set
	 */
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}
	
	/**
	 * @return the emailTitle
	 */
	public String getEmailTitle() {
		return emailTitle;
	}

	/**
	 * @param emailTitle the emailTitle to set
	 */
	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}
	
}
