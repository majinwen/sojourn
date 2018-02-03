package com.ziroom.minsu.services.common.sms.base;

import java.io.Serializable;

/**
 * 短信服务工具
 * @author yd
 * @date 2016-03-31
 * @version 1.0
 *
 */
public class SmsMessage  extends BaseMessage implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 2390834953107482346L;
	
	
	public SmsMessage(String to, String content) {
		super(to,content);
	}

	public SmsMessage(String to, String content,String mobileNationCode) {
		super(to,content,mobileNationCode);
	}
}
