/**
 * @FileName: AppMsgBaseVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2016年9月22日 下午4:16:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

/**
 * <p>聊天消息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class MsgAdvisoryChatVo extends MsgChatVo{

	private static final long serialVersionUID = 3251217298243932196L;
	private Integer houseCard;
	
	private String msgRealContent;

	public Integer getHouseCard() {
		return houseCard;
	}

	public void setHouseCard(Integer houseCard) {
		this.houseCard = houseCard;
	}

	public String getMsgRealContent() {
		return msgRealContent;
	}

	public void setMsgRealContent(String msgRealContent) {
		this.msgRealContent = msgRealContent;
	}

}
