/**
 * @FileName: MailAttachments.java
 * @Package com.ziroom.minsu.services.common.sms.base
 * 
 * @author yd
 * @created 2016年4月2日 下午6:16:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.sms.base;

import java.io.Serializable;

/**
 * <p>邮件附件实体</p>
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
public class MailAttachments  implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -9105886075603529747L;
	/**
	 * 文件名（ 必填）
	 */
	private String filename;
	/**
	 * 文件内容字节的base64编码串（ 必填）
	 */
	private String base64;
	
	/**
	 * 不是必填
	 * 文件类型，支持常见后缀自动填充。默认支持见底下FQA，不支持的需要自己传此参数。  
	 */
	private String mime;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	@Override
	public String toString() {
		return "MailAttachments [filename=" + filename + ", base64=" + base64
				+ ", mime=" + mime + "]";
	}
	
	

}
