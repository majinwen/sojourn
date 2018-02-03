/**
 * @FileName: MsgUserLivenessService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author loushuai
 * @created 2017年9月1日 上午11:43:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;

/**
 * <p>聊天用户活跃度</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public interface MsgUserLivenessService {

	/**
	 * 同步聊天用户活跃时间
	 *
	 * @author loushuai
	 * @created 2017年9月1日 上午11:57:38
	 *
	 * @param msgReplySetRequest
	 * @return
	 */
	public String syncLivenessTime(String param);
	
}
