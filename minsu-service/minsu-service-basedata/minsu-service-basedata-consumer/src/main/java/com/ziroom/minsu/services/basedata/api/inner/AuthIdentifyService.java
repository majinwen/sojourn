/**
 * @FileName: AuthIdentifyService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author lunan
 * @created 2017年8月31日 下午2:41:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;

/**
 * <p>自如网授权标示校验接口</p>
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
public interface AuthIdentifyService {

	/**
	 * 
	 * 获取自如网授权标示
	 *
	 * @author loushuai
	 * @created 2017年8月31日 下午2:43:48
	 *
	 * @param parmaJson
	 * @return
	 */
	public String getAuthIdentifyByCode(String parmaJson);
}
