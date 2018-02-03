/**
 * @FileName: CustomerInfoPcService.java
 * @Package com.ziroom.minsu.services.customer.api.inner
 * 
 * @author bushujie
 * @created 2016年7月26日 下午2:48:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.api.inner;

/**
 * <p>客户pc端相关接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public interface CustomerInfoPcService {
	
	/**
	 * 
	 * 更新客户个人资料
	 *
	 * @author bushujie
	 * @created 2016年7月26日 下午2:50:48
	 *
	 * @param paramJson
	 * @return
	 */
	public String updatePersonData(String paramJson);
	
	/**
	 * 
	 * 初始化客户认证资料
	 *
	 * @author bushujie
	 * @created 2016年7月26日 下午8:24:23
	 *
	 * @param paramJson
	 * @return
	 */
	public String initCustomerAuthData(String uid);
	
	/**
	 * 
	 * 更新客户认证资料
	 *
	 * @author bushujie
	 * @created 2016年7月27日 下午8:38:38
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateCustomerAuthData(String paramJson);
}
