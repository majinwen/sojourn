/**
 * @FileName: MailMessage.java
 * @Package com.ziroom.minsu.services.common.sms.base
 * 
 * @author yd
 * @created 2016年4月2日 下午6:19:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.sms.base;

/**
 * <p>邮件实体</p>
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
public class MailMessage extends BaseMessage{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4333058373481838128L;
	
	/**
	 * 	抄送，多个用逗号隔开
	 */
	private String cc;
	
	/**
	 * 标题(必填)
	 */
	private String title;
	/**
	 * 附件信息
	 */
	//private MailAttachments mailAttachments;
	
	public MailMessage(){};
	
	public MailMessage(String to, String content){
		super(to,content);
	}
	
	public MailMessage(String to, String content, String title){
		super(to,content);
		this.title = title;
	}
	
/*	public MailMessage(String to, String content,MailAttachments mailAttachments){
		super(to,content);
		this.mailAttachments = mailAttachments;
	}*/
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
/*	public MailAttachments getMailAttachments() {
		return mailAttachments;
	}
	public void setMailAttachments(MailAttachments mailAttachments) {
		this.mailAttachments = mailAttachments;
	}*/
	/*@Override
	public String toString() {
		return "MailMessage [cc=" + cc + ", title=" + title
				+ ", mailAttachments=" + mailAttachments + "]";
	}*/

	@Override
	public String toString() {
		return "MailMessage [cc=" + cc + ", title=" + title + "]";
	}


	

}
