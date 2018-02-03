/**
 * @FileName: StatusConfService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author jixd
 * @created 2016年9月27日 下午9:15:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;

/**
 * <p>TODO</p>
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
public interface StatusConfService {

	/**
	 * 
	 * 获取配置 根据key
	 *
	 * @author jixd
	 * @created 2016年9月27日 下午9:16:04
	 *
	 * @param key
	 * @return
	 */
	String getStatusConfByKey(String key);
}
