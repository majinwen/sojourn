/**
 * @FileName: CustomerChatService.java
 * @Package com.ziroom.minsu.services.customer.api.inner
 * 
 * @author loushuai
 * @created 2017年9月18日 下午6:25:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.api.inner;

/**
 * <p>用户聊天相关业务</p>
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
public interface CustomerChatService {

	/**
	 * 
	 * 获取用户（集合）头像和昵称
	 *
	 * @author loushuai
	 * @created 2017年9月18日 下午6:26:51
	 *
	 * @param paramJson
	 * @return
	 */
	String getListUserPicAndNickName(String paramJson);
}
