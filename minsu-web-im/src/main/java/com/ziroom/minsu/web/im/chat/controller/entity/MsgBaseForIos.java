/**
 * @FileName: MsgBaseForIos.java
 * @Package com.ziroom.minsu.web.im.chat.controller.entity
 * 
 * @author yd
 * @created 2016年9月27日 上午8:10:52
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>IOS 好友列表请求数据封装</p>
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
public class MsgBaseForIos implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 2049205608530577038L;
	
	/**
	 * seesionId
	 */
	private String appKey;
	
	/**
	 * 聊天记录
	 */
	private List<MsgBaseAppVo> listMsg ;

	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * @param appKey the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * @return the listMsg
	 */
	public List<MsgBaseAppVo> getListMsg() {
		return listMsg;
	}

	/**
	 * @param listMsg the listMsg to set
	 */
	public void setListMsg(List<MsgBaseAppVo> listMsg) {
		this.listMsg = listMsg;
	}
	
	
}
