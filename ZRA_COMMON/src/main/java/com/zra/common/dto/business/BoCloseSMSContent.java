/**
 * @FileName: BoCloseSMSContent.java
 * @Package com.zra.common.dto.business
 * 
 * @author tianxf9
 * @created 2017年10月23日 下午3:19:06
 * 
 * Copyright 2011-2025 自如民宿 版权所有
 */
package com.zra.common.dto.business;

/**
 * <p>关闭商机发送的短信内容</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author tianxf9
 * @since 1.0
 * @version 1.0
 */
public class BoCloseSMSContent {
	
	private Integer id;
	
	private String content;
	
	private String describe;
	
	private Integer isSend;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}
	
	

}
