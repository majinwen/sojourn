/**
 * @FileName: BaseMessage.java
 * @Package com.ziroom.minsu.services.common.sms
 * 
 * @author yd
 * @created 2016年4月2日 下午5:53:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.sms.base;

import java.io.Serializable;

import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;

/**
 * <p>基本消息实体</p>
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
public class BaseMessage   implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 8397359175347681151L;
	
	/**
	 * 收信人（对应to），多个用逗号隔开 必填
	 */
	private String to ;
	/**
	 * 短信内容,不需要增加签名   必填
	 */
	private String content;
	
	/**
	 * 短信token
	 */
	private String token = SystemGlobalsUtils.getValue("sms.token");
	
	/**
	 *  国际码 :默认中国
	 */
	private String mobileNationCode = SysConst.MOBILE_NATION_CODE;

	public BaseMessage(){};
	
	public BaseMessage(String to, String content) {
		this.to = to;
		this.content = content;
	}
	public BaseMessage(String to, String content,String mobileNationCode) {
		this.to = to;
		this.content = content;
		this.mobileNationCode = mobileNationCode;
	}

	/**
	 * @return the mobileNationCode
	 */
	public String getMobileNationCode() {
		return mobileNationCode;
	}

	/**
	 * @param mobileNationCode the mobileNationCode to set
	 */
	public void setMobileNationCode(String mobileNationCode) {
		this.mobileNationCode = mobileNationCode;
	}

	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "BaseMessage [to=" + to + ", content=" + content + ", token="
				+ token + "]";
	}
	
	

}
